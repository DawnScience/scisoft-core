package uk.ac.diamond.scisoft.ptychography.rcp;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.handlers.IHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.handlers.CallPtychoEditor;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;

public class PtychoPerspective implements IPerspectiveFactory {

	private static final Logger logger = LoggerFactory.getLogger(PtychoPerspective.class);
	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.perspective";

	@Override
	public void createInitialLayout(IPageLayout layout) {

		String editorArea = layout.getEditorArea();
		IFolderLayout navigatorFolder = layout.createFolder("navigator-folder", IPageLayout.LEFT, 0.2f, editorArea);
		navigatorFolder.addView("org.eclipse.ui.navigator.ProjectExplorer");
		navigatorFolder.addView("uk.ac.diamond.sda.navigator.views.FileView");

		String ptychoPlotViews = PtychoConstants.PTYCHOPLOTVIEWS;
		String probePlot = ptychoPlotViews + "Probe";
		String objectPlot = ptychoPlotViews + "Object";
		String scanPosPlot = ptychoPlotViews + "ScanPos";
		String dataPlot = ptychoPlotViews + "Data";

		IFolderLayout objectFolder = layout.createFolder(objectPlot, IPageLayout.RIGHT, 0.7f, editorArea);
		objectFolder.addView(ptychoPlotViews + "ObjectComplex");
		objectFolder.addView(ptychoPlotViews + "ObjectPhase");
		objectFolder.addView(ptychoPlotViews + "ObjectModulus");
		
		IFolderLayout probeFolder = layout.createFolder(probePlot, IPageLayout.RIGHT, 0.6f, editorArea);
		probeFolder.addView(ptychoPlotViews + "ProbeComplex");
		probeFolder.addView(ptychoPlotViews + "ProbePhase");
		probeFolder.addView(ptychoPlotViews + "ProbeModulus");
		
		IFolderLayout dataFolder = layout.createFolder(dataPlot, IPageLayout.BOTTOM, 0.6f, objectPlot);
		dataFolder.addView(ptychoPlotViews + "RMSE");
		dataFolder.addView(ptychoPlotViews + "DeadPixels");
		dataFolder.addView(ptychoPlotViews + "RecordedDiffPattern");
		dataFolder.addView(ptychoPlotViews + "DiffPatternEstimate");
		
		IFolderLayout scanPosFolder = layout.createFolder(scanPosPlot, IPageLayout.BOTTOM, 0.6f, probePlot);
		scanPosFolder.addView(ptychoPlotViews + "RelativeScanPosOriginal");
		scanPosFolder.addView(ptychoPlotViews + "RelativeScanPosCorrected");
		
		// add the console and the outline to the bottom
		IFolderLayout bottomLayout = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.6f, editorArea);
		bottomLayout.addView(IConsoleConstants.ID_CONSOLE_VIEW);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getAdapter(IHandlerService.class);
				try {
					handlerService.executeCommand(CallPtychoEditor.COMMAND_ID, null);
				} catch (ExecutionException | NotDefinedException | NotEnabledException
						| NotHandledException e) {
					logger.error("Error opening Ptycho editor:" + e.getMessage());
				}
			}
		});
	}
}
