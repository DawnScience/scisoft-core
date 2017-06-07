package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import uk.co.norphos.crystallography.toolkit.Crystal;
import uk.co.norphos.crystallography.toolkit.Lattice;

public interface ICellInteraction {
	
	public void setAVal(double a);
	public double getAVal();
	
	public void setBVal(double b);
	public double getBVal();
	
	public void setCVal(double c);
	public double getCVal();
	
	public double getAlphaVal();
	public void setAlphaVal(double alpha);
	
	public double getBetaVal();
	public void setBetaVal(double beta);
	
	public double getGammaVal();
	public void setGammaVal(double gamma);
	
	Crystal getSearchCrystal();
	
	Lattice getLattice();
}
