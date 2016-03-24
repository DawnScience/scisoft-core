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
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;

/**
 * A table with grouped columns.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class XPDFGroupedTable extends Composite {
	
	private Composite headerTableCompo;
	private Composite dataTableCompo;
	
	// Only one of each of these per grouped table
	private IStructuredContentProvider cachedContentProvider;
	private Object cachedInput;
//	private ITableLabelProvider cachedLabelProvider;
	private ISelectionChangedListener cachedUserSelectionChangedListener;
	private int cachedDragFlags;
	private Transfer[] cachedDragTransfers;
	private DragSourceAdapter cachedDragSourceListener;
	private int cachedDropFlags;
	private Transfer[] cachedDropTransfers;
	private ViewerDropAdapterFactory cachedDropTargetListenerFactory;

	// A TableViewer for the data table and one for the column headers
	// vestigial table 
	private TableViewer dataViewer;
	private TableViewer headerViewer;
	private List<String> groupNames;
	// the name of the header in which column occurs
	private List<String> columnsToHeaderNames;
	
	/**
	 * Constructs a new XPDFGroupedTable, within a {@link Composite} parent,
	 * with style bits style.
	 * @param parent
	 * 				Parent Composite into which the grouped table will be put.
	 * Does not need TableColumnLayout
	 * @param style
	 * 			Style bits to apply to the table.
	 */
	public XPDFGroupedTable(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FormLayout());
		
		groupNames = new ArrayList<String>();
		columnsToHeaderNames = new ArrayList<String>();

		// Two Composites to hold the header table and the data table
		headerTableCompo = new Composite(this, SWT.NONE);
		headerTableCompo.setLayout(new TableColumnLayout());
		headerViewer = new TableViewer(headerTableCompo, SWT.NO_SCROLL);
		headerViewer.getTable().setHeaderVisible(true);
		
		dataTableCompo = new Composite(this, SWT.NONE);
		dataTableCompo.setLayout(new TableColumnLayout());
		dataViewer = new TableViewer(dataTableCompo, SWT.NO_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		dataViewer.getTable().setHeaderVisible(true);
		dataViewer.getTable().setLinesVisible(true);

		dataViewer.addSelectionChangedListener(new SubTableSelectionChangedListener());
		
		// Attach the table-holding Composites to the sides and each other
		FormData headerFormData = new FormData(), dataFormData = new FormData();
		headerFormData.left = new FormAttachment(0, 0);
		headerFormData.right = new FormAttachment(100, 0);
		headerFormData.top = new FormAttachment(0, 0);
		headerFormData.bottom = new FormAttachment(dataTableCompo);
		headerTableCompo.setLayoutData(headerFormData);
		dataFormData.left = new FormAttachment(0, 0);
		dataFormData.right = new FormAttachment(100, 0);
		dataFormData.top = new FormAttachment(headerTableCompo);
		dataFormData.bottom = new FormAttachment(100, 0);
		dataTableCompo.setLayoutData(dataFormData);

		// null the cached table classes
		cachedContentProvider = null;
		cachedInput = null;
		cachedUserSelectionChangedListener = null;
		cachedDragTransfers = null;
		cachedDragSourceListener = null;
		cachedDropTransfers = null;
		cachedDropTargetListenerFactory = null;
	}			

	/**
	 * Sets the width of a data column.
	 * @param col
	 * 			object representing the column to be set
	 * @param weight
	 * 				the weight to set the column to
	 */
	public void setColumnWidth(TableViewerColumn col, Integer weight) {
		
		TableColumnLayout tCL = (TableColumnLayout) dataTableCompo.getLayout();
		tCL.setColumnData(col.getColumn(), new ColumnWeightData(weight));
		col.getColumn().setWidth(weight);
			
		tCL = (TableColumnLayout) headerTableCompo.getLayout();
		for (String groupName : groupNames) {
			int groupWidth = 0;
			for (int iCol = 0 ; iCol < columnsToHeaderNames.size(); iCol++) {
				String columnName = columnsToHeaderNames.get(iCol);
				if (columnName.equals(groupName))
					groupWidth += dataViewer.getTable().getColumn(iCol).getWidth();
			}
			tCL.setColumnData(headerViewer.getTable().getColumn(groupNames.indexOf(groupName)), new ColumnWeightData(groupWidth));
		}
		
	}

	// Editing support may need to manufacture its classes, hence a factory interface is used.
	/**
	 * Sets the per column editing support.
	 * <p>
	 * Hiding the multiple {@link TableViewer}s means that the 
	 * {@link EditingSupport} objects cannot be create by the surrounding
	 * objects. To allow creation of the {@link EditindSupport} classes, a
	 * Factory implementation is defined in {@link EditingSupportFactory}.
	 * @param column
	 *	 			column for which the editing is to be defined
	 * @param editingSupportFactory
	 * 							the factory class to produce the editing support classes.
	 * 			
	 */
	public void setColumnEditingSupport(TableViewerColumn column, EditingSupportFactory editingSupportFactory) {
		column.setEditingSupport(editingSupportFactory.get(dataViewer));//.get(getIndexOfTableContaining(column.getColumn()))));
	}

	/**
	 * Refreshes all sub-tables.
	 */
	public void refresh() {
		dataViewer.refresh();
		headerViewer.refresh();
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
	public void createColumnGroup(String groupNameIn, boolean isLastGroup) {
		String groupName  = (groupNameIn != null) ? groupNameIn : "";
		// Do not add duplicate named groups
		if (!groupName.equals("") && groupNames.contains(groupName)) return;
		// Otherwise, add a group with this name to the end of the list of groups
		groupNames.add(groupName);

		TableViewerColumn tVC = new TableViewerColumn(headerViewer, SWT.NONE);
		tVC.getColumn().setText(groupName);
		((TableColumnLayout) headerTableCompo.getLayout()).setColumnData(tVC.getColumn(), new ColumnWeightData(10));
		
		
	}
	
	/**
	 * Adds a column to a group.
	 * <p>
	 * Adds a new column to the end of the named group. A previously unnamed
	 * group is created at the end. If the group to be added to is anonymous,
	 * and the last defined group was not, define a new anonymous group at the
	 * end of the List.
	 * @param groupID
	 * 				The name of the group the column is to be added to
	 * @param style
	 * 				SWT style of the Viewer to be created
	 * @return
	 * 		The new TableViewerColumn
	 */
	public TableViewerColumn addColumn(String groupID, int style) {

		int columnIndex;
		if (columnsToHeaderNames.contains(groupID)) {
			columnIndex = columnsToHeaderNames.lastIndexOf(groupID) + 1;
		} else {
			// Add an unknown group to the index
			createColumnGroup(groupID, false);
			columnIndex = columnsToHeaderNames.size();
		}
		
		TableViewerColumn tVC = new TableViewerColumn(dataViewer, style, columnIndex); 
		
		columnsToHeaderNames.add(columnIndex, groupID);
		
		return tVC;
					
	}


	/**
	 * Sets the content providers for all sub-tables.
	 * @param iStructuredContentProvider
	 * 									the content provider class to be used.
	 */
	public void setContentProvider(IStructuredContentProvider iStructuredContentProvider) {
		cachedContentProvider = iStructuredContentProvider;
		dataViewer.setContentProvider(iStructuredContentProvider);
		if (cachedInput != null)
			dataViewer.setInput(cachedInput);
	}
	
	/**
	 * Sets the input data.
	 * @param input
	 * 				Object that provides the input.
	 */
	public void setInput(Object input) {
		cachedInput = input;
		if (cachedContentProvider != null)
			dataViewer.setInput(cachedInput);
	}
	
	/**
	 * Sets the LabelProvider for the entire Table
	 * @param iTLP
	 * 			Label provider for all the tables 
	 */
	public void setLabelProvider(ITableLabelProvider iTLP) {
	}
	
	/**
	 * Adds a {@link ISelectionChangedListener} defined by the user.
	 * @param iSCL
	 * 			ISelectionChangedListener to be used table wide
	 */
	public void addSelectionChangedListener(ISelectionChangedListener iSCL) {
		cachedUserSelectionChangedListener = iSCL;
	}
	
	/**
	 * Adds drag support to the table
	 * @param dragFlags
	 * 				as {@link Table.addDragSupport().
	 * @param transfers
	 * 				as {@link Table.addDragSupport().
	 * @param dSL
	 * 				as {@link Table.addDragSupport().
	 */
	public void addDragSupport(int dragFlags, Transfer[] transfers, DragSourceAdapter dSL) {
		cachedDragFlags = dragFlags;
		cachedDragTransfers = transfers;
		cachedDragSourceListener = dSL;
		dataViewer.addDragSupport(cachedDragFlags, cachedDragTransfers, cachedDragSourceListener);
	}
	
	public void addDropSupport(int dropFlags, Transfer[] transfers, ViewerDropAdapterFactory dTLF) {
		cachedDropFlags = dropFlags;
		cachedDropTransfers = transfers;
		cachedDropTargetListenerFactory = dTLF;
		dataViewer.addDropSupport(cachedDropFlags, cachedDropTransfers, cachedDropTargetListenerFactory.get(dataViewer));
	}
	
	/**
	 * Gets the selected data
	 * @return the selected data of the table
	 */
	public ISelection getSelection() {
		return dataViewer.getSelection();
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
				if (cachedUserSelectionChangedListener != null)
					cachedUserSelectionChangedListener.selectionChanged(event);
			}
		}
	}

	/**
	 * Gets the TableColumn within the table that is sorted 
	 * @return
	 * 		the object of the column that is sorted; otherwise -1.
	 */
	public TableColumn getSortColumn() {
		return dataViewer.getTable().getSortColumn();
	}
	
	/**
	 * Gets the direction of the sorted column within the table
	 * @return
	 * 		SWT.UP, SWT.DOWN or SWT.NONE
	 */
	public int getSortDirection() {
		return dataViewer.getTable().getSortDirection();
	}
	
	/**
	 * Set the column to be sorted.
	 * @param column
	 * 				the column to be sorted. Can be a valid column in any group viewer.
	 */
	public void setSortColumn(TableColumn column) {
		dataViewer.getTable().setSortColumn(column);
	}
	
	/**
	 * Sets the sort direction of the sorted column.
	 * @param direction
	 * 				SWT.UP, SWT.DOWN or SWT.NONE
	 */
	public void setSortDirection(int direction) {
		dataViewer.getTable().setSortDirection(direction);
	}
	
	/**
	 * Creates a context menu look in each {@link TableViewer}.
	 * @param menuManager
	 * 					the managed menu to be inserted.
	 */
	public void createContextMenu(MenuManager menuManager) {
		Menu popupMenu = menuManager.createContextMenu(dataViewer.getControl());
		dataViewer.getControl().setMenu(popupMenu);
	}
	
	@Override
	public void addMouseListener(MouseListener listener) {
		dataViewer.getTable().addMouseListener(listener);
	}
	
	@Override
	public void removeMouseListener(MouseListener listener) {
		dataViewer.getTable().removeMouseListener(listener);
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when the user changes the receiver's selection, by sending it one of the
	 * messages defined in the SelectionListener interface.
	 * <p>
	 * When widgetSelected is called, the item field of the event object is
	 * valid. If the receiver has the SWT.CHECK style and the check selection
	 * changes, the event object detail field contains the value SWT.CHECK.
	 * widgetDefaultSelected is typically called when an item is
	 * double-clicked. The item field of the event object is valid for default
	 * selection, but the detail field is not used.
	 * <p>
	 * Passes the listener through to the underlying {@link Table}.
	 * @param listener
	 * 				the listener which should be notified when the user changes the receiver's selection
	 */
	public void addSelectionListener(SelectionListener listener) {
		dataViewer.getTable().addSelectionListener(listener);
	}

	/**
	 * Removes the listener from the collection of listeners who will be notified when the user changes the receiver's selection.
	 * <p>
	 * Removes the listener from the underlying {@link Table}.
	 * @param listener
	 * 				 the listener which should no longer be notified.
	 */
	public void removeSelectionListener(SelectionListener listener) {
		dataViewer.getTable().removeSelectionListener(listener);
	}
}