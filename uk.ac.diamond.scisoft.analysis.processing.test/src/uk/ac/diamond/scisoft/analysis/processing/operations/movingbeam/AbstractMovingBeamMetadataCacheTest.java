/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.dawnsci.persistence.internal.PersistSinglePowderCalibrationNode;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.junit.Assert;
import org.junit.rules.TemporaryFolder;

import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionMetadataUtils;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public abstract class AbstractMovingBeamMetadataCacheTest {

	private Dataset XAxisData = DatasetFactory.createFromObject(new double[] { -1., -0.5, 0, 0.5, 1. });
	private Dataset YAxisData = DatasetFactory.createFromObject(new double[] { -2, -1.5, -1, -0.5, 0 });
	private Dataset frameKeys = DatasetFactory.createRange(IntegerDataset.class, shape[0]*shape[1] ).reshape(shape[0],shape[1],1,1);
	private int flatRefMetaID = 12;
	private static int[] shape = new int[] { 5, 5, 100, 100 };
	private double[] refPosition = new double[2];
	private Dataset iData = DatasetFactory.zeros(IntegerDataset.class, shape);
	private NexusFileFactoryHDF5 fac = new NexusFileFactoryHDF5();
	protected static String XAxisPath = "/entry/diffraction/kbx_value";
	protected static String YAxisPath = "/entry/diffraction/kby_value";
	private static List<String> scanAxesNames = Arrays.asList(XAxisPath, YAxisPath);
	protected List<String> pNames = Arrays.asList("dimension_0", "dimension_1", NexusConstants.DATA_AXESEMPTY,
			NexusConstants.DATA_AXESEMPTY);
	protected static String dataPath = "/entry/diffraction/data";

	protected File scanFile;
	protected List<File> calibrationFile = new ArrayList<>();
	protected File iFrameFolder;

	private IDiffractionMetadata dm;
	protected List<IDiffractionMetadata> metadatas;
	private List<Dataset> readbacks;

	public AbstractMovingBeamMetadataCacheTest() {

		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(iData.getShape()[2],
				iData.getShape()[3]);
		DiffractionCrystalEnvironment ce = DiffractionCrystalEnvironment.getDefaultDiffractionCrystalEnvironment();

		dm = new DiffractionMetadata("", dp, ce);

	}
	
	/**
	 * Construct and write the scan file to the specified temp folder
	 * @param tmp
	 * @throws IOException
	 */
	public void buildScanFile(TemporaryFolder tmp) throws IOException {

		scanFile = tmp.newFile("scan.nxs");
		iFrameFolder = tmp.newFolder("frames");
		readbacks = DatasetUtils.meshGrid(XAxisData, YAxisData);
		
		List<Dataset> flatData = readbacks.stream().map(Dataset::clone).map(Dataset::flatten)
				.collect(Collectors.toList());

		double xr = flatData.get(0).getDouble(flatRefMetaID);
		double yr = flatData.get(1).getDouble(flatRefMetaID);

		refPosition = new double[] { xr, yr };
		flatData.get(0).isubtract(xr);
		flatData.get(1).isubtract(yr);

		metadatas = IntStream.range(0, flatData.get(0).getSize()).mapToObj(i -> DiffractionMetadataUtils
				.getOffsetMetadata(dm, flatData.get(0).getDouble(i), flatData.get(1).getDouble(i), 0))
				.collect(Collectors.toList());

		Tree t = buildScanTree();

		try (NexusFile nf = fac.newNexusFile(scanFile.getAbsolutePath(), false)) {
			nf.createAndOpenToWrite();
			nf.addNode("/entry", t.getGroupNode());
		} catch (NexusException e) {
			Assert.fail("Error generating scan nexus file for testing.");
		}

	}

	/**
	 * Write a single calibration frame with additional information required to ensure compatibility 
	 * with loading using {@link DiffractionMetadataImportModel}
	 * 
	 * @param tmp
	 * @throws IOException
	 */
	public void writeSingleCalibrationFile(TemporaryFolder tmp) throws IOException {

		Dataset image = iData.getSlice(new Slice(0, 1, 1), new Slice(0, 1, 1), new Slice(null), new Slice(null));
		if (calibrationFile.isEmpty()) {
			String frameReference = String.format(iFrameFolder.getName() + "/scan-frame_%03d.nxs", 0);
			calibrationFile.add(tmp.newFile(frameReference));
		}

		GroupNode n = PersistSinglePowderCalibrationNode.persistSingleCalibration(image, dm, null);
		GroupNode cDataNode = n.getGroupNode("calibration_data");
		int[] scanMetaShape = Arrays.copyOf(image.getShape(), 4);
		scanMetaShape[2] = scanMetaShape[3] = 1;

		addAxesInformationToNode(cDataNode, pNames);

		List<String> dSetNames = scanAxesNames.stream().map(this::extractDataName).collect(Collectors.toList());

		Dataset pData0 = DatasetUtils.resize(DatasetFactory.createFromObject(refPosition[0]), scanMetaShape);
		pData0.setName(dSetNames.get(0));
		String path0 = extractPath(scanAxesNames.get(0));

		Dataset pData1 = DatasetUtils.resize(DatasetFactory.createFromObject(refPosition[1]), scanMetaShape);
		pData1.setName(dSetNames.get(1));
		String path1 = extractPath(scanAxesNames.get(1));

		try (NexusFile nf = fac.newNexusFile(calibrationFile.get(0).getAbsolutePath(), false)) {
			nf.createAndOpenToWrite();
			nf.addNode("/entry", n);

			nf.createData(path0, pData0, true);
			nf.createData(path1, pData1, true);
			nf.link(scanAxesNames.get(0), "/entry/calibration_data/" + pNames.get(0));
			nf.link(scanAxesNames.get(1), "/entry/calibration_data/" + pNames.get(1));

		} catch (NexusException e) {
			Assert.fail("Error generating scan nexus file for testing.");
		}

	}
	
	/**
	 * Construct a folder and write individual frame diffraction calibrations to it to test metadata retrieval.
	 * 
	 * @param tmp
	 */
	public void writeNeighbourCalibrationFiles(TemporaryFolder tmp) {
		SliceND snd = new SliceND(iData.getShape(), new Slice(0, null, 1), new Slice(0, null, 1), new Slice(null),
				new Slice(null));
		SliceViewIterator s = new SliceViewIterator(iData, snd, 2, 3);

		s.reset();
		while (s.hasNext()) {
			try {
				int pos1D = s.getCurrent();
				Dataset frame = DatasetUtils.sliceAndConvertLazyDataset(s.next());
				String frameReference = String.format(iFrameFolder.getName() + "/scan-frame_%03d.nxs", pos1D);
				File cFile = tmp.newFile(frameReference);
				GroupNode n = PersistSinglePowderCalibrationNode.persistSingleCalibration(frame.squeeze(),
						metadatas.get(pos1D), null);

				try (NexusFile nf = fac.newNexusFile(cFile.getAbsolutePath(), false)) {
					nf.createAndOpenToWrite();
					nf.addNode("/entry", n);

				} catch (NexusException e) {
					Assert.fail(e.getMessage());
				}
				calibrationFile.add(cFile);

			} catch (DatasetException e) {
				Assert.fail("Error on writing multiple frames");
			} catch (IOException e) {
				Assert.fail("Error on multiple frame file creation");

			}

		}

	}
	
	/**
	 * extract the path to a node from a string representation
	 * 
	 * @param s
	 * @return string up to the last index of //
	 */
	private String extractPath(String s) {
		return s.substring(0, s.lastIndexOf(Node.SEPARATOR));
	}
	
	/**
	 * extract a node name from a string representation
	 * 
	 * @param s
	 * @return last section of a string after //
	 */
	private String extractDataName(String s) {
		return s.substring(s.lastIndexOf(Node.SEPARATOR) + 1);
	}
	
	/**
	 * add the necessary attributes to the specified group node
	 * 
	 * @param parent
	 * @param nodeNames list of names for the axes 
	 */
	private void addAxesInformationToNode(GroupNode parent, List<String> nodeNames) {
		
		Attribute axes = TreeFactory.createAttribute(NexusConstants.DATA_AXES, nodeNames.toArray(new String[nodeNames.size()]));
		Attribute ax1Indices = TreeFactory.createAttribute(nodeNames.get(0) + NexusConstants.DATA_INDICES_SUFFIX, 0);
		Attribute ax2Indices = TreeFactory.createAttribute(nodeNames.get(1) + NexusConstants.DATA_INDICES_SUFFIX, 1);
		parent.addAttribute(axes);
		parent.addAttribute(ax1Indices);
		parent.addAttribute(ax2Indices);
		parent.addAttribute(TreeFactory.createAttribute("signal", "data"));

	}
	
	/**
	 * Construct a basic scan tree in memory for testing
	 * 
	 * @return
	 */
	public Tree buildScanTree() {

		Tree tree = TreeFactory.createTreeFile(0, scanFile.getAbsolutePath());

		GroupNode entry = NexusNodeFactory.createNXentry();
		NXdata images = NexusNodeFactory.createNXdata();

		images.setData("data", iData);
		List<String> dNames = scanAxesNames.stream().map(s -> extractDataName(s) + NexusConstants.DATA_AXESSET_SUFFIX).collect(Collectors.toList());
		dNames.addAll(Arrays.asList(NexusConstants.DATA_AXESEMPTY, NexusConstants.DATA_AXESEMPTY));

		addAxesInformationToNode(images, dNames);
		
		String xname = extractDataName(XAxisPath);
		String yname = extractDataName(YAxisPath);
		XAxisData.setName(xname + NexusConstants.DATA_AXESSET_SUFFIX);
		YAxisData.setName(yname + NexusConstants.DATA_AXESSET_SUFFIX);
		readbacks.get(0).setName(xname);
		readbacks.get(1).setName(yname);
		List<Dataset> metaData = Arrays.asList(XAxisData, YAxisData, readbacks.get(0), readbacks.get(1));
		createDataNodes(metaData, images);

		entry.addGroupNode("diffraction", images);
		
		
		
		DataNode keyNode = TreeFactory.createDataNode(0);
		String keyPath = AbstractMovingBeamMetadataCache.buildFrameKeysPath(scanFile.getAbsolutePath(),AbstractMovingBeamMetadataCache.frameKeyPath, true);
		keyPath=keyPath.replace("/entry/", "");
		keyNode.setDataset(frameKeys);
		String[] groups = keyPath.split(Node.SEPARATOR);
		
		GroupNode dScan = TreeFactory.createGroupNode(1);
		GroupNode keys = TreeFactory.createGroupNode(2);
		
		
		keys.addDataNode(groups[2], keyNode);
		dScan.addGroupNode(groups[1], keys);
		entry.addGroupNode(groups[0],dScan);
		
		tree.setGroupNode(entry);
		
		

		return tree;

	}
	
	/**
	 * Add a list of datasets to a node.
	 * 
	 * @param datasets
	 * @param parent
	 */
	private void createDataNodes(List<Dataset> datasets, GroupNode parent) {
		IntStream.range(0, datasets.size()).forEach(i -> {
			DataNode d = TreeFactory.createDataNode(i);
			d.setDataset(datasets.get(i));
			parent.addDataNode(datasets.get(i).getName(), d);
		});
	}
}
