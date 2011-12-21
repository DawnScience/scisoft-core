/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
