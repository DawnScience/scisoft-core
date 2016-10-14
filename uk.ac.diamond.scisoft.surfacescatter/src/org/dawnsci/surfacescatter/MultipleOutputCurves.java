package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.dawb.common.ui.widgets.ActionBarWrapper;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.region.IRegion.RegionType;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import uk.ac.diamond.scisoft.analysis.io.ASCIIDataWithHeadingSaver;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class MultipleOutputCurves extends Composite {

	private IPlottingSystem<Composite> plotSystem4;
	private IRegion imageNo;
	private ILineTrace lt;
	private ExampleModel model;
	private DataModel dm;
	private ArrayList<Button> datSelector;
	private Button overlap;
	private Button sc;
	private Button save;
	private Button intensitySelect;
	private Group datSelection;
	private Group overlapSelection;
	private Composite parent;
	
	public MultipleOutputCurves(Composite parent, int style, ArrayList<ExampleModel> models, ArrayList<DataModel> dms,
			SuperModel sm) {
		super(parent, style);

		new Label(this, SWT.NONE).setText("Output Curves");

		this.parent = parent;
		this.model = models.get(sm.getSelection());
		this.dm = dms.get(sm.getSelection());
	
		
		try {
			plotSystem4 = PlottingFactory.createPlottingSystem();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		this.createContents(model, sm, dm);

	}

	public void createContents(ExampleModel model, SuperModel sm, DataModel dm) {

		datSelection = new Group(this, SWT.NULL);
		GridLayout datSelectionLayout = new GridLayout(4, true);
		GridData datSelectionData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		datSelectionData.minimumWidth = 50;
		datSelection.setLayout(datSelectionLayout);
		datSelection.setLayoutData(datSelectionData);

		datSelector = new ArrayList<Button>();

		for (int i = 0; i < sm.getFilepaths().length; i++) {
			datSelector.add(new Button(datSelection, SWT.CHECK));
			datSelector.get(i).setText(StringUtils.substringAfterLast(sm.getFilepaths()[i], "/"));
		}
		
		overlapSelection = new Group(this, SWT.NULL);
		GridLayout overlapSelectionLayout = new GridLayout(4, true);
		GridData overlapSelectionData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		overlapSelectionData.minimumWidth = 50;
		overlapSelection.setLayout(overlapSelectionLayout);
		overlapSelection.setLayoutData(overlapSelectionData);
		
		overlap = new Button(overlapSelection, SWT.PUSH);
		overlap.setText("Overlap");
		
		intensitySelect = new Button(overlapSelection, SWT.PUSH);
		intensitySelect.setText("Intensity?");
		
		save = new Button(overlapSelection, SWT.PUSH);
		save.setText("Save Spliced");
		
		
		
		
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		setLayout(gridLayout);
		
		
		
		
		
		ActionBarWrapper actionBarComposite = ActionBarWrapper.createActionBars(this, null);
		
		final GridData gd_secondField = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_secondField.grabExcessVerticalSpace = true;
		gd_secondField.grabExcessVerticalSpace = true;
		gd_secondField.heightHint = 100;

		plotSystem4.createPlotPart(this, "ExamplePlot", actionBarComposite, PlotType.IMAGE, null);
		
		String t = CurveStateIdentifier.CurveStateIdentifier1(plotSystem4);
		
		if(t == "f"){
			lt = plotSystem4.createLineTrace(dm.getName() + "_Fhkl");
		}
		else{
			lt = plotSystem4.createLineTrace(dm.getName() + "_Intensity");
		}
		
		if (dm.getyList() == null || dm.getxList() == null) {
			lt.setData(dm.backupDataset(), dm.backupDataset());
		} else {
			if (t == "f"){
				lt.setData(dm.yIDatasetFhkl(), dm.xIDataset());
			}
			else{
				lt.setData(dm.yIDataset(), dm.xIDataset());
			}
		}

		plotSystem4.addTrace(lt);
		try {
			imageNo = plotSystem4.createRegion("Image", RegionType.XAXIS_LINE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		plotSystem4.getPlotComposite().setLayoutData(gd_secondField);

	}

	public Composite getComposite() {

		return this;
	}

	public IPlottingSystem<Composite> getPlotSystem() {
		return plotSystem4;
	}

	public IRegion getRegionNo() {
		return imageNo;
	}

	public void resetCurve() {
	

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				plotSystem4.clear();
				lt = plotSystem4.createLineTrace("Output Curve");
				lt.setData(dm.backupDataset(), dm.backupDataset());
				plotSystem4.addTrace(lt);
				plotSystem4.repaint();
			}
		});

	}

	public void updateCurve(DataModel dm1, Boolean intensity, SuperModel sm) {

		if (lt.getDataName() == null) {
			lt = plotSystem4.createLineTrace("Output Curve");
		}

		if (dm1.getyList() == null || dm1.getxList() == null) {
			lt.setData(dm1.backupDataset(), dm1.backupDataset());
		} else if (intensity == true) {
			lt.setData(dm1.xIDataset(), dm1.yIDataset());
			lt.setName(dm1.getName()+ "_Intensity");
//			System.out.println("doin' intensity");
		
		}else{
			lt.setData(dm1.xIDataset(), dm1.yIDatasetFhkl());
			lt.setName(dm1.getName()+ "_Fhkl");
//			System.out.println("doin' fhkl");
		}
		
//		System.out.println("IN MultipleOutput updateCuve");
//		System.out.println("dm1 xIDataset: " + dm1.xIDataset().getSize());
//		System.out.println("dm1 yIDataset: " + dm1.yIDataset().getSize());
		plotSystem4.clear();
		plotSystem4.addTrace(lt);
		plotSystem4.repaint();
		

	}

	public ArrayList<Button> getDatSelector() {
		return datSelector;
	}
	
	public Button getOverlap(){
		return overlap;
	}
	
	
	public void addToDatSelector(){
		if(this.getSc() == null){
			sc = new Button(overlapSelection, SWT.CHECK);
			sc.setText("Spliced Curve");
			sc.setSelection(true);
			overlapSelection.layout(true, true);
			overlapSelection.setSize(overlapSelection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			this.redraw();
			this.update();
		}
	}
	
	public Button getSc(){
		return sc;
	}
	
	
	public Group getOverlapSelectionGroup(){
		return overlapSelection;
	}
	
	public Button getIntensity(){
		return intensitySelect;
	}
	
	public Button getSave(){
		return save;
	}
	
}
