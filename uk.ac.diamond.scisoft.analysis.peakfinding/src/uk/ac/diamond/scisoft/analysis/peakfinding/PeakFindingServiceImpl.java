package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;

public class PeakFindingServiceImpl implements IPeakFindingService {
	
	private Map<String, PeakFinderInfo> peakFinders;

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
//		final List<Class<?>> clazzes = ClassUtils.getClassesForPackage(cl, pakage);
//		for (Class<?> clazz : clazzes) {
//			if (Modifier.isAbstract(clazz.getModifiers())) continue;
//			if (IPeakFinder.class.isAssignableFrom(clazz)) {
//				IPeakFinder pf = (IPeakFinder) clazz.newInstance();
//				
//				registerPeakFinder(null, pf.getName(), null, pf);
//			}
//		}

	}

	@Override
	public void addPeakFindersByExtension() {
		// TODO Auto-generated method stub

	}
	
	private void registerPeakFinder(String pfID, String nm, String desc, IPeakFinder pf) {
		if (peakFinders == null) {
			peakFinders = new HashMap<String, PeakFinderInfo>();
		}
		
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
