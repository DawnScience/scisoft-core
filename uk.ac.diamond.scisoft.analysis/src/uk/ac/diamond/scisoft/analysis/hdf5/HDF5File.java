/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

import java.io.File;
import java.net.URI;

/**
 * Top level node for tree
 */
public class HDF5File extends HDF5Node {

	/**
	 * Attribute name for a NeXus class
	 */
	public static final String NXCLASS = "NX_class";
	public static final String HOST_SEPARATOR = ":";
	public static final String FILE_STARTER = "//";

	public final static String ROOT = "/";
	final private URI source;
	private String host;
	final private String path; // full path to file (including filename)
	private HDF5NodeLink link; // this is a link to the root group

	public HDF5File(final long oid, URI uri) {
		super(oid);

		source = uri;
		host = uri.getHost(); // this can return null for "file:/blah"
		path = new File(source).getAbsolutePath();
		link = new HDF5NodeLink(null, ROOT, null, new HDF5Group(oid));
	}

	public HDF5File(final long oid, final String fileName) {
		this(oid, new File(fileName).toURI());
	}

	public URI getSourceURI() {
		return source;
	}

	public void setHostname(String hostname) {
		host = hostname;
	}

	/**
	 * @return hostname (can be null for localhost)
	 */
	public String getHostname() {
		return host;
	}

	/**
	 * @return full path to file (including name)
	 */
	public String getFilename() {
		return path;
	}

	/**
	 * @return root group
	 */
	public HDF5Group getGroup() {
		return (HDF5Group) link.getDestination();
	}

	/**
	 * Set root group
	 */
	public void setGroup(HDF5Group g) {
		link = new HDF5NodeLink(null, ROOT, this, g);
	}

	/**
	 * @return link to root group
	 */
	public HDF5NodeLink getNodeLink() {
		return link;
	}

	@Override
	public String toString() {
		return (host != null ? (host + HOST_SEPARATOR + FILE_STARTER) : "") + path;
	}

	/**
	 * @param pathname
	 * @return node link to given path (needs to be absolute)
	 */
	public HDF5NodeLink findNodeLink(final String pathname) {
		final String path = canonicalizePath(pathname);
		if (path.indexOf(SEPARATOR) != 0)
			return null;

		if (path.length() == 1) {
			return link;
		}

		// check if group is empty - this indicates an external link created this
		final HDF5Group g = (HDF5Group) link.getDestination();
//		if ((g.getNumberOfGroups() + g.getNumberOfDatasets() + g.getNumberOfAttributes()) == 0) {
//			
//		}
		// check if root attribute is needed
		final String spath = path.substring(1);
		if (!spath.startsWith(ATTRIBUTE)) {
			return g.findNodeLink(spath);
		}

		if (g.containsAttribute(spath.substring(1)))
			return link;

		return null;
	}

	private static final String UPDIR = "/..";
	private static final String CURDIR = "/.";

	/**
	 * Remove ".." and "." from pathname
	 * @param pathname
	 * @return canonical form of pathname
	 */
	public static String canonicalizePath(final String pathname) {
		if (!pathname.contains(UPDIR) && !pathname.contains(CURDIR))
			return pathname;

		StringBuilder path = new StringBuilder(pathname);
		int i = 0;
		while ((i = path.indexOf(UPDIR)) >= 0) {
			int j = path.lastIndexOf(SEPARATOR, i - 1);
			if (j <= 0) {
				// can not find SEPARATOR or preserve ROOT
				path.insert(0, ROOT);
				i++;
				j++;
			}
			path.delete(j, i + UPDIR.length());
		}

		while ((i = path.indexOf(CURDIR)) >= 0) {
			path.delete(i, i + CURDIR.length());
		}

		return path.toString();
	}

	// can add lazy write method (needs modified flag in nodes)

}
