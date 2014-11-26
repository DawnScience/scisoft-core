/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.ShortDataset;

/**
 * Loader to allow the XMap files to be loaded in
 */
public class XMapLoader extends AbstractFileLoader {

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

	@Override
	protected void clearMetadata() {
	}
	
	/**
	 * Implemented method which actually loads in the data. Using the DXD-xMAP : Mapping buffer Specification
	 * 
	 * @return the loaded data
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {		
		ZipFile zipFile = null;
		DataHolder ldh = new DataHolder();
		ILazyDataset[] data = new ILazyDataset[CHANNELS];
		int[] size = new int[CHANNELS];
		int[] count = new int[CHANNELS];

		try {
			zipFile = new ZipFile(fileName);
			for (Enumeration<?> e = zipFile.entries(); e.hasMoreElements();) {

				InputStream in;

				try {
					in = zipFile.getInputStream((ZipEntry) e.nextElement());
				} catch (IOException e1) {
					throw new ScanFileHolderException("Zip entry not valid", e1);
				}

				boolean finished = false;

				BufferData bufferedData = new BufferData();
				try {
					bufferedData.read(in);
				} catch (IOException e2) {
					throw new ScanFileHolderException("The main header was not read correctly", e2);
				}

				while (!finished) {

					if (bufferedData.mappingMode == 1) {

						// i think there are a number of scans at this point
						for (int i = 0; i < bufferedData.numberOfPixelsInBuffer; i++) {

							// we are running in mapping mode and should therefore read in the data in this way
							MappingMode1Data mappingMode1Data = new MappingMode1Data();
							try {
								mappingMode1Data.read(in, !loadLazily);
							} catch (IOException e2) {
								throw new ScanFileHolderException("The zip file was not opened correctly", e2);
							}
							// System.out.println(mappingMode1Data);

							for (int c = 0; c < CHANNELS; c++) {
								if (mappingMode1Data.channelSize[c] > 0) {
									size[c] = mappingMode1Data.channelSize[c];
									if (!loadLazily) {
										ShortDataset temp = new ShortDataset(mappingMode1Data.channelSpectrum[c], 1,
												mappingMode1Data.channelSize[c]);
										if (data[c] == null) {
											// create the dataset
											data[c] = temp;
										} else {
											data[c] = DatasetUtils.append((IDataset) data[c], temp, 0);
										}
									}
									count[c]++;
								}
							}
						}
					}

					try {
						bufferedData.read(in);
					} catch (IOException e2) {
						finished = true;
					}
//					System.out.println(bufferedData);

				}
			}

			if (loadLazily) {
				for (int c = 0; c < CHANNELS; c++) {
					data[c] = createLazyDataset("Channel" + c, Dataset.INT16, new int[] { count[c], size[c] },
							new XMapLoader(fileName));
				}
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("The zip file was not opened correctly", e);
		} finally {
			try {
				if (zipFile != null)
					zipFile.close();
			} catch (IOException e) {
				throw new ScanFileHolderException("The zip file could not be closed correctly", e);
			}
		}

		for (int c = 0; c < CHANNELS; c++) {
			ldh.addDataset("Channel" + c, data[c]);
		}
		if (loadMetadata) {
			metadata = new Metadata();
			metadata.setFilePath(fileName);
			for (int c = 0; c < CHANNELS; c++) {
				metadata.addDataInfo("Channel" + c, data[c].getShape());
			}
		}

		return ldh;
	}

	final static int CHANNELS = 4;
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
		int[] detectorChannel = new int[CHANNELS];
		int[] detectorElementChannel = new int[CHANNELS];
		int[] channelSize = new int[CHANNELS];
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
			for (int c = 0; c < CHANNELS; c++) {
				detectorChannel[c] = buf.readShort();
				detectorElementChannel[c] = buf.readShort();
			}
			for (int c = 0; c < CHANNELS; c++) {
				channelSize[c] = buf.readShort();
			}
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
			for (int c = 0; c < CHANNELS; c++) {
				output += "detectorChannel " + c + ": " + detectorChannel[c] + "\n";
				output += "detectorElementChannel " + c + ": " + detectorElementChannel[c] + "\n";
			}
			for (int c = 0; c < CHANNELS; c++) {
				output += "Channel " + c + " Size = " + channelSize[c] + "\n";
			}
			output += ("bufferErrors " + bufferErrors + "\n");
			return output;
		}

	}

	private class ChannelStatistics {
		float realtime;
		float livetime;
		int triggers;
		int outputEvents;

		void read(ByteBuffer buf) { // 16 bytes
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
		int[] channelSize = new int[CHANNELS];
		ChannelStatistics[] channelStatistics = new ChannelStatistics[CHANNELS];
		short[][] channelSpectrum = new short[CHANNELS][];

		short[] readSpectrum(InputStream in, int sizeOfSpectrum) throws IOException {
			short[] spectrum = new short[sizeOfSpectrum];
			ByteBuffer buf = new ByteBuffer(sizeOfSpectrum, in);
			for (int i = 0; i < sizeOfSpectrum; i++) {
				spectrum[i] = (short) buf.readShort();
			}
			return spectrum;
		}

		void read(InputStream in, boolean includeData) throws IOException {
			
			ByteBuffer buf = new ByteBuffer(256, in);
			
			tagWord0 = buf.readShort();
			tagWord1 = buf.readShort();
			pixelheaderSize = buf.readShort();
			mappingMode = buf.readShort();
			pixelNumber = buf.readInt();
			totalPixelBlockSize = buf.readInt();
			for (int c = 0; c < CHANNELS; c++) {
				channelSize[c] = buf.readShort();
			}

			if (includeData) {
				buf.skipWords(20);
	
				for (int c = 0; c < CHANNELS; c++) {
					channelStatistics[c] = new ChannelStatistics();
					channelStatistics[c].read(buf);
				}
				buf.skipWords(192);
				
				for (int c = 0; c < CHANNELS; c++) {
					channelSpectrum[c] = readSpectrum(in, channelSize[c]);
				}
			} else {
				// 4x2 + 2x4 + 4x2 + 20x2 + 4x16 + 192x2 = 512 bytes already read in 
				// (channel0Size + ...) x 2
				in.skip((channelSize[0] + channelSize[1] + channelSize[2] + channelSize[3]) * 2);
			}
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
			for (int c = 0; c < CHANNELS; c++) {
				output += "Channel " + c + " Size = " + channelSize[c] + "\n";
			}
			for (int c = 0; c < CHANNELS; c++) {
				output += "Channel " + c + " Statistics = " + channelStatistics[c] + "\n";
			}
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
