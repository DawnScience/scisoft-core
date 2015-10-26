package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.util.Scanner;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;


public class SpeLoader extends AbstractFileLoader {

	transient protected static final Logger logger = LoggerFactory.getLogger(SpeLoader.class);

	protected int channel_begin;
	protected int channel_end;
	protected int nchannels;
	
	public SpeLoader() {
	}
	
	public SpeLoader(final String fileName) {
		setFile(fileName);
	}

	@Override
	public void setFile(String fileName) {		
		super.setFile(fileName);
	}

	@Override
	protected void clearMetadata() {
		//no metadata in SPE files
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile((IMonitor)null);
	}
	
	/**
	 * Function that loads in the standard fio datafile
	 * 
	 * @return The package which contains the data that has been loaded
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {
        final DataHolder result = loadFile(-1, mon);
		return result;
	}
	
	private DataHolder loadFile(final int columnIndex, final IMonitor mon) throws ScanFileHolderException {
		// first instantiate the return object.
		final DataHolder result = new DataHolder();
		// then try to read the file given
		Scanner scanner = null;
		try {
			File file = new File(fileName);
			scanner = new Scanner(file);
			String line;
			
			do {
				line = scanner.next();	
			} while (!line.trim().equals("$DATA:"));
			
			channel_begin = scanner.nextInt();
			channel_end = scanner.nextInt();
			nchannels = channel_end - channel_begin + 1;
			double[] array = new double[nchannels];
			
			for (int channel = 0 ; channel < nchannels ; channel++) {
				array[channel] = scanner.nextDouble();
			}
			scanner.close();
			final Dataset set =  DatasetFactory.createFromObject(array);
			set.setName("Spectrum");
			result.addDataset("Spectrum", set);
		} catch (Exception e) {
			throw new ScanFileHolderException("DatLoader.loadFile exception loading  " + fileName, e);
		} finally {
			if (scanner != null)
				scanner.close();
		}
		
		return result;
	}
	
}
