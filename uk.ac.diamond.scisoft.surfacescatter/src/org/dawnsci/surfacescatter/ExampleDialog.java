package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
//import org.dawnsci.spectrum.ui.wizard.operationJob1.MovieJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.dawnsci.plotting.api.trace.ITrace;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.TraceType;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class ExampleDialog extends Dialog {
	
	private String[] filepaths;
	private PlotSystemComposite customComposite;
	private SuperSashPlotSystem2Composite customComposite2;
	private PlotSystem1Composite customComposite1;
	private PlotSystem3Composite customComposite3;
	private MultipleOutputCurvesTable outputCurves;
	private int imageNo;
	private OutputMovie outputMovie;
	private AggregateDataset aggDat;
	private ArrayList<ExampleModel> models;
	private ArrayList<DataModel> dms;
	private ArrayList<GeometricParametersModel> gms;
	private SuperModel sm;
	private DatDisplayer datDisplayer;
	private PrintWriter writer;
	private GeometricParametersWindows paramField;
	private Shell parentShell;
	
	public ExampleDialog(Shell parentShell, String[] datFilenames) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.parentShell = parentShell;
		this.filepaths = datFilenames;
		//createDialogArea(parentShell.getParent());

	}
	
	



	@Override
	protected Control createDialogArea(Composite parent) {
		
		final Composite container = (Composite) super.createDialogArea(parent);

		GridLayout gridLayout = new GridLayout(4, true);
	    container.setLayout(gridLayout);			
	    sm = new SuperModel();
	
		
	    
		ArrayList<ILazyDataset> arrayILD = new ArrayList<ILazyDataset>();
		gms = new ArrayList<GeometricParametersModel>();
		dms = new ArrayList<DataModel>();
		models = new ArrayList<ExampleModel>();
		sm.setFilepaths(filepaths);
		
		for (int id = 0; id<filepaths.length; id++){ 
			try {
				models.add(new ExampleModel());
				dms.add(new DataModel());
				gms.add(new GeometricParametersModel());
				IDataHolder dh1 =LoaderFactory.getData(filepaths[id]);
				ILazyDataset ild =dh1.getLazyDataset(gms.get(sm.getSelection()).getImageName());
				dms.get(id).setName(StringUtils.substringAfterLast(sm.getFilepaths()[id], File.separator));
				models.get(id).setDatImages(ild);
				models.get(id).setFilepath(filepaths[id]);
				
				if (sm.getCorrectionSelection() == 0){
					ILazyDataset ildx =dh1.getLazyDataset(gms.get(sm.getSelection()).getxName()); 
					models.get(id).setDatX(ildx);
				}
				else if (sm.getCorrectionSelection() == 1){
					ILazyDataset ildx =dh1.getLazyDataset(gms.get(sm.getSelection()).getxNameRef()); 
					models.get(id).setDatX(ildx);
					
					ILazyDataset dcdtheta = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getdcdtheta());
					models.get(id).setDcdtheta(dcdtheta);
					
					ILazyDataset qdcd = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getqdcd());
					models.get(id).setQdcd(qdcd);
						
						
					if (dcdtheta == null){
						try{
					    	dcdtheta = dh1.getLazyDataset(ReflectivityMetadataTitlesForDialog.getsdcdtheta());
					    	models.get(id).setDcdtheta(dcdtheta);
						} catch (Exception e2){
							System.out.println("can't get dcdtheta");
						}
					}
					else{
					}
					
					
				}
				else{
				}	
			} 
			
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		for (GeometricParametersModel gm : gms){
		
			gm.addPropertyChangeListener(new PropertyChangeListener(){

				public void propertyChange(PropertyChangeEvent evt) {
					for (int id = 0; id<filepaths.length; id++){ 
						try {
							IDataHolder dh1 =LoaderFactory.getData(filepaths[id]);
							ILazyDataset ild =dh1.getLazyDataset(gms.get(sm.getSelection()).getImageName()); 
							models.get(id).setDatImages(ild);
						} 
						
						catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}


///////////////////////////Window 1////////////////////////////////////////////////////
		try {
			
			datDisplayer = new DatDisplayer(container, SWT.NONE, sm, filepaths);
			datDisplayer.setLayout(new GridLayout());
			datDisplayer.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
			
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
///////////////////////////Window 2////////////////////////////////////////////////////
		
	    customComposite = new PlotSystemComposite(container, SWT.NONE, models, sm,
	    		PlotSystemCompositeDataSetter.imageSetter(models.get(sm.getSelection()), 0));
					
	    customComposite.setLayout(new GridLayout());
	    customComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    

///////////////////////////Window 3////////////////////////////////////////////////////	    
	    
	    customComposite1 = new PlotSystem1Composite(container, 
	    		SWT.NONE, models, dms, sm, gms.get(sm.getSelection()), 
	    		customComposite, 0);
	    customComposite1.setLayout(new GridLayout());
	    customComposite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    
///////////////////////////Window 4////////////////////////////////////////////////////
	    try {
			outputCurves = new MultipleOutputCurvesTable(container, SWT.NONE, models, dms, sm);
			outputCurves.setLayout(new GridLayout());
			outputCurves.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
///////////////////////////Window 5////////////////////////////////////////////////////

	    try {
			paramField = new GeometricParametersWindows(container, SWT.NONE, gms, sm);
			paramField.setLayout(new GridLayout());
			paramField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
///////////////////////////Window 6////////////////////////////////////////////////////
		try {
			customComposite2 = new SuperSashPlotSystem2Composite(container, SWT.NONE);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IDataset k = PlotSystem2DataSetter.PlotSystem2DataSetter1(models.get(sm.getSelection()));
		customComposite2.setData(k);
		models.get(sm.getSelection()).setCurrentImage(k);
	    customComposite2.setLayout(new GridLayout());
	    customComposite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
///////////////////////////Window 7////////////////////////////////////////////////////
	    
	    
		try {
			customComposite3 = new PlotSystem3Composite(container, SWT.NONE, 
					aggDat,models.get(sm.getSelection()), dms.get(sm.getSelection()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    customComposite3.setLayout(new GridLayout());
	    customComposite3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    
	    
///////////////////////////Window 8////////////////////////////////////////////////////
	    
	    try {
			outputMovie = new OutputMovie(container, SWT.NONE, dms.get(sm.getSelection()));
			outputMovie.setLayout(new GridLayout());
			outputMovie.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
///////////////////////////////////////////////////////////////////////////////////	    
///////////////////////Update Methods/////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////	    
	    for (ExampleModel m : models){
	    
	    	m.addPropertyChangeListener(new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					IDataset j = PlotSystem2DataSetter.PlotSystem2DataSetter1(m);
					customComposite2.setImage(j);
					int it = (int) m.getIterationMarker();
					customComposite3.updateAll(m, dms.get(it), j);
					
					customComposite2.getPlotSystem1().clear();
					customComposite2.getPlotSystem1().addTrace(VerticalHorizontalSlices.horizontalslice(customComposite2));
					customComposite2.getPlotSystem1().repaint();
					customComposite2.getPlotSystem1().autoscaleAxes();
					
					customComposite2.getPlotSystem3().clear();
					customComposite2.getPlotSystem3().addTrace(VerticalHorizontalSlices.verticalslice(customComposite2));
					customComposite2.getPlotSystem3().repaint();
					customComposite2.getPlotSystem3().autoscaleAxes();
					
				}
		    	
		    });
	    }
//////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////TRACKING////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
	    customComposite1.getRunButton().addSelectionListener(new SelectionListener(){
	    	
	    	
			@Override
			public void widgetSelected(SelectionEvent e) {

				try{
					outputCurves.resetCurve();
					dms.get(sm.getSelection()).resetAll();
					models.get(sm.getSelection()).setInput(null);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			        
		            int selection = models.get(sm.getSelection()).getSliderPos();
		            System.out.println("Slider position in reset:  " + selection);
		            SliceND slice = new SliceND(models.get(sm.getSelection()).getDatImages().getShape());
		            slice.setSlice(0, selection, selection+1, 1);
					IDataset i = null;
					try {
						i = models.get(sm.getSelection()).getDatImages().getSlice(slice);
					} catch (DatasetException e1) {
							// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					i.squeeze();
					customComposite.getBoxPosition();
	            	IROI region = models.get(sm.getSelection()).getROI();
	             	IRectangularROI currentBox = region.getBounds();
	             	int[] currentLen = currentBox.getIntLengths();
	             	int[] currentPt = currentBox.getIntPoint();
	             	int[][] currentLenPt = {currentLen, currentPt};
			        double[] currentTrackerPos = new double[] {(double) currentPt[1],(double)currentPt[0], (double) (currentPt[1] +currentLen[1]),(double) (currentPt[0]),(double) currentPt[1],
					(double) currentPt[0]+currentLen[0], (double) (currentPt[1]+currentLen[1]),(double) (currentPt[0]+currentLen[0])};
			             	
			        int[] ab =customComposite1.getMethodology();
			        models.get(sm.getSelection()).setMethodology((Methodology.values()[ab[0]]));
			       	models.get(sm.getSelection()).setFitPower(FitPower.values()[ab[1]]);
			       	models.get(sm.getSelection()).setBoundaryBox(ab[2]);
							
			             	
	             	models.get(sm.getSelection()).setTrackerCoordinates(new double[] {currentTrackerPos[1], currentTrackerPos[0]});
	             	models.get(sm.getSelection()).setLenPt(currentLenPt);
			             	
	             	IDataset j = DummyProcessingClass.DummyProcess(sm, i, models.get(sm.getSelection()),
	             			dms.get(sm.getSelection()), 
	             			gms.get(sm.getSelection()), customComposite, 
	             			paramField.getTabFolder().getSelectionIndex(), 
	             			customComposite.getSliderPos(), 0);
			             	
	             	customComposite1.getPlotSystem().createPlot2D(j, null, null);
	             	dms.get(sm.getSelection()).resetAll();
				
				
				dms.get(sm.getSelection()).resetAll();
				operationJob1 oJ = new operationJob1();
				oJ.setCustomComposite(customComposite);
				oJ.setCustomComposite1(customComposite1);
				oJ.setCorrectionSelection(paramField.getTabFolder().getSelectionIndex());
				oJ.setOutputCurves(outputCurves);
				oJ.setSuperModel(sm);
				oJ.setDm(dms.get(sm.getSelection()));
				oJ.setModel(models.get(sm.getSelection()));
				oJ.setGeoModel(gms.get(sm.getSelection()));;
				oJ.setPlotSystem(customComposite1.getPlotSystem());
				oJ.schedule();	
//				oJ.run(null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	   });
////////////////////////////////////////////////////////////////////////////////
	    outputCurves.getRegionNo().addROIListener(new IROIListener() {
			
			@Override
			public void roiSelected(ROIEvent evt) {
				roiStandard(evt);
				
			}
			
			@Override
			public void roiDragged(ROIEvent evt) {
				roiStandard(evt);
				
			}
			
			@Override
			public void roiChanged(ROIEvent evt) {
				roiStandard(evt);
			}
			
			public void roiStandard(ROIEvent evt){	
				imageNo = ClosestNoFinder.closestNoPos(outputCurves.getRegionNo().getROI().getPointX(), dms.get(sm.getSelection()).getxList());
				models.get(sm.getSelection()).setOutputNo(imageNo);
//				System.out.println("ImageNo: " + models.get(sm.getSelection()).getOutputNo());
			}
			
		});
////////////////////////////////////////////////////////////////////////////////
	    ////////THE RESET///////////////////
//	    customComposite1.getButton2().addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				try{
//				outputCurves.resetCurve();
//				dms.get(sm.getSelection()).resetAll();
//				models.get(sm.getSelection()).setInput(null);
//			} catch (Exception e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//
//		        
//	            int selection = models.get(sm.getSelection()).getSliderPos();
//	            System.out.println("Slider position in reset:  " + selection);
//	            SliceND slice = new SliceND(models.get(sm.getSelection()).getDatImages().getShape());
//	            slice.setSlice(0, selection, selection+1, 1);
//				IDataset i = null;
//				try {
//					i = models.get(sm.getSelection()).getDatImages().getSlice(slice);
//				} catch (DatasetException e1) {
//						// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				i.squeeze();
//				customComposite.getBoxPosition();
//            	IROI region = models.get(sm.getSelection()).getROI();
//             	IRectangularROI currentBox = region.getBounds();
//             	int[] currentLen = currentBox.getIntLengths();
//             	int[] currentPt = currentBox.getIntPoint();
//             	int[][] currentLenPt = {currentLen, currentPt};
//		        double[] currentTrackerPos = new double[] {(double) currentPt[1],(double)currentPt[0], (double) (currentPt[1] +currentLen[1]),(double) (currentPt[0]),(double) currentPt[1],
//				(double) currentPt[0]+currentLen[0], (double) (currentPt[1]+currentLen[1]),(double) (currentPt[0]+currentLen[0])};
//		             	
//		        int[] ab =customComposite1.getMethodology();
//		        models.get(sm.getSelection()).setMethodology((Methodology.values()[ab[0]]));
//		       	models.get(sm.getSelection()).setFitPower(FitPower.values()[ab[1]]);
//		       	models.get(sm.getSelection()).setBoundaryBox(ab[2]);
//						
//		             	
//             	models.get(sm.getSelection()).setTrackerCoordinates(new double[] {currentTrackerPos[1], currentTrackerPos[0]});
//             	models.get(sm.getSelection()).setLenPt(currentLenPt);
//		             	
//             	IDataset j = DummyProcessingClass.DummyProcess(sm, i, models.get(sm.getSelection()),
//             			dms.get(sm.getSelection()), 
//             			gms.get(sm.getSelection()), customComposite, 
//             			paramField.getTabFolder().getSelectionIndex(), 
//             			customComposite.getSliderPos(), 0);
//		             	
//             	customComposite1.getPlotSystem().createPlot2D(j, null, null);
//             	dms.get(sm.getSelection()).resetAll();
//		        
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	    
/////////////////////////////Take Output Marker///////////////////////////////////////////////////
/////////////////////////////////Keywords: display, selected image, output curve to output movie, get image///////////////////////////
	   //////////////////////////////////////////////
	    
	    outputMovie.getOutputControl().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(outputCurves.getRegionNo()!=null){
					outputMovie.getPlotSystem().updatePlot2D(dms.get(sm.getSelection()).getOutputDatArray().get(ClosestNoFinder.closestNoPos(outputCurves.getRegionNo().getROI().getPointX(),
							dms.get(sm.getSelection()).getxList())), null,null);
					System.out.println("DatArray size:  " + dms.get(sm.getSelection()).getOutputDatArray().size());
				}
				else{
					IRegion pr = null;
					try {
						pr = outputCurves.getPlotSystem().createRegion("Image", RegionType.XAXIS_LINE);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					outputMovie.getPlotSystem().updatePlot2D(
							dms.get(sm.getSelection()).getOutputDatArray().get(ClosestNoFinder.closestNoPos(pr.getROI().getPointX(),
							dms.get(sm.getSelection()).getxList())), null,null);
					System.out.println("DatArray size:  " + dms.get(sm.getSelection()).getOutputDatArray().size());
					
					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
//////////////////////////////////////////////////////////////////////////////////////

	    outputMovie.getPlayButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				MovieJob movieJob = new MovieJob();
				movieJob.setOutputMovie(outputMovie);
				movieJob.setData(dms.get(sm.getSelection()).getOutputDatArray());
				movieJob.setTime(Integer.parseInt(outputMovie.getTimeConstant().getText()));
				if(movieJob.getState() == Job.RUNNING) {
					movieJob.cancel();
				}
				movieJob.schedule();
					
			}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
	        });
	    
//////////////////////////////////////Output Curves marker/////////////////////////////
//////////////////////////////Keywords: Marker, line region, image finder//////////////////
//////////////////////////////////////////////////////////////////////////
	    outputCurves.getRegionNo().addROIListener(new IROIListener() {
			
			@Override
			public void roiSelected(ROIEvent evt) {
				// TODO Auto-generated method stub
				roiStandard1(evt);
			}
			
			@Override
			public void roiDragged(ROIEvent evt) {
				// TODO Auto-generated method stub
				roiStandard1(evt);
			}
			
			@Override
			public void roiChanged(ROIEvent evt) {
				roiStandard1(evt);
				
			}
			
			public void roiStandard1(ROIEvent evt){	
				
				imageNo = ClosestNoFinder.closestNoPos(outputCurves.getRegionNo().getROI().getPointX(), dms.get(sm.getSelection()).getxList());
				
				if (outputMovie.getOutputControl().getSelection() == true){
				
					models.get(sm.getSelection()).setOutputNo(imageNo);
//					System.out.println("ImageNo: " + models.get(sm.getSelection()).getOutputNo());
					
					operationJob2 oJ2 = new operationJob2();
					
					oJ2.setDm(dms.get(sm.getSelection()));
					oJ2.setModel(models.get(sm.getSelection()));
				
					oJ2.setImageNo(imageNo);
					
					models.get(sm.getSelection()).setOutputNo(imageNo);
//					System.out.println("ImageNo: " + models.get(sm.getSelection()).getOutputNo());
					oJ2.setOutputCurves(outputCurves);
					oJ2.setDm(dms.get(sm.getSelection()));
					oJ2.setModel(models.get(sm.getSelection()));
					oJ2.setOutputMovie(outputMovie);
					oJ2.setPlotSystem(outputMovie.getPlotSystem());
					
					oJ2.schedule();
				}
				if (customComposite.getOutputControl().getSelection() == true){
					
					operationJob3 oJ3 = new operationJob3();
					
					oJ3.setDm(dms.get(sm.getSelection()));
					oJ3.setModel(models.get(sm.getSelection()));
				
					oJ3.setImageNo(imageNo);
					oJ3.setPlotSystemComposite(customComposite);
					
					oJ3.schedule();
					
					
				}
				}
	    });
		
/////////////////////////////////////////////////////////////////////
/////////////////////////Resized Green ROI///////////////////////// 	    
///////////////////////////////////////////////////////////////////
	    
	    customComposite.getGreenRegion().addROIListener(new IROIListener() {
			
			@Override
			public void roiSelected(ROIEvent evt) {
				roiModStandard(evt);
				
			}
			
			@Override
			public void roiDragged(ROIEvent evt) {
				roiModStandard(evt);
			}
			
			@Override
			public void roiChanged(ROIEvent evt) {
				roiModStandard(evt);
			}
			
			public void roiModStandard(ROIEvent evt) {
				
				customComposite2.updateImage(PlotSystem2DataSetter.PlotSystem2DataSetter1(models.get(sm.getSelection())));
				
				customComposite2.getPlotSystem1().clear();
				customComposite2.getPlotSystem1().addTrace(VerticalHorizontalSlices.horizontalslice(customComposite2));
				customComposite2.getPlotSystem1().repaint();
				customComposite2.getPlotSystem1().autoscaleAxes();
				
				customComposite2.getPlotSystem3().clear();
				customComposite2.getPlotSystem3().addTrace(VerticalHorizontalSlices.verticalslice(customComposite2));
				customComposite2.getPlotSystem3().repaint();
				customComposite2.getPlotSystem3().autoscaleAxes();
				
				
				if (customComposite2.getBackgroundButton().getSelection()){
					
					
						SliceND slice = new SliceND(models.get(sm.getSelection()).getDatImages().getShape());
						int sel = models.get(sm.getSelection()).getImageNumber();
						slice.setSlice(0, sel, sel+1, 1);
						IDataset j = null;
						try {
							j = models.get(sm.getSelection()).getDatImages().getSlice(slice);
						} catch (Exception e1){
							e1.printStackTrace();
						}
						
						j.squeeze();
						
						IDataset background = DummyProcessingClass.DummyProcess(sm, j, models.get(sm.getSelection()),
								dms.get(sm.getSelection()), gms.get(sm.getSelection()), 
								customComposite, paramField.getTabFolder().getSelectionIndex(), sel, 0);
						dms.get(sm.getSelection()).setSlicerBackground(background);
						
						ILineTrace lt1 = VerticalHorizontalSlices.horizontalslice(customComposite2);
						ILineTrace lt2 = VerticalHorizontalSlices.horizontalsliceBackgroundSubtracted(customComposite2, background);
						
						IDataset backSubTop = Maths.subtract(lt1.getYData(), lt2.getYData());
						IDataset xTop = lt1.getXData();
						ILineTrace lt12BackSub = customComposite2.getPlotSystem1().createLineTrace("background slice");
						lt12BackSub.setData( xTop, backSubTop);
						
						
						ILineTrace lt3 = VerticalHorizontalSlices.verticalslice(customComposite2);
						ILineTrace lt4 = VerticalHorizontalSlices.verticalsliceBackgroundSubtracted(customComposite2,background);
						
						IDataset backSubSide = Maths.subtract(lt3.getYData(), lt4.getYData());
						IDataset xSide = lt3.getYData();
						ILineTrace lt34BackSub = customComposite2.getPlotSystem3().createLineTrace("background slice");
						lt34BackSub.setData(backSubSide,xSide);
						
						customComposite2.getPlotSystem1().clear();
						customComposite2.getPlotSystem1().addTrace(lt1);
						customComposite2.getPlotSystem1().addTrace(lt2);
						customComposite2.getPlotSystem1().addTrace(lt12BackSub);
						customComposite2.getPlotSystem1().repaint();
						customComposite2.getPlotSystem1().autoscaleAxes();
						
						customComposite2.getPlotSystem3().clear();
						customComposite2.getPlotSystem3().addTrace(lt3);
						customComposite2.getPlotSystem3().addTrace(lt4);
						customComposite2.getPlotSystem3().addTrace(lt34BackSub);
						customComposite2.getPlotSystem3().repaint();
						customComposite2.getPlotSystem3().autoscaleAxes();
					}
			}
		});
		    
	    
	    
//////////////////////////////////////////////////////////////////////////////////
	    /////////////////Slider///////////////////////////
////////////////////////////////////////////////////
	    customComposite.getSlider().addSelectionListener(new SelectionListener() {
	    	
		public void widgetSelected(SelectionEvent e) {
			
			int selection = customComposite.getSlider().getSelection();
			models.get(sm.getSelection()).setSliderPos(selection);
			if (customComposite.getOutputControl().getSelection() == false){
				IDataset jk = PlotSystemCompositeDataSetter.imageSetter(models.get(sm.getSelection()), selection);
				customComposite.updateImage(jk);
				
				customComposite2.updateImage(PlotSystem2DataSetter.PlotSystem2DataSetter1(models.get(sm.getSelection())));
				
				customComposite2.getPlotSystem1().clear();
				customComposite2.getPlotSystem1().addTrace(VerticalHorizontalSlices.horizontalslice(customComposite2));
				customComposite2.getPlotSystem1().repaint();
				customComposite2.getPlotSystem1().autoscaleAxes();
				
				customComposite2.getPlotSystem3().clear();
				customComposite2.getPlotSystem3().addTrace(VerticalHorizontalSlices.verticalslice(customComposite2));
				customComposite2.getPlotSystem3().repaint();
				customComposite2.getPlotSystem3().autoscaleAxes();
				
				
				if (customComposite2.getBackgroundButton().getSelection()){
					
					
						SliceND slice = new SliceND(models.get(sm.getSelection()).getDatImages().getShape());
						int sel = models.get(sm.getSelection()).getImageNumber();
						slice.setSlice(0, sel, sel+1, 1);
						IDataset j = null;
						try {
							j = models.get(sm.getSelection()).getDatImages().getSlice(slice);
						} catch (Exception e1){
							e1.printStackTrace();
						}
						
						j.squeeze();
						
						IDataset background = DummyProcessingClass.DummyProcess(sm, j, models.get(sm.getSelection()),
								dms.get(sm.getSelection()), gms.get(sm.getSelection()), 
								customComposite, paramField.getTabFolder().getSelectionIndex(), sel, 0);
						dms.get(sm.getSelection()).setSlicerBackground(background);
						
						ILineTrace lt1 = VerticalHorizontalSlices.horizontalslice(customComposite2);
						ILineTrace lt2 = VerticalHorizontalSlices.horizontalsliceBackgroundSubtracted(customComposite2, background);
						
						IDataset backSubTop = Maths.subtract(lt1.getYData(), lt2.getYData());
						IDataset xTop = lt1.getXData();
						ILineTrace lt12BackSub = customComposite2.getPlotSystem1().createLineTrace("background slice");
						lt12BackSub.setData( xTop, backSubTop);
						
						
						ILineTrace lt3 = VerticalHorizontalSlices.verticalslice(customComposite2);
						ILineTrace lt4 = VerticalHorizontalSlices.verticalsliceBackgroundSubtracted(customComposite2,background);
						
						IDataset backSubSide = Maths.subtract(lt3.getYData(), lt4.getYData());
						IDataset xSide = lt3.getYData();
						ILineTrace lt34BackSub = customComposite2.getPlotSystem3().createLineTrace("background slice");
						lt34BackSub.setData(backSubSide,xSide);
						
						customComposite2.getPlotSystem1().clear();
						customComposite2.getPlotSystem1().addTrace(lt1);
						customComposite2.getPlotSystem1().addTrace(lt2);
						customComposite2.getPlotSystem1().addTrace(lt12BackSub);
						customComposite2.getPlotSystem1().repaint();
						customComposite2.getPlotSystem1().autoscaleAxes();
						
						customComposite2.getPlotSystem3().clear();
						customComposite2.getPlotSystem3().addTrace(lt3);
						customComposite2.getPlotSystem3().addTrace(lt4);
						customComposite2.getPlotSystem3().addTrace(lt34BackSub);
						customComposite2.getPlotSystem3().repaint();
						customComposite2.getPlotSystem3().autoscaleAxes();
				}
				}
			}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
			});
		

///////////////////////////////////////////////////////////////////////////////////	    
	    
	    sm.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
//				System.out.println("I got fired off 2 ");
				customComposite.setModels(models.get(sm.getSelection()));

				IDataset jl = PlotSystemCompositeDataSetter.imageSetter(models.get(sm.getSelection()), 0);
				customComposite.sliderReset(models.get(sm.getSelection()));
				customComposite.updateImage(jl);
				customComposite2.updateImage(PlotSystem2DataSetter.PlotSystem2DataSetter1(models.get(sm.getSelection())));
				
				customComposite2.getPlotSystem1().clear();
				customComposite2.getPlotSystem1().addTrace(VerticalHorizontalSlices.horizontalslice(customComposite2));
				customComposite2.getPlotSystem1().repaint();
				customComposite2.getPlotSystem1().autoscaleAxes();
				
				customComposite2.getPlotSystem3().clear();
				customComposite2.getPlotSystem3().addTrace(VerticalHorizontalSlices.verticalslice(customComposite2));
				customComposite2.getPlotSystem3().repaint();
				customComposite2.getPlotSystem3().autoscaleAxes();

			}
		});
	    
///////////////////////////////////////////////////////////////////////////////////
//////////////////////////////Add curves to output curves////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
	    
	    
		CharSequence f = "Fhkl";
		CharSequence i = "Intensity";
	    
		outputCurves.getDatTable().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				int det = 0;
	    		
	    		for(ITrace tr : outputCurves.getPlotSystem().getTraces()){
	    			if (tr.getName().contains(f)){
	    				det +=1;
	    			}
	    			else{
	    				det-=1;
	    			}
	    		}
				
				outputCurves.getPlotSystem().clear();
				
				ArrayList<TableItem> items = new ArrayList<>();
				
				for (int ni = 0; ni<outputCurves.getDatTable().getItemCount(); ni++){
					
					TableItem no = outputCurves.getDatTable().getItem(ni);
					
					if (no.getChecked()){
						items.add(no);
					}
				}
				
				for(TableItem b :items){
					
						ILineTrace lte = outputCurves.getPlotSystem().createLineTrace(b.getText());

						int p = (Arrays.asList(datDisplayer.getList().getItems())).indexOf(b.getText());
						
						if (dms.get(p).getyList() == null || dms.get(p).getxList() == null) {
							
							IDataset filler  = dms.get(0).backupDataset();
							lte.setData(filler, filler);
						} else {
								if (det>=0){
									lte.setData(dms.get(p).xIDataset(),dms.get(p).yIDatasetFhkl());
									lte.setName(b.getText() + "_Fhkl");
								}
								else{
									lte.setData(dms.get(p).xIDataset(),dms.get(p).yIDataset());
									lte.setName(b.getText() + "_Intensity");
								}
						}
						
						outputCurves.getPlotSystem().addTrace(lte);
						outputCurves.getPlotSystem().autoscaleAxes();
					}
				
				
				
				try{
				     if (outputCurves.getSc().getSelection() == true){
				    	 if (CurveStateIdentifier.CurveStateIdentifier1(outputCurves.getPlotSystem()) == "f"){
				    		 ILineTrace sct = outputCurves.getPlotSystem().createLineTrace("Spliced Curve_Fhkl");
					    	 System.out.println("sct name:  " + sct.getName());
								sct.setData(sm.getSplicedCurveX(), sm.getSplicedCurveYFhkl());
								outputCurves.getPlotSystem().addTrace(sct);
				    	 } else{
				    		 ILineTrace sct = outputCurves.getPlotSystem().createLineTrace("Spliced Curve_Intensity");
				    		 System.out.println("sct name:  " + sct.getName());
							 sct.setData(sm.getSplicedCurveX(), sm.getSplicedCurveY());
							 outputCurves.getPlotSystem().addTrace(sct);
				    	 }
				     }
				    	 
				     else{
				    	 
				    	 CharSequence chsq = "Spliced Curve";
				     
				    	 for(ITrace tr : outputCurves.getPlotSystem().getTraces()){
								if (tr.getName().contains(chsq)){
									outputCurves.getPlotSystem().removeTrace(tr);
								}
								else{
									
								}
				    	 }
				     }
				}
				catch (Exception e1){
					e1.printStackTrace();
				}
				
				outputCurves.getPlotSystem().autoscaleAxes();
			
			}
			
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
	    
	    	
				
	    
/////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////Overlap setup////////////////////////////////////////
//////////////////////////////////Keywords:  spliced, splice, overlap
///////////////////////////////////curves
	    
	    
	    outputCurves.getOverlap().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
								
				ArrayList<TableItem> items = new ArrayList<>();
				
				for (int ni = 0; ni<outputCurves.getDatTable().getItemCount(); ni++){
					
					TableItem no = outputCurves.getDatTable().getItem(ni);
					
					if (no.getChecked()){
						items.add(no);
					}
				}
				
				TableItem[] selectedTableItems = new TableItem[items.size()];
				
				for (int na = 0; na<items.size(); na++){
					
					selectedTableItems[na] = items.get(na);
				}
				
				StitchedOutputWithErrors.curveStitch(outputCurves.getPlotSystem(),
						selectedTableItems,
						dms, sm, datDisplayer);
				
				outputCurves.addToDatSelector();
					
				String bi =  CurveStateIdentifier.CurveStateIdentifier1(outputCurves.getPlotSystem());
				
				CharSequence chsq = "Spliced Curve";
			     
		    	 for(ITrace ab : outputCurves.getPlotSystem().getTraces()){
						
		    		 if (ab.getName().contains(chsq)){
							outputCurves.getPlotSystem().removeTrace(ab);
					}
					else{
					}
		    	 }
				
				if (bi =="f"){
					ILineTrace sct = outputCurves.getPlotSystem().createLineTrace("Spliced Curve_Fhkl");
					sct.setData(sm.getSplicedCurveX(), sm.getSplicedCurveYFhkl());
					outputCurves.getPlotSystem().addTrace(sct);
					outputCurves.getPlotSystem().autoscaleAxes();
					}
				else if(bi =="i"){
					ILineTrace sct = outputCurves.getPlotSystem().createLineTrace("Spliced Curve_Intensity");
					sct.setData(sm.getSplicedCurveX(), sm.getSplicedCurveY());
					outputCurves.getPlotSystem().addTrace(sct);
					outputCurves.getPlotSystem().autoscaleAxes();
					}
				else{
					outputCurves.getPlotSystem().clear();
				}
				outputCurves.getPlotSystem().autoscaleAxes();
				}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

///////////////////////////Save Spliced Curve GenX//////////////////////////
/////////////////////////Keywords: save, output, GenX file, output curve, spliced curve
	    
	    outputCurves.getSave().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd = new FileDialog(getParentShell(), SWT.OPEN); 
				
				String stitle = "r";
				String path = "p";
				
				if (fd.open() != null) {
					stitle = fd.getFileName();
					path = fd.getFilterPath();
					System.out.println(path);
					System.out.println(stitle);
				
				}
				
				String title = path + File.separator + stitle;
				
	
				String[] dmnames= new String [dms.size()];
				
				for (int v = 0; v<dms.size(); v++){
					dmnames[v] = dms.get(v).getName();
				}
				
				String fr = CurveStateIdentifier.CurveStateIdentifier1(outputCurves.getPlotSystem());
				
				IDataset outputDatY = DatasetFactory.ones(new int[] {1});
				
				String s = gms.get(sm.getSelection()).getSavePath();

				
				try {
					File file = new File(title);
					//file.getParentFile().mkdirs(); 
					file.createNewFile();
					writer = new PrintWriter(file);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
			    Date now = new Date();
			    String strDate = sdfDate.format(now);
			    
				writer.println("# Test file created: " + strDate);
				writer.println("# Headers: ");
				writer.println("#h	k	l	I	Ie");
				
				ILazyDataset h = SXRDGeometricCorrections.geth(models.get(sm.getSelection()));
				ILazyDataset k = SXRDGeometricCorrections.getk(models.get(sm.getSelection()));
				ILazyDataset l = SXRDGeometricCorrections.getl(models.get(sm.getSelection()));
				
				for (int tn = 0 ; tn< dmnames.length; tn++){
					if (outputCurves.getPlotSystem().getTraces().iterator().next().getName().contains(dmnames[tn])){
						IDataset outputDatX = dms.get(tn).xIDataset();
						if (fr == "f"){
							 outputDatY = dms.get(tn).yIDatasetFhkl();
						}
						else{
							outputDatY = dms.get(tn).yIDataset();
						}
					}
				}
				
				SliceND sliced = new SliceND(h.getShape());
				
				try {
					IDataset hSliced = h.getSlice(sliced);
					IDataset kSliced = k.getSlice(sliced);
					IDataset lSliced = l.getSlice(sliced);
				
					for(int gh = 0 ; gh< outputCurves.getPlotSystem().getTraces().iterator().next().getData().getSize(); gh++){
					writer.println(hSliced.getDouble(gh) +"	"+kSliced.getDouble(gh) +"	"+lSliced.getDouble(gh) + 
							"	"+ outputDatY.getDouble(gh)+ "	"+ Math.pow(outputDatY.getDouble(gh),0.5));
					}
				}
				catch (DatasetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				writer.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	 
////////////////////////////////////////////////////////////////////////////////////
///////////////////////Background slice viewer//////////////////////////////////////
/////////////////Keywords: cross hairs viewer, image examiner/////////////////////////////
////////////////////////////////////////////////////////////////////////
	    
	    customComposite2.getRegions()[0].addROIListener(new IROIListener(){
	    		@Override
				public void roiSelected(ROIEvent evt) {
					// TODO Auto-generated method stub
					roiStandard1(evt);
				}
				
				@Override
				public void roiDragged(ROIEvent evt) {
					// TODO Auto-generated method stub
					roiStandard1(evt);
				}
				
				@Override
				public void roiChanged(ROIEvent evt) {
					roiStandard1(evt);
					
				}
				
				public void roiStandard1(ROIEvent evt){	
					
					ILineTrace lt1 = VerticalHorizontalSlices.horizontalslice(customComposite2);
					

					customComposite2.getPlotSystem1().clear();
					customComposite2.getPlotSystem1().addTrace(lt1);
					customComposite2.getPlotSystem1().repaint();
					customComposite2.getPlotSystem1().autoscaleAxes();
				 }
	    	
	    });
///////////////////////////////////////////////////////////////////////////////
	    customComposite2.getRegions()[1].addROIListener(new IROIListener(){
    		
	    	
	    	@Override
			public void roiSelected(ROIEvent evt) {
				// TODO Auto-generated method stub
				roiStandard2(evt);
			}
			
			@Override
			public void roiDragged(ROIEvent evt) {
				// TODO Auto-generated method stub
				roiStandard2(evt);
			}
			
			@Override
			public void roiChanged(ROIEvent evt) {
				roiStandard2(evt);
				
			}
			
			public void roiStandard2(ROIEvent evt){	
				
				ILineTrace lt3 = VerticalHorizontalSlices.verticalslice(customComposite2);
				

				
				customComposite2.getPlotSystem3().clear();
				customComposite2.getPlotSystem3().addTrace(lt3);
				customComposite2.getPlotSystem3().repaint();
				customComposite2.getPlotSystem3().autoscaleAxes();
				
				
			 }
    	
    });

////////////////////////////////Proceed Button///////////////////////////////////////////////////
	    
	    customComposite1.getProceedButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				try{
					outputCurves.resetCurve();
					dms.get(sm.getSelection()).resetAll();
					models.get(sm.getSelection()).setInput(null);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	
		        
	            int selection = models.get(sm.getSelection()).getSliderPos();
	            System.out.println("Slider position in reset:  " + selection);
	            SliceND slice = new SliceND(models.get(sm.getSelection()).getDatImages().getShape());
	           slice.setSlice(0, selection, selection+1, 1);
	           IDataset i = null;
				try {
					i = models.get(sm.getSelection()).getDatImages().getSlice(slice);
				} catch (DatasetException e1) {
						// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				i.squeeze();
				customComposite.getBoxPosition();
	          	IROI region = models.get(sm.getSelection()).getROI();
	           	IRectangularROI currentBox = region.getBounds();
             	int[] currentLen = currentBox.getIntLengths();
             	int[] currentPt = currentBox.getIntPoint();
	            int[][] currentLenPt = {currentLen, currentPt};
			    double[] currentTrackerPos = new double[] {(double) currentPt[1],(double)currentPt[0], (double) (currentPt[1] +currentLen[1]),(double) (currentPt[0]),(double) currentPt[1],
			    		(double) currentPt[0]+currentLen[0], (double) (currentPt[1]+currentLen[1]),(double) (currentPt[0]+currentLen[0])};
			             	
		        int[] ab =customComposite1.getMethodology();
		        models.get(sm.getSelection()).setMethodology((Methodology.values()[ab[0]]));
		       	models.get(sm.getSelection()).setFitPower(FitPower.values()[ab[1]]);
		       	models.get(sm.getSelection()).setBoundaryBox(ab[2]);
							
			             	
             	models.get(sm.getSelection()).setTrackerCoordinates(new double[] {currentTrackerPos[1], currentTrackerPos[0]});
             	models.get(sm.getSelection()).setLenPt(currentLenPt);
			             	
             	IDataset j = DummyProcessingClass.DummyProcess(sm, i, models.get(sm.getSelection()),
	             		dms.get(sm.getSelection()), 
             			gms.get(sm.getSelection()), customComposite, 
             			paramField.getTabFolder().getSelectionIndex(), 
             			customComposite.getSliderPos(), 0);
			             	
	            customComposite1.getPlotSystem().createPlot2D(j, null, null);
	            dms.get(sm.getSelection()).resetAll();
			        
				}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });

////////////////////////////////Load Fit Parameters////////////////////////////////////////
	    
	    customComposite1.getLoadButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getParentShell(), SWT.OPEN); 
				
				String stitle = "r";
				String path = "p";
				
				if (fd.open() != null) {
					stitle = fd.getFileName();
					path = fd.getFilterPath();
					System.out.println(path);
					System.out.println(stitle);
				
				}
				
				String title = path + File.separator + stitle;
				
				FittingParameters fp = FittingParametersInputReader.reader(title);
				
				ExampleModel m = models.get(sm.getSelection());
				
				m.setLenPt(fp.getLenpt());
				m.setTrackerType(fp.getTracker());
				m.setFitPower(fp.getFitPower());
				m.setBoundaryBox(fp.getBoundaryBox());
				m.setMethodology(fp.getBgMethod());
				m.setSliderPos(fp.getSliderPos());
				
				
				customComposite1.setMethodologyDropDown(fp.getBgMethod());
				customComposite1.setFitPowerDropDown(fp.getFitPower());
				customComposite1.setTrackerTypeDropDown(fp.getTracker());
				customComposite1.setBoundaryBox(fp.getBoundaryBox());
				
				customComposite.setRegion(fp.getLenpt());
				customComposite.redraw();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
////////////////////////////////Save Fit Parameters////////////////////////////////////////
	    
		customComposite1.getSaveButton().addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd = new FileDialog(getParentShell(), SWT.SAVE); 
				ExampleModel m = models.get(sm.getSelection());
	
				String stitle = "r";
	 			String path = "p";
				
				
				if (fd.open() != null) {
					stitle = fd.getFileName();
					path = fd.getFilterPath();
					System.out.println(path);
					System.out.println(stitle);
				
				}
				
				String s = gms.get(sm.getSelection()).getSavePath();
				String title = path + File.separator + stitle + ".txt";
				System.out.println(title);
		
				FittingParametersOutput.FittingParametersOutputTest(title, 
						m.getLenPt()[1][0], m.getLenPt()[1][1],
						m.getLenPt()[0][0], m.getLenPt()[0][1], 
						m.getMethodology(), m.getTrackerType(), 
						m.getFitPower(), m.getBoundaryBox(), 
						m.getSliderPos(), m.getFilepath());
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
		}
		});	    
			    
/////////////////////////// Load / Select Files from tree//////////////////////////////////////////////////			    
		datDisplayer.getSelectFiles().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fd = new FileDialog(getParentShell(), SWT.MULTI); 
	
				String[] stitle = new String [] {"empty"};
				String path = "p";
				
				
				if (fd.open() != null) {
					stitle = fd.getFileNames();
					path = fd.getFilterPath();
				}
				
				
				String[] newPaths = new String[stitle.length];
				
				for(int i =0; i<stitle.length; i++){
					newPaths[i] = path + File.separator + stitle[i];
				}
			
				gms.clear();
				dms.clear();
				models.clear();
				datDisplayer.getList().removeAll();
				sm.clearFilepaths();
				
				filepaths = newPaths;
				sm.setFilepaths(filepaths);
				sm.setSelection(0);
				
				for (int id = 0; id<stitle.length; id++){ 
					try {
						
						dms.add(new DataModel());
						gms.add(new GeometricParametersModel());
						
						IDataHolder dh1 =LoaderFactory.getData(filepaths[id]);
						ILazyDataset ild =dh1.getLazyDataset(gms.get(sm.getSelection()).getImageName());
						models.add(new ExampleModel());
						
						dms.get(id).setName(StringUtils.substringAfterLast(sm.getFilepaths()[id], File.separator));
						models.get(id).setDatImages(ild);
						models.get(id).setFilepath(filepaths[id]);
						
						customComposite.getBoxPosition(id);
						
						if (sm.getCorrectionSelection() == 0){
							ILazyDataset ildx =dh1.getLazyDataset(gms.get(sm.getSelection()).getxName()); 
							models.get(id).setDatX(ildx);
						}
						else if (sm.getCorrectionSelection() == 1){
							ILazyDataset ildx =dh1.getLazyDataset(gms.get(sm.getSelection()).getxNameRef()); 
							models.get(id).setDatX(ildx);
							
							ILazyDataset dcdtheta = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getdcdtheta());
							models.get(id).setDcdtheta(dcdtheta);
							
							ILazyDataset qdcd = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getqdcd());
							models.get(id).setQdcd(qdcd);
								
								
							if (dcdtheta == null){
								try{
							    	dcdtheta = dh1.getLazyDataset(ReflectivityMetadataTitlesForDialog.getsdcdtheta());
							    	models.get(id).setDcdtheta(dcdtheta);
								} catch (Exception e2){
									System.out.println("can't get dcdtheta");
								}
							}
							else{
							}
							
							
						}
						else{
						}	
					
					datDisplayer.setList(filepaths);
					outputCurves.setDatTable(sm);
					
					} 
					
					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
			    
///////////////////////////////////////////////////////////////////////////////////////////////
	    customComposite2.getLeft().addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent e) {
				customComposite2.getRight().setWeights(customComposite2.getLeft().getWeights());
				
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
				customComposite2.getRight().setWeights(customComposite2.getLeft().getWeights());
				
			}
		});
	    
/////////////////////////////////////////////////////////////////////////////////////////	    
//////////////////////////////////Background Curves in cross sections Plotsystem2////////
/////////////////////////////////////////////////////////////////////////////////////////
	    
	    customComposite2.getBackgroundButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				SliceND slice = new SliceND(models.get(sm.getSelection()).getDatImages().getShape());
				int selection = models.get(sm.getSelection()).getImageNumber();
				slice.setSlice(0, selection, selection+1, 1);
				IDataset j = null;
				try {
					j = models.get(sm.getSelection()).getDatImages().getSlice(slice);
				} catch (Exception e1){
					e1.printStackTrace();
				}
				j.squeeze();
				
				IDataset background = DummyProcessingClass.DummyProcess(sm, j, models.get(sm.getSelection()),
						dms.get(sm.getSelection()), gms.get(sm.getSelection()), 
						customComposite, paramField.getTabFolder().getSelectionIndex(), selection, 0);
				dms.get(sm.getSelection()).setSlicerBackground(background);
				
				ILineTrace lt1 = VerticalHorizontalSlices.horizontalslice(customComposite2);
				ILineTrace lt2 = VerticalHorizontalSlices.horizontalsliceBackgroundSubtracted(customComposite2, background);
				
				IDataset backSubTop = Maths.subtract(lt1.getYData(), lt2.getYData());
				IDataset xTop = lt1.getXData();
				ILineTrace lt12BackSub = customComposite2.getPlotSystem1().createLineTrace("background slice");
				lt12BackSub.setData( xTop, backSubTop);
				
				
				ILineTrace lt3 = VerticalHorizontalSlices.verticalslice(customComposite2);
				ILineTrace lt4 = VerticalHorizontalSlices.verticalsliceBackgroundSubtracted(customComposite2,background);
				
				IDataset backSubSide = Maths.subtract(lt3.getYData(), lt4.getYData());
				IDataset xSide = lt3.getYData();
				ILineTrace lt34BackSub = customComposite2.getPlotSystem3().createLineTrace("background slice");
				lt34BackSub.setData(backSubSide,xSide);
				
				customComposite2.getPlotSystem1().clear();
				customComposite2.getPlotSystem1().addTrace(lt1);
				customComposite2.getPlotSystem1().addTrace(lt2);
				customComposite2.getPlotSystem1().addTrace(lt12BackSub);
				customComposite2.getPlotSystem1().repaint();
				customComposite2.getPlotSystem1().autoscaleAxes();
				customComposite2.getPlotSystem1().clearAnnotations();
				
				customComposite2.getPlotSystem3().clear();
				customComposite2.getPlotSystem3().addTrace(lt3);
				customComposite2.getPlotSystem3().addTrace(lt4);
				customComposite2.getPlotSystem3().addTrace(lt34BackSub);
				customComposite2.getPlotSystem3().repaint();
				customComposite2.getPlotSystem3().autoscaleAxes();
				customComposite2.getPlotSystem3().clearAnnotations();
			
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
	    
/////////////////////////////////////////////////////////////////////////////////
///////////////////////Reflectivity/SXRD CorrectionSelection////////////////////////
///////////////////////////////////////////////////////////////////////
	    paramField.getTabFolder().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				sm.setCorrectionSelection(paramField.getTabFolder().getSelectionIndex());
				
				for (int id = 0; id<filepaths.length; id++){ 
					try {
						IDataHolder dh1 =LoaderFactory.getData(filepaths[id]);
						
						if (sm.getCorrectionSelection() == 0){
							ILazyDataset ildx =dh1.getLazyDataset(gms.get(sm.getSelection()).getxName()); 
							models.get(id).setDatX(ildx);
						}
						else if (sm.getCorrectionSelection() == 1){
							ILazyDataset ildx =dh1.getLazyDataset(gms.get(sm.getSelection()).getxNameRef()); 
							models.get(id).setDatX(ildx);
							
							ILazyDataset dcdtheta = dh1.getLazyDataset(ReflectivityMetadataTitlesForDialog.getdcdtheta());
							models.get(id).setDcdtheta(dcdtheta);
							
							ILazyDataset qdcd = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getqsdcd());
							models.get(id).setQdcd(qdcd);
							
							if ((boolean) (gms.get(id).getFluxPath().equalsIgnoreCase("NO") 
									||(gms.get(sm.getSelection()).getFluxPath().equalsIgnoreCase(null)))) {
								try { 
									ILazyDataset flux = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getionc1());
									models.get(id).setFlux(flux);
									
									ILazyDataset theta = dh1.getLazyDataset( ReflectivityMetadataTitlesForDialog.getqsdcd());
									models.get(id).setTheta(theta);
									
								}
								catch (Exception e6){
									System.out.println("No normalisation data available internally");
									
								}
							}
							
							if (dcdtheta == null){
							    try{
							    	dcdtheta = dh1.getLazyDataset(ReflectivityMetadataTitlesForDialog.getsdcdtheta());
							    	models.get(id).setDcdtheta(dcdtheta);
								} catch (Exception e2){
									System.out.println("can't get dcdtheta");
								}
							}
							else{
							}
						}
						else{
						}
						
					} 
					catch (Exception e4 ){
						
					}	
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////Mass Runner////////////////////////////////////////////////////
/////////////////////////Keywords: Run all, run files, series runner///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	    
	    datDisplayer.getMassRunner().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				for (int g =0; g<sm.getFilepaths().length; g++){
					try{
						outputCurves.resetCurve();
						dms.get(g).resetAll();
						models.get(g).setInput(null);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	
				        
			            int selection = models.get(g).getSliderPos();
			            System.out.println("Slider position in reset:  " + selection);
			            SliceND slice = new SliceND(models.get(g).getDatImages().getShape());
			            slice.setSlice(0, selection, selection+1, 1);
						IDataset i = null;
						try {
							i = models.get(g).getDatImages().getSlice(slice);
						} catch (DatasetException e1) {
								// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						i.squeeze();
						customComposite.getBoxPosition(g);
		            	IROI region = models.get(g).getROI();
		             	IRectangularROI currentBox = region.getBounds();
		             	int[] currentLen = currentBox.getIntLengths();
		             	int[] currentPt = currentBox.getIntPoint();
		             	int[][] currentLenPt = {currentLen, currentPt};
				        double[] currentTrackerPos = new double[] {(double) currentPt[1],(double)currentPt[0], (double) (currentPt[1] +currentLen[1]),(double) (currentPt[0]),(double) currentPt[1],
						(double) currentPt[0]+currentLen[0], (double) (currentPt[1]+currentLen[1]),(double) (currentPt[0]+currentLen[0])};
				             	
				        int[] ab =customComposite1.getMethodology();
				        models.get(g).setMethodology((Methodology.values()[ab[0]]));
				       	models.get(g).setFitPower(FitPower.values()[ab[1]]);
				       	models.get(g).setBoundaryBox(ab[2]);
				       	models.get(g).setTrackerType(customComposite1.getTrackerTypeDropDown());
		             	models.get(g).setTrackerCoordinates(new double[] {currentTrackerPos[1], currentTrackerPos[0]});
		             	models.get(g).setLenPt(currentLenPt);
				             	
		             	IDataset j = DummyProcessingClass.DummyProcess(sm, i, models.get(g),
		             			dms.get(g), 
		             			gms.get(g), customComposite, 
		             			paramField.getTabFolder().getSelectionIndex(), 
		             			customComposite.getSliderPos(), 0);
				             	
		             	customComposite1.getPlotSystem().createPlot2D(j, null, null);
		             	dms.get(g).resetAll();
					
					
					dms.get(g).resetAll();
					operationJob1 oJ = new operationJob1();
					oJ.setCustomComposite(customComposite);
					oJ.setCustomComposite1(customComposite1);
					oJ.setCorrectionSelection(paramField.getTabFolder().getSelectionIndex());
					oJ.setOutputCurves(outputCurves);
					oJ.setSuperModel(sm);
					oJ.setDm(dms.get(g));
					oJ.setModel(models.get(g));
					oJ.setGeoModel(gms.get(sm.getSelection()));
					oJ.setPlotSystem(customComposite1.getPlotSystem());
					oJ.run(null);	
					System.out.println("g value: " + g);

				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    
///////////////////////////////////////////////////////////////////////////////////
//////////////////////////////Intensity/Fhkl switch///////////////////////
	    /////////////////////////////////////////////////////////////
	    
	    outputCurves.getIntensity().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String[] dmnames= new String [dms.size()];
				
				CharSequence chsq = "Spliced Curve";
				
				for (int v = 0; v<dms.size(); v++){
					dmnames[v] = dms.get(v).getName();
				}
				
				String fr = CurveStateIdentifier.CurveStateIdentifier1(outputCurves.getPlotSystem());
				
				ArrayList<ILineTrace> traceList = new ArrayList<>();
				
				for(ITrace tr : outputCurves.getPlotSystem().getTraces()){
				
					ILineTrace lt = outputCurves.getPlotSystem().createLineTrace("Holder");
					
					if(tr.getName().contains(chsq) == false){
						if (fr == "f"){
							lt.setName(tr.getName().replace(f, i));
							for (int tn = 0 ; tn< dmnames.length; tn++){
								if (tr.getName().contains(dmnames[tn])){
									lt.setData(dms.get(tn).xIDataset(), dms.get(tn).yIDataset());
								}
							}
						}
						else{
							lt.setName(tr.getName().replace(i, f));
							for (int tn = 0 ; tn< dmnames.length; tn++){
								if (tr.getName().contains(dmnames[tn])){
									lt.setData(dms.get(tn).xIDataset(), dms.get(tn).yIDatasetFhkl());
								}
							}
						}
					}
					else{
						if (fr == "f"){
							lt.setName(tr.getName().replace(f, i));
							lt.setData(sm.getSplicedCurveX(), sm.getSplicedCurveY());
							}
						else{
							lt.setName(tr.getName().replace(i, f));
							lt.setData(sm.getSplicedCurveX(), sm.getSplicedCurveYFhkl());
							}
						}
					
					traceList.add(lt);		
					}
				
				if (fr == "f"){
					outputCurves.getIntensity().setText("Fhkl?");
				}
				else{
					outputCurves.getIntensity().setText("Intensity?");
				}
				
				outputCurves.getPlotSystem().clear();
				for (ILineTrace ltr : traceList){
					outputCurves.getPlotSystem().addTrace(ltr);
				}
				outputCurves.getPlotSystem().autoscaleAxes();	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	    
/////////////////////////////////////////////////////////////////////////////////////
////////////////////Errors on Curves ////////////////////////////////////////////////
///////////////Keywords: output curves, errors///////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
	    
	    
	    outputCurves.getErrors().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				int te = 0;
				int det = 0;
				CharSequence c = "Error";
				CharSequence d = "Spliced";
				
				ArrayList<ITrace> al = new ArrayList<>();;
				
				for(ITrace trac : outputCurves.getPlotSystem().getTraces()){
					if (trac != null){
						al.add(trac);
					}
				}
				
				for(int tr = 0; tr<al.size(); tr++){
					
					if(al.get(tr).getName().contains(c) == true){
						te+=1;
						al.remove(tr);
					}else{
						
					}
					
					if (al.get(tr).getName().contains(f)){
	    				det +=1;
	    			}else{
	    				det-=1;
	    			}
				}
				
				ArrayList<TableItem> items = new ArrayList<>();
				
				for (int ni = 0; ni<outputCurves.getDatTable().getItemCount(); ni++){
					
					TableItem no = outputCurves.getDatTable().getItem(ni);
					
					if (no.getChecked()){
						items.add(no);
					}
				}
				
				for(ITrace tra : al){
					outputCurves.getPlotSystem().addTrace(tra);
				}
				
				if (te == 0){
					for (TableItem b : items){
						
						ILineTrace ltmax = outputCurves.getPlotSystem().createLineTrace(b.getText() + "_MaxError");
						ILineTrace ltmin = outputCurves.getPlotSystem().createLineTrace(b.getText() + "_MinError");
						
						int p = (Arrays.asList(datDisplayer.getList().getItems())).indexOf(b.getText());
						
						if (dms.get(p).getyList() == null || dms.get(p).getxList() == null) {
							
						} 
						else {
								if (det>=0){
									ltmax.setData(dms.get(p).xIDataset(),dms.get(p).getYIDatasetFhklMax());
									ltmax.setName(b.getText() + "_FhklMaxError");
									
									ltmin.setData(dms.get(p).xIDataset(),dms.get(p).getYIDatasetFhklMin());
									ltmin.setName(b.getText() + "_FhklMinError");
	
								}
								else{
									ltmax.setData(dms.get(p).xIDataset(),dms.get(p).getYIDatasetMax());
									ltmax.setName(b.getText() + "_IntensityMaxError");
									
									ltmin.setData(dms.get(p).xIDataset(),dms.get(p).getYIDatasetMin());
									ltmin.setName(b.getText() + "_IntensityMinError");
	
								}
						}
						
						outputCurves.getPlotSystem().addTrace(ltmax);
						outputCurves.getPlotSystem().addTrace(ltmin);
						
						ltmax.setTraceType(TraceType.DASH_LINE);
						ltmin.setTraceType(TraceType.DASH_LINE);
						
						outputCurves.getPlotSystem().autoscaleAxes();
					}
					
					if (outputCurves.getSc() != null){
						for(ITrace tr : outputCurves.getPlotSystem().getTraces()){
							
							if(tr.getName().contains(d) == true){
							
								ILineTrace spmax = outputCurves.getPlotSystem().createLineTrace("Spliced_MaxError");
								ILineTrace spmin = outputCurves.getPlotSystem().createLineTrace("Spliced_MinError");
								
								if (det>=0){
									spmax.setData(sm.getSplicedCurveX(),sm.getSplicedCurveYFhklErrorMax());
									spmax.setName("Spliced_FhklMaxError");
									
									spmin.setData(sm.getSplicedCurveX(),sm.getSplicedCurveYFhklErrorMin());
									spmin.setName("Spliced_FhklMinError");
		
								}
								else{
									spmax.setData(sm.getSplicedCurveX(),sm.getSplicedCurveYErrorMax());
									spmax.setName("Spliced_IntensityMaxError");
									
									spmin.setData(sm.getSplicedCurveX(),sm.getSplicedCurveYErrorMin());
									spmin.setName("Spliced_IntensityMinError");
								}
	
							}
						}
					}
					outputCurves.getErrors().setText("Errors");
				}
				else{
					outputCurves.getErrors().setText("Errors");
				}
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    
	    
	    
	    
	    
//////////////////////////////////////////////////////////////////////////////////////	    
	    
	    return container;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("ExampleDialog");
	}

	@Override
	protected Point getInitialSize() {
		Rectangle rect = PlatformUI.getWorkbench().getWorkbenchWindows()[0].getShell().getBounds();
		int h = rect.height;
		int w = rect.width;
		
		return new Point((int) Math.round(0.6*w), (int) Math.round(0.8*h));
	}
	
	@Override
	  protected boolean isResizable() {
	    return true;
	}
	
	public void roiStandard1(ROIEvent evt){	
		
		imageNo = ClosestNoFinder.closestNoPos(outputCurves.getRegionNo().getROI().getPointX(), dms.get(sm.getSelection()).getxList());
		
		if (outputMovie.getOutputControl().getSelection() == true){
		
			models.get(sm.getSelection()).setOutputNo(imageNo);
//			System.out.println("ImageNo: " + models.get(sm.getSelection()).getOutputNo());
			
			operationJob2 oJ2 = new operationJob2();
			
			oJ2.setDm(dms.get(sm.getSelection()));
			oJ2.setModel(models.get(sm.getSelection()));
		
			oJ2.setImageNo(imageNo);
			
			models.get(sm.getSelection()).setOutputNo(imageNo);
//			System.out.println("ImageNo: " + models.get(sm.getSelection()).getOutputNo());
			
			oJ2.setDm(dms.get(sm.getSelection()));
			oJ2.setModel(models.get(sm.getSelection()));
			oJ2.setOutputMovie(outputMovie);
			oJ2.setPlotSystem(outputMovie.getPlotSystem());
			
			oJ2.schedule();
		}
		if (customComposite.getOutputControl().getSelection() == true){
			
			operationJob3 oJ3 = new operationJob3();
			
			oJ3.setDm(dms.get(sm.getSelection()));
			oJ3.setModel(models.get(sm.getSelection()));
		
			oJ3.setImageNo(imageNo);
			oJ3.setPlotSystemComposite(customComposite);
			
			oJ3.schedule();
			
			
		}
		}
	
}	
	
	/////////////////////////////////////////////////////////////////////////////////////
	class operationJob3 extends Job {
		
		private ExampleModel model;
		private IPlottingSystem<Composite> plotSystem;
		private PlotSystemComposite plotSystemComposite;
		private DataModel dm;
		private int imageNo;
		
		public operationJob3() {
			super("updating image...");
		}
		
		public void setPlotSystemComposite(PlotSystemComposite customComposite) {
			this.plotSystemComposite = customComposite;
		}
		
		public void setPlotSystem(IPlottingSystem<Composite> plotSystem) {
			this.plotSystem = plotSystem;
		}
		

		public void setDm(DataModel dm) {
			this.dm = dm;
		}
		
		public void setModel(ExampleModel model) {
			this.model = model;
		}
		
		public void setImageNo(int imageNo) {
			this.imageNo = imageNo;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected IStatus run(IProgressMonitor monitor) {
	
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run() {
			
				SliceND slice = new SliceND(model.getDatImages().getShape());
				slice.setSlice(0, imageNo, imageNo+1, 1);
				
				
				try {
					
					
					System.out.println("slice[0]:  " + slice.getShape()[0]);
					
					
					Dataset d = (Dataset) model.getDatImages().getSlice(slice);
					d.squeeze();
					
					plotSystemComposite.getPlotSystem().
					updatePlot2D(d, null, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			});

			return Status.OK_STATUS;

		}
	
	}
	
////////////////////////////////////////////////////////////////////////////////////
	
	
	class operationJob2 extends Job {
	
		private ExampleModel model;
		private IPlottingSystem<Composite> plotSystem;
		private OutputMovie outputMovie;
		private DataModel dm;
		private PlotSystemComposite plotSystemComposite;
		private int imageNo;
		private MultipleOutputCurvesTable outputCurves;
		
		
		public void setPlotSystemComposite(PlotSystemComposite customComposite) {
			this.plotSystemComposite = customComposite;
		}
		
		public operationJob2() {
			super("updating image...");
		}
		
		public void setOutputMovie(OutputMovie outputMovie) {
			this.outputMovie = outputMovie;
		}
		
		public void setPlotSystem(IPlottingSystem<Composite> plotSystem) {
			this.plotSystem = plotSystem;
		}
		
		public void setOutputCurves(MultipleOutputCurvesTable outputCurves) {
			this.outputCurves = outputCurves;
		}

		public void setDm(DataModel dm) {
			this.dm = dm;
		}
		
		public void setModel(ExampleModel model) {
			this.model = model;
		}
		
		
		public void setImageNo(int imageNo) {
			this.imageNo = imageNo;
		}
		
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			Display.getDefault().asyncExec(new Runnable() {
				
			@Override
			public void run() {
				IRegion t = outputCurves.getRegionNo();
				IROI u = t.getROI();
				double v = u.getPointX();
				int w = ClosestNoFinder.closestNoPos(v, dm.getxList());
				IDataset x = dm.getOutputDatArray().get(w);
				outputMovie.getPlotSystem().updatePlot2D(x, null, null);
			}
			});
			
			return Status.OK_STATUS;

		}
	
	}
	
/////////////////////////////////////////////////////////////////////
/////////////////////Tracking Job////////////////////////////////////
/////////////////////////////////////////////////////////////////////
	
	class operationJob1 extends Job {

		private DataModel dm;
		private ExampleModel model;
		private IPlottingSystem<Composite> plotSystem;
		private MultipleOutputCurvesTable outputCurves;
		private GeometricParametersModel gm;
		private SuperModel sm;
		private PlotSystem1Composite customComposite1;
		private PlotSystemComposite customComposite;
		private int correctionSelection;

		public operationJob1() {
			super("updating image...");
		}

		
		
		
		public void setCustomComposite(PlotSystemComposite customComposite) {
			this.customComposite = customComposite;
		}
		
		
		public void setCustomComposite1(PlotSystem1Composite customComposite1) {
			this.customComposite1 = customComposite1;
		}
		
		public void setOutputCurves(MultipleOutputCurvesTable outputCurves) {
			this.outputCurves = outputCurves;
		}
		
		
		public void setData(IDataset input) {
		}
		
		public void setCorrectionSelection(int cS) {
			this.correctionSelection = cS;
		}
		
		public void setDm(DataModel dm) {
			this.dm = dm;
		}
		
		public void setModel(ExampleModel model) {
			this.model = model;
		}
		
		public void setSuperModel(SuperModel sm) {
			this.sm= sm;
		}
		
		public void setGeoModel(GeometricParametersModel gm) {
			this.gm = gm;
		}	
			
		public void setPlotSystem(IPlottingSystem<Composite> plotSystem) {
			this.plotSystem = plotSystem;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			
			int[] ab =customComposite1.getMethodology();
			model.setMethodology((Methodology.values()[ab[0]]));
			model.setFitPower(FitPower.values()[ab[1]]);
			model.setBoundaryBox(ab[2]);
			dm.resetAll();
			
			outputCurves.resetCurve();
			
			System.out.println("number of images:   " + model.getDatImages().getShape()[0]);
			
			int k =0;
				
			if (model.getSliderPos() == 0){ 
				for ( k = 0; k<model.getDatImages().getShape()[0]; k++){
					
//					System.out.println("k value :   " + k);
					
					int trackingMarker = 0;
					IDataset j = null;
					SliceND slice = new SliceND(model.getDatImages().getShape());
					slice.setSlice(0, k, k+1, 1);
						
					SliceND slicex = new SliceND(model.getDatX().getShape());
					slicex.setSlice(0, k, k+1, 1);
						
					if(sm.getCorrectionSelection() == 0){
						try {
							//slice.setSlice(0, 0, 1, 1);
							dm.addxList(model.getDatImages().getShape()[0], k,(model.getDatX().getSlice(slicex)).getDouble(0));
							
//							System.out.println("Added to xList:  " + k);
								
						} catch (DatasetException e2) {
								// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					else if (sm.getCorrectionSelection() == 1){
						try {
								//slice.setSlice(0, 0, 1, 1);
							dm.addxList(model.getDatImages().getShape()[0], k,(model.getDatX().getSlice(slicex)).getDouble(0));
							
//							System.out.println("Added to xList:  " + k);
							
						} catch (DatasetException e2) {
								// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
						
					try {
						j = model.getDatImages().getSlice(slice);
					} 
					catch (Exception e1) {
					}
							
					j.squeeze();
						
					customComposite.getBoxPosition();
						
					IDataset output1 = DummyProcessingClass.DummyProcess(sm, j, model,dm, gm, customComposite, correctionSelection, k, trackingMarker);
							
//					System.out.println("Added to yList:  " + k);
	
					Display.getDefault().syncExec(new Runnable() {
							
						@Override
						public void run() {
							plotSystem.clear();
							plotSystem.updatePlot2D(output1, null,monitor);
				    		plotSystem.repaint(true);
				    		outputCurves.updateCurve(dm, outputCurves.getIntensity().getSelection(), sm);
				    		
			    		
						}
					});
				}
			
			} else if (model.getSliderPos() != 0){
				
				
			////////////////////////inside second loop scenario@@@@@@@@@@@@@@@@@@@@@@@@@@@@///////////
				//k=(model.getSliderPos()-1);
				
				for (k = (model.getSliderPos()); k >= 1; k-- ){
					
//					System.out.println("k value :   " + k);
					
					if (k == (model.getSliderPos()-1)){
						model.setInput(null);
					}
					int trackingMarker = 1;
					
					IDataset j = null;
					SliceND slice = new SliceND(model.getDatImages().getShape());
					slice.setSlice(0, k-1, k, 1);
						
					SliceND slicex = new SliceND(model.getDatX().getShape());
					slicex.setSlice(0, k-1, k, 1);
					
					if(sm.getCorrectionSelection() == 0){
						try {
							//slice.setSlice(0, 0, 1, 1);
							
							IDataset nom = (model.getDatX().getSlice(slicex));
							
							double nim = nom.getDouble(0);
							
							dm.addxList(model.getDatImages().getShape()[0], k-1,nim);
							
//							System.out.println("Added to xList:  " + k);
								
						} catch (DatasetException e2) {
								// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					
					else if (sm.getCorrectionSelection() == 1){
						try {
								//slice.setSlice(0, 0, 1, 1);
							dm.addxList(model.getDatImages().getShape()[0], k-1,(model.getDatX().getSlice(slicex)).getDouble(0));
							
//							System.out.println("Added to xList:  " + k);
							
						} catch (DatasetException e2) {
								// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
						
					try {
						j = model.getDatImages().getSlice(slice);
					} 
					catch (Exception e1) {
					}
							
					j.squeeze();
						
					customComposite.getBoxPosition();
						
					IDataset output1 = DummyProcessingClass.DummyProcess(sm, j, model,dm, gm, customComposite, correctionSelection, k-1, trackingMarker);
							
//					System.out.println("Added to yList:  " + k);
	
					Display.getDefault().syncExec(new Runnable() {
							
						@Override
						public void run() {
							plotSystem.clear();
							plotSystem.updatePlot2D(output1, null,monitor);
				    		plotSystem.repaint(true);
				    		outputCurves.updateCurve(dm, outputCurves.getIntensity().getSelection(), sm);
				    		
			    		
						}
					});
				}
			
				for ( k = model.getSliderPos(); k<model.getDatImages().getShape()[0]; k++){
					
					int trackingMarker = 2;
					
//					System.out.println("k value :   " + k);
					
					if (k==model.getSliderPos()){
						model.setInput(null);
					}
					
					IDataset j = null;
					SliceND slice = new SliceND(model.getDatImages().getShape());
					slice.setSlice(0, k, k+1, 1);
						
					SliceND slicex = new SliceND(model.getDatX().getShape());
					slicex.setSlice(0, k, k+1, 1);
						
					if(sm.getCorrectionSelection() == 0){
						try {
							
							dm.addxList(model.getDatImages().getShape()[0], k,(model.getDatX().getSlice(slicex)).getDouble(0));
							
						} catch (DatasetException e2) {
								// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					else if (sm.getCorrectionSelection() == 1){
						try {
								//slice.setSlice(0, 0, 1, 1);
							dm.addxList(model.getDatImages().getShape()[0], k,(model.getDatX().getSlice(slicex)).getDouble(0));
							
//							System.out.println("Added to xList:  " + k);
							
						} catch (DatasetException e2) {
								// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
						
					try {
						j = model.getDatImages().getSlice(slice);
					} 
					catch (Exception e1) {
					}
							
					j.squeeze();
						
					customComposite.getBoxPosition();
						
					IDataset output1 = DummyProcessingClass.DummyProcess(sm, j, model,dm, gm, 
							customComposite, correctionSelection, k, trackingMarker);
							
	
					Display.getDefault().syncExec(new Runnable() {
							
						@Override
						public void run() {
							plotSystem.clear();
							plotSystem.updatePlot2D(output1, null,monitor);
				    		plotSystem.repaint(true);
				    		outputCurves.updateCurve(dm, outputCurves.getIntensity().getSelection(), sm);
				    		
			    		
						}
					});
				}
				
				
			
			
			}
				
				
	    		try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			
		return Status.OK_STATUS;

		
		
		}
	}
	
/////////////////////////////////////////////////////////////////////	
/////////////////////////////Movie Job///////////////////////////////
/////////////////////////////////////////////////////////////////////
	   
	   
	class MovieJob extends Job {
	
	private List<IDataset> outputDatArray;
	private int time;
	private OutputMovie outputMovie;
	
	
	public MovieJob() {
		super("Playing movie...");
	}
	
	public void setOutputMovie(OutputMovie outputMovie) {
		this.outputMovie = outputMovie;
	}
	
	public void setData(List outputDatArray) {
	this.outputDatArray = outputDatArray;
	}
	
	public void setTime(int time) {
	this.time = time;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		for( IDataset t: outputDatArray){
			java.util.Date date = new java.util.Date();
			System.out.println(date);
			System.out.println("start:" + date);
			System.out.println("sum: " + (Double) DatasetUtils.cast(DoubleDataset.class, t).sum());
			//outputMovie.clear();
			outputMovie.getPlotSystem().updatePlot2D(t, null, monitor);
			outputMovie.getPlotSystem().repaint(true);
			//outputMovie.repaint();
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
			date = new java.util.Date();
			System.out.println("stop:" + date);
			}
	return Status.OK_STATUS;
	}
}




