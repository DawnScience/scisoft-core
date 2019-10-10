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

package org.eclipse.dawnsci.analysis.tree.impl;

import java.io.Serializable;
import java.net.URI;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;

/**
 * A tree has a link to the top group node
 */
public class TreeImpl implements Tree, Serializable {
	protected static final long serialVersionUID = -4612527676015545433L;

	protected final URI source;
	protected String host;
	private NodeLink link;

	/**
	 * Construct a tree with given object ID and URI 
	 * @param oid object ID
	 * @param uri (can be null)
	 */
	public TreeImpl(final long oid, URI uri) {
		source = uri;
		host = uri == null ? null : uri.getHost(); // this can return null for "file:/blah"
		
		link = new NodeLinkImpl(ROOT, null, new GroupNodeImpl(oid));
	}

	@Override
	public URI getSourceURI() {
		return source;
	}

	@Override
	public void setHostname(String hostname) {
		host = hostname;
	}

	@Override
	public String getHostname() {
		return host;
	}

	@Override
	public long getID() {
		return getGroupNode().getID();
	}

	@Override
	public GroupNode getGroupNode() {
		return (GroupNode) link.getDestination();
	}

	@Override
	public void setGroupNode(GroupNode g) {
		link = new NodeLinkImpl(ROOT, null, g);
	}

	@Override
	public NodeLink getNodeLink() {
		return link;
	}

	@Override
	public String toString() {
		return source.toString();
	}

	@Override
	public NodeLink findNodeLink(final String pathname) {
		final String path = TreeUtils.canonicalizePath(pathname);
		if (path.indexOf(Node.SEPARATOR) != 0) {
			return null;
		}

		if (path.length() == 1) {
			return link;
		}

		// check if group is empty - this indicates an external link created this
		final GroupNode g = (GroupNode) link.getDestination();
//		if (!g.isPopulated() && g.getNumberOfAttributes() == 0) {
//			
//		}

		// check if root attribute is needed
		final String spath = path.substring(1);
		if (!spath.startsWith(Node.ATTRIBUTE)) {
			return g.findNodeLink(spath);
		}

		if (g.containsAttribute(spath.substring(1))) {
			return link;
		}

		return null;
	}
}
