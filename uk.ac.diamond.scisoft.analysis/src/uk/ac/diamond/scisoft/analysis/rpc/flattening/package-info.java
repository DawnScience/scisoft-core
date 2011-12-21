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
 * This package contains the "flatteners" that are used as part of AnalysisRpc to flatten/unflatten data types before
 * passing them to the XML-RPC transport layer.
 * <p>
 * Most clients will want to deal with AnalysisRpc* classes directly and let them instantiate and use the flatteners as
 * needed. The only exception is the special wrapper classes that allow specific functionality to be performed that
 * cannot be easily represented without wrapping simpler data types. At the time of writing, these are the classes, see
 * their javadocs for more info:
 * <ul>
 * <li> {@link uk.ac.diamond.scisoft.analysis.rpc.flattening.AbstractDatasetDescriptor}</li>
 * <li> {@link uk.ac.diamond.scisoft.analysis.rpc.flattening.TypedNone}</li>
 * </ul>
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

