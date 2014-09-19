/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.CollectionStats;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.DoubleUtils;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;


/**
 * Basic tests of Stats class
 */
public class CollectionStatsTest {


   private static List<IDataset> SETS1D, SETS2D;
   static {
	   SETS1D = new ArrayList<IDataset>(5);
	   SETS1D.add(new DoubleDataset(new double[]{1,  2,1,  4,5  }, 5));
	   SETS1D.add(new DoubleDataset(new double[]{500,2,1,  4,5  }, 5));
	   SETS1D.add(new DoubleDataset(new double[]{1,  2,1,  4,5  }, 5));
	   SETS1D.add(new DoubleDataset(new double[]{1,  2,3,  4,500}, 5));
	   SETS1D.add(new DoubleDataset(new double[]{1,  2,300,4,5  }, 5));
	   
	   SETS2D = new ArrayList<IDataset>(5);
	   SETS2D.add(new DoubleDataset(new double[]{1,  2,1,4  }, 2,2));
	   SETS2D.add(new DoubleDataset(new double[]{500,2,1,4  }, 2,2));
	   SETS2D.add(new DoubleDataset(new double[]{1,  2,1,4  }, 2,2));
	   SETS2D.add(new DoubleDataset(new double[]{1,  2,3,500}, 2,2));
	   SETS2D.add(new DoubleDataset(new double[]{1,  2,300,4}, 2,2));
   }
   
   @Test
   public void test2D() throws Exception {
	   
	   final Dataset median = CollectionStats.median(SETS2D);
	   if (median.getDouble(0,0)!=1) throw new Exception("Median not calculated correctly!");
	   if (median.getDouble(1,1)!=4) throw new Exception("Median not calculated correctly!");
	   if (median.getShape()[0]!=2) throw new Exception("Median shape not correct!");
	   if (median.getShape()[1]!=2) throw new Exception("Median shape not correct!");
	   
	   final Dataset mean = CollectionStats.mean(SETS2D);
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(0,0), 100.8d, 0.01)) throw new Exception("Mean not calculated correctly!");
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(1,1), 103.2d, 0.01)) throw new Exception("Mean not calculated correctly!");  
   }

   @Test
   public void test1D() throws Exception {
	   
	   final Dataset median = CollectionStats.median(SETS1D);
	   if (median.getDouble(0)!=1) throw new Exception("Median not calculated correctly!");
	   if (median.getDouble(4)!=5) throw new Exception("Median not calculated correctly!");
	   
	   final Dataset mean = CollectionStats.mean(SETS1D);
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(0), 100.8, 0.0001)) throw new Exception("Mean not calculated correctly!");
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(4), 104, 0.0001)) throw new Exception("Mean not calculated correctly!");  
    }

   @Test
   public void testLarge() throws Exception {
	   
	   final long start = System.currentTimeMillis();
	   final List<IDataset> images = new ArrayList<IDataset>(10);
	   
	   final File dir = new File(System.getProperty("GDALargeTestFilesLocation")+"/EDFLoaderTest/");
	   final File[] files = dir.listFiles();
	   for (int i = 0; i < files.length; i++) {
		   if (files[i].getName().startsWith("billeA")) {
			   images.add(LoaderFactory.getData(files[i].getAbsolutePath(), null).getDataset(0));
		   }
	   }
	   
	   final Dataset median = CollectionStats.median(images);
	   final long end = System.currentTimeMillis();
	   if (median.getShape()[0] != 2048) throw new Exception("Median has wrong size!");
	   if (median.getShape()[1] != 2048) throw new Exception("Median has wrong size!");
	   
	   System.out.println("Did median of ten images 2048x2048 in "+((end-start)/1000d)+"s");
	   
	   final Dataset mean = CollectionStats.median(images);
	   final long end1 = System.currentTimeMillis();
	   if (mean.getShape()[0] != 2048) throw new Exception("Mean has wrong size!");
	   if (mean.getShape()[1] != 2048) throw new Exception("Mean has wrong size!");
	   
	   System.out.println("Did mean of ten images 2048x2048 in "+((end1-end)/1000d)+"s");
   }

}
