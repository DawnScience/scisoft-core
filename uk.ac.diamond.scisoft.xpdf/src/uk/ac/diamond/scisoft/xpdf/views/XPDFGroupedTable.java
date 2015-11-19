package uk.ac.diamond.scisoft.xpdf.views;

import java.util.List;

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
	
	public XPDFGroupedTable(Composite parent, int style) {
		super(parent, style);
		topLevelSash = new SashForm(this, SWT.BORDER);
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
	public TableViewerColumn newGroupedColumn(String id, int style, int index) {
		if (id != null && id.equalsIgnoreCase("")) {
			// Named group
			if (!groupNames.contains(id))
				makeNewGroup(id);

		
		}
	}
	
	private void makeNewGroup(String id) {
		if (id != null && !id.equals("") && groupNames.contains(id)) return;
		
		// The Composite containing the group header and all the columns in that group
		Composite groupCompo = new Composite(topLevelSash, SWT.NONE);
		groupCompo.setLayout(new FormLayout());
		
		// The Button pretending to be the column header
		Button headerButton = new Button(groupCompo, SWT.TOGGLE);
		// Might make sure the button is never selected. Might cause a Stack Overflow.
		headerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				((Button) event.item).setSelection(false);
			}
		});
		
		
		
		groupViewers.add(new)
	}
	
	
}
