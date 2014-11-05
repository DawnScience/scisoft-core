package uk.ac.diamond.scisoft.ptychography.rcp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoInput;

public class PtychoUtils {

	/**
	 * Loads the CSV spreadshet and returns a list of PtychoInput
	 * 
	 * @param input
	 * @return
	 */
	public static List<PtychoInput> loadSpreadSheet(IEditorInput input) {
		IResource rsc = getResource(input);
		String fullPath = "";
		if (rsc instanceof File) {
			File file = (File) rsc;
			fullPath = file.getAbsolutePath();
		} else if (rsc instanceof IFile) {
			IFile iFile = (IFile) rsc;
			fullPath = iFile.getLocation().toOSString();
		}
		if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput fileStore = (FileStoreEditorInput) input;
			fullPath = fileStore.getURI().getPath();
		}
		CSVParser parser;
		List<PtychoInput> loaded = new ArrayList<PtychoInput>();
		try {
			CSVFormat format = CSVFormat.EXCEL
					.withHeader("level", "name", "default", "type", "unique", "lowerlimit", "upperlimit", "shortdoc", "longdoc")
					.withSkipHeaderRecord(true);
			parser = CSVParser.parse(new File(fullPath), StandardCharsets.UTF_8, format);
			for (CSVRecord csvRecord : parser) {
				String level = csvRecord.get("level");
				String name = csvRecord.get("name");
				String defaultVal = csvRecord.get("default");
				String type = csvRecord.get("type");
				String unique = csvRecord.get("unique");
				String lowerlimit = csvRecord.get("lowerlimit");
				String upperlimit = csvRecord.get("upperlimit");
				String shortdoc = csvRecord.get("shortdoc");
				String longdoc = csvRecord.get("longdoc");
				
				PtychoInput row = new PtychoInput();
				if (level.length() > 0 && StringUtils.isNumeric(level))
					row.setLevel(Integer.valueOf(level));
				row.setName(name);
				row.setDefaultValue(defaultVal);
				row.setType(type);
				row.setUnique(unique.equals("yes") ? true : false);
				if (lowerlimit.length() > 0 && StringUtils.isNumeric(lowerlimit))
					row.setLowerLimit(Integer.valueOf(lowerlimit));
				if (upperlimit.length() > 0 && StringUtils.isNumeric(upperlimit))
					row.setUpperLimit(Integer.valueOf(upperlimit));
				row.setShortDoc(shortdoc);
				row.setLongDoc(longdoc);
				loaded.add(row);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loaded;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static IResource getResource(IEditorInput input) {
		IResource resource = (IResource) input.getAdapter(IFile.class);
		if (resource == null) {
			resource = (IResource) input.getAdapter(IResource.class);
		}
		return resource;
	}
}
