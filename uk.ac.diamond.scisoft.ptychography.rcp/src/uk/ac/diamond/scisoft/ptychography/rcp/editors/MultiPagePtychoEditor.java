package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class MultiPagePtychoEditor extends MultiPageEditorPart {

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoMultiPageEditor";

	@Override
	protected void createPages() {
		try {
			int index = 0;
			PtychoTreeViewerEditor treeEditor = new PtychoTreeViewerEditor();
			addPage(index, treeEditor, getEditorInput());
			setPageText(index, "Advanced");
			index++;

			SimplePtychoEditor simpleEditor = new SimplePtychoEditor();
			addPage(index, simpleEditor, getEditorInput());
			setPageText(index, "Basic");
			index++;
		
			
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
