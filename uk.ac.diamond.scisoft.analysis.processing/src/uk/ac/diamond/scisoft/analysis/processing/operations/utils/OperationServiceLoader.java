/*-
 * Copyright 2011-2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.utils;

import org.eclipse.dawnsci.analysis.api.image.IImageFilterService;
import org.eclipse.dawnsci.analysis.api.image.IImageStitchingProcess;
import org.eclipse.dawnsci.analysis.api.image.IImageTransform;

public class OperationServiceLoader {

	private static IImageFilterService imageFilterService;
	private static IImageTransform imageTransformService;
	private static IImageStitchingProcess imageStitchingService;

	/**
	 * Injected by OSGI
	 * @param it
	 */
	public static void setImageFilterService(IImageFilterService ifs) {
		imageFilterService = ifs;
	}

	/**
	 * Injected by OSGI
	 * @param it
	 */
	public static void setImageTransformService(IImageTransform it) {
		imageTransformService = it;
	}

	/**
	 * Injected by OSGI
	 * @param isp
	 */
	public static void setImageStitchingService(IImageStitchingProcess isp) {
		imageStitchingService = isp;
	}

	/**
	 * Used for OSGI injection
	 */
	public OperationServiceLoader () {
	}

	public static IImageFilterService getImageFilterService() {
		return imageFilterService;
	}

	public static IImageTransform getImageTransformService() {
		return imageTransformService;
	}

	public static IImageStitchingProcess getImageStitchingService() {
		return imageStitchingService;
	}
}
