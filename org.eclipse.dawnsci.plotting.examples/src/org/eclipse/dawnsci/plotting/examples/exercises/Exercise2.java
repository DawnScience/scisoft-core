/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.examples.exercises;

import org.eclipse.dawnsci.plotting.api.trace.IImageTrace;
import org.eclipse.dawnsci.plotting.api.trace.ITraceListener;
import org.eclipse.dawnsci.plotting.api.trace.TraceEvent;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.widgets.Composite;

public class Exercise2 extends Exercise1 {
	
	private ITraceListener traceListener;
	private BooleanDataset mask;

	@Override
	public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        
        traceListener = createTraceListener();
        system.addTraceListener(traceListener);
	}
	
	protected ITraceListener createTraceListener() {
		return new ITraceListener.Stub() {
			@Override
			public void traceUpdated(TraceEvent evt) {
				if (evt.getSource() instanceof IImageTrace) {
					IImageTrace trace = (IImageTrace) evt.getSource();
					createThresholdMask(trace);
				}
 			}
		};
	}

	protected void createThresholdMask(IImageTrace trace) {
		// Lets do some masking...
		IDataset image = trace.getData();
		if (mask==null) mask = DatasetFactory.zeros(BooleanDataset.class, image.getShape());

		// Start off with everything true (true = not-masked!)
		mask.fill(true);

		// converse of setting everything <= -1 false
		Comparisons.greaterThan(image, -1, mask);

		trace.setMask(mask);
	}

	@Override
	public void dispose() {
		system.removeTraceListener(traceListener); // Not necessary but good practice since we added one...
		super.dispose();
	}
}
