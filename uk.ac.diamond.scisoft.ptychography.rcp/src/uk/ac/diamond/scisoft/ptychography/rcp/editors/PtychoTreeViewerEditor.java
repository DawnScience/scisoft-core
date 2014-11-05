package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.util.List;

import org.dawnsci.common.widgets.tree.ClearableFilteredTree;
import org.dawnsci.common.widgets.tree.DelegatingProviderWithTooltip;
import org.dawnsci.common.widgets.tree.IResettableExpansion;
import org.dawnsci.common.widgets.tree.NodeFilter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoInput;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoUtils;

public class PtychoTreeViewerEditor extends EditorPart implements IResettableExpansion {

	public final static String ID = "uk.ac.diamond.scisoft.ptychography.rcp.ptychoEditor";
	private TreeViewer viewer;
	private ClearableFilteredTree filteredTree;
	private static final Logger logger = LoggerFactory.getLogger(PtychoTreeViewerEditor.class);
	private List<PtychoInput> levels;
	private List<PtychoNode> tree;
	
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
		try {
			levels = PtychoUtils.loadSpreadSheet(input);
			tree = PtychoTreeUtils.populate(levels);
		} catch (Exception e) {
			logger.error("Error loading spreadsheet file:" + e.getMessage());
			e.printStackTrace();
		}

		setSite(site);
		setInput(input);
		setPartName("Ptychography parameter Tree Editor");
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.filteredTree = new ClearableFilteredTree(
				container,
				SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER,
				new NodeFilter(this),
				true,
				"Enter search string to filter the tree.\nThis will match on name, value or units");
		viewer = filteredTree.getViewer();
		viewer.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setColumnProperties(new String[] { "Name", "Value" });
		ColumnViewerToolTipSupport.enableFor(viewer);
		TreeViewerColumn var = new TreeViewerColumn(viewer, SWT.LEFT, 0);
		var.getColumn().setText("Name"); // Selected
		var.getColumn().setWidth(260);
		var.setLabelProvider(new DelegatingProviderWithTooltip(new PtychoTreeLabelProvider(0)));
		var = new TreeViewerColumn(viewer, SWT.LEFT, 1);
		var.getColumn().setText("Value"); // Selected
		var.getColumn().setWidth(100);
		var.setLabelProvider(new DelegatingProviderWithTooltip(
				new PtychoTreeLabelProvider(1)));

		viewer.setContentProvider(new PtychoTreeContentProvider());
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderVisible(true);
		viewer.setInput(tree);

		Composite editorComposite = new Composite(container, SWT.NONE);
		editorComposite.setLayout(new GridLayout(2, false));
		editorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite leftComp = new Composite(editorComposite, SWT.NONE);
		leftComp.setLayout(new GridLayout(3, false));

		GridData gridData = new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1);
		Label nameLabel = new Label(leftComp, SWT.NONE);
		nameLabel.setText("Name");
		Text nameText = new Text(leftComp, SWT.BORDER);
		nameText.setText("");
		nameText.setLayoutData(gridData);
		Label valueLabel = new Label(leftComp, SWT.NONE);
		valueLabel.setText("Value");
		Text valueText = new Text(leftComp, SWT.BORDER);
		valueText.setText("");
		valueText.setLayoutData(gridData);
		valueText.setLayoutData(gridData);
		Label typeLabel = new Label(leftComp, SWT.NONE);
		typeLabel.setText("Type");
		Combo typeCombo = new Combo(leftComp, SWT.BORDER);
		typeCombo.setItems(new String[] {"Param", "str", "int", "float", "bool", "list", "tuple"});
		typeCombo.setLayoutData(gridData);

		Composite subLeftComp = new Composite(leftComp, SWT.NONE);
		subLeftComp.setLayout(new GridLayout(3, false));
		subLeftComp.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 3, 1));
		gridData = new GridData();
		gridData.widthHint = 60;
		Label lowerUpperLabel = new Label(subLeftComp, SWT.NONE);
		lowerUpperLabel.setText("Lower/Upper limit");
		Text lowerText = new Text(subLeftComp, SWT.BORDER);
		lowerText.setText("");
		lowerText.setLayoutData(gridData);
		Text upperText = new Text(subLeftComp, SWT.BORDER);
		upperText.setText("");
		upperText.setLayoutData(gridData);

		Composite rightComp = new Composite(editorComposite, SWT.NONE);
		rightComp.setLayout(new GridLayout(2, false));
		rightComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label shortDocLabel = new Label(rightComp, SWT.NONE);
		shortDocLabel.setText("Shortdoc");
		Text shortDocText = new Text(rightComp, SWT.BORDER);
		shortDocText.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false));
		
		Label longDocLabel = new Label(rightComp, SWT.NONE);
		longDocLabel.setText("Longdoc");
		StyledText longDocStyledText = new StyledText(rightComp, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		longDocStyledText.setText("");
		longDocStyledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		Button runButton = new Button(container, SWT.NONE);
		runButton.setText("RUN");
		runButton.setToolTipText("Run ptychography process on cluster");
		runButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false));
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetExpansion() {
		// TODO Auto-generated method stub

	}

}
