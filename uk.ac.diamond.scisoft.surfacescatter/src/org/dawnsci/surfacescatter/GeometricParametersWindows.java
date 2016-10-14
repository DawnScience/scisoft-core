package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class GeometricParametersWindows extends Composite{

	private Button beamCorrection;
	private Text beamInPlane;
	private Text beamOutPlane;
	private Text covar;
	private Text detectorSlits;
	private Text inPlaneSlits;
	private Text inplanePolarisation;
	private Text outPlaneSlits;
	private Text outplanePolarisation;
	private Text reflectivityA;
	private Text sampleSize;
	private Text normalisationFactor;
	private Button specular;
	private Text imageName;
	private Text xName;
	private Text scalingFactor;
	private GeometricParametersModel gm;
	private Text beamHeight;
	private Text footprint;
	private Text angularFudgeFactor;
	private Text savePath;
	private Text fluxPath;
	private TabFolder folder;
	private Text xNameRef;
	
	public GeometricParametersWindows(Composite parent, int style,ArrayList<GeometricParametersModel> gms, SuperModel sm){
		
		super(parent, style);
        //composite = new Composite(parent, SWT.NONE);
		
        new Label(this, SWT.NONE).setText("Geometric Parameters Window");
        
        this.createContents(gms, sm);
	}
	
	public void createContents(ArrayList<GeometricParametersModel> gms, SuperModel sm) {
		
		
		folder = new TabFolder(this, SWT.NONE);
	    
	    //Tab 1
	    TabItem paramsSXRD = new TabItem(folder, SWT.NONE);
	    paramsSXRD.setText("SXRD Parameters");
	   
		gm = gms.get(sm.getSelection());
		Group geometricParametersSX = new Group(folder, SWT.NULL);
		GridLayout geometricParametersSXLayout = new GridLayout(2,true);
		GridData geometricParametersSXData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		geometricParametersSX.setLayout(geometricParametersSXLayout);
		geometricParametersSX.setLayoutData(geometricParametersSXData);
		
		new Label(geometricParametersSX, SWT.LEFT).setText("beamCorrection");
		beamCorrection = new Button(geometricParametersSX, SWT.CHECK);
		new Label(geometricParametersSX, SWT.LEFT).setText("beamInPlane");
		beamInPlane = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("beamOutPlane");
		beamOutPlane = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("covar");
		covar = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("detectorSlits");
		detectorSlits = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("inPlaneSlits");
		inPlaneSlits = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("inplanePolarisation");
		inplanePolarisation = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("outPlaneSlits");
		outPlaneSlits = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("outplanePolarisation");
		outplanePolarisation = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("reflectivityA");
		reflectivityA = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("sampleSize");
		sampleSize = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("normalisationFactor");
		scalingFactor = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("scalingFactor");
		normalisationFactor = new Text(geometricParametersSX, SWT.SINGLE);		
		new Label(geometricParametersSX, SWT.LEFT).setText("specular");
		specular = new Button (geometricParametersSX, SWT.CHECK);
		new Label(geometricParametersSX, SWT.LEFT).setText("imageName");
		imageName = new Text(geometricParametersSX, SWT.SINGLE);
		new Label(geometricParametersSX, SWT.LEFT).setText("xName");
		xName = new Text (geometricParametersSX, SWT.CHECK);
		new Label(geometricParametersSX, SWT.LEFT).setText("savePath");
		savePath = new Text (geometricParametersSX, SWT.CHECK);
		
		
		paramsSXRD.setControl(geometricParametersSX);
	   	    
	    //Tab 2
	    TabItem paramsReflec = new TabItem(folder, SWT.NONE);
	    paramsReflec.setText("Reflectivity Parameters");

	    gm = gms.get(sm.getSelection());
		Group geometricParametersReflec = new Group(folder, SWT.NULL);
		GridLayout geometricParametersLayoutReflec = new GridLayout(2,true);
		GridData geometricParametersDataReflec = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		geometricParametersReflec.setLayout(geometricParametersLayoutReflec);
		geometricParametersReflec.setLayoutData(geometricParametersDataReflec);
		
		new Label(geometricParametersReflec, SWT.LEFT).setText("beamHeight");
		beamHeight = new Text(geometricParametersReflec, SWT.SINGLE);
		new Label(geometricParametersReflec, SWT.LEFT).setText("footprint");
		footprint = new Text(geometricParametersReflec, SWT.SINGLE);
		new Label(geometricParametersReflec, SWT.LEFT).setText("angularFudgeFactor");
		angularFudgeFactor = new Text(geometricParametersReflec, SWT.SINGLE);
		new Label(geometricParametersReflec, SWT.LEFT).setText("savePath");
		savePath = new Text (geometricParametersReflec, SWT.CHECK);
		new Label(geometricParametersReflec, SWT.LEFT).setText("fluxPath");
		fluxPath = new Text (geometricParametersReflec, SWT.CHECK);
		new Label(geometricParametersReflec, SWT.LEFT).setText("xNameRef");
		xNameRef = new Text (geometricParametersReflec, SWT.CHECK);
		
	    paramsReflec.setControl(geometricParametersReflec);
	    
	    xNameRef.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setFluxPath(xNameRef.getText());
			}
	    	
	    });
	    
	    fluxPath.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setFluxPath(fluxPath.getText());
			}
	    	
	    });
	    
	    beamHeight.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setBeamHeight(Double.parseDouble(beamHeight.getText()));
			}
	    	
	    });
		
	    savePath.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setSavePath(savePath.getText());
			}
	    	
	    });
	 
	    footprint.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setBeamHeight(Double.parseDouble(footprint.getText()));
			}
	    	
	    });
	    
	    angularFudgeFactor.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setAngularFudgeFactor(Double.parseDouble(angularFudgeFactor.getText()));
			}
	    	
	    });
	    
		beamCorrection.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				gm.setBeamCorrection(beamCorrection.getSelection());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				gm.setBeamCorrection(beamCorrection.getSelection());
				
			}
		});
		
		beamInPlane.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setBeamInPlane(Double.parseDouble(beamInPlane.getText()));
			}
	    	
	    });
		
		beamOutPlane.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setBeamOutPlane(Double.parseDouble(beamOutPlane.getText()));
			}
	    	
	    });
		
		covar.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setCovar(Double.parseDouble(covar.getText()));
			}
	    	
	    });
		
		detectorSlits.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setDetectorSlits(Double.parseDouble(detectorSlits.getText()));
			}
	    	
	    });
		
		inPlaneSlits.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setInPlaneSlits(Double.parseDouble(inPlaneSlits.getText()));
			}
	    	
	    });
		
		inplanePolarisation.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setInplanePolarisation(Double.parseDouble(inplanePolarisation.getText()));
			}
	    	
	    });

		outPlaneSlits.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setOutPlaneSlits(Double.parseDouble(outPlaneSlits.getText()));
			}
	    	
	    });

		outplanePolarisation.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setOutplanePolarisation(Double.parseDouble(outplanePolarisation.getText()));
			}
	    	
	    });

		
		scalingFactor.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setScalingFactor(Double.parseDouble(scalingFactor.getText()));
			}
	    	
	    });
		
		reflectivityA.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setReflectivityA(Double.parseDouble(reflectivityA.getText()));
			}
	    	
	    });

		sampleSize.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setSampleSize(Double.parseDouble(sampleSize.getText()));
			}
	    	
	    });

		normalisationFactor.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setNormalisationFactor(Double.parseDouble(normalisationFactor.getText()));
			}
	    	
	    });

		specular.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				gm.setSpecular(specular.getSelection());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				gm.setSpecular(specular.getSelection());
				
			}
		});
		
		imageName.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setImageName(imageName.getText());
			}
	    	
	    });
		
		xName.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				gm.setxName(xName.getText());
			}
	    	
	    });
		
	}
	
	public TabFolder getTabFolder(){
		return folder;
	}
	
}
