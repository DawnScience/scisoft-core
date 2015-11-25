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

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;

class XPDFGroupedTable extends Composite {
	
	private SashForm tableCompo;
	
	// Only one of each of these per grouped table
	private IStructuredContentProvider cachedContentProvider;
	private Object cachedInput;
	private ITableLabelProvider cachedLabelProvider;
	private ISelectionChangedListener cachedUserSelectionChangedListener;
	private int cachedDragFlags;
	private Transfer[] cachedDragTransfers;
	private DragSourceAdapter cachedDragSourceListener;
	private int cachedDropFlags;
	private Transfer[] cachedDropTransfers;
	private ViewerDropAdapter cachedDropTargetListener;

	private List<TableViewer> groupViewers;
	private List<String> groupNames;
	
	private int sortedGroup; // -1 signifies no column is sorted
	
	private boolean propagateSelectionChange; // To MyGroupedTable
	
	public XPDFGroupedTable(Composite parent, int style) {
		super(parent, style);
		// Attach to the left and right edges of the parent, and set to a fixed height
		this.setLayout(new FormLayout());
		
		// Composite to hold the table column groups
		tableCompo = new SashForm(this, SWT.HORIZONTAL);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(100);
		tableCompo.setLayoutData(formData);
		// To properly fake a table, set the sash width to 0
		tableCompo.setSashWidth(0);
		
		groupViewers = new ArrayList<TableViewer>();
		groupNames = new ArrayList<String>();

		sortedGroup = -1;
		
		propagateSelectionChange = true;
		
		// null the cached table classes
		cachedContentProvider = null;
		cachedInput = null;
		cachedLabelProvider = null;
		cachedUserSelectionChangedListener = null;
		cachedDragTransfers = null;
		cachedDragSourceListener = null;
		cachedDropTransfers = null;
		cachedDropTargetListener = null;
	}			

	public void setColumnWidth(TableViewerColumn col, Integer weight) {
		
		TableViewer tV = null;
		for (TableViewer iTV : groupViewers) {
			if (iTV.getTable() == col.getColumn().getParent()) {
				tV = iTV;
				break;
			}
		}
		if (tV == null) return;
		TableColumnLayout tCL = (TableColumnLayout) tV.getTable().getParent().getLayout();
		tCL.setColumnData(col.getColumn(), new ColumnWeightData(weight));
	}

	public void setColumnEditingSupport(TableViewerColumn col, EditingSupportFactory eSF) {
		col.setEditingSupport(eSF.get(groupViewers.get(getIndexOfTableContaining(col.getColumn()))));
	}

	/**
	 * Refreshes all sub-tables.
	 */
	// refresh the entire table
	public void refresh() {
		for (TableViewer tV : groupViewers)
			tV.refresh();
	}

	/**
	 * Creates a new column group.
	 * <p>
	 * Adds a new named or anonymous column group to the end of the list of
	 * groups. Repeated named groups are not allowed, but an new anonymous
	 * group can be added to the end of the list at any point.
	 * @param groupNameIn
	 * 					the name to give the group of columns
	 */
	public void createColumnGroup(String groupNameIn) {
		String groupName  = (groupNameIn != null) ? groupNameIn : "";
		// Do not add duplicate named groups
		if (!groupName.equals("") && groupNames.contains(groupName)) return;
		// Otherwise, add a group with this name to the end of the list of groups
		groupNames.add(groupName);
		groupViewers.add(createColumnGroupInternal(tableCompo, groupName));
	}
	
	
	private TableViewer createColumnGroupInternal(Composite parent, String groupName) {
		Composite groupCompo = new Composite(parent, SWT.NONE);		
		groupCompo.setLayout(new FormLayout());

		// Add the Group column header as a do-nothing button
		Button headerButton = new Button(groupCompo, SWT.PUSH);

		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		headerButton.setLayoutData(formData);

		headerButton.setText(groupName);

		// Add the table that will hold this subset of the columns
		Composite subTableCompo = new Composite(groupCompo, SWT.NONE);

		formData = new FormData();
		formData.top = new FormAttachment(headerButton);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		subTableCompo.setLayoutData(formData);

		// Define the sub-table
		TableViewer tV = new TableViewer(subTableCompo);

		// Style of each sub-table
		tV.getTable().setHeaderVisible(true);
		tV.getTable().setLinesVisible(true);

		// Column Layout
		subTableCompo.setLayout(new TableColumnLayout());
		
		// Previously set cached classes
		if (cachedContentProvider != null) {
			tV.setContentProvider(cachedContentProvider);
			if (cachedInput != null)
				tV.setInput(cachedInput);
		}
		
		tV.setLabelProvider(new SubTableLabelProvider(tV));

		tV.addSelectionChangedListener(new SubTableSelectionChangedListener());
		
		return tV;
	}

	public TableViewerColumn addColumn(String groupID, int style) {
		// TODO: Add column group if it does not exist
		if (!groupNames.contains(groupID)) return null;
		TableViewer groupViewer = groupViewers.get(groupNames.indexOf(groupID));
		
		return new TableViewerColumn(groupViewer, style);
	}


	
	public void setContentProvider(IStructuredContentProvider iSCP) {
		cachedContentProvider = iSCP;
		for (TableViewer tV : groupViewers)
			tV.setContentProvider(iSCP);
		if (cachedInput != null)
			for (TableViewer tV : groupViewers)
				tV.setInput(cachedInput);
	}
	
	public void setInput(Object input) {
		cachedInput = input;
		if (cachedContentProvider != null)
			for (TableViewer tV : groupViewers)
				tV.setInput(cachedInput);
	}
	
	public void setLabelProvider(ITableLabelProvider iTLP) {
		cachedLabelProvider = iTLP;
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener iSCL) {
		cachedUserSelectionChangedListener = iSCL;
	}
	
	public void addDragSupport(int dragFlags, Transfer[] transfers, DragSourceAdapter dSL) {
		cachedDragFlags = dragFlags;
		cachedDragTransfers = transfers;
		cachedDragSourceListener = dSL;
		for (TableViewer tV : groupViewers)
				tV.addDragSupport(cachedDragFlags, cachedDragTransfers, cachedDragSourceListener);
	}
	
	public void addDropSupport(int dropFlags, Transfer[] transfers, ViewerDropAdapter dTL) {
		cachedDropFlags = dropFlags;
		cachedDropTransfers = transfers;
		cachedDropTargetListener = dTL;
		for (TableViewer tV : groupViewers)
			try {
				tV.addDropSupport(cachedDropFlags, cachedDropTransfers, cachedDropTargetListener.getClass().getDeclaredConstructor(Viewer.class).newInstance(tV));
			} catch (Exception e) {
				// If there are any exceptions, log it and carry on; drag and drop is not critical
				// TODO: Real logger, not stderr
				System.err.println("Exception setting drag and drop support in " + this.getClass() + ":" + e);
			}
	}
	
	/**
	 * Gets the selected data
	 * @return the selected data of the table
	 */
	public ISelection getSelection() {
		return (groupViewers.size() > 0) ? groupViewers.get(0).getSelection() : null;
	}
	
	/**
	 * Changes the selection on all sub-tables.
	 * <p>
	 * When the selection changes, change the selection on the other
	 * sub-tables.
	 *
	 */
	// By using a object-wide flag we can prevent each call triggering four
	// more and causing a stack overflow.
	class SubTableSelectionChangedListener implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				if (propagateSelectionChange) {
					// Oh, the race conditions we shall have.
					// Tell the tables not to further propagate the selection
					// change when we programmatically change the selection
					propagateSelectionChange = false;

					// Fire off the user defined SelectionChangedListener once
					if (cachedUserSelectionChangedListener != null)
						cachedUserSelectionChangedListener.selectionChanged(event);
					
					for (TableViewer tV : groupViewers)
						tV.setSelection(selection, true);
				
					propagateSelectionChange= true;
				}
			}
		}
	}

	public TableColumn getSortColumn() {
		return (sortedGroup != -1) ? groupViewers.get(sortedGroup).getTable().getSortColumn() : null;
	}
	
	public int getSortDirection() {
		return (sortedGroup != -1) ? groupViewers.get(sortedGroup).getTable().getSortDirection() : SWT.NONE;
	}
	
	public void setSortColumn(TableColumn column) {
		if (column == null) {
			if (sortedGroup != -1)
				groupViewers.get(sortedGroup).getTable().setSortColumn(null);
			sortedGroup = -1;
		}
		
		int newSortedGroup = getIndexOfTableContaining(column);
		// Only change sorted order if the grouped table contains the column 
		if (newSortedGroup != -1) {
			sortedGroup = newSortedGroup;
			groupViewers.get(newSortedGroup).getTable().setSortColumn(column);
		}
	}
	
	public void setSortDirection(int direction) {
		if (sortedGroup != -1)
			groupViewers.get(sortedGroup).getTable().setSortDirection(direction);
	}
	
	public void createContextMenu(MenuManager menuManager) {
		for (TableViewer tV : groupViewers) {
			Menu popupMenu = menuManager.createContextMenu(tV.getControl());
			tV.getControl().setMenu(popupMenu);
//			getSite().registerContextMenu(menuManager, tV); // Was this important?
		}
	}
	
	private int getIndexOfTableContaining(TableColumn col) {
		for (int i = 0; i < groupViewers.size(); i++) {
			if (Arrays.asList(groupViewers.get(i).getTable().getColumns()).contains(col))
				return i;//Arrays.asList(groupViewers.get(i).getTable().getColumns()).indexOf(col);
		}
		return -1;
	}
	
	class SubTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		TableViewer tV;
		
		public SubTableLabelProvider(TableViewer tV) {
			this.tV = tV;
		}
		
		private int getOffset() {
			int columnCount = 0;
			if (groupViewers != null && groupViewers.size() > 1) {
				for (int iTV = 0; iTV < groupViewers.size()-1 && groupViewers.get(iTV) != tV; iTV++) {
					columnCount += groupViewers.get(iTV).getTable().getColumnCount();
				}
			}
			return columnCount;
		}
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return (cachedLabelProvider != null) ? 
					cachedLabelProvider.getColumnImage(element, columnIndex+getOffset()) :
					null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return (cachedLabelProvider != null) ?
					cachedLabelProvider.getColumnText(element, columnIndex+getOffset()) :
					null;
		}
	}
}