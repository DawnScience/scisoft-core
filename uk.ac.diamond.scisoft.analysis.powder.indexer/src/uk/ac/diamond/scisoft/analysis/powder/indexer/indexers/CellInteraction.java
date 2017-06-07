package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Crystal;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.CrystalSystem;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Lattice;

public class CellInteraction implements ICellInteraction {
	
	private Crystal crystalSys;
	
	private double a, b, c, al, be, ga;
	
	public CellInteraction(){
		this.a = 0;
	}
	
	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}
	
	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getAl() {
		return al;
	}

	public void setAl(double al) {
		this.al = al;
	}

	public double getBe() {
		return be;
	}

	public void setBe(double be) {
		this.be = be;
	}

	public double getGa() {
		return ga;
	}

	public void setGa(double ga) {
		this.ga = ga;
	}
	
	@Override
	public Crystal getSearchCrystal() {
		return new Crystal(getLattice()); //TODO: way to set 
	}
	@Override
	public Lattice getLattice() {
		return new Lattice(getA(), getB(), getC(), getAl(), getBe(), getGa());
	}


	
}
