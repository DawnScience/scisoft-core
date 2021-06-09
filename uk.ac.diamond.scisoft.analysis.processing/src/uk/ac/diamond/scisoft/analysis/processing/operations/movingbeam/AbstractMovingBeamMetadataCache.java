/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.vecmath.Matrix4d;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

/**
 * The cache stores information obtained from the calibration and the scan data as well as the IDiffractionMetadata loaded from the specified 
 * calibration.
 */
public abstract class AbstractMovingBeamMetadataCache {
	protected List<IDiffractionMetadata> metadata = new ArrayList<>();
	protected Double calibrationX;
	protected Double calibrationY;
	protected Double calibrationZ;
	protected ILazyDataset cachedSourceXPositions;
	protected ILazyDataset cachedSourceYPositions;
	protected boolean isLive = false;
	protected boolean usingSetPositions = false; 
	protected Matrix4d transform = new Matrix4d();
	protected String[] sourceAxesNames;
	protected AxesMetadata axes;
	

	
	public abstract ReferencePosition2DMetadata getPositionMeta(SliceFromSeriesMetadata ssm) throws DatasetException;
	
	/**
	 * get the IDiffractionMetadata metadata for the current image
	 * 
	 * @param ssm
	 * @return
	 * @throws DatasetException
	 */
	public abstract IDiffractionMetadata getDiffractionMetadata(SliceFromSeriesMetadata ssm) throws DatasetException;
	
	
	
	/**
	 * Get target attribute from a specified Node in a Tree. If the node is not found, or the target attribute is missing null is returned.   
	 * @param NodePath
	 * @param tree
	 * @return string representation of the target attribute (or null if not node not found or target attribute doesn't exist);
	 */
	protected String getNodeLinkTargetPath(String NodePath, Tree tree) {
		
		NodeLink nl = tree.findNodeLink(NodePath);
		if (Objects.isNull(nl)) return null;
		
		Attribute attr = nl.getDestination().getAttribute("target"); 
		if (Objects.isNull(attr)) return null;
		
		return attr.getFirstElement();
	}
	
	
	/**
	 * Custom exception class used to wrap errors generated during initialisation of the {@link MovingBeamMetadataCache}
	 */
	final class CacheConstructionException extends Exception {
		private static final long serialVersionUID = -5113506787527672829L;

		public CacheConstructionException(String error) {
			super(error);
		}
	
	}
	
}