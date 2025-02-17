/*-
 *******************************************************************************
 * Copyright (c) 2015 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Dickie - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.nexus.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.nexus.NXcollection;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder;
import org.eclipse.dawnsci.nexus.builder.NexusEntryModification;
import org.eclipse.dawnsci.nexus.builder.NexusMetadataProvider;
import org.eclipse.dawnsci.nexus.builder.NexusMetadataProvider.MetadataEntry;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.data.NexusDataBuilder;
import org.eclipse.dawnsci.nexus.builder.data.impl.DefaultNexusDataBuilder;
import org.eclipse.dawnsci.nexus.validation.NexusValidationService;
import org.eclipse.dawnsci.nexus.validation.ValidationReport;

import uk.ac.diamond.osgi.services.ServiceProvider;

/**
 * Default implementation of {@link NexusEntryBuilder}
 */
public class DefaultNexusEntryBuilder implements NexusEntryBuilder {

	private final String entryName;
	
	private final NXentry nxEntry;

	private NXinstrument nxInstrument = null;

	private NXsample nxSample = null;

	private List<NXobject> defaultGroups = null;

	/**
	 * Creates a new {@link DefaultNexusEntryBuilder}. This constructor should only be called
	 * by {@link DefaultNexusFileBuilder}.
	 * @param entryName name of entry
	 * @param nxEntry entry to wrap
	 */
	protected DefaultNexusEntryBuilder(String entryName,
			final NXentry nxEntry) {
		this.nxEntry = nxEntry;
		this.entryName = entryName;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#add(org.eclipse.dawnsci.nexus.builder.NexusObjectProvider)
	 */
	@Override
	public <N extends NXobject> N add(NexusObjectProvider<N> nexusObjectProvider) throws NexusException {
		final N nexusObject = nexusObjectProvider.getNexusObject();
		addGroupToNexusTree(nexusObjectProvider, nexusObject);

		return nexusObject;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#getNxEntry()
	 */
	@Override
	public NXentry getNXentry() {
		return nxEntry;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#getEntryName()
	 */
	@Override
	public String getEntryName() {
		return entryName;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#createDefaultData()
	 */
	@Override
	public NexusDataBuilder createDefaultData() {
		final NXdata nxData = NexusNodeFactory.createNXdata();
		nxEntry.setData(nxData);
		return new DefaultNexusDataBuilder(this, nxData);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#newData(java.lang.String)
	 */
	@Override
	public NexusDataBuilder newData(final String name) {
		final NXdata nxData = NexusNodeFactory.createNXdata();
		nxEntry.setData(name, nxData);
		return new DefaultNexusDataBuilder(this, nxData);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#add(java.util.Collection)
	 */
	@Override
	public List<NXobject> addAll(Collection<? extends NexusObjectProvider<?>> nexusObjectProviders) throws NexusException {
		final List<NXobject> nexusObjects = new ArrayList<>(nexusObjectProviders.size());
		for (final NexusObjectProvider<?> nexusObjectProvider : nexusObjectProviders) {
			final NXobject nexusObject = add(nexusObjectProvider);
			nexusObjects.add(nexusObject);
		}

		return nexusObjects;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#modifyEntry(org.eclipse.dawnsci.nexus.builder.CustomNexusEntryModification)
	 */
	@Override
	public void modifyEntry(CustomNexusEntryModification modification) throws NexusException {
		modification.modifyEntry(nxEntry);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#addMetadata(org.eclipse.dawnsci.nexus.builder.NexusMetadataProvider)
	 */
	@Override
	public void addMetadata(NexusMetadataProvider metadataProvider) throws NexusException {
		final NexusBaseClass category = metadataProvider.getCategory();
		final NXobject group = category == null ? nxEntry : findGroupForCategory(category);
		
		final Iterator<MetadataEntry> metadataEntryIterator = metadataProvider.getMetadataEntries();
		while (metadataEntryIterator.hasNext()) {
			final MetadataEntry entry = metadataEntryIterator.next();
			group.setField(entry.getName(), entry.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#modifyEntry(org.eclipse.dawnsci.nexus.builder.NexusEntryModification)
	 */
	@Override
	public void modifyEntry(NexusEntryModification modification) throws NexusException {
		if (modification instanceof NexusObjectProvider) {
			add((NexusObjectProvider<?>) modification);
		} else if (modification instanceof NexusMetadataProvider metadata) {
			addMetadata(metadata);
		} else if (modification instanceof CustomNexusEntryModification customModification) {
			modifyEntry(customModification);
		} else {
			throw new IllegalArgumentException("Unknown modification type: " + modification.getClass());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#modifyEntry(java.util.Collection)
	 */
	@Override
	public void modifyEntry(
			Collection<NexusEntryModification> modifications) throws NexusException {
		for (final NexusEntryModification modification : modifications) {
			modifyEntry(modification);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#setInstrumentName(java.lang.String)
	 */
	@Override
	public void setInstrumentName(String instrumentName) {
		nxInstrument.setNameScalar(instrumentName);
	}

	@Override
	public void setDefaultDataGroupName(String dataGroupName) throws NexusException {
		if (!nxEntry.getAllData().keySet().contains(dataGroupName)) {
			throw new NexusException("No such NXdata group: " + dataGroupName);
		}
		
		nxEntry.setAttributeDefault(dataGroupName);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#getDataNode(java.lang.String)
	 */
	@Override
	public DataNode getDataNode(String relativePath) throws NexusException {
		final NodeLink nodeLink = nxEntry.findNodeLink(relativePath);
		if (nodeLink == null) {
			throw new NexusException("Cannot find expected data node within the entry with relative path: " + relativePath);
		}
		if (!nodeLink.isDestinationData()) {
			throw new NexusException("Node found was not a data node, relative path within the entry: " + relativePath);
		}

		return (DataNode) nodeLink.getDestination();
	}

	/**
	 * Adds the default groups for the entry. Subclasses may override as appropriate.
	 */
	@Override
	public void addDefaultGroups() {
		// TODO is this correct for all nexus trees we might want to create?
		// how do we configure it? (or just let subclasses override if they want?)
		defaultGroups = new ArrayList<>();
		defaultGroups.add(nxEntry);

		nxInstrument = NexusNodeFactory.createNXinstrument();
		defaultGroups.add(nxInstrument);
		nxEntry.setInstrument(nxInstrument);

		nxSample = NexusNodeFactory.createNXsample();
		defaultGroups.add(nxSample);
		nxEntry.setSample(nxSample);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.builder.NexusEntryBuilder#validate()
	 */
	@Override
	public ValidationReport validate() throws NexusException {
		return ServiceProvider.getService(NexusValidationService.class).validateEntry(nxEntry);
	}
	
	/**
	 * Adds the new nexus object to the first skeleton class instance that it
	 * can be added to, unless category is specified, in which case it is added to the first
	 * element of that category that it can be added to.
	 * A special case is where the nexus object is of base class {@link NexusBaseClass#NX_SAMPLE},
	 * in which case this replaces the existing NXsample base class in the nexus tree.
	 * @param nexusObjectProvider
	 * @param nexusObject
	 * @throws NexusException
	 */
	protected <N extends NXobject> void addGroupToNexusTree(NexusObjectProvider<N> nexusObjectProvider, N nexusObject) throws NexusException {
		if (defaultGroups == null) {
			throw new IllegalStateException("There are no groups to add this element to. defaultGroups() must be invoked on this method before child groups can be added.");
		}
		
		if (defaultGroups.stream().map(NXobject::getNexusBaseClass).anyMatch(x -> x == nexusObject.getNexusBaseClass())) {
			// special case for NXEntry, NXSample, NXInstrument: merge incoming with existing node
			mergeIntoGroup(findGroupForCategory(nexusObject.getNexusBaseClass()), nexusObjectProvider, nexusObject);
			
		} else {
			// normal case
			addChildToGroup(nexusObjectProvider, nexusObject);
		}
	}
	
	private <N extends NXobject> void addChildToGroup(NexusObjectProvider<N> nexusObjectProvider, N nexusObject) throws NexusException {
		final String name = nexusObjectProvider.getName();
		final NexusBaseClass category = nexusObjectProvider.getCategory();

		// find the parent group
		NXobject parentGroup = null;
		if (category != null) {
			// if a category is specified, the parent group is the first group for this category
			parentGroup = findGroupForCategory(category);
		} else {
			// otherwise the parent group is the first group we can add this type of object to
			for (final NXobject group : defaultGroups) {
				if (group.canAddChild(nexusObject)) {
					parentGroup = group;
					break;
				}
			}
			if (parentGroup == null) {
				throw new NexusException("Cannot find a parent group that accepts a " + nexusObject.getNexusBaseClass());
			}
		}

		// if a collection name is specified, get the parent collection - creating it if necessary
		String collectionName = nexusObjectProvider.getCollectionName();
		if (collectionName != null) {
			parentGroup = getCollection(parentGroup, collectionName);
		}

		parentGroup.addGroupNode(name, nexusObject);
	}

	private <N extends NXobject> void mergeIntoGroup(NXobject group, NexusObjectProvider<N> nexusObjectProvider,N nexusObject) throws NexusException {
		checkNodeAndAttributeNames(nexusObjectProvider.getName(), group, nexusObject);

		for (var attrName : nexusObject.getAttributeNames()) {
			group.addAttribute(nexusObject.getAttribute(attrName));
		}

		for (var nodeNames : nexusObject.getNames()) {
			group.addNode(nodeNames, nexusObject.getNode(nodeNames));
		}
	}
	
	private <N extends NXobject> void checkNodeAndAttributeNames(String name, NXobject group,
			N nexusObject) throws NexusException {
		final Set<String> sharedNodeNames = new HashSet<>(group.getNames());
		sharedNodeNames.retainAll(nexusObject.getNames());
		if (!sharedNodeNames.isEmpty()) {
			throw new NexusException("Cannot merge %s into %s as it contains existing node(s): %s"
					.formatted(name, group, sharedNodeNames));
		}

		final Set<String> sharedAttrNames = new HashSet<>(group.getAttributeNames());
		sharedAttrNames.retainAll(nexusObject.getAttributeNames());
		sharedAttrNames.remove(NexusConstants.NXCLASS);
		if (!sharedAttrNames.isEmpty()) {
			throw new NexusException("Cannot merge %s into %s as it contains existing attribute(s): %s"
					.formatted(name, group, sharedAttrNames));
		}
	}
		
	private NXcollection getCollection(NXobject group, String collectionName) {
		final GroupNode collectionGroup = group.getGroupNode(collectionName);
		if (collectionGroup == null) {
			final NXcollection collection = NexusNodeFactory.createNXcollection();
			group.addGroupNode(collectionName, collection);
			return collection;
		} else if (collectionGroup instanceof NXcollection collection) {
			return collection;
		} else {
			throw new IllegalArgumentException("Cannot add collection " + collectionName +
					". A child group with that name already exists");
		}
	}

	private NXobject findGroupForCategory(NexusBaseClass category) throws NexusException {
		if (category == NexusBaseClass.NX_ENTRY) {
			return nxEntry;
		}

		for (final NXobject group : defaultGroups) {
			if (category == group.getNexusBaseClass()) {
				return group;
			}
		}

		throw new NexusException("No group found for category " + category);
	}

}
