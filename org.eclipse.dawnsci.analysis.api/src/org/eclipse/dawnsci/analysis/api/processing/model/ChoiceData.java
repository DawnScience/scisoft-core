/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.processing.model;

import java.util.Arrays;

/**
 * Contains a number of choices and the chosen
 */
public class ChoiceData {
	private String[] choices;
	private String chosen;

	public ChoiceData(String[] choices, String chosen) {
		this.choices = choices;
		setChosen(chosen);
	}

	public ChoiceData(String[] choices) {
		this.choices = choices;
		this.chosen = choices[0];
	}

	public ChoiceData(ChoiceData c) {
		this(c.choices, c.chosen);
	}

	public void setChosen(String choice) {
		// do not validate choice as UI can set empty string
		this.chosen = choice;
	}

	/**
	 * @return chosen string that can be not from choices
	 */
	public String getChosen() {
		return chosen;
	}

	public String[] getChoices() {
		return choices;
	}

	@Override
	public String toString() {
		return chosen;
	}

	/**
	 * @return true if chosen is from choices
	 */
	public boolean isValid() {
		return Arrays.stream(choices).anyMatch(c -> c.equals(chosen));
	}
}
