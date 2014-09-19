/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.IMetaLoader;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;

import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReader;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReaderSpi;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReader;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;

/**
 * This class loads a TIFF image file
 */
public class TIFFImageLoader extends JavaImageLoader implements IMetaLoader {

	protected Map<String, Serializable> metadata = null;
	private boolean loadData = true;
	private int height = -1;
	private int width = -1;
	
	public TIFFImageLoader() {
		this(null, false);
	}
	
	/**
	 * @param FileName
	 */
	public TIFFImageLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 */
	public TIFFImageLoader(String FileName, boolean convertToGrey) {
		super(FileName, "tiff", convertToGrey);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 * @param keepBitWidth
	 */
	public TIFFImageLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "tiff", convertToGrey, keepBitWidth);
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

		// TODO cope with multiple images (tiff)
		DataHolder output = new DataHolder();
		ImageReader reader = null;
		try {
			// test to see if the filename passed will load
			f = new File(fileName);
			ImageInputStream iis = new FileImageInputStream(f);

			try {
				reader = new TIFFImageReader(new TIFFImageReaderSpi());
				reader.setInput(iis);
				readImages(output, reader);
			} catch (Exception e) { // catch bad number of bits
				logger.warn("Exception using TIFFImageReader for file:" + fileName,e);
				reader = new Grey12bitTIFFReader(new Grey12bitTIFFReaderSpi());
				reader.setInput(iis);
				readImages(output, reader);
			}
		} catch (IOException e) {
			throw new ScanFileHolderException("IOException loading file '" + fileName + "'", e);
		} catch (IllegalArgumentException e) {
			throw new ScanFileHolderException("IllegalArgumentException interpreting file '" + fileName + "'", e);
		} catch (NullPointerException e) {
			throw new ScanFileHolderException("NullPointerException interpreting file '" + fileName + "'", e);
		} finally {
			if (reader != null)
				reader.dispose();
		}


		if (!loadData) {
			return null;
		}

		return output;
	}

	
	private void readImages(DataHolder output, ImageReader reader) throws IOException, ScanFileHolderException {
		int n = reader.getNumImages(true);

		if (n == 0) {
			return;
		}
		if (loadMetadata && metadata == null)
			metadata = createMetadata(reader.getImageMetadata(0));

		if (!loadData)
			return;

		if (height < 0 || width < 0) {
			height = reader.getHeight(0); // this can throw NPE when using 12-bit reader
			width = reader.getWidth(0);
			for (int i = 1; i < n; i++) {
				if (height != reader.getHeight(i)) {
					throw new ScanFileHolderException("Height of image in stack does not match first");
				}
				if (width != reader.getWidth(i)) {
					throw new ScanFileHolderException("Width of image in stack does not match first");
				}
			}
		}

		final ImageTypeSpecifier its = reader.getRawImageType(0); // this raises an exception for 12-bit images when using standard reader
		for (int i = 1; i < n; i++) {
			if (!its.equals(reader.getRawImageType(i))) {
				throw new ScanFileHolderException("Type of image in stack does not match first");
			}
		}
		if (loadMetadata) {
			output.setMetadata(getMetadata());
		}

		if (n == 1) {
			Dataset image = createDataset(reader.read(0));
			image.setMetadata(getMetadata());
			output.addDataset(DEF_IMAGE_NAME, image);
		} else {
			int dtype = createDataset(its.createBufferedImage(1, 1)).getDtype();
			ILazyDataset ld = createLazyDataset(dtype, height, width, n);
			ld.setMetadata(getMetadata());
			output.addDataset(STACK_NAME, ld);
		}
	}

	private ILazyDataset createLazyDataset(final int dtype, final int width, final int height, final int depth) {
		final int[] trueShape = new int[] {depth, height, width};

		ILazyLoader l = new ILazyLoader() {
			
			@Override
			public boolean isFileReadable() {
				return new File(fileName).canRead();
			}
			
			@Override
			public IDataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step) throws Exception {
				final int rank = shape.length;
				int[] lstart, lstop, lstep;

				if (step == null) {
					lstep = new int[rank];
					for (int i = 0; i < rank; i++) {
						lstep[i] = 1;
					}
				} else {
					lstep = step;
				}

				if (start == null) {
					lstart = new int[rank];
				} else {
					lstart = start;
				}

				if (stop == null) {
					lstop = new int[rank];
				} else {
					lstop = stop;
				}

				int[] newShape = AbstractDataset.checkSlice(shape, start, stop, lstart, lstop, lstep);

				Dataset d = null;
				try {
					if (!Arrays.equals(trueShape, shape)) {
						final int trank = trueShape.length;
						int[] tstart = new int[trank];
						int[] tsize = new int[trank];
						int[] tstep = new int[trank];

						if (rank > trank) { // shape was extended (from left) then need to translate to true slice
							int j = 0;
							for (int i = 0; i < trank; i++) {
								if (trueShape[i] == 1) {
									tstart[i] = 0;
									tsize[i] = 1;
									tstep[i] = 1;
								} else {
									while (shape[j] == 1 && (rank - j) > (trank - i))
										j++;

									tstart[i] = lstart[j];
									tsize[i] = newShape[j];
									tstep[i] = lstep[j];
									j++;
								}
							}
						} else { // shape was squeezed then need to translate to true slice
							int j = 0;
							for (int i = 0; i < trank; i++) {
								if (trueShape[i] == 1) {
									tstart[i] = 0;
									tsize[i] = 1;
									tstep[i] = 1;
								} else {
									tstart[i] = lstart[j];
									tsize[i] = newShape[j];
									tstep[i] = lstep[j];
									j++;
								}
							}
						}

						d = loadData(mon, fileName, asGrey, keepBitWidth, dtype, tstart, tsize, tstep);
						d.setShape(newShape); // squeeze shape back
					} else {
						d = loadData(mon, fileName, asGrey, keepBitWidth, dtype, lstart, newShape, lstep);
					}
				} catch (Exception e) {
					throw new ScanFileHolderException("Problem with TIFF loading", e);
				}
				return d;
			}

		};

		return new LazyDataset(STACK_NAME, dtype, 1, trueShape.clone(), l);
	}

	private static Dataset loadData(IMonitor mon, String filename, boolean asGrey, boolean keepBitWidth,
			int dtype, int[] start, int[] count, int[] step) throws ScanFileHolderException {
		ImageInputStream iis = null;
		ImageReader reader = null;
		Dataset d = DatasetFactory.zeros(count, dtype);

		try {
			// test to see if the filename passed will load
			iis = new FileImageInputStream(new File(filename));

			int[] imageStart = new int[] {start[1], start[2]};
			int[] imageStop  = new int[] {start[1] + count[1] * step[1], start[2] + count[2] * step[2]};
			int[] imageStep  = new int[] {step[1], step[2]};
			int[] dataStart = new int[d.getRank()];
			int[] dataStop  = count.clone();

			try {
				reader = new TIFFImageReader(new TIFFImageReaderSpi());
				reader.setInput(iis);

			} catch (IllegalArgumentException e) { // catch bad number of bits
				logger.warn("Exception using TIFFImageReader for file:" + filename, e);
				reader = new Grey12bitTIFFReader(new Grey12bitTIFFReaderSpi());
				reader.setInput(iis);
			}
			int num = start[0];
			do {
				Dataset image = readImage(filename, reader, asGrey, keepBitWidth, num);
				d.setSlice(image.getSliceView(imageStart, imageStop, imageStep), dataStart, dataStop, null);
				if (mon != null) {
					mon.worked(1);
				}
				num += step[0];
				dataStart[0]++;
				dataStop[0] = dataStart[0] + 1;
			} while (dataStart[0] < count[0]);
		} catch (IOException e) {
			throw new ScanFileHolderException("IOException loading file '" + filename + "'", e);
		} catch (IllegalArgumentException e) {
			throw new ScanFileHolderException("IllegalArgumentException interpreting file '" + filename + "'", e);
		} finally {
			if (reader != null)
				reader.dispose();
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
				}
			}
		}

		if (!Arrays.equals(count, d.getShapeRef())) {
			throw new ScanFileHolderException("Image does not have expected shape");

		}
		return d;
	}

	private static Dataset readImage(String filename, ImageReader reader, boolean asGrey, boolean keepBitWidth, int num) throws IOException, ScanFileHolderException {
		int n = reader.getNumImages(true);

		if (num >= n) {
			throw new ScanFileHolderException("Number exceeds images found in '" + filename + "'");
		}
		BufferedImage input = reader.read(num);
		if (input == null) {
			throw new ScanFileHolderException("File format in '" + filename + "' cannot be read");
		}

		return createDataset(input, asGrey, keepBitWidth);
	}


	/**
	 * This can be overridden to add metadata
	 * @param imageMetadata
	 * @return metadata map
	 */
	@SuppressWarnings("unused")
	protected Map<String, Serializable> createMetadata(IIOMetadata imageMetadata) throws ScanFileHolderException {
		try {
			Map<String, Serializable> metadataTable = new HashMap<String, Serializable>();
			TIFFDirectory tiffDir;
			try {
				tiffDir = TIFFDirectory.createFromMetadata(imageMetadata);
			} catch (IIOInvalidTreeException e) {
				throw new ScanFileHolderException("Problem creating TIFF directory from header", e);
			}
	
			TIFFField[] tiffField = tiffDir.getTIFFFields();
			for (int i = 0; i < tiffField.length; i++) {
				TIFFField field = tiffField[i];
				metadataTable.put(field.getTag().getName(), field.getValueAsString(0));
			}
			return metadataTable;
		} catch (Throwable ne) {
			return null;
		}
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		loadData = false;
		loadFile();
		loadData = true;
	}

	@Override
	public IMetadata getMetadata() {
		return getMetaData(null);
	}

	public IMetadata getMetaData(Dataset data) {
		if (metadata == null) {
			if (data!=null) return data.getMetadata(); // Might be null or might be set in AWTImageUtils.
			return null;
		}

		Metadata md = new Metadata(metadata);
		md.setFilePath(fileName);
		return md;
	}
}
