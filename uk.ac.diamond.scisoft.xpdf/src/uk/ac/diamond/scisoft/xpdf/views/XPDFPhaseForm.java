/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.Collection;
import java.util.EnumMap;

class XPDFPhaseForm {

	private static EnumMap<Forms, XPDFPhaseForm> formMap;
	private static EnumMap<Forms, String> names;
	public enum Forms {AMORPHOUS, GLASSY, LIQUID, CRYSTALLINE};
	private Forms form; 
	
	public static XPDFPhaseForm get(Forms inForm) {
		if (formMap == null) 
			generate();
		return formMap.get(inForm);
	}
	
	private static void generate() {
		formMap = new EnumMap<XPDFPhaseForm.Forms, XPDFPhaseForm>(XPDFPhaseForm.Forms.class);
		for(Forms form : Forms.values()) {
			XPDFPhaseForm phorm = new XPDFPhaseForm();
			phorm.form = form;
			formMap.put(form, phorm);
		}
		names = new EnumMap<XPDFPhaseForm.Forms, String>(XPDFPhaseForm.Forms.class);
		names.put(Forms.AMORPHOUS, "Amorphous");
		names.put(Forms.GLASSY, "Glassy");
		names.put(Forms.LIQUID, "Liquid");
		names.put(Forms.CRYSTALLINE, "Crystalline");
	}

	public static String[] getNames() {
		Collection<String> allNames = names.values();
		return allNames.toArray(new String[allNames.size()]);
	}
	
	public int getOrdinal() {
		return form.ordinal();
	}
	
	public String getName() {
		return names.get(form);
	}
	
	public boolean isCrystalline() {
		return form == Forms.CRYSTALLINE;
	}
}
