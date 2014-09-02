/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

			IMetaData meta = dh.getMetadata();
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
