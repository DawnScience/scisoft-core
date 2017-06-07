package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

/**
 * @author Dean P. Ottewell
 */
public interface ICCDCService {
	
	/*Service Components*/
	
	/**
	 * 
	 */
	public void setUpServer();
	
	/**
	 * 
	 */
	public void terminateServer();
	
	/**
	 * @return
	 */
	public Boolean serverAvaliable();

	/*Searching Components*/
	
	
	/**
	 * @return
	 */
	public Boolean runCrystalSearch();

	
	
	/**
	 * @return
	 */
	public Object gatherMatches();

}

