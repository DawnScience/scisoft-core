/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.IFindInTree;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;


/**
 * @author Tekevwe Kwakpovwe
 * Takes information from the nexus file on the processing tab in dawn
 * and reproduces this information in the form of an input file to gudrun
 *
 */
public class GenerateGudrun extends AbstractOperation<GenerateGudrunModel, OperationData>{
	
	private final double NORMCONST = Math.pow(10, -9);
	
	private static final Logger LOGGER = Logger.getLogger( GenerateGudrun.class.getName());
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.gudrun.GenerateGudrun";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	private File[] searchForProcessed(File fileDirec, String sampleName) {
		try {
			File[] processFiles = fileDirec.listFiles(new FilenameFilter() {
				//&& name.contains(sampleLabel)
		        public boolean accept(File dir, String name) {
				    return (name.contains("rocess") && name.endsWith("nxs") && name.contains(sampleName));
				}
			});
			
			return processFiles;
		} catch (Exception e2) {
			throw new OperationException(this, e2);
		}
	}

	/**
	 * The interface between dawn and the eclipse code
	 */
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		/*
		 * This code is modelled on code from XPDFReadMetadataOperation line 66 onwards
		 * this is to get the filepath of the nexus file being processed in dawn
		 */
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);

		try {
			//this is the processed files filepath
			String operationFile = ssm.getFilePath();
			File thisFile = new File(operationFile);
			String sampleName = thisFile.getName().replaceAll(".nxs", "");
			File fileDirec = new File(thisFile.getParent());
			
			File[] processFiles = searchForProcessed(fileDirec, sampleName);
			File processedDirec = new File(thisFile.getParent() + "/processed/");

			File[] nexusFiles = {thisFile};
			//location of the cluster processing file
			File prcedFile;
			if ((processFiles.length == 0) && (processedDirec.exists())) {
				LOGGER.log(Level.INFO, "processed directory found for: " + sampleName);
				//If the processed file is not in the same directory as the nexus file,
				//Search for it within a folder called processed
				processFiles = searchForProcessed(processedDirec, sampleName);
				if (processFiles.length != 0) {
					prcedFile= new File(processFiles[0].toString());
					LOGGER.log(Level.INFO,"the processed file's directory is: " + prcedFile);
				}
				else {
					LOGGER.log(Level.WARNING, "No 'processed' folder could be found, program will assume this file is in"
							+ "the same location as the nexus file");
				}
			}
			else {
				LOGGER.log(Level.WARNING, "Either the directory .../processed/ does not exist or the processed file is not inside it");
			}
			//The location of the cluster processing file
			File clstrLocation = new File(thisFile.getParent());
			//the bulk of the autogudrun generation is handled by this function
			getGudrun(nexusFiles, operationFile, clstrLocation);
		} catch (Exception e1) {                                                                                                                                                                                                                                                                                                                                                                         
			LOGGER.log(Level.WARNING, "The operation could not be continued");
			throw new OperationException(this, e1);
		}
		return new OperationData(input);
	}
	
	//How the program obtains the NXclasses as objects for interaction with the metadata
	private static IFindInTree getSample() {
		return getSomething("NXsample");
	}
	
	private static IFindInTree getSomething(String NXclass) {
		
		return new IFindInTree() {
			@Override
			public boolean found(NodeLink node) {
				if (node.getDestination() instanceof GroupNode) {
					Attribute nxClass = ((GroupNode) node.getDestination()).getAttribute("NX_class");
					if (nxClass != null
							&& nxClass.getFirstElement() != null
							&& nxClass.getFirstElement().equals(NXclass))
						return true;
				}
				return false;
			}
		};
	}
	
	/*
	 * The "elsewhere" is :
	 * uk.ac.diamond.scisoft.xpdf.operations.XPDFReadMetadataOperation.readAndAddContainerInfo(XPDFMetadataImpl, Tree, ILazyDataset) 
	 */
	
	//Taken from elsewhere (found above)
	private  <T extends NXobject> T getFirstSomething(Tree tree, String NXclass) {

		Map<String, NodeLink> nodeMap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getSomething(NXclass), true, null);
		if (nodeMap.size() == 0) return null;
		GroupNode node = (GroupNode) nodeMap.values().toArray(
				new NodeLink[nodeMap.size()])[0].getDestination();
		//The cast has already been checked, since NXobject is a derived class of GroupNode
		@SuppressWarnings("unchecked")
		T tt = (T) node;
		return tt;
	}
	//Taken from "elsewhere", not sure about its operations, required for creating nexus objects.
	private NXsample getNXsampleFromTree(XPDFMetadataImpl xpdfMeta, Tree tree, ILazyDataset parent) {
		Map<String, NodeLink> nodeMap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getSample(), true, null);

		if (nodeMap.size() < 1) throw new OperationException(this, "Sample information requested, but no NXsample data was found.");
		if (nodeMap.size() > 1) throw new OperationException(this, "Multiple NXsample data found. Giving up.");

		//Get the first (only) NXsample
		GroupNode sampleNode = (GroupNode) nodeMap.values().toArray(
				new NodeLink[nodeMap.size()])[0].getDestination();
		return (NXsample) sampleNode;
	}
	
	/**
	 * Makes a copy of the background xy file and processing xy fileplaces it in the same directory as the sample filer
	 * @param inputFileDirectory: the directory of the background file
	 * @param outDirecName: the user's chosen directory.
	 * @param sB: a SamepleBackground file.
	 * @param sample_file: the xy file's name
	 */
	private void moveFiles(String inputFileDirectory, String outDirecName, SampleBackground sB, String sample_file){
	    
		/*
	     * This for loop is actually only ever run once in the processes' current implementation as there is only one
		 * background file  
		 */
		
		for (String backgr_file: sB.getFileNames()) {
			File backgr = new File(backgr_file);
            try {
			    Files.copy((new File(backgr_file)).toPath(),(new File(outDirecName + "/" + backgr.getName())).toPath());
			    LOGGER.log(Level.INFO, "Background file succesfully moved");
		    } catch (IOException e) {
			    LOGGER.log(Level.WARNING, "An I/O exception was thrown, do you have the correct permissions? "
			    		+ "\n" + "The background file may not exist in the directory : " + backgr_file, e);
		    }
            try {
            	Files.copy((new File(inputFileDirectory + "/processed/tth_det2/" + sample_file)).toPath(),(new File(outDirecName + "/" + sample_file)).toPath());
            }catch (IOException e) {
            	LOGGER.log(Level.WARNING, "An I/O exception was thrown do you have the correct permissions? " +
            	"\n" + "the xy file may not exist in the directory : " + inputFileDirectory + "/processed/tth_det2/", e);
            }
  	    }
	}
	
	/**
	 * Takes all the names of the nexus files within the directory and finds the sample background file that they each
	 * use, if there are any that use the same background file, only one of such file is stored to avoid duplicates in the
	 * autogudrun.txt file
	 * 
	 * @param fileNames: The names of the nexus files within the directory
	 * @param outFileDirec
	 * @return a sampleBackground with the appropriate attributes (an array of unique background files)
	 */
	private SampleBackground getGudrunBackground(File[] fileNames, String outFileDirec, String i0filepath) {
		//new sample background to be returned with the appropriate attributes
		SampleBackground sampleBackground = new SampleBackground();
		NexusFile containerFile;
		Tree containerTree;
		String[] xyfileContainer = new String[fileNames.length];
		
		LOGGER.log(Level.INFO,"the smaple file names: " + fileNames[0].getName());
		//gets all the container files from each nexus file
		
		for (int i = 0; i < fileNames.length; i ++) {
			String filepath = fileNames[i].toString();
		    try {
			    containerFile = NexusFileHDF5.openNexusFileReadOnly(filepath);
			    containerTree = NexusUtils.loadNexusTree(containerFile);
		    } catch (Exception e1) {
			    throw new OperationException(this, e1);
		    }
		    GroupNode containerFileNameNode = getFirstSomething(containerTree, "NXcontainer");
		    String confilepath = containerFileNameNode.getDataNode("inside_of_file_name").getString();
		    LOGGER.log(Level.INFO, "Confilepath has this value: " + confilepath);
		    xyfileContainer[i] = confilepath; 
		}

		//Create an array with only containers that have a unique sample background file
		List<String> containersList = Arrays.asList(xyfileContainer);
		Set<String> containerSet = new HashSet<String>(containersList);
		String[] uniqContainers = containerSet.toArray(new String[containerSet.size()]);
	    
		//get the unique elements in the array of file names
		sampleBackground.setFileNames(uniqContainers);
		return sampleBackground;
	}
	
	/**
	 * swaps the direction of forward and back slashes for when data is being stored on windows vs linux
	 * @param filepath to be converted
	 * @return: the same filepath except with the forward slashes as backslashes for windows users
	 */
	private String linuxToWindows(String filepath) {
		String drive = "Y";
	    return (filepath.replace("/dls/i15-1/data/",drive+":\\")).replace("/","\\");
	}

	/**
	 * Obtains the required metadata to write the "SAMPLE" section of the autogudrun.txt and stores it in
	 * a sample object.
	 * 
	 * @param filepath: the filepath of the nexus file being processed
	 * @param fileName: the name of the file being processed
	 * @param i0Filepath: the location fo the i0 file
	 * @return sample: a sample object with the appropriate attributes 
	 */	
	private Sample getGudrunSample(String filepath, String fileName, String i0Filepath) {	
		/*
		 * Can move all this i0 file reading to the new function being made. 
		 * Will need a flag variable to determine whether we are getting the sample's i0 vs
		 * the background's i0. 
		 * 
		 */
		NexusFile i0File;
		NexusFile sampleFile;
		Tree i0Tree;
		Tree sampleTree;
		Sample sample = new Sample();
		try {
		    i0File = NexusFileHDF5.openNexusFileReadOnly(i0Filepath);
			i0Tree = NexusUtils.loadNexusTree(i0File);
			sampleFile = NexusFileHDF5.openNexusFileReadOnly(filepath);
			sampleTree = NexusUtils.loadNexusTree(sampleFile);
		} catch (Exception e1) {
			throw new OperationException(this, e1);
		}
	    NXsample treeSample = getFirstSomething(sampleTree, "NXsample");
	    NXdata treei0 = getFirstSomething(i0Tree, "NXdata");
	    //data is definitely there	    NXdata
	    List<ILazyDataset> listi0 = treei0.getDatasets("data");
	    ILazyDataset[] lazyi0 = listi0.toArray(new ILazyDataset[listi0.size()]);
	    try {
			double meani0 = (double) lazyi0[0].getSlice().mean(true);
			sample.setDataFactor(NORMCONST/meani0);
		} catch (DatasetException e) {
			LOGGER.log(Level.WARNING, "couldn't obtain mean of i0");
		}
	    
	    String density  = treeSample.getString("density");
		NXsample nxSample= getNXsampleFromTree(null, sampleTree, null); 
		String composition = (nxSample.getChemical_formulaScalar());
		String name = nxSample.getNameScalar();
		sample.setName(name);
		sample.setDensity(density);
		sample.setFileNames(new String[] {fileName.replace(".nxs","_tth_det2_0.xy")});
		sample.setComposition(composition);
		return sample;
	}
	
	/**
	 * gets the name(s) of the file(s) to create. This name follows the pattern (name of file being processed)_gudrun.txt, 
	 * either the file(s) is/are a completely new so the name(s) are unique or an existing file is being appended to. 
	 * @param inputFileDirectory:  
	 * @param samplesArr: 
	 * @return the name(s) of the file(s)
	 * @throws IOException
	 */
	public String[] getOutFileNames(String inputFileDirectory, Sample[] samplesArr) throws IOException {

		String[] outFileArray = new String[2];
		String outFileName;
		String outDirecName = "";
		if (model.getFilePath() == null) {
			outFileName = "";
		} else {
			outFileName = model.getFilePath();
			File outFileLoc = new File(outFileName);
			outDirecName = outFileLoc.getParent();
		}
		if (!(model.getFileDirectory() == null)) {
		    outDirecName = model.getFileDirectory();
		}
		//Section for a completely new file
		if (!(outDirecName.isEmpty())) {
			File oFDFile = new File(outDirecName);
			File autoTxtFile = new File(outDirecName + "/" + (samplesArr[0].getFileNames())[0].replace("_tth_det2_0.xy","_gudrun.txt"));
			if (!(oFDFile.exists())){
				outDirecName += "/" + (samplesArr[0].getFileNames())[0].replace("_tth_det2_0.xy","_gudrun.txt");
				oFDFile.mkdirs();
				autoTxtFile.createNewFile();
			}else if (!(autoTxtFile.exists())) {
				outDirecName += "/" + (samplesArr[0].getFileNames())[0].replace("_tth_det2_0.xy","_gudrun.txt");
				autoTxtFile.createNewFile();
			}
			outFileArray[0] = outDirecName;
		}
		//For append section
		if (!(outFileName.isEmpty())) {
			outFileArray[1] = outFileName;
		} 
		return outFileArray;
	}
	
	/**
	 * sets some default attributes for the instrument object to be passed into gudrunInput
	 * 
	 * @return an instrument object
	 */
	private Instrument getInstrument(){
		//dataFileDirectory is passed in even though it's unused in case it
		//Will need to be used in the future
		Instrument instrument = new Instrument();	
		instrument.setQMax(25);
		instrument.setQMin(0.5);
		return instrument; 
	}
	
	/**
	 * 
	 * @param sampleName
	 * @param sampleDirec
	 * @param targetDirec
	 * @return
	 */
	private String geti0File(String sampleName, String sampleDirec, String targetDirec) {
		File i0File = new File(sampleDirec + "/" + sampleName + "_i0.hdf5");
		if (i0File.exists()) {
			return i0File.toString();
		}
		else {
			return (targetDirec + "/" + sampleName + "_i0.hdf5");
		}
	}
	
	/**
	 * A function for returning the directory specified by the user
	 * 
	 * @param procTarget 
	 */
	private String getTargetDirec(String procTarget) throws OperationException{
		try {
			File processTarget = new File(procTarget);
			if (procTarget.contains(".nxs")) {
				return processTarget.getParent();
			}
			else {
				return procTarget;
			}
		} catch (OperationException e){
			LOGGER.log(Level.WARNING, "no target file set. Target file may not exist or permissions may not be adequate");
		}
		
		return procTarget;
	}
	
	/**
	 * Calls the main generate() methods from the GudrunInput class that calls the subsequent generate() methods, this function is essentially
	 * what writes the metadata to the autogudrun text file after the objects of the various classes have been created. 
	 * @param fileNames: other nexus files that are in the directory of the processedFile.
	 * @param operationFile: the file that has been placed for processing
	 * @param clstrLocation: the location of the cluster processing file (from the directory only) 
	 * @throws IOException 
	 */
	private void getGudrun(File[] fileNames, String operationFile, File clstrLocation) throws IOException {
		
		//os.path.basename() = tail of the path so the "arts" in mixed/martial/arts
		//os.path.dirname() = the head and body of the path so the "mixed/martial" in mixed/martial/arts

		File nexusFile = new File(operationFile);
		String sampleDirec = nexusFile.getParent();
		String sampleName = nexusFile.getName().replace(".nxs", "");
		String targetDirec = getTargetDirec(model.getFileDirectory() );
		//This is what will be used when the operation is actually in operation.
		String dataFileDirectory= nexusFile.getParent();
		String inputFileDirectory = clstrLocation.toString();

		File iFDFile = new File(dataFileDirectory);
		//If the file doesn't exist then it is created here
		if (!(iFDFile.exists())){
			iFDFile.mkdirs();
		}
		
		Instrument instrument = getInstrument();
		
		Beam beam = new Beam();
		Normalisation normalisation = new Normalisation();
		
		/*
		 * gets all the unique sample backgrounds so no duplicates are obtained for
		 * samples that use the same background.
		 */
		
		ArrayList<Sample> samplesList = new ArrayList<Sample>();
		String i0filepath = geti0File(sampleName, sampleDirec, targetDirec);
		for (File filePath : fileNames) {
			String strFilePath = filePath.toString();
			String strFileName = filePath.getName();
			//need to obtain path to the i0file, this should be a string placed within getGudrunSample's function call
			Sample sample = getGudrunSample(strFilePath, strFileName, i0filepath);
			samplesList.add(sample);
		}
		Sample[] samplesArr = samplesList.toArray(new Sample[samplesList.size()]);
		
		/*
		 * With the objects set, the file can now be written, 
		 * the objects are passed into GudrunInput as parameters to do this 
		 */
		try {
			String[] outFileArray = getOutFileNames(inputFileDirectory, samplesArr);
			
			//directory that we want to put the gudrun input file in.
			String outFileDirec = outFileArray[0];
			SampleBackground sampleBackground = getGudrunBackground(fileNames, outFileDirec, i0filepath);
			moveFiles(inputFileDirectory, outFileDirec, sampleBackground, (samplesArr[0].getFileNames())[0]);
			if (outFileArray[0] != null && !(outFileArray[0].equals(""))) {
				(new GudrunInput(instrument,samplesArr,beam,sampleBackground,normalisation)).generate(outFileArray[0]);
				LOGGER.log(Level.FINE, "File succesfully written");
			}
			if (outFileArray[1] != null && !(outFileArray[1].equals(""))) {
				(new AppendGudrun(instrument,samplesArr,beam,sampleBackground,normalisation)).generate(outFileArray[1]);
				LOGGER.log(Level.FINE, "File succesfully written");
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.INFO, "The input file was not found", e);
		}		
	}
}

