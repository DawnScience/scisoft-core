package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;

import uk.ac.diamond.scisoft.analysis.utils.ClassUtils;

public class PeakFindingServiceImpl implements IPeakFindingService {
	
	private Map<String, PeakFinderInfo> peakFinders;
	
	
	
	@Override
	public String getPeakFinderName(String id) throws Exception {
		checkPeakFinders();
		return peakFinders.get(id).getName();
	}

	@Override
	public Collection<String> getRegisteredPeakFinders() {
		checkPeakFinders();
		return peakFinders.keySet();
	}

	@Override
	public String getPeakFinderDescription(String id) throws Exception {
		checkPeakFinders();
		return peakFinders.get(id).getDescription();
	}

	@Override
	public void addPeakFindersByClass(ClassLoader cl, String pakage) throws Exception {
		checkPeakFinders();
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
		checkPeakFinders();
		// TODO Auto-generated method stub

	}
	
	private void checkPeakFinders() {
		if (peakFinders != null) return;
		peakFinders = new HashMap<String, PeakFinderInfo>();
	}
	
	private void registerPeakFinder(String pfID, String nm, String desc, IPeakFinder pf) {
		checkPeakFinders();
		
		//In case we're not working from extension points.
		if (pfID == null) {
			pfID = pf.getClass().getName();
		}
		
		peakFinders.put(pfID, new PeakFinderInfo(nm, desc, pf));
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
