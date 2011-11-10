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

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Provides RMI "view" of PlotWindowManager
 * <p>
 * For a version of this interface that does not throw exceptions for use when not remote, use
 * see IPlotWindowManager (in RCP Plug-in)
 * <p>
 * NOTE, this class does not "belong" in this bundle. However there are lots of problems
 * with getting generic type support for an OSGi/RMI mixed environment. For more info see:
 * https://mail.osgi.org/pipermail/osgi-dev/2009-January/001639.html
 */
public interface IPlotWindowManagerRMI extends Remote {

	/**
	 * Create and open a view with a new unique name and fill the view's GuiBean and DataBean with a copy of viewName's
	 * beans
	 * 
	 * @param viewName
	 *            to duplicate
	 * @return name of the newly duplicated and opened view
	 * @throws RemoteException
	 *             on RMI Communications Exception
	 */
	public String openDuplicateView(String viewName) throws RemoteException;

	/**
	 * Opens the plot view with the given name. If the view name is registered with Eclipse as a primary view, open
	 * that, otherwise open a new Plot window with the given name.
	 * 
	 * @param viewName
	 *            to open, or <code>null</code> to open a newly named plot window
	 * @return name of the opened view
	 * @throws RemoteException
	 *             on RMI Communications Exception
	 */
	public String openView(String viewName) throws RemoteException;

	/**
	 * Returns a list of all the plot window views currently open.
	 * 
	 * @return list of views
	 * @throws RemoteException
	 *             on RMI Communications Exception
	 */
	public String[] getOpenViews() throws RemoteException;

}