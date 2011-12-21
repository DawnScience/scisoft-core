/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.metadata.IIOMetadata;

import gda.analysis.io.ScanFileHolderException;

public class PixiumLoader extends TIFFImageLoader {
	
	/**
	 * @param FileName
	 */
	public PixiumLoader(String FileName) {
		super(FileName, false);
	}

	@Override
	protected Map<String, Serializable> createMetadata(IIOMetadata imageMetadata) throws ScanFileHolderException {
		// check to see if our metadata exists
		File file = new File(fileName);
		File mFile = new File(file.getParent(), "metadata.inf");

		if (!mFile.exists())
			throw new ScanFileHolderException("No metadata found, please create metadata.inf file");
		
		try {
			return readMetadata(mFile);
		} catch (Exception e) {
			throw new ScanFileHolderException("Could not read metadata file contents, check the metadata.inf file is correct",e);
		} 
	}

	private Map<String, Serializable> readMetadata(File metadata) throws NumberFormatException, IOException {
		
		// load the metadata info
		BufferedReader br = new BufferedReader(new FileReader(metadata));
		
		double pixSize = Double.parseDouble(br.readLine().split("\t")[1]); 
		double detX = Double.parseDouble(br.readLine().split("\t")[1]);
		double detY = Double.parseDouble(br.readLine().split("\t")[1]);
		double detDistance = Double.parseDouble(br.readLine().split("\t")[1]);
		double centX = Double.parseDouble(br.readLine().split("\t")[1]);
		double centY = Double.parseDouble(br.readLine().split("\t")[1]);
		double wavelength = Double.parseDouble(br.readLine().split("\t")[1]);
		
		
		HashMap<String, Serializable> GDAMetadata = new HashMap<String, Serializable>(); 
		
		// NXGeometery:NXtranslation
		double pixelsize = pixSize;
		double[] detectorOrigin = {
				detX * pixelsize - centX,
				detY * pixelsize - centY,
				detDistance };
		GDAMetadata.put("NXdetector:NXgeometery:NXtranslation", detectorOrigin);
		GDAMetadata.put("NXdetector:NXgeometery:NXtranslation:NXunits","milli*meter");
		
		// NXGeometery:NXOrientation
		double [] directionCosine = {1,0,0,0,1,0}; // to form identity matrix as no header data
		GDAMetadata.put("NXdetector:NXgeometery:NXorientation",directionCosine);
		// NXGeometery:XShape (shape from origin (+x, +y, +z,0, 0, 0) > x,y,0,0,0,0)
		double[] detectorShape = {
				detX * pixSize,
				detY * pixSize,0,0,0,0 };
		GDAMetadata.put("NXdetector:NXgeometery:NXshape", detectorShape);
		GDAMetadata.put("NXdetector:NXgeometery:NXshape:NXshape", "milli*metre");
		
		// NXGeometery:NXFloat
		double[] pixelSize = { pixSize,
				pixSize };
		GDAMetadata.put("NXdetector:x_pixel_size", pixelSize[0]);
		GDAMetadata.put("NXdetector:x_pixel_size:NXunits", "milli*metre");
		GDAMetadata.put("NXdetector:y_pixel_size", pixelSize[1]);
		GDAMetadata.put("NXdetector:y_pixel_size:NXunits", "milli*metre");
		// "NXmonochromator:wavelength"
		GDAMetadata.put("NXmonochromator:wavelength",wavelength);
		GDAMetadata.put("NXmonochromator:wavelength:NXunits", "Angstrom");
		
		// oscillation range
		GDAMetadata.put("NXSample:rotation_start",0.0);
		GDAMetadata.put("NXSample:rotation_start:NXUnits","degree");
		GDAMetadata.put("NXSample:rotation_range",0.0);
		GDAMetadata.put("NXSample:rotation_range:NXUnits", "degree");
		
		//Exposure time
		GDAMetadata.put("NXSample:exposure_time", 0.0);
		GDAMetadata.put("NXSample:exposure_time:NXUnits", "seconds");
		
		return GDAMetadata;
	}
	
}
