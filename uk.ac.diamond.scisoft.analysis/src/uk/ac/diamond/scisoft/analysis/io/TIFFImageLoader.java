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
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;

import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReader;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReaderSpi;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReader;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;

/**
 * This class loads a TIFF image file
 */
public class TIFFImageLoader extends JavaImageLoader {

	protected Map<String, Serializable> metadataMap = null;
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
				readImages(output, reader); // this raises an exception for 12-bit images when using standard reader
			} catch (Exception e) {
				logger.debug("Using alternative 12-bit TIFF reader: {}", fileName);
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
		if (loadMetadata && metadataMap == null)
			metadataMap = createMetadataMap(reader.getImageMetadata(0));

		if (!loadData)
			return;

		boolean allSame = true;
		if (height < 0 || width < 0) {
			height = reader.getHeight(0); // this can throw NPE when using 12-bit reader
			width = reader.getWidth(0);
			for (int i = 1; i < n; i++) {
				if (height != reader.getHeight(i) || width != reader.getWidth(i)) {
					allSame = false;
					break;
				}
			}
		}

		final ImageTypeSpecifier its = reader.getRawImageType(0); // this raises an exception for 12-bit images when using standard reader
		if (allSame) {
			for (int i = 1; i < n; i++) {
				if (!its.equals(reader.getRawImageType(i))) {
					throw new ScanFileHolderException("Type of image in stack does not match first");
				}
			}
		}
		int dtype = AWTImageUtils.getDTypeFromImage(its.getSampleModel(), keepBitWidth)[0];
		if (n == 1) {
			ILazyDataset image;
			if (loadLazily) {
				image = createLazyDataset(dtype, height, width);
			} else {
				image = createDataset(reader.read(0));
			}
			image.setMetadata(metadata);
			output.addDataset(DEF_IMAGE_NAME, image);
		} else if (allSame) {
			ILazyDataset ld = createLazyDataset(dtype, n, height, width);
			ld.setMetadata(metadata);
			output.addDataset(STACK_NAME, ld);
		} else {
			createLazyDatasets(output, reader);
		}

		if (loadMetadata) {
			createMetadata(output, reader);
			metadata.setMetadata(metadataMap);
			output.setMetadata(metadata);
		}
	}

	private ILazyDataset createLazyDataset(final int dtype, final int... trueShape) {
		LazyLoaderStub l = new LazyLoaderStub() {
			@Override
			public IDataset getDataset(IMonitor mon, SliceND slice) throws Exception {
				int[] lstart = slice.getStart();
				int[] lstep  = slice.getStep();
				int[] newShape = slice.getShape();
				int[] shape = slice.getSourceShape();
				final int rank = shape.length;

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

						d = loadData(mon, fileName, asGrey, keepBitWidth, dtype, shape, tstart, tsize, tstep);
						d.setShape(newShape); // squeeze shape back
					} else {
						d = loadData(mon, fileName, asGrey, keepBitWidth, dtype, shape, lstart, newShape, lstep);
					}
				} catch (Exception e) {
					throw new ScanFileHolderException("Problem with TIFF loading", e);
				}
				return d;
			}

		};

		return createLazyDataset(STACK_NAME, dtype, trueShape.clone(), l);
	}

	private static Dataset loadData(IMonitor mon, String filename, boolean asGrey, boolean keepBitWidth,
			int dtype, int[] oshape, int[] start, int[] count, int[] step) throws ScanFileHolderException {
		ImageInputStream iis = null;
		ImageReader reader = null;
		Dataset d = DatasetFactory.zeros(count, dtype);

		try {
			// test to see if the filename passed will load
			iis = new FileImageInputStream(new File(filename));

			int rank = start.length;
			boolean is2D = rank == 2;
			int num = is2D ? 0 : start[0];
			int off = is2D ? 0 : rank - 2;
			int[] nshape = Arrays.copyOfRange(oshape, off, rank);
			int[] nstart = Arrays.copyOfRange(start, off, rank);
			int[] nstep = Arrays.copyOfRange(step, off, rank);

			SliceND iSlice = new SliceND(nshape, nstart,
					new int[] {nstart[0] + count[off] * nstep[0], nstart[1] + count[off + 1] * nstep[1]},
					nstep);
			SliceND dSlice = new SliceND(count);
			int[] dataStart = dSlice.getStart();
			int[] dataStop  = dSlice.getStop();

			Dataset image;
			try {
				reader = new TIFFImageReader(new TIFFImageReaderSpi());
				reader.setInput(iis);

				image = readImage(filename, reader, asGrey, keepBitWidth, num);
			} catch (IllegalArgumentException e) { // catch bad number of bits
				logger.debug("Using alternative 12-bit TIFF reader: {}", filename);
				reader = new Grey12bitTIFFReader(new Grey12bitTIFFReaderSpi());
				reader.setInput(iis);

				image = readImage(filename, reader, asGrey, keepBitWidth, num);
			}

			while (dataStart[0] < count[0]) {
				if (image == null)
					image = readImage(filename, reader, asGrey, keepBitWidth, num);
				d.setSlice(image.getSliceView(iSlice), dSlice);
				if (monitorIncrement(mon) || is2D) {
					break;
				}
				num += step[0];
				dataStart[0]++;
				dataStop[0] = dataStart[0] + 1;
				image = null;
			} 
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
		IDataHolder holder = LoaderFactory.fetchData(filename, false, num);
		if (holder != null)
			return (Dataset) holder.getDataset(0);

		int n = reader.getNumImages(true);

		if (num >= n) {
			throw new ScanFileHolderException("Number exceeds images found in '" + filename + "'");
		}
		BufferedImage input = reader.read(num);
		if (input == null) {
			throw new ScanFileHolderException("File format in '" + filename + "' cannot be read");
		}

		Dataset d = createDataset(input, asGrey, keepBitWidth);
		holder = new DataHolder();
		holder.setLoaderClass(TIFFImageLoader.class);
		holder.setFilePath(filename);
		holder.addDataset(DEF_IMAGE_NAME, d);
		LoaderFactory.cacheData(holder, num);
		return d;
	}

	/**
	 * This can be overridden to add metadata
	 * @param imageMetadata
	 * @return metadata map
	 */
	@SuppressWarnings("unused")
	protected Map<String, Serializable> createMetadataMap(IIOMetadata imageMetadata) throws ScanFileHolderException {
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
	protected void clearMetadata() {
		super.clearMetadata();
		metadataMap.clear();
	}
}
