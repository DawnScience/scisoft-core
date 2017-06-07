/*-
 * Copyright 2016, 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import javax.measure.Unit;

import org.eclipse.dawnsci.analysis.api.metadata.UnitMetadata;
import org.eclipse.january.metadata.MetadataType;

public class UnitMetadataImpl implements UnitMetadata {

	private Unit<?> unit;
	
	public UnitMetadataImpl(Unit<?> unit) {
		this.unit = unit;
	}
	
	@Override
	public MetadataType clone() {
		return new UnitMetadataImpl(unit);
	}

	@Override
	public Unit<?> getUnit() {
		return unit;
	}
	
	@Override
	public String toString(){
		return unit.toString();
	}

}
