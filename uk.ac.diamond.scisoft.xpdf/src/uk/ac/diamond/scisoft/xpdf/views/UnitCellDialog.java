package uk.ac.diamond.scisoft.xpdf.views;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

class UnitCellDialog extends Dialog {

	protected UnitCellDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		
		Label silly  = new Label(container, SWT.NONE);
		silly.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		silly.setText("Unit Cell Editor");
		
		
		return container;
	}
	
	// Override to set the window text
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Unit Cell Editor");
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(320, 240);
	}
}
