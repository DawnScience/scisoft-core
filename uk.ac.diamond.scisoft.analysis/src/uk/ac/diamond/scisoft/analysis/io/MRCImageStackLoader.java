/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.io.ILazyLoader;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loader for MRC electron microscope image stacks
 */
public class MRCImageStackLoader extends AbstractFileLoader implements Serializable {
	protected static final Logger logger = LoggerFactory.getLogger(MRCImageStackLoader.class);
	private boolean isLittleEndian;

	public MRCImageStackLoader(String filename) {
		this.fileName = filename;
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
		headers.clear();
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		File f = null;
		BufferedInputStream bi = null;

		f = new File(fileName);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + fileName);
		}

		int pos = 0;

		try {
			bi = new BufferedInputStream(new FileInputStream(f));

			pos = readMetadata(bi);
			if (mon != null) {
				mon.worked(1);
			}
		} catch (Exception e) {
			logger.error("Problem with file", e);
			throw new ScanFileHolderException("Problem with file", e);
		} finally {
			if (bi != null) {
				try {
					bi.close();
				} catch (IOException e1) {
					logger.error("Cannot close stream", e1);
					throw new ScanFileHolderException("Cannot close stream");
				}
			}
		}

		DataHolder result = new DataHolder();
		result.addDataset(STACK_NAME, createDataset(pos, getInteger(BinaryKey.MODE), getInteger(BinaryKey.WIDTH),
				getInteger(BinaryKey.HEIGHT), getInteger(BinaryKey.DEPTH)));

		if (loadMetadata) {
			result.setMetadata(metadata);
			result.getLazyDataset(0).setMetadata(metadata);
		}
		return result;
	}

	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	private ILazyDataset createDataset(final long pos, final int mode, final int width, final int height, final int depth) throws ScanFileHolderException {
		final int[] trueShape = new int[] {depth, height, width};
		final int type = modeToDtype.get(mode);
		if (type != Dataset.INT16 && type != Dataset.FLOAT32) { // TODO support other modes
			throw new ScanFileHolderException("Only 16-bit integers and 32-bit floats are currently supported");
		}

		final int dsize = modeToDsize.get(mode);
		final boolean signExtend = !modeToUnsigned.get(mode);
		final int dtype = signExtend ? type : Dataset.INT32;

		ILazyLoader l = new ILazyLoader() {
			
			@Override
			public boolean isFileReadable() {
				return new File(fileName).canRead();
			}
			
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

						d = loadData(mon, fileName, pos, isLittleEndian, dsize, dtype, signExtend, trueShape, tstart, tsize, tstep);
						d.setShape(newShape); // squeeze shape back
					} else {
						d = loadData(mon, fileName, pos, isLittleEndian, dsize, dtype, signExtend, trueShape, lstart, newShape, lstep);
					}
				} catch (ScanFileHolderException e) {
					throw new IOException("Problem with loading data", e);
				}
				return d;
			}

		};

		return new LazyDataset(STACK_NAME, dtype, 1, trueShape.clone(), l);
	}

	private static Dataset loadData(IMonitor mon, String filename, long pos, boolean isLE, int dsize, int dtype, boolean signExtend, int[] shape, int[] start, int[] count, int[] step) throws ScanFileHolderException {
		File f = null;
		BufferedInputStream bi = null;

		f = new File(filename);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + filename);
		}

		Dataset d = DatasetFactory.zeros(count, dtype);

		int idtype = dtype == Dataset.FLOAT32 ? Dataset.FLOAT32 : Dataset.INT32;
		Dataset image = DatasetFactory.zeros(new int[] {shape[1], shape[2]}, idtype);
		try {
			bi = new BufferedInputStream(new FileInputStream(f));

			// flip each image row-wise as origin is bottom-left
			int[] imageStart = new int[] {start[1] + (count[1] - 1)* step[1], start[2]};
			int[] imageStop  = new int[] {start[1] - step[1] - shape[1], start[2] + count[2] * step[2]};
			int[] imageStep  = new int[] {-step[1], step[2]};
//			int[] imageStart = new int[] {start[1], start[2]};
//			int[] imageStop  = new int[] {start[1] + count[1] * step[1], start[2] + count[2] * step[2]};
//			int[] imageStep  = new int[] {step[1], step[2]};
			int[] dataStart = new int[d.getRank()];
			int[] dataStop = count.clone();

			long imageSize = dsize * shape[1] * shape[2];
			pos += dataStart[0] * imageSize;
			bi.skip(pos);
			pos = (step[0] - 1) * imageSize;
			do { // TODO maybe read smaller chunk of image...
				if (dtype == Dataset.INT16) {
					if (isLE) {
						Utils.readLeShort(bi, (IntegerDataset) image, 0, signExtend);
					} else {
						Utils.readBeShort(bi, (IntegerDataset) image, 0, signExtend);
					}
				} else if (dtype == Dataset.FLOAT32) {
					if (isLE) {
						Utils.readLeFloat(bi, (FloatDataset) image, 0);
					} else {
						Utils.readBeFloat(bi, (FloatDataset) image, 0);
					}
				} else {
					
				}
				dataStop[0] = dataStart[0] + 1;
				d.setSlice(image.getSliceView(imageStart, imageStop, imageStep), dataStart, dataStop, null);
				if (mon != null) {
					mon.worked(1);
				}
				if (pos > 0)
					bi.skip(pos);
				dataStart[0]++;
			} while (dataStart[0] < count[0]);
		} catch (Exception e) {
			logger.error("Problem with file", e);
			throw new ScanFileHolderException("Problem with file", e);
		} finally {
			if (bi != null) {
				try {
					bi.close();
				} catch (IOException e1) {
					logger.error("Cannot close stream", e1);
					throw new ScanFileHolderException("Cannot close stream");
				}
			}
		}

		return d;
	}
	
	private int readMetadata(BufferedInputStream bis) throws IOException, ScanFileHolderException {
		byte[] header = new byte[HEADER_SIZE];
		if (bis.read(header) != HEADER_SIZE) {
			throw new ScanFileHolderException("Could not read header");
		}
		isLittleEndian = true;
		int pos = readHeader(header);
		if (pos < 0) {
			isLittleEndian = false;
			pos = readHeader(header);
			if (pos < 0) {
				throw new ScanFileHolderException("Could not parse header by either endianness");
			}
		}
		metadata = new Metadata();
		metadata.initialize(headers);
		return pos;
	}

	private static final int HEADER_SIZE = 1024;

	enum KeyType {
		UNUSED(0), // for skipping
		CHARS(1),
		BYTE(1),
		SHORT(2),
		INT(4),
		FLOAT(4),
		;

		int size; // length of type
		KeyType(int size) {
			this.size = size;
		}
	}

	enum BinaryKey {
		WIDTH,           // columns in image stack
		HEIGHT,          // rows in image stack
		DEPTH,           // sections in image stack
		MODE,            // 0 = byte (signed or unsigned according to IMODFLAGS) but unsigned if written by IMOD before 4.2.23
		                 // 1 = signed shorts
		                 // 2 = 4-byte floats
                         // 3 = 2 * shorts for complex data
                         // 4 = 2 * floats for complex data
		                 // 6 = unsigned 16-bit integers (non-standard)
		                 // 16 = 3 * unsigned bytes for RGB data (non-standard)
		START(3),        // start point - three values
		GRID(3),         // grid size - three values
		CELL(3, KeyType.FLOAT), // cell dimensions - three values
		ANGLES(3, KeyType.FLOAT), // cell angles - three values
		MAPS(3),         // mapping - three values
		SCALE(3, KeyType.FLOAT), // scaling - three values
		SPACEGROUP,      // space group
		NEXT,            // extended header size in bytes
		ID(KeyType.SHORT), // ID is now 0 as of IMOD 4.2.23
		UNUSED1(30, KeyType.UNUSED),  // not used
		INT(KeyType.SHORT), // 
		REAL(KeyType.SHORT), // 
		UNUSED2(20, KeyType.UNUSED),  // not used
		IMODSTAMP,       // 1146047817 indicates that file was created by IMOD or 
		                 // other software that uses bit flags in the following field
		IMODFLAGS,       // 1 = bytes are signed, 2 = pixel spacing set in extended header,
		                 // 4 = origin is stored with sign inverted from definition below
		DATATYPE(6, KeyType.SHORT), // six data type indicators
		TILTANGLES(6, KeyType.FLOAT), // six tilt angles
		// 24-bytes of MRC header assumed to be new type
		ORG(3, KeyType.FLOAT), // x,y,z origin
		CMAP(4, KeyType.CHARS), // contains "MAP "
		STAMP(4, KeyType.BYTE), // First two bytes have 17 and 17 for big-endian or 68 and 65 (DA) for little-endian
		RMS(1, KeyType.FLOAT), // RMS deviation of densities from mean density
		LABELS,          // number of labels
		LABELTEXT(800, KeyType.CHARS), // 10 label strings of 80 characters
		;

		KeyType type;    // key type
		int next;        // next key offset in bytes
		BinaryKey() {    // default key is an integer
			this(1);
		}

		BinaryKey(KeyType type) {
			this(1, type);
		}

		BinaryKey(int number) {
			this(number, KeyType.INT);
		}

		BinaryKey(int number, KeyType type) {
			this.type = type;
			this.next = type == KeyType.UNUSED ? number : number*type.size;
		}
	}

	private static final Map<Integer, Integer> modeToDtype = new HashMap<>(); // destination dataset type
	private static final Map<Integer, Integer> modeToDsize = new HashMap<>(); // source data size
	private static final Map<Integer, Boolean> modeToUnsigned= new HashMap<>(); // source data unsignedness
	static {
		modeToDtype.put(0, Dataset.INT8);
		modeToDtype.put(1, Dataset.INT16);
		modeToDtype.put(2, Dataset.FLOAT32);
		modeToDtype.put(3, Dataset.ARRAYINT16); // complex short
		modeToDtype.put(4, Dataset.COMPLEX64);
		modeToDtype.put(6, Dataset.INT16); // unsigned shorts
		modeToDtype.put(16, Dataset.RGB); // three unsigned bytes

		modeToDsize.put(0, 1);
		modeToDsize.put(1, 2);
		modeToDsize.put(2, 4);
		modeToDsize.put(3, 2);
		modeToDsize.put(4, 8);
		modeToDsize.put(6, 2);
		modeToDsize.put(16, 3);

		modeToUnsigned.put(0, false);
		modeToUnsigned.put(1, false);
		modeToUnsigned.put(2, false);
		modeToUnsigned.put(3, false);
		modeToUnsigned.put(4, false);
		modeToUnsigned.put(6, true);
		modeToUnsigned.put(16, true);
	}

	protected Map<String, Serializable> headers = new HashMap<>();

	private int getInteger(BinaryKey key) throws ScanFileHolderException {
		String k = key.toString();
		Serializable s = headers.get(k);
		if (s == null)
			throw new ScanFileHolderException("Could not find in header the key: " + k);
		return (Integer) s;
	}

	private int readHeader(byte[] header) throws IOException, ScanFileHolderException {
		headers.clear();

		int pos = 0;

		// Assume little endian byte-order
		for (BinaryKey k : BinaryKey.values()) {
			Serializable s = null;
			switch (k.type) {
			case UNUSED:
				break;
			case CHARS:
				s = Utils.getString(header, pos, k.next).trim();
				break;
			case BYTE:
				s = Arrays.copyOfRange(header, pos, pos + k.next);
				break;
			case SHORT:
				s = isLittleEndian ? Utils.leInt(header[pos], header[pos + 1]) : Utils.beInt(header[pos], header[pos + 1]);
				break;
			case INT:
				s = isLittleEndian ? Utils.leInt(header[pos], header[pos + 1], header[pos + 2], header[pos + 3]) :
					Utils.beInt(header[pos], header[pos + 1], header[pos + 2], header[pos + 3]);
				break;
			case FLOAT:
				break;
			}
			if (s != null)
				headers.put(k.toString(), s);
			pos += k.next;
		}

		assert pos == 1024;

		if (getInteger(BinaryKey.WIDTH) < 0 || getInteger(BinaryKey.HEIGHT) < 0) {
			// signal that the byte order may be wrong as width and/or height are negative
			return -1;
		}
		pos += getInteger(BinaryKey.NEXT);

		return pos;
	}
}
