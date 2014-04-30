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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * Class to read Rayonix's MAR345 image format
 * 
 */
public class MAR345Loader extends AbstractFileLoader implements IMetaLoader, Serializable {
	transient protected static final Logger logger = LoggerFactory.getLogger(MAR345Loader.class);

	private String fileName;
	protected Map<String, Serializable> headers = new HashMap<>();
	private boolean littleEndian;
	private int side;
	private DiffractionMetadata diffMetadata = null;

	public MAR345Loader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		File f = null;
		BufferedInputStream bi = null;
		int[] image = null;

		f = new File(fileName);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + fileName);
		}

		try {
			bi = new BufferedInputStream(new FileInputStream(f));

			int[] highs = readMetadata(bi);
			if (mon != null) {
				mon.worked(1);
			}

			image = readPackedImage(bi);
			if (mon != null) {
				mon.worked(10);
			}

			if (highs != null) {// apply high values
				for (int i = 0; i < highs.length; i += 2) {
					image[highs[i]] = highs[i+1];
				}
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
		result.addDataset(DEF_IMAGE_NAME, new IntegerDataset(image, side, side));
		if (loadMetadata) {
			result.setMetadata(diffMetadata);
			result.getDataset(0).setMetadata(diffMetadata);
		}
		return result;
	}

	@Override
	public void loadMetaData(IMonitor mon) throws Exception {
		if (diffMetadata != null)
			return;

		File f = null;
		BufferedInputStream bi = null;

		f = new File(fileName);
		if (!f.exists()) {
			throw new ScanFileHolderException("Cannot find " + fileName);
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
	public IMetaData getMetaData() {
		return diffMetadata;
	}

	private int[] readMetadata(BufferedInputStream bi) throws IOException, ScanFileHolderException {
		if (!loadMetadata) {
			bi.skip(HEADER_SIZE);
			return null;
		}

		readHeader(bi);

		Serializable o;

		o = headers.get(BinaryKey.B_FORMAT.toString());
		int format = -1;
		if (o instanceof Integer) {
			 format = (Integer) o;
		}
		o = headers.get(TextKey.FORMAT.toString());
		if (o instanceof List<?>) {
			String t = (String) ((List<?>) o).get(1);
			if (format == 1 && !t.startsWith("PCK")) {
				logger.warn("Binary header does not match text header for FORMAT: {} cf {}", format, t);
			}
			if (format == 2 && !t.equals("SPIRAL")) {
				logger.warn("Binary header does not match text header for FORMAT: {} cf {}", format, t);
			}
		}
		if (format != 1) {
			logger.error("Spiral and uncompressed images are not supported");
			throw new ScanFileHolderException("Spiral and uncompressed images are not supported");
		}

		int high = -1;
		o = headers.get(BinaryKey.B_HIGH.toString());
		if (o instanceof Integer) {
			high = (Integer) o;
		}

		int[] highs = high > 0 ? readHighValues(high, bi) : null;

		DetectorProperties detprop = new DetectorProperties(getKeyAsDouble(TextKey.DISTANCE),
				0, 0,
				getKeyAsInt(TextKey.FORMAT), getKeyAsInt(TextKey.FORMAT),
				getKeyAsDouble(TextKey.PIXEL, 1), getKeyAsDouble(TextKey.PIXEL, 0));
		detprop.setBeamCentreCoords(new double[] {getKeyAsDouble(TextKey.CENTER, 0),
				getKeyAsDouble(TextKey.CENTER, 1)});

		DiffractionCrystalEnvironment env = new DiffractionCrystalEnvironment(getKeyAsDouble(TextKey.WAVELENGTH),
				getKeyAsDouble(TextKey.PHI, 0), getKeyAsDouble(TextKey.PHI, 1), getKeyAsDouble(TextKey.TIME),
				getKeyAsDouble(TextKey.PHI, 2));

		diffMetadata = new DiffractionMetadata(fileName, detprop, env);
		diffMetadata.setMetadata(headers);
		return highs;
	}

	enum BinaryKey {
		B_SIDE,            // length of side in pixels
		B_HIGH,            // number of high intensity pixels
		B_FORMAT,          // 1 = compressed, 2 = spiral
		B_MODE,            // 0 = dose, 1=time
		B_SIZE,            // total number of pixels
		B_WIDTH,           // pixel width in microns
		B_HEIGHT,          // pixel height in microns
		B_WAVELENGTH(1e6), // in Angstroms*1e6
		B_DISTANCE(1e3),   // in mm*1e3
		B_B_PHI(1e3),      // in degrees*1e3
		B_E_PHI(1e3),      // in degrees*1e3
		B_B_OMEGA(1e3),    // in degrees*1e3
		B_E_OMEGA(1e3),    // in degrees*1e3
		B_CHI(1e3),        // in degrees*1e3
		B_TWO_THETA(1e3),  // in degrees*1e3
		;

		double factor;
		BinaryKey() {
			this(1.0);
		}

		BinaryKey(double factor) {
			this.factor = factor;
		}
	}

	enum TextKey {
		PROGRAM(2),    // <name> <version>
		DATE,          // <week(?) day month hh:mm:ss year>
		SCANNER,       // <serial_number>
		FORMAT(3),     // <size>(side) <type>("MAR345", "PCK345", "SPIRAL") <no_pixels>
		HIGH,          // <n_high>(pixels with values > 65535)
		PIXEL("LENGTH", "HEIGHT"),
					   // LENGTH <pix_length>(in microns) HEIGHT <pix_height>
		OFFSET("ROFF", "TOFF"),
					   // ROFF <roff>(radial offset in mm) TOFF <toff>(tangential)
		MULTIPLIER,    // <multi>(high intensity multiplier)
		GAIN,          // <gain>
		WAVELENGTH,    // <wave>(in Angstroms)
		DISTANCE,      // <distance>(in mm)
		RESOLUTION,    // <dmax>(in Angstroms)
		PHI("START", "END", "OSC"),
					   // START <phi_start>(in degrees) END <phi_end>(in degrees) OSC <n_osc>
		OMEGA("START", "END", "OSC"),
					   // START <omega_start> END <omega_end> OSC <n_osc>
		CHI,           // <chi>(in degrees)
		TWOTHETA,      // <two_theta>(in degrees)
		CENTER("X", "Y"),
					   // X <x_cen>(direct beam in pixels) Y <y_cen>
		MODE,          // <dc_mode>("TIME" or "DOSE")
		TIME,          // <exp_time>(in seconds)
		COUNTS("START", "END", "MIN", "MAX", "AVE", "SIG", "NMEAS"),
					   // START <cnt_beg> END <cnt_end> MIN <cnt_min> MAX <cnt_max> AVE <cnt_ave> SIG <cnt_sig> NMEAS <cnt_n>
		INTENSITY("MIN", "MAX", "AVE", "SIG"),
					   // MIN <int_min> MAX <int_max> AVE <int_ave> SIG <int_sig>
		HISTOGRAM("START", "END", "MAX"),
					   // START <his_beg> END <his_end> MAX <his_max>(modal pixel value)
		GENERATOR(1, "kV", "mA"),
					   // <type>("SEALED TUBE", ROTATING ANODE", "SYNCHROTRON") kV <kiloVolt> mA <millAmps>
		MONOCHROMATOR(1, "POLAR"),
					   // <type>("GRAPHITE", "MIRRORS", "FILTER") POLAR <polarization>
		COLLIMATOR("WIDTH", "HEIGHT"),
					   // WIDTH <width>(aperature slit dims in mm) HEIGHT <height>
		REMARK,        // <text>(single line)
		GAPS,          // some numbers
		ADC("A", "B", "ADD_A", "ADD_B"),
					   // A <gradient_a> B <gradient_b> ADD_A <offset_a> ADD_B <offset_b> 
		DETECTOR,      // some string
		;

		int initial;
		String[] subkeys;

		TextKey() {
			this(1);
		}

		TextKey(String...subkeys) {
			this(0, subkeys);
		}

		TextKey(int initial, String...subkeys) {
			this.initial = initial;
			this.subkeys = subkeys;
		}
	}

	private static final Map<String, TextKey> keywords = new HashMap<>();
	static {
		for (TextKey t : TextKey.values()) {
			keywords.put(t.toString(), t);
		}
	}

	private static final int LINE_LENGTH = 64;
	private static final int INT_LENGTH = 4;
	private static final int HEADER_SIZE = 4096;

	private static final String FIRST_HEADER = "mar research";
	private static final String END_OF_HEADER = "END OF HEADER";
	private static final Pattern WHITE_SPACES_REGEX = Pattern.compile("\\s+");

	private void readHeader(BufferedInputStream bis) throws IOException, ScanFileHolderException {
		headers.clear();

		int pos = 0;
		byte[] header = new byte[HEADER_SIZE];
		bis.read(header);

		littleEndian = MARLoader.isLittleEndian(header, pos);
		pos += INT_LENGTH;

		for (BinaryKey k : BinaryKey.values()) {
			headers.put(k.toString(), getInteger(header, pos));
			pos += INT_LENGTH;
		}

		assert pos == 64;

		String line;

		do {
			line = Utils.getString(header, pos, LINE_LENGTH);
			pos += LINE_LENGTH;
			if (pos >= HEADER_SIZE)  {
				throw new ScanFileHolderException("Cannot find correct identifier string in header");
			}
		} while (!line.startsWith(FIRST_HEADER));

		while (pos < HEADER_SIZE) {
			line = Utils.getString(header, pos, LINE_LENGTH).trim();
			pos += LINE_LENGTH;
			if (line.startsWith(END_OF_HEADER)) {
				break;
			}
			String[] words = WHITE_SPACES_REGEX.split(line, 2);
			if (words.length > 0) {
				String keyword = words[0].trim();
				if (keywords.containsKey(keyword)) {
					if (words.length > 1) {
						headers.put(keyword, (Serializable) parseLine(keyword, words[1]));
					} else {
						logger.warn("Missing argument for {}", keyword);
					}
				} else {
					logger.warn("Keyword {}: unknown", keyword);
					headers.put(keyword, words[1]);
				}
			} else {
				logger.warn("Empty line");
			}
		}

		if (!line.startsWith(END_OF_HEADER)) {
			throw new ScanFileHolderException("Cannot find end header string");
		}
		assert pos == HEADER_SIZE;

		sanityCheck();
	}

	private List<String> parseLine(String keyword, String line) {
		TextKey textKey = keywords.get(keyword);

		String[] subKeys = textKey.subkeys;
		List<String> values = new ArrayList<>();
		int start = -1;
		int l = -1;
		for (String s : subKeys) {
			int i = line.indexOf(s);
			if (i < 0) {
				logger.warn("Subkey {} not found for {}", s, textKey);
				continue;
			}
			if (start < 0) {
				start = i;
			}
			if (l >= 0) {
				values.add(line.substring(l, i).trim());
			}
			l = i + s.length();
		}
		if (l >= 0) {
			values.add(line.substring(l).trim());
		}
		if (start >= 0) {
			line = line.substring(0, start);
		}
		int initial = textKey.initial;
		if (initial > 0) {
			if (initial == 1) {
				values.add(0, line);
			} else {
				int j = 0;
				for (String v : line.split("\\s+", initial)) {
					if (v.length() > 0) {
						values.add(j++, v);
					}
				}
			}
		}

		Serializable old = headers.get(keyword);
		if (old instanceof List && ((List<?>) old).size() > 0) {
			Object o = ((List<?>) old).get(0);
			if (o instanceof Serializable) {
				int j = 0;
				for (Object i : (List<?>) old) {
					values.add(j++, i.toString());
				}
			}
		} else if (old != null) {
			values.add(0, old.toString());
		}
		return values;
	}

	private int getInteger(byte[] bytes, int pos) {
		return littleEndian ? Utils.leInt(bytes[pos], bytes[pos + 1], bytes[pos + 2], bytes[pos + 3]) :
			Utils.beInt(bytes[pos], bytes[pos + 1], bytes[pos + 2], bytes[pos + 3]);
	}

	private void sanityCheck() {
		checkIntegerKey(BinaryKey.B_HIGH, TextKey.HIGH, 0);
		checkIntegerKey(BinaryKey.B_SIDE, TextKey.FORMAT, 0);
		checkIntegerKey(BinaryKey.B_SIZE, TextKey.FORMAT, 2);
		checkFloatKey(BinaryKey.B_WIDTH, TextKey.PIXEL, 0);
		checkFloatKey(BinaryKey.B_HEIGHT, TextKey.PIXEL, 1);
		checkFloatKey(BinaryKey.B_WAVELENGTH, TextKey.WAVELENGTH, 0);
		checkFloatKey(BinaryKey.B_DISTANCE, TextKey.DISTANCE, 0);
		checkFloatKey(BinaryKey.B_B_PHI, TextKey.PHI, 0);
		checkFloatKey(BinaryKey.B_E_PHI, TextKey.PHI, 1);
		checkFloatKey(BinaryKey.B_B_OMEGA, TextKey.OMEGA, 0);
		checkFloatKey(BinaryKey.B_E_OMEGA, TextKey.OMEGA, 1);
		checkFloatKey(BinaryKey.B_CHI, TextKey.CHI, 0);
		checkFloatKey(BinaryKey.B_TWO_THETA, TextKey.TWOTHETA, 0);
	}

	private void checkIntegerKey(BinaryKey b, TextKey t, int i) {
		Serializable o;
		int bv = -1;
		o = headers.get(b.toString());
		if (o instanceof Integer) {
			bv = (Integer) o;
		}
		o = headers.get(t.toString());
		if (o instanceof List<?>) {
			int tv = Integer.valueOf((String) ((List<?>) o).get(i));
			if (tv != bv) {
				logger.warn("Binary header does not match text header for {}: {} cf {}", t.toString(), bv, tv);
			}
		}
	}

	private void checkFloatKey(BinaryKey b, TextKey t, int i) {
		Serializable o;
		int bv = -1;
		o = headers.get(b.toString());
		if (o instanceof Integer) {
			bv = (Integer) o;
		}
		
		int tv = (int) Math.round(getKeyAsDouble(t, i) * b.factor);
		if (bv >= 0 && tv != bv) {
			logger.warn("Binary header does not match text header for {}: {} cf {}", t.toString(), bv, tv);
		}
	}

	private double getKeyAsDouble(TextKey t) {
		return getKeyAsDouble(t, 0);
	}

	private double getKeyAsDouble(TextKey t, int i) {
		Serializable o;
		o = headers.get(t.toString());
		if (o instanceof List<?>) {
			return Double.valueOf((String) ((List<?>) o).get(i));
		}
		return Double.NaN;
	}

	private int getKeyAsInt(TextKey t) {
		return getKeyAsInt(t, 0);
	}

	private int getKeyAsInt(TextKey t, int i) {
		Serializable o;
		o = headers.get(t.toString());
		if (o instanceof List<?>) {
			return Integer.valueOf((String) ((List<?>) o).get(i));
		}
		return 0;
	}

	private static final int HIGH_RECORD_SIZE = 64;

	private int[] readHighValues(int high, BufferedInputStream bi) throws IOException {
		int records = (int) Math.ceil(high/8.0);
		high *= 2;
		int h = 0;
		byte[] record = new byte[HIGH_RECORD_SIZE];
		int[] pixels = new int[high];
		for (int r = 0; r < records; r++) {
			bi.read(record);
			int pos = 0;

			while (pos < HIGH_RECORD_SIZE && h < high) {
				pixels[h++] = getInteger(record, pos) - 1; // address
				pos += INT_LENGTH;
				pixels[h++] = getInteger(record, pos); // value
				pos += INT_LENGTH;
			}
		}
		return pixels;
	}

	private static final String CCP4 = "CCP4 packed image";
	private static final int CCP4_LENGTH = 38; // excludes initial LF (38 for V2)
	private static final String CCP4_V2 = " V2";
	private static final Pattern CCP4_PATTERN = Pattern.compile(CCP4 + "(" + CCP4_V2 + ")?, X: (\\d+), Y: (\\d+)");
	private static final int BUFFER_SIZE = 4096; // must be greater than CCP4_LENGTH + 1
	private static final int BUFFER_HALF = BUFFER_SIZE/2;

	private static final int[] PACK_V1_BITS = new int[] {0, 4, 5, 6, 7, 8, 16, 32};

	/**
	 * an array of masks with set bits from 0 to index
	 */
	private static final int[] MASK_UP_TO = new int[9];

	/**
	 * an array of masks with set bits from 832 to index
	 */
	private static final int[] MASK_DOWN_TO = new int[33];

	static {
		int b = 0xff;
		for (int i = 8; i >= 0; i--) {
			MASK_UP_TO[i] = b;
			b >>>= 1;
		}
		b = 0x80000000;
		for (int i = 32; i >= 0; i--) {
			MASK_DOWN_TO[i] = b;
			b >>= 1;
		}
	}

	private int[] readPackedImage(BufferedInputStream bi) throws IOException, ScanFileHolderException {
		// look something like for "CCP4 packed image, X: 0123, Y: 4567\n"
		byte[] buffer = new byte[BUFFER_SIZE];
		if (bi.read(buffer) != BUFFER_SIZE) {
			throw new ScanFileHolderException("End of file reached before CCP4 string found");
		}
		String l = Utils.getString(buffer, 0, buffer.length);
		while (!l.contains(CCP4)) { // do overlapping search
			System.arraycopy(buffer, BUFFER_HALF, buffer, 0, BUFFER_HALF);
			if (bi.read(buffer, BUFFER_HALF, BUFFER_HALF) != BUFFER_HALF) {
				throw new ScanFileHolderException("End of file reached before CCP4 string found");
			}
			l = Utils.getString(buffer);
		}

		int p = l.indexOf(CCP4) + CCP4_LENGTH; // position of current compressed data
		if (p > BUFFER_SIZE) { // ensure starting point is in buffer
			System.arraycopy(buffer, BUFFER_HALF, buffer, 0, BUFFER_HALF);
			if (bi.read(buffer, BUFFER_HALF, BUFFER_HALF) != BUFFER_HALF) {
				throw new ScanFileHolderException("End of file reached before CCP4 string found");
			}
			l = Utils.getString(buffer);
		}

		Matcher m = CCP4_PATTERN.matcher(l);
		if (!m.find()) {
			throw new ScanFileHolderException("Not sufficient dimensions in CCP4 string");
		}

		int i = 1;
		String t = m.group(i++);
		boolean v1 = t == null;
		side = Integer.valueOf(v1 ? m.group(i++) : t);
		int y = Integer.valueOf(m.group(i++));
		int size = getKeyAsInt(TextKey.FORMAT);
		if (side != y || side != size) {
			throw new ScanFileHolderException("Dimensions in CCP4 string does not match those specified in header");
		}

		size = side * y;
		int[] image = new int[size];

		ByteBuffer bb = new ByteBuffer(bi, buffer, m.end() + 1, BUFFER_SIZE); // skip LF after last dimension
		int b = bb.nextByte();

		final int csize = v1 ? 3 : 4; // chunk parameter size
		final int chmask = MASK_UP_TO[csize]; // chunk mask
		final int cfmask = MASK_UP_TO[2*csize]; // chunk mask

		int imax; // end of chunk
		int bpp; // number of bits per pixel
		int ptr = 0; // points to number of bits used in byte
		int optr = 0; // points to number of bits used in byte
		i = 0;
		while (i < size) {
			// decode chunk parameters
			optr = ptr;
			ptr += 2*csize;
			if (ptr < 8) {
				bpp = (b >>> optr) & cfmask;
			} else {
				bpp = b >>> optr;
				ptr -= 8;
				b = bb.nextByte();
				bpp |= b << (8-optr);
			}
			imax = i + (1 << (bpp & chmask));
			bpp = v1 ? PACK_V1_BITS[(bpp >>> csize) & chmask]
					: 1 << ((bpp >>> csize) & chmask);

			// read chunk
			while (i < imax) {
				int value;
				if (bpp == 0) {
					value = 0;
				} else {
					optr = ptr;
					ptr += bpp;
					if (ptr < 8) {
						value = (b >>> optr) & MASK_UP_TO[bpp];
					} else { // pixel overlaps byte boundary
						value = b >>> optr;
						ptr -= 8;
						b = bb.nextByte();

						int r = 8 - optr; // read number of bits
						while (ptr >= 8) {
							value |= b << r;
							r += 8;
							ptr -= 8;
							b = bb.nextByte();
						}

						if (ptr > 0) {
							value |= (b & MASK_UP_TO[ptr]) << r;
						}
					}
					if ((value & (1 << (bpp-1))) != 0) {
						value |= MASK_DOWN_TO[bpp]; // sign-extend result
					}
				}

				if (i == 0) {
					image[i] = value;
				} else if (i <= side) {
					image[i] = value + getSignedShort(image[i-1]);
				} else {
					int j = i - side - 1;
					int sum = 2 + getSignedShort(image[j++]) + getSignedShort(image[j++]) + getSignedShort(image[j]) + getSignedShort(image[i - 1]);
					image[i] = value + sum/4;
				}
				i++;
			}
		}

		return image;
	}

	private static short getSignedShort(int n) {
		return (short) (n & 0xffff);
	}

	/**
	 * Class to allow next byte to be read from given buffer
	 */
	class ByteBuffer {
		BufferedInputStream bi;
		private byte[] buffer;
		int p;
		int pmax;

		ByteBuffer(BufferedInputStream is, byte[] buffer, int pos, int bufferSize) {
			bi = is;
			this.buffer = buffer;
			p = pos;
			pmax = bufferSize;
		}

		int nextByte() throws IOException {
			if (p == pmax) {
				p = 0;
				if ((pmax = bi.read(buffer)) == 0) {
					throw new IOException("No values left");
				}
			}
			return buffer[p++] & 0xff;
		}
	}

}
