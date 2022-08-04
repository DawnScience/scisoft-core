package org.eclipse.dawnsci.nexus.validation;

import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NexusException;

/**
 * A service providing methods to validate nexus trees, entries and files
 * to produce a {@link ValidationReport} detailing any validation issues.
 */
public interface NexusValidationService {
	
	/**
	 * Sets whether to validate according to the Diamond Default Nexus File Structure,
	 * as well as any application definitions that are declared
	 * on the entry and/or subentries. This validator will be invoked when calling
	 * {@link #validateEntry(NXentry)} and on the first {@link NXentry} in a nexus tree
	 * when calling {@link #validateNexusFile(String)}, {@link #validateNexusTree(Tree)
	 * or #validateNexusTree(NXroot).
	 * @param validateDiamond <code>true</code> to validate diamond default nexus
	 *     structure, <code>false</code> otherwise
	 */
	public void setValidateDiamond(boolean validateDiamond);
	
	/**
	 * Validates the nexus file at the given path, producing a {@link ValidationReport}.
	 * @param filePath path of nexus file to validate
	 * @return a validation report detailing any validation issues
	 * @throws NexusException if the file could not be loaded
	 */
	public ValidationReport validateNexusFile(String filePath) throws NexusException;
	
	/**
	 * Validates the given nexus tree, producing a {@link ValidationReport}.
	 * @param tree nexus tree to validate
	 * @return a validation report detailing any validation issues
	 */
	public ValidationReport validateNexusTree(Tree tree);
	
	/**
	 * Validates the given nexus tree, producing a {@link ValidationReport}.
	 * @param root root of the nexus tree to validate
	 * @return a validation report detailing any validation issues
	 */
	public ValidationReport validateNexusTree(NXroot root);
	
	/**
	 * Validates the given nexus entry, producing a {@link ValidationReport}.
	 * @param entry nexus entry to validate
	 * @return a validation report detailing any validation issues
	 */
	public ValidationReport validateEntry(NXentry entry);

	/**
	 * Validates the given nexus subentry, producing a {@link ValidationReport}.
	 * @param subentry nexus subentry to validate
	 * @return a validation report detailing any validation issues
	 */
	public ValidationReport validateSubentry(NXsubentry subentry);
	
}
