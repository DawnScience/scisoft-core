/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.image;

public enum AlignMethod {
	WITH_ROI(0), AFFINE_TRANSFORM(1);
	private int idx;

	private AlignMethod(int idx) {
		this.idx = idx;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public static AlignMethod getAlignMethod(int idx) {
		switch (idx) {
		case 0:
			return WITH_ROI;
		case 1:
			return AFFINE_TRANSFORM;
		default:
			break;
		}
		return null;
	}
}