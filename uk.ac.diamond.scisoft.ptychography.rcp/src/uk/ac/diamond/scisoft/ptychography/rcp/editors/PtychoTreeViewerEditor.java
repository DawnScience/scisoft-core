package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.dawnsci.common.widgets.tree.ClearableFilteredTree;
import org.dawnsci.common.widgets.tree.DelegatingProviderWithTooltip;
import org.dawnsci.common.widgets.tree.IResettableExpansion;
import org.dawnsci.common.widgets.tree.NodeFilter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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

public class PtychoTreeViewerEditor extends EditorPart implements IResettableExpansion {

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
//			model = loadSpreadSheet(input);
			levels = loadSpreadSheet(input);
		
			tree = populate(levels);
		} catch (Exception e) {
			logger.error("Error loading spreadsheet file:" + e.getMessage());
			e.printStackTrace();
		}

		setSite(site);
		setInput(input);
//		levels = PtychoModel.getInstance((PtychoTreeEditorInput)loadedInput));
		setPartName("Ptychography parameter Tree Editor");
	}

	private List<PtychoInput> loadSpreadSheet(IEditorInput input) {
		IResource rsc = getResource(input);
		String fullPath = "";
		if (rsc instanceof File) {
			File file = (File) rsc;
			fullPath = file.getAbsolutePath();
		} else if (rsc instanceof IFile) {
			IFile iFile = (IFile) rsc;
			fullPath = iFile.getLocation().toOSString();
		}
		CSVParser parser;
		List<PtychoInput> loaded = new ArrayList<PtychoInput>();
		try {
			CSVFormat format = CSVFormat.EXCEL
					.withHeader("level", "name", "default", "type", "unique", "lowerlimit", "upperlimit", "shortdoc", "longdoc")
					.withSkipHeaderRecord(true);
			parser = CSVParser.parse(new File(fullPath), StandardCharsets.UTF_8, format);
			for (CSVRecord csvRecord : parser) {
				String level = csvRecord.get("level");
				String name = csvRecord.get("name");
				String defaultVal = csvRecord.get("default");
				String type = csvRecord.get("type");
				String unique = csvRecord.get("unique");
				String lowerlimit = csvRecord.get("lowerlimit");
				String upperlimit = csvRecord.get("upperlimit");
				String shortdoc = csvRecord.get("shortdoc");
				String longdoc = csvRecord.get("longdoc");
				
				PtychoInput row = new PtychoInput();
				if (level.length() > 0 && StringUtils.isNumeric(level))
					row.setLevel(Integer.valueOf(level));
				row.setName(name);
				row.setDefaultValue(defaultVal);
				row.setType(type);
				row.setUnique(unique.equals("yes") ? true : false);
				if (lowerlimit.length() > 0 && StringUtils.isNumeric(lowerlimit))
					row.setLowerLimit(Integer.valueOf(lowerlimit));
				if (upperlimit.length() > 0 && StringUtils.isNumeric(upperlimit))
					row.setUpperLimit(Integer.valueOf(upperlimit));
				row.setShortDoc(shortdoc);
				row.setLongDoc(longdoc);
				loaded.add(row);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loaded;
	}

	//TODO to be finished
	private List<PtychoNode> populate(List<PtychoInput> input) {
		List<PtychoNode> nodes = new ArrayList<PtychoNode>();
		int i = 0;
		while (i < input.size()) {
			if (i < input.size() - 1) {
				PtychoNode node = new PtychoNode(input.get(i));
				int level = input.get(i).getLevel();
				int nextLevel = input.get(i + 1).getLevel();
				if (level == 3 && nextLevel == 2) {
					int j = 0;
					while(nextLevel == 2){
						int max = input.size() - (j+1);
						if (i == max)
							break;
//						if ((i + 1 +j) > 114)
//							System.out.println("");
						PtychoNode child = new PtychoNode(input.get(i + 1 + j));
						j++;
						if ((i +1 +j) >= input.size()) {
							child.setParent(node);
							node.addChild(child);
							break;
						}
						nextLevel = input.get(i + 1 + j).getLevel();
						if (nextLevel == 1) {
							int k = 0;
							i += j;
							while (nextLevel == 1) {
								PtychoNode subchild = new PtychoNode(input.get(i + 1 + k));
								k++;
								if (i < input.size() - k)
									nextLevel = input.get(i + 1 + k).getLevel();
								subchild.setParent(child);
								child.addChild(subchild);
							}
							i += k;
							j = 0;
						}
						child.setParent(node);
						node.addChild(child);
					}
					i += j;
				}
				nodes.add(node);
			}
			i++;
		}
		return nodes;
	}

	private IResource getResource(IEditorInput input) {
		IResource resource = (IResource) input.getAdapter(IFile.class);
		if (resource == null) {
			resource = (IResource) input.getAdapter(IResource.class);
		}
		return resource;
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
		gridData.widthHint = 80;
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
