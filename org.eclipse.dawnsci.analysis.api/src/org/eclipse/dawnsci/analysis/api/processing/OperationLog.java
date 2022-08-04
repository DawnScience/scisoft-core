/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.processing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log of what happened during an operation
 */
public class OperationLog {
	private static final Logger l = LoggerFactory.getLogger(OperationLog.class);

	private static final String NEWLINE = "\n";
	StringBuilder log = new StringBuilder();
	List<Integer> success = new ArrayList<>(); // contains pairs of start, length
	List<Integer> failure = new ArrayList<>(); // contains pairs of start, length

	/**
	 * Name of property to set to "true" to send OperationLog messages to {@link Logger} at DEBUG level
	 */
	public static final String OPERATION_LOG_DEBUG = "org.eclipse.dawnsci.processing.operation.log.debug";

	private boolean debug = "true".equalsIgnoreCase(System.getProperty(OPERATION_LOG_DEBUG));

	/**
	 * Format a string like in {@link String#format(String, Object...)} and append it to the log
	 * @param format
	 * @param objs
	 */
	public void append(String format, Object... objs) {
		append(null, format, objs);
	}

	/**
	 * Format a string like in {@link String#format(String, Object...)} and append it to the log as a failure
	 * @param format
	 * @param objs
	 */
	public void appendFailure(String format, Object... objs) {
		append(failure, format, objs);
	}

	/**
	 * Format a string like in {@link String#format(String, Object...)} and append it to the log as a success
	 * @param format
	 * @param objs
	 */
	public void appendSuccess(String format, Object... objs) {
		append(success, format, objs);
	}

	/**
	 * Format a string like in {@link String#format(String, Object...)} and append it to the log
	 * @param format
	 * @param objs
	 */
	private void append(List<Integer> list, String format, Object... objs) {
		if (objs.length > 0) { // replace throwable at end with all causes
			Object last = objs[objs.length - 1];
			if (last instanceof Throwable) {
				Throwable t = (Throwable) last;
				StringBuilder s = new StringBuilder();
				s.append(t.getClass().getName());
				s.append(": ");
				s.append(t.getMessage());
				Throwable c;
				while ((c = t.getCause()) != null && t != c) {
					s.append("\n\t");
					s.append(c.getClass().getName());
					s.append(": ");
					s.append(c.getMessage());
					t = c;
				}
				Array.set(objs, objs.length - 1, s.toString());
			}
		}
		String s = String.format(format, objs);
		if (list != null) {
			list.add(log.length());
			list.add(s.length());
		}
		log.append(s);
		log.append(NEWLINE);
		if (debug) {
			l.debug(s);
		}
	}

	/**
	 * Get success data
	 * @return list of pairs of start, length
	 */
	public List<Integer> getSuccess() {
		return success;
	}

	/**
	 * Get failure data
	 * @return list of pairs of start, length
	 */
	public List<Integer> getFailure() {
		return failure;
	}

	@Override
	public String toString() {
		return log.toString();
	}

	/**
	 * @param debug if true, turn on debug output to logger
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Clear contents of log
	 */
	public void clear() {
		log.setLength(0);
		success.clear();
		failure.clear();
	}

	/**
	 * Append log onto this log
	 * @param log
	 */
	public void append(OperationLog log) {
		int offset = this.log.length();
		this.log.append(log.toString());
		appendList(offset, failure, log.getFailure());
		appendList(offset, success, log.getSuccess());
	}

	private void appendList(int offset, List<Integer> current, List<Integer> other) {
		for (int i = 0, imax = other.size(); i < imax; i += 2) {
			current.add(other.get(i) + offset);
			current.add(other.get(i+1));
		}
	}
}
