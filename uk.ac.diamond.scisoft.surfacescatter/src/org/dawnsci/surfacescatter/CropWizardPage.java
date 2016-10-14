/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dawnsci.surfacescatter;

import java.util.Arrays;
import java.util.List;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.dawnsci.spectrum.ui.file.IContain1DData;
import org.dawnsci.spectrum.ui.processing.CropProcess;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.XAxisBoxROI;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IROIListener;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.plotting.api.region.ROIEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CropWizardPage extends WizardPage implements ISpectrumWizardPage {

	CropProcess process;
	List<IContain1DData> dataList;
	IPlottingSystem<Composite> system;
	
	public CropWizardPage(List<IContain1DData> dataList) {
		super("Processing Wizard page");
		process = new CropProcess();
		this.dataList = dataList;
	}
	

	@Override
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		setControl(container);
		setTitle("Crop wizard page");
		setDescription("Choose region to crop to.");
		Label label = new Label(container,SWT.None); 
		label.setText("");
		label.setLayoutData(new GridData());
		ActionBarWrapper actionBarWrapper = ActionBarWrapper.createActionBars(container, null);
		Composite controlComposite = new Composite(container, SWT.None);
		controlComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		controlComposite.setLayout(new GridLayout());
		
		//TODO make a show original data checkbox
		
		Composite plotComposite = new Composite(container, SWT.None);
		plotComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		plotComposite.setLayout(new FillLayout());
		
		
		try {
			system = PlottingFactory.createPlottingSystem();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		system.createPlotPart(plotComposite, "Spectrum Wizard",actionBarWrapper,PlotType.XY,null);
		
		IDataset data = dataList.get(0).getxDataset();
		IDataset y = dataList.get(0).getyDatasets().get(0);
		
		system.createPlot1D(data, Arrays.asList(new IDataset[]{y}), null);
		
		double min = data.min().doubleValue();
		double max = data.max().doubleValue();
		
		XAxisBoxROI roi = new XAxisBoxROI(min, 0, max-min, 0, 0);
		
		try {
			IRegion region = system.createRegion("Crop range", RegionType.XAXIS);
			region.setROI(roi);
			region.setUserRegion(false);
			system.addRegion(region);
			region.addROIListener(new IROIListener.Stub() {
			
				
				@Override
				public void roiDragged(ROIEvent evt) {
					IROI roi = evt.getROI();
					if (roi instanceof RectangularROI) {
						process.setRange(new double[]{roi.getPoint()[0], roi.getPoint()[0]+((RectangularROI) roi).getLength(0)});
					}
				}
				
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<IContain1DData> process(List<IContain1DData> dataList) {
		// TODO Auto-generated method stub
		return process.process(dataList);
	}

}
