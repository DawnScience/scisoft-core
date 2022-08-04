/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.eclipse.dawnsci.analysis.dataset.roi.GridPreferences;
import org.eclipse.dawnsci.analysis.dataset.roi.ROIList;

/**
 * This class holds the names of the parameters used in GuiBean to communicate with the GUI
 */
public final class GuiParameters implements Serializable {
	private static final long serialVersionUID = 9046820395601669002L;

	private String param;

	// Ordinal of next parameter to be created
	private static int nextOrdinal = 0;

	// Assign an ordinal to this parameter
	private final int ordinal = nextOrdinal++;
	private final Class<?> clazz;
	
	private static final HashMap<String, GuiParameters> namesToObjects = new HashMap<String, GuiParameters>();

	/**
	 * Create a new GuiParameters with the specified name that can contain objects of the specified type.
	 * 
	 * @param parameter
	 * @param clazz
	 */
	private GuiParameters(String parameter, Class<?> clazz) {
		param = parameter;
		this.clazz = clazz;
		namesToObjects.put(parameter, this);
	}

	/**
	 * Create a new GuiParameters with the specified name that can contain objects of the top level type, Serializable.
	 * 
	 * @param parameter
	 */
	private GuiParameters(String parameter) {
		param = parameter;
		this.clazz = Serializable.class;
		namesToObjects.put(parameter, this);
	}

	@Override
	public String toString() {
		return param;
	}


	/**
	 * Provide enum like valueOf method.
	 * @param name String value of GuiParameters
	 * @return the matching GuiParameters object, or <code>null</code> if no matching GuiParameters found
	 */
	public static GuiParameters valueOf(String name) {
		return namesToObjects.get(name);
	}	

	/**
	 * Provide enum like values method.
	 * @return the set of all GuiParameters objects
	 */
	public static GuiParameters[] values() {
		Collection<GuiParameters> values = namesToObjects.values();
		return values.toArray(new GuiParameters[namesToObjects.size()]);
	}
	
	/**
	 * Return the permitted storage type for the given parameter.
	 * <p>
	 * Note, some class types are List.class which does not reflect the contents of the list, see documentation for
	 * individual item for more details.
	 * 
	 * @return class
	 */
	public Class<?> getStorageClass() {
		return clazz;
	}

	@Override
	public final boolean equals(Object that) {
		return that != null && ordinal == ((GuiParameters) that).ordinal;
	}

	@Override
	public final int hashCode() {
		return ordinal;
	}

	/**
	 * Update value of Plot Operation GuiParameter
	 */
	public static final String PLOTOP_UPDATE = "UPDATE";

	/**
	 * ADD value of Plot Operation GuiParameter
	 */
	public static final String PLOTOP_ADD = "ADD";

	/**
	 * NONE value of Plot Operation GuiParameter
	 */
	public static final String PLOTOP_NONE = "NONE";

	/**
	 * Specifies the plotting mode can be any of the values in GuiPlotMode
	 */
	public static final GuiParameters PLOTMODE = new GuiParameters("PlotMode", GuiPlotMode.class);

	/**
	 * Specifies the Title string of the graph
	 */
	public static final GuiParameters TITLE = new GuiParameters("Title", String.class);

	/**
	 * Specifies the ROI name
	 */
	public static final GuiParameters ROIDATA = new GuiParameters("ROI", String.class);

	/**
	 * Specifies the ROI data list
	 */
	public static final GuiParameters ROIDATALIST = new GuiParameters("ROIList", ROIList.class);

	/**
	 * Indicates that all ROIs should be removed
	 */
	public static final GuiParameters ROICLEARALL = new GuiParameters("ROIClearAll", Boolean.class);

	/**
	 * Specifies the UUID of the plot client that originates the bean
	 */
	public static final GuiParameters PLOTID = new GuiParameters("PlotID", UUID.class);

	/**
	 * Specifies the plot operation at the moment can only be UPDATE, ADD or NONE
	 */
	public static final GuiParameters PLOTOPERATION = new GuiParameters("PlotOp", String.class);

	/**
	 * Specified the file operation and should hold a FileOperationsBean for further detail
	 */
	public static final GuiParameters FILEOPERATION = new GuiParameters("FileOp", FileOperationBean.class);

	/**
	 * Specifies the filename
	 */
	public static final GuiParameters FILENAME = new GuiParameters("Filename", String.class);

	/**
	 * Specifies the file format
	 */
	public static final GuiParameters FILEFORMAT = new GuiParameters("FileFormat", String.class);

	/**
	 * Specifies the saving path
	 */
	public static final GuiParameters SAVEPATH = new GuiParameters("SavePath", String.class);

	/**
	 * Specifies a list of selected filenames (as a list of strings)
	 */
	public static final GuiParameters FILESELECTEDLIST = new GuiParameters("FileList", List.class); // List<String>

	/**
	 * Specifies the view to send the loaded file
	 */
	public static final GuiParameters DISPLAYFILEONVIEW = new GuiParameters("DisplayOnView", String.class);

	/**
	 * Specifies the X position / column the data should be placed in the image grid
	 */
	public static final GuiParameters IMAGEGRIDXPOS = new GuiParameters("IGridX", Integer.class);

	/**
	 * Specifies the Y position / row the data should be placed in the image grid
	 */

	public static final GuiParameters IMAGEGRIDYPOS = new GuiParameters("IGridY", Integer.class);

	/**
	 * Specifies the number of columns (or rows and columns) in Image Grid
	 */
	public static final GuiParameters IMAGEGRIDSIZE = new GuiParameters("IGridSize", Integer[].class);

	/**
	 * Metadata node path
	 */
	public static final GuiParameters METADATANODEPATH = new GuiParameters("NodePath", String.class);

	/**
	 * Tree node path (filename#/path/to/node)
	 */
	public static final GuiParameters TREENODEPATH = new GuiParameters("TreeNodePath", String.class);

	/**
	 * Specifies the GUI Preferences used by GridProfile
	 */
	public static final GuiParameters GRIDPREFERENCES = new GuiParameters("GridPrefs", GridPreferences.class);

	/**
	 * Session store for all the images in the ImageGridView for retrieval at next startup.
	 * <p>
	 * The Images are as a list of uk.ac.diamond.scisoft.imagegrid.gridentry.GridImageEntry
	 */
	public static final GuiParameters IMAGEGRIDSTORE = new GuiParameters("ImageGridStore", List.class); // List<GridImageEntry>

	public static final GuiParameters VOLUMEHEADERSIZE = new GuiParameters("RawVolumeHeaderSize", Integer.class);

	public static final GuiParameters VOLUMEVOXELTYPE = new GuiParameters("RawVolumeVoxelType", Integer.class);

	public static final GuiParameters VOLUMEXDIM = new GuiParameters("RawVolumeVoxelXDim", Integer.class);

	public static final GuiParameters VOLUMEYDIM = new GuiParameters("RawVolumeVoxelYDim", Integer.class);

	public static final GuiParameters VOLUMEZDIM = new GuiParameters("RawVolumeVoxelZDim", Integer.class);

	/**
	 * Parameter for external access of the image grid view
	 */
	public static final GuiParameters IMAGEGRIDLIVEVIEW = new GuiParameters("ImageGridLiveView", String.class);

	/**
	 * List of IPeaks from fitting routing
	 */
	public static final GuiParameters FITTEDPEAKS = new GuiParameters("FittedPeaks", List.class); // List<IPeak>

	public static final GuiParameters MASKING = new GuiParameters("Masking"); // Unknown/unused?

	/**
	 * Calibration peaks for NCD
	 */
	public static final GuiParameters CALIBRATIONPEAKS = new GuiParameters("CalibrationPeaks"); // Unknown/unused?

	/**
	 * Calibration function for NCD
	 */
	public static final GuiParameters CALIBRATIONFUNCTIONNCD = new GuiParameters("CalibrationFunction"); // Unknown/unused?
	
	/** 
 	 * Specifies the OneDFile 
 	 */ 
 	public static final GuiParameters ONEDFILE = new GuiParameters("OneDFile", OneDDataFilePlotDefinition.class); 

 	/**
 	 * Parameters for controlling axes
 	 */
 	public static final GuiParameters AXIS_OPERATION = new GuiParameters("AxisOp", AxisOperation.class); 

 	/**
 	 * Indicates the current update should be done quietly and not broadcast
 	 */
 	public static final GuiParameters QUIET_UPDATE = new GuiParameters("QuietUpdate"); 
}
