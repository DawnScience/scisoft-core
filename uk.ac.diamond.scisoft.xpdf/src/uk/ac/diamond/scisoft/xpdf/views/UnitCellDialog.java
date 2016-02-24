package uk.ac.diamond.scisoft.xpdf.views;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import uk.ac.diamond.scisoft.xpdf.views.XPDFPhase.LabelledAtom;

class UnitCellDialog extends Dialog {

	private Map<String, XPDFAtom> atoms;
//	private XPDFGroupedTable groupedTable;

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
		
		Composite cellTableCompo = new Composite(container, SWT.NONE);
		cellTableCompo.setLayoutData(atomGD);
		cellTableCompo.setLayout(new GridLayout(1, true));
		
		cellTable = new UnitCellGroupedTable(cellTableCompo, SWT.NONE);
		cellTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		cellTable.setInput(atoms);

		createAtomButtons(container);
		createAtomActions();
		
		createFirstRowFields(container);
//		createPositionFields();
//		createBUDropDown();
//		createSUFields();
//		createAnisoTable();
		
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
	
	public Map<String, XPDFAtom> getAllAtoms() {
		return atoms;
	}

	public void setAllAtoms(Map<String, XPDFAtom> atoms) {
		this.setAllAtoms(LabelledAtom.fromMap(atoms));
	}
	
	public void setAllAtoms(Collection<LabelledAtom> atoms) {
		this.atoms = new HashMap<String, XPDFAtom>();
		for (LabelledAtom atom : atoms) {
			this.atoms.put(atom.getLabel(), atom.getAtom());
		}
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
				int i;
				for (i = 0; atoms.containsKey(Integer.toString(i)); i++)
					;
				atoms.put(Integer.toString(i), new XPDFAtom(0));
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
	
	private void createFirstRowFields(Composite parent) {
		
	}
}
