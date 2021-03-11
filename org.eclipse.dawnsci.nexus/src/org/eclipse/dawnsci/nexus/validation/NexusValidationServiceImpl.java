package org.eclipse.dawnsci.nexus.validation;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NXsubentry;
import org.eclipse.dawnsci.nexus.NexusApplicationDefinition;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusUtils;

public class NexusValidationServiceImpl implements NexusValidationService {

	@Override
	public ValidationReport validateNexusFile(String filePath) throws NexusException {
		final TreeFile nexusTree = NexusUtils.loadNexusTree(filePath);
		return validateNexusTree(nexusTree);
	}

	@Override
	public ValidationReport validateNexusTree(Tree tree) {
		final NXroot nexusRoot = (NXroot) tree.getGroupNode();
		return validateNexusTree(nexusRoot);
	}

	@Override
	public ValidationReport validateNexusTree(NXroot nexusRoot) {
		final Map<String, NXentry> entries = nexusRoot.getAllEntry();
		if (entries.size() == 1) {
			return validateEntry(entries.values().iterator().next());
		}
		
		final ValidationReport validationReport = new ValidationReport();
		for (NXentry entry : entries.values()) {
			validationReport.merge(validateEntry(entry));
		}
		return validationReport;
	}

	@Override
	public ValidationReport validateEntry(NXentry entry) {
		final ValidationReport validationReport = doValidateEntry(entry);
		
		// validate any subentries
		final Map<String, NXsubentry> subentries = entry.getAllSubentry();
		if (!subentries.isEmpty()) {
			subentries.values().stream().map(this::validateSubentry).forEach(validationReport::merge);
		}
		return validationReport;
	}
	
	@Override
	public ValidationReport validateSubentry(NXsubentry subentry) {
		return doValidateEntry(subentry);
	}
	
	private ValidationReport doValidateEntry(NXsubentry entry) {
		final String definitionStr = entry.getDefinitionScalar();
		if (definitionStr == null) {
			return new ValidationReport();
		}
		
		final NexusApplicationDefinition appDef = NexusApplicationDefinition.fromName(definitionStr);
		final NexusApplicationValidator validator = appDef.createNexusValidator();
		return validator.validate(entry);
	}

}
