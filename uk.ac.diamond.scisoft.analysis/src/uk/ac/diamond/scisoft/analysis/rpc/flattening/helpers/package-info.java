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

