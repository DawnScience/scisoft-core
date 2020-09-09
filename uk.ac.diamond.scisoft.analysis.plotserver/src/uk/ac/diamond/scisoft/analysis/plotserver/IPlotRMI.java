/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.rmi.Remote;

import uk.ac.diamond.scisoft.analysis.PlotService;

public interface IPlotRMI extends Remote, PlotService {

	// Need to override all PlotService methods to make them callable (post 8u241)
	// See non-public JDK-8230967 on https://www.oracle.com/java/technologies/javase/8u241-relnotes.html

	@Override
	public void updateGui(String guiName, GuiBean guiData) throws Exception;

	@Override
	public void setData(String guiName, DataBean plotData) throws Exception;

	@Override
	public void updateData(String guiName) throws Exception;

	@Override
	public GuiBean getGuiState(String guiName) throws Exception;

	@Override
	public boolean isServerLocal() throws Exception;

	@Override
	public String[] getGuiNames() throws Exception;

	@Override
	public DataBean getData(String guiName) throws Exception;
}
