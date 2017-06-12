package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.powder.indexer.IPowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.powder.indexer.PowderIndexerParam;

/**
 * Utilises Ntreor indexer
 *
 * Produce indexed file extensions are *.short. *.imp, *.con Plausible cells are
 * extracted from *.short
 *
 * Summary of input line used to Ntreor:
 *
 * Maximum Volume (A^3): 4000 -VOL is key, default to 4000 Maximum a,b,c: 35000
 * -KS ~ Max impurity lines: 1 -believe this result of limit Min
 * figure-of-merit: -MERIT is key, default 10
 *
 * Molecular weight: MOLW - depends on DENS use Measured density: DENS = 0 (No
 * density used) Error on density: EDENS - depends on DENS use
 *
 * See Ntreor documentation for further input file and output file information.
 * 
 * 
 * TODO: too few lines monoclinic test... `1
 * 
 * @author Dean P. Ottewell
 */
public class Ntreor extends AbstractPowderIndexerProcess {

	private final Logger logger = LoggerFactory.getLogger(Ntreor.class);

	public static final String ID = "Ntreor";

	private static String CELLFILEINDETIFIER = "  Cell # ";// "The
																									// following
																									// cell
																									// has
																									// been
																									// selected
																									// for
																									// refinement
																									// by
																									// PIRUM:";

	private static String BINNAME = "ntreor_new";

	public Ntreor() {

		isIndexerAvaliable(BINNAME);
		// TODO: setters in the below components
		binName = BINNAME;
		resultsExtension = ".short";
	}
	
	@Override
	public String getPowderRoutineID() {
		return ID;
	}
	
	public boolean isPeakDataValid(IDataset peakData) {
		boolean isValid = true;

		if (peakData.getSize() < 10) {
			logger.debug("to few data lines to index on");
			isValid = false;
		}

		return isValid;
	}

	public void generateIndexFile(String fullPathFile) {
		try {
			PrintWriter writer = new PrintWriter(fullPathFile + ".dat", "UTF-8");

			writer.println(outFileTitle);

			// write in the peak data
			for (int i = 0; i < peakData.getSize(); ++i) {
				double d = peakData.getDouble(i);
				writer.println(String.valueOf(d));
			}

			writer.println();

			IPowderIndexerParam kp = parameters.get("Choice");
			parameters.remove("Choice");
			String formated = kp.getName() + "=" + kp.getValue().intValue() + ",";
			writer.println(formated);

			for (Entry<String, IPowderIndexerParam> entry : parameters.entrySet()) {
				PowderIndexerParam param = (PowderIndexerParam) entry.getValue();

				String key = param.getName();
				double value = param.getValue().doubleValue();
				formated = key + "=" + value + ",";

				writer.println(formated);
			}

			// finish file
			writer.println("END*");
			// writer.println("0.00");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<CellParameter> extractResults(String resultFilePath) {

		// List<String> rawIndexes = extractRawCellInfo(resultFilePath,
		// CELLFILEINDETIFIER, 3);

		//TODO: move to filescanner
//		Scanner filescanner = null;
//		try {
//			filescanner = new Scanner(new File(resultFilePath));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			logger.debug("File not avalaible: " + resultFilePath);
//			e1.printStackTrace();
//		}
//
//		// filescanner.findInLine(CELLFILEINDETIFIER);
//
//		List<String> rawUnitCellResults = new ArrayList<String>(); // Some
//																	// output
//																	// has
//		while (filescanner.hasNextLine()) {
//			Scanner lineScanner = new Scanner(filescanner.nextLine());
//			if (lineScanner.hasNext(CELLFILEINDETIFIER)) {
//				
//				lineScanner = new Scanner(filescanner.nextLine());
//	
//				String id = "Crystal system: ";
//				String rawcrystalsys = filescanner.next(id);
//				String crystalSystem = rawcrystalsys.substring(id.length() + 1, rawcrystalsys.length()); // Until
//				// reach
//				// space
//	
//				Map<String, Double> cellParam = new HashMap<String, Double>();
//				for (int i = 0; i < 3; ++i) {
//					String unitCellLine = filescanner.nextLine();
//					cellParam = extractDataKeyVal(unitCellLine);
//				}
//	
//				Scanner findLine = new Scanner(filescanner.findInLine("  M(20)"));
//	
//				// use the lineScanner to extract tokens from the line
//				Scanner meritLine = findLine.useDelimiter("=");
//	
//				lineScanner.close();
//			}
//		}
//		filescanner.close(); // likely done in a finally block after null check

		List<String> rawIndexes = new ArrayList<String>(); // Some output has
		// order value
		try {
			BufferedReader br = new BufferedReader(new FileReader(resultFilePath));
			String line = null; // line variable

			while ((line = br.readLine()) != null) {
				//a = a.replaceAll("\\s","") remove spaces
				
				// Process line based of core cell params
				if (line.contains(CELLFILEINDETIFIER)) {

					String rawInfoLines = "";

					br.readLine();
					br.readLine();
					rawInfoLines  += br.readLine();
					
					br.readLine();
					//Map<String, Double> cellParam = new HashMap<String, Double>();
					for (int i = 0; i < 6; ++i) {
						String unitCellLine = br.readLine();
						//cellParam.putAll(extractDataKeyValunitCellLine));
						rawInfoLines += unitCellLine;
					}
					rawIndexes.add(rawInfoLines);
					
//					br.skip(3);
//					String rawmerit = br.readLine();
//		
					//Scanner findLine = new Scanner(filescanner.findInLine("  M(20)"));
		
					// use the lineScanner to extract tokens from the line
					//Scanner meritLine = findLine.useDelimiter("=");

				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String i : rawIndexes) {

			String id = "Crystal system: ";
			String crystalSys = i.substring(id.length() + 1, i.length()); // Until
																			// reach
																			// space
			Map<String, Double> raw = extractDataKeyVal(i);

			double a = raw.get("A");
			double b = raw.get("B");
			double c = raw.get("C");
			double al = raw.get("Alpha");
			double be = raw.get("Beta");
			double ga = raw.get("Gamma");

			// Extract & set figure of merit
			double merit = (Double) raw.get("M(20)");

			CellParameter cell = new CellParameter(a, b, c, al, be, ga, merit,this.ID);

			plausibleCells.add(cell);
		}

		return this.plausibleCells;
	}

	private Map<String, Double> extractDataKeyVal(String rawCellData) {

		Map<String, Double> cellParam = new HashMap<String, Double>();
		// Cell unit data + merit
		String[] out = rawCellData.split("\\s+");
		for (int i = 0; i < out.length; ++i) {
			if (out[i].equals("=")) {
				cellParam.put(out[i - 1], Double.parseDouble(out[i + 1]));
				++i;
			}
		}
		return cellParam;
	}

	@Override
	public void commsSpecificIndexer(BufferedWriter bw, String path) {
		// Communication need when executing process
		try {
			// General information ?[Y / N, (default=N) ]
			bw.write("N\n");
			// Input file......:
			bw.write(path + "/" + outFileTitle + ".dat" + "\n");
			// Output file.....:
			bw.write(path + "/" + outFileTitle + ".imp" + "\n");
			// Condensed output file.....:
			bw.write(path + "/" + outFileTitle + ".con" + "\n");
			// Short output file ........:
			bw.write(path + "/" + outFileTitle + ".short" + "\n");
			// Theta-shift (irrelevant if LIMIT=1) :
			bw.write("1\n");
			// Do you want to have the possibility to stop the iterated N-TREOR
			// run ?
			// Y makes it possible to avoid the triclinic tests.
			// [Y / N, (default=N) ]
			bw.write("N\n");
			bw.flush();

		} catch (IOException e) {
			logger.debug("Ntreor was unable to run ");
			e.printStackTrace();
		}
	}

	@Override
	public String getResultsDataPath() {
		return fileOutPath + outFileTitle + ".short";
	}

	@Override
	public Map<String, IPowderIndexerParam> getInitialParamaters() {
		Map<String, IPowderIndexerParam> intialParams = new TreeMap<String, IPowderIndexerParam>();
		intialParams.put(StandardConstantParameters.wavelength, new PowderIndexerParam("wave", new Double(0.49481)));
		intialParams.put(StandardConstantParameters.maxVolume, new PowderIndexerParam("VOL", new Double(5000)));
		// intialParams.put("limit", new NtreorParam("LIMIT", new Double(1)));
		intialParams.put("Merit Limit", new PowderIndexerParam("merit", new Double(10)));
		intialParams.put("Choice", new PowderIndexerParam("choice", new Integer(3))); //

		// TODO: crystal search intial paramters
		// Cant pass all the crystal choices... only cancel triclinic search

		return intialParams;
	}

}
