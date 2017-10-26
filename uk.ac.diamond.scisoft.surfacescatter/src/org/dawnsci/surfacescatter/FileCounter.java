package org.dawnsci.surfacescatter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang.StringUtils;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.dataset.Dataset;

public class FileCounter {

	private boolean good = false;

	public FileCounter(String[] filepaths, String paramFileString) {
		this.good = checkParamFileLongEnough(filepaths, paramFileString);
	}

	public boolean getGood() {
		return good;
	}

	private boolean checkParamFileLongEnough(String[] filepaths, String paramFileString) {

		int i1 = imageCounter(filepaths);
		int i2 = frameCounter(paramFileString);

		return (i1 == i2 && i1 != 0);
	}

	private int imageCounter(String[] filepaths) {

		String[][] tifNamesArray = new String[filepaths.length][];

		try {

			for (int id = 0; id < filepaths.length; id++) {

				Path from = Paths.get(filepaths[id]);

				Charset charset = StandardCharsets.UTF_8;

				String content = new String(Files.readAllBytes(from), charset);

				String[] tifNames = StringUtils.substringsBetween(content, "/", ".tif");

				tifNamesArray[id] = tifNames;

			}

		} catch (Exception j) {
			return 0;
		}

		int g = 0;

		for (String[] t : tifNamesArray) {
			g += t.length;
		}

		return g;
	}

	private int frameCounter(String paramFileString) {

		NexusFile file = new NexusFileFactoryHDF5().newNexusFile(paramFileString);

		try {
			file.openToRead();
		} catch (NexusException e) {
			e.printStackTrace();
		}

		final String path = "/" + NeXusStructureStrings.getEntry() + "/" + NeXusStructureStrings.getOverviewOfFrames();

		String attributeName =NeXusStructureStrings.getBoundaryboxArray()[1];

		GroupNode point;

		try {
			point = file.getGroup(path, false);
			Attribute roiAttribute = point.getAttribute(attributeName);
			Dataset roiAttributeDat = (Dataset) roiAttribute.getValue();

			return roiAttributeDat.getSize();

		} catch (NexusException e) {

			e.printStackTrace();
		}

		return 0;

	}

}
