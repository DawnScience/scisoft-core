/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.api.processing;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.january.dataset.IDataset;

/**
 * Object to hold return data from an IOperation.
 */
public class OperationData {

	private IDataset data;
	private Serializable[] auxData;
	private OperationLog log;
	private Serializable[] summaryData;
	private Map<String, Serializable> configuredFields;

	public IDataset getData() {
		return data;
	}

	public OperationData() {
		this(null, (Serializable) null);
	}

	public OperationData(IDataset data) {
		this(data, (Serializable) null);
	}

	public OperationData(IDataset data, Serializable... aux) {
		this.data = data;
		this.auxData = aux;
	}

	public OperationData(OperationData od) {
		if (od != null) {
			data = od.data;
			auxData = od.auxData;
			log = od.log;
			summaryData = od.summaryData;
			configuredFields = od.configuredFields;
		}
	}

	public void setData(IDataset data) {
		this.data = data;
	}

	public Serializable[] getAuxData() {
		return auxData;
	}

	public void setAuxData(Serializable... auxData) {
		this.auxData = auxData;
	}

	public OperationLog getLog() {
		return log;
	}

	public void setLog(OperationLog log) {
		this.log = log;
	}

	/**
	 * @return summary data. This is distinct from auxiliary data in that it summarises the
	 * state of the operation to date and is continually overwritten in the file
	 */
	public Serializable[] getSummaryData() {
		return summaryData;
	}

	/**
	 * Set summary data. This is distinct from auxiliary data in that it summarises the
	 * state of the operation to date and is continually overwritten in the file
	 * @param summaryData
	 */
	public void setSummaryData(Serializable... summaryData) {
		this.summaryData = summaryData;
	}

	/**
	 * @return map of fields and their values configured by operation
	 */
	public Map<String, Serializable> getConfiguredFields() {
		return configuredFields;
	}

	/**
	 * Set configured fields
	 * @param configured
	 */
	public void setConfiguredFields(Map<String, Serializable> configured) {
		this.configuredFields = configured;
	}
}
