/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;


import gda.analysis.io.ScanFileHolderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;

/**
 *
 */
public class PilatusTiffLoader extends TIFFImageLoader {
	private Map<String, String> metadataTable = new HashMap<String, String>();

	/**
	 * @param FileName
	 */
	public PilatusTiffLoader(String FileName) {
		super(FileName);
	}
	
	static String[] PILATUS_NAMES = { "Pixel_size", "Silicon sensor, thickness", "Exposure_time", "Exposure_period",
		"Tau =", "Threshold_setting", "N_excluded_pixels", "Excluded_pixels:", "Trim_directory:", "Flat_field:" };

	@Override
	protected Map<String,Serializable> createMetadata(IIOMetadata imageMetadata) throws ScanFileHolderException {
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
		
		return createGDAMetadata();
	}

	private Map<String, Serializable> createGDAMetadata() {
		String pixelSize = metadataTable.get("Pixel_size");
		if (pixelSize == null)
			return null;
		String[] xypixVal = pixelSize.split("m x");

		double xPxVal = Double.parseDouble(xypixVal[0])*1000;
		double yPXVal = Double.parseDouble(xypixVal[1].split("m")[0])*1000;
		
		
		Map<String, Serializable> GDAMetadata = new HashMap<String, Serializable>();
		// NXGeometery:NXFloat
		GDAMetadata .put("NXdetector:x_pixel_size", xPxVal);
		GDAMetadata.put("NXdetector:x_pixel_size:NXunits", "milli*metre");
		GDAMetadata.put("NXdetector:y_pixel_size", yPXVal);
		GDAMetadata.put("NXdetector:y_pixel_size:NXunits", "milli*metre");
		return GDAMetadata;
	}
}
