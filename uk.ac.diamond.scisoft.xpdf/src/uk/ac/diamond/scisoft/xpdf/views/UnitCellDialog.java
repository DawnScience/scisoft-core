package uk.ac.diamond.scisoft.xpdf.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

class UnitCellDialog extends Dialog {

	private Map<String, XPDFAtom> atoms;
	
	protected UnitCellDialog(Shell parentShell) {
		super(parentShell);
		atoms = new HashMap<String, XPDFAtom>();
    	// All phases are ferrous titanate
		atoms.put("Fe1", new XPDFAtom(26, 0.5, new double[] {0.333, 0.333, 0.333}));
		atoms.put("Fe2", new XPDFAtom(26, 0.5, new double[] {0.667, 0.667, 0.667}));
		atoms.put("Ti1", new XPDFAtom(22, 0.5, new double[] {0.167, 0.167, 0.167}));
		atoms.put("Ti2", new XPDFAtom(22, 0.5, new double[] {0.833, 0.833, 0.833}));
		atoms.put("O1", new XPDFAtom(8, 0.5, new double[] {0.583, 0.917, 0.250}));
		atoms.put("O2", new XPDFAtom(8, 0.5, new double[] {0.917, 0.250, 0.583}));
		atoms.put("O3", new XPDFAtom(8, 0.5, new double[] {0.250, 0.583, 0.917}));
		atoms.put("O4", new XPDFAtom(8, 0.5, new double[] {-0.583, -0.917, -0.250}));
		atoms.put("O5", new XPDFAtom(8, 0.5, new double[] {-0.917, -0.250, -0.583}));
		atoms.put("O6", new XPDFAtom(8, 0.5, new double[] {-0.250, -0.583, -0.917}));
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
	
	public Map<String, XPDFAtom> getAllAtoms() {
		return atoms;
	}
}
