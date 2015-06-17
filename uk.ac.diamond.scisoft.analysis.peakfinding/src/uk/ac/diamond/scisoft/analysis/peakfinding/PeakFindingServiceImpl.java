package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;

public class PeakFindingServiceImpl implements IPeakFindingService {

	@Override
	public String getPeakFinderName(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getPeakFinderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPeakFinderDescription(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPeakFindersByClass(ClassLoader cl, String pakage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPeakFindersByExtension() {
		// TODO Auto-generated method stub

	}
	
	private class PeakFinderInfo {
		
		private String name;
		private String description;
		private IPeakFinder peakFinder;
		
		public PeakFinderInfo(String nm, String desc, IPeakFinder pf) {
			this.name = nm;
			this.description = desc;
			this.peakFinder = pf;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public IPeakFinder getPeakFinder() {
			return peakFinder;
		}
	}

}
