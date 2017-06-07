package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import uk.ac.diamond.scisoft.xpdf.views.CrystalSystem;

import uk.ac.diamond.scisoft.xpdf.views.XPDFPhase;
import uk.ac.diamond.scisoft.xpdf.views.XPDFPhaseForm;

import uk.co.norphos.crystallography.toolkit.Crystal;
import uk.co.norphos.crystallography.toolkit.Lattice;
import uk.co.norphos.crystallography.toolkit.UnitCell;

/**
 *         Structure of cell parameters receive after indexing.
 *
 *         A wrapper over XPDFPhase to add specific factors found with cells.
 *			
 *
 *         //TODO: only use the norphos crystal and no longer extend on xpdfphase
 *         
 * @author Dean P. Ottewell
 */
public class CellParameter extends XPDFPhase {

	private Double merit;
	
	private String indexerIdentifer;

	private CellInteraction cell;
	
	public CellParameter() {
		setForm(XPDFPhaseForm.get(XPDFPhaseForm.Forms.CRYSTALLINE)); 

		CrystalSystem system = new CrystalSystem();
		// Extract crystal system indexing found
		setCrystalSystem(system); // Shouldnt really be having to set this
		// setCrystalSystem(inSystem);
		
		cell = new CellInteraction();
		
		//TODO: intialise to zero
		// setUnitCellAngles(a, b, c);
		setUnitCellLengths(0, 0, 0);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		CellParameter cell = (CellParameter) obj;

		return (cell.getUnitA().equals(this.getUnitA()) || cell.getUnitB().equals(this.getUnitB())
				|| cell.getUnitC().equals(this.getUnitC()) || cell.getUnitCellAngle().equals(this.getUnitCellAngle()));
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
	
	//TODO: all these must be used differently to use the norphos crystal now. 
	//Did something similar in the ui config...might want to just expose that method
	public Double getUnitA() {
		return getUnitCellLength(0);
	}
	public void setUnitA(double a) {
		cell.setAVal(a);
		setUnitCellLengths(a, getUnitB(), getUnitC());
	}
	

	public Double getUnitB() {
		return getUnitCellLength(1);
	}
	public void setUnitB(double b) {
		cell.setBVal(b);
		setUnitCellLengths(getUnitA(), b, getUnitC());
	}
	

	public Double getUnitC() {
		return getUnitCellLength(2);
	}
	public void setUnitC(double c) {
		cell.setCVal(c);
		setUnitCellLengths(getUnitA(), getUnitB(), c);
	}


	public Double getAngleAlpha() {
		double[] angs = getUnitCellAngle();
		return angs[0];
	}
	public void setAngleAlpha(double a) {
		cell.setAlphaVal(a);
		setUnitCellAngles(a, getAngleBeta(), getAngleGamma());
	}

	public Double getAngleBeta() {
		double[] angs = getUnitCellAngle();
		return angs[1];
	}
	public void setAngleBeta(double b) {
		cell.setBetaVal(b);
		setUnitCellAngles(getAngleAlpha(), b, getAngleGamma());
	}

	public Double getAngleGamma() {
		double[] angs = getUnitCellAngle();
		return angs[2];
	}
	public void setAngleGamma(double g) {
		cell.setGammaVal(g);
		setUnitCellAngles(getAngleAlpha(), getAngleBeta(), g);
	}

	public String getIndexerIdentifer() {
		return indexerIdentifer;
	}

	public void setIndexerIdentifer(String indexerIdentifer) {
		this.indexerIdentifer = indexerIdentifer;
	}
	
}