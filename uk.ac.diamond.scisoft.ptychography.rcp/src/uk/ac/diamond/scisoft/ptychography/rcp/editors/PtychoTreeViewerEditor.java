package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public class PtychoTreeViewerEditor extends EditorPart {

	public final static String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoEditor";
	private static final Logger logger = LoggerFactory.getLogger(PtychoTreeViewerEditor.class);
	private TreeViewer viewer;
	private FilteredTree filteredTree;
	private List<PtychoData> levels;
	private List<PtychoNode> tree;
	private ISelectionChangedListener selectionListener;
	private Text nameText;
	private Text valueText;
	private Combo typeCombo;
	private Text lowerText;
	private Text upperText;
	private Text shortDocText;
	private StyledText longDocStyledText;
	private PtychoNode currentNode;
	private boolean isDirtyFlag = false;
	private Color white;
	private Color gray;
	private Color darkGray;
	private Color black;
	private String fullPath;
	private String fileSavedPath;

	@Override
	public void doSave(IProgressMonitor monitor) {
		List<PtychoData> list = PtychoTreeUtils.extract(tree);
		if (fileSavedPath == null)
			PtychoUtils.saveCSVFile(fullPath, list);
		else
			PtychoUtils.saveCSVFile(fileSavedPath, list);
		setDirty(false);
	}

	@Override
	public void doSaveAs() {
		saveAs();
		setDirty(false);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		try {
			fullPath = PtychoUtils.getFullPath(input);
			levels = PtychoUtils.loadSpreadSheet(fullPath);
			tree = PtychoTreeUtils.populate(levels);
		} catch (Exception e) {
			logger.error("Error loading spreadsheet file:" + e.getMessage());
			e.printStackTrace();
		}

		setSite(site);
		setInput(input);
		setPartName("Ptychography parameter Tree Editor");

		Display display = Display.getDefault();
		white = new Color(display, 255, 255, 255);
		gray = new Color(display, 237, 236, 235);
		darkGray = new Color(display, 170, 166, 161);
		black = new Color(display, 0, 0, 0);

		selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof TreeSelection) {
					TreeSelection treeSelection = (TreeSelection) selection;
					Object selected = treeSelection.getFirstElement();
					if (selected instanceof PtychoNode) {
						PtychoNode node = (PtychoNode) selected;
						currentNode = node;
						PtychoData data = node.getData();
						nameText.setText(data.getName());
						valueText.setText(data.getDefaultValue());
						typeCombo.setText(data.getType());
						lowerText.setText(String.valueOf(data.getLowerLimit()));
						upperText.setText(String.valueOf(data.getUpperLimit()));
						shortDocText.setText(data.getShortDoc());
						longDocStyledText.setText(data.getLongDoc());
						boolean unique = data.isUnique();
						nameText.setEnabled(!unique);
						valueText.setEnabled(!unique);
						typeCombo.setEnabled(!unique);
						lowerText.setEnabled(!unique);
						upperText.setEnabled(!unique);
						shortDocText.setEnabled(!unique);
						longDocStyledText.setEnabled(!unique);
						if (unique) {
							longDocStyledText.setBackground(gray);
							longDocStyledText.setForeground(darkGray);
						} else {
							longDocStyledText.setBackground(white);
							longDocStyledText.setForeground(black);
						}
					}
				}
			}
		};
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
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));

		createToolBarActions(container);

		PatternFilter filter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				String name = ((PtychoNode) element).getData().getName();
				if (name == null) {
					return false;
				}
				return wordMatches(name);
			}
		};
		this.filteredTree = new FilteredTree(
				container,
				SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER,
				filter,
				true);
		viewer = filteredTree.getViewer();
		viewer.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setColumnProperties(new String[] { "Name", "Value" });
		ColumnViewerToolTipSupport.enableFor(viewer);
		TreeViewerColumn var = new TreeViewerColumn(viewer, SWT.LEFT, 0);
		var.getColumn().setText("Name"); // Selected
		var.getColumn().setWidth(260);
		var.setLabelProvider(new PtychoTreeLabelProvider(0));
		var = new TreeViewerColumn(viewer, SWT.LEFT, 1);
		var.getColumn().setText("Value"); // Selected
		var.getColumn().setWidth(100);
		var.setLabelProvider(new PtychoTreeLabelProvider(1));

		viewer.setContentProvider(new PtychoTreeContentProvider());
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderVisible(true);
		viewer.setInput(tree);
		viewer.addSelectionChangedListener(selectionListener);

		Composite editorComposite = new Composite(container, SWT.NONE);
		editorComposite.setLayout(new GridLayout(2, false));
		editorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite leftComp = new Composite(editorComposite, SWT.NONE);
		leftComp.setLayout(new GridLayout(3, false));

		GridData gridData = new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1);
		Label nameLabel = new Label(leftComp, SWT.NONE);
		nameLabel.setText("Name");
		nameText = new Text(leftComp, SWT.BORDER);
		nameText.setText("");
		nameText.setLayoutData(gridData);
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateAttributes(e);
			}
		});
		Label valueLabel = new Label(leftComp, SWT.NONE);
		valueLabel.setText("Value");
		valueText = new Text(leftComp, SWT.BORDER);
		valueText.setText("");
		valueText.setLayoutData(gridData);
		valueText.setLayoutData(gridData);
		valueText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateAttributes(e);
			}
		});
		Label typeLabel = new Label(leftComp, SWT.NONE);
		typeLabel.setText("Type");
		typeCombo = new Combo(leftComp, SWT.BORDER);
		typeCombo.setItems(new String[] {"Param", "str", "int", "float", "bool", "list", "tuple"});
		typeCombo.setLayoutData(gridData);

		Composite subLeftComp = new Composite(leftComp, SWT.NONE);
		subLeftComp.setLayout(new GridLayout(3, false));
		subLeftComp.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 3, 1));
		gridData = new GridData();
		gridData.widthHint = 60;
		Label lowerUpperLabel = new Label(subLeftComp, SWT.NONE);
		lowerUpperLabel.setText("Lower/Upper limit");
		lowerText = new Text(subLeftComp, SWT.BORDER);
		lowerText.setText("");
		lowerText.setLayoutData(gridData);
		lowerText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateAttributes(e);
			}
		});
		upperText = new Text(subLeftComp, SWT.BORDER);
		upperText.setText("");
		upperText.setLayoutData(gridData);
		upperText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateAttributes(e);
			}
		});

		Composite rightComp = new Composite(editorComposite, SWT.NONE);
		rightComp.setLayout(new GridLayout(2, false));
		rightComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label shortDocLabel = new Label(rightComp, SWT.NONE);
		shortDocLabel.setText("Shortdoc");
		shortDocText = new Text(rightComp, SWT.BORDER);
		shortDocText.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false));
		shortDocText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateAttributes(e);
			}
		});
		Label longDocLabel = new Label(rightComp, SWT.NONE);
		longDocLabel.setText("Longdoc");
		longDocStyledText = new StyledText(rightComp, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		longDocStyledText.setText("");
		longDocStyledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		longDocStyledText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateAttributes(e);
			}
		});
		Button runButton = new Button(container, SWT.NONE);
		runButton.setText("RUN");
		runButton.setToolTipText("Run ptychography process on cluster");
		runButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false));

		getSite().setSelectionProvider(viewer);
	}

	private void updateAttributes(ModifyEvent event) {
		if (currentNode == null)
			return;
		PtychoData data = currentNode.getData();
		if (!data.isUnique()) {
			Object evt = event.getSource();
			if (evt instanceof Text) {
				Text txt = (Text) evt;
				if (txt == nameText) {
					data.setName(txt.getText());
				} else if (txt == valueText)
					data.setDefaultValue(txt.getText());
				else if (txt == lowerText)
					data.setLowerLimit(Integer.valueOf(txt.getText()));
				else if (txt == upperText)
					data.setUpperLimit(Integer.valueOf(txt.getText()));
				else if (txt == shortDocText)
					data.setShortDoc(txt.getText());
			} else if (evt instanceof StyledText) {
				StyledText stxt = (StyledText) evt;
				data.setLongDoc(stxt.getText());
			} else if (evt instanceof Combo) {
				;
			}
			viewer.refresh();
			setDirty(true);
		}
	}

	private void createToolBarActions(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.RIGHT);
		ToolItem expand = new ToolItem(toolBar, SWT.PUSH);
		expand.setToolTipText("Expand All");
		Image icon = Activator.getImageDescriptor("icons/expand_all.png").createImage();
		expand.setImage(icon);
		expand.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (viewer != null) 
					viewer.expandAll();
			}
		});

		ToolItem collapse = new ToolItem(toolBar, SWT.PUSH);
		collapse.setToolTipText("Collapse All");
		icon = Activator.getImageDescriptor("icons/collapse_all.png").createImage();
		collapse.setImage(icon);
		collapse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (viewer != null) 
					viewer.collapseAll();
			}
		});

		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem export = new ToolItem(toolBar, SWT.PUSH);
		export.setToolTipText("Export table to file");
		icon = Activator.getImageDescriptor("icons/export_wiz.gif").createImage();
		export.setImage(icon);
		export.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					saveAs();
					isDirtyFlag = false;
				} catch (Exception e) {
					logger.error("Problem exporting to file", e);
				}
			}
		});

		ToolItem itemDrop = new ToolItem(toolBar, SWT.DROP_DOWN);
		itemDrop.addSelectionListener(new SelectionAdapter() {
			Menu dropMenu = null;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (dropMenu == null) {
					Shell shell = Display.getDefault().getActiveShell();
					dropMenu = new Menu(shell, SWT.POP_UP);
					shell.setMenu(dropMenu);
					MenuItem preferences = new MenuItem(dropMenu, SWT.PUSH);
					preferences.setText("Preferences...");
					preferences.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent event) {
							PreferenceDialog pref = PreferencesUtil
									.createPreferenceDialogOn(PlatformUI
											.getWorkbench()
											.getActiveWorkbenchWindow()
											.getShell(),
											PtychoTreeViewerEditor.ID, null,
											null);
							if (pref != null)
								pref.open();
						}
					});
				}

				if (e.detail == SWT.ARROW) {
					// Position the menu below and vertically aligned with the
					// the drop down tool button.
					final ToolItem toolItem = (ToolItem) e.widget;
					final ToolBar toolBar = toolItem.getParent();

					Point point = toolBar.toDisplay(new Point(e.x, e.y));
					dropMenu.setLocation(point.x, point.y);
					dropMenu.setVisible(true);
				}
			}
		});
	}

	private void saveAs() {
		FileDialog dialog = new FileDialog(Display.getDefault()
				.getActiveShell(), SWT.SAVE);
		dialog.setText("Save as");
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

	@Override
	public void dispose() {
		if (viewer != null)
			viewer.removeSelectionChangedListener(selectionListener);
		if (white != null && !white.isDisposed() && gray != null
				&& !gray.isDisposed() && darkGray != null
				&& !darkGray.isDisposed() && black != null
				&& !black.isDisposed()) {
			white.dispose();
			gray.dispose();
			darkGray.dispose();
			black.dispose();
		}
	}

	@Override
	public void setFocus() {
		if (viewer != null)
			viewer.getControl().setFocus();
	}

}
