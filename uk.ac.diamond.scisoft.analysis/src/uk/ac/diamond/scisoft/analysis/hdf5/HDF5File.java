/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	public final static String ROOT = SEPARATOR;
	final private URI source;
	private String host;
	final private String path; // full path to file (including filename)
	private HDF5NodeLink link; // this is a link to the root group
	private String prefix; // full path prefix

	/**
	 * Construct a HDF5 file with given object ID and URI 
	 * @param oid object ID
	 * @param uri
	 */
	public HDF5File(final long oid, URI uri) {
		super(oid);

		source = uri;
		host = uri.getHost(); // this can return null for "file:/blah"
		File f = new File(source);
		
		path = f.getAbsolutePath();
		prefix = f.getParentFile().getAbsolutePath();

		link = new HDF5NodeLink(null, ROOT, null, new HDF5Group(oid));
	}

	/**
	 * Construct a HDF5 file with given object ID and file name 
	 * @param oid object ID
	 * @param fileName
	 */
	public HDF5File(final long oid, final String fileName) {
		this(oid, new File(fileName).toURI());
	}

	/**
	 * @return source URI
	 */
	public URI getSourceURI() {
		return source;
	}

	/**
	 * Set name of host that holds file 
	 * @param hostname
	 */
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
	 * @return full path of parent directory
	 */
	public String getParentDirectory() {
		return prefix;
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
