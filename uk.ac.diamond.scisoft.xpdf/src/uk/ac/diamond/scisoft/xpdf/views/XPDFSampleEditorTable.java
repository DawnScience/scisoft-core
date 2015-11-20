package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class XPDFSampleEditorTable {

	XPDFGroupedTable groupedTable;
	
	List<XPDFSampleParameters> samples;
	
	private enum Column {
		NAME, ID, DETAILS, PHASES, COMPOSITION, DENSITY, PACKING,
		SUGGESTED_ENERGY, MU, SUGGESTED_DIAMETER, ENERGY, CAPILLARY, DIMENSIONS, SHAPE
	}
	
	private enum ColumnGroup {
		ID, SAMPLE_DETAILS, GEOMETRY
	}

	private Map<ColumnGroup, List<Column>> columnGroupings;
	
	
	public XPDFSampleEditorTable(Composite parent, int style) {
		groupedTable = new XPDFGroupedTable(parent, style);
		samples = new ArrayList<XPDFSampleParameters>();
		
		columnGroupings = new HashMap<XPDFSampleEditorTable.ColumnGroup, List<Column>>();
		columnGroupings.put(ColumnGroup.ID, Arrays.asList(new Column[]{Column.NAME, Column.ID}));
		columnGroupings.put(ColumnGroup.SAMPLE_DETAILS, Arrays.asList(new Column[]{Column.PHASES, Column.COMPOSITION, Column.DENSITY, Column.PACKING}));
		columnGroupings.put(ColumnGroup.GEOMETRY, Arrays.asList(new Column[]{Column.DIMENSIONS, Column.SHAPE}));
		
		Map<ColumnGroup, String> groupNames = new HashMap<XPDFSampleEditorTable.ColumnGroup, String>();
		groupNames.put(ColumnGroup.ID, "Sample");
		groupNames.put(ColumnGroup.SAMPLE_DETAILS, "Material details");
		groupNames.put(ColumnGroup.GEOMETRY, "Geometry");
		
		Map<Column, String> columnNames = new HashMap<XPDFSampleEditorTable.Column, String>();
		columnNames.put(Column.ID, "Code");
		columnNames.put(Column.NAME, "Name");
		columnNames.put(Column.PHASES, "Phases");
		columnNames.put(Column.COMPOSITION, "Composition");
		columnNames.put(Column.DENSITY, "Density");
		columnNames.put(Column.PACKING, "Vol. frac.");
		columnNames.put(Column.DIMENSIONS, "Dimensions");
		columnNames.put(Column.SHAPE, "Shape");
		
		
		for (ColumnGroup columnGroup : ColumnGroup.values()) {
			for (Column column : columnGroupings.get(columnGroup)) {
				TableViewerColumn tVC = groupedTable.newGroupedColumn(groupNames.get(columnGroup));
				tVC.getColumn().setText(columnNames.get(column));
				tVC.setLabelProvider(new SampleEditorCLP(column));
				tVC.setEditingSupport(new SampleEditorCES(column, (TableViewer) tVC.getViewer()));
			}
		}
		groupedTable.setContentProvider(new IStructuredContentProvider() {
				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}	// TODO Auto-generated method stub
				
				@Override
				public void dispose() {} // TODO Auto-generated method stub
				
				@Override
				public Object[] getElements(Object inputElement) {
					return samples.toArray();
				}
			});
		
	}


	// Column label provider. Use a switch to provide the different data labels for the different columns.
	class SampleEditorCLP extends ColumnLabelProvider {

		final Column column;

		public SampleEditorCLP(Column column) {
			this.column = column;
		}

		@Override
		public String getText(Object element) {
			XPDFSampleParameters sample = (XPDFSampleParameters) element;
			switch (column) {
			case NAME: return sample.getName();
			case ID: return Integer.toString(sample.getId());
			case DETAILS: return "+";
			case PHASES: 
				StringBuilder sb = new StringBuilder();
				for (String phase : sample.getPhases()) {
					sb.append(phase);
					sb.append(", ");
				}
				if (sb.length() > 2) sb.delete(sb.length()-2, sb.length());
				return sb.toString();
			case COMPOSITION: return sample.getComposition();
			case DENSITY: return Double.toString(sample.getDensity());
			case PACKING: return Double.toString(sample.getPackingFraction());
			case SUGGESTED_ENERGY: return Double.toString(sample.getSuggestedEnergy());
			case MU: return String.format("%.4f", sample.getMu());
			case SUGGESTED_DIAMETER: return Double.toString(sample.getSuggestedCapDiameter());
			case ENERGY: return sample.getBeamState();
			case CAPILLARY: return sample.getContainer();
			default: return "";
			}
		}
	}

	// Column editing support. The different cases for the different columns are just switched by a switch statements or if-then-else
	class SampleEditorCES extends EditingSupport {

		final Column column;
		final TableViewer tV;	

		public SampleEditorCES(Column column, TableViewer tV) {
			super(tV);
			this.column = column;
			this.tV = tV;
		};
		@Override
		protected CellEditor getCellEditor(Object element) {
			return new TextCellEditor(tV.getTable());
		}
		@Override
		protected boolean canEdit(Object element) {
			if (column == Column.ID || column == Column.DETAILS || column == Column.MU) 
				return false;
			else
				return true;
		}
		@Override
		protected Object getValue(Object element) {
			XPDFSampleParameters sample = (XPDFSampleParameters) element;
			switch (column) {
			case NAME: return sample.getName();
			case ID: return sample.getId();
			case DETAILS: return null; // TODO: This should eventually show something, but nothing for now.
			case PHASES: return (new SampleEditorCLP(Column.PHASES)).getText(element);
			case COMPOSITION: return sample.getComposition();
			case DENSITY: return Double.toString(sample.getDensity());
			case PACKING: return Double.toString(sample.getPackingFraction());
			case SUGGESTED_ENERGY: return Double.toString(sample.getSuggestedEnergy());
			case MU: return 1.0;
			case SUGGESTED_DIAMETER: return Double.toString(sample.getSuggestedCapDiameter());
			case ENERGY: return sample.getBeamState();
			case CAPILLARY: return sample.getContainer();
			default: return null;
			}
		}
		@Override
		protected void setValue(Object element, Object value) {
			XPDFSampleParameters sample = (XPDFSampleParameters) element;
			String sValue = (String) value;
			switch (column) {
			case NAME: sample.setName(sValue); break;
			case ID: break;
			case DETAILS: break; // TODO: This should eventually do something. Call a big function, probably.
			case PHASES: { // Parse a comma separated list of phases to a list of Strings
				String[] arrayOfPhases = sValue.split(","); 
				List<String> listOfPhases = new ArrayList<String>();
				for (int i = 0; i < arrayOfPhases.length; i++)
					listOfPhases.add(arrayOfPhases[i].trim());
				sample.setPhases(listOfPhases);
			} break;
			case COMPOSITION: sample.setComposition(sValue); break;
			case DENSITY: sample.setDensity(Double.parseDouble(sValue)); break;
			case PACKING: sample.setPackingFraction(Double.parseDouble(sValue)); break;
			case SUGGESTED_ENERGY: sample.setSuggestedEnergy(Double.parseDouble(sValue)); break;
			case MU: break;
			case SUGGESTED_DIAMETER: sample.setSuggestedCapDiameter(Double.parseDouble(sValue)); break;
			case ENERGY: sample.setBeamState(sValue); break;
			case CAPILLARY: sample.setContainer(sValue); break;
			default: break;
			}
			// Here, only this table needs updating
			tV.update(element, null);
		}
	}
	
	public boolean setFocus() {
		return groupedTable.setFocus();
	}
	
	/**
	 * Set the inputs of the grouped table.
	 * @param input
	 * 				the input providing Object
	 */
	public void setInput(Object input) {
		groupedTable.setInput(input);
	}

}