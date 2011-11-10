/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which allows for the analysis package to print to the terminal, this should be changed in future so that it is not required.
 */
public class TerminalPrinter {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(TerminalPrinter.class);

	private static Method print;
	private static Object printer;

	static private void findPrinter() {
		try {
			Class<?> classDef = Class.forName("gda.jython.InterfaceProvider");
			Method getTerminalPrinter = classDef.getDeclaredMethod("getTerminalPrinter", (Class<?>[]) null);
			printer = getTerminalPrinter.invoke(classDef, (Object[]) null);
			Class<?> printerClass = printer.getClass();
			print = printerClass.getDeclaredMethod("print", String.class);
		} catch (Exception e) {
			logger.info("Couldn't find a terminal printer; falling back to logger", e);
			printer = logger;
			try {
				print = Logger.class.getDeclaredMethod("info", String.class);
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}
		}
	}


	/**
	 * @param output The string to write to the terminal
	 */
	public static void print(String output) {	
		if (print == null) findPrinter();
		if (print != null) {
			try {
				print.invoke(printer, output);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

}
