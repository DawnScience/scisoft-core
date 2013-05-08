/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;


public class ASCIIDataWithHeadingSaver extends RawTextSaver {

	private List<String> headings;
	private String header;

	/**
	 * Takes the dataset from a data holder and output them as a height x width array called 'filename'.txt.
	 * If there are multiple datasets in a ScanFileHolder then the class will save each in a separate file.
	 * 
	 * @param filename
	 */
	public ASCIIDataWithHeadingSaver(String filename) {
		super(filename);
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	public void setHeadings(List<String> headings) {
		this.headings = headings;
	}
	
	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}
	
	@Override
	protected void writeHeader(BufferedWriter writer) throws IOException {
		if (header != null) {
			writer.write(header);
			writer.newLine();
		}
		
		if (headings != null) {
			for (String heading: headings) {
				writer.write(heading);
				writer.write(delimiter);
			}
			
			writer.newLine();
		}
	}
}
