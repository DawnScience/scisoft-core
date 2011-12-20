/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AWTImageUtils;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.RGBDataset;

/**
 * Class that loads in data from an image file using native Java ImageIO
 * library with built-in image reader/writer.
 * <p>
 * A Raster object comprises a SampleModel and a DataBuffer where we have the
 * model of an image comprising bands of samples so that each pixel is a tuple
 * of samples (e.g. R, G, B) and the SampleModel maps to/from a sample (of a
 * pixel) to information held by the DataBuffer. A BufferedImage object
 * comprises a Raster and a ColorModel. The reader/writer handles BufferImages
 * so access the image data via the BufferedImage's Raster attribute.
 */
public class JavaImageLoader extends AbstractFileLoader {
	transient protected static final Logger logger = LoggerFactory.getLogger(JavaImageLoader.class);

	protected String fileName = "";
	private String fileType = "";
	private boolean asGrey;
	private boolean keepBitWidth = false;

	
	public void setFile(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return true if loader keeps bit width of pixels
	 */
	public boolean isKeepBitWidth() {
		return keepBitWidth;
	}

	/**
	 * set loader to keep bit width of pixels
	 * @param keepBitWidth
	 */
	public void setKeepBitWidth(boolean keepBitWidth) {
		this.keepBitWidth = keepBitWidth;
	}

	/**
	 * @param FileName
	 *            which is the name of the file being passed to this class.
	 * @param FileType
	 *            which is the type of image being passed to the class
	 */
	public JavaImageLoader(String FileName, String FileType) {
		this(FileName, FileType, false);
	}

	/**
	 * @param FileName
	 *            which is the name of the file being passed to this class.
	 * @param FileType
	 *            which is the type of image being passed to the class
	 * @param convertToGrey
	 *            interpret colour image as a greyscale image (rather than taking the first colour channel)
	 */
	public JavaImageLoader(String FileName, String FileType, boolean convertToGrey) {
		this(FileName, FileType, convertToGrey, false);
	}

	/**
	 * @param FileName
	 *            which is the name of the file being passed to this class.
	 * @param FileType
	 *            which is the type of image being passed to the class
	 * @param convertToGrey
	 *            interpret colour image as a greyscale image (rather than taking the first colour channel)
	 * @param keepBitWidth
	 *            true if loader keeps bit width of pixels
	 */
	public JavaImageLoader(String FileName, String FileType, boolean convertToGrey, boolean keepBitWidth) {
		fileName = FileName;
		fileType = FileType; // format name
		asGrey = convertToGrey;
		this.keepBitWidth = keepBitWidth;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		AbstractDataset data = null;
		File f = null;
		BufferedImage input = null;

		// Check for file
		f = new File(fileName);
		if (!f.exists()) {
			logger.warn("File, {}, did not exist. Now trying to replace suffix", fileName);
			f = findCorrectSuffix();
		}

		// TODO cope with multiple images (gif, tiff)
		try {
			// test to see if the filename passed will load
			f = new File(fileName);

			input = ImageIO.read(f);
			if (input == null) {
				throw new ScanFileHolderException("File format in '" + fileName + "' cannot be read");
			}
		} catch (IOException e) {
			throw new ScanFileHolderException("IOException loading file '" + fileName + "'", e);
		} catch (IllegalArgumentException e) {
			throw new ScanFileHolderException("IllegalArgumentException interpreting file '" + fileName + "'", e);
		}

		data = createDataset(input);

		DataHolder output = new DataHolder();
		output.addDataset(fileName, data);
		return output;
	}

	protected AbstractDataset createDataset(BufferedImage input) throws ScanFileHolderException {
		AbstractDataset data = null;
		try {
			AbstractDataset[] channels = AWTImageUtils.makeDatasets(input, keepBitWidth);
			final int bands = input.getData().getNumBands();

			if (bands == 1) {
				data = channels[0];
			} else {
				if (input.getColorModel().getColorSpace().getType() != ColorSpace.TYPE_RGB) {
					throw new ScanFileHolderException("File does not contain RGB data");					
				}
				if (bands < 3) {
					throw new ScanFileHolderException("Number of colour channels is less than three so cannot load and convert");
				}

				data = new RGBDataset(channels[0], channels[1], channels[2]);

				if (asGrey)
					data = ((RGBDataset) data).createGreyDataset(channels[0].getDtype());
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("There was a problem loading the image", e);
		}
		return data;
	}

	protected File findCorrectSuffix() throws ScanFileHolderException {
		String[] suffixes = ImageIO.getReaderFileSuffixes();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		File f = null;
		testforsuffix: {
			if (!extension.equals(fileName)) { // there is a suffix
				for (String s : suffixes) {
					if (extension.equalsIgnoreCase(s)) {
						break testforsuffix;
					}
				}
			}
			// try standard suffix first then all supported suffixes
			String name = fileName + "." + fileType;
			f = new File(name);
			if (f.exists()) {
				fileName = name;
				break testforsuffix;
			}
			for (String s : suffixes) {
				name = fileName + "." + s;
				f = new File(name);
				if (f.exists()) {
					fileName = name;
					break testforsuffix;
				}
			}
		}
		if (f == null || !f.exists()) {
			throw new ScanFileHolderException("Does not exist",
					new FileNotFoundException(fileName));
		}
		return f;
	}
}
