package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;

public class SimplePtychoEditor extends AbstractPtychoEditor {

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.basicPtychoInput";
	private Text dataFilePathText;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName("Ptychography parameter simple Editor");
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		String dataFilePath = getDataFilePath(levels);
		Label dataFileLabel = new Label(container, SWT.NONE);
		dataFileLabel.setText("Scan path:");
		dataFilePathText = new Text(container, SWT.BORDER);
		dataFilePathText.setText(dataFilePath);
		dataFilePathText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
	}

	private String getDataFilePath(List<PtychoData> levels) {
		if(levels == null)
			return null;
		for (PtychoData data : levels) {
			if (data.getName().equals("path")) {
				return data.getDefaultValue();
			}
		}
		return "";
	}

}
