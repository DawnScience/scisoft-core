package uk.ac.diamond.scisoft.ptychography.rcp.utils;

import java.io.File;

public class PtychoConstants {
	public static final String EPI_FOLDER = File.separator + "dls_sw" + File.separator + "apps" + File.separator + "ePi";
	public static final String PTYCHOPLOTVIEWS = "uk.ac.diamond.scisoft.ptychography.rcp.plotView";
	public static final String SPREADSHEET_FILE = "epi_parameters_descriptions.csv";
	public static final String RESOURCES = "resources";
	public static final String[] FILE_TYPES = new String[] { "CSV File", "JSon File", "XML File" };
	public static final String INJECT_PYTHON_CMD_ID = "uk.ac.diamond.scisoft.ptychography.rcp.injectPythonCommandID";
	public static final String TMP_FILE = System.getProperty("java.io.tmpdir") + File.separator + "parameter.csv";
}
