package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.dawb.common.util.eclipse.BundleUtils;
import org.dawnsci.python.rpc.AnalysisRpcPythonPyDevService;
import org.dawnsci.python.rpc.PythonRunScriptService;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.dawnsci.analysis.api.rpc.IAnalysisRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Crystal;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;

/**
 * Cambridge Crystallographic Data Centre wrapper to communicate with xmlrpc
 * Python interface.
 *
 * 
 * @author Dean P. Ottewell
 */
public class CCDCService implements ICCDCService {

	public static final String PLUGIN_ID = "uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc"; //$NON-NLS-1$

	private static final Logger logger = LoggerFactory.getLogger(CCDCService.class);

	public static final String ID = "CCDC";

	protected URL urlCellSearchHandler = getClass().getResource("cellSearchHandler.py");

	private static final String SCRIPTPATH = "/uk/ac/diamond/scisoft/analysis/powder/matcher/ccdc/";
	private static final String PYTHONSCRIPTHANDLER = "cellSearchHandler.py";

	private PythonRunInfo server;

	private IAnalysisRpcClient analysisRpcClient;

	private AnalysisRpcPythonPyDevService rpcservice;

	private PythonRunScriptService pythonRunScriptService;

	private static final int PORT = 8700;

	private static final String FINDCELLMATCHES = "FINDCELLMATCHES";
	private static final String SETLATTICE = "SETLATTICE";
	private static final String GATHERMATCHES = "GATHERMATCHES";
	private static final String SETREFCODE = "SETREFCODE";

	private static final String SEARCHCRYSTAL = "SEARCHCRYSTAL";
	private static final String SETTOLLATTICE = "SETTOLLATTICE";

	private static final String SAVECIFREFCODE = "SAVECIFREFCODE";

	private static final String GENERATEREPORTREFCODE = "GENERATEREPORTREFCODE";

	private CellParameter cell;

	private Crystal crystalSys;

	private PythonRunSearcherService serviceSearcher;

	private String scriptAbsPath;

	private PythonRunSearcherService rpcSearcherService;

	private Object matches = null;

	public CCDCService() {
		// setUpServer();
	}

	/**
	 * Destroys the server background Python file listening for input. Gives
	 * time to ensure ports are freed.
	 * 
	 * XXX: If the Python file has entered some unreliable loop the reset of the
	 * server is necessary
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

		// Destroy the python interpretor
		rpcservice.stop();
	}

	/**
	 * Initialise Python server to run in background and listen for requests
	 * 
	 */
	public void setUpServer() throws Exception {
		try {					
			File bundlePath = BundleUtils.getBundleLocation(PLUGIN_ID);
			
			scriptAbsPath = bundlePath.getAbsolutePath() + SCRIPTPATH
					+ PYTHONSCRIPTHANDLER;
			
			if (!new File(scriptAbsPath).exists()) {
				scriptAbsPath = bundlePath.getAbsolutePath() + "/src" + SCRIPTPATH
						+ PYTHONSCRIPTHANDLER;
				if (!new File(scriptAbsPath).exists()) {
					throw new RuntimeException("Couldn't find path to "
							+ PYTHONSCRIPTHANDLER + "!");
				}
			}

			try {
				rpcservice = AnalysisRpcPythonPyDevService.create();
				
				pythonRunScriptService = new PythonRunScriptService(rpcservice);
			} catch (Exception e) {
				//System.out.println(e);
				logger.debug("Server already started" +e);
				//this, "Could not create script service!");
			}		
			
			Map<String,Object> inputs = new HashMap<>();
			//Cast below into runner - need a way to destroy...
			//XXX: this method does not proeprly destroy! MUST encapulate into alternative method of service holder. 
			//currently issues with returning results from that method.
			 Runnable r = new Runnable() {
		         public void run() {
			
						try {
							Map<String, Object> runScript = pythonRunScriptService.runScript(scriptAbsPath, inputs);
						} catch (AnalysisRpcException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
			     }
		     };

		     Thread pythonServer = new Thread(r);
		     pythonServer.start();
		     pythonServer.setName("Core CCDC service server");
		     pythonServer.interrupt();

		     
		} catch (Exception e) {
			logger.debug("Was not able to start" + urlCellSearchHandler.getFile() + e);
			terminateServer();
			throw new Exception(e);
		}
		
		
		//Connect analysis handler separately
		
		analysisRpcClient = new AnalysisRpcClient(PORT);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//XXX: below is how the service should work once the return from the service handlers stop not casting back. They do run and the execution of the python is visable. The return does not however fuinctions
//		try {

//			File bundlePath = BundleUtils.getBundleLocation(Activator.PLUGIN_ID);
//
//			scriptAbsPath = bundlePath.getAbsolutePath() + SCRIPTPATH + PYTHONSCRIPTHANDLER;
//
//			if (!new File(scriptAbsPath).exists()) {
//				scriptAbsPath = bundlePath.getAbsolutePath() + "/src" + SCRIPTPATH + PYTHONSCRIPTHANDLER;
//				if (!new File(scriptAbsPath).exists()) {
//					throw new RuntimeException("Couldn't find path to " + PYTHONSCRIPTHANDLER + "!");
//				}
//			}
//
//			if (scriptAbsPath == null || scriptAbsPath.isEmpty())
//				throw new Exception("Python handler file is not available. Check project structures"); // TODO:
//																										// does
//																										// user
//																										// need
//																										// to
//																										// know
//																										// this?
//			// TODO:^is top check needed as should be able to get error from the
//			// pytho nservice crate...
//
//			// TODO: check service rpc is avlaiable before create one.
//			// TODO: delete on the tool close
//
//			try {
//				rpcservice = AnalysisRpcPythonPyDevService.create();
//				// pythonRunScriptService = new
//				// PythonRunScriptService(rpcservice);
//				rpcSearcherService = new PythonRunSearcherService(rpcservice,false);
//			} catch (AnalysisRpcException e) {
//
//				// TODO: place in better exception catch clause
//				throw new Exception(e.getMessage());
//				// System.out.println(e);
//				// logger.debug("Server already started" +e);
//				// this, "Could not create script service!");
//			}
//
//			if (rpcservice == null || rpcSearcherService == null)
//				throw new Exception("Could not create Python interpreter");
//
//			// TODO: cuts off but works whilst chanign methods to
//			// rpcSearcherService reuqest
//			analysisRpcClient =  rpcservice.getClient();
//
//			// Map<String,Object> inputs = new HashMap<>();
//			// inputs.put("A", 5);
//			try {
//				matches = rpcSearcherService.findCellMatches(5.2, 5.2, 5.2, 90, 90, 90);
//				//analysisRpcClient.request("cellSearchMatches", new Object[] {5.2, 5.2, 5.2, 90, 90, 90});
//			} catch (AnalysisRpcException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//
//
//		} catch (Exception e) {
//			logger.debug("Was not able to start" + urlCellSearchHandler.getFile() + e.getMessage());
//
//			// TODO: the termination of said server creates a error! can assume
//			// not really need to terminate...
//			// terminateServer();
//
//			throw new Exception(e.getMessage());
//		}

	}

	// See results paths
	public Object performSearchMatches(double a, double b, double c, double alp, double bet, double gam)
			throws AnalysisRpcException {
		return rpcSearcherService.findCellMatches(a, b, c, alp, bet, gam);
	}

	public Boolean serverAvailable() {
		Boolean avaliblePyfile = false;
		try {
			avaliblePyfile = (boolean) analysisRpcClient.request("AVALIABLE", new Object[] {});
		} catch (AnalysisRpcException e) {
			logger.debug("Server failed to respond availability" + e);
			terminateServer();
			e.printStackTrace();
		}
		return avaliblePyfile;
	}

	// Configuration Before setLattice
	public Boolean setLattice(CellParameter cell) {
		this.cell = cell;
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETLATTICE,
					new Object[] { cell.getA(), cell.getB(), cell.getC(), cell.getAl(), cell.getBe(), cell.getGa() });
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to set lattice parameter" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}

	public Boolean setSearchLattice(Crystal crystal) {
		this.crystalSys = crystal;
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETLATTICE,
					new Object[] { crystal.getUnitCell().getLattice().getA(), crystal.getUnitCell().getLattice().getB(),
							crystal.getUnitCell().getLattice().getC(), crystal.getUnitCell().getLattice().getAl(),
							crystal.getUnitCell().getLattice().getBe(), crystal.getUnitCell().getLattice().getGa() });
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to set lattice parameter" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}

	public Boolean setLattice(String refcode) {
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETREFCODE, new Object[] { refcode });
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to set refcode parameter" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}

	// Searches TODO: although there should really only be one. That being the
	// core crystal search
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

		if (!success) {
			logger.debug("Unable to request crystal search. Check if crystal values were set!");
		}

		return success;
	}

	/**
	 * Allows for varied search
	 * 
	 * @return
	 */
	public Boolean setCrystalSearchTolerance(double absAngleTol, double percentLenTol) {
		boolean success = false;
		try {
			success = (boolean) analysisRpcClient.request(SETTOLLATTICE, new Object[] { absAngleTol, percentLenTol });
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request crystal search." + e);
			terminateServer();
			e.printStackTrace();
		}

		if (!success) {
			logger.debug("Unable to request crystal search. Check if crystal values were set!");
		}

		return success;
	}

	/**
	 * Triggers a search on the the current unit cell passed.
	 * 
	 * Not a crystal search and therefore preconfigurables are not taken into
	 * account
	 * 
	 * @return success
	 */
	public Boolean runIndependentCellSearch(double a, double b, double c, double alp, double bet, double gam) {
		boolean success = false;

		// Run cell search
		try {
			success = (boolean) analysisRpcClient.request(FINDCELLMATCHES, new Object[] { a, b, c, alp, bet, gam });
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request search" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}

	// TODO: really need to cast matches into a unitcellconfig
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

	public Boolean generateRefcodeCif(String filepath, String refcode) {
		boolean success = false;
		// Run cell search
		try {
			success = (boolean) analysisRpcClient.request(SAVECIFREFCODE, new Object[] { filepath, refcode });
		} catch (AnalysisRpcException e) {
			logger.debug("Unable to request search" + e);
			terminateServer();
			e.printStackTrace();
		}
		return success;
	}

	public Boolean generateRefcodeReport(String filepath, String refcode) {
		boolean success = false;
		// Run cell search
		try {
			success = (boolean) analysisRpcClient.request(GENERATEREPORTREFCODE, new Object[] { filepath, refcode });
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






