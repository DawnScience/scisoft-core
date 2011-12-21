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
