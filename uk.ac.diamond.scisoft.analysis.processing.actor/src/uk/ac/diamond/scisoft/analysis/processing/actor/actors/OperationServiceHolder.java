package uk.ac.diamond.scisoft.analysis.processing.actor.actors;

import org.eclipse.dawnsci.analysis.api.processing.IOperationService;

import uk.ac.diamond.scisoft.analysis.processing.actor.Activator;

public class OperationServiceHolder {

	private static IOperationService oservice;
	
	public OperationServiceHolder() {
		
	}

	public static void setOperationService(IOperationService s) {
		oservice = s;
	}

	// Because actors can be instantiated outside OSGI by ptolemy,
	// it can be that oservice is not set
	public static IOperationService getOperationService() {
		if (oservice==null) {
			oservice = (IOperationService)Activator.getService(IOperationService.class);
		}
		return oservice;
	}
}
