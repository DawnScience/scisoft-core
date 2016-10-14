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
import org.dawnsci.spectrum.ui.processing.SubtractionProcess;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;


public class SpectrumSubtractionWizardPage extends WizardPage implements ISpectrumWizardPage {
	
	List<IContain1DData> dataList;
	IDataset xdata, ydata1, ydata2;
	IPlottingSystem<Composite> system;
	double scale = 1;
	Job subtractionJob;
	
	SubtractionProcess process;

	public SpectrumSubtractionWizardPage(IContain1DData subtrahend,List<IContain1DData> dataList ) {
		super("Processing Wizard page");
		process = new SubtractionProcess(subtrahend);
		this.dataList = dataList;
		
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		setControl(container);
		setTitle("Subtraction wizard page");
		setDescription("Adjust scale value for subtraction");
		Label label = new Label(container,SWT.None); 
		label.setText("");
		label.setLayoutData(new GridData());
		ActionBarWrapper actionBarWrapper = ActionBarWrapper.createActionBars(container, null);
		Composite controlComposite = new Composite(container, SWT.None);
		controlComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		controlComposite.setLayout(new GridLayout());
		
		//TODO make a show original data checkbox
		
		label = new Label(controlComposite,SWT.None); 
		label.setText("Scale:");
		
		Spinner spinner = new Spinner(controlComposite, SWT.None);
		spinner.setDigits(4);
		
		spinner.setMaximum(Integer.MAX_VALUE);
		spinner.setMinimum(1);
		spinner.setSelection((int)Math.pow(10, 4));
		spinner.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = ((Spinner)e.getSource()).getSelection();
		        int digits = ((Spinner)e.getSource()).getDigits();
		        scale = (selection / Math.pow(10, digits));
		        update();
			}
			
		});
		
		spinner.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				e.doit = false; 
			}
		});
		
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
		
		update();
		
	}
	
	
	private void update() {
		
		if (subtractionJob == null) {
			subtractionJob = new Job("subtraction") {
				
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					process.setScale(scale);
					
			        List<IContain1DData> out = process.process(dataList);
			        
			        List<IDataset> yds = out.get(0).getyDatasets();
			        
			        int i = 0;
			        for (IDataset ds : yds) ds.setName("difference_"+ i++);
			        
			        IDataset y = dataList.get(0).getyDatasets().get(0).clone();
			        y.setName("data");
			        IDataset sub = process.getSubtrahend().getyDatasets().get(0).clone();
			        Dataset suba = DatasetUtils.convertToDataset(sub);
			        suba.imultiply(scale);
			        suba.setName("subtrahend");
					//system.clear();
					system.updatePlot1D(dataList.get(0).getxDataset(),Arrays.asList(new IDataset[]{y}) ,null);
					system.updatePlot1D(process.getSubtrahend().getxDataset(), Arrays.asList(new IDataset[]{suba}) , null);
					system.updatePlot1D(out.get(0).getxDataset(),out.get(0).getyDatasets() ,null);
					system.repaint();
					return Status.OK_STATUS;
				}
			};
		}
		
		subtractionJob.schedule();
	}
	
	public void setVisible(boolean visible) {
		   super.setVisible(visible);
		   // Set the initial field focus
		   if (visible) {
		      system.setFocus();
		   }
		}

	@Override
	public List<IContain1DData> process(List<IContain1DData> dataList) {
		return process.process(dataList);
	}
}
