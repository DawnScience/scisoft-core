/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.metadata;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MetadataType;

public class MapMetadata implements MetadataType {

	private static final long serialVersionUID = 613515064975171120L;

	private final Map<String, Serializable> metadata = new HashMap<>();
	
	public MapMetadata(Map<String, Serializable> metadata) {
		if (metadata == null)
			return;
		for (Entry<String, Serializable> entry : metadata.entrySet()) {
			if (entry.getValue() instanceof IDataset) {
				// SerializationUtils.clone gives a ClassNotFoundException for IDataset instances...
				// also this has better performance anyway
				this.metadata.put(entry.getKey(), ((IDataset) entry.getValue()).clone());
			} else {
				try {
					this.metadata.put(entry.getKey(), SerializationUtils.clone(entry.getValue()));
				} catch (Exception e) {
					// ignore and go to the next entry
				}
			}
		}
	}
	
	public MapMetadata() {
		// no need to do anything here...
	}

	@Override
	public MetadataType clone() {
		return new MapMetadata(metadata);
	}

	public Collection<String> getMetaNames() {
		return metadata.keySet();
	}

	public Serializable getMetaValue(String metaName) {
		return metadata.get(metaName);
	}

	public void setMetadata(Map<String, Serializable> metadata) {
		this.metadata.clear();
		this.metadata.putAll(metadata);
	}
}
