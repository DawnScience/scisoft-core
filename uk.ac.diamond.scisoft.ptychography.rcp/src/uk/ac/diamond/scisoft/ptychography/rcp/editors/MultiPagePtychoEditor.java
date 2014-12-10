package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class MultiPagePtychoEditor extends MultiPageEditorPart {

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoMultiPageEditor";
	private PtychoTreeViewerEditor treeEditor;
	private SimplePtychoEditor simpleEditor;

	@Override
	protected void createPages() {
		try {
			treeEditor = new PtychoTreeViewerEditor();
			addPage(0, treeEditor, getEditorInput());
			setPageText(0, "Advanced");

			simpleEditor = new SimplePtychoEditor();
			addPage(1, simpleEditor, getEditorInput());
			setPageText(1, "Basic");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (newPageIndex == 0)
			treeEditor.refresh();
		else if (newPageIndex == 1)
			simpleEditor.refresh();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
