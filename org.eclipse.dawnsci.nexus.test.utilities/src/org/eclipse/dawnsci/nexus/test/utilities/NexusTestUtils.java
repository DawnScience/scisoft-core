/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.test.utilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.device.impl.NexusDeviceService;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.eclipse.dawnsci.nexus.template.TemplateServiceHolder;
import org.eclipse.dawnsci.nexus.template.impl.NexusTemplateServiceImpl;

public class NexusTestUtils {

	private static INexusFileFactory nexusFileFactory = new NexusFileFactoryHDF5();
	
	private static INexusFileFactory getNexusFileFactory() {
		return nexusFileFactory;
	}
	
	public static void setUpServices() {
		final INexusDeviceService nexusDeviceService = new NexusDeviceService();
		final NexusTemplateService nexusTemplateService = new NexusTemplateServiceImpl();
		
		final ServiceHolder serviceHolder = new ServiceHolder();
		serviceHolder.setNexusFileFactory(nexusFileFactory);
		serviceHolder.setNexusDeviceService(nexusDeviceService);
		
		final TemplateServiceHolder templateServiceHolder = new TemplateServiceHolder();
		templateServiceHolder.setNexusFileFactory(nexusFileFactory);
		templateServiceHolder.setNexusTemplateService(nexusTemplateService);
	}
	
	/**
	 * Create a new Nexus file (overwriting any existing one)
	 * @param path
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFile createNexusFile(String path) throws NexusException {
		return createNexusFile(path, false);
	}

	/**
	 * Create a new Nexus file (overwriting any existing one)
	 * @param path
	 * @param enableSWMR
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFile createNexusFile(String path, boolean enableSWMR) throws NexusException {
		NexusFile file = getNexusFileFactory().newNexusFile(path, enableSWMR);
		file.createAndOpenToWrite();
		return file;
	}
	
	/**
	 * Save the NeXus tree with the given root node to the given path.
	 * <p>
	 * Use this method when the NeXus tree has been created using the NeXus base classes (subinterfaces of {@link NXobject}), rather than using
	 * {@link NexusFile} to create the nodes.
	 * @param filePath
	 *            file path
	 * @param rootNode
	 *            root node
	 *
	 * @throws NexusException
	 */
	public static void saveNexusFile(String filePath, NXroot rootNode) throws NexusException {
		final TreeFile treeFile = TreeFactory.createTreeFile(filePath.hashCode(), filePath);
		treeFile.setGroupNode(rootNode);
		saveNexusFile(treeFile);
	}

	/**
	 * Save the given NeXus file tree.
	 * <p>
	 * Use this method when the NeXus tree has been created using the NeXus base classes (subinterfaces of {@link NXobject}), rather than using
	 * {@link NexusFile} to create the nodes.
	 *
	 * @param nexusTree
	 *            nexus tree
	 * @throws NexusException
	 */
	public static void saveNexusFile(TreeFile nexusTree) throws NexusException {
		try (NexusFile nexusFile = createNexusFile(nexusTree.getFilename())) {
			nexusFile.addNode("/", nexusTree.getGroupNode());
			nexusFile.flush();
		}
	}
	
	/**
	 * Open an existing Nexus file to modify
	 * @param path
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFile openNexusFile(String path) throws NexusException {
		NexusFile file = getNexusFileFactory().newNexusFile(path);
		file.openToWrite(false);
		return file;
	}

	/**
	 * Open an existing Nexus file to read only
	 * @param path
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFile openNexusFileReadOnly(String path) throws NexusException {
		NexusFile file = getNexusFileFactory().newNexusFile(path);
		file.openToRead();
		return file;
	}

	/**
	 * Loads the nexus file with the given path.
	 * @param filePath
	 * @param readOnly
	 * @return
	 * @throws NexusException
	 */
	public static TreeFile loadNexusFile(String filePath, boolean readOnly) throws NexusException {
		try (NexusFile nexusFile = readOnly ? openNexusFileReadOnly(filePath) : openNexusFile(filePath)) {
			return NexusUtils.loadNexusTree(nexusFile);
		}
	}

	/**
	 * Utility method to delete a file if it exists.
	 * @param file file to delete
	 * @throws Exception if the file couldn't be deleted for any reason
	 */
	public static void deleteFile(String filename) throws Exception {
		File file = new File(filename);
		if (file.exists()) {
			if (!file.delete()) {
				throw new Exception("Could not delete file " + file);
			}
		}
	}
	
	public static Node getNode(NXroot root, String path) {
		final String[] pathSegments = path.split(Node.SEPARATOR);
		assertThat(pathSegments, arrayWithSize(greaterThan(1)));
		assertThat(pathSegments[0], isEmptyString());
		
		Node node = root;
		for (int i = 1; i < pathSegments.length; i++) {
			assertThat(node, is(instanceOf(GroupNode.class)));
			node = ((GroupNode) node).getNode(pathSegments[i]);
		}
		assertThat(node, is(notNullValue()));
		return node;
	}

}
