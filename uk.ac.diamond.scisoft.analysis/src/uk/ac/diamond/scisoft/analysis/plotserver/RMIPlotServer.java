/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

import java.rmi.RemoteException;

import uk.ac.diamond.scisoft.analysis.RMIServerProvider;

/**
 *
 */
public class RMIPlotServer extends SimplePlotServer implements IPlotRMI {

	public final static String RMI_SERVICE_NAME = "RMIPlotServer";
	public RMIPlotServer() throws Exception {
		RMIServerProvider.getInstance().exportAndRegisterObject(RMI_SERVICE_NAME, this);
	}

	@Override
	public DataBean getData(String guiName) throws RemoteException {
		try {
			return super.getData(guiName);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void setData(String guiName, DataBean data) throws RemoteException {
		try {
			super.setData(guiName, data);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void updateData(String guiName) throws RemoteException {
		try {
			super.updateData(guiName);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void updateGui(String guiName, GuiBean guiData) throws RemoteException {
		try {
			super.updateGui(guiName, guiData);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
