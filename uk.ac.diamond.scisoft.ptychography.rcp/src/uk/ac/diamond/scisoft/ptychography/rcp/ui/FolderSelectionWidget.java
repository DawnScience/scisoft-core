/*-
 * Copyright 2015 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.diamond.scisoft.ptychography.rcp.ui;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;

/**
 * Selector widget made of a SWT Text with drag and drop listeners and content proposal and SWT Button
 * which opens a Directory or file dialog
 * The method loadPath(String) needs to be implemented and whatever action needs to be done once a path 
 * is loaded can be put in it.
 * <br/><br/>
 * Stolen from uk.ac.diamond.scisoft.peena.rcp.uk
 * Should really be refactored if necessary so it can be centralised for multiple plugins, though 
 * apparently this type of behaviour has already been duplicated so probably needs more investigation.
 * 
 * @author wqk87977
 * 
 *
 */
public abstract class FolderSelectionWidget {

	private static final Logger logger = LoggerFactory.getLogger(FolderSelectionWidget.class);
	private Text inputLocation;
	private String path = "";
	private String textTooltip = "";
	private String buttonTooltip = "";
	private Composite container;
	private Button fileButton;
	private Label label;
	private boolean isFile;

	/**
	 * 
	 * @param parent
	 *           parent composite
	 */
	public FolderSelectionWidget(Composite parent, boolean isFile) {
		this.setFile(isFile);
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.LEFT, SWT.BOTTOM, true, true, 2, 1);
		container.setLayoutData(gridData);

		label = new Label(container, SWT.NONE);
		label.setText("Folder:");
		inputLocation = new Text(container, SWT.BORDER);
		inputLocation.setText(path);
		inputLocation.setSelection(path.length());
		gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.widthHint = 150;
		inputLocation.setLayoutData(gridData);
		inputLocation.setToolTipText(textTooltip);
		inputLocation.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				File tmp = new File(inputLocation.getText());
				if (tmp.isDirectory()) {
					path = tmp.getAbsolutePath();
				}
				pathChanged(inputLocation.getText(), e);
			}
		});
		inputLocation.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!isFile() && e.keyCode == SWT.CR)
					if(!checkDirectory(inputLocation.getText(), true))
						MessageDialog.openWarning(null, "Warning", "Invalid directory entered!");
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		DropTarget dt = new DropTarget(inputLocation, DND.DROP_MOVE| DND.DROP_DEFAULT| DND.DROP_COPY);
		dt.setTransfer(new Transfer[] { TextTransfer.getInstance (), FileTransfer.getInstance()});
		dt.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				Object data = event.data;
				if (data instanceof String[]) {
					String[] stringData = (String[]) data;
					if (stringData.length > 0) {
						File dir = new File(stringData[0]);
						if (dir.isDirectory()) {
							setText(dir.getAbsolutePath());
							inputLocation.notifyListeners(SWT.Modify, null);
							pathChanged(dir.getAbsolutePath(), event);
						}
					}
				}
			}
		});

		fileButton = new Button(container, SWT.PUSH);
		//fileButton.setText("Browse...");
		fileButton.setImage(Activator.getImageAndAddDisposeListener(fileButton, "icons/folder.png"));
		fileButton.setToolTipText("Select an external folder");
		fileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(e);
			}
		});
	}

	/**
	 * 
	 * @param inputText text of the main text widget
	 */
	public void setText(String inputText) {
		this.path = inputText;
		if (inputLocation != null) {
			inputLocation.setText(inputText);
			inputLocation.setSelection(inputText.length()); // show rightmost characters of text
		}
	}

	/**
	 * 
	 * @param textLabel label in front of the text widget.
	 *    If none, will be defaulted to File or Folder
	 */
	public void setLabel(String textLabel) {
		if (label != null)
			label.setText(textLabel);
	}

	public String getText() {
		if (inputLocation != null)
			return inputLocation.getText();
		return null;
	}

	public void setEnabled(boolean isEnabled) {
		if (inputLocation != null)
			inputLocation.setEnabled(isEnabled);
		if (fileButton != null)
			fileButton.setEnabled(isEnabled);
	}

	/**
	 * Checks whether the path is a directory
	 * @param path
	 * @param forRead
	 * @return boolean
	 */
	public boolean checkDirectory(final String path, boolean forRead) {
		if (path == null || path.length() == 0) {
			logger.warn("No path given");
			return false;
		}
		File f = new File(path);
		if (!f.exists() || f.isFile()) {
			logger.warn("Path does not exist or is not a directory");	// NEED SOME WAY TO RETURN A GOOD STATUS MESSAGE IF WE ARRIVE HERE!!!! ///
			return false;
		}
		return forRead ? f.canRead() : f.canWrite();
	}

	/**
	 * To be overridden with the action that needs to be run once that a path is chosen
	 * @param path
	 *          path to file or folder
	 * @param event
	 *          used to check the type of event
	 */
	public abstract void pathChanged(String path, TypedEvent event);

	/**
	 * 
	 * @param textTooltip 
	 *             Tooltip of the path text field
	 */
	public void setTextToolTip(String textTooltip) {
		this.textTooltip = textTooltip;
		if (inputLocation != null)
			inputLocation.setToolTipText(textTooltip);
	}

	/**
	 * 
	 * @param buttonTooltip 
	 *             Tooltip of the browse button
	 */
	public void setButtonToolTip(String buttonTooltip) {
		this.buttonTooltip = buttonTooltip;
		if (fileButton != null) {
			fileButton.setToolTipText(this.buttonTooltip + " (from external location)");
		}
	}

	private void handleFileBrowse(TypedEvent event) {
		String path = null;
		final Dialog dialog;
		
		if(isFile())
			dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
		else
			dialog = new DirectoryDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
		
		dialog.setText("Choose folder");
		if (this.path != null) {
			File file = new File(this.path);
			if (file.exists()) {
				// Nothing
			} else if (file.getParentFile() != null && file.getParentFile().exists()) {
				file = file.getParentFile();
			}
			if (file.isDirectory()) {
				if(dialog instanceof DirectoryDialog)
					((DirectoryDialog)dialog).setFilterPath(file.getAbsolutePath());
				else
					((FileDialog)dialog).setFilterPath(file.getAbsolutePath());
			} else {
				if(dialog instanceof DirectoryDialog)
					((DirectoryDialog)dialog).setFilterPath(file.getParent());
				else
					((FileDialog)dialog).setFilterPath(file.getParent());
			}
		}
		if(dialog instanceof DirectoryDialog)
			path = ((DirectoryDialog) dialog).open();
		else
			path = ((FileDialog) dialog).open();
		
		if (path!=null) {
			setText(path);
			pathChanged(path, event);
		}
	}

	private boolean isFile() {
		return isFile;
	}

	private void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	
	public boolean checkFile(String path){
		File f = new File(path);
		
		if(f.exists() && f.canRead() && f.isFile())
			return true;
			
		return false;
	}
}
