/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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
		gridRowPos = new Integer(gridRow);
		gridColumnPos = new Integer(gridColumn);
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
