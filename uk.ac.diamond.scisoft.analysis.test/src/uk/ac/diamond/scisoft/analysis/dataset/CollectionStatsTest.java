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

package uk.ac.diamond.scisoft.analysis.dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	   
	   final AbstractDataset median = CollectionStats.median(SETS2D);
	   if (median.getDouble(0,0)!=1) throw new Exception("Median not calculated correctly!");
	   if (median.getDouble(1,1)!=4) throw new Exception("Median not calculated correctly!");
	   if (median.getShape()[0]!=2) throw new Exception("Median shape not correct!");
	   if (median.getShape()[1]!=2) throw new Exception("Median shape not correct!");
	   
	   final AbstractDataset mean = CollectionStats.mean(SETS2D);
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(0,0), 100.8d, 0.01)) throw new Exception("Mean not calculated correctly!");
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(1,1), 103.2d, 0.01)) throw new Exception("Mean not calculated correctly!");  
   }

   @Test
   public void test1D() throws Exception {
	   
	   final AbstractDataset median = CollectionStats.median(SETS1D);
	   if (median.getDouble(0)!=1) throw new Exception("Median not calculated correctly!");
	   if (median.getDouble(4)!=5) throw new Exception("Median not calculated correctly!");
	   
	   final AbstractDataset mean = CollectionStats.mean(SETS1D);
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(0), 100.8, 0.0001)) throw new Exception("Mean not calculated correctly!");
	   if (!DoubleUtils.equalsWithinTolerance(mean.getDouble(4), 104, 0.0001)) throw new Exception("Mean not calculated correctly!");  
    }

   @Test
   public void testLarge() throws Exception {
	   
	   final long start = System.currentTimeMillis();
	   final List<IDataset> images = new ArrayList<IDataset>(10);
	   
	   final File dir = new File(System.getProperty("GDALargeTestFilesLocation")+"EDFLoaderTest/");
	   final File[] files = dir.listFiles();
	   for (int i = 0; i < files.length; i++) {
		   if (files[i].getName().startsWith("billeA")) {
			   images.add(LoaderFactory.getData(files[i].getAbsolutePath(), null).getDataset(0));
		   }
	   }
	   
	   final AbstractDataset median = CollectionStats.median(images);
	   final long end = System.currentTimeMillis();
	   if (median.getShape()[0] != 2048) throw new Exception("Median has wrong size!");
	   if (median.getShape()[1] != 2048) throw new Exception("Median has wrong size!");
	   
	   System.out.println("Did median of ten images 2048x2048 in "+((end-start)/1000d)+"s");
	   
	   final AbstractDataset mean = CollectionStats.median(images);
	   final long end1 = System.currentTimeMillis();
	   if (mean.getShape()[0] != 2048) throw new Exception("Mean has wrong size!");
	   if (mean.getShape()[1] != 2048) throw new Exception("Mean has wrong size!");
	   
	   System.out.println("Did mean of ten images 2048x2048 in "+((end1-end)/1000d)+"s");
   }

}
