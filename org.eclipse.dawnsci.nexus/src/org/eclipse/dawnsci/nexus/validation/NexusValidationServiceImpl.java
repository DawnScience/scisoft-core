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

	private boolean validateDiamond = true;
	
	// cache the last validation report. Used for testing purposes
	private ValidationReport lastValidationReport = null;
	
	public void setValidateDiamond(boolean validateDiamond) {
		this.validateDiamond = validateDiamond;
	}
	
	@Override
	public ValidationReport validateNexusFile(String filePath) throws NexusException {
		final TreeFile nexusTree = NexusUtils.loadNexusTree(filePath);
		return validateNexusTree(nexusTree);
	}

	@Override
	public ValidationReport validateNexusTree(Tree tree) {
		final NXroot nexusRoot = (NXroot) tree.getGroupNode();
		lastValidationReport = validateNexusTree(nexusRoot);
		return lastValidationReport;
	}

	@Override
	public ValidationReport validateNexusTree(NXroot nexusRoot) {
		final Map<String, NXentry> entries = nexusRoot.getAllEntry();
		
		if (entries.size() == 1) {
			return validateEntry(entries.values().iterator().next(), validateDiamond);
		}
		
		final ValidationReport validationReport = new ValidationReport();
		// only validate the first entry according to Diamond Default Nexus File Structure
		validationReport.merge(validateEntry(entries.values().iterator().next(), validateDiamond));
		entries.values().stream().skip(1).forEach(entry -> validationReport.merge(validateEntry(entry, false)));
		return validationReport;
	}

	@Override
	public ValidationReport validateEntry(NXentry entry) {
		final ValidationReport validationReport = doValidateEntry(entry, validateDiamond);
		
		// validate any subentries
		final Map<String, NXsubentry> subentries = entry.getAllSubentry();
		if (!subentries.isEmpty()) {
			subentries.values().stream().map(this::validateSubentry).forEach(validationReport::merge);
		}
		return validationReport;
	}
	
	private ValidationReport validateEntry(NXentry entry, boolean validateDiamond) {
		final ValidationReport validationReport = doValidateEntry(entry, validateDiamond);
		
		// validate any subentries
		final Map<String, NXsubentry> subentries = entry.getAllSubentry();
		if (!subentries.isEmpty()) {
			subentries.values().stream().map(this::validateSubentry).forEach(validationReport::merge);
		}
		return validationReport;
	}
	
	@Override
	public ValidationReport validateSubentry(NXsubentry subentry) {
		return doValidateEntry(subentry, false);
	}
	
	private ValidationReport doValidateEntry(NXsubentry entry, boolean validateDiamond) {
		final ValidationReport validationReport = new ValidationReport();
		if (validateDiamond) {
			validationReport.merge(NexusApplicationDefinition.NX_DIAMOND.createNexusValidator().validate(entry));
		}
		
		final String definitionStr = entry.getDefinitionScalar();
		if (definitionStr != null) {
			final NexusApplicationDefinition appDef = NexusApplicationDefinition.fromName(definitionStr);
			final NexusApplicationValidator validator = appDef.createNexusValidator();
			validationReport.merge(validator.validate(entry));
		}
		
		return validationReport;
	}
	
	public ValidationReport getLastValidationReport() {
		return lastValidationReport;
	}

}
