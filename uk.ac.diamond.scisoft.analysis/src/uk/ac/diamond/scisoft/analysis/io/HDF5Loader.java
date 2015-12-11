/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.io.SliceObject;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDynamicDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5LazyLoader;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.hdf5.HDF5Utils.DatasetType;
import org.eclipse.dawnsci.nexus.NexusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.HDFNativeData;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.hdf5lib.structs.H5G_info_t;
import ncsa.hdf.hdf5lib.structs.H5L_info_t;
import ncsa.hdf.hdf5lib.structs.H5O_info_t;

/**
 * Load HDF5 files using NCSA's Java library
 */
public class HDF5Loader extends AbstractFileLoader {
	
	protected static final Logger logger = LoggerFactory.getLogger(HDF5Loader.class);


	private boolean keepBitWidth = false;
	private boolean async = false;
	private int syncLimit;
	private int syncNodes;
	private ScanFileHolderException syncException = null;

	private String host = null;

	private static final long DEFAULT_OBJECT_ID = -1;

	public static final String DATA_FILENAME_ATTR_NAME = "data_filename";

	public HDF5Loader() {
		setHost();
	}

	public HDF5Loader(final String name) {
		setHost();
		setFile(name);
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
	}

	/**
	 * Set whether loading is done asynchronously so that program flow is returned after first
	 * level of a tree is read in and at least 200 nodes are read 
	 * @param loadAsynchronously
	 */
	@Override
	public void setAsyncLoad(boolean loadAsynchronously) {
		setAsyncLoad(loadAsynchronously, 200);
	}

	/**
	 * Set whether loading is done asynchronously so that program flow is returned after first
	 * level of a tree is read in and at least the given number of nodes are read 
	 * @param loadAsynchronously
	 * @param nodes
	 */
	public void setAsyncLoad(boolean loadAsynchronously, int nodes) {
		async = loadAsynchronously;
		syncLimit = nodes;
	}

	/**
	 * Stop asynchronous loading
	 */
	public synchronized void stopAsyncLoading() {
		syncNodes = syncLimit;
	}

	private void setHost() {
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("Could not find host name", e);
		}
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile(null);
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		Tree tree = loadTree(mon);
		DataHolder dh = new DataHolder();
		dh.setFilePath(fileName);
		dh.setTree(tree);
		updateDataHolder(dh, loadMetadata);
		if (loadMetadata) {
			metadata = (Metadata) dh.getMetadata();
		}
		return dh;
	}

	public TreeFile loadTree() throws ScanFileHolderException {
		return loadTree(null);
	}

	TreeFile tFile = null;

	private LoadFileThread loaderThread = null;

	class LoadFileThread extends Thread {
		private IMonitor mon;

		public LoadFileThread(final IMonitor monitor) {
			mon = monitor;
			setName("Load HDF5 file: " + fileName);
		}
		
		@Override
		public void run() {
			try {
				long fid = HDF5FileFactory.acquireFile(fileName, false);

				if (!monitorIncrement(mon)) {
					return;
				}

				tFile = createTreeBF(mon, fid, keepBitWidth);
			} catch (Throwable le) {
				syncException = new ScanFileHolderException("Problem loading file: " + fileName, le);
				try {
					updateSyncNodes(syncLimit); // prevent deadlock
				} catch (Throwable e) {
				}
			} finally {
				try {
					HDF5FileFactory.releaseFile(fileName);
				} catch (ScanFileHolderException e) {
					logger.error("Error in releasing file", e);
				}
			}
		}
	}

	private synchronized void waitForSyncLimit() throws ScanFileHolderException {
		while (syncNodes < syncLimit) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (syncException != null)
			throw syncException;
	}

	private synchronized void updateSyncNodes(int nodes) throws ScanFileHolderException {
		syncNodes = nodes;
		if (syncNodes >= syncLimit) {
			notifyAll();
		}
		if (syncException != null)
			throw syncException;
	}

	/**
	 * Load tree
	 * @param mon
	 * @return a HDF5File tree
	 * @throws ScanFileHolderException
	 */
	public TreeFile loadTree(final IMonitor mon) throws ScanFileHolderException {
		if (!monitorIncrement(mon)) {
			return null;
		}

		if (tFile != null) {
			return tFile;
		}

		logger.trace(String.format("Loading in thd %x\n", Thread.currentThread().getId()));
		File f = new File(fileName);
		if (!f.exists()) {
			throw new ScanFileHolderException("File, " + fileName + ", does not exist");
		}
		
		try {
			fileName = f.getCanonicalPath();
		} catch (IOException e) {
			logger.error("Could not get canonical path", e);
			throw new ScanFileHolderException("Could not get canonical path", e);
		}
		if (async) {
			loaderThread = new LoadFileThread(mon);
			loaderThread.start();
			try {
				Thread.sleep(100l);
			} catch (InterruptedException e) {
			}
			waitForSyncLimit();
		} else {
			try {
				long fid = HDF5FileFactory.acquireFile(fileName, false);

				if (!monitorIncrement(mon)) {
					return null;
				}

				tFile = createTree(fid, keepBitWidth);
			} catch (Throwable le) {
				throw new ScanFileHolderException("Problem loading file: " + fileName, le);
			} finally {
				HDF5FileFactory.releaseFile(fileName);
			}
		}
		return tFile;
	}

	/**
	 * @return true if still loading file
	 */
	@Override
	public boolean isLoading() {
		if (loaderThread == null)
			return false;
		return loaderThread.isAlive();
	}

	/**
	 * @param fid file ID
	 * @param keepBitWidth
	 * @return a HDF5 tree
	 * @throws Exception
	 */
	private TreeFile createTree(final long fid, final boolean keepBitWidth) throws Exception {
		final long oid = fileName.hashCode(); // include file name in ID
		TreeFile f = TreeFactory.createTreeFile(oid, fileName);
		f.setHostname(host);

		GroupNode g = (GroupNode) createGroup(fid, f, DEFAULT_OBJECT_ID, new HashMap<Long, Node>(), null, Tree.ROOT, keepBitWidth);
		if (g == null) {
			throw new ScanFileHolderException("Could not copy root group");
		}
		f.setGroupNode(g);
		return f;
	}

	/**
	 * Breadth-first tree walk
	 * @param mon
	 * @param fid file ID
	 * @param keepBitWidth
	 * @return a HDF5 tree
	 * @throws Exception
	 */
	private TreeFile createTreeBF(final IMonitor mon, final long fid, final boolean keepBitWidth) throws Exception {
		final long oid = fileName.hashCode(); // include file name in ID
		TreeFile f = TreeFactory.createTreeFile(oid, fileName);
		f.setHostname(host);

		HashMap<Long, Node> pool = new HashMap<Long, Node>();
		Queue<String> iqueue = new LinkedList<String>();
		GroupNode g = (GroupNode) createGroup(fid, f, DEFAULT_OBJECT_ID, pool, iqueue, Tree.ROOT, keepBitWidth);
		if (g == null) {
			throw new ScanFileHolderException("Could not copy root group");
		}
		f.setGroupNode(g);

		Queue<String> oqueue = new LinkedList<String>();
		while (iqueue.size() > 0) { // read in first level
			String nn = iqueue.remove();
			Node n = createGroup(fid, f, DEFAULT_OBJECT_ID, pool, oqueue, nn, keepBitWidth);
			if (n == null) {
				logger.error("Could not find group {}", nn);
				continue;
			}
			NodeLink ol = f.findNodeLink(Tree.ROOT);
			GroupNode og = (GroupNode) ol.getDestination();
			og.addNode(nn.substring(1, nn.length() - 1), n);
		}

		tFile = f;

		if (!monitorIncrement(mon)) {
			updateSyncNodes(syncLimit); // notify if finished
			return null;
		}

		do {
			Queue<String> q = iqueue;
			iqueue = oqueue;
			oqueue = q;
			while (iqueue.size() > 0) {
				updateSyncNodes(pool.size());
				String nn = iqueue.remove();
				Node n = createGroup(fid, f, DEFAULT_OBJECT_ID, pool, oqueue, nn, keepBitWidth);
				if (n == null) {
					logger.error("Could not find group {}", nn);
					continue;
				}
				int i = nn.lastIndexOf(Node.SEPARATOR, nn.length() - 2);
				NodeLink ol = f.findNodeLink(nn.substring(0, i));
				GroupNode og = (GroupNode) ol.getDestination();
				og.addNode(nn.substring(i + 1, nn.length() - 1), n);
			}
			if (!monitorIncrement(mon)) {
				updateSyncNodes(syncLimit); // notify if finished
				return null;
			}
		} while (oqueue.size() > 0);

		updateSyncNodes(syncLimit); // notify if finished

		return f;
	}

	private static int LIMIT = 10240;

	/**
	 * Create a node (and all its children, recursively) from given location ID
	 * @param fid location ID
	 * @param f HDF5 file
	 * @param pool
	 * @param queue
	 * @param name of group (full path and ends in '/')
	 * @param keepBitWidth
	 * @return node
	 * @throws Exception
	 */
	private Node createNode(final long fid, final TreeFile f, final HashMap<Long, Node> pool, final Queue<String> queue, final String name, final boolean keepBitWidth) throws Exception {
		try {
			H5O_info_t info = H5.H5Oget_info_by_name(fid, name, HDF5Constants.H5P_DEFAULT);
			int t = info.type;
			if (t == HDF5Constants.H5O_TYPE_GROUP) {
				return createGroup(fid, f, DEFAULT_OBJECT_ID, pool, queue, name, keepBitWidth);
			} else if (t == HDF5Constants.H5O_TYPE_DATASET) {
				return createDataset(fid, f, DEFAULT_OBJECT_ID, pool, name, keepBitWidth);
			} else if (t == HDF5Constants.H5O_TYPE_NAMED_DATATYPE) {
				logger.error("Named datatype not supported"); // TODO
			} else {
				logger.error("Unknown object type");
			}
		} catch (HDF5Exception ex) {
			logger.error("Could not find info about object {}" + name);
		}
		return null;
	}

	private static final int HASH_MULTIPLIER = 139;
	private static long createObjectID(long id, long other) {
		long oid = id * HASH_MULTIPLIER + other;
		if (oid == DEFAULT_OBJECT_ID) {
			oid = oid * HASH_MULTIPLIER + other;
		}
		return oid;
	}

	/**
	 * Create a group (and all its children, recursively) from given location ID
	 * @param fid location ID
	 * @param f HDF5 file
	 * @param oid object ID
	 * @param pool
	 * @param queue
	 * @param name of group (full path and ends in '/')
	 * @param keepBitWidth
	 * @return node
	 * @throws Exception
	 */
	private Node createGroup(final long fid, final TreeFile f, long oid, final HashMap<Long, Node> pool, final Queue<String> queue, final String name, final boolean keepBitWidth) throws Exception {
		long gid = -1;
		GroupNode group = null;

		try {
			int nelems = 0;
			try {
				gid = H5.H5Gopen(fid, name, HDF5Constants.H5P_DEFAULT);
				H5G_info_t info = H5.H5Gget_info(gid);
				nelems = (int) info.nlinks;
			} catch (HDF5Exception ex) {
				throw new ScanFileHolderException("Could not open group", ex);
			}

			if (oid == DEFAULT_OBJECT_ID) {
				byte[] idbuf = null;
				try {
					idbuf = H5.H5Rcreate(fid, name, HDF5Constants.H5R_OBJECT, -1);
					oid = createObjectID(f.getID(), HDFNativeData.byteToLong(idbuf, 0));
				} catch (HDF5Exception ex) {
					throw new ScanFileHolderException("Could not find group reference", ex);
				}
				if (oid != DEFAULT_OBJECT_ID && pool != null && pool.containsKey(oid)) {
					Node p = pool.get(oid);
					if (!(p instanceof GroupNode)) {
						throw new IllegalStateException("Matching pooled node is not a group");
					}
					return p;
				}
			}

			group = TreeFactory.createGroupNode(oid);
			if (pool != null)
				pool.put(oid, group);
			if (copyAttributes(name, group, gid)) {
				final String link = group.getAttribute(NAPIMOUNT).getFirstElement();
				try {
					return copyNAPIMountNode(f, pool, link, keepBitWidth);
				} catch (Exception e) {
					logger.error("Could not copy NAPI mount", e);
				}
			}

			if (nelems <= 0) {
				return group;
			}

			if (nelems > LIMIT) {
				logger.warn("Number of members in group {} exceed limit ({} > {}). Only reading up to limit",
						new Object[] { name, nelems, LIMIT });
				nelems = LIMIT;
			}

			int[] oTypes = new int[nelems];
			int[] lTypes = new int[nelems];
			long[] oids = new long[nelems];
			String[] oNames = new String[nelems];
			try {
				H5.H5Gget_obj_info_all(fid, name, oNames, oTypes, lTypes, oids, HDF5Constants.H5_INDEX_NAME);
			} catch (HDF5Exception ex) {
				logger.error("Could not get objects info in group", ex);
				return null;
			}

			String oname;
			int otype;
			int ltype;

			// Iterate through the file to see members of the group
			for (int i = 0; i < nelems; i++) {
				oname = oNames[i];
				if (oname == null) {
					continue;
				}
				otype = oTypes[i];
				ltype = lTypes[i];
				oid = createObjectID(f.getID(), oids[i]);
				if (ltype == -1) { // need to handle cases where the get_info call fails
					H5L_info_t info = H5.H5Lget_info(fid, name + Node.SEPARATOR + oname, HDF5Constants.H5P_DEFAULT);
					ltype = info.type; // not worth getting object type as it has already failed
				}

				if (ltype == HDF5Constants.H5L_TYPE_HARD) {
					if (otype == HDF5Constants.H5O_TYPE_GROUP) {
						if (oid != DEFAULT_OBJECT_ID && pool != null && pool.containsKey(oid)) {
							Node p = pool.get(oid);
							if (!(p instanceof GroupNode)) {
								throw new IllegalStateException("Matching pooled node is not a group");
							}
							group.addNode(oname, p);
							continue;
						}

						// System.err.println("G: " + oname);
						String newname = name + Node.SEPARATOR + oname + Node.SEPARATOR;
						newname = newname.replaceAll("([" + Node.SEPARATOR + "])\\1+",
								Node.SEPARATOR);
						if (queue != null) {
							queue.add(newname);
						} else {
							Node g = createGroup(fid, f, oid, pool, queue, newname, keepBitWidth);
							if (g == null) {
								logger.error("Could not load group {} in {}", oname, name);
							} else {
								group.addNode(oname, g);
							}
						}
					} else if (otype == HDF5Constants.H5O_TYPE_DATASET) {
						if (oid != DEFAULT_OBJECT_ID && pool != null && pool.containsKey(oid)) {
							Node p = pool.get(oid);
							if (!(p instanceof DataNode)) {
								throw new IllegalStateException("Matching pooled node is not a dataset");
							}
							group.addNode(oname, p);
							continue;
						}

						// System.err.println("D: " + oname);
						String newname = name + Node.SEPARATOR + oname;
						newname = newname.replaceAll("([" + Node.SEPARATOR + "])\\1+",
								Node.SEPARATOR);
						Node n = createDataset(fid, f, oid, pool, newname, keepBitWidth);

						if (n != null)
							group.addNode(oname, n);
					} else if (otype == HDF5Constants.H5O_TYPE_NAMED_DATATYPE) {
						logger.error("Named datatype not supported"); // TODO
					} else {
						logger.error("Something wrong with hardlinked object");
					}
				} else if (ltype == HDF5Constants.H5L_TYPE_SOFT) {
					// System.err.println("S: " + oname);
					String[] linkName = new String[1];
					int t = H5.H5Lget_val(gid, oname, linkName, HDF5Constants.H5P_DEFAULT);
					if (t < 0) {
						logger.error("Could not get value of link");
						continue;
					}
					// System.err.println("  -> " + linkName[0]);
					SymbolicNode slink = TreeFactory.createSymbolicNode(oid, f, linkName[0]);
					group.addNode(oname, slink);
				} else if (ltype == HDF5Constants.H5L_TYPE_EXTERNAL) {
					// System.err.println("E: " + oname);
					String[] linkName = new String[2]; // file name and file path
					int t = H5.H5Lget_val(gid, oname, linkName, HDF5Constants.H5P_DEFAULT);
					// System.err.println("  -> " + linkName[0] + " in " + linkName[1]);
					if (t < 0) {
						logger.error("Could not get value of link");
						continue;
					}

					String eName = linkName[1];
					if (!(new File(eName).exists())) { // use directory of linking file
						logger.warn("Could not find external file {}, trying in {}", eName, f.getParentDirectory());
						eName = new File(f.getParentDirectory(), new File(linkName[1]).getName()).getAbsolutePath();
						if (!(new File(eName).exists())) { // append to directory of linking file
							String eName2 = new File(f.getParentDirectory(),linkName[1]).getAbsolutePath();
							logger.warn("Could not find external file {}, trying in {}", eName, eName2);
							eName = eName2;
						}						
					}
					if (new File(eName).exists()) {
						group.addNode(oname, getExternalNode(pool, f.getHostname(), eName, linkName[0], keepBitWidth));
					} else {
						logger.error("Could not find external file {}", eName);
					}
				}
			}
		} finally {
			if (gid >= 0) {
				try {
					H5.H5Gclose(gid);
				} catch (HDF5Exception ex) {
				}
			}
		}

		if (NexusTreeUtils.isNXClass(group, NexusTreeUtils.NX_DATA)) {
			for (NodeLink l : group) {
				augmentLink(l);
			}
		}

		return group;
	}

	/**
	 * Create a dataset (and all its children, recursively) from given location ID
	 * @param lid location ID
	 * @param f HDF5 file
	 * @param oid object ID
	 * @param pool
	 * @param path of dataset (can full path or relative)
	 * @param keepBitWidth
	 * @return node
	 * @throws Exception
	 */
	private Node createDataset(final long lid, final TreeFile f, long oid, final HashMap<Long, Node> pool, final String path, final boolean keepBitWidth) throws Exception {
		byte[] idbuf = null;
		if (oid == DEFAULT_OBJECT_ID) {
			try {
				idbuf = H5.H5Rcreate(lid, path, HDF5Constants.H5R_OBJECT, -1);
				oid = createObjectID(f.getID(), HDFNativeData.byteToLong(idbuf, 0));
			} catch (HDF5Exception ex) {
				throw new ScanFileHolderException("Could not find object reference", ex);
			}
			if (oid != DEFAULT_OBJECT_ID && pool != null && pool.containsKey(oid)) {
				Node p = pool.get(oid);
				if (!(p instanceof DataNode)) {
					throw new IllegalStateException("Matching pooled node is not a dataset");
				}
				return p;
			}
		}

		long did = -1, tid = -1;
		try {
//			Thread.sleep(200);

			did = H5.H5Dopen(lid, path, HDF5Constants.H5P_DEFAULT);
			tid = H5.H5Dget_type(did);

			// create a new dataset
			DataNode d = TreeFactory.createDataNode(oid);
			if (copyAttributes(path, d, did)) {
				final String link = d.getAttribute(NAPIMOUNT).getFirstElement();
				return copyNAPIMountNode(f, pool, link, keepBitWidth);
			}
			int i = path.lastIndexOf(Node.SEPARATOR);
			final String sname = i >= 0 ? path.substring(i + 1) : path;
			if (!createLazyDataset(f, d, path, sname, did, tid, keepBitWidth,
					d.containsAttribute(DATA_FILENAME_ATTR_NAME))) {
				logger.error("Could not create a lazy dataset from {}", path);
			}
			if (pool != null)
				pool.put(oid, d);
			return d;
		} catch (HDF5Exception ex) {
			logger.error(String.format("Could not open dataset %s in %s", path, f), ex);
		} finally {
			if (tid != -1) {
				try {
					H5.H5Tclose(tid);
				} catch (HDF5Exception ex) {
				}
			}
			if (did != -1) {
				try {
					H5.H5Dclose(did);
				} catch (HDF5Exception ex) {
				}
			}
		}

		return null;
	}

	private static final String NAPIMOUNT = "napimount";
	private static final String NAPISCHEME = "nxfile";


	// return true when attributes contain a NAPI mount - dodgy external linking for HDF5 version < 1.8
	private static boolean copyAttributes(final String name, final Node nn, final long id) {
		boolean hasNAPIMount = false;

		try {
			for (Dataset a : HDF5Utils.readAttributes(id)) {
				Attribute h = TreeFactory.createAttribute(a.getName(), a);
				h.setTypeName(DatasetUtils.getDTypeName(a));
				nn.addAttribute(h);
				if (a.getName().equals(NAPIMOUNT)) {
					hasNAPIMount = true;
				}
			}
		} catch (NexusException e) {
			logger.warn("Problem with attributes on {}: {}", name, e.getMessage());
		}
		return hasNAPIMount;
	}

	// get external node
	private Node getExternalNode(final HashMap<Long, Node> pool, final String host, final String path, String node, final boolean keepBitWidth) throws Exception {
		Node nn = null;

		if (!node.startsWith(Tree.ROOT)) {
			node = Tree.ROOT + node;
		}

		try {
			long fid = HDF5FileFactory.acquireFile(path, false);

			final long oid = path.hashCode(); // include file name in ID
			TreeFile f = TreeFactory.createTreeFile(oid, path);
			f.setHostname(host);

			nn = createNode(fid, f, pool, null, node, keepBitWidth);
		} catch (Throwable le) {
			throw new ScanFileHolderException("Problem loading file: " + path, le);
		} finally {
			HDF5FileFactory.releaseFile(path);
		}
		
		return nn;
	}

	// retrieve external file link
	private Node copyNAPIMountNode(final TreeFile file, final HashMap<Long, Node> pool, final String link, final boolean keepBitWidth) throws Exception {
		final URI ulink = new URI(link);
		Node nn = null;
		if (ulink.getScheme().equals(NAPISCHEME)) {
			String lpath = ulink.getPath();
			String ltarget = ulink.getFragment();
			File f = new File(lpath);
			if (!f.exists()) {
				logger.debug("File, {}, does not exist!", lpath);

				// see if linked file in same directory
				f = new File(file.getParentDirectory(), f.getName());
				if (!f.exists()) {
					throw new ScanFileHolderException("File, " + lpath + ", does not exist");
				}
				lpath = f.getAbsolutePath();
			}
			nn = getExternalNode(pool, file.getHostname(), lpath, ltarget, keepBitWidth);
			if (nn == null)
				logger.warn("Could not find external node: {}", ltarget);
		} else {
			System.err.println("Wrong scheme: " + ulink.getScheme());
		}

		return nn;
	}

	/**
	 * Create a lazy dataset from given data node and datatype IDs
	 * @param file
	 * @param node
	 * @param nodePath full node path
	 * @param name
	 * @param did
	 * @param tid
	 * @param keepBitWidth
	 * @param useExternalFiles
	 * @return true if created
	 * @throws Exception
	 */
	private static boolean createLazyDataset(final TreeFile file, final DataNode node,
			final String nodePath, final String name, final long did, final long tid,
			final boolean keepBitWidth, final boolean useExternalFiles) throws Exception {
		long sid = -1, pid = -1;
		long ntid = -1;
		int rank;
		long[] dims;
		DatasetType type;
		final int[] trueShape;

		try {
//			Thread.sleep(200);
			H5.H5Drefresh(did);
			sid = H5.H5Dget_space(did);
			rank = H5.H5Sget_simple_extent_ndims(sid);

			ntid = H5.H5Tget_native_type(tid);
			type = HDF5Utils.getDatasetType(tid, ntid);

			try {
				pid = H5.H5Dget_create_plist(did);
				int layout = H5.H5Pget_layout(pid);
				if (layout == HDF5Constants.H5D_CHUNKED) { // add any chunking information
					long[] chunk = new long[rank];
					int crank = H5.H5Pget_chunk(pid, rank, chunk);
					if (crank != rank) {
						logger.error("Rank of chunk does not equal rank of dataset");
					} else {
						node.setChunkShape(chunk);
					}
				}
				// check if it is an external dataset
				int nfiles = H5.H5Pget_external_count(pid);
				if (nfiles > 0) {
					logger.error("External files are not supported");
					return false;
				}
			} catch (HDF5Exception ex) {
				logger.error("Could not get creation property information", ex);
			} finally {
				try {
					H5.H5Pclose(pid);
				} catch (HDF5Exception ex) {
				}
			}

			if (rank == 0) {
				// a single data point
				rank = 1;
				dims = new long[1];
				dims[0] = 1;
				node.setMaxShape(dims);
			} else {
				dims = new long[rank];
				long[] maxDims = new long[rank];
				H5.H5Sget_simple_extent_dims(sid, dims, maxDims);
				node.setMaxShape(maxDims);
			}
		} catch (HDF5Exception ex) {
			logger.error("Could not get data space information", ex);
			return false;
		} finally {
			if (ntid != -1) {
				try {
					H5.H5Tclose(ntid);
				} catch (HDF5Exception ex) {
				}
			}
			if (sid != -1) {
				try {
					H5.H5Sclose(sid);
				} catch (HDF5Exception ex) {
				}
			}
		}

		trueShape = HDF5Utils.toIntArray(dims);

		if (trueShape.length == 1 && trueShape[0] == 1) { // special case for single values
			try {
				final Dataset d = DatasetFactory.zeros(type.isize, trueShape, type.dtype);
				Object data = d.getBuffer();

				if (type.vlen) {
					H5.H5DreadVL(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, (Object[]) data);
				} else {
					H5.H5Dread(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
				}

				d.setName(name);
				node.setDataset(d);
				return true;
			} catch (HDF5Exception ex) {
				logger.error("Could not read single value dataset", ex);
				return false;
			}
		}

		final boolean extendUnsigned = !keepBitWidth && type.unsigned;
		
		// cope with external files specified in a non-standard way and which may not be HDF5 either
		if (type.dtype == Dataset.STRING && useExternalFiles) {
			// interpret set of strings as the full path names to a group of external files that are stacked together

			StringDataset ef = extractExternalFileNames(did, tid, type.vlen, trueShape);
			ImageStackLoaderEx loader;
			try {
				loader = new ImageStackLoaderEx(ef, null);
			} catch (Exception e) {
				try { // try again with known-to-be-good directory
					loader = new ImageStackLoaderEx(ef, file.getParentDirectory());
				} catch (Exception e2) {
					logger.error("Could creating loader from external files", e2);
					return false;
				}
			}
			loader.setMaxShape(node.getMaxShape());
			loader.squeeze();
			// set dataset information again as loader now has correct shapes
			node.setMaxShape(loader.getMaxShape());
			node.setChunkShape(loader.getChunkShape());
			node.setDataset(new LazyDataset(name, loader.getDtype(), loader.getShape(), loader));
			return true;
		}

		// check for zero-sized datasets
		long trueSize = AbstractDataset.calcLongSize(trueShape);
		if (trueSize == 0) {
			node.setEmpty();
			return true;
		}

		HDF5LazyLoader l = new HDF5LazyLoader(file.getHostname(), file.getFilename(), nodePath, name, trueShape, type.isize, type.dtype, extendUnsigned);

		int[] max = null;
		try {
			max = HDF5Utils.toIntArray(node.getMaxShape());
		} catch (Exception e) {
			logger.warn("Max shape cant convert to int");
		}
		
		
		node.setDataset(new LazyDynamicDataset(name, type.dtype, type.isize, trueShape.clone(), max, l));
		return true;
	}

	/**
	 * Return any External File references to be followed.
	 * @return string dataset
	 * @throws Exception
	 */
	private static StringDataset extractExternalFileNames(final long did, final long tid, final boolean isVLEN, final int[] shape) throws Exception {

		final StringDataset d = new StringDataset(shape);
		Object data = d.getBuffer();

		if (isVLEN) {
			H5.H5DreadVL(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, (Object[]) data);
		} else {
			H5.H5Dread(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
		}

		return d;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		loadTree(mon);

		DataHolder dh = new DataHolder();
		dh.setFilePath(fileName);
		dh.setTree(tFile);

		updateDataHolder(dh, true);
		metadata = (Metadata) dh.getMetadata();
	}

	/**
	 * Create data holder from tree
	 * @param dh
	 * @param withMetadata
	 */
	public static void updateDataHolder(IDataHolder dh, boolean withMetadata) {
		Tree tree = dh.getTree();
		if (tree == null)
			return;

		// Change to TreeMap so that order maintained
		Map<String, ILazyDataset> lMap = new LinkedHashMap<String, ILazyDataset>();
		Map<String, Serializable> aMap = withMetadata ? new LinkedHashMap<String, Serializable>() : null;
		addToMaps("", tree.getNodeLink(), lMap, aMap);

		dh.clear();
		dh.setTree(tree);
		if (withMetadata) {
			Metadata metadata = new Metadata(aMap);
			if (tree instanceof TreeFile)
				metadata.setFilePath(((TreeFile) tree).getFilename());
			for (Entry<String, ILazyDataset> e : lMap.entrySet()) {
				String n = e.getKey();
				ILazyDataset l = e.getValue();
				if (l != null) {
					dh.addDataset(n, l);
					metadata.addDataInfo(n, l.getShape());
					// also add datasets under their local name (if defined) 
					try {

						// TODO FIXME local name pollutes the DataHolder with 
						// ghost entries which do not have an H5 location.
						// This does not play nicely with some GUI in DAWN
						// which assumes that the holder has a list of complete
						// paths. For now this GUI is changed to ignore holder keys
						// which do not start with "/"
						String attr = n.concat("@local_name");
						Object localattr = metadata.getMetaValue(attr);
						if (localattr == null) continue;
						String localname = localattr.toString();
						if (localname == null || localname.isEmpty()) continue;
						if (lMap.keySet().contains(localname)) continue;
						dh.addDataset(localname, l);
					} catch (Exception ex) {
						logger.info("seen an exception populating local nexus name for "+n, ex);
					}
				}
			}
			dh.setMetadata(metadata);
		} else {
			for (Entry<String, ILazyDataset> e : lMap.entrySet()) {
				dh.addDataset(e.getKey(), e.getValue());
			}
		}
	}

	/**
	 * Adds lazy datasets and the attributes and scalar dataset items in a node to the given maps recursively
	 * @param ppath - parent path
	 * @param link - link to node to investigate
	 * @param lMap - the lazy dataset map to add items to, to aid the recursive method
	 * @param aMap - the attribute map to add items to, to aid the recursive method (can be null)
	 */
	private static void addToMaps(String ppath, NodeLink link, Map<String, ILazyDataset> lMap, Map<String, Serializable> aMap) {
		Node node = link.getDestination();
		String cpath = ppath + link.getName();
		if (aMap != null) {
			Iterator<String> iter = node.getAttributeNameIterator();
			String name = cpath + Node.ATTRIBUTE;
			while (iter.hasNext()) {
				String attr = iter.next();
				aMap.put(name + attr, node.getAttribute(attr).getFirstElement());
			}
		}

		if (node instanceof DataNode) {
			ILazyDataset dataset = ((DataNode) node).getDataset();
			if (dataset == null)
				return;

			if (lMap != null)
				lMap.put(cpath, dataset);
			if (aMap != null && dataset instanceof Dataset) { // zero-rank dataset
				Dataset a = (Dataset) dataset;
				aMap.put(cpath, a.getRank() == 0 ? a.getString() : a.getString(0));
			}
		} else if (node instanceof GroupNode) {
			GroupNode g = (GroupNode) node;
			Collection<String> names = g.getNames();
			for (String n: names) {
				addToMaps(ppath.isEmpty() ? cpath : cpath  + Node.SEPARATOR, g.getNodeLink(n), lMap, aMap);
			}
		}
	}

	/**
	 * Augment a dataset with metadata that is pointed by link
	 * @param link
	 */
	@SuppressWarnings("unused")
	public void augmentLink(NodeLink link) {
	}

	/**
	 * Find all datasets of given names at given depth
	 * @param names
	 * @param depth
	 * @param mon
	 * @return list of datasets
	 * @throws ScanFileHolderException 
	 */
	public List<ILazyDataset> findDatasets(final String[] names, final int depth, IMonitor mon) throws ScanFileHolderException {
		ArrayList<ILazyDataset> list = new ArrayList<ILazyDataset>();

		try {
			fileName = new File(fileName).getCanonicalPath();
		} catch (IOException e) {
			logger.error("Could not get canonical path", e);
			throw new ScanFileHolderException("Could not get canonical path", e);
		}

		try {
			long fid = HDF5FileFactory.acquireFile(fileName, false);
			if (!monitorIncrement(mon)) {
				return null;
			}

			final long oid = fileName.hashCode(); // include file name in ID
			TreeFile f = TreeFactory.createTreeFile(oid, fileName);
			f.setHostname(host);

			visitGroup(fid, f, Tree.ROOT, Arrays.asList(names), 0, depth, list);

		} catch (Throwable le) {
			throw new ScanFileHolderException("Problem loading file: " + fileName, le);
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
		return list;
	}

	private void visitGroup(final long fid, final TreeFile f, final String name, final List<String> names, int cDepth, int rDepth, ArrayList<ILazyDataset> list) throws Exception {
		long gid = -1;

		try {
			int nelems = 0;
			try {
				gid = H5.H5Gopen(fid, name, HDF5Constants.H5P_DEFAULT);
				H5G_info_t info = H5.H5Gget_info(gid);
				nelems = (int) info.nlinks;
			} catch (HDF5Exception ex) {
				throw new ScanFileHolderException("Could not open group", ex);
			}

			byte[] idbuf = null;
			long oid = -1;
			try {
				idbuf = H5.H5Rcreate(fid, name, HDF5Constants.H5R_OBJECT, -1);
				oid = HDFNativeData.byteToLong(idbuf, 0);
			} catch (HDF5Exception ex) {
				throw new ScanFileHolderException("Could not find group reference", ex);
			}

			if (nelems <= 0) {
				return;
			}

			int[] oTypes = new int[nelems];
			int[] lTypes = new int[nelems];
			long[] oids = new long[nelems];
			String[] oNames = new String[nelems];
			try {
				H5.H5Gget_obj_info_all(fid, name, oNames, oTypes, lTypes, oids, HDF5Constants.H5_INDEX_NAME);
			} catch (HDF5Exception ex) {
				logger.error("Could not get objects info in group", ex);
				return;
			}

			String oname;
			int otype;
			int ltype;

			// Iterate through the file to see members of the group
			for (int i = 0; i < nelems; i++) {
				oname = oNames[i];
				if (oname == null) {
					continue;
				}
				otype = oTypes[i];
				ltype = lTypes[i];
				oid = oids[i];

				if (ltype == HDF5Constants.H5L_TYPE_HARD) {
					if (otype == HDF5Constants.H5O_TYPE_GROUP && cDepth < rDepth) {
						// System.err.println("G: " + oname);
						visitGroup(fid, f, name + oname + Node.SEPARATOR, names,
								cDepth + 1, rDepth, list);
					} else if (otype == HDF5Constants.H5O_TYPE_DATASET && cDepth == rDepth) {
						// System.err.println("D: " + oname);
						if (names.contains(oname)) {
							
							long did = -1, tid = -1;
							try {
								did = H5.H5Dopen(gid, oname, HDF5Constants.H5P_DEFAULT);
								tid = H5.H5Dget_type(did);
	
								// create a new dataset
								DataNode d = TreeFactory.createDataNode(oid);
								if (!createLazyDataset(f, d, name + oname, oname, did, tid, keepBitWidth,
										d.containsAttribute(DATA_FILENAME_ATTR_NAME))) {
									logger.error("Could not create a lazy dataset {} from {}", oname, name);
									continue;
								}
								if (d.getDataset() != null)
									list.add(d.getDataset());
							} catch (HDF5Exception ex) {
								logger.error(String.format("Could not open dataset (%s) %s in %s", name, oname, f), ex);
							} finally {
								if (tid >= 0) {
									try {
										H5.H5Tclose(tid);
									} catch (HDF5Exception ex) {
									}
								}
								if (did >= 0) {
									try {
										H5.H5Dclose(did);
									} catch (HDF5Exception ex) {
									}
								}
							}
						}
					} else if (otype == HDF5Constants.H5O_TYPE_NAMED_DATATYPE) {
						logger.error("Named datatype not supported"); // TODO
					}
				} else if (ltype == HDF5Constants.H5L_TYPE_SOFT) {
					// System.err.println("S: " + oname);
				} else if (ltype == HDF5Constants.H5L_TYPE_EXTERNAL) {
					// System.err.println("E: " + oname);
				} else {
					// System.err.println("U: " + oname);
				}
			}
		} finally {
			if (gid >= 0) {
				try {
					H5.H5Gclose(gid);
				} catch (HDF5Exception ex) {
				}
			}
		}
	}
	
	protected Dataset slice(SliceObject object, @SuppressWarnings("unused") IMonitor mon) throws Exception {
		final int[] start = object.getSliceStart();
		final int[] stop = object.getSliceStop();
		final int[] step = object.getSliceStep();
		int[] lstart, lstop, lstep;
		int rank;
		if (start != null)
			rank = start.length;
		else if (stop != null)
			rank = stop.length;
		else if (step != null)
			rank = step.length;
		else
			throw new IllegalArgumentException("Slice object does not have any info about rank");

		if (step == null) {
			lstep = new int[rank];
			for (int i = 0; i < rank; i++) {
				lstep[i] = 1;
			}
		} else {
			lstep = step;
		}

		if (start == null) {
			lstart = new int[rank];
		} else {
			lstart = start;
		}

		if (stop == null) {
			lstop = object.getSlicedShape();
			if (lstop == null)
				lstop = object.getFullShape();
		} else {
			lstop = stop;
		}

		if (lstop == null)
			throw new IllegalArgumentException("Slice object does not have any info about stop or shape");

		int[] newShape = new int[rank];

		for (int i = 0; i < rank; i++) {
			if (lstep[i] > 0) {
				newShape[i] = (lstop[i] - lstart[i] - 1) / lstep[i] + 1;
			} else {
				newShape[i] = (lstop[i] - lstart[i] + 1) / lstep[i] + 1;
			}
		}

		return HDF5Utils.loadDataset(object.getPath(), object.getName(), lstart, newShape, lstep, -1, 1, true);
	}
}
