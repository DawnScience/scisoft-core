package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.powder.indexer.IPowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.powder.indexer.PowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Lattice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 *         Utilises Dicvol indexer
 *
 *         Produces two indexed file and one a ordered *.ord type Plausible
 *         cells are extracted from *.ord
 *
 *         See Dicvol documentation for further input file and output file
 *         information

 * @author Dean P. Ottewell
 */
public class Dicvol extends AbstractPowderIndexerProcess implements IPowderProcessingIndexer {

	private static final Logger logger = LoggerFactory.getLogger(Dicvol.class);
	
	public static final String ID = "Dicvol";
	
	private static String CELLFILEINDETIFIER = "REDUCED CELL :";
	
	private static String BINNAME = "dicvol";
	
	public Dicvol() {
		this.binName = BINNAME;
		this.resultsExtension = ".ord";
		//Set params
	}
	
	@Override
	public String getPowderRoutineID() {
		return ID;
	}
	
	// Intial keys and values. Insertion order is important to file created.
	// Also included defaults
	// See dicvol.html for details
	private static LinkedHashMap<String, String> stdKeyval = new LinkedHashMap<String, String>() {
		{
			// CARD 2 N,ITYPE,JC,JT,JH,JO,JM,JTR -8 FREE FORMAT
			put("N", "20");
			// N NUMBER OF LINES USED FOR SEARCHING SOLUTIONS.
			// (This number is, generally, lower than the
			// number N_TOTAL of input lines). e.g. N = 20.
			put("ITYPE", "2"); // 2-theta default
			// ITYPE SPACING DATA TYPE.
			// =1 THETA BRAGG ANGLE IN DEGREES.
			// =2 2-THETA ANGLE IN DEGREES.
			// =3 D-SPACING IN ANGSTROM UNIT.
			// =4 Q SPECIFIED IN Q-UNITS AS E+04/D**2.
		
			put("JC", "1");
			// JC =0 CUBIC SYSTEM IS NOT TESTED.
			// =1 CUBIC SYSTEM IS TESTED.
			put("JT", "1");
			// JT =0 TETRAGONAL SYSTEM IS NOT TESTED.
			// =1 TETRAGONAL SYSTEM IS TESTED.
			put("JH", "1");
			// JH =0 HEXAGONAL SYSTEM IS NOT TESTED.
			// =1 HEXAGONAL SYSTEM IS TESTED.
			put("JO", "1");
			// JO =0 ORTHORHOMBIC SYSTEM IS NOT TESTED.
			// =1 ORTHORHOMBIC SYSTEM IS TESTED.
			put("JM", "1");
			// JM =0 MONOCLINIC SYSTEM IS NOT TESTED.
			// =1 MONOCLINIC SYSTEM IS TESTED.
			put("JTR", "0");
			// JTR =0 TRICLINIC SYSTEM IS NOT TESTED.
			// =1 TRICLINIC SYSTEM IS TESTED.

			// 0. sets to default
			// CARD 3 AMAX,BMAX,CMAX,VOLMIN,VOLMAX,BEMIN,BEMAX-7 FREE FORMAT
			put("AMAX", "0.");
			// AMAX MAXIMUM VALUE OF UNIT CELL DIMENSION A, IN ANGSTROMS.
			// (IF AMAX= 0.0 DEFAULT= 25. ANGSTROMS)
			put("BMAX", "0.");
			// BMAX MAXIMUM VALUE OF UNIT CELL DIMENSION B, IN ANGSTROMS.
			// (IF BMAX= 0.0 DEFAULT= 25. ANGSTROMS)
			put("CMAX", "0.");
			// CMAX MAXIMUM VALUE OF UNIT CELL DIMENSION C, IN ANGSTROMS.
			// (IF CMAX= 0.0 DEFAULT= 25. ANGSTROMS)
			put("VOLMIN", "0.");
			// VOLMIN MINIMUM VOLUME FOR UNIT CELLS IN ANGSTROMS**3.
			put("VOLMAX", "0."); // TODO: default 4000
			// VOLMAX MAXIMUM VOLUME FOR UNIT CELLS IN ANGSTROMS**3.
			// (IF VOLMAX= 0.0 DEFAULT= 2500. ANGSTROMS**3)
			put("BEMIN", "0."); // TODO: default 90.
			// BEMIN MINIMUM BETA ANGLE FOR MONOCLINIC CELLS IN DEGREES
			// (IF BEMIN= 0.0 DEFAULT= 90. DEGREES).
			put("BEMAX", "0."); // TODOL default 125.
			// BEMAX MAXIMUM BETA ANGLE FOR MONOCLINIC CELLS IN DEGREES
			// (IF BEMAX= 0.0 DEFAULT= 125. DEGREES).

			// CARD 4 WAVE,POIMOL,DENS,DELDEN-4 FREE FORMAT
			put("WAVE", "0."); // TODO: Parametrise wavelength
			// WAVE WAVELENGTH IN ANGSTROMS (DEFAULT=0.0 IF CU K ALPHA1).
			put("POIMOL", "0.");
			// POIMOL MOLECULAR WEIGHT OF ONE FORMULA UNIT IN A.M.U.
			// (DEFAULT =0.0 IF FORMULA WEIGHT NOT KNOWN).
			put("DENS", "0.");
			// DENS MEASURED DENSITY IN G CM(**-3)
			// (DEFAULT =0.0 IF DENSITY NOT KNOWN).
			put("DELDEN", "0.");
			// DELDEN ABSOLUTE ERROR IN MEASURED DENSITY.
			// (DEFAULT =0.0 IF DENSITY NOT KNOWN).

			// CARD 5 EPS,FOM,N_IMP,ZERO_S,ZERO_REF,OPTION-6 FREE FORMAT
			put("EPS", "0.03"); // Defaults 0.03 when 2THETA used
			// EPS =0.0 THE ABSOLUTE ERROR ON EACH OBSERVED LINE
			// IS TAKEN TO 0.03 DEG. 2THETA (DEFAULT VALUE)
			// WHATEVER THE SPACING DATA TYPE (ITYPE IN CARD 2).
			// =1.0 THE ABSOLUTE ERROR ON EACH OBSERVED LINE IS
			// INPUT INDIVIDUALLY FROM CARD 6, AFTER THE
			// OBSERVED 'D(I)' ON THE SAME LINE, ACCORDING
			// TO THE SPACING DATA UNIT (e.g. 18.678 0.018 in
			// deg. 2theta)
			// EPS NE 0.0 AND 1.0
			// THE ABSOLUTE ERROR IS TAKEN AS A CONSTANT
			// (= EPS),IN DEG. 2THETA, WHATEVER THE SPACING
			// DATA TYPE (ITYPE IN CARD 2) (e.g. 0.02, which will
			// apply to all input lines).

			put("FOM", "0."); // TODO: Default 10
			// FOM LOWER FIGURE OF MERIT M(N) REQUIRED FOR PRINTED
			// SOLUTION(S) (DEFAULT=0.0 M(N)=10.0).
			put("N_IMP", "0");
			// N_IMP MAXIMUM NUMBER OF IMPURITY/SPURIOUS LINES ACCEPTED AMONG
			// THE FIRST N LINES [N_IMP takes into account both
			// impurity lines and peak positions out of the input
			// absolute angular error EPS].
			// IF N_IMP <0 THE SEARCH STARTS WITH ZERO IMPURITY LINES,
			// THEN, IT CONTINUES WITH ONE IMPURITY LINE, AND
			// SO ON UNTIL 'N_IMP' IMPURITY LINES IS REACHED.
			put("ZERO_S", "0");
			// ZERO_S A PRIORI SEARCH FOR A ZERO-POINT ERROR IN INPUT DATA.
			// =0 NO SEARCH
			// =1 SEARCH
			// IF ZERO_S NE 0 OR 1, THEN ZERO_S REPRESENTS A KNOWN
			// ZERO CORRECTION (e.g. -0.10) IN DEG. 2THETA.
			put("ZERO_REF", "0");
			// ZERO_REF =0 NO 'ZERO-POINT' LEAST-SQUARES REFINEMENT.
			// =1 'ZERO-POINT' LEAST-SQUARES REFINEMENT.
			//
			put("OPTION", "0"); // exhaustive search divol06 default
			// OPTION =0 DICVOL04 OPTION (OPTIMIZED STRATEGY SEARCH WITH
			// DECREASING CELL VOLUMES).
			// =1 OPTION WITH EXTENDED (EXHAUSTIVE) SEARCH IN VOLUME
			// DOMAINS CONTAINING MATHEMATICAL SOLUTION(S)
			// (LONGER CPU TIMES).
			// IF OPTION IS OMITTED, DEFAULT IS DICVOL04.
		}
	};

	@Override
	public List<CellParameter> extractResults(String resultFilePath) {
		//TODO: check the addition of "o"
		List<String> rawUnitCell = extractRawCellInfo(resultFilePath, CELLFILEINDETIFIER, 1);

		List<String> rawFigureMerit = extractRawCellInfo(resultFilePath, "FIGURES OF MERIT", 1);

		if (rawUnitCell.size() != rawFigureMerit.size()) {
			logger.debug("Found more unit cells than there were figure of merits...");
		}

		for (int i = 0; i < rawUnitCell.size(); ++i)// (String rawCell :
													// rawUnitCell)
		{
			Map<String, Double> rawCell = extractKeyVal(rawUnitCell.get(i));

			// Key sets for dicvol extraction A, B, C, ALP, BET, GAM, VOL, these are constant but only used once during extraction
			double a = rawCell.get("A");
			double b = rawCell.get("B");
			double c = rawCell.get("C");
			double al = rawCell.get("ALP");
			double be = rawCell.get("BET");
			double ga = rawCell.get("GAM");

			String[] out = rawFigureMerit.get(i).split("=");
			double merit = Double.parseDouble(out[1]);
			
			CellParameter cell = new CellParameter(a, b, c, al, be, ga, merit, this.ID);

			this.plausibleCells.add(cell);
		}

		return this.plausibleCells;
	}

	private Map<String, Double> extractKeyVal(String rawCellData) {

		Map<String, Double> cellParam = new HashMap<String, Double>();

		// Cell unit data + merit
		String[] out = rawCellData.split("=|\\s+");// rawCellData.split("\\s+");

		List<String> listOut = new ArrayList<String>(Arrays.asList(out));

		listOut.removeAll(Arrays.asList("", null));

		for (int i = 0; i < listOut.size(); ++i) {
			String ID = listOut.get(i++);
			Double val = Double.parseDouble(listOut.get(i));

			cellParam.put(ID, val);
		}

		return cellParam;
	}

	@Override
	public void commsSpecificIndexer(BufferedWriter bw, String path) {
		try {
			// PLEASE ENTER THE NAME OF INPUT FILE
			bw.write(path + "/" + outFileTitle + "\n");
			// PLEASE ENTER THE NAME OF OUTPUT FILE
			bw.write(path + "/" + outFileTitle + resultsExtension  +"\n");
			bw.flush();
		} catch (IOException e) {
			logger.debug("Unable to communicate with Dicvol executable" + e);
			e.printStackTrace();
		}
	}

	// -- Just playing with a method Dean --//
	private static BiConsumer<Integer, Runnable> repeat = (n, f) -> {
		for (int i = 1; i <= n; ++i)
			f.run();
	};

	@Override
	public void generateIndexFile(String fullPathFile) {
		try {
			PrintWriter writer = new PrintWriter(fullPathFile, "UTF-8");

//			// CARD 1 TITLE FREE FORMAT
//			writer.println(outFileTitle);
//
//			String delim = " ";
//
//			Iterator<Entry<String, String>> itr = stdKeyval.entrySet().iterator();
//
//			int card2Sz = 8;
//			int card3Sz = 7;
//			int card4Sz = 4;
//			int card5Sz = 6;
//
//			// CARD 2 N,ITYPE,JC,JT,JH,JO,JM,JTR -8 FREE FORMAT
//			// CARD 3 AMAX,BMAX,CMAX,VOLMIN,VOLMAX,BEMIN,BEMAX-7 FREE FORMAT
//			// CARD 4 WAVE,POIMOL,DENS,DELDEN-4 FREE FORMAT
//			// CARD 5 EPS,FOM,N_IMP,ZERO_S,ZERO_REF,OPTION-6 FREE FORMAT
//			repeat.accept(card2Sz, () -> writer.print(itr.next().getValue() + delim));
//			writer.println();
//			repeat.accept(card3Sz, () -> writer.print(itr.next().getValue() + delim));
//			writer.println();
//			repeat.accept(card4Sz, () -> writer.print(itr.next().getValue() + delim));
//			writer.println();
//			repeat.accept(card5Sz, () -> writer.print(itr.next().getValue() + delim));
//			writer.println();
//			for (int i = 0; i < peakData.getSize(); ++i) {
//				double d = peakData.getDouble(i);
//				writer.println(String.valueOf(d));
//			}
//
//			writer.close();

			//TODO: what to do when the value is not there 
			
			// CARD 1 TITLE FREE FORMAT
			writer.println(fullPathFile);

			String[] CARD2KEYS = {"N","ITYPE",				
					StandardConstantParameters.cubicSearch,
					StandardConstantParameters.tetragonalSearch,
					StandardConstantParameters.hexagonalSearch,
					StandardConstantParameters.orthorhombicSearch,
					StandardConstantParameters.monoclinicSearch,
					StandardConstantParameters.triclinicSearch };
			
			for (String cardKey : CARD2KEYS){
				writer.print(parameters.get(cardKey).getValue().intValue() + " ");
			}
			writer.println();

			
			//TODO: repeat for rest?
			String[] CARD3KEYS = {"AMAX","BMAX","CMAX","VOLMIN",StandardConstantParameters.maxVolume,"BEMIN","BEMAX"};
			for (String cardKey : CARD3KEYS){
				writer.print(parameters.get(cardKey).getValue().toString() + " ");
			}
			writer.println();
			
			String[] CARD4KEYS ={StandardConstantParameters.wavelength,"POIMOL","DENS","DELDEN"}; 
			for (String cardKey : CARD4KEYS){
				writer.print(parameters.get(cardKey).getValue().toString()+ " ");			
			}
			writer.println();
			
			//Double precison set
			String[] CARD5KEYSPRECISION = {"EPS","FOM"};
			for (String cardKey : CARD5KEYSPRECISION ){
				writer.print(parameters.get(cardKey).getValue().toString() + " ");
			}
			
			String[] CARD5KEYS = {"N_IMP","ZERO_S","ZERO_REF","OPTION"};
			for (String cardKey : CARD5KEYS ){
				writer.print(parameters.get(cardKey).getValue().intValue() + " ");
			}
			writer.println();
			
			for (int i = 0; i < peakData.getSize(); ++i) {
				double d = peakData.getDouble(i);
				writer.println(String.valueOf(d));
			}
			
			writer.close();

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isPeakDataValid(IDataset peakData) {
		boolean valid = true;
		// title + path can only be max 18 characters long or dicvol executable concatenates
		String relativePath = Paths.get(System.getProperty("user.dir") + "/").relativize(Paths.get(fileOutPath))
				.toString();

		String fullpath = relativePath + "/" + outFileTitle + ".dat";

		if (relativePath.length() > 18)
			valid = false;

		return valid;
	}
	
	@Override
	public String getResultsDataPath() {
		return fileOutPath + outFileTitle;
	}

	@Override
	public Map<String, IPowderIndexerParam> getInitialParamaters() {
		Map<String, IPowderIndexerParam> intialParams = new TreeMap<String, IPowderIndexerParam>();
		//intialParams.put("WAVE", new DicvolParam("WAVE", new Double(1.0)));
		
		Iterator<Entry<String, String>> itr = stdKeyval.entrySet().iterator();
		
		
		//TODO: tmp lazy method as all these values are needed to run Dicvol
		while(itr.hasNext()){
			Entry<String, String> kv = itr.next();
			
			Number val = new Double(kv.getValue());
			
			try{
				  val = Integer.parseInt(kv.getValue());
				  // is an integer!
			} catch (NumberFormatException e) {
			  // not an integer!
				//val = new Double(kv.getValue());
			}
			
			intialParams.put(kv.getKey(), new PowderIndexerParam(kv.getKey(), val));
		}

		
		//TODO: complete the rest of the standardparameters
		intialParams.put(StandardConstantParameters.wavelength, new PowderIndexerParam(StandardConstantParameters.wavelength, 0.));
		intialParams.put(StandardConstantParameters.maxVolume, new PowderIndexerParam(StandardConstantParameters.maxVolume, 2000.));
		
		intialParams.put(StandardConstantParameters.minFigureMerit, new PowderIndexerParam(StandardConstantParameters.minFigureMerit, 0.03));
		
		// JC =0 CUBIC SYSTEM IS NOT TESTED.
		// =1 CUBIC SYSTEM IS TESTED.
		intialParams.put(StandardConstantParameters.cubicSearch, new PowderIndexerParam("JC", 1));
		// JM =0 MONOCLINIC SYSTEM IS NOT TESTED.
		// =1 MONOCLINIC SYSTEM IS TESTED.
		intialParams.put(StandardConstantParameters.monoclinicSearch, new PowderIndexerParam("JM", 1));		
		// JH =0 HEXAGONAL SYSTEM IS NOT TESTED.
		// =1 HEXAGONAL SYSTEM IS TESTED.
		intialParams.put(StandardConstantParameters.hexagonalSearch, new PowderIndexerParam("JH", 1)); 
		// JO =0 ORTHORHOMBIC SYSTEM IS NOT TESTED.
		// =1 ORTHORHOMBIC SYSTEM IS TESTED.
		intialParams.put(StandardConstantParameters.orthorhombicSearch,new PowderIndexerParam("JO", 1));
		// JT =0 TETRAGONAL SYSTEM IS NOT TESTED.
		// =1 TETRAGONAL SYSTEM IS TESTED.
		intialParams.put(StandardConstantParameters.tetragonalSearch, new PowderIndexerParam("JT",1));
		// JTR =0 TRICLINIC SYSTEM IS NOT TESTED.
		// =1 TRICLINIC SYSTEM IS TESTED.
		intialParams.put(StandardConstantParameters.triclinicSearch, new PowderIndexerParam("JTR", 0));

		intialParams.put(StandardConstantParameters.trigonalSearch, new PowderIndexerParam(StandardConstantParameters.trigonalSearch, 0));
		intialParams.put(StandardConstantParameters.hexagonalSearch, new PowderIndexerParam(StandardConstantParameters.hexagonalSearch	, 0));
		
		return intialParams;
	}

	

}
