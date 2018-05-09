package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.eclipse.january.metadata.MetadataType;

import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;

public class XRMCMetadataImpl implements XRMCMetadata {

	String inputFileName;
	EnumMap<XRMCDevice, Boolean> readDeviceType;
	XRMCDetector savedDetector;
	
	public XRMCMetadataImpl() {
		
	}
	
	public XRMCMetadataImpl(String inputFile) throws IOException {
		inputFileName = inputFile;
		initDeviceReadingness();
		readData();
	}

	public void readData() throws IOException {
		XRMCInputReader xrmcInput = new XRMCInputReader(inputFileName);
		xrmcInput.read();
		
		XRMCTextualData xrmcText = new XRMCTextualData(xrmcInput);
		
		// Read detectors
		if (getDeviceRead(XRMCDevice.DETECTORARRAY)) {
			String[] detectorNames = xrmcText.getDeviceNames(XRMCDevice.DETECTORARRAY);
			// Get the detector that was used to by XRMC to save the image.
			String savedDetectorName = xrmcInput.getSaveInfo().getDevicename();
			if (!Arrays.asList(detectorNames).contains(savedDetectorName))
				throw new IllegalArgumentException("XRMCMetadata.readData(): Saved detector not found in the list of available detectors");
			List<String> savedDetectorText = xrmcText.getDeviceText(savedDetectorName);
			savedDetector = new XRMCDetector(savedDetectorText.toArray(new String[savedDetectorText.size()]));
			
		}

		// Read 
		if (getDeviceRead(XRMCDevice.SPECTRUM)) {
			
		}
		
	}
	
	public void setDeviceRead(XRMCDevice deviceType, boolean isRead) {
		readDeviceType.put(deviceType, isRead);
	}
	
	public boolean getDeviceRead(XRMCDevice deviceType) {
		return readDeviceType.get(deviceType);
	}
	
	@Override
	public MetadataType clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XRMCDetector getDetector() {
		return savedDetector;
	}

	private void initDeviceReadingness() {
		readDeviceType = new EnumMap<>(XRMCDevice.class);
		for (XRMCDevice dev : XRMCDevice.values()) {
			readDeviceType.put(dev, false);
		}
	}
	
}
