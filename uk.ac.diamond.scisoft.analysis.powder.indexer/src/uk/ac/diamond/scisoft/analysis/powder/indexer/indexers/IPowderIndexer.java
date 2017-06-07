package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import java.util.List;
import java.util.Map;

import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.powder.indexer.IPowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.powder.indexer.IPowderIndexerPowderParams;

/**
 * 
 *         Interface containing methods for auto indexers require to run.
 * 
 *         Status during this run procedure can also be grabbed. The run time of
 *         these processes are undeterminate.
 *         
 * @author Dean P. Ottewell
 */
public interface IPowderIndexer extends IPowderIndexerPowderParams {

	/**
	 * Configure indexer setup to load in specified parameter sets and run
	 * styles. For indexers these range from search exhauation, error bounds and
	 * sample configuration.
	 * 
	 * Preceeds the runIndexer call by setting
	 * 
	 */
	public void configureIndexer();

	//TODO: configureIndexer to generic. Want another method for validating indexer avaliabilit
	
	public String getIndexerDirectory();
	
	public void setIndexerDirectory(String fullpath);
	
	public Boolean isIndexerAvaliable(String identifier);
	
	public void setPeakData(IDataset peakData);
	
	/**
	 * Run indexer process based on the configured setting
	 * 
	 * Extract cells from output and convert to {@link CellParameter} objects
	 * 
	 * Require set up through configureIndexer first
	 * 
	 */
	public void runIndexer();

	/**
	 * Cancel the current indexer setup that is running.
	 * 
	 * Stopping has no effect on configureIndexer. A runIndexer can be performed after.
	 * 
	 */
	public void stopIndexer();

	/**
	 * Get the run indexer components results during run indexer.
	 * 
	 * @return status line from indexer
	 */
	public String getStatus();

	/**
	 * Returns completed plausible cells indexer procedure results.
	 * 
	 * @return contains all cells that were extracted and formated from a
	 *         indexer run
	 */
	public List<CellParameter> getResultCells();

	
	
	//TODO: below is mroe something you could do if you want to ensure your validity in your indexer...
	
	/**
	 * Validation of data for autoindexer.
	 * 
	 * Ensures the data is not null, enough peaks to run and correct format
	 * 
	 * @param peakData
	 * @return true if data in correct format
	 */
	public boolean isPeakDataValid(IDataset peakData);

	
}