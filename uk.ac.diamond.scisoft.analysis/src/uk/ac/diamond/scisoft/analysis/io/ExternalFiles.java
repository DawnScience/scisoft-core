/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

/**
 * Object used to encapulate reference to ExternalFile from HDF5
 */
public class ExternalFiles {
	int[] shape;
	String[] files;

	public String getAsText() {
		if (files == null)
			return "";
		StringBuilder sBuilder = new StringBuilder();
		boolean addCR = false;
		for (String filename : files) {
			if (addCR) {
				sBuilder.append("\n");
			}
			sBuilder.append(filename);
			addCR = true;
		}
		return sBuilder.toString();
	}
}