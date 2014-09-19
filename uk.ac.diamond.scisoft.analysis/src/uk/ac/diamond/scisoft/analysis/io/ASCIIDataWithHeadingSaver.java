/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	
	public void setCellFormat(String cellFormat) {
		this.cellFormat = cellFormat;
	}
	
	@Override
	protected void writeHeader(BufferedWriter writer) throws IOException {
		if (header != null) {
			writer.write(header);
			writer.newLine();
		}
		
		if (headings != null) {
			writer.write('#');
			for (String heading: headings) {
				writer.write(heading);
				writer.write(delimiter);
			}
			
			writer.newLine();
		}
	}
}
