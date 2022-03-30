/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.IMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReader;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;

import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReader;
import uk.ac.diamond.scisoft.analysis.io.tiff.Grey12bitTIFFReaderSpi;

/**
 * This class loads a TIFF image file
 */
public class TIFFImageLoader extends JavaImageLoader {

	private static final Logger logger = LoggerFactory.getLogger(TIFFImageLoader.class);

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
			} catch (ScanFileHolderException e) {
				throw e;
			} catch (IllegalArgumentException e) {
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

		boolean is12bitGrey = reader instanceof Grey12bitTIFFReader;
		final ImageTypeSpecifier its = is12bitGrey ? null : reader.getRawImageType(0); // this raises an exception for 12-bit images when using standard reader
		if (allSame) {
			for (int i = 1; i < n; i++) {
				if (!its.equals(reader.getRawImageType(i))) {
					throw new ScanFileHolderException("Type of image in stack does not match first");
				}
			}
		}

		Class<? extends Dataset> clazz = is12bitGrey ? ShortDataset.class : AWTImageUtils.getInterface(its.getSampleModel(), keepBitWidth);
		if (n == 1) {
			ILazyDataset image;
			if (loadLazily) {
				image = createLazyDataset(clazz, is12bitGrey, height, width);
			} else {
				try {
					if (is12bitGrey) {
						image = AWTImageUtils.makeDatasets(reader.read(0), true)[0];
					} else {
						image = AWTImageUtils.readImage(reader, 0, asGrey, keepBitWidth);
					}
				} catch (Exception e) {
					throw new ScanFileHolderException("File format in '" + fileName + "' cannot be read", e);
				}
			}
			image.setName(DEF_IMAGE_NAME);
			if (metadata != null)
				mergeMetadata(image);
			output.addDataset(DEF_IMAGE_NAME, image);
		} else if (allSame) {
			ILazyDataset ld = createLazyDataset(clazz, is12bitGrey, n, height, width);
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

	private void mergeMetadata(ILazyDataset image) {
		IMetadata imd;
		try {
			imd = image.getFirstMetadata(IMetadata.class);
			if (imd == null)
				return;

			HashMap<String, Serializable> map = new HashMap<String, Serializable>();
			for (String m : imd.getMetaNames()) {
				map.put(m, imd.getMetaValue(m));
			}
			for (String m : metadata.getMetaNames()) {
				map.put(m, imd.getMetaValue(m));
			}
			metadata.setMetadata(map);
			image.clearMetadata(IMetadata.class);
			image.setMetadata(metadata);
		} catch (Exception e) {
		}
	}

	private ILazyDataset createLazyDataset(Class<? extends Dataset> clazz, boolean is12bitGrey, final int... trueShape) {
		LazyLoaderStub l = new LazyLoaderStub() {
			private static final long serialVersionUID = -7770430899468579819L;

			@Override
			public IDataset getDataset(IMonitor mon, SliceND slice) throws IOException {
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

						d = loadData(clazz, is12bitGrey, mon, fileName, asGrey, keepBitWidth, shape, tstart, tsize, tstep);
						d.setShape(newShape); // squeeze shape back
					} else {
						d = loadData(clazz, is12bitGrey, mon, fileName, asGrey, keepBitWidth, shape, lstart, newShape, lstep);
					}
				} catch (ScanFileHolderException e) {
					throw new IOException("Problem with TIFF loading", e);
				}
				return d;
			}

		};

		return createLazyDataset(l, STACK_NAME, clazz, trueShape.clone());
	}

	private static Dataset loadData(Class<? extends Dataset> clazz, boolean is12bitGrey, IMonitor mon, String filename, boolean asGrey, boolean keepBitWidth,
			int[] oshape, int[] start, int[] count, int[] step) throws ScanFileHolderException {
		ImageInputStream iis = null;
		ImageReader reader = null;

		int rank = start.length;
		boolean is2D = rank == 2;
		int num = is2D ? 0 : start[0];
		int off = rank - 2;
		int[] nshape = Arrays.copyOfRange(oshape, off, rank);
		int[] nstart = Arrays.copyOfRange(start, off, rank);
		int[] nstep = Arrays.copyOfRange(step, off, rank);

		SliceND iSlice = new SliceND(nshape, nstart,
				new int[] {nstart[0] + count[off] * nstep[0], nstart[1] + count[off + 1] * nstep[1]},
				nstep);
		SliceND dSlice = new SliceND(count);

		Dataset d = is2D || count[0] == 1 ? null : DatasetFactory.zeros(clazz, count);

		try {
			// test to see if the filename passed will load
			iis = new FileImageInputStream(new File(filename));

			int[] dataStart = dSlice.getStart();
			int[] dataStop  = dSlice.getStop();

			Dataset image;
			if (is12bitGrey) {
				logger.debug("Using alternative 12-bit TIFF reader: {}", filename);
				reader = new Grey12bitTIFFReader(new Grey12bitTIFFReaderSpi());
			} else {
				reader = new TIFFImageReader(new TIFFImageReaderSpi());
			}
			reader.setInput(iis);
			image = readImage(filename, reader, asGrey, keepBitWidth, num);

			while (dataStart[0] < count[0]) {
				if (image == null)
					image = readImage(filename, reader, asGrey, keepBitWidth, num);
				image = image.getSliceView(iSlice);
				if (d == null) {
					d = image;
					d.setShape(count);
					break;
				}
				d.setSlice(image, dSlice);
				if (monitorIncrement(mon)) {
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

		return d;
	}

	private static Dataset readImage(String filename, ImageReader reader, boolean asGrey, boolean keepBitWidth, int num) throws IOException, ScanFileHolderException {
		String imageName = String.format(IMAGE_NAME_FORMAT, num + 1);
		IDataHolder holder = LoaderFactory.fetchData(filename, false, num);
		if (holder != null) {
			IDataset data = holder.getDataset(imageName);
			if (data != null) {
				return DatasetUtils.convertToDataset(data);
			}
		}

		int n = reader.getNumImages(true);

		if (num >= n) {
			throw new ScanFileHolderException("Number exceeds images found in '" + filename + "'");
		}

		Dataset d;
		try {
			if (reader instanceof Grey12bitTIFFReader) {
				d = AWTImageUtils.makeDatasets(reader.read(0), true)[0];
			} else {
				d = AWTImageUtils.readImage(reader, num, asGrey, keepBitWidth);
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("File format in '" + filename + "' cannot be read");
		}

		if (holder == null) {
			holder = new DataHolder();
			holder.setLoaderClass(TIFFImageLoader.class);
			holder.setFilePath(filename);
			LoaderFactory.cacheData(holder, num);
		}
		holder.addDataset(imageName, d);
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
