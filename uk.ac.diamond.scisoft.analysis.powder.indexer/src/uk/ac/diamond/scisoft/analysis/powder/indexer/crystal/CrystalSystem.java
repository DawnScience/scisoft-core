package uk.ac.diamond.scisoft.analysis.powder.indexer.crystal;

/**
 * An enum containing the names of the different crystal systems. 
 * 
 * The crystal system is defined by the metric symmetry of the lattice. By 
 * considering only the lattice parameters it is not possible to distinguish 
 * between trigonal, rhombohedral and hexagonal, thus they are grouped 
 * together in the same crystal family. Moreover, the trigonal and 
 * rhombohedral crystal systems have the same point group symmetry. The table 
 * below provides a guide for which {@link CrystalSystem} to use.
 * 
 * <table summary="Value to use with Lattice and Crystal System">
 * 	<tr><th>CrystalSystem (enum)	</th><th>Crystal Family	</th><th>Crystal System			</th></tr>
 * 	<tr><td>CUBIC					</td><td>Cubic			</td><td>Cubic					</td></tr>
 * 	<tr><td>HEXAGONAL				</td><td>Hexagonal		</td><td>Hexagonal				</td></tr>
 * 	<tr><td>RHOMBOHEDRAL			</td><td>Hexagonal		</td><td>Rhombohedral/Trigonal	</td></tr>
 * 	<tr><td>TRIGONAL				</td><td>Hexagonal		</td><td>Trigonal				</td></tr>
 * 	<tr><td>TETRAGONAL				</td><td>Tetragonal		</td><td>Tetragonal				</td></tr>
 * 	<tr><td>ORTHORHOMBIC			</td><td>Orthorhombic	</td><td>Orthorhombic			</td></tr>
 * 	<tr><td>MONOCLINIC				</td><td>Monoclinic		</td><td>Monoclinic				</td></tr>
 * 	<tr><td>TRICLINIC				</td><td>Triclinic		</td><td>Triclinic				</td></tr>
 * </table
 * 
 * @author Michael Wharmby
 *
 */
public enum CrystalSystem {
	TRICLINIC, MONOCLINIC, ORTHORHOMBIC, TETRAGONAL,
	TRIGONAL, RHOMBOHEDRAL, HEXAGONAL, CUBIC; 

}
