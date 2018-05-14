/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Read an XRMC input file, and report the available files, the device to run and the device to save.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class XRMCInputReader {

	private String filePath;
	private File inputFile;
	private List<String> fileLines;
	private List<String> loadFiles;
	private String runLine;
	private String saveLine;
	private String runDevice;
	private SaveInfo saveInfo;
	
	private static Pattern loadLineR = null;
	private static Pattern loadR = null;
	private static Pattern saveLineR = null;
	private static Pattern saveR = null;
	private static Pattern runLineR = null;
	private static Pattern runR = null;
	private static Pattern blankR = null;
	private static Pattern unescapedSpaceR = null;
	
	public XRMCInputReader(String fileName) {
		// create the File object for the input file.
		inputFile = new File(fileName);
		// the parent directory of the file
		filePath = inputFile.getParent();
		// All the actual reading is done elsewhere

		createRegex();
	}
	
	public XRMCInputReader(List<String> fileLines, String basePath) {
		this.fileLines = new ArrayList<>(fileLines);
		filePath = basePath;
		
		createRegex();
	}
	
	public void read() throws IOException {
		// Get all the files to be loaded from this list.
		if (fileLines == null && inputFile != null)
			fileLines = Files.readAllLines(inputFile.toPath());
		readLines();
		assignRunDevice();
		assignSaveInfo();
	}
	
	public List<String> getFiles() {
		return loadFiles;
	}
	
	public String getSaveLine() {
		return saveLine;
	}
	
	public SaveInfo getSaveInfo() {
		return saveInfo;
	}
	
	public String getRunLine() {
		return runLine;
	}
	
	public String getRunDevice() {
		return runDevice;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	private void readLines() {
		// Filter out comments
		List<String> commentlessLines = fileLines.stream().map(line -> (line.equals(";")) ? "" : line.split(";")[0]).collect(Collectors.toList());
		// Remove blank lines
		commentlessLines.removeIf(s -> blankR.matcher(s).matches());
		// All the lines that 'Load' a file
		List<String> loadLines = commentlessLines.stream().map(line -> loadLineR.matcher(line).matches() ? line : "").collect(Collectors.toList());
		// Replace the first part of the line with 
		loadFiles = loadLines.stream().map(line -> loadR.matcher(line).replaceFirst("").trim()).collect(Collectors.toList());
		loadFiles.removeIf(s -> blankR.matcher(s).matches());
		// Get the first lines that match run and save commands
		runLine = commentlessLines.stream().filter(line -> runLineR.matcher(line).matches()).collect(Collectors.toList()).get(0);
		// Get the first lines that match run and save commands
		saveLine = commentlessLines.stream().filter(line -> saveLineR.matcher(line).matches()).collect(Collectors.toList()).get(0);
	}
	
	private void assignRunDevice() {
		runDevice = runR.matcher(runLine).replaceFirst("").trim();
	}
	
	private void assignSaveInfo() {
		saveInfo = new SaveInfo(saveLine);
	}

	// Regex patterns
	private static final String LOADREGEX = "\\s*Load\\s+";
	private static final String RUNREGEX = "\\s*Run\\s+";
	private static final String SAVEREGEX = "\\s*Save\\s+";
	private static final String BLANKREGEX = "\\s*";
	private static final String UNESCAPEDSPACEREGEX = "(?<!\\\\)\\s+";
	
	private static void createRegex() {
		// Any amount of whitespace, followed by 'Load', followed by whitespace 
		loadR = createNonNullRegex(loadR, LOADREGEX);
		// Any amount of whitespace, followed by 'Load', followed by whitespace and then any characters 
		loadLineR = createNonNullRegexTraillingText(loadLineR, LOADREGEX);
		// Any amount of whitespace, followed by 'Run', followed by whitespace 
		runR = createNonNullRegex(runR, RUNREGEX);
		// Any amount of whitespace, followed by 'Run', followed by some whitespace and then any characters 
		runLineR = createNonNullRegexTraillingText(runLineR, RUNREGEX);
		// Any amount of whitespace, followed by 'Save', followed by whitespace 
		saveR = createNonNullRegex(saveR, SAVEREGEX);
		// Any amount of whitespace, followed by 'Load', followed by some whitespace and then any characters 
		saveLineR = createNonNullRegexTraillingText(saveLineR, SAVEREGEX);
		// Any amount of whitespace only
		blankR = createNonNullRegex(blankR, BLANKREGEX);
		// Any amount of whitespace, except where it is preceded by an escaping slash
		unescapedSpaceR = createNonNullRegex(unescapedSpaceR, UNESCAPEDSPACEREGEX);
	}
	
	private static Pattern createNonNullRegex(Pattern pattern, String regex) {
		if (pattern == null) {
			return Pattern.compile(regex);
		} else {
			return pattern;
		}
	}
	
	// Allow extra text on the end of the regex
	private static Pattern createNonNullRegexTraillingText(Pattern pattern, String regex) {
		return createNonNullRegex(pattern, regex+".*");
	}

	public static void main(String[] args) {

		String commonLine = "Save Detectorarray Image ";
		String[] filenames = new String[] {
				"/file/without/spaces",
				"/file/with/unescaped space",
				"/file/with/escaped\\ space",
				"/file/with/escaped/and/unescaped\\  space",
				"    /file/name/preceded/by/many/spaces"
		};
		
		for (String filename : filenames) {
			System.out.println(Arrays.toString((new XRMCInputReader.SaveInfo(commonLine + filename)).getParameters()));
		}
		
		
		XRMCInputReader reader = new XRMCInputReader("/home/rkl37156/xrmc/experiments/BTO/input.dat");
		try {
			reader.read();
		} catch (IOException ioe) {
			System.err.println("Oops!");
		}
	}

	/**
	 * POD class for the XRMC save context.
	 */
	public static class SaveInfo {
		private String devicename;
		private String dataname;
		private String filename;
		
		private SaveInfo() {
		}
	
		public SaveInfo(String saveLine) {
			XRMCInputReader.createRegex();
			
			String saveParameters = saveR.matcher(saveLine).replaceFirst("").trim();
			// split on unescaped spaces
			String[] splitParameters = unescapedSpaceR.split(saveParameters);
			devicename = splitParameters[0];
			dataname = splitParameters[1];
			filename = splitParameters[2];
		}
		public String[] getParameters() {
			return new String[] {devicename, dataname, filename};
		}
		public String getDevicename() {
			return devicename;
		}
		public String getDataname() {
			return dataname;
		}
		public String getFilename() {
			return filename;
		}
	}
	
}
