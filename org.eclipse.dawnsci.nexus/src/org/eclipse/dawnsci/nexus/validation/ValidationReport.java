package org.eclipse.dawnsci.nexus.validation;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.nexus.validation.ValidationReportEntry.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A report produced by validating a nexus file according to an Nexus application definition.
 * The report is made up of zero or more {@link ValidationReportEntry}s.
 * 
 * @author Matthew Dickie
 */
public class ValidationReport {
	
	private static final Logger logger = LoggerFactory.getLogger(ValidationReport.class);
	 
	private final List<ValidationReportEntry> validationEntries = new ArrayList<>();
	
	public void merge(ValidationReport report) {
		validationEntries.addAll(report.getValidationEntries());
	}
	
	public List<ValidationReportEntry> getValidationEntries() {
		return validationEntries;
	}
	
	public void addValidationEntry(ValidationReportEntry validationEntry) {
		logValidationEntry(validationEntry);
		validationEntries.add(validationEntry);
	}
	
	private void logValidationEntry(ValidationReportEntry validationEntry) {
		switch (validationEntry.getLevel()) {
			case ERROR:
				logger.error(validationEntry.getLogMessage());
				break;
			case WARNING:
				logger.error(validationEntry.getLogMessage());
				break;
			case INFO:
				logger.error(validationEntry.getLogMessage());
				break;
			default:
				throw new IllegalArgumentException("Unknown validation level: " + validationEntry.getLevel());
		}
	}
	
	public boolean isOk() {
		return validationEntries.isEmpty();
	}
	
	public boolean isError() {
		return !validationEntries.isEmpty() && validationEntries.stream().
				map(ValidationReportEntry::getLevel).
				anyMatch(level -> level == Level.ERROR);
	}

	public String summarize() {
		return summarize(Level.INFO);
	}
	
	public String summarize(Level level) {
		return validationEntries.stream().
				filter(entry -> entry.getLevel().compareTo(level) >= 0).
				map(ValidationReportEntry::toString).collect(joining("\n"));
	}
	
	public String toString() {
		return summarize(Level.INFO);
	}

}
