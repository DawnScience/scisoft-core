/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;

/**
 * A simple class more or less a placeholder that stores persistently over sessions
 * different entries in the ImageGrid so information can be stored on the server
 * and then retrieved by the client again for the next session
 */
public class GridImageEntry implements Serializable {

	private String filename;
	private Integer gridRowPos;
	private Integer gridColumnPos;
	
	/**
	 * Create a new GridImageEntry
	 * @param filename absolute file image name
	 * @param gridRow grid row position 
	 * @param gridColumn grid column position
	 */
	public GridImageEntry(String filename,int gridRow, int gridColumn) {
		this.filename = filename;
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

}
