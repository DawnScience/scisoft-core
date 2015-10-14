/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.HashMap;
import java.util.Map;

public class FittingConstants {

	public static final String PEAK_NUMBER             = "org.dawb.workbench.plotting.tools.peakNumber";
	public static final String PEAK_NUMBER_CHOICES     = "org.dawb.workbench.plotting.tools.peakNumberChoices";
	public static final String FIT_SMOOTH_FACTOR       = "org.dawb.workbench.plotting.tools.fitSmoothFactor";
	public static final String SHOW_FWHM_SELECTIONS    = "org.dawb.workbench.plotting.tools.showFWHMSelection";
	public static final String SHOW_PEAK_SELECTIONS    = "org.dawb.workbench.plotting.tools.showPeakSelection";
	public static final String SHOW_FITTING_TRACE      = "org.dawb.workbench.plotting.tools.showFittingTrace";
	public static final String SHOW_ANNOTATION_AT_PEAK = "org.dawb.workbench.plotting.tools.showAnnoationsAtPeak";
	public static final String PEAK_TYPE               = "org.dawb.workbench.plotting.tools.peakType";
	public static final String SMOOTHING               = "org.dawb.workbench.plotting.tools.smoothing";
	public static final String QUALITY                 = "org.dawb.workbench.plotting.tools.quality";
	public static final String POLY_ORDER              = "org.dawb.workbench.plotting.tools.polyOrder";
	public static final String POLY_CHOICES            = "org.dawb.workbench.plotting.tools.polyChoices";
	public static final String SHOW_POLY_TRACE         = "org.dawb.workbench.plotting.tools.showPolyTrace";
	public static final String SHOW_POLY_RANGE         = "org.dawb.workbench.plotting.tools.showPolyRange";
	public static final String INT_FORMAT              = "org.dawb.workbench.plotting.tools.fitting.intFormat";
	public static final String REAL_FORMAT             = "org.dawb.workbench.plotting.tools.fitting.realFormat";
	public static final String FIT_QUALITY             = "org.dawb.workbench.plotting.tools.fitQuality";
	public static final String FIT_ALGORITHM           = "org.dawb.workbench.plotting.tools.fitAlgorithm";
	public static final String ADD_PEAK_MODE           = "org.dawb.workbench.plotting.tools.addingPeaks";

	private static Map<Integer, FIT_ALGORITHMS> idMap = new HashMap<>();
	/**
	 * Function fitting algorithms available
	 */
	public enum FIT_ALGORITHMS {
		APACHENELDERMEAD(0, "Nelder Mead Fitting"),
		GENETIC(1, "Genetic Algorithm"),
		APACHECONJUGATEGRADIENT(2, "Conjugate Gradient"),
		APACHELEVENBERGMAQUARDT(3, "Levenberg Marquardt");

		public final int ID;
		public final String NAME;

		private FIT_ALGORITHMS(int id, String name) {
			ID = id;
			NAME = name;
			idMap.put(id, this);
		}

		/**
		 * Return the algorithm for the given id, or null if
		 * no such id exists.
		 */
		public static FIT_ALGORITHMS fromId(int id) {
			return idMap.get(id);
		}
	}
}
