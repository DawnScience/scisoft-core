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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By default all fields in the model are editable. This annotation can be used to 
 * mark fields as invisible or just read only. This is used in the GUI to determine which
 * fields of the model should be editable in the UI.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationModelField {

	/**
	 * 
	 * @return true if the field is visible in the UI
	 */
	public boolean visible() default true;
	
	/**
	 * 
	 * @return true if the field is editable or false for read only.
	 */
	public boolean editable() default true;

	/**
	 * The label attribute. If unset, uses the name of the field for the label.
	 */
	public String label() default "";
	
	/**
	 * 
	 * @return maximum allowed legal value for field
	 */
	public double max() default 1000d;
	
	/**
	 * 
	 * @return minimum allowed legal value for field
	 */
	public double min() default 0d;

	/**
	 * 
	 * @return the unit that the fields value should be in.
	 */
	public String unit() default "";
	
	/**
	 * 
	 * @return the string hint which is shown to the user when they first edit the value.
	 */
	public String hint() default "";
	
}
