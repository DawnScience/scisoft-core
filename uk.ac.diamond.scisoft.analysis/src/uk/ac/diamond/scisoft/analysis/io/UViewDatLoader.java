/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loader to open the Elmitec UView .dat files (or sequence of files). The UView format is historically called
 * UKSOFT2001 (it was used originally for STM pictures)
 */
public class UViewDatLoader extends AbstractFileLoader {

	private static final Logger logger = LoggerFactory.getLogger(UViewDatLoader.class);

	private Map<String, Serializable> headers = new HashMap<>();

	private static final int HEADER_SIZE = 104; // headerSize is always 104 bytes
	private static final int IMAGE_HEADER_SIZE = 288;
	private static final int RECIPE_BLOCK_SIZE = 128;

	public UViewDatLoader() {
	}

	public UViewDatLoader(String fileName) {
		this();
		setFile(fileName);
	}

	@Override
	public IDataHolder loadFile() throws ScanFileHolderException {
		return loadFile((IMonitor) null);
	}

	private static final int IMAGE_LIMIT = 16*1024*1024; // 4k*4k pixels at most

	@Override
	public IDataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {
		// first instantiate the return object.
		final IDataHolder result = new DataHolder();

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(fileName));
			//read metadata/headers and return position of image data
			int pos = readMetadata(in);
			int width = (int) headers.get(BinaryKey.WIDTH.toString());
			int height = (int) headers.get(BinaryKey.HEIGHT.toString());
			int size = width * height;
			if (size > IMAGE_LIMIT) {
				logger.error("UViewDatLoader: {} has too many pixels {}x{} = {} > {}", fileName, width, height, size, IMAGE_LIMIT);
				throw new ScanFileHolderException("UViewDatLoader: " + fileName + " has too many pixels " + width + "x" + height + " > " + IMAGE_LIMIT);
			}
			IntegerDataset imagedata = DatasetFactory.zeros(IntegerDataset.class, height, width);

			Utils.readLeShort(in, imagedata, pos, false);
			result.addDataset(DEF_IMAGE_NAME, imagedata);
			if (loadMetadata) {
				result.setMetadata(metadata);
				result.getDataset(0).setMetadata(metadata);
			}
			return result;
		} catch (IOException e) {
			logger.error("UViewDatLoader.loadFile exception loading  " + fileName, e);
			throw new ScanFileHolderException("UViewDatLoader.loadFile exception loading  " + fileName, e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				logger.error("Cannot close stream from file  " + fileName, e);
				throw new ScanFileHolderException("Cannot close stream from file  " + fileName, e);
			}
		}
	}

	private int readMetadata(BufferedInputStream bi) throws IOException {
		int pos = readHeader(bi);
		metadata = new Metadata();
		metadata.initialize(headers);
		return pos;
	}

	private int readHeader(BufferedInputStream bis) throws IOException {
		headers.clear();
		int[] sizeAndStart = new int[3];
		boolean hasRecipe = false, hasAttachedMarkup = false;
		int line = 0, ukVersion = 0, attachedRecipeSize = 0, filePointer = 0, attachedMarkupSize = 0,
				imageWidth = 0, imageHeight = 0, leemDataVersion = 0;
		int totalHeaderSize = 0;

		int idx = checkHeaderForMagicString(bis);
		while ((line = Utils.readLeShort(bis)) != -1) {
			System.err.println(line);
			if (idx == 11) { // UK version
				ukVersion = line;
			}
			if (idx == 20) { // image width
				imageWidth = line;
			}
			if (idx == 21) { // image height
				imageHeight = line;
			}
			if (idx == 23) {
				if (ukVersion >= 7) { // attached RecipeSize
					attachedRecipeSize = line;
					hasRecipe = attachedRecipeSize > 0;
				} else {
					attachedRecipeSize = 0;
					hasRecipe = false;
				}
				// define file pointer length
				if (hasRecipe) {
					filePointer = HEADER_SIZE + RECIPE_BLOCK_SIZE;
				} else {
					filePointer = HEADER_SIZE;
				}
			}
			if (idx > 23 && idx == ((filePointer + 22) / 2)) {
				attachedMarkupSize = line;
				hasAttachedMarkup = attachedMarkupSize != 0;
				if (hasAttachedMarkup) {
					attachedMarkupSize = 128 * ((attachedMarkupSize / 128) + 1);
				} else {
					attachedMarkupSize = 0;
				}
			}

			if (idx > 25 && idx == ((filePointer + 26) / 2)) {
				leemDataVersion = line;
				totalHeaderSize = HEADER_SIZE + attachedRecipeSize + IMAGE_HEADER_SIZE + attachedMarkupSize
						+ leemDataVersion;
				sizeAndStart[2] = totalHeaderSize;
				break;
			}
			idx++;
		}
		// Fill headers
		for (BinaryKey k : BinaryKey.values()) {
			Serializable s = null;
			switch (k) {
			case WIDTH:
				s = imageWidth;
				break;
			case HEIGHT:
				s = imageHeight;
				break;
			case VERSION:
				s = ukVersion;
				break;
			case HASRECIPE:
				s = hasRecipe;
				break;
			case HASMARKUP:
				s = hasAttachedMarkup;
				break;
			case RECIPESIZE:
				s = attachedRecipeSize;
				break;
			case MARKUPSIZE:
				s = attachedMarkupSize;
				break;
			case LEEMDATAVERSION:
				s = leemDataVersion;
				break;
			case TOTALHEADERSIZE:
				s = totalHeaderSize;
				break;
			}
			if (s != null)
				headers.put(k.toString(), s);
		}
		return totalHeaderSize - (idx * 2);
	}

	private static final String MAGIC_STRING = "UKSOFT";
	private static final byte[] MAGIC_VALUES = MAGIC_STRING.getBytes(StandardCharsets.US_ASCII);

	private int checkHeaderForMagicString(BufferedInputStream is) throws IOException {
		assert MAGIC_VALUES.length % 2 == 0; // length must be even as shorts are read after
		byte[] magic = new byte[MAGIC_VALUES.length];
		if (is.read(magic) != magic.length) {
			throw new IOException("Could not read magic string at start of file");
		}

		if (!Arrays.equals(MAGIC_VALUES, magic)) {
			throw new IOException("Magic string at start of file |" + StandardCharsets.US_ASCII.decode(ByteBuffer.wrap(magic)) + "| does not match " + MAGIC_STRING);
		}
		return magic.length / 2; // number of shorts read
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
		headers.clear();
	}

	enum BinaryKey {
		WIDTH,           // 
		HEIGHT,          // 
		VERSION,         //
		HASRECIPE,       //
		HASMARKUP,       //
		RECIPESIZE,      // 
		MARKUPSIZE,      // 
		LEEMDATAVERSION, // 
		TOTALHEADERSIZE, // 
		;
	}
}
