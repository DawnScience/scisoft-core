/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.iucr.cbflib.SWIGTYPE_p_p_char;
import org.iucr.cbflib.cbf;
import org.iucr.cbflib.cbf_handle_struct;
import org.iucr.cbflib.intP;
import org.iucr.cbflib.sizetP;
import org.iucr.cbflib.uintP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.CBFlib.CBFlib;

/**
 * Crystallographic Binary File (CBF) and image-supporting Crystallographic Information File (imgCIF) loader
 *
 */
public class CBFLoader extends AbstractFileLoader {
	protected static final Logger logger = LoggerFactory.getLogger(CBFLoader.class);
	private HashMap<String, String> metadataMap = new HashMap<String, String>();
	public HashMap<String, Serializable> GDAMetadata = new HashMap<String, Serializable>();

	static {
		CBFlib.loadLibrary();
	}

	public CBFLoader() {
	}

	/**
	 * @param FileName
	 */
	public CBFLoader(String FileName) {
		fileName = FileName;
	}

	@Override
	protected void clearMetadata() {
		metadata = null;
		metadataMap.clear();
		GDAMetadata.clear();
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		DataHolder output = new DataHolder();
		ILazyDataset data = null;
		ImageOrientation imageOrien = null;

		logger.info("Loading {}", fileName);

		cbf_handle_struct chs = new cbf_handle_struct(fileName);

		Tree tree = readAllMetadata(chs);

		if (loadMetadata) {
			NodeLink link = tree.getGroupNode().iterator().next(); // first group in root group
			if (link.isDestinationGroup()) {
				GroupNode group = (GroupNode) link.getDestination();
				parseMiniCBFHeader(group);
				imageOrien = parseCBFHeaderData(group);
			}
		}
		imageOrien = readImageOrientation(chs, imageOrien);

		if (loadLazily) {
			data = createLazyDataset(DEF_IMAGE_NAME, imageOrien.getDType(), imageOrien.getShape(), new CBFLoader(fileName));
		} else {
			data = readCBFBinaryData(chs, imageOrien);
		}
		chs.delete(); // this also closes the file

		output.addDataset(DEF_IMAGE_NAME, data);

		if (loadMetadata) {
			int[] shape = imageOrien.getShape();
			try {
				createGDAMetadata(shape[1], shape[0]);
			} catch (Exception e) {
				// ignore
			}
			data.setMetadata(metadata);
			output.setMetadata(metadata);
			// We need to read the header completely
			if (loadMetadata && metadata==null) { // We create something
				metadata = new Metadata(metadataMap);
				metadata.setFilePath(fileName);
			}
		}
		return output;
	}

	private static final String PLACE_HOLDER = "."; // can mean inapplicable or inappropriate for a row and also use default
	private static final String MISSING = "?"; // missing value

	private Tree readAllMetadata(cbf_handle_struct chs) throws ScanFileHolderException {
		SWIGTYPE_p_p_char s = cbf.new_charPP();
		uintP n = new uintP();

		Tree tree = TreeFactory.createTreeFile(fileName.hashCode(), fileName);
		GroupNode gt = tree.getGroupNode();

		CBFError.errorChecker(cbf.cbf_rewind_datablock(chs));
		do {
			CBFError.errorChecker(cbf.cbf_datablock_name(chs, s));
			String blockName = new String(cbf.charPP_value(s));
			GroupNode block;
			if (gt.containsGroupNode(blockName)) {
				block = tree.getGroupNode().getGroupNode(blockName);
			} else {
				block = TreeFactory.createGroupNode(blockName.hashCode());
				gt.addGroupNode(tree, Tree.ROOT, blockName, block);
			}

			// TODO add save frame support using block-item functions
			CBFError.errorChecker(cbf.cbf_rewind_category(chs));
			do {
				CBFError.errorChecker(cbf.cbf_category_name(chs, s));

				String catName = new String(cbf.charPP_value(s));
				GroupNode category;
				if (block.containsGroupNode(catName)) {
					category = block.getGroupNode(catName);
				} else {
					category = TreeFactory.createGroupNode(catName.hashCode());
					block.addGroupNode(tree, Tree.ROOT + blockName + Node.SEPARATOR, catName, category);
				}

				CBFError.errorChecker(cbf.cbf_count_columns(chs, n.cast()));
				int c = (int) n.value();
				CBFError.errorChecker(cbf.cbf_count_rows(chs, n.cast()));
				int r = (int) n.value();

				if (c == 0 || r == 0) {
					continue;
				}

				CBFError.errorChecker(cbf.cbf_rewind_row(chs));
				do {
					CBFError.errorChecker(cbf.cbf_row_number(chs, n.cast()));
					int row = (int) n.value();
					
					CBFError.errorChecker(cbf.cbf_rewind_column(chs));
					do {
						CBFError.errorChecker(cbf.cbf_column_name(chs, s));
						String colName = new String(cbf.charPP_value(s));
						Attribute column = category.getAttribute(colName);

						String v = null;
						if (cbf.cbf_get_value(chs, s) == 0) {
							v = cbf.charPP_value(s);
							if (!PLACE_HOLDER.equals(v) && !MISSING.equals(v)) {
								Number num = Utils.parseValue(v);
								if (column == null) {
									int dt = num == null ? Dataset.STRING : (num instanceof Float || num instanceof Double ? Dataset.FLOAT64 : Dataset.INT32);
									Dataset ds = DatasetFactory.zeros(new int[] {r}, dt);
									column = TreeFactory.createAttribute(colName, ds, false);
									category.addAttribute(column);
								}
								column.getValue().set(num == null ? v : num, row);
							}
						}
					} while (CBFError.errorChecker(cbf.cbf_next_column(chs)));
				} while (CBFError.errorChecker(cbf.cbf_next_row(chs)));
			} while (CBFError.errorChecker(cbf.cbf_next_category(chs)));
		} while (CBFError.errorChecker(cbf.cbf_next_datablock(chs)));

		return tree;
	}

	static String[] miniCBFheaderNames = { "Pixel_size", "Silicon sensor, thickness", "Exposure_time",
			"Exposure_period", "Tau =", "Count_cutoff", "Threshold_setting,", "N_excluded_pixels =",
			"Excluded_pixels:", "Flat_field:", "Trim_directory:", "Wavelength", "Energy_range",
			"Detector_distance", "Detector_Voffset", "Beam_xy", "Flux", "Filter_transmission",
			"Start_angle", "Angle_increment", "Detector_2theta", "Polarization", "Alpha", "Kappa", "Phi",
			"Chi", "Oscillation_axis", "N_oscillations" };

	private void parseMiniCBFHeader(GroupNode group) {
		NodeLink link = group.getNodeLink("array_data");
		if (link == null || !link.isDestinationGroup())
			return;

		String header = getString((GroupNode) link.getDestination(), "header_contents", 0);
		if (header == null || header.length() == 0)
			return;

		BufferedReader in = new BufferedReader(new StringReader(header));
		String temp;
		int unknownNum = 0;
		try {
			while ((temp = in.readLine()) != null) {
				if (temp.length() == 0)
					continue;
				boolean found = false;
				for (String name : miniCBFheaderNames) {
					if (temp.startsWith(name, 2)) {
						metadataMap.put(name, temp.substring(2 + name.length()).trim());
						found = true;
						break;
					}
				}
				if (!found) {
					metadataMap.put("Unknown " + unknownNum, temp);
					unknownNum++;
				}
			}
		} catch (IOException e) {
		}
	}

	private ImageOrientation parseCBFHeaderData(GroupNode block) throws ScanFileHolderException {
		int xLength = 0;
		int yLength = 0;
		boolean xIncreasing = false;
		boolean yIncreasing = false;
		boolean isRowsX = false;

		GroupNode category = null;
		if (block.containsGroupNode("diffrn_frame_data")) {
			category = block.getGroupNode("diffrn_frame_data");
		} else if (block.containsGroupNode("diffrn_data_frame")) {
			category = block.getGroupNode("diffrn_data_frame");
		}

		if (category == null)
			return null; // no proper CBF metadata!!!

		String arrayid = category.getAttribute("array_id").getFirstElement();
		metadataMap.put("diffrn_data_frame.array_id", arrayid);

		// get the image dimensions
		if (block.containsGroupNode("array_structure_list")) {
			category = block.getGroupNode("array_structure_list");
			Dataset values = DatasetUtils.convertToDataset(category.getAttribute("array_id").getValue());
			IndexIterator it = values.getIterator();
			int i = 0;
			while (it.hasNext()) {
				if (values.getObjectAbs(it.index).equals(arrayid)) {
					int ind = getInteger(category, "index", i);
					int dim = getInteger(category, "dimension", i);
					int pre = getInteger(category, "precedence", i);
					String dir = getString(category, "direction", i);
					String asi = getString(category, "axis_set_id", i);

					metadataMap.put("SIZE " + ind, String.valueOf(dim));
					metadataMap.put("precedence " + ind, String.valueOf(pre));
					metadataMap.put("direction " + ind, dir);
					metadataMap.put("axis_set_id " + ind, asi);

				}
				i++;
			}
		}

		// Attempt to find rows that matches above array_id
		if (isMatch("axis_set_id 1", "ELEMENT_X")) { // FIXME is this always the case?
			isRowsX = getInteger("precedence 1") == 1;

			xLength = getInteger("SIZE 1");
			yLength = getInteger("SIZE 2");

			xIncreasing = isMatch("direction 1", "increasing");
			yIncreasing = isMatch("direction 2", "increasing");
		} else {
			isRowsX = getInteger("precedence 2") == 1;

			xLength = getInteger("SIZE 2");
			yLength = getInteger("SIZE 1");

			xIncreasing = isMatch("direction 2", "increasing");
			yIncreasing = isMatch("direction 1", "increasing");
		}

		String pixelSize = metadataMap.get("Pixel_size");
		if (pixelSize == null) {
			// need to fake mini-header
			/*
			 * 
			 */
//			 TODO
//			Beam_xy
//			Detector_distance
			
//			Wavelength
			/*
			 * _diffrn_radiation.diffrn_id DS1
_diffrn_radiation.wavelength_id WAVELENGTH1
_diffrn_radiation.probe x-ray
_diffrn_radiation_wavelength.id WAVELENGTH1
_diffrn_radiation_wavelength.wavelength 0.979399979114532
_diffrn_radiation_wavelength.wt 1.0

			 */
//			Start_angle
//			Angle_increment
//			Exposure_time
//			Count_cutoff
		}
		metadataMap.put("numPixels_x", String.valueOf(xLength));
		metadataMap.put("numPixels_y", String.valueOf(yLength));
		return new ImageOrientation(xLength, yLength, -1, -1, xIncreasing, yIncreasing, isRowsX);
	}

	private static Object getObject(GroupNode category, String attribute, int... pos) {
		Attribute attr = category.getAttribute(attribute);
		return attr == null ? null : attr.getValue().getObject(pos);
	}

	private static String getString(GroupNode category, String attribute, int... pos) {
		Object obj = getObject(category, attribute, pos);
		return obj == null ? null : obj.toString();
	}

	private static int getInteger(GroupNode category, String attribute, int... pos) {
		String str = getString(category, attribute, pos);
		Object obj = str == null ? null : Utils.parseValue(str);
		return obj == null ? 0 : ((Number) obj).intValue();
	}

	private void createGDAMetadata(int x, int y) throws ScanFileHolderException {
		try {
			String pixelSize = metadataMap.get("Pixel_size");
			if (pixelSize == null) {
				throw new ScanFileHolderException("No relevant metadata found");
			}
			String[] xypixVal = pixelSize.split("m x");
			double xPxVal = Double.parseDouble(xypixVal[0])*1000;
			double yPXVal = Double.parseDouble(xypixVal[1].split("m")[0])*1000;
			
			String tmp = metadataMap.get("Beam_xy");
			double beamPosX;
			double beamPosY;
			if (tmp != null) {
				String[] beamxy = tmp.split(",");
				beamPosX = Double.parseDouble(beamxy[0].split("\\(")[1]);
				beamPosY = Double.parseDouble(beamxy[1].split("\\)")[0]);
			} else {
				beamPosX = x/2;
				beamPosY = y/2;
			}
			
			// NXGeometery:NXtranslation
//			double[] detectorOrigin = { 
//					(Double.parseDouble(getMetadataValue("numPixels_x")) - beamPosX)* xPxVal ,
//					(Double.parseDouble(getMetadataValue("numPixels_y")) - beamPosY)* yPXVal ,
//					Double.parseDouble(getMetadataValue("Detector_distance").split("m")[0])*1000 };
			double[] detectorOrigin = {  beamPosX* xPxVal, beamPosY* yPXVal ,
					getFirstDouble("Detector_distance", "m")*1000 };
			GDAMetadata.put("NXdetector:NXgeometry:NXtranslation", detectorOrigin);
			//System.out.println(detectorOrigin[0] +"  "+detectorOrigin[1]+"   "+detectorOrigin[2]);
			GDAMetadata.put("NXdetector:NXgeometry:NXtranslation@units", "milli*metre");
			
			// NXGeometery:NXOrientation
			double [] directionCosine = {1,0,0,0,1,0}; // to form identity matrix as no header data
			GDAMetadata.put("NXdetector:NXgeometry:NXorientation",directionCosine);
			
			// NXGeometery:XShape (shape from origin (+x, +y, +z,0, 0, 0) > x,y,0,0,0,0)
			double[] detectorShape = {
					getDouble("numPixels_x") * xPxVal,
					getDouble("numPixels_y") * yPXVal,0,0,0,0 };
			GDAMetadata.put("NXdetector:NXgeometry:NXshape", detectorShape);
			GDAMetadata.put("NXdetector:NXgeometry:NXshape@units","milli*metre");
			
			// NXGeometery:NXFloat
			GDAMetadata.put("NXdetector:x_pixel_size", xPxVal);
			GDAMetadata.put("NXdetector:x_pixel_size@units", "milli*metre");
			GDAMetadata.put("NXdetector:y_pixel_size", yPXVal);
			GDAMetadata.put("NXdetector:y_pixel_size@units", "milli*metre");

			// "NXmonochromator:wavelength"
			double lambda = Double.NaN;
			String value;
			value = metadataMap.get("Wavelength");
			if (value != null) {
				if (value.contains("A"))
					lambda = getFirstDouble("Wavelength", "A");
				else if (value.contains("nm"))
					lambda = getFirstDouble("Wavelength", "nm")*10;
			}
			if (Double.isNaN(lambda))
				throw new ScanFileHolderException("The wavelength could not be parsed in from the mini cbf file header");

			GDAMetadata.put("NXmonochromator:wavelength",lambda);
			GDAMetadata.put("NXmonochromator:wavelength@units", "Angstrom");

			// oscillation range
			GDAMetadata.put("NXsample:rotation_start", getFirstDouble("Start_angle", "deg"));
			GDAMetadata.put("NXsample:rotation_start@units","degree");
			GDAMetadata.put("NXsample:rotation_range", getFirstDouble("Angle_increment", "deg"));
			GDAMetadata.put("NXsample:rotation_range@units", "degree");
			
			//Exposure time
			GDAMetadata.put("NXsample:exposure_time", getFirstDouble("Exposure_time", "s"));
			GDAMetadata.put("NXsample:exposure_time@units", "seconds");
			
			GDAMetadata.put("NXdetector:pixel_overload", getFirstDouble("Count_cutoff", "counts"));
			GDAMetadata.put("NXdetector:pixel_overload@units", "counts");
			
			createMetadata(detectorOrigin, xPxVal, yPXVal, lambda);
		} catch (NumberFormatException e) {
			throw new ScanFileHolderException("There was a problem parsing numerical value from string ",e);
		}
	}

	private void createMetadata(double[] detectorOrigin, double xPxVal, double yPXVal, double lambda) {
		try {
			// This is new metadata
			Matrix3d identityMatrix = new Matrix3d();
			identityMatrix.setIdentity();
			DetectorProperties detectorProperties = new DetectorProperties(new Vector3d(detectorOrigin),
					getInteger("numPixels_x"), getInteger("numPixels_y"), xPxVal, yPXVal, identityMatrix);
	
			DiffractionCrystalEnvironment diffractionCrystalEnvironment = new DiffractionCrystalEnvironment(lambda,
					getFirstDouble("Start_angle", "deg"), getFirstDouble("Angle_increment", "deg"), getFirstDouble(
							"Exposure_time", "s"));
	
			metadata = new DiffractionMetadata(fileName, detectorProperties, diffractionCrystalEnvironment);
			metadata.addDataInfo(DEF_IMAGE_NAME, getInteger("numPixels_x"), getInteger("numPixels_y"));
			metadata.setMetadata(metadataMap);
		} catch (ScanFileHolderException e) {
			metadata = new Metadata(metadataMap);
		}
		metadata.setFilePath(fileName);
	}

	private ImageOrientation readImageOrientation(cbf_handle_struct chs, ImageOrientation imageOrien) throws ScanFileHolderException {
		CBFError.errorChecker(cbf.cbf_rewind_datablock(chs));
		CBFError.errorChecker(cbf.cbf_rewind_category(chs));
		CBFError.errorChecker(cbf.cbf_find_category(chs, "array_data"));
		CBFError.errorChecker(cbf.cbf_rewind_row(chs));
		CBFError.errorChecker(cbf.cbf_rewind_column(chs));
		CBFError.errorChecker(cbf.cbf_find_column(chs, "data"));

		uintP cifcomp = new uintP();
		intP bid = new intP(), els = new intP(), elu = new intP();
		intP minel = new intP(), maxel = new intP(), isre = new intP();
		sizetP elsize = new sizetP(), elnum = new sizetP();
		sizetP dim1 = new sizetP(), dim2 = new sizetP(), dim3 = new sizetP(), pad = new sizetP();
		SWIGTYPE_p_p_char byteorder = cbf.new_charPP();

		CBFError.errorChecker(cbf.cbf_get_arrayparameters_wdims(chs, cifcomp.cast(), bid.cast(), elsize.cast(), els
				.cast(), elu.cast(), elnum.cast(), minel.cast(), maxel.cast(), isre.cast(), byteorder, dim1.cast(),
				dim2.cast(), dim3.cast(), pad.cast()));

		if (imageOrien == null) {
			imageOrien = new ImageOrientation((int) dim1.value(), (int) dim2.value(), isre.value(), els.value());
		} else {
			imageOrien.isReal = isre.value();
			imageOrien.isSigned = els.value();
		}

		metadataMap.put("numPixels_x", String.valueOf(imageOrien.shape[1]));
		metadataMap.put("numPixels_y", String.valueOf(imageOrien.shape[0]));

		long numPixels = AbstractDataset.calcLongSize(imageOrien.shape);

		if (numPixels != elnum.value()) {
			throw new ScanFileHolderException("Mismatch of CBF binary data size: " + numPixels + " cf " + elnum.value());
		}

		cifcomp.delete();
		minel.delete();
		maxel.delete();
		isre.delete();
		elsize.delete();
		elnum.delete();
		dim1.delete();
		dim2.delete();
		dim3.delete();
		pad.delete();

		bid.delete();
		els.delete();
		elu.delete();
		return imageOrien;
	}

	private Dataset readCBFBinaryData(cbf_handle_struct chs, ImageOrientation imageOrien) throws ScanFileHolderException {

		int[] shape = imageOrien.getShape();
		AbstractDataset data;
		try {
			data = (AbstractDataset) DatasetFactory.zeros(shape, imageOrien.getDType());
		} catch (Exception eb) {
			throw new ScanFileHolderException("CBFLoader failed when creating a Dataset for the data", eb);
		}
		int xLength = shape[1];
		int yLength = shape[0];
		boolean xIncreasing = imageOrien.isXIncreasing();
		boolean yIncreasing = imageOrien.isYIncreasing();
		boolean isRowsX = imageOrien.isRowsX();

		int numPixels = xLength * yLength;

//		System.out.println("Loading " + fileName + ", " + numPixels);

		// remember to explicitly delete arrays allocated on the JNI side
		// as finalize method is up to garbage collector and is tardily done

		// TODO add smaller data type support (with sign extension ala NeXus)
		// deal with floating point data differently than integer data

		int stride1; // stride is change in position on n-th dim
		int stride2;
		int start;  // start is offset in position

		if (!isRowsX) { // swap row and column directions
			boolean b = yIncreasing;
			yIncreasing = !xIncreasing;
			xIncreasing = !b;
		}

		if (!yIncreasing) { // note that image in GDA is plotted so Y increases from top to bottom
			stride1 = xLength;
			start = 0;
		} else {
			stride1 = -xLength;
			start = xLength*yLength - xLength;
		}

		if (xIncreasing) {
			stride2 = 1;
		} else {
			stride2 = -1;
			start += xLength - 1;
		}

		int rows;
		int cols;
		int rstep;
		int cstep;

		if (isRowsX) {
			rows = yLength;
			cols = xLength;
			rstep = stride1;
			cstep = stride2;
		} else {
			rows = xLength;
			cols = yLength;
			rstep = stride2;
			cstep = stride1;
		}

		int index = 0; // index in destination
		int position = 0; // position in buffer
		int hash = 0;
		intP bid = new intP();
		sizetP rsize = new sizetP();

		CBFError.errorChecker(cbf.cbf_rewind_datablock(chs));
		CBFError.errorChecker(cbf.cbf_rewind_category(chs));
		CBFError.errorChecker(cbf.cbf_find_category(chs, "array_data"));
		CBFError.errorChecker(cbf.cbf_rewind_row(chs));
		CBFError.errorChecker(cbf.cbf_rewind_column(chs));
		CBFError.errorChecker(cbf.cbf_find_column(chs, "data"));

		if (data instanceof DoubleDataset) {
			DoubleBuffer ddata;
			try {
				ddata = ByteBuffer.allocateDirect(numPixels * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
			} catch (OutOfMemoryError e) {
				throw new ScanFileHolderException("CBFloader failed to while allocating the byte buffer ", e);
			} catch (Exception eb) {
				throw new ScanFileHolderException("CBFloader failed to while allocating the byte buffer ", eb);
			}

			CBFError.errorChecker(cbf.cbf_get_realarray(chs, bid.cast(), ddata, (Double.SIZE / 8), numPixels,
					rsize.cast()));

			if (numPixels != rsize.value()) {
				throw new ScanFileHolderException("Mismatch of CBF binary data size");
			}

			double[] dArray = ((DoubleDataset) data).getData();

			// map from CBF data to dataset
			double amax = -Double.MAX_VALUE;
			double amin = Double.MAX_VALUE;
			double dhash = 0;
			for (int j = 0; j < rows; j++) {
				position = start;
				for (int i = 0; i < cols; i++) {
					double value = ddata.get(position);
					position += cstep;
					if (Double.isInfinite(value) || Double.isNaN(value))
						dhash = (dhash * 19) % Integer.MAX_VALUE;
					else
						dhash = (dhash * 19 + value) % Integer.MAX_VALUE;
					dArray[index++] = value;
					if (value > amax) {
						amax = value;
					}
					if (value < amin) {
						amin = value;
					}
				}
				start += rstep;
			}
			hash = (int) dhash;
			data.setStoredValue(AbstractDataset.STORE_MAX, amax);
			data.setStoredValue(AbstractDataset.STORE_MIN, amin);
			ddata = null;
		} else {
			IntBuffer idata;
			try {
				idata = ByteBuffer.allocateDirect(numPixels * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
			} catch (OutOfMemoryError e) {
				throw new ScanFileHolderException("CBFLoader failed to allocate bytebuffer (intbuffer)",e);
			} catch (Exception eb) {
				throw new ScanFileHolderException("CBFLoader failed to allocate bytebuffer (intbuffer)",eb);
			}

			int signed = imageOrien.isSigned;
			CBFError.errorChecker(cbf.cbf_get_integerarray(chs, bid.cast(), idata, (Integer.SIZE / 8), signed,
					numPixels, rsize.cast()));

			if (numPixels != rsize.value()) {
				throw new ScanFileHolderException("Mismatch of CBF binary data size");
			}

			int[] dArray = ((IntegerDataset) data).getData();
			int amax = Integer.MIN_VALUE;
			int amin = Integer.MAX_VALUE;

			for (int j = 0; j < rows; j++) {
				position = start;
				for (int i = 0; i < cols; i++) {
					int value = idata.get(position);
					position += cstep;
					hash = hash * 19 + value;
					dArray[index++] = value;
					if (value > amax) {
						amax = value;
					}
					if (value < amin) {
						amin = value;
					}
				}
				start += rstep;
			}

			data.setStoredValue(AbstractDataset.STORE_MAX, amax);
			data.setStoredValue(AbstractDataset.STORE_MIN, amin);
			idata = null;
		}

		rsize.delete();
		bid.delete();

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);

		return data;
	}


	private int getInteger(String key) throws ScanFileHolderException {
		try {
			String value = metadataMap.get(key);
			if (value == null) {
				throw new ScanFileHolderException("No such key: " + key);
			}
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ScanFileHolderException("There was a problem parsing integer value from string",e);
		}
	}

	private double getDouble(String key) throws ScanFileHolderException {
		try {
			String value = metadataMap.get(key);
			if (value == null) {
				throw new ScanFileHolderException("No such key: " + key);
			}
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new ScanFileHolderException("There was a problem parsing double value from string",e);
		}
	}

	private double getFirstDouble(String key, String split) throws ScanFileHolderException {
		try {
			String value = metadataMap.get(key);
			if (value == null) {
				throw new ScanFileHolderException("No such key: " + key);
			}
			return Double.parseDouble(value.split(split)[0]);
		} catch (NumberFormatException e) {
			throw new ScanFileHolderException("There was a problem parsing double value from string",e);
		}
	}

	private boolean isMatch(String key, String value) throws ScanFileHolderException {
		try {
			String mValue = metadataMap.get(key);
			return value.equalsIgnoreCase(mValue);
		} catch (NumberFormatException e) {
			throw new ScanFileHolderException("There was a problem parsing double value from string",e);
		}
	}

	private class ImageOrientation {

		int [] shape;
		boolean xIncreasing;
		boolean yIncreasing;
		boolean isRowsX;
		int isReal;
		int isSigned;

		private ImageOrientation(int x, int y, int isReal, int isSigned) {
			// these values are to support the 6M on i24 for the time being
			this(x, y, isReal, isSigned, true, false, true);
		}

		private ImageOrientation(int x, int y, int isReal, int isSigned, boolean increasingX, boolean increasingY,
				boolean areRowsX) {
			shape = new int[] {y, x};
			this.isReal = isReal;
			this.isSigned = isSigned;
			xIncreasing = increasingX;
			yIncreasing = increasingY;
			isRowsX = areRowsX;
		}

		public int getDType() {
			if (isReal == -1)
				return -1;
			if (isReal == 0)
				return Dataset.INT32;
			return Dataset.FLOAT64;
		}

		public int[] getShape() {
			return shape;
		}

		public boolean isXIncreasing() {
			return xIncreasing;
		}

		public boolean isYIncreasing() {
			return yIncreasing;
		}

		public boolean isRowsX() {
			return isRowsX;
		}
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		// We need to read the header completely
		if (loadMetadata && metadata==null) { // We create something
			metadata = new Metadata(metadataMap);
			metadata.setFilePath(fileName);
		}
	}
}
