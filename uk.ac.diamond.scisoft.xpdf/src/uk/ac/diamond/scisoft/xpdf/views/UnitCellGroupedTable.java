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
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * Grouped table to edit the atoms in a unit cell
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class UnitCellGroupedTable {

	private static List<XPDFAtom> defaultAtoms;
	private List<XPDFAtom> atoms;
	private XPDFGroupedTable groupedTable;

	/**
	 * Constructor for the table.
	 * @param parent
	 * 				Composite into which the table will be placed. Does not
	 * need a {@link TableColumnLayout}. 
	 * @param style
	 * 				Style bits to apply to the table.
	 */
	public UnitCellGroupedTable(Composite parent, int style) {

		if (defaultAtoms == null) {
			defaultAtoms = new ArrayList<XPDFAtom>();
			// All phases are ferrous titanate
			defaultAtoms.add(new XPDFAtom("Fe1", 26, 1.0, new double[] {0.333, 0.333, 0.333}));
			defaultAtoms.add(new XPDFAtom("Fe2", 26, 1.0, new double[] {0.667, 0.667, 0.667}));
			defaultAtoms.add(new XPDFAtom("Ti1", 22, 1.0, new double[] {0.167, 0.167, 0.167}));
			defaultAtoms.add(new XPDFAtom("Ti2", 22, 1.0, new double[] {0.833, 0.833, 0.833}));
			defaultAtoms.add(new XPDFAtom("O1", 8, 1.0, new double[] {0.583, 0.917, 0.250}));
			defaultAtoms.add(new XPDFAtom("O2", 8, 1.0, new double[] {0.917, 0.250, 0.583}));
			defaultAtoms.add(new XPDFAtom("O3", 8, 1.0, new double[] {0.250, 0.583, 0.917}));
			defaultAtoms.add(new XPDFAtom("O4", 8, 1.0, new double[] {-0.583, -0.917, -0.250}));
			defaultAtoms.add(new XPDFAtom("O5", 8, 1.0, new double[] {-0.917, -0.250, -0.583}));
			defaultAtoms.add(new XPDFAtom("O6", 8, 1.0, new double[] {-0.250, -0.583, -0.917}));
		}		

		groupedTable = new XPDFGroupedTable(parent, SWT.NONE);
		createColumns();
		groupedTable.setContentProvider(new AtomContentProvider());

	}
	
	/**
	 * Sets the layout data to apply to the table as a whole
	 * @param layout
	 * 				the layout to be applied
	 */
	public void setLayoutData(Object layout) {
		groupedTable.setLayoutData(layout);
	}
	
	/**
	 * Sets the data source of the table.
	 * @param atoms
	 * 				A Map between labels and atoms
	 */
	public void setInput(List<XPDFAtom> atoms) {
		this.atoms = atoms;
		groupedTable.setInput(this.atoms);
	}

	/**
	 * Refreshes the internal table.
	 */
	public void refresh() {
		groupedTable.refresh();
	}
	
	/**
	 * Returns the Map of labels to atoms
	 * @return
	 * 		the map of labels to atoms in this unit cell.
	 */
	public List<XPDFAtom> getAtoms() {
		return atoms;
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
			return atoms.toArray(new XPDFAtom[atoms.size()]);
		}
	}

	private interface ColumnInterface extends EditingSupportFactory {
//		public SelectionAdapter getSelectionAdapter(final UnitCellDialog uCD, final TableViewerColumn col);
		public ColumnLabelProvider getLabelProvider();
		public String getName();
		public int getWeight();
		public boolean presentAsUneditable(Object element);
	}

//	private static class DummyColumnInterface implements ColumnInterface {
//
//		@Override
//		public EditingSupport get(ColumnViewer v) {
//			return new DummyEditingSupport(v);
//		}
//
////		@Override
////		public SelectionAdapter getSelectionAdapter(UnitCellDialog uCD,
////				TableViewerColumn col) {
////			return DummySelectionAdapter.get(uCD, col);
////		}
//
//		@Override
//		public ColumnLabelProvider getLabelProvider() {
//			return new ColumnLabelProvider() {
//				@Override
//				public String getText(Object element) {
//					return "This space left intentionally blank";
//				}
//			};
//		}
//
//		@Override
//		public String getName() {
//			return "Column";
//		}
//
//		@Override
//		public int getWeight() {
//			return 10;
//		}
//
//		@Override
//		public boolean presentAsUneditable(Object element) {
//			return false;
//		}
//		
//	}
	
	private static class DummyEditingSupport extends EditingSupport {
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

//	private static class DummyLabelProvider extends ColumnLabelProvider {
//		String text;
//		public DummyLabelProvider(String text) {
//			this.text = text;
//		}
//		@Override
//		public String getText(Object element) {
//			return text;
//		}
//	}

	private static class ElementColumnInterface implements ColumnInterface {

		private final List<String> elementSymbol = Arrays.asList( new String[] { "?", 
				"H","He","Li","Be","B","C","N","O","F","Ne",
				"Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca",
				"Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn",
				"Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
		        "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn",
		        "Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd",
		        "Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb",
		        "Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg",
		        "Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th",
		        "Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm"}
		);
		private static final int elementOffset = 1; 

		@Override
		public EditingSupport get(final ColumnViewer v) {
			return new EditingSupport(v) {

				@Override
				protected CellEditor getCellEditor(Object element) {
					Table theTable =  ((TableViewer) v).getTable();
					String[] elementStrings = elementSymbol.subList(elementOffset, elementSymbol.size()).toArray(new String[elementSymbol.size()-1]);
					return new ComboBoxCellEditor(theTable, elementStrings);
				}

				@Override
				protected boolean canEdit(Object element) {
					return true;
				}

				@Override
				protected Object getValue(Object element) {
					int atomicNumber;
					if (element instanceof XPDFAtom)
						atomicNumber = ((XPDFAtom) element).getAtomicNumber();
					else
						atomicNumber = elementOffset;
					return atomicNumber - elementOffset;
				}

				@Override
				protected void setValue(Object element, Object value) {
					if (value instanceof Integer) {
						int atomicNumber = (Integer) value;
						atomicNumber += elementOffset;
						if (element instanceof XPDFAtom)
							((XPDFAtom) element).setAtomicNumber(atomicNumber);
					}
					v.refresh(element);
				}
				
			};
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new ColumnLabelProvider() {		
				
				@Override
				public String getText(Object element) {
					int atomicNumber = 0;
					if (element instanceof XPDFAtom)
						atomicNumber = ((XPDFAtom) element).getAtomicNumber();
					return elementSymbol.get(atomicNumber);
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

	private class LabelColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(final ColumnViewer v) {
			return new EditingSupport(v) {

				@Override
				protected CellEditor getCellEditor(Object element) {
					return new TextCellEditor(((TableViewer) v).getTable());
				}

				@Override
				protected boolean canEdit(Object element) {
					return true;
				}

				@Override
				protected Object getValue(Object element) {
					return ((XPDFAtom) element).getLabel();
				}

				@Override
				protected void setValue(Object element, Object value) {
					if (element instanceof XPDFAtom)
						((XPDFAtom) element).setLabel(value.toString());
					v.refresh(element);
				}
				
			};
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					return (element instanceof XPDFAtom) ? ((XPDFAtom) element).getLabel() : "";  
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
	
	private static class PositionColumnInterface implements ColumnInterface {

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
					return (element instanceof XPDFAtom) ? Double.toString(((XPDFAtom) element).getPosition()[axisIndex]) : "";
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

	private static class OccupancyColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					return (element instanceof XPDFAtom) ? Double.toString(((XPDFAtom) element).getOccupancy()) : "";
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

	private static class adpColumnInterface implements ColumnInterface {

		@Override
		public EditingSupport get(ColumnViewer v) {
			return new DummyEditingSupport(v);
		}

		@Override
		public ColumnLabelProvider getLabelProvider() {
			return new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					return (element instanceof XPDFAtom) ? Double.toString(((XPDFAtom) element).getIsotropicDisplacement()) : "";
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
