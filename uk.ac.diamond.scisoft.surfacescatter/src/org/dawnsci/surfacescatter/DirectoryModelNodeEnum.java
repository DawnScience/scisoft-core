package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;

public enum DirectoryModelNodeEnum {

	initialLenPt(NeXusStructureStrings.getInitiallenpt(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getInitiallenpt(), drm.getInitialLenPt())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setInitialLenPt(
					getIntegerArrayofArraysAttribute(NeXusStructureStrings.getInitiallenpt(), nxData))),

	trackerCoordinates(NeXusStructureStrings.getTrackercoordinates(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getTrackercoordinates(), drm.getTrackerCoordinates())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setTrackerCoordinates(
					getDoubleArrayAttribute(NeXusStructureStrings.getTrackercoordinates(), nxData))),

	initialTrackerCoordinates(NeXusStructureStrings.getInitialtrackercoordinates(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getInitialtrackercoordinates(), drm.getInitialTrackerCoordinates())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setInitialTrackerCoordinates(
					getDoubleArrayAttribute(NeXusStructureStrings.getInitialtrackercoordinates(), nxData))),

	initialDatasetForEachDat(NeXusStructureStrings.getInitialdatasetforeachdat(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getInitialdatasetforeachdat(), drm.getInitialDatasetForEachDatSingleDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setInitialDatasetForEachDatFromSingleDataset(
					getDatasetAttribute(NeXusStructureStrings.getInitialdatasetforeachdat(), nxData))),

	lenPtForEachDat(NeXusStructureStrings.getLenptforeachdat(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLenptforeachdat(), drm.getLenPtForEachDatAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setLenPtForEachDatFromDataset(
					getDatasetAttribute(NeXusStructureStrings.getLenptforeachdat(), nxData))),

	sortedTheta(NeXusStructureStrings.getSortedtheta(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getSortedtheta(), drm.getSortedTheta())),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setSortedTheta(getDatasetAttribute(NeXusStructureStrings.getSortedtheta(), nxData))),

	interpolatedLenPts(NeXusStructureStrings.getInterpolatedlenpts(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getInterpolatedlenpts(), String.valueOf(drm.getInterpolatedLenPts()))),
			(GroupNode nxData, DirectoryModel drm) -> drm.setInterpolatedLenPts(
					getListofDoubleArrayofArrays(NeXusStructureStrings.getInterpolatedlenpts(), nxData))),

	permanentBoxOffsetLenPt(NeXusStructureStrings.getPermanentboxoffsetlenpt(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getPermanentboxoffsetlenpt(), drm.getPermanentBoxOffsetLenPtAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setPermanentBoxOffsetLenPtFromDataset(
					getDatasetAttribute(NeXusStructureStrings.getPermanentboxoffsetlenpt(), nxData))),

	permanentBackgroundLenPt(NeXusStructureStrings.getPermanentbackgroundlenpt(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getPermanentbackgroundlenpt(), drm.getPermanentBackgroundLenPtAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setPermanentBackgroundLenPtFromDataset(
					getDatasetAttribute(NeXusStructureStrings.getPermanentbackgroundlenpt(), nxData))),

	boxOffsetLenPt(NeXusStructureStrings.getBoxoffsetlenpt(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getBoxoffsetlenpt(), drm.getBoxOffsetLenPt())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setBoxOffsetLenPt(
					getIntegerArrayofArraysAttribute(NeXusStructureStrings.getBoxoffsetlenpt(), nxData))),

	backgroundROI(NeXusStructureStrings.getBackgroundroi(),
			(GroupNode nxData, DirectoryModel drm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getBackgroundroi(),
							createDoubleArrayAttributeFromROI((new int[][] { drm.getBackgroundROI().getIntLengths(),
									drm.getBackgroundROI().getBounds().getIntPoint() })))),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setBackgroundROI(getDoubleArrayAttribute(NeXusStructureStrings.getBackgroundroi(), nxData))),

	backgroundROIArray(NeXusStructureStrings.getBackgroundroiarray(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getBackgroundroiarray(), drm.getBackgroundROIForEachDatAsIntegerArrays())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setBackgroundROIForEachDat(
					getDatasetAttribute(NeXusStructureStrings.getBackgroundroiarray(), nxData))),

	backgroundLenPt(NeXusStructureStrings.getBackgroundlenpt(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getBackgroundlenpt(), drm.getBackgroundLenPt())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setBackgroundLenPt(
					getIntegerArrayofArraysAttribute(NeXusStructureStrings.getBackgroundlenpt(), nxData))),

	seedLocation(NeXusStructureStrings.getSeedlocation(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getSeedlocation(), drm.getSeedLocation())),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setSeedLocation(getDoubleArrayofArraysAttribute(NeXusStructureStrings.getSeedlocation(), nxData))),

	trackerLocationList(NeXusStructureStrings.getTrackerlocationlist(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getTrackerlocationlist(), drm.getTrackerLocationListAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setTrackerLocationListFromDataset(
					getDatasetAttribute(NeXusStructureStrings.getTrackerlocationlist(), nxData))),

	interpolatorBoxes(NeXusStructureStrings.getInterpolatorboxes(),
			(GroupNode nxData,
					DirectoryModel drm) -> nxData.addAttribute(TreeFactory
							.createAttribute(NeXusStructureStrings.getInterpolatorboxes(), drm.getInterpolatorBoxesAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm.setInterpolatorBoxesFromDataset(
					getDatasetAttribute(NeXusStructureStrings.getInterpolatorboxes(), nxData))),

	rodName(NeXusStructureStrings.getRodname(),
			(GroupNode nxData, DirectoryModel drm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getRodname(), drm.getRodName())),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setRodName(getStringAttribute(NeXusStructureStrings.getRodname(), nxData))),

	setPositions(NeXusStructureStrings.getSetpositions(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getSetpositions(), drm.getSetPositionsAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setSetPositionsFromDataset(getDatasetAttribute(NeXusStructureStrings.getSetpositions(), nxData))),
	
	interpolatorRegions(NeXusStructureStrings.getInterpolatorregions(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getInterpolatorregions(), drm.getInterpolatorRegionsAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setInterpolatorRegionsFromDataset(getDatasetAttribute(NeXusStructureStrings.getInterpolatorregions(), nxData))),

	setRegions(NeXusStructureStrings.getSetregions(),
			(GroupNode nxData, DirectoryModel drm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getSetregions(), drm.getSetRegionsAsDataset())),
			(GroupNode nxData, DirectoryModel drm) -> drm
					.setSetRegionsFromDataset(getDatasetAttribute(NeXusStructureStrings.getSetregions(), nxData)))


	;

	private String firstName;
	private directoryGroupNodePopulateFromDirectoryModel fg;
	private DirectoryModelPopulateFromGroupNode fp;

	DirectoryModelNodeEnum(String a, directoryGroupNodePopulateFromDirectoryModel fg,
			DirectoryModelPopulateFromGroupNode fp) {

		this.firstName = a;
		this.fg = fg;
		this.fp = fp;

	}

	public String getFirstName() {
		return firstName;
	}

	public directoryGroupNodePopulateFromDirectoryModel getdirectoryGroupNodePopulateFromDirectoryModelMethod() {
		return fg;

	}

	public DirectoryModelPopulateFromGroupNode getDirectoryModelPopulateFromGroupNode() {

		return fp;
	}

	public void directoryModelPopulateFromGroupNodeMethod(GroupNode n, DirectoryModel drm) {

		fp.DirectoryModelPopulateFromGroupNode1(n, drm);
	}

	public void directoryGroupNodePopulateFromDirectoryModelMethod(GroupNode nxData,
			DirectoryModel drm) {
		fg.directoryGroupNodePopulateFromDirectoryModel1(nxData, drm);
	}

	private static String getStringAttribute(String desired, GroupNode g) {
		StringDataset sd = DatasetUtils.cast(StringDataset.class, g.getAttribute(desired).getValue());
		return sd.get(0);
	}

	private static Dataset getDatasetAttribute(String desired, GroupNode g) {
		Dataset sd = DatasetUtils.cast(Dataset.class, g.getAttribute(desired).getValue());
		return sd;
	}

	private static Dataset[] getDatasetArrayAttribute(String desired, GroupNode g) {
		Dataset sd = DatasetUtils.cast(Dataset.class, g.getAttribute(desired).getValue());

		int[] sdShape = sd.getShape();

		Dataset[] slices = new Dataset[sdShape[0]];
		SliceND slice = new SliceND(sdShape);

		for (int i = 0; i < sdShape[0]; i++) {
			slice.setSlice(0, i, i + 1, 1);
			slices[i] = sd.getSlice(slice);
		}

		return slices;
	}

	private static double[] getDoubleArrayAttribute(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();

		return (double[]) roiAttributeDat.getObject(0);
	}

	private static ArrayList<double[]> getListofDoubleArrays(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();
		ArrayList<double[]> out = new ArrayList<>();

		for (int i = 0; i < roiAttributeDat.getShape()[0]; i++) {
			out.add((double[]) roiAttributeDat.getObject(i));
		}

		return out;
	}

	private static double[] createDoubleArrayAttributeFromROI(int[][] lenPt) {

		double[] roi = LocationLenPtConverterUtils.lenPtToLocationConverter(lenPt);

		return roi;
	}

	private static ArrayList<double[][]> getListofDoubleArrayofArrays(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();
		ArrayList<double[][]> out = new ArrayList<>();

		for (int i = 0; i < roiAttributeDat.getShape()[0]; i++) {
			out.add((double[][]) roiAttributeDat.getObject(i));
		}

		return out;
	}

	private static double[][] getDoubleArrayofArraysAttribute(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();

		return (double[][]) roiAttributeDat.getObject(0);
	}

	private static int[][] getIntegerArrayofArraysAttribute(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();

		return (int[][]) roiAttributeDat.getObject(0);
	}

	private static int[][][] getIntegerArrayofArrayofArraysAttribute(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();

		return (int[][][]) roiAttributeDat.getObject(0);
	}

	public DirectoryModelNodeEnum getFromFirstName(String in) {

		for (DirectoryModelNodeEnum o : DirectoryModelNodeEnum.values()) {
			if (in.equals(o.getFirstName())) {
				return o;
			}
		}

		return null;
	}

	@FunctionalInterface
	public interface directoryGroupNodePopulateFromDirectoryModel {
		void directoryGroupNodePopulateFromDirectoryModel1(GroupNode nxData, DirectoryModel drm);
	}

	@FunctionalInterface
	public interface DirectoryModelPopulateFromGroupNode {
		void DirectoryModelPopulateFromGroupNode1(GroupNode nxData, DirectoryModel drm);
	}

}
