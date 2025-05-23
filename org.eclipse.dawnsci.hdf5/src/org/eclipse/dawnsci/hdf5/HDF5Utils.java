/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusConstants.DLS_DATATYPE_ATTR;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.ComplexDoubleDataset;
import org.eclipse.january.dataset.ComplexFloatDataset;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;
import hdf.hdf5lib.exceptions.HDF5LibraryException;
import hdf.hdf5lib.structs.H5O_info_t;

public class HDF5Utils {
	private static final Logger logger = LoggerFactory.getLogger(HDF5Utils.class);

	private static String host;

	private HDF5Utils() {
		
	}

	/**
	 * Gracefully gets the local host name (if there is a mis-configuration or any other issue, "localhost" is returned) 
	 * @return local host name
	 */
	public static String getLocalHostName() {
		if (host == null) {
			ExecutorService ex = Executors.newSingleThreadExecutor();
			Future<String> fu = ex.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					try { // this can block for a long while
						return InetAddress.getLocalHost().getHostName();
					} catch (UnknownHostException e) {
						logger.error("Could not find host name", e);
					}
					return null;
				}
			});
	
			try { // time-out after 5 seconds
				host = fu.get(5, TimeUnit.SECONDS);
			} catch (TimeoutException e) {
				logger.error("Timed out finding host name", e);
			} catch (InterruptedException e) {
				logger.error("Thread interrupted on finding host name", e);
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				logger.error("Task aborted on finding host name", e);
			}
			if (host == null) {
				host = "localhost";
			}
		}
		return host;
	}

	/**
	 * Check if file is a HDF5 file
	 * @param fileName
	 * @return true if it can be read as an HDF5 file
	 */
	public static boolean isHDF5(final String fileName) {
		if (fileName == null || !new File(fileName).canRead()) {
			return false;
		}
		try {
			return H5.H5Fis_accessible(fileName, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5LibraryException e) {
			logger.error("Problem using HDF5 library when checking if a file is HDF5: {}", fileName, e);
		}
		return false;
	}

	/**
	 * Create a dataset from the given data object
	 * @param data
	 * @param shape
	 * @param clazz
	 * @param extend true dataset for unsigned types 
	 * @return dataset
	 */
	public static Dataset createDataset(final Object data, final int[] shape,final Class<? extends Dataset> clazz,
			final boolean extend) {
		Dataset ds = DatasetFactory.createFromObject(clazz, data);
	
		if (extend) {
			ds = DatasetUtils.makeUnsigned(ds);
		}
		ds.setShape(shape);
		return ds;
	}

	/**
	 * Get HDF5 data type constants for dataset interface
	 * @param dClazz
	 * @return
	 */
	public static long getHDF5type(final Class<? extends Dataset> dClazz) {
		Class<?> clazz = InterfaceUtils.getElementClass(dClazz);
		if (clazz.equals(String.class)) {
			return HDF5Constants.H5T_C_S1;
		} else if (clazz.equals(Boolean.class)) {
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
		throw new IllegalArgumentException("Invalid datatype requested");
	}

	/**
	 * Load dataset from given file
	 * @param fileName
	 * @param node
	 * @param start
	 * @param count
	 * @param step
	 * @param isize can be -1 for item size from file
	 * @param clazz can be null for dataset interface from file
	 * @param extend
	 * @return dataset
	 * @throws Exception
	 */
	public static Dataset loadDatasetWithClose(final String fileName, final String node,
				final int[] start, final int[] count, final int[] step,
				final int isize, final Class<? extends Dataset> clazz, final boolean extend)
				throws ScanFileHolderException {

		return loadDataset(fileName, node, start, count, step, isize, clazz, extend, true);
	}

	/**
	 * Load entire dataset from given file
	 * @param fileName
	 * @param node
	 * @return dataset
	 * @throws Exception
	 */
	public static Dataset loadDataset(final String fileName, final String node)
				throws ScanFileHolderException {

		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, false);

			return loadDataset(fid, node);
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	/**
	 * Load entire dataset from given file
	 * @param fileName
	 * @param node
	 * @return dataset
	 * @throws Exception
	 */
	public static Dataset loadDataset(final HDF5File fid, final String node)
				throws ScanFileHolderException {

		Dataset data = null;
		try {
			int[][] shapes = readDatasetShape(fid, node);
			int[] shape = shapes[0];
			int[] start = new int[shape.length];
			int[] step = new int[shape.length];
			Arrays.fill(step, 1);
			data = readDataset(fid, node, start, shape, step, -1, null, false);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem loading dataset %s in %s", node, fid);
		}

		return data;
	}
	
	/**
	 * Check whether a dataset exists in a file
	 * @param fileName
	 * @param node
	 * @return dataset
	 * @throws Exception
	 */
	public static boolean hasDataset(final String fileName, String node)
				throws ScanFileHolderException {

		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, false);
			
			StringBuilder build = new StringBuilder();
			
			if (node.startsWith(Tree.ROOT)) {
				node = node.substring(1);
				build.append(Tree.ROOT);
			}
			
			String[] split = node.split(Node.SEPARATOR);
			
			for (String s : split) {
				build.append(s);
				if (!H5.H5Lexists(fid.getID(), build.toString(), HDF5Constants.H5P_DEFAULT)) {
					return false;
				}
				build.append(Node.SEPARATOR);
			}
			build.deleteCharAt(build.length()-1);
			
			String full = build.toString();
			
			if (!H5.H5Oexists_by_name(fid.getID(), full, HDF5Constants.H5P_DEFAULT)) {
				return false;
			}
			
			H5O_info_t info = H5.H5Oget_info_by_name(fid.getID(), full,HDF5Constants.H5O_INFO_BASIC, HDF5Constants.H5P_DEFAULT);
			return info.type == HDF5Constants.H5O_TYPE_DATASET;
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	private static void logAndThrowSFHException(Throwable e, String format, Object... args) throws ScanFileHolderException {
		String msg = String.format(format, args);
		if (e == null) {
			logger.error(msg);
			throw new ScanFileHolderException(msg);
		}
		logger.error(msg, e);
		throw new ScanFileHolderException(msg, e);
	}

	private static void logAndThrowNexusException(Throwable e, String format, Object... args) throws NexusException {
		String msg = String.format(format, args);
		if (e == null) {
			logger.error(msg);
			throw new NexusException(msg);
		}
		logger.error(msg, e);
		throw new NexusException(msg, e);
	}

	/**
	 * Check if dataset in data node is flagged as a boolean
	 * @param node
	 * @return
	 */
	public static boolean isDataNodeBoolean(DataNode node) {
		Attribute dlsDatatype = node.getAttribute(NexusConstants.DLS_READ_DATATYPE);
		if (dlsDatatype != null) {
			return NexusConstants.DLS_DATATYPE_ATTR.BOOLEAN.toString().equals(dlsDatatype.getFirstElement());
		}
		return false;
	}

	/**
	 * Load dataset from given file
	 * @param fileName
	 * @param node
	 * @param start
	 * @param count
	 * @param step
	 * @param isize can be -1 for item size from file
	 * @param clazz can be null for dataset interface from file
	 * @param extend
	 * @return dataset
	 * @throws Exception
	 */
	public static Dataset loadDataset(final String fileName, final String node,
				final int[] start, final int[] count, final int[] step,
				final int isize, final Class<? extends Dataset> clazz, final boolean extend)
				throws ScanFileHolderException {

		return loadDataset(fileName, node, start, count, step, isize, clazz, extend, false);
	}

	/**
	 * Load dataset from given file
	 * @param fileName
	 * @param node
	 * @param start
	 * @param count
	 * @param step
	 * @param dtype
	 * @param isize
	 * @param extend
	 * @return dataset
	 * @throws Exception
	 */
	private static Dataset loadDataset(final String fileName, final String node,
				final int[] start, final int[] count, final int[] step,
				final int isize, final Class<? extends Dataset> clazz, final boolean extend, final boolean close)
				throws ScanFileHolderException {

		Dataset data = null;
		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, false);

			data = readDataset(fid, node, start, count, step, isize, clazz, extend);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem loading dataset from file");
		} finally {
			HDF5FileFactory.releaseFile(fileName, close);
		}

		return data;
	}

	/**
	 * Get dataset shape information from given file
	 * @param fileName
	 * @param dataPath
	 * @return null for when there's no data; two empty arrays for a zero-rank dataset;
	 *  shape, max shape otherwise
	 * @throws ScanFileHolderException
	 */
	public static int[][] getDatasetShape(final String fileName, final String node)
			throws ScanFileHolderException {

		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, false);

			return readDatasetShape(fid, node);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem loading dataset shape in file");
			return null;
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	/**
	 * Read shape information from a dataset
	 * @param f
	 * @param dataPath
	 * @return null for when there's no data; two empty arrays for a zero-rank dataset;
	 *  shape, max shape otherwise
	 * @throws NexusException
	 */
	public static int[][] readDatasetShape(HDF5File f, String dataPath) throws NexusException {
		long hdfDatasetId = -1;
		try {
			try {
				hdfDatasetId = H5.H5Dopen(f.getID(), dataPath, HDF5Constants.H5P_DEFAULT);
	
				long hdfDataspaceId = -1;
				try {
					H5.H5Drefresh(hdfDatasetId);
					hdfDataspaceId = H5.H5Dget_space(hdfDatasetId);
					int type = H5.H5Sget_simple_extent_type(hdfDataspaceId);
					if (type == HDF5Constants.H5S_NULL) {
						return null;
					} else if (type == HDF5Constants.H5S_SCALAR) {
						return new int[][] {new int[0], new int[0]};
					}
					int rank = H5.H5Sget_simple_extent_ndims(hdfDataspaceId);
					long[] dims = new long[rank];
					long[] mdims = new long[rank];
					if (rank > 0) {
						H5.H5Sget_simple_extent_dims(hdfDataspaceId, dims, mdims);
					}
					int[] shape = new int[rank];
					int[] mshape = new int[rank];
					for (int i = 0; i < rank; i++) {
						shape[i] = (int) dims[i];
						mshape[i] = (int) mdims[i];
					}
					return new int[][] { shape, mshape}; 
				} finally {
					if (hdfDataspaceId != -1) {
						try {
							H5.H5Sclose(hdfDataspaceId);
						} catch (HDF5Exception ex) {
						}
					}
				}
			} finally {
				if (hdfDatasetId != -1) {
					try {
						H5.H5Dclose(hdfDatasetId);
					} catch (HDF5Exception ex) {
					}
				}
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not read dataset shape for %s in %s", dataPath, f);
		}
		return null;
	}

	/**
	 * Read dataset from given file ID
	 * @param f
	 * @param dataPath
	 * @param start
	 * @param count
	 * @param step
	 * @param isize can be -1 for item size from file
	 * @param clazz can be null for dataset interface from file
	 * @param extend
	 * @return dataset
	 * @throws NexusException
	 */
	public static Dataset readDataset(HDF5File f, final String dataPath, final int[] start, final int[] count,
			final int[] step, final int isize, final Class<? extends Dataset> clazz, final boolean extend) throws NexusException {
		return readDataset(f, dataPath, start, count, step, isize, clazz, extend, false);
	}

	/**
	 * Read dataset from given file ID
	 * @param f
	 * @param dataPath
	 * @param start
	 * @param count
	 * @param step
	 * @param isize can be -1 for item size from file
	 * @param clazz can be null for dataset interface from file
	 * @param extend if true, then widen dataset type to accommodate all values as unsigned if necessary
	 * @param checkUnsigned if true, then check that data is unsigned before extending
	 * @return dataset
	 * @throws NexusException
	 */
	public static Dataset readDataset(HDF5File f, final String dataPath, final int[] start, final int[] count,
			final int[] step, final int isize, final Class<? extends Dataset> clazz, final boolean extend, final boolean checkUnsigned)
					throws NexusException {
		Dataset data = null;

		try {
			H5O_info_t info = H5.H5Oget_info_by_name(f.getID(), dataPath, HDF5Constants.H5O_INFO_BASIC, HDF5Constants.H5P_DEFAULT);
			int t = info.type;
			if (t != HDF5Constants.H5O_TYPE_DATASET) {
				logger.error("Path {} was not a dataset in {}", dataPath, f);
				return data;
			}
		} catch (HDF5Exception ex) {
			logger.error("Could not find info about object {} in {}", dataPath, f);
			return data;
		}

		long did = -1;
		long tid = -1;
		long ntid = -1;
		try {
			did = H5.H5Dopen(f.getID(), dataPath, HDF5Constants.H5P_DEFAULT);
			tid = H5.H5Dget_type(did);
			if (H5.H5Tequal(tid, HDF5Constants.H5T_STD_REF_OBJ)) {
				logAndThrowNexusException(null, "Could not handle reference object data for %s in %s", dataPath, f);
			}

			ntid = H5.H5Tget_native_type(tid);
			DatasetType type = getDatasetType(tid, ntid);
			if (type == null) {
				logAndThrowNexusException(null, "Datatype not supported for %s in %s", dataPath, f);
			}
			long sid = -1;
			long msid = -1;
			int rank;

			boolean unsignExtend = checkUnsigned ? type.unsigned && extend : extend;

			// create a new scalar dataset
			try {
				sid = H5.H5Dget_space(did);
				rank = H5.H5Sget_simple_extent_ndims(sid);

				if (rank == 0) {
					msid = H5.H5Screate(HDF5Constants.H5S_SCALAR);
				} else {
					final long[] sstart = new long[rank]; // source start
					final long[] sstride = new long[rank]; // source steps
					final long[] dsize = new long[rank]; // destination size
	
					for (int i = 0; i < rank; i++) {
						sstart[i] = start[i];
						sstride[i] = step[i];
						dsize[i] = count[i];
					}

					H5.H5Sselect_hyperslab(sid, HDF5Constants.H5S_SELECT_SET, sstart, sstride, dsize, null);
					msid = H5.H5Screate_simple(rank, dsize, null);
					H5.H5Sselect_all(msid);
				}

				boolean convert = clazz != null && !(type.clazz.equals(clazz) && isize == type.isize);

				data = DatasetFactory.zeros(type.isize, type.clazz, count);
				Object odata = data.getBuffer();

				try {
					if (StringDataset.class.isAssignableFrom(type.clazz)) {
						if (type.isVariableLength) {
							H5.H5Dread_VLStrings(did, ntid, msid, sid, HDF5Constants.H5P_DEFAULT, (Object[]) odata);
						} else {
							H5.H5Dread_string(did, ntid, msid, sid, HDF5Constants.H5P_DEFAULT, (String[]) odata);
						}
					} else {
						if (type.isVariableLength) {
							logAndThrowNexusException(null, "Non-string variable length attribute are not supported for %s", dataPath);
						}
						H5.H5Dread(did, ntid, msid, sid, HDF5Constants.H5P_DEFAULT, odata);
					}
					if (convert) {
						data = data.cast(isize, clazz, false);
					}
					if (unsignExtend) {
						data = DatasetUtils.makeUnsigned(data, true);
					}
				} catch (HDF5LibraryException e) {
					logAndThrowNexusException(e, "Could not read data from %s in %s", dataPath, f);
				}
			} catch (HDF5Exception ex) {
				logAndThrowNexusException(ex, "Could not get data space information from %s in %s", dataPath, f);
			} finally {
				if (msid != -1) {
					try {
						H5.H5Sclose(msid);
					} catch (HDF5Exception ex2) {
					}
				}
				if (sid != -1) {
					try {
						H5.H5Sclose(sid);
					} catch (HDF5Exception ex2) {
					}
				}
			}
		} catch (HDF5Exception ex) {
			logAndThrowNexusException(ex, "Could not open dataset %s in %s", dataPath, f);
		} finally {
			if (ntid != -1) {
				try {
					H5.H5Tclose(ntid);
				} catch (HDF5Exception ex) {
				}
			}
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

		return data;
	}

	/**
	 * @return the absolute path to data
	 */
	public static String absolutePathToData(String parentPath, String name) {
		if (parentPath == null || parentPath.isEmpty()) {
			parentPath = Tree.ROOT;
		} else if (!parentPath.startsWith(Tree.ROOT)) {
			parentPath = Tree.ROOT.concat(parentPath);
		}

		if (!parentPath.endsWith(Node.SEPARATOR)) {
			parentPath = parentPath.concat(Node.SEPARATOR);
		}
		return parentPath.concat(name);
	}

	/**
	 * Create a dataset in HDF5 file. Create the file if necessary
	 * @param fileName
	 * @param parentPath path to group containing dataset
	 * @param name name of dataset
	 * @param initialShape
	 * @param maxShape
	 * @param chunking
	 * @param clazz dataset interface
	 * @param fill
	 * @param asUnsigned
	 * @throws ScanFileHolderException
	 */
	public static void createDataset(final String fileName, final String parentPath, final String name, final int[] initialShape, final int[] maxShape, final int[] chunking, final Class<? extends Dataset> clazz, final Object fill, final boolean asUnsigned) throws ScanFileHolderException {
		createDataset(fileName, parentPath, name, initialShape, maxShape, chunking, clazz, fill, asUnsigned, false);
	}

	/**
	 * Create a dataset in HDF5 file. Create the file if necessary
	 * @param fileName
	 * @param parentPath path to group containing dataset
	 * @param name name of dataset
	 * @param initialShape
	 * @param maxShape
	 * @param chunking
	 * @param clazz dataset interface
	 * @param fill
	 * @param asUnsigned
	 * @param close
	 * @throws ScanFileHolderException
	 */
	private static void createDataset(final String fileName, final String parentPath, final String name, final int[] initialShape, final int[] maxShape, final int[] chunking, final Class<? extends Dataset> clazz, final Object fill, final boolean asUnsigned, final boolean close) throws ScanFileHolderException {

		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, true);

			requireDestination(fid, parentPath);
			String dataPath = absolutePathToData(parentPath, name);
			createDataset(fid, NexusFile.COMPRESSION_NONE, dataPath, clazz, initialShape, maxShape, chunking, fill);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem creating dataset in file: %s", fileName);
		} finally {
			HDF5FileFactory.releaseFile(fileName, close);
		}
	}

	/**
	 * Create a lazy dataset in HDF5 file
	 * @param fileName
	 * @param parentPath
	 * @param name
	 * @param initialShape
	 * @param maxShape
	 * @param chunking
	 * @param clazz
	 * @param fill
	 * @param asUnsigned
	 * @return
	 */
	public static LazyWriteableDataset createLazyDataset(final String fileName, final String parentPath, final String name, final int[] initialShape, final int[] maxShape, final int[] chunking, final Class<? extends Dataset> clazz, final Object fill, final boolean asUnsigned) {
		HDF5LazySaver saver = new HDF5LazySaver(null, fileName,
				parentPath + Node.SEPARATOR + name, name, initialShape, 1, clazz, asUnsigned, maxShape, chunking, fill);
		saver.setCreateOnInitialization(true);
		LazyWriteableDataset lazy = new LazyWriteableDataset(saver, name, clazz, initialShape, maxShape, chunking);
		lazy.setFillValue(fill);
		return lazy;
	}

	/**
	 * Create a dataset in HDF5 file. Create the file if necessary
	 * @param fileName
	 * @param parentPath path to group containing dataset
	 * @param name name of dataset
	 * @param initialShape
	 * @param maxShape
	 * @param chunking
	 * @param clazz dataset interface
	 * @param fill
	 * @param asUnsigned
	 * @throws ScanFileHolderException
	 */
	public static void createDatasetWithClose(final String fileName, final String parentPath, final String name, final int[] initialShape, final int[] maxShape, final int[] chunking, final Class<? extends Dataset> clazz, final Object fill, final boolean asUnsigned) throws ScanFileHolderException {
		createDataset(fileName, parentPath, name, initialShape, maxShape, chunking, clazz, fill, asUnsigned, true);
	}

	/**
	 * Check if group exists in file and create group if not
	 * @param file
	 *            id
	 * @param group
	 * @throws HDF5Exception
	 */
	private static void requireDestination(HDF5File fid, String group) throws HDF5Exception {
		boolean exists = false;
		long gid = -1;
		try {
			try {
				gid = H5.H5Gopen(fid.getID(), group, HDF5Constants.H5P_DEFAULT);
				exists = true;
			} catch (Exception e) {
			} finally {
				if (!exists) {
					gid = createDestination(fid.getID(), group);
				}
			}
		} finally {
			if (gid != -1) {
				try {
					H5.H5Gclose(gid);
				} catch (HDF5Exception ex) {
				}
			}
		}
	}

	private static long createDestination(long fileID, String group) throws HDF5Exception {
		long gcpid = -1;
		long gid = -1;

		try {
			gcpid = H5.H5Pcreate(HDF5Constants.H5P_LINK_CREATE);
			H5.H5Pset_create_intermediate_group(gcpid, true);
			gid = H5.H5Gcreate(fileID, group, gcpid, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
		} catch (HDF5Exception e) {
			logger.error("Could not create destination", e);
			throw e;
		} finally {
			if (gcpid != -1) {
				try {
					H5.H5Pclose(gcpid);
				} catch (HDF5Exception ex) {
				}
			}
		}

		return gid;
	}
	/**
	 * Create a dataset in given file
	 * @param f
	 * @param compression
	 * @param dataPath
	 * @param dtype
	 * @param iShape
	 * @param iMaxShape
	 * @param iChunks
	 * @param fillValue
	 * @throws NexusException
	 */
	public static void createDataset(HDF5File f, int compression, String dataPath, Class<? extends Dataset> clazz, int[] iShape, int[] iMaxShape, int[] iChunks,
			Object fillValue) throws NexusException {
		long[] shape = toLongArray(iShape);
		long[] maxShape = toLongArray(iMaxShape);
		long[] chunks = toLongArray(iChunks);
		final boolean isScalar = shape.length == 0;
		if (isScalar) {
			chunks = null;
			compression = 0;
		}
		boolean isStringDataset = StringDataset.class.isAssignableFrom(clazz);
		boolean isBooleanDataset = BooleanDataset.class.isAssignableFrom(clazz);

		if (isBooleanDataset) {
			clazz = ByteDataset.class;
			if (fillValue != null) {
				if (!fillValue.getClass().isArray()) {
					logAndThrowNexusException(null, "Fill value must be an array: %s", fillValue);
				}
				if (Array.get(fillValue, 0) instanceof Boolean bfv) {
					int b = Boolean.TRUE.equals(bfv) ? 1 : 0;
					fillValue = new byte[] { (byte) b};
				}
			}
		}

		long hdfType = getHDF5type(clazz);
		try {
			long hdfDatatypeId = -1;
			long hdfDataspaceId = -1;
			long hdfPropertiesId = -1;
			try {
				hdfDatatypeId = H5.H5Tcopy(hdfType);
				hdfDataspaceId = isScalar ? H5.H5Screate(HDF5Constants.H5S_SCALAR)
						: H5.H5Screate_simple(shape.length, shape, maxShape);
				hdfPropertiesId = H5.H5Pcreate(HDF5Constants.H5P_DATASET_CREATE);

				if (isStringDataset) {
					H5.H5Tset_cset(hdfDatatypeId, HDF5Constants.H5T_CSET_UTF8);
					H5.H5Tset_size(hdfDatatypeId, HDF5Constants.H5T_VARIABLE);
				} else if (fillValue != null) {
					// Strings must not have a fill value set
					H5.H5Pset_fill_value(hdfPropertiesId, hdfDatatypeId, fillValue);
				}
				if (chunks != null) {
					// these have to be set in this order
					H5.H5Pset_layout(hdfPropertiesId, HDF5Constants.H5D_CHUNKED);
					H5.H5Pset_chunk(hdfPropertiesId, chunks.length, chunks);
				}
				int deflateLevel = 0;
				switch (compression) {
				case NexusFile.COMPRESSION_LZW_L1:
					deflateLevel = 1;
					break;
				default:
					compression = NexusFile.COMPRESSION_NONE;
					break;
				}
				if (compression != NexusFile.COMPRESSION_NONE) {
					H5.H5Pset_deflate(hdfPropertiesId, deflateLevel);
				}
				long hdfDatasetId = -1;
				try {
					hdfDatasetId = H5.H5Dcreate(f.getID(), dataPath, hdfDatatypeId, hdfDataspaceId,
						HDF5Constants.H5P_DEFAULT, hdfPropertiesId, HDF5Constants.H5P_DEFAULT);
				} finally {
					if (hdfDatasetId != -1) {
						try {
							H5.H5Dclose(hdfDatasetId);
						} catch (HDF5Exception ex) {
						}
					}
				}
			} finally {
				if (hdfPropertiesId != -1) {
					try {
						H5.H5Pclose(hdfPropertiesId);
					} catch (HDF5Exception ex) {
					}
				}
				if (hdfDataspaceId != -1) {
					try {
						H5.H5Sclose(hdfDataspaceId);
					} catch (HDF5Exception ex) {
					}
				}
				if (hdfDatatypeId != -1) {
					try {
						H5.H5Tclose(hdfDatatypeId);
					} catch (HDF5Exception ex) {
					}
				}
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not create dataset %s in %s", dataPath, f);
		}

		if (isBooleanDataset) {
			addDLSDatatype(f, dataPath, DLS_DATATYPE_ATTR.BOOLEAN);
		}
	}

	private static void addDLSDatatype(HDF5File f, String nodePath, DLS_DATATYPE_ATTR attr) throws NexusException {
		long fileID = f.getID();
		String attrName = attr.getAttribute().getName();

		try {
			// if an attribute with the same name already exists, we delete it to be consistent with NAPI
			if (!H5.H5Aexists_by_name(fileID, nodePath, attrName, HDF5Constants.H5P_DEFAULT)) {
				createAttribute(f, nodePath, attr.getAttribute().getValue(), attrName);
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not check for existing attribute %s for %s in %s", attrName, nodePath, f);
		}
	}

	/**
	 * Write a dataset in HDF5 file. Create the file if necessary
	 * @param fileName
	 * @param parentPath path to group containing dataset
	 * @param data
	 * @throws ScanFileHolderException
	 * @throws IllegalArgumentException when data has null or empty name
	 */
	public static void writeDataset(String fileName, String parentPath, IDataset data) throws ScanFileHolderException {
		HDF5File fid = HDF5FileFactory.acquireFile(fileName, true);

		try {
			requireDestination(fid, parentPath);
			String dataName = data.getName();
			if (dataName == null || dataName.isEmpty()) {
				throw new IllegalArgumentException("Dataset must have a name");
			}
			String dataPath = absolutePathToData(parentPath, data.getName());
			writeDataset(fid, dataPath, data);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem writing dataset to file: %s", fileName);
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	/**
	 * Write a dataset in given file
	 * @param f
	 * @param dataPath
	 * @param data
	 * @throws NexusException
	 */
	public static void writeDataset(HDF5File f, String dataPath, IDataset data) throws NexusException {
		Dataset dataset = DatasetUtils.convertToDataset(data);

		long[] shape = toLongArray(dataset.getShapeRef());
		final boolean isScalar = shape.length == 0;

		Class<? extends IDataset> clazz = data.getClass();
		boolean isStringDataset = StringDataset.class.isAssignableFrom(clazz);
		boolean isBooleanDataset = BooleanDataset.class.isAssignableFrom(clazz);
		if (isBooleanDataset) {
			clazz = ByteDataset.class;
		}


		long hdfType = getHDF5type(dataset.getClass());

		try {
			long hdfDatatypeId = -1;
			long hdfDataspaceId = -1;
			long hdfPropertiesId = -1;

			try {
				hdfDatatypeId = H5.H5Tcopy(hdfType);
				hdfDataspaceId = isScalar ? H5.H5Screate(HDF5Constants.H5S_SCALAR)
						: H5.H5Screate_simple(shape.length, shape, null);
				hdfPropertiesId = H5.H5Pcreate(HDF5Constants.H5P_DATASET_CREATE);

				if (isStringDataset) {
					H5.H5Tset_cset(hdfDatatypeId, HDF5Constants.H5T_CSET_UTF8);
					H5.H5Tset_size(hdfDatatypeId, HDF5Constants.H5T_VARIABLE);
				}
				long hdfDatasetId = -1;
				try {
					hdfDatasetId = H5.H5Dcreate(f.getID(), dataPath, hdfDatatypeId, hdfDataspaceId,
						HDF5Constants.H5P_DEFAULT, hdfPropertiesId, HDF5Constants.H5P_DEFAULT);
					if (isStringDataset) {
						String[] strings = (String[])DatasetUtils.serializeDataset(data);
						H5.H5Dwrite_VLStrings(hdfDatasetId, hdfDatatypeId, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, strings);
					} else {
						Serializable buffer = DatasetUtils.serializeDataset(data);
						H5.H5Dwrite(hdfDatasetId, hdfDatatypeId, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, buffer);
					}
				} finally {
					if (hdfDatasetId != -1) {
						try {
							H5.H5Dclose(hdfDatasetId);
						} catch (HDF5Exception ex) {
						}
					}
				}
			} finally {
				if (hdfPropertiesId != -1) {
					try {
						H5.H5Pclose(hdfPropertiesId);
					} catch (HDF5Exception ex) {
					}
				}
				if (hdfDataspaceId != -1) {
					try {
						H5.H5Sclose(hdfDataspaceId);
					} catch (HDF5Exception ex) {
					}
				}
				if (hdfDatatypeId != -1) {
					try {
						H5.H5Tclose(hdfDatatypeId);
					} catch (HDF5Exception ex) {
					}
				}
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not write dataset %s in %s", dataPath, f);
		}

		if (isBooleanDataset) {
			addDLSDatatype(f, dataPath, DLS_DATATYPE_ATTR.BOOLEAN);
		}
	}

	/**
	 * Write attributes to a group or dataset in given file. When writing to group,
	 * this checks if group exists in file and creates group if not
	 * 
	 * @param fileName
	 * @param path
	 * @param isGroup
	 * @param attributes
	 * @throws ScanFileHolderException
	 */
	public static void writeAttributes(String fileName, String path, boolean isGroup, IDataset... attributes)
			throws ScanFileHolderException {

		HDF5File f = HDF5FileFactory.acquireFile(fileName, true);
		try {
			if (isGroup) {
				requireDestination(f, path);
			}

			writeAttributes(f, path, attributes);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem writing attributes in %s", path);
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	/**
	 * Write attributes to a group or dataset in given file
	 * @param f
	 * @param path
	 * @param attributes
	 * @throws NexusException
	 * @throws IllegalArgumentException when any attribute has null or empty name
	 */
	public static void writeAttributes(HDF5File f, String path, IDataset... attributes) throws NexusException {
		for (IDataset attr : attributes) {
			Dataset attrData = DatasetUtils.convertToDataset(attr);
			String attrName = attr.getName();
			if (attrName == null || attrName.isEmpty()) {
				throw new IllegalArgumentException("Attribute must have a name");
			}

			long fileID = f.getID();
			try {
				// if an attribute with the same name already exists, we delete it to be consistent with NAPI
				if (H5.H5Aexists_by_name(fileID, path, attrName, HDF5Constants.H5P_DEFAULT)) {
					try {
						H5.H5Adelete_by_name(fileID, path, attrName, HDF5Constants.H5P_DEFAULT);
					} catch (HDF5Exception e) {
						logAndThrowNexusException(e, "Could not delete existing attribute %s for %s in %s", attrName, path, f);
					}
				}
			} catch (HDF5Exception e) {
				logAndThrowNexusException(e, "Could not check for existing attribute %s for %s in %s", attrName, path, f);
			}
			createAttribute(f, path, attrData, attrName);
		}
	}

	private static void createAttribute(HDF5File f, String path, IDataset attr, String attrName)
			throws NexusException {
		Dataset attrData = DatasetUtils.convertToDataset(attr);
		long baseHdf5Type = getHDF5type(attrData.getClass());
		long fileID = f.getID();

		final boolean isScalar = attrData.getRank() == 0;
		final long[] shape = toLongArray(attrData.getShapeRef());
		long datatypeID = -1;
		long dataspaceID = -1;
		try {
			datatypeID = H5.H5Tcopy(baseHdf5Type);
			dataspaceID = isScalar ? H5.H5Screate(HDF5Constants.H5S_SCALAR) : H5.H5Screate_simple(shape.length, shape, shape);
			Serializable buffer = DatasetUtils.serializeDataset(attrData);
			if (attrData instanceof StringDataset) {
				String[] strings = (String[]) buffer;
				int strCount = strings.length;
				int maxLength = 0;
				byte[][] stringbuffers = new byte[strCount][];
				int i = 0;
				for (String str : strings) {
					stringbuffers[i] = str.getBytes(StandardCharsets.UTF_8);
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
				//deliberate choice, mis-labelling to work around h5py/numpy
				//handling of non ascii strings
				H5.H5Tset_cset(datatypeID, HDF5Constants.H5T_CSET_UTF8);
				H5.H5Tset_size(datatypeID, maxLength);
			}
			long attrID = -1;
			try {
				attrID = H5.H5Acreate_by_name(fileID, path, attrName, datatypeID, dataspaceID,
							HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);

				H5.H5Awrite(attrID, datatypeID, buffer);
			} catch (HDF5Exception e) {
				logAndThrowNexusException(e, "Could not create or write attribute %s for %s in %s", attrName, path, f);
			} finally {
				if (attrID != -1) {
					try {
						H5.H5Aclose(attrID);
					} catch (HDF5Exception e) {
						// do nothing
					}
				}
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not make data type or space for attribute %s for %s in %s", attrName, path, f);
		} finally {
			if (dataspaceID != -1) {
				try {
					H5.H5Sclose(dataspaceID);
				} catch (HDF5Exception e) {
					// do nothing
				}
			}
			if (datatypeID != -1) {
				try {
					H5.H5Tclose(datatypeID);
				} catch (HDF5Exception e) {
					// do nothing
				}
			}
		}
	}

	/**
	 * Set slice of dataset in HDF5 file. Create file if necessary
	 * @param fileName
	 * @param parentPath
	 * @param name
	 * @param slice
	 * @param value
	 * @throws ScanFileHolderException
	 */
	public static void setDatasetSliceWithClose(final String fileName, final String parentPath, final String name, final SliceND slice, final IDataset value) throws ScanFileHolderException {
		setDatasetSlice(fileName, parentPath, name, slice, value, true, false);
	}

	/**
	 * Set slice of dataset in HDF5 file. Create file if necessary
	 * @param fileName
	 * @param parentPath
	 * @param name
	 * @param slice
	 * @param value
	 * @throws ScanFileHolderException
	 */
	public static void setDatasetSlice(final String fileName, final String parentPath, final String name, final SliceND slice, final IDataset value) throws ScanFileHolderException {
		setDatasetSlice(fileName, parentPath, name, slice, value, false, false);
	}

	/**
	 * Set slice of dataset in HDF5 file. Create file if necessary
	 * @param fileName
	 * @param parentPath
	 * @param name
	 * @param slice
	 * @param value
	 * @throws ScanFileHolderException
	 */
	public static void setExistingDatasetSlice(final String fileName, final String parentPath, final String name, final SliceND slice, final IDataset value) throws ScanFileHolderException {
		setDatasetSlice(fileName, parentPath, name, slice, value, false, true);
	}

	/**
	 * Set slice of dataset in HDF5 file. Create file if necessary
	 * @param fileName
	 * @param parentPath
	 * @param name
	 * @param slice
	 * @param value
	 * @param close
	 * @param exists 
	 * @throws ScanFileHolderException
	 */
	private static void setDatasetSlice(final String fileName, final String parentPath, final String name, final SliceND slice, final IDataset value, final boolean close, boolean exists) throws ScanFileHolderException {
		try {
			if (!exists) {
				prepareFile(fileName, parentPath, name, slice, value);
			}
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, true);

			String dataPath = absolutePathToData(parentPath, name);
			writeDatasetSlice(fid, dataPath, slice, value);
		} catch (Throwable le) {
			logAndThrowSFHException(le, "Problem setting slice of dataset in file: %s", fileName);
		} finally {
			HDF5FileFactory.releaseFile(fileName, close);
		}
	}

	private static void prepareFile(final String fileName, final String parentPath, final String name, final SliceND slice, final IDataset value) throws Exception {
		if (!new File(fileName).exists()) {
			int[] mshape = slice.getMaxShape();
			if (mshape == null) {
				mshape = slice.getShape();
			}
			createDataset(fileName, parentPath, name, slice.getStart(), mshape, slice.getShape(),
					InterfaceUtils.getInterface(value), null, false);
		}
	}

	/**
	 * 
	 * @param f
	 * @param dataPath
	 * @return
	 * @throws NexusException
	 */
	public static long[] openDataset(HDF5File f, String dataPath) throws NexusException {
		long hdfDatasetId = -1;
		long hdfDataspaceId = -1;
		try {
			hdfDatasetId = H5.H5Dopen(f.getID(), dataPath, HDF5Constants.H5P_DEFAULT);

			hdfDataspaceId = H5.H5Dget_space(hdfDatasetId);
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not open dataset %s in %s", dataPath, f);
		}

		return new long[] {hdfDatasetId, hdfDataspaceId};
	}

	/**
	 * 
	 * @param ids
	 * @throws NexusException
	 */
	public static void flushDataset(long[] ids) throws NexusException {
		long id = ids[0];
		if (id != -1) {
			try {
				H5.H5Dflush(id);
			} catch (HDF5Exception ex) {
				logAndThrowNexusException(ex, "Could not flush data");
			}
		}
	}

	/**
	 * 
	 * @param ids
	 * @throws NexusException
	 */
	public static void closeDataset(long[] ids) throws NexusException {
		long id = ids[1];
		if (id != -1) {
			ids[1] = -1;
			try {
				H5.H5Sclose(id);
			} catch (HDF5Exception ex) {
				logger.error("Could not close file space", ex);
			}
		}
		try {
			flushDataset(ids);
		} finally {
			id = ids[0];
			if (id != -1) {
				ids[0] = -1;
				try {
					H5.H5Dclose(id);
				} catch (HDF5Exception ex) {
					logAndThrowNexusException(ex, "Could not close data");
				}
			}
		}
	}

	/**
	 * Write to a slice in a dataset
	 * @param f
	 * @param dataPath
	 * @param slice
	 * @param value
	 * @throws NexusException
	 */
	public static void writeDatasetSlice(HDF5File f, String dataPath, SliceND slice, IDataset value) throws NexusException {
		long[] ids = null;
		try {
			ids = f.openDataset(dataPath);
			long hdfDatasetId = ids[0];
			long hdfDataspaceId = ids[1];

			long hdfMemspaceId = -1;
			long hdfDatatypeId = -1;
			boolean isBooleanDataset = false;
			try {
				int rank = H5.H5Sget_simple_extent_ndims(hdfDataspaceId);
				boolean isScalar = rank == 0;
				if (isScalar) {
					if (slice != null && slice.getShape().length != 0) {
						String msg = "SliceND must have zero-rank when target dataset is a scalar";
						logger.error(msg);
						throw new IllegalArgumentException(msg);
					}
				} else {

					long[] dims = new long[rank];
					long[] mdims = new long[rank];
					if (rank > 0) {
						H5.H5Sget_simple_extent_dims(hdfDataspaceId, dims, mdims);
					}
					long[] start = toLongArray(slice.getStart());
					long[] stride = toLongArray(slice.getStep());
					long[] shape = toLongArray(slice.getShape());
	
					long[] newShape = null;
					if (slice.isExpanded()) {
						newShape = toLongArray(slice.getSourceShape());
					} else {
						long[] mShape = toLongArray(slice.getStop());
						if (expandToGreatestShape(mShape, dims)) {
							newShape = mShape;
						}
					}
					if (newShape != null) {
						H5.H5Dset_extent(hdfDatasetId, newShape);
						try {
							H5.H5Sclose(hdfDataspaceId);
						} catch (HDF5Exception ex) {
						}
						hdfDataspaceId = H5.H5Screate_simple(rank, newShape, mdims);
						ids[1] = hdfDataspaceId;
					}

					H5.H5Sselect_hyperslab(hdfDataspaceId, HDF5Constants.H5S_SELECT_SET, start, stride, shape, null);
				}

				Dataset data = DatasetUtils.convertToDataset(value);
				isBooleanDataset = BooleanDataset.class.isAssignableFrom(data.getClass());
				if (isBooleanDataset) {
					data = DatasetUtils.cast(ByteDataset.class, data);
				}

				long memtype = getHDF5type(data.getClass());
				Serializable buffer = DatasetUtils.serializeDataset(data);

				hdfMemspaceId = isScalar ? H5.H5Screate(HDF5Constants.H5S_SCALAR) : H5.H5Screate_simple(rank, HDF5Utils.toLongArray(data.getShape()), null);
				if (data instanceof StringDataset) {
					//use the properties from the existing dataset for a string,
					//not the memtype of the dataset.
					boolean vlenString = false;
					hdfDatatypeId = H5.H5Dget_type(hdfDatasetId);
					int typeSize = -1;
					typeSize = (int) H5.H5Tget_size(hdfDatatypeId);
					vlenString = H5.H5Tis_variable_str(hdfDatatypeId);

					if (vlenString) {
						H5.H5Dwrite_VLStrings(hdfDatasetId, hdfDatatypeId, hdfMemspaceId, hdfDataspaceId, HDF5Constants.H5P_DEFAULT, (String[]) buffer);
					} else {
						String[] strings = (String[]) buffer;
						byte[] strBuffer = new byte[typeSize * strings.length];
						int idx = 0;
						for (String str : (String[]) strings) {
							//typesize - 1 since we always want to leave room for \0 at the end of the string
							if (str.length() > typeSize - 1) {
								logger.warn("String does not fit into space allocated in HDF5 file in {} - string will be truncated", dataPath);
							}
							byte[] src = str.getBytes(StandardCharsets.US_ASCII);
							int length = Math.min(typeSize - 1, src.length);
							System.arraycopy(src, 0, strBuffer, idx, length);
							idx += typeSize;
						}
						H5.H5Dwrite(hdfDatasetId, hdfDatatypeId, hdfMemspaceId, hdfDataspaceId, HDF5Constants.H5P_DEFAULT, strBuffer);
					}
				} else {
					H5.H5Dwrite(hdfDatasetId, memtype, hdfMemspaceId, hdfDataspaceId, HDF5Constants.H5P_DEFAULT, buffer);
				}
				if (f.isSWMR()) {
					H5.H5Dflush(hdfDatasetId);
				}
			} finally {
				if (hdfDatatypeId != -1) {
					try {
						H5.H5Tclose(hdfDatatypeId);
					} catch (HDF5Exception ex) {
						logger.error("Could not close datatype", ex);
						throw ex;
					}
				}
				if (hdfMemspaceId != -1) {
					try {
						H5.H5Sclose(hdfMemspaceId);
					} catch (HDF5Exception ex) {
						logger.error("Could not close memory space", ex);
						throw ex;
					}
				}
			}

			if (isBooleanDataset) {
				addDLSDatatype(f, dataPath, DLS_DATATYPE_ATTR.BOOLEAN);
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not write dataset slice (%s) to %s in %s", slice, dataPath, f);
		} finally {
			if (!f.containsDataset(dataPath)) {
				closeDataset(ids);
			}
		}
	}

	private static boolean expandToGreatestShape(long[] a, long[] b) {
		int rank = a.length;
		boolean isExpanded = false;
		for (int i = 0; i < rank; i++) {
			if (a[i] > b[i]) {
				isExpanded = true;
			} else { // ensure shape is maximal
				a[i] = b[i];
			}
		}

		return isExpanded;
	}

	private static final Map<Long, Class<? extends Dataset>> HDF_TYPES_TO_DATASET_TYPES;
	private static final Map<Long, Long> ENUM_SIZE_TO_HDF_TYPES;
	private static final Set<Long> UNSIGNED_HDF_TYPES;

	private static long getTypeRepresentation(long nativeHdfTypeId) throws NexusException {
		try {
			for (long type : HDF_TYPES_TO_DATASET_TYPES.keySet()) {
				if (H5.H5Tequal(nativeHdfTypeId, type)) {
					return type;
				}
			}
		} catch (HDF5LibraryException e) {
			logAndThrowNexusException(e, "Could not compare types");
		}

		return -1;
	}

	static {
		HDF_TYPES_TO_DATASET_TYPES = new HashMap<Long, Class<? extends Dataset>>();
		ENUM_SIZE_TO_HDF_TYPES = new HashMap<Long, Long>();
		UNSIGNED_HDF_TYPES = new HashSet<Long>();

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_INT8, ByteDataset.class);
		ENUM_SIZE_TO_HDF_TYPES.put(1l, HDF5Constants.H5T_NATIVE_INT8);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_INT16, ShortDataset.class);
		ENUM_SIZE_TO_HDF_TYPES.put(2l, HDF5Constants.H5T_NATIVE_INT16);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_INT32, IntegerDataset.class);
		ENUM_SIZE_TO_HDF_TYPES.put(4l, HDF5Constants.H5T_NATIVE_INT32);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_INT64, LongDataset.class);
		ENUM_SIZE_TO_HDF_TYPES.put(8l, HDF5Constants.H5T_NATIVE_INT64);


		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_UINT8, ByteDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_UINT8);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_UINT16, ShortDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_UINT16);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_UINT32, IntegerDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_UINT32);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_UINT64, LongDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_UINT64);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_FLOAT, FloatDataset.class);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_DOUBLE, DoubleDataset.class);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_C_S1, StringDataset.class);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_B8, ByteDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_B8);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_B16, ShortDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_B16);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_B32, IntegerDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_B32);

		HDF_TYPES_TO_DATASET_TYPES.put(HDF5Constants.H5T_NATIVE_B64, LongDataset.class);
		UNSIGNED_HDF_TYPES.add(HDF5Constants.H5T_NATIVE_B64);
	}

	public static class DatasetType {
		public Class<? extends Dataset> clazz = null; // dataset interface
		public int isize = 1; // number of elements per item
		public long size; // size of string in bytes
		public int bits = -1; // max number of bits for bit-fields (-1 for other types)
		public int nEnum; // number of enumerations
		public String name;
		public boolean isVariableLength; // is variable length
		public boolean isComplex = false;
		public boolean unsigned; // is unsigned
	}

	public static Dataset[] readAttributes(long oid) throws NexusException {
		return readAttributes(oid, HERE);
	}

	private static final String HERE = ".";

	/**
	 * Read attributes from group or dataset in given file
	 * 
	 * @param fileName
	 * @param groupPath
	 * @throws ScanFileHolderException
	 */
	public static Dataset[] readAttributes(String fileName, String path)
			throws NexusException, ScanFileHolderException {
		long id = -1;
		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, false);
			id = H5.H5Oopen(fid.getID(), path, HDF5Constants.H5P_DEFAULT);
			return readAttributes(id);
		} finally {
			if (id != -1) {
				try {
					H5.H5Oclose(id);
				} catch (HDF5Exception ex) {
				}
			}
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	public static Dataset[] readAttributes(long oid, String path) throws NexusException {
		H5O_info_t info = null;
		try {
			info = H5.H5Oget_info(oid, HDF5Constants.H5O_INFO_NUM_ATTRS);
		} catch (HDF5LibraryException e) {
			logAndThrowNexusException(e, "Could not get info from object: %s", path);
		}

		int n = (int) info.num_attrs;
		if (n <= 0) {
			return new Dataset[0];
		}

		Dataset[] attrs = new Dataset[n];
		for (int i = 0; i < n; i++) {
			attrs[i] = getAttrDataset(oid, path, i);
		}

		return attrs;
	}

	/**
	 * Create a link to a source node in an external file
	 * @param fileName file name where link will be created
	 * @param destination path of created link
	 * @param externalFileName external file
	 * @param source path of source node
	 * @throws ScanFileHolderException
	 */
	public static void createExternalLink(String fileName, String destination, String externalFileName, String source) throws ScanFileHolderException {
		try {
			HDF5File fid = HDF5FileFactory.acquireFile(fileName, false);
			createExternalLink(fid, destination, externalFileName, source);
		} catch (Throwable t) {
			logAndThrowSFHException(t, "Could not create external link in %s", fileName);
		} finally {
			HDF5FileFactory.releaseFile(fileName);
		}
	}

	/**
	 * Create a link to a source node in an external file
	 * @param f
	 * @param destination path of created link (if ends with {@value Node#SEPARATOR} then use name from source)
	 * @param externalFileName external file
	 * @param source path of source node
	 * @throws ScanFileHolderException
	 */
	public static void createExternalLink(HDF5File f, String destination, String externalFileName, String source) throws NexusException {
		String parent;
		if (destination.endsWith(Node.SEPARATOR)) { // use name from source
			parent = destination.substring(0, destination.length() - 1);
			if (source.endsWith(Node.SEPARATOR)) {
				source = source.substring(0, source.length() - 1);
			}
			int l = source.lastIndexOf(Node.SEPARATOR);
			if (l < 0) {
				// no group left!
			} else {
				destination = destination.concat(source.substring(l));
			}
		} else {
			int l = destination.lastIndexOf(Node.SEPARATOR);
			parent = l <= 0 ? Tree.ROOT : destination.substring(0, l);
		}

		long fapl = -1;
		long lapl = -1;
		try {
			if (!Tree.ROOT.equals(parent)) {
				requireDestination(f, parent);
			}

			fapl = H5.H5Pcreate(HDF5Constants.H5P_FILE_ACCESS);
			lapl = H5.H5Pcreate(HDF5Constants.H5P_LINK_ACCESS);

			H5.H5Pset_libver_bounds(fapl, HDF5Constants.H5F_LIBVER_LATEST, HDF5Constants.H5F_LIBVER_LATEST);
			H5.H5Pset_elink_fapl(lapl, fapl);
			H5.H5Pset_elink_acc_flags(lapl, HDF5Constants.H5F_ACC_RDONLY | HDF5Constants.H5F_ACC_SWMR_READ);
			H5.H5Lcreate_external(externalFileName, source, f.getID(), destination, HDF5Constants.H5P_DEFAULT, lapl);
		} catch (Throwable le) {
			logAndThrowNexusException(le, "Problem creating external link (%s) to %s in file: %s", source, destination, f);
		} finally {
			if (lapl != -1) {
				H5.H5Pclose(lapl);
			}
			if (fapl != -1) {
				H5.H5Pclose(fapl);
			}
		}
	}

	/**
	 * Find classes in composite data type
	 * @param tid
	 * @return
	 * @throws HDF5LibraryException
	 * @throws NexusException
	 */
	public static DatasetType findClassesInComposite(long tid) throws HDF5LibraryException, NexusException {
		List<String> names = new ArrayList<>();
		List<Integer> classes = new ArrayList<>();
		List<Class< ? extends Dataset>> iClass = new ArrayList<>();
		List<Integer> widths = new ArrayList<>();
		List<Boolean> signs = new ArrayList<>();
		flattenCompositeDatasetType(tid, "", names, classes, iClass, widths, signs);
		DatasetType comp = new DatasetType();
		comp.isize = classes.size();
		if (comp.isize > 0) {
			int tclass = classes.get(0);
			for (int i = 1; i < comp.isize; i++) {
				if (tclass != classes.get(i)) {
					logger.error("Could not load inhomogeneous compound datatype");
					return null;
				}
			}
			comp.clazz = iClass.get(0);
			
			//if flatten composite call has not succeeded
			if (comp.clazz == null) {
				return null;
			}
			
			if (comp.isize == 2 && tclass == HDF5Constants.H5T_FLOAT) {
				if (getLastComponent(names.get(0)).toLowerCase().startsWith("r") && getLastComponent(names.get(1)).toLowerCase().startsWith("i")) {
					comp.isComplex = true;
					comp.clazz = FloatDataset.class.isAssignableFrom(comp.clazz) ? ComplexFloatDataset.class: ComplexDoubleDataset.class;
				}
			}
			if (!comp.isComplex) {
				comp.clazz = InterfaceUtils.getInterfaceFromClass(comp.isize, InterfaceUtils.getElementClass(comp.clazz));
			}

			StringBuilder name = new StringBuilder();
			if (comp.isComplex) {
				name.append("Complex = {");
				for (int i = 0; i < comp.isize; i++) {
					name.append(names.get(i));
					name.append(constructType(classes.get(i), widths.get(i), signs.get(i)));
					name.append(", ");
				}
				name.delete(name.length() - 2, name.length());
				name.append("}");
			} else {
				name.append("Array of ");
				name.append(classes.size());
				name.append(" ");
				name.append(constructType(classes.get(0), widths.get(0), signs.get(0)).substring(1));
			}
			comp.name = name.toString();
			Collections.sort(widths);
			comp.bits = widths.get(widths.size() - 1);
		}
		return comp;
	}

	private static final String COLON = ":";

	private static String getLastComponent(String n) {
		String[] bits = n.split(COLON);
		int l = bits.length - 1;
		while (bits[l].trim().length() == 0) {
			l--;
		}
		return bits[l];
	}

	private static String constructType(int c, int w, boolean s) {
		StringBuilder n = new StringBuilder(":");
		if (!s) {
			n.append("U");
		}
		if (c == HDF5Constants.H5T_BITFIELD) {
			n.append("INT");
			n.append(w);
		} else if (c == HDF5Constants.H5T_INTEGER) {
			n.append("INT");
			n.append(-w*8);
		} else if (c == HDF5Constants.H5T_FLOAT) {
			n.append("FLOAT");
			n.append(-w*8);
		}
		return n.toString();
	}

	/**
	 * This flattens compound and array
	 * @param tid
	 * @param prefix
	 * @param names
	 * @param classes
	 * @param iClazzes
	 * @param widths bits (positive) or bytes (negative)
	 * @param signs
	 * @throws HDF5LibraryException
	 * @throws NexusException 
	 */
	private static void flattenCompositeDatasetType(long tid, String prefix, List<String> names, List<Integer> classes, List<Class<? extends Dataset>> iClazzes, List<Integer> widths, List<Boolean> signs) throws HDF5LibraryException, NexusException {
		int tclass = H5.H5Tget_class(tid);
		if (tclass == HDF5Constants.H5T_ARRAY) {
			long btid = -1;
			try {
				btid = H5.H5Tget_super(tid);
				tclass = H5.H5Tget_class(btid);
				// deal with array of composite
				if (tclass == HDF5Constants.H5T_COMPOUND || tclass == HDF5Constants.H5T_ARRAY) {
					flattenCompositeDatasetType(btid, prefix, names, classes, iClazzes, widths, signs);
					return;
				}
				long mtype = H5.H5Tget_native_type(btid);
				int w = 1;
				try {
					w = (int) H5.H5Tget_size(mtype);
				} catch (HDF5Exception ex) {
				}
				boolean s = true;
				if (tclass == HDF5Constants.H5T_INTEGER) {
					try {
						s = H5.H5Tget_sign(mtype) == HDF5Constants.H5T_SGN_2;
					} catch (HDF5Exception ex) {
					}
				}
				int r = H5.H5Tget_array_ndims(tid);
				long[] shape = new long[r];
				H5.H5Tget_array_dims(tid, shape);
				long size = calcLongSize(shape);
				widths.add(-w);
				signs.add(s);
				for (long i = 0; i < size; i++) {
					classes.add(tclass);
				}
				iClazzes.add(HDF_TYPES_TO_DATASET_TYPES.get(getTypeRepresentation(mtype)));
			} catch (HDF5Exception ex) {
				logger.warn("Problem reading array datatype", ex);
			} finally {
				if (btid != -1) {
					try {
						H5.H5Tclose(btid);
					} catch (HDF5LibraryException e) {
					}
				}
			}
		} else { // compound datatype
			int n = H5.H5Tget_nmembers(tid);
			if (n <= 0)
				return;
	
			int mclass = 0;
			long tmptid = -1;
			for (int i = 0; i < n; i++) {
				long mtype = -1;
				try {
					mtype = H5.H5Tget_member_type(tid, i);
	
					try {
						tmptid = mtype;
						mtype = H5.H5Tget_native_type(tmptid);
					} catch (HDF5Exception ex) {
						continue;
					} finally {
						if (tmptid != -1) {
							try {
								H5.H5Tclose(tmptid);
							} catch (HDF5LibraryException e) {
							}
						}
					}
		
					try {
						mclass = H5.H5Tget_class(mtype);
					} catch (HDF5Exception ex) {
						continue;
					}
		
					
					String mname = prefix;
					if (!prefix.isEmpty()) {
						mname += COLON;
					}
					mname += H5.H5Tget_member_name(tid, i);
					if (mclass == HDF5Constants.H5T_COMPOUND || mclass == HDF5Constants.H5T_ARRAY) {
						// deal with composite
						flattenCompositeDatasetType(mtype, mname, names, classes, iClazzes, widths, signs);
					} else if (mclass == HDF5Constants.H5T_VLEN) {
						continue;
					} else {
						names.add(mname);
						classes.add(mclass);
						if (mclass == HDF5Constants.H5T_BITFIELD) {
							int p = -1;
							try {
								p = H5.H5Tget_precision(mtype);
							} catch (HDF5Exception ex) {
								continue;
							} finally {
								widths.add(p);
							}
							signs.add(false);
						} else {
							int w = 1;
							try {
								w = (int) H5.H5Tget_size(mtype);
							} catch (HDF5Exception ex) {
								continue;
							} finally {
								widths.add(-w);
							}
							if (mclass == HDF5Constants.H5T_INTEGER) {
								boolean s = true;
								try {
									s = H5.H5Tget_sign(mtype) == HDF5Constants.H5T_SGN_2;
								} catch (HDF5Exception ex) {
									continue;
								} finally {
									signs.add(s);
								}
							} else {
								signs.add(true);
							}
						}
						iClazzes.add(HDF_TYPES_TO_DATASET_TYPES.get(getTypeRepresentation(mtype)));
					}
				} finally {
					if (mtype != -1) {
						try {
							H5.H5Tclose(mtype);
						} catch (HDF5LibraryException e) {
						}
					}
				}
			}
		}
	}

	public static DatasetType getDatasetType(long typeId, long nativeTypeId) throws NexusException, HDF5LibraryException {
		DatasetType type = new DatasetType();
		int tclass = H5.H5Tget_class(nativeTypeId);

		if (tclass == HDF5Constants.H5T_ARRAY || tclass == HDF5Constants.H5T_COMPOUND) {
			type = findClassesInComposite(typeId);
		} else {
			type.size = H5.H5Tget_size(nativeTypeId);
	
			long typeRepresentation;
			if (tclass == HDF5Constants.H5T_STRING) {
				type.isVariableLength = H5.H5Tis_variable_str(nativeTypeId);
				typeRepresentation = HDF5Constants.H5T_C_S1;
			} else if (tclass == HDF5Constants.H5T_ENUM) {
				// TODO maybe convert to string dataset eventually
				type.nEnum = H5.H5Tget_nmembers(nativeTypeId);
				typeRepresentation = ENUM_SIZE_TO_HDF_TYPES.get(type.size);
			} else if (tclass == HDF5Constants.H5T_REFERENCE) {
				return null;
			} else {
				type.isVariableLength = tclass == HDF5Constants.H5T_VLEN;
				typeRepresentation = getTypeRepresentation(nativeTypeId);
				//if type not found
				if (typeRepresentation == -1) {
					return null;
				}
			}
			type.clazz = HDF_TYPES_TO_DATASET_TYPES.get(typeRepresentation);
			type.unsigned = UNSIGNED_HDF_TYPES.contains(typeRepresentation);
			type.name = DTypeUtils.getDTypeName(DTypeUtils.getDType(type.clazz), type.isize);
			if (type.unsigned) {
				type.name = "U" + type.name;
			}
		}
//		isRegRef = H5.H5Tequal(tid, HDF5Constants.H5T_STD_REF_DSETREG);
		return type;
	}

	public static Dataset getAttrDataset(long locId, String path, long i) throws NexusException {
		Dataset dataset = null;
		String name = null;
		try {
			try (HDF5Resource attrResource = new HDF5AttributeResource(
					H5.H5Aopen_by_idx(locId, path, HDF5Constants.H5_INDEX_NAME, HDF5Constants.H5_ITER_INC, i,
							HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT))) {
				long[] shape = null;
				long[] maxShape = null;
				long attrId = attrResource.getResource();
				name = H5.H5Aget_name(attrId);
				try (HDF5Resource spaceResource = new HDF5DataspaceResource(H5.H5Aget_space(attrId));
						HDF5Resource typeResource = new HDF5DatatypeResource(H5.H5Aget_type(attrId));
						HDF5Resource nativeTypeResource = new HDF5DatatypeResource(H5.H5Tget_native_type(typeResource.getResource()))) {
					final long spaceId = spaceResource.getResource();
					final long nativeTypeId = nativeTypeResource.getResource();
					DatasetType type = getDatasetType(typeResource.getResource(), nativeTypeId);
					if (type == null) {
						logAndThrowNexusException(null, "Unknown data type: %s in %s", name, path);
					}
					final int nDims = H5.H5Sget_simple_extent_ndims(spaceId);
					shape = new long[nDims];
					maxShape = new long[nDims];
					if (nDims > 0) {
						H5.H5Sget_simple_extent_dims(spaceId, shape, maxShape);
					}
					final int[] iShape = toIntArray(shape);
					int strCount = 1;
					for (int d : iShape) {
						strCount *= d;
					}
					if (type.clazz.isAssignableFrom(StringDataset.class)) {
						if (type.isVariableLength) {
							String[] buffer = new String[strCount];
							H5.H5Aread_VLStrings(attrId, nativeTypeId, buffer);
							dataset = DatasetFactory.createFromObject(buffer).reshape(iShape);
						} else {
							byte[] buffer = new byte[(int) (strCount * type.size)];
							H5.H5Aread(attrId, nativeTypeId, buffer);
							String[] strings = new String[strCount];
							int strIndex = 0;
							for (int j = 0; j < buffer.length; j += type.size) {
								int strLength = 0;
								//Java doesn't strip null bytes during string construction
								for (int k = j; k < j + type.size && buffer[k] != '\0'; k++) strLength++;
								strings[strIndex++] = new String(buffer, j, strLength, StandardCharsets.UTF_8);
							}
							dataset = DatasetFactory.createFromObject(strings).reshape(iShape);
						}
					} else {
						if (type.isVariableLength) {
							logAndThrowNexusException(null, "Non-string variable length attribute are not supported: %s for %s", name, path);
						}
						dataset = DatasetFactory.zeros(type.clazz, iShape);
						Serializable buffer = dataset.getBuffer();
						H5.H5Aread(attrId, nativeTypeId, buffer);
					}
					dataset.setName(name);
				}
			}
		} catch (HDF5Exception e) {
			logAndThrowNexusException(e, "Could not retrieve attribute %s for %s", name, path);
		}
		return dataset;
	}

	public static long calcLongSize(final long[] shape) {
		double dsize = 1.0;
		for (int i = 0; i < shape.length; i++) {
			// make sure the indexes isn't zero or negative
			if (shape[i] == 0) {
				return 0;
			} else if (shape[i] < 0) {
				throw new IllegalArgumentException(String.format(
						"The %d-th is %d which is an illegal argument as it is negative", i, shape[i]));
			}
	
			dsize *= shape[i];
		}
	
		// check to see if the size is larger than an integer, i.e. we can't allocate it
		if (dsize > Long.MAX_VALUE) {
			throw new IllegalArgumentException("Size of the dataset is too large to allocate");
		}
		return (long) dsize;
	}

	/**
	 * Convert integer array to long array
	 * @param in
	 * @return long array
	 */
	public static final long[] toLongArray(final int[] in) {
		if (in == null)
			return null;
	
		long[] out = new long[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] = in[i];
		}
		return out;
	}

	/**
	 * Convert long array to integer array
	 * @param in
	 * @return integer array
	 */
	public static int[] toIntArray(long[] in) {
		int[] out = new int[in.length];
		for (int i = 0; i < out.length; i++) {
			long value = in[i];
			if (value == Long.MAX_VALUE) {
				value = -1; // stopgap fix for incorrectly written maximum shape
			}
			if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
				throw new IllegalArgumentException("Cannot convert to int array without data loss");
			}
			out[i] = (int)value;
		}
		return out;
	}
}
