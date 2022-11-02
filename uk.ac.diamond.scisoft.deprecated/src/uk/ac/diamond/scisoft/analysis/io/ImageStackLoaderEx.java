/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.StringDataset;

/**
 * Class to create dataset from a list of filenames.
 * 
 * The filenames can either represent a 1d or 2d scan. The dimensionality is
 * indicated by a dimensions constructor argument
 * 
 * In the case of 2d scan 
 * 
 * filename for position x,y = filenames[x*dimensions[1] + y ]
 * 
 * 
 * The shape of the 'whole' dataset represented by the set of filenames is dimensions
 * shape of the first image
 * 
 * The type of the dataset is set to equal the type of the first image.
 * @deprecated Use {@link ImageStackLoader} directly instead
 */
@Deprecated(since="Dawn 2.1")
public class ImageStackLoaderEx extends ImageStackLoader {
	
	private static final DeprecationLogger logger = DeprecationLogger.getLogger(ImageStackLoaderEx.class);
	
	public ImageStackLoaderEx(int[] dimensions, String[] imageFilenames, String directory) throws Exception {
		this(DatasetFactory.createFromObject(StringDataset.class, imageFilenames, dimensions), null, directory);
	}

	public ImageStackLoaderEx(int[] dimensions, String[] imageFilenames) throws Exception {
		this(dimensions, imageFilenames, null);
	}

	public ImageStackLoaderEx(StringDataset imageFilenames, String directory) throws Exception {
		super(imageFilenames, directory);
		logger.deprecatedClass(null, "uk.ac.diamond.scisoft.analysis.io.ImageStackLoader");
	}

	public ImageStackLoaderEx(StringDataset imageFilenames, IDataHolder dh, String directory) throws Exception {
		super(imageFilenames, dh, directory);
		logger.deprecatedClass(null, "uk.ac.diamond.scisoft.analysis.io.ImageStackLoader");
	}
}
