/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.validation;

import static org.eclipse.dawnsci.nexus.NexusConstants.MILLISECOND_DATE_FORMAT;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

/**
 * Enumeration of Data types allowed in NXDL specifications. Call the {@link #validate(String, IDataset)}
 * method to validate a dataset against the type. Note that we only validate that the type of dataset
 * (i.e. the element type) is compatible for the nexus type. For types where the nexus type is more
 * specific than merely the type of a dataset can express, this is not checked. For example, for NX_POSINT
 * we check that the element type of the dataset an integer type, but do not check that all the values are
 * positive. Doing this would require checking all the values in a dataset at the end of a scan.
 * 
 * <p>Source: <a href="http://download.nexusformat.org/doc/html/nxdl-types.html#data-types-allowed-in-nxdl-specifications">Data Types allowed in NXDL specifications</a>
 *
 * TODO, can this be generated from nxdlTypes.xsd? See https://jira.diamond.ac.uk/browse/DAQ-3300
 */
public enum NexusDataType {

	/**
	 * 	ISO8601 date/time stamp.
	 *  Note, we don't properly validate this type. Currently it is unused in NXDL definitions, so doing this
	 *  is not urgent.
	 */
	ISO8601(String.class),

	/**
	 * Any representation of binary data - if text, line terminator is [CR][LF]
	 */
	NX_BINARY(Object.class),

	/**
	 * true/false value ( true | 1 | false | 0 )
	 */
	NX_BOOLEAN(Boolean.class),

	/**
	 * any string representation
	 */
	NX_CHAR(String.class),

	/**
	 * Alias for ISO8601. ISO 8601 date and time representation (http://www.w3.org/TR/NOTE-datetime)
	 */
	NX_DATE_TIME(String.class, Date.class) {

		@Override
		public boolean validate(ILazyDataset lazyDataset) throws NexusException {
			if (!super.validate(lazyDataset)) return false;
			
			// we only validate single values (TODO: should we validate a per-point dataset?)
			if (lazyDataset.getSize() != 1) return true;
			
			final IDataset dataset;
			try {
				dataset = lazyDataset.getSlice();
			} catch (DatasetException e) {
				throw new NexusException("Could not read dataset", e);
			}
			
			String dateStr = null;
			// only validate a single value
			if (dataset.getRank() == 0) {
				dateStr = dataset.getString();
			} else if (dataset.getRank() == 1 && dataset.getSize() == 1) {
				dateStr = dataset.getString(0);
			} else {
				return true;
			}
			
			try {
				MILLISECOND_DATE_FORMAT.parse(dateStr);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		}
		
	},

	/**
	 * any representation of a floating point number
	 */
	NX_FLOAT(Float.class, Double.class),

	/**
	 * any representation of an integer number
	 */
	NX_INT(Byte.class, Short.class, Integer.class, Long.class),

	/**
	 * any valid NeXus number representation
	 */
	NX_NUMBER(Number.class),

	/**
	 * any representation of a positive integer number (greater than zero)
	 * Note: we don't currently check the values are all positive. This could only be done
	 * at the end of a scan, i.e. after all the data had been written.
	 */
	NX_POSINT(Byte.class, Short.class, Integer.class, Long.class),
	/**
	 * any representation of an unsigned integer number (includes zero)
	 * Note: we don't currently check the values are all positive or zero. This could only be done
	 * at the end of a scan, i.e. after all the data had been written.
	 */
	NX_UINT(Byte.class, Short.class, Integer.class, Long.class);

	private List<Class<?>> javaClasses;

	private NexusDataType(final Class<?>... javaClasses) {
		this.javaClasses = Arrays.asList(javaClasses);
	}

	/**
	 * Validate that the given dataset is valid according to thdataset.getElementClass();is {@link NexusDataType}
	 * @param fieldName
	 * @param dataset
	 * @throws NexusException 
	 */
	public boolean validate(final ILazyDataset dataset) throws NexusException {
		Class<?> elementClass = dataset.getElementClass();
		for (Class<?> javaClass : javaClasses) {
			if (javaClass.isAssignableFrom(elementClass)) {
				return true;
			}
		}
		return false;
	}

}
