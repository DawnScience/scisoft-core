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
import org.eclipse.january.dataset.Dataset;

/**
 * A set of activities that occurred to a physical entity prior/during experiment.
 * Ideally, a full report of the previous operations (or links to a chain of operations).
 * Alternatively, notes allow for additional descriptors in any format.
 *
 */
public interface NXhistory extends NXobject {

	public static final String NX_IDENTIFIERNAME = "identifiername";
	/**
	 * Any activity that was performed on the physical entity prior or during the experiment.
	 *
	 * @return  the value.
	 */
	public NXactivity getActivity();

	/**
	 * Any activity that was performed on the physical entity prior or during the experiment.
	 *
	 * @param activityGroup the activityGroup
	 */
	public void setActivity(NXactivity activityGroup);

	/**
	 * Get a NXactivity node by name:
	 * <ul>
	 * <li>
	 * Any activity that was performed on the physical entity prior or during the experiment.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXactivity for that node.
	 */
	public NXactivity getActivity(String name);

	/**
	 * Set a NXactivity node by name:
	 * <ul>
	 * <li>
	 * Any activity that was performed on the physical entity prior or during the experiment.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param activity the value to set
	 */
	public void setActivity(String name, NXactivity activity);

	/**
	 * Get all NXactivity nodes:
	 * <ul>
	 * <li>
	 * Any activity that was performed on the physical entity prior or during the experiment.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXactivity for that node.
	 */
	public Map<String, NXactivity> getAllActivity();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Any activity that was performed on the physical entity prior or during the experiment.</li>
	 * </ul>
	 *
	 * @param activity the child nodes to add
	 */

	public void setAllActivity(Map<String, NXactivity> activity);


	/**
	 * An ID or reference to the location or a unique (globally
	 * persistent) identifier of e.g. another file which gives
	 * as many as possible details of the history event.
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifiername();

	/**
	 * An ID or reference to the location or a unique (globally
	 * persistent) identifier of e.g. another file which gives
	 * as many as possible details of the history event.
	 *
	 * @param identifiernameDataset the identifiernameDataset
	 */
	public DataNode setIdentifiername(IDataset identifiernameDataset);

	/**
	 * An ID or reference to the location or a unique (globally
	 * persistent) identifier of e.g. another file which gives
	 * as many as possible details of the history event.
	 *
	 * @return  the value.
	 */
	public String getIdentifiernameScalar();

	/**
	 * An ID or reference to the location or a unique (globally
	 * persistent) identifier of e.g. another file which gives
	 * as many as possible details of the history event.
	 *
	 * @param identifiername the identifiername
	 */
	public DataNode setIdentifiernameScalar(String identifiernameValue);

	/**
	 * A descriptor to keep track of the treatment of the physical entity before or during the
	 * experiment (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * This should only be used in case that there is no rigorous description
	 * using the base classes above. This group can also be used to pull in any activities
	 * that are not well described by an existing base class definition.
	 * Any number of instances of NXnote are allowed for describing extra details of
	 * this activity.
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 * A descriptor to keep track of the treatment of the physical entity before or during the
	 * experiment (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * This should only be used in case that there is no rigorous description
	 * using the base classes above. This group can also be used to pull in any activities
	 * that are not well described by an existing base class definition.
	 * Any number of instances of NXnote are allowed for describing extra details of
	 * this activity.
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * A descriptor to keep track of the treatment of the physical entity before or during the
	 * experiment (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * This should only be used in case that there is no rigorous description
	 * using the base classes above. This group can also be used to pull in any activities
	 * that are not well described by an existing base class definition.
	 * Any number of instances of NXnote are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXnote for that node.
	 */
	public NXnote getNote(String name);

	/**
	 * Set a NXnote node by name:
	 * <ul>
	 * <li>
	 * A descriptor to keep track of the treatment of the physical entity before or during the
	 * experiment (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * This should only be used in case that there is no rigorous description
	 * using the base classes above. This group can also be used to pull in any activities
	 * that are not well described by an existing base class definition.
	 * Any number of instances of NXnote are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param note the value to set
	 */
	public void setNote(String name, NXnote note);

	/**
	 * Get all NXnote nodes:
	 * <ul>
	 * <li>
	 * A descriptor to keep track of the treatment of the physical entity before or during the
	 * experiment (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * This should only be used in case that there is no rigorous description
	 * using the base classes above. This group can also be used to pull in any activities
	 * that are not well described by an existing base class definition.
	 * Any number of instances of NXnote are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A descriptor to keep track of the treatment of the physical entity before or during the
	 * experiment (NXnote allows to add pictures, audio, movies). Alternatively, a
	 * reference to the location or a unique identifier or other metadata file. In the
	 * case these are not available, free-text description.
	 * This should only be used in case that there is no rigorous description
	 * using the base classes above. This group can also be used to pull in any activities
	 * that are not well described by an existing base class definition.
	 * Any number of instances of NXnote are allowed for describing extra details of
	 * this activity.</li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


}
