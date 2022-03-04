/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.awt.image.SampleModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.ImageInputStream;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(JavaImageLoader.class);

	private String fileType = "";
	protected boolean asGrey;
	protected boolean keepBitWidth = false;

	/**
	 * @return true if loader keeps bit width of pixels
	 */
	public boolean isKeepBitWidth() {
		return keepBitWidth;
	}

	/**
	 * Set loader to keep bit width of pixels. This is ignored and taken as true if image is colour
	 * @param keepBitWidth
	 */
	public void setKeepBitWidth(boolean keepBitWidth) {
		this.keepBitWidth = keepBitWidth;
	}

	/**
	 * @param FileName
	 *            which is the name of the file being passed to this class.
	 */
	public JavaImageLoader(String FileName) {
		this(FileName, null);
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
		if (FileType == null) {
			int i = fileName.lastIndexOf(".");
			if (i > 0 && i < fileName.length() - 1) {
				fileType = fileName.substring(i+1);
			} else {
				fileType = "";
			}
		} else {
			fileType = FileType; // format name
		}
		asGrey = convertToGrey;
		this.keepBitWidth = keepBitWidth;
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		File f = null;

		// Check for file
		f = new File(fileName);
		if (!f.exists()) {
			logger.warn("File, {}, did not exist. Now trying to replace suffix", fileName);
			f = findCorrectSuffix();
		}

		DataHolder output = new DataHolder();
		// test to see if the filename passed will load
		f = new File(fileName);

		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(f);
		} catch (Exception e) {
			logger.error("Problem creating input stream for file " + fileName, e);
			throw new ScanFileHolderException("Problem creating input stream for file " + fileName, e);
		}
		if (iis == null) {
			logger.error("File format in '{}' cannot be read", fileName);
			throw new ScanFileHolderException("File format in '" + fileName + "' cannot be read");
		}
		Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
		boolean loaded = false;
		while (it.hasNext()) {
			ImageReader reader = it.next();
			reader.setInput(iis, false, loadMetadata);
			if (loadLazily) {
				loaded = createLazyDatasets(output, reader);
			} else {
				loaded = createDatasets(output, reader);
			}
			if (loaded) {
				if (loadMetadata) {
					createMetadata(output, reader);
				}
				break;
			}
		}
		if (!loaded) {
			logger.error("File format in '{}' cannot be read", fileName);
			throw new ScanFileHolderException("File format in '" + fileName + "' cannot be read");
		}

		return output;
	}

	private boolean createDatasets(DataHolder output, ImageReader reader) {
		for (int i = 0; true; i++) {
			try {
				String name = String.format(IMAGE_NAME_FORMAT, i+1);
				Dataset data = AWTImageUtils.readImage(reader, i, asGrey, keepBitWidth);
				data.setName(name);
				output.addDataset(name, data);
			} catch (IOException e) {
				return false;
			} catch (IndexOutOfBoundsException e) {
				break;
			} catch (Exception e) {
				logger.error("Problem with creating dataset from image", e);
				return false;
			}
		}
		return true;
	}

	protected boolean createLazyDatasets(DataHolder output, ImageReader reader) {
		for (int i = 0; true; i++) {
			try {
				int[] shape = new int[] {reader.getHeight(i), reader.getWidth(i)};
				Iterator<ImageTypeSpecifier> it = reader.getImageTypes(i);
				SampleModel sm = it.next().getSampleModel();
				Class<? extends Dataset> clazz = AWTImageUtils.getInterface(sm, keepBitWidth);
				final String name = String.format(IMAGE_NAME_FORMAT, i + 1);
				final int num = i;
				LazyDataset lazy = createLazyDataset(new LazyLoaderStub() {
					private static final long serialVersionUID = -6725836260790990070L;

					@Override
					public IDataset getDataset(IMonitor mon, SliceND slice) throws IOException {
						try {
							Dataset data = loadDataset(fileName, name, num, asGrey, keepBitWidth);
							return data == null ? null : data.getSliceView(slice);
						} catch (ScanFileHolderException e) {
							throw new IOException(e);
						}
					}
				}, name, clazz, shape);
				output.addDataset(name, lazy);
//				IIOMetadata imd = reader.getImageMetadata(i);
//				imd.getAsTree(imd.getNativeMetadataFormatName()).toString();
			} catch (IndexOutOfBoundsException e) {
				break;
			} catch (Exception e) {
				logger.warn("Could not get height or width for image {}", i);
				continue;
			}
		}
		return output.getNames().length > 0;
	}

	private static Dataset loadDataset(String path, String name, int num, boolean asGrey, boolean keepBitWidth) throws ScanFileHolderException {
		IDataHolder holder = LoaderFactory.fetchData(path, false, num);
		if (holder != null) {
			IDataset data = holder.getDataset(name);
			if (data != null) {
				return DatasetUtils.convertToDataset(data);
			}
		}

		File f = new File(path);

		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(f);
		} catch (Exception e) {
			logger.error("Problem creating input stream for file " + path, e);
			throw new ScanFileHolderException("Problem creating input stream for file " + path, e);
		}
		if (iis == null) {
			logger.error("File format in '{}' cannot be read", path);
			throw new ScanFileHolderException("File format in '" + path + "' cannot be read");
		}
		Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
		while (it.hasNext()) {
			ImageReader reader = it.next();
			reader.setInput(iis, false, true);
			Dataset data;
			try {
				data = AWTImageUtils.readImage(reader, num, asGrey, keepBitWidth);
				data.setName(name);

				if (holder == null) {
					holder = new DataHolder();
					holder.setLoaderClass(JavaImageLoader.class);
					holder.setFilePath(path);
					LoaderFactory.cacheData(holder, num);
				}
				holder.addDataset(name, data);
				return data;
			} catch (IndexOutOfBoundsException e) {
				throw new ScanFileHolderException("Image number is incorrect");
			} catch (IOException e) {
				logger.error("Problem reading file", e);
			} catch (Exception e) {
				logger.error("Problem creating dataset", e);
			}
		}

		return null;
	}

	protected void createMetadata(DataHolder output, @SuppressWarnings("unused") ImageReader reader) {
		if (metadata == null) {
			metadata = new Metadata();
		}
		metadata.setFilePath(fileName);
		for (String n : output.getNames()) {
			ILazyDataset lazy = output.getLazyDataset(n);
			metadata.addDataInfo(n, lazy.getShape());
		}
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
