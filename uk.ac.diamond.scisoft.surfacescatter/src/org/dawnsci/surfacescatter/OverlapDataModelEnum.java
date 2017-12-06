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
import org.eclipse.january.dataset.StringDataset;

public enum OverlapDataModelEnum {

	lowerDatName(NeXusStructureStrings.getLowerdatname(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLowerdatname(), ovdm.getLowerDatName())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setLowerDatName(getStringAttribute(NeXusStructureStrings.getLowerdatname(), nxData))),

	lowerOverlapPositions(NeXusStructureStrings.getLoweroverlappositions(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getLoweroverlappositions(), getIntegerArrayAsDataset(ovdm.getLowerOverlapPositions()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setLowerOverlapPositions(getIntegerArrayAttribute(NeXusStructureStrings.getLoweroverlappositions(), nxData))),

	lowerOverlapScannedValues(NeXusStructureStrings.getLoweroverlapscannedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapscannedvalues(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapScannedValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapScannedValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlapscannedvalues(), nxData))),

	lowerOverlapCorrectedValues(NeXusStructureStrings.getLoweroverlapcorrectedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapcorrectedvalues(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapCorrectedValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapCorrectedValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlapcorrectedvalues(), nxData))),

	lowerOverlapRawValues(NeXusStructureStrings.getLoweroverlaprawvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlaprawvalues(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapRawValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapRawValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlaprawvalues(), nxData))),

	lowerOverlapFhklValues(NeXusStructureStrings.getLoweroverlapfhklvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfhklvalues(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapFhklValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapFhklValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlapfhklvalues(), nxData))),

	lowerOverlapFitParametersCorrected(NeXusStructureStrings.getLoweroverlapfitparameterscorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfitparameterscorrected(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapFitParametersCorrected()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapFitParametersCorrected(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlapfitparameterscorrected(), nxData))),

	lowerOverlapFitParametersRaw(NeXusStructureStrings.getLoweroverlapfitparametersraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfitparametersraw(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapFitParametersRaw()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapFitParametersRaw(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlapfitparametersraw(), nxData))),

	lowerOverlapFitParametersFhkl(NeXusStructureStrings.getLoweroverlapfitparametersfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLoweroverlapfitparametersfhkl(),
							getDoubleArrayAsDataset(ovdm.getLowerOverlapFitParametersFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerOverlapFitParametersFhkl(
					getDoubleArrayAttribute(NeXusStructureStrings.getLoweroverlapfitparametersfhkl(),
							nxData))),

	upperDatName(NeXusStructureStrings.getUpperdatname(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperdatname(), ovdm.getUpperDatName())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperDatName(
					getStringAttribute(NeXusStructureStrings.getUpperdatname(), nxData))),

	upperOverlapPositions(NeXusStructureStrings.getUpperoverlappositions(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlappositions(),
							getIntegerArrayAsDataset(ovdm.getUpperOverlapPositions()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapPositions(
					getIntegerArrayAttribute(NeXusStructureStrings.getUpperoverlappositions(), nxData))),

	upperOverlapScannedValues(NeXusStructureStrings.getUpperoverlapscannedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapscannedvalues(),
							getDoubleArrayAsDataset(ovdm.getUpperOverlapScannedValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapScannedValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getUpperoverlapscannedvalues(), nxData))),

	upperOverlapCorrectedValues(NeXusStructureStrings.getUpperoverlapcorrectedvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapcorrectedvalues(),
							getDoubleArrayAsDataset(ovdm.getUpperOverlapCorrectedValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapCorrectedValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getUpperoverlapcorrectedvalues(), nxData))),

	upperOverlapRawValues(NeXusStructureStrings.getUpperoverlaprawvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
				.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlaprawvalues(),
					getDoubleArrayAsDataset(ovdm.getUpperOverlapRawValues()))),
	(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapRawValues(
			getDoubleArrayAttribute(NeXusStructureStrings.getUpperoverlaprawvalues(), nxData))),


	upperOverlapFhklValues(NeXusStructureStrings.getUpperoverlapfhklvalues(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
			.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapfhklvalues(),
					getDoubleArrayAsDataset(ovdm.getUpperOverlapFhklValues()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapFhklValues(
					getDoubleArrayAttribute(NeXusStructureStrings.getUpperoverlapfhklvalues(), nxData))),

	upperOverlapFitParametersRaw(NeXusStructureStrings.getUpperoverlapfitparametersraw(),
			(GroupNode nxData,
					OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
							NeXusStructureStrings.getUpperoverlapfitparametersraw(), ovdm.getUpperOverlapFitParametersRaw())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapFitParametersRaw(
					getDoubleArrayAttribute(NeXusStructureStrings.getUpperoverlapfitparametersraw(), nxData))),

	upperOverlapFitParametersFhkl(NeXusStructureStrings.getUpperoverlapfitparametersfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperoverlapfitparametersfhkl(),
							getDoubleArrayAsDataset(ovdm.getUpperOverlapFitParametersFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperOverlapFitParametersFhkl(
					getDoubleArrayAttribute(NeXusStructureStrings.getUpperoverlapfitparametersfhkl(), nxData))),

	attenuationFactor(NeXusStructureStrings.getAttenuationfactor(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getAttenuationfactor(), (ovdm.getAttenuationFactor()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setAttenuationFactor(getDoubleAttribute(NeXusStructureStrings.getAttenuationfactor(), nxData))),

	attenuationFactorFhkl(NeXusStructureStrings.getAttenuationfactorfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getAttenuationfactorfhkl(), (ovdm.getAttenuationFactorFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm
					.setAttenuationFactorFhkl(getDoubleAttribute(NeXusStructureStrings.getAttenuationfactorfhkl(), nxData))),

	attenuationFactorRaw(NeXusStructureStrings.getAttenuationfactorraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getAttenuationfactorraw(), (ovdm.getAttenuationFactorRaw()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setAttenuationFactorRaw(
					getDoubleAttribute(NeXusStructureStrings.getAttenuationfactorraw(), nxData))),

	upperFFTFitCoefficientsCorrected(NeXusStructureStrings.getUpperfftfitcoefficientscorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getUpperfftfitcoefficientscorrected(),
							 getDoubleArrayOfArraysAsDataset(ovdm.getUpperFFTFitCoefficientsCorrected()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperFFTFitCoefficientsCorrected(
					getDoubleArrayofArraysAttribute(NeXusStructureStrings.getUpperfftfitcoefficientscorrected(), nxData))),

	upperFFTFitCoefficientsRaw(NeXusStructureStrings.getUpperfftfitcoefficientsraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsraw(),
							getDoubleArrayOfArraysAsDataset(ovdm.getUpperFFTFitCoefficientsRaw()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperFFTFitCoefficientsRaw(
					getDoubleArrayofArraysAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsraw(), nxData))),

	upperFFTFitCoefficientsFhkl(NeXusStructureStrings.getUpperfftfitcoefficientsfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsfhkl(),
							getDoubleArrayOfArraysAsDataset(ovdm.getUpperFFTFitCoefficientsFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperFFTFitCoefficientsFhkl(
					getDoubleArrayofArraysAttribute(NeXusStructureStrings.getUpperfftfitcoefficientsfhkl(), nxData))),

	upperBaseFrequencyCorrected(NeXusStructureStrings.getUpperbasefrequencycorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperbasefrequencycorrected(),
							(ovdm.getUpperBaseFrequencyCorrected()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperBaseFrequencyCorrected(
					getDoubleAttribute(NeXusStructureStrings.getUpperbasefrequencycorrected(), nxData))),

	upperBaseFrequencyRaw(NeXusStructureStrings.getUpperbasefrequencyraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperbasefrequencyraw(),
							(ovdm.getUpperBaseFrequencyRaw()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperBaseFrequencyRaw(
					getDoubleAttribute(NeXusStructureStrings.getUpperbasefrequencyraw(), nxData))),

	upperBaseFrequencyFhkl(NeXusStructureStrings.getUpperbasefrequencyfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getUpperbasefrequencyfhkl(),
							(ovdm.getUpperBaseFrequencyFhkl()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setUpperBaseFrequencyFhkl(
					getDoubleAttribute(NeXusStructureStrings.getUpperbasefrequencyfhkl(), nxData))),

	lowerFFTFitCoefficientsCorrected(NeXusStructureStrings.getLowerfftfitcoefficientscorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(
					TreeFactory.createAttribute(NeXusStructureStrings.getLowerfftfitcoefficientscorrected(),
							getDoubleArrayOfArraysAsDataset(ovdm.getLowerFFTFitCoefficientsCorrected()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerFFTFitCoefficientsCorrected(
					getDoubleArrayofArraysAttribute(NeXusStructureStrings.getLowerfftfitcoefficientscorrected(),
							nxData))),

	lowerFFTFitCoefficientsRaw(NeXusStructureStrings.getLowerfftfitcoefficientsraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData
					.addAttribute(TreeFactory.createAttribute(NeXusStructureStrings.getLowerfftfitcoefficientsraw(),
							getDoubleArrayOfArraysAsDataset(ovdm.getLowerFFTFitCoefficientsRaw()))),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerFFTFitCoefficientsRaw(
					getDoubleArrayofArraysAttribute(NeXusStructureStrings.getLowerfftfitcoefficientsraw(), nxData))),

	lowerFFTFitCoefficientsFhkl(NeXusStructureStrings.getLowerfftfitcoefficientsfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> addDataNodeFromDataset(
					NeXusStructureStrings.getLowerfftfitcoefficientsfhkl(), nxData,
					getDoubleArrayOfArraysAsDataset(ovdm.getLowerFFTFitCoefficientsFhkl()), 0),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerFFTFitCoefficientsFhkl(
					getDoubleArrayofArraysAttribute(NeXusStructureStrings.getLowerfftfitcoefficientsfhkl(), nxData))),

	lowerBaseFrequencyCorrected(NeXusStructureStrings.getLowerbasefrequencycorrected(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getLowerbasefrequencycorrected(), ovdm.getLowerBaseFrequencyCorrected())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerBaseFrequencyCorrected(
					getDoubleAttribute(NeXusStructureStrings.getLowerbasefrequencycorrected(), nxData))),

	lowerBaseFrequencyRaw(NeXusStructureStrings.getLowerbasefrequencyraw(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getLowerbasefrequencyraw(), ovdm.getLowerBaseFrequencyRaw())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerBaseFrequencyRaw(
					getDoubleAttribute(NeXusStructureStrings.getLowerbasefrequencyraw(), nxData))),

	lowerBaseFrequencyFhkl(NeXusStructureStrings.getLowerbasefrequencyfhkl(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getLowerbasefrequencyfhkl(), ovdm.getLowerBaseFrequencyFhkl())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setLowerBaseFrequencyFhkl(
					getDoubleAttribute(NeXusStructureStrings.getLowerbasefrequencyfhkl(), nxData))),

	attenuationFactorFFT(NeXusStructureStrings.getAttenuationfactorfft(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getAttenuationfactorfft(), ovdm.getAttenuationFactorFFT())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setAttenuationFactorFFT(
					getDoubleAttribute(NeXusStructureStrings.getAttenuationfactorfft(), nxData))),

	attenuationFactorFhklFFT(NeXusStructureStrings.getAttenuationfactorfhklfft(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getAttenuationfactorfhklfft(), ovdm.getAttenuationFactorFhklFFT())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setAttenuationFactorFhklFFT(
					getDoubleAttribute(NeXusStructureStrings.getAttenuationfactorfhklfft(), nxData))),

	attenuationFactorRawFFT(NeXusStructureStrings.getAttenuationfactorrawfft(),
			(GroupNode nxData, OverlapDataModel ovdm) -> nxData.addAttribute(TreeFactory.createAttribute(
					NeXusStructureStrings.getAttenuationfactorrawfft(),
					ovdm.getAttenuationFactorRawFFT())),
			(GroupNode nxData, OverlapDataModel ovdm) -> ovdm.setAttenuationFactorRawFFT(
					getDoubleAttribute(NeXusStructureStrings.getAttenuationfactorrawfft(), nxData)));

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

	private static double getDoubleAttribute(String desired, GroupNode g) {
		IDataset sd = g.getAttribute(desired).getValue();
		return (double) sd.getInt(0);
	}

	private static Dataset getDoubleArrayOfArraysAsDataset(double[][] in) {

		ArrayList<double[]> out = new ArrayList<>();

		for (int i = 0; i < in.length; i++) {
			out.add(in[i]);
		}

		return DatasetFactory.createFromList(out);
	}

	private static int[] getIntegerArrayAttribute(String desired, GroupNode g) {
		
		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();
		
		int[] out= new int[roiAttributeDat.getSize()];
		
		for(int i= 0; i<roiAttributeDat.getSize();i++) {
			out[i] = (int) roiAttributeDat.getDouble(i);
		}
		
		return out;
	}
	
	private static Dataset getIntegerArrayAsDataset(int[] in) {
		
		ArrayList<Integer> out1 = new ArrayList<>();
		
		for(int i= 0; i<in.length;i++) {
			out1.add(in[i]);
		}
		
		return DatasetFactory.createFromList(out1);
	}
	
	private static Dataset getDoubleArrayAsDataset(double[] in) {
		
		ArrayList<Double> out1 = new ArrayList<>();
		
		for(int i= 0; i<in.length;i++) {
			out1.add(in[i]);
		}
		
		return DatasetFactory.createFromList(out1);
	}
	
	private static String getStringAttribute(String desired, GroupNode g) {
		StringDataset sd = DatasetUtils.cast(StringDataset.class, g.getAttribute(desired).getValue());
		return sd.get();
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

	private static double[] getDoubleArrayAttribute(String desired, GroupNode g) {
		
		Attribute roiAttribute = g.getAttribute(desired);
		Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();
		
		double[] out= new double[roiAttributeDat.getSize()];
		
		for(int i= 0; i<roiAttributeDat.getSize();i++) {
			out[i] = roiAttributeDat.getDouble(i);
		}
		
		return out;
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
