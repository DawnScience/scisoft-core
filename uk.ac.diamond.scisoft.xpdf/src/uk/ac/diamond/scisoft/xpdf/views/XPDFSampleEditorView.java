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
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
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
	
	private Action loadTestDataAction;
	private Action simPDFAction;
	private Action saveAction;
	private Action newCifAction;
	private Action newEraAction;
	private Action newBlankAction;
	private Action pointBreakAction;
	private Action deleteAction;
	private Action clearAction;
		
//	private Map<ColumnGroup, TableViewer> groupViewers;
//	private List<TableViewer> groupViewers; // To MyGroupedTable
//	private List<String> groupNames; // To MyGroupedTable
	
//	private boolean propagateSelectionChange; // To MyGroupedTable

	private SortedSet<Integer> usedIDs; // The set of assigned ID numbers. Should come from the database eventually?	

	private SampleGroupedTable sampleTable;

	// members that will be moved to within sampleTable
//	List<String> groupNamesST; // To SampleGroupedTable
//	List<List<String>> groupedColumnNames; // To SampleGroupedTable
//	List<List<Integer>> groupedColumnWeights; // To SampleGroupedTable
	
	public XPDFSampleEditorView() {
	}
	
	public XPDFSampleEditorView(Composite parent, int style) {
		createPartControl(parent);
	}

	@Override
	public void createPartControl(Composite parent) {
		
		// Overall composite of the view
		Composite sampleTableCompo = new Composite(parent, SWT.BORDER);
		sampleTableCompo.setLayout(new FormLayout());
		
		sampleTable = new SampleGroupedTable(this, sampleTableCompo, SWT.NONE);
		Composite buttonCompo = new Composite(sampleTableCompo, SWT.NONE);

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
		
		createActions();
		createLoadButtons(buttonCompo);
		createCentreButtons(buttonCompo);
		createRHSButtons(buttonCompo);
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
		newBlankAction = new Action() {
			@Override
			public void run() {
				XPDFSampleParameters blankSample = new XPDFSampleParameters();
				blankSample.setId(generateUniqueID());
				sampleTable.add(blankSample);
			}
		};
		newBlankAction.setText("New sample");
		newBlankAction.setToolTipText("Add an empty sample to the table");
		newBlankAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		
		// clear all the data from the list
		clearAction = new Action() {
			@Override
			public void run() {
				sampleTable.clear();
				usedIDs.clear();
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
		
		hookIntoContextMenu();
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
		int bottomMargin = -10;
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
		
		clrButton = new Button(parent, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(50);
		formData.top = new FormAttachment(delButton, offset);
		formData.bottom = new FormAttachment(100, bottomMargin);
		clrButton.setLayoutData(formData);
		clrButton.setText(clearAction.getText());
		clrButton.setToolTipText(clearAction.getToolTipText());
		clrButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				clearAction.run();
			}
		});
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

	@Override
	public void setFocus() {
		sampleTable.setFocus();
		// Can all the TableViewers have focus?
//		for (TableViewer iTV : groupViewers ) {
//			iTV.getControl().setFocus();
//		}
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
		
//		for (TableViewer iTV : groupViewers) {
//			Menu popupMenu = menuMan.createContextMenu(iTV.getControl());
//			iTV.getControl().setMenu(popupMenu);
//			getSite().registerContextMenu(menuMan, iTV);
//		}
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(loadTestDataAction);
		manager.add(newBlankAction);
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

	// Generate a new id
	int generateUniqueID() {
		final int lowestID = 154;
		if (usedIDs == null)
			usedIDs = new TreeSet<Integer>();
		int theID = (usedIDs.isEmpty()) ? lowestID : usedIDs.last()+1;
		usedIDs.add(theID);
		return theID;
	}
	
	// Sample data with integer multiplicities
	class LoadTestDataAction extends Action {

		@Override
		public void run() {
			
			// barium titanate
			XPDFSampleParameters bto = new XPDFSampleParameters();
			bto.setName("Barium Titanate");
			bto.setId(generateUniqueID());
			bto.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"BTO", "CaTiO3"})));
			bto.setComposition("BaTiO3"); // Should be "Ba0.9Ca0.1TiO3"
			bto.setDensity(3.71);
			// Packing fraction as default
			bto.setSuggestedEnergy(76.6);
			bto.setSuggestedCapDiameter(1.0);
			bto.setBeamState("76.6 Hi Flux");
			bto.setContainer("0.3 mm B");
			
			sampleTable.add(bto);
			
			// rutile
			XPDFSampleParameters rutile = new XPDFSampleParameters();
			rutile.setName("Rutile");
			rutile.setId(generateUniqueID());
			rutile.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"TiO2"})));
			rutile.setComposition("TiO2");
			rutile.setDensity(6.67);
			// Packing fraction as default
			rutile.setSuggestedEnergy(76.6);
			rutile.setSuggestedCapDiameter(5.0);
			rutile.setBeamState("76.6 Hi Flux");
			rutile.setContainer("0.5 mm B");
			
			sampleTable.add(rutile);
			
			// and something else
			XPDFSampleParameters explodite = new XPDFSampleParameters();
			explodite.setName("Explodite");
			explodite.setId(generateUniqueID());
			explodite.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"LF", "O"})));
			explodite.setComposition("K2S4P");
			explodite.setDensity(1.1);
			//packing fraction as default
			explodite.setSuggestedEnergy(76.6);
			explodite.setSuggestedCapDiameter(5.0);
			explodite.setBeamState("76.6 Hi Flux");
			explodite.setContainer("0.5 mm");
			
			sampleTable.add(explodite);
		}
	}
}
