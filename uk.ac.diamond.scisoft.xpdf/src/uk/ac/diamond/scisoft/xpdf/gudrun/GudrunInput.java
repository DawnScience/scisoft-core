/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Tekevwe Kwakpovwe
 * A class for generating the autogudrun text file
 * using the attributes of objects made from the other classes in the gudrun package.
 */
public class GudrunInput {
	
	private static final Logger LOGGER = Logger.getLogger( GudrunInput.class.getName());
	
	private Instrument instrument;
	private Sample[] samples;
	private Beam beam;
	private SampleBackground background;
	private Normalisation normalisation;
	
	/**
	 * Constructor for GudrunInput
	 */
	public GudrunInput(Instrument ins, Sample[] sam, Beam bea,
			           SampleBackground saB, Normalisation nor) {
		
		this.instrument = ins;
		this.samples = sam;
		this.beam = bea;
		this.background = saB;;
		this.normalisation = nor;
	}

	/**
	 * Creates the autgudrun text file
	 * @param outFileName: the path of the file
	 * @throws FileNotFoundException
	 */
	public void generate(String outFileName) throws FileNotFoundException {
		StringBuilder entry = new StringBuilder();
		File outFileDirec = new File(outFileName);
		instrument.setDataFileDirectory(outFileDirec.getParentFile());
		instrument.setInDirectory(outFileDirec.getParentFile());
        LOGGER.log(Level.INFO, "Creating Gudrun input file...");
        entry.append("'  '  '          '  '\\'\n\n");
        entry.append(this.instrument.generate());
        entry.append("\n\n");
        entry.append(this.beam.generate());
        entry.append("\n\n");
        entry.append(this.normalisation.generate());
        entry.append("\n\n");
        entry.append(this.background.generate());
        entry.append("\n\n");
        for (Sample sample : this.samples) {
            entry.append(sample.generate());
            entry.append("\n\n");
        }

        entry.append("\nEND\n1\n");
        String dateTimeString = (LocalDateTime.now()).toString();
        dateTimeString = dateTimeString.replace("-"," ");
        
        entry.append("Date and time last written:  " + dateTimeString.split("\\.")[0] + "          \n");
        entry.append("X");
        try {
        PrintWriter outFile = new PrintWriter(outFileName);
        outFile.write(entry.toString());
        outFile.close();
        } catch (IOException x) {
        	System.err.format("IOException: %s%n", x);
        } finally {
        	LOGGER.log(Level.INFO, outFileName + " was written to!");;
        }
	}
}
