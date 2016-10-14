package org.dawnsci.surfacescatter;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class SuperPlotSystem2Composite extends Composite {


	private IPlottingSystem<Composite> plotSystem1;/////top plot
    private IPlottingSystem<Composite> plotSystem2;/////main image
    private IPlottingSystem<Composite> plotSystem3;/////side image
    private IDataset image1;

    
    public SuperPlotSystem2Composite(Composite parent, int style) throws Exception {
        super(parent, style);
       
        	
        if (image1==null){
        	image1 = DatasetFactory.zeros(new int[] {4,4});
        }
        
        new Label(this, SWT.NONE).setText("Region of Interest");
        
        try {
        	plotSystem1 = PlottingFactory.createPlottingSystem();
			plotSystem2 = PlottingFactory.createPlottingSystem();
			plotSystem3 = PlottingFactory.createPlottingSystem();
        } 
        catch (Exception e2) {
			e2.printStackTrace();
		}

        this.createContents(); 
        
    }
     
    public void createContents() throws Exception {

    	
    	final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        
        this.setLayout(gridLayout);

/////////////////Top Image///////////////////////////////////////////////////
        Group topImage = new Group(this, SWT.NONE);
        topImage.setText("Top Image");
        GridLayout topImageLayout = new GridLayout(4, true);
        topImageLayout.numColumns = 4;
        topImage.setLayout(topImageLayout);
		GridData topImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		//topImageData.horizontalSpan =3;
		//topImageData.verticalSpan = 1;
		//topImageData.horizontalAlignment = GridData.FILL;
		topImage.setLayoutData(topImageData);
        
//        ActionBarWrapper actionBarCompositeTop = ActionBarWrapper.createActionBars(topImage, null);;

		Button a = new Button(topImage, SWT.PUSH);
		a.setText("Top");
		GridData ld1 = new GridData(SWT.FILL, SWT.FILL, true, true);
//		ld1.minimumHeight=100;
//		ld1.minimumWidth=50;
		ld1.horizontalSpan = 3;
		ld1.verticalSpan = 2;
        a.setLayoutData(ld1);
        
//		plotSystem1.createPlotPart(topImage, "Top Image", actionBarCompositeTop, PlotType.IMAGE, null);
//		plotSystem1.getPlotComposite().setLayoutData(ld2);
////		plotSystem1.createPlot2D(image1, null, null);
//        
//        final GridData gd_secondField = new GridData(SWT.FILL, SWT.FILL, true, true);
//        gd_secondField.grabExcessVerticalSpace = true;
//        gd_secondField.grabExcessVerticalSpace = true;
//
//        plotSystem1.getPlotComposite().setLayoutData(gd_secondField);
        
////////////////////////////////////////////////////////////////////////////////////
/////////////////Dud panel//////////////////////////////////////////////////////////
        
//        Label dud = new Label(this, SWT.NONE);
//        
//        GridData gd = new GridData(GridData.CENTER, GridData.CENTER, true, false);
//        gd.horizontalSpan = 1;
//        dud.setLayoutData(gd);
//        
////////////////////////////////////////////////////////////////////////////////////
////////////////////Main image//////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
//        Group mainImage = new Group(this, SWT.NONE);
//        mainImage.setText("Main Image");
//        GridLayout mainImageLayout = new GridLayout(4, true);
//        mainImageLayout.numColumns = 4;
//        mainImage.setLayout(mainImageLayout);
//		GridData mainImageData= new GridData(SWT.FILL, SWT.FILL, true, true);
		//topImageData.horizontalSpan =3;
//		mainImageData.verticalSpan = 5;
//		mainImageData.horizontalAlignment = GridData.FILL;
//		mainImage.setLayoutData(topImageData);
        
//        ActionBarWrapper actionBarCompositeMain1 = ActionBarWrapper.createActionBars(mainImage, null);;

		Button b = new Button(topImage, SWT.PUSH);
		b.setText("Main");
		GridData ld2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		ld2.grabExcessVerticalSpace = true;
        ld2.grabExcessHorizontalSpace = true;
//      ld2.minimumHeight=100;
//		ld1.minimumWidth=50;
		ld2.horizontalSpan = 3;
		ld2.verticalSpan = 5;
        b.setLayoutData(ld1);
        
//        plotSystem2.createPlotPart(mainImage, "Main Image", actionBarCompositeMain1, PlotType.IMAGE, null);
//		plotSystem2.getPlotComposite().setLayoutData(ld1);
//        
//		ActionBarWrapper actionBarCompositeMain2 = ActionBarWrapper.createActionBars(mainImage, null);;
//
        
        Button c = new Button(topImage, SWT.PUSH);
		c.setText("Side");
		GridData ld3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		ld3.horizontalSpan = 1;
		ld3.verticalSpan = 5;
//		ld3.minimumHeight=100;
		//ld3.minimumWidth=50;
        c.setLayoutData(ld3);
        
//        plotSystem3.createPlotPart(mainImage, "Side Image", actionBarCompositeMain2, PlotType.IMAGE, null);
//		plotSystem3.getPlotComposite().setLayoutData(ld3);
        
        
//        Group mainImage = new Group(this, SWT.NONE);
//        mainImage.setText("Main Image");
//        GridLayout mainImageLayout = new GridLayout(4, true);
//        mainImageLayout.numColumns = 4;
//        mainImage.setLayout(mainImageLayout);
//		GridData mainImageData= new GridData(GridData.GRAB_HORIZONTAL, GridData.CENTER, true, true);
//		//mainImageData.horizontalSpan = 3;
//		mainImageData.verticalSpan = GridData.FILL;
//		mainImageData.horizontalAlignment = GridData.FILL;
////		mainImageData.verticalAlignment = GridData.FILL;
//		mainImage.setLayoutData(mainImageData);
//        
//		Button a = new Button(mainImage, SWT.PUSH);
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = GridData.FILL;
//		gridData.horizontalSpan = 3;
//		a.setLayoutData(gridData);
//		
//		
////		Button a = new Button(mainImage, SWT.PUSH);
//		a.setText("Main");
//		GridData ld = new GridData(GridData.GRAB_HORIZONTAL, GridData.CENTER, true, true);
//		ld.horizontalSpan = 3;
//        a.setLayoutData(ld);
//        ActionBarWrapper actionBarCompositeMain = ActionBarWrapper.createActionBars(mainImage, null);;

        //plotSystem2.createPlotPart(mainImage, "Region of Interest", null, PlotType.IMAGE, null);

		//plotSystem2.createPlot2D(image1, null, null);
        
//        gd_secondField.grabExcessVerticalSpace = true;
//        gd_secondField.grabExcessVerticalSpace = true;
//
//        plotSystem2.getPlotComposite().setLayoutData(gd_secondField);
///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////Side Image/////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		Group sideImage = new Group(this, SWT.NONE);
//		sideImage.setText("Side Image");
//		GridLayout sideImageLayout = new GridLayout();
////		sideImageLayout.numColumns = 1;
//		sideImage.setLayout(sideImageLayout);
//		GridData sideImageData= new GridData(GridData.FILL, GridData.CENTER, true, true);
//				//(GridData.FILL, GridData.CENTER, true, false);
//		sideImageData.horizontalSpan =1;
//		sideImageData.verticalSpan = 5;
//		sideImageData.horizontalAlignment = GridData.FILL_BOTH;
////		sideImageData.verticalAlignment = GridData.FILL;
//		sideImage.setLayoutData(sideImageData);
//		
////		ActionBarWrapper actionBarCompositeSide = ActionBarWrapper.createActionBars(sideImage, null);;
		
		
//		Button c = new Button(mainImage, SWT.PUSH);
//		c.setText("side");
//		GridData ld1 = new GridData();
//		ld1.horizontalAlignment = GridData.FILL;
//		ld1.horizontalSpan = 1;
//		c.setLayoutData(ld1);
//		plotSystem3.createPlotPart(sideImage, "Side Image", null, PlotType.IMAGE, null);
		
//		plotSystem3.createPlot2D(image1, null, null);
		
//		gd_secondField.grabExcessVerticalSpace = true;
//		gd_secondField.grabExcessVerticalSpace = true;
//		
//		plotSystem3.getPlotComposite().setLayoutData(gd_secondField);

//////////////////////////////////////////////////////////////////////////////////////////////

		}
   
   
   public Composite getComposite(){   	
   	return this;
   }
   
   public IPlottingSystem<Composite> getPlotSystem(){
	   return plotSystem2;
   }

   public IDataset getImage(){
	   return image1;
   }
   
//   public IRegion returnRegion(){
//	   return region;
//   }
   
   public void setData(IDataset input){
	   this.image1 = input;
   }
   
   
   public void setImage(IDataset input){
	   plotSystem2.updatePlot2D(input,  null, null);
	   
   }
   
   public void plotSystem2Redraw(){
	   plotSystem2.repaint();
   }
   
	public void updateImage(IDataset image){
		plotSystem2.updatePlot2D(image, null, null);
	}

}
