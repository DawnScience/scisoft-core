package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.trace.ITrace;
import org.eclipse.swt.widgets.Composite;

public class CurveStateIdentifier {
	
	public static String CurveStateIdentifier1(IPlottingSystem<Composite> pS){
		
		CharSequence f = "Fhkl";
		CharSequence i = "Intensity";
		
		int det = 0;
		
		
		try{
			for(ITrace tr : pS.getTraces()){
				if (tr.getName().contains(f)){
					det +=1;
				}
				else{
					det-=1;
				}
			}
			
			if(det>=0){
				return "f";
			}
			else{
				return "i";
			}
		}
		catch(Exception e){
			return "f";
		}
	}

}
