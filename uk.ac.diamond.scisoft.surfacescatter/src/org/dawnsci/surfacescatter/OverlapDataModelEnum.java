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
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;

public enum OverlapDataModelEnum {

	lowerDatName(NeXusStructureStrings.getLowerdatname(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLowerdatname(), ovdm.getLowerDatName())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setLowerDatName(getStringAttribute(NeXusStructureStrings.getLowerdatname(), nxData))),

	lowerOverlapPositions(NeXusStructureStrings.getLoweroverlappositions(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getLoweroverlappositions(), convertListofDoublesToDataset(ovdm.getyList()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setyList(getListofDoublesAttribute(NeXusStructureStrings.getLoweroverlappositions(), nxData))),

	lowerOverlapScannedValues(NeXusStructureStrings.getLoweroverlapscannedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapscannedvalues(),
							convertListofDoublesToDataset(ovdm.getyListError()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListError(
					getListofDoublesAttribute(NeXusStructureStrings.getLoweroverlapscannedvalues(), nxData))),

	lowerOverlapCorrectedValues(NeXusStructureStrings.getLoweroverlapcorrectedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapcorrectedvalues(),
							convertListofDoublesToDataset(ovdm.getyListFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhkl(
					getListofDoublesAttribute(NeXusStructureStrings.getLoweroverlapcorrectedvalues(), nxData))),

	lowerOverlapRawValues(NeXusStructureStrings.getLoweroverlaprawvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlaprawvalues(),
							convertListofDoublesToDataset(ovdm.getyListFhklError()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhklError(
					getListofDoublesAttribute(NeXusStructureStrings.getLoweroverlaprawvalues(), nxData))),

	lowerOverlapFhklValues(NeXusStructureStrings.getLoweroverlapfhklvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfhklvalues(),
							convertListofDoublesToDataset(ovdm.getyListRawIntensity()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensity(
					getListofDoublesAttribute(NeXusStructureStrings.getLoweroverlapfhklvalues(), nxData))),

	lowerOverlapFitParametersCorrected(NeXusStructureStrings.getLoweroverlapfitparameterscorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfitparameterscorrected(),
							convertListofDoublesToDataset(ovdm.getyListRawIntensityError()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensityError(
					getListofDoublesAttribute(NeXusStructureStrings.getLoweroverlapfitparameterscorrected(), nxData))),

	lowerOverlapFitParametersRaw(NeXusStructureStrings.getLoweroverlapfitparametersraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfitparametersraw(),
							convertListofListsOfDoublesToDataset(ovdm.getyListForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getLoweroverlapfitparametersraw(), nxData))),

	lowerOverlapFitParametersFhkl(NeXusStructureStrings.getLoweroverlapfitparametersfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfitparametersfhkl(),
							convertListofListsOfDoublesToDataset(ovdm.getyListErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getLoweroverlapfitparametersfhkl(),
							nxData))),

	upperDatName(NeXusStructureStrings.getUpperdatname(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperdatname(),
							convertListofListsOfDoublesToDataset(ovdm.getyListFhklForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhklForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperdatname(), nxData))),

	upperOverlapPositions(NeXusStructureStrings.getUpperoverlappositions(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlappositions(),
							convertListofListsOfDoublesToDataset(ovdm.getyListFhklErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhklErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperoverlappositions(), nxData))),

	upperOverlapScannedValues(NeXusStructureStrings.getUpperoverlapscannedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapscannedvalues(),
							convertListofListsOfDoublesToDataset(ovdm.getyListRawIntensityErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensityForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperoverlapscannedvalues(), nxData))),

	upperOverlapCorrectedValues(NeXusStructureStrings.getUpperoverlapcorrectedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapcorrectedvalues(),
							convertListofListsOfDoublesToDataset(ovdm.getyListRawIntensityErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensityErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperoverlapcorrectedvalues(), nxData))),

	upperOverlapRawValues(NeXusStructureStrings.getUpperoverlaprawvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getUpperoverlaprawvalues(), nxData,
					concatenateIDatasetArrayList(ovdm.getOutputDatArray()), 0),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setOutputDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getUpperoverlaprawvalues(), nxData))),

	upperOverlapFhklValues(NeXusStructureStrings.getUpperoverlapfhklvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getUpperoverlapfhklvalues(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getUpperoverlapfhklvalues(), nxData))),

	upperOverlapFitParametersRaw(NeXusStructureStrings.getUpperoverlapfitparametersraw(),
			(GroupNode nxData,
					OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUpperoverlapfitparametersraw(), ovdm.getNoOfDats())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setNoOfDats(getIntegerAttribute(NeXusStructureStrings.getUpperoverlapfitparametersraw(), nxData))),

	upperOverlapFitParametersFhkl(NeXusStructureStrings.getUpperoverlapfitparametersfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapfitparametersfhkl(),
							convertListofDoublesToDataset(ovdm.getyList()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyList(
					getListofDoublesAttribute(NeXusStructureStrings.getUpperoverlapfitparametersfhkl(), nxData))),

	attenuationFactor(NeXusStructureStrings.getAttenuationfactor(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getAttenuationfactor(), convertListofDoublesToDataset(ovdm.getyListError()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setyListError(getListofDoublesAttribute(NeXusStructureStrings.getAttenuationfactor(), nxData))),

	attenuationFactorFhkl(NeXusStructureStrings.getAttenuationfactorfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getAttenuationfactorfhkl(),
							convertListofDoublesToDataset(ovdm.getyListFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setyListFhkl(getListofDoublesAttribute(NeXusStructureStrings.getAttenuationfactorfhkl(), nxData))),

	attenuationFactorRaw(NeXusStructureStrings.getAttenuationfactorraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getAttenuationfactorraw(),
							convertListofDoublesToDataset(ovdm.getyListFhklError()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhklError(
					getListofDoublesAttribute(NeXusStructureStrings.getAttenuationfactorraw(), nxData))),

	upperFFTFitCoefficientsCorrected(NeXusStructureStrings.getUpperfftfitcoefficientscorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getUpperfftfitcoefficientscorrected(),
							convertListofDoublesToDataset(ovdm.getyListRawIntensity()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensity(
					getListofDoublesAttribute(NeXusStructureStrings.getUpperfftfitcoefficientscorrected(), nxData))),

	upperFFTFitCoefficientsRaw(NeXusStructureStrings.getUpperfftfitcoefficientsraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsraw(),
							convertListofDoublesToDataset(ovdm.getyListRawIntensityError()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensityError(
					getListofDoublesAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsraw(), nxData))),

	upperFFTFitCoefficientsFhkl(NeXusStructureStrings.getUpperfftfitcoefficientsfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsfhkl(),
							convertListofListsOfDoublesToDataset(ovdm.getyListForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsfhkl(), nxData))),

	upperBaseFrequencyCorrected(NeXusStructureStrings.getUpperbasefrequencycorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperbasefrequencycorrected(),
							convertListofListsOfDoublesToDataset(ovdm.getyListErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperbasefrequencycorrected(), nxData))),

	upperBaseFrequencyRaw(NeXusStructureStrings.getUpperbasefrequencyraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperbasefrequencyraw(),
							convertListofListsOfDoublesToDataset(ovdm.getyListFhklForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhklForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperbasefrequencyraw(), nxData))),

	upperBaseFrequencyFhkl(NeXusStructureStrings.getUpperbasefrequencyfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperbasefrequencyfhkl(),
							convertListofListsOfDoublesToDataset(ovdm.getyListFhklErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListFhklErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getUpperbasefrequencyfhkl(), nxData))),

	lowerFFTFitCoefficientsCorrected(NeXusStructureStrings.getLowerfftfitcoefficientscorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLowerfftfitcoefficientscorrected(),
							convertListofListsOfDoublesToDataset(ovdm.getyListRawIntensityErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensityForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getLowerfftfitcoefficientscorrected(),
							nxData))),

	lowerFFTFitCoefficientsRaw(NeXusStructureStrings.getLowerfftfitcoefficientsraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLowerfftfitcoefficientsraw(),
							convertListofListsOfDoublesToDataset(ovdm.getyListRawIntensityErrorForEachDat()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setyListRawIntensityErrorForEachDat(
					getListofListsOfDoublesAttribute(NeXusStructureStrings.getLowerfftfitcoefficientsraw(), nxData))),

	lowerFFTFitCoefficientsFhkl(NeXusStructureStrings.getLowerfftfitcoefficientsfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getLowerfftfitcoefficientsfhkl(), nxData,
					concatenateIDatasetArrayList(ovdm.getOutputDatArray()), 0),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setOutputDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getLowerfftfitcoefficientsfhkl(), nxData))),

	lowerBaseFrequencyCorrected(NeXusStructureStrings.getLowerbasefrequencycorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getLowerbasefrequencycorrected(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getLowerbasefrequencycorrected(), nxData))),

	lowerBaseFrequencyRaw(NeXusStructureStrings.getLowerbasefrequencyraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getLowerbasefrequencyraw(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getLowerbasefrequencyraw(), nxData))),

	lowerBaseFrequencyFhkl(NeXusStructureStrings.getLowerbasefrequencyfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getLowerbasefrequencyfhkl(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getLowerbasefrequencyfhkl(), nxData))),

	attenuationFactorFFT(NeXusStructureStrings.getAttenuationfactorfft(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getAttenuationfactorfft(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getAttenuationfactorfft(), nxData))),

	attenuationFactorFhklFFT(NeXusStructureStrings.getAttenuationfactorfhklfft(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getAttenuationfactorfhklfft(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getAttenuationfactorfhklfft(), nxData))),

	attenuationFactorRawFFT(NeXusStructureStrings.getAttenuationfactorrawfft(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getAttenuationfactorrawfft(), nxData,
					concatenateIDatasetArrayList(ovdm.getBackgroundDatArray()), 1),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setBackgroundDatArray(
					getListOfDatasetsAttribute(NeXusStructureStrings.getAttenuationfactorrawfft(), nxData))),;

	private String firstName;
	private ovdmGroupNodePopulateFromOverlapDataModel fg;
	private OverlapDataModelPopulateFromGroupNode fp;

	OverlapDataModelEnum(String a, ovdmGroupNodePopulateFromOverlapDataModel fg,
			OverlapDataModelPopulateFromGroupNode fp) {

		this.firstName = a;
		this.fg = fg;
		this.fp = fp;

	}

	public String getFirstName() {
		return firstName;
	}

	public ovdmGroupNodePopulateFromOverlapDataModel getdirectoryGroupNodePopulateFromOverlapDataModelMethod() {
		return fg;

	}

	public OverlapDataModelPopulateFromGroupNode getOverlapDataModelPopulateFromGroupNode() {

		return fp;
	}

	public void OverlapDataModelPopulateFromGroupNodeMethod(GroupNode n, OverlapDataModel ovdm) {

		fp.OverlapDataModelPopulateFromGroupNode1(n, ovdm);
	}

	public void ovdmGroupNodePopulateFromOverlapDataModelMethod(GroupNode nxData, OverlapDataModel ovdm) {
		fg.ovdmGroupNodePopulateFromOverlapDataModel1(nxData, ovdm);
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

		Dataset h = DatasetFactory.ones(new int[] { in.size(), in.get(0).getSize() });

		int maxj = in.get(0).getShape()[0];

		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < maxj; j++) {

				h.set(in.get(i).getDouble(j), i, j);
			}

		}

		return h;
	}

	private static ArrayList<ArrayList<Double>> padListsToUniformLength(ArrayList<ArrayList<Double>> in) {

		ArrayList<ArrayList<Double>> out = (ArrayList<ArrayList<Double>>) in.clone();

		int longestListLength = 0;

		for (ArrayList<Double> r : out) {
			if (r.size() > longestListLength) {
				longestListLength = r.size();
			}
		}

		for (ArrayList<Double> r : out) {
			if (r.size() < longestListLength) {
				int padding = longestListLength - r.size();

				for (int j = 0; j < padding; j++) {
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
				if (a.getDouble(j) != Double.MAX_VALUE) {
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
	
	private static String getStringAttribute(String desired, GroupNode g) {
		StringDataset sd = DatasetUtils.cast(StringDataset.class, g.getAttribute(desired).getValue());
		return sd.get();
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

	public OverlapDataModelEnum getFromFirstName(String in) {

		for (OverlapDataModelEnum o : OverlapDataModelEnum.values()) {
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
	public interface ovdmGroupNodePopulateFromOverlapDataModel {
		void ovdmGroupNodePopulateFromOverlapDataModel1(GroupNode nxData, OverlapDataModel ovdm);
	}

	@FunctionalInterface
	public interface OverlapDataModelPopulateFromGroupNode {
		void OverlapDataModelPopulateFromGroupNode1(GroupNode nxData, OverlapDataModel ovdm);
	}

}
