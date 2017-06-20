package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Crystal;
import uk.ac.diamond.scisoft.analysis.powder.indexer.indexers.CellParameter;
import uk.ac.diamond.scisoft.analysis.processing.python.AbstractPythonScriptOperation;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.dawb.common.util.eclipse.BundleUtils;

import uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc.Activator;

import org.dawnsci.python.rpc.AnalysisRpcPythonPyDevService;
import org.dawnsci.python.rpc.AnalysisRpcPythonService;
import org.dawnsci.python.rpc.IPythonRunScript;
import org.dawnsci.python.rpc.PythonRunScriptService;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcRemoteException;
import org.eclipse.dawnsci.analysis.api.rpc.IAnalysisRpcPythonService;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.internal.theme.Theme;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Cambridge Crystallographic Data Centre wrapper to communicate with xmlrpc Python interface.
 *
 * TODO: send crystal system
 *TODO: set port java scide and take as argument
 * @author Dean P. Ottewell
 */
public class CCDCService implements ICCDCService {

	private static final Logger logger = LoggerFactory.getLogger(CCDCService.class);

	public static final String ID = "CCDC";

	protected URL urlCellSearchHandler = getClass().getResource("python/cellSearchHandler.py");

	
	private static final String SCRIPTPATH = "/uk/ac/diamond/scisoft/analysis/powder/matcher/ccdc/python/";
	private static final String PYTHONSCRIPTHANDLER = "cellSearchHandler.py";
	
	
	private PythonRunInfo server;

	private AnalysisRpcClient analysisRpcClient;

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
	
	public CCDCService() {
		//setUpServer();
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
		
		//Destroy the python interpretor
		rpcservice.stop();
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
	public void setUpServer() throws Exception {
			
		try {
//			try {
//				rpcservice = AnalysisRpcPythonPyDevService.create();
//			
//
//				
//				Map<String,Object> inputs = new HashMap<>();
//				//Map<String, String> in = new Map<String, String>();
//
//				//Cast below into runner - need a way to destroy...
//				 Runnable r = new Runnable() {
//			         public void run() {
//						try {
//							serviceSearcher = new PythonRunSearcherService(rpcservice);
//						} catch (AnalysisRpcException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				     }
//			         };
//
//			     Thread pythonServer = new Thread(r);
//			     pythonServer.start();
//			     pythonServer.setName("Core CCDC service server");
//			     pythonServer.interrupt();
//				
//			} catch (Exception e) {
//				logger.debug("Could not create script service: " +e);
//			}		
//			
//			/if (rpcservice == null || pythonRunScriptService == null) throw new Exception("Could not create python interpreter");

//			if (script == null || script.isEmpty()) throw new Exception("Path to script not set");
//			
//					
					
			File bundlePath = BundleUtils.getBundleLocation(Activator.PLUGIN_ID);
			
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
		//	if (scriptAbsPath == null || scriptAbsPath.isEmpty()) throw new Exception("Path to script not set");
			
			try {
				rpcservice = AnalysisRpcPythonPyDevService.create();
				
				pythonRunScriptService = new PythonRunScriptService(rpcservice);
			} catch (Exception e) {
				//System.out.println(e);
				logger.debug("Server already started" +e);
				//this, "Could not create script service!");
			}		
			
			//if (rpcservice == null || pythonRunScriptService == null) throw new Exception("Could not create python interpreter");
			
			
			

			
			Map<String,Object> inputs = new HashMap<>();
			//Cast below into runner - need a way to destroy...
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
//		     

		     
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

		} catch (Exception e) {
			logger.debug("Was not able to start" + urlCellSearchHandler.getFile() + e);
			terminateServer();
			throw new Exception(e);
//			e.printStackTrace();
		}
		
		
		//Connect analysis seperatte
		
		analysisRpcClient = new AnalysisRpcClient(PORT);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	
	public Boolean setSearchLattice(Crystal crystal){
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



//TODO: wanted to extend this
class PythonRunSearcherService implements IPythonRunScript {

	private static final String SCRIPTPATH = "/uk/ac/diamond/scisoft/analysis/powder/matcher/ccdc/python/";
	
	private static final String PYTHONSERVICESCRIPTHANDLER = "cellSearchHandler.py";
	
	private static final Logger logger = LoggerFactory.getLogger(PythonRunSearcherService.class);
	
	private IPythonRunScript proxy;
	
	/**
	 * Create a new proxy and register the runScript code. Calls to runScript
	 * are run the client's {@link AnalysisRpcClient#request(String, Object[])}
	 * (as opposed to
	 * {@link AnalysisRpcClient#request_debug(String, Object[], boolean)}.
	 * 
	 * @param rpcservice
	 *            the running service to register with
	 * @throws IOException
	 *             if there is a problem resolving the location of
	 *             {@value #PYTHON_SERVICE_RUNSCRIPT_PY}
	 * @throws AnalysisRpcException
	 *             if there is a problem registering the handler
	 */
	public PythonRunSearcherService(IAnalysisRpcPythonService rpcservice)
			throws IOException, AnalysisRpcException {
		this(rpcservice, false);
	}
	
	/**
	 * Create a new proxy and register the runScript code.
	 * 
	 * @param rpcservice
	 *            the running service to register with
	 * @param debug
	 *            if true uses's the client's
	 *            {@link AnalysisRpcClient#request_debug(String, Object[], boolean)}
	 *            , if false uses
	 *            {@link AnalysisRpcClient#request(String, Object[])}
	 * @throws IOException
	 *             if there is a problem resolving the location of
	 *             {@value #PYTHON_SERVICE_RUNSCRIPT_PY}
	 * @throws AnalysisRpcException
	 *             if there is a problem registering the handler
	 */
	public PythonRunSearcherService(IAnalysisRpcPythonService rpcservice,
			boolean debug) throws IOException, AnalysisRpcException {
		this(rpcservice, debug, false);
	}

	/**
	 * Create a new proxy and register the runScript code.
	 * 
	 * @param rpcservice
	 *            the running service to register with
	 * @param debug
	 *            if true uses's the client's
	 *            {@link AnalysisRpcClient#request_debug(String, Object[], boolean)}
	 *            , if false uses
	 *            {@link AnalysisRpcClient#request(String, Object[])}
	 * @param skipAddHandler
	 *            if true, does not add the handler to the server. This should
	 *            only be true in cases where the handler has already been added
	 * @throws IOException
	 *             if there is a problem resolving the location of
	 *             {@value #PYTHON_SERVICE_RUNSCRIPT_PY}
	 * @throws AnalysisRpcException
	 *             if there is a problem registering the handler
	 */
	public PythonRunSearcherService(IAnalysisRpcPythonService rpcservice,
			boolean debug, boolean skipAddHandler) throws IOException,
			AnalysisRpcException {
		if (!skipAddHandler) {
			File bundlePath = BundleUtils.getBundleLocation(Activator.PLUGIN_ID);
			
			String script = bundlePath.getAbsolutePath() + SCRIPTPATH
					+ PYTHONSERVICESCRIPTHANDLER;
			
			if (!new File(script).exists()) {
				script = bundlePath.getAbsolutePath() + "/src" + SCRIPTPATH
						+ PYTHONSERVICESCRIPTHANDLER;
				if (!new File(script).exists()) {
					throw new RuntimeException("Couldn't find path to "
							+ PYTHONSERVICESCRIPTHANDLER + "!");
				}
			}
			
			
			//rpcservice.addHandlers( bundlePath.getAbsolutePath(), new String[]{});
			
//			rpcservice.addHandlers("execfile(r'" + getSearcherPath() + "')",
//					new String[] {  }); //TODO: extra handler?
			
			// script has a function definition called "runScript", add a
			// handler for it, then create a proxy to run the function
			rpcservice.addHandlers("execfile(r'" + script + "')",
					new String[] {  }); //TODO: extra handler?
		}
		proxy = rpcservice.getClient().newProxyInstance(IPythonRunScript.class,
				debug);
	}
	
	@Override
	public Map<String, Object> runScript(String scriptFullPath,
			Map<String, ?> data) throws AnalysisRpcException {
		return proxy.runScript(scriptFullPath, data);
	}

	@Override
	public Map<String, Object> runScript(String scriptFullPath,
			Map<String, ?> data, String funcName)
			throws AnalysisRpcException {
		return proxy.runScript(scriptFullPath, data, funcName);
	}

	/**
	 * Formats a remote exception to limit the Python code that was not "users" code. 
	 * @param e Remote Exception to format
	 * @return a Python style exception format
	 */
	public String formatException(AnalysisRpcRemoteException e) {
		return e.getPythonFormattedStackTrace(PYTHONSERVICESCRIPTHANDLER);
	}
	
	
	private static Map<String, String> updatePythonPathForCCDCModules(Map<String, String> env) {

		// To support this flow, we need both Diamond and PyDev's python
		// paths in the PYTHONPATH. We add the expected ones here.
		// NOTE: This can be problematic in cases where the user really
		// wanted a different Diamond or PyDev python path. Therefore we
		// force the paths in here.
		// TODO consider if scisoftpath should be added
		// in AnalysisRpcPythonService instead
		String path = env.get("PYTHONPATH");
		if (path == null) {
			path = "";
		}
		StringBuilder orignalsearcherPath = new StringBuilder(path);
		if (orignalsearcherPath.length() > 0) {
			orignalsearcherPath.append(File.pathSeparator);
		}

		String searcherPath = getSearcherPath();
		if (searcherPath != null) {
			orignalsearcherPath.append(searcherPath).append(File.pathSeparator);
			orignalsearcherPath.append(searcherPath + "/src").append(File.pathSeparator);
		}

		env.put("SEARCHERS", orignalsearcherPath.toString());

		return env;
	} 
	
	private static String getSearcherPath() {
		
		File bundlePath = null;
		try {
			bundlePath = BundleUtils.getBundleLocation(Activator.PLUGIN_ID);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String path = bundlePath.getAbsolutePath() + SCRIPTPATH
				+ "cellSearcher.py";
		
		if (!new File(path).exists()) {
			path = bundlePath.getAbsolutePath() + "/src" + SCRIPTPATH
					+ "cellSearcher.py";
			if (!new File(path).exists()) {
				throw new RuntimeException("Couldn't find path to "
						+ "cellSearcher.py" + "!");
			}
		}
		
		return path;
	}

}

//TODO: register all handlers on  this side of the the srvice


//class PythonServerServie {
//	  private volatile Thread blinker;
//
//    public void stop() {
//        blinker = null;
//    }
//
//    public void run() {
//        Thread thisThread = Thread.currentThread();
//        while (blinker == thisThread) {
//            try {
//                thisThread.sleep(interval);
//            } catch (InterruptedException e){
//            }
//        }
//    }
//}