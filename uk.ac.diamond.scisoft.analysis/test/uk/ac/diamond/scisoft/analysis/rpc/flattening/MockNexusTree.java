/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import gda.data.nexus.extractor.NexusGroupData;
import gda.data.nexus.tree.INexusTree;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import junit.framework.AssertionFailedError;

/**
 * For use by {@link FlatteningTestAbstract#testDataBean()}. None of the methods
 * should be called by flattening/unflattening, therefore every method is an assertion error.
 */
final class MockNexusTree implements INexusTree {
	@Override
	public Iterator<INexusTree> iterator() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public StringBuffer toXMLend(boolean newlineAfterEach, boolean dataAsString) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public StringBuffer toXMLbegin(boolean newlineAfterEach, boolean dataAsString) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String toXML(boolean newlineAfterEach, boolean dataAsString) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String toText(String prefix, String keyValueSep, String dataItemSep, String nodeSep, boolean includeData) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String toText(String prefix, String keyValueSep, String dataItemSep, String nodeSep) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public void sort(Comparator<INexusTree> comparator) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public void setParentNode(INexusTree parentNode) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public void removeChildNode(INexusTree e) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public boolean isPointDependent() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public INexusTree getParentNode() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String getNxClass() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public int getNumberOfChildNodes() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String getNodePathWithClasses() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String getNodePath() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public INexusTree getNode(String nodePath) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public String getName() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public NexusGroupData getData() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public INexusTree getChildNode(String name, String className) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public INexusTree getChildNode(int index) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public HashMap<String, Serializable> getAttributes() {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public Serializable getAttribute(String name) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public boolean equals(Object obj, boolean reportFalse) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}

	@Override
	public void addChildNode(INexusTree e) {
		throw new AssertionFailedError("Methods in MockNexusTree should not be called");
	}
}
