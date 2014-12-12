package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.dawnsci.python.rpc.action.InjectPyDevConsole;
import org.dawnsci.python.rpc.action.InjectPyDevConsoleAction;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public abstract class AbstractPtychoEditor extends EditorPart {

	private static final Logger logger = LoggerFactory.getLogger(AbstractPtychoEditor.class);

	protected List<PtychoData> levels;
	protected List<PtychoNode> tree;
	protected String jsonSavedPath;
	protected String fullPath;
	protected boolean isDirtyFlag = false;

	private InjectPyDevConsoleAction runPython;

	@Override
	public void doSave(IProgressMonitor monitor) {
		List<PtychoData> list = PtychoTreeUtils.extract(tree);
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		String fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
		if (fileSavedPath == null)
			doSaveAs();
		PtychoUtils.saveCSVFile(fileSavedPath, list);
		setDirty(false);
	}

	@Override
	public void doSaveAs() {
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		String fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
		saveAs(fileSavedPath);
		setDirty(false);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
//		IPreferenceStore store = Activator.getPtychoPreferenceStore();
//		fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
//		fullPath = PtychoUtils.getFullPath(input);
//		try {
//			String path = "";
//			if (fileSavedPath != null || !fileSavedPath.equals("")) {
//				File f = new File(fileSavedPath);
//				if (!f.exists())
//					path = fullPath;
//				else
//					path = fileSavedPath;
//			} else
//				path = fullPath;
//			levels = PtychoUtils.loadSpreadSheet(path);
//			if (levels != null)
//				tree = PtychoTreeUtils.populate(levels);
//		} catch (Exception e) {
//			logger.error("Error loading spreadsheet file:" + e.getMessage());
//			e.printStackTrace();
//		}
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

	protected void createPythonRunCommand(Composite parent) {
		runPython = new InjectPyDevConsoleAction("Run Ptychographic Iterative Engine python script") {
			@Override
			public void run() {
				IPreferenceStore store = Activator.getPtychoPreferenceStore();
				String fileSavedPath = store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
				jsonSavedPath = saveJSon(fileSavedPath);
				// reinject command
				this.setParameter(InjectPyDevConsole.INJECT_COMMANDS_PARAM, getPythonCmd(jsonSavedPath));
				super.run();
			}
		};
		runPython.setDataInjected(false);
		runPython.setParameter(InjectPyDevConsole.CREATE_NEW_CONSOLE_PARAM, Boolean.TRUE.toString());
		runPython.setParameter(InjectPyDevConsole.INJECT_COMMANDS_PARAM, getPythonCmd(jsonSavedPath));
		ActionContributionItem aci = new ActionContributionItem(runPython);
		aci.fill(parent);
		Button runButton = (Button) aci.getWidget();
		runButton.setText("RUN");
		runButton.setToolTipText("Run Ptychographic Iterative Engine process");
		runButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false));
	}

	private String getPythonCmd(String jsonSavedPath) {
		StringBuilder pythonCmd = new StringBuilder();
		pythonCmd.append("run ");
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		String epiFolder = store.getString(PtychoPreferenceConstants.PIE_RESOURCE_PATH);
		pythonCmd.append(epiFolder + File.separator + "LaunchPtycho.py ");
		pythonCmd.append(jsonSavedPath);
		pythonCmd.append("\n");
		return pythonCmd.toString();
	}

	private String saveJSon(String fileSavedPath) {
		if (fileSavedPath == null || fileSavedPath.equals("")) {
			//trigger the save wizard
			saveAs(fileSavedPath);
			if (fileSavedPath == null)
				return "";
			setDirty(false);
		}
		String jsonSavedPath = fileSavedPath.substring(0, fileSavedPath.length() - 3);
		jsonSavedPath += "json";
		String json = PtychoTreeUtils.jsonMarshal(tree);
		try {
			PtychoUtils.saveJSon(jsonSavedPath, json);
		} catch (IOException e) {
			logger.error("Error saving JSON file:" + e.getMessage());
			MessageDialog.openError(Display.getDefault()
					.getActiveShell(), "Error writing JSON file", "An IO error occured while writing '"
					+ jsonSavedPath
					+ "' JSON file.");
		}
		return jsonSavedPath;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void saveAs(String fileSavedPath) {
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
