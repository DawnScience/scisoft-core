/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRMIPlotServer {
	private static final Logger logger = LoggerFactory.getLogger(TestRMIPlotServer.class);

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			new RMIPlotServer();
		} catch (Exception e) {
			logger.error("Couldn't start RMIPlotServer ", e);
		}

	}

}
