package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;

public class SimplePtychoEditor extends AbstractPtychoEditor {

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.basicPtychoInput";

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName("Ptychography parameter simple Editor");
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite subComp = new Composite(container, SWT.NONE);
		subComp.setLayout(new GridLayout(1, false));
		subComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLabelAndText(subComp, "path");

		createPythonRunCommand(container);
	}

	private void createLabelAndText(Composite parent, String name) {
		if(tree == null)
			return;
		List<PtychoNode> result = new ArrayList<PtychoNode>();
		for (PtychoNode node : tree) {
			PtychoNode aNode = PtychoTreeUtils.findNodeWithName(node, name);
			if (aNode != null)
				result.add(aNode);
		}
		for (final PtychoNode res : result) {
			if (res != null) {
				String parentName = "";
				PtychoNode par = res.getParent();
				parentName = par != null ? par.getData().getName() : "";
				Label label = new Label(parent, SWT.NONE);
				label.setText(parentName + " " + name + ":");
				final Text text = new Text(parent, SWT.BORDER);
				text.setText(res.getData().getDefaultValue());
				text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
				text.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						res.getData().setDefaultValue(text.getText());
					}
				});
			}
		}
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
