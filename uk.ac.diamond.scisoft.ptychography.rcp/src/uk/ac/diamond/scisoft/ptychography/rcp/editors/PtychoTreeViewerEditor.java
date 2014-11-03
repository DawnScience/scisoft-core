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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
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
			switch (input.get(i).getLevel()) {
			case 1:
				nodes.add(new PtychoNode(input.get(i), 0));
				i++;
				break;
			case 2:
				
				break;
			case 3:
				break;
			}
			i++;
		}
		return nodes;
	}

	//http://stackoverflow.com/questions/16229732/cant-wrap-my-head-around-populating-an-n-ary-tree
//	private static void addNodesRecursive(PtychoNode node, List<PtychoNode> addedList) {
//		for (String stationName : network.getConnections(node)) {
//			if (!addedList.contains(stationName)) {
//				PtychoNode child = new PtychoNode(stationName);
//				node.addChild(child);
//				addedList.add(child);
//				addNodesRecursive(child, addedList);
//			}
//		}
//	}

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
		this.filteredTree = new ClearableFilteredTree(
				parent,
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
		var.setLabelProvider(new PtychoTreeLabelProvider(0));
		var = new TreeViewerColumn(viewer, SWT.LEFT, 1);
		var.getColumn().setText("Value"); // Selected
		var.getColumn().setWidth(100);
		var.setLabelProvider(new DelegatingProviderWithTooltip(
				new PtychoTreeLabelProvider(1)));

		viewer.setContentProvider(new PtychoTreeContentProvider());
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderVisible(true);
		viewer.setInput(tree);

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
