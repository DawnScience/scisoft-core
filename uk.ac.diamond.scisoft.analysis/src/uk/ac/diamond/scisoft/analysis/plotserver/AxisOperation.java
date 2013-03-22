/*-
 * Copyright 2013 Diamond Light Source Ltd.
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
 * Class for sending in a GUIBean to change axis information.
 */
public class AxisOperation implements Serializable {
	
	/**
	 * Enum not used because Corba, allowed values of operationType
	 */
	public final static String CREATE  = "CREATE";
	public final static String DELETE  = "CREATE";
	public final static String ACTIVEX = "ACTIVEX";
	public final static String ACTIVEY = "ACTIVEY";
	
	/**
	 * Enum not used because Corba, allowed values of side
	 */
	public final static int TOP     = 1 << 7;
	public final static int BOTTOM  = 1 << 10;
	public final static int LEFT    = 1 << 14;
	public final static int RIGHT   = 1 << 17;


	private String  operationType;
	private String  title;
	private boolean isYAxis;
	private int     side;
	
	public AxisOperation(String operationType, String title) {
		this(operationType, title, false, -1);
	}
	
	public AxisOperation(String operationType, String title, boolean isYAxis, int side) {
		super();
		this.operationType = operationType;
		this.title = title;
		this.isYAxis = isYAxis;
		this.side = side;
	}
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isYAxis() {
		return isYAxis;
	}
	public void setYAxis(boolean isYAxis) {
		this.isYAxis = isYAxis;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int side) {
		this.side = side;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isYAxis ? 1231 : 1237);
		result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
		result = prime * result + side;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AxisOperation other = (AxisOperation) obj;
		if (isYAxis != other.isYAxis)
			return false;
		if (operationType == null) {
			if (other.operationType != null)
				return false;
		} else if (!operationType.equals(other.operationType))
			return false;
		if (side != other.side)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
