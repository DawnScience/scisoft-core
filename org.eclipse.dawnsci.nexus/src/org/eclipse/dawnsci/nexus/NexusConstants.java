/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.nexus;

import java.time.format.DateTimeFormatter;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;

/**
 * Constants used to deal with NeXus files
 */
public class NexusConstants {
	/**
	 * Group attribute to indicate NeXus class
	 */
	public static final String NXCLASS = "NX_class";

	/**
	 * Dataset attribute name for units
	 */
	public static final String UNITS = "units";

	/**
	 * Dataset attribute name for interpretation
	 */
	public static final String INTERPRETATION = "interpretation";

	public static final String INTERPRETATION_IMAGE_RGB = "rgb-image";
	public static final String INTERPRETATION_IMAGE_RGBA = "rgba-image";
	public static final String INTERPRETATION_IMAGE_HSL = "hsl-image";
	public static final String INTERPRETATION_IMAGE_HSLA = "hsla-image";

	/**
	 * base classes
	 */
	public static final String APERTURE = "NXaperture";
	public static final String ATTENUATOR = "NXattenuator";
	public static final String BEAM = "NXbeam";
	public static final String BEAMSTOP = "NXbeam_stop";
	public static final String BENDINGMAGNET = "NXbending_magnet";
	public static final String CAPILLARY = "NXcapillary";
	public static final String CITE = "NXcite";
	public static final String COLLECTION = "NXcollection";
	public static final String COLLIMATOR = "NXcollimator";
	public static final String CONTAINER = "NXcontainer";
	public static final String CRYSTAL = "NXcrystal";
	public static final String CSG = "NXcsg";
	public static final String DATA = "NXdata";
	public static final String DETECTORGROUP = "NXdetector_group";
	public static final String DETECTORMODULE = "NXdetector_module";
	public static final String DETECTOR = "NXdetector";
	public static final String DISKCHOPPER = "NXdisk_chopper";
	public static final String ELECTROSTATICKICKER = "NXelectro_kicker";
	public static final String ENTRY = "NXentry";
	public static final String ENVIRONMENT = "NXenvironment";
	public static final String EVENTDATA = "NXevent_data";
	public static final String FERMICHOPPER = "NXfermi_chopper";
	public static final String FILTER = "NXfilter";
	public static final String FLIPPER = "NXflipper";
	public static final String FRESNELZONEPLATE = "NXfresnel_zone_plate";

	/**
	 * @deprecated superseded by {@link #TRANSFORMATIONS}
	 */
	@Deprecated(since="2014 NIAC meeting") // Use instead NXTransformations
	public static final String GEOMETRY = "NXgeometry";
	public static final String GRATING = "NXgrating";
	public static final String GUIDE = "NXguide";
	public static final String INSERTIONDEVICE = "NXinsertion_device";
	public static final String INSTRUMENT = "NXinstrument";
	public static final String LOG = "NXlog";
	public static final String MAGNETICKICKER = "NXmagnetic_kicker";
	public static final String MIRROR = "NXmirror";
	public static final String MODERATOR = "NXmoderator";
	public static final String MONITOR = "NXmonitor";
	public static final String MONOCHROMATOR = "NXmonochromator";
	public static final String NOTE = "NXnote";
	public static final String OBJECT = "NXobject";
	public static final String OFFGEOTMETRY = "NXoff_geometry";
	@Deprecated(since="2014 NIAC meeting") // Use instead NXTransformations
	public static final String ORIENTATION = "NXorientation";
	public static final String PARAMETERS = "NXparameters";
	public static final String PDB = "NXpdb";
	public static final String PINHOLE = "NXpinhole";
	public static final String POLARIZER = "NXpolarizer";
	public static final String POSITIONER = "NXpositioner";
	public static final String PROCESS = "NXprocess";
	public static final String QUADRIC = "NXquadric";
	public static final String QUADRUPOLE = "NXquadrupole";
	public static final String REFLECTIONS = "NXreflections";
	public static final String ROOT = "NXroot";
	public static final String SAMPLECOMPONENT = "NXsample_component";
	public static final String SAMPLE = "NXsample";
	public static final String SENSOR = "NXsensor";
	public static final String SEPARATOR = "NXseparator";
	@Deprecated(since="2014 NIAC meeting") // Used by NXGeometry, use instead NXTransformations
	public static final String SHAPE = "NXshape";
	public static final String SLIT = "NXslit";
	public static final String SNSEVENT = "NXsnsevent";
	public static final String SNSHISTO = "NXsnshisto";
	public static final String SOLENOIDMAGNET = "NXsolenoid_magnet";
	public static final String SOLIDGEOMETRY = "NXsolid_geometry";
	public static final String SOURCE = "NXsource";
	public static final String SPINROTATOR = "NXspin_rotator";
	public static final String SUBENTRY = "NXsubentry";
	public static final String TRANSFORMATIONS = "NXtransformations";
	@Deprecated(since="2014 NIAC meeting") // Used by NXGeometry, use instead NXTransformations
	public static final String TRANSLATION = "NXtranslation";
	public static final String USER = "NXuser";
	public static final String VELOCITYSELECTOR = "NXvelocity_selector";
	public static final String XRAYLENS = "NXxraylens";

	public static final String DEFAULT = "default";
	
	/**
	 * A {@link DateTimeFormatter} that formats dates as in ISO8601 format, always using
	 * three digits for milliseconds.  
	 */
	public static final DateTimeFormatter MILLISECOND_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

	/**
	 * NXdata default data name
	 */
	public static final String DATA_DATA = "data";
	/**
	 * NXdata dataset attribute
	 */
	public static final String DATA_NAME = "long_name";
	/**
	 * NXdata attribute that points to main dataset
	 */
	public static final String DATA_SIGNAL = "signal";
	/**
	 * NXdata attribute that points to auxiliary datasets
	 */
	public static final String DATA_AUX_SIGNALS = "auxiliary_signals";
	/**
	 * NXdata attribute that maps dimensions of main dataset to axis datasets
	 */
	public static final String DATA_AXES = "axes";
	/**
	 * NXdata attribute that points to error datasets (may also be legacy if attached to main dataset)
	 */
	public static final String DATA_UNCERTAINTIES = "uncertainties";

	/**
	 * NXdata indicate no default for axis in axes attribute
	 */
	public static final String DATA_AXESEMPTY = ".";

	/**
	 * Suffix for name of error datasets
	 */
	public static final String DATA_ERRORS_SUFFIX = "_errors";
	/**
	 * Suffix for name of attributes that indicate dimension mappings from axis dataset to main dataset
	 */
	public static final String DATA_INDICES_SUFFIX = "_indices";
	/**
	 * @deprecated This never was a standard suffix for the name of error datasets or attributes
	 * pointing to them
	 */
	@Deprecated(since="Dawn 2.22")
	public static final String DATA_UNCERTAINTY_SUFFIX ="_uncertainty";
	/**
	 * Suffix for names of datasets that contain set values (for axes)
	 */
	public static final String DATA_AXESSET_SUFFIX = "_set";

	/**
	 * NXdata default name for errors field (was legacy attribute name of main dataset)
	 */
	public static final String DATA_ERRORS = "errors";
	/**
	 * @deprecated legacy attribute name, use axes group instead
	 */
	@Deprecated(since="2014 NIAC meeting")
	public static final String DATA_AXIS = "axis";
	/**
	 * @deprecated legacy attribute name
	 */
	@Deprecated(since="2014 NIAC meeting")
	public static final String DATA_PRIMARY = "primary";

	/**
	 * Attribute for nodes that are linked to within the same nexus file.
	 */
	public static final String TARGET  = "target";

	/**
	 * Attribute for field that specifies what type to convert file data to
	 */
	public static final String DLS_READ_DATATYPE = "DLS_read_datatype";

	public enum DLS_DATATYPE_ATTR {
		BOOLEAN("boolean"); // other datatypes (e.g. complex) may be added at a later date

		private Attribute attr;

		private DLS_DATATYPE_ATTR(String name) {
			attr = TreeFactory.createAttribute(DLS_READ_DATATYPE, name);
		}

		public String toString() {
			return attr.getFirstElement();
		}

		public Attribute getAttribute() {
			return attr;
		}
	}

	private NexusConstants() {
	}
}
