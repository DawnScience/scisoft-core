package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;


/**
 * Class that wrappers the equation <br>
 * z(x, y) = a_0  + a_1 * exp( a_2 * x + a_3 * y)
 */


public class TwoDExponential implements IFunction{

	private static final String NAME = "2D exponential";
	private static final String DESC = "z(x, y) = a_0  + a_1 * exp( a_2 * x + a_3 * y)";
	private transient double[] a;
	private transient int nparams = 4;
	
	
	@Override
	public String getName() {
		return NAME;
		
	}

	@Override
	public void setName(String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return DESC;
	}

	@Override
	public void setDescription(String newDescription) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IParameter getParameter(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IParameter[] getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNoOfParameters() {
		// TODO Auto-generated method stub
		return nparams;
	}

	@Override
	public double getParameterValue(int index) {
		// TODO Auto-generated method stub
		return a[index];
	}

	@Override
	public double[] getParameterValues() {
		// TODO Auto-generated method stub
		return a;
	}

	@Override
	public void setParameter(int index, IParameter parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameterValues(double... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double val(double... values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double partialDeriv(IParameter parameter, double... values) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public IDataset calculateValues(IDataset coords) {
//		
//		IDataset[] outputArray = new IDataset[coords.length]; 
//		
//		for(IDataset r : coords){
//			IDataset output = DatasetFactory.createFromObject(r);
//		
//			for(int i = 0; i < r.getShape()[0]; i++){
//				for(int j = 0; j < r.getShape()[0]; j++){
//					double z = a[0]+a[1]*Math.exp(a[2]*i + a[3]*j);
//					output.set(z, i, j );
//					
//					
//				}
//			}
//		
//		
//		
//		}
//		
//		return null;
//	}
	
	@Override
	public IDataset calculateValues(IDataset... coords) {
		
		@SuppressWarnings("deprecation")
		IDataset output = DatasetFactory.zeros(
								new int[] {coords[0].getSize(), coords[1].getSize()}, 
								Dataset.ARRAYFLOAT64);
		
			for(int i = 0; i < coords[0].getShape()[0]; i++){
				for(int j = 0; j < coords[1].getShape()[0]; j++){
					double z = a[0]+a[1]*Math.exp(a[2]*i + a[3]*j);
					output.set(z, i, j );
				}
			}
		
		return output;
	}


	@Override
	public IDataset calculatePartialDerivativeValues(IParameter parameter, IDataset... coords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double residual(boolean allValues, IDataset data, IDataset weight, IDataset... coords) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDirty(boolean isDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMonitor(IMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMonitor getMonitor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFunction copy() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IOperator getParentOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParentOperator(IOperator parent) {
		// TODO Auto-generated method stub
		
	}

}
