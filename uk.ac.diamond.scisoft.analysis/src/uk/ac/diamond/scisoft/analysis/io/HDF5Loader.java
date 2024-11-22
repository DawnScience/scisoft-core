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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5LazyLoader;
import org.eclipse.dawnsci.hdf5.HDF5Token;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.hdf5.HDF5Utils.DatasetType;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.LazyDynamicDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.OriginMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;
import hdf.hdf5lib.structs.H5G_info_t;
import hdf.hdf5lib.structs.H5L_info_t;
import hdf.hdf5lib.structs.H5O_info_t;
import hdf.hdf5lib.structs.H5O_token_t;

/**
 * Load HDF5 files using NCSA's Java library
 */
public class HDF5Loader extends AbstractFileLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(HDF5Loader.class);


	private boolean keepBitWidth = false;
	private boolean async = false;
	private int syncLimit;
	private int syncNodes;
	private ScanFileHolderException syncException = null;

	private static String host = HDF5Utils.getLocalHostName();

	public static final String DATA_FILENAME_ATTR_NAME = "data_filename";

	private static int EXT_LINK_MAX_DEPTH = 8; // maximum depth to follow external links

	public HDF5Loader() {
	}

	public HDF5Loader(final String name) {
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
			metadata = dh.getMetadata();
		}
		return dh;
	}

	/**
	 * @param tree
	 * @return data holder for tree
	 */
	public DataHolder createDataHolder(Tree tree) {
		DataHolder dh = new DataHolder();
		dh.setFilePath(fileName);
		dh.setTree(tree);
		updateDataHolder(dh, loadMetadata);
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
				long fid = HDF5FileFactory.acquireFile(fileName, false).getID();

				if (!monitorIncrement(mon)) {
					return;
				}

				tFile = createTreeBF(mon, fid, keepBitWidth);
			} catch (Exception le) {
				syncException = new ScanFileHolderException("Problem loading file: " + fileName, le);
				try {
					updateSyncNodes(syncLimit); // prevent deadlock
				} catch (Exception e) {
					// do nothing
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
				// do nothing
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

		logger.trace("Loading in thd {}", Thread.currentThread().threadId());
		File f = new File(fileName);
		if (!f.exists()) {
			throw new ScanFileHolderException("File, " + fileName + ", does not exist");
		}
		
		try {
			fileName = f.getCanonicalPath();
		} catch (IOException e) {
			logger.error("Could not get canonical path for {}", fileName, e);
			throw new ScanFileHolderException("Could not get canonical path for " + fileName, e);
		}
		if (async) {
			loaderThread = new LoadFileThread(mon);
			loaderThread.start();
			try {
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				// do nothing
			}
			waitForSyncLimit();
		} else {
			try {
				long fid = HDF5FileFactory.acquireFile(fileName, false).getID();

				if (!monitorIncrement(mon)) {
					return null;
				}

				tFile = createTree(fid, keepBitWidth);
			} catch (Exception le) {
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

		GroupNode g = (GroupNode) createGroup(fid, f, null, new HashMap<>(), null, Tree.ROOT, keepBitWidth);
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

		HashMap<HDF5Token, Node> pool = new HashMap<>();
		Queue<String> iqueue = new LinkedList<>();
		GroupNode g = (GroupNode) createGroup(fid, f, null, pool, iqueue, Tree.ROOT, keepBitWidth);
		if (g == null) {
			throw new ScanFileHolderException("Could not copy root group");
		}
		f.setGroupNode(g);

		Queue<String> oqueue = new LinkedList<>();
		while (!iqueue.isEmpty()) { // read in first level
			String nn = iqueue.remove();
			Node n = createGroup(fid, f, null, pool, oqueue, nn, keepBitWidth);
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
			while (!iqueue.isEmpty()) {
				updateSyncNodes(pool.size());
				String nn = iqueue.remove();
				Node n = createGroup(fid, f, null, pool, oqueue, nn, keepBitWidth);
				if (n == null) {
					logger.error("Could not find group {}", nn);
					continue;
				}
				int i = nn.lastIndexOf(Node.SEPARATOR, nn.length() - 2);
				NodeLink ol = f.findNodeLink(nn.substring(0, i));
				GroupNode og = (GroupNode) ol.getDestination();
				String gName = nn.substring(i + 1, nn.length() - 1);
				og.addNode(gName, n);
				if (n instanceof GroupNode && NexusTreeUtils.isNXClass(n, NexusConstants.DATA)) {
					augmentLink(nn, og.getNodeLink(gName));
				}
			}
			if (!monitorIncrement(mon)) {
				updateSyncNodes(syncLimit); // notify if finished
				return null;
			}
		} while (!oqueue.isEmpty());

		updateSyncNodes(syncLimit); // notify if finished

		return f;
	}

	private static final int LIMIT = 10240;

	/**
	 * Create a node (and all its children, recursively) from given location ID
	 * @param fid location ID
	 * @param f HDF5 file
	 * @param name of group (full path and ends in '/')
	 * @param keepBitWidth
	 * @param depth
	 * @return node
	 * @throws Exception
	 */
	private Node createNode(final long fid, final TreeFile f, final String name, final boolean keepBitWidth, int depth) throws Exception {
		try {
			H5L_info_t linfo = H5.H5Lget_info(fid, name, HDF5Constants.H5P_DEFAULT);
			int lt = linfo.type;
			HDF5Token token = new HDF5Token(linfo.token);
			if (lt == HDF5Constants.H5L_TYPE_EXTERNAL) {
				return retrieveExternalNode(fid, f, null, token, null, name, keepBitWidth, depth + 1);
			} else if (lt == HDF5Constants.H5L_TYPE_SOFT) {
				return retrieveSymbolicNode(fid, f, null, token, name);
			} else if (lt == HDF5Constants.H5L_TYPE_HARD) {
				H5O_info_t info = H5.H5Oget_info_by_name(fid, name, HDF5Constants.H5O_INFO_BASIC, HDF5Constants.H5P_DEFAULT);
				int t = info.type;
				HDF5Token oToken = new HDF5Token(info.token);
				
				if (t == HDF5Constants.H5O_TYPE_GROUP) {
					return createGroup(fid, f, oToken, null, null, name, keepBitWidth);
				} else if (t == HDF5Constants.H5O_TYPE_DATASET) {
					return createDataset(fid, f, oToken, null, name, keepBitWidth);
				} else if (t == HDF5Constants.H5O_TYPE_NAMED_DATATYPE) {
					logger.error("Named datatype not supported"); // TODO
				} else {
					logger.error("Unknown object type");
				}
			}
		} catch (HDF5Exception ex) {
			logger.error("Could not find info about object {}", name, ex);
		}
		return null;
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
	private Node createGroup(final long fid, final TreeFile f, HDF5Token token, final HashMap<HDF5Token, Node> pool, final Queue<String> queue, final String name, final boolean keepBitWidth) throws Exception {
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

			if (token == null) {
				try {
					byte[] idbuf = H5.H5Rcreate(fid, name, HDF5Constants.H5R_OBJECT, -1);
					token = new HDF5Token(idbuf);
				} catch (HDF5Exception ex) {
					throw new ScanFileHolderException("Could not find group reference", ex);
				}
			}
			if (pool != null && pool.containsKey(token)) {
				Node p = pool.get(token);
				if (!(p instanceof GroupNode)) {
					throw new IllegalStateException("Matching pooled node is not a group");
				}
				return p;
			}

			group = TreeFactory.createGroupNode(token.getData());
			if (pool != null)
				pool.put(token, group);
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
						name, nelems, LIMIT);
				nelems = LIMIT;
			}

			final int[] lTypes = new int[1];
			final String[] oNames = new String[1];

			// Iterate through the group to see its members
			for (int i = 0; i < nelems; i++) {
				try {
					H5.H5Gget_obj_info_idx(fid, name, i, oNames, lTypes);
				} catch (HDF5Exception ex) {
					logger.error("Could not get object info for {}-th link in group", i, ex);
					continue;
				}
				final String oname = oNames[0];
				final int ltype = lTypes[0];

				if (ltype == HDF5Constants.H5L_TYPE_HARD) {
					final int otype;
					HDF5Token lToken = null;
					try {
						H5O_info_t info = H5.H5Oget_info_by_name(gid, oname, HDF5Constants.H5O_INFO_BASIC, HDF5Constants.H5P_DEFAULT);
						otype = info.type;
						lToken = new HDF5Token(info.token);
					} catch (HDF5Exception ex) {
						logger.error("Could not get object info for {} in group {}", oname, name, ex);
						continue;
					}
					if (otype == HDF5Constants.H5O_TYPE_GROUP) {
						if (pool != null && pool.containsKey(lToken)) {
							Node p = pool.get(lToken);
							if (!(p instanceof GroupNode)) {
								throw new IllegalStateException("Matching pooled node is not a group");
							}
							group.addNode(oname, p);
							continue;
						}

						String newname = name + Node.SEPARATOR + oname + Node.SEPARATOR;
						newname = newname.replaceAll("([" + Node.SEPARATOR + "])\\1+",
								Node.SEPARATOR);
						if (queue != null) {
							queue.add(newname);
						} else {
							Node g = createGroup(fid, f, lToken, pool, queue, newname, keepBitWidth);
							if (g == null) {
								logger.error("Could not load group {} in {}", oname, name);
							} else {
								group.addNode(oname, g);

								if (NexusTreeUtils.isNXClass(g, NexusConstants.DATA)) {
									augmentLink(newname, group.getNodeLink(oname));
								}
							}
						}
					} else if (otype == HDF5Constants.H5O_TYPE_DATASET) {
						if (pool != null && pool.containsKey(lToken)) {
							Node p = pool.get(lToken);
							if (!(p instanceof DataNode)) {
								throw new IllegalStateException("Matching pooled node is not a dataset");
							}
							group.addNode(oname, p);
							continue;
						}

						String newname = name + Node.SEPARATOR + oname;
						newname = newname.replaceAll("([" + Node.SEPARATOR + "])\\1+",
								Node.SEPARATOR);
						Node n = createDataset(fid, f, lToken, pool, newname, keepBitWidth);

						if (n != null)
							group.addNode(oname, n);
					} else if (otype == HDF5Constants.H5O_TYPE_NAMED_DATATYPE) {
						logger.error("Named datatype not supported for {}", oname); // TODO
					} else {
						logger.error("Something wrong with hardlinked object {}", oname);
					}
				} else if (ltype == HDF5Constants.H5L_TYPE_SOFT) {
					group.addNode(oname, retrieveSymbolicNode(gid, f, group, token, oname));
				} else if (ltype == HDF5Constants.H5L_TYPE_EXTERNAL) {
					group.addNode(oname, retrieveExternalNode(gid, f, group, token, pool, oname, keepBitWidth, 0));
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

		return group;
	}

	private HDF5Token createExternalNodeToken(String filePath, String nodePath) {
		MessageDigest hasher;
		try {
			hasher = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error("Could not get digester algorithm for SHA-256", e);
			return null;
		}
		byte[] hash = hasher.digest(String.join(":", filePath, nodePath).getBytes(StandardCharsets.UTF_8));
		return new HDF5Token(Arrays.copyOf(hash, 16));
	}

	private Node retrieveExternalNode(final long lid, final TreeFile f, GroupNode group, HDF5Token token, Map<HDF5Token, Node> pool, final String name, final boolean keepBitWidth, int depth) throws Exception {
		String[] linkName = new String[2]; // object path and file path
		int t = H5.H5Lget_value(lid, name, linkName, HDF5Constants.H5P_DEFAULT);
		if (t < 0) {
			logger.error("Could not get value of link for {}", name);
		}

		HDF5Token nToken = createExternalNodeToken(linkName[1], linkName[0]);
		if (nToken != null && pool != null && pool.containsKey(nToken)) {
			return pool.get(nToken);
		}

		String eName = Utils.findExternalFilePath(logger, f.getParentDirectory(), linkName[1]);
		if (eName != null && depth < EXT_LINK_MAX_DEPTH) {
			Node eNode = getExternalNode(f.getHostname(), eName, linkName[0], keepBitWidth, depth);
			if (pool != null) {
				pool.put(nToken, eNode);
			}
			return eNode;
		}
		eName = linkName[1];
		if (depth >= EXT_LINK_MAX_DEPTH) {
			logger.error("External link retrieval has reached maximum depth of {} with {}", depth, eName);
		} else {
			logger.warn("Could not find external file {} so adding symbolic node", eName);
		}

		String edName = linkName[0];
		if (!edName.startsWith(Tree.ROOT)) {
			edName = Tree.ROOT + edName;
		}
		return TreeFactory.createSymbolicNode(token.getData(), eName == null ? null : new URI(eName), group, edName);
	}

	private Node retrieveSymbolicNode(final long lid, final TreeFile f, GroupNode group, HDF5Token token, final String name) throws Exception {
		String[] linkName = new String[1];
		int t = H5.H5Lget_value(lid, name, linkName, HDF5Constants.H5P_DEFAULT);
		if (t < 0) {
			logger.warn("Could not get value of link for {}", name);
		}

		return TreeFactory.createSymbolicNode(token.getData(), f, group, linkName[0]);
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
	private Node createDataset(final long lid, final TreeFile f, HDF5Token token, final HashMap<HDF5Token, Node> pool, final String path, final boolean keepBitWidth) throws Exception {
		if (token == null) {
			try {
				byte[] idbuf = H5.H5Rcreate(lid, path, HDF5Constants.H5R_OBJECT, -1);
				token = new HDF5Token(idbuf);
			} catch (HDF5Exception ex) {
				throw new ScanFileHolderException("Could not find object reference", ex);
			}
		}
		if (pool != null && pool.containsKey(token)) {
			Node p = pool.get(token);
			if (!(p instanceof DataNode)) {
				throw new IllegalStateException("Matching pooled node is not a dataset");
			}
			return p;
		}

		long did = -1, tid = -1;
		try {
			did = H5.H5Dopen(lid, path, HDF5Constants.H5P_DEFAULT);
			tid = H5.H5Dget_type(did);

			// create a new dataset
			DataNode d = TreeFactory.createDataNode(token.getData());
			if (copyAttributes(path, d, did)) {
				final String link = d.getAttribute(NAPIMOUNT).getFirstElement();
				return copyNAPIMountNode(f, pool, link, keepBitWidth);
			}
			int i = path.lastIndexOf(Node.SEPARATOR);
			final String sname = i >= 0 ? path.substring(i + 1) : path;
			if (!createLazyDataset(f, d, path, sname, did, tid, keepBitWidth,
					d.containsAttribute(DATA_FILENAME_ATTR_NAME), loadLazily, !f.getFilename().equals(fileName))) {
				logger.error("Could not create a lazy dataset from {}", path);
			}
			if (pool != null)
				pool.put(token, d);
			return d;
		} catch (HDF5Exception ex) {
			logger.error("Could not open dataset {} in {}", path, f, ex);
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
				h.setTypeName(DTypeUtils.getDTypeName(a));
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

	private Node getExternalNode(final String host, final String path, String node, final boolean keepBitWidth, int depth) throws Exception {
		Node nn = null;

		if (!node.startsWith(Tree.ROOT)) {
			node = Tree.ROOT + node;
		}

		try {
			long fid = HDF5FileFactory.acquireFile(path, false).getID();

			final long oid = path.hashCode(); // include file name in ID
			TreeFile f = TreeFactory.createTreeFile(oid, path);
			f.setHostname(host);

			nn = createNode(fid, f, node, keepBitWidth, depth);
			if (nn instanceof DataNode dn) {
				ILazyDataset ld = dn.getDataset();
				OriginMetadata om = ld.getFirstMetadata(OriginMetadata.class);
				if (om == null || !path.equals(om.getFilePath())) {
					ld.addMetadata(MetadataFactory.createMetadata(OriginMetadata.class, null, null, null, path, node));
				}
			}
		} catch (Exception le) {
			throw new ScanFileHolderException("Problem loading file: " + path, le);
		} finally {
			HDF5FileFactory.releaseFile(path);
		}
		
		return nn;
	}

	// retrieve external file link
	private Node copyNAPIMountNode(final TreeFile file, Map<HDF5Token, Node> pool, final String link, final boolean keepBitWidth) throws Exception {
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
			HDF5Token nToken = createExternalNodeToken(lpath, ltarget);
			if (nToken != null && pool != null && pool.containsKey(nToken)) {
				return pool.get(nToken);
			}

			nn = getExternalNode(file.getHostname(), lpath, ltarget, keepBitWidth, 0);
			if (nn == null)
				logger.warn("Could not find external node: {}", ltarget);
		} else {
			logger.error("Wrong scheme: {}", ulink.getScheme());
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
	 * @param loadLazily
	 * @param isExternal
	 * @return true if created
	 * @throws Exception
	 */
	private static boolean createLazyDataset(final TreeFile file, final DataNode node,
			final String nodePath, final String name, final long did, final long tid,
			final boolean keepBitWidth, final boolean useExternalFiles, boolean loadLazily, boolean isExternal) throws Exception {
		long sid = -1, pid = -1;
		long ntid = -1;
		int rank;
		long[] dims;
		DatasetType type;
		final int[] trueShape;

		boolean isBoolean = HDF5Utils.isDataNodeBoolean(node);

		try {
			H5.H5Drefresh(did);
			sid = H5.H5Dget_space(did);
			rank = H5.H5Sget_simple_extent_ndims(sid);

			ntid = H5.H5Tget_native_type(tid);
			type = HDF5Utils.getDatasetType(tid, ntid);
			if (type == null) { // inhomogeneous datatype
				return false;
			}
			if (type.name != null) {
				node.setTypeName(type.name);
			}

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

			dims = new long[rank];
			long[] maxDims = new long[rank];
			if (rank > 0) {
				H5.H5Sget_simple_extent_dims(sid, dims, maxDims);
			}
			node.setMaxShape(maxDims);
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
		int[] maxShape = HDF5Utils.toIntArray(node.getMaxShape());

		if (rank == 0 || (trueShape.length == 1 && trueShape[0] == 1 && maxShape.length == 1 && maxShape[0] == 1)) { // special case for single values
			try {
				Dataset d = DatasetFactory.zeros(type.isize, type.clazz, trueShape);
				Object data = d.getBuffer();

				if (type.clazz.isAssignableFrom(StringDataset.class)) {
					if (type.isVariableLength) {
						H5.H5Dread_VLStrings(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, (Object[]) data);
					} else {
						H5.H5Dread_string(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, (String[]) data);
					}
				} else {
					if (type.isVariableLength) {
						logger.error("Non-string variable length datasets are not supported");
						return false;
					}
					H5.H5Dread(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
				}

				if (isBoolean) {
					d = d.cast(type.isize, BooleanDataset.class, false);
				}
				if (type.unsigned) {
					d = DatasetUtils.makeUnsigned(d, true);
				}
				d.setName(name);
				node.setDataset(d);
				node.setTypeName(DTypeUtils.getDTypeName(d));
				return true;
			} catch (HDF5Exception ex) {
				logger.error("Could not read single value dataset", ex);
				return false;
			}
		}

		boolean extendUnsigned = !keepBitWidth && type.unsigned;
		if (extendUnsigned && type.clazz.equals(ByteDataset.class)) {
			String intr = NexusTreeUtils.getFirstString(node.getAttribute(NexusConstants.INTERPRETATION));
			if (intr != null && intr.contains("image")) {
				extendUnsigned = false; // override in case of image
			}
		}

		// cope with external files specified in a non-standard way and which may not be HDF5 either
		if (StringDataset.class.isAssignableFrom(type.clazz) && useExternalFiles) {
			// interpret set of strings as the full path names to a group of external files that are stacked together

			StringDataset ef = extractExternalFileNames(did, tid, type.isVariableLength, trueShape);
			ImageStackLoader loader;
			try {
				loader = new ImageStackLoader(ef, file.getParentDirectory());
			} catch (Exception e) {
				logger.error("Could creating loader from external files", e);
				return false;
			}
			loader.setMaxShape(node.getMaxShape());
			if (trueShape.length > 1) {
				loader.squeeze();
			}
			// set dataset information again as loader now has correct shapes
			node.setMaxShape(loader.getMaxShape());
			node.setChunkShape(loader.getChunkShape());
			node.setDataset(loader.createLazyDataset(name));
			return true;
		}

		if (!loadLazily) {
			// check for zero-sized datasets
			long trueSize = ShapeUtils.calcLongSize(trueShape);
			if (trueSize == 0) {
				node.setEmpty();
				return true;
			}
		}

		Class<? extends Dataset> lClazz = isBoolean ? BooleanDataset.class : type.clazz;
		HDF5LazyLoader l = new HDF5LazyLoader(file.getHostname(), file.getFilename(), nodePath, name, trueShape, type.isize, lClazz, extendUnsigned);

		long[] chunks = node.getChunkShape();
		LazyDynamicDataset lazy = new LazyDynamicDataset(l, name, type.isize, lClazz, trueShape.clone(), maxShape, chunks == null ? null : HDF5Utils.toIntArray(chunks));
		if (isExternal) {
			try {
				lazy.addMetadata(MetadataFactory.createMetadata(OriginMetadata.class, null, null, null, file.getFilename(), nodePath));
			} catch (Exception e) {
				// do nothing
			}
		}
		node.setDataset(lazy);
		return true;
	}

	/**
	 * Return any External File references to be followed.
	 * @return string dataset
	 * @throws Exception
	 */
	private static StringDataset extractExternalFileNames(final long did, final long tid, final boolean isVLEN, final int[] shape) throws Exception {

		final StringDataset d = DatasetFactory.zeros(StringDataset.class, shape);
		Object data = d.getBuffer();

		if (isVLEN) {
			H5.H5Dread_VLStrings(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, (Object[]) data);
		} else {
			H5.H5Dread(did, tid, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
		}

		return d;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws IOException {
		try {
			loadTree(mon);
		} catch (ScanFileHolderException e) {
			throw new IOException(e);
		}

		DataHolder dh = new DataHolder();
		dh.setFilePath(fileName);
		dh.setTree(tFile);

		updateDataHolder(dh, true);
		metadata = dh.getMetadata();
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
		Map<String, ILazyDataset> lMap = new LinkedHashMap<>();
		Map<String, Serializable> aMap = withMetadata ? new LinkedHashMap<>() : null;
		addToMaps("", tree.getNodeLink(), lMap, aMap);

		dh.clear();
		dh.setTree(tree);
		if (withMetadata) {
			IMetadata metadata = new Metadata();
			metadata.initialize(aMap);
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
						if (lMap.containsKey(localname)) continue;
						dh.addDataset(localname, l);
					} catch (Exception ex) {
						logger.info("Seen an exception populating local nexus name for {}", n, ex);
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

		while (node instanceof SymbolicNode s) {
			link = s.getNodeLink();
			node = link == null ? null : link.getDestination();
		}
		if (node instanceof DataNode d) {
			ILazyDataset dataset = d.getDataset();
			if (dataset == null)
				return;

			if (lMap != null)
				lMap.put(cpath, dataset);
			if (aMap != null && (dataset instanceof Dataset a) && a.getSize() <= 1) { // zero-rank dataset
				aMap.put(cpath, a.getRank() == 0 ? a.getString() : a.getString(0));
			}
		} else if (node instanceof GroupNode g) {
			Collection<String> names = g.getNames();
			for (String n: names) {
				addToMaps(ppath.isEmpty() ? cpath : cpath  + Node.SEPARATOR, g.getNodeLink(n), lMap, aMap);
			}
		}
	}

	/**
	 * Augment a dataset with metadata within group that is pointed by link
	 * @param groupPath
	 * @param link
	 */
	public void augmentLink(String groupPath, NodeLink link) {
		// do nothing
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
			logger.error("Could not get canonical path for {}", fileName, e);
			throw new ScanFileHolderException("Could not get canonical path for " + fileName, e);
		}

		try {
			long fid = HDF5FileFactory.acquireFile(fileName, false).getID();
			if (!monitorIncrement(mon)) {
				return null;
			}

			final long oid = fileName.hashCode(); // include file name in ID
			TreeFile f = TreeFactory.createTreeFile(oid, fileName);
			f.setHostname(host);

			visitGroup(fid, f, Tree.ROOT, Arrays.asList(names), 0, depth, list);

		} catch (Exception le) {
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

			if (nelems <= 0) {
				return;
			}

			int[] oTypes = new int[nelems];
			int[] lTypes = new int[nelems];
			H5O_token_t[] oTokens = new H5O_token_t[nelems];
			String[] oNames = new String[nelems];
			try {
				H5.H5Gget_obj_info_all(fid, name, oNames, oTypes, lTypes, oTokens, HDF5Constants.H5_INDEX_NAME);
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
				H5O_token_t oToken = oTokens[i];

				if (ltype == HDF5Constants.H5L_TYPE_HARD) {
					if (otype == HDF5Constants.H5O_TYPE_GROUP && cDepth < rDepth) {
						visitGroup(fid, f, name + oname + Node.SEPARATOR, names,
								cDepth + 1, rDepth, list);
					} else if (otype == HDF5Constants.H5O_TYPE_DATASET && cDepth == rDepth) {
						if (names.contains(oname)) {
							
							long did = -1, tid = -1;
							try {
								did = H5.H5Dopen(gid, oname, HDF5Constants.H5P_DEFAULT);
								tid = H5.H5Dget_type(did);
	
								// create a new dataset
								DataNode d = TreeFactory.createDataNode(oToken.data);
								if (!createLazyDataset(f, d, name + oname, oname, did, tid, keepBitWidth,
										d.containsAttribute(DATA_FILENAME_ATTR_NAME), loadLazily, false)) {
									logger.error("Could not create a lazy dataset {} from {}", oname, name);
									continue;
								}
								if (d.getDataset() != null) {
									list.add(d.getDataset());
								}
							} catch (HDF5Exception ex) {
								logger.error("Could not open dataset ({}) {} in {}", name, oname, f, ex);
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
}
