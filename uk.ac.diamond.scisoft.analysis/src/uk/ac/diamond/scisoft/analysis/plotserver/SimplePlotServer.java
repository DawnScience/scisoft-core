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

import gda.observable.IObserver;
import gda.observable.ObservableComponent;

import java.io.File;

import uk.ac.diamond.scisoft.analysis.PlotServer;

public class SimplePlotServer extends AbstractPlotServer {
	private ObservableComponent observableComponent = new ObservableComponent();	
	private String basePath;

	/**
	 * Constructor to initialise all the collection objects
	 */
	public SimplePlotServer() {
		basePath = System.getProperty("gda.data");
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	protected String getDefaultDataDir(){
		return basePath + File.separator;//PathConstructor.createFromDefaultProperty()
	}

	@Override
	public void setData(String guiName, DataBean data) throws Exception {
		super.setData(guiName, data);
		notifyIObservers(this, guiName);
	}
	
	@Override
	public void updateData(String guiName) throws Exception {
		notifyIObservers(this, guiName);
	}

	@Override
	public void updateGui(String guiName, GuiBean guiData) throws Exception {
		super.updateGui(guiName, guiData);
		respondToGui(guiName, guiData);
		GuiUpdate update = new GuiUpdate(guiName, guiData);
		notifyIObservers(this, update);
	}

	/**
	 * Check GuiBean and service request if necessary
	 * @param guiName
	 * @param guiData
	 */
	protected void respondToGui(String guiName, GuiBean guiData) throws Exception {

		FileOperationBean fob = null;
		if ((fob = (FileOperationBean) guiData.get(GuiParameters.FILEOPERATION)) != null) {
			switch (fob.getMode()) {
			case FileOperationBean.GETFILE:
				// TODO
				break;
			case FileOperationBean.GETIMAGEFILE:
				if (guiData.containsKey(GuiParameters.DISPLAYFILEONVIEW)) {
					String viewName = (String) guiData.get(GuiParameters.DISPLAYFILEONVIEW);
					DataBean db = fob.loadImage(false);
					if (db != null) {
						GuiBean guiBean = getGuiState(guiName);
						if (!fob.hasMultiplyFiles()) {
							if (!guiBean.containsKey(GuiParameters.PLOTMODE)
									|| !guiBean.get(GuiParameters.PLOTMODE).equals(GuiPlotMode.TWOD)) {
								guiBean = new GuiBean();
								guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.TWOD);
							}
						} else {
							if (!guiBean.containsKey(GuiParameters.PLOTMODE)
									|| !guiBean.get(GuiParameters.PLOTMODE).equals(GuiPlotMode.MULTI2D)) {
								guiBean = new GuiBean();
								guiBean.put(GuiParameters.PLOTMODE, GuiPlotMode.MULTI2D);
							}							
						}
						guiBean.remove(GuiParameters.FILEOPERATION);
						updateGui(viewName, guiBean);
						setData(viewName, db);
					}
				}
				break;
			case FileOperationBean.GETIMAGEFILE_THUMB:
				DataBean db = fob.loadImage(true);
				if (db != null) {
					setData(guiName, db);
				}
				break;
			case FileOperationBean.GETDIR:
			case FileOperationBean.UPDIR:
			case FileOperationBean.DNDIR:
				guiData.put(GuiParameters.FILEOPERATION, fob.chDir(basePath));
				break;
			case FileOperationBean.DELETEGRIDIMGTEMPDIR:
				fob.deleteDir(getDefaultDataDir() + PlotServer.GRIDVIEWDIR);
				break;
			case FileOperationBean.NOOP:
			default:
				break;
			}
		}

	}

	@Override
	public void addIObserver(IObserver observer) {
		observableComponent.addIObserver(observer);
	}

	@Override
	public void deleteIObserver(IObserver observer) {
		observableComponent.deleteIObserver(observer);
	}

	@Override
	public void deleteIObservers() {
		observableComponent.deleteIObservers();
	}

	/**
	 * Notify all observers on the list of the requested change.
	 * 
	 * @param source the observed component
	 * @param arg the data to be sent to the observer.
	 */
	public void notifyIObservers(Object source, Object arg) {
		observableComponent.notifyIObservers(source, arg);
	}

	@Override
	public boolean isServerLocal() {
		return true;
	}
}
