package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;

public enum OutputCurvesDataPackageEnum {
	
	NO_DATS(NeXusStructureStrings.getNoDats(),
			(GroupNode nxData,
					OutputCurvesDataPackage ocdp) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getNoDats(), ocdp.getNoOfDats())),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setNoOfDats(getIntegerAttribute(NeXusStructureStrings.getNoDats(), nxData))),
	
	YLIST(NeXusStructureStrings.getYlist(),
			(GroupNode nxData,
					OutputCurvesDataPackage ocdp) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getYlist(), convertListofDoublesToDataset(ocdp.getyList()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setyList(getListofDoublesAttribute(NeXusStructureStrings.getYlist(), nxData))),

	YLIST_ERROR(NeXusStructureStrings.getYlistError(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getYlistError(), convertListofDoublesToDataset(ocdp.getyListError()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setyListError(getListofDoublesAttribute(NeXusStructureStrings.getYlistError(), nxData))),

	YLIST_FHKL(NeXusStructureStrings.getYlistFhkl(),
			(GroupNode nxData,
					OutputCurvesDataPackage ocdp) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getYlistFhkl(), convertListofDoublesToDataset(ocdp.getyListFhkl()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setyListFhkl(getListofDoublesAttribute(NeXusStructureStrings.getYlistFhkl(), nxData))),

	YLIST_FHKL_ERROR(NeXusStructureStrings.getYlistFhklError(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistFhklError(),
							convertListofDoublesToDataset(ocdp.getyListFhklError()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setyListFhklError(getListofDoublesAttribute(NeXusStructureStrings.getYlistFhklError(), nxData))),

	YLIST_RAW(NeXusStructureStrings.getYlistRaw(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getYlistRaw(), convertListofDoublesToDataset(ocdp.getyListRawIntensity()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setyListRawIntensity(getListofDoublesAttribute(NeXusStructureStrings.getYlistRaw(), nxData))),

	YLIST_RAW_ERROR(NeXusStructureStrings.getYlistRawError(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistRawError(),
							convertListofDoublesToDataset(ocdp.getyListRawIntensityError()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListRawIntensityError(
					getListofDoublesAttribute(NeXusStructureStrings.getYlistRawError(), nxData))),

	YLIST_FOR_EACH_DAT(NeXusStructureStrings.getYlistForEachDat(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistForEachDat(),
							convertListofListsOfDoublesToDataset(ocdp.getyListForEachDat()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getYlistForEachDat(), nxData))),

	YLIST_ERROR_FOR_EACH_DAT(NeXusStructureStrings.getYlistErrorForEachDat(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistErrorForEachDat(),
							convertListofListsOfDoublesToDataset(ocdp.getyListErrorForEachDat()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getYlistErrorForEachDat(), nxData))),

	YLIST_FHKL_FOR_EACH_DAT(NeXusStructureStrings.getYlistFhklForEachDat(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistFhklForEachDat(),
							convertListofListsOfDoublesToDataset(ocdp.getyListFhklForEachDat()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListFhklForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getYlistFhklForEachDat(), nxData))),

	YLIST_FHKL_ERROR_FOR_EACH_DAT(NeXusStructureStrings.getYlistFhklErrorForEachDat(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistFhklErrorForEachDat(),
							convertListofListsOfDoublesToDataset(ocdp.getyListFhklErrorForEachDat()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListFhklErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getYlistFhklErrorForEachDat(), nxData))),

	YLIST_RAW_FOR_EACH_DAT(NeXusStructureStrings.getYlistRawForEachDat(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistRawForEachDat(),
							convertListofListsOfDoublesToDataset(ocdp.getyListRawIntensityErrorForEachDat()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListRawIntensityForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getYlistRawErrorForEachDat(), nxData))),

	YLIST_RAW_ERROR_FOR_EACH_DAT(NeXusStructureStrings.getYlistRawErrorForEachDat(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getYlistRawErrorForEachDat(),
							convertListofListsOfDoublesToDataset(ocdp.getyListRawIntensityErrorForEachDat()))),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setyListRawIntensityErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getYlistRawErrorForEachDat(), nxData))),

	OUTPUT_DATA_ARRAY(NeXusStructureStrings.getOutputDataArray(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> addDataNodeFromDataset(
					NeXusStructureStrings.getOutputDataArray(), nxData,
					concatenateIDatasetArrayList(ocdp.getOutputDatArray()), 0),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp
					.setOutputDatArray(getListOfDatasetsAttribute(NeXusStructureStrings.getOutputDataArray(), nxData))),

	BACKGROUND_DATA_ARRAY(NeXusStructureStrings.getBackgroundDataArray(),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> addDataNodeFromDataset(
					NeXusStructureStrings.getBackgroundDataArray(), nxData,
					concatenateIDatasetArrayList(ocdp.getBackgroundDatArray()), 1),
			(GroupNode nxData, OutputCurvesDataPackage ocdp) -> ocdp.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getBackgroundDataArray(), nxData))),;

	private String firstName;
	private ocdpGroupNodePopulateFromOutputCurvesDataPackage fg;
	private OutputCurvesDataPackagePopulateFromGroupNode fp;

	OutputCurvesDataPackageEnum(String a, ocdpGroupNodePopulateFromOutputCurvesDataPackage fg,
			OutputCurvesDataPackagePopulateFromGroupNode fp) {

		this.firstName = a;
		this.fg = fg;
		this.fp = fp;

	}

	public String getFirstName() {
		return firstName;
	}

	public ocdpGroupNodePopulateFromOutputCurvesDataPackage getdirectoryGroupNodePopulateFromOutputCurvesDataPackageMethod() {
		return fg;

	}

	public OutputCurvesDataPackagePopulateFromGroupNode getOutputCurvesDataPackagePopulateFromGroupNode() {

		return fp;
	}

	public void outputCurvesDataPackagePopulateFromGroupNodeMethod(GroupNode n, OutputCurvesDataPackage ocdp) {

		fp.OutputCurvesDataPackagePopulateFromGroupNode1(n, ocdp);
	}

	public void ocdpGroupNodePopulateFromOutputCurvesDataPackageMethod(GroupNode nxData, OutputCurvesDataPackage ocdp) {
		fg.ocdpGroupNodePopulateFromOutputCurvesDataPackage1(nxData, ocdp);
	}

	private static ArrayList<IDataset> getListOfDatasetsAttribute(String desired, GroupNode g) {
		Dataset sd = DatasetUtils.cast(Dataset.class, g.getAttribute(desired).getValue());

		int[] sdShape = sd.getShape();

		ArrayList<IDataset> slices = new ArrayList<>();

		SliceND slice = new SliceND(sdShape);

		for (int i = 0; i < sdShape[0]; i++) {
			slice.setSlice(0, i, i + 1, 1);
			slices.add(sd.getSlice(slice));
		}

		return slices;
	}

	private static void addDataNodeFromDataset(String name, GroupNode g, IDataset in, int l) {

		DataNode newDN = new DataNodeImpl(l);

		try {
			newDN.setDataset(in.getSlice(new SliceND(in.getShape())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage() + "    what, why?   ");
		}

		g.addDataNode(name, newDN);

	}

	private static Dataset convertListofDoublesToDataset(ArrayList<Double> in) {

		return DatasetFactory.createFromList(in);

	}

	private static IDataset convertListofListsOfDoublesToDataset(ArrayList<ArrayList<Double>> in) {

		
		ArrayList<ArrayList<Double>> in1 = padListsToUniformLength(in);
		
		ArrayList<IDataset> im = new ArrayList<>();

		for (ArrayList<Double> a : in1) {
			im.add(DatasetFactory.createFromList(a));
			
		}

		return concatenateIDatasetArrayList(im);
	}

	private static IDataset concatenateIDatasetArrayList(ArrayList<IDataset> in) {

		Dataset h = DatasetFactory.ones(new int[] {in.size(), in.get(0).getSize()});
		
		int maxj = in.get(0).getShape()[0];
		
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < maxj; j++) {
			
				h.set(in.get(i).getDouble(j), i, j);
			}
			
		}

		return h;
	}
	
	private static ArrayList<ArrayList<Double>> padListsToUniformLength (ArrayList<ArrayList<Double>> in){
		
		ArrayList<ArrayList<Double>> out= (ArrayList<ArrayList<Double>>) in.clone();
		
		int longestListLength = 0;
		
		for(ArrayList<Double> r : out) {
			if(r.size()>longestListLength) {
				longestListLength = r.size();
			}
		}
		
		for(ArrayList<Double> r : out) {
			if(r.size()<longestListLength) {
				int padding = longestListLength -r.size();
				
				for(int j =0; j<padding; j++) {
					r.add(Double.MAX_VALUE);
				}
			}
		}
		
		return out;
	}

	private static ArrayList<ArrayList<Double>> getListofListsOfDoublesAttribute(String desired, GroupNode g) {

		IDataset sd = g.getAttribute(desired).getValue();

		ArrayList<ArrayList<Double>> out = new ArrayList<>();

		SliceND slice = new SliceND(sd.getShape());

		for (int i = 0; i < sd.getShape()[0]; i++) {
			slice.setSlice(0, i, i + 1, 1);
			IDataset a = sd.getSlice(slice).squeeze();

			ArrayList<Double> b = new ArrayList<>();

			for (int j = 0; j < a.getSize(); j++) {
				if(a.getDouble(j) != Double.MAX_VALUE) {
					b.add(a.getDouble(j));
				}
			}

			out.add(b);
		}
		
		

		return out;

	}
	
	private Dataset getDoubleArrayOfArraysAsDataset(double[][] in) {

		ArrayList<double[]> out = new ArrayList<>();

		for (int i = 0; i < in.length; i++) {
			out.add(in[i]);
		}

		return DatasetFactory.createFromList(out);
	}
		
		

	private static ArrayList<Double> getListofDoublesAttribute(String desired, GroupNode g) {

		IDataset sd = g.getAttribute(desired).getValue();

		ArrayList<Double> out = new ArrayList<>();

		for (int j = 0; j < sd.getSize(); j++) {
			out.add(sd.getDouble(j));
		}

		return out;

	}

	private static Dataset localConcatenate(IDataset[] in, int dim) {

		for (IDataset i : in) {

			if (i == null) {
				return null;
			}

			if (i.getSize() == 0) {
				return null;
			}
		}

		return DatasetUtils.convertToDataset(DatasetUtils.concatenate(in, dim));
	}

	public OutputCurvesDataPackageEnum getFromFirstName(String in) {

		for (OutputCurvesDataPackageEnum o : OutputCurvesDataPackageEnum.values()) {
			if (in.equals(o.getFirstName())) {
				return o;
			}
		}

		return null;
	}
	

	private static double[][] getDoubleArrayofArraysAttribute(String desired, GroupNode g) {

		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();

		double[][] out = new double[roiAttributeDat.getShape()[0]][];

		SliceND slice = new SliceND(roiAttributeDat.getShape());

		for (int i = 0; i < roiAttributeDat.getShape()[0]; i++) {

			slice.setSlice(0, i, i + 1, 1);

			ILazyDataset ld = roiAttributeDat.getSlice(slice).squeeze();
			
			double[] a = new double[ld.getShape()[0]];

			for (int j = 0; j < ld.getShape()[0]; j++) {

				try {
					a[j] = ld.getSlice(new SliceND(ld.getShape())).getInt(j);
				} catch (DatasetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			out[i] = a;

		}

		return out;
	}

	
	private static int getIntegerAttribute(String desired, GroupNode g) {
		IDataset sd = g.getAttribute(desired).getValue();
		return (int) sd.getInt(0);
	}

	@FunctionalInterface
	public interface ocdpGroupNodePopulateFromOutputCurvesDataPackage {
		void ocdpGroupNodePopulateFromOutputCurvesDataPackage1(GroupNode nxData, OutputCurvesDataPackage ocdp);
	}

	@FunctionalInterface
	public interface OutputCurvesDataPackagePopulateFromGroupNode {
		void OutputCurvesDataPackagePopulateFromGroupNode1(GroupNode nxData, OutputCurvesDataPackage ocdp);
	}

}
