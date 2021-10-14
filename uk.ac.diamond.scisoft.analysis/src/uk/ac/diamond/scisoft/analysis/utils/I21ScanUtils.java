/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.january.dataset.ShapeUtils;

import uk.ac.diamond.scisoft.analysis.io.HDF5Loader;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

public class I21ScanUtils {

	public static final String SCAN_ORIGIN_ID = "scan_origin_id";
	public static final String SCAN_LEVELS = "scan_levels";
	public static final String SCAN_FORMAT = "scan%d_";
	public static final String SAMPLE_SCAN = "sample_scan";
	public static final String ELASTIC_SCAN = "elastic_scan";
	public static final String SCAN_TYPE = "sample_type";
	public static final String SCAN_TYPE_ELASTIC = "elastic_reference";
	public static final String SCAN_TYPE_SAMPLE = "sample";

	public static final String GDA_METADATA_SUFFIX = "_scan";

	/**
	 * Class representing a collection of scans for I21's RIXS experiments
	 * <p>
	 * Old GDA (v8) scanning at I21 records a single NeXus file per scan. To align spectra,
	 * scans with an elastic reference are interleaved with scans on the actual sample of interest.
	 * <p>
	 * This class aggregates the scans scripted to contain custom metadata:
	 * <pre>
	 *   scan_origin_id: initial scan file number
	 *   scan_levels: 1
	 *   scan0_name: "qval"
	 *   scan0_length: # of points in this scan dimension
	 *      scan0_index: 0
	 *      qval: qval
	 *      Then either
	 *         sample_type: "sample"
	 *         elastic_scan: sfn for this sample
	 *      or
	 *         sample_type: "elastic_reference"
	 *         sample_scan", sfn for the sample
	 * </pre>
	 */
	public static class I21ScanCollection {
		private int id;
		private boolean isSample;
		private boolean isElasticAfter;
		private int origin;
		private int[] shape;
		private int[] posn;
		private String[] names;
		private int size;

		/**
		 * Construct with current scan number
		 * @param current
		 */
		public I21ScanCollection(int current) {
			this.id = current; 
		}

		/**
		 * Parses GDA metadata and initializes scan collection
		 * @param g
		 */
		public void parseGDAMetadata(GroupNode g) {
			// find 
			for (NodeLink l : g) {
				if (l.isDestinationGroup()) {
					GroupNode d = (GroupNode) l.getDestination();
					if (l.getName().endsWith(GDA_METADATA_SUFFIX) && NexusTreeUtils.isNXClass(d, NexusConstants.COLLECTION)) {
						retrieveScanInfo(d);
						return;
					}
					parseGDAMetadata(d);
				}
			}
		}

		private void retrieveScanInfo(GroupNode g) {
			int rank = NexusTreeUtils.getIntArray(g.getDataNode(SCAN_LEVELS), 1)[0];
			shape = new int[rank];
			posn = new int[rank];
			names = new String[rank];
			origin = NexusTreeUtils.getIntArray(g.getDataNode(SCAN_ORIGIN_ID), 1)[0];

			String type = NexusTreeUtils.getStringArray(g.getDataNode(SCAN_TYPE), 1)[0];
			int otherID;
			if (SCAN_TYPE_SAMPLE.equals(type)) {
				isSample = true;
				otherID = NexusTreeUtils.getIntArray(g.getDataNode(ELASTIC_SCAN), 1)[0];
			} else if (SCAN_TYPE_ELASTIC.equals(type)) {
				isSample = false;
				otherID = NexusTreeUtils.getIntArray(g.getDataNode(SAMPLE_SCAN), 1)[0];
			} else {
				throw new IllegalArgumentException("Sample type is not valid");
			}
			if (id == otherID) {
				throw new IllegalArgumentException("Referenced scan must not be the same as current scan");
			}

			isElasticAfter = isSample ^ (id > otherID);

			for (int i = 0; i < rank; i++) {
				String prefix = String.format(SCAN_FORMAT, i);
				shape[i] = NexusTreeUtils.getIntArray(g.getDataNode(prefix + "length"), 1)[0];
				posn[i] = NexusTreeUtils.getIntArray(g.getDataNode(prefix + "index"), 1)[0];
				names[i] = NexusTreeUtils.getStringArray(g.getDataNode(prefix + "name"), 1)[0];
			}

			size = ShapeUtils.calcSize(shape);
			// check if origin is correct
			int start = id > otherID ? otherID : id;
			start -= 2 * ShapeUtils.getFlat1DIndex(shape, posn);
			if (origin != start) {
				throw new IllegalArgumentException("Origin does not match calculated start");
			}
		}

		/**
		 * Get numbers (aka IDs) for scans of given type
		 * @param elastic if true, get IDs for elastic scan otherwise for sample scan
		 * @return array of IDs
		 */
		public int[] getIDs(boolean elastic) {
			int offset = elastic ^ isElasticAfter ? origin : origin + 1;
			int[] ids = new int[size];
			for (int i = 0; i < size; i++) {
				ids[i] = offset;
				offset += 2;
			}
			return ids;
		}

		/**
		 * Get number (aka ID) for scan of given type at given position
		 * @param elastic
		 * @param pos
		 * @return ID
		 */
		public int getID(boolean elastic, int... pos) {
			int offset = elastic ^ isElasticAfter ? origin : origin + 1;
			return offset + 2 * ShapeUtils.getFlat1DIndex(shape, pos);
		}

		/**
		 * @return rank of scan collection
		 */
		public int getRank() {
			return shape.length;
		}

		/**
		 * @return shape of scan collection
		 */
		public int[] getShape() {
			return shape;
		}

		/**
		 * @return size of scan collection
		 */
		public int getSize() {
			return size;
		}

		/**
		 * @return array of scan names, corresponding to each dimension in the collection
		 */
		public String[] getScanNames() {
			return names;
		}

		/**
		 * @return true if current scan is for a sample (rather than the elastic reference)
		 */
		public boolean isSample() {
			return isSample;
		}

		/**
		 * @return true if the elastic reference scan is recorded after the sample scan
		 */
		public boolean isElasticAfter() {
			return isElasticAfter;
		}
	}

	static final Pattern I21_SCAN_REGEX = Pattern.compile(".*-(\\d+)\\..*");

	/**
	 * Get I21 scan collection information from a I21 NeXus file
	 * @param filePath
	 * @return scan collection
	 * @throws ScanFileHolderException
	 */
	public static I21ScanCollection getI21ScanCollection(String filePath) throws ScanFileHolderException {
		File f = new File(filePath);
		String fileName = f.getName();
		Matcher m = I21_SCAN_REGEX.matcher(fileName);
		if (!m.matches()) {
			return null;
		}
		int id = Integer.parseInt(m.group(1));
		I21ScanCollection sc = new I21ScanCollection(id);
		HDF5Loader l = new HDF5Loader(f.getAbsolutePath());
		TreeFile t = l.loadTree();

		sc.parseGDAMetadata((GroupNode) NexusTreeUtils.findFirstNode(t.getGroupNode(), NexusConstants.ENTRY).getDestination());
		return sc;
	}

	static final String I21_SCAN_FORMAT = "i21-%d.nxs";

	/**
	 * @param id
	 * @return scan file name for given id (or scan number)
	 */
	public static String getI21ScanFileName(int id) {
		return String.format(I21_SCAN_FORMAT, id);
	}
}
