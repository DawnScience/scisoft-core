package uk.ac.diamond.scisoft.analysis.powder.matcher.ccdc;

/**
 * @author Dean P. Ottewell
 */
public interface ICCDCService {
	
	/*Service Components*/
	
	/**
	 * 
	 * TODO: poor mans exception to get some feedback to progress than run this ervice
	 * @throws Exception 
	 * 
	 */
	public void setUpServer() throws Exception;
	
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

