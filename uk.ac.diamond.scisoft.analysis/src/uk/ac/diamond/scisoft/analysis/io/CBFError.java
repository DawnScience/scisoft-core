/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.iucr.cbflib.cbfConstants;

/**
 * This class takes the error code from CBFlib and converts it into human readable format.
 */

public class CBFError {

	/**
	 * Translates the error code from CBFlib that is in binary and converts it into strings to be passed to a
	 * ScanFileHolderException.
	 * 
	 * @param errorCode
	 * @return true if all okay or false if error is not fatal
	 * @throws ScanFileHolderException
	 */
	public static boolean errorChecker(int errorCode) throws ScanFileHolderException {
		String message = "";

		if ((errorCode & cbfConstants.CBF_FORMAT) != 0) {
			message = message + "cbfLib: The file format is invalid.\n";
		}
		if ((errorCode & cbfConstants.CBF_ALLOC) != 0) {
			message = message + "cbfLib: Memory allocation failed.\n";
		}
		if ((errorCode & cbfConstants.CBF_ARGUMENT) != 0) {
			message = message + "cbfLib: Invalid function argument.\n";
		}
		if ((errorCode & cbfConstants.CBF_ASCII) != 0) {
			message = message + "cbfLib: The value is ACSII not binary\n";
		}
		if ((errorCode & cbfConstants.CBF_BINARY) != 0) {
			message = message + "cbfLib: The value is binary not ASCII\n";
		}
		if ((errorCode & cbfConstants.CBF_BITCOUNT) != 0) {
			message = message + "cbfLib: The expected number of bits does not match the actual number of written.\n";
		}
		if ((errorCode & cbfConstants.CBF_ENDOFDATA) != 0) {
			message = message + "cbfLib: The end of file was reached before the end of the array.\n";
		}
		if ((errorCode & cbfConstants.CBF_FILECLOSE) != 0) {
			message = message + "cbfLib: File close error.\n";
		}
		if ((errorCode & cbfConstants.CBF_FILEOPEN) != 0) {
			message = message + "cbfLib: File open error.\n";
		}
		if ((errorCode & cbfConstants.CBF_FILEREAD) != 0) {
			message = message + "cbfLib: File read error.\n";
		}
		if ((errorCode & cbfConstants.CBF_FILESEEK) != 0) {
			message = message + "cbfLib: File seek error.\n";
		}
		if ((errorCode & cbfConstants.CBF_FILETELL) != 0) {
			message = message + " cbfLib: File tell error.\n";
		}
		if ((errorCode & cbfConstants.CBF_FILEWRITE) != 0) {
			message = message + "cbfLib: File write error.\n";
		}
		if ((errorCode & cbfConstants.CBF_IDENTICAL) != 0) {
			message = message + "cbfLib: A data block with the new name already exists.\n";
		}
		if ((errorCode & cbfConstants.CBF_OVERFLOW) != 0) {
			message = message + "cbfLib: The number cannot be fit into the destination argument.\n"
					+ "The destination has been set to the nearest value.\n";
		}
		if ((errorCode & cbfConstants.CBF_UNDEFINED) != 0) {
			message = message + "cbfLib: The requested number is not defined.\n";
		}
		if ((errorCode & cbfConstants.CBF_NOTIMPLEMENTED) != 0) {
			message = message + "cbflib: The requested functionality has not been implemented.\n";
		}
		if ((errorCode & cbfConstants.CBF_NOTFOUND) != 0) {
			message = message + "cbfLib: The data block, category, column or row does not exist.\n";
			return false;
		}
		if (errorCode != 0) {
			throw new ScanFileHolderException(message);
		}
		return true;
	}
}
