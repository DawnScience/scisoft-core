/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Collection;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.media.jai.PlanarImage;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;

/**
 * This class saves a DataHolder as a TIFF image file.
 */
public class TIFFImageSaver extends JavaImageSaver {

	private static final String FORMAT_NAME = "tiff";

	/**
	 * @param filename
	 */
	public TIFFImageSaver(String filename) {
		this(filename, false);
	}

	/**
	 * @param filename
	 * @param numBits
	 */
	public TIFFImageSaver(String filename, int numBits) {
		super(filename, FORMAT_NAME, numBits, true);
	}

	/**
	 * @param filename
	 * @param numBits
	 * @param asUnsigned
	 */
	public TIFFImageSaver(String filename, int numBits, boolean asUnsigned) {
		super(filename, FORMAT_NAME, numBits, asUnsigned);
	}

	/**
	 * @param filename
	 * @param asFloat
	 */
	public TIFFImageSaver(String filename, boolean asFloat) {
		super(filename, FORMAT_NAME, asFloat ? 33 : 16, true);
	}

	@Override
	protected boolean writeImageLocked(RenderedImage image, String fileType, File f, IDataHolder dh) throws Exception {
		
		// Write meta data to image header
		if (image instanceof PlanarImage) {
			PlanarImage pi = (PlanarImage)image;
			if (dh.getFilePath()!=null) {
				pi.setProperty("originalDataSource", dh.getFilePath());
			}

			IMetadata meta = dh.getMetadata();
			if (meta != null) {
				Collection<String> dNames = meta.getMetaNames();
				if (dNames!=null) for (String name : dNames) {
					pi.setProperty(name, meta.getMetaValue(name));
				}
			}
		}

		// special case to force little endian
		ImageWriter writer = ImageIO.getImageWritersByFormatName(FORMAT_NAME).next();
		IIOMetadata streamMeta = writer.getDefaultStreamMetadata(null);
		String metadataFormatName = streamMeta.getNativeMetadataFormatName();

		// Create the new stream metadata object for new byte order
		IIOMetadataNode tree = new IIOMetadataNode(metadataFormatName);
		IIOMetadataNode endianNode = new IIOMetadataNode("ByteOrder");
		endianNode.setAttribute("value", "LITTLE_ENDIAN");
		tree.appendChild(endianNode);
		streamMeta.setFromTree(metadataFormatName, tree);

		ImageOutputStream stream = ImageIO.createImageOutputStream(f);
		writer.setOutput(stream);
		writer.write(streamMeta, new IIOImage(image, null, null), null);
		return true;
	}
}
