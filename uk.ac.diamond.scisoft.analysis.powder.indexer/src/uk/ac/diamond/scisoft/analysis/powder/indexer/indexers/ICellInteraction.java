package uk.ac.diamond.scisoft.analysis.powder.indexer.indexers;


import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Crystal;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.CrystalSystem;
import uk.ac.diamond.scisoft.analysis.powder.indexer.crystal.Lattice;

public interface ICellInteraction {
	
	Crystal getSearchCrystal();
	
	Lattice getLattice();
}
