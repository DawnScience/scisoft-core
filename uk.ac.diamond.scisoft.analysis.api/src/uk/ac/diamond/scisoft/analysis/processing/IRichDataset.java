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
import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

/**
 * This dataset holds several bits of information about scientific data.
 * 
 * The class contains methods for getting/setting the data components and
 * operating on the rich dataset.
 */
public interface IRichDataset {

	public ILazyDataset getData();

	public void setData(ILazyDataset data);

	public List<IDataset> getAxes();

	public void setAxes(List<IDataset> axes);
	
	public List<IROI> getRegions();

	public void setRegions(List<IROI> rois);

	public ILazyDataset getMask();

	public void setMask(ILazyDataset mask);

	public IMetaData getMeta()  throws Exception;

	public void setMeta(IMetaData meta);

}