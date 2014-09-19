/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.rmi.RemoteException;

import org.eclipse.dawnsci.analysis.api.RMIServerProvider;

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
