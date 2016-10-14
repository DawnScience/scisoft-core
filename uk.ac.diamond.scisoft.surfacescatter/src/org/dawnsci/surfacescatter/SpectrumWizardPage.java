/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.List;

import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.dawnsci.spectrum.ui.file.IContain1DData;
import org.dawnsci.spectrum.ui.utils.PolynomialInterpolator1D;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;


public class SpectrumWizardPage extends WizardPage {
	
	IContain1DData data1, data2;
	IDataset xdata, ydata1, ydata2;

	protected SpectrumWizardPage() {
		super("Processing Wizard page");
		// TODO Auto-generated constructor stub
	}
	
	public void add1DDatas(IContain1DData data1, IContain1DData data2){
		this.data1 = data1;
		this.data2 = data2;
		
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		setControl(container);
		
		Label label = new Label(container,SWT.None); 
		label.setText("Subtraction wizard page");
		label.setLayoutData(new GridData());
		ActionBarWrapper actionBarWrapper = ActionBarWrapper.createActionBars(container, null);
		Composite controlComposite = new Composite(container, SWT.None);
		controlComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		controlComposite.setLayout(new GridLayout());
		//TODO MAke a single selection listener for all widgets
		//TODO make a show original data checkbox
		Button radioA = new Button(controlComposite, SWT.RADIO);
		radioA.setText("a-b*scale");
		radioA.setSelection(true);
		
		radioA.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.err.println("test");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		
		Button radioB = new Button(controlComposite, SWT.RADIO);
		radioB.setText("b-a*scale");
		
		label = new Label(controlComposite,SWT.None); 
		label.setText("Scale:");
		
		Spinner spinner = new Spinner(controlComposite, SWT.None);
		
		spinner.setSelection(1);
		spinner.setDigits(4);
		spinner.setMaximum(Integer.MAX_VALUE);
		spinner.setMinimum(Integer.MIN_VALUE);
		
		spinner.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Composite plotComposite = new Composite(container, SWT.None);
		plotComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		plotComposite.setLayout(new FillLayout());
		
		IPlottingSystem<Composite> system;
		try {
			system = PlottingFactory.createPlottingSystem();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		system.createPlotPart(plotComposite, "Spectrum Wizard",actionBarWrapper,PlotType.XY,null);
		
		IDataset x1 = data1.getxDataset();
		IDataset x2 = data2.getxDataset();
		
		List<IDataset> datasets = new ArrayList<IDataset>();
		
		if (!x1.equals(x2)) {
			int[] common = PolynomialInterpolator1D.getCommonRangeIndicies(x1,x2);
			int[] start = new int[]{common[0]};
			int[] stop = new int[]{common[1]};
			
			xdata = x1.getSlice(start,stop,null);
			ydata1 = data1.getyDatasets().get(0).getSlice(start,stop,null);
			ydata2 = PolynomialInterpolator1D.interpolate(data2.getxDataset(), data2.getyDatasets().get(0), xdata);
			
		} else {
			xdata = x1;
			ydata1 = data1.getyDatasets().get(0);
			ydata2 = data2.getyDatasets().get(0);
		}
		
		datasets.add(ydata1);
		datasets.add(ydata2);
		datasets.add(Maths.subtract(ydata2, ydata1));
		system.createPlot1D(xdata, datasets,null);
		
	}
}
