package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Crystal;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.processing.python.AbstractPythonScriptOperation;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.dawnsci.python.rpc.AnalysisRpcPythonPyDevService;
import org.dawnsci.python.rpc.AnalysisRpcPythonService;
import org.dawnsci.python.rpc.PythonRunScriptService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Cambridge Crystallographic Data Centre wrapper to communicate with xmlrpc Python interface.
 *
 * TODO: send crystal system
 *
 * @author Dean P. Ottewell
 */
public class CCDCService implements ICCDCService {

	private static final Logger logger = LoggerFactory.getLogger(CCDCService.class);

	public static final String ID = "CCDC";

	protected URL urlCellSearchHandler = getClass().getResource("python/cellSearchHandler.py");

	private PythonRunInfo server;

	private AnalysisRpcClient analysisRpcClient;

	private static final int PORT = 8700;

	private static final String FINDCELLMATCHES = "FINDCELLMATCHES";
	private static final String SETLATTICE = "SETLATTICE";
	private static final String GATHERMATCHES = "GATHERMATCHES";
	private static final String SETREFCODE = "SETREFCODE";

	private static final String SEARCHCRYSTAL = "SEARCHCRYSTAL";
	private static final String SETTOLLATTICE = "SETTOLLATTICE";
	private static final String FILTERELEMENTS = "FILTERELEMENTS";
	
	private static final String SAVECIFREFCODE = "SAVECIFREFCODE";
	
	private static final String GENERATEREPORTREFCODE = "GENERATEREPORTREFCODE";
	
	private CellParameter cell;

	private Crystal crystalSys;
	
	/**
	 * Begins the server xmlrpc session
	 */
	public CCDCService() {
		setUpServer();
	}

	/**
	 * Destroys the server background Python file listening for input. 
	 * Gives time to ensure ports are freed.
	 * 
	 * XXX: If the Python file has entered some unreliable loop the reset of the server is necessary
	 */
	public void terminateServer() {
		if (getServer() != null) {
			if (!getServer().hasTerminated()) {
				getServer().terminate();
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
	 * Initialise Python server to run in background and listen for requests
	 * 
	 * Throws exceptions? 
	 * * Env license not avaliable
	 * * CCDC path wrong -> that is multiple trie. Then can resutl in CSD_HOME or CCDC_ERR
	 * 
	 * 
	 * TODO: need to gather feedback on why the server did not run. This can then be fed up the ui.... does service need a status
	 */
	public void setUpServer() {
		try {
//			File f = new Path(urlCellSearchHandler.getPath());
//			
//			String absPath = f.getAbsoluteFile().getAbsolutePath().toString();
			//String absPath = Paths.get(urlCellSearchHandler.toURI()).toAbsolutePath().toString();
			String absPath = "/dls/tmp/PowderAnalysisConfig/cellSearchHandler.py";
			//TODO: check the python setup before even beginnign the interaction?
			
			//TODO: pass a debug mode level for the logger. If in debug also dd not fail the getStdout the same
			//PythonRunInfo server = PythonHelper.runPythonFileBackground(absPath);
			
			AnalysisRpcPythonPyDevService s = null;
			PythonRunScriptService pythonRunScriptService = null;
			
				
			try {
				s = AnalysisRpcPythonPyDevService.create();
				pythonRunScriptService = new PythonRunScriptService(s);
			} catch (Exception e) {
				System.out.println(e);
				//this, "Could not create script service!");
			}		
			
			if (s == null || pythonRunScriptService == null) throw new Exception("Could not create python interpreter");
			
			if (absPath == null || absPath.isEmpty()) throw new Exception("Path to script not set");
			
			
			Map<String,Object> inputs = new HashMap<>();
			//Map<String, String> in = new Map<String, String>();
			pythonRunScriptService.runScript(absPath, inputs);
			
			/*
			 * BELOW ARE OLD METHODS PLAYING WITh
			 * 
			 * */
			//TODO: Can wrap around these exceptions in the UI
//			AnalysisRpcPythonPyDevService serviceTestPyDev = new AnalysisRpcPythonPyDevService(true);
//			AnalysisRpcPythonService serviceTest= new AnalysisRpcPythonPyDevService(true);
			//PythonRunScriptService scriptRunTest = new PythonRunScriptService(serviceTest);
			//TODO: wrap in manageable background process that catches errors
			//scriptRunTest.runScript(absPath, null);
			
			 
			//Grab the client..
			//analysisRpcClient = (AnalysisRpcClient) serviceTest.getClient();
			//serviceTest.addHandler(pycode, single_handler_name);
			
//			PythonInterpreter interpreter = new PythonInterpreter();
			
			//Selecting a intrepreter?
//			PydevConsoleInterpreter pyInter = new PydevConsoleInterpreter();
//			pyInter.getInterpreterInfo();
//			
//			
			//pyInter.setInterpreterInfo(interpreterInfo);
//			PySystemState interpretorUsing = interpreter.getSystemState();
			
			
//			pyInter.exec("execfile(" + absPath +", {} )",  new ICallback<Object, InterpreterResponse>() {
//                @Override
//                public Object call(final InterpreterResponse response) {
//                    //sets the new mode
//                    //prompt.setMode(!response.more);
//                    //prompt.setNeedInput(response.need_input);
//
//                    //notify about the console answer (not in the UI thread).
////                    for (Object listener : listeners) {
////                        ((IScriptConsoleListener) listener).interpreterResponse(response, prompt);
////                    }
////                    onResponseReceived.call(response);
//                 return null;
//                }
//            });
			
			
			//PydevConsole consoleTest = new PydevConsole(null, absPath);
			
			//NOTE: fails on any output... not neccasirly a error...
//			String outting = server.getStdout(true);
			//TODO: how to determine which import caused this? we would know about he assert but how to delegate and say get your path sorted?
			
			setServer(server);

			
			analysisRpcClient = new AnalysisRpcClient(PORT);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			
			logger.debug("Was not able to start" + urlCellSearchHandler.getFile() + e);
			
			
			terminateServer();
			e.printStackTrace();
		}
	}
	
	private String checkConfiguration(){
		
		
		return null;
	}
	
	public Boolean serverAvaliable() {
		Boolean avaliblePyfile = false;
		try {
			avaliblePyfile = (boolean) analysisRpcClient.request("AVALIABLE", new Object[] {});
		} catch (AnalysisRpcException e) {
			logger.debug("Server failed to respond avaliability" + e);
			terminateServer();
			e.printStackTrace();
		}
		return avaliblePyfile;
	}

	//Configuration Before setLattice
	public Boolean setLattice(CellParameter cell){
		this.cell = cell;
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETLATTICE, new Object[] {cell.getA(),cell.getB(),cell.getC(),cell.getAl(),cell.getBe(),cell.getGa()});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to set lattice parameter" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}
	
	public Boolean setLattice(Crystal crystal){
		this.crystalSys = crystal;
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETLATTICE, new Object[] {crystal.getUnitCell().getLattice().getA(),
					crystal.getUnitCell().getLattice().getB(),
					crystal.getUnitCell().getLattice().getC(),
					crystal.getUnitCell().getLattice().getAl(),
					crystal.getUnitCell().getLattice().getBe(),
					crystal.getUnitCell().getLattice().getGa()});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to set lattice parameter" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}
	
	public Boolean setLattice(String refcode){
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETREFCODE, new Object[] {refcode});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to set refcode parameter" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}
	
	//Searches TODO: although there should really only be one. That being the core crystal search
	/**
	 * Crystal should already be configured for a search
	 * 
	 * @return 
	 */
	public Boolean runCrystalSearch() {
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SEARCHCRYSTAL, new Object[] {});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request crystal search." + e);
			terminateServer();
			e.printStackTrace();
		}
		
		if(!success){
			logger.debug("Unable to request crystal search. Check if crystal values were set!");
		}
		
		return success;
	}
	

	/**
	 * Allows for varied search
	 * 
	 * @return
	 */
	public Boolean setCrystalSearchTolerance(double absAngleTol, double percentLenTol){
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETTOLLATTICE, new Object[] {absAngleTol,percentLenTol});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request crystal search." + e);
			terminateServer();
			e.printStackTrace();
		}
		
		if(!success){
			logger.debug("Unable to request crystal search. Check if crystal values were set!");
		}
		
		return success;
	}
	
	/**
	 * Triggers a search on the the current unit cell passed. 
	 * 
	 * Not a crystal search and therefore preconfigurables are not taken into account
	 * 
	 * @return success
	 */
	public Boolean runIndependentCellSearch(double a, double b, double c, double alp, double bet, double gam) {
		boolean success = false;
		
		if(crystalSys == null){
			
			return false;
		}
		
		// Run cell search
		try {
			success = (boolean) analysisRpcClient.request(FINDCELLMATCHES, new Object[] {a,b,c,alp,bet,gam});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request search" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}

	//TODO: really need to cast matches into a unitcellconfig
	public Object gatherMatches() {
		Object matches = null;
		// Gather matches
		try {
			matches = (Object) analysisRpcClient.request(GATHERMATCHES, new Object[] {});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to retrieve hit matches" + e);
			terminateServer();
			e.printStackTrace();
		}
		return matches;
	}

	public Boolean generateRefcodeCif(String filepath, String refcode){
		boolean success = false;
		// Run cell search
		try {
			success = (boolean) analysisRpcClient.request(SAVECIFREFCODE, new Object[] {
					filepath,
					refcode});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request search" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}
	
	public Boolean generateRefcodeReport(String filepath, String refcode){
		boolean success = false;
		// Run cell search
		try {
			success = (boolean) analysisRpcClient.request(GENERATEREPORTREFCODE, new Object[] {
					filepath,
					refcode});
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request search" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}
	
	public PythonRunInfo getServer() {
		return server;
	}

	public void setServer(PythonRunInfo server) {
		this.server = server;
	}
}
