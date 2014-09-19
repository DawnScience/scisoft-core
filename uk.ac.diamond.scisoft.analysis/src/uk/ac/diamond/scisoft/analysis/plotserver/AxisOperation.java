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
 * Class for sending in a GUIBean to change axis information.
 */
public class AxisOperation implements Serializable {
	
	/**
	 * Enum not used because Corba, allowed values of operationType
	 */
	public final static String CREATE  = "CREATE";
	public final static String RENAMEX = "RENAMEX";
	public final static String RENAMEY = "RENAMEY";
	
	/**
	 * Enum not used because Corba, allowed values of side
	 */
	public final static int TOP     = 1 << 7;
	public final static int BOTTOM  = 1 << 10;
	public final static int LEFT    = 1 << 14;
	public final static int RIGHT   = 1 << 17;


	private String  operationType;
	private String  title;
	private int     side;
	
	public AxisOperation(String operationType, String title) {
		this(operationType, title, -1);
	}
	
	public AxisOperation(String operationType, String title, int side) {
		super();
		this.operationType = operationType;
		this.title = title;
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

	public boolean isYAxis() {
		return side == LEFT || side == RIGHT;
	}
}
