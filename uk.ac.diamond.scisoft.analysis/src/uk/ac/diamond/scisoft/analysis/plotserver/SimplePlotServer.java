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
