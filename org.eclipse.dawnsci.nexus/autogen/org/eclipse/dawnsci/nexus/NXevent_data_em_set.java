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


/**
 * Container to hold NXevent_data_em instances of an electron microscope session.
 * An event is a time interval during which the microscope was configured,
 * considered stable, and used for characterization.
 * 
 */
public interface NXevent_data_em_set extends NXobject {

	/**
	 * 
	 * @return  the value.
	 */
	public NXevent_data_em getEvent_data_em();
	
	/**
	 * 
	 * @param event_data_emGroup the event_data_emGroup
	 */
	public void setEvent_data_em(NXevent_data_em event_data_emGroup);

	/**
	 * Get a NXevent_data_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXevent_data_em for that node.
	 */
	public NXevent_data_em getEvent_data_em(String name);
	
	/**
	 * Set a NXevent_data_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param event_data_em the value to set
	 */
	public void setEvent_data_em(String name, NXevent_data_em event_data_em);
	
	/**
	 * Get all NXevent_data_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXevent_data_em for that node.
	 */
	public Map<String, NXevent_data_em> getAllEvent_data_em();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param event_data_em the child nodes to add 
	 */
	
	public void setAllEvent_data_em(Map<String, NXevent_data_em> event_data_em);
	

}
