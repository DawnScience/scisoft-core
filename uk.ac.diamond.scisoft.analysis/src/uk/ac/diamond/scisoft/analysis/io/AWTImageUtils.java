/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;

import javax.media.jai.DataBufferFloat;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;

import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.CompoundShortDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.RGBByteDataset;
import org.eclipse.january.dataset.RGBDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;

/**
 * Helper methods to convert to/from AWT images and datasets
 */
public class AWTImageUtils {
	private AWTImageUtils() {
	}

	/**
	 * Create datasets from a Raster
	 * @param r raster
	 * @param clazz dataset interface
	 * @param data array to output datasets
	 */
	public static void createDatasets(Raster r, Class <? extends Dataset> clazz, Dataset[] data) {
		final int bands = data.length;
		final int height = r.getHeight();
		final int width = r.getWidth();

		SampleModel sm = r.getSampleModel();
		boolean blueFirst = false;
		if (sm instanceof ComponentSampleModel && ((ComponentSampleModel) sm).getBandOffsets()[0] == 2) {
			blueFirst = true;
		}

		if (clazz.equals(RGBByteDataset.class) || clazz.equals(RGBDataset.class)) {
			DataBuffer db = r.getDataBuffer();
			if (db instanceof DataBufferByte) {
				data[0] = DatasetFactory.createFromObject(clazz, ((DataBufferByte) db).getData(), height, width);
				if (blueFirst) {
					switchElements((RGBByteDataset) data[0]);
				}
			} else if (db instanceof DataBufferShort) {
				if (blueFirst) {
					CompoundShortDataset tmp = DatasetFactory.createFromObject(3, CompoundShortDataset.class, ((DataBufferShort) db).getData(), height, width);
					data[0] = new RGBDataset(tmp.getElementsView(2), tmp.getElementsView(1), tmp.getElementsView(0));
				} else {
					data[0] = DatasetFactory.createFromObject(clazz, ((DataBufferShort) db).getData(), height, width);
				}
			}
			return;
		}

		Dataset tmp;
		for (int i = 0; i < bands; i++) {
			if (FloatDataset.class.isAssignableFrom(clazz)) {
				tmp =  DatasetFactory.createFromObject(r.getSamples(0, 0, width, height, i, (float[]) null), height, width);
			} else if (DoubleDataset.class.isAssignableFrom(clazz)) {
				tmp = DatasetFactory.createFromObject(r.getSamples(0, 0, width, height, i, (double[]) null), height, width);
			} else if (IntegerDataset.class.isAssignableFrom(clazz)) {
				tmp = DatasetFactory.createFromObject(r.getSamples(0, 0, width, height, i, (int[]) null), height, width);
			} else {
				tmp = DatasetFactory.createFromObject(clazz, r.getSamples(0, 0, width, height, i, (int[]) null), height, width);
			}
			data[i] = tmp;
		}
	}

	// just to avoid RGBByteDataset constructor as it's noisy
	private static void switchElements(RGBByteDataset bgr) {
		byte[] bd = bgr.getData();
		for (int i = 0, imax = bd.length; i < imax; i += 3) {
			int k = i + 2;
			byte b = bd[i]; // switch blue and red elements
			bd[i] = bd[k];
			bd[k] = b;
		}
	}

	/**
	 * Get datasets from an image
	 * @param image
	 * @param keepBitWidth if true, then use signed primitives of same bit width for possibly unsigned data
	 * @return array of datasets (if image is rgb then a single RGBByteDataset or RGBDataset is returned
	 */
	public static Dataset[] makeDatasets(final BufferedImage image, boolean keepBitWidth) {
		// make raster from buffered image
		boolean isRGB = image.getColorModel().getColorSpace().getType() == ColorSpace.TYPE_RGB;
		final Raster ras = image.getData();
		final SampleModel sm = ras.getSampleModel();
		int dbType = reduceDataBufferType(sm);
		Class<? extends Dataset> clazz = getInterfaceFromDataBufferType(dbType, keepBitWidth || isRGB);
		int bands = ras.getNumBands();
		if (isRGB && bands == 3) {
			if (clazz.equals(ByteDataset.class)) {
				clazz = RGBByteDataset.class;
				bands = 1;
			} else if (clazz.equals(ShortDataset.class)) {
				clazz = RGBDataset.class;
				bands = 1;
			}
		}

		Dataset[] data = new Dataset[bands];

		createDatasets(ras, clazz, data);

		if (dbType == DataBuffer.TYPE_USHORT && !keepBitWidth) {
			for (int i = 0; i < bands; i++) {
				tagIntForShortDataset(data[i]);
			}
		}
		return data;
	}

	private static void tagIntForShortDataset(Dataset ret) {
		final Map<String,String> map = new HashMap<String, String>(1);
		map.put("unsigned.short.data", "true");
		IMetadata metadata = new Metadata();
		metadata.initialize(map);
		ret.setMetadata(metadata);
	}

	/**
	 * Get dataset interface from data buffer type
	 * @param dbtype
	 * @param keepBitWidth
	 * @return dataset interface
	 */
	private static Class<? extends Dataset> getInterfaceFromDataBufferType(final int dbtype, boolean keepBitWidth) {
		switch (dbtype) {
		case DataBuffer.TYPE_BYTE:
			return keepBitWidth ? ByteDataset.class : ShortDataset.class;
		case DataBuffer.TYPE_SHORT:
			return ShortDataset.class;
		case DataBuffer.TYPE_USHORT:
			return keepBitWidth ? ShortDataset.class : IntegerDataset.class;
		case DataBuffer.TYPE_INT:
			return IntegerDataset.class;
		case DataBuffer.TYPE_FLOAT:
			return FloatDataset.class;
		case DataBuffer.TYPE_DOUBLE:
			return DoubleDataset.class;
		}

		return null;
	}

	private static int reduceDataBufferType(final SampleModel sm) {
		int dbtype = sm.getDataType();
		final int bits = sm.getSampleSize(0);
		if (dbtype == DataBuffer.TYPE_INT) {
			if (bits <= 8) {
				dbtype = DataBuffer.TYPE_BYTE;
			} else if (bits <= 16) {
				dbtype = DataBuffer.TYPE_SHORT;
			}
		}
		if (dbtype == DataBuffer.TYPE_USHORT) {
			if (bits < 8) {
				dbtype = DataBuffer.TYPE_BYTE;
			} else if (bits < 16) {
				dbtype = DataBuffer.TYPE_SHORT;
			}
		}
		if (dbtype == DataBuffer.TYPE_SHORT) {
			if (bits <= 8) {
				dbtype = DataBuffer.TYPE_BYTE;
			}
		}
		return dbtype;
	}

	/**
	 * Get dataset interface from given sample model
	 * @param sm
	 * @param keepBitWidth
	 * @return dataset interface
	 */
	public static Class<? extends Dataset> getInterface(final SampleModel sm, boolean keepBitWidth) {
		return getInterfaceFromDataBufferType(reduceDataBufferType(sm), keepBitWidth);
	}

	/**
	 * Get image from a dataset
	 * @param data
	 * @param bits number of bits (<=16 for non-RGB datasets)
	 * @return buffered image
	 */
	public static BufferedImage makeBufferedImage(final Dataset data, final int bits) {
		final int[] shape = data.getShape();
		if (shape.length > 2) {
			throw new IllegalArgumentException("Rank of data must be less than or equal to two");
		}

		final int height = shape[0];
		final int width = shape.length == 1 ? 1 : shape[1]; // allow 1D datasets to be saved

		final int size = data.getSize();
		BufferedImage image = null;

		if (data instanceof RGBByteDataset) {
			RGBByteDataset rgbds = (RGBByteDataset) data;
			byte[] rgbdata = rgbds.getData();
			DataBufferByte dataBuffer = new DataBufferByte(rgbdata, rgbdata.length);
			WritableRaster raster = Raster.createInterleavedRaster(dataBuffer, width, height, 3*width, 3, new int[] {0, 1, 2}, null);
			
//			DirectColorModel colourModel = new DirectColorModel(24, 0xff0000, 0x00ff00, 0x0000ff);
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
			ComponentColorModel colorModel = new ComponentColorModel(cs, new int[] {8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
			image = new BufferedImage(colorModel, raster, false, null);
		} else if (data instanceof RGBDataset) {
			RGBDataset rgbds = (RGBDataset) data;

			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			short maxv = rgbds.max().shortValue();
			final IndexIterator iter = rgbds.getIterator(true);
			final int[] pos = iter.getPos();
			final short[] rgbdata = rgbds.getData();
			if (maxv < 256) { // 888
				while (iter.hasNext()) {
					final int n = iter.index;
					final int rgb = ((rgbdata[n] & 0xff) << 16) | ((rgbdata[n + 1] & 0xff) << 8) | (rgbdata[n + 2] & 0xff);
					image.setRGB(pos[1], pos[0], rgb);
				}
			} else {
				int shift = 0;
				while (maxv >= 256) {
					shift++;
					maxv >>= 2;
				}

				while (iter.hasNext()) {
					final int n = iter.index;
					final int rgb = (((rgbdata[n] >> shift) & 0xff) << 16) | (((rgbdata[n + 1] >> shift) & 0xff) << 8) | ((rgbdata[n + 2] >> shift) & 0xff);
					image.setRGB(pos[1], pos[0], rgb);
				}
			}
		} else {
			DataBuffer buffer = null;
			SampleModel sampleModel = null;
			// reconcile data with output format

			// populate data buffer using sample model
			IntegerDataset tmp = DatasetUtils.cast(IntegerDataset.class, data);

			if (bits <= 8) {
				buffer = new DataBufferByte(size);
				sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_BYTE, width, height, 1, width,
						new int[] { 0 });
				image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

				sampleModel.setPixels(0, 0, width, height, tmp.getData(), buffer);
			} else if (bits <= 16) {
				buffer = new DataBufferUShort(size);
				sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_USHORT, width, height, 1,
						width, new int[] { 0 });
				image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);

				sampleModel.setPixels(0, 0, width, height, tmp.getData(), buffer);
			} else {
				throw new IllegalArgumentException("Number of bits must be less than or equal to 16");
			}

			WritableRaster wRas = Raster.createWritableRaster(sampleModel, buffer, null);
			image.setData(wRas);
		}

		return image;
	}

	/**
	 * Get image from a dataset
	 * @param data
	 * @param bits number of bits (> 32 for float image)
	 * @return tiled image
	 */
	static public TiledImage makeTiledImage(final Dataset data, final int bits) {
		final int[] shape = data.getShape();
		int height = shape[0];
		int width = shape.length == 1 ? 1 : shape[1]; // allow 1D datasets to be saved
		final int size = data.getSize();

		SampleModel sampleModel;
		DataBuffer buffer;

		if (data instanceof RGBByteDataset) {
			RGBByteDataset rgbds = (RGBByteDataset) data;
			byte[] rgbdata = rgbds.getData();
			buffer = new DataBufferByte(rgbdata, rgbdata.length);
			sampleModel = new PixelInterleavedSampleModel(buffer.getDataType(), width, height, 3, 3*width, new int[] {0, 1, 2});
		} else if (data instanceof RGBDataset) {
			RGBDataset rgbds = (RGBDataset) data;

			buffer = new DataBufferInt(size);
			sampleModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, width, height, new int[] { 0xff0000, 0x00ff00, 0x0000ff} );

			short maxv = rgbds.max().shortValue();
			final IndexIterator iter = rgbds.getIterator(true);
			final int[] pos = iter.getPos();
			final short[] rgbdata = rgbds.getData();

			if (maxv < 256) { // 888
				while (iter.hasNext()) {
					final int n = iter.index;
					final int rgb = ((rgbdata[n] & 0xff) << 16) | ((rgbdata[n + 1] & 0xff) << 8) | (rgbdata[n + 2] & 0xff);
					sampleModel.setSample(pos[1], pos[0], 0, rgb, buffer);
				}
			} else {
				int shift = 0;
				while (maxv >= 256) {
					shift++;
					maxv >>= 2;
				}

				while (iter.hasNext()) {
					final int n = iter.index;
					final int rgb = (((rgbdata[n] >> shift) & 0xff) << 16) | (((rgbdata[n + 1] >> shift) & 0xff) << 8) | ((rgbdata[n + 2] >> shift) & 0xff);
					sampleModel.setSample(pos[1], pos[0], 0, rgb, buffer);
				}
			}

		} else {
			// reconcile data with output format

			// populate data buffer using sample model
			if (bits <= 32) {
				buffer = new DataBufferInt(size);
				sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_INT,
								width, height, 1);

				IntegerDataset tmp = data.cast(IntegerDataset.class);
				sampleModel.setPixels(0, 0, width, height, tmp.getData(), buffer);
			} else { // Only TIFF supports floats (so far)
				buffer = new DataBufferFloat(size);
				sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_FLOAT,
								width, height, 1);

				FloatDataset ftmp = data.cast(FloatDataset.class);
				sampleModel.setPixels(0, 0, width, height, ftmp.getData(), buffer);
			}
		}

		WritableRaster wRas = Raster.createWritableRaster(sampleModel, buffer, null);
		ColorModel cm = PlanarImage.createColorModel(sampleModel);
		TiledImage timage = new TiledImage(0, 0, width, height, 0, 0, sampleModel, cm);
		timage.setData(wRas);
		return timage;
	}
	
}
