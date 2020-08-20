package org.eclipse.dawnsci.nexus.scan;

import org.eclipse.dawnsci.nexus.NexusException;

public interface NexusScanFileService {
	
	public NexusScanFile newNexusScanFile(NexusScanModel nexusScanModel, ScanMetadataWriter metadataWriter) throws NexusException;

}
