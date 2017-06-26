package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

import java.io.File;
import java.io.IOException;

import org.dawb.common.util.eclipse.BundleUtils;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcRemoteException;
import org.eclipse.dawnsci.analysis.api.rpc.IAnalysisRpcPythonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;

//TODO: wanted to extend this
public class PythonRunSearcherService implements IPythonSearcherHandlers {

	private static final String SCRIPTPATH = "/uk/ac/diamond/scisoft/analysis/powder/matcher/ccdc/";

	private static final String PYTHONSERVICESCRIPTHANDLER = "cellSearcherService.py";

	private static final Logger logger = LoggerFactory.getLogger(PythonRunSearcherService.class);

	private IPythonSearcherHandlers proxy;
	
	IAnalysisRpcPythonService rpcservice; //TODO: shoukd not store her

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
	public PythonRunSearcherService(IAnalysisRpcPythonService rpcservice) throws IOException, AnalysisRpcException {
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
	public PythonRunSearcherService(IAnalysisRpcPythonService rpcservice, boolean debug)
			throws IOException, AnalysisRpcException {
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
	public PythonRunSearcherService(IAnalysisRpcPythonService rpcservice, boolean debug, boolean skipAddHandler)
			throws IOException, AnalysisRpcException {
		
		this.rpcservice = rpcservice;
		if (!skipAddHandler) {
			File bundlePath = BundleUtils.getBundleLocation(Activator.PLUGIN_ID);

			String script = bundlePath.getAbsolutePath() + SCRIPTPATH + PYTHONSERVICESCRIPTHANDLER;

			if (!new File(script).exists()) {
				script = bundlePath.getAbsolutePath() + "/src" + SCRIPTPATH + PYTHONSERVICESCRIPTHANDLER;
				if (!new File(script).exists()) {
					throw new RuntimeException("Couldn't find path to " + PYTHONSERVICESCRIPTHANDLER + "!");
				}
			}

			// rpcservice.addHandlers( bundlePath.getAbsolutePath(), new
			// String[]{});

			// rpcservice.addHandlers("execfile(r'" + getSearcherPath() + "')",
			// new String[] { }); //TODO: extra handler?

			// script has a function definition called "runScript", add a
			// // handler for it, then create a proxy to run the function
			// rpcservice.addHandlers("execfile(r'" + script + "')",
			// new String[] { }); //TODO: extra handler?

			// TODO: Handlers added for addition of own interface
			rpcservice.addHandlers("execfile(r'" + script + "')",
					new String[] { "isCallAvailable", "findCellMatches" }); // TODO:
																			// extra
																			// handler?

		}

		// As long as proxy is made for the implemented interface all the
		// functions should be avalaible.
		// That if the functions are matching
		proxy = rpcservice.getClient().newProxyInstance(IPythonSearcherHandlers.class, debug);
	}

	/**
	 * Formats a remote exception to limit the Python code that was not "users"
	 * code.
	 * 
	 * @param e
	 *            Remote Exception to format
	 * @return a Python style exception format
	 */
	public String formatException(AnalysisRpcRemoteException e) {
		return e.getPythonFormattedStackTrace(PYTHONSERVICESCRIPTHANDLER);
	}

	@Override
	public Boolean isCallAvailable() throws AnalysisRpcException {
		return proxy.isCallAvailable();
	}

	@Override
	public int findCellMatches(double a, double b, double c, double alp, double bet, double gam)
			throws AnalysisRpcException {
		
     rpcservice.getClient().getPort();
     
		
		
		IPythonSearcherHandlers newProxyInstance = rpcservice.getClient().newProxyInstance(IPythonSearcherHandlers.class,false);
		rpcservice.getClient().newProxyInstance(IPythonSearcherHandlers.class);
		final Object newOut = newProxyInstance.findCellMatches(a, b, c, alp, bet, gam);
		
		final Object out =  proxy.findCellMatches(a, b, c, alp, bet, gam);//this.rpcservice.getClient().request("findCellMatches", new Object[]{a, b, c, alp,bet,gam}); 
		
		//rpcservice.addHandler("findCellMatches", "noParm");
		
		// Calls the method 'runScript' in the script with the arguments
	    return (int) out;
		//return proxy.findCellMatches(a, b, c, alp, bet, gam);
	}

}
