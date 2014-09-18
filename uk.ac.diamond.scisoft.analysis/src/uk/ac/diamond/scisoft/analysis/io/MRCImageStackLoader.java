/*-
 * Copyright 2014 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LazyDataset;
import uk.ac.diamond.scisoft.analysis.metadata.IMetaLoader;
import uk.ac.diamond.scisoft.analysis.metadata.IMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.Metadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * Loader for MRC electron microscope image stacks
 */
public class MRCImageStackLoader extends AbstractFileLoader implements IMetaLoader, Serializable {
	protected static final Logger logger = LoggerFactory.getLogger(MRCImageStackLoader.class);

	private String filename;

	Metadata metadata = null;

	public MRCImageStackLoader(String filename) {
		this.filename = filename;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		File f = null;
		BufferedInputStream bi = null;

		f = new File(filename);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + filename);
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

		int mode = getInteger(BinaryKey.MODE);
		if (mode != 1) { // TODO support other modes
			throw new ScanFileHolderException("Only mode 1 (signed 16-bit integers) is currently supported");
		}
		DataHolder result = new DataHolder();
		result.addDataset(STACK_NAME, createDataset(pos, mode, getInteger(BinaryKey.WIDTH),
				getInteger(BinaryKey.HEIGHT), getInteger(BinaryKey.DEPTH)));

		if (loadMetadata) {
			result.setMetadata(metadata);
			result.getLazyDataset(0).setMetadata(metadata);
		}
		return result;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		if (metadata != null)
			return;

		File f = null;
		BufferedInputStream bi = null;

		f = new File(filename);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + filename);
		}

		try {
			bi = new BufferedInputStream(new FileInputStream(f));
			
			readMetadata(bi);
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
	}

	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	private ILazyDataset createDataset(final long pos, final int mode, final int width, final int height, final int depth) {
		final int[] trueShape = new int[] {depth, height, width};
		final int dtype = modeToDtype.get(mode);
		final int dsize = modeToDsize.get(mode);

		ILazyLoader l = new ILazyLoader() {
			
			@Override
			public boolean isFileReadable() {
				return new File(filename).canRead();
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

						d = loadData(mon, filename, pos, dsize, dtype, trueShape, tstart, tsize, tstep);
						d.setShape(newShape); // squeeze shape back
					} else {
						d = loadData(mon, filename, pos, dsize, dtype, trueShape, lstart, newShape, lstep);
					}
				} catch (Exception e) {
					throw new ScanFileHolderException("Problem with HDF library", e);
				}
				return d;
			}

		};

		return new LazyDataset(STACK_NAME, dtype, 1, trueShape.clone(), l);
	}

	private static Dataset loadData(IMonitor mon, String filename, long pos, int dsize, int dtype, int[] shape, int[] start, int[] count, int[] step) throws ScanFileHolderException {
		File f = null;
		BufferedInputStream bi = null;

		f = new File(filename);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + filename);
		}

		Dataset d = DatasetFactory.zeros(count, dtype);

		IntegerDataset image = (IntegerDataset) DatasetFactory.zeros(new int[] {shape[1], shape[2]}, Dataset.INT32);
		try {
			bi = new BufferedInputStream(new FileInputStream(f));

			int[] imageStart = new int[] {start[1], start[2]};
			int[] imageStop  = new int[] {start[1] + count[1] * step[1], start[2] + count[2] * step[2]};
			int[] imageStep  = new int[] {step[1], step[2]};
			int[] dataStart = new int[d.getRank()];
			int[] dataStop = count.clone();

			long imageSize = dsize * shape[1] * shape[2];
			pos += dataStart[0] * imageSize;
			bi.skip(pos);
			pos = (step[0] - 1) * imageSize;
			do { // TODO maybe read smaller chunk of image...
				Utils.readLeShort(bi, image, 0, true);
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
	
	private int readMetadata(BufferedInputStream bi) throws IOException, ScanFileHolderException {
		int pos =readHeader(bi);
		metadata = new Metadata(headers);
		return pos;
	}

	private static final int HEADER_SIZE = 1024;
	private static final int LABEL_LENGTH = 80;

	enum KeyType {
		UNUSED(0), // for skipping
		CHARS(1),
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
		UNSUED1(30, KeyType.UNUSED),  // not used
		INT(KeyType.SHORT), // 
		REAL(KeyType.SHORT), // 
		UNUSED2(20, KeyType.UNUSED),  // not used
		IMODSTAMP,       // 1146047817 indicates that file was created by IMOD or 
		                 // other software that uses bit flags in the following field
		IMODFLAGS,       // 1 = bytes are signed, 2 = pixel spacing set in extended header,
		                 // 4 = origin is stored with sign inverted from definition below
		DATATYPE(6, KeyType.SHORT), // six data type indicators
		TILTANGLES(6, KeyType.FLOAT), // six tilt angles
		MRCHEADER(24, KeyType.UNUSED), // 24-bytes of MRC header can be old or new
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
	}

	protected Map<String, Serializable> headers = new HashMap<>();

	private int getInteger(BinaryKey key) throws ScanFileHolderException {
		String k = key.toString();
		Serializable s = headers.get(k);
		if (s == null)
			throw new ScanFileHolderException("Could not find in header the key: " + k);
		return (Integer) s;
	}

	private int readHeader(BufferedInputStream bis) throws IOException, ScanFileHolderException {
		headers.clear();

		int pos = 0;
		byte[] header = new byte[HEADER_SIZE];
		if (bis.read(header) != HEADER_SIZE) {
			throw new ScanFileHolderException("Could not read header");
		}

		// Assume little endian byte-order
		for (BinaryKey k : BinaryKey.values()) {
			Serializable s = null;
			switch (k.type) {
			case UNUSED:
				break;
			case CHARS:
				s = Utils.getString(header, pos, LABEL_LENGTH).trim();
				break;
			case SHORT:
				s = Utils.leInt(header[pos], header[pos + 1]);
				break;
			case INT:
				s= Utils.leInt(header[pos], header[pos + 1], header[pos + 2], header[pos + 3]);
				break;
			case FLOAT:
				break;
			}
			if (s != null)
				headers.put(k.toString(), s);
			pos += k.next;
		}

		assert pos == 1024;

		// advance position but TODO currently does not read in extra header info
		pos += getInteger(BinaryKey.NEXT);

		return pos;
	}
}
