package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

/**
 *         Structure of cell parameters receive after indexing.
 *			
 *			TODO: zero point + vol + more scraped besides merit			
 *
 *
 *	
 * @author Dean P. Ottewell
 */
public class CellParameter extends CellInteraction {

	private Double merit;
	
	private String indexerIdentifer;

	
	public CellParameter() {
		
		//TODO: intialise to zero?
	}

	
	public boolean isGreaterMerit(Object obj){
		
		if (obj == this) {
			return true;
		}

		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		CellParameter cell = (CellParameter) obj;

		
		return (cell.getFigureMerit() > this.getFigureMerit());
	}
	

	public void setFigureMerit(double m) {
		merit = m;
	}

	public Double getFigureMerit() {
		return merit;
	}

	public String getIndexerIdentifer() {
		return indexerIdentifer;
	}

	public void setIndexerIdentifer(String indexerIdentifer) {
		this.indexerIdentifer = indexerIdentifer;
	}
	
}