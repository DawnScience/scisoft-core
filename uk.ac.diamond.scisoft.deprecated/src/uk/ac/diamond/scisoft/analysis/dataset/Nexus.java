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

package uk.ac.diamond.scisoft.analysis.dataset;

import gda.data.nexus.extractor.NexusExtractor;
import gda.data.nexus.extractor.NexusExtractorException;
import gda.data.nexus.extractor.NexusGroupData;
import gda.data.nexus.tree.INexusSourceProvider;
import gda.data.nexus.tree.INexusTree;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.ByteDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LongDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.ShortDataset;
import gda.data.nexus.NexusException;
import gda.data.nexus.NexusGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper methods for NeXus
 */
public class Nexus {
	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger logger = LoggerFactory.getLogger(Nexus.class);

	/**
	 * Get dataset type from NeXus group data type
	 * @param type
	 * @return dataset type
	 */
	static public int getDType(int type) {
		switch (type) {
		case NexusGlobals.NX_FLOAT64:
			return Dataset.FLOAT64;
		case NexusGlobals.NX_FLOAT32:
			return Dataset.FLOAT32;
		case NexusGlobals.NX_INT64:
		case NexusGlobals.NX_UINT64:
			return Dataset.INT64;
		case NexusGlobals.NX_INT32:
		case NexusGlobals.NX_UINT32:
			return Dataset.INT32;
		case NexusGlobals.NX_INT16:
		case NexusGlobals.NX_UINT16:
			return Dataset.INT16;
		case NexusGlobals.NX_INT8:
		case NexusGlobals.NX_UINT8:
			return Dataset.INT8;
		default:
			throw new IllegalArgumentException("Unknown or unsupported NeXus data type");
		}
	}

	/**
	 * Get NeXus group data type from dataset type
	 * @param dtype
	 * @return NeXus group data type
	 */
	static public int getGroupDataType(int dtype) {
		switch (dtype) {
		case Dataset.FLOAT64:
			return NexusGlobals.NX_FLOAT64;
		case Dataset.FLOAT32:
			return NexusGlobals.NX_FLOAT32;
		case Dataset.INT64:
			return NexusGlobals.NX_INT64;
		case Dataset.INT32:
			return NexusGlobals.NX_INT32;
		case Dataset.INT16:
			return NexusGlobals.NX_INT16;
		case Dataset.INT8:
			return NexusGlobals.NX_INT8;
		default:
			throw new IllegalArgumentException("Unknown or unsupported dataset type");
		}
	}

	/**
	 * Get unsigned integer NeXus group data type from dataset type
	 * @param dtype
	 * @return NeXus group data type
	 */
	static public int getUnsignedGroupDataType(int dtype) {
		switch (dtype) {
		case Dataset.INT64:
			return NexusGlobals.NX_UINT64;
		case Dataset.INT32:
			return NexusGlobals.NX_UINT32;
		case Dataset.INT16:
			return NexusGlobals.NX_UINT16;
		case Dataset.INT8:
			return NexusGlobals.NX_UINT8;
		default:
			throw new IllegalArgumentException("Unknown or unsupported dataset type");
		}
	}
	/**
	 * Create a dataset from NeXus group data
	 * @param groupData
	 * @param keepBitWidth if true, does not promoted unsigned types to wider (signed) Java primitive type 
	 * @return dataset
	 */
	static public Dataset createDataset(NexusGroupData groupData, boolean keepBitWidth) {
		Dataset ds = null;
		switch (groupData.getType()) {
		case NexusGlobals.NX_FLOAT64:
			double[] dData = (double[]) groupData.getBuffer();
			ds = new DoubleDataset(Arrays.copyOf(dData, dData.length), groupData.dimensions);
			break;
		case NexusGlobals.NX_FLOAT32:
			float[] fData = (float[]) groupData.getBuffer();
			ds = new FloatDataset(Arrays.copyOf(fData, fData.length), groupData.dimensions);
			break;
		case NexusGlobals.NX_INT64:
		case NexusGlobals.NX_UINT64:
			long[] lData = (long[]) groupData.getBuffer();
			ds = new LongDataset(Arrays.copyOf(lData, lData.length), groupData.dimensions);
			break;
		case NexusGlobals.NX_INT32:
		case NexusGlobals.NX_UINT32:
			int[] iData = (int[]) groupData.getBuffer();
			ds = new IntegerDataset(Arrays.copyOf(iData, iData.length), groupData.dimensions);
			break;
		case NexusGlobals.NX_INT16:
		case NexusGlobals.NX_UINT16:
			short[] sData = (short[]) groupData.getBuffer();
			ds = new ShortDataset(Arrays.copyOf(sData, sData.length), groupData.dimensions);
			break;
		case NexusGlobals.NX_INT8:
		case NexusGlobals.NX_UINT8:
			byte[] bData = (byte[]) groupData.getBuffer();
			ds = new ByteDataset(Arrays.copyOf(bData, bData.length), groupData.dimensions);
			break;
		default:
			throw new IllegalArgumentException("Unknown or unsupported dataset type");
		}

		if (!keepBitWidth) {
			switch (groupData.getType()) {
			case NexusGlobals.NX_UINT32:
				ds = new LongDataset(ds);
				DatasetUtils.unwrapUnsigned(ds, 32);
				break;
			case NexusGlobals.NX_UINT16:
				ds = new IntegerDataset(ds);
				DatasetUtils.unwrapUnsigned(ds, 16);
				break;
			case NexusGlobals.NX_UINT8:
				ds = new ShortDataset(ds);
				DatasetUtils.unwrapUnsigned(ds, 8);
				break;
			}
		}
		return ds;
	}

	/**
	 * Make a NeXus group data object from a IDataset
	 * @param data
	 * @return group data
	 */
	public static NexusGroupData createNexusGroupData(IDataset data) {
		Dataset ad = DatasetUtils.convertToDataset(data);
		return new NexusGroupData(ad.getShape(), getGroupDataType(ad.getDtype()), ad.getBuffer());
	}

	public static ILazyDataset createLazyDataset(INexusTree node) {
		NexusGroupData groupData = node.getData();
		final URL source;
		final String nodePath;
		INexusTree top = node;
		while (top.getParentNode() != null) {
			top = top.getParentNode();
		}
		if (top instanceof INexusSourceProvider) {
			source = ((INexusSourceProvider) top).getSource();
		} else {
			source = null;
		}

		if (source == null) {
			logger.error("Source of Nexus tree is not defined");
			return null;
		}

		final String name = node.getName();
		nodePath = node.getNodePathWithClasses();

		final int[] trueShape = groupData.dimensions;
		ILazyDataset groupDataset;


		if (groupData.getBuffer() == null) {
			
			ILazyLoader l = new ILazyLoader() {
				@Override
				public boolean isFileReadable() {
					try {
						String host = source.getHost();
						if (host != null && host.length() > 0 && !host.equals(InetAddress.getLocalHost().getHostName()))
							return false;
					} catch (UnknownHostException e) {
						logger.warn("Problem finding local host so ignoring check", e);
					}
					return new File(source.getPath()).canRead();
				}

				@Override
				public String toString() {
					return source.getFile() + ":" + nodePath;
				}

				@Override
				public Dataset getDataset(IMonitor mon, SliceND slice) throws ScanFileHolderException {
					int[] lstart = slice.getStart();
					int[] lstep  = slice.getStep();
					int[] newShape = slice.getShape();
					int rank = newShape.length;

					boolean useSteps = false;
					int[] size;

					for (int i = 0; i < rank; i++) {
						if (lstep[i] != 1) {
							useSteps = true;
							break;
						}
					}

					if (useSteps) { // have to get superset of slice as NeXus API's getslab doesn't allow steps
						size = new int[rank];
						for (int i = 0; i < rank; i++) {
							int last = lstart[i] + (newShape[i]-1)*lstep[i]; // last index
							if (lstep[i] < 0) {
								size[i] = lstart[i] - last + 1;
								lstart[i] = last;
							} else {
								size[i] = last - lstart[i] + 1;
							}
						}
					} else {
						size = newShape;
					}

					Dataset d = null;
					try {
						NexusGroupData ngd = null;
						if (!Arrays.equals(trueShape, slice.getSourceShape())) { // if shape was squeezed then need to translate to true slice
							final int trank = trueShape.length;
							int[] tstart = new int[trank];
							int[] tsize = new int[trank];

							int j = 0;
							for (int i = 0; i < trank; i++) {
								if (trueShape[i] == 1) {
									tstart[i] = 0;
									tsize[i] = 1;
								} else {
									tstart[i] = lstart[j];
									tsize[i] = size[j];
									j++;
								}
							}
							ngd = NexusExtractor.getNexusGroupData(source, nodePath, tstart, tsize, logger.isDebugEnabled());
							d = createDataset(ngd, false);
							d.setShape(size); // squeeze shape back
						} else {
							ngd = NexusExtractor.getNexusGroupData(source, nodePath, lstart, size, logger.isDebugEnabled());
							d = createDataset(ngd, false);
						}
						if (d != null) {
							if (useSteps)
								d = d.getSlice(null, null, lstep); // reduce dataset to requested elements
							d.setName(name);
						}
					} catch (NexusException e) {
						logger.error("Problem with NeXus library: {}", e);
					} catch (NexusExtractorException e) {
						logger.error("Problem with NeXus extraction: {}", e.getMessage());
					}
					return d;
				}
			};

			groupDataset = new LazyDataset(name, getDType(groupData.getType()), trueShape.clone(), l);
		} else {
			Dataset dataset = createDataset(groupData, false);
			dataset.setName(name);
			groupDataset = dataset;
		}

		return groupDataset;
	}
}
