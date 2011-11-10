/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;


/**
 * Holds data needed to get a slice from a nexus file. Since 
 * quite a bit of information is needed, we use an object.
 */
public class SliceObject {

	private String path;
	private String name;
	private int[]  fullShape;  // Not always needed
	private int[]  slicedShape;// The final shape expected
    private int[]  sliceStart; 
    private int[]  sliceStop; 
    private int[]  sliceStep;
    private int    x=-1;
    private int    y=-1;
    private boolean isRange;
    
	public void clear() {
		path=null;
		name=null;
		slicedShape=null;// The final shape expected
	    sliceStart=null; 
	    sliceStop=null; 
	    sliceStep=null;
	}
    
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((axes == null) ? 0 : axes.hashCode());
		result = prime * result + Arrays.hashCode(fullShape);
		result = prime * result + (isRange ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((shapeMessage == null) ? 0 : shapeMessage.hashCode());
		result = prime * result + Arrays.hashCode(sliceStart);
		result = prime * result + Arrays.hashCode(sliceStep);
		result = prime * result + Arrays.hashCode(sliceStop);
		result = prime * result + Arrays.hashCode(slicedShape);
		result = prime * result + x;
		result = prime * result + y;
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
		SliceObject other = (SliceObject) obj;
		if (axes == null) {
			if (other.axes != null)
				return false;
		} else if (!axes.equals(other.axes))
			return false;
		if (!Arrays.equals(fullShape, other.fullShape))
			return false;
		if (isRange != other.isRange)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (shapeMessage == null) {
			if (other.shapeMessage != null)
				return false;
		} else if (!shapeMessage.equals(other.shapeMessage))
			return false;
		if (!Arrays.equals(sliceStart, other.sliceStart))
			return false;
		if (!Arrays.equals(sliceStep, other.sliceStep))
			return false;
		if (!Arrays.equals(sliceStop, other.sliceStop))
			return false;
		if (!Arrays.equals(slicedShape, other.slicedShape))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getSliceStart() {
		return sliceStart;
	}
	public void setSliceStart(int[] sliceStart) {
		this.sliceStart = sliceStart;
	}
	public int[] getSliceStop() {
		return sliceStop;
	}
	public void setSliceStop(int[] sliceStop) {
		this.sliceStop = sliceStop;
	}
	public int[] getSliceStep() {
		return sliceStep;
	}
	public void setSliceStep(int[] sliceStep) {
		this.sliceStep = sliceStep;
	}
	
	
	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

    @Override
	public SliceObject clone() {
    	final SliceObject ret = new SliceObject();
    	ret.fullShape = fullShape;
    	ret.name = name;
    	ret.path = path;
    	ret.sliceStart = sliceStart;
    	ret.sliceStop  = sliceStop;
    	ret.sliceStep  = sliceStep;
		return ret;
    }
    
    // These methods intentionally not part of the slice API as
    // not really needed by the loader.
    
    private List<AbstractDataset> axes;
    private String                shapeMessage;

	public List<AbstractDataset> getAxes() {
		return axes;
	}
	public void setAxes(List<AbstractDataset> axes) {
		this.axes = axes;
	}
	public String getShapeMessage() {
		return shapeMessage;
	}
	public void setShapeMessage(String shapeMessage) {
		this.shapeMessage = shapeMessage;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isAxis(int i) {
		return x==i||y==i;
	}
	public int[] getSlicedShape() {
		return slicedShape;
	}
	public void setSlicedShape(int[] slicedShape) {
		this.slicedShape=slicedShape;
	}
	public boolean isRange() {
		return isRange;
	}
	public void setRange(boolean isRange) {
		this.isRange = isRange;
	}

	public int[] getFullShape() {
		return fullShape;
	}

	public void setFullShape(int[] fullShape) {
		this.fullShape = fullShape;
	}
    
    
}
