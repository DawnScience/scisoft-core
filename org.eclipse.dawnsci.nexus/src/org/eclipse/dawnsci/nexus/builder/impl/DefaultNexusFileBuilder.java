/*-
 *******************************************************************************
 * Copyright (c) 2015 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Dickie - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.nexus.builder.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.builder.NexusBuilderFile;
import org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.dawnsci.nexus.validation.ValidationReport;

/**
 * Default implementation of {@link NexusFileBuilder}.
 */
public class DefaultNexusFileBuilder implements NexusFileBuilder {

	private static final String DEFAULT_ENTRY_NAME = "entry"; 
	
	private final TreeFile treeFile;

	private final NXroot nxRoot;
	
	private Map<String, NexusEntryBuilder> entryBuilders = new HashMap<>();
	
	private boolean fileCreated = false;

	/**
	 * Creates a new {@link DefaultNexusFileBuilder}.
	 * @param filePath
	 */
	public DefaultNexusFileBuilder(final String filePath) {
		treeFile = NexusNodeFactory.createTreeFile(filePath);
		nxRoot = NexusNodeFactory.createNXroot();
		
		// TODO: do we need to set any attributes on root?
		nxRoot.setAttributeFile_name(filePath);
		treeFile.setGroupNode(nxRoot);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#getNexusTree()
	 */
	@Override
	public TreeFile getNexusTree() {
		return treeFile;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#getNxRoot()
	 */
	@Override
	public NXroot getNXroot() {
		return nxRoot;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#newEntry()
	 */
	@Override
	public DefaultNexusEntryBuilder newEntry() throws NexusException {
		return newEntry(null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#newEntry(java.lang.String)
	 */
	@Override
	public DefaultNexusEntryBuilder newEntry(String entryName) throws NexusException {
		if (entryName == null) {
			entryName = DEFAULT_ENTRY_NAME;
		}
		
		if (entryBuilders.containsKey(entryName)) {
			throw new NexusException("An entry with the name " + entryName + " already exists");
		}
		
		final NXentry entry = NexusNodeFactory.createNXentry();
		nxRoot.setEntry(entryName, entry);

		DefaultNexusEntryBuilder entryModel = new DefaultNexusEntryBuilder(entryName, entry);
		entryBuilders.put(entryName, entryModel);
		
		return entryModel;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#validate()
	 */
	@Override
	public ValidationReport validate() throws NexusException {
		return ServiceHolder.getNexusValidationService().validateNexusTree(treeFile);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#createFile(boolean)
	 */
	@Override
	public NexusBuilderFile createFile(boolean async) throws NexusException {
		return createFile(async, true);
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusFileBuilder#createFile(boolean, boolean)
	 */
	@Override
	public NexusBuilderFile createFile(boolean async, boolean useSwmr) throws NexusException {
		if (fileCreated) {
			throw new IllegalStateException("The Nexus file has already been created");
		}
		
		final String filename = treeFile.getFilename();
		
		// create the parent dir if it doesn't exist
		File parentDir = new File(filename).getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		
		// create and open the nexus file
		final INexusFileFactory nexusFileFactory = ServiceHolder.getNexusFileFactory();
		try (NexusFile nexusFile = nexusFileFactory.newNexusFile(filename, useSwmr)) {
			nexusFile.createAndOpenToWrite();
			nexusFile.setWritesAsync(async);
			// save the content of the TreeFile into the nexus file
			nexusFile.addNode("/", treeFile.getGroupNode());
			nexusFile.flush();
			fileCreated = true;
			return new DefaultNexusBuilderFile(filename, useSwmr);
		} // NexusFile is auto-closed
	}

}
