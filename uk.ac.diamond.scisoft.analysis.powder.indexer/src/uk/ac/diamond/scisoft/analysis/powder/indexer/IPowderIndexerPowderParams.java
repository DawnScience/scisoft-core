
package uk.ac.diamond.scisoft.analysis.powder.indexer;

import java.util.Map;

/**
 * TODO: docs
 * 
 * @author Dean P. Ottewell
 *
 */
public interface IPowderIndexerPowderParams {
	
	public Map<String, IPowderIndexerParam> getParameters();
	
	public IPowderIndexerParam  getParameter(String pName) throws Exception;
	
	public void setParameter(IPowderIndexerParam param, String paramName) throws Exception;
	
	public Map<String, IPowderIndexerParam> getInitialParamaters();
	
	//TODO: standard parameters setup must be defined
	//public Map<String, IPowderIndexerParam> getWavelength();
	
}
