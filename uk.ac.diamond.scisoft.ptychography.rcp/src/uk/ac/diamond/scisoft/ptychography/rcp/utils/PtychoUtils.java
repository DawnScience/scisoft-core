package uk.ac.diamond.scisoft.ptychography.rcp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;

public class PtychoUtils {

	private final static Logger logger = LoggerFactory.getLogger(PtychoUtils.class);
	private final static String[] HEADERS = new String[] {"level", "name", "default", "type", "unique", "lowerlimit", "upperlimit", "shortdoc", "longdoc"};
	private final static CSVFormat format = CSVFormat.EXCEL
			.withHeader(HEADERS)
			.withSkipHeaderRecord(true);

	/**
	 * Returns a fullpath given an IEditorInput
	 * @param input
	 * @return
	 */
	public static String getFullPath(IEditorInput input) {
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
		return fullPath;
	}

	/**
	 * Loads the CSV spreadshet and returns a list of PtychoInput
	 * 
	 * @param fullPath
	 * @return
	 */
	public static List<PtychoData> loadSpreadSheet(String fullPath) {
		CSVParser parser;
		List<PtychoData> loaded = new ArrayList<PtychoData>();
		try {
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
				
				PtychoData row = new PtychoData();
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
	 * Save the list of PtychoInput into a CSV file
	 * @param input
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public static void saveSpreadsheet(List<PtychoData> input, String filePath) throws FileNotFoundException {
		CSVPrinter printer;
		try {
			printer = new CSVPrinter(new PrintWriter(filePath), format);
			for (PtychoData row : input) {
				List<String> rowData = new ArrayList<String>();
				rowData.add(String.valueOf(row.getLevel()));
				rowData.add(row.getName());
				rowData.add(row.getDefaultValue());
				rowData.add(row.getType());
				rowData.add(String.valueOf(row.isUnique()));
				rowData.add(String.valueOf(row.getLowerLimit()));
				rowData.add(String.valueOf(row.getUpperLimit()));
				rowData.add(row.getShortDoc());
				rowData.add(row.getLongDoc());
				printer.printRecord(rowData);
			}
			printer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/**
	 * 
	 * @param filePath
	 * @param content
	 */
	public static void saveCSVFile(String filePath, List<PtychoData> content) {
		try {
			saveSpreadsheet(content, filePath);
		} catch (FileNotFoundException e) {
			logger.error("Error saving file:" +e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param filePath
	 * @param content
	 */
	public static void saveJSon(String filePath, String content) throws IOException{
		FileWriter fileWriter = null;
		try {
			File newTextFile = new File(filePath);
			fileWriter = new FileWriter(newTextFile);
			fileWriter.write(content);
			fileWriter.close();
		} finally {
			fileWriter.close();
		}
	}
}
