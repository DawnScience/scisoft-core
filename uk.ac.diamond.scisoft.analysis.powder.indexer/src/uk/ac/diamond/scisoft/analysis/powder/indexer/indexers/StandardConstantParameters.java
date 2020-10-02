package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import java.util.Map;
import java.util.TreeMap;

import uk.ac.diamond.scisoft.analysis.powder.indexer.IPowderIndexerParam;
import uk.ac.diamond.scisoft.analysis.powder.indexer.PowderIndexerParam;

public class StandardConstantParameters {

	//TODO: tmp, realyl just need a collection of parameters to go through
	
	public Map<String, IPowderIndexerParam> getStandardParameters(){
		Map<String, IPowderIndexerParam> intialParams = new TreeMap<String, IPowderIndexerParam>();
		intialParams.put("Wavelength", new PowderIndexerParam("wave", 0));
		
		return null;
	}
	
	//Generic constants decided on for the ui..
	public static final String wavelength = "Wavelength";
	
	public static final String maxVolume = "MaxVol";
	
	public static final String maxABC = "MaxABC";
	
	public static final String minFigureMerit = "minFigureMerit";
	
	public static final String cubicSearch = "Cubic";
	public static final String monoclinicSearch = "Monoclinic";
	public static final String orthorhombicSearch = "Orthorhombic";
	public static final String tetragonalSearch = "Tetragonal";
	public static final String trigonalSearch = "Trigonal";
	public static final String hexagonalSearch = "Hexagonal";
	public static final String triclinicSearch = "Triclinic";
	
}
