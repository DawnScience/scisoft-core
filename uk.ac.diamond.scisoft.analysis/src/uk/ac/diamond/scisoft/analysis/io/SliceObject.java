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

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;


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
    
    /**
     * Dataset name required for dimension where 1 is the first.
     */
    private Map<Integer,String> nexusAxes;
    
    /**
     * Name, expression name to a lazy dataset which can be used for an axis.
     */
	private Map<String, IDataset> expressionAxes;

    public SliceObject() {
    	this.nexusAxes = new HashMap<Integer, String>(3);
    }
    
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
		result = prime * result + ((nexusAxes == null) ? 0 : nexusAxes.hashCode());
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
		if (nexusAxes == null) {
			if (other.nexusAxes != null)
				return false;
		} else if (!nexusAxes.equals(other.nexusAxes))
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
    	ret.nexusAxes.putAll(nexusAxes);
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

	public Map<Integer, String> getNexusAxes() {
		return nexusAxes;
	}

	public void setNexusAxes(Map<Integer, String> nexusAxes) {
		this.nexusAxes = nexusAxes;
	}
    
	public void setNexusAxis(int inexusDim, String name) {
		nexusAxes.put(inexusDim, name);
	}

	public String getNexusAxis(int inexusDim) {
		return nexusAxes.get(inexusDim);
	}

	public void putExpressionAxis(String name, IDataset set) {
		if (expressionAxes == null) expressionAxes = new HashMap<String, IDataset>(7);
		expressionAxes.put(name, set);
	}
	
	public IDataset getExpressionAxis(String name) {
		if (expressionAxes == null) return null;
		return expressionAxes.get(name);
	}
  
}
