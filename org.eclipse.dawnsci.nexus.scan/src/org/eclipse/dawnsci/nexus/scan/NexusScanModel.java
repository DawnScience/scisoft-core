/*-
 * Copyright © 2020 Diamond Light Source Ltd.
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

package org.eclipse.dawnsci.nexus.scan;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.NexusScanInfo.ScanRole;
import org.eclipse.dawnsci.nexus.builder.NexusMetadataProvider;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;

/**
 * An model describing a scan in terms of nexus.
 * 
 * @author Matthew Dickie
 */
public class NexusScanModel {

	/**
	 * The path of the nexus file to write
	 */
	private String filePath;
	
	/**
	 * The name of the nexus entry to create within the file.
	 */
	private String entryName;

	/**
	 * The nexus devices from which to get the nexus objects, grouped by {@link ScanRole}.
	 */
	private Map<ScanRole, List<INexusDevice<?>>> nexusDevices;

	private INexusDevice<NXcollection> metadataWriter;

	/**
	 * Some information about the scan shape, dimensions and size
	 */
	private NexusScanInfo nexusScanInfo;

	/**
	 * The file paths of nexus template files (YAML files) to apply to the nexus file.
	 */
	private List<String> templateFilePaths;

	/**
	 * list of nexus templates to apply to the nexus file.
	 */
	private List<NexusTemplate> nexusTemplates = Collections.emptyList();

	/**
	 * A list of the dimensions names for the scan, each with the same index in the list
	 * as the index of the scan for. 
	 */
	private List<List<String>> dimensionNamesByIndex;

	/**
	 * A list of metadata providers, used to add metadata fields to the core nexus groups,
	 * i.e. the NXentry, NXinstrument and NXsample groups that are present in all nexus files
	 * rather than depending on the devices in the scan.
	 */
	private List<NexusMetadataProvider> nexusMetadataProviders;
	
	public NexusScanModel() {
		// do nothing
	}
	
	public NexusScanModel(Map<ScanRole, List<INexusDevice<?>>> nexusDevices) {
		this.nexusDevices = nexusDevices;
	}
	
	public String getEntryName() {
		return entryName;
	}
	
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Map<ScanRole, List<INexusDevice<?>>> getNexusDevices() {
		return nexusDevices;
	}
	
	public void setNexusDevices(Map<ScanRole, List<INexusDevice<?>>> nexusDevices) {
		this.nexusDevices = nexusDevices;
	}

	public INexusDevice<NXcollection> getMetadataWriter() {
		return metadataWriter;
	}

	public void setMetadataWriter(INexusDevice<NXcollection> metadataWriter) {
		this.metadataWriter = metadataWriter;
	}

	public NexusScanInfo getNexusScanInfo() {
		return nexusScanInfo;
	}

	public void setNexusScanInfo(NexusScanInfo nexusScanInfo) {
		this.nexusScanInfo = nexusScanInfo;
	}

	public List<String> getTemplateFilePaths() {
		if (templateFilePaths == null) {
			return Collections.emptyList();
		}
		return templateFilePaths;
	}

	public void setTemplateFilePaths(List<String> templateFilePaths) {
		this.templateFilePaths = templateFilePaths;
	}

	public List<List<String>> getDimensionNamesByIndex() {
		return dimensionNamesByIndex;
	}

	public void setDimensionNamesByIndex(List<List<String>> dimensionNamesByIndex) {
		this.dimensionNamesByIndex = dimensionNamesByIndex;
	}

	public List<NexusMetadataProvider> getNexusMetadataProviders() {
		if (nexusMetadataProviders == null) {
			return Collections.emptyList();
		}

		return nexusMetadataProviders;
	}

	public void setNexusMetadataProviders(List<NexusMetadataProvider> nexusMetadataProviders) {
		this.nexusMetadataProviders = nexusMetadataProviders;
	}

	public List<NexusTemplate> getNexusTemplates() {
		return nexusTemplates;
	}

	public void setNexusTemplates(List<NexusTemplate> nexusTemplates) {
		this.nexusTemplates = nexusTemplates;
	}

}
