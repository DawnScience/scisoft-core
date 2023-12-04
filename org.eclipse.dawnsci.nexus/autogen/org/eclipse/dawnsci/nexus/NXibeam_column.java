/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Container for components of a focused-ion-beam (FIB) system.
 * FIB capabilities turn especially scanning electron microscopes
 * into specimen preparation labs. FIB is a material preparation
 * technique whereby portions of the sample are illuminated with a
 * focused ion beam with controlled intensity intense enough and with
 * sufficient ion momentum to remove material in a controllable manner.
 * The fact that an electron microscope with FIB capabilities has needs a
 * second gun with own relevant control circuits, focusing lenses, and
 * other components, warrants an own base class to group these components
 * and distinguish them from the lenses and components for creating and
 * shaping the electron beam.
 * For more details about the relevant physics and application examples
 * consult the literature, for example:
 * * `L. A. Giannuzzi et al. <https://doi.org/10.1007/b101190>`_
 * * `E. I. Preiß et al. <https://link.springer.com/content/pdf/10.1557/s43578-020-00045-w.pdf>`_
 * * `J. F. Ziegler et al. <https://www.sciencedirect.com/science/article/pii/S0168583X10001862>`_
 * * `J. Lili <https://www.osti.gov/servlets/purl/924801>`_
 * 
 */
public interface NXibeam_column extends NXobject {

	/**
	 * 
	 * @return  the value.
	 */
	public NXmanufacturer getManufacturer();
	
	/**
	 * 
	 * @param manufacturerGroup the manufacturerGroup
	 */
	public void setManufacturer(NXmanufacturer manufacturerGroup);

	/**
	 * Get a NXmanufacturer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmanufacturer for that node.
	 */
	public NXmanufacturer getManufacturer(String name);
	
	/**
	 * Set a NXmanufacturer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param manufacturer the value to set
	 */
	public void setManufacturer(String name, NXmanufacturer manufacturer);
	
	/**
	 * Get all NXmanufacturer nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmanufacturer for that node.
	 */
	public Map<String, NXmanufacturer> getAllManufacturer();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param manufacturer the child nodes to add 
	 */
	
	public void setAllManufacturer(Map<String, NXmanufacturer> manufacturer);
	

	/**
	 * The source which creates the ion beam.
	 * 
	 * @return  the value.
	 */
	public NXsource getIon_gun();
	
	/**
	 * The source which creates the ion beam.
	 * 
	 * @param ion_gunGroup the ion_gunGroup
	 */
	public void setIon_gun(NXsource ion_gunGroup);
	// Unprocessed group: probe
	// Unprocessed group: 

	/**
	 * 
	 * @return  the value.
	 */
	public NXaperture_em getAperture_em();
	
	/**
	 * 
	 * @param aperture_emGroup the aperture_emGroup
	 */
	public void setAperture_em(NXaperture_em aperture_emGroup);

	/**
	 * Get a NXaperture_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaperture_em for that node.
	 */
	public NXaperture_em getAperture_em(String name);
	
	/**
	 * Set a NXaperture_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param aperture_em the value to set
	 */
	public void setAperture_em(String name, NXaperture_em aperture_em);
	
	/**
	 * Get all NXaperture_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXaperture_em for that node.
	 */
	public Map<String, NXaperture_em> getAllAperture_em();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param aperture_em the child nodes to add 
	 */
	
	public void setAllAperture_em(Map<String, NXaperture_em> aperture_em);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXlens_em getLens_em();
	
	/**
	 * 
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public NXlens_em getLens_em(String name);
	
	/**
	 * Set a NXlens_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param lens_em the value to set
	 */
	public void setLens_em(String name, NXlens_em lens_em);
	
	/**
	 * Get all NXlens_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param lens_em the child nodes to add 
	 */
	
	public void setAllLens_em(Map<String, NXlens_em> lens_em);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXsensor getSensor();
	
	/**
	 * 
	 * @param sensorGroup the sensorGroup
	 */
	public void setSensor(NXsensor sensorGroup);

	/**
	 * Get a NXsensor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public NXsensor getSensor(String name);
	
	/**
	 * Set a NXsensor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param sensor the value to set
	 */
	public void setSensor(String name, NXsensor sensor);
	
	/**
	 * Get all NXsensor nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public Map<String, NXsensor> getAllSensor();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param sensor the child nodes to add 
	 */
	
	public void setAllSensor(Map<String, NXsensor> sensor);
	

	/**
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * NXtransformations should be used to specify the location or position
	 * at which details about the ion beam are probed.
	 * 
	 * @return  the value.
	 */
	public NXbeam getBeam();
	
	/**
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * NXtransformations should be used to specify the location or position
	 * at which details about the ion beam are probed.
	 * 
	 * @param beamGroup the beamGroup
	 */
	public void setBeam(NXbeam beamGroup);

	/**
	 * Get a NXbeam node by name:
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * NXtransformations should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public NXbeam getBeam(String name);
	
	/**
	 * Set a NXbeam node by name:
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * NXtransformations should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param beam the value to set
	 */
	public void setBeam(String name, NXbeam beam);
	
	/**
	 * Get all NXbeam nodes:
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * NXtransformations should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXbeam for that node.
	 */
	public Map<String, NXbeam> getAllBeam();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Individual characterization results for the position, shape,
	 * and characteristics of the ion beam.
	 * NXtransformations should be used to specify the location or position
	 * at which details about the ion beam are probed.</li>
	 * </ul>
	 * 
	 * @param beam the child nodes to add 
	 */
	
	public void setAllBeam(Map<String, NXbeam> beam);
	

}
