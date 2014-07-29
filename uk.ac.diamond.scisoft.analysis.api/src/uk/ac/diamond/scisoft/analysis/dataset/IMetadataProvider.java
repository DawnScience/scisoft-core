/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.metadata.MetadataType;

/**
 * Interface which acts to provide metadata from an object
 */
public interface IMetadataProvider {

	/**
	 * @return an instance of IMetaData
	 * @throws Exception
	 */
	public IMetaData getMetadata() throws Exception;

	/**
	 * @param clazz if null return everything
	 * @return metadata
	 * @throws Exception
	 */
	public <T extends MetadataType> List<T> getMetadata(Class<T> clazz) throws Exception;
}
