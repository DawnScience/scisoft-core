package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.eclipse.january.metadata.MetadataType;

import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;

public class XRMCMetadataImpl implements XRMCMetadata {

	private String inputFileName;
	private EnumMap<XRMCDevice, Boolean> readDeviceType;
	private XRMCDetector savedDetector;
	private List<XRMCSpectrum> spectra;
	private List<XRMCSource> sources;
	
	public XRMCMetadataImpl() {
		
	}
	
	public XRMCMetadataImpl(String inputFile) throws IOException {
		inputFileName = inputFile;
		initDeviceReadingness();
	}

	// Copy constructor
	public XRMCMetadataImpl(XRMCMetadataImpl inMeta) {
		this.inputFileName = (inMeta.inputFileName != null) ? new String(inMeta.inputFileName) : null;
		this.readDeviceType = (inMeta.readDeviceType != null) ? inMeta.readDeviceType.clone(): null;
		this.savedDetector = new XRMCDetector(inMeta.savedDetector);
	}
	
	public void readData() throws IOException {
		System.err.println("XRMCMetadataImpl.readData(): reading data");
		
		XRMCInputReader xrmcInput = new XRMCInputReader(inputFileName);
		xrmcInput.read();
		
		XRMCTextualData xrmcText = new XRMCTextualData(xrmcInput);
		
		// Read detectors
		if (getDeviceRead(XRMCDevice.DETECTORARRAY)) {
			String[] detectorNames = xrmcText.getDeviceNames(XRMCDevice.DETECTORARRAY);
			// Get the detector that was used to by XRMC to save the image.
			String savedDetectorName = xrmcInput.getSaveInfo().getDevicename();
			if (!Arrays.asList(detectorNames).contains(savedDetectorName))
				throw new IllegalArgumentException("XRMCMetadataImpl.readData(): Saved detector not found in the list of available detectors");
			List<String> savedDetectorText = xrmcText.getDeviceText(savedDetectorName);
			savedDetector = new XRMCDetector(savedDetectorText.toArray(new String[savedDetectorText.size()]));
			
		}

		// Read spectrum
		if (getDeviceRead(XRMCDevice.SPECTRUM)) {
			String[] spectrumNames = xrmcText.getDeviceNames(XRMCDevice.SPECTRUM);
			for (String spectrumName: spectrumNames) {
				List<String> spectralLines = xrmcText.getDeviceText(spectrumName);
				spectra.add(new XRMCSpectrum(spectralLines.toArray(new String[spectralLines.size()])));
			}
		}
		
		// Read Source
		if (getDeviceRead(XRMCDevice.SOURCE)) {
			String[] sourceNames = xrmcText.getDeviceNames(XRMCDevice.SOURCE);
			for (String sourceName: sourceNames) {
				List<String> sourceLines = xrmcText.getDeviceText(sourceName);
				sources.add(new XRMCSource(sourceLines.toArray(new String[sourceLines.size()])));
			}
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
		return new XRMCMetadataImpl(this);
	}

	@Override
	public XRMCDetector getDetector() {
		return savedDetector;
	}

	@Override
	// Read the first spectrum
	public XRMCSpectrum getSpectrum() {
		return spectra.get(0);
	}
	
	private void initDeviceReadingness() {
		readDeviceType = new EnumMap<>(XRMCDevice.class);
		for (XRMCDevice dev : XRMCDevice.values()) {
			readDeviceType.put(dev, false);
		}
	}
	
	@Override
	public XRMCSource getSource() {
		return sources.get(0);
	}
	
}
