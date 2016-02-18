/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class XPDFSampleEditorView extends ViewPart {
	
	private Button cifButton;
	private Button eraButton;
	private Button simButton;
	private Button savButton;
	private Button delButton;
	private Button clrButton;
	
	private Button cifPhaseButton;
//	private Button eraPhaseButton;
	private Button delPhaseButton;
	
	private Action loadTestDataAction;
	private Action simPDFAction;
	private Action saveAction;
	private Action newCifAction;
	private Action newEraAction;
	private Action newSampleAction;
	private Action newContainerAction;
	private Action pointBreakAction;
	private Action deleteAction;
	private Action clearAction;

	private Action deletePhaseAction;
	private Action newPhaseAction;
	
	private SampleGroupedTable sampleTable;
	private PhaseGroupedTable phaseTable;

	public XPDFSampleEditorView() {
	}
	
	public XPDFSampleEditorView(Composite parent, int style) {
		createPartControl(parent);
	}

	@Override
	public void createPartControl(Composite parent) {
		
		SashForm sampleEditorComposite = new SashForm(parent, SWT.VERTICAL);
		
		// Overall composite of the view
		Composite sampleTableCompo = new Composite(sampleEditorComposite, SWT.BORDER);
		sampleTableCompo.setLayout(new FormLayout());
		Composite phaseTableCompo = new Composite(sampleEditorComposite, SWT.BORDER);
		phaseTableCompo.setLayout(new FormLayout());
		
		sampleEditorComposite.setWeights(new int[]{1,1});
		
		sampleTable = new SampleGroupedTable(sampleTableCompo, SWT.NONE);
		Composite buttonCompo = new Composite(sampleTableCompo, SWT.NONE);
		phaseTable = new PhaseGroupedTable(phaseTableCompo, SWT.NONE);
		Composite phaseButtonCompo = new Composite(phaseTableCompo, SWT.NONE);
		
		sampleTable.setPhaseTable(phaseTable);
		phaseTable.setSampleTable(sampleTable);
		
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(buttonCompo);
		//		formData.height = 800;
		sampleTable.setLayoutData(formData);
		
		formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
//		formData.top = new FormAttachment(sampleTable);
		formData.bottom = new FormAttachment(100);
		buttonCompo.setLayoutData(formData);
		buttonCompo.setLayout(new FormLayout());
		
		formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(phaseButtonCompo);
		formData.top = new FormAttachment(0);
//		formData.top = new FormAttachment(buttonCompo);
		phaseTable.setLayoutData(formData);
		
		formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		phaseButtonCompo.setLayoutData(formData);
		phaseButtonCompo.setLayout(new FormLayout());
		
		createActions();
		createLoadButtons(buttonCompo);
		createCentreButtons(buttonCompo);
		createRHSButtons(buttonCompo);
		
		createPhaseLoadButtons(phaseButtonCompo);
		createPhaseCentreButtons(phaseButtonCompo);
		
		sampleTable.setInput();
		phaseTable.setInput();
	}

		
	private void createActions() {
		// load the nonsense test data
		loadTestDataAction = new LoadTestDataAction();
		loadTestDataAction.setText("Load test data");
		loadTestDataAction.setToolTipText("Load the test data");
		loadTestDataAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE));
		
		// do nothing but allow a breakpoint hook
		pointBreakAction = new Action() {
			@Override
			public void run() {
				sampleTable.get(0).getDensity();
			}
		};
		pointBreakAction.setToolTipText("Ze goggles, zey...");
		pointBreakAction.setText("...do nothing");
		pointBreakAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_CLEAR));
		
		// save the data
		saveAction = new Action() {
			@Override
			public void run() {
				for (XPDFSampleParameters sample : sampleTable.getAll())
					System.err.println(sample.toString());
			}
		};
		saveAction.setToolTipText("Save data to stderr");
		saveAction.setText("Save");
		saveAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT));
		
		// add a new entry to the end of the list
		newSampleAction = new Action() {
			@Override
			public void run() {
				XPDFSampleParameters blankSample = new XPDFSampleParameters(true);
//				blankSample.setId(generateUniqueID());
				sampleTable.add(blankSample);
			}
		};
		newSampleAction.setText("New sample");
		newSampleAction.setToolTipText("Add an empty sample to the table");
		newSampleAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		
		newContainerAction = new Action() {
			@Override
			public void run() {
				sampleTable.add(new XPDFSampleParameters(false));
			}
		};
		newContainerAction.setText("New container");
		newContainerAction.setToolTipText("Add an empty container to the table");
		newContainerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		
		// clear all the data from the list
		clearAction = new Action() {
			@Override
			public void run() {
				sampleTable.clear();
			}
		};
		clearAction.setText("Clear");
		clearAction.setToolTipText("Clear all entries");
		clearAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_CLEAR));
		
		// delete a selected entry
		deleteAction = new DeleteSampleAction();
		deleteAction.setText("Delete");
		deleteAction.setToolTipText("Delete selected samples");
		deleteAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
		
		// simulate a PDF
		simPDFAction = new SimulatePDFAction();
		simPDFAction.setText("Simulate");
		simPDFAction.setToolTipText("Simulate a PDF for each of the selected samples");

		// Phase Table Actions
		deletePhaseAction = new DeletePhaseAction();
		deletePhaseAction.setText("Delete unused");
		deletePhaseAction.setToolTipText("Delete selected (or all) phases not used by a sample.");
		
		newPhaseAction = new NewPhaseAction();
		newPhaseAction.setText("New");
		newPhaseAction.setToolTipText("New blank phase");
		
		hookIntoContextMenu();
		hookIntoPhaseContextMenu();
	}

	private void createRHSButtons(Composite parent) {
		int rightMargin = -10;
		int topMargin = 10;
//		Composite stCompo = compoAbove.getParent();

		simButton = new Button(parent, SWT.NONE);
		FormData formData= new FormData();
		formData.right = new FormAttachment(100, rightMargin);
		formData.top = new FormAttachment(0, topMargin);
		simButton.setLayoutData(formData);
		simButton.setText("Simulate PDF");
		simButton.setToolTipText("Produce a simulated pair distribution function for the selected sample");
		simButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				simPDFAction.run();
			}
		});
		
		savButton = new Button(parent, SWT.NONE);
		formData = new FormData();
		formData.right = new FormAttachment(100, rightMargin);
		formData.top = new FormAttachment(simButton, topMargin);
		formData.bottom = new FormAttachment(100, -topMargin);
		savButton.setLayoutData(formData);
		savButton.setText("Save");
		savButton.setToolTipText("Save the sample data to file (or the database?)");
		savButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				saveAction.run();
			}
		});
	}

	private void createCentreButtons(Composite parent) {
		int offset = 10;
//		int bottomMargin = -10;
//		Composite stCompo = compoAbove.getParent();
		
		delButton = new Button(parent, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(50);
		formData.top = new FormAttachment(0, offset);
		delButton.setLayoutData(formData);
		delButton.setText(deleteAction.getText());
		delButton.setToolTipText(deleteAction.getToolTipText());
		delButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				deleteAction.run();
			}
		});
		
//		clrButton = new Button(parent, SWT.NONE);
//		formData = new FormData();
//		formData.left = new FormAttachment(50);
//		formData.top = new FormAttachment(delButton, offset);
//		formData.bottom = new FormAttachment(100, bottomMargin);
//		clrButton.setLayoutData(formData);
//		clrButton.setText(clearAction.getText());
//		clrButton.setToolTipText(clearAction.getToolTipText());
//		clrButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent event) {
//				clearAction.run();
//			}
//		});
	}
	
	private void createLoadButtons(Composite parent) {
		int leftMargin = 10;
		int topMargin = 10;
//		Composite stCompo = compoAbove.getParent();

		cifButton = new Button(parent, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, leftMargin);
		formData.top = new FormAttachment(0, topMargin);
		cifButton.setLayoutData(formData);
		cifButton.setText("New sample from CIF file");
		cifButton.setToolTipText("Create new sample from the data contained in a specified Crystallographic Information File.");

		eraButton = new Button(parent, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(0, leftMargin);
		formData.top = new FormAttachment(cifButton, topMargin);
		formData.bottom = new FormAttachment(100, -topMargin);
		eraButton.setLayoutData(formData);
		eraButton.setText("New sample from ERA file");
		eraButton.setToolTipText("Create a new sample from the data contained in a specified ERA file.");
	}

	private void createPhaseLoadButtons(Composite parent) {
		int leftMargin = 10;
		int topMargin = 10;
//		Composite stCompo = compoAbove.getParent();

		cifPhaseButton = new Button(parent, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, leftMargin);
		formData.top = new FormAttachment(0, topMargin);
		formData.bottom = new FormAttachment(100, -topMargin);
		cifPhaseButton.setLayoutData(formData);
		cifPhaseButton.setText("New phase(s) from CIF file");
		cifPhaseButton.setToolTipText("Create new phase(s) from the data contained in a specified Crystallographic Information File.");
		
	}
	
	private void createPhaseCentreButtons(Composite parent) {
		int offset = 10;
//		int bottomMargin = -10;
//		Composite stCompo = compoAbove.getParent();
		
		delPhaseButton = new Button(parent, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(50);
		formData.top = new FormAttachment(0, offset);
		delPhaseButton.setLayoutData(formData);
		delPhaseButton.setText(deletePhaseAction.getText());
		delPhaseButton.setToolTipText(deletePhaseAction.getToolTipText());
		delPhaseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				deletePhaseAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		sampleTable.setFocus();
	}
	
	// Hook actions into the context menu
	private void hookIntoContextMenu() {
		MenuManager menuMan = new MenuManager("#PopupMenu");
		menuMan.setRemoveAllWhenShown(true);
		menuMan.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);				
			}
		});
		
		sampleTable.createContextMenu(menuMan);
		
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(loadTestDataAction);
		manager.add(newSampleAction);
		manager.add(newContainerAction);
		manager.add(saveAction);
		manager.add(deleteAction);
		manager.add(clearAction);
		manager.add(new Separator("Data"));
		manager.add(simPDFAction);
		manager.add(new Separator("Debug"));
		manager.add(pointBreakAction);
	}	
	
	// Action class that simulates the pair-distribution function of the given sample.
	// At the moment, this plots the NIST ceria standard PDF
	class SimulatePDFAction extends Action {
		@Override
		public void run() {
			List<XPDFSampleParameters> selectedXPDFParameters = sampleTable.getSelectedSamples();
			if (selectedXPDFParameters.isEmpty()) return;
			// Get the pair-distribution function data for each sample
			List<Dataset> simulatedPDFs = new ArrayList<Dataset>(selectedXPDFParameters.size());
			for (int i = 0; i < selectedXPDFParameters.size(); i++)
				simulatedPDFs.add(i, selectedXPDFParameters.get(i).getSimulatedPDF());
			// Because the XPDF Sample View is not necessarily going to be 
			// used in a Perspective containing a plot view, and because there
			// may be many selected samples, open an new plot window, and plot
			// the data there.
			
		// TODO: Do something with the data
			for (Dataset pdf : simulatedPDFs) {
				XPDFPopUpPlotDialog popUp = new XPDFPopUpPlotDialog(XPDFSampleEditorView.this.getViewSite().getShell(), null, (IDataset) pdf);
				popUp.open();
//				popUp.createDialogArea(XPDFSampleView.this.getViewSite().getShell().getParent());
			}
		}
	}

	private class DeleteSampleAction extends Action {
		@Override
		public void run() {
			List<XPDFSampleParameters> selectedXPDFParameters = sampleTable.getSelectedSamples();
			if (selectedXPDFParameters.isEmpty()) return;
			sampleTable.removeAll(selectedXPDFParameters);
		}
	}

	private void hookIntoPhaseContextMenu() {
		MenuManager menuMan = new MenuManager("#PopupMenu");
		menuMan.setRemoveAllWhenShown(true);
		menuMan.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillPhaseContextMenu(manager);				
			}
		});
		
		phaseTable.createContextMenu(menuMan);
		
	}

	protected void fillPhaseContextMenu(IMenuManager manager) {
		manager.add(newPhaseAction);
		manager.add(deletePhaseAction);
	}	
	
	
	private class DeletePhaseAction extends Action {
		@Override
		public void run() {
			List<XPDFPhase> selectedPhases = phaseTable.getSelectedPhases();
			if (selectedPhases.isEmpty()) selectedPhases = phaseTable.getAll();
			List<XPDFPhase> usedPhases = sampleTable.getAllPhases();
			List<XPDFPhase> unusedPhases = new ArrayList<XPDFPhase>();
			for (XPDFPhase phase : selectedPhases)
				if (!usedPhases.contains(phase))
					unusedPhases.add(phase);
			phaseTable.removeAll(unusedPhases);
			phaseTable.refresh();
		}
	}
	
	private class NewPhaseAction extends Action {
		@Override
		public void run() {
			List<XPDFPhase> newPhases = new ArrayList<XPDFPhase>();
			newPhases.add(new XPDFPhase());
			phaseTable.addPhases(newPhases);
		}
	}
	
//	// Generate a new id
//	int generateUniqueID() {
//		final int lowestID = 154;
//		if (usedIDs == null)
//			usedIDs = new TreeSet<Integer>();
//		int theID = (usedIDs.isEmpty()) ? lowestID : usedIDs.last()+1;
//		usedIDs.add(theID);
//		return theID;
//	}
	
	// Sample data with integer multiplicities
	class LoadTestDataAction extends Action {

		@Override
		public void run() {
			// barium titanate
			sampleTable.add(SampleTestData.createTestSample("Barium Titanate"));
			// rutile
			sampleTable.add(SampleTestData.createTestSample("Rutile"));
			// ceria
			sampleTable.add(SampleTestData.createTestSample("ceria"));
			// quartz capillary
			sampleTable.add(SampleTestData.createTestSample("Quartz Capillary"));
		
		}
	}
}
