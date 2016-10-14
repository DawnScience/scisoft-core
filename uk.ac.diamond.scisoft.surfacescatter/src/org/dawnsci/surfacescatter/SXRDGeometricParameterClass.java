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
import org.eclipse.swt.widgets.Text;

public class SXRDGeometricParameterClass extends Composite{

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
	
	public SXRDGeometricParameterClass(Composite parent, int style,ArrayList<GeometricParametersModel> gms, SuperModel sm){
		
		super(parent, style);
        //composite = new Composite(parent, SWT.NONE);
		
        new Label(this, SWT.NONE).setText("Geometric Parameters Window");
        
        this.createContents(gms, sm);
	}
	
	public void createContents(ArrayList<GeometricParametersModel> gms, SuperModel sm) {
		
		gm = gms.get(sm.getSelection());
		Group geometricParameters = new Group(this, SWT.NULL);
		GridLayout geometricParametersLayout = new GridLayout(2,true);
		GridData geometricParametersData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		geometricParameters.setLayout(geometricParametersLayout);
		geometricParameters.setLayoutData(geometricParametersData);
		
		new Label(geometricParameters, SWT.LEFT).setText("beamCorrection");
		beamCorrection = new Button(geometricParameters, SWT.CHECK);
		new Label(geometricParameters, SWT.LEFT).setText("beamInPlane");
		beamInPlane = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("beamOutPlane");
		beamOutPlane = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("covar");
		covar = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("detectorSlits");
		detectorSlits = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("inPlaneSlits");
		inPlaneSlits = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("inplanePolarisation");
		inplanePolarisation = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("outPlaneSlits");
		outPlaneSlits = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("outplanePolarisation");
		outplanePolarisation = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("reflectivityA");
		reflectivityA = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("sampleSize");
		sampleSize = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("normalisationFactor");
		scalingFactor = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("scalingFactor");
		normalisationFactor = new Text(geometricParameters, SWT.SINGLE);		
		new Label(geometricParameters, SWT.LEFT).setText("specular");
		specular = new Button (geometricParameters, SWT.CHECK);
		new Label(geometricParameters, SWT.LEFT).setText("imageName");
		imageName = new Text(geometricParameters, SWT.SINGLE);
		new Label(geometricParameters, SWT.LEFT).setText("xName");
		xName = new Text (geometricParameters, SWT.CHECK);
		
		
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
}
