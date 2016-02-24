/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import uk.ac.diamond.scisoft.xpdf.views.XPDFPhase.LabelledAtom;

public class UnitCellGroupedTable {

	private static Map<String, XPDFAtom> defaultAtoms;
	private Map<String, XPDFAtom> atoms;
	private XPDFGroupedTable groupedTable;

	public UnitCellGroupedTable(Composite parent, int style) {

		if (defaultAtoms == null) {
			defaultAtoms = new HashMap<String, XPDFAtom>();
			// All phases are ferrous titanate
			defaultAtoms.put("Fe1", new XPDFAtom(26, 1.0, new double[] {0.333, 0.333, 0.333}));
			defaultAtoms.put("Fe2", new XPDFAtom(26, 1.0, new double[] {0.667, 0.667, 0.667}));
			defaultAtoms.put("Ti1", new XPDFAtom(22, 1.0, new double[] {0.167, 0.167, 0.167}));
			defaultAtoms.put("Ti2", new XPDFAtom(22, 1.0, new double[] {0.833, 0.833, 0.833}));
			defaultAtoms.put("O1", new XPDFAtom(8, 1.0, new double[] {0.583, 0.917, 0.250}));
			defaultAtoms.put("O2", new XPDFAtom(8, 1.0, new double[] {0.917, 0.250, 0.583}));
			defaultAtoms.put("O3", new XPDFAtom(8, 1.0, new double[] {0.250, 0.583, 0.917}));
			defaultAtoms.put("O4", new XPDFAtom(8, 1.0, new double[] {-0.583, -0.917, -0.250}));
			defaultAtoms.put("O5", new XPDFAtom(8, 1.0, new double[] {-0.917, -0.250, -0.583}));
			defaultAtoms.put("O6", new XPDFAtom(8, 1.0, new double[] {-0.250, -0.583, -0.917}));
		}		

		groupedTable = new XPDFGroupedTable(parent, SWT.NONE);
		createColumns();
		groupedTable.setContentProvider(new AtomContentProvider());

	}
	
	public void setLayoutData(Object layout) {
		groupedTable.setLayoutData(layout);
	}
	
	public void setInput(Map<String, XPDFAtom> atoms) {
		this.atoms = atoms;
		groupedTable.setInput(this.atoms);
	}

	public void refresh() {
		groupedTable.refresh();
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
		
		groupNames.add("‍");
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new OccupancyColumnInterface());
		columnInterfaces.add(new adpColumnInterface());
		groupedColumnInterfaces.add(columnInterfaces);		
		
		for (int iGroup = 0; iGroup < groupNames.size(); iGroup++) {
			groupedTable.createColumnGroup(groupNames.get(iGroup), iGroup == groupNames.size()-1);
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

	private class AtomContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return LabelledAtom.fromMap(atoms).toArray(new LabelledAtom[atoms.size()]);
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
			return new ColumnLabelProvider() {		
				
				private final String[] elementSymbol = { "n",
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

				@Override
				public String getText(Object element) {
					int atomicNumber = 0;
					if (element instanceof LabelledAtom )
						atomicNumber = ((LabelledAtom) element).getAtom().getAtomicNumber();
					else if (element instanceof XPDFAtom)
						atomicNumber = ((XPDFAtom) element).getAtomicNumber();
					return elementSymbol[atomicNumber];
				}
			};
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
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					if (element instanceof LabelledAtom) 
						return ((LabelledAtom) element).getLabel();
					else if (element instanceof XPDFAtom)
						return "-";
					else return "";
				}
			};
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
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					XPDFAtom atom;
					if (element instanceof LabelledAtom)
						atom = ((LabelledAtom) element).getAtom();
					else if (element instanceof XPDFAtom)
						atom = (XPDFAtom) element;
					else
						return "";
					return Double.toString(atom.getPosition()[axisIndex]);
				}
			};
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
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					XPDFAtom atom;
					if (element instanceof LabelledAtom)
						atom = ((LabelledAtom) element).getAtom();
					else if (element instanceof XPDFAtom)
						atom = (XPDFAtom) element;
					else
						return "";
					return Double.toString(atom.getOccupancy());
				}
			};
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
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					XPDFAtom atom;
					if (element instanceof LabelledAtom)
						atom = ((LabelledAtom) element).getAtom();
					else if (element instanceof XPDFAtom)
						atom = (XPDFAtom) element;
					else
						return "";
					return Double.toString(atom.getIsotropicDisplacement());
				}
			};
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
