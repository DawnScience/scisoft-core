package uk.ac.diamond.scisoft.xpdf.views;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.PlotType;
import org.eclipse.dawnsci.plotting.api.PlottingFactory;
import org.eclipse.dawnsci.plotting.api.trace.ITrace;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XPDFPopUpPlotDialog extends Dialog {

	private IDataset trace;
	private IPlottingSystem harryPlotter;
	
	// Logger
	private final static Logger logger = LoggerFactory.getLogger(XPDFPopUpPlotDialog.class);

	public XPDFPopUpPlotDialog(Shell parentShell, String infoText, IDataset trace) {
		super(parentShell);
		this.trace = trace;
		try{
			harryPlotter = PlottingFactory.createPlottingSystem();
		} catch (Exception e) {
			logger.error("Could not create plotting system.", e);
		}
		// TODO: What style bits do I need to make the windows able to go behind the parent?
		setShellStyle(getShellStyle() ^ SWT.APPLICATION_MODAL | SWT.MODELESS);
		setBlockOnOpen(false);
	}

	
	@Override
	public Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		
		harryPlotter.createPlotPart(container, "Plot!", null, PlotType.XY, null);
		List<ITrace> plotTraces = harryPlotter.createPlot1D(trace.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice(), Arrays.asList(new IDataset[] {trace}), null);
		
		return container;
		
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Plot!");
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(640, 480);
	}

	@Override
	public boolean close() {
		harryPlotter.dispose();
		return super.close();
	}
	
}
