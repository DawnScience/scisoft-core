package uk.ac.diamond.scisoft.ptychography.rcp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoData;

public class PtychoUtils {

	private final static Logger logger = LoggerFactory.getLogger(PtychoUtils.class);
	private static CSVFormat format;

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
	
	
	public static List<PtychoData> loadTemplateFile(String fullPath){
		if(fullPath.endsWith(".json"))
			return loadJsonTemplate(fullPath);
		else
			return loadSpreadSheet(fullPath);
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
			parser = CSVParser.parse(new File(fullPath), StandardCharsets.UTF_8, CSVFormat.EXCEL);
			CSVRecord headers = parser.getRecords().get(0);
			String[] headerArray = new String[9];
			
			for(int i=0; i<headers.size(); i++){
				headerArray[i] = headers.get(i); 
			}
			
			format = CSVFormat.EXCEL.withHeader(headerArray).withSkipHeaderRecord(true);
			parser = CSVParser.parse(new File(fullPath), StandardCharsets.UTF_8, format);
			
			for(CSVRecord csvRecord : parser){
				
				String name = null;
				String level = null;
				String defaultVal = null;
				String type = null;
				String unique = "";
				String lowerlimit = null;
				String upperlimit = null;
				String shortdoc = null;
				String longdoc = null;
				
				for(int i=0; i<headerArray.length;i++){
					if(headerArray[i].equals("name") || headerArray[i].equals("path")){
						name = csvRecord.get(i);
					} else if(headerArray[i].equals("level")){
						level = csvRecord.get(i);
					} else if(headerArray[i].equals("default")){
						defaultVal = csvRecord.get(i);
					} else if(headerArray[i].equals("type")){
						type = csvRecord.get(i);
					} else if(headerArray[i].equals("unique")){
						unique = csvRecord.get(i);
					} else if(headerArray[i].equals("lowerlimit") || headerArray[i].equals("lowlim")){
						lowerlimit = csvRecord.get(i);
					} else if(headerArray[i].equals("upperlimit") || headerArray[i].equals("uplim")){
						upperlimit = csvRecord.get(i);
					} else if(headerArray[i].equals("shortdoc") || headerArray[i].equals("help")){
						shortdoc = csvRecord.get(i);
					} else if(headerArray[i].equals("longdoc") || headerArray[i].equals("doc")){
						longdoc = csvRecord.get(i);
					}
				}
				if(level == null)
					level = Integer.toString(name.split("\\.").length -1);
				
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
			logger.error("Failed to load file: {}", fullPath, e);
		}
		return loaded;
	}
	
	
	public static List<PtychoData> loadJsonTemplate(String fullPath){
		JsonObject jsonObject = new JsonObject();
		JsonElement rootNode;
		
		try {
			JsonParser parser = new JsonParser();
			rootNode = parser.parse(new FileReader(fullPath));
			jsonObject = rootNode.getAsJsonObject();
		} catch(FileNotFoundException e) {
			logger.error("Failed to load file: {}", fullPath, e);
		}
		
		return recursiveJsonLoad(jsonObject, 0);
	}
	
	/*
	 * Recursively load JSON files into existing editor structure
	 * Only populates the name and value fields, due to key value pair structure of JSON
	 */
	private static List<PtychoData> recursiveJsonLoad(JsonObject root, int level) {
		List<PtychoData> loaded = new ArrayList<PtychoData>();
		
		for(String name : root.keySet()) {
			JsonElement element = root.get(name);
			
			PtychoData data = new PtychoData();
			data.setName(name);
			data.setDefaultValue("");
			
			if(element.isJsonObject()) {
				data.setLevel(level);
				loaded.add(data);
				loaded.addAll(recursiveJsonLoad(element.getAsJsonObject(), ++level));
				level--;
			}
			
			if(!element.isJsonObject() && !element.isJsonArray())
				data.setDefaultValue(element.getAsString());
			if(element.isJsonArray()) {
				JsonArray array = element.getAsJsonArray();
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				
				int count = 1;
				for(JsonElement arrayElement : array) {
					sb.append(arrayElement.getAsString());
					if(count < array.size()) {
						sb.append(",");
						count++;
					}
				}
				sb.append("]");
				data.setDefaultValue(sb.toString());
			}
			if(!element.isJsonObject()) {
				data.setLevel(level);
				loaded.add(data);
			}
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
		
		if(format == null)
			format = CSVFormat.EXCEL.withHeader("level","name","value");
		
		try {
			printer = new CSVPrinter(new PrintWriter(filePath), format);
			for (PtychoData row : input) {
				List<String> rowData = new ArrayList<String>();
				rowData.add(String.valueOf(row.getLevel()));
				rowData.add(row.getName());
				rowData.add(row.getDefaultValue());
				if(format.getHeader().length > 3) {
					rowData.add(row.getType());
					rowData.add(String.valueOf(row.isUnique()));
					rowData.add(String.valueOf(row.getLowerLimit()));
					rowData.add(String.valueOf(row.getUpperLimit()));
					rowData.add(row.getShortDoc());
					rowData.add(row.getLongDoc());
				}
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
