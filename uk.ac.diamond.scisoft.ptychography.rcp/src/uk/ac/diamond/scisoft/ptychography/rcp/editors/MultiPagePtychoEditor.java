package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.util.List;

import org.dawnsci.common.widgets.editor.ITitledEditor;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public class MultiPagePtychoEditor extends MultiPageEditorPart implements ITitledEditor, IResourceChangeListener {

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

	public MultiPagePtychoEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	@Override
	protected void createPages() {
		try {
			treeEditor = new PtychoTreeViewerEditor(levels, tree, fullPath, isDirtyFlag);
			treeEditor.setFileSavedPath(fileSavedPath);
			addPage(0, treeEditor, getEditorInput());
			setPageText(0, "Advanced");

			simpleEditor = new SimplePtychoEditor(levels, tree, isDirtyFlag);
			addPage(1, simpleEditor, getEditorInput());
			setPageText(1, "Basic");
		} catch (PartInitException e) {
			logger.error("Error creating pages:" + e.getMessage());
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
//		setSite(site);
//		setInput(input);
		super.init(site, input);

	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	@Override
	public boolean isDirty() {
		return treeEditor.isDirtyFlag;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
//		getEditor(1).doSave(monitor);
	}
	

	@Override
	public void doSaveAs() {
		IEditorPart treeEditor = getEditor(0);
		treeEditor.doSaveAs();
		setPageText(0, treeEditor.getTitle());
		setInput(treeEditor.getEditorInput());
//		IEditorPart simpleEditor = getEditor(1);
//		simpleEditor.doSaveAs();
//		setPageText(1, simpleEditor.getTitle());
//		setInput(simpleEditor.getEditorInput());
	}

	@SuppressWarnings("unused")
	private String getFileName(String fileSavedPath) {
		String[] temp = fileSavedPath.split(File.separator);
		if (temp.length == 0)
			return "";
		return temp[temp.length - 1];
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void setPartTitle(String name) {
		super.setPartName(name);
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
							.getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) treeEditor.getEditorInput())
								.getFile().getProject()
								.equals(event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(treeEditor
									.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

}
