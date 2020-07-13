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
import org.eclipse.january.metadata.Metadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;


public class XMSOLoader extends AbstractFileLoader {

	private static final Logger logger = LoggerFactory.getLogger(XMSOLoader.class);
	private static final String ENERGY = "Energy (keV)";
	
	public XMSOLoader() {
	}
	
	public XMSOLoader(final String fileName) {
		setFile(fileName);
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
			int nInteractions = ((Double)xpath.evaluate("/xmimsim-results/xmimsim-input/general/n_interactions_trajectory", dom, XPathConstants.NUMBER)).intValue();
			
			//check how many children the first channel has. This will let us know if there was transmission (zero-interactions) or not...
			int nChildrenChannel0 = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel[1]/counts", dom, XPathConstants.NODESET)).getLength();

			int firstInteraction;
			
			if (nInteractions == nChildrenChannel0) {
				firstInteraction = 1;
			}
			else if (nChildrenChannel0 == nInteractions + 1) {
				firstInteraction = 0;
			}
			else {
				//this should never happen and indicates a corrupt XMSO file
				throw new ScanFileHolderException("DatLoader.loadFile exception loading  " + fileName);
			}
			
			//how many channels are we dealing with
			int nchannels = ((Double)xpath.evaluate("/xmimsim-results/xmimsim-input/detector/nchannels", dom, XPathConstants.NUMBER)).intValue();
			
			//read the energies
			double[] energies = new double[nchannels];
			NodeList energiesList = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel/energy", dom, XPathConstants.NODESET));
			
			for (int i = 0 ; i < nchannels ; i++) {
				energies[i] = Double.parseDouble(energiesList.item(i).getFirstChild().getNodeValue());
			}
			
			Dataset energiesDS =  DatasetFactory.createFromObject(energies);
			energiesDS.setName(ENERGY);
			result.addDataset(ENERGY, energiesDS);

			//convoluted data
			for (int interaction = firstInteraction ; interaction <= nInteractions ; interaction++) {
				double[] counts = new double[nchannels];
				NodeList countsList = ((NodeList) xpath.evaluate("/xmimsim-results/spectrum_conv/channel/counts[@interaction_number=\""+ interaction + "\"]", dom, XPathConstants.NODESET));
				for (int i = 0 ; i < nchannels ; i++) {
					counts[i] = Double.parseDouble(countsList.item(i).getFirstChild().getNodeValue());
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
				
				Metadata md = new Metadata();

				String name;
				
				if (interaction == 1) {
					name = "Counts after 1 interaction";
				}
				else {
					name = String.format("Counts after %d interactions", interaction);
				}
				set.setName(name);
				md.addDataInfo(ENERGY, set.getShape());
				result.addDataset(name, set, md);
			}
			
			
			
		} catch (Exception e) {
			logger.error("Exception message: {}", e.getMessage());
			throw new ScanFileHolderException("XMSOLoader.loadFile exception loading  " + fileName, e);
		}
		
		return result;
	}
	
}
