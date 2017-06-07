package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *         Basic factory made for swapping out different indexers by on the
 *         indexers ID
 * 
 * @author Dean P. Ottewell
 */
public class PowderIndexerFactory {

	private static final Logger logger = LoggerFactory.getLogger(PowderIndexerFactory.class);

	/**
	 * Creates a instance of corresponding indexer based on the unqiue ID for each idexer
	 * 
	 * @param ID
	 * @return auto-indexer 
	 */
	public static AbstractPowderIndexer createIndexer(String ID) {

		if (ID.equals(Dicvol.ID))
			return new Dicvol();
		if (ID.equals(Ntreor.ID))
			return new Ntreor();
		if (ID.equals(GsasIIWrapper.ID))
			return new GsasIIWrapper();
		else
			logger.debug("INVALID ID");
		return null;
	}

}
