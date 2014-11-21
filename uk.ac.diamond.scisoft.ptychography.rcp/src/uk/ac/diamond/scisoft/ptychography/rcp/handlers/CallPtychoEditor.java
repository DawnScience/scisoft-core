package uk.ac.diamond.scisoft.ptychography.rcp.handlers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.dawb.common.util.eclipse.BundleUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.FileStoreEditorInput;

import uk.ac.diamond.scisoft.ptychography.rcp.editors.PtychoTreeViewerEditor;
import uk.ac.diamond.scisoft.ptychography.rcp.utils.PtychoConstants;

public class CallPtychoEditor extends AbstractHandler implements IHandler {

	public static final String COMMAND_ID = "uk.ac.diamond.scisoft.ptychography.rcp.openPtychoEditor";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();

		IFileStore fileLocation;
		try {
			File bundle = BundleUtils.getBundleLocation("uk.ac.diamond.scisoft.ptychography.rcp");
			String fullPath = bundle.getAbsolutePath();
			File f = new File(fullPath + File.separator
					+ PtychoConstants.RESOURCES + File.separator
					+ PtychoConstants.SPREADSHEET_FILE);
			URI uri = f.toURI();
			fileLocation = EFS.getLocalFileSystem().getStore(uri);
			FileStoreEditorInput fileStoreEditorInput = new FileStoreEditorInput(fileLocation);
			page.openEditor((IEditorInput) fileStoreEditorInput,
					PtychoTreeViewerEditor.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
