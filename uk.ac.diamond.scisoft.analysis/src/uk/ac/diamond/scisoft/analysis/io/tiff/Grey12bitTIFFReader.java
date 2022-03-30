/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.tiff;

import it.tidalwave.imageio.io.RAWImageInputStream;
import it.tidalwave.imageio.raw.Packed12RasterReader;
import it.tidalwave.imageio.raw.RAWImageReaderSupport;
import it.tidalwave.imageio.raw.RasterReader;
import it.tidalwave.imageio.tiff.IFD;
import it.tidalwave.imageio.tiff.TIFFImageReaderSupport;
import it.tidalwave.imageio.tiff.TIFFMetadataSupport;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.spi.ImageReaderSpi;

public class Grey12bitTIFFReader extends TIFFImageReaderSupport {
	class Grey12RasterReader extends Packed12RasterReader {

		@Override
		protected void loadUncompressedRaster(final RAWImageInputStream iis, final WritableRaster raster,
				final RAWImageReaderSupport ir) throws IOException {

			final DataBufferUShort dataBuffer = (DataBufferUShort) raster.getDataBuffer();
			final short[] data = dataBuffer.getData();
			final int width = raster.getWidth();
			final int height = raster.getHeight();
			final int pixelStride = 1;
			final int scanStride = width * pixelStride;
			setBitsPerSample(12);
			selectBitReader(iis, raster, 12);
			//
			// We can rely on the fact that the array has been zeroed by the JVM, so we just set nonzero samples.
			//
			for (int y = 0; y < height; y++) {
				final int row = getRow(y, height);
				int i = row * scanStride;

				for (int x = 0; x < width; x++) {
					int sample = (int) iis.readBits(12);

					data[i] = (short) sample;
					endOfColumn(x, iis);
					i += pixelStride;
				}

				ir.processImageProgress((100.0f * y) / height);
				endOfRow(y, iis);
			}
		}

		@Override
		protected int[] getBandOffsets() {
			return new int[] {R_OFFSET};
		}
	}

	public Grey12bitTIFFReader(final ImageReaderSpi originatingProvider) {
		super(originatingProvider, IFD.class, TIFFMetadataSupport.class);
	}

	@Override
	protected WritableRaster loadRAWRaster() throws IOException {
		final IFD rasterIFD = ((TIFFMetadataSupport) metadata).getPrimaryIFD();
		final RasterReader rasterReader = new Grey12RasterReader();
		final int width = rasterIFD.getImageWidth();
		final int height = rasterIFD.getImageLength();
		final int bitsPerSample = rasterIFD.getBitsPerSample()[0];
		initializeRasterReader(width, height, bitsPerSample, rasterReader);

		if (!rasterIFD.isTileWidthAvailable()) {
			iis.seek(rasterIFD.getStripOffsets()); // FIXME: move, it's responsibility of the reader to seek
		}

		final WritableRaster raster = rasterReader.loadRaster(iis, this);
		return raster;
	}

	@Override
	protected BufferedImage loadImage(int imageIndex) throws IOException {
		checkImageIndex(imageIndex);
		ensureMetadataIsLoaded(imageIndex);

		final WritableRaster raster = loadRAWRaster();
		final int width = raster.getWidth();
		final int height = raster.getHeight();

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
		bufferedImage.setData(raster);
		return bufferedImage;
	}

	@Override
	protected void initializeRasterReader(int width, int height, int bitsPerSample, RasterReader rasterReader) {
		rasterReader.setWidth(width);
		rasterReader.setHeight(height);
		rasterReader.setBitsPerSample(bitsPerSample);
		IFD rasterIFD = ((TIFFMetadataSupport) metadata).getRasterIFD();
		if (rasterIFD == null) {
			rasterIFD = ((TIFFMetadataSupport) metadata).getPrimaryIFD();
		}

		if (rasterIFD != null) {
			if (rasterIFD.isCompressionAvailable()) {
				rasterReader.setCompression(rasterIFD.getCompression().intValue());
			}

			if (rasterIFD.isStripByteCountsAvailable()) {
				rasterReader.setStripByteCount(rasterIFD.getStripByteCounts());
			}

			if (rasterIFD.isTileWidthAvailable()) {
				int imageWidth = rasterIFD.getImageWidth();
				int imageLength = rasterIFD.getImageLength();
				int tileWidth = rasterIFD.getTileWidth();
				int tileLength = rasterIFD.getTileLength();
				rasterReader.setTileWidth(tileWidth);
				rasterReader.setTileHeight(tileLength);
				rasterReader.setTilesAcross((imageWidth + tileWidth - 1) / tileWidth);
				rasterReader.setTilesDown((imageLength + tileLength - 1) / tileLength);
				rasterReader.setTileOffsets(rasterIFD.getTileOffsets());
				// int[] tileByteCounts = imageIFD.getTileByteCounts();
			}
		}
	}
}
