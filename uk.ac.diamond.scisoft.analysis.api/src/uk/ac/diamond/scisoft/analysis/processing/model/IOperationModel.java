/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.processing.model;


/**
 * A model to use with operations. Do not put JSON stuff in here.
 * 
 * This model is designed to be the POJO and should be separate
 * from the serialization methods such as XML or JSON. It should not contain
 * mathematical methods or non-data operations.
 * 
 * OperationModel objects must be POJOs! Do not put unexpected methods
 * not involved with setting and getting the data. Put only model things
 * here or the reflection risks doing the wrong thing or perhaps unintended 
 * dependencies will be created.
 * 
 */
public interface IOperationModel {

	/**
	 * Get a field by name using reflection.
	 * @param name
	 * @return field value
	 */
	public Object get(String name) throws Exception;
	
	/**
	 * Set a field by name using reflection.
	 * @param name
	 * @return fields old value, or null
	 */
	public Object set(String name, Object value) throws Exception;

}
