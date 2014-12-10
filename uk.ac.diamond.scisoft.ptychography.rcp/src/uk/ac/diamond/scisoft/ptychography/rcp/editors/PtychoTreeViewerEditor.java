package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferencePage;

public class PtychoTreeViewerEditor extends AbstractPtychoEditor {

	public final static String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoTreeEditor";
	private static final Logger logger = LoggerFactory.getLogger(PtychoTreeViewerEditor.class);
	private TreeViewer viewer;
	private FilteredTree filteredTree;
	private ISelectionChangedListener selectionListener;
	private Text nameText;
	private Text valueText;
	private Combo typeCombo;
	private Text lowerText;
	private Text upperText;
	private Text shortDocText;
	private StyledText longDocStyledText;
	private PtychoNode currentNode;
	private Color white;
	private Color gray;
	private Color darkGray;
	private Color black;
	private IPropertyChangeListener propertyListener;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		//save the json right away
//		saveJSon();

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
		propertyListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (isInterestingProperty(event)) {
					String propName = event.getProperty();
					if (PtychoPreferenceConstants.FILE_SAVE_PATH.equals(propName))
						fileSavedPath = event.getProperty();
					else if (PtychoPreferenceConstants.PIE_RESOURCE_PATH.equals(propName))
						;
				}
			}
			private boolean isInterestingProperty(PropertyChangeEvent event) {
				String propName = event.getProperty();
				return PtychoPreferenceConstants.FILE_SAVE_PATH.equals(propName) || 
						PtychoPreferenceConstants.PIE_RESOURCE_PATH.equals(propName);
			}
		};
		Activator.getPtychoPreferenceStore().addPropertyChangeListener(propertyListener);
	}

	@Override
	public void createPartControl(final Composite parent) {
//		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createToolBarActions(container);
//
//		Composite viewerComp = new Composite(container, SWT.NONE);
//		viewerComp.setLayout(new GridLayout());
//		viewerComp.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

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
		GridData data = new GridData(SWT.TOP, SWT.FILL, true, true);
		data.widthHint = 600;
		data.heightHint = 300;
		viewer.getTree().setLayoutData(data);
		viewer.addSelectionChangedListener(selectionListener);
		createRightMenuActions();

		final ScrolledComposite scrollComposite = new ScrolledComposite(container, SWT.H_SCROLL | SWT.V_SCROLL);
		scrollComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final Composite editorComposite = new Composite(scrollComposite, SWT.NONE);
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
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (currentNode == null)
					return;
				PtychoData data = currentNode.getData();
				if (!data.isUnique()) {
					Object evt = event.getSource();
					Combo cmb = (Combo) evt;
					data.setType(cmb.getText());
					viewer.refresh();
					setDirty(true);
				}
			}
		});

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
		scrollComposite.setContent(editorComposite);
		scrollComposite.setExpandHorizontal(true);
		scrollComposite.setExpandVertical(true);
		scrollComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle r = scrollComposite.getClientArea();
				scrollComposite.setMinSize(editorComposite.computeSize(r.width, SWT.DEFAULT));
			}
		});

		createPythonRunCommand(container);

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
					setDirty(false);
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
											PtychoPreferencePage.ID, null,
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

	private void createRightMenuActions() {
		Action addNodeAction = new Action("Add row", Activator.getImageDescriptor("icons/add_obj.gif")) {
			@Override
			public void run() {
				if (currentNode != null) {
					int level = currentNode.getData().getLevel();
					PtychoData data = new PtychoData();
					data.setLevel(level);
					data.setName("new node");
					data.setDefaultValue("");
					data.setShortDoc("");
					data.setLongDoc("");
					data.setType("str");
					data.setUnique(false);
					PtychoNode node = new PtychoNode(data);
					PtychoNode parent = currentNode.getParent();
					if (parent != null) {
						List<PtychoNode> children = parent.getChildren();
						int currentIdx = children.indexOf(currentNode);
						node.setParent(parent);
						children.add(currentIdx + 1, node);
					} else {
						int currentIdx = tree.indexOf(currentNode);
						tree.add(currentIdx + 1, node);
					}
					viewer.refresh();
				}
			}
		};
		Action removeNodeAction = new Action("Delete selected row", Activator.getImageDescriptor("icons/delete_obj.gif")) {
			@Override
			public void run() {
				if (currentNode != null) {
					PtychoNode parent = currentNode.getParent();
					if (parent != null) {
						List<PtychoNode> children = parent.getChildren();
						int currentIdx = children.indexOf(currentNode);
						children.remove(currentNode);
						if (currentIdx != 0)
							currentNode = children.get(currentIdx -1);
						else
							currentNode = children.get(0);
					} else {
						int currentIdx = tree.indexOf(currentNode);
						tree.remove(currentNode);
						if (currentIdx != 0)
							currentNode = tree.get(currentIdx -1);
						else
							currentNode = tree.get(0);
					}
					viewer.refresh();
				}
			}
		};
		// right click menu buttons
		MenuManager rightClickMenuMan = new MenuManager();
		rightClickMenuMan.add(addNodeAction);
		rightClickMenuMan.add(removeNodeAction);
		viewer.getControl().setMenu(rightClickMenuMan.createContextMenu(viewer.getControl()));
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
		Activator.getPtychoPreferenceStore().removePropertyChangeListener(propertyListener);
	}

	@Override
	public void setFocus() {
		if (viewer != null)
			viewer.getControl().setFocus();
	}

}
