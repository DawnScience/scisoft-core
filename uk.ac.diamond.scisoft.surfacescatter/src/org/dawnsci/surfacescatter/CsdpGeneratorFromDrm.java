package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

import javolution.io.Struct.Bool;

public class CsdpGeneratorFromDrm {

	private CurveStitchDataPackage csdp;

	private DirectoryModel drm;

	public CurveStitchDataPackage generateCsdpFromDrm(DirectoryModel drm) {

		this.drm = drm;

		csdp = new CurveStitchDataPackage();

		OutputCurvesDataPackage ocdp = drm.getOcdp();

		int noOfDats = drm.getDatFilepaths().length;

		ocdp.setNoOfDats(noOfDats);
		
		csdp.setFilepaths(drm.getDatFilepaths());

		ArrayList<ArrayList<Boolean>> goodPointLists = new ArrayList<>();
		ArrayList<ArrayList<Boolean>> goodPointListsForSplice = new ArrayList<>();

		for (int i = 0; i < drm.getDatFilepaths().length; i++) {
			goodPointLists.add(new ArrayList<>());
			goodPointListsForSplice.add(new ArrayList<>());

			int g = drm.getDmxList().get(i).size();

			for (int j = 0; j < g; j++) {

				if (drm.getDmxList().get(i).get(j) != -10000000000.0) {
					for (FrameModel fm : drm.getFms()) {
						if (fm.getDatNo() == i && fm.getNoInOriginalDat() == j)
							goodPointLists.get(i).add(fm.isGoodPoint());
						goodPointListsForSplice.get(i).add(fm.isGoodPoint());
					}
				} else {
					goodPointListsForSplice.get(i).add(j, null);
				}
			}
		}

		ArrayList<Boolean> splicedGoodPointArray = new ArrayList<>();

		for (FrameModel fm : drm.getFms()) {

			int datNo = fm.getDatNo();
			int noInDatFile = fm.getNoInOriginalDat();
			boolean goodPoint = fm.isGoodPoint();

			try {
				goodPointListsForSplice.get(datNo).set(noInDatFile, goodPoint);
			} catch (Exception r) {
				System.out.println("error at datNo:  " + datNo + "   noInDatFile : " + noInDatFile);
			}
			splicedGoodPointArray.add(fm.isGoodPoint());

		}

		IDataset splicedGoodPointIDataset = DatasetFactory.createFromList(splicedGoodPointArray);
		csdp.setSplicedGoodPointIDataset(splicedGoodPointIDataset);

		IDataset[] goodPointDatArray = goodPointIDatasetArrayGenerator(noOfDats, goodPointLists);

		csdp.setGoodPointIDataset(goodPointDatArray);

		IDataset[] xIDataset = iDatasetArrayGenerator(noOfDats, drm.getDmxList());

		csdp.setxIDataset(xIDataset);

		IDataset[] qIDataset = iDatasetArrayGenerator(noOfDats, drm.getDmqList());

		csdp.setqIDataset(qIDataset);

		IDataset[] yIDataset = iDatasetArrayGenerator(noOfDats, ocdp.getyListForEachDat());

		csdp.setyIDataset(yIDataset);

		IDataset[] yIDatasetError = iDatasetArrayGenerator(noOfDats, ocdp.getyListErrorForEachDat());

		csdp.setyIDatasetError(yIDatasetError);

		IDataset[] yIDatasetFhkl = iDatasetArrayGenerator(noOfDats, ocdp.getyListFhklForEachDat());

		csdp.setyIDatasetFhkl(yIDatasetFhkl);

		IDataset[] yIDatasetFhklError = iDatasetArrayGenerator(noOfDats, ocdp.getyListFhklErrorForEachDat());

		csdp.setyIDatasetFhklError(yIDatasetFhklError);

		IDataset[] yRawIDataset = iDatasetArrayGenerator(noOfDats, ocdp.getyListRawIntensityForEachDat());

		csdp.setyRawIDataset(yRawIDataset);

		IDataset[] yRawIDatasetError = iDatasetArrayGenerator(noOfDats, ocdp.getyListRawIntensityErrorForEachDat());

		csdp.setyRawIDatasetError(yRawIDatasetError);

		csdp.setCorrectionSelection(drm.getFms().get(0).getCorrectionSelection());

		if (xIDataset.length != yIDataset.length) {
			System.out.println("error in lengths, in csdp generator");
		}

		return csdp;
	}

	public CurveStitchDataPackage getCsdp() {
		return csdp;
	}

	public void setCsdp(CurveStitchDataPackage csdp) {
		this.csdp = csdp;
	}

	public static IDataset iDatasetGenerator(ArrayList<Double> input) {

		if (input == null) {
			input = new ArrayList<Double>();
		}

		ArrayList<Double> outputC = (ArrayList<Double>) input.clone();

		ArrayList<Double> zero = new ArrayList<Double>();

		zero.add(-10000000000.0);

		outputC.removeAll(zero);

		IDataset yOut = DatasetFactory.createFromList(outputC);

		return yOut;
	}

	public IDataset[] iDatasetArrayGenerator(int n, // number of Dats
			ArrayList<ArrayList<Double>> input) {

		if (input == null) {
			input = new ArrayList<ArrayList<Double>>();

			for (int r = 0; r < n; r++) {
				input.add(new ArrayList<Double>());

				for (int u = 0; u <= drm.getNoOfImagesInDatFile(r); u++) {
					input.get(r).add(-10000000000.0);
				}
			}
		}
		ArrayList<ArrayList<Double>> outputC = new ArrayList<>();

		for (ArrayList<Double> w : input) {

			ArrayList<Double> q = new ArrayList<>();

			for (Double c : w) {
				q.add(c);
			}
			outputC.add(q);
		}

		ArrayList<Double> zero = new ArrayList<Double>();

		zero.add(-10000000000.0);

		for (ArrayList<Double> t : outputC) {
			t.removeAll(zero);
		}

		int r = 0;

		for (int s = 0; s < outputC.size(); s++) {
			if (outputC.get(s) != null && outputC.get(s).size() > 0) {
				r++;
			}
		}

		IDataset[] output = new IDataset[r];

		r = 0;
		for (int u = 0; u < input.size(); u++) {
			if (outputC.get(u) != null && outputC.get(u).size() > 0) {
				IDataset yOut = DatasetFactory.createFromList(outputC.get(u));
				output[r] = yOut;
				r++;
			}
		}

		return output;
	}

	public IDataset[] goodPointIDatasetArrayGenerator(int n, // number of Dats
			ArrayList<ArrayList<Boolean>> input) {


		ArrayList<ArrayList<Boolean>> goodArrays = new ArrayList<>();
		
		for (int d= 0; d< input.size();d++) {

			ArrayList<Boolean> s = input.get(d);
			
			if (s.size() > 0) {
				goodArrays.add(s);
			}
		}

		IDataset[] output = new IDataset[goodArrays.size()];

		
		for (int r = 0; r < goodArrays.size(); r++) {

			IDataset i = DatasetFactory.createFromObject(goodArrays.get(r));
			output[r] = i;
		}

		return output;

	}

	public ArrayList<IDataset> convert(IDataset[] al) {

		ArrayList<IDataset> output = new ArrayList<>();

		for (int i = 0; i < al.length; i++) {
			output.add(al[i]);
		}

		return output;
	}
}
