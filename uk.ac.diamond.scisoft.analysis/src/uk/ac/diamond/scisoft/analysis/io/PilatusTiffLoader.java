/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;

/**
 *
 */
public class PilatusTiffLoader extends TIFFImageLoader {
	
	/**
	 * @param FileName
	 */
	public PilatusTiffLoader(String FileName) {
		super(FileName);
	}
	
	static String[] PILATUS_NAMES = { "Pixel_size", "Silicon sensor, thickness", "Exposure_time", "Exposure_period",
		"Tau =", "Threshold_setting", "N_excluded_pixels", "Excluded_pixels:", "Trim_directory:", "Flat_field:" };

	@Override
	protected Map<String,Serializable> createMetadataMap(IIOMetadata imageMetadata) throws ScanFileHolderException {

		Map<String, Serializable> metadataTable = new HashMap<String, Serializable>();
        
		String temp = "";
		TIFFDirectory tiffDir;
		try {
			tiffDir = TIFFDirectory.createFromMetadata(imageMetadata);
		} catch (IIOInvalidTreeException e) {
			throw new ScanFileHolderException("Problem creating TIFF directory from header", e);
		}

		TIFFField[] tiffField = tiffDir.getTIFFFields();
		int unknownNum = 0;
		boolean found = false;
		for (int i = 0; i < tiffField.length; i++) {
			TIFFField field = tiffField[i];
			if (field.getTag().getName().equalsIgnoreCase("ImageDescription")) {
				BufferedReader in = new BufferedReader(new StringReader(field.getValueAsString(0)));
				try {
					while ((temp = in.readLine()) != null) {
						found = false;
						for (int j = 0; j < PILATUS_NAMES.length; j++) {
							if (temp.contains(PILATUS_NAMES[j])) {
								metadataTable.put(PILATUS_NAMES[j],
									temp.substring(temp.indexOf(PILATUS_NAMES[j]) + PILATUS_NAMES[j].length(),
										temp.length()).trim());
								found = true;
								break;
							}
						}
						if (!found) {
							metadataTable.put("Unknown " + unknownNum, temp);
							unknownNum++;
						}
					}
				} catch (IOException e) {
					throw new ScanFileHolderException("Problem reading TIFF header", e);
				}
			} else
				metadataTable.put(field.getTag().getName(), field.getValueAsString(0));
		}
		
		return createGDAMetadata(metadataTable);
	}

	// Fix to http://jira.diamond.ac.uk/browse/DAWNSCI-851 whereby the
	// caching is not working for tifs because they have no metadata.
	private Map<String, Serializable> createGDAMetadata(Map<String, Serializable> metadataTable ) {
		
		Map<String, Serializable> metaData = new HashMap<String, Serializable>();
		metaData.putAll(metadataTable);

		String pixelSize = (String)metadataTable.get("Pixel_size");
		if (pixelSize != null) {
			String[] xypixVal = pixelSize.split("m x");
	
			double xPxVal = Double.parseDouble(xypixVal[0])*1000;
			double yPXVal = Double.parseDouble(xypixVal[1].split("m")[0])*1000;
			
			// NXGeometery:NXFloat
			metaData .put("NXdetector:x_pixel_size", xPxVal);
			metaData.put("NXdetector:x_pixel_size@units", "milli*metre");
			metaData.put("NXdetector:y_pixel_size", yPXVal);
			metaData.put("NXdetector:y_pixel_size@units", "milli*metre");
		} 
		
		return metaData;
	
	}
}
