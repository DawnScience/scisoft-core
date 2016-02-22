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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;

/**
 * A table with grouped columns.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
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
	private ViewerDropAdapterFactory cachedDropTargetListenerFactory;

	// An ordered list of the TableViewers containing the column groups, and
	// their names 
	private List<TableViewer> groupViewers;
	private List<String> groupNames;
	
	private int sortedGroup; // -1 signifies no column is sorted
	
	// If the SelectionChanged listener propagates blindly, you will eventually 
	// exhaust the stack
	private boolean propagateSelectionChange;
	
	// summed widths of the columns per group
	private int[] groupWidths;
	
	public XPDFGroupedTable(Composite parent, int style) {
		super(parent, style);
		// Attach to the left and right edges of the parent, and set to a fixed height
		this.setLayout(new FormLayout());
		
		// Composite to hold the table column groups. Fills this class's
		// Composite
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
		groupWidths = new int[0];
		
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
		cachedDropTargetListenerFactory = null;
	}			

	public void setColumnWidth(TableViewerColumn col, Integer weight) {
		
		TableViewer tV = null;
		for (TableViewer iTV : groupViewers) {
			if (iTV.getTable() == col.getColumn().getParent()) {
				tV = iTV;
				break;
			}
		}
		if (tV != null) {
			TableColumnLayout tCL = (TableColumnLayout) tV.getTable().getParent().getLayout();
			tCL.setColumnData(col.getColumn(), new ColumnWeightData(weight));
			
			groupWidths[groupViewers.indexOf(tV)] += weight;
			tableCompo.setWeights(groupWidths);
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
		column.setEditingSupport(editingSupportFactory.get(groupViewers.get(getIndexOfTableContaining(column.getColumn()))));
	}

	/**
	 * Refreshes all sub-tables.
	 */
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
	public void createColumnGroup(String groupNameIn, boolean isLastGroup) {
		String groupName  = (groupNameIn != null) ? groupNameIn : "";
		// Do not add duplicate named groups
		if (!groupName.equals("") && groupNames.contains(groupName)) return;
		// Otherwise, add a group with this name to the end of the list of groups
		groupNames.add(groupName);
		groupViewers.add(createColumnGroupInternal(tableCompo, groupName, isLastGroup));
		groupWidths = Arrays.copyOf(groupWidths, groupWidths.length+1);
		groupWidths[groupWidths.length-1] = 0;
	}
	
	
	private TableViewer createColumnGroupInternal(Composite parent, String groupName, boolean isLastGroup) {
		Composite groupCompo = new Composite(parent, SWT.NONE);		
		groupCompo.setLayout(new FormLayout());

		// Add the Group column header as a do-nothing button
		Button headerButton = new Button(groupCompo, SWT.PUSH);

		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		headerButton.setLayoutData(formData);

		// An empty string makes the button collapse. Use a zero width,
		// non-breaking space instead
		headerButton.setText( (groupName.equals("")) ? "﻿" : groupName );

		// Add the table that will hold this subset of the columns
		Composite subTableCompo = new Composite(groupCompo, SWT.NONE);

		formData = new FormData();
		formData.top = new FormAttachment(headerButton);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		subTableCompo.setLayoutData(formData);

		// Define the sub-table
		TableViewer tV = new TableViewer(subTableCompo, SWT.NO_SCROLL | ((isLastGroup) ? SWT.V_SCROLL : SWT.NONE));

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
		
		// Synchronize scrolling
		if (isLastGroup)
			tV.getTable().getVerticalBar().addSelectionListener(new ScrollSynchronizer(tV));
		
		return tV;
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
		// TODO: Add column group if it does not exist
		if (!groupNames.contains(groupID)) return null;
		TableViewer groupViewer = groupViewers.get(groupNames.lastIndexOf(groupID));

		
		return new TableViewerColumn(groupViewer, style);
	}


	/**
	 * Sets the content providers for all sub-tables.
	 * @param iStructuredContentProvider
	 * 									the content provider class to be used.
	 */
	public void setContentProvider(IStructuredContentProvider iStructuredContentProvider) {
		cachedContentProvider = iStructuredContentProvider;
		for (TableViewer tV : groupViewers)
			tV.setContentProvider(iStructuredContentProvider);
		if (cachedInput != null)
			for (TableViewer tV : groupViewers)
				tV.setInput(cachedInput);
	}
	
	/**
	 * Sets the Eclipse input.
	 * @param input
	 * 				Object that provides the input.
	 */
	public void setInput(Object input) {
		cachedInput = input;
		if (cachedContentProvider != null)
			for (TableViewer tV : groupViewers)
				tV.setInput(cachedInput);
	}
	
	/**
	 * Sets the LabelProvider for the entire Table
	 * @param iTLP
	 * 			Label provider for all the tables 
	 */
	public void setLabelProvider(ITableLabelProvider iTLP) {
		cachedLabelProvider = iTLP;
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
		for (TableViewer tV : groupViewers)
				tV.addDragSupport(cachedDragFlags, cachedDragTransfers, cachedDragSourceListener);
	}
	
	public void addDropSupport(int dropFlags, Transfer[] transfers, ViewerDropAdapterFactory dTLF) {
		cachedDropFlags = dropFlags;
		cachedDropTransfers = transfers;
		cachedDropTargetListenerFactory = dTLF;
		for (TableViewer tV : groupViewers)
				tV.addDropSupport(cachedDropFlags, cachedDropTransfers, cachedDropTargetListenerFactory.get(tV));
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

	/**
	 * Gets the TableColumn within the table that is sorted 
	 * @return
	 * 		the object of the column that is sorted; otherwise -1.
	 */
	public TableColumn getSortColumn() {
		return (sortedGroup != -1) ? groupViewers.get(sortedGroup).getTable().getSortColumn() : null;
	}
	
	/**
	 * Gets the direction of the sorted column within the table
	 * @return
	 * 		SWT.UP, SWT.DOWN or SWT.NONE
	 */
	public int getSortDirection() {
		return (sortedGroup != -1) ? groupViewers.get(sortedGroup).getTable().getSortDirection() : SWT.NONE;
	}
	
	/**
	 * Set the column to be sorted.
	 * @param column
	 * 				the column to be sorted. Can be a valid column in any group viewer.
	 */
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
	
	/**
	 * Sets the sort direction of the sorted column.
	 * @param direction
	 * 				SWT.UP, SWT.DOWN or SWT.NONE
	 */
	public void setSortDirection(int direction) {
		if (sortedGroup != -1)
			groupViewers.get(sortedGroup).getTable().setSortDirection(direction);
	}
	
	/**
	 * Creates a context menu look in each {@link TableViewer}.
	 * @param menuManager
	 * 					the managed menu to be inserted.
	 */
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
	
	class ScrollSynchronizer implements SelectionListener {

		TableViewer Tv;
		
		public ScrollSynchronizer(TableViewer Tv) {
			this.Tv = Tv;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			for (TableViewer iTV : groupViewers) {
				if (iTV != Tv) { // yes, inequality by reference
					iTV.getTable().setTopIndex(Tv.getTable().getTopIndex());
				}
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
}