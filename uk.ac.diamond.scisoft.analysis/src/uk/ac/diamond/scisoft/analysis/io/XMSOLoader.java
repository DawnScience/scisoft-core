package uk.ac.diamond.scisoft.analysis.io;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
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
		//no metadata in XMSO files
	}
	
	@Override
	public DataHolder loadFile() throws ScanFileHolderException {
		return loadFile((IMonitor)null);
	}
	
	/**
	 * Function that loads in the standard XMSO datafile
	 * 
	 * @return The package which contains the data that has been loaded
	 * @throws ScanFileHolderException
	 */
	@Override
	public DataHolder loadFile(final IMonitor mon) throws ScanFileHolderException {
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
			
			Dataset energiesDS =  DatasetFactory.createFromObject(energies);
			energiesDS.setName("Energy (keV)");
			result.addDataset("Energy (keV)", energiesDS);

			//convoluted data
			for (int interaction = first_interaction ; interaction <= n_interactions ; interaction++) {
				double[] counts = new double[nchannels];
				NodeList counts_list = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel/counts[@interaction_number=\""+ interaction + "\"]", dom, XPathConstants.NODESET));
				for (int i = 0 ; i < nchannels ; i++) {
					counts[i] = Double.parseDouble(counts_list.item(i).getFirstChild().getNodeValue());
				}
				Dataset set = DatasetFactory.createFromObject(counts);
				
				// add energies as x-axis
				try {
					AxesMetadata amd = MetadataFactory.createMetadata(AxesMetadata.class, 1);
					amd.setAxis(0, energiesDS);
					set.addMetadata(amd);
				} catch (MetadataException e) {
					logger.error("Could not created AxesMetadata: ", e);
				}
				
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
			logger.error("Exception message: {}", e.getMessage());
			throw new ScanFileHolderException("XMSOLoader.loadFile exception loading  " + fileName, e);
		}
		
		return result;
	}
	
}
