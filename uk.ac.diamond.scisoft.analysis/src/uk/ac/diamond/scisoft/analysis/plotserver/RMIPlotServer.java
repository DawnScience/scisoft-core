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
