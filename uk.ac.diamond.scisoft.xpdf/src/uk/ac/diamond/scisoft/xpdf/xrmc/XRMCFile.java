package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

public class XRMCFile {
	
	protected XRMCDatReader reader;
	
	private static final String NEWDEVICE = "Newdevice";
	
	protected XRMCFile(String fileName) {
		reader = new XRMCDatReader(fileName);
	}
	
	protected XRMCFile(String[] lines) {
		reader = new XRMCDatReader(lines);
	}
	
	/**
	 * Returns whether the file is valid for its type
	 * @return
	 */
	public boolean isValidFile() {
		return reader.hasKey(NEWDEVICE) && getDeviceName().equals(reader.getValue(NEWDEVICE)); 
	}
	
	protected String getDeviceName() {
		return "";
	}
	
	protected String getValue(String key) {
		return reader.getValue(key);
	}
	
	protected int getIntegerValue(String key) {
		return Integer.parseInt(getValue(key));
	}
	
	protected double getDoubleValue(String key) {
		return Double.parseDouble(getValue(key));
	}
	
	protected double[] parseToDoubleArray(String[] array) {
		return ArrayUtils.toPrimitive(Arrays.stream(array).map((String s) -> Double.parseDouble(s)).toArray(Double[]::new));
	}
	
	protected double[] getAndParseValues(String key) {
		return parseToDoubleArray(getValue(key).split("\\s+"));
	}
	
	protected double[] getParseAndScaleValues(String key, double scale) {
		return Arrays.stream(getAndParseValues(key)).map(d -> d*scale).toArray();
	}
	
	protected boolean getBooleanValue(String key, boolean defaultValue) {
		String flag = getValue(key);
		return (flag != null) ? jollyWellCanCastFromIntToBoolean(Integer.parseInt(flag)) : defaultValue;
	}
	
	private boolean jollyWellCanCastFromIntToBoolean(int i) {
		return i != 0;
	}

}
