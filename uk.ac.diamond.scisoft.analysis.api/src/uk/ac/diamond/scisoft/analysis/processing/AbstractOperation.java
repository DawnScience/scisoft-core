/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.processing;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.IMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.MetadataType;
import uk.ac.diamond.scisoft.analysis.metadata.OriginMetadata;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;


public abstract class AbstractOperation<T extends IOperationModel, D extends OperationData> implements IOperation<T, D> {

	protected T model;
	
    private String name;
    private String description;
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getDescription() {
		if (description==null) return getId();
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AbstractOperation other = (AbstractOperation) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AbstractOperation [name=" + name + "]";
	}
	
	@Override
	public T getModel() {
		return model;
	}
	@Override
	public void setModel(T model) {
		this.model = model;
	}
	
	public ILazyDataset[] getFirstAxes(IDataset slice) {
		List<AxesMetadata> metaList = null;
		
		try {
			metaList = slice.getMetadata(AxesMetadata.class);
			if (metaList == null || metaList.isEmpty()) return null;
		} catch (Exception e) {
			return null;
		}
		
		AxesMetadata am = metaList.get(0);
		
		return am.getAxes();
	}
	
	public ILazyDataset getFirstMask(IDataset slice) {
		
		List<MaskMetadata> metaList = null;
		
		try {
			metaList = slice.getMetadata(MaskMetadata.class);
			if (metaList == null || metaList.isEmpty()) return null;
		} catch (Exception e) {
			return null;
		}
		
		MaskMetadata mm = metaList.get(0);
		
		return mm.getMask();
	}
	
	public IDiffractionMetadata getFirstDiffractionMetadata(IDataset slice) {
		
		List<IMetadata> metaList;
		
		try {
			metaList = slice.getMetadata(IMetadata.class);
			if (metaList == null || metaList.isEmpty()) return null;
		} catch (Exception e) {
			return null;
		}
		
		for (IMetadata meta : metaList) if (meta instanceof IDiffractionMetadata) return (IDiffractionMetadata)meta;
		
		return null;
	}
	
	public int[] getOriginalDataDimensions(IDataset slice) {
		
		List<OriginMetadata> metaList = null;
		
		try {
			metaList = slice.getMetadata(OriginMetadata.class);
			if (metaList == null || metaList.isEmpty()) return null;
		} catch (Exception e) {
			return null;
		}
		
		OriginMetadata om = metaList.get(0);
		
		return om.getDataDimensions();
		
	}
	
	public void copyMetadata(IDataset original, IDataset out) {
		try {
			List<MetadataType> metadata = original.getMetadata(null);
			
			for (MetadataType m : metadata) {
				out.addMetadata(m);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
