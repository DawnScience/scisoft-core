package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

public class PeakFindingData implements IPeakFindingData {
	
	private Map<String, Map<Integer, Double>> allFoundPeaks = new TreeMap<String, Map<Integer, Double>>();
	private Set<String> activePeakFinders = new TreeSet<String>();
	private IDataset xData, yData;
	private Integer nPeaks;

	@Override
	public void activatePeakFinder(String id) throws Exception {
		if (activePeakFinders.contains(id)) {
			throw new Exception(id+" already set active");
		} else {
			activePeakFinders.add(id);
		}
	}

	@Override
	public void deactivatePeakFinder(String id) throws Exception {
		if (activePeakFinders.contains(id)) {
			activePeakFinders.remove(id);
		} else {
			throw new Exception(id+" not set active");
		}
	}

	@Override
	public Collection<String> getActivePeakFinders() {
		return activePeakFinders;
	}

	@Override
	public boolean hasActivePeakFinders() {
		return !activePeakFinders.isEmpty();
	}

	@Override
	public void setData(IDataset xData, IDataset yData, Integer nPeaks) {
		this.xData = xData;
		this.yData = yData;
		this.nPeaks = nPeaks;
	}

	@Override
	public void setData(IDataset xData, IDataset yData) {
		setData(xData, yData, null);
	}

	@Override
	public void setXData(IDataset xData) {
		setData(xData, null, null);
	}

	@Override
	public void setYData(IDataset yData) {
		setData(null, yData, null);
	}

	@Override
	public IDataset[] getData() throws Exception {
		if (hasData()) {
			return new IDataset[]{xData, yData};
		} else {
			throw new Exception("No data has been set to find peaks in");
		}
	}

	@Override
	public boolean hasData() {
		if ((xData == null) || (xData.getSize() == 0) &&
				(yData == null) || (yData.getSize() == 0)) {
			return false;
		}
		return true;
	}

	@Override
	public void setNPeaks(Integer nPeaks) {
		setData(null, null, nPeaks);
	}

	@Override
	public Integer getNPeaks() {
		return nPeaks;
	}

	@Override
	public Map<String, Map<Integer, Double>> getPeaks() throws Exception {
		if (allFoundPeaks == null || allFoundPeaks.isEmpty()) throw new Exception("No peaks found. Need to run findPeaks()");
		return allFoundPeaks;
	}

	@Override
	public Map<Integer, Double> getPeaks(String id) throws Exception {
		if (allFoundPeaks == null || allFoundPeaks.isEmpty()) throw new Exception("No peaks found. Need to run findPeaks()");
		if (!allFoundPeaks.keySet().contains(id)) throw new Exception(id+" was not active when findPeaks() was called");
		return allFoundPeaks.get(id);
	}

}
