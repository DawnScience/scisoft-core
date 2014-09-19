/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.metadata.IIOMetadata;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.IMetaLoader;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;

/**
 * This class will read the MAR 300 image file type
 * 
 * This class is a sub class of the JavaImageLoader class. This reads the image
 * data. The following method initially reads the default tiff header while the
 * following reads the MAR specific headers. All information is read into a
 * hashmap as string object pairs. The MAR header is read as a stream of bytes
 * the value of which is determined in a C header file. Most values are 32bit ints.
 */
public class MARLoader extends TIFFImageLoader implements IMetaLoader, Serializable {
	private static final int MAR_TAG_NUMBER = 34710;
	static final int MAX_IMAGES = 9;
	static final int MAR_HEADER_SIZE = 3072;
	private boolean littleEndian;
	private Map<String, Serializable> metadataTable = new HashMap<String, Serializable>();
	private DiffractionMetadata diffMetadata;
	
	/**
	 * @param FileName
	 */
	public MARLoader(String FileName) {
		super(FileName);
	}

	@Override
	protected Map<String, Serializable> createMetadata(IIOMetadata imageMetadata) throws ScanFileHolderException {
		long offset = -1;
		try {
			TIFFDirectory tiffDir = TIFFDirectory.createFromMetadata(imageMetadata);

			TIFFField[] tiffField = tiffDir.getTIFFFields();

			for (TIFFField tfield : tiffField) {
				if (tfield.getTagNumber() == MAR_TAG_NUMBER) {
					offset = tfield.getAsLong(0);
					continue;
				}
				metadataTable.put(tfield.getTag().getName(), tfield.getValueAsString(0));
			}
		} catch (Exception e) {
			throw new ScanFileHolderException("Problem loading tiff header metadata in the MAR Loader class", e);
		}
		if (offset < 0) {
			throw new ScanFileHolderException("Cannot find MAR header offset as TIFF header tag is missing");
		}

		File f = new File(fileName);

		InputStream is = null;
		try {
			is = new FileInputStream(f);
			byte[] hbd = new byte[MAR_HEADER_SIZE];
			is.skip(offset);
			is.read(hbd, 0, MAR_HEADER_SIZE);

			int poss = 28; // 4+16+4+4
			littleEndian = isLittleEndian(hbd, poss);
			metadataTable.put("headerByteOrderLE", littleEndian);

			// read header information 
			// header format parameters 256 bytes
			poss = 0;
			metadataTable.put("headerType", getInteger(hbd, poss)); // flag for header type (can be used as magic number)
			poss += 4;

			metadataTable.put("headerName", Utils.getString(hbd, poss, 16)); // header name (MMX)
			poss += 16;

			metadataTable.put("headerMajorVersion", getInteger(hbd, poss)) ;// header_major_version (n.)
			poss += 4;
			metadataTable.put("headerMinorVersion", getInteger(hbd, poss)); // header_minor_version (.n)
			poss += 4;

			poss += 4; // skip header byte order

			boolean dataByteOrder = false;
			try {
				dataByteOrder = isLittleEndian(hbd, poss);
			} catch (Exception e) {
				// Do nothing--data should be read in superclass
				// System.out.println("Unknown dataByteOrder");
			}
			metadataTable.put("dataByteOrder", dataByteOrder); // BIG_ENDIAN (Motorola,MIPS); LITTLE_ENDIAN (DEC, Intel)
			poss += 4;

			metadataTable.put("headerSize", getInteger(hbd, poss)); // in bytes
			poss += 4;

			metadataTable.put("frameType", getInteger(hbd, poss)); // flag for frame type
			poss += 4;

			metadataTable.put("magicNumber", getInteger(hbd, poss)); // to be used as a flag - usually to indicate new file
			poss += 4;

			metadataTable.put("compressionType", getInteger(hbd, poss)); // type of image compression
			poss += 4;

			metadataTable.put("compression1", getInteger(hbd, poss)); // compression parameter 1
			poss += 4;
			metadataTable.put("compression2", getInteger(hbd, poss)); // compression parameter 2
			poss += 4;
			metadataTable.put("compression3", getInteger(hbd, poss)); // compression parameter 3
			poss += 4;
			metadataTable.put("compression4", getInteger(hbd, poss)); // compression parameter 4
			poss += 4;
			metadataTable.put("compression5", getInteger(hbd, poss)); // compression parameter 5
			poss += 4;
			metadataTable.put("compression6", getInteger(hbd, poss)); // compression parameter 6
			poss += 4;

			metadataTable.put("nHeaders", getInteger(hbd, poss)); // total number of headers
			poss += 4;

			metadataTable.put("nFast", getInteger(hbd, poss)); // number of pixels in one line
			poss += 4;
			metadataTable.put("nSlow", getInteger(hbd, poss)); // number of lines in image
			poss += 4;
			metadataTable.put("depth", getInteger(hbd, poss)); // number of bytes per pixel
			poss += 4;
			metadataTable.put("recordLength", getInteger(hbd, poss)); // number of pixels between successive rows
			poss += 4;
			metadataTable.put("signifBits", getInteger(hbd, poss)); // true depth of data, in bits
			poss += 4;
			metadataTable.put("dataType", getInteger(hbd, poss)); // (signed,unsigned,float...)
			poss += 4;
			metadataTable.put("saturatedValue", getInteger(hbd, poss)); // value marks pixel as saturated
			poss += 4;

			metadataTable.put("sequence", getInteger(hbd, poss)); // TRUE or FALSE
			poss += 4;
			metadataTable.put("nImages", getInteger(hbd, poss)); // total number of images - size of each is nfast*(nslow/nimages)
			poss += 4;

			metadataTable.put("origin", getInteger(hbd, poss)); // corner of origin
			poss += 4;
			metadataTable.put("orientation", getInteger(hbd, poss)); // direction of fast axis
			poss += 4;
			metadataTable.put("viewDirection", getInteger(hbd, poss)); // direction to view frame
			poss += 4;

			metadataTable.put("overflowLocation", getInteger(hbd, poss)); // FOLLOWING_HEADER, FOLLOWING_DATA
			poss += 4;
			metadataTable.put("over8Bits", getInteger(hbd, poss)); // # of pixels with counts > 255
			poss += 4;
			metadataTable.put("over16Bits", getInteger(hbd, poss)); // # of pixels with count > 65535
			poss += 4;

			metadataTable.put("multiplexed", getInteger(hbd, poss)); // multiplex flag
			poss += 4;
			metadataTable.put("numFastImages", getInteger(hbd, poss));// # of images in fast direction
			poss += 4;
			metadataTable.put("numSlowImages", getInteger(hbd, poss)); // # of images in slow direction
			poss += 4;

			metadataTable.put("backgroundApplied", getInteger(hbd, poss)); // flags correction has been applied - hold magic number?
			poss += 4;
			metadataTable.put("biasApplied", getInteger(hbd, poss)); // flags correction has been applied - hold magic number ?
			poss += 4;
			metadataTable.put("flatFieldApplied", getInteger(hbd, poss)); // flags correction has been applied - hold magic number ?
			poss += 4;
			metadataTable.put("distortionApplied", getInteger(hbd, poss)); // flags correction has been applied - hold magic number ?
			poss += 4;

			metadataTable.put("originalHeaderType", getInteger(hbd, poss)); // Header/frame type from file that frame is read from
			poss += 4;
			metadataTable.put("fileSaved", getInteger(hbd, poss)); // Header/frame type from file that frame is read from
			poss += 4;

			// 15 more items missed out

			poss += 80; // move forward to ignore reserve1 (64-40)*sizeof(int32)-16

			assert poss == 256;
			// Data statistics (128)
			int[] totalCounts = new int[2];
			totalCounts[0] = getInteger(hbd, poss);
			poss += 4;
			totalCounts[1] = getInteger(hbd, poss);
			poss += 4;
			metadataTable.put("totalCounts", totalCounts);

			int[] specialCounts1 = new int[2];
			specialCounts1[0] = getInteger(hbd, poss);
			poss += 4;
			specialCounts1[1] = getInteger(hbd, poss);
			poss += 4;
			metadataTable.put("specialCounts1", specialCounts1);
			int[] specialCounts2 = new int[2];
			specialCounts2[0] = getInteger(hbd, poss);
			poss += 4;
			specialCounts2[1] = getInteger(hbd, poss);
			poss += 4;
			metadataTable.put("specialCounts2", specialCounts1);

			metadataTable.put("min", getInteger(hbd, poss));
			poss += 4;
			metadataTable.put("max", getInteger(hbd, poss));
			poss += 4;
			metadataTable.put("mean", getInteger(hbd, poss));
			poss += 4;
			metadataTable.put("rms", getInteger(hbd, poss));
			poss += 4;
			metadataTable.put("p10", getInteger(hbd, poss));
			poss += 4;
			metadataTable.put("p90", getInteger(hbd, poss));
			poss += 4;
			metadataTable.put("statsUpToDate", getInteger(hbd, poss));
			poss += 4;
			int[] pixelNoise = new int[MAX_IMAGES];
			for (int i = 0; i < MAX_IMAGES; i++) {
				pixelNoise[i] = getInteger(hbd, poss);
				poss += 4;
			}
			metadataTable.put("pixelNoise", pixelNoise);
			
			poss += (32 - 13 - MAX_IMAGES) * 4; // move forward to ignore reserve 2 32-13-MAX_IMAGES*sizeof(int32)

			assert poss == 384;

			// more Statistics (256)
			// this can be sample changer info too
			int[] percentile = new int[128];
			for (int i = 0; i < 128; i++) {
				percentile[i] = getShort(hbd, poss);
				poss += 2;
			}

			assert poss == 640;

			// goniostat parameters (128)
			metadataTable.put("xtalToDetector", getInteger(hbd, poss) / 1000.0); // 1000*distance in millimetres
			poss += 4;
			metadataTable.put("beamX", getInteger(hbd, poss) / 1000.0); // 1000*x beam position (pixels)
			poss += 4;
			metadataTable.put("beamY", getInteger(hbd, poss) / 1000.0); // 1000*y beam position (pixels)
			poss += 4;
			metadataTable.put("intergrationTime", getInteger(hbd, poss) / 1000.0); // integration time in milliseconds
			poss += 4;
			metadataTable.put("exposureTime", getInteger(hbd, poss) / 1000.0); // exposure time in milliseconds
			poss += 4;
			metadataTable.put("readoutTime", getInteger(hbd, poss) / 1000.0); // readout time in milliseconds
			poss += 4;
			metadataTable.put("nReads", getInteger(hbd, poss) / 1000.0); // number of readouts to get this image
			poss += 4;
			metadataTable.put("start2theta", getInteger(hbd, poss) / 1000.0); // 1000*two_theta angle
			poss += 4;
			metadataTable.put("startOmega", getInteger(hbd, poss) / 1000.0); // 1000*omega angle
			poss += 4;
			metadataTable.put("startChi", getInteger(hbd, poss) / 1000.0); // 1000*chi angle
			poss += 4;
			metadataTable.put("startKappa", getInteger(hbd, poss) / 1000.0); // 1000*kappa angle
			poss += 4;
			metadataTable.put("startPhi", getInteger(hbd, poss) / 1000.0); // 1000*phi angle
			poss += 4;
			metadataTable.put("startDelta", getInteger(hbd, poss) / 1000.0); // 1000*delta angle
			poss += 4;
			metadataTable.put("startGamma", getInteger(hbd, poss) / 1000.0); // 1000*gamma angle
			poss += 4;
			metadataTable.put("startXtalToDetector", getInteger(hbd, poss) / 1000.0); // 1000*distance in mm (dist in um)
			poss += 4;
			metadataTable.put("stop2theta", (getInteger(hbd, poss) / 1000.0)); // 1000*two_theta angle
			poss += 4;
			metadataTable.put("stopOmega", getInteger(hbd, poss) / 1000.0); // 1000*omega angle
			poss += 4;
			metadataTable.put("stopChi", getInteger(hbd, poss) / 1000.0); // 1000*chi angle
			poss += 4;
			metadataTable.put("stopKappa", getInteger(hbd, poss) / 1000.0); // 1000*kappa angle
			poss += 4;
			metadataTable.put("stopPhi", getInteger(hbd, poss) / 1000.0); // 1000*phi angle
			poss += 4;
			metadataTable.put("stopDelta", getInteger(hbd, poss) / 1000.0); // 1000*delta angle
			poss += 4;
			metadataTable.put("stopGamma", getInteger(hbd, poss) / 1000.0); // 1000*gamma angle
			poss += 4;
			metadataTable.put("stopXtalToDetector", getInteger(hbd, poss) / 1000.0); // 1000*distance in mm (dist in  um)
			poss += 4;
			metadataTable.put("rotationAxis", getInteger(hbd, poss)); // active rotation axis
			poss += 4;
			metadataTable.put("rotationRange", getInteger(hbd, poss) / 1000.0); // 1000*rotation angle
			poss += 4;
			metadataTable.put("detectorRotateX", getInteger(hbd, poss) / 1000.0); // 1000*rotation of detector around X
			poss += 4;
			metadataTable.put("detectorRotateY", getInteger(hbd, poss) / 1000.0); // 1000*rotation of detector around Y
			poss += 4;
			metadataTable.put("detectorRotateZ", getInteger(hbd, poss) / 1000.0); // 1000*rotation of detector around  Z
			poss += 4;

			// ignored total_dose

			poss += (32 - 28) * 4; // skip forward to ignore reserve 3 32-28*sizeof(uint32)

			assert poss == 768;

			// Detector parameters (128)
			metadataTable.put("detectorType", getInteger(hbd, poss)); // detector type

			poss += 4;
			metadataTable.put("pixelSizeX", getInteger(hbd, poss)); // pixel size (nanometers)

			poss += 4;
			metadataTable.put("pixelSizeY", getInteger(hbd, poss)); // pixel size (nanometers)
			poss += 4;
			metadataTable.put("meanBias", getInteger(hbd, poss) / 1000.0); // 1000*mean bias value

			poss += 4;
			metadataTable.put("photonPer100ADU", getInteger(hbd, poss)); // photons/100 ADUs
			poss += 4;

			int[] measuredBias = new int[MAX_IMAGES];
			for (int i = 0; i < MAX_IMAGES; i++) {
				measuredBias[i] = getInteger(hbd, poss); // 1000*mean bias value for each image
				poss += 4;
			}
			metadataTable.put("measuredBias", measuredBias);

			int[] measuredTemperature = new int[MAX_IMAGES];
			for (int i = 0; i < MAX_IMAGES; i++) {
				measuredTemperature[i] = getInteger(hbd, poss); // Temperature of each detector in milliKelvins
				poss += 4;
			}
			metadataTable.put("measuredTemperature", measuredTemperature);

			int[] measuredPressure = new int[MAX_IMAGES];
			for (int i = 0; i < MAX_IMAGES; i++) {
				measuredPressure[i] = getInteger(hbd, poss); // Pressure of each chamber in microTorr
				poss += 4;
			}

			metadataTable.put("measuredPressure", measuredPressure);

			// currentOffset += 32 - 5 + 3 * MAX_IMAGES * 4; // 32-5+3*MAX_IMAGES*sizeof(int23)
			// reserve 4 retired to make room for measured pressure and measured temperature

			assert poss == 896;

			// X-ray source and optics parameters (128)
			// X-ray source parameters 
			metadataTable.put("sourceType", getInteger(hbd, poss)); // (code) - target, synch. etc
			poss += 4;
			metadataTable.put("sourceDx", getInteger(hbd, poss));// Optics param. - (size microns)
			poss += 4;
			metadataTable.put("sourceDy", getInteger(hbd, poss)); // Optics param. - (size microns)
			poss += 4;
			metadataTable.put("sourceWavelength", getInteger(hbd, poss));
			// wavelength (femtoMeters)
			poss += 4;
			metadataTable.put("sourcePower", getInteger(hbd, poss)); // (Watts)
			poss += 4;
			metadataTable.put("sourceVoltage", getInteger(hbd, poss)); // (Volts)
			poss += 4;
			metadataTable.put("sourceCurrent", getInteger(hbd, poss)); // (microAmps)
			poss += 4;
			metadataTable.put("sourceBias", getInteger(hbd, poss)); // (Volts)
			poss += 4;
			metadataTable.put("sourcePolarizationX", getInteger(hbd, poss)); // ()
			poss += 4;
			metadataTable.put("sourcePolarizationY", getInteger(hbd, poss));// ()
			poss += 4;

			poss += 16; // ignore reserve_source 4*sizeof(int32)

			// X-ray optics parameters
			metadataTable.put("opticsType", getInteger(hbd, poss)); // Optics type (code)
			poss += 4;
			metadataTable.put("opticsDx", getInteger(hbd, poss)); // Optics param. - (size microns)
			poss += 4;
			metadataTable.put("opticsDy", getInteger(hbd, poss)); // Optics param. - (size microns)
			poss += 4;
			metadataTable.put("opticsWavelength", getInteger(hbd, poss)); // Optics param. - (size microns)
			poss += 4;
			metadataTable.put("opticsDispersion", getInteger(hbd, poss)); // Optics param. - (*10E6)
			poss += 4;
			metadataTable.put("opticsCrossfireX", getInteger(hbd, poss)); // Optics param. - (microRadians)
			poss += 4;
			metadataTable.put("opticsCrossfireY", getInteger(hbd, poss)); // Optics param. - (microRadians)
			poss += 4;
			metadataTable.put("opticsAngle", getInteger(hbd, poss)); // Optics param. - (microRadians)

			poss += 4;
			metadataTable.put("opticsPolarizationX", getInteger(hbd, poss)); // ()
			poss += 4;
			metadataTable.put("opticsPolarizationY", getInteger(hbd, poss)); // ()
			poss += 4;

			poss += 16; // 4*sizeof(int32) reserve_optics
			poss += 16; // 32-28*sizeof(int32) reserve5

			assert poss == 1024;

			// File parameters 1024 bytes
			metadataTable.put("filetitle", Utils.getString(hbd, poss, 128)); // Title
			poss += 128;
			metadataTable.put("filepath", Utils.getString(hbd, poss, 128)); // path  name  for  data  file
			poss += 128;
			metadataTable.put("filename", Utils.getString(hbd, poss, 64)); // name of data file
			poss += 64;
			metadataTable.put("AcquireTimestamp", Utils.getString(hbd, poss, 32)); // date and time of acquisition
			poss += 32;
			metadataTable.put("headerTimestamp", Utils.getString(hbd, poss, 32)); // date and time of header update
			poss += 32;
			metadataTable.put("saveTimestamp", Utils.getString(hbd, poss, 32)); // date and time file saved
			poss += 32;
			metadataTable.put("fileComment", Utils.getString(hbd, poss, 512)); // comments
			poss += 512;

			poss += 96; // 1024-(128+128+64+3*32+512) // reserve 6

			assert poss == 2048;

			metadataTable.put("datasetComments", Utils.getString(hbd, poss, 512)); // comments - can be used as desired
			poss += 512;

			metadataTable.put("userData", Utils.getString(hbd, poss, 512)); // reserved for user definable data - will not be used by Mar!
			poss += 512;

			assert poss == MAR_HEADER_SIZE;
		} catch (Exception e) {
			throw new ScanFileHolderException("Problem loading MAR metadata", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new ScanFileHolderException("Problem closing MAR file", e);
				}
			}
		}

		return createGDAMetadata();
	}

	/**
	 * Check if four bytes from MAR file indicate little endian byte ordering 
	 * @param bytes
	 * @param pos
	 * @return true if little endian
	 * @throws ScanFileHolderException
	 */
	public static boolean isLittleEndian(byte[] bytes, int pos) throws ScanFileHolderException {
		int ba = bytes[pos++];
		int bb = bytes[pos++];
		int bc = bytes[pos++];
		int bd = bytes[pos];
		if (Utils.leInt(ba, bb, bc, bd) == 1234)
			return true;
		if (Utils.beInt(ba, bb, bc, bd) == 4321)
			return false;
		throw new ScanFileHolderException("Byte order unknown!");
	}

	private int getInteger(byte[] bytes, int pos) {
		return littleEndian ? Utils.leInt(bytes[pos], bytes[pos + 1], bytes[pos + 2], bytes[pos + 3]) :
			Utils.beInt(bytes[pos], bytes[pos + 1], bytes[pos + 2], bytes[pos + 3]);
	}

	private int getShort(byte[] bytes, int pos) {
		return littleEndian ? Utils.leInt(bytes[pos], bytes[pos + 1]) :
			Utils.beInt(bytes[pos], bytes[pos + 1]);
	}

	private Map<String, Serializable> createGDAMetadata() throws ScanFileHolderException {
		Map<String, Serializable> GDAMetadata = new HashMap<String, Serializable>();

		try {

			double pixelSizeX = ((Integer) getMetadataValue("pixelSizeX")).intValue() / 1.0e6;
			int imageWidth = Integer.parseInt((String) getMetadataValue("ImageWidth"));
			double beamX = ((Double) getMetadataValue("beamX")).doubleValue();

			double pixelSizeY = (((Integer) getMetadataValue("pixelSizeY")).intValue() / 1.0e6);
			int imageLength = Integer.parseInt((String) getMetadataValue("ImageLength"));
			double beamY = ((Double) getMetadataValue("beamY")).doubleValue();

			double distance = ((Double) getMetadataValue("xtalToDetector")).doubleValue();
			// NXGeometery:NXtranslation
			double[] detectorOrigin = { imageWidth * pixelSizeX - beamX, imageLength * pixelSizeY - beamY, distance };

			GDAMetadata.put("NXdetector:NXgeometry:NXtranslation", detectorOrigin);
			GDAMetadata.put("NXdetector:NXgeometry:NXtranslation@units", "metre");

			// NXGeometery:NXOrientation
			double detectorRotationX = ((Double) getMetadataValue("detectorRotateX")).doubleValue();
			double detectorRotationY = ((Double) getMetadataValue("detectorRotateY")).doubleValue();
			double detectorRotationZ = ((Double) getMetadataValue("detectorRotateZ")).doubleValue();

			Matrix3d rotX = new Matrix3d();
			rotX.rotX(detectorRotationX);
			Matrix3d rotY = new Matrix3d();
			rotY.rotY(detectorRotationY);
			Matrix3d rotZ = new Matrix3d();
			rotZ.rotZ(detectorRotationZ);

			Matrix3d euler = new Matrix3d();
			euler.mul(rotX, rotY);
			euler.mul(rotZ);
			double[] tmp = new double[3];
			double[] tmp1 = new double[3];
			double[] directionCosine = new double[6];
			euler.getColumn(0, tmp);
			euler.getColumn(1, tmp1);

			for (int i = 0; i < directionCosine.length / 2; i++) {
				directionCosine[i] = tmp[i];
				directionCosine[3 + i] = tmp1[i];
			}
			GDAMetadata.put("NXdetector:NXgeometry:NXorientation", directionCosine);

			// NXGeometery:XShape (shape from origin (+x, +y, +z,0, 0, 0) > x,y,0,0,0,0)
			double[] detectorShape = { imageWidth * pixelSizeX, imageLength * pixelSizeY, 0, 0, 0, 0 };
			GDAMetadata.put("NXdetector:NXgeometry:NXshape", detectorShape);
			GDAMetadata.put("NXdetector:NXgeometry:NXshape@units", "milli*metre");

			// NXGeometery:NXFloat
			GDAMetadata.put("NXdetector:x_pixel_size", pixelSizeX);
			GDAMetadata.put("NXdetector:x_pixel_size@units", "milli*metre");
			GDAMetadata.put("NXdetector:y_pixel_size", pixelSizeY);
			GDAMetadata.put("NXdetector:y_pixel_size@units", "milli*metre");
			
			// "NXmonochromator:wavelength"
			double lambda = ((Integer) getMetadataValue("sourceWavelength")).intValue() / 100000.0;
			GDAMetadata.put("NXmonochromator:wavelength", lambda);
			GDAMetadata.put("NXmonochromator:wavelength@units", "Angstrom");

		
			// oscillation range
			double startOmega =  Double.parseDouble( getMetadataValue("startOmega").toString());
			double rangeOmega = startOmega - Double.parseDouble(getMetadataValue("stopOmega").toString());
	    	GDAMetadata.put("NXsample:rotation_start",startOmega);
			GDAMetadata.put("NXsample:rotation_start@units","degree");
			GDAMetadata.put("NXsample:rotation_range",rangeOmega);
			GDAMetadata.put("NXsample:rotation_range@units", "degree");
			
			//Exposure time
			GDAMetadata.put("NXsample:exposure_time", getMetadataValue("exposureTime"));
			GDAMetadata.put("NXsample:exposure_time@units", "seconds");
			createMetadata(detectorOrigin, imageLength, imageWidth, pixelSizeX, pixelSizeY, euler, lambda, startOmega, rangeOmega);
		} catch (NumberFormatException e) {
			throw new ScanFileHolderException("There was a problem parsing numerical value from string", e);
		} catch (ScanFileHolderException e) {
			throw new ScanFileHolderException("A problem occoured parsing the internal metatdata into the GDA metadata", e);
		}
		return GDAMetadata;
	}

	private void createMetadata(double[] detectorOrigin, int imageLength, int imageWidth, double pixelSizeX, double pixelSizeY, Matrix3d euler, double lambda, double startOmega, double rangeOmega) throws ScanFileHolderException {
		DetectorProperties detProps = new DetectorProperties(new Vector3d(detectorOrigin), imageLength, imageWidth, pixelSizeX, pixelSizeY, euler);
		DiffractionCrystalEnvironment diffEnv = new DiffractionCrystalEnvironment(lambda, startOmega, rangeOmega, (Double) getMetadataValue("exposureTime"));

		diffMetadata = new DiffractionMetadata(fileName, detProps, diffEnv);
		diffMetadata.setMetadata(createStringMap());
	}

	private Serializable getMetadataValue(String key) throws ScanFileHolderException {
		try {
			Serializable value = metadataTable.get(key);
			return value;
		} catch (Exception e) {
			throw new ScanFileHolderException("The keyword " + key + " was not found in the MAR header", e);
		}
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		boolean tmp = loadMetadata;
		try {
			loadMetadata = true;
			loadFile(); // TODO Implement a method for loading meta which does not load the image
		} finally {
			loadMetadata = tmp;
		}
	}

	private Map<String, String> createStringMap() {
		Map<String, String> ret = new HashMap<String,String>(7);
		for (String key : metadataTable.keySet()) {
			ret.put(key, metadataTable.get(key).toString().trim());
		}
		return ret;
	}

	@Override
	public IMetadata getMetadata() {
		return diffMetadata;
	}
	
	@Override
	public IMetadata getMetaData(Dataset data) {
		if (metadata == null) {
			if (data!=null) return data.getMetadata(); 
			return null;
		}
		return getMetadata();
	}
}
