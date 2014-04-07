/*
 * Copyright 2011 Diamond Light Source Ltd.
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.HashMap;

import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * Class to load Rigaku images. Class returns a DataHolder that is called from the ScanFileHolder class.
 */
public class RAxisImageLoader extends AbstractFileLoader implements IMetaLoader, Serializable {

	private String fileName = "";

	private HashMap<String, Serializable> metadata = new HashMap<String, Serializable>();
	private HashMap<String, Serializable> GDAMetadata = new HashMap<String, Serializable>();
	private boolean keepBitWidth = false;

	private DiffractionMetadata diffMetadata;
	private static final String DATA_NAME = "RAxis Image";

	/**
	 * @return true if loader keeps bit width of pixels
	 */
	public boolean isKeepBitWidth() {
		return keepBitWidth;
	}

	/**
	 * set loader to keep bit width of pixels
	 * 
	 * @param keepBitWidth
	 */
	public void setKeepBitWidth(boolean keepBitWidth) {
		this.keepBitWidth = keepBitWidth;
	}

	public RAxisImageLoader() {

	}

	/**
	 * @param FileName
	 */
	public RAxisImageLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param keepBitWidth
	 *            true if loader keeps bit width of pixels
	 */
	public RAxisImageLoader(String FileName, boolean keepBitWidth) {
		setFile(FileName);
		this.keepBitWidth = keepBitWidth;
	}

	public void setFile(final String fileName) {
		this.fileName = fileName;
		// New file, new meta data
		metadata.clear();
		GDAMetadata.clear();
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {

		DataHolder output = new DataHolder();
		// opens the file and reads the header information
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException fnf) {
			throw new ScanFileHolderException("File not found", fnf);
		} catch (Exception e) {
			try {
				if (raf != null)
					raf.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			throw new ScanFileHolderException("There was a problem loading or reading metadata", e);
		}
		processingMetadata(raf);
		int height = toInt("nFast");
		int width = toInt("nSlow");
		double st = toDouble("phistart");
		double[] origin = createGDAMetadata(height, width, st);
		createMetadata(origin, height, width, st);

		// Opens the file and reads the byte information and parsed them to doubles.
		try {

			// poke with stick int[] shape = { height, width };
			int[] shape = { width, height };
			AbstractDataset data;

			// read in all the data at once for speed.

			byte[] read = new byte[(height * width) * 2];
			
			raf.read(read);

			// and put it into the dataset
			data = new IntegerDataset(shape);
			int[] databuf = ((IntegerDataset) data).getData();
			int amax = Integer.MIN_VALUE;
			int amin = Integer.MAX_VALUE;
			int hash = 0;
			for (int i = 0, j = 0; i < databuf.length; i++) {
				int value = Utils.beInt(read[j++], read[j++]);
				hash = hash * 19 + value;
				databuf[i] = value;
				if (value > amax) {
					amax = value;
				}
				if (value < amin) {
					amin = value;
				}
		
			}
			if (keepBitWidth || amax < (1 << 15)) {
				data = DatasetUtils.cast(data, AbstractDataset.INT16);
			}

			hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
			int rank = shape.length;
			for (int i = 0; i < rank; i++) {
				hash = hash*17 + shape[i];
			}
			data.setStoredValue(AbstractDataset.STORE_MAX, amax);
			data.setStoredValue(AbstractDataset.STORE_MIN, amin);
			data.setStoredValue(AbstractDataset.STORE_HASH, hash);

			data.setName(DEF_IMAGE_NAME);
			output.addDataset(DATA_NAME, data);
			if (loadMetadata) {
				data.setMetadata(getMetaData());
				output.setMetadata(data.getMetadata());
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("There was a problem reading the RAxis image", e);
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				throw new ScanFileHolderException("Problem closing RAxis file", e);
			}
		}

		return output;
	}

	/**
	 * processing all Metadata between { and } tags at the top of the file, put it into a key-value pair map if they are
	 * in "key=value" format. remove ; from line ending
	 * 
	 * @param raf
	 * @throws ScanFileHolderException
	 */
	private void processingMetadata(RandomAccessFile raf) throws ScanFileHolderException {
		// handling metadata in the file header
		try {
			byte[] b = new byte[10];
			raf.readFully(b);
			metadata.put("Device", new String(b).trim());
			// this is a really crude way of figuring out if the binary files
			// is from a RAxis IP
			if (!new String(b).trim().contains("RAXIS"))
				throw new ScanFileHolderException("This image does not appear to be an RAxis image");

			b = new byte[10];
			raf.readFully(b);
			metadata.put("Version", new String(b).trim());

			b = new byte[20];
			raf.readFully(b);
			metadata.put("Crystal", new String(b).trim());

			b = new byte[12];
			raf.readFully(b);
			metadata.put("Crystal system", new String(b).trim());

			raf.skipBytes(24); // this is supposed to be a b c al be ga

			b = new byte[12];
			raf.readFully(b);
			metadata.put("SpaceGroup", new String(b).trim());

			metadata.put("mosaic1", raf.readFloat());

			b = new byte[80];
			raf.readFully(b);
			metadata.put("memo", new String(b).trim());

			b = new byte[84];
			raf.readFully(b);
			metadata.put("reserve1", new String(b).trim());

			b = new byte[12];
			raf.readFully(b);
			metadata.put("date", new String(b).trim());

			b = new byte[20];
			raf.readFully(b);
			metadata.put("operatorname", new String(b).trim());

			b = new byte[4];
			raf.readFully(b);
			metadata.put("target", new String(b).trim());

			// ('wavelength',4,'!f'),
			metadata.put("wavelength", raf.readFloat());

			// ('monotype',20,'s'),
			b = new byte[20];
			raf.readFully(b);
			metadata.put("monotype", new String(b).trim());

			// ('mono2theta',4,'!f'),
			metadata.put("mono2theta", raf.readFloat());

			// ('collimator',20,'s'),
			b = new byte[20];
			raf.readFully(b);
			metadata.put("collimator", new String(b).trim());

			// ('filter',4,'s'),
			b = new byte[4];
			raf.readFully(b);
			metadata.put("filter", new String(b).trim());

			// ('distance',4,'!f'),
			metadata.put("distance", raf.readFloat());

			// ('Kv',4,'!f'),
			metadata.put("Kv", raf.readFloat());

			// ('mA',4,'!f'),
			metadata.put("mA", raf.readFloat());

			// ('focus',12,'s'),
			b = new byte[12];
			raf.readFully(b);
			metadata.put("focus", new String(b).trim());

			// ('Xmemo',80,'s'),
			b = new byte[80];
			raf.readFully(b);
			metadata.put("Xmemo", new String(b).trim());

			// ('cyl',4,'!i'),
			metadata.put("cyl", raf.readInt());

			// (None,60),
			raf.skipBytes(60);

			// ('Spindle',4,'s'), # Crystal mount axis closest to spindle axis
			b = new byte[4];
			raf.readFully(b);
			metadata.put("Spindle", new String(b).trim());

			// ('Xray_axis',4,'s'), # Crystal mount axis closest to beam axis
			b = new byte[4];
			raf.readFully(b);
			metadata.put("Xray_axis", new String(b).trim());

			// ('phidatum',4,'!f'),
			metadata.put("phidatum", raf.readFloat());

			// ('phistart',4,'!f'),
			metadata.put("phistart", raf.readFloat());

			// ('phiend',4,'!f'),
			metadata.put("phiend", raf.readFloat());

			// ('noscillations',4,'!i'),
			metadata.put("noscillations", raf.readInt());

			// ('minutes',4,'!f'), # Exposure time in minutes?
			metadata.put("minutes", raf.readFloat());

			// ('beampixels_x',4,'!f'),
			metadata.put("beampixels_x", raf.readFloat());

			// ('beampixels_y',4,'!f'), # Direct beam position in pixels
			metadata.put("beampixels_y", raf.readFloat());

			// ('omega',4,'!f'),
			metadata.put("omega", raf.readFloat());

			// ('chi',4,'!f'),
			metadata.put("chi", raf.readFloat());

			// ('twotheta',4,'!f'),
			metadata.put("twotheta", raf.readFloat());
			// ('Mu',4,'!f'), # Spindle inclination angle?

			metadata.put("Mu", raf.readFloat());
			// ('ScanTemplate',204,'s'), # This space is now used for storing the scan
			// # templates information
			b = new byte[204];
			raf.readFully(b);
			metadata.put("ScanTemplate", new String(b).trim());

			// ('nFast',4,'!i'),
			metadata.put("nFast", raf.readInt());

			// ('nSlow',4,'!i'), # Number of fast, slow pixels
			metadata.put("nSlow", raf.readInt());

			// ('sizeFast',4,'!f'),
			metadata.put("sizeFast", raf.readFloat());

			// ('sizeSlow',4,'!f'), # Size of fast, slow direction in mm
			metadata.put("sizeSlow", raf.readFloat());

			// ('record_length',4,'!i'), # Record length in bytes
			metadata.put("record_length", raf.readInt());

			// ('number_records',4,'!i'), # number of records
			metadata.put("number_records", raf.readInt());

			// ('Read_start',4,'!i'), # For partial reads, 1st read line
			metadata.put("Read_start", raf.readInt());

			// ('IP_num',4,'!i'), # Which imaging plate 1, 2 ?
			metadata.put("IP_num", raf.readInt());

			// ('Ratio',4,'!f'), # Output ratio for high value pixels
			metadata.put("Ratio", raf.readFloat());

			// ('Fading_start',4,'!f'), # Fading time to start of read
			metadata.put("Fading_start", raf.readFloat());

			// ('Fading_end',4,'!f'), # Fading time to end of read
			metadata.put("Fading_end", raf.readFloat());

			// ('computer',10,'s'), # Type of computer "IRIS", "VAX", "SUN", etc
			b = new byte[10];
			raf.readFully(b);
			metadata.put("computer", new String(b).trim());

			// ('plate_type',10,'s'), # Type of IP
			b = new byte[10];
			raf.readFully(b);
			metadata.put("plate_type", new String(b).trim());

			// ('Dr',4,'!i'),
			metadata.put("Dr", raf.readInt());

			// ('Dx',4,'!i'),
			metadata.put("Dx", raf.readInt());

			// ('Dz',4,'!i'), # IP scanning codes??
			metadata.put("Dz", raf.readInt());

			// ('PixShiftOdd',4,'!f'), # Pixel shift to odd lines
			metadata.put("PixShiftOdd", raf.readFloat());

			// ('IntRatioOdd',4,'!f'), # Intensity ratio to odd lines
			metadata.put("IntRatioOdd", raf.readFloat());

			// ('MagicNum',4,'!i'), # Magic number to indicate next values are legit
			metadata.put("MagicNum'", raf.readInt());

			// ('NumGonAxes',4,'!i'), # Number of goniometer axes
			metadata.put("NumGonAxes'", raf.readInt());

			// ('a5x3fGonVecs',60,'!fffffffffffffff'),# Goniometer axis vectors
			float[] fa = { raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(),
					raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(),
					raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), };
			metadata.put("a5x3fGonVecs", fa);

			// ('a5fGonStart',20,'!fffff'),# Start angles for each of 5 axes
			float[] fb = { raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat() };
			metadata.put("a5x3fGonStart", fb);

			// ('a5fGonEnd',20,'!fffff'), # End angles for each of 5 axes
			float[] fc = { raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat() };
			metadata.put("a5fGonEnd", fc);

			// ('a5fGonOffset',20,'!fffff'),# Offset values for each of 5 axes
			float[] fd = { raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat(), raf.readFloat() };
			metadata.put("a5fGonOffset", fd);

			// ('ScanAxisNum',4,'!i'), # Which axis is the scan axis?
			metadata.put("ScanAxisNum", raf.readInt());

			// ('AxesNames',40,'s'), # Names of the axes (space or comma separated?)'''
			b = new byte[10];
			raf.readFully(b);
			metadata.put("ScanAxisNum", new String(b).trim());

			raf.skipBytes((int) (toInt("record_length") - raf.getFilePointer()));

		} catch (IOException e) {
			throw new ScanFileHolderException("There was a problem parsing the RAxis header information", e);
		}
	}

	private int toInt(String key) {
		return (Integer) metadata.get(key);
	}

	private double toDouble(String key) {
		return ((Float) metadata.get(key)).doubleValue();
	}

	private double[] createGDAMetadata(int nx, int ny, double st) throws ScanFileHolderException {
		try {

			// NXGeometery:NXtranslation

			double x = nx - (nx - toDouble("beampixels_x")) * toDouble("sizeFast");
			double y = ny - (ny - toDouble("beampixels_y")) * toDouble("sizeSlow");
			double[] detectorOrigin = { x, y, toDouble("distance") };
			GDAMetadata.put("NXdetector:NXgeometery:NXtranslation", detectorOrigin);
			GDAMetadata.put("NXdetector:NXgeometery:NXtranslation:NXunits", "milli*meter");

			// NXGeometery:NXOrientation
			double[] directionCosine = { 1, 0, 0, 0, 1, 0 }; // to form identity matrix as no header data
			GDAMetadata.put("NXdetector:NXgeometery:NXorientation", directionCosine);
			// NXGeometery:XShape (shape from origin (+x, +y, +z,0, 0, 0) > x,y,0,0,0,0)
			double[] detectorShape = { nx * toDouble("sizeFast"),
					ny * toDouble("sizeSlow"), 0, 0, 0, 0 };
			GDAMetadata.put("NXdetector:NXgeometery:NXshape", detectorShape);
			GDAMetadata.put("NXdetector:NXgeometery:NXshape:NXshape", "milli*metre");

			// NXGeometery:NXFloat
			double[] pixelSize = { toDouble("sizeFast"), toDouble("sizeSlow") };
			GDAMetadata.put("NXdetector:x_pixel_size", pixelSize[0]);
			GDAMetadata.put("NXdetector:x_pixel_size:NXunits", "milli*metre");
			GDAMetadata.put("NXdetector:y_pixel_size", pixelSize[1]);
			GDAMetadata.put("NXdetector:y_pixel_size:NXunits", "milli*metre");
			// "NXmonochromator:wavelength"
			GDAMetadata.put("NXmonochromator:wavelength", toDouble("wavelength"));
			GDAMetadata.put("NXmonochromator:wavelength:NXunits", "Angstrom");

			// oscillation range
			GDAMetadata.put("NXSample:rotation_start", st);
			GDAMetadata.put("NXSample:rotation_start:NXUnits", "degree");
			GDAMetadata.put("NXSample:rotation_range", toDouble("phiend") - st);
			GDAMetadata.put("NXSample:rotation_range:NXUnits", "degree");

			// Exposure time
			GDAMetadata.put("NXSample:exposure_time", toDouble("minutes") * 60);
			GDAMetadata.put("NXSample:exposure_time:NXUnits", "seconds");
			return detectorOrigin;
		} catch (Exception e) {
			throw new ScanFileHolderException("There was a problem creating the GDA metatdata", e);
		}
	}

	private void createMetadata(double[] detectorOrigin, int nx, int ny, double st) {
		DetectorProperties detProps = new DetectorProperties(new Vector3d(detectorOrigin), ny, nx, toDouble("sizeSlow"), toDouble("sizeFast"), null);
		DiffractionCrystalEnvironment diffEnv = new DiffractionCrystalEnvironment(toDouble("wavelength"), st, toDouble("phiend"), toDouble("minutes") * 60);

		diffMetadata = new DiffractionMetadata(fileName, detProps, diffEnv);
		HashMap<String, Serializable> md = new HashMap<String, Serializable>();
		md.putAll(metadata);
		md.putAll(GDAMetadata);
		diffMetadata.setMetadata(md);
		diffMetadata.addDataInfo(DATA_NAME, ny, nx);
	}

	@Override
	public void loadMetaData(IMonitor mon) throws Exception {
		// opens the file and reads the header information
		RandomAccessFile raf = null;
		try {

			raf = new RandomAccessFile(fileName, "r");
			processingMetadata(raf);

		} catch (FileNotFoundException fnf) {
			throw new ScanFileHolderException("File not found", fnf);
		} catch (Exception e) {
			try {
				if (raf != null)
					raf.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			throw new ScanFileHolderException("There was a problem loading or reading metadata", e);
		}
	}

	@Override
	public IMetaData getMetaData() {
		return diffMetadata;
	}
}
