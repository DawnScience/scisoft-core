/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.api.tree;

import java.net.URI;

/**
 * A tree has a link to the top group node
 */
public interface Tree {

	public static final String HOST_SEPARATOR = ":";
	public static final String FILE_STARTER = "//";
	public static final String ROOT = Node.SEPARATOR;

	/**
	 * @return ID
	 */
	public long getID();

	/**
	 * @return source URI
	 */
	public URI getSourceURI();

	/**
	 * @return hostname (can be null for localhost)
	 */
	public String getHostname();

	/**
	 * Set name of host that holds file 
	 * @param hostname
	 */
	public void setHostname(String hostname);

	/**
	 * @return root group node
	 */
	public GroupNode getGroupNode();

	/**
	 * Set root group node
	 */
	public void setGroupNode(GroupNode g);

	/**
	 * @return link to root group
	 */
	public NodeLink getNodeLink();

	/**
	 * @param pathname
	 * @return node link to given path (needs to be absolute) or null if not found
	 * @throws NullPointerException if pathname is null
	 */
	public NodeLink findNodeLink(String pathname);
}
