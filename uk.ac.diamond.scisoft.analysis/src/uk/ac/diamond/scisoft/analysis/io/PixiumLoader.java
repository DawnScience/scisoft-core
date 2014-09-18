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

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;

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

	private double getDouble(BufferedReader in) throws IOException, ScanFileHolderException {
		String line = in.readLine();
		if (line == null) {
			throw new ScanFileHolderException("End of file reached during metadata reading");
		}
		String[] parts = line.split("\t");
		if (parts.length < 2) {
			throw new ScanFileHolderException("No tab separated values on line");
		}
		return Double.parseDouble(parts[1]);
	}

	private Map<String, Serializable> readMetadata(File metadata) throws NumberFormatException, IOException, NullPointerException, ScanFileHolderException {
		
		// load the metadata info
		BufferedReader br = new BufferedReader(new FileReader(metadata));
		
		double pixSize = getDouble(br);
		double detX = getDouble(br);
		double detY = getDouble(br);
		double detDistance = getDouble(br);
		double centX = getDouble(br);
		double centY = getDouble(br);
		double wavelength = getDouble(br);
		
		
		HashMap<String, Serializable> GDAMetadata = new HashMap<String, Serializable>(); 
		
		// NXGeometery:NXtranslation
		double pixelsize = pixSize;
		double[] detectorOrigin = {
				detX * pixelsize - centX,
				detY * pixelsize - centY,
				detDistance };
		GDAMetadata.put("NXdetector:NXgeometry:NXtranslation", detectorOrigin);
		GDAMetadata.put("NXdetector:NXgeometry:NXtranslation@units","milli*meter");
		
		// NXGeometery:NXOrientation
		double [] directionCosine = {1,0,0,0,1,0}; // to form identity matrix as no header data
		GDAMetadata.put("NXdetector:NXgeometry:NXorientation",directionCosine);
		// NXGeometery:XShape (shape from origin (+x, +y, +z,0, 0, 0) > x,y,0,0,0,0)
		double[] detectorShape = {
				detX * pixSize,
				detY * pixSize,0,0,0,0 };
		GDAMetadata.put("NXdetector:NXgeometry:NXshape", detectorShape);
		GDAMetadata.put("NXdetector:NXgeometry:NXshape@units", "milli*metre");
		
		// NXGeometery:NXFloat
		double[] pixelSize = { pixSize,
				pixSize };
		GDAMetadata.put("NXdetector:x_pixel_size", pixelSize[0]);
		GDAMetadata.put("NXdetector:x_pixel_size@units", "milli*metre");
		GDAMetadata.put("NXdetector:y_pixel_size", pixelSize[1]);
		GDAMetadata.put("NXdetector:y_pixel_size@units", "milli*metre");
		// "NXmonochromator:wavelength"
		GDAMetadata.put("NXmonochromator:wavelength",wavelength);
		GDAMetadata.put("NXmonochromator:wavelength@units", "Angstrom");
		
		// oscillation range
		GDAMetadata.put("NXsample:rotation_start",0.0);
		GDAMetadata.put("NXsample:rotation_start@units","degree");
		GDAMetadata.put("NXsample:rotation_range",0.0);
		GDAMetadata.put("NXsample:rotation_range@units", "degree");
		
		//Exposure time
		GDAMetadata.put("NXsample:exposure_time", 0.0);
		GDAMetadata.put("NXsample:exposure_time@units", "seconds");
		
		br.close();
		return GDAMetadata;
	}
	
}
