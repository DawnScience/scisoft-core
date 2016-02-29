/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Implements a dialog window for editing the unit cell of a phase.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class UnitCellDialog extends Dialog {

	private List<XPDFAtom> atoms;

	private UnitCellGroupedTable cellTable;
	
	private Button addAtomButton;
	private Button deleteAtomButton;
	private Button copyAtomButton;
	private Button clearAtomsButton;
	
	private Action addAtomAction;
	private Action deleteAtomAction;
	private Action copyAtomAction;
	private Action clearAtomsAction;
	
	protected UnitCellDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(4, true));
		GridData atomGD = new GridData();

		atomGD.verticalAlignment = SWT.FILL;
		atomGD.horizontalSpan = 3;
		atomGD.grabExcessHorizontalSpace = true;
		atomGD.grabExcessVerticalSpace = true;
		atomGD.horizontalAlignment = SWT.FILL;
		atomGD.verticalSpan = 4;
		
		Composite cellTableCompo = new Composite(container, SWT.BORDER);
		cellTableCompo.setLayoutData(atomGD);
		cellTableCompo.setLayout(new GridLayout(1, true));
		
		cellTable = new UnitCellGroupedTable(cellTableCompo, SWT.BORDER);
		cellTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		cellTable.setInput(atoms);

		createAtomButtons(container);
		createAtomActions();
		
		return container;
	}
		
	// Override to set the window text
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Unit Cell Editor");
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(640, 480);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
	
	/**
	 * Returns the present state of all atoms.
	 * @return Map between the atoms and their labels.
	 */
	public List<XPDFAtom> getAllAtoms() {
		return atoms;
	}

	/**
	 * Sets the atoms to be edited in the dialog.
	 * <p>
	 * Takes a copy of the List of atoms to be edited within the dialog.
	 * @param atoms
	 * 				A List of {@link XPDFAtom}s
	 */
	public void setAllAtoms(List<XPDFAtom> atoms) {
		this.atoms = new ArrayList<XPDFAtom>();
		for (XPDFAtom atom : atoms) {
			this.atoms.add(atom);
		}
//		this.atoms = atoms;
	}
	
	private void createAtomButtons(Composite parent) {
		addAtomButton = new Button(parent, SWT.PUSH);
		addAtomButton.setText("Add");
		addAtomButton.setToolTipText("Add a new atom to the unit cell");
		addAtomButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		addAtomButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				addAtomAction.run();
			}
		});
		copyAtomButton = new Button(parent, SWT.PUSH);
		copyAtomButton.setText("Copy");
		copyAtomButton.setToolTipText("Copy the selected atom");
		copyAtomButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		deleteAtomButton = new Button (parent, SWT.PUSH);
		deleteAtomButton.setText("Delete");
		deleteAtomButton.setToolTipText("Delete the selected atom");
		deleteAtomButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		clearAtomsButton = new Button(parent, SWT.PUSH);
		clearAtomsButton.setText("Clear");
		clearAtomsButton.setToolTipText("Clear all atoms");
		clearAtomsButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		clearAtomsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				clearAtomsAction.run();
			}
		});
	}

	private void createAtomActions() {
		addAtomAction = new Action() {
			@Override
			public void run() {
				atoms.add(new XPDFAtom());
				cellTable.refresh();
			}
		};
		addAtomAction.setText("Add");
		addAtomAction.setToolTipText("Add a new atom to the unit cell");
		
		clearAtomsAction = new Action() {
			@Override
			public void run() {
				atoms.clear();
				cellTable.refresh();
				
			}
		};
		clearAtomsAction.setText("Clear");
		clearAtomsAction.setToolTipText("Clear all atoms");
			
	}
}
