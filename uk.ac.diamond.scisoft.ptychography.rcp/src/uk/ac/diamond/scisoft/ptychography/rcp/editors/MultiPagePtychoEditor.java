package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
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
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;
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
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
		fullPath = PtychoUtils.getFullPath(input);
		try {
			String path = "";
			if (fileSavedPath != null || !fileSavedPath.equals("")) {
				File f = new File(fileSavedPath);
				if (!f.exists())
					path = fullPath;
				else
					path = fileSavedPath;
			} else
				path = fullPath;
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
		return isDirtyFlag;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		List<PtychoData> list = PtychoTreeUtils.extract(tree);
		if (fileSavedPath == null)
			doSaveAs();
		PtychoUtils.saveCSVFile(fileSavedPath, list);
		setDirty(false);
	}

	@Override
	public void doSaveAs() {
		saveAs();
		setDirty(false);
	}

	protected void setDirty(boolean value) {
		isDirtyFlag = value;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void saveAs() {
		FileDialog dialog = new FileDialog(Display.getDefault()
				.getActiveShell(), SWT.SAVE);
		dialog.setText("Choose file path and name to save parameter input");
		String[] filterExtensions = new String[] {
				"*.csv;*.CSV", "*.json;*.JSON",
				"*.xml;*.XML" };
		if (fileSavedPath != null) {
			dialog.setFilterPath((new File(fileSavedPath)).getParent());
		} else {
			String filterPath = "/";
			String platform = SWT.getPlatform();
			if (platform.equals("win32") || platform.equals("wpf")) {
				filterPath = "c:\\";
			}
			dialog.setFilterPath(filterPath);
		}
		dialog.setFilterNames(PtychoConstants.FILE_TYPES);
		dialog.setFilterExtensions(filterExtensions);
		fileSavedPath = dialog.open();
		if (fileSavedPath == null) {
			return;
		}
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		store.setValue(PtychoPreferenceConstants.FILE_SAVE_PATH, fileSavedPath);
		try {
			final File file = new File(fileSavedPath);
			if (file.exists()) {
				boolean yes = MessageDialog.openQuestion(Display.getDefault()
						.getActiveShell(), "Confirm Overwrite", "The file '"
						+ file.getName()
						+ "' exists.\n\nWould you like to overwrite it?");
				if (!yes)
					;
			}
			String fileType = PtychoConstants.FILE_TYPES[dialog.getFilterIndex()];
			if (fileType.equals("CSV File")) {
				List<PtychoData> list = PtychoTreeUtils.extract(tree);
				PtychoUtils.saveCSVFile(fileSavedPath, list);
			} else if (fileType.equals("JSon File")) {
				String json = PtychoTreeUtils.jsonMarshal(tree);
				PtychoUtils.saveJSon(fileSavedPath, json);
			} else {
				throw new Exception("XML serialisation is not yet implemented.");
			}
		} catch (Exception e) {
			logger.error("Error saving file:"+ e.getMessage());
		}
	}
}
