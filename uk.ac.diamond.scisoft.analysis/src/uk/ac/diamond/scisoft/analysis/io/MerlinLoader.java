/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerlinLoader extends AbstractFileLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(MerlinLoader.class);

	private static final String DATA_NAME = "MerlinData";
	
	public MerlinLoader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	protected void clearMetadata() {
	}

	private class MetaListHolder {
		
		private List<Serializable> dataList = new ArrayList<Serializable>();
		private String name = "Undefined";
		
		public MetaListHolder(String metaName) {
			name = metaName;
		}

		public void addValue(Serializable value) {
			dataList.add(value);
		}

		public List<Serializable> getValue() {
			return dataList;
		}

		public IDataset getDataset() {
			IDataset dataset = DatasetFactory.createFromList(getValue());
			dataset.setName(name);
			return dataset;
		}
	}
	
	private static int INITIAL_LENGTH = 40;
	private int droppedFrames = 0;
	private int[] mapShape = null;
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		final DataHolder output = new DataHolder();
		// if a metadata file exisits, load it
		String metaFilename = fileName+"meta";
		try {
			if (new File(metaFilename).exists()){
				byte[] encoded = Files.readAllBytes(Paths.get(metaFilename));
				String metadata = new String(encoded, StandardCharsets.UTF_8);
				
				for (String part : metadata.split("\n")) {
					if(part.split(":")[0].equals("DroppedFrames")){
						this.droppedFrames = Integer.parseInt(part.split(":")[1].trim());
					} 
					if(part.split(":")[0].equals("Shape")){
						ArrayList<Integer> values = new ArrayList<Integer>();
						for (String part2 : part.split(":")[1].split(",")) {
							values.add(Integer.parseInt(part2.trim()));
						}
						this.mapShape = ArrayUtils.toPrimitive(values.toArray(new Integer[0]), 0);
					} 
				} 
			}
		} catch (IOException e) {
			logger.error("A metadata file exisited for the Merlin data file, but did not load correctly", e);
		}
		
		File f = null;
		BufferedReader br = null;
		List<Long> offsetList = new ArrayList<Long>();
		LazyDataset lazy = null;
		
		List<MetaListHolder> metaHolder = new ArrayList<MetaListHolder>();
		int x;
		int y;
		int dtype;
		try {
			f = new File(fileName);
			char[] cbuf = new char[INITIAL_LENGTH];

			br = new BufferedReader(new FileReader(f));
			br.read(cbuf);
			String[] head = new String(cbuf).split(",");

			if (!head[0].equals("MQ1")) {
				throw new ScanFileHolderException("Merlin File must start with MQ1!");
			}
			
			// missing frame number head[1]
			int numberOfChips = Integer.parseInt(head[3]);
			x = Integer.parseInt(head[4]);
			y = Integer.parseInt(head[5]);

			// build the arrays to hold the data
			metaHolder.add(new MetaListHolder("acquisitionSequenceNumber"));
			metaHolder.add(new MetaListHolder("dataOffset"));
			metaHolder.add(new MetaListHolder("numberOfChips"));
			metaHolder.add(new MetaListHolder("pixelDimensionX"));
			metaHolder.add(new MetaListHolder("pixelDimensionY"));
			metaHolder.add(new MetaListHolder("pixelDepth"));
			metaHolder.add(new MetaListHolder("sensorLayout"));
			metaHolder.add(new MetaListHolder("chipSelect"));
			metaHolder.add(new MetaListHolder("timeStamp"));
			metaHolder.add(new MetaListHolder("acquisitionShutterTime"));
			metaHolder.add(new MetaListHolder("counter"));
			metaHolder.add(new MetaListHolder("colourMode"));
			metaHolder.add(new MetaListHolder("gainMode"));
			metaHolder.add(new MetaListHolder("TH0"));
			metaHolder.add(new MetaListHolder("TH1"));
			metaHolder.add(new MetaListHolder("TH2"));
			metaHolder.add(new MetaListHolder("TH3"));
			metaHolder.add(new MetaListHolder("TH4"));
			metaHolder.add(new MetaListHolder("TH5"));
			metaHolder.add(new MetaListHolder("TH6"));
			metaHolder.add(new MetaListHolder("TH7"));

			for(int i = 0; i < numberOfChips; i++) {
				String chip = String.format("Chip%02d_", i);
				metaHolder.add(new MetaListHolder(chip + "DACFormat"));
				metaHolder.add(new MetaListHolder(chip + "Threshold0"));
				metaHolder.add(new MetaListHolder(chip + "Threshold1"));
				metaHolder.add(new MetaListHolder(chip + "Threshold2"));
				metaHolder.add(new MetaListHolder(chip + "Threshold3"));
				metaHolder.add(new MetaListHolder(chip + "Threshold4"));
				metaHolder.add(new MetaListHolder(chip + "Threshold5"));
				metaHolder.add(new MetaListHolder(chip + "Threshold6"));
				metaHolder.add(new MetaListHolder(chip + "Threshold7"));
				metaHolder.add(new MetaListHolder(chip + "Preamp"));
				metaHolder.add(new MetaListHolder(chip + "Ikrum"));
				metaHolder.add(new MetaListHolder(chip + "Shaper"));
				metaHolder.add(new MetaListHolder(chip + "Disc"));
				metaHolder.add(new MetaListHolder(chip + "DiscLS"));
				metaHolder.add(new MetaListHolder(chip + "ShaperTest"));
				metaHolder.add(new MetaListHolder(chip + "DACDiscL"));
				metaHolder.add(new MetaListHolder(chip + "DACTest"));
				metaHolder.add(new MetaListHolder(chip + "DACDiscH"));
				metaHolder.add(new MetaListHolder(chip + "Delay"));
				metaHolder.add(new MetaListHolder(chip + "TPBuffIn"));
				metaHolder.add(new MetaListHolder(chip + "TPBuffOut"));
				metaHolder.add(new MetaListHolder(chip + "RPZ"));
				metaHolder.add(new MetaListHolder(chip + "GND"));
				metaHolder.add(new MetaListHolder(chip + "TPRef"));
				metaHolder.add(new MetaListHolder(chip + "FBK"));
				metaHolder.add(new MetaListHolder(chip + "Cas"));
				metaHolder.add(new MetaListHolder(chip + "TPRefA"));
				metaHolder.add(new MetaListHolder(chip + "TPRefB"));
			}

			char[] cbufRemainder;
			
			long imageReadStart = 0;
			do {
				head = new String(cbuf).split(",");

				if (!head[0].equals("MQ1")) {
					throw new ScanFileHolderException("Merlin file must start with MQ1!");
				}
				// missing frame number head[1]
				int headerLength = Integer.parseInt(head[2]);
				if (x != Integer.parseInt(head[4]) || y != Integer.parseInt(head[5])) {
					throw new ScanFileHolderException("Size of image has changed!");
				}
				int itemSize = 0;
				switch (head[6]) { // TODO support other formats like U01, U64
				case "U08":
					itemSize = 1;
					dtype = Dataset.INT16;
					break;
				case "U16":
					itemSize = 2;
					dtype = Dataset.INT32;
					break;
				case "U32":
					itemSize = 4;
					dtype = Dataset.INT32;
					break;
				default:
					throw new ScanFileHolderException("Binary number format not supported");
				}
				long imageLength = x * y * itemSize;

				cbufRemainder = new char[headerLength - INITIAL_LENGTH];
				br.read(cbufRemainder);

				String fullHeader = new String(cbuf) + new String(cbufRemainder);
				parseHeaders(fullHeader.split(","), metaHolder);
				br.skip(imageLength);

				imageReadStart += headerLength;

				offsetList.add(imageReadStart);
				
				imageReadStart += imageLength;

			} while (br.read(cbuf) > 0);

			
			if (this.mapShape == null) {
				this.mapShape = new int[] {offsetList.size()};
			}
			
			int[] frameShape = new int[] {y, x};
			int[] shape = ArrayUtils.addAll(this.mapShape, frameShape);
			lazy = createLazyDataset(DATA_NAME, dtype, shape,
					new MerlinFrameLazyDataset(f, offsetList.toArray(new Long[0]), dtype, frameShape, this.droppedFrames, this.mapShape));

		} catch (Exception e) {
			throw new ScanFileHolderException("File failed to load " + fileName, e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}

		IMetadata meta = new Metadata();
		
		if (loadMetadata) {
			processMetadata(metaHolder, output, meta, this.droppedFrames, this.mapShape);
		}
		
		// Adds some simple Axis to get the mapping functionality working
		if ((this.mapShape != null) && (this.mapShape.length < 0)) { 
			Dataset xdataset = DatasetFactory.createRange(IntegerDataset.class, this.mapShape[0]);
			xdataset.setName("xAxis");
			output.addDataset(xdataset.getName(), xdataset);
			meta.addDataInfo(xdataset.getName(), xdataset.getShape());
		}
		
		if ((this.mapShape != null) && (this.mapShape.length < 1)) { 
			Dataset ydataset = DatasetFactory.createRange(IntegerDataset.class, this.mapShape[1]);
			ydataset.setName("yAxis");
			output.addDataset(ydataset.getName(), ydataset);
			meta.addDataInfo(ydataset.getName(), ydataset.getShape());
		}
		
		if (offsetList.size() > 1) {
			// add the lazy dataset			
			lazy.setName(DATA_NAME);
			output.addDataset(DATA_NAME, lazy);
			meta.addDataInfo(DATA_NAME, lazy.getShape());
		} else {
			// Actually load the only frame into memory.
			try {
				IDataset dataset = lazy.getSlice(null, null, null);
				dataset.squeeze();
				dataset.setName(DATA_NAME);
				output.addDataset(DATA_NAME, dataset);
				meta.addDataInfo(DATA_NAME, dataset.getShape());
			} catch (DatasetException e) {
				throw new ScanFileHolderException("Unable to load frame of data from file", e);
			}
		}

		output.setMetadata(meta);
		metadata = meta;
		return output;
	}

	private void parseHeaders(String[] headers, List<MetaListHolder> metaHolder) {
		for(int i = 0, imax = Math.min(headers.length-1, metaHolder.size()); i < imax; i++) {
			String h = headers[i+1];
			Number value = Utils.parseValue(h);
			metaHolder.get(i).addValue(value == null ? h : value);
		}
	}

	private void processMetadata(List<MetaListHolder> metaHolder, DataHolder output, IMetadata meta, int droppedFrames, int[] mapShape) {
		for (MetaListHolder h : metaHolder) {
			IDataset dataset = h.getDataset();
			int length = 1;
			for (int i = 0; i < mapShape.length; i++) {
				length *= mapShape[i];
			}
			dataset = dataset.getSlice(new Slice(droppedFrames, droppedFrames+length));
			dataset.setShape(mapShape);
			output.addDataset(h.name, dataset);
			meta.addDataInfo(h.name, dataset.getShape());
		}
	}

	class MerlinFrameLazyDataset extends LazyLoaderStub {
		
		private File file;
		private Long[] frameOffsets;
		private int dtype;
		private int[] frameShape;
		private int droppedFramesInt;
		private int[] mapShapeInt;
		
		public MerlinFrameLazyDataset(File file, Long[] frameOffsets, int dtype, int[] frameShape, int droppedFrames, int[] mapShape) {
			this.file = file;
			this.frameOffsets = frameOffsets;
			this.dtype = dtype;
			this.frameShape = frameShape;
			this.droppedFramesInt = droppedFrames;
			this.mapShapeInt = mapShape;
		}
		
		@Override
		public IDataset getDataset(IMonitor mon, SliceND slice) throws IOException {
			Dataset loaded = null;
			Dataset temp = null;
			Class<? extends Dataset> clazz = null;
			switch (dtype) {
			case Dataset.INT16:
				clazz = ShortDataset.class;
				break;
			case Dataset.INT32:
			case Dataset.INT64:
				clazz = IntegerDataset.class;
				break;
			}
			
			loaded = DatasetFactory.zeros(clazz, slice.getShape());
			temp = DatasetFactory.zeros(clazz, frameShape);
			
			LongDataset lookup = DatasetFactory.createRange(LongDataset.class, (long) DatasetFactory.createFromObject(mapShapeInt).product(true));
			lookup.iadd(droppedFramesInt);
			lookup.setShape(mapShapeInt);
			
			Slice[] mapSlice = Arrays.copyOf(slice.convertToSlice(), slice.convertToSlice().length-2);
			lookup = (LongDataset) lookup.getSliceView(mapSlice);				
			
			IndexIterator iter = lookup.getPositionIterator(null);
			
			int[] pos = iter.getPos();
			while(iter.hasNext()){
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					long position = frameOffsets[(int) lookup.get(pos)];
					switch (dtype) {
					case Dataset.INT16:
						Utils.readByte(fis, (ShortDataset) temp, position);
						break;
					case Dataset.INT32:
						Utils.readBeShort(fis, (IntegerDataset) temp, position, false);			
						break;
					case Dataset.INT64:
						Utils.readBeInt(fis, (IntegerDataset) temp, position);
						break;
					}
				} catch (Exception e) {
					throw new IOException(e);				
				} finally {
					if (fis != null) {
						fis.close();
					}
					fis = null;
				}

				Slice[] readSlice = Arrays.copyOfRange(slice.convertToSlice(), frameShape.length, frameShape.length+2);
				IDataset sliced = temp.getSlice(readSlice);
				SliceND setSlice = slice.clone();
				for (int i = 0; i < iter.getPos().length; i ++) {
					setSlice.setSlice(i, pos[i],  pos[i]+1 , 1);
				}
				loaded.setSlice(sliced, setSlice);
			}
			return loaded == null ? null : loaded;
		}
	}
}
