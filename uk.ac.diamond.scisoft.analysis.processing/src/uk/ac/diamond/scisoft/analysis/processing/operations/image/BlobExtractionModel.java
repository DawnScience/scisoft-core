/*-
 * Copyright (c) 2011-2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class BlobExtractionModel extends AbstractOperationModel {

	enum ConnectRule {
		FOUR(4), EIGHT(8);
		private final int value;

		ConnectRule(int value) {
			this.value = value;
		}

		int value() {
			return value;
		}
	}

	@OperationModelField(label = "Connect Rule", hint = "Connectivity rule: blobs can be defined using a 4 or 8 connect rule")
	private ConnectRule rule = ConnectRule.EIGHT;

	public ConnectRule getRule() {
		return rule;
	}
	public void setRule(ConnectRule rule) {
		firePropertyChange("connectRule", this.rule, this.rule = rule);
	}
}
