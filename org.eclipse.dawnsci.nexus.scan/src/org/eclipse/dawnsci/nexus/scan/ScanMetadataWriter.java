package org.eclipse.dawnsci.nexus.scan;

import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;

/**
 * A temporary interface for SolsticeScanMonitor to implement as part of DAQ-3061.
 * TODO: This needs to be refactored away.
 */
public interface ScanMetadataWriter extends INexusDevice<NXcollection> {

	void setNexusObjectProviders(Map<ScanRole, List<NexusObjectProvider<?>>> nexusObjectProviders);

}
