/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

/**
 * Loader to allow the XMap files to be loaded in
 */
public class XMapLoader extends AbstractFileLoader {

	String fileName = "";

	public XMapLoader() {
		
	}
	
	/**
	 * Constructor which takes a filename
	 * 
	 * @param InputFileName
	 */
	public XMapLoader(String InputFileName) {
		fileName = InputFileName;
	}

	
	public void setFile(final String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Implemented method which actually loads in the data. Using the DXD-xMAP : Mapping buffer Specification
	 * 
	 * @return the loaded data
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {		
		ZipFile zipFile;
		DataHolder ldh = new DataHolder();
		AbstractDataset[] data = new AbstractDataset[4];

		try {
			zipFile = new ZipFile(fileName);
		} catch (IOException e) {
			throw new ScanFileHolderException("The zip file was not opened correctly", e);
		}

		for (Enumeration<?> e = zipFile.entries(); e.hasMoreElements();) {

			InputStream in;

			try {
				in = zipFile.getInputStream((ZipEntry) e.nextElement());
			} catch (IOException e1) {
				throw new ScanFileHolderException("Zip entry not valid", e1);
			}

			// File file = new File(fileName);
			// InputStream in;
			// try {
			// in = new FileInputStream(file);
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
			// throw new ScanFileHolderException("");
			// }

			boolean finished = false;

			BufferData bufferedData = new BufferData();
			try {
				bufferedData.read(in);
			} catch (IOException e2) {
				throw new ScanFileHolderException("The main Headder was not read correctly", e2);
			}
//			System.out.println(bufferedData);

			while (!finished) {

				if (bufferedData.mappingMode == 1) {

					// i think there are a number of scans at this point
					for (int i = 0; i < bufferedData.numberOfPixelsInBuffer; i++) {

						// we are running in mapping mode and should therefore read in the data in this way
						MappingMode1Data mappingMode1Data = new MappingMode1Data();
						try {
							mappingMode1Data.read(in);
						} catch (IOException e2) {
							throw new ScanFileHolderException("The zip file was not opend cortrectly", e2);
						}
//						System.out.println(mappingMode1Data);

						if (mappingMode1Data.channel0Size > 0) {
							ShortDataset temp = new ShortDataset(mappingMode1Data.channel0Spectrum, 1,
									mappingMode1Data.channel0Size);
							if (data[0] == null) {
								// create the dataset
								data[0] = temp;
							} else {
								data[0] = DatasetUtils.append(data[0], temp, 0);
							}
						}
						if (mappingMode1Data.channel1Size > 0) {
							ShortDataset temp = new ShortDataset(mappingMode1Data.channel1Spectrum, 1,
									mappingMode1Data.channel1Size);
							if (data[1] == null) {
								// create the dataset
								data[1] = temp;
							} else {
								data[1] = DatasetUtils.append(data[1], temp, 0);
							}
						}
						if (mappingMode1Data.channel2Size > 0) {
							ShortDataset temp = new ShortDataset(mappingMode1Data.channel2Spectrum, 1,
									mappingMode1Data.channel2Size);
							if (data[2] == null) {
								// create the dataset
								data[2] = temp;
							} else {
								data[2] = DatasetUtils.append(data[2], temp, 0);
							}
						}
						if (mappingMode1Data.channel3Size > 0) {
							ShortDataset temp = new ShortDataset(mappingMode1Data.channel3Spectrum, 1,
									mappingMode1Data.channel3Size);
							if (data[3] == null) {
								// create the dataset
								data[3] = temp;
							} else {
								data[3] = DatasetUtils.append(data[3], temp, 0);
							}
						}

					}
				}

				try {
					bufferedData.read(in);
				} catch (IOException e2) {
					finished = true;
				}
//				System.out.println(bufferedData);

			}

		}

		ldh.addDataset("Channel0", data[0]);
		ldh.addDataset("Channel1", data[1]);
		ldh.addDataset("Channel2", data[2]);
		ldh.addDataset("Channel3", data[3]);

		return ldh;
	}

	private class BufferData {
		int tagWord0;
		int tagWord1;
		int bufferHeadderSize;
		int mappingMode;
		int runNumber;
		int sequentialBufferNumber;
		int bufferID;
		int numberOfPixelsInBuffer;
		int startingPixelNumber;
		int moduleSerialNumber;
		int detectorChannel0;
		int detectorElementChannel0;
		int detectorChannel1;
		int detectorElementChannel1;
		int detectorChannel2;
		int detectorElementChannel2;
		int detectorChannel3;
		int detectorElementChannel3;
		int channel0Size;
		int channel1Size;
		int channel2Size;
		int channel3Size;
		int bufferErrors;

		void read(InputStream in) throws IOException {
			
			ByteBuffer buf = new ByteBuffer(256, in);
			
			tagWord0 = buf.readShort();
			tagWord1 = buf.readShort();
			bufferHeadderSize = buf.readShort();
			mappingMode = buf.readShort();
			runNumber = buf.readShort();
			sequentialBufferNumber = buf.readInt();
			bufferID = buf.readShort();
			numberOfPixelsInBuffer = buf.readShort();
			startingPixelNumber = buf.readInt();
			moduleSerialNumber = buf.readShort();
			detectorChannel0 = buf.readShort();
			detectorElementChannel0 = buf.readShort();
			detectorChannel1 = buf.readShort();
			detectorElementChannel1 = buf.readShort();
			detectorChannel2 = buf.readShort();
			detectorElementChannel2 = buf.readShort();
			detectorChannel3 = buf.readShort();
			detectorElementChannel3 = buf.readShort();
			channel0Size = buf.readShort();
			channel1Size = buf.readShort();
			channel2Size = buf.readShort();
			channel3Size = buf.readShort();
			bufferErrors = buf.readShort();
			buf.skipWords( 231);

		}

		/**
		 * To string method
		 */
		@Override
		public String toString() {
			String output = "";
			output += ("tagWord0 " + tagWord0 + "\n");
			output += ("tagWord1 " + tagWord1 + "\n");
			output += ("bufferHeadderSize " + bufferHeadderSize + "\n");
			output += ("mappingMode " + mappingMode + "\n");
			output += ("runNumber " + runNumber + "\n");
			output += ("sequentialBufferNumber " + sequentialBufferNumber + "\n");
			output += ("bufferID " + bufferID + "\n");
			output += ("numberOfPixelsInBuffer " + numberOfPixelsInBuffer + "\n");
			output += ("startingPixelNumber " + startingPixelNumber + "\n");
			output += ("moduleSerialNumber " + moduleSerialNumber + "\n");
			output += ("detectorChannel0 " + detectorChannel0 + "\n");
			output += ("detectorElementChannel0 " + detectorElementChannel0 + "\n");
			output += ("detectorChannel1 " + detectorChannel1 + "\n");
			output += ("detectorElementChannel1 " + detectorElementChannel1 + "\n");
			output += ("detectorChannel2 " + detectorChannel2 + "\n");
			output += ("detectorElementChannel2 " + detectorElementChannel2 + "\n");
			output += ("detectorChannel3 " + detectorChannel3 + "\n");
			output += ("detectorElementChannel3 " + detectorElementChannel3 + "\n");
			output += ("channel0Size " + channel0Size + "\n");
			output += ("channel1Size " + channel1Size + "\n");
			output += ("channel2Size " + channel2Size + "\n");
			output += ("channel3Size " + channel3Size + "\n");
			output += ("bufferErrors " + bufferErrors + "\n");
			return output;
		}

	}

	private class ChannelStatistics {
		float realtime;
		float livetime;
		int triggers;
		int outputEvents;

		void read(ByteBuffer buf) {
			realtime = buf.readFloat();
			livetime = buf.readFloat();
			triggers = buf.readInt();
			outputEvents = buf.readInt();
		}

		/**
		 * Overloaded tostring method
		 * 
		 * @return output
		 */
		@Override
		public String toString() {
			return "Realtime " + realtime + ": Livetime " + livetime + ": Triggers " + triggers + ": Output Events "
					+ outputEvents;
		}

	}

	private class MappingMode1Data {
//		int spaceToStart;
		int tagWord0;
		int tagWord1;
		int pixelheaderSize;
		int mappingMode;
		int pixelNumber;
		int totalPixelBlockSize;
		int channel0Size, k;
		int channel1Size, l;
		int channel2Size, m;
		int channel3Size, n;
		ChannelStatistics channel0Statistics;
		ChannelStatistics channel1Statistics;
		ChannelStatistics channel2Statistics;
		ChannelStatistics channel3Statistics;
		short[] channel0Spectrum;
		short[] channel1Spectrum;
		short[] channel2Spectrum;
		short[] channel3Spectrum;

		short[] readSpectrum(InputStream in, int sizeOfSpectrum) throws IOException {
			short[] spectrum = new short[sizeOfSpectrum];
			ByteBuffer buf = new ByteBuffer(sizeOfSpectrum, in);
			for (int i = 0; i < sizeOfSpectrum; i++) {
				spectrum[i] = (short) buf.readShort();
			}
			return spectrum;
		}

		void read(InputStream in) throws IOException {
			
			ByteBuffer buf = new ByteBuffer(256, in);
			
			tagWord0 = buf.readShort();
			tagWord1 = buf.readShort();
			pixelheaderSize = buf.readShort();
			mappingMode = buf.readShort();
			pixelNumber = buf.readInt();
			totalPixelBlockSize = buf.readInt();
			channel0Size = buf.readShort();
			k = channel0Size;
			channel1Size = buf.readShort();
			l = channel1Size;
			channel2Size = buf.readShort();
			m = channel2Size;
			channel3Size = buf.readShort();
			n = channel3Size;
			buf.skipWords(20);
			channel0Statistics = new ChannelStatistics();
			channel0Statistics.read(buf);
			channel1Statistics = new ChannelStatistics();
			channel1Statistics.read(buf);
			channel2Statistics = new ChannelStatistics();
			channel2Statistics.read(buf);
			channel3Statistics = new ChannelStatistics();
			channel3Statistics.read(buf);
			buf.skipWords(192);
			
			channel0Spectrum = readSpectrum(in, k);
			channel1Spectrum = readSpectrum(in, l);
			channel2Spectrum = readSpectrum(in, m);
			channel3Spectrum = readSpectrum(in, n);
		}

		/**
		 * Overloaded tostring method
		 * 
		 * @return output
		 */
		@Override
		public String toString() {
			String output = "";
			output += "Tag Word 0 = " + tagWord0 + "\n";
			output += "Tag Word 1 = " + tagWord1 + "\n";
			output += "Pixel Header Size = " + pixelheaderSize + "\n";
			output += "Mapping Mode = " + mappingMode + "\n";
			output += "Pixel Number = " + pixelNumber + "\n";
			output += "Total Pixel Block Size = " + totalPixelBlockSize + "\n";
			output += "Channel 0 Size = " + channel0Size + "\n";
			output += "Channel 1 Size = " + channel1Size + "\n";
			output += "Channel 2 Size = " + channel2Size + "\n";
			output += "Channel 3 Size = " + channel3Size + "\n";
			output += "(k) = " + k + "\n";
			output += "(l) = " + l + "\n";
			output += "(m) = " + m + "\n";
			output += "(n) = " + n + "\n";
			output += "Channel 0 Statistics = " + channel0Statistics + "\n";
			output += "Channel 1 Statistics = " + channel1Statistics + "\n";
			output += "Channel 2 Statistics = " + channel2Statistics + "\n";
			output += "Channel 3 Statistics = " + channel3Statistics + "\n";
			return output;
		}

	}

	private class ByteBuffer {
		byte[] buffer;
		int pos = 0;
		
		/**
		 * Constructor which specifies the details needed by the ByteBuffer
		 * @param size The amount of data to be read.
		 * @param in The input stream to read from.
		 * @throws IOException If there is any issue with the stream
		 */
		public ByteBuffer(int size, InputStream in) throws IOException {
			buffer = new byte[size*2];
			if ( in.read(buffer) == -1) {
				throw new IOException("End of File");
			}
			pos = 0;			
		}
		
		/**
		 * reads a short from the stream 
		 * @return the read short.
		 */
		public int readShort() {
			int a = ( 0xFF & buffer[pos]);
			pos++;
			int b = ( 0xFF & buffer[pos]);
			pos++;
			return (b << 8) + a;
		}

		/**
		 * reads an int from the stream
		 * @return the read integer
		 */
		public int readInt() {
			int a = ( 0xFF & buffer[pos]);
			pos++;
			int b = ( 0xFF & buffer[pos]);
			pos++;
			int c = ( 0xFF & buffer[pos]);
			pos++;
			int d = ( 0xFF & buffer[pos]);
			pos++;
			return (d << 24) + (c << 16) + (b << 8) + a;
		}

		/**
		 * reads a float from the stream
		 * @return the read Float
		 */
		public float readFloat() {
			return Float.intBitsToFloat(readInt());
			
		}

		/**
		 * Skips a number of words in the stream, as there are several small gaps in the data file.
		 * @param numberOfWordsToSkip The number of words to skip
		 */
		public void skipWords(int numberOfWordsToSkip) {
			pos += numberOfWordsToSkip*2;
		}
	}
	
}
