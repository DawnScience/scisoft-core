package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;

/*
 * Extend to interface for the python run script with handler names and that
 * should include the
 */

interface IPythonSearcherHandlers {
	// expects def isAvailiable
	// rpcserver.add_handler("AVALIABLE", isCallAvaliable)
	// TODO: redundent with the wrapper now?
	public Boolean isCallAvailable() throws AnalysisRpcException;

	// expects def findCellmatches
	// rpcserver.add_handler("FINDCELLMATCHES", searcher.findCellMatches)
	/// findCellMatches(a, b, c, alpha, beta, gamma)
	public int findCellMatches(double a, double b, double c, double alp, double bet, double gam)
			throws AnalysisRpcException;

}