package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;

import uk.co.norphos.crystallography.toolkit.Crystal;
import uk.co.norphos.crystallography.toolkit.CrystalSystem;
import uk.co.norphos.crystallography.toolkit.Lattice;

public class CellInteraction implements ICellInteraction {
	
	private Crystal crystalSys;
	
	private Lattice latt;
	
	public CellInteraction(){
		//Initialise unitcell
		//this.unitcell = new CellParameter();
		
		//Cant initialise
		//Lattice latt = new Lattice(angleTol, angleTol, angleTol, angleTol, angleTol, angleTol, null);
		//this.crytalSys = new Crystal();
		//Generic crystall initialisation
		crystalSys = new Crystal(new Lattice.LatticeBuilder(1).build(), CrystalSystem.CUBIC);
	}
	
	public void setAVal(double a){
		Lattice preLatt =this.crystalSys.getUnitCell().getLattice();
		Lattice nLatt = new Lattice.LatticeBuilder(preLatt).setA(a).build();
		this.crystalSys = new Crystal(nLatt, this.crystalSys.getCrystalsystem());
	}

	public double getAVal(){
		return crystalSys.getUnitCell().getLattice().a();
	}
	
	public void setBVal(double b){
		Lattice preLatt =this.crystalSys.getUnitCell().getLattice();
		Lattice nLatt = new Lattice.LatticeBuilder(preLatt).setB(b).build();
		this.crystalSys = new Crystal(nLatt, this.crystalSys.getCrystalsystem());
	}	
	public double getBVal(){
		return crystalSys.getUnitCell().getLattice().b();
	}
	
	public void setCVal(double c){
		Lattice preLatt =this.crystalSys.getUnitCell().getLattice();
		Lattice nLatt = new Lattice.LatticeBuilder(preLatt).setC(c).build();
		this.crystalSys = new Crystal(nLatt, this.crystalSys.getCrystalsystem());
	}
	public double getCVal(){
		return crystalSys.getUnitCell().getLattice().c();
	}
	
	
	public double getAlphaVal() {
		return crystalSys.getUnitCell().getLattice().al();
	}
	public void setAlphaVal(double alpha) {
		Lattice preLatt =this.crystalSys.getUnitCell().getLattice();
		Lattice nLatt = new Lattice.LatticeBuilder(preLatt).setAl(alpha).build();
		this.crystalSys = new Crystal(nLatt, this.crystalSys.getCrystalsystem());
	}
	
	public double getBetaVal() {
		return crystalSys.getUnitCell().getLattice().be();
	}
	public void setBetaVal(double beta) {
		Lattice preLatt =this.crystalSys.getUnitCell().getLattice();
		Lattice nLatt = new Lattice.LatticeBuilder(preLatt).setBe(beta).build();
		this.crystalSys = new Crystal(nLatt, this.crystalSys.getCrystalsystem());
	}
	
	public double getGammaVal() {
		return crystalSys.getUnitCell().getLattice().ga();
	}
	public void setGammaVal(double gamma) {
		Lattice preLatt =this.crystalSys.getUnitCell().getLattice();
		Lattice nLatt = new Lattice.LatticeBuilder(preLatt).setGa(gamma).build();
		this.crystalSys = new Crystal(nLatt, this.crystalSys.getCrystalsystem());
	}
	
	
	@Override
	public Crystal getSearchCrystal() {
		return crystalSys;
	}
	@Override
	public Lattice getLattice() {
		return crystalSys.getUnitCell().getLattice();
	}
	
}
