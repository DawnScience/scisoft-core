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

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.utils.ImageThumbnailLoader;

/**
 * Bean to hold directory information with a mode to switch directory
 */
public class FileOperationBean implements Serializable {
	/*
	 * It is important to note that all paths are relative to a base gda.data property
	 */
	private String parent = null; // if null then at base
	private List<String> children = null;
	private String next = null; // used to hold a relative path to parent or to base for next directory
	private String cwp = null; // current path relative to base

	private List<String> files = null;
	private int mode;

	/**
	 * 
	 */
	public FileOperationBean() {

	}
	
	public FileOperationBean(int mode)
	{
		this.mode = mode;
	}

	/**
	 * @param files
	 */
	public void setFiles(List<String> files) {
		this.files = files;
	}

	/**
	 * @return array of filenames
	 */
	public List<String> getFiles() {
		return files;
	}

	/**
	 * @return if bean contains more than one file
	 */
	
	public boolean hasMultiplyFiles() {
		return (files != null && files.size() > 1);
	}
	
	/**
	 * @param parent
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return parent path
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param children
	 */
	public void setChildren(List<String> children) {
		this.children = children;
	}

	/**
	 * @return array of child names
	 */
	public List<String> getChildren() {
		return children;
	}

	/**
	 * @param c
	 *            child index
	 * @return child
	 */
	public String getChild(int c) {
		return children.get(c);
	}

	/**
	 * @param next
	 *            is next path
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * @return current working path
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @return current path
	 */
	public String getCwp() {
		return cwp;
	}

	/**
	 * @param cwp
	 */
	public void setCwp(String cwp) {
		this.cwp = cwp;
	}

	/**
	 * @param mode
	 *            for file operation
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return mode for file operation
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * no operation
	 */
	public static final int NOOP = 0;

	/**
	 * get a file and load it up to metadata tree
	 */
	public static final int GETFILE = 1;

	/**
	 * get an image file and push it to client
	 */
	
	public static final int GETIMAGEFILE = 2;
	
	/**
	 * get an image file turn it into thumb nail size and push it to client
	 */
	
	public static final int GETIMAGEFILE_THUMB = 3;
	/**
	 * get a directory and populate bean
	 */
	public static final int GETDIR = 4;

	/**
	 * go up to parent directory
	 */
	public static final int UPDIR = 5;

	/**
	 * enter a child directory
	 */
	public static final int DNDIR = 6;

	/**
	 * delete grid image temporary directory
	 */
	
	public static final int DELETEGRIDIMGTEMPDIR = 7;
	
	public DataBean loadImage(boolean downsample) {

		DataBean db = new DataBean(GuiPlotMode.TWOD);
		Iterator<String> iter = files.iterator();
		while (iter.hasNext()) {
			String filename = iter.next();
			AbstractDataset ds = ImageThumbnailLoader.loadImage(filename,downsample);
			DataSetWithAxisInformation dsAxisInf = new DataSetWithAxisInformation();
			AxisMapBean amb = new AxisMapBean(AxisMapBean.DIRECT);
			dsAxisInf.setData(ds);
			dsAxisInf.setAxisMap(amb);
			try {
				db.addData(dsAxisInf);
			} catch (DataBeanException e) {
				e.printStackTrace();
			}
		}
		return db;
	}
	
	/**
	 * Change directory
	 * 
	 * The destination is limited to be within a tree based on the given base path
	 *
	 * @param basePath 
	 * @return new bean for directory
	 */
	public FileOperationBean chDir(String basePath) {
		try {
			basePath = (new File(basePath)).getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		FileOperationBean fob = new FileOperationBean();
		File f = null;

		// set File to destination
		switch (mode) {
		case GETDIR:
			if (next == null)
				f = new File(basePath);
			else
				f = new File(basePath, next);
			break;
		case UPDIR:
			if (parent != null) {
				f = new File(basePath, parent);
			} else if (cwp != null) {
				f = new File(basePath);
			} else {
				return this; // do nothing when at root
			}
			break;
		case DNDIR:
			if (cwp == null) {
				f = new File(basePath, next);
			} else {
				f = new File(basePath, cwp);
				try {
					f = new File(f.getCanonicalPath(), next);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			return this;
		}

		// set current path to destination
		try {
			String path = f.getCanonicalPath();
			// try to remove the preset base path from the path
			if (path.startsWith(basePath)) {
				if (path.length() > basePath.length())
					fob.setCwp(path.substring(basePath.length() + 1)); // include path separator
			} else {
				fob.setCwp(path);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set parent
		if (fob.getCwp() != null) {
			try {
				String parentpath = f.getParentFile().getCanonicalPath();
				// try to remove the preset base path from the path
				if (parentpath.startsWith(basePath)) {
					if (parentpath.length() > basePath.length())
						fob.setParent(parentpath.substring(basePath.length() + 1)); // include path separator
				} else {
					fob.setParent(parentpath);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		System.out.println("Parent: " + fob.getParent() + "; current: " + fob.getCwp());

		ArrayList<String> dirs = new ArrayList<String>();
		ArrayList<String> files = new ArrayList<String>();
		for (File fa : f.listFiles()) {
			if (fa.isDirectory())
				dirs.add(fa.getName());
			else if (fa.isFile())
				files.add(fa.getName());
		}
		fob.setFiles(files);
		fob.setChildren(dirs);
		fob.setMode(NOOP);
		return fob;
	}
	
	public void deleteDir(String dirName) {
		File directory = new File(dirName);
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (int i = 0 ; i < files.length; i++) {
				files[i].delete();
			}
			directory.delete();
		}
	}
}
