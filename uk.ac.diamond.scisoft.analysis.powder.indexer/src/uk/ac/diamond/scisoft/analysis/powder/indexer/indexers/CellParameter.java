package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Lattice;

/**
 *         Structure of cell parameters receive after indexing.
 *			
 *			TODO: zero point + vol + more scraped besides merit			
 * @author Dean P. Ottewell
 */
public class CellParameter extends Lattice implements ICellParameter {

	private Double merit;
	
	private String indexerIdentifer;
	
	public CellParameter() {
		//Default lattice TODO: should be intialising to zero?
		super(0.0, 0.0, 0.0, 90.0, 90.0, 90.0);
	}

	public CellParameter(double a, double b, double c, double al, double be, double ga, double merit, String indexerId){
		super(a, b, c, al, be, ga);
		this.merit = merit;
		this.indexerIdentifer = indexerId;
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

	public double getFigureMerit() {
		return merit;
	}

	public String getIndexerIdentifer() {
		return indexerIdentifer;
	}

	public void setIndexerIdentifer(String indexerIdentifer) {
		this.indexerIdentifer = indexerIdentifer;
	}
	
}