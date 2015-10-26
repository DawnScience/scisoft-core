package uk.ac.diamond.scisoft.analysis.io;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;


public class XMSOLoader extends AbstractFileLoader {

	transient protected static final Logger logger = LoggerFactory.getLogger(XMSOLoader.class);
	
	public XMSOLoader() {
	}
	
	public XMSOLoader(final String fileName) {
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
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
		try {
			//ignore the dtd
			dbf.setValidating(false);
			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
			dbf.setFeature("http://xml.org/sax/features/validation", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(fileName);
			
			//check how many interactions there were
			XPath xpath = XPathFactory.newInstance().newXPath();
			int n_interactions = ((Double)xpath.evaluate("/xmimsim-results/xmimsim-input/general/n_interactions_trajectory", dom, XPathConstants.NUMBER)).intValue();
			
			//check how many children the first channel has. This will let us know if there was transmission (zero-interactions) or not...
			int nchildren_channel0 = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel[1]/counts", dom, XPathConstants.NODESET)).getLength();

			int first_interaction;
			
			if (n_interactions == nchildren_channel0) {
				first_interaction = 1;
			}
			else if (nchildren_channel0 == n_interactions+1) {
				first_interaction = 0;
			}
			else {
				//this should never happen and indicates a corrupt XMSO file
				throw new ScanFileHolderException("DatLoader.loadFile exception loading  " + fileName);
			}
			
			//how many channels are we dealing with
			int nchannels = ((Double)xpath.evaluate("/xmimsim-results/xmimsim-input/detector/nchannels", dom, XPathConstants.NUMBER)).intValue();
			
			//read the energies
			double[] energies = new double[nchannels];
			NodeList energies_list = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel/energy", dom, XPathConstants.NODESET));
			
			for (int i = 0 ; i < nchannels ; i++) {
				energies[i] = Double.parseDouble(energies_list.item(i).getFirstChild().getNodeValue());
			}
			
			Dataset set =  DatasetFactory.createFromObject(energies);
			set.setName("Energy (keV)");
			result.addDataset("Energy (keV)", set);

			//convoluted data
			for (int interaction = first_interaction ; interaction <= n_interactions ; interaction++) {
				double[] counts = new double[nchannels];
				NodeList counts_list = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel/counts[@interaction_number=\""+ interaction + "\"]", dom, XPathConstants.NODESET));
				for (int i = 0 ; i < nchannels ; i++) {
					counts[i] = Double.parseDouble(counts_list.item(i).getFirstChild().getNodeValue());
				}
				set = DatasetFactory.createFromObject(counts);
				if (interaction == 1) {
					set.setName("Counts after 1 interaction");
					result.addDataset("Counts after 1 interaction", set);
				}
				else {
					set.setName("Counts after " + interaction + " interactions");
					result.addDataset("Counts after " + interaction + " interactions", set);
				}
			}
			
			
			
		} catch (Exception e) {
			System.err.println("Exception message: " + e.getMessage());
			throw new ScanFileHolderException("DatLoader.loadFile exception loading  " + fileName, e);
		}
		
		return result;
	}
	
}
