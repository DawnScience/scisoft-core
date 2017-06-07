package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import java.io.BufferedWriter;
import java.util.List;

/**
 * 
 *         Interface for configuring a indexer that executes a external process.
 *         These indexers should be configured to gather progress asynchronously.
 *
 * @author Dean P. Ottewell
 */
public interface IPowderProcessingIndexer {

	/**
	 * Creates a formated file that can then be inputed and interpreted by the
	 * indexer process.
	 * 
	 * @param inputDataSavePath
	 *            to where to save process input file
	 */
	public void generateIndexFile(String inputDataSavePath);

	/**
	 * Specific communicated lines sent to a running indexer process to execute
	 * on.
	 * 
	 * @param bw
	 *            attached to a indexer process
	 * @param relativeDataPath
	 *            pointing the indexer comms to input and output file store
	 *            location
	 */
	public void commsSpecificIndexer(BufferedWriter bw, String relativeDataPath);

	/**
	 * Gets resulted output file by the indexing process.
	 * 
	 * Path dependent on indexer extension and configuration. 
	 * 
	 * getResultsDataPath can lead to deducing this path.
	 * 
	 * @param resultFilePath
	 * 
	 * @return extracted cells extracted from the runIndex result file
	 */
	public List<CellParameter> extractResults(String resultFilePath);

	/**
	 * Retrieves the formated path and extensions for output file of indexer
	 * process.
	 * 
	 * @return path configured with indexer extension
	 */
	public String getResultsDataPath();

}
