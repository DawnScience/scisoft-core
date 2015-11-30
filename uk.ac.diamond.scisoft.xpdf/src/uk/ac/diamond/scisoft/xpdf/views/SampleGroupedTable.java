/*
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TableColumn;

/**
 * A class to display and edit samples for the XPDF project.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 *
 */
class SampleGroupedTable {
		
	private SortedSet<Integer> usedIDs; // The set of assigned ID numbers. Should come from the database eventually?	

	// The samples held in the table
	private List<XPDFSampleParameters> samples;
	
	private boolean showSamples, showContainers;
		
	// the grouped table object that does the displaying
	private XPDFGroupedTable groupedTable;

	// Names of the groups, the columns in those groups, and the relative
	// widths of the columns
	private List<String> groupNames;
	private List<List<String>> groupedColumnNames;
	private List<List<Integer>> groupedColumnWeights;
	
	private SampleParametersContentProvider contentProvider;

	/**
	 * Constructor.
	 * <p>
	 * takes the same parameters as a Composite, which are passed to the delegated display table.
	 * @param parent
	 * 				parent Composite in which to insert
	 * @param style
	 * 				style flags to set
	 */
	public SampleGroupedTable(Composite parent, int style) {

		samples = new ArrayList<XPDFSampleParameters>();

		showSamples = true;
		showContainers  = true;
		
		groupedTable = new XPDFGroupedTable(parent, SWT.NONE);

		groupNames = new ArrayList<String>();
		groupedColumnNames = new ArrayList<List<String>>();
		groupedColumnWeights = new ArrayList<List<Integer>>();
		List<List<ColumnInterface>> groupedColumnInterfaces = new ArrayList<List<ColumnInterface>>();
		List<ColumnInterface> columnInterfaces = new ArrayList<ColumnInterface>();
		
		// Define the column groups and the columns they contain
		groupNames.add("Sample Identification");
		groupedColumnNames.add(Arrays.asList(new String[] {"Sample name", "Code"}));
		groupedColumnWeights.add(Arrays.asList(new Integer[] {20, 10}));
		columnInterfaces.clear();
		columnInterfaces.add(new NameColumnInterface());
		columnInterfaces.add(new CodeColumnInterface());
		groupedColumnInterfaces.add(columnInterfaces);
		
		groupNames.add("Type");
		groupedColumnNames.add(Arrays.asList(new String[] {""}));
		groupedColumnWeights.add(Arrays.asList(new Integer[] {15}));
//		groupedColumnInterfaces.add(Arrays.asList(new ColumnInterface<?>[] {null, null, null, null, null}));
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new TypeColumnInterface(this));
		groupedColumnInterfaces.add(columnInterfaces);
		
		groupNames.add("Properties");
		groupedColumnNames.add(Arrays.asList(new String[] {"Phases", "Composition", "Density", "Vol. frac."}));
		groupedColumnWeights.add(Arrays.asList(new Integer[] {10, 15, 10, 5}));
//		groupedColumnInterfaces.add(Arrays.asList(new ColumnInterface<?>[] {null, null, null}));
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new PhaseColumnInterface());
		columnInterfaces.add(new CompositionColumnInterface());
		columnInterfaces.add(new DensityColumnInterface());
		columnInterfaces.add(new PackingColumnInterface());
		groupedColumnInterfaces.add(columnInterfaces);

		groupNames.add("Geometry");
		groupedColumnNames.add(Arrays.asList(new String[] {"Container", "μ"}));
		groupedColumnWeights.add(Arrays.asList(new Integer[] {10, 10}));
//		groupedColumnInterfaces.add(Arrays.asList(new ColumnInterface<?>[] {null, null}));
		columnInterfaces = new ArrayList<ColumnInterface>();
		columnInterfaces.add(new ShapeColumnInterface());
		columnInterfaces.add(new DimensionColumnInterface());
		groupedColumnInterfaces.add(columnInterfaces);

//		Make up the columns
		for (int iGroup = 0; iGroup < groupNames.size(); iGroup++) {
			groupedTable.createColumnGroup(groupNames.get(iGroup));
			for (int iColumn = 0; iColumn < groupedColumnInterfaces.get(iGroup).size(); iColumn++) {
				ColumnInterface colI = groupedColumnInterfaces.get(iGroup).get(iColumn);
				TableViewerColumn col = groupedTable.addColumn(groupNames.get(iGroup), SWT.NONE);
				col.getColumn().setText(colI.getName());
				groupedTable.setColumnWidth(col, colI.getWeight());
				col.setLabelProvider(colI.getLabelProvider());
				groupedTable.setColumnEditingSupport(col, colI);
				if (colI.getSelectionAdapter(this, col) != null) col.getColumn().addSelectionListener(colI.getSelectionAdapter(this, col));
			}
		}

		
		contentProvider = new SampleParametersContentProvider();
		groupedTable.setContentProvider(contentProvider);

		// The label provider for the column headers
//		List<String> allColumnNames = new ArrayList<String>();
//		for (List<String> groupedNames : groupedColumnNames)
//			allColumnNames.addAll(groupedNames);
//		groupedTable.setLabelProvider(new SampleTableLP(allColumnNames));

		// The Drag Listener and the Drop Adapter need the Viewer, which 
		// we do not (and should not) have access to at this level. The
		// final argument in each case is an object that returns the class
		// when the method generate(Viewer) is called.
		groupedTable.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY, new Transfer[]{LocalSelectionTransfer.getTransfer()}, new LocalDragSupportListener(groupedTable));
		groupedTable.addDropSupport(DND.DROP_MOVE | DND.DROP_COPY, new Transfer[]{LocalSelectionTransfer.getTransfer()}, new LocalViewerDropAdapterFactory(samples, groupedTable));

	}
		
	/**
	 * Sets the input of the delegated viewer objects.
	 * @param input
	 * 				the object providing the input
	 */
	public void setInput(Object input) {
		groupedTable.setInput(input);
	}

	/**
	 * Adds a set of sample parameters.
	 * @param sample
	 * 				parameters of the sample to be added
	 */
	public void add(XPDFSampleParameters sample) {
		sample.setId(generateUniqueID());
		samples.add(sample);
		groupedTable.refresh();
	}

	/**
	 * Clears all the samples in this table.
	 */
	public void clear() {
		samples.clear();
		usedIDs.clear();
		groupedTable.refresh();
		showSamples();
		showContainers();
	}

	/**
	 * Removes all the samples in the provided {@link Collection}.
	 * @param sample
	 * 				the samples to be removed
	 */
	public void removeAll(Collection<XPDFSampleParameters> sample) {
		samples.removeAll(sample);
		groupedTable.refresh();
	}

	/**
	 * Returns the list of all samples.
	 * <p>
	 * Use this wisely.
	 * @return list of all the samples in the table.
	 */
	public List<XPDFSampleParameters> getAll() {
		return samples;
	}

	/**
	 * Gets the sample parameters at the given index.
	 * @param index
	 * 				index to retrieve the parameters at.
	 * @return the parameters retrieved
	 */
	public XPDFSampleParameters get(int index) {
		return samples.get(index);
	}

	/**
	 * Returns the number of samples in the sample table.
	 * @return the size of the sample table
	 */
	public int size() {
		return samples.size();
	}

	/**
	 * Sets the focus of the underlying Viewers.
	 */
	public void setFocus() {
		groupedTable.setFocus();
	}

	/**
	 * Sets the {@link Layout} data of the underlying Composite.
	 * @param layout
	 */
	public void setLayoutData(Object layout) {
		groupedTable.setLayoutData(layout);
	}
	
	/**
	 * Returns the SWT {@link Control} of the grouped table, for the purposes
	 * of laying out &c.
	 * @return the Control of the grouped table.
	 */
	public Control getControl() {
		return groupedTable;
	}
	
	/**
	 * Sets the samples to be shown
	 */
	public void showSamples() {
		showSamples(true);
	}
	/**
	 * Sets the flag as to whether the samples are to be shown
	 * @param show
	 * 			show the samples?
	 */
	public void showSamples(boolean show) {
		showSamples = show;
	}
	/**
	 * Sets the containers to be shown
	 */
	public void showContainers() {
		showContainers(true);
	}
	/**
	 * Sets the flag as to whether the containers are to be shown
	 * @param show
	 * 			show the containers?
	 */
	public void showContainers(boolean show) {
		showContainers = show;
	}
	/**
	 * Sets the samples to be hidden
	 */
	public void hideSamples() {
		showSamples(false);
	}
	/**
	 * Set the containers to be hidden
	 */
	public void hideContainers() {
		showContainers(false);
	}

	public boolean isShowingSamples() {
		return showSamples;
	}
	
	public boolean isShowingContainers() {
		return showContainers;
	}
	
	public void refresh() {
		groupedTable.refresh();
	}
	
	/**
	 * Returns a list of the samples selected in the table.
	 * @return the selected samples.
	 */
	public List<XPDFSampleParameters> getSelectedSamples() {
		List<XPDFSampleParameters> selectedXPDFParameters = new ArrayList<XPDFSampleParameters>();
		ISelection selection = groupedTable.getSelection();
		// No items? return, having done nothing.
		if (selection.isEmpty()) return selectedXPDFParameters;
		// If it is not an IStructureSelection, then I don't know what to do with it.
		if (!(selection instanceof IStructuredSelection)) return selectedXPDFParameters;
		// Get the list of all selected data
		List<?> selectedData = ((IStructuredSelection) selection).toList();
		for (Object datum : selectedData)
			if (datum instanceof XPDFSampleParameters)
				selectedXPDFParameters.add((XPDFSampleParameters) datum);
		return selectedXPDFParameters;		
	}

	public void createContextMenu(MenuManager menuManager) {
		groupedTable.createContextMenu(menuManager);			
	}
	
	public SelectionAdapter getColumnSelectionAdapter(final TableColumn tableColumn, final Comparator<XPDFSampleParameters> comparator) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (comparator == null) return;
				// Find the present sorted column, if any
				TableColumn presentSorted = null;
				int oldSortDirection = SWT.NONE;
				presentSorted = groupedTable.getSortColumn();
				oldSortDirection = groupedTable.getSortDirection();

				groupedTable.setSortColumn(null);
				groupedTable.setSortDirection(SWT.NONE);

				int newSortDirection = SWT.DOWN;
				
				// If the same column is sorted as is now selected, then reverse the sorting
				if (presentSorted == tableColumn)
					newSortDirection = (oldSortDirection == SWT.UP) ? SWT.DOWN : SWT.UP;

				// Do the sort
				Collections.sort(samples, comparator);
				if (newSortDirection == SWT.UP)
					Collections.reverse(samples);

				groupedTable.setSortColumn(tableColumn);
				groupedTable.setSortDirection(newSortDirection);

				groupedTable.refresh();
			}
		};
	}

//	// The table label provider does nothing except delegate to the column label provider
//	class SampleTableLP extends LabelProvider implements ITableLabelProvider {
//
//		final List<String> columns;
//
//		public SampleTableLP(List <String> columns) {
//			this.columns = columns;
//		}
//
//		@Override
//		public Image getColumnImage(Object element, int columnIndex) {
//			return null;
//		}
//
//		@Override
//		public String getColumnText(Object element, int columnIndex) {
//			return (new SampleTableCLP(columns.get(columnIndex))).getText(element);
//		}	
//	}

	class SampleParametersContentProvider implements IStructuredContentProvider {

		public SampleParametersContentProvider() {
			showSamples = true;
			showContainers = true;
		}
		
		public boolean isShowingContainers() {
			return showContainers;
		}

		public boolean isShowingSamples() {
			return showSamples;            
		}                                     
		@Override
		public void dispose() {} // TODO Auto-generated method stub

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
//			viewer.refresh();
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (showSamples && showContainers) {
				return samples.toArray(new XPDFSampleParameters[]{});
			} else if (showContainers) {
				List<XPDFSampleParameters> containers = new ArrayList<XPDFSampleParameters>();
				for (XPDFSampleParameters container : samples)
					if (!container.isSample())
						containers.add(container);
				return containers.toArray(new XPDFSampleParameters[]{});
			} else {
				List<XPDFSampleParameters> filteredSamples = new ArrayList<XPDFSampleParameters>();
				for (XPDFSampleParameters sample : samples)
					if (sample.isSample())
						filteredSamples.add(sample);
				return filteredSamples.toArray(new XPDFSampleParameters[]{});
			}
				
		}

		public void showSamples(boolean show) {
			showSamples = show;
		}

		public void showContainers(boolean show) {
			showContainers = show;
		}
		
	}

//	// Column label provider. Use a switch to provide the different data labels for the different columns.
//	class SampleTableCLP extends ColumnLabelProvider {
//
//		final String columnName;
//
//		public SampleTableCLP(String columnName) {
//			this.columnName = columnName;
//		}
//
//		@Override
//		public String getText(Object element) {
//			XPDFSampleParameters sample = (XPDFSampleParameters) element;
//			switch (columnName) {
//			case "Sample name": return sample.getName();
//			case "Code": return Integer.toString(sample.getId());
//			case "": return "+";
//			case "Phases": 
//				StringBuilder sb = new StringBuilder();
//				for (String phase : sample.getPhases()) {
//					sb.append(phase);
//					sb.append(", ");
//				}
//				if (sb.length() > 2) sb.delete(sb.length()-2, sb.length());
//				return sb.toString();
//			case "Composition": return sample.getComposition();
//			case "Density": return Double.toString(sample.getDensity());
//			case "Vol. frac.": return Double.toString(sample.getPackingFraction());
//			case "Energy": return Double.toString(sample.getSuggestedEnergy());
//			case "μ": return String.format("%.4f", sample.getMu());
//			case "Max capillary ID": return Double.toString(sample.getSuggestedCapDiameter());
//			case "Beam state": return sample.getBeamState();
//			case "Container": return sample.getContainer();
//			default: return "";
//			}
//		}
//	}

	// drag support for local moves. Copy data
	class LocalDragSupportListener extends DragSourceAdapter {
		private XPDFGroupedTable gT;
		public LocalDragSupportListener(XPDFGroupedTable gT) {
			this.gT = gT;
		}

		@Override
		public void dragSetData(DragSourceEvent event) {
			LocalSelectionTransfer.getTransfer().setSelection(gT.getSelection());
		}
	}

	class LocalViewerDropAdapterFactory implements ViewerDropAdapterFactory {
		List<XPDFSampleParameters> samples;
		XPDFGroupedTable groupedTable;
		public LocalViewerDropAdapterFactory(List<XPDFSampleParameters> samples, XPDFGroupedTable groupedTable) {
			this.samples = samples;
			this.groupedTable = groupedTable;
		}
		
		public ViewerDropAdapter get(Viewer v) {
			return new LocalViewerDropAdapter(v, samples, groupedTable);
		}
	}
	
	// Deals with both dragging and copy-dragging
	class LocalViewerDropAdapter extends ViewerDropAdapter {
		List<XPDFSampleParameters> samples;
		XPDFGroupedTable groupedTable;

		public LocalViewerDropAdapter(Viewer tV, List<XPDFSampleParameters> samples, XPDFGroupedTable groupedTable) {
			super(tV);
			this.samples = samples;
			this.groupedTable = groupedTable;
		}

		@Override
		public boolean performDrop(Object data) {
			XPDFSampleParameters targetEntry = (XPDFSampleParameters) getCurrentTarget();
			// Create the List of new sample parameters to be dropped in.
			List<XPDFSampleParameters> samplesToAdd = new ArrayList<XPDFSampleParameters>(((IStructuredSelection) LocalSelectionTransfer.getTransfer().getSelection()).size());
			for (Object oSample: ((IStructuredSelection) LocalSelectionTransfer.getTransfer().getSelection()).toList()) {
				XPDFSampleParameters sampleToAdd;
				if (getCurrentOperation() == DND.DROP_COPY) {
					sampleToAdd = new XPDFSampleParameters((XPDFSampleParameters) oSample);
					sampleToAdd.setId(generateUniqueID());
				} else {
					sampleToAdd = (XPDFSampleParameters) oSample;
				}
				samplesToAdd.add(sampleToAdd);
			}

			int targetIndex;
			// Deal with removing the originals when moving and get the index to insert the dragees before
			if (getCurrentOperation() == DND.DROP_MOVE) {
				// Remove the originals, except the target if it is in the dragged set
				List<XPDFSampleParameters> samplesToRemove = new ArrayList<XPDFSampleParameters>(samplesToAdd);
				boolean moveInitialTarget = samplesToAdd.contains(targetEntry);
				if (moveInitialTarget)
					samplesToRemove.remove(targetEntry);
				samples.removeAll(samplesToRemove);

				// Get the index before which to insert the moved data
				targetIndex = getDropTargetIndex(targetEntry, getCurrentLocation(), moveInitialTarget);
				if (targetIndex == -1) return false;

				if (moveInitialTarget)
					samples.remove(targetEntry);
			} else {
				// Get the index before which to insert the moved data
				targetIndex = getDropTargetIndex(targetEntry, getCurrentLocation());
				if (targetIndex == -1) return false;
			}				
			boolean success = samples.addAll(targetIndex, samplesToAdd);
			groupedTable.refresh();

			return success;
		}

		@Override
		public boolean validateDrop(Object target, int operation,
				TransferData transferType) {
			// Fine, whatever. Just don't try anything funny.
			// TODO: real validation.
			return true;
		}

	}

	private int getDropTargetIndex(XPDFSampleParameters targetEntry, int currentLocation) {
		return getDropTargetIndex(targetEntry, currentLocation, false);
	}

	private int getDropTargetIndex(XPDFSampleParameters targetEntry, int currentLocation, boolean isTargetRemoved) {
		// If they are identical, there is only one dragee, and the location is ON, then do nothing.
		if (((IStructuredSelection) LocalSelectionTransfer.getTransfer().getSelection()).size() == 1 &&
				targetEntry == ((XPDFSampleParameters) ((IStructuredSelection) LocalSelectionTransfer.getTransfer().getSelection()).getFirstElement()) &&
				currentLocation == ViewerDropAdapter.LOCATION_ON)
			return -1;

		// Otherwise, copy all the data dragged.
		int targetIndex;
		if (targetEntry != null) {
			targetIndex = samples.indexOf(targetEntry);

			switch (currentLocation) {
			case ViewerDropAdapter.LOCATION_BEFORE:
			case ViewerDropAdapter.LOCATION_ON:
				break;
			case ViewerDropAdapter.LOCATION_AFTER:
				if (!isTargetRemoved) targetIndex++;
				break;
			case ViewerDropAdapter.LOCATION_NONE:
				return -1;
			default: return -1;

			}
		} else {
			targetIndex = samples.size();
		}
		return targetIndex;
	}

//	class SampleTableCESFactory  implements EditingSupportFactory {
//		final String column;
//		public SampleTableCESFactory(String column) {
//			this.column = column;
//		}
//		@Override
//		public EditingSupport get(final ColumnViewer v) {
//			return new SampleTableCES(column, (TableViewer) v);
//		}
//	}
//
//
//	class SampleTableCES extends EditingSupport {
//
//		final String column;
//		final TableViewer tV;	
//
//		public SampleTableCES(String column, TableViewer tV) {
//			super(tV);
//			this.column = column;
//			this.tV = tV;
//		};
//		@Override
//		protected CellEditor getCellEditor(Object element) {
//			return new TextCellEditor(tV.getTable());
////			return new TextCellEditor();
//		}
//		@Override
//		protected boolean canEdit(Object element) {
//			if (column == "Code" || column == "" || column == "μ") 
//				return false;
//			else
//				return true;
//		}
//		@Override
//		protected Object getValue(Object element) {
//			XPDFSampleParameters sample = (XPDFSampleParameters) element;
//			switch (column) {
//			case "Sample name": return sample.getName();
//			case "Code": return sample.getId();
//			case "": return null; // TODO: This should eventually show something, but nothing for now.
//			case "Phases": return (new SampleTableCLP("Phases")).getText(element);
//			case "Composition": return sample.getComposition();
//			case "Density": return Double.toString(sample.getDensity());
//			case "Vol. frac.": return Double.toString(sample.getPackingFraction());
//			case "Energy": return Double.toString(sample.getSuggestedEnergy());
//			case "μ": return 1.0;
//			case "Max capillary ID": return Double.toString(sample.getSuggestedCapDiameter());
//			case "Beam state": return sample.getBeamState();
//			case "Container": return sample.getContainer();
//			default: return null;
//			}
//		}
//		@Override
//		protected void setValue(Object element, Object value) {
//			XPDFSampleParameters sample = (XPDFSampleParameters) element;
//			String sValue = (String) value;
//			switch (column) {
//			case "Sample name": sample.setName(sValue); break;
//			case "Code": break;
//			case "": break; // TODO: This should eventually do something. Call a big function, probably.
//			case "Phases": { // Parse a comma separated list of phases to a list of Strings
//				String[] arrayOfPhases = sValue.split(","); 
//				List<String> listOfPhases = new ArrayList<String>();
//				for (int i = 0; i < arrayOfPhases.length; i++)
//					listOfPhases.add(arrayOfPhases[i].trim());
//				sample.setPhases(listOfPhases);
//			} break;
//			case "Composition": sample.setComposition(sValue); break;
//			case "Density": sample.setDensity(Double.parseDouble(sValue)); break;
//			case "Vol. frac.": sample.setPackingFraction(Double.parseDouble(sValue)); break;
//			case "Energy": sample.setSuggestedEnergy(Double.parseDouble(sValue)); break;
//			case "μ": break;
//			case "Max capillary ID": sample.setSuggestedCapDiameter(Double.parseDouble(sValue)); break;
//			case "Beam state": sample.setBeamState(sValue); break;
//			case "Container": sample.setContainer(sValue); break;
//			default: break;
//			}
//			// Here, only this table needs updating
//			tV.update(element, null);
//		}
//	}

 	// Generate a new id
	private	int generateUniqueID() {
		final int lowestID = 154;
		if (usedIDs == null)
			usedIDs = new TreeSet<Integer>();
		int theID = (usedIDs.isEmpty()) ? lowestID : usedIDs.last()+1;
		usedIDs.add(theID);
		return theID;
	}

}

interface ColumnInterface extends EditingSupportFactory {
	public SelectionAdapter getSelectionAdapter(final SampleGroupedTable tab, final TableViewerColumn col);
	public ColumnLabelProvider getLabelProvider();
	public String getName();
	public int getWeight();
	public boolean presentAsUneditable(Object element);
}


class NameColumnInterface implements ColumnInterface {

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
				return ((XPDFSampleParameters) element).getName();
			}

			@Override
			protected void setValue(Object element, Object value) {
				((XPDFSampleParameters) element).setName( (value != null) ? (String) value : "");
				v.refresh();
			}
			
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(final SampleGroupedTable tab, final TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((XPDFSampleParameters) element).getName();
			}
		};
	}

	@Override
	public String getName() {
		return "Name";
	}

	@Override
	public int getWeight() {
		return 20;
	}

	@Override
	public boolean presentAsUneditable(Object element) {
		return false;
	}
	
}

class CodeColumnInterface implements ColumnInterface {

	@Override
	public EditingSupport get(ColumnViewer v) {
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((XPDFSampleParameters) element).getId();
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return null;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return false;
			}
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(final SampleGroupedTable tab, final TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "E"+String.format("%05d", ((XPDFSampleParameters)element).getId());
			}
		};
	}

	@Override
	public String getName() {
		return "Code";
	}

	@Override
	public int getWeight() {
		return 5;
	}

	@Override
	public boolean presentAsUneditable(Object element) {
		return false;
	}
	
}

class TypeColumnInterface implements ColumnInterface {

	private static String sampleString = "Sample";
	private static String containerString = "Container";
	private static String[] comboChoices = {sampleString, containerString};
	private SampleGroupedTable sampleTable;
	
	public TypeColumnInterface(SampleGroupedTable sampleTable) {
		this.sampleTable = sampleTable;
	}
	
	@Override
	public EditingSupport get(final ColumnViewer v) {
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
				switch((int) value) {
				case(0) : 
					((XPDFSampleParameters) element).setAsSample();
				break;
				case(1) :
					((XPDFSampleParameters) element).setAsContainer();
				break;
				default :
					break;
				}
				sampleTable.refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((XPDFSampleParameters) element).isSample() ? 0 : 1;
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(((TableViewer) v).getTable(), comboChoices);
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(final SampleGroupedTable tab, final TableViewerColumn col) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (tab.isShowingContainers()) {
					if (tab.isShowingSamples()) {
						tab.hideContainers();
						tab.showSamples();
					} else {
						tab.showSamples();
						tab.showContainers();
					}
				} else {
					tab.showContainers();
					tab.hideSamples();
				}
				tab.refresh();
				col.getColumn().setText(getName());
		}
		};
	}
	
	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((XPDFSampleParameters) element).isSample() ? sampleString : containerString;
			}
		};
	}

	@Override
	public String getName() {
		if (sampleTable.isShowingSamples() && sampleTable.isShowingContainers()) return sampleString+"/"+containerString;
		if (sampleTable.isShowingSamples())
			return sampleString;
		else
			return containerString;
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

class PhaseColumnInterface implements ColumnInterface {

	private String phasesString(Collection<String> phases) {
		StringBuilder sb = new StringBuilder();
		for (String phase : phases) {
			sb.append(phase);
			sb.append(", ");
		}
		if (sb.length() > 2) sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	
	@Override
	public EditingSupport get(final ColumnViewer v) {
		// TODO: Should interact with the phases table
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
				String[] arrayOfPhases = ((String) value).split(","); 
				List<String> listOfPhases = new ArrayList<String>();
				for (int i = 0; i < arrayOfPhases.length; i++)
					listOfPhases.add(arrayOfPhases[i].trim());
				((XPDFSampleParameters) element).setPhases(listOfPhases);
				v.refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				return phasesString(((XPDFSampleParameters) element).getPhases());
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(((TableViewer) v).getTable());
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		};
		}

	@Override
	public SelectionAdapter getSelectionAdapter(SampleGroupedTable tab,
			TableViewerColumn col) {
		return null;
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return phasesString(((XPDFSampleParameters) element).getPhases());
			}
		};
	}

	@Override
	public String getName() {
		return "Phases";
	}

	@Override
	public int getWeight() {
		return 20;
	}

	@Override
	public boolean presentAsUneditable(Object element) {
		return false;
	}
	
}

class CompositionColumnInterface implements ColumnInterface {

	@Override
	public EditingSupport get(final ColumnViewer v) {
		// TODO: Should be uneditable, derived from the phases
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
				((XPDFSampleParameters) element).setComposition((value != null) ? (String) value : null);
				v.refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((XPDFSampleParameters) element).getComposition();
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(((TableViewer) v).getTable());
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		};
		}

	@Override
	public SelectionAdapter getSelectionAdapter(SampleGroupedTable tab,
			TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return o1.getComposition().compareTo(o2.getComposition());
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((XPDFSampleParameters) element).getComposition();
			}
		};
	}

	@Override
	public String getName() {
		return "Composition";
	}

	@Override
	public int getWeight() {
		return 15;
	}

	@Override
	public boolean presentAsUneditable(Object element) {
		return false;
	}
	
}

class DensityColumnInterface implements ColumnInterface {

	@Override
	public EditingSupport get(final ColumnViewer v) {
		// TODO: Should be uneditable, derived from the phases
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
				((XPDFSampleParameters) element).setDensity(Double.parseDouble((String) value));
				v.refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				return Double.toString(((XPDFSampleParameters) element).getDensity());
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(((TableViewer) v).getTable());
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(final SampleGroupedTable tab, final TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getDensity(), o2.getDensity());
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Double.toString(((XPDFSampleParameters) element).getDensity());
			}
		};
	}

	@Override
	public String getName() {
		return "Density";
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

class PackingColumnInterface implements ColumnInterface {

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
				return Double.toString(((XPDFSampleParameters) element).getPackingFraction());
			}

			@Override
			protected void setValue(Object element, Object value) {
				((XPDFSampleParameters) element).setPackingFraction(Double.parseDouble((String) value));				
				v.refresh();
			}
			
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(SampleGroupedTable tab,
			TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getPackingFraction(), o2.getPackingFraction());
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Double.toString(((XPDFSampleParameters) element).getPackingFraction());
			}
		};
	}

	@Override
	public String getName() {
		return "Pack. frac.";
	}

	@Override
	public int getWeight() {
		return 5;
	}

	@Override
	public boolean presentAsUneditable(Object element) {
		return false;
	}
	
}

class ShapeColumnInterface implements ColumnInterface {

	private final static String cylinderString = "Cylinder";
	final static String cylinderLowerString = "cylinder";
	private final static String plateString = "Plate";
	final static String plateLowerString = "plate";
	private final static String noneString = "Defined by container";
	private final static String[] shapeChoices = {cylinderString, plateString, noneString};
	
	@Override
	public EditingSupport get(final ColumnViewer v) {
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
				switch((int) value) {
				case(0) :
				case(1) :
					((XPDFSampleParameters) element).setShape(shapeChoices[(int) value]);
				break;
				default :
					((XPDFSampleParameters) element).setShape(null);
				}
				v.refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				String shapeName = ((XPDFSampleParameters) element).getShapeName();
				if (shapeName == null) {
					return 2;
				} else {
					switch (shapeName.toLowerCase()) {
					case (cylinderLowerString):
						return 0;
					case (plateLowerString):
						return 1;
					default:
						return 2;
					}
				}
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(((TableViewer) v).getTable(), shapeChoices);
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(SampleGroupedTable tab,
			TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return o1.getShapeName().compareTo(o2.getShapeName());
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((XPDFSampleParameters) element).getShapeName();
			}

			@Override
			public Font getFont(Object element) {
				return (presentAsUneditable((XPDFSampleParameters) element)) ?
						JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT) :
							JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT);
			}
		};
	}

	@Override
	public String getName() {
		return "Shape";
	}

	@Override
	public int getWeight() {
		return 15;
	}

	@Override
	public boolean presentAsUneditable(Object element) {
		String shape = ((XPDFSampleParameters) element).getShapeName();
		boolean isCylinder = shape.equalsIgnoreCase(cylinderLowerString);
		boolean isPlate = shape.equalsIgnoreCase(plateLowerString);
		return !(isCylinder || isPlate);
	}
	
}

class DimensionColumnInterface implements ColumnInterface {
	
	@Override
	public EditingSupport get(final ColumnViewer v) {
		return new EditingSupport(v) {
			
			@Override
			protected void setValue(Object element, Object value) {
				String[] dimStrings = ((String) value).split(",", 2);
				if (dimStrings.length == 1)
						((XPDFSampleParameters) element).setDimensions(0, Double.parseDouble(dimStrings[0]));
				else if (dimStrings.length > 1)
					((XPDFSampleParameters) element).setDimensions(Double.parseDouble(dimStrings[0]), Double.parseDouble(dimStrings[1]));
				v.refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				double[] dims = ((XPDFSampleParameters) element).getDimensions();
				return (dims != null) ? Double.toString(dims[0]) + ", " + Double.toString(dims[1]) : "";
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(((TableViewer) v).getTable());
			}
			
			@Override
			protected boolean canEdit(Object element) {
//				String shape = ((XPDFSampleParameters) element).getShapeName();
//				boolean isCylinder = shape.equalsIgnoreCase(ShapeColumnInterface.cylinderLowerString);
//				boolean isPlate = shape.equalsIgnoreCase(ShapeColumnInterface.plateLowerString);
//				return (isCylinder || isPlate) ?
//						true :
//							false;
				return presentAsUneditable((XPDFSampleParameters) element);
			}
		};
	}

	@Override
	public SelectionAdapter getSelectionAdapter(SampleGroupedTable tab,
			TableViewerColumn col) {
		return tab.getColumnSelectionAdapter(col.getColumn(), new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return ((Double) o1.getDimensions()[0]).compareTo((Double) o2.getDimensions()[0]);
			}
		});
	}

	@Override
	public ColumnLabelProvider getLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				double[] dims = ((XPDFSampleParameters) element).getDimensions();
				return (dims != null) ? Double.toString(dims[0]) + ", " + Double.toString(dims[1]) : "-";
			}
			
			@Override
			public Font getFont(Object element) {
				return (presentAsUneditable((XPDFSampleParameters) element)) ?
						JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT) :
							JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT);
			}
		};
	}

	@Override
	public String getName() {
		return "Dimensions";
	}

	@Override
	public int getWeight() {
		return 20;
	}
	
	@Override
	public boolean presentAsUneditable(Object element) {
		return (new ShapeColumnInterface()).presentAsUneditable(element);
	}
	
}

