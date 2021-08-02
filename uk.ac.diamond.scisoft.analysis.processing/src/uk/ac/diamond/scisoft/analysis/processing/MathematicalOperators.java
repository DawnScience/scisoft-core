/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Tim Snow


package uk.ac.diamond.scisoft.analysis.processing;


public enum MathematicalOperators {
		ADD("Add"),
		SUBTRACT("Subtract"),
		DIVIDE("Divide"),
		MULTIPLY("Multiply");
		
		private final String displayName;
		
		MathematicalOperators(String displayName) {
			this.displayName = displayName;
		}
		
		@Override
		public String toString() {
			return displayName;
		}
	}