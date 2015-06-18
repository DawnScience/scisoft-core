package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;

import uk.ac.diamond.scisoft.analysis.utils.ClassUtils;

public class PeakFindingServiceImpl implements IPeakFindingService {
	
	private final Map<String, PeakFinderInfo> PEAKFINDERS = new HashMap<String, PeakFinderInfo>();;
	
	@Override
	public String getPeakFinderName(String id) throws Exception {
		return PEAKFINDERS.get(id).getName();
	}

	@Override
	public Collection<String> getRegisteredPeakFinders() {
		return PEAKFINDERS.keySet();
	}

	@Override
	public String getPeakFinderDescription(String id) throws Exception {
		return PEAKFINDERS.get(id).getDescription();
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
		// TODO Auto-generated method stub

	}
	
	private void registerPeakFinder(String pfID, String nm, String desc, IPeakFinder pf) {
		//In case we're not working from extension points.
		if (pfID == null) {
			pfID = pf.getClass().getName();
		}
		
		PEAKFINDERS.put(pfID, new PeakFinderInfo(nm, desc, pf));
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
