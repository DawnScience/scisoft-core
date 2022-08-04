package org.eclipse.dawnsci.nexus.scan;

import java.util.List;

public interface IDefaultDataGroupCalculator {
	
	public default String getDefaultDataGroupName(NexusScanModel nexusScanModel, List<String> dataGroupNames) {
		return getDefaultDataGroupName(dataGroupNames);
	}
	
	public String getDefaultDataGroupName(List<String> dataGroupNames);
	
}
