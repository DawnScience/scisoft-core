package org.eclipse.dawnsci.nexus.template;

import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.NexusException;

/**
 * A nexus template that can be applied to a nexus file one of two ways:<ul>
 * <li>by calling {@link #apply(String)} with a the file path of the nexus file</li>
 * <li>by calling {@link #apply(Tree)} with the {@link Tree} object at the root of an
 *   in-memory nexus tree.</li>
 * </ul>
 * 
 * @author wgp76868
 *
 */
public interface NexusTemplate {
	
	/**
	 * Applies the nexus template to the given in-memory NeXus {@link Tree}.
	 * @param nexusTree the nexus tree to apply the template to
	 * @throws NexusException if the template could not be fully applied for any reason,
	 *    note the template may have been partially applied, so the tree may have some
	 *    modifications
	 */
	public void apply(Tree nexusTree) throws NexusException;
	
	/**
	 * Applies the nexus template to the NeXus file at the given file path.
	 * @param nexusTree the path of the NeXus file to apply the template to
	 * @throws NexusException if the template could not be fully applied for any reason,
	 *    note the template may have been partially applied, so the file may have some
	 *    modifications
	 */
	public void apply(String nexusFilePath) throws NexusException;
	
}
