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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.HDFNativeData;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.hdf5lib.exceptions.HDF5LibraryException;
import ncsa.hdf.hdf5lib.structs.H5O_info_t;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.h5.H5Datatype;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDF5Utils {
	protected static final Logger logger = LoggerFactory.getLogger(HDF5Utils.class);

	/**
	 * Create a dataset from the given data object
	 * @param data
	 * @param shape
	 * @param dtype
	 * @param extend true dataset for unsigned types 
	 * @return dataset
	 */
	public static Dataset createDataset(final Object data, final int[] shape, final int dtype,
			final boolean extend) {
		Dataset ds = DatasetFactory.createFromObject(data, dtype);
	
		if (extend) {
			ds = DatasetUtils.makeUnsigned(ds);
		}
		ds.setShape(shape);
		return ds;
	}

	/**
	 * Translate between data type and dataset type
	 * @param dclass data type class
	 * @param dsize data type element size in bytes
	 * @return dataset type
	 */
	public static int getDtype(final int dclass, final int dsize) {
		return getDtype(dclass, dsize, 1, false);
	}

	/**
	 * Translate between data type and dataset type
	 * @param dclass data type class
	 * @param dsize data type element size in bytes
	 * @param isize number of items
	 * @param isComplex
	 * @return dataset type
	 */
	public static int getDtype(final int dclass, final int dsize, final int isize, final boolean isComplex) {
		switch (dclass) {
		case Datatype.CLASS_STRING:
			return Dataset.STRING;
		case Datatype.CLASS_CHAR:
		case Datatype.CLASS_INTEGER:
			switch (dsize) {
			case 1:
				return Dataset.INT8;
			case 2:
				return Dataset.INT16;
			case 4:
				return Dataset.INT32;
			case 8:
				return Dataset.INT64;
			}
			break;
		case Datatype.CLASS_BITFIELD:
			switch (dsize) {
			case 1:
				return isize == 1 ? Dataset.INT8 : Dataset.ARRAYINT8;
			case 2:
				return isize == 1 ? Dataset.INT16 : Dataset.ARRAYINT16;
			case 4:
				return isize == 1 ? Dataset.INT32 : Dataset.ARRAYINT32;
			case 8:
				return isize == 1 ? Dataset.INT64 : Dataset.ARRAYINT64;
			}
			break;
		case Datatype.CLASS_FLOAT:
			if (isComplex) {
				switch (dsize) {
				case 4:
					return Dataset.COMPLEX64;
				case 8:
					return Dataset.COMPLEX128;
				}
			}
			switch (dsize) {
			case 4:
				return Dataset.FLOAT32;
			case 8:
				return Dataset.FLOAT64;
			}
			break;
		}
		return -1;
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
	public static Dataset loadDataset(final String fileName, final String node,
				final int[] start, final int[] count, final int[] step,
				final int dtype, final int isize, final boolean extend)
				throws Exception {
		Dataset data = null;

		final String cPath;
		try {
			cPath = new File(fileName).getCanonicalPath();
		} catch (IOException e) {
			logger.error("Could not get canonical path", e);
			throw new ScanFileHolderException("Could not get canonical path", e);
		}
		long fid = -1;
		try {
			HierarchicalDataFactory.acquireLowLevelReadingAccess(cPath);
			fid = H5Fopen(fileName, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);

			try {
				H5O_info_t info = H5.H5Oget_info_by_name(fid, node, HDF5Constants.H5P_DEFAULT);
				int t = info.type;
				if (t != HDF5Constants.H5O_TYPE_DATASET) {
					logger.error("Node {} was not a dataset", node);
					return data;
				}
			} catch (HDF5Exception ex) {
				logger.error("Could not find info about object {}" + node);
				return data;
			}

			long did = -1;
			long tid = -1;
			int tclass = -1;
			try {
				did = H5.H5Dopen(fid, node, HDF5Constants.H5P_DEFAULT);
				tid = H5.H5Dget_type(did);

				tclass = H5.H5Tget_class(tid);
				if (tclass == HDF5Constants.H5T_ARRAY || tclass == HDF5Constants.H5T_VLEN) {
					// for ARRAY, the type is determined by the base type
					long btid = H5.H5Tget_super(tid);
					tclass = H5.H5Tget_class(btid);
					try {
						H5.H5Tclose(btid);
					} catch (HDF5Exception ex) {
					}
				}
				long sid = -1, pid = -1;
				int rank;
				boolean isText, isVLEN; //, isUnsigned = false;
//				boolean isEnum, isRegRef, isNativeDatatype;
				long[] dims;

				// create a new scalar dataset

				try {
					pid = H5.H5Dget_create_plist(did);

					sid = H5.H5Dget_space(did);

					rank = H5.H5Sget_simple_extent_ndims(sid);

					isText = tclass == HDF5Constants.H5T_STRING;
					isVLEN = tclass == HDF5Constants.H5T_VLEN || H5.H5Tis_variable_str(tid);

					final int ldtype;
					if (dtype >= 0) {
						ldtype = dtype;
					} else {
						Datatype type = new H5Datatype(tid);
						ldtype = getDtype(type.getDatatypeClass(), type.getDatatypeSize());
					}

					if (rank == 0) {
						// a single data point
						rank = 1;
						dims = new long[1];
						dims[0] = 1;
					} else {
						dims = new long[rank];
						H5.H5Sget_simple_extent_dims(sid, dims, null);
					}

					long[] schunk = null; // source chunking

					try {
						if (H5.H5Pget_layout(pid) == HDF5Constants.H5D_CHUNKED) {
							schunk = new long[rank];
							H5.H5Pget_chunk(pid, rank, schunk);
						}
					} catch (HDF5Exception ex) {
						logger.error("Could not get chunk size");
						return data;
					}

					final long[] sstart = new long[rank]; // source start
					final long[] sstride = new long[rank]; // source steps
					final long[] dsize = new long[rank]; // destination size

					for (int i = 0; i < rank; i++) {
						sstart[i] = start[i];
						sstride[i] = step[i];
						dsize[i] = count[i];
					}

					boolean all = false;
					if (schunk == null) {
						all = true;
					} else {
						if (Arrays.equals(dims, schunk)) {
							all = true;
						} else {
							int j = rank - 1; // find last chunked dimension that is sliced across
							while (j >= 0) {
								if (schunk[j] > 1 && dsize[j] <= 1)
									break;
								j--;
							}
							all = j < 0;
						}
					}
					if (schunk == null || all) {
						H5.H5Sselect_hyperslab(sid, HDF5Constants.H5S_SELECT_SET, sstart, sstride, dsize, null);
//						long length = 1;
//						for (int i = 0; i < rank; i++)
//							length *= count[i];
//
//						long msid = H5.H5Screate_simple(1, new long[] {length}, null);
						long msid = H5.H5Screate_simple(rank, dsize, null);
						H5.H5Sselect_all(msid);
						data = DatasetFactory.zeros(isize, count, ldtype);
						Object odata = data.getBuffer();

						boolean isREF = H5.H5Tequal(tid, HDF5Constants.H5T_STD_REF_OBJ);
						if (isVLEN) {
							H5.H5DreadVL(did, tid, msid, sid, HDF5Constants.H5P_DEFAULT, (Object[]) odata);
						} else {
							H5.H5Dread(did, tid, msid, sid, HDF5Constants.H5P_DEFAULT, odata);

							if (odata instanceof byte[] && ldtype != Dataset.INT8) {
								// TODO check if this is actually used
								Object idata = null;
								byte[] bdata = (byte[]) odata;
								if (isText) {
									idata = ncsa.hdf.object.Dataset.byteToString(bdata, H5.H5Tget_size(tid));
								} else if (isREF) {
									idata = HDFNativeData.byteToLong(bdata);
								}

								if (idata != null) {
									data = createDataset(idata, count, ldtype, false); // extend later, if necessary
								}
							}
						}
					} else {
						// read in many split chunks
						final boolean[] isSplit = new boolean[rank];
						final long[] send = new long[rank];
						int length = 1;
						for (int i = 0; i < rank; i++) {
							send[i] = sstart[i] + count[i] * step[i];
							isSplit[i] = schunk[i] <= 1 && dsize[i] > 1;
							if (isSplit[i]) {
								dsize[i] = 1;
							} else {
								length *= dsize[i];
							}
						}
						if (length == 1) { // if just single point then bulk up request
							for (int i = rank - 1; i >= 0; i--) {
								int l = count[i];
								if (l > 1) {
									dsize[i] = l;
									length = l;
									isSplit[i] = false;
									break;
								}
							}
						}
						final List<Integer> notSplit = new ArrayList<Integer>();
						for (int i = 0; i < rank; i++) {
							if (!isSplit[i])
								notSplit.add(i);
						}
						final int[] axes = new int[notSplit.size()];
						for (int i = 0; i < axes.length; i++) {
							axes[i] = notSplit.get(i);
						}
						data = DatasetFactory.zeros(count, ldtype);
						Object odata;
						try {
							odata = H5Datatype.allocateArray(tid, length);
						} catch (OutOfMemoryError err) {
							throw new ScanFileHolderException("Out Of Memory", err);
						}
//						long msid = H5.H5Screate_simple(1, new long[] {length}, null);
						long msid = H5.H5Screate_simple(rank, dsize, null);
						H5.H5Sselect_all(msid);

						PositionIterator it = data.getPositionIterator(axes);
						final int[] pos = it.getPos();
						final boolean[] hit = it.getOmit();
						while (it.hasNext()) {
							H5.H5Sselect_hyperslab(sid, HDF5Constants.H5S_SELECT_SET, sstart, sstride, dsize, null);
							boolean isREF = H5.H5Tequal(tid, HDF5Constants.H5T_STD_REF_OBJ);
							Object idata;
							if (isVLEN) {
								H5.H5DreadVL(did, tid, msid, sid, HDF5Constants.H5P_DEFAULT, (Object[]) odata);
								idata = odata;
							} else {
								H5.H5Dread(did, tid, msid, sid, HDF5Constants.H5P_DEFAULT, odata);

								if (odata instanceof byte[] && ldtype != Dataset.INT8) {
									// TODO check if this is actually used
									byte[] bdata = (byte[]) odata;
									if (isText) {
										idata = ncsa.hdf.object.Dataset.byteToString(bdata, H5.H5Tget_size(tid));
									} else if (isREF) {
										idata = HDFNativeData.byteToLong(bdata);
									} else {
										idata = odata;
									}
								} else {
									idata = odata;
								}
							}

							data.setItemsOnAxes(pos, hit, idata);
							int j = rank - 1;
							for (; j >= 0; j--) {
								if (isSplit[j]) {
									sstart[j] += sstride[j];
									if (sstart[j] >= send[j]) {
										sstart[j] = start[j];
									} else {
										break;
									}
								}
							}
							if (j == -1)
								break;
						}
					}
					if (extend) {
						data = DatasetUtils.makeUnsigned(data);
					}
				} catch (HDF5Exception ex) {
					logger.error("Could not get data space information", ex);
					return data;
				} finally {
					if (sid >= 0) {
						try {
							H5.H5Sclose(sid);
						} catch (HDF5Exception ex2) {
						}
					}
					if (pid >= 0) {
						try {
							H5.H5Pclose(pid);
						} catch (HDF5Exception ex) {
						}
					}
				}

			} catch (HDF5Exception ex) {
				logger.error("Could not open dataset", ex);
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

		} catch (Throwable le) {
			throw new ScanFileHolderException("Problem loading file: " + fileName, le);
		} finally {
			try {
				H5.H5Fclose(fid);
			} catch (Throwable e) {
			}
			HierarchicalDataFactory.releaseLowLevelReadingAccess(cPath);
		}

		return data;
	}

	/**
	 * Create a dataset in HDF5 file
	 * @param fileName
	 * @param path path to group containing dataset
	 * @param name name of dataset
	 * @param initialShape
	 * @param maxShape
	 * @param chunking
	 * @param dtype dataset type
	 * @param fill
	 * @param asUnsigned
	 * @throws ScanFileHolderException
	 */
	public static void createDataset(final String fileName, final String path, final String name, final int[] initialShape, final int[] maxShape, final int[] chunking, final int dtype, final Object fill, final boolean asUnsigned) throws ScanFileHolderException {
		try (HierarchicalDataFile writer = (HierarchicalDataFile) HierarchicalDataFactory.getWriter(fileName, true)) {
			writer.createDataset(name, dtype, toLongArray(initialShape), toLongArray(maxShape), toLongArray(chunking), null, fill, path, true);
		} catch (Exception e) {
			logger.error("Could not create dataset", e);
			throw new ScanFileHolderException("Could not create dataset", e);
		}
	}

	/**
	 * Set slice of dataset in HDF5 file
	 * @param fileName
	 * @param path
	 * @param name
	 * @param slice
	 * @param value
	 * @throws ScanFileHolderException
	 */
	public static void setDatasetSlice(final String fileName, final String path, final String name, final SliceND slice, final IDataset value) throws ScanFileHolderException {
		try (HierarchicalDataFile writer = (HierarchicalDataFile) HierarchicalDataFactory.getWriter(fileName, true)) {
			writer.insertSlice(name, value, path, slice, true);
		} catch (Exception e) {
			logger.error("Could not create dataset", e);
			throw new ScanFileHolderException("Could not create dataset", e);
		}		
	}

	private static final long[] toLongArray(final int[] in) {
		if (in == null)
			return null;

		long[] out = new long[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] = in[i];
		}
		return out;
	}

	/**
	 * FIXME remove once upstream as fixed broken backward compatibility
	 * Wrapper to fix super block status flag issue
	 * @param filePath
	 * @param flags
	 * @param fapl
	 * @return file ID
	 * @throws HDF5LibraryException
	 * @throws NullPointerException
	 */
	public static long H5Fopen(String filePath, int flags, long fapl) throws HDF5LibraryException, NullPointerException {
		long fid = -1;
		try {
			fid = H5.H5Fopen(filePath, flags, fapl);
		} catch (HDF5LibraryException e) {
			boolean isAccessDefault = fapl == HDF5Constants.H5P_DEFAULT;
			if (isAccessDefault) {
				fapl = -1;
				try {
					fapl = H5.H5Pcreate(HDF5Constants.H5P_FILE_ACCESS);
				} catch (HDF5LibraryException ex) {
					logger.error("Could not create file access property list");
					throw ex;
				}
			}
			try {
				H5.H5Pset(fapl, "clear_status_flags", 1);
			} catch (HDF5LibraryException ex) {
				logger.warn("Could not clear status flag but continuing to open file");
			}
	
			fid = H5.H5Fopen(filePath, flags, fapl);
	
			if (isAccessDefault) {
				if (fapl != -1) {
					try {
						H5.H5Pclose(fapl);
					} catch (HDF5LibraryException ex) {
						logger.error("Could not close file access property list");
						throw ex;
					}
				}
			}
		}
		return fid;
	}
}
