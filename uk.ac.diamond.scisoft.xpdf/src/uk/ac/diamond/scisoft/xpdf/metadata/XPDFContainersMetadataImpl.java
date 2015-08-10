/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import java.util.LinkedList;

import org.eclipse.dawnsci.analysis.api.metadata.XPDFContainerMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;

public class XPDFContainersMetadataImpl implements XPDFContainerMetadata {

	private LinkedList<XPDFTargetComponentMetadata> containerList;
		
	public XPDFContainersMetadataImpl() {
		containerList = new LinkedList<XPDFTargetComponentMetadata>();
	}

	public XPDFContainersMetadataImpl(XPDFContainersMetadataImpl inContainers) {
		containerList = new LinkedList<XPDFTargetComponentMetadata>();
		// Deep copy of metadata
		for (XPDFTargetComponentMetadata iContainer : inContainers.containerList) {
			this.addContainer((XPDFTargetComponentMetadata) iContainer.clone()); 
		}
	}
	
	@Override
	public MetadataType clone() {
		return new XPDFContainersMetadataImpl(this);
	}

	@Override
	public void addContainer(XPDFTargetComponentMetadata incomp) {
		containerList.add(incomp);
	}

	@Override
	public MetadataType getContainer(int i) {
		if (i < this.size()) {
			return containerList.get(i);
		}
		return null;
	}

	@Override
	public int size() {
		return containerList.size();
	}

}
