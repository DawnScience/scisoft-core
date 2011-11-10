/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

