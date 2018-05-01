package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads a general XRMC .dat input file into a List<Map.Entry<String, String>> for later parsing
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class XRMCDatReader {

	List<NameValue> data;
	
	public XRMCDatReader(String filename) {
		try {
			data = read(filename);
		} catch (IOException ioe) {
			System.err.println("Error loading data from " + filename + ": " + ioe.toString());
			data = null;
		}
	}
	
	public XRMCDatReader(String[] lines) {
		data = read(lines);
	}
	
	/**
	 * Returns whether a given XRMC input file contains the requested key.
	 * @param key
	 * 			the key to be found.
	 * @return true if the key exists, false if it does not.
	 */
	public boolean hasKey(String key) {
		for (NameValue nv : data)
			if (key.equals(nv.getName()))
				return true;
		return false;
	}
	
	/**
	 * Returns the value associated with the requested key.
	 * <p>
	 * Returns the line keyed by the requested value, missing the key itself.
	 * @param key
	 * 			the key for which the data is to be returned.
	 * @return the value associated with the key, excluding the key itself.
	 */
	public String getValue(String key) {
		for (NameValue nv : data)
			if (key.equals(nv.getName()))
				return nv.getValue();
		return null;
	}
	
	/**
	 * Returns the value found on the requested line.
	 * <p>
	 * Returns the full line of data from the row indexed by the
	 * requested number. This is not (necessarily) the line number in
	 * the file, due to blank lines and comments. This will include the
	 * (assumed) key.
	 * @param index
	 * 				the index of the line of data with in the array. 
	 * @return the full line of data at the requested index.
	 */
	public String getLine(int index) {
		return data.get(index).getLine();
	}
	
	/**
	 * Returns the value for the keyed line and a number of extra lines.
	 * <p>
	 * Returns an array of values. The first value is treated as
	 * key: value, and only the value without the key is returned. The
	 * extra lines are treated as value only lines, and the entire line
	 * of data is returned. The total number of lines of data returned
	 * is extraLines+1 
	 * @param key
	 * 			The key to search for to locate the data.
	 * @param extraLines
	 * 					The number of extra lines of values to return, following the key.
	 * @return The values of the data following the key, as one String per line.
	 */
	public String[] getValues(String key, int extraLines) {
		String[] values = new String[extraLines+1];
		
		int i0 = firstIndexOf(key);
		values[0] = data.get(i0).getValue();
		for (int i = 1; i <= extraLines; i++) {
			values[i] = data.get(i+i0).getLine();
		}
		
		return values;
	}
	
	/**
	 * Returns the values for several lines, starting at the indexed line.
	 * <p>
	 * Returns all the data for totalLines lines, starting at index.
	 * This will not necessarily be the same number of lines in the
	 * original file, as comment and blank lines will be skipped.
	 * @param index
	 * 				the index at which to start returning data.
	 * @param totalLines
	 * 					the total number of lines of data to return.
	 * @return The data as an array of Strings
	 */
	public String[] getLines(int index, int totalLines) {
		String[] values = new String[totalLines];
		
		for (int i = 0; i < totalLines; i++) {
			values[i] = data.get(index+i).getLine();
		}
		return values;
	}
	
	/**
	 * Returns the index at which the requested key first occurs
	 * @param key
	 * 			the requested key
	 * @return The index of the line in which the key first occurs 
	 */
	public int firstIndexOf(String key) {
		return firstIndexOfAfter(key, 0);
	}
	
	/**
	 * Returns the indices of the lines containing the key.
	 * @param key
	 * 			the requested key
	 * @return The indices of all the lines with the requested key
	 */
	public int[] indicesOf(String key) {
		int[] indices = new int[data.size()];
		int nFound = 0;
		for (int i = 0; i < data.size(); i++) {
			if (key.equals(data.get(i).getName())) {
				indices[nFound] = i;
				nFound++;
			}
		}
		return (nFound > 0) ? Arrays.copyOfRange(indices, 0, nFound) : new int[0]; 
	}

	/**
	 * Returns the index of the first occurrence of the key after the given index
	 * @param key
	 * 			the requested key
	 * @param after
	 * 			the index of the line after which to begin looking
	 * @return The first index containing the key after the target index
	 */
	public int firstIndexOfAfter(String key, int after) {
		for (int i = after+1; i < data.size(); i++)
			if (key.equals(data.get(i).getName()))
				return i;
		return -1;
	}
	
	private static List<NameValue> read(String[] lines) {
		// Delete comments: semicolon to end of line
		List<String> commentlessLines = Arrays.stream(lines).map(line -> (line.equals(";")) ? "" : line.split(";")[0]).collect(Collectors.toList());
		
		// Remove blank lines
		commentlessLines.removeIf((String s) -> s.matches("\\s*"));
		
		// Remove all trailing tabs
		List<String> trimmedLines = commentlessLines.stream().map(line -> line.replaceAll("\\s*$", "")).collect(Collectors.toList());
		
		List<NameValue> outLines = new ArrayList<>();
		for (String line: trimmedLines)
			outLines.add(parseLine(line));
				
		return outLines;
		
	}
	
	private static List<NameValue> read(String filename) throws IOException {
		// read the file to the Strings comprising the lines of the file
		String[] fileLines = Files.readAllLines(Paths.get(filename)).toArray(new String[1]);
		
		return read(fileLines);
	}

	/**
	 * Parses the line into a (name: value) format
	 * @param line
	 * @return
	 */
	private static NameValue parseLine(String line) {
		return new NameValue(line);
	}
	
	public static void main(String args[]) {
		try {
			System.out.println(XRMCDatReader.read("/home/rkl37156/xrmc/experiments/DSK_ceria/geom3d.dat"));	
		} catch (IOException ioe) {
			System.err.println("Could not read file");
		}
	}
	
	
	private static class NameValue {
		private String name;
		private String value;
		private String line;
		
		public NameValue(String line) {
			this.line = line;
			
			String possibleName = line.split("\\s+")[0];
			// Names are assumed to have no digits. There may be false
			// positives, where the first value of a set has no digits
			if (!possibleName.matches(".*\\d.*")) {
				name = possibleName;
				// remove the name and leading spaces
				String possibleValue = line.replace(name, "").replaceFirst("^\\s*", "");
				value = possibleValue;
			} else {
				name = "";
				value = line;
			}
		}
		
		public String getName() {
			return name;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getLine() {
			return line;
		}
		
		public String toString() {
			return (name.length() > 0) ? name + ": " + value : line;
		}
	}
	
}
