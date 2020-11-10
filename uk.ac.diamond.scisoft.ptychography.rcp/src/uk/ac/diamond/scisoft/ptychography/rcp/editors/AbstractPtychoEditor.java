package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.dawnsci.python.rpc.action.InjectPyDevConsole;
import org.dawnsci.python.rpc.action.InjectPyDevConsoleAction;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.internal.workbench.handlers.SaveHandler;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.ui.FolderSelectionWidget;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public abstract class AbstractPtychoEditor {

	private static final Logger logger = LoggerFactory.getLogger(AbstractPtychoEditor.class);
	private static final String ENABLE_TEXT_BOXES = "enableTextBoxes";
	static final String ALTERNATE_SCRIPT = "Alternate Script";
	static final String RECON_SCRIPT = "Recon Script";
	private String defaultScript;

	protected List<PtychoData> levels;
	protected List<PtychoNode> tree;
	protected String jsonSavedPath;
	protected String fullPath;
	protected boolean isDirtyFlag = false;
	protected boolean isTextBoxInputEnabled = false;
	@Inject MDirtyable dirty;
	@Inject ECommandService commandService;
	@Inject EHandlerService handlerService;
	

	private InjectPyDevConsoleAction runPython;

	private String fileSavedPath;
	
	private FolderSelectionWidget processDir;
	private Text configName, scanNumber;
	
	@PersistState
	public void saveState(MPart part) {
		part.getPersistedState().put(ENABLE_TEXT_BOXES, Boolean.toString(isTextBoxInputEnabled));
	}
	
	@PostConstruct
	public void loadState(MPart part) {
		if(part.getPersistedState().get(ENABLE_TEXT_BOXES) != null)
			isTextBoxInputEnabled = Boolean.parseBoolean(part.getPersistedState().get(ENABLE_TEXT_BOXES));
	}

	public boolean isDirty() {
		return dirty.isDirty();
	}

	protected void setDirty(boolean value) {
		handlerService.activateHandler("org.eclipse.ui.file.save", new SaveHandler());
		dirty.setDirty(value);
		commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
	}

	@Persist
	public void doSave(IProgressMonitor monitor) {
		fileSavedPath = getFileSavePathPreference();
		List<PtychoData> list = PtychoTreeUtils.extract(tree);
		if (fileSavedPath == null || fileSavedPath.equals(""))
			doSaveAs();
		// if doSaveAs() dialog was cancelled we don't want to save or change state
		if (fileSavedPath != null && !fileSavedPath.equals("")) {
			if(fileSavedPath.endsWith(".json"))
				saveJSon(fileSavedPath);
			else
				PtychoUtils.saveCSVFile(fileSavedPath, list);
			setFileSavedPath(fileSavedPath);
			setDirty(false);
			commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
		}
	}

	public void doSaveAs() {
		fileSavedPath = saveAs(fileSavedPath);
		if(fileSavedPath != null && !fileSavedPath.equals(""))
			setDirty(false);
	}

	public AbstractPtychoEditor() {
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		String fileSavedPath = store.getString(PtychoPreferenceConstants.TEMPLATE_FILE_PATH);
		
		try {
			levels = PtychoUtils.loadTemplateFile(fileSavedPath);
			if (levels != null)
				tree = PtychoTreeUtils.populate(0, 0, levels);
		} catch (Exception e) {
			logger.error("Error loading spreadsheet file", e);
		}
	}

	protected void createPythonRunCommand(Composite parent) { 
		
		final Composite scriptBuilder = new Composite(parent, SWT.NONE);
		scriptBuilder.setLayout(new GridLayout(8, false));
		scriptBuilder.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		
		final Button enableTextBoxInput = new Button(scriptBuilder, SWT.CHECK);
		enableTextBoxInput.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		enableTextBoxInput.setToolTipText("Enable/disable text box inputs \n \nEnabling will force the RUN button to use the process dir, config and scan entries as arguments \n\n"
				+ "Disabling will pass the editor file as an argument.");
		enableTextBoxInput.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if(enableTextBoxInput.getSelection()) {
					isTextBoxInputEnabled = true;
				} else {							
					isTextBoxInputEnabled = false;
				}
				processDir.setEnabled(enableTextBoxInput.getSelection());
				configName.setEnabled(enableTextBoxInput.getSelection());
				scanNumber.setEnabled(enableTextBoxInput.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		runPython = new InjectPyDevConsoleAction("Run Ptychographic Iterative Engine python script") {
			@Override
			public void run() {

				// reinject command
				if(!enableTextBoxInput.getSelection()) {
					jsonSavedPath = saveJSon(fileSavedPath);
					this.setParameter(InjectPyDevConsole.INJECT_COMMANDS_PARAM, getPythonCmd(jsonSavedPath));
				}
				else
					this.setParameter(InjectPyDevConsole.INJECT_COMMANDS_PARAM, getPythonCmd(processDir.getText() + " " + configName.getText() + " " + scanNumber.getText()));
				super.run();
			}
		};
		
		processDir = new FolderSelectionWidget(scriptBuilder, false){
			@Override
			public void pathChanged(String path, TypedEvent event) {}
		};
		processDir.setLabel("Process Dir: ");
		
		Label configNameLbl = new Label(scriptBuilder, SWT.NONE);
		configNameLbl.setText("Config: ");
		configName = new Text(scriptBuilder, SWT.BORDER);
		configName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Label scanNumberLbl = new Label(scriptBuilder, SWT.NONE);
		scanNumberLbl.setText("Scan: ");
		scanNumber = new Text(scriptBuilder, SWT.BORDER);
		scanNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		runPython.setDataInjected(false);
		runPython.setParameter(InjectPyDevConsole.CREATE_NEW_CONSOLE_PARAM, Boolean.TRUE.toString());
		//runPython.setParameter(InjectPyDevConsole.INJECT_COMMANDS_PARAM, getPythonCmd(jsonSavedPath));	// DOesn't work, so not sure what the point of this is... Probably need to add it into the run() method above
		runPython.setParameter(InjectPyDevConsole.INJECT_COMMANDS_PARAM, getPythonCmd(processDir.getText() + " " + configName.getText() + " " + scanNumber.getText()));
		ActionContributionItem aci = new ActionContributionItem(runPython);
		aci.fill(scriptBuilder);
		Button runButton = (Button) aci.getWidget();
		runButton.setText("RUN");
		runButton.setToolTipText("Run Ptychographic Iterative Engine process");
		runButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		
		enableTextBoxInput.setSelection(isTextBoxInputEnabled);
		enableTextBoxInput.notifyListeners(SWT.Selection, new Event());
	}

	private String getPythonCmd(String jsonSavedPath) {
		StringBuilder pythonCmd = new StringBuilder();
		pythonCmd.append("run ");
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		
		if(defaultScript != null && defaultScript.startsWith(ALTERNATE_SCRIPT))
			pythonCmd.append(store.getString(PtychoPreferenceConstants.ALTERNATE_SCRIPT_PATH) + " ");
		else
			pythonCmd.append(store.getString(PtychoPreferenceConstants.RECON_SCRIPT_PATH) + " ");
		pythonCmd.append(jsonSavedPath);
		pythonCmd.append("\n");
		return pythonCmd.toString();
	}

	private String saveJSon(String fileSavedPath) {
		String fileSavePref = getFileSavePathPreference();
		// Should only be true on first use (fileSavedPath preference not set), or if manually set to ""
		if ((fileSavedPath == null || fileSavedPath.equals("")) && (fileSavePref == null || fileSavePref.equals(""))) {
			fileSavedPath = saveAs(fileSavedPath);
			if (fileSavedPath == null || fileSavedPath.equals(""))
				return "";
			setDirty(false);
		}
		// saveAs() will set the fileSavedPath preference, use this if variable not set
		if(fileSavedPath == null || fileSavedPath.equals("")) {
			fileSavedPath = fileSavePref;
			setFileSavedPath(fileSavedPath);
		}
		
		String jsonSavedPath;
		
		if(!fileSavedPath.endsWith(".json")) {
			jsonSavedPath = fileSavedPath.substring(0, fileSavedPath.length() - 3);
			jsonSavedPath += "json";
		} else {
			jsonSavedPath = fileSavedPath;
		}
		String json = PtychoTreeUtils.jsonMarshal(tree);
		try {
			PtychoUtils.saveJSon(jsonSavedPath, json);
		} catch (IOException e) {
			logger.error("Error saving JSON file", e);
			MessageDialog.openError(Display.getDefault()
					.getActiveShell(), "Error writing JSON file", "An IO error occured while writing '"
					+ jsonSavedPath
					+ "' JSON file.");
		}
		return jsonSavedPath;
	}

	@Focus
	public void setFocus() {}

	private static final String[] EXTENSIONS = new String[] { "csv", "json", "xml" };
	private static final String[] FILTER_EXTENSIONS = new String[] {
			"*.csv;*.CSV", "*.json;*.JSON",
			"*.xml;*.XML" };

	protected String saveAs(String fileSavedPath) {
		FileDialog dialog = new FileDialog(Display.getDefault()
				.getActiveShell(), SWT.SAVE);
		dialog.setText("Choose file path and name to save parameter input");
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
		dialog.setFilterExtensions(FILTER_EXTENSIONS);
		String path = dialog.open();
		if (path == null) {
			return fileSavedPath;
		}
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		store.setValue(PtychoPreferenceConstants.FILE_SAVE_PATH, path);
		try {
			final File file = new File(path);
			if (file.exists()) {
				boolean yes = MessageDialog.openQuestion(Display.getDefault()
						.getActiveShell(), "Confirm Overwrite", "The file '"
						+ file.getName()
						+ "' exists.\n\nWould you like to overwrite it?");
				if (!yes) {
					return null;
				}
			}
			int index = dialog.getFilterIndex();
			if (index < 0) {
				String name = file.getName();
				int i = name.lastIndexOf('.') + 1;
				if (i > 0 && i < name.length()) {
					String ext = name.substring(i).toLowerCase();
					index = Arrays.binarySearch(EXTENSIONS, ext);
				} else {
					index = -1;
				}
				if (index < 0) {
					logger.error("Extension of {} not recognised; saving as {}", name,  PtychoConstants.FILE_TYPES[0]);
					index = 0;
				}
			}
			String fileType = PtychoConstants.FILE_TYPES[index];
			if (fileType.equals("CSV File")) {
				List<PtychoData> list = PtychoTreeUtils.extract(tree);
				PtychoUtils.saveCSVFile(path, list);
			} else if (fileType.equals("JSon File")) {
				String json = PtychoTreeUtils.jsonMarshal(tree);
				PtychoUtils.saveJSon(path, json);
			} else {
				throw new Exception("XML serialisation is not yet implemented.");
			}
		} catch (Exception e) {
			logger.error("Error saving file", e);
			return fileSavedPath;
		}
		return path;
	}

	public void setFileSavedPath(String fileSavedPath) {
		this.fileSavedPath = fileSavedPath;
	}

	public String getFileSavedPath() {
		return fileSavedPath;
	}
	
	private String getFileSavePathPreference() {
		IPreferenceStore store = Activator.getPtychoPreferenceStore();
		return store.getString(PtychoPreferenceConstants.FILE_SAVE_PATH);
	}

	void setDefaultScript(String defaultScript) {
		this.defaultScript = defaultScript;
	}
}
