/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Tekevwe Kwakpovwe
 * A class for generating the autogudrun text file
 * using the attributes of objects made from the other classes in the gudrun package.
 */
public class AppendGudrun {
	
	private static final Logger LOGGER = Logger.getLogger( AppendGudrun.class.getName());
	
	private Instrument instrument;
	private Sample[] samples;
	private Beam beam;
	private SampleBackground background;
	private Normalisation normalisation;
	
	public AppendGudrun (Instrument ins, Sample[] sam, Beam bea,
			           SampleBackground saB, Normalisation nor) {
		
		this.instrument = ins;
		this.samples = sam;
		this.beam = bea;
		this.background = saB;
		this.normalisation = nor;
	}
	/**
	 * empty constructor 
	 */
	public AppendGudrun() {
	}
	
	/**
	 * Creates an array of strings for each line that is to be written to the autogudrun text file
	 * This is done so we can compare this potential output to what is currently stored on the file. 
	 * @return A String array with each line of the sample background of the file being processed 
	 */
	public String[] bckgrCompEntry(String[] theOther) {
		ArrayList<String> theEntry = new ArrayList<String>();
		String[] fileNames = this.background.getFileNames();
		theEntry.add("SAMPLE BACKGROUND          {\n\n");
		theEntry.add("");
        theEntry.add(String.format("%s            Number of  files\n", (fileNames.length)));
        for (String filename : fileNames) {
            theEntry.add(String.format("%s          SAMPLE BACKGROUND data files\n", filename));
        }
        theEntry.add(String.format("%s          Sample background factor \n", this.background.getBckgrFac()));
        for (String filename : fileNames) {
            theEntry.add(String.format("%s          Data factor\n", this.background.getDataFactor()));
        }
        for (String filename : fileNames) {
            theEntry.add("0          Exclude scans\n");
        }
        theEntry.add("\n}");
        
        String[] entryOut = theEntry.toArray(new String[theEntry.size()]);
		return entryOut;
	}
	
	/**
	 * Determines whether the sample information that is about to be written to the autogudrun text file
	 * Is already present on the text file.
	 * @param oldEntries: The lines of what's currently on the text file.
	 * @param newEntry: The lines of what can be potentially written on the autogudrun text file.
	 * @return
	 */
	public static boolean sameSample(String[] oldEntries, String[] newEntry) {
		int sameCheck = 0;
		for (int i = 0; i < newEntry.length; i++) {
			//This is the line that says what the data factor is
			if (oldEntries[i].contains(newEntry[i])) {
				sameCheck += 1;
			}
			else {
				sameCheck = 0;
			}
			//This is the line that says what the data file is
			if (sameCheck >= 6) {
				LOGGER.log(Level.INFO, "The same background was found...");
				return true;
			}
		}
		//If there are more than two matches, this is enough to conclude that this is a duplicate
		//There should not be more than 1 duplicate so we only need to witness 2 matches.
		return false;
	}
	
	/**
	 * A function for segmenting the file array into the various subsections, i.e. INSTRUMENT, BEAM, SAMPLE BACKGROUND, SAMPLE
	 * 
	 * 
	 * @param fileArr: the array with each element being a line from the file as a string.
	 * @param start: the point on the file/fileArr that we begin looking for a segment from.
	 * @param marker: the string that will indicate the end of the segment.
	 * @return an array with the lines that make up the segment.
	 */
	private String[] segmentFile(String[] fileArr, int start, String marker) {
		ArrayList<String> segment = new ArrayList<String>();
		int endPoint = 0;
		for (int i = start; i < fileArr.length; i ++) {
			if (fileArr[i].contains(marker)) {
				endPoint = i;
				segment.add(Integer.toString(endPoint));
				break;
			}
			else {
				segment.add(fileArr[i] + "\n");
			}
		}
        return segment.toArray(new String[segment.size()]);
	}
	
	
	/**
	 * Creates the autgudrun text file
	 * @param outFileName: the path of the file
	 * @throws FileNotFoundException
	 */
	public void generate(String outFileName) throws FileNotFoundException {
		ArrayList<String> fileList = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(outFileName))){
					String currentLine;
					while((currentLine = br.readLine()) != null) {
						fileList.add(currentLine);
					}
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, "I/O exception, does this file exist?", e);
				}
		File outFileDirec = new File(outFileName);
		instrument.setDataFileDirectory(outFileDirec.getParentFile());
		instrument.setInDirectory(outFileDirec.getParentFile());
		String[] fileArr = fileList.toArray(new String[fileList.size()]);
		String[] instSection = segmentFile(fileArr, 0, "BEAM");
		String[] beamSection = segmentFile(fileArr, Integer.parseInt(instSection[instSection.length - 1]), "NORMALISATION");
		String[] normalSection = segmentFile(fileArr, Integer.parseInt(beamSection[beamSection.length - 1]), "SAMPLE BACKGROUND");
		String[] sBackgrSection = segmentFile(fileArr, Integer.parseInt(normalSection[normalSection.length - 1]), "}");
		String[] sampleSection = segmentFile(fileArr, Integer.parseInt(sBackgrSection[sBackgrSection.length - 1]) + 1, "END");
	
        LOGGER.log(Level.INFO, "Creating Gudrun input file...");
        StringBuilder entry = new StringBuilder();
        entry.append("'  '  '          '  '\\'\n\n");
        entry.append(this.instrument.generate());
        entry.append("\n\n");
        entry.append(this.beam.generate());
        entry.append("\n\n");
        entry.append(this.normalisation.generate());
        entry.append("\n\n");
        String[] bckgrEntry = bckgrCompEntry(sBackgrSection);
        if (!(sameSample(sBackgrSection, bckgrEntry))) {
        	for (String line : sBackgrSection) {
        		entry.append(line);
        	}
        } else {
        	entry.append(this.background.generate());
        }
        entry.append("\n");
        for (String line : Arrays.copyOf(sampleSection, sampleSection.length - 1)) {
        	entry.append(line);
        }
        
        for (Sample sample : this.samples) {
            entry.append(sample.generate());
            entry.append("\n\n");
        }
    
        entry.append("\nEND\n1\n");
        String dateTimeString = (LocalDateTime.now()).toString();
        dateTimeString = dateTimeString.replace("-"," ");
        
        entry.append("Date and time last written:  " + dateTimeString.split("\\.")[0] + "          \n");
        entry.append("X");
        
        try {
            PrintWriter outFile = new PrintWriter(outFileName);
            outFile.write(entry.toString());
            outFile.close();
        } catch (IOException x) {
        	LOGGER.log(Level.SEVERE, "I/O exception: could not write to file", x);
        } finally {
            LOGGER.log(Level.INFO, outFileName + "was written to!");
        }
	}
}
