package uk.ac.diamond.scisoft.ptychography.rcp.utils;

import java.io.File;

public class PtychoConstants {
	public static final String PTYCHOPLOTVIEWS = "uk.ac.diamond.scisoft.ptychography.rcp.plotView";
	public static final String RESOURCES = "resources";
	public static final String INJECT_PYTHON_CMD_ID = "uk.ac.diamond.scisoft.ptychography.rcp.injectPythonCommandID";
	public static final String TMP_FILE = System.getProperty("java.io.tmpdir") + File.separator + "parameter.csv";
	public static final String SCRIPT = File.separator + "dls_sw" + File.separator + "apps" + File.separator + "pycho" + File.separator + "master" + File.separator + "pycho_recon.py";
	public static final String ALTERNATE_SCRIPT = File.separator + "dls_sw" + File.separator + "apps" + File.separator + "pycho" + File.separator + "master" + File.separator + "pycho_recon.py";
	public static final String TEMPLATE_FILE = File.separator + "dls_sw" + File.separator + "apps" + File.separator + "ePi" + File.separator + "epi_parameters_descriptions.csv";
}
