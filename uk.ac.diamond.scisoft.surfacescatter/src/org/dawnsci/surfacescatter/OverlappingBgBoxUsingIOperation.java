package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;

public class OverlappingBgBoxUsingIOperation 
extends AbstractOperation<SecondConstantROIBackgroundSubtractionModel, OperationData> {

	private static Polynomial2D g2;
	private static Dataset output;
	private DoubleDataset in1Background;
	private static int DEBUG =1;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.surfacescatter.SecondConstantROIUsingIOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO ;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO ;
	}


	public OperationData process (IDataset input, IMonitor monitor) 
			throws OperationException {

		int[] len = model.getLenPt()[0];
		int[] pt = model.getLenPt()[1];

		debug("pt[0]: " + pt[0]);
		debug("pt[1]: " + pt[1]);


		g2 = new Polynomial2D(AnalaysisMethodologies.toInt(model.getFitPower()));

		Dataset in1 = BoxSlicerRodScanUtilsForDialog.rOIBox(input,len, pt);

		int[] backLen = model.getBackgroundLenPt()[0];
		int[] backPt = model.getBackgroundLenPt()[1];

		//	        if(model.getBoxOffsetLenPt() != null){

		int[] offsetLen = model.getBoxOffsetLenPt()[0];
		int[] offsetPt = model.getBoxOffsetLenPt()[1];

		int pt0 = pt[0] + offsetPt[0];
		int pt1 = pt[1] + offsetPt[1];
		backPt = new int[] {pt0, pt1}; 


		int len0 = len[0] + offsetLen[0];
		int len1 = len[1] + offsetLen[1];
		backLen = new int[] {len0, len1}; 

		//	        }

		debug("backPt[0]: " + backPt[0]);
		debug("backPt[1]: " + backPt[1]);

		BackgroundRegionArrays br = new BackgroundRegionArrays();


		for (int i = backPt[0]; i<backPt[0]+backLen[0]; i++){
			for(int j = backPt[1]; j<backPt[1]+backLen[1]; j++){
				try{
					if((i<pt[0]||i>=(pt[0]+len[0]))||(j<pt[1]||j>=(pt[1]+len[1]))){
						br.xArrayAdd(i-backPt[0]);
						br.yArrayAdd(j-backPt[1]);
						br.zArrayAdd(input.getDouble(j,i));
					}
				}
				catch(Exception u){
					System.out.println(u.getMessage());
				}
			}
		}

		Dataset xBackgroundDat = DatasetFactory.createFromObject(br.getXArray());
		Dataset yBackgroundDat = DatasetFactory.createFromObject(br.getYArray());
		Dataset zBackgroundDat = DatasetFactory.createFromObject(br.getZArray());

		Dataset xBackgroundDatEx = xBackgroundDat.clone();
		Dataset yBackgroundDatEx = yBackgroundDat.clone();
		Dataset zBackgroundDatEx = zBackgroundDat.clone();
		
		
		for(int i = 0; i<br.getXArray().size(); i++){
			
			double xi = br.getXArray().get(i);
			xBackgroundDatEx.set(xi, i);
			
			double yi = br.getYArray().get(i);
			yBackgroundDatEx.set(yi, i);
			
			double zi = br.getZArray().get(i);
			zBackgroundDatEx.set(zi, i);
			
		}
		
		
		Dataset matrix = LinearLeastSquaresServicesForDialog.polynomial2DLinearLeastSquaresMatrixGenerator(
				AnalaysisMethodologies.toInt(model.getFitPower()), xBackgroundDatEx, yBackgroundDatEx);

		DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, zBackgroundDatEx);
		double[] params = test.getData();

		in1Background = g2.getOutputValuesOverlapping(params, len, new int[] {(int) (model.getBoxOffsetLenPt()[1][0]),(int) (model.getBoxOffsetLenPt()[1][1])},
				AnalaysisMethodologies.toInt(model.getFitPower()));


//		in1Background.transpose(new int[] {1,0});

		Dataset pBackgroundSubtracted = DatasetFactory.createFromObject(0);

		try{
			pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
		}
		catch(Exception e){
			debug("error in second overlapping background subtraction IOperation");
		}

		output = DatasetUtils.cast(DoubleDataset.class, pBackgroundSubtracted);

		output.setName("Region of Interest, constant background removed");

		return new OperationData(output, 
				null, 
				null,
				in1Background,
				null);

	}

	private void debug (String output) {
		if (DEBUG == 1) {
			System.out.println(output);
		}
	}

}
