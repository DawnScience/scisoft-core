package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;

import uk.ac.diamond.scisoft.analysis.utils.ClassUtils;

public class PeakFindingServiceImpl implements IPeakFindingService {
	
	private final Map<String, PeakFinderInfo> PEAKFINDERS = new HashMap<String, PeakFinderInfo>();
	private Set<String> activePeakFinders = new TreeSet<String>(); 
	private Map<String, Map<Integer, Double>> allFoundPeaks;
	
	private IDataset xData, yData;
	private Integer nPeaks;
	private boolean dataInitialised = false;
	
	public PeakFindingServiceImpl() {
		//Intentionally left blank (OSGi).
	}
	
	/**
	 * Checks whether the PEAKFINDERS is populated and if not tries to fill it.
	 */
	private void checkForPeakFinders() {
		if (!PEAKFINDERS.isEmpty()) return;
		addPeakFindersByExtension();
	}
	
	@Override
	public void addPeakFindersByClass(ClassLoader cl, String pakage) throws Exception {
		final List<Class<?>> clazzes = ClassUtils.getClassesForPackage(cl, pakage);
		for (Class<?> clazz : clazzes) {
			if (Modifier.isAbstract(clazz.getModifiers())) continue;
			if (IPeakFinder.class.isAssignableFrom(clazz)) {
				IPeakFinder pf = (IPeakFinder) clazz.newInstance();
				
				registerPeakFinder(null, pf.getName(), null, pf);
			}
		}
	}
	
	@Override
	public void addPeakFindersByExtension() {
		IConfigurationElement[] elems = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.dawnsci.analysis.api.peakfinder");
		for (IConfigurationElement el: elems) {
			if (el.getName().equals("peakFinder")) {
				final String pfID = el.getAttribute("id");
				final String pfNm = el.getAttribute("name");
				final String pfDesc = el.getAttribute("description");
				IPeakFinder pf = null;
				try {
					pf = (IPeakFinder)el.createExecutableExtension("class");
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
				
				registerPeakFinder(pfID, pfNm, pfDesc, pf);
			}
		}
	}
	
	private void registerPeakFinder(String pfID, String pfNm, String pfDesc, IPeakFinder pf) {
		//In case we're not working from extension points.
		if (pfID == null) {
			pfID = pf.getClass().getName();
		}
		
		PEAKFINDERS.put(pfID, new PeakFinderInfo(pfNm, pfDesc, pf));
	}
	
	@Override
	public String getPeakFinderName(String id) throws Exception {
		checkForPeakFinders();
		return PEAKFINDERS.get(id).getName();
	}

	@Override
	public Collection<String> getRegisteredPeakFinders() {
		checkForPeakFinders();
		return PEAKFINDERS.keySet();
	}

	@Override
	public String getPeakFinderDescription(String id) throws Exception {
		checkForPeakFinders();
		return PEAKFINDERS.get(id).getDescription();
	}
	
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
	public void findPeaks() throws Exception {
		if (!dataInitialised) throw new Exception("Data has not been initialised. Cannot find peaks");
		Iterator<String> activePeakFindersIter = activePeakFinders.iterator();
		while (activePeakFindersIter.hasNext()) {
			//Get each active IPeakFinder in turn...
			String currID = activePeakFindersIter.next();
			IPeakFinder currPF = PEAKFINDERS.get(currID).getPeakFinder();
			
			//... call the findPeaks method and record the result
			allFoundPeaks.put(currID, currPF.findPeaks(xData, yData, nPeaks));
		}
		//TODO Add some process here which averages the results of the findPeaks calls
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
	
	@Override
	public void setData(IDataset xData, IDataset yData, Integer nPeaks) {
		this.xData = xData;
		this.yData = yData;
		this.nPeaks = nPeaks;
		
		//As long as we have initialised xData and yData once, we can call findPeaks
		if (xData != null && yData != null) dataInitialised = true;
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
	public void setNPeaks(Integer nPeaks) {
		setData(null, null, nPeaks);
	}

	@Override
	public Integer getNPeaks() {
		return nPeaks;
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
