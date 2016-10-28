package org.dawnsci.surfacescatter;



import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.trace.ITrace;
import org.eclipse.swt.widgets.Composite;

public class CurveStateIdentifier {
	
	public static String[] CurveStateIdentifier1(IPlottingSystem<Composite> pS){
		
		CharSequence f = "Fhkl";
		CharSequence i = "Intensity";
		CharSequence e = "Error";
		
		String[] s = new String[]{"f", "n"};
		
		try{
			for(ITrace tr : pS.getTraces()){
				if (tr.getName().contains(f)){
					s[0] = "f";
				}
				else {
					s[0] = "i";
				}
				if (tr.getName().contains(e)){
					s[1] = "e";
				}
				else {
					s[1] = "n";
				}
			}
			return s;
		}
		catch(Exception e1){
			return s;
		}
	}

}
