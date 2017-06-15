package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *         Abstract class utilising process calls for established powder indexers.
 * 
 *         Configuration for executables different binary locations.
 * 
 * 
 *         Reads in streams for both writing to and reading from the indexer
 *         process.
 *
 * 		   @author Dean P. Ottewell
 */
public abstract class AbstractPowderIndexerProcess extends AbstractPowderIndexer implements IPowderProcessingIndexer {

	protected final Logger logger = LoggerFactory.getLogger(AbstractPowderIndexerProcess.class);
	
	protected String fileOutPath = System.getProperty("java.io.tmpdir") + "/"; //- TODO: until can properly kill process if i cancel during dev shouldn't place here

	protected static String binName = null;

	protected String resultsExtension = ".dat";

	protected String outFileTitle = "tmpSet";
	
	// -- Calling Process Configuration --/
	ProcessBuilder procBuilder;
	Process indexerProcess;
	BufferedReader readerIndexer;
	DataOutputStream outputIndexer;
	BufferedWriter writerIndexer;
	BufferedReader errIndexer;

	List<String> rawCommsOutput = new ArrayList<String>();

	private void startProcessStream() {

		try {
			indexerProcess = procBuilder.start();
		} catch (IOException e1) {
			logger.debug("Indexer process unable to start");
			e1.printStackTrace();
		}

		try {
			// Grab streams from process once started
			DataInputStream in = new DataInputStream(indexerProcess.getInputStream());
			readerIndexer = new BufferedReader(new InputStreamReader(in));

			outputIndexer = new DataOutputStream(indexerProcess.getOutputStream());
			writerIndexer = new BufferedWriter(new OutputStreamWriter(outputIndexer));

			DataInputStream errIn = new DataInputStream(indexerProcess.getErrorStream());
			errIndexer = new BufferedReader(new InputStreamReader(errIn));

		} catch (Exception e) {
			logger.error("processIndexing setting up comms failed" + e);
		}

	}

	@Override
	public void configureIndexer() {
		String fullPath = fileOutPath + outFileTitle;

		generateIndexFile(fullPath);

		procBuilder = new ProcessBuilder(indexerDirectory + binName);
	}

	private String processIndexOutput() {

		String status = null;

		// Check on process status
//		if (!indexerProcess.isAlive()) {
//			logger.debug("process is no longer active");
//		}

		try {
			if ((status = readerIndexer.readLine()) != null) {
				logger.info(status);
			}
		} catch (IOException e) {
			logger.debug("Auto indexer reader process has stopped or has nothing to read");
		}

		return status;
	}
	
	@Override
	public String getStatus() {
		String status = null;
		status = processIndexOutput();
		if (status == null) {
			try {
				while ((status = errIndexer.readLine()) != null) {
					rawCommsOutput.add(status);
					logger.error("Executable error: ", status);
				}
			} catch (IOException e) {
				logger.error("Uanble to read error comms");
			}
		}
		return status;
	}

	@Override
	public void runIndexer() {

		startProcessStream();

		String relativePath = Paths.get(System.getProperty("user.dir") + "/").relativize(Paths.get(fileOutPath))
				.toString();

		commsSpecificIndexer(writerIndexer, relativePath);

		// once comms done, close the output
		try {
			outputIndexer.flush();
			writerIndexer.flush();
			outputIndexer.close();
			writerIndexer.close();
		} catch (IOException e1) {
			logger.error("Unable to close indexer comms writer");
			e1.printStackTrace();
		}
	}

	@Override
	public void stopIndexer() {
		// Close stream manually first if they are not already closed
		try {
			writerIndexer.close();
			readerIndexer.close();
			outputIndexer.close();
			errIndexer.close();
		} catch (IOException e) {
			logger.debug("Closing streams unaccesible");
			e.printStackTrace();
		}
		indexerProcess.destroy();
		indexerProcess.destroyForcibly();
	}

	@Override
	public List<CellParameter> getResultCells() {
		return extractResults(fileOutPath + outFileTitle + resultsExtension);
	}
	

	public String getOutFileTitle() {
		return outFileTitle;
	}


	/*
	 * Helper Functions
	 */

	/**
	 * Extract raw segments of a index produced file based on a specific
	 * identifier and number of important lines after
	 * 
	 * @param filepath
	 * @param identifier
	 *            to search file for
	 * @param linesOfInterest
	 *            after the to extracts
	 * @return container of lines extracted that match the search criteria
	 */
	protected static List<String> extractRawCellInfo(String filepath, String identifier, int linesOfInterest) {
		List<String> rawIndexes = new ArrayList<String>(); // Some output has
															// order value
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line = null; // line variable

			while ((line = br.readLine()) != null) {
				// Process line based of core cell params
				if (line.contains(identifier)) {
					String newline = "";
					for (int i = 0; i < linesOfInterest; ++i) {
						line = br.readLine(); // grab the range of lines after
						newline += line;
					}
					rawIndexes.add(newline);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rawIndexes;
	}
	
	@Override
	public Boolean isIndexerAvaliable(String identifier) {
		String path = System.getenv(identifier);
		if(path != null){
			setIndexerDirectory(path);
			return true;
		}
		return false;
	}

}
