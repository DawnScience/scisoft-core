package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.powder.indexer.IPowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.powder.indexer.PowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;
import uk.ac.diamond.scisoft.xpdf.views.CrystalSystem;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**l
 *         GsasIIWrap call based of xmlrpc. Class acts as a client that
 *         communicates with the Python server that has access to GsasII 
 *         powder indexing procedure. The server file {@link runGSASII.py} to run as
 *         the server.
 * 
 * @author Dean P. Ottewell
 */
public class GsasIIWrapper extends AbstractPowderIndexer {

	private static final Logger logger = LoggerFactory.getLogger(GsasIIWrapper.class);

	public static final String ID = "GsasII";

	protected URL urlGsasIIPyServer = getClass().getResource("runGSASII.py");

	PythonRunInfo server;

	AnalysisRpcClient analysisRpcClient;

	private static final int PORT = 8715;

	private static final String INDEXING = "INDEXING";

	/*
	 * GSASII parameter set 
	*/

	// Controls UNKNOWN_UNUSED,zero=0,ncno = 4 ,volume=25, - these are deafult
	// values
	private List<Double> controls = Arrays.asList(0.01134, 0.0, 4.0, 25.0);

	@Override
	public List<CellParameter> getResultCells() {
		return plausibleCells;
	}

	/**
	 * Destroys the server background python file listening for input.
	 * Gives time to ensure ports are freed.
	 */
	public void terminatePyServer() {
		if (server != null) {
			if (!server.hasTerminated()) {
				server.terminate();
				try {
					// Give some breadth in letting the server shutdown
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// server.getStdout(true);
			}
		}
	}

	/**
	 * Initialise python server to run in background and listen for requests
	 */
	private void setUpServer() {
		try {
			File f = new File(urlGsasIIPyServer.getPath());
			String absPath = f.getAbsolutePath().toString();
			server = PythonHelper.runPythonFileBackground(absPath);

			analysisRpcClient = new AnalysisRpcClient(PORT);

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			logger.debug("Was not able to start GsasII python file" + e);
			terminatePyServer();
			e.printStackTrace();
		}

	}

	private Boolean connectServerSuccesfully() {

		Boolean avaliblePyfile = false;
		try {
			avaliblePyfile = (boolean) analysisRpcClient.request("AVAILABLE", new Object[] {});
		} catch (AnalysisRpcException e) {
			logger.debug("Server failed to respond avaliability" + e);
			terminatePyServer();
			e.printStackTrace();
		}

		return avaliblePyfile;
	}

	private List<CellParameter> extractCellResults(String result) {
		List<CellParameter> pC = new ArrayList<CellParameter>();

		String[] cells = result.split(",");

		for (int val = 0; val < cells.length; ++val) {
			// Create plausible cells
			CellParameter cell = new CellParameter();
			cell.setCrystalSystem(new CrystalSystem());

			Double merit = Double.valueOf(cells[val++]);
			Double a = Double.valueOf(cells[val++]);
			Double b = Double.valueOf(cells[val++]);
			Double c = Double.valueOf(cells[val++]);

			Double alp = Double.valueOf(cells[val++]);
			Double bet = Double.valueOf(cells[val++]);
			Double gam = Double.valueOf(cells[val]);

			cell.setFigureMerit(merit);
			cell.setUnitCellLengths(a, b, c);
			cell.setUnitCellAngles(alp, bet, gam);

			pC.add(cell);
		}

		return pC;
	}

	@Override
	public void configureIndexer() {
		// Call setup and start running the python file in the bg
		setUpServer();
		// Check success of server
		connectServerSuccesfully();
	}

	@Override
	public void runIndexer() {
		try {
			String rawCellResult = (String) analysisRpcClient.request(INDEXING,
					new Object[] { peakData, controls, gatherActiveBravaisParamters()});

			if (rawCellResult.length() > 0){
				plausibleCells = extractCellResults(rawCellResult);
				
				//Tmp primitive sort of PlausibleCells
				Collections.sort(plausibleCells, new Comparator<CellParameter>(){
					@Override
					public int compare(CellParameter o1, CellParameter o2) {
						int winner =  o1.getFigureMerit() > o2.getFigureMerit() ? -1 : 1;// (o1.isGreaterMerit(o2)) ? -1 : 1;
						return winner;
					}
				});
			}
			
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request indexing results");
			terminatePyServer();
			e.printStackTrace();
		}

		// TODO: might want a rerun so no need to terminate here
		terminatePyServer();
	}
	
	private List<Boolean> gatherActiveBravaisParamters(){
		
//		['Cubic-F','Cubic-I','Cubic-P','Trigonal-R','Trigonal/Hexagonal-P',
//	    'Tetragonal-I','Tetragonal-P','Orthorhombic-F','Orthorhombic-I','Orthorhombic-C',
//	    'Orthorhombic-P','Monoclinic-C','Monoclinic-P','Triclinic']
		List<Boolean> activeBravais = Arrays.asList(
				isParameterSet(StandardConstantParameters.cubicSearch), //'Cubic-F'
				isParameterSet(StandardConstantParameters.cubicSearch), //'Cubic-I'
				isParameterSet(StandardConstantParameters.cubicSearch), //,'Cubic-P'
				isParameterSet(StandardConstantParameters.trigonalSearch), //,'Trigonal-R'
				isParameterSet(StandardConstantParameters.trigonalSearch), //,'Trigonal/Hexagonal-P',
				isParameterSet(StandardConstantParameters.tetragonalSearch), //'Tetragonal-I'
				isParameterSet(StandardConstantParameters.tetragonalSearch), //,'Tetragonal-P'
				isParameterSet(StandardConstantParameters.orthorhombicSearch), //,'Orthorhombic-F'
				isParameterSet(StandardConstantParameters.orthorhombicSearch), //,'Orthorhombic-I'
				isParameterSet(StandardConstantParameters.orthorhombicSearch), //,'Orthorhombic-C',
				isParameterSet(StandardConstantParameters.orthorhombicSearch), //'Orthorhombic-P'
				isParameterSet(StandardConstantParameters.monoclinicSearch),//,'Monoclinic-C'
				isParameterSet(StandardConstantParameters.monoclinicSearch), //,'Monoclinic-P'
				isParameterSet(StandardConstantParameters.triclinicSearch)//,'Triclinic']
				);
		
		return activeBravais;
	}

	
	private Boolean isParameterSet(String paramName){
		try {
			return getParameter(paramName).getValue().intValue() != 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	
	@Override
	public void stopIndexer() {
		terminatePyServer();
	}
	
	@Override
	public String getStatus() {
		String status = null;

		if (!server.hasTerminated())
			status = "Indexing Active";

		return status;
	}

	@Override
	public boolean isPeakDataValid(IDataset peakData) {
		boolean isValid = true;

		if (peakData == null)
			isValid = false;

		return isValid;
	}
	
	@Override
	public Map<String, IPowderIndexerParam> getInitialParamaters() {
		// TODO Auto-generated method stub
		Map<String, IPowderIndexerParam> intialParams = new TreeMap<String, IPowderIndexerParam>();
		//TODO: wavelength should be grabbed from the service constant file
		intialParams.put("wavelength", new PowderIndexerParam("Wavelength", new Double(1.5405981)));

		// Controls UNKNOWN_UNUSED,zero=0,ncno = 4 ,volume=25, - these are deafult
		intialParams.put("Zero Point Error", new PowderIndexerParam("zero", 0));
		
		//TODO: what is ncno then?
		//intialParams.put("ncno",new PowderIndexerParam("ncno", 4));
		intialParams.put("Volume Limit", new PowderIndexerParam("volume", 25));
		
	
		intialParams.put(StandardConstantParameters.cubicSearch, new PowderIndexerParam(StandardConstantParameters.cubicSearch, 1));
		intialParams.put(StandardConstantParameters.monoclinicSearch, new PowderIndexerParam(StandardConstantParameters.monoclinicSearch, 1));
		intialParams.put(StandardConstantParameters.orthorhombicSearch,new PowderIndexerParam(StandardConstantParameters.orthorhombicSearch, 1));
		intialParams.put(StandardConstantParameters.hexagonalSearch, new PowderIndexerParam(StandardConstantParameters.hexagonalSearch, 1)); 
		intialParams.put(StandardConstantParameters.tetragonalSearch, new PowderIndexerParam(StandardConstantParameters.tetragonalSearch,10));
		intialParams.put(StandardConstantParameters.trigonalSearch, new PowderIndexerParam(StandardConstantParameters.trigonalSearch, 0));
		intialParams.put(StandardConstantParameters.hexagonalSearch, new PowderIndexerParam(StandardConstantParameters.hexagonalSearch	, 0));
		intialParams.put(StandardConstantParameters.triclinicSearch, new PowderIndexerParam(StandardConstantParameters.triclinicSearch, 0));
		
		return intialParams;
	}


	@Override
	public Boolean isIndexerAvaliable(String identifier) {
		//TODO: is GSASII a differnt setup? i need to get to python files.. Should check what Iwant is avaliable
		
		return false;
	}

	
}
