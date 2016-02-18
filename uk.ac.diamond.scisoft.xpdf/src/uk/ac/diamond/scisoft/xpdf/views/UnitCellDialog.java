package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

class UnitCellDialog extends Dialog {

	private Map<String, XPDFAtom> atoms;
	private XPDFGroupedTable groupedTable;
	
	protected UnitCellDialog(Shell parentShell) {
		super(parentShell);
		atoms = new HashMap<String, XPDFAtom>();
    	// All phases are ferrous titanate
		atoms.put("Fe1", new XPDFAtom(26, 0.5, new double[] {0.333, 0.333, 0.333}));
		atoms.put("Fe2", new XPDFAtom(26, 0.5, new double[] {0.667, 0.667, 0.667}));
		atoms.put("Ti1", new XPDFAtom(22, 0.5, new double[] {0.167, 0.167, 0.167}));
		atoms.put("Ti2", new XPDFAtom(22, 0.5, new double[] {0.833, 0.833, 0.833}));
		atoms.put("O1", new XPDFAtom(8, 0.5, new double[] {0.583, 0.917, 0.250}));
		atoms.put("O2", new XPDFAtom(8, 0.5, new double[] {0.917, 0.250, 0.583}));
		atoms.put("O3", new XPDFAtom(8, 0.5, new double[] {0.250, 0.583, 0.917}));
		atoms.put("O4", new XPDFAtom(8, 0.5, new double[] {-0.583, -0.917, -0.250}));
		atoms.put("O5", new XPDFAtom(8, 0.5, new double[] {-0.917, -0.250, -0.583}));
		atoms.put("O6", new XPDFAtom(8, 0.5, new double[] {-0.250, -0.583, -0.917}));
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(4, true));
		createUnitCellTable(container);
		return container;
	}
		
	private void createUnitCellTable(Composite parent) {		
		GridData atomGD = new GridData();
		atomGD.verticalAlignment = GridData.FILL;
		atomGD.horizontalSpan = 3;
		atomGD.grabExcessHorizontalSpace = true;
		atomGD.grabExcessVerticalSpace = true;
		atomGD.horizontalAlignment = GridData.FILL;
		
		groupedTable = new XPDFGroupedTable(parent, SWT.NONE);
		groupedTable.setLayoutData(atomGD);
		createColumns();
		groupedTable.setContentProvider(new AtomContentProvider());
		groupedTable.setInput(atoms);
	}
	
	private void createColumns() {
		List<String> groupNames = new ArrayList<String>();
		List<ColumnInterface> columnInterfaces;
		List<List<ColumnInterface>> groupedColumnInterfaces = new ArrayList<List<ColumnInterface>>();
		
		groupNames.add("Atom");
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new ElementColumnInterface());
		columnInterfaces.add(new LabelColumnInterface());
		groupedColumnInterfaces.add(columnInterfaces);		
		
		groupNames.add("Position");
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new PositionColumnInterface(0));
		columnInterfaces.add(new PositionColumnInterface(1));
		columnInterfaces.add(new PositionColumnInterface(2));
		groupedColumnInterfaces.add(columnInterfaces);		
		
		groupNames.add("");
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new OccupancyColumnInterface());
		columnInterfaces.add(new adpColumnInterface());
		groupedColumnInterfaces.add(columnInterfaces);		
		
		for (int iGroup = 0; iGroup < groupNames.size(); iGroup++) {
			groupedTable.createColumnGroup(groupNames.get(iGroup));
			for (int iColumn = 0; iColumn < groupedColumnInterfaces.get(iGroup).size(); iColumn++) {
				ColumnInterface colI = groupedColumnInterfaces.get(iGroup).get(iColumn);
				TableViewerColumn col = groupedTable.addColumn(groupNames.get(iGroup), SWT.NONE);
				col.getColumn().setText(colI.getName());
				groupedTable.setColumnWidth(col, colI.getWeight());
				col.setLabelProvider(colI.getLabelProvider());
				groupedTable.setColumnEditingSupport(col, colI);
				// Sorting on column header button selection. See PhaseGroupedTable for reference
				//				if (colI.getSelectionAdapter(this, col) != null) col.getColumn().addSelectionListener(colI.getSelectionAdapter(this, col));
			}
		}
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

	private class LabelledAtom {
		private XPDFAtom atom;
		private String label;
		public LabelledAtom(String label, XPDFAtom atom) {
			this.label = label;
			this.atom = atom;
		}
		public String XPDFLabel() {
			return label;
		}
		public XPDFAtom getAtom() {
			return atom;
		}
	}
	
	private class AtomContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			List<LabelledAtom> latom = new ArrayList<LabelledAtom>();
			for (Map.Entry<String, XPDFAtom> entry : atoms.entrySet()) {
				latom.add(new LabelledAtom(entry.getKey(), entry.getValue()));
			}
			LabelledAtom[] aatom = latom.toArray(new LabelledAtom[latom.size()]);
			return aatom;
		}
	}

	private interface ColumnInterface extends EditingSupportFactory {
//		public SelectionAdapter getSelectionAdapter(final UnitCellDialog uCD, final TableViewerColumn col);
		public ColumnLabelProvider getLabelProvider();
		public String getName();
		public int getWeight();
		public boolean presentAsUneditable(Object element);
	}
	static class DummyColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

//		@Override
//		public SelectionAdapter getSelectionAdapter(UnitCellDialog uCD,
//				TableViewerColumn col) {
//			return DummySelectionAdapter.get(uCD, col);
//		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					return "This space left intentionally blank";
				}
			};
		}

		@Override
		public String getName() {
			return "Column";
		}

		@Override
		public int getWeight() {
			return 10;
		}

		@Override
		public boolean presentAsUneditable(Object element) {
			return false;
		}
		
	}
	
	static class DummyEditingSupport extends EditingSupport {
		DummyEditingSupport(ColumnViewer v) {
			super(v);
		}
		@Override
		protected CellEditor getCellEditor(Object element) {
			return null;
		}
		@Override
		protected boolean canEdit(Object element) {
			return false;
		}
		@Override
		protected Object getValue(Object element) {
			return null;
		}
		@Override
		protected void setValue(Object element, Object value) {
		}
	}

	static class DummyLabelProvider extends ColumnLabelProvider {
		String text;
		public DummyLabelProvider(String text) {
			this.text = text;
		}
		@Override
		public String getText(Object element) {
			return text;
		}
	}

	static class ElementColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new DummyLabelProvider("Uuo");
		}

		@Override
		public String getName() {
			return "Element";
		}

		@Override
		public int getWeight() {
			return 10;
		}

		@Override
		public boolean presentAsUneditable(Object element) {
			return false;
		}
	}

	static class LabelColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new DummyLabelProvider("1");
		}

		@Override
		public String getName() {
			return "Label";
		}

		@Override
		public int getWeight() {
			return 10;
		}

		@Override
		public boolean presentAsUneditable(Object element) {
			return false;
		}
	}
	
	static class PositionColumnInterface implements ColumnInterface {

		static final String[] axisNames = {"x", "y", "z"};
		private int axisIndex;
		
		public PositionColumnInterface(int axisIndex) {
			this.axisIndex = axisIndex;
		}
		
		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new DummyLabelProvider("1");
		}

		@Override
		public String getName() {
			return axisNames[axisIndex];
		}

		@Override
		public int getWeight() {
			return 10;
		}

		@Override
		public boolean presentAsUneditable(Object element) {
			return false;
		}
	}

	static class OccupancyColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new DummyLabelProvider("♺");
		}

		@Override
		public String getName() {
			return "Occupancy";
		}

		@Override
		public int getWeight() {
			return 10;
		}

		@Override
		public boolean presentAsUneditable(Object element) {
			return false;
		}
	}

	static class adpColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new DummyLabelProvider("0");
		}

		@Override
		public String getName() {
			return "Displacement";
		}

		@Override
		public int getWeight() {
			return 10;
		}

		@Override
		public boolean presentAsUneditable(Object element) {
			return false;
		}
	}

}
