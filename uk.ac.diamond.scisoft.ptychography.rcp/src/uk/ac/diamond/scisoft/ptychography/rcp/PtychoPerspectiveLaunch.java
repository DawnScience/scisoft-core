package uk.ac.diamond.scisoft.ptychography.rcp;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import uk.ac.diamond.scisoft.ptychography.rcp.handlers.CallPtychoEditor;

public class PtychoPerspectiveLaunch implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	private IWorkbenchPartSite site;

	@Override
	public void init(IWorkbenchWindow window) {
		site = window.getActivePage().getActiveEditor().getSite();
	}

	@Override
	public void run(IAction action) {
		try {
			PlatformUI.getWorkbench().showPerspective(PtychoPerspective.ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
		site = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getSite();
		Command command = ((ICommandService) site.getService(
				ICommandService.class)).getCommand(CallPtychoEditor.COMMAND_ID);
		final Event trigger = new Event();
		ExecutionEvent executionEvent = ((IHandlerService) site
				.getService(IHandlerService.class)).createExecutionEvent(
				command, trigger);
		try {
			command.executeWithChecks(executionEvent);
		} catch (ExecutionException | NotDefinedException | NotEnabledException
				| NotHandledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
