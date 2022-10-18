/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;

/**
 * Reads the output of XRMC
 * <p>
 * If the file extension of an XMRC file (with header) is changed from .dat to
 * .xmrc, then this loader will load the data and metadata from the file into
 * Dawn.
 */
public class XRMCLoader extends AbstractFileLoader {
	
	public enum XRMCMetadata{
		NORDER("nOrder", Integer.class), NCOLUMNS("nColumns", Integer.class),
		NROWS("nRows", Integer.class), PIXELSIZEX("pixelSizeX", Double.class),
		PIXELSIZEY("pixelSizeY", Double.class), EXPOSURETIME("exposureTime", Double.class),
		PIXELCONTENTTYPE("pixelContentType", Integer.class),
		NENERGY("nEnergy", Integer.class), MINENERGY("minEnergy", Double.class),
		MAXENERGY("maxEnergy", Double.class);
		
		private final String name;
		private Class<?> datatype;
		
		XRMCMetadata(String name, Class<?> datatype) {
			this.name = name;
			this.datatype = datatype;
		}
		
		public String getName() {
			return name;
		}
		
		public double readDoubleData(XRMCInputStream in) throws IOException {
			if (this.datatype == Double.class)
				return in.readDouble();
			else if (this.datatype == Integer.class)
				return in.readInt();
			else
				return Double.NaN;
		}
	}
	
	// metadata as a String double map. All 32 bit values are exactly storeable
	// as doubles
	protected Map<String, Double> metadataMap;
	
	public XRMCLoader() {
		metadataMap = new HashMap<String, Double>();
	}
	
	public XRMCLoader(final String filename) {
		this();
		setFile(filename);
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		DataHolder result = new DataHolder();
		
		XRMCInputStream in = null;
		
		
		try {
			FileInputStream fIS = new FileInputStream(fileName);
			in = new XRMCInputStream(fIS);
			
			for (XRMCMetadata aMeta : XRMCMetadata.values()) {
				metadataMap.put(aMeta.getName(), aMeta.readDoubleData(in));
			}
			
			// Header read in. Now check for consistency.
			if (metadataMap.get("nOrder") <= 0 ||
					metadataMap.get("nColumns") <= 0 ||
					metadataMap.get("nRows") <= 0 ||
					metadataMap.get("pixelSizeX") < 0 ||
					metadataMap.get("pixelSizeY") < 0 ||
					metadataMap.get("exposureTime") < 0 ||
					metadataMap.get("nEnergy") <= 0 ||
					metadataMap.get("minEnergy") < 0 ||
					metadataMap.get("maxEnergy") <= metadataMap.get("minEnergy"))
				throw new IllegalArgumentException("XRMC header data out of range");
			
			int nOrder = (int) Math.round(metadataMap.get("nOrder")),
					nColumns = (int) Math.round(metadataMap.get("nColumns")),
							nRows = (int) Math.round(metadataMap.get("nRows")),
									nEnergy = (int) Math.round(metadataMap.get("nEnergy"));
			
			double[] rawNumbers = new double[nOrder * nColumns * nRows * nEnergy];
			
			in.read(rawNumbers);
			
			Dataset data = DatasetFactory.createFromObject(rawNumbers, nOrder, nEnergy, nColumns, nRows);
			
			result.addDataset("data", data);
			
			metadata = new ExtendedMetadata(new File(fileName));
			metadata.setMetadata(metadataMap);
			
			metadata.addDataInfo("data", data.getShape());
			
			result.setMetadata(metadata);
			
		} catch (Exception e) {
			throw new ScanFileHolderException("XRMCLoader cannot load from file " + fileName, e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				throw new ScanFileHolderException("Cannot close stream from file " + fileName, ioe);
			}
		}
		return result;
	}

	@Override
	protected void clearMetadata() {
		metadataMap.clear();
	}

	/**
	 * A wrapping of DataInputStream that performs byte swapping, and allows reading of arrays of doubles
	 */
	public static class XRMCInputStream implements DataInput {
		
		private static final DeprecationLogger logger = DeprecationLogger.getLogger(XRMCInputStream.class);
		private final DataInputStream stream;
		
		public XRMCInputStream(InputStream in) {
			stream = new DataInputStream(in);
		}

		public void close() throws IOException{
			stream.close();
		}

		public int read(byte[] b) throws IOException {
			return stream.read(b);
		}
		
		public int read(byte[] b, int off, int len) throws IOException {
			return stream.read(b, off, len);
		}
		
		@Override
		public void readFully(byte[] b) throws IOException {
			stream.readFully(b);
		}

		@Override
		public void readFully(byte[] b, int off, int len) throws IOException {
			stream.readFully(b, off, len);
		}

		@Override
		public int skipBytes(int n) throws IOException {
			return stream.skipBytes(n);
		}
		
		@Override
		public boolean readBoolean() throws IOException {
			return stream.readBoolean();
		}
		
		@Override
		public byte readByte() throws IOException {
			return stream.readByte();
		}
		
		@Override
		public int readUnsignedByte() throws IOException {
			return stream.readUnsignedByte();
		}
		
		@Override
		public short readShort() throws IOException {
			byte[] bytes = new byte[2];
			stream.read(bytes);
			return (short)((bytes[1] << 8) | (bytes[0] & 0xff));
		}
		
		@Override
		public int readUnsignedShort() throws IOException {
			byte[] bytes = new byte[2];
			stream.read(bytes);
			return (((bytes[1] & 0xff) << 8) | (bytes[0] & 0xff));
		}
		
		@Override
		public char readChar() throws IOException {
			byte[] bytes = new byte[2];
			stream.read(bytes);
			return (char)((bytes[1] << 8) | (bytes[0] & 0xff));
		}
		
		@Override
		public int readInt() throws IOException {
			byte[] bytes = new byte[4];
			stream.read(bytes);
			return (((bytes[3] & 0xff) << 24) | ((bytes[2] & 0xff) << 16) |
					((bytes[1] & 0xff) << 8) | (bytes[0] & 0xff));
		}
		
		@Override
		@SuppressWarnings("cast") // unnecessary cast on last line
		public long readLong() throws IOException {
			byte[] bytes = new byte[8];
			stream.read(bytes);
			return (((long)(bytes[7] & 0xff) << 56) |
					((long)(bytes[6] & 0xff) << 48) |
					((long)(bytes[5] & 0xff) << 40) |
					((long)(bytes[4] & 0xff) << 32) |
					((long)(bytes[3] & 0xff) << 24) |
					((long)(bytes[2] & 0xff) << 16) |
					((long)(bytes[1] & 0xff) <<  8) |
					((long)(bytes[0] & 0xff)));
		}
		
		@Override
		public float readFloat() throws IOException {
			int raw = this.readInt();
			return Float.intBitsToFloat(raw);
		}

		@Override
		public double readDouble() throws IOException {
			long raw = this.readLong();
			return Double.longBitsToDouble(raw);
		}
	
		@Override
		@Deprecated
		public String readLine() throws IOException {
			logger.deprecatedMethod("readLine()");
			return stream.readLine();
		}
		
		@Override
		public String readUTF() throws IOException {
			return stream.readUTF();
		}

		public static String readUTF(DataInput in) throws IOException {
			return DataInputStream.readUTF(in);
		}
		
		public void read(double[] in) throws IOException{
			read(in, 0, in.length);
		}
		
		public void read(double[] in, int offset, int length) throws IOException {
			for (int i = 0; i < length; i++) {
				in[i+offset] = readDouble();
			}
		}
	}
}
