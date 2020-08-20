/*-
 * Copyright © 2020 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package org.eclipse.dawnsci.nexus.scan;

import java.util.Set;

import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFile;

/**
 * An instance of this interface knows how to build a {@link NexusBuilderFile} for a given {@link NexusScanModel}.
 */
public interface NexusScanFileBuilder {

	/**
	 * Returns the absolute file path of the underlying {@link NexusFile} on disk
	 * @return
	 */
	public String getFilePath();

	/**
	 * Returns the set of file paths of the external file that this scan file links to.
	 * @return external file paths
	 */
	public Set<String> getExternalFilePaths();

	/**
	 * Create the structure of the nexus file according to the {@link NexusScanModel} that this
	 * {@link NexusScanFileBuilder} was created with and writes it to disk.
	 * @param async if <code>true</code> all writes to datasets are done asynchronously
	 * @return the nexus file 
	 * @throws NexusException if the nexus file could not be created for any reason
	 */
	public void createNexusFile(boolean async) throws NexusException;

	/**
	 * Flush the underlying nexus file.
	 * @return
	 * @throws NexusException
	 */
	public int flush() throws NexusException;

	/**
	 * Call at the end of the scan to write the final timestamps and close the nexus file.
	 * @throws NexusException 
	 */
	public void scanFinished() throws NexusException;

}
