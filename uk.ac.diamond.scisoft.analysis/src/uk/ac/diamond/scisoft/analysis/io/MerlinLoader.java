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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;

public class MerlinLoader extends AbstractFileLoader {

	private String fileName;
	private static final String DATA_NAME = "MerlinData";
	private static final String U16 = "U16";
//	private static final String U32 = "U32";
	
	public MerlinLoader(String fileName) {
		this.fileName = fileName;
	}
	
	
	private class MetaListHolder {
		
		private ArrayList<Object> dataList = new ArrayList<Object>();
		private String name = "Undefined";
		
		public MetaListHolder(String metaName) {
			name = metaName;
		}

		public void addValue(Object value) {
			dataList.add(value);
		}
		
		public void addToDataHolder(DataHolder holder) {
			if (dataList.get(0) instanceof Number) {
				AbstractDataset data = AbstractDataset.createFromList(dataList);
				holder.addDataset(name, data);
			}
		}
		
	}
	
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		IntegerDataset data = null;
		final DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		BufferedReader br = null;
		ArrayList<AbstractDataset> dataList = new ArrayList<AbstractDataset>();
		
		ArrayList<MetaListHolder> metaHolder = new ArrayList<MetaListHolder>();
		
		try {
			
			
			f = new File(fileName);
			fi = new FileInputStream(f);
			char[] cbuf = new char[54];
			
			
			br = new BufferedReader(new FileReader(f));
			
			br.read(cbuf);
			
			String[] head = new String(cbuf).split(",");
			head.toString();
			
			if (!head[0].equals("MQ1")) {
				throw new ScanFileHolderException("Merlin File should start with MQ1!");
			}
			
			String imageStart = head[2];
			String numberOfChips = head[3];
			String imageX = head[4];
			String imageY = head[5];
			String depth = head[6];
			
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
			metaHolder.add(new MetaListHolder("threshold"));
			metaHolder.add(new MetaListHolder("dacs"));
			metaHolder.add(new MetaListHolder("padding"));
			metaHolder.add(new MetaListHolder("o1"));
			metaHolder.add(new MetaListHolder("o2"));
			metaHolder.add(new MetaListHolder("o3"));
			metaHolder.add(new MetaListHolder("o4"));
			metaHolder.add(new MetaListHolder("o5"));
			
			for(int i = 0; i < Integer.parseInt(numberOfChips); i++) {
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_DACFormat", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold0", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold1", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold2", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold3", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold4", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold5", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold6", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Threshold7", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Preamp", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Ikrum", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Shaper", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Disc", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_DiskLS", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_ShaperTest", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_DACDiskL", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_DACTest", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_DACDISKH", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Delay", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_TPBuffIn", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_TPBuffOut", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_RPZ", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_GND", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_TPRef", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_FBK", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_Cas", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_TPrefA", i)));
				metaHolder.add(new MetaListHolder(String.format("Chip%02d_TPrefB", i)));
			}
			
			char[] cbufRemainder = new char[Integer.parseInt(imageStart)-54];
			
			
			int imageReadStart = Integer.parseInt(imageStart);
			
			while (br.read(cbufRemainder) > 0) {
			
				String fullHeader = new String(cbuf) + new String(cbufRemainder);
				cbuf = new char[0];
				head = fullHeader.split(",");
				
				for(int i = 0; i < metaHolder.size(); i++) {
					Object value = head[i+1];
					try {
						value = Integer.parseInt(head[i+1]);
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						value = Double.parseDouble(head[i+1]);
					} catch (Exception e) {
						// TODO: handle exception
					}
					metaHolder.get(i).addValue(value);
				}
				
				br.skip(Integer.parseInt(imageX)*Integer.parseInt(imageY)*2);
				
				// reset the cbuffer to the full size
				cbufRemainder = new char[Integer.parseInt(imageStart)];
				
				data = new IntegerDataset(new int[]{Integer.parseInt(imageX),Integer.parseInt(imageY)});
	
				if (depth.contains(U16)) {
					Utils.readBeShort(fi, data, imageReadStart,false);
				} else {
					Utils.readBeInt(fi, data, imageReadStart);
				}			
				
//				Number max = data.max(false);
//				Number min = data.min(false);
				
				//imageReadStart += Integer.parseInt(imageX)*Integer.parseInt(imageY)*2 + Integer.parseInt(imageStart) + 100000;
				
				dataList.add(new IntegerDataset(data));
			}
			
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
		
		IntegerDataset ds = new IntegerDataset(dataList.size(), dataList.get(0).getShape()[0], dataList.get(0).getShape()[1]);
		
		for(int i = 0; i < dataList.size(); i++) {
			ds.setSlice(dataList.get(i), new int[] {i,0,0}, new int[] {i+1,dataList.get(0).getShape()[0], dataList.get(0).getShape()[1]}, new int[] {1,1,1});
		}
		output.addDataset(DATA_NAME, ds.squeeze());
		for(int i = 0; i < metaHolder.size(); i++) {
			metaHolder.get(i).addToDataHolder(output);
		}

		return output;

	}

}
