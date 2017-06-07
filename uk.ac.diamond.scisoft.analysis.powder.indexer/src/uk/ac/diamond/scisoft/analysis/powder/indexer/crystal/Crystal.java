package uk.ac.diamond.scisoft.analysis.powder.indexer.crystal;

public class Crystal {
	
	private UnitCell unitCell;
	private CrystalSystem crystalSystem;
	
	public Crystal(Lattice lattice) {
		unitCell = new UnitCell(lattice);
	}
	
	public UnitCell getUnitCell() {
		return unitCell;
	}

}
