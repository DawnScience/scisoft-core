/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.io.File;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener.FlattenedFormChecker;

public interface IRootFlattener {
	/**
	 * Flattens the given object. Will only be called if canFlatten(obj) is true.
	 * <p>
	 * The normal thing to do would be to represent the object as a dictionary ({@link Map}) with the special key
	 * __type__ ({@link IFlattener#TYPE_KEY}) used to identify the type of the object so that the unflattener can
	 * identify and unflatten it. By convention, the __type__ key is the qualified Java type name.
	 * 
	 * @param obj
	 * @return the flattened form of obj. Returned value must be {@link FlattenedFormChecker#legal(Object)}
	 */
	public Object flatten(Object obj);

	/**
	 * Takes a flattened object and reconstructs the original object. Will only be called if canUnFlatten(obj) is true
	 * 
	 * @param obj
	 *            which is of flattened form and which is {@link FlattenedFormChecker#legal(Object)}
	 * @return the unflattened version of obj
	 */
	public Object unflatten(Object obj);

	/**
	 * Tests whether an object can be flattened by this IFlattener
	 * 
	 * @param obj
	 * @return true if obj can be flattened
	 */
	public boolean canFlatten(Object obj);

	/**
	 * Tests whether an object can be unflattened by this IFlattener
	 * 
	 * @param obj
	 *            which is of flattened form and which is {@link FlattenedFormChecker#legal(Object)}
	 * @return true if obj can be unflattened
	 */
	public boolean canUnFlatten(Object obj);

	/**
	 * Additional flatteners can be registered with the root flattener by adding themselves with addHelper. Helpers
	 * added here are checked for their ability to flatten and unflatten before the built-in helpers are called.
	 * <p>
	 * It is recommended for new object to, where possible, implement IFlattens and pass to addHelper an IFlattener that
	 * only unflattens.
	 * 
	 * @param helper
	 *            new helper to add to the beginning of the list of flatters
	 */
	public void addHelper(IFlattener<?> helper);

	/**
	 * Current location for temporary files.
	 * 
	 * @return temp file location, or <code>null</code> to indicate use of system temp
	 */
	public File getTempLocation();

	/**
	 * Set a custom temporary file location. This is used by some flatteners to store large data sets which are faster
	 * to write to disk than send over XML-RPC.
	 * <p>
	 * There is no requirement for the unflattener at the other end to have the same temp location as a full path to the
	 * temp file should be stored in the flattened form.
	 * 
	 * @param tempLocation
	 *            new temp file location to use, or <code>null</code>to use default of system temp
	 */
	public void setTempLocation(String tempLocation);

}
