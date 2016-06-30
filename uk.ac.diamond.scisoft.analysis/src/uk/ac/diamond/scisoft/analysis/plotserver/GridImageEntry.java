/*
 * Copyright (c) 2012-2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

/**
 * A simple class more or less a placeholder that stores persistently over sessions
 * different entries in the ImageGrid so information can be stored on the server
 * and then retrieved by the client again for the next session
 */
public class GridImageEntry implements Serializable {

	private String filename;
	private Integer gridRowPos;
	private Integer gridColumnPos;
	private IDataset dataset;
	
	/**
	 * Create a new GridImageEntry
	 * 
	 * @param filename
	 *            absolute file image name
	 * @param gridRow
	 *            grid row position
	 * @param gridColumn
	 *            grid column position
	 */
	public GridImageEntry(String filename,int gridRow, int gridColumn) {
		this.filename = filename;
		gridRowPos = Integer.valueOf(gridRow);
		gridColumnPos = Integer.valueOf(gridColumn);
	}

	/**
	 * Create a new GridImageEntry
	 * 
	 * @param filename
	 *            absolute file image name
	 * @param dataset
	 *            for a stack of images in a nxs file, this is necessary so there is no need to create temp copy of each
	 *            file and then use the filename
	 * @param gridRow
	 *            grid row position
	 * @param gridColumn
	 *            grid column position
	 */
	public GridImageEntry(String filename, IDataset dataset, int gridRow, int gridColumn) {
		this.filename = filename;
		this.dataset = dataset;
		gridRowPos = Integer.valueOf(gridRow);
		gridColumnPos = Integer.valueOf(gridColumn);
	}

	public String getFilename() {
		return filename;
	}

	public Integer getGridRowPos() {
		return gridRowPos;
	}

	public Integer getGridColumnPos() {
		return gridColumnPos;
	}

	public IDataset getData() {
		return dataset;
	}
}
