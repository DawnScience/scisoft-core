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

package uk.ac.diamond.scisoft.analysis.io;

/**
 * Class to extend, does not have any tests itself.
 */
public abstract class LoaderThreadTestBase {
	

	public void testInTestThread() throws Exception{
		doTestOfDataSet(0);
		
		System.out.println("One thread passed.");
	}
	
	protected abstract void doTestOfDataSet(int index) throws Exception;

	private Exception exception;
	
	/**
	 * Test shows that nexus API cannot handle threads. 
	 * 
	 * @throws Exception
	 */
	public void testWithTenThreads() throws Exception {
		
		testWithNThreads(10);
	}

	protected void testWithNThreads(int threadNumber) throws Exception {
		
        exception = null;
		
        final boolean[] done    = new boolean[threadNumber];
        final Thread[]  threads = new Thread[threadNumber];
        for (int i = 0; i < threads.length; i++) {
        	final int index = i;
        	threads[i] =  new Thread(new Runnable() {
        		@Override
        		public void run() {
        			done[index] = false;
        			try {
        				doTestOfDataSet(index);
					} catch (Exception e) {
						exception = e;
					} finally {
	        			done[index] = true;
					}
        		}
        	});
		}
        
        for (int i = 0; i < threads.length; i++) {
        	threads[i].start();
        }
        
        // Wait for them to do their thing.
        WHILE_LOOP: while(true) {
        	for (boolean d : done) {
				if (!d) {
					Thread.sleep(1000);
					continue WHILE_LOOP;
				}
        	}
        	break;
        }
        
        if (exception!=null) throw exception;

        
		System.out.println(threadNumber+" threads passed."); 	
	}


}
