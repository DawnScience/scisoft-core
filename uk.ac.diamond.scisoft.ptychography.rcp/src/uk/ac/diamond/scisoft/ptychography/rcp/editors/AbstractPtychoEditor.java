package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public abstract class AbstractPtychoEditor extends EditorPart {

	private static final Logger logger = LoggerFactory.getLogger(AbstractPtychoEditor.class);

	protected List<PtychoData> levels;
	protected String fullPath;
	protected String fileSavedPath;
	protected String jsonSavedPath;
	protected boolean isDirtyFlag = false;

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

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

	protected void setDirty(boolean value) {
		isDirtyFlag = value;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public abstract void createPartControl(Composite parent);

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
}
