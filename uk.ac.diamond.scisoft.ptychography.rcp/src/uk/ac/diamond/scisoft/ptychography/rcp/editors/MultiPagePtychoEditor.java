package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public class MultiPagePtychoEditor extends MultiPageEditorPart {

	private static final Logger logger = LoggerFactory.getLogger(MultiPagePtychoEditor.class);

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoMultiPageEditor";
	private PtychoTreeViewerEditor treeEditor;
	private SimplePtychoEditor simpleEditor;
	protected List<PtychoData> levels;
	protected List<PtychoNode> tree;
	protected String fullPath;
	protected String fileSavedPath;
	protected String jsonSavedPath;
	protected boolean isDirtyFlag = false;

	@Override
	protected void createPages() {
		try {
			treeEditor = new PtychoTreeViewerEditor(levels, tree, fullPath, isDirtyFlag);
			addPage(0, treeEditor, getEditorInput());
			setPageText(0, "Advanced");

			simpleEditor = new SimplePtychoEditor(levels, tree, isDirtyFlag);
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
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		String fullPath = PtychoUtils.getFullPath(input);
		File file = new File(fullPath);
		String path = "";
		if (file != null && file.isFile() && file.canWrite()) {
			this.fileSavedPath = fullPath;
			path = fullPath;
		} else {
			IPreferenceStore store = Activator.getPtychoPreferenceStore();
			String fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
			if (fileSavedPath != null && !fileSavedPath.equals("")) {
				File f = new File(fileSavedPath);
				if (!f.exists())
					path = fullPath;
				else
					path = fileSavedPath;
			} else
				path = fullPath;
		}
		try {
			levels = PtychoUtils.loadSpreadSheet(path);
			if (levels != null)
				tree = PtychoTreeUtils.populate(levels);
		} catch (Exception e) {
			logger.error("Error loading spreadsheet file:" + e.getMessage());
			e.printStackTrace();
		}
		setSite(site);
		setInput(input);

	}

	@Override
	public boolean isDirty() {
		return treeEditor.isDirtyFlag;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		List<PtychoData> list = PtychoTreeUtils.extract(tree);
//		IPreferenceStore store = Activator.getPtychoPreferenceStore();
//		String fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
		if (fileSavedPath == null)
			doSaveAs();
		PtychoUtils.saveCSVFile(fileSavedPath, list);
		treeEditor.setDirty(false);
	}

	@Override
	public void doSaveAs() {
//		IPreferenceStore store = Activator.getPtychoPreferenceStore();
//		String fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
		treeEditor.saveAs(fileSavedPath);
		treeEditor.setDirty(false);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

}
