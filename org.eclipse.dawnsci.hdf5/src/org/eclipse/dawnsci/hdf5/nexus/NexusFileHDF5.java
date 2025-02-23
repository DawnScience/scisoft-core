/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5.nexus;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.HDF5AttributeResource;
import org.eclipse.dawnsci.hdf5.HDF5DatasetResource;
import org.eclipse.dawnsci.hdf5.HDF5DataspaceResource;
import org.eclipse.dawnsci.hdf5.HDF5DatatypeResource;
import org.eclipse.dawnsci.hdf5.HDF5File;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5FileResource;
import org.eclipse.dawnsci.hdf5.HDF5LazyLoader;
import org.eclipse.dawnsci.hdf5.HDF5LazySaver;
import org.eclipse.dawnsci.hdf5.HDF5ObjectResource;
import org.eclipse.dawnsci.hdf5.HDF5PropertiesResource;
import org.eclipse.dawnsci.hdf5.HDF5Resource;
import org.eclipse.dawnsci.hdf5.HDF5Token;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.hdf5.HDF5Utils.DatasetType;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.dawnsci.nexus.NexusUtils.AugmentedPathElement;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.StringDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;
import hdf.hdf5lib.exceptions.HDF5LibraryException;
import hdf.hdf5lib.structs.H5G_info_t;
import hdf.hdf5lib.structs.H5L_info_t;
import hdf.hdf5lib.structs.H5O_info_t;

public class NexusFileHDF5 implements NexusFile {

	private static final Logger logger = LoggerFactory.getLogger(NexusFileHDF5.class);

	private HDF5File file = null;
	private long fileId = -1;

	private String fileName;

	private String fileDir;

	private TreeFile tree;

	private Map<HDF5Token, Node> nodeMap; //used to remember node locations in file for detecting hard links

	private boolean writeable = false;

	private IdentityHashMap<Node, String> passedNodeMap; // associate given nodes with "canonical" path (used for working out hardlinks)

	private boolean useSWMR = false;
	private boolean writeAsync;

	private boolean swmrOn = false;

	private static final int DEF_FIXED_STRING_LENGTH = 1024;

	/**
	 * Create a new Nexus file (overwriting any existing one)
	 * @param path
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFileHDF5 createNexusFile(String path) throws NexusException {
		NexusFileHDF5 file = new NexusFileHDF5(path, false);
		file.createAndOpenToWrite();
		return file;
	}

	/**
	 * Create a new Nexus file (overwriting any existing one)
	 * @param path
	 * @param enableSWMR
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFileHDF5 createNexusFile(String path, boolean enableSWMR) throws NexusException {
		NexusFileHDF5 file = new NexusFileHDF5(path, enableSWMR);
		file.createAndOpenToWrite();
		return file;
	}

	/**
	 * Open an existing Nexus file to modify
	 * @param path
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFileHDF5 openNexusFile(String path) throws NexusException {
		NexusFileHDF5 file = new NexusFileHDF5(path);
		file.openToWrite(false);
		return file;
	}

	/**
	 * Open an existing Nexus file to read only
	 * @param path
	 * @return Nexus file
	 * @throws NexusException
	 */
	public static NexusFileHDF5 openNexusFileReadOnly(String path) throws NexusException {
		NexusFileHDF5 file = new NexusFileHDF5(path);
		file.openToRead();
		return file;
	}

	//TODO: Clean up and move stuff to helper classes?

	public enum NodeType {
		GROUP(HDF5Constants.H5O_TYPE_GROUP),
		DATASET(HDF5Constants.H5O_TYPE_DATASET);
		public final int value;
		private static Map<Integer, NodeType> map = new HashMap<>();

		static {
			for (NodeType nt : NodeType.values()) {
				map.put(nt.value, nt);
			}
		}

		NodeType(int value) {
			this.value = value;
		}

		public static NodeType valueOf(int type) {
			return map.get(type);
		}
	}

	private static final class NodeData {
		public final String name;
		public final String path;
		public final Node node;
		public final NodeType type;
		NodeData(String name, String path, Node node, NodeType type) {
			this.name = name;
			this.path = path;
			this.node = node;
			this.type = type;
		}
	}

	public NexusFileHDF5(String path) {
		this(path, false);
	}

	public NexusFileHDF5(String path, boolean enableSWMR) {
		fileName = path;
		fileDir = new File(fileName).getParentFile().getAbsolutePath();
		useSWMR  = enableSWMR;
	}

	private void initializeTree() {
		if (tree == null) {
			tree = TreeFactory.createTreeFile(fileName.hashCode(), fileName);
			tree.setGroupNode(NexusNodeFactory.createNXroot(0l));
			nodeMap = new HashMap<>();
			passedNodeMap = new IdentityHashMap<>();
		} else {
			throw new IllegalStateException("File (" + fileName + ") is already open");
		}
	}

	private void assertOpen() {
		if (tree == null) {
			throw new IllegalStateException("File has been closed");
		}
	}

	private void assertCanWrite() {
		assertOpen();
		if (!writeable) {
			throw new IllegalStateException("Cannot write as opened as read-only");
		}
	}

	@Override
	public String getFilePath() {
		return fileName;
	}


	@Override
	public void openToRead() throws NexusException {
		try {
			file = HDF5FileFactory.acquireFile(fileName, false, useSWMR);
			fileId = file.getID();
		} catch (ScanFileHolderException e) {
			throw new NexusException("Cannot open '" + fileName + "' to read", e);
		}
		initializeTree();
	}

	@Override
	public void openToWrite(boolean createIfNecessary) throws NexusException {
		if (new File(fileName).exists()) {
			try {
				file = HDF5FileFactory.acquireFile(fileName, true, useSWMR);
				fileId = file.getID();
			} catch (ScanFileHolderException e) {
				throw new NexusException("Cannot open '" + fileName + "' to write", e);
			}
		} else if (createIfNecessary) {
			try {
				file = HDF5FileFactory.acquireFile(fileName, true, useSWMR);
				fileId = file.getID();
			} catch (ScanFileHolderException e) {
				throw new NexusException("Cannot create '" + fileName + "' to write", e);
			}
		} else {
			throw new NexusException("File '" + fileName + "' not found and not created");
		}
		initializeTree();
		writeable = true;
	}

	@Override
	public void createAndOpenToWrite() throws NexusException {
		try {
			file = HDF5FileFactory.acquireFileAsNew(fileName, useSWMR);
			fileId = file.getID();
		} catch (ScanFileHolderException e) {
			throw new NexusException("Cannot create '" + fileName + "' to write", e);
		}
		initializeTree();
		writeable = true;
	}

	@Override
	public void setWritesAsync(boolean async) {
		writeAsync = async;
	}

	@Override
	public void setDebug(boolean debug) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPath(Node node) {
		return TreeUtils.getPath(tree, node);
	}

	@Override
	public String getRoot() {
		return TreeUtils.getPath(tree, tree.getGroupNode());
	}

	@Override
	public GroupNode getGroup(String augmentedPath, boolean createPathIfNecessary) throws NexusException {
		assertOpen();
		String plainPath = NexusUtils.stripAugmentedPath(augmentedPath);
		NodeLink link = tree.findNodeLink(plainPath);
		if (link != null) {
			if (link.isDestinationGroup()) {
				GroupNode g = (GroupNode) link.getDestination();
				if (!g.isPopulated() && !NO_LINK.equals(getLinkToken(plainPath))) {
					//"leaf" group nodes are never populated and paths that traverse napimounts cannot be populated
					//via the simple path mechanism
					if (!plainPath.endsWith(Node.SEPARATOR)) {
						plainPath += Node.SEPARATOR;
					}
					populateGroupNode(plainPath, g);
				}
				return g;
			}
			throw new NexusException("Path specified (" + plainPath + ") is not for a group");
		}
		NodeData node = getGroupNode(augmentedPath, createPathIfNecessary);
		return (GroupNode) (node.name == null ? null : (node.node));
	}

	@Override
	public Node getNode(String augmentedPath) throws NexusException {
		String plainPath = NexusUtils.stripAugmentedPath(augmentedPath);
		if (!isPathValid(plainPath)) {
			return null;
		}
		NodeLink link = tree.findNodeLink(plainPath);
		if (link != null) {
			if (link.isDestinationData()) {
				return getData(augmentedPath);
			} else if (link.isDestinationGroup()) {
				return getGroup(augmentedPath, true);
			}
		} else {
			// test if root
			NodeData node = getNode(plainPath, false);
			NodeType type = node.type;
			if (type == NodeType.DATASET) {
				int i = plainPath.lastIndexOf(Node.SEPARATOR);
				String parentPath = plainPath.substring(0, i);
				String name = plainPath.substring(i + 1, plainPath.length());
				GroupNode parentNode = getGroup(parentPath, true);
				return getData(parentNode, name);
			} else if (type == NodeType.GROUP) {
				return getGroup(plainPath, true);
			}
		}
		throw new NexusException("Path specified does not exist");
	}

	@Override
	public GroupNode getGroup(GroupNode group,
			String name,
			String nxClass,
			boolean createPathIfNecessary) throws NexusException {
		String path = getPath(group);
		if (name == null) {
			throw new NullPointerException("Name must not be null");
		}
		return getGroup(NexusUtils.addToAugmentPath(path, name, nxClass), createPathIfNecessary);
	}

	private void createGroupNode(long oid, GroupNode group, String path, String name, String nxClass)
			throws NexusException {
		GroupNode g;
		HDF5Token fileAddr = getLinkToken(TreeUtils.join(path, name));
		if (!nodeMap.containsKey(fileAddr)) {
			// create the new group, a subclass of NXobject if nxClass is set. Note nxClass is not yet known when loading
			if (nxClass == null || nxClass.equals("")) {
				g = TreeFactory.createGroupNode(oid);
			} else {
				try {
					g = NexusNodeFactory.createNXobjectForClass(nxClass, oid);
				} catch (IllegalArgumentException e) {
					logger.warn("Attribute {} was {} but not a known one", NexusConstants.NXCLASS, nxClass);
					g = TreeFactory.createGroupNode(oid);
				}
			}
			if (nxClass != null && !nxClass.isEmpty()) {
				g.addAttribute(TreeFactory.createAttribute(NexusConstants.NXCLASS, nxClass));
			}
			cacheAttributes(TreeUtils.join(path, name), g);
			// if the new attributes now includes an nxClass attribute, create
			// the appropriate subclass of NXobject (TODO is there a better way of doing this?)
			if (!(g instanceof NXobject) && g.getAttribute(NexusConstants.NXCLASS) != null) {
				nxClass = g.getAttribute(NexusConstants.NXCLASS).getFirstElement();
				if (nxClass != null && !nxClass.isEmpty()) {
					try {
						g = NexusNodeFactory.createNXobjectForClass(nxClass, oid);
						cacheAttributes(TreeUtils.join(path, name), g);
					} catch (IllegalArgumentException e) {
						logger.warn("Attribute {} was {} but not a known one", NexusConstants.NXCLASS, nxClass);
					}
				}
			}

			if (!IS_EXTERNAL_LINK.equals(fileAddr) && !NO_LINK.equals(fileAddr) && !testForExternalLink(path)) {
				//if our node is an external link we cannot cache its file location
				//we cannot handle hard links in external files
				nodeMap.put(fileAddr, g);
			}
		} else {
			g = (GroupNode)nodeMap.get(fileAddr);
		}
		group.addGroupNode(name, g);
	}

	private void cacheAttributes(String path, Node node) throws NexusException {
		try {
			H5O_info_t objInfo = H5.H5Oget_info_by_name(fileId, path, HDF5Constants.H5O_INFO_NUM_ATTRS, HDF5Constants.H5P_DEFAULT);
			long numAttrs = objInfo.num_attrs;
			for (long i = 0; i < numAttrs; i++) {

				Dataset dataset = HDF5Utils.getAttrDataset(fileId, path, i);
				if (dataset == null || node.containsAttribute(dataset.getName())) {
					//we don't need to read an attribute we already have
					continue;
				}
				node.addAttribute(createAttribute(dataset));
			}
		} catch (HDF5Exception e) {
			throw new NexusException("Could not retrieve node attributes: " + path, e);
		}
	}

	private void populateGroupNode(String path, GroupNode group) throws NexusException {
		cacheAttributes(path, group);
		try {
			H5G_info_t groupInfo = H5.H5Gget_info_by_name(fileId, path, HDF5Constants.H5P_DEFAULT);
			for (long i = 0; i < groupInfo.nlinks; i++) {
				//we have to open the object itself to handle external links
				//H5.H5Lget_name_by_idx(fileId, "X", ....) will fail if X is an external link node, as will similar methods
				try (HDF5Resource objResource = new HDF5ObjectResource(H5.H5Oopen(fileId, path, HDF5Constants.H5P_DEFAULT) )) {
					long objId = objResource.getResource();
					String linkName = H5.H5Lget_name_by_idx(objId, ".", HDF5Constants.H5_INDEX_NAME,
							HDF5Constants.H5_ITER_INC, i, HDF5Constants.H5P_DEFAULT);
					H5L_info_t linkInfo = H5.H5Lget_info_by_idx(objId, ".", HDF5Constants.H5_INDEX_NAME,
							HDF5Constants.H5_ITER_INC, i, HDF5Constants.H5P_DEFAULT);
					if (linkInfo.type == HDF5Constants.H5L_TYPE_EXTERNAL) {
						String[] linkTarget = getExternalLinkTarget(objId, linkName);
						String extFilePath = linkTarget[1];
						if (!new File(extFilePath).exists()) {
							// link may be relative
							extFilePath = TreeUtils.join(fileDir, extFilePath);
							if (!new File(extFilePath).exists()) {
								//TODO: cache "lazy" node
								//this results on a potentially invalid cache
								continue;
							}
						}
						// test we can open the file
						try {
							try (HDF5Resource extFile = new HDF5FileResource(
									H5.H5Fopen(extFilePath, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT))) {
								// do nothing - just let it close
							}
						} catch (HDF5LibraryException e) {
							// someone else has opened the file
							// TODO: same caveat as the "file does not exist" case above
							logger.warn("Cannot open external file {}", extFilePath, e);
							continue;
						}
					}
					String childPath = TreeUtils.join(path, linkName);
					H5O_info_t objectInfo;
					try {
						objectInfo = H5.H5Oget_info_by_name(fileId, childPath, HDF5Constants.H5O_INFO_BASIC, HDF5Constants.H5P_DEFAULT);
					} catch (HDF5LibraryException e) {
						logger.debug("Ignoring error from link for file {}, childPath {}", path, childPath);
						continue;
					}
					if (objectInfo.type == HDF5Constants.H5O_TYPE_GROUP) {
						createGroupNode(childPath.hashCode(), group, path, linkName, "");
					} else if (objectInfo.type == HDF5Constants.H5O_TYPE_DATASET) {
						group.addDataNode(linkName, getDataNodeFromFile(childPath, group, linkName));
					} else {
						throw new NexusException("Unhandled object type (" + objectInfo.type + ")");
					}
				}
			}
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not process over child links: " + path, e);
		}
	}

	private static String determineExternalFilePath(String filePathFragment, String currentFile) throws NexusException {
		//try filepath as relative location first, then absolute
		String currentLocation = currentFile.substring(0, currentFile.lastIndexOf("/") + 1);
		if (new File(currentLocation + filePathFragment).exists()) {
			return currentLocation + filePathFragment;
		} else if (new File(filePathFragment).exists()) {
			return filePathFragment;
		}
		throw new NexusException("Could not find specified file: " + filePathFragment);
	}

	private boolean isNapiMount(Node node) {
		return node.containsAttribute("napimount");
	}

	private NodeData getNodeAfterNapiMount(String fileName,
			String externalMountPoint,
			String internalMountPoint,
			String pathAfterMount,
			GroupNode parentGroup) throws NexusException {

		//We open a *new* NexusFile, have it process its own cache, then just add the bit
		//we're interested in into our own cache
		Node childNode;
		try (NexusFileHDF5 extFile = new NexusFileHDF5(fileName)) {
			String fullPathInExtFile = TreeUtils.join(externalMountPoint, pathAfterMount);
			extFile.openToRead();
			AugmentedPathElement[] parsed = NexusUtils.parseAugmentedPath(internalMountPoint);
			String nodeName = parsed[parsed.length - 1].name;
			NodeType nType = extFile.getNodeType(externalMountPoint);
			if (nType == null) {
				logger.warn("Node type was null");
			} else {
				switch (nType) {
				case DATASET:
					throw new NexusException("Invalid napimount - must not point to dataset");
				case GROUP:
					//we have to add the mounted node and process the new tree
					childNode = extFile.getGroup(externalMountPoint, false);
					String internalPath = internalMountPoint + pathAfterMount;

					GroupNode newNode = TreeFactory.createGroupNode(internalPath.hashCode());
					//we want a copy of the node in the other file since we do not want to
					//adding attributes in the other file's cache
					//this is a bit of a hack
					GroupNode originalNode = (GroupNode) childNode;
					cacheAttributes(internalMountPoint, newNode);
					Iterator<? extends Attribute> attributes = originalNode.getAttributeIterator();
					Iterator<String> names = originalNode.getNodeNameIterator();
					while (attributes.hasNext()) {
						Attribute attr = attributes.next();
						newNode.addAttribute(attr);
					}
					while (names.hasNext()) {
						String name = names.next();
						NodeLink link = originalNode.getNodeLink(name);
						newNode.addNode(name, link.getDestination());
					}
					parentGroup.addGroupNode(nodeName, newNode);
					return extFile.getNode(fullPathInExtFile, false);
				default:
					logger.warn("Node type not known");
				}
			}
		}
		return null;
	}

	private NodeData getGroupNode(String augmentedPath, boolean createPathIfNecessary) throws NexusException {
		NodeData node = getNode(augmentedPath, createPathIfNecessary);
		if (node.type != NodeType.GROUP) {
			throw new NexusException("Found dataset node instead of group node");
		}
		return node;
	}

	/**
	 * Try to retrieve or create the target node (and parents), building cache as necessary.
	 * Cannot create data nodes
	 */
	private NodeData getNode(String augmentedPath, boolean createPathIfNecessary) throws NexusException {
		AugmentedPathElement[] parsedNodes = NexusUtils.parseAugmentedPath(augmentedPath);
		StringBuilder pathBuilder = new StringBuilder(Tree.ROOT);
		String parentPath = pathBuilder.toString();
		GroupNode group = tree.getGroupNode();
		if (parsedNodes.length == 0) {
			return new NodeData(Tree.ROOT, null, group, NodeType.GROUP);
		}
		Node node = group;
		AugmentedPathElement parsedNode = null;
		NodeType type = null;
		//traverse to target node
		for (int i = 0; i < parsedNodes.length; i++) {
			parsedNode = parsedNodes[i];
			if (parsedNode.name.isEmpty()) {
				continue;
			}
			parentPath = pathBuilder.toString();
			pathBuilder.append(parsedNode.name);
			pathBuilder.append(Node.SEPARATOR);
			String path = pathBuilder.toString();
			type = getNodeType(path);
			if (type == null || type == NodeType.GROUP) {
				//make sure the group actually exists
				long nodeId = openGroup(type, path, parsedNode.nxClass, createPathIfNecessary);
				closeNode(nodeId);
				type = NodeType.GROUP;
			} else if (type == NodeType.DATASET) {
				//should be last iteration of loop (final path segment)
				if (i < parsedNodes.length - 1) {
					throw new NexusException("Dataset node should only be final path segment");
				}
				//dataset node *must* exist on disk because we are not creating
				//so just establish in cache and return that object
				if (group.containsDataNode(parsedNode.name)) {
					node = group.getDataNode(parsedNode.name);
				} else {
					node = getData(path);
					group.addDataNode(parsedNode.name, (DataNode) node);
				}
				break;
			} else {
				throw new NexusException("Unhandled node type: " + type);
			}
			//establish group in our cache
			try {
				H5L_info_t linkInfo = H5.H5Lget_info(fileId, path, HDF5Constants.H5P_DEFAULT);
				if (linkInfo.type == HDF5Constants.H5L_TYPE_SOFT) {
					String[] name = new String[2];
					H5.H5Lget_value(fileId, path, name, HDF5Constants.H5P_DEFAULT);
					path = name[0];
					if (!group.containsGroupNode(parsedNode.name)) {
						NodeData linkedNode = getGroupNode(path, false);
						group.addGroupNode(parsedNode.name, (GroupNode)linkedNode.node);
					}
				}
			} catch (HDF5LibraryException e) {
				throw new NexusException("Could not query if path is soft link: " + path, e);
			}
			if (!group.containsGroupNode(parsedNode.name)) {
				createGroupNode(path.hashCode(), group, parentPath, parsedNode.name, parsedNode.nxClass);
			}
			GroupNode parentGroup = group;
			group = group.getGroupNode(parsedNode.name);
			if (!group.isPopulated()) {
				//make sure cached node looks like node on files
				populateGroupNode(path, group);
			}
			if (isNapiMount(group)) {
				parentGroup.removeGroupNode(group);
				String mountString = group.getAttribute("napimount").getFirstElement();
				mountString = mountString.replace("nxfile://", "");
				String[] parts = mountString.split("#");
				String extFileName = determineExternalFilePath(parts[0], this.fileName);
				String extMountPoint = Node.SEPARATOR + parts[1];
				StringBuilder pathAfterMount = new StringBuilder();
				for (int j = i + 1; j < parsedNodes.length; j++) {
					pathAfterMount.append(Node.SEPARATOR);
					pathAfterMount.append(parsedNodes[j].name);
				}
				return getNodeAfterNapiMount(extFileName, extMountPoint, path, pathAfterMount.toString(), parentGroup);
			}
			node = group;
		}
		return new NodeData(parsedNode.name, parentPath, node, type);
	}

	private NodeType getNodeType(String absolutePath) throws NexusException {
		//TODO: inspect cache first (checking for napimounts, as they seriously mess things up)
		try {
			if (!testLinkExists(absolutePath)) {
				return null;
			}
			H5O_info_t info = H5.H5Oget_info_by_name(fileId, absolutePath, HDF5Constants.H5O_INFO_BASIC, HDF5Constants.H5P_DEFAULT);
			NodeType type = NodeType.valueOf(info.type);
			if (type == null) {
				throw new NexusException("Unsupported object type: " + info.type);
			}
			return type;
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not get object information for " + absolutePath, e);
		}
	}

	private long openNode(String absolutePath) throws NexusException {
		if (!absolutePath.startsWith(Tree.ROOT)) {
			throw new IllegalArgumentException("Group path (" + absolutePath + ") must be absolute");
		}
		try {
			return H5.H5Oopen(fileId, absolutePath, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Cannot open object: " + absolutePath, e);
		}
	}

	private long openGroup(NodeType type, String absolutePath, String nxClass, boolean create) throws NexusException {
		long groupId;

		if (type == null) {
			if (create && writeable) {
				if (swmrOn) {
					throw new NexusException("Can not create groups in SWMR mode");
				}
				try {
					groupId = H5.H5Gcreate(fileId, absolutePath,
							HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
				} catch (HDF5LibraryException e) {
					throw new NexusException("Cannot create group: " + absolutePath, e);
				}
				if (nxClass != null && !nxClass.isBlank()) {
					addAttribute(absolutePath, TreeFactory.createAttribute(NexusConstants.NXCLASS, nxClass));
				}
			} else {
				throw new NexusException("Group '" + absolutePath + "' does not exist and cannot be created");
			}
		} else if (type != NodeType.GROUP) {
			throw new NexusException("Specified path (" + absolutePath + ") is not a group");
		} else {
			groupId = openNode(absolutePath);
			if (create && writeable && nxClass != null && !nxClass.isBlank()) {
				addAttribute(absolutePath, TreeFactory.createAttribute(NexusConstants.NXCLASS, nxClass));
			}
		}
		return groupId;
	}

	private long openDataset(String absolutePath) throws NexusException {
		NodeType type = getNodeType(absolutePath);
		if (type != NodeType.DATASET) {
			throw new NexusException("Path (" + absolutePath + ") does not refer to dataset");
		}
		return openNode(absolutePath);
	}

	private void closeNode(long nodeId) throws NexusException {
		try {
			H5.H5Oclose(nodeId);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Cannot close node", e);
		}
	}

	private DataNode createDataNode(GroupNode parentNode, String path, String name,
			long[] shape, Class<? extends Dataset> clazz, boolean unsigned, long[] maxShape, long[] chunks) throws NexusException {
		int[] iShape = shape == null ? null : HDF5Utils.toIntArray(shape);
		int[] iMaxShape = maxShape == null ? null : HDF5Utils.toIntArray(maxShape);
		int[] iChunks = chunks == null ? null : HDF5Utils.toIntArray(chunks);
		DataNode dataNode = TreeFactory.createDataNode(path.hashCode());
		cacheAttributes(path, dataNode);
		boolean isBoolean = HDF5Utils.isDataNodeBoolean(dataNode);
		if (isBoolean) {
			clazz = BooleanDataset.class;
		}

		parentNode.addDataNode(name, dataNode);
		dataNode.setUnsigned(unsigned);
		ILazyDataset lazyDataset = null;
		int itemSize = 1;
		boolean extendUnsigned = false;
		Object[] fill = NexusUtils.getFillValue(InterfaceUtils.getElementClass(clazz));
		if (writeable) {
			HDF5LazySaver saver = new HDF5LazySaver(null, fileName, path, name, iShape, itemSize,
					clazz, extendUnsigned, iMaxShape, iChunks, fill);
			lazyDataset = new LazyWriteableDataset(saver, name, itemSize, clazz, iShape, iMaxShape, iChunks);
			saver.setAlreadyCreated();
			if (writeAsync) {
				saver.setAsyncWriteableDataset((LazyWriteableDataset) lazyDataset);
			}
			((ILazyWriteableDataset) lazyDataset).setWritingAsync(writeAsync);
		} else {
			lazyDataset = new LazyDataset(new HDF5LazyLoader(null, fileName, path, name, iShape,
					itemSize, clazz, extendUnsigned), name, clazz, iShape);
		}
		dataNode.setChunkShape(chunks);
		dataNode.setMaxShape(maxShape);
		dataNode.setDataset(lazyDataset);
		if (!testForExternalLink(path)) {
			nodeMap.put(getLinkToken(path), dataNode);
		}
		return dataNode;
	}

	private DataNode getDataNodeFromFile(String path, GroupNode parentNode, String dataName) throws NexusException {
		if (!testForExternalLink(path)) {
			DataNode node = (DataNode) nodeMap.get(getLinkToken(path));
			if (node != null) {
				return node;
			}
		}
		long[] shape = null;
		long[] maxShape = null;
		long[] chunks = null;
		DatasetType type;
		try (HDF5Resource hdfDataset = new HDF5DatasetResource(openDataset(path));
				HDF5Resource hdfDataspace = new HDF5DataspaceResource( H5.H5Dget_space(hdfDataset.getResource()) );
				HDF5Resource hdfDatatype = new HDF5DatatypeResource( H5.H5Dget_type(hdfDataset.getResource()) );
				HDF5Resource hdfNativetype = new HDF5DatatypeResource( H5.H5Tget_native_type(hdfDatatype.getResource()))) {
			final long datasetId = hdfDataset.getResource();
			final long dataspaceId = hdfDataspace.getResource();
			type = HDF5Utils.getDatasetType(hdfDatatype.getResource(), hdfNativetype.getResource());
			if (type == null) {
				throw new NexusException("Unknown data type");
			}
			int nDims = H5.H5Sget_simple_extent_ndims(dataspaceId);
			try (HDF5Resource hdfProperties = new HDF5PropertiesResource(H5.H5Dget_create_plist(datasetId))) {
				long propertiesId = hdfProperties.getResource();
				if (H5.H5Pget_layout(propertiesId) == HDF5Constants.H5D_CHUNKED) {
					chunks = new long[nDims];
					H5.H5Pget_chunk(propertiesId, nDims, chunks);
				} else {
					//H5D_COMPACT || H5D_CONTIGUOUS can have chunk array set to the shape
					//or can leave null and dataset stuff assumes the same
				}
			}
			shape = new long[nDims];
			maxShape = new long[nDims];
			if (nDims > 0) {
				H5.H5Sget_simple_extent_dims(dataspaceId, shape, maxShape);
			}
		} catch (HDF5Exception e) {
			throw new NexusException("Error reading dataspace: " + path, e);
		}
		return createDataNode(parentNode, path, dataName, shape, type.clazz, type.unsigned, maxShape, chunks);
	}

	@Override
	public DataNode getData(String path) throws NexusException {
		assertOpen();

		path = NexusUtils.stripAugmentedPath(path);
		//check if the data node itself is a symlink
		HDF5Token fileAddr = getLinkToken(path);
		if (!IS_EXTERNAL_LINK.equals(fileAddr) && !NO_LINK.equals(fileAddr)) {
			Node node = nodeMap.get(fileAddr);
			if (node instanceof DataNode dataNode) {
				return dataNode;
			} else if (node != null) {
				throw new IllegalArgumentException("Path specified (" + path + ") is not for a dataset");
			}
		}

		NodeLink link = tree.findNodeLink(path);
		if (link != null) {
			if (link.isDestinationData()) {
				return (DataNode) link.getDestination();
			}
			throw new IllegalArgumentException("Path specified (" + path + ") is not for a dataset");
		}

		String dataName = NexusUtils.getName(path);
		String parentPath = path.substring(0, path.lastIndexOf(dataName));
		NodeData parentNodeData = getGroupNode(parentPath, false);
		if (parentNodeData.name == null) {
			return null;
		}

		NodeType nodeType = getNodeType(path);
		if (nodeType == null || nodeType != NodeType.DATASET) {
			//inablity to find nodetype could indicate the path traverses a napimount
			Node node = getNode(path, false).node;
			if (node instanceof DataNode dataNode) {
				return dataNode;
			}
			throw new NexusException("Path (" + path + ") points to non-dataset object");
		}

		return getDataNodeFromFile(path, (GroupNode)parentNodeData.node, dataName);
	}

	@Override
	public DataNode getData(GroupNode group, String name) throws NexusException {
		String path = NexusUtils.addToAugmentPath(getPath(group), name, null);
		return getData(path);
	}

	private static final int MAX_CHUNK = 1024 * 1024; //default chunk cache is 1MB so we should fit in that.
	private static final int MIN_CHUNK = 8 * 1024;
	private static final int BASE_CHUNK = 16 * 1024;
	private static final int UNLIMITED_DIM_ESTIMATE = 64; //we'd like this to be lower than typical detector sizes


	/**
	 * Estimate chunk size
	 * @param shape shape of the dataset
	 * @param maxshape maxshape of the dataset
	 * @param typeSize
	 * @return chunk size
	 */
	//TODO: lustre might prefer larger chunks (1MB or 4MB)
	//      we might want to reconsider this later as a result
	public static long[] estimateChunking(long[] shape, long[] maxshape, int typeSize) {
		boolean fixedSize = true;
		long[] chunk = new long[shape.length];
		boolean[] fixedDims = new boolean[shape.length];
		for (int i = 0; i < shape.length; i++) {
			fixedDims[i] = maxshape[i] == shape[i];
			fixedSize &= fixedDims[i];
			chunk[i] = maxshape[i] > 0 ? maxshape[i] : UNLIMITED_DIM_ESTIMATE; //we have to have *something* for unlimited dimensions
		}
		long rawDataSize = typeSize;
		for (long c : chunk) {
			rawDataSize *= c;
			assert rawDataSize > 0;
		}
		//heuristic copied from h5py
		//(2 << ... instead of 1 << ... because we want to "round up" the exponent)
		long targetSize = BASE_CHUNK * (2 << (int) Math.log10(rawDataSize / (1024 * 1024)));
		if (targetSize > MAX_CHUNK) {
			targetSize = MAX_CHUNK;
		} else if (targetSize < MIN_CHUNK) {
			targetSize = MIN_CHUNK;
		}

		long chunkByteSize = rawDataSize;
		//make sure we're over the minimum chunk size if possible
		if (!fixedSize) {
			while (chunkByteSize < targetSize) {
				int count = 0;
				for (int i = 0; i < chunk.length; i++) {
					if (!fixedDims[i]) {
						chunk[i] *= 2;
						count++;
					}
				}
				chunkByteSize <<= count;
			}
		}
		int idx = 0;
		//halve each axis in turn until we're under the maximum size, and smaller than the target size or within 50% of it
		while ( !((chunkByteSize < targetSize || (chunkByteSize - targetSize) / (double)targetSize < 0.5) &&
				chunkByteSize < MAX_CHUNK) ) {
			chunk[idx] = (long)Math.ceil(chunk[idx] / 2.0);
			chunkByteSize /= 2;
			long p = 1;
			for (long c : chunk) p *= c;
			if (p == 1) break;
			idx++;
			idx %= chunk.length;
		}
		// chunk dimensions must not exceed positive dimensions in max shape
		for (int i = 0; i < chunk.length; i++) {
			long m = maxshape[i];
			if (m > 0 && m < chunk[i]) {
				chunk[i] = m;
			}
		}
		return chunk;
	}

	@Override
	public DataNode createData(String path, ILazyWriteableDataset data, int compression, boolean createPathIfNecessary) throws NexusException {
		return createData(path, null, data, compression, createPathIfNecessary);
	}

	@Override
	public DataNode createData(String path, String name, ILazyWriteableDataset data, int compression, boolean createPathIfNecessary)
			throws NexusException {
		assertCanWrite();
		if (swmrOn) {
			throw new NexusException("Can not create datasets in SWMR mode");
		}
		NodeData parentNode = getGroupNode(path, createPathIfNecessary);
		if (parentNode.name == null) {
			return null;
		}
		if (name == null) {
			name = data.getName();
		}
		if (name == null || name.isEmpty()) {
			throw new NullPointerException("Dataset name must be defined");
		}
		String dataPath = TreeUtils.join(path, name);
		if (isPathValid(dataPath)) {
			throw new NexusException("Object already exists at specified location: " + dataPath);
		}

		int itemSize = data.getElementsPerItem();
		Class<?> eClass = data.getElementClass();
		boolean isBooleanDataset = Boolean.class.equals(eClass);
		if (isBooleanDataset) {
			eClass = Byte.class;
		}
		Class<? extends Dataset> clazz = InterfaceUtils.getInterfaceFromClass(itemSize, data.getElementClass());
		final boolean isScalar = data.getRank() == 0;
		int[] iShape = data.getShape();
		int[] iMaxShape = data.getMaxShape();
		int[] iChunks = data.getChunking();
		if (iChunks != null) {
			int[] mShape = iMaxShape != null ? iMaxShape : iShape;
			for (int i = 0; i < mShape.length; i++) {
				int m = mShape[i];
				if (m > 0 && m < iChunks[i]) {
					String msg = String.format("Dataset at %s has chunk shape (%s) that exceeds its max shape (%s)", dataPath, Arrays.toString(iChunks), Arrays.toString(mShape));
					throw new IllegalArgumentException(msg);
				}
			}
		}
		Object[] fillValue = NexusUtils.getFillValue(eClass);
		Object providedFillValue = data.getFillValue();
		if (providedFillValue != null && !providedFillValue.getClass().isArray()) {
			fillValue[0] = providedFillValue;
		}
		data.setFillValue(fillValue);

		long[] shape = HDF5Utils.toLongArray(iShape);
		long[] maxShape = HDF5Utils.toLongArray(iMaxShape);
		long[] chunks = HDF5Utils.toLongArray(iChunks);
		boolean stringDataset = data.getElementClass().equals(String.class);
		boolean writeVlenString = stringDataset && !file.canSwitchSWMR(); //SWMR does not allow vlen structures
		long hdfType = getHDF5Type(eClass);
		try {
			try (HDF5Resource hdfDatatype = new HDF5DatatypeResource(H5.H5Tcopy(hdfType));
					HDF5Resource hdfDataspace = new HDF5DataspaceResource(isScalar ? H5.H5Screate(HDF5Constants.H5S_SCALAR)
							: H5.H5Screate_simple(shape.length, shape, maxShape));
					HDF5Resource hdfProperties = new HDF5PropertiesResource(H5.H5Pcreate(HDF5Constants.H5P_DATASET_CREATE))) {

				final long hdfPropertiesId = hdfProperties.getResource();
				final long hdfDatatypeId = hdfDatatype.getResource();
				final long hdfDataspaceId = hdfDataspace.getResource();

				if (stringDataset) {
					H5.H5Tset_cset(hdfDatatypeId, HDF5Constants.H5T_CSET_ASCII);
					H5.H5Tset_size(hdfDatatypeId, writeVlenString ? HDF5Constants.H5T_VARIABLE : DEF_FIXED_STRING_LENGTH);
				} else if (fillValue != null) {
					//Strings must not have a fill value set
					H5.H5Pset_fill_value(hdfPropertiesId, hdfDatatypeId, fillValue);
				}

				boolean recalcChunks = true;
				if (chunks != null) {
					for (long c : chunks) {
						if (c > 1) {
							recalcChunks = false;
							break;
						}
					}
					if (chunks.length > 0 && chunks[chunks.length - 1] == 1) {
						recalcChunks = true;
					}
				}
				if (recalcChunks && !Arrays.equals(shape, maxShape)) {
					logger.debug("Inappropriate chunking {} requested for {}: attempting to estimate suitable chunking.", Arrays.toString(chunks), name);
					chunks = estimateChunking(shape, maxShape, (int) H5.H5Tget_size(hdfDatatypeId));
					iChunks = HDF5Utils.toIntArray(chunks);
					logger.debug("New chunking = {}", Arrays.toString(iChunks));
					data.setChunking(iChunks);
				}
				if (chunks != null && chunks.length > 0) {
					//these have to be set in this order
					H5.H5Pset_layout(hdfPropertiesId, HDF5Constants.H5D_CHUNKED);
					H5.H5Pset_chunk(hdfPropertiesId, chunks.length, chunks);
				}
				int deflateLevel = 0;
				switch (compression) {
				case COMPRESSION_LZW_L1:
					deflateLevel = 1;
					break;
				default:
					compression = COMPRESSION_NONE;
					break;
				}
				if (compression != COMPRESSION_NONE) {
					H5.H5Pset_deflate(hdfPropertiesId, deflateLevel);
				}
				long datasetId = H5.H5Dcreate(fileId, dataPath, hdfDatatypeId, hdfDataspaceId,
						HDF5Constants.H5P_DEFAULT, hdfPropertiesId, HDF5Constants.H5P_DEFAULT);
				H5.H5Dclose(datasetId);
			}
		} catch (HDF5Exception e) {
			throw new NexusException("Could not create dataset: " + name + " in " + path, e);
		}

		HDF5LazySaver saver = new HDF5LazySaver(null, fileName, dataPath,
				name, iShape, itemSize, clazz, false, iMaxShape, iChunks, fillValue);

		saver.setAlreadyCreated();
		if (writeAsync) {
			saver.setAsyncWriteableDataset(data);
		}
		data.setWritingAsync(writeAsync);
		data.setSaver(saver);

		DataNode dataNode = TreeFactory.createDataNode(dataPath.hashCode());

		((GroupNode)parentNode.node).addDataNode(name, dataNode);
		dataNode.setChunkShape(chunks);
		dataNode.setMaxShape(maxShape);
		dataNode.setDataset(data);
		HDF5Token fileAddr = getLinkToken(dataPath);
		nodeMap.put(fileAddr, dataNode);
		if (isBooleanDataset) {
			labelAsBoolean(dataPath);
		}
		return dataNode;
	}

	private void labelAsBoolean(String dataNodePath) throws NexusException {
		addAttribute(dataNodePath, NexusConstants.DLS_DATATYPE_ATTR.BOOLEAN.getAttribute());
	}

	@Override
	public DataNode createData(String path, ILazyWriteableDataset data, boolean createPathIfNecessary) throws NexusException {
		return createData(path, null, data, createPathIfNecessary);
	}

	@Override
	public DataNode createData(String path, String name, ILazyWriteableDataset data, boolean createPathIfNecessary)
			throws NexusException {
		return createData(path, name, data, COMPRESSION_NONE, createPathIfNecessary);
	}

	@Override
	public DataNode createData(GroupNode group, ILazyWriteableDataset data, int compression) throws NexusException {
		return createData(group, null, data, compression);
	}

	@Override
	public DataNode createData(GroupNode group, String name, ILazyWriteableDataset data, int compression) throws NexusException {
		String path = getPath(group);
		return createData(path, name, data, compression, true);
	}

	@Override
	public DataNode createData(GroupNode group, ILazyWriteableDataset data) throws NexusException {
		return createData(group, null, data);
	}

	@Override
	public DataNode createData(GroupNode group, String name, ILazyWriteableDataset data) throws NexusException {
		String path = getPath(group);
		return createData(path, name, data, true);
	}

	@Override
	public DataNode createData(String path, IDataset data, boolean createPathIfNecessary) throws NexusException {
		return createData(path, null, data, createPathIfNecessary);
	}

	@Override
	public DataNode createData(String path, String name, IDataset data, boolean createPathIfNecessary) throws NexusException {
		assertCanWrite();
		if (swmrOn) {
			throw new NexusException("Can not create datasets in SWMR mode");
		}

		NodeData parentNode = getGroupNode(path, createPathIfNecessary);
		if (name == null) {
			name = data.getName();
		}
		if (name == null || name.isEmpty()) {
			throw new NullPointerException("Dataset name must be defined");
		}

		String dataPath = TreeUtils.join(parentNode.path == null ? "" : parentNode.path + parentNode.name, name);
		if (isPathValid(dataPath)) {
			throw new NexusException("Object already exists at specified location: " + dataPath);
		}

		final Class<?> dataClass = data.getElementClass();
		final boolean isStringDataset = dataClass.equals(String.class);
		final boolean isBooleanDataset = dataClass.equals(Boolean.class);
		if (isBooleanDataset) {
			data = DatasetUtils.cast(ByteDataset.class, data);
		}

		final boolean isScalar = data.getRank() == 0;
		final long[] shape = isScalar ? new long[0] : HDF5Utils.toLongArray(data.getShape());

		long type = getHDF5Type(data);

		try {
			try (HDF5Resource hdfDatatype = new HDF5DatatypeResource(H5.H5Tcopy(type));
					HDF5Resource hdfDataspace = new HDF5DataspaceResource(isScalar ?
							H5.H5Screate(HDF5Constants.H5S_SCALAR) :
							H5.H5Screate_simple(shape.length, shape, (long[])null))) {

				final long datatypeId = hdfDatatype.getResource();
				final long dataspaceId = hdfDataspace.getResource();
				if (isStringDataset) {
					H5.H5Tset_cset(datatypeId, HDF5Constants.H5T_CSET_UTF8);
					H5.H5Tset_size(datatypeId, HDF5Constants.H5T_VARIABLE);
				}
				try (HDF5Resource hdfDataset = new HDF5DatasetResource(
						H5.H5Dcreate(fileId, dataPath, datatypeId, dataspaceId,
								HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT));) {

					final long dataId = hdfDataset.getResource();
					if (isStringDataset) {
						String[] strings = (String[])DatasetUtils.serializeDataset(data);
						H5.H5Dwrite_VLStrings(dataId, datatypeId, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, strings);
					} else {
						Serializable buffer = DatasetUtils.serializeDataset(data);
						H5.H5Dwrite(dataId, datatypeId, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, buffer);
					}
				}
			}
		} catch (HDF5Exception e) {
			throw new NexusException("Could not create dataset: " + name + " in " + path, e);
		}
		DataNode dataNode = TreeFactory.createDataNode(dataPath.hashCode());
		HDF5Token fileAddr = getLinkToken(dataPath);
		nodeMap.put(fileAddr, dataNode);
		dataNode.setMaxShape(shape);
		dataNode.setDataset(data);
		((GroupNode) parentNode.node).addDataNode(name, dataNode);

		if (isBooleanDataset) {
			labelAsBoolean(dataPath);
		}

		return dataNode;
	}

	@Override
	public DataNode createData(GroupNode group, IDataset data) throws NexusException {
		return createData(group, null, data);
	}

	@Override
	public DataNode createData(GroupNode group, String name, IDataset data) throws NexusException {
		String path = getPath(group);
		return createData(path, name, data, true);
	}

	@Override
	public void addNode(GroupNode group, String name, Node node) throws NexusException {
		String parentPath = getPath(group);
		addNode(parentPath, name, node);
	}

	@Override
	public void addNode(String path, Node node) throws NexusException {
		if (path == null || "".equals(path)) {
			throw new IllegalArgumentException("Path name must be specified.");
		}
		if ("/".equals(path)) {
			addNode("/", null, node);
		} else {
			String[] components = path.split(Node.SEPARATOR);
			String name = components[components.length - 1];
			String parentPath = path.substring(0, path.lastIndexOf(name));
			addNode(parentPath, name, node);
		}
	}

	@Override
	public void removeNode(GroupNode group, String name) throws NexusException {
		removeNode(getPath(group), group, name);
	}

	@Override
	public void removeNode(String path, String name) throws NexusException {
		removeNode(path, getGroup(path, false), name);
	}

	private void removeNode(String path, GroupNode group, String name) throws NexusException {
		if (group.containsDataNode(name)) {
			group.removeDataNode(name);
		} else if (group.containsGroupNode(name)) {
			group.removeGroupNode(name);
		} else if (group.containsSymbolicNode(name)) {
			group.removeSymbolicNode(name);
		} else {
			throw new IllegalArgumentException("Cannot remove node " + name + ": it is not in group " + path);
		}
		path = TreeUtils.join(path, name);
		HDF5Token fileAddr = getLinkToken(path);
		nodeMap.remove(fileAddr);
		try {
			H5.H5Ldelete(fileId, path, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5LibraryException | NullPointerException e) {
			String msg = String.format("Could not remove node (%s) from file", path);
			logger.error(msg, e);
			throw new NexusException(msg, e);
		}
	}

	private void addNode(String parentPath, String name, Node node) throws NexusException {
		final Map<String, String> linksToCreate = new LinkedHashMap<>();
		recursivelyUpdateTree(parentPath, name, node, linksToCreate);

		// add links for internal SymbolicNodes after all other nodes have been added, so that
		// we're not dependent on the encounter order
		for (Map.Entry<String, String> entry : linksToCreate.entrySet()) {
			createHardLink(entry.getValue(), entry.getKey());
		}
	}

	private void recursivelyUpdateTree(String parentPath, String name, Node node, Map<String, String> linksToCreate) throws NexusException {
		String nxClass = node.containsAttribute(NexusConstants.NXCLASS) ? node.getAttribute(NexusConstants.NXCLASS).getFirstElement() : "";
		String fullPath = TreeUtils.join(parentPath, name == null ? "" : name);
		fullPath = fullPath.replace("//", "/");
		NodeData parentNodeData = getNode(parentPath, false);
		GroupNode parentNode = (GroupNode) parentNodeData.node;
		if (passedNodeMap.containsKey(node)) {
			if (!fullPath.equals(passedNodeMap.get(node))) {
				createHardLink(passedNodeMap.get(node), fullPath);
			}
			return;
		}
		if (node instanceof GroupNode groupNode) {
			if (!parentNode.containsGroupNode(name)) {
				if (nxClass.isEmpty()) {
					logger.warn("Adding node at {} without an NXclass", fullPath);
				}
				long id = openGroup(getNodeType(fullPath), fullPath, nxClass, true);
				closeNode(id);
			}
			GroupNode existingNode = (GroupNode) getNode(fullPath, false).node;
			Iterator<? extends Attribute> it = node.getAttributeIterator();
			while (it.hasNext()) {
				Attribute attr = it.next();
				if (!existingNode.containsAttribute(attr.getName())) {
					IDataset value = attr.getValue().clone();
					value.setName(attr.getName());
					addAttribute(existingNode, createAttribute(value));
				}
			}
			for (String childName : groupNode.getNames()) {
				Node childNode = groupNode.getNodeLink(childName).getDestination();
				recursivelyUpdateTree(fullPath, childName, childNode, linksToCreate);
			}
		} else if (node instanceof DataNode dataNode) {
			if (!parentNode.containsDataNode(name)) {
				ILazyDataset dataset = dataNode.getDataset();
				DataNode newDataNode;
				if (dataset instanceof IDataset idataset) {
					newDataNode = createData(parentNode, name, idataset);
				} else if (dataset instanceof ILazyWriteableDataset lazyWriteableDataset) {
					newDataNode = createData(parentNode, name, lazyWriteableDataset);
					dataNode.setChunkShape(newDataNode.getChunkShape());
				} else {
					throw new NexusException("Unrecognised dataset type: " + dataset.getClass());
				}
				if (dataNode.getMaxShape() == null) {
					dataNode.setMaxShape(newDataNode.getMaxShape());
				}
			}
			DataNode existingNode = getData(parentNode, name);
			Iterator<? extends Attribute> it = node.getAttributeIterator();
			while (it.hasNext()) {
				Attribute attr = it.next();
				if (!existingNode.containsAttribute(attr.getName())) {
					IDataset value = attr.getValue().clone();
					value.setName(attr.getName());
					addAttribute(existingNode, createAttribute(value));
				}
			}
		} else if (node instanceof SymbolicNode linkNode) {
			if (linkNode.getSourceURI() == null) {
				// if no source uri is specified, add to map of hard links to create
				// note: we delay this as the node at the linkPath may not yet exist
				linksToCreate.put(fullPath, linkNode.getPath());
			} else if (linkNode.getSourceURI().getPath() == null || linkNode.getSourceURI().getPath().equals("")) {
				throw new NexusException("Symbolic link node URI does not specify a target file");
			} else if (!parentNode.containsDataNode(name) && !parentNode.containsGroupNode(name)) {
				createExternalLink(linkNode.getSourceURI().getPath(), parentPath, name, linkNode.getPath());
			}
		} else {
			throw new NexusException("Node to update is not a group or data node");
		}
		passedNodeMap.put(node, fullPath);
	}

	@Override
	public Attribute createAttribute(IDataset attr) {
		assertOpen();
		Attribute a = TreeFactory.createAttribute(attr.getName());
		a.setValue(attr);
		return a;
	}

	@Override
	public void addAttribute(String path, Attribute... attribute) throws NexusException {
		assertCanWrite();
		Node node = (attribute != null && attribute.length > 0) ? getNode(path, false).node : null;
		for (Attribute attr : attribute) {
			String attrName = attr.getName();
			if (attrName == null || attrName.isEmpty()) {
				throw new NullPointerException("Attribute must have a name");
			}
			try {
				//if an attribute with the same name already exists, we delete it to be consistent with NAPI
				if (H5.H5Aexists_by_name(fileId, path, attrName, HDF5Constants.H5P_DEFAULT)) {
					try {
						H5.H5Adelete_by_name(fileId, path, attrName, HDF5Constants.H5P_DEFAULT);
					} catch (HDF5LibraryException e) {
						throw new NexusException("Could not delete existing attribute: " + attrName, e);
					}
				}
			} catch (HDF5LibraryException e) {
				throw new NexusException("Error inspecting existing attributes", e);
			}
			Dataset attrData = DatasetUtils.convertToDataset(attr.getValue());
			long baseHdf5Type = getHDF5Type(attrData);
			final boolean isScalar = attrData.getRank() == 0;
			final long[] shape = HDF5Utils.toLongArray(attrData.getShapeRef());
			try {
				try (HDF5Resource typeResource = new HDF5DatatypeResource(H5.H5Tcopy(baseHdf5Type));
						HDF5Resource spaceResource = new HDF5DataspaceResource(isScalar ?
								H5.H5Screate(HDF5Constants.H5S_SCALAR) :
								H5.H5Screate_simple(shape.length, shape, shape))) {

					long datatypeId = typeResource.getResource();
					long dataspaceId = spaceResource.getResource();
					boolean stringDataset = attrData instanceof StringDataset;
					Serializable buffer = DatasetUtils.serializeDataset(attrData);
					if (stringDataset) {
						String[] strings = (String[]) buffer;
						int strCount = strings.length;
						int maxLength = 0;
						byte[][] stringbuffers = new byte[strCount][];
						int i = 0;
						for (String str : strings) {
							stringbuffers[i] = str.getBytes(UTF_8);
							int l = stringbuffers[i].length;
							if (l > maxLength) maxLength = l;
							i++;
						}
						maxLength++; //we require null terminators
						buffer = new byte[maxLength * strCount];
						int offset = 0;
						for (byte[] str: stringbuffers) {
							System.arraycopy(str, 0, buffer, offset, str.length);
							offset += maxLength;
						}

						H5.H5Tset_cset(datatypeId, HDF5Constants.H5T_CSET_ASCII);
						H5.H5Tset_size(datatypeId, maxLength);
					}
					try (HDF5Resource attributeResource = new HDF5AttributeResource(
							H5.H5Acreate_by_name(fileId, path, attrName, datatypeId, dataspaceId,
									HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT))) {
						H5.H5Awrite(attributeResource.getResource(), datatypeId, buffer);
					}
				}
				node.addAttribute(attr);
			} catch (HDF5Exception e) {
				throw new NexusException("Could not create attribute: " + attrName, e);
			}
		}
	}

	@Override
	public void addAttribute(Node node, Attribute... attribute) throws NexusException {
		String path = getPath(node);
		addAttribute(path, attribute);
	}

	@Override
	public String getAttributeValue(String fullAttributeKey) throws NexusException {
		final String[] sa    = fullAttributeKey.split(Node.ATTRIBUTE);
		Node object = null;
		try {
			object = getGroup(sa[0], false);
		} catch (NexusException ne) {
			// if Exception, it might be a datanode
			object = getData(sa[0]);
		}

		Attribute a = object.getAttribute(sa[1]);
		if (a != null) {
			return a.getFirstElement();
		}

		return null;
	}

	private void createSoftLink(String source, String destination) throws NexusException {
		if (swmrOn) {
			throw new NexusException("Can not create links in SWMR mode");
		}
		boolean useNameAtSource = destination.endsWith(Node.SEPARATOR);
		String linkName = destination;
		if (!useNameAtSource) {
			destination = destination.substring(0, destination.lastIndexOf(Node.SEPARATOR));
			if (destination.isEmpty()) destination = Tree.ROOT;
		} else {
			int index = source.lastIndexOf(Node.SEPARATOR);
			linkName += source.substring(index);
		}
		getGroupNode(destination, true);
		try {
			H5.H5Lcreate_soft(source, fileId, linkName, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not create soft link from " + source + " to " + destination, e);
		}
	}

	private void createHardLink(String source, String destination) throws NexusException {
		if (swmrOn) {
			throw new NexusException("Can not create links in SWMR mode");
		}
		boolean useNameAtSource = destination.endsWith(Node.SEPARATOR);
		NodeData sourceData = getNode(source, false);
		if (sourceData.name == null) {
			throw new IllegalArgumentException("Source (" + source + ") does not exist");
		}
		String linkName = destination;
		String nodeName;
		if (useNameAtSource) {
			nodeName = sourceData.name;
			linkName += nodeName;
		} else {
			destination = destination.substring(0, destination.lastIndexOf(Node.SEPARATOR));
			nodeName = source.substring(source.lastIndexOf(Node.SEPARATOR) + 1);
			if (destination.isEmpty()) destination = Tree.ROOT;
		}

		GroupNode destNode = (GroupNode) getGroupNode(destination, true).node;
		switch(sourceData.type) {
		case DATASET:
			destNode.addDataNode(nodeName, (DataNode) sourceData.node);
			break;
		case GROUP:
			destNode.addGroupNode(nodeName, (GroupNode) sourceData.node);
			break;
		}
		try {
			H5.H5Lcreate_hard(fileId, source, fileId, linkName, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not create hard link from " + source + " to " + destination, e);
		}
		//TODO: Maybe remove this and fix the areas where this is required (NexusLoader and some tests?)
		IDataset target = DatasetFactory.createFromObject(source);
		target.setName("target");
		Attribute targetAttr = createAttribute(target);
		if (!sourceData.node.containsAttribute("target")) {
			addAttribute(linkName, targetAttr);
		}
	}

	private void createExternalLink(String externalFileName, String destinationParent, String linkNodeName, String source) throws NexusException {
		if (swmrOn) {
			throw new NexusException("Can not create links in SWMR mode");
		}
		//create the destination node (the path on our side of the link)
		getGroupNode(destinationParent, true);
		String linkName = TreeUtils.join(destinationParent, linkNodeName);
		try (HDF5Resource linkAccess = new HDF5PropertiesResource(H5.H5Pcreate(HDF5Constants.H5P_LINK_ACCESS));
				HDF5Resource fileAccess = new HDF5PropertiesResource(H5.H5Pcreate(HDF5Constants.H5P_FILE_ACCESS))) {
			long lapl = linkAccess.getResource();
			long fapl = fileAccess.getResource();
			H5.H5Pset_libver_bounds(fapl, HDF5Constants.H5F_LIBVER_LATEST, HDF5Constants.H5F_LIBVER_LATEST);
			H5.H5Pset_elink_fapl(lapl, fapl);
			// TODO: Flag should probably be H5F_ACC_RDONLY | H5F_ACC_SWMR_READ but not yet supported
			H5.H5Pset_elink_acc_flags(lapl, HDF5Constants.H5F_ACC_RDONLY);
			H5.H5Lcreate_external(externalFileName, source, fileId, linkName, HDF5Constants.H5P_DEFAULT, lapl);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not create external link " + linkNodeName + " to " + externalFileName, e);
		}
	}

	@Override
	public void link(String source, String destination) throws NexusException {
		assertCanWrite();
		// we cannot hardlink over an external link, so check for an external link on the source path
		String sourcePath = NexusUtils.stripAugmentedPath(source);
		String destinationPath = NexusUtils.stripAugmentedPath(destination);
		String externalLinkPath = findExternalLink(sourcePath);
		if (externalLinkPath == null) {
			createHardLink(sourcePath, destinationPath);
		} else {
			int index = destinationPath.lastIndexOf(Node.SEPARATOR);
			String linkName = destinationPath.substring(index + 1);
			String linkParent = destinationPath.substring(0, index);
			if (linkParent.isEmpty()) linkParent = Tree.ROOT;
			String[] externalLinkTarget = getExternalLinkTarget(externalLinkPath);
			String externalObjectPath = externalLinkTarget[0];
			String externalFileName = externalLinkTarget[1];
			createExternalLink(externalFileName, linkParent, linkName, externalObjectPath);
		}
	}

	@Override
	public void linkExternal(URI source, String destination, boolean isGroup) throws NexusException {
		//creates a soft link *at* destination *to* source
		assertCanWrite();
		destination = NexusUtils.stripAugmentedPath(destination);
		String sourceString = source.toString();
		//the URI is malformed if the specified path was relative, so we have to manually extract the path
		String externalFileName;
		if (sourceString.startsWith("#")) {
			externalFileName = "";
		} else {
			externalFileName = sourceString.replaceFirst("nxfile://", "");
		}
		String externalNexusPath = source.getFragment();
		if (externalFileName.contains("#")) {
			externalFileName = externalFileName.substring(0, externalFileName.indexOf("#"));
		}
		externalNexusPath = NexusUtils.stripAugmentedPath(externalNexusPath);
		if (externalFileName == null || externalFileName.isEmpty()) {
			createSoftLink(externalNexusPath, destination);
			return;
		}
		if (!externalNexusPath.startsWith(Tree.ROOT)) {
			externalNexusPath = Tree.ROOT + externalNexusPath;
		}

		boolean useNameAtSource = destination.endsWith(Node.SEPARATOR);
		String linkName;
		if (!useNameAtSource) {
			int index = destination.lastIndexOf(Node.SEPARATOR);
			linkName = destination.substring(index + 1);
			destination = destination.substring(0, index);
			if (destination.isEmpty()) destination = Tree.ROOT;
		} else {
			int index = externalNexusPath.lastIndexOf(Node.SEPARATOR);
			linkName = externalNexusPath.substring(index);
		}
		createExternalLink(externalFileName, destination, linkName, externalNexusPath);
	}

	@Override
	public void activateSwmrMode() throws NexusException {
		if (!file.canSwitchSWMR()) {
			throw new IllegalStateException("File was not created to use SWMR");
		}

		try {
			H5.H5Fstart_swmr_write(fileId);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not switch to SWMR mode", e);
		}
		swmrOn = true;
		file.setSWMR(swmrOn);
	}

	@Override
	public int flush() throws NexusException {
		if (fileId == -1) {
			return -1;
		}

		try {
			return H5.H5Fflush(fileId, HDF5Constants.H5F_SCOPE_GLOBAL);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Cannot flush file", e);
		}
	}

	public void flushAllCachedDatasets() {
		file.flushDatasets();
	}


	@Override
	public void close() throws NexusException {
		if (fileId == -1) {
			return;
		}

		try {
			try {
				file.flushWrites();
			} catch (Exception e) {
				throw new NexusException("Cannot flush file", e);
			}
			fileId = -1;
			tree = null;
			nodeMap = null;
			passedNodeMap = null;
			writeable = false;
			swmrOn = false;
		} catch (NexusException e) {
			throw new NexusException("Cannot close file", e);
		} finally {
			try {
				HDF5FileFactory.releaseFile(fileName, true);
			} catch (ScanFileHolderException e) {
				throw new NexusException("Cannot release file", e);
			}
		}
	}

	@Override
	public boolean isPathValid(String path) {
		try {
			return H5.H5Lexists(fileId, path, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5LibraryException e) {
			return false;
		}
	}

	private static final HDF5Token ROOT_NODE_ADDR = new HDF5Token(new byte[] {-12, 0, 34});
	private static final HDF5Token IS_EXTERNAL_LINK = new HDF5Token(new byte[] {-43, 0, 70});
	private static final HDF5Token NO_LINK = new HDF5Token(new byte[] {23, 0, -12});


	/**
	 * Walks the provided path until an external link is found,
	 * returning the path of the link (or null if no link is found).
	 * @param path
	 * @return path of the external link (or null)
	 * @throws NexusException
	 */
	private String findExternalLink(String path) throws NexusException {
		if (Tree.ROOT.equals(path)) return null;
		AugmentedPathElement[] parsedNodes = NexusUtils.parseAugmentedPath(path);
		StringBuilder currentPath = new StringBuilder();
		try {
			for (AugmentedPathElement node : parsedNodes) {
				if (node.name.isEmpty()) {
					continue;
				}
				currentPath.append(Node.SEPARATOR);
				currentPath.append(node.name);
				H5L_info_t linkInfo = H5.H5Lget_info(fileId, currentPath.toString(), HDF5Constants.H5P_DEFAULT);
				if (linkInfo.type == HDF5Constants.H5L_TYPE_EXTERNAL) {
					return currentPath.toString();
				}
			}
		} catch (HDF5LibraryException e) {
			throw new NexusException("Error checking for external links: " + path, e);
		}
		return null;
	}

	private boolean testForExternalLink(String path) throws NexusException {
		return findExternalLink(path) != null;
	}


	/**
	 *
	 * Return the external link value referred to by the provided path from the reference object.
	 * The output is the two element String array {objectPath, fileName}
	 * @param objId
	 * @param path
	 * @return String[2] {objectPath, fileName}
	 * @throws NexusException
	 */
	private String[] getExternalLinkTarget(long objId, String path) throws NexusException {
		H5L_info_t linkInfo = H5.H5Lget_info(objId, path, HDF5Constants.H5P_DEFAULT);
		if (linkInfo.type != HDF5Constants.H5L_TYPE_EXTERNAL) {
			throw new NexusException("Path '" + path + "' does not refer to external link");
		}
		String[] value = new String[2];
		H5.H5Lget_value(objId, path, value, HDF5Constants.H5P_DEFAULT);
		return value;
	}

	/**
	 * Return the external link value referred to by the provided path from the reference object.
	 * @param path
	 * @return String[2] {objectPath, fileName}
	 * @throws NexusException
	 */
	public String[] getExternalLinkTarget(String path) throws NexusException {
		return getExternalLinkTarget(fileId, path);
	}

	private HDF5Token getLinkToken(String path) throws NexusException {
		try {
			//The H5 library doesn't consider the "root" node to really be a group but NAPI does,
			//so we emulate the old behaviour by pretending it has a file address
			if (path.equals(Tree.ROOT)) {
				return ROOT_NODE_ADDR;
			}
			if (!testLinkExists(path)) {
				return NO_LINK;
			}
			H5L_info_t linkInfo = H5.H5Lget_info(fileId, path, HDF5Constants.H5P_DEFAULT);
			if (linkInfo.type == HDF5Constants.H5L_TYPE_SOFT) {
				String[] name = new String[2];
				H5.H5Lget_value(fileId, path, name, HDF5Constants.H5P_DEFAULT);
				return getLinkToken(name[0]);
			} else if (linkInfo.type == HDF5Constants.H5L_TYPE_HARD) {
				return new HDF5Token(linkInfo.token);
			} else if (linkInfo.type == HDF5Constants.H5L_TYPE_EXTERNAL) {
				return IS_EXTERNAL_LINK;
			}
			throw new NexusException("Unhandled link type: " + linkInfo.type);
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not get link target from: " + path, e);
		}
	}

	private boolean testLinkExists(String path) throws NexusException {
		AugmentedPathElement[] parsedNodes = NexusUtils.parseAugmentedPath(path);
		StringBuilder currentPath = new StringBuilder(Tree.ROOT);
		try {
			for (AugmentedPathElement parsedNode : parsedNodes) {
				if (parsedNode.name == null || parsedNode.name.isEmpty()) {
					continue;
				}
				currentPath.append(parsedNode.name);
				if (!H5.H5Lexists(fileId, currentPath.toString(), HDF5Constants.H5P_DEFAULT)) {
					return false;
				}
				currentPath.append(Node.SEPARATOR);
			}
			return true;
		} catch (HDF5LibraryException e) {
			throw new NexusException("Could not verify chain (path: " + path + ")", e);
		}
	}

	private static long getHDF5Type(ILazyDataset data) {
		return getHDF5Type(data.getElementClass());
	}

	private static long getHDF5Type(Class<?> clazz) {
		if (clazz.equals(String.class)) {
			return HDF5Constants.H5T_C_S1;
		} else if (clazz.equals(Boolean.class)) {
			// H5T_NATIVE_HBOOL usually maps to UINT8 and we convert boolean[] to byte[]
			return HDF5Constants.H5T_NATIVE_INT8;
		} else if (clazz.equals(Byte.class)) {
			return HDF5Constants.H5T_NATIVE_INT8;
		} else if (clazz.equals(Short.class)) {
			return HDF5Constants.H5T_NATIVE_INT16;
		} else if (clazz.equals(Integer.class)) {
			return HDF5Constants.H5T_NATIVE_INT32;
		} else if (clazz.equals(Long.class)) {
			return HDF5Constants.H5T_NATIVE_INT64;
		} else if (clazz.equals(Float.class)) {
			return HDF5Constants.H5T_NATIVE_FLOAT;
		} else if (clazz.equals(Double.class)) {
			return HDF5Constants.H5T_NATIVE_DOUBLE;
		}
		throw new IllegalArgumentException("Invalid datatype requested: " + clazz.getName());
	}

	public void setCacheDataset(boolean cacheDataset) {
		file.setDatasetIDsCaching(cacheDataset);
	}
}
