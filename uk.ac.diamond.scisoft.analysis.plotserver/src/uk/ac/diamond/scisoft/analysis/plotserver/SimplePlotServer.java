/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import gda.configuration.properties.LocalProperties;
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
		this(false);
	}
	public SimplePlotServer(boolean removeOnGet) {
		super(removeOnGet);
		basePath = System.getProperty(LocalProperties.GDA_DATA);
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
		boolean respond = !guiData.containsKey(GuiParameters.QUIET_UPDATE);
		super.updateGui(guiName, guiData);
		if (respond) {
			respondToGui(guiName, guiData);
			GuiUpdate update = new GuiUpdate(guiName, guiData);
			notifyIObservers(this, update);
		}
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
