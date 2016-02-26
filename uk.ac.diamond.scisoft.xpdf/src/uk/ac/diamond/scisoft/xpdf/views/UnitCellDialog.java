package uk.ac.diamond.scisoft.xpdf.views;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import uk.ac.diamond.scisoft.xpdf.views.XPDFPhase.LabelledAtom;

class UnitCellDialog extends Dialog {

	private Map<String, XPDFAtom> atoms;

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
		createPositionFields(container);
		createBUDropDown(container);
		createSUFields(container);
		createAnisoTable(container);
		
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
				atoms.put(Integer.toString(i), new XPDFAtom());
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
		//TODO: remove borders
		Composite elementCompo = new Composite(parent, SWT.BORDER);
		elementCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		elementCompo.setLayout(new GridLayout(2, false));
		Label elementLabel = new Label(elementCompo, SWT.BORDER);
		elementLabel.setText("Element:");
		elementLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
		AbstractListViewer elementViewer = new ComboViewer(elementCompo, SWT.BORDER);
		elementViewer.getControl().setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final String[] elementSymbol = { "?",
		"H","He","Li","Be","B","C","N","O","F","Ne",
		"Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca",
		"Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn",
		"Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
        "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn",
        "Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd",
        "Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb",
        "Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg",
        "Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th",
        "Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm"};

		elementViewer.add(elementSymbol);
		
		Composite labelCompo = new Composite(parent, SWT.BORDER);
		labelCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		labelCompo.setLayout(new GridLayout(2, false));
		Label labelLabel = new Label(labelCompo, SWT.BORDER);
		labelLabel.setText("Label:");
		labelLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
		Text labelText = new Text(labelCompo, SWT.BORDER);
		labelText.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		
		Composite occupancyCompo = new Composite(parent, SWT.BORDER);
		occupancyCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		occupancyCompo.setLayout(new GridLayout(2, false));
		Label occupancyLabel = new Label(occupancyCompo, SWT.BORDER);
		occupancyLabel.setText("Occupancy:");
		occupancyLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
		Text occupancyText = new Text(occupancyCompo, SWT.BORDER);
		occupancyText.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		occupancyText.setText("1");
		
		Composite emptyCompo = new Composite(parent, SWT.BORDER);
		emptyCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		emptyCompo.setSize(occupancyCompo.getSize());

	}
	
	private void createPositionFields(Composite parent) {
		Composite xCompo = new Composite(parent, SWT.BORDER);
		xCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		xCompo.setLayout(new GridLayout(2, false));
		Label xLabel = new Label(xCompo, SWT.BORDER);
		xLabel.setText("x:");
		xLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text xText = new Text(xCompo, SWT.BORDER);
		xText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		xText.setText("0");
		
		Composite yCompo = new Composite(parent, SWT.BORDER);
		yCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		yCompo.setLayout(new GridLayout(2, false));
		Label yLabel = new Label(yCompo, SWT.BORDER);
		yLabel.setText("y:");
		yLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text yText = new Text(yCompo, SWT.BORDER);
		yText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		yText.setText("0");

		Composite zCompo = new Composite(parent, SWT.BORDER);
		zCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		zCompo.setLayout(new GridLayout(2, false));
		Label zLabel = new Label(zCompo, SWT.BORDER);
		zLabel.setText("z:");
		zLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text zText = new Text(zCompo, SWT.BORDER);
		zText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		zText.setText("0");
	}
	
	private void createBUDropDown(Composite parent) {
		Composite bUCompo = new Composite(parent, SWT.BORDER);
		bUCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		bUCompo.setLayout(new GridLayout(2, false));
		Label bULabel = new Label(bUCompo, SWT.BORDER);
		bULabel.setText("B/U:");
		bULabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
		AbstractListViewer bUViewer = new ComboViewer(bUCompo, SWT.NONE);
		bUViewer.getControl().setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		bUViewer.add(new String[]{"B", "U"});
		
		
	}

	private void createSUFields(Composite parent) {
		final int textBoxWidth = 200;
		Composite suxCompo = new Composite(parent, SWT.BORDER);
		suxCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		suxCompo.setLayout(new GridLayout(2, false));
		Label suxLabel = new Label(suxCompo, SWT.BORDER);
		suxLabel.setText("s.u. (x):");
		suxLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text suxText = new Text(suxCompo, SWT.BORDER);
		suxText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		suxText.setSize(textBoxWidth, suxText.getSize().y);
		suxText.setText("0");

		Composite suyCompo = new Composite(parent, SWT.BORDER);
		suyCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		suyCompo.setLayout(new GridLayout(2, false));
		Label suyLabel = new Label(suyCompo, SWT.BORDER);
		suyLabel.setText("s.u. (y):");
		suyLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text suyText = new Text(suyCompo, SWT.BORDER);
		suyText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		suyText.setSize(textBoxWidth, 0);
		suyText.setText("0");

		Composite suzCompo = new Composite(parent, SWT.BORDER);
		suzCompo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		suzCompo.setLayout(new GridLayout(2, false));
		Label suzLabel = new Label(suzCompo, SWT.BORDER);
		suzLabel.setText("s.u. (z):");
		suzLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text suzText = new Text(suzCompo, SWT.BORDER);
		suzText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		suzText.setText("0");

	}

	public void createAnisoTable(Composite parent) {
		Composite anisoCompo = new Composite(parent, SWT.BORDER);
		anisoCompo.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true, 3, 2));
		anisoCompo.setLayout(new TableColumnLayout());
		TableViewer anisoViewer = new TableViewer(anisoCompo, SWT.NO_SCROLL);
		
		TableColumnLayout tCL = (TableColumnLayout) anisoCompo.getLayout();
		
		TableViewerColumn tVCX = new TableViewerColumn(anisoViewer, SWT.NONE);
		tCL.setColumnData(tVCX.getColumn(), new ColumnWeightData(1, false));
		TableViewerColumn tVCY = new TableViewerColumn(anisoViewer, SWT.NONE);
		tCL.setColumnData(tVCY.getColumn(), new ColumnWeightData(1, false));
		TableViewerColumn tVCZ = new TableViewerColumn(anisoViewer, SWT.NONE);
		tCL.setColumnData(tVCZ.getColumn(), new ColumnWeightData(1, false));
	}
}
