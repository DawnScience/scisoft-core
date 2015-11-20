package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class XPDFGroupedTable extends Composite {

	private SashForm topLevelSash;
	
	private List<TableViewer> groupViewers;
	private List<String> groupNames;

	private List<ISelectionChangedListener> tableSelectionListeners;
	private boolean propagateSelectionChange;
	
	public XPDFGroupedTable(Composite parent, int style) {
		super(parent, style);
		
		groupViewers = new ArrayList<TableViewer>();
		groupNames = new ArrayList<String>();
		
		this.setLayout(new FormLayout());
		topLevelSash = new SashForm(parent, SWT.BORDER);
		FormData sashForm = new FormData();
		sashForm.left = new FormAttachment(0);
		sashForm.right = new FormAttachment(100);
		sashForm.top = new FormAttachment(0);
		sashForm.bottom = new FormAttachment(100);
		topLevelSash.setLayoutData(sashForm);
		topLevelSash.setSashWidth(0);

	}

	public TableViewerColumn newGroupedColumn(String id) {
		return newGroupedColumn(id, SWT.NONE);
	}
	
	/**
	 * Creates a new grouped column for the given group name with the given style bits.
	 * <p>
	 * Given a  named group, a new column is inserted at the end of the group.
	 * If the group name has not previously been defined, a new group with that
	 * name is defined, containing that new column. If the name is 
	 * <code>null</code> or the empty <code>String</code>, then the new column
	 * is added to the anonymous group, if the last defined group is anonymous,
	 * or starts a new anonymous group otherwise.  
	 * @param id
	 * 			the name of the group to add the column to. May be <code>null</code> or "". 
	 * @param style
	 * 			the style bits of the new column. As <code>new TableViewerColumn</code>.
	 * @return the new TableViewerColumn, associated with the named group.
	 */
	public TableViewerColumn newGroupedColumn(String id, int style) {
		return newGroupedColumn(id, style, -1);
	}
	
	/**
	 * Creates a new grouped column for the given group name with the given style bits.
	 * <p>
	 * Given a  named group, a new column is inserted at the given index.
	 * If the group name has not previously been defined, a new group with that
	 * name is defined, containing that new column.
	 * <p>
	 * If the name is <code>null</code> or the empty <code>String</code>, then
	 * the new column is added to the anonymous group at the position given by
	 * the index of all columns so far existing, if that position is covered by
	 * an anonymous group. Otherwise, the new column goes in the first
	 * anonymous group <emph>after</emph> that position, creating a new
	 * anonymous group if necessary.
	 * @param id
	 * 			the name of the group to add the column to. May be
	 * 			<code>null</code> or "". 
	 * @param style
	 * 			the style bits of the new column. As <code>new
	 * 			TableViewerColumn</code>.
	 * @param index
	 * 			the index within a named group, or the relative index for a
	 * 			column in the anonymous group.
	 * @return the new TableViewerColumn, associated with the named group.
	 */
	public TableViewerColumn newGroupedColumn(String inID, int style, int index) {

		String id = (inID == null) ? "" : inID;
		
		if (id.equals("")) {
			if (index == -1) {
				// Add a column in an anonymous group to the end of the table
				if (!groupNames.get(groupNames.size()-1).equals(""))
					makeNewGroup("");

				// The last group is now guaranteed to be anonymous, add the
				// new column to the end of it
				return new TableViewerColumn(groupViewers.get(groupViewers.size()-1), style, -1);
			} else {
				// For an anonymous group, the index is over the total count of
				// columns, and it is inserted at or after that index, should
				// that column lie in a non-anonymous group
				int groupIndex = 0;
				int columnIndex = 0;
				int addIndex = 0;
				while (columnIndex < index) {
					columnIndex += groupViewers.get(groupIndex).getTable().getColumnCount();
					groupIndex++;
				}
				groupIndex--;
				columnIndex -= groupViewers.get(groupIndex).getTable().getColumnCount();
				addIndex = index - columnIndex;
				// groupIndex now holds the index of the group containing the
				// index-th column, and columnIndex the ordinal of the first
				// column of that group.
				if (!groupNames.get(groupIndex).equals("")) {
					addIndex = 0;
					while (groupNames.get(groupIndex) != "") {
						groupIndex++;
						if (groupIndex == groupNames.size()) break;
					}
					if (groupIndex == groupNames.size())
						makeNewGroup("");
				}

				return new TableViewerColumn(groupViewers.get(groupIndex), style, addIndex);
			}
		} else {
			// Add to a named group with the given index *within that group*
			if (!groupNames.contains(id))
				makeNewGroup(id);

			return new TableViewerColumn(groupViewers.get(groupNames.indexOf(id)), style, index);

		}
	}
	
	/**
	 * Make all the data structures required to hold a new group of columns
	 * @param id
	 * 			name of the group of columns. Apart from <code>null</code>s
	 * 			and "", repeated group names are not allowed. 
	 */
	private void makeNewGroup(String id) {
		
		String labelID = (id==null) ? "" : id;
		
		// The Composite containing the group header and all the columns in that group
		Composite groupCompo = new Composite(topLevelSash, SWT.NONE);
		groupCompo.setLayout(new FormLayout());
		
		// The Button pretending to be the column header
		Button headerButton = new Button(groupCompo, SWT.TOGGLE);
		// Might make sure the button is never selected. Might cause a stack overflow.
		headerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				((Button) event.item).setSelection(false);
			}
		});
		
		FormData buttonForm = new FormData();
		buttonForm.top = new FormAttachment(0);
		buttonForm.left = new FormAttachment(0);
		buttonForm.right = new FormAttachment(100);
		headerButton.setLayoutData(buttonForm);
		
		headerButton.setText(labelID);
		
		// The Composite that will hold the sub-table
		Composite subTableCompo = new Composite(groupCompo, SWT.NONE);
		
		FormData tableForm = new FormData();
		tableForm.top = new FormAttachment(headerButton);
		tableForm.bottom = new FormAttachment(100);
		tableForm.left = new FormAttachment(0);
		tableForm.right = new FormAttachment(100);
		subTableCompo.setLayoutData(tableForm);
		
		// The TableViewer that will hold the data
		TableViewer tV = new TableViewer(subTableCompo);
		groupViewers.add(tV);
		groupNames.add(labelID);
		
		subTableCompo.setLayout(new TableColumnLayout());
		
		// Set the style of the sub-table.
		tV.getTable().setHeaderVisible(true);
		tV.getTable().setLinesVisible(true);
		
		tV.addSelectionChangedListener(new SubTableSelectionListener());
		
	}
	
	/**
	 * Adds a listener for selection changes in this selection provider. Has no
	 * effect if an identical listener is already registered
	 * @param listener
	 * 				a selection changed listener
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (tableSelectionListeners == null)
			tableSelectionListeners = new ArrayList<ISelectionChangedListener>();
		if (!tableSelectionListeners.contains(listener))
			tableSelectionListeners.add(listener);
	}
	
	@Override
	public boolean setFocus() {
		boolean gotFocus = true;
		for (TableViewer tV : groupViewers)
			gotFocus &= tV.getControl().setFocus();
		return gotFocus;
	}
	
	public void setContentProvider(IContentProvider cP) {
		for (TableViewer tV : groupViewers)
			// Is this a terrible idea? the same object, rather than copies
			tV.setContentProvider(cP);
		}
	
	public void setInput(Object in) {
		for (TableViewer tV : groupViewers)
			tV.setInput(in);
	}
	
	private class SubTableSelectionListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if (propagateSelectionChange) {
				propagateSelectionChange = false;
				
				for (ISelectionChangedListener listener : tableSelectionListeners)
					listener.selectionChanged(event);
				
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					for (TableViewer tV : groupViewers)
						tV.setSelection(selection, true);
				}
				propagateSelectionChange = true;
			}
		}
	}
}
