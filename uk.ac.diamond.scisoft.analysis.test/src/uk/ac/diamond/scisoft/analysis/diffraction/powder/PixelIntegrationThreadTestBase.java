/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

public abstract class PixelIntegrationThreadTestBase extends AbstractPixelIntegrationTestBase {

	public void testInTestThread() throws Exception{
		testWithNThreads(1);
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

	protected void testWithNThreads(final int threadNumber) throws Exception {
		
        exception = null;
		
        final boolean[] done    = new boolean[threadNumber];
        final boolean[] ok      = new boolean[threadNumber];
        final Thread[]  threads = new Thread[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
        	final int index = i;
        	threads[i] =  new Thread(new Runnable() {
        		@Override
        		public void run() {
        			done[index] = false;
        			ok[index]   = false;
        			try {
        				doTestOfDataSet(index);
            			ok[index]   = true;
					} catch (Exception e) {
						exception = e;
					} finally {
	        			done[index] = true;
					}
        		}
        	});
		}

        for (int i = 0; i < threadNumber; i++) {
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

        boolean allok = true;
        for (int i = 0; i < threadNumber; i++) {
        	if (!ok[i]) {
                System.out.println("Thread #" + i + "/" + threadNumber + " failed!");
                allok = false;
        	}
        }
        if (exception!=null) throw exception;

        if (allok)
        	System.out.println("All (" + threadNumber + ") threads passed!");
	}
	
}
