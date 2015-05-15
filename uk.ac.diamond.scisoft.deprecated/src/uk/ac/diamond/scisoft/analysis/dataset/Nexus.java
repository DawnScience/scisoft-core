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
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.hdf5.nexus.NexusException;
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
	 * Create a dataset from NeXus group data
	 * @param groupData
	 * @param keepBitWidth if true, does not promoted unsigned types to wider (signed) Java primitive type 
	 * @return dataset
	 */
	static public Dataset createDataset(NexusGroupData groupData, boolean keepBitWidth) {
		return groupData.toDataset(keepBitWidth);
	}

	/**
	 * Make a NeXus group data object from a IDataset
	 * @param data
	 * @return group data
	 */
	public static NexusGroupData createNexusGroupData(IDataset data) {
		Dataset ad = DatasetUtils.convertToDataset(data);
		return new NexusGroupData(ad.getShape(), ad.getBuffer());
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

			groupDataset = new LazyDataset(name, groupData.getDtype(), trueShape.clone(), l);
		} else {
			Dataset dataset = createDataset(groupData, false);
			dataset.setName(name);
			groupDataset = dataset;
		}

		return groupDataset;
	}
}
