package org.eclipse.dawnsci.nexus.scan;

import org.eclipse.dawnsci.nexus.NexusException;

/**
 * A service that can create a nexus file given a {@link NexusScanModel} describing a scan.
 */
public interface NexusScanFileService {
	
	/**
	 * Create a nexus file on disk, given a {@link NexusScanModel} describing a scan. The
	 * returned {@link NexusScanFile} encapsulates the nexus file that has been created,
	 * with methods to flush and close the file.
	 * @param nexusScanModel a model describing the scan
	 * @return an object encapsulating the nexus file created
	 * @throws NexusException if the file could not be written for any reason
	 */
	public NexusScanFile newNexusScanFile(NexusScanModel nexusScanModel) throws NexusException;

}
