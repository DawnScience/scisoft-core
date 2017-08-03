package uk.ac.diamond.scisoft.analysis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VersionSort {
	private VersionSort() {
		
	}
	
	private static String getDigitString(String str, final int indexTemp) {
		final int index = indexTemp >= str.length() ? indexTemp -1 : indexTemp;
		
		if (!Character.isDigit(str.charAt(index))) {
			return null;
		}
		
		int startIndex = index;
		while (true) {
			startIndex--;
			if (startIndex < 0 || !Character.isDigit(str.charAt(startIndex)))
				break;
		}
		startIndex++;
		int endIndex = index;
		while (true) {
			endIndex++;
			if (endIndex >= str.length() || !Character.isDigit(str.charAt(endIndex)))
				break;
		}
		return str.substring(startIndex, endIndex);
	}

	private static String addDecimalPoint(String str) {
		if (str.charAt(0) != '0' || str.length() == 1)
			return str;
		return "0." + str.substring(1);
	}
	
	private static int countLeadingZeroes(String str) {
		int rv = 0;
		for (int i = 0 ; i < str.length() ; i++) {
			if (str.charAt(i) != '0')
				return rv;
			rv++;
		}
		return rv;
	}
	
	private static int countTrailingZeroes(String str) {
		int rv = 0;
		for (int i = str.length()-1 ; i >= 0  ; i--) {
			if (str.charAt(i) != '0')
				return rv;
			rv++;
		}
		return rv;
	}
	
	/** Code written based on man strverscmp, with one key difference -> value takes precedence over number of leading zeroes!!!
	 * 
	 * @param str1
	 * @param str2
	 * @return an integer less than, equal to, or greater than zero if str1 is found, respectively, to be earlier than,
       equal to, or later than s2.
	 */
	public static int versionCompare(String str1, String str2) {
		if (str1.equals(str2))
			return 0;
		
		// look for last byte where the strings are identical 
		int index = 0;
		while (true) {
			char char1;
			char char2;
			try {
				char1 = str1.charAt(index);
				char2 = str2.charAt(index);
			} catch (IndexOutOfBoundsException e) {
				char1 = '0';
				char2 = '1';
			}
			
			if (char1 != char2) {
				//index--;
				break;
			}
			
			index++;
		}
	
		//if (index == -1)
		//	index = 0;
		
		// we found where the strings started to differ. Now check if this position is within a digit string
		String versionString1 = getDigitString(str1, index);
		String versionString2 = getDigitString(str2, index);
	
		if (versionString1 == null || versionString2 == null)
			return str1.compareTo(str2);
		
		// if the versionStrings are equal, sorting needs to be decided on what follows after the versionString
		if (versionString1.equals(versionString2)) {
			int afterVersionIndex1 = str1.indexOf(versionString1)+versionString1.length();
			int afterVersionIndex2 = str2.indexOf(versionString2)+versionString2.length();
			String afterVersionString1 = afterVersionIndex1 >= str1.length() ? "" : str1.substring(afterVersionIndex1);
			String afterVersionString2 = afterVersionIndex2 >= str2.length() ? "" : str2.substring(afterVersionIndex2);
			return afterVersionString1.compareTo(afterVersionString2);
		}
		
		// digit strings with leading zeroes must be interpreted as having a decimal point in front
		String versionDecimalString1 = addDecimalPoint(versionString1);
		String versionDecimalString2 = addDecimalPoint(versionString2);
		
		// parse as double
		double versionDouble1 = Double.parseDouble(versionDecimalString1);
		double versionDouble2 = Double.parseDouble(versionDecimalString2);
		
		if (versionDouble1 != versionDouble2)
			return (int) Math.signum(versionDouble1 - versionDouble2);
		
		// at this point the strings have the same numerical value,
		// so the only way for them to differ is by the number of leading or trailing zeroes
		int leadingZeroes1 = countLeadingZeroes(versionString1);
		int leadingZeroes2 = countLeadingZeroes(versionString2);
	
		if (leadingZeroes1 != leadingZeroes2)
			return leadingZeroes2 - leadingZeroes1;
		
		// if number of leading zeroes is identical, look at trailing zeroes
		int trailingZeroes1 = countTrailingZeroes(versionString1);
		int trailingZeroes2 = countTrailingZeroes(versionString2);
	
		return trailingZeroes1 - trailingZeroes2;
	}
	
	public static void main(String[] args) {
		List<String> strings = new ArrayList<>(20);
		for (int i = 0 ; i < 20 ; i++)
			strings.add(i, String.format("Column %d", i));
		Collections.shuffle(strings);
		System.out.format("Shuffled array: %s\n", Arrays.toString(strings.toArray(new String[20])));
		strings.sort(VersionSort::versionCompare);
		System.out.format("Sorted array: %s\n", Arrays.toString(strings.toArray(new String[20])));
		
		String[] stringArray2 = new String[]{"000", "00", "01", "010", "09", "0", "1", "9", "10"};
		//String[] stringArray2 = new String[]{"000", "00"};
		List<String> strings2 = Arrays.asList(stringArray2);
		Collections.shuffle(strings2);
		System.out.format("Shuffled array2: %s\n", Arrays.toString(strings2.toArray(new String[0])));
		strings2.sort(VersionSort::versionCompare);
		System.out.format("Sorted array2: %s\n", Arrays.toString(strings2.toArray(new String[0])));
	}
}
