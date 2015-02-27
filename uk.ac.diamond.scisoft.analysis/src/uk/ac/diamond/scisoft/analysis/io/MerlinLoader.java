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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.ShortDataset;

public class MerlinLoader extends AbstractFileLoader {

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
	}
	
	private static int INITIAL_LENGTH = 40;

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		Dataset data = null;
		final DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		BufferedReader br = null;
		List<Dataset> dataList = new ArrayList<Dataset>();
		
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

				if (loadLazily) {
					dataList.add(null);
				} else {
					fi = new FileInputStream(f);
					try {
						data = DatasetFactory.zeros(new int[] {x, y}, dtype);
						switch (itemSize) {
						case 1:
							Utils.readByte(fi, (ShortDataset) data, imageReadStart);
							break;
						case 2:
							Utils.readBeShort(fi, (IntegerDataset) data, imageReadStart, false);
							break;
						case 4:
							Utils.readBeInt(fi, (IntegerDataset) data, imageReadStart);
							break;
						}
					} finally {
						fi.close();
						fi = null;
					}
					imageReadStart += imageLength;
					dataList.add(data);
				}
			} while (br.read(cbuf) > 0);
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
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException ex) {
					// do nothing
				}
				fi = null;
			}
		}

		ILazyDataset ds;
		int[] shape = dataList.size() > 1 ? new int[] {dataList.size(), y, x} : new int[] {y, x};
		if (loadLazily) {
			ds = createLazyDataset(DATA_NAME, dtype, shape, new MerlinLoader(fileName));
		} else if (shape.length == 3) {
			ds = DatasetFactory.zeros(shape, dtype);

			int[] start = new int[3];
			int[] stop  = shape.clone();
			int[] step  = new int[] {1,1,1};
			for(int i = 0; i < shape[0]; i++) {
				start[0] = i;
				stop[0] = i + 1;
				((Dataset) ds).setSlice(dataList.get(i), start, stop, step);
			}
		} else {
			ds = dataList.get(0);
		}
		output.addDataset(DATA_NAME, ds.squeezeEnds());
		if (loadMetadata) {
			createMetadata(metaHolder);
			output.setMetadata(metadata);
		}

		return output;
	}

	private void parseHeaders(String[] headers, List<MetaListHolder> metaHolder) {
		for(int i = 0, imax = Math.min(headers.length-1, metaHolder.size()); i < imax; i++) {
			String h = headers[i+1];
			Number value = Utils.parseValue(h);
			metaHolder.get(i).addValue(value == null ? h : value);
		}
	}

	private void createMetadata(List<MetaListHolder> metaHolder) {
		metadata = new Metadata();
		Map<String, Serializable> map = new HashMap<>();
		for (MetaListHolder h : metaHolder) {
			map.put(h.name, (Serializable) h.getValue());
		}
		metadata.setMetadata(map);
	}
}
