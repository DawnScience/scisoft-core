/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dawnsci.boofcv.BoofCVImageTrackerServiceCreator;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker;
import org.eclipse.dawnsci.analysis.api.image.IImageTracker.TrackerType;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial2D;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Cuts out the region of interest and fits it with a 2D polynomial background.
 */
public class TrackingWork2 extends AbstractOperation<BoxSlicerModel, OperationData> {
	
	
	private IImageTracker tracker = null;
	private Polynomial2D g2;
	private double[] location;
	private double[] initialLocation;
	private IDataset input1;
	private RectangularROI box;
	private Dataset in1;
	private IMonitor monitor1;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.TrackingWork2";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO ;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO ;
	}
	
	@SuppressWarnings("null")
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		
		box = model.getBox();
		monitor1 = monitor;
		input1=input;
		
		in1 =null;

		int[] len = box.getIntLengths();
		int[] pt = box.getIntPoint();
		initialLocation = new double[] {(double) pt[1],(double)pt[0], (double) (pt[1] +len[1]),(double) (pt[0]),(double) pt[1],
				(double) pt[0]+len[0], (double) (pt[1]+len[1]),(double) (pt[0]+len[0])};
		
		
		model.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				in1 = BoxSlicerRodScanUtils.rOIBox(input1, monitor1, box.getIntLengths(), box.getIntPoint());
				tracker =  BoofCVImageTrackerServiceCreator.createImageTrackerService();
				int[] len = box.getIntLengths();
				int[] pt = box.getIntPoint();
				initialLocation = new double[] {(double) pt[1],(double)pt[0], (double) (pt[1] +len[1]),(double) (pt[0]),(double) pt[1],
						(double) pt[0]+len[0], (double) (pt[1]+len[1]),(double) (pt[0]+len[0])};
				try {
					tracker.initialize(input1, initialLocation, TrackerType.TLD);
				} catch (Exception e) {
					System.out.println("First tracker failed too!!");
				}
				
				System.out.println("Loop No:  " + model.getLoopNo() + "  initialLocation:  " + initialLocation[0] +" , "+ initialLocation[1] +" , "+ initialLocation[2] +" , "+ initialLocation[3]);
				model.setLoopNo(0);
				model.setTrackerCoordinates(new double[]{initialLocation[1], initialLocation[0], 
						initialLocation[5], initialLocation[0], initialLocation[1], initialLocation[2], 
						initialLocation[5], initialLocation[2]});
				model.setInput(input1);
				location =initialLocation;
				
			}
		});
		
		if (model.getInput() == null){	
			in1 = BoxSlicerRodScanUtils.rOIBox(input, monitor, box.getIntLengths(), box.getIntPoint());
			tracker =  BoofCVImageTrackerServiceCreator.createImageTrackerService();
			len = box.getIntLengths();
			pt = box.getIntPoint();
			initialLocation = new double[] {(double) pt[1],(double)pt[0], (double) (pt[1] +len[1]),(double) (pt[0]),(double) pt[1],
					(double) pt[0]+len[0], (double) (pt[1]+len[1]),(double) (pt[0]+len[0])};
			try {
				tracker.initialize(input, initialLocation, TrackerType.TLD);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			System.out.println("Loop No:  " + model.getLoopNo() + "  initialLocation:  " + initialLocation[0] +" , "+ initialLocation[1] +" , "+ initialLocation[6] +" , "+ initialLocation[7]);
			model.setLoopNo(model.getLoopNo()+1);
			model.setTrackerCoordinates(new double[]{initialLocation[1], initialLocation[0], 
					initialLocation[5], initialLocation[0], initialLocation[1], initialLocation[2], 
					initialLocation[5], initialLocation[2]});
			model.setInput(input);
			location =initialLocation;
		}
		
		
		else{
			System.out.println("In here!");
			
			
			try {
				System.out.println("First tracker line");
				//tracker = new IImageTracker
				//IImageTracker tracker1 = null;
				//tracker =  BoofCVImageTrackerServiceCreator.createImageTrackerService();
				//tracker.initialize(model.getInput(), model.getTrackerCoordinates(), TrackerType.TLD);
				//System.out.println("Now In here!");
				location = tracker.track(input);
				if (location != null){
					model.setTrackerCoordinates(location);
				}
				model.setInput(input);
				
				System.out.println("~~~~~~~~~~~~~~~~~~~success!~~~~~~~~~~~~~~~~~");
			} catch (Exception e) {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Failed to track");// TODO Auto-generated catch block
				model.setTrackerCoordinates(new double[]{initialLocation[1], initialLocation[0], 
						initialLocation[5], initialLocation[0], initialLocation[1], initialLocation[2], 
						initialLocation[5], initialLocation[2]});
				model.setInput(input);
				
			}
			
		}
		
		if (location ==null){
			location = model.getTrackerCoordinates();
		}
		
		if (location ==null){
			location = model.getTrackerCoordinates();
		}
		

		in1 = BoxSlicerRodScanUtils.rOIBox(input, monitor, box.getIntLengths(), new int[] {(int) location[0],(int) location[1]});
		

		System.out.println("Tracking Loop No:  " + model.getLoopNo() + "  Location:  " + location[0] +" , "+ location[1] +" , "+ location[2] +" , "+ location[3]);
		model.setLoopNo(model.getLoopNo()+1);
	
		
		if (g2 == null)
			g2 = new Polynomial2D(model.getFitPower());
		if ((int) Math.pow(model.getFitPower() + 1, 2) != g2.getNoOfParameters())
			g2 = new Polynomial2D(model.getFitPower());
	
		Dataset[] fittingBackground = BoxSlicerRodScanUtils.LeftRightTopBottomBoxes(input, monitor, box.getIntLengths(),
				new int[]{(int) location[0], (int) location[1]},model.getBoundaryBox());
		
		Dataset offset = DatasetFactory.ones(fittingBackground[2].getShape(), Dataset.FLOAT64);
		
		Dataset intermediateFitTest = Maths.add(offset, fittingBackground[2]);
		Dataset matrix = LinearLeastSquaresServicesForSXRD.polynomial2DLinearLeastSquaresMatrixGenerator(
				model.getFitPower(), fittingBackground[0], fittingBackground[1]);
		

		
		DoubleDataset test = (DoubleDataset)LinearAlgebra.solveSVD(matrix, intermediateFitTest);
		double[] params = test.getData();


		
		DoubleDataset in1Background = g2.getOutputValues0(params, box.getIntLengths(), model.getBoundaryBox(),
				model.getFitPower());
		
		
		IndexIterator it = in1Background.getIterator();
	
		while (it.hasNext()) {
			double v = in1Background.getElementDoubleAbs(it.index);
			if (v < 0)
				in1Background.setObjectAbs(it.index, 0);
		}
	
		Dataset pBackgroundSubtracted = Maths.subtract(in1, in1Background, null);
	
		pBackgroundSubtracted.setName("pBackgroundSubtracted");
	
		IndexIterator it1 = pBackgroundSubtracted.getIterator();
	
		while (it1.hasNext()) {
			double q = pBackgroundSubtracted.getElementDoubleAbs(it1.index);
			if (q < 0)
				pBackgroundSubtracted.setObjectAbs(it1.index, 0);
		}
		
		Dataset output = DatasetUtils.cast(pBackgroundSubtracted, Dataset.FLOAT64);
		
		output.setName("Region of Interest, polynomial background removed");
		
		return new OperationData(output);
	}

}