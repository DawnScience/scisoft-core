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

package uk.ac.diamond.scisoft.analysis.io;

import gda.data.nexus.extractor.NexusExtractor;
import gda.data.nexus.extractor.NexusExtractorException;
import gda.data.nexus.extractor.NexusGroupData;
import gda.data.nexus.tree.INexusTree;
import gda.data.nexus.tree.NexusTreeBuilder;
import gda.data.nexus.tree.NexusTreeNodeSelection;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.io.SliceObject;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.hdf5.nexus.NexusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.Nexus;

/**
 *
 */
public class NexusLoader extends AbstractFileLoader {
	private static final Logger logger = LoggerFactory.getLogger(NexusLoader.class);

	private String nexusDataSelectionFilename;
	private String nexusMetaDataSelectionFilename;
	private NexusTreeNodeSelection dataSelectionTree; 
	private NexusTreeNodeSelection metaSelectionTree;
	private List<String> dataSetNames;

	private boolean keepBitWidth = false;

	public NexusLoader() {
		
	}
	/**
	 * User function to get all NXentries for named SDS items. Not that no metadata is read
	 * 
	 * @param filename
	 * @param dataSetNames list of scannables and detectors for which data is to be extracted. If null then all
	 */
	public NexusLoader(String filename, List<String> dataSetNames) {
		this.fileName = filename;
		this.nexusDataSelectionFilename = "";
		this.nexusMetaDataSelectionFilename = "";
		this.dataSelectionTree=null;
		this.metaSelectionTree=null;
		this.dataSetNames = dataSetNames;
	}

	/**
	 * User function to get all NXentries
	 * 
	 * @param filename
	 */
	public NexusLoader(String filename) {
		setFile(filename);
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
		this.nexusDataSelectionFilename = "";
		this.nexusMetaDataSelectionFilename = "";
		this.dataSelectionTree=null;
		this.metaSelectionTree=null;
		this.dataSetNames = null;
	}

	/**
	 * User function to get selected entries
	 * 
	 * @param filename
	 * @param nexusDataSelectionFilename
	 *            - filename of xml doc specifying data  to be extracted from nexus file @see
	 *            NexusTreeBuilder.getNexusTree
	 * @param nexusMetaDataSelectionFilename  filename of xml doc specifying metadata  to be extracted from nexus file @see
	 *            NexusTreeBuilder.getNexusTree
	 * @param dataSetNames list of scannables and detectors for which data is to be extracted. If null then all
	 */
	public NexusLoader(String filename, String nexusDataSelectionFilename, 
			String nexusMetaDataSelectionFilename, List<String> dataSetNames) {
		this.fileName = filename;
		this.nexusDataSelectionFilename = nexusDataSelectionFilename;
		this.nexusMetaDataSelectionFilename = nexusMetaDataSelectionFilename;
		this.dataSelectionTree=null;
		this.metaSelectionTree=null;
		this.dataSetNames = dataSetNames;
	}

	/**
	 * User function to get selected entries
	 * 
	 * @param filename
	 * @param dataSelectionTree tree specifying data  to be extracted from nexus file @see
	 *            NexusTreeBuilder.getNexusTree
	 * @param metaSelectionTree tree specifying metadata  to be extracted from nexus file @see
	 *            NexusTreeBuilder.getNexusTree
	 * @param dataSetNames list of scannables and detectors for which data is to be extracted. If null then all
	 */
	public NexusLoader(String filename, NexusTreeNodeSelection dataSelectionTree, 
			NexusTreeNodeSelection metaSelectionTree, List<String> dataSetNames) {
		this.fileName = filename;
		this.nexusDataSelectionFilename = null;
		this.nexusMetaDataSelectionFilename = null;
		this.dataSelectionTree=dataSelectionTree;
		this.metaSelectionTree=metaSelectionTree;
		this.dataSetNames = dataSetNames;
		if(dataSelectionTree == null || metaSelectionTree == null){
			throw new IllegalArgumentException("dataSelectionTree == null || metaSelectionTree == null");
		}
	}

	/**
	 * User function to get all entries
	 * @param filename
	 * @param loadAll true if all wanted
	 */
	public NexusLoader(String filename, boolean loadAll) {
		if (loadAll) {
			NexusTreeNodeSelection sel = NexusTreeNodeSelection.createTreeForAllData();
			this.fileName = filename;
			this.nexusDataSelectionFilename = null;
			this.nexusMetaDataSelectionFilename = null;
			this.dataSelectionTree = sel;
			this.metaSelectionTree = sel;
			this.dataSetNames = null;
		} else {
			this.fileName = filename;
			this.nexusDataSelectionFilename = "";
			this.nexusMetaDataSelectionFilename = "";
			this.dataSelectionTree = null;
			this.metaSelectionTree = null;
			this.dataSetNames = null;
		}
	}

	/**
	 * @param filename
	 *            - name of nexus file
	 * @param nexusDataSelectionFilename
	 *            - name of xml file defining data to be extracted. if null get all contents, if empty get NX class
	 *            items
	 * @param nexusMetaDataSelectionFilename  name of xml file defining metadata to be extracted. if null get all contents, if empty get NX class
	 *            items
	 * @param outputFilename
	 *            - name of file to write to
	 * @param dataSetNames list of scannables and detectors for which data is to be extracted. If null then all
	 * @throws ScanFileHolderException
	 */
	static public void convertToAscii(String filename, String nexusDataSelectionFilename, String nexusMetaDataSelectionFilename,
			String outputFilename, List<String> dataSetNames)
			throws ScanFileHolderException {
		DataHolder nxsh = new NexusLoader(filename, nexusDataSelectionFilename, nexusMetaDataSelectionFilename, dataSetNames).loadFile();
		new ASCIIDataHolderNexusSaver(outputFilename).saveFile(nxsh);
	}

	/**
	 * @param filename
	 *            - name of nexus file
	 * @param nexusDataSelectionFilename
	 *            - name of xml file defining data to be extracted. if null or empty get all NXdata entries
	 *            items
	 * @param nexusMetaDataSelectionFilename  name of xml file defining metadata to be extracted. if null or empty get all metaData entries
	 *            items
	 * @param outputFilename
	 *            - name of file to write to
	 * @param dataSetNames list of scannables and detectors for which data is to be extracted. If null then all
	 * @throws ScanFileHolderException
	 */
	static public void convertToSRS(String filename, String nexusDataSelectionFilename, String nexusMetaDataSelectionFilename, 
			String outputFilename, List<String> dataSetNames)
			throws ScanFileHolderException {
		DataHolder nxsh = new NexusLoader(filename, nexusDataSelectionFilename, nexusMetaDataSelectionFilename, dataSetNames).loadFile();
		new SRSNexusLoader(outputFilename).saveFile(nxsh);
	}

	
	public INexusTree loadTree(IMonitor mon) throws ScanFileHolderException {
		
		INexusTree tree = null;
		try {
			if( dataSelectionTree != null ){
				tree = dataSelectionTree.equals(NexusTreeNodeSelection.SKIP) ? null :NexusTreeBuilder.getNexusTree(fileName, dataSelectionTree, mon);
			} else {
				if( nexusDataSelectionFilename != null && !nexusDataSelectionFilename.isEmpty()){
					tree = NexusTreeBuilder.getNexusTree(fileName, nexusDataSelectionFilename, mon);
				} else {
					if( dataSetNames != null){
						tree = getTreeForDatasetNames(fileName, dataSetNames, true, mon);
					} else {
						tree = NexusTreeBuilder.getNexusTree(fileName, NexusTreeNodeSelection.createTreeForAllNXData(), mon);
					}
				}
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("NexusReader exception loading " + fileName
					+ (nexusDataSelectionFilename == null ? "" : (" using selection file" + nexusDataSelectionFilename)), e);
		
		}
		
		return tree;
	
	}
	
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
	    return this.loadFile(null);
	}
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		try {
			INexusTree tree = loadTree(mon);
			
			Map<String, INexusTree> nodes = getDatasetNodes(tree, dataSetNames);
			NexusDataHolder dataHolder = new NexusDataHolder();
			Vector<String> retrieveDataSetNames = new Vector<String>();
			if (dataSetNames != null) {
				for (String dataSetName : dataSetNames) {
					if (nodes.containsKey(dataSetName)) {
						addData(retrieveDataSetNames, dataHolder, nodes.get(dataSetName), dataSetName);
					}
				}
			} else {
				for( Entry<String, INexusTree> entry : nodes.entrySet()){
					addData(retrieveDataSetNames, dataHolder, entry.getValue(), entry.getKey());
				}			
			}
			
			if (!monitorIncrement(mon)) {
				return null;
			}

			INexusTree metaDatatree = null;
			if (metaSelectionTree != null) {
				metaDatatree = metaSelectionTree.equals(NexusTreeNodeSelection.SKIP) ? null : NexusTreeBuilder
						.getNexusTree(fileName, metaSelectionTree, mon);
			} else {
				if( nexusMetaDataSelectionFilename != null && !nexusMetaDataSelectionFilename.isEmpty()){
					metaDatatree = NexusTreeBuilder.getNexusTree(fileName, nexusMetaDataSelectionFilename, mon);
				} else {
					//if we only want specific dataSet them do not read in the metadata
					if( dataSetNames == null){
						metaDatatree = NexusTreeBuilder.getNexusTree(fileName, NexusTreeNodeSelection.createTreeForAllMetaData(), mon);
					}
				}
			}
			
			if (!monitorIncrement(mon)) return null;

			if (!monitorIncrement(mon)) {
				return null;
			}

			if (metaDatatree != null) {
				dataHolder.setNexusTree(metaDatatree);
			}

			return dataHolder;
			
		} catch (Exception e) {
			throw new ScanFileHolderException("NexusReader exception loading " + fileName
					+ (nexusDataSelectionFilename == null ? "" : (" using selection file " + nexusDataSelectionFilename)), e);
		}
	}

	public static Map<String, INexusTree> getDatasetNodes(INexusTree tree, List<String> datasetNames) {
		SortedMap<String, INexusTree> nodes = new TreeMap<String, INexusTree>();
		Vector<String> targets= new Vector<String>();
		if (tree != null) {
			for (int i = 0; i < tree.getNumberOfChildNodes(); i++) {
				INexusTree node = tree.getChildNode(i);
				if (node.getNxClass().equals(NexusExtractor.NXEntryClassName)) {
					for (int j = 0; j < node.getNumberOfChildNodes(); j++) {
						INexusTree entryNode = node.getChildNode(j);
						if (entryNode.getNxClass().equals(NexusExtractor.NXDataClassName)) {
							String nxDataName = entryNode.getName();
							for (int k = 0; k < entryNode.getNumberOfChildNodes(); k++) {
								INexusTree dataNode = entryNode.getChildNode(k);
								String name = dataNode.getName();
								/* get target if it exists as this is the unique id in the file */
								Map<String, Serializable> attributes = dataNode.getAttributes();
								String target = null;
								if (attributes != null ){
									target = (String)attributes.get("target");
								} 
								boolean add=true;
								if( target != null ){
									add  = !targets.contains(target);
									if( add ){
										if (target.endsWith( "instrument/" + nxDataName + "/" + name)){
											name = nxDataName + "." + name;
										}
									}
								} else {
									name = nxDataName + "." + name; 
								}
								if( add && ( datasetNames == null || datasetNames.contains(name))
										&& !nodes.containsKey(name)){
									nodes.put(name, dataNode);
									if (target != null) {
										targets.add(target);
									}
								}
							}
						}

					}
				}
			}
			if( nodes.isEmpty()){
				//look in detector section - data from areaDetector 
				for (int i = 0; i < tree.getNumberOfChildNodes(); i++) {
					INexusTree node = tree.getChildNode(i);
					if (node.getNxClass().equals(NexusExtractor.NXEntryClassName)) {
						for (int j = 0; j < node.getNumberOfChildNodes(); j++) {
							INexusTree entryNode = node.getChildNode(j);
							if (entryNode.getNxClass().equals(NexusExtractor.NXInstrumentClassName)) {
								for (int k = 0; k < entryNode.getNumberOfChildNodes(); k++) {
									INexusTree instrumentEntryNode = entryNode.getChildNode(k);
									if (instrumentEntryNode.getNxClass().equals(NexusExtractor.NXDetectorClassName)) {
										String detname = instrumentEntryNode.getName();
										for (int l = 0; l < instrumentEntryNode.getNumberOfChildNodes(); l++) {
											INexusTree detEntryNode = instrumentEntryNode.getChildNode(l);
											if ( detEntryNode.getNxClass().equals(NexusExtractor.SDSClassName))
												nodes.put(detname, detEntryNode);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return nodes;
	}

	public static INexusTree getTreeForDatasetNames(String filename, List<String> datasetNames, boolean withData, IMonitor mon) throws NexusException, NexusExtractorException {
		return NexusTreeBuilder.getNexusTree(filename, NexusTreeNodeSelection.createTreeForDataSetNames(datasetNames, withData), mon);
	}

	private void addData(Vector<String> retrieveDatasetNames, DataHolder dataHolder, INexusTree dataNode,
			String name) {
		if (retrieveDatasetNames.contains(name)) {
			return;
		}
		if (dataSetNames == null || dataSetNames.contains(name)) {
			NexusGroupData data = dataNode.getData();
			if (data != null && data.getBuffer() != null && !data.isChar()) {
				Dataset ds = data.toDataset(keepBitWidth);
				if (ds == null) {
					logger.error("NexusLoader cannot handle data of type {}", data.getType());
				} else {
					ds.setName(name);
					dataHolder.addDataset(name, ds);
					retrieveDatasetNames.add(name);
				}
			}
		}
	}

	private List<String>         allDataSetNames;
	private Map<String, int[]>   allDataSetRanks;
	
	@Override
	public void loadMetadata(final IMonitor mon) throws Exception {
		allDataSetNames = getDatasetNames(fileName, mon);
		allDataSetRanks = getDataShapes(fileName, allDataSetNames, mon);
	}
	
	@Override
	public IMetadata getMetadata() {
		Metadata md = new Metadata();
		for (Entry<String, int[]> e : allDataSetRanks.entrySet()) {
			md.addDataInfo(e.getKey(), e.getValue());
		}
		return md;
	}

//	private Map<String, Integer> getDataSizes(String path, List<String> sets, IMonitor mon) {
//		
//		try {
//			Map< String, INexusTree> trees = getDatasetNexusTrees(path, sets, false, mon);
//			final Map<String,Integer> ret = new HashMap<String, Integer>(sets.size());
//			for (Entry<String, INexusTree> tree : trees.entrySet()) {
//				ret.put(tree.getKey(), NexusExtractor.calcTotalLength(tree.getValue().getData().dimensions));
//			}
//			return ret;
//		} catch (Exception ne) {
//			logger.error("Cannot get data sizes for "+path, ne);
//		}
//		return null;
//	}
	private Map<String, int[]> getDataShapes(String path, List<String> sets, IMonitor mon) {
		
		try {
			Map< String, INexusTree> trees = getDatasetNexusTrees(path, sets, false, mon);
			final Map<String,int[]> ret = new TreeMap<String, int[]>();
			for (Entry<String, INexusTree> tree : trees.entrySet()) {
				ret.put(tree.getKey(), tree.getValue().getData().dimensions);
			}
			return ret;
		} catch (Exception ne) {
			logger.error("Cannot get data sizes for "+path, ne);
		}
		return null;
	}

	/*
	 * Helper method to extract DataSetNames from a NexusFile that can then be used to build a list of wanted names 
	 * in subsequent calls to NexusLoader(file, dataSetNames)
	 */
	static public List<String> getDatasetNames(String nexusFilename, IMonitor mon) throws NexusException, NexusExtractorException, Exception{
		INexusTree tree = getTreeForDatasetNames(nexusFilename, null, false, mon);
		Map<String, INexusTree> nodes =  getDatasetNodes(tree, null);
		Vector<String> dataSetNames = new Vector<String>();
		for( Entry<String, INexusTree> entry : nodes.entrySet()){
			dataSetNames.add(entry.getKey());
		}
		return dataSetNames;
	}	


	/*
	 * Helper method to extract sizes of specified datasets
	 */
	static public Map<String, INexusTree> getDatasetNexusTrees(String nexusFilename, List<String> datasetNames, boolean withData, IMonitor mon) throws NexusException, NexusExtractorException, Exception{
		INexusTree tree = getTreeForDatasetNames(nexusFilename, datasetNames, withData, mon);
		Map<String, INexusTree> nodes =  getDatasetNodes(tree, datasetNames);
		return nodes;
	}
	
	
	public Dataset loadSet(String path, String name, IMonitor mon) throws Exception {
		
		final List<String> origNames    =  dataSetNames;
		final String       origFileName =  fileName;
		
		try {
			this.fileName     = path;
			this.dataSetNames = Arrays.asList(new String[]{name});
			final DataHolder dh = loadFile(mon);
			return dh.getDataset(name);
		} finally {
			this.dataSetNames = origNames;
			this.fileName     = origFileName;
		}
    }
	
	public Map<String,ILazyDataset> loadSets(String path, List<String> names, IMonitor mon) throws Exception {
		
		final List<String> origNames    =  dataSetNames;
		final String       origFileName =  fileName;
		
		try {
			this.fileName     = path;
			this.dataSetNames = names;
			final DataHolder dh = loadFile(mon);
			return dh.toLazyMap();
		} finally {
			this.dataSetNames = origNames;
			this.fileName     = origFileName;
		}
    }

	
	/**
	 * Method is supposed to slice without reading all the rest of the data in.
	 * 
	 * *NOTE* If this is not protected with a synchronized then 
	 * 
	 * @param object
	 * @param mon
	 * @return set
	 * @throws Exception
	 */
	protected Dataset slice(final SliceObject object, IMonitor mon) throws Exception {

		final List<String> origNames    =  dataSetNames;
		final String       origFileName =  fileName;
		try {
			this.fileName     = object.getPath();
			this.dataSetNames = Arrays.asList(new String[]{object.getName()});
			if (mon!=null&&mon.isCancelled()) return null;
			INexusTree tree = getTreeForDatasetNames(fileName, dataSetNames, false, mon);
			if (mon!=null&&mon.isCancelled()) return null;
			Map<String, INexusTree> nodes =  getDatasetNodes(tree, dataSetNames);
			if (mon!=null&&mon.isCancelled()) return null;
		
			final INexusTree   node = nodes.get(object.getName());
			if (mon!=null&&mon.isCancelled()) return null;
			final ILazyDataset nSet = Nexus.createLazyDataset(node);
			if (mon!=null&&mon.isCancelled()) return null;
			
			Dataset slicedData = DatasetUtils.convertToDataset(
					nSet.getSlice(object.getSliceStart(), object.getSliceStop(), object.getSliceStep()));
            return slicedData;

		} finally {
			this.dataSetNames = origNames;
			this.fileName     = origFileName;
		}

	}

	/**
	 * @param node 
	 * @param name
	 * @return an Integer attribute (or null)
	 */
	public Integer getIntAttribute(INexusTree node, String name) {
		Serializable n = node.getAttribute(name);
		if (n instanceof Integer)
			return (Integer) n;
		return null;
	}
	/*
	|:
		|:|NXentry:entry1
		|:|NXentry:entry1|NXdata:Rapid2D
		|:|NXentry:entry1|NXdata:Rapid2D|SDS:data/dimensions:1:1:512:512/type. type - 5
		|:|NXentry:entry1|NXdata:Rapid2D|SDS:data/dimensions:1:1:512:512/type. type - 5|Attr:signal/dimensions:1/type. type - 24
		|:|NXentry:entry1|NXdata:Rapid2D|SDS:data/dimensions:1:1:512:512/type. type - 5|Attr:target/dimensions:31/type:NX_CHAR
		|:|NXentry:entry1|NXdata:Rapid2D|SDS:data/dimensions:1:1:512:512/type. type - 5|Attr:units/dimensions:6/type:NX_CHAR

		
		|SDS:data/dimensions:1:1:512:512/type. type - 5
		|SDS:data/dimensions:1:1:512:512/type. type - 5|Attr:signal/dimensions:1/type. type - 24
		|SDS:data/dimensions:1:1:512:512/type. type - 5|Attr:target/dimensions:31/type:NX_CHAR
  		|SDS:data/dimensions:1:1:512:512/type. type - 5|Attr:units/dimensions:6/type:NX_CHAR
    */
}
