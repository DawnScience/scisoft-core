package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import ncsa.hdf.object.Dataset;

import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.HierarchicalDataUtils;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;
import org.eclipse.dawnsci.hdf5.nexus.IFindInNexus;
import org.eclipse.dawnsci.hdf5.nexus.NexusFindDatasetByName;
import org.eclipse.dawnsci.hdf5.nexus.NexusFindGroupByAttributeText;
import org.eclipse.dawnsci.hdf5.nexus.NexusUtils;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ByteDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LongDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;

public class NexusDiffractionMetaReader {
	
	public static final String NX_MONOCHROMATOR = "NXmonochromator";
	public static final String NX_INSTRUMENT = "NXinstrument";
	public static final String NX_DETECTOR = "NXDetector";
	public static final String DATA_NAME = "data";
	public static final String DISTANCE_NAME = "distance";
	public static final String CAMERA_LENGTH = "camera length";
	public static final String ENERGY_NAME = "energy";
	public static final String WAVELENGTH = "wavelength";
	public static final String IN_ENERGY_NAME = "incident_energy";
	public static final String IN_WAVELENGTH = "incident_wavelength";
	public static final String UNITS = "units";
	public static final String BEAM_CENTER_X = "beam_center_x";
	public static final String BEAM_CENTER_Y = "beam_center_y";
	public static final String BEAM_CENTER = "beam centre";
	public static final String COUNT_TIME = "count_time";
	public static final String EXPOSURE_NAME = "exposure";
	public static final String MM = "mm";
	public static final String M = "m";
	
	
	public enum DiffractionMetaValue {
		PIXEL_SIZE,PIXEL_NUMBER,BEAM_CENTRE,DISTANCE,DETECTOR_ORIENTATION,ENERGY,PHI,EXPOSURE,BEAM_VECTOR
	}
	
	private String filePath;
	
	private Map<DiffractionMetaValue,Boolean> successMap = new HashMap<DiffractionMetaValue,Boolean>();
	
	public NexusDiffractionMetaReader(String filePath) {
		this.filePath = filePath;
		for (DiffractionMetaValue val : DiffractionMetaValue.values()) {
			successMap.put(val, false);
		}
	}

	/**
	 * Read the diffraction metadata from a Nexus file.
	 * Other methods on the class can be used to determine how complete the read is
	 * May return null
	 * 
	 * @param imageSize Size of the image the diffraction metadata is associated with in pixels (can be null)
	 */
	public IDiffractionMetadata getDiffractionMetadataFromNexus(int[] imageSize) {
		return getDiffractionMetadataFromNexus(imageSize, null, null, null);
	}
	
	/**
	 * Read the diffraction metadata from a Nexus file.
	 * Other methods on the class can be used to determine how complete the read is
	 * May return null
	 * 
	 * @param imageSize Size of the image the diffraction metadata is associated with in pixels (can be null)
	 * @param detprop Detector properties object to be populated from the nexus file
	 * @param diffcrys Detector properties object to be populated from the nexus file
	 */
	public IDiffractionMetadata getDiffractionMetadataFromNexus(int[] imageSize,DetectorProperties detprop, DiffractionCrystalEnvironment diffcrys) {
		return getDiffractionMetadataFromNexus(imageSize, detprop, diffcrys, null);
	}
	
	/**
	 * Read the diffraction metadata from a Nexus file.
	 * Other methods on the class can be used to determine how complete the read is
	 * May return null
	 * 
	 * @param imageSize Size of the image the diffraction metadata is associated with in pixels (can be null)
	 * @param detprop Detector properties object to be populated from the nexus file
	 * @param diffcrys Detector properties object to be populated from the nexus file
	 * @param xyPixelSize Guess at pixel size. Will be used if pixel size not read.
	 */
	public IDiffractionMetadata getDiffractionMetadataFromNexus(int[] imageSize,DetectorProperties detprop, DiffractionCrystalEnvironment diffcrys, double[] xyPixelSize) {
		
		if (!HierarchicalDataFactory.isHDF5(filePath)) return null;
		
		if (detprop == null) detprop = getInitialDetectorProperties();
		if (diffcrys == null) diffcrys = getInitialCrystalEnvironment();
		
		IHierarchicalDataFile hiFile = null;
		
		try {
			hiFile = HierarchicalDataFactory.getReader(filePath);

			String rootGroup = hiFile.getRoot();

			//Check only one entry in root - might not act on it at the moment but may be useful to know

			String nxBeam = getNXGroup(hiFile, rootGroup, "NXbeam");
			if (nxBeam != null) successMap.put(DiffractionMetaValue.ENERGY,updateEnergyFromBeam(hiFile, nxBeam,diffcrys));
			
			List<String> nxInstruments = getNXGroups(hiFile, rootGroup, NX_INSTRUMENT);
			
			String nxDetector = null;
			String nxInstrument = null;
			if (nxInstruments != null) {
				for (String inst : nxInstruments) {
					nxInstrument =  inst;
					nxDetector = findBestNXDetector(hiFile, inst, imageSize);
					if (nxDetector != null) {
						break;
					}
				}
			}
			
			//if no beam look in mono
			if (nxInstrument != null && !successMap.get(DiffractionMetaValue.ENERGY)) {
				String nxMono = getNXGroup(hiFile, nxInstrument, NX_MONOCHROMATOR);
				if (nxMono != null) successMap.put(DiffractionMetaValue.ENERGY,updateEnergy(hiFile, nxMono,diffcrys));
			}
			
			//For NCD results files
			if (nxDetector == null) {
				nxDetector= findNXDetectorByName(hiFile, rootGroup, "SectorIntegration");
			}

			
			//if no detectors with pixel in search the entire nxInstrument group
			if (nxDetector == null) {
				nxDetector = getNXGroup(hiFile, rootGroup, NX_DETECTOR);
			}
			
			if (nxDetector != null) {
				//populate the crystal environment
				populateFromNexus(hiFile, nxDetector, diffcrys);
				//populate detector properties
				populateFromNexus(hiFile, nxDetector, detprop, imageSize);
			}

			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (hiFile!= null)
				try {
					hiFile.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		if (!successMap.get(DiffractionMetaValue.PIXEL_SIZE) && xyPixelSize != null) {
			detprop.setHPxSize(xyPixelSize[0]);
			detprop.setVPxSize(xyPixelSize[1]);
		}
		
		return new DiffractionMetadata(filePath,detprop,diffcrys);
	}
	
	/**
	 * Check if the specified value was read from the Nexus file
	 */
	public boolean isMetadataEntryRead(DiffractionMetaValue entry) {
		if (successMap.containsKey(entry)) {
			return successMap.get(entry);
		}
		return false;
	}
	
	/**
	 * Have complete DetectorProperties and DiffractionCrystalEnvironment values been read
	 */
	public boolean isCompleteRead() {
		return !successMap.containsValue(false);
	}
	
	/**
	 * Have enough values to perform downstream calculations been read (ie exposure time not read)
	 */
	public boolean isPartialRead() {
		return successMap.get(DiffractionMetaValue.BEAM_CENTRE) &&
			   successMap.get(DiffractionMetaValue.DETECTOR_ORIENTATION) &&
			   successMap.get(DiffractionMetaValue.DISTANCE) &&
			   successMap.get(DiffractionMetaValue.ENERGY) &&
			   successMap.get(DiffractionMetaValue.PIXEL_NUMBER) &&
			   successMap.get(DiffractionMetaValue.PIXEL_SIZE);
	}
	
	public boolean isDetectorRead() {
		return successMap.get(DiffractionMetaValue.PIXEL_SIZE);

	}
	
	/**
	 * Were any values read from the Nexus file
	 */
	public boolean anyValuesRead() {
		return successMap.containsValue(true);
	}
	
	private String findBestNXDetector(IHierarchicalDataFile file, String nxInstrument, int[] imageSize) throws Exception {
		
		//Find nxDetectors in instrument
		// TODO should probably change to find data then locate correct
		// detector from image size
		List<String> nxDetectors = findNXDetectors(file, nxInstrument, DATA_NAME);

		if (nxDetectors == null || nxDetectors.isEmpty()) return null;
		
		if (imageSize == null) {
			//only one nxdetector or we dont know the image size
			//so just use the first one
			return nxDetectors.get(0);
		}
		for (String detector : nxDetectors) {
			String dataset = getDataset(file, detector, DATA_NAME);
			long[] dataShape;
			try {
				dataShape = HierarchicalDataUtils.getDims(file, dataset);
			} catch (Exception e) {
				continue;
			}
			if (dataShape == null) continue;
			boolean matchesX = false;
			boolean matchesY = false;
			for (long val : dataShape) {
				if (val == imageSize[0])
					matchesX = true;
				else if (val == imageSize[1])
					matchesY = true;
			}
			if (matchesX & matchesY) {
				return detector;
			}
		}
		
		return null;
	}
	
	private String getNXGroup(IHierarchicalDataFile file, String rootGroup, String nxAttribute) throws Exception {
		//Find NXinstrument (hopefully there is only one!)
		NexusFindGroupByAttributeText finder = new NexusFindGroupByAttributeText(file, nxAttribute,NexusUtils.NXCLASS);
		List<String> hOb = NexusUtils.nexusBreadthFirstSearch(file, finder, rootGroup, true);
		if (hOb.isEmpty() || !file.isGroup(hOb.get(0))) return null;
		return hOb.get(0);
	}
	
	private List<String> getNXGroups(IHierarchicalDataFile file, String rootGroup, String nxAttribute) throws Exception {
		//Find NXinstrument (hopefully there is only one!)
		NexusFindGroupByAttributeText finder = new NexusFindGroupByAttributeText(file, nxAttribute,NexusUtils.NXCLASS);
		List<String> hOb = NexusUtils.nexusBreadthFirstSearch(file, finder, rootGroup, false);
		if (hOb.isEmpty()) return null;
		
		List<String> groupList = new ArrayList<String>();
		
		for (String ob : hOb) {
			if (file.isGroup(ob)) groupList.add(ob);
		}
		
		if (groupList.isEmpty()) return null;
		
		return groupList;
	}
	
	
	private void populateFromNexus(IHierarchicalDataFile file, String nexusGroup, DiffractionCrystalEnvironment diffcrys) throws Exception {
		successMap.put(DiffractionMetaValue.EXPOSURE, updateExposureTime(file, nexusGroup,diffcrys));
		
		//Energy might not have been in NXmonochromator, if not look here
		if (!successMap.get(DiffractionMetaValue.ENERGY)) {
			successMap.put(DiffractionMetaValue.ENERGY,updateEnergy(file, nexusGroup, diffcrys));
		}
		
		boolean phi = updatePhiRange(file, nexusGroup, diffcrys) && updatePhiStart(file, nexusGroup, diffcrys);
		successMap.put(DiffractionMetaValue.PHI, phi);
	}
	
	private boolean updateEnergy(IHierarchicalDataFile file, String nexusGroup, DiffractionCrystalEnvironment diffcrys) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, ENERGY_NAME);
		if (dataset == null) dataset = getDataset(file, nexusGroup, WAVELENGTH);
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units.equals("keV")) {
				diffcrys.setWavelengthFromEnergykeV(ds.getDouble(0));
				return true;
				}
			if (units.contains("Angstrom")) {
				diffcrys.setWavelength(ds.getDouble(0));
				return true;
				}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean updateEnergyFromBeam(IHierarchicalDataFile file, String nexusGroup, DiffractionCrystalEnvironment diffcrys) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, IN_ENERGY_NAME);
		if (dataset == null) dataset = getDataset(file, nexusGroup, IN_WAVELENGTH);
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units.equals("keV")) {
				diffcrys.setWavelengthFromEnergykeV(ds.getDouble(0));
				return true;
				}
			if (units.contains("Angstrom")) {
				diffcrys.setWavelength(ds.getDouble(0));
				return true;
				}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
		
	private boolean updatePhiStart(IHierarchicalDataFile file, String nexusGroup, DiffractionCrystalEnvironment diffcry) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, "phi_start");
		if (dataset == null) return false;
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals("degrees")) {
				diffcry.setPhiStart(ds.getDouble(0));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	private boolean updatePhiRange(IHierarchicalDataFile file, String nexusGroup, DiffractionCrystalEnvironment diffcry) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, "phi_range");
		if (dataset == null) return false;
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals("degrees")) {
				diffcry.setPhiRange(ds.getDouble(0));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	private boolean updateExposureTime(IHierarchicalDataFile file, String nexusGroup, DiffractionCrystalEnvironment diffcrys) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, COUNT_TIME);
		if (dataset == null) dataset = getDataset(file, nexusGroup, EXPOSURE_NAME);
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals("s")) {
				diffcrys.setExposureTime(ds.getDouble(0));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
	
	private boolean updateBeamCentre(IHierarchicalDataFile file, String nexusGroup, DetectorProperties detprop) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, BEAM_CENTER);
		if (dataset == null) dataset = getDataset(file, nexusGroup, "beam_centre");
		if (dataset == null) return updateBeamCentreFromXY(file, nexusGroup, detprop);
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null || units.equals("pixels")) {
				detprop.setBeamCentreCoords(new double[] {ds.getDouble(0),ds.getDouble(1)});
				return true;
			} else if (units.equals(MM)) {
				detprop.setBeamCentreCoords(new double[] {ds.getDouble(0)*detprop.getVPxSize(),
						ds.getDouble(1)*detprop.getHPxSize()});
				return true;
			} else if (units.equals(M)) {
				detprop.setBeamCentreCoords(new double[] {ds.getDouble(0)*detprop.getVPxSize()*1000,
						ds.getDouble(1)*detprop.getHPxSize()*1000});
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	private boolean updateBeamCentreFromXY(IHierarchicalDataFile file, String nexusGroup, DetectorProperties detprop) throws Exception{
		
		String dataset = getDataset(file, nexusGroup, BEAM_CENTER_X);
		if (dataset == null) return false;
		
		double xCoord = Double.NaN;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null || units.equals("pixels")) {
				xCoord = ds.getDouble(0);
			}
			
			dataset = getDataset(file, nexusGroup, BEAM_CENTER_Y);
			if (dataset == null) return false;
			
			ds = getSet(file, dataset);
			units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null || units.equals("pixels")) {
				if (!Double.isNaN(xCoord)) {
					detprop.setBeamCentreCoords(new double[] {xCoord,ds.getDouble(0)});
					return true;
				}
				
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	private boolean updateDetectorDistance(IHierarchicalDataFile file, String nexusGroup, DetectorProperties detprop) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, CAMERA_LENGTH);
		if (dataset == null) dataset = getDataset(file, nexusGroup, DISTANCE_NAME);
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units.equals(MM)) {
				detprop.setBeamCentreDistance(ds.getDouble(0));
				return true;
			} else if (units.equals(M)) {
				detprop.setBeamCentreDistance(1000*ds.getDouble(0));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
	private void populateFromNexus(IHierarchicalDataFile file, String nxDetector, DetectorProperties detprop, int[] imageSize) throws Exception {
		successMap.put(DiffractionMetaValue.BEAM_VECTOR, updateBeamVector(file, nxDetector, detprop));
		successMap.put(DiffractionMetaValue.PIXEL_NUMBER, updatePixelNumber(file, nxDetector, detprop));
		if (!successMap.get(DiffractionMetaValue.PIXEL_NUMBER) && imageSize != null) {
			detprop.setPx(imageSize[1]);
			detprop.setPy(imageSize[0]);
			successMap.put(DiffractionMetaValue.PIXEL_NUMBER, true);
		}
		successMap.put(DiffractionMetaValue.PIXEL_SIZE,updatePixelSize(file, nxDetector,detprop));
		successMap.put(DiffractionMetaValue.DETECTOR_ORIENTATION, updateDetectorOrientation(file, nxDetector, detprop));
		successMap.put(DiffractionMetaValue.DISTANCE, updateDetectorDistance(file, nxDetector,detprop));
		successMap.put(DiffractionMetaValue.BEAM_CENTRE, updateBeamCentre(file, nxDetector,detprop));
	}
	
	private boolean updateDetectorOrientation(IHierarchicalDataFile file, String nexusGroup, DetectorProperties detprop) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, "detector_orientation");
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			if (ds.getSize() != 9) return false;
			if (ds instanceof DoubleDataset) {
				detprop.setOrientation(new Matrix3d(((DoubleDataset)ds).getData()));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	private boolean updateBeamVector(IHierarchicalDataFile file, String nexusGroup, DetectorProperties detprop) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, "beam_vector");
		if (dataset == null) return false;

		try {
			AbstractDataset ds = getSet(file, dataset);
			if (ds.getSize() != 3) return false;
			if (ds instanceof DoubleDataset) {
				detprop.setBeamVector(new Vector3d(((DoubleDataset)ds).getData()));
				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}
	
	private boolean updatePixelNumber(IHierarchicalDataFile file, String nexusGroup, DetectorProperties detprop) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, "x_pixel_number");
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals("pixels")) {
				detprop.setPx(ds.getInt(0));
			}
		} catch (Exception e) {
			return false;
		}
		
		
		dataset = getDataset(file, nexusGroup, "y_pixel_number");
		if (dataset == null) return false;
		
		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals("pixels")) {
				detprop.setPy(ds.getInt(0));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
	
	
	private boolean updatePixelSize(IHierarchicalDataFile file,String nexusGroup, DetectorProperties detprop) throws Exception {
		
		String dataset = getDataset(file, nexusGroup, "x_pixel_size");
		if (dataset == null) return false;

		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals(MM)) {detprop.setVPxSize(ds.getDouble(0));}
			else if (units.equals(M)) {detprop.setVPxSize(ds.getDouble(0)*1000);}
		} catch (Exception e) {
			return false;
		}

		dataset = getDataset(file, nexusGroup, "y_pixel_size");

		try {
			AbstractDataset ds = getSet(file, dataset);
			String units = NexusUtils.getNexusGroupAttributeValue(file, dataset, UNITS);
			if (units == null) return false;
			if (units.equals(MM)) {
				detprop.setHPxSize(ds.getDouble(0));
				return true;
			}
			else if (units.equals(M)) {
				detprop.setHPxSize(ds.getDouble(0)*1000);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
		return false;
		
	}
	
	private String getDataset(IHierarchicalDataFile file, String group, String name) throws Exception {
		
		NexusFindDatasetByName dataFinder = new NexusFindDatasetByName(file, name);
		List<String> hOb = NexusUtils.nexusBreadthFirstSearch(file, dataFinder,group, false);
		if (hOb.isEmpty() || !file.isDataset(hOb.get(0))) return null;
		
		return hOb.get(0);
	}
	
	
	private List<String> findNXDetectors(final IHierarchicalDataFile file, String nxInstrument, String childNameContains) throws Exception {
		
		final String groupText = NX_DETECTOR;
		final String childText = childNameContains;
		
		IFindInNexus findWithChild = new IFindInNexus() {
			
			@Override
			public boolean inNexus(String nexusObjectPath) {
				
				try {
					if (file.isGroup(nexusObjectPath)) {
						String attrNexusObject = NexusUtils.getNexusGroupAttributeValue(file, nexusObjectPath,NexusUtils.NXCLASS);
						if (attrNexusObject != null && attrNexusObject.toLowerCase().equals(groupText.toLowerCase())) {
							for (String obPath : file.memberList(nexusObjectPath)) {
								
								final String name = obPath.substring(obPath.lastIndexOf('/')+1);
								if (name.toLowerCase().contains((childText.toLowerCase()))) {
									return true;
								}
							}
						}
					}
				} catch (Exception ne) {
					return false;
				}
				
				return false;
			}
		};
		
		List<String> hOb = NexusUtils.nexusBreadthFirstSearch(file, findWithChild, nxInstrument, false);
		
		List<String> detectorGroups = new ArrayList<String>(hOb.size());
		
		for (String path : hOb) {
			if (file.isGroup(path)) {
				detectorGroups.add(path);
			}
		}
		return detectorGroups;
	}
	
	private String findNXDetectorByName(final IHierarchicalDataFile file, String nxInstrument, final String name) throws Exception {
		
		IFindInNexus findWithName = new IFindInNexus() {

			@Override
			public boolean inNexus(String nexusObject) {
				try {
					if (file.isGroup(nexusObject)) {
						String attr = NexusUtils.getNexusGroupAttributeValue(file, nexusObject, NexusUtils.NXCLASS);
						if (attr.toLowerCase().equals(NX_DETECTOR.toLowerCase())) {
							final String cname = nexusObject.substring(nexusObject.lastIndexOf('/')+1);
							if  (cname.toLowerCase().contains((name.toLowerCase()))) return true;
						}
					}
				} catch (Exception e) {
					return false;
				}
				return false;
			}
		};
		
		List<String> hOb = NexusUtils.nexusBreadthFirstSearch(file, findWithName, nxInstrument, true);
		if (hOb == null || hOb.isEmpty()) return null;


		return hOb.get(0);
	}
	
	private DetectorProperties getInitialDetectorProperties() {
		//Try to return harmless but not physical values
		return DetectorProperties.getDefaultDetectorProperties(1000,1000);
	}
	
	private DiffractionCrystalEnvironment getInitialCrystalEnvironment() {
		//Try to return harmless but not physical values
		return new DiffractionCrystalEnvironment(1, 0, 0, 1);
	}
	
	private String getString(final Dataset set) throws Exception {
			return ((String[])set.getData())[0];
	}
	
	private AbstractDataset getSet(IHierarchicalDataFile file, final String path) throws Exception {

		if (!file.isDataset(path)) return null;
		
		Dataset       set = (Dataset)file.getData(path);
		final Object  val = set.read();
		
		long[] dataShape = HierarchicalDataUtils.getDims(set);
		
		final int[] intShape  = getInt(dataShape);
         
		AbstractDataset ret = null;
        if (val instanceof byte[]) {
        	ret = new ByteDataset((byte[])val, intShape);
        } else if (val instanceof short[]) {
        	ret = new ShortDataset((short[])val, intShape);
        } else if (val instanceof int[]) {
        	ret = new IntegerDataset((int[])val, intShape);
        } else if (val instanceof long[]) {
        	ret = new LongDataset((long[])val, intShape);
        } else if (val instanceof float[]) {
        	ret = new FloatDataset((float[])val, intShape);
        } else if (val instanceof double[]) {
        	ret = new DoubleDataset((double[])val, intShape);
        } else {
        	throw new Exception("Cannot deal with data type "+set.getDatatype().getDatatypeDescription());
        }
        
		if (set.getDatatype().isUnsigned()) {
			switch (ret.getDtype()) {
			case uk.ac.diamond.scisoft.analysis.dataset.Dataset.INT32:
				ret = new LongDataset(ret);
				DatasetUtils.unwrapUnsigned(ret, 32);
				break;
			case uk.ac.diamond.scisoft.analysis.dataset.Dataset.INT16:
				ret = new IntegerDataset(ret);
				DatasetUtils.unwrapUnsigned(ret, 16);
				break;
			case uk.ac.diamond.scisoft.analysis.dataset.Dataset.INT8:
				ret = new ShortDataset(ret);
				DatasetUtils.unwrapUnsigned(ret, 8);
				break;
			}
		}
        return ret;
	}
	
	private int[] getInt(long[] longShape) {
		final int[] intShape  = new int[longShape.length];
		for (int i = 0; i < intShape.length; i++) intShape[i] = (int)longShape[i];
		return intShape;
	}
	
	public String getFilePath() {
		return filePath;
	}

}
