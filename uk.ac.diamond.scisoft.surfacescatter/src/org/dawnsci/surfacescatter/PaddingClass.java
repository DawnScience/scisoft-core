package org.dawnsci.surfacescatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PaddingClass extends Composite {

    
    public PaddingClass(Composite parent, int style) throws Exception {
        super(parent, style);
        //composite = new Composite(parent, SWT.NONE);

        new Label(this, SWT.NONE).setText("PADDING CLASS");
     }
}
