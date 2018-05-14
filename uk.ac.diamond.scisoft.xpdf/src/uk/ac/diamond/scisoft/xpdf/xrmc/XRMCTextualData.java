package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class XRMCTextualData {

	
	private Map<String, XRMCDevice> deviceTypeByName;
	private Map<String, List<String>> deviceTextByName;

	private static EnumMap<XRMCDevice, String> canonicalDeviceNames = null;
	private static Map<String, XRMCDevice> deviceByCanonicalName = null;
	
	private static Pattern blankR = null;
	private static Pattern nyDeviceLineR = null;
	private static Pattern nyDeviceR = null;
	private static Pattern endR = null;
	private static Pattern commentOnlyR = null;
	
	// Only constructable from a XRMCInputReader
	private XRMCTextualData() {		
		
		// maps of the data
		deviceTypeByName = new HashMap<>();
		deviceTextByName = new HashMap<>();

		createRegex();
		createDeviceNameMap();
}
	
	public XRMCTextualData(XRMCInputReader reader) {
		this();
		readFiles(reader.getFilePath(), reader.getFiles());
	}

	public String[] getDeviceNames() {
		return deviceTypeByName.keySet().toArray(new String[deviceTypeByName.size()]);
	}
	
	public String[] getDeviceNames(XRMCDevice type) {
		List<String> deviceNames = new ArrayList<>();
		for (Entry<String, XRMCDevice> entry: deviceTypeByName.entrySet())
			if (entry.getValue() == type)
				deviceNames.add(entry.getKey());
		
		return deviceNames.toArray(new String[deviceNames.size()]);
	}
	
	public XRMCDevice getDeviceType(String name) {
		return deviceTypeByName.get(name);
	}
	
	public List<String> getDeviceText(String name) {
		return deviceTextByName.get(name);
	}
	
	private void readFiles(String filePath, List<String> loadFiles) {
		for (String fileName: loadFiles) {
			// Read each file
			List<String> fileLines;
			try {
				fileLines = Files.readAllLines(Paths.get(filePath, fileName));
			} catch (IOException ioe) {
				System.err.println("XRMCInputReader: Error opening " + fileName + ": " + ioe.toString() + ". Ignoring.");
				continue;
			}

			List<List<String>> allDevices = new ArrayList<>();
			List<String> deviceLines = null;
			// Iterate through its lines for each 'Newdevice'-'End' pair
			for (String line: fileLines) {
				// Start of a device
				if (nyDeviceLineR.matcher(line).matches()) {
					deviceLines = new ArrayList<>();
				}
				// If device lines are being copied, copy them, if the line consists of more than blank or just a comment
				if (deviceLines != null && 
						!blankR.matcher(line).matches() && 
						!commentOnlyR.matcher(line).matches())
					deviceLines.add(line);
				// check for the end of the device
				if (endR.matcher(line).matches()) {
					allDevices.add(deviceLines);
					deviceLines = null;
				}
			}

			mapDevices(allDevices);
		}	
	}
	
	private void mapDevices(List<List<String>> allDevices) {
		// Iterate over the devices, and assign each a device type and its text
		for (List<String> deviceLines: allDevices) {
			// The 'Newdevice' entry is the first line
			String nyDeviceLine = deviceLines.get(0);
			// The format of the Newdevice line is such that on splitting by blanks, the second element is the device name. 
			String deviceCanonicalName = blankR.split(nyDeviceLine)[1];
			// The next non blank, non-comment line is the device name
			String deviceName = blankR.split(deviceLines.get(1))[0];
			// Add the data to the maps
			deviceTypeByName.put(deviceName, deviceByCanonicalName.get(deviceCanonicalName));
			deviceTextByName.put(deviceName, deviceLines);
		}
	}
	
	// Predefined regex strings 
	private static final String BLANKREGEX = "\\s+";
	private static final String NYDEVREGEX = "\\s*Newdevice\\s+";
	private static final String ENDREGEX = "(\\s*End$|\\s*End\\s.*)";
	private static final String COMMENTREGEX = ";";
	private static void createRegex() {
		// Any amount of whitespace only
		blankR = createNonNullRegex(blankR, BLANKREGEX);
		// New device line: Any amount of whitespace, followed by 'Newdevice', followed by some whitespace and then any characters
		nyDeviceLineR = createNonNullRegexTraillingText(nyDeviceLineR, NYDEVREGEX);
		// New device text: Any amount of whitespace, followed by 'Newdevice', followed by some whitespace
		nyDeviceR = createNonNullRegexTraillingText(nyDeviceR, NYDEVREGEX);
		// End device line: Any amount of whitespace, followed by 'End', followed by some whitespace and then any characters. OR any whitespace, "End" and nothing more.
		endR = createNonNullRegex(endR, ENDREGEX);
		// Comment-only line: Any amount of whitespace, followed by a semicolon, followed by anything
		commentOnlyR = createNonNullRegexTraillingText(commentOnlyR, "\\s*"+COMMENTREGEX);
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
	
	private static void createDeviceNameMap() {
		if (canonicalDeviceNames == null) {
			canonicalDeviceNames = new EnumMap<>(XRMCDevice.class);
			canonicalDeviceNames.put(XRMCDevice.SPECTRUM, "spectrum");
			canonicalDeviceNames.put(XRMCDevice.SOURCE, "source");
			canonicalDeviceNames.put(XRMCDevice.DETECTORARRAY, "detectorarray");
			canonicalDeviceNames.put(XRMCDevice.COMPOSITION, "composition");
			canonicalDeviceNames.put(XRMCDevice.QUADRICARRAY, "quadricarray");
			canonicalDeviceNames.put(XRMCDevice.GEOM3D, "geom3d");
			canonicalDeviceNames.put(XRMCDevice.SAMPLE, "sample");
			// Advanced usage devices
			canonicalDeviceNames.put(XRMCDevice.ANISOTROPICSOURCE, "anisotropicsource");
			canonicalDeviceNames.put(XRMCDevice.INTENSITYSCREEN, "intensityscreen");
			canonicalDeviceNames.put(XRMCDevice.BEAMSOURCE, "beamsource");
			canonicalDeviceNames.put(XRMCDevice.BEAMSCREEN, "beamscreen");
			canonicalDeviceNames.put(XRMCDevice.RADIONUCLIDE, "radionuclide");
			canonicalDeviceNames.put(XRMCDevice.PHCDETECTOR, "phcdetector");
			// XMI-MSIM devices not included
		}
		// reverse lookup map
		if (deviceByCanonicalName == null) {
			deviceByCanonicalName = new HashMap<>();
			for(Entry<XRMCDevice, String> entry: canonicalDeviceNames.entrySet()) {
				deviceByCanonicalName.put(entry.getValue(), entry.getKey());
			}
		}
	}
}
