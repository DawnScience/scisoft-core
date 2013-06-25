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

package uk.ac.diamond.scisoft.analysis;

import gda.observable.IObserver;
import junit.framework.AssertionFailedError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.plotserver.AbstractPlotServer;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;

public class MockPlotServer extends AbstractPlotServer {

	private static final Logger logger = LoggerFactory.getLogger(MockPlotServer.class);

	private String lastPlotname;
	
	private GuiBean lastGuiBean;
	
	private DataBean lastDataBean;
	
	public void clear() {
		lastPlotname = null;
		lastGuiBean = null;
		lastDataBean = null;
		getGuiStore().clear();
		getDataStore().clear();
	}
	
	public String getLastPlotname() {
		return lastPlotname;
	}

	public GuiBean getLastGuiBean() {
		return lastGuiBean;
	}

	public DataBean getLastDataBean() {
		return lastDataBean;
	}


	@Override
	public void updateGui(String guiName, GuiBean guiData) throws Exception {
		super.updateGui(guiName, guiData);
		lastPlotname = guiName;
		lastGuiBean = guiData;
		logger.info("Mock-updating gui with");
		logger.info("guiName = " + guiName);
		logger.info("guiData = " + guiData);

	}

	@Override
	public void setData(String guiName, DataBean plotData) throws Exception {
		super.setData(guiName, plotData);
		lastPlotname = guiName;
		lastDataBean = plotData;
		logger.info("Mock-setting data with");
		logger.info("guiName = " + guiName);
		logger.info("plotData = " + plotData);
	}
	
	// These methods should be filled in as/if needed

	@Override
	public void updateData(String guiName) throws Exception {
		throw new AssertionFailedError("No Implementation, method should not be called.");
	}

	@Override
	public boolean isServerLocal() throws Exception {
		throw new AssertionFailedError("No Implementation, method should not be called.");
	}

	@Override
	public void addIObserver(IObserver observer) {
		throw new AssertionFailedError("No Implementation, method should not be called.");
	}

	@Override
	public void deleteIObserver(IObserver observer) {
		throw new AssertionFailedError("No Implementation, method should not be called.");
	}

	@Override
	public void deleteIObservers() {
		throw new AssertionFailedError("No Implementation, method should not be called.");
	}
}
