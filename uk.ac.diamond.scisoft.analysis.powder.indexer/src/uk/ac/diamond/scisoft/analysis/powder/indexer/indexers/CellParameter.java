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

	private CellInteraction cell;
	
	public CellParameter() {
		
		cell = new CellInteraction();
		//TODO: intialise to zero?
	}

	@Override
	public boolean equals(Object obj) {
		return cell.equals(obj);
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

	public CellInteraction getCell(){
		return this.cell;
	}
	
	public String getIndexerIdentifer() {
		return indexerIdentifer;
	}

	public void setIndexerIdentifer(String indexerIdentifer) {
		this.indexerIdentifer = indexerIdentifer;
	}
	
}