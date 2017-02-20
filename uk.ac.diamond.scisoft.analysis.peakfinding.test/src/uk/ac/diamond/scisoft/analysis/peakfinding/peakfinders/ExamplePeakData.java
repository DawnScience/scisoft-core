package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * 
 * Example sample data produced from x-ray diffraction 
 *
 */
public class ExamplePeakData {

	private String rawDataPath = "src/uk/ac/diamond/scisoft/analysis/peakfinding/peakfinders/exampleDetrendingQuasiPeriodicData.xy";
	
	//TODO: correct  the results
	private String resultsDataPath = "src/uk/ac/diamond/scisoft/analysis/peakfinding/peakfinders/exampleDetrendingQuasiPeriodicDataPeaks";
	
	private Dataset xData = null;
	private Dataset yData = null;
	
	public Dataset getxData() {
		return xData;
	}

	public Dataset getyData() {
		return yData;
	}
	
	public ExamplePeakData(){
		extractData(rawDataPath);
	}
	
	public void extractData(String data){
		List<Double> xraw = new ArrayList<Double>();
		List<Double> yraw = new ArrayList<Double>();
		try {
			for (String line : Files.readAllLines(Paths.get(data))) {
				String[] xy  = line.split("\\s+");
				Double x = Double.parseDouble(xy[0]);
				Double y = Double.parseDouble(xy[1]);
				
				xraw.add(x);
				yraw.add(y);		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xData = DatasetFactory.createFromList(xraw);
		yData = DatasetFactory.createFromList(yraw);
	}
	
	
	//Example Expectance		
	public List<Double> expectedPeakValues() {
		
		List<Double> peaks = new ArrayList<Double>();
		
		try {
			for (String line : Files.readAllLines(Paths.get(resultsDataPath))) {
				peaks.add(Double.parseDouble(line));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return peaks;
	}
	
	
	
	
}
