/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class XPDFSampleView extends ViewPart {
	
//	private TableViewer sampleTV;
	
	private List<XPDFSampleParameters> samples;
	
	private Button cifButton;
	private Button eraButton;
	private Button simButton;
	private Button savButton;
	
	private Action loadTestDataAction;
	private Action simPDFAction;
	private Action saveAction;
	private Action newCifAction;
	private Action newEraAction;
	private Action pointBreakAction;
	
	private enum Column {
		NAME, ID, DETAILS, PHASES, COMPOSITION, DENSITY, PACKING,
		SUGGESTED_ENERGY, MU, SUGGESTED_DIAMETER, ENERGY, CAPILLARY
	}
	
	private enum ColumnGroup {
		ID, SAMPLE_DETAILS, SUGGESTED_EXPT, CHOSEN_EXPT
	}
	
	private Map<ColumnGroup, TableViewer> groupViewers;
	
	private boolean propagateSelectionChange;

	// Sorting parameters: possibly held by the table(s?)?
	private Column sortedColumn;
	private boolean isDescendingSort;
	
	public XPDFSampleView() {
		propagateSelectionChange = true;
		isDescendingSort = true;
		sortedColumn = Column.ID;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public XPDFSampleView(Composite parent, int style) {
		createPartControl(parent);
	}

	@Override
	public void createPartControl(Composite parent) {
		
		// Make the data container not-null
		samples = new ArrayList<XPDFSampleParameters>();
		
		// Overall composite of the view
		Composite sampleTableCompo = new Composite(parent, SWT.BORDER);
		sampleTableCompo.setLayout(new FormLayout());
		
		// Composite to hold the table column groups
		SashForm tableCompo = new SashForm(sampleTableCompo, SWT.HORIZONTAL);
		// Attach to the left and right edges of the parent, and set to a fixed height
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.height = 800;
		tableCompo.setLayoutData(formData);
		// To properly fake a table, set the sash width to 0
		tableCompo.setSashWidth(0);
		
		createColumnGroups(tableCompo);
		
		createActions();
		createLoadButtons(tableCompo);
		createRHSButtons(tableCompo);
	}

	
	
	// Create the column groupings
	private void createColumnGroups(Composite outerComposite) {

		// Define the groupings of the columns
		Map<ColumnGroup, List<Column>> columnGrouping = new TreeMap<XPDFSampleView.ColumnGroup, List<Column>>();
		columnGrouping.put(ColumnGroup.ID, Arrays.asList(new Column[]{Column.NAME, Column.ID}));
		columnGrouping.put(ColumnGroup.SAMPLE_DETAILS, Arrays.asList(new Column[] {Column.DETAILS, Column.PHASES, Column.COMPOSITION, Column.DENSITY, Column.PACKING}));
		columnGrouping.put(ColumnGroup.SUGGESTED_EXPT, Arrays.asList(new Column[] {Column.SUGGESTED_ENERGY, Column.MU, Column.SUGGESTED_DIAMETER}));
		columnGrouping.put(ColumnGroup.CHOSEN_EXPT, Arrays.asList(new Column[] {Column.ENERGY, Column.CAPILLARY}));

		String[] groupHeaderText = {"Sample Identification", "Sample Details", "Suggested Parameters", "Chosen Parameters"};
		
		// TableViewers for each sub-table
		groupViewers = new HashMap<XPDFSampleView.ColumnGroup, TableViewer>();

		// Column weights, and column group weights
		int[] columnWeights = {20, 10, 2, 10, 15, 10, 5, 5, 5, 15, 10, 10};
		int[] groupWeights = new int[columnGrouping.size()];
		
		// Iterate over the groups.
		for (Map.Entry<ColumnGroup, List<Column>> columngroup : columnGrouping.entrySet()) {
			// Composite to hold the group header and table
			Composite groupCompo = new Composite(outerComposite, SWT.NONE);		
			groupCompo.setLayout(new FormLayout());
			
			// Add the Group column header as a do-nothing button
			Button headerButton = new Button(groupCompo, SWT.PUSH);

			FormData formData = new FormData();
			formData.top = new FormAttachment(0, 0);
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			headerButton.setLayoutData(formData);

			headerButton.setText(groupHeaderText[columngroup.getKey().ordinal()]);
			
			// Add the table that will hold this subset of the columns
			Composite subTableCompo = new Composite(groupCompo, SWT.NONE);

			formData = new FormData();
			formData.top = new FormAttachment(headerButton);
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			formData.bottom = new FormAttachment(100, 0);
			subTableCompo.setLayoutData(formData);
			
			// Define the sub-table
			TableViewer tV = new TableViewer(subTableCompo);
			groupViewers.put(columngroup.getKey(), tV);
			TableColumnLayout tCL = new TableColumnLayout();
			subTableCompo.setLayout(tCL);
			
			// Create the columns of the sub-table, according to the defintion of the group and the overall weights 
			createColumns(tCL, columngroup.getValue(), tV, columnWeights);

			// Style of each sub-table
			tV.getTable().setHeaderVisible(true);
			tV.getTable().setLinesVisible(true);
			// Interactions of the sub-table
			tV.setContentProvider(new IStructuredContentProvider() {
				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}	// TODO Auto-generated method stub
				
				@Override
				public void dispose() {} // TODO Auto-generated method stub
				
				@Override
				public Object[] getElements(Object inputElement) {
					return samples.toArray();
				}
			});
			tV.setLabelProvider(new SampleTableLP(columngroup.getValue()));
			tV.setInput(getViewSite());
			
			// Set the listener that sets the selection as the same on each sub-table
			tV.addSelectionChangedListener(new SubTableSelectionChangedListener());
			
			// Calculate the relative weighting of each group
			groupWeights[columngroup.getKey().ordinal()] = 0;
			for (Column column : columngroup.getValue()) {
				groupWeights[columngroup.getKey().ordinal()] += columnWeights[column.ordinal()];
			}
		}

		// Set the weights of the SashForm, if the parent Composite is one
		if (outerComposite instanceof SashForm) {
			((SashForm) outerComposite).setWeights(groupWeights); 
		}
		
	}

	// Create a sub-set of the columns of the table
	private void createColumns(TableColumnLayout tCL, List<Column> columns, TableViewer tV, int[] columnWeights) {
		TableViewerColumn col;
		String[] columnNames = {"Sample name", "Code", "", "Phases", "Composition", "Density", "Vol. frac.", "Energy", "μ", "Max capillary ID", "Energy", "Container"}; 
		for (Column column : columns) {
			col = new TableViewerColumn(tV, SWT.NONE);
			col.getColumn().setText(columnNames[column.ordinal()]);
			tCL.setColumnData(col.getColumn(), new ColumnWeightData(columnWeights[column.ordinal()], 10, true));
			col.setLabelProvider(new SampleTableCLP(column));
			col.setEditingSupport(new SampleTableCES(column, tV));
			col.getColumn().addSelectionListener(getColumnSelectionAdapter(col.getColumn(), column));
		}
	}

	// The table label provider does nothing except delegate to the column label provider
	class SampleTableLP extends LabelProvider implements ITableLabelProvider {

		final List<Column> columns;
		
		public SampleTableLP(List <Column> columns) {
			this.columns = columns;
		}
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return (new SampleTableCLP(columns.get(columnIndex))).getText(element);
		}
		
	}
	
	// Column label provider. Use a switch to provide the different data labels for the different columns.
	class SampleTableCLP extends ColumnLabelProvider {
		
		final Column column;
		
		public SampleTableCLP(Column column) {
			this.column = column;
		}
		
		@Override
		public String getText(Object element) {
			XPDFSampleParameters sample = (XPDFSampleParameters) element;
			switch (column) {
			case NAME: return sample.getName();
			case ID: return Integer.toString(sample.getId());
			case DETAILS: return "+";
			case PHASES: 
				StringBuilder sb = new StringBuilder();
				for (String phase : sample.getPhases()) {
					sb.append(phase);
					sb.append(", ");
				}
				sb.delete(sb.length()-2, sb.length());
				return sb.toString();
			case COMPOSITION: return sample.getComposition();
			case DENSITY: return Double.toString(sample.getDensity());
			case PACKING: return Double.toString(sample.getPackingFraction());
			case SUGGESTED_ENERGY: return Double.toString(sample.getSuggestedEnergy());
			case MU: return String.format("%.4f", sample.getMu());
			case SUGGESTED_DIAMETER: return Double.toString(sample.getSuggestedCapDiameter());
			case ENERGY: return sample.getBeamState();
			case CAPILLARY: return sample.getContainer();
			default: return "";
			}
		}
	}

	// Column editing support. The different cases for the different columns are just switched by a switch statements or if-then-else
	class SampleTableCES extends EditingSupport {

		final Column column;
		final TableViewer tV;	

		public SampleTableCES(Column column, TableViewer tV) {
			super(tV);
			this.column = column;
			this.tV = tV;
		};
		@Override
		protected CellEditor getCellEditor(Object element) {
			return new TextCellEditor(tV.getTable());
		}
		@Override
		protected boolean canEdit(Object element) {
			if (column == Column.ID || column == Column.DETAILS || column == Column.MU) 
				return false;
			else
				return true;
		}
		@Override
		protected Object getValue(Object element) {
			XPDFSampleParameters sample = (XPDFSampleParameters) element;
			switch (column) {
			case NAME: return sample.getName();
			case ID: return sample.getId();
			case DETAILS: return null; // TODO: This should eventually show something, but nothing for now.
			case PHASES: return (new SampleTableCLP(Column.PHASES)).getText(element);
			case COMPOSITION: return sample.getComposition();
			case DENSITY: return Double.toString(sample.getDensity());
			case PACKING: return Double.toString(sample.getPackingFraction());
			case SUGGESTED_ENERGY: return Double.toString(sample.getSuggestedEnergy());
			case MU: return 1.0;
			case SUGGESTED_DIAMETER: return Double.toString(sample.getSuggestedCapDiameter());
			case ENERGY: return sample.getBeamState();
			case CAPILLARY: return sample.getContainer();
			default: return null;
			}
		}
		@Override
		protected void setValue(Object element, Object value) {
			XPDFSampleParameters sample = (XPDFSampleParameters) element;
			String sValue = (String) value;
			switch (column) {
			case NAME: sample.setName(sValue); break;
			case ID: break;
			case DETAILS: break; // TODO: This should eventually do something. Call a big function, probably.
			case PHASES: { // Parse a comma separated list of phases to a list of Strings
				String[] arrayOfPhases = sValue.split(","); 
				List<String> listOfPhases = new ArrayList<String>();
				for (int i = 0; i < arrayOfPhases.length; i++)
					listOfPhases.add(arrayOfPhases[i].trim());
				sample.setPhases(listOfPhases);
			} break;
			case COMPOSITION: sample.setComposition(sValue); break;
			case DENSITY: sample.setDensity(Double.parseDouble(sValue)); break;
			case PACKING: sample.setPackingFraction(Double.parseDouble(sValue)); break;
			case SUGGESTED_ENERGY: sample.setSuggestedEnergy(Double.parseDouble(sValue)); break;
			case MU: break;
			case SUGGESTED_DIAMETER: sample.setSuggestedCapDiameter(Double.parseDouble(sValue)); break;
			case ENERGY: sample.setBeamState(sValue); break;
			case CAPILLARY: sample.setContainer(sValue); break;
			default: break;
			}
			// Here, only this table needs updating
			tV.update(element, null);
		}
	}

	// Set a Comparator, depending on the column selected
	private Comparator<XPDFSampleParameters> getColumnSorting(Column column) {
		Comparator<XPDFSampleParameters> columnSorter;
		switch (column) {
		case NAME:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		break;
		case ID:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		};
		break;
		case DETAILS:
		case PHASES:
		case COMPOSITION:
			columnSorter = null;
		case DENSITY:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getDensity(), o2.getDensity());
			}
		};
		break;
		case PACKING:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getPackingFraction(), o2.getPackingFraction());
			}
		};
		break;
		case SUGGESTED_ENERGY:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getSuggestedEnergy(), o2.getSuggestedEnergy());
			}
		};
		break;
		case MU:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getMu(), o2.getMu());
			}
		};
		break;
		case SUGGESTED_DIAMETER:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return Double.compare(o1.getSuggestedCapDiameter(), o2.getSuggestedCapDiameter());
			}
		};
		break;
		case ENERGY:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return o1.getBeamState().compareTo(o2.getBeamState());
			}
		};
		case CAPILLARY:
			columnSorter = new Comparator<XPDFSampleParameters>() {
			@Override
			public int compare(XPDFSampleParameters o1, XPDFSampleParameters o2) {
				return o1.getContainer().compareTo(o2.getContainer());
			}
		};
		default:
			columnSorter = null;
		}
		return columnSorter;
	}

	// Column selection listeners.
	/**
	 * Creates a listener that sorts the data depending on the column selected.
	 * <p>
	 * Clicking on the individual column headers will sort, or reverse the sort on the data in all sub-tables.
	 * @param tableColumn
	 * 					the SWT column object
	 * @param column
	 * 				the enum identifier of the column
	 * @return the new anonymous sub-class of Selection Adapter
	 */
	private SelectionAdapter getColumnSelectionAdapter(final TableColumn tableColumn, final Column column) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				// Find the present sorted column, if any
				TableColumn presentSorted = null;
				int sortDirection = SWT.NONE;
				for (TableViewer tV : groupViewers.values()) {
					if (tV.getTable().getSortColumn() != null) {
						presentSorted = tV.getTable().getSortColumn();
						sortDirection = tV.getTable().getSortDirection();
					}
					// Set each table to unsorted
					tV.getTable().setSortDirection(SWT.NONE);
					tV.getTable().setSortColumn(null);
				}

				// If the same column is sorted as is now selected, then reverse the sorting
				if (presentSorted == tableColumn)
					sortDirection = (sortDirection == SWT.UP) ? SWT.DOWN : SWT.UP;

				// Do the sort
				if (sortDirection != SWT.NONE) {
					if (getColumnSorting(column) != null) {
						Collections.sort(samples, getColumnSorting(column));
						if (sortDirection == SWT.UP)
							Collections.reverse(samples);
					}
				}
				
				tableColumn.getParent().setSortDirection(sortDirection);
				tableColumn.getParent().setSortColumn(tableColumn);
				
				for (TableViewer tV : groupViewers.values())
					tV.refresh();
			}
		};
	}
		
	private void createActions() {
		loadTestDataAction = new LoadTestDataAction();
		loadTestDataAction.setText("Load test data");
		loadTestDataAction.setToolTipText("Load the test data");
		loadTestDataAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE));
		
		pointBreakAction = new Action() {
			@Override
			public void run() {
				samples.get(0).getDensity();
			}
		};
		pointBreakAction.setToolTipText("Ze goggles, zey...");
		pointBreakAction.setText("...do nothing");
		pointBreakAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_CLEAR));
		
		hookIntoContextMenu();
	}

	private void createRHSButtons(Composite compoAbove) {
		int rightMargin = -10;
		int topMargin = 10;
		Composite stCompo = compoAbove.getParent();

		simButton = new Button(stCompo, SWT.NONE);
		FormData formData= new FormData();
		formData.right = new FormAttachment(100, rightMargin);
		formData.top = new FormAttachment(compoAbove, topMargin);
		simButton.setLayoutData(formData);
		simButton.setText("Simulate PDF");
		simButton.setToolTipText("Produce a simulated pair distribution function for the selected sample");

		savButton = new Button(stCompo, SWT.NONE);
		formData = new FormData();
		formData.right = new FormAttachment(100, rightMargin);
		formData.top = new FormAttachment(simButton, topMargin);
		savButton.setLayoutData(formData);
		savButton.setText("Save");
		savButton.setToolTipText("Save the sample data to file (or the database?)");
		
	}

	private void createLoadButtons(Composite compoAbove) {
		int leftMargin = 10;
		int topMargin = 10;
		Composite stCompo = compoAbove.getParent();

		cifButton = new Button(stCompo, SWT.NONE);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, leftMargin);
		formData.top = new FormAttachment(compoAbove, topMargin);
		cifButton.setLayoutData(formData);
		cifButton.setText("New sample from CIF file");
		cifButton.setToolTipText("Create new sample from the data contained in a specified Crystallographic Information File.");

		eraButton = new Button(stCompo, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(0, leftMargin);
		formData.top = new FormAttachment(cifButton, topMargin);
		eraButton.setLayoutData(formData);
		eraButton.setText("New sample from ERA file");
		eraButton.setToolTipText("Create a new sample from the data contained in a specified ERA file.");
	}

	@Override
	public void setFocus() {
		// Can all the TableViewers have focus?
		for (TableViewer iTV : groupViewers.values() ) {
			iTV.getControl().setFocus();
		}
	}
	
	// Hook actions into the context menu
	private void hookIntoContextMenu() {
		MenuManager menuMan = new MenuManager("#PopupMenu");
		menuMan.setRemoveAllWhenShown(true);
		menuMan.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);				
			}
		});
		
		for (TableViewer iTV : groupViewers.values() ) {
			Menu popupMenu = menuMan.createContextMenu(iTV.getControl());
			iTV.getControl().setMenu(popupMenu);
			getSite().registerContextMenu(menuMan, iTV);
		}
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(loadTestDataAction);
		manager.add(new Separator("Debug"));
		manager.add(pointBreakAction);
	}


	class SampleParametersContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {} // TODO Auto-generated method stub

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {} // TODO Auto-generated method stub

		@Override
		public Object[] getElements(Object inputElement) {
			return samples.toArray(new XPDFSampleParameters[]{});
		}
		
	}

	/**
	 * Changes the selection on all sub-tables.
	 * <p>
	 * When the selection changes, change the selection on the other
	 * sub-tables.
	 *
	 */
	// By using a object-wide flag we can prevent each call triggering four
	// more and causing a stack overflow.
	class SubTableSelectionChangedListener implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				if (propagateSelectionChange) {
					// Oh, the race conditions we shall have.
					// Tell the tables not to further propagate the selection
					// change when we programmatically change the selection
					propagateSelectionChange = false;
								
					for (TableViewer tV : groupViewers.values())
						tV.setSelection(selection, true);
				
					propagateSelectionChange= true;
				}
			}
		}
	}
	
	// Sample data with integer multiplicities
	class LoadTestDataAction extends Action {

		@Override
		public void run() {
			samples = new ArrayList<XPDFSampleParameters>();
			
			int currentID = 154;
			
			// barium titanate
			XPDFSampleParameters bto = new XPDFSampleParameters();
			bto.setName("Barium Titanate");
			bto.setId(currentID++);
			bto.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"BTO", "CaTiO3"})));
			bto.setComposition("BaTiO3"); // Should be "Ba0.9Ca0.1TiO3"
			bto.setDensity(3.71);
			// Packing fraction as default
			bto.setSuggestedEnergy(76.6);
			bto.setSuggestedCapDiameter(1.0);
			bto.setBeamState("76.6 Hi Flux");
			bto.setContainer("0.3 mm B");
			
			samples.add(bto);
			
			// rutile
			XPDFSampleParameters rutile = new XPDFSampleParameters();
			rutile.setName("Rutile");
			rutile.setId(currentID++);
			rutile.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"TiO2"})));
			rutile.setComposition("TiO2");
			rutile.setDensity(6.67);
			// Packing fraction as result
			rutile.setSuggestedEnergy(76.6);
			rutile.setSuggestedCapDiameter(5.0);
			rutile.setBeamState("76.6 Hi Flux");
			rutile.setContainer("0.5 mm B");
			
			samples.add(rutile);
			
			// and something else
			XPDFSampleParameters explodite = new XPDFSampleParameters();
			explodite.setName("Explodite");
			explodite.setId(currentID++);
			explodite.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"LF", "O"})));
			explodite.setComposition("K2S4P");
			explodite.setDensity(1.1);
			//packing fraction as default
			explodite.setSuggestedEnergy(76.6);
			explodite.setSuggestedCapDiameter(5.0);
			explodite.setBeamState("76.6 Hi Flux");
			explodite.setContainer("0.5 mm");
			
			samples.add(explodite);
			
			for (TableViewer iTV : groupViewers.values()) {
				iTV.getTable().setSortDirection(SWT.NONE);
				iTV.getTable().setSortColumn(null);
				iTV.refresh();
			}
		}
	}
}