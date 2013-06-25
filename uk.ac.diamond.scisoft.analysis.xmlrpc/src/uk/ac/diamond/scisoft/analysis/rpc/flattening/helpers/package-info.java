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

/**
 * This package contains the helper classes. There is a roughly 1-2-1 match between the concrete classes in this package
 * and the data types throughout scisoft analysis that are supported.
 * <p>
 * New data types flatteners can be added here and manually added to
 * {@link uk.ac.diamond.scisoft.analysis.rpc.flattening.RootFlattener}, or they can be defined somewhere else
 * and be registered at runtime with
 * {@link uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener#addHelper(uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener)}
 * <p>
 * The contents of this package are internal and do not form part of API
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

