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

/*
 * Copied from PyDev and modified to remove PyDev dependencies
 */
package uk.ac.diamond.scisoft.analysis;

import java.io.InputStream;
import java.io.InputStreamReader;

/*package*/ final class ThreadStreamReader extends Thread {
    
    /**
     * Input stream read.
     */
    private final InputStream is;
    
    /**
     * Buffer with the contents gotten.
     */
    private final StringBuilder contents;
    
    /**
     * Access to the buffer should be synchronized.
     */
    private final Object lock = new Object();

    /**
     * Whether the read should be synchronized.
     */
    private final boolean synchronize;
    
    /**
     * Keeps the next unique identifier.
     */
    private static int next=0;
    
    /**
     * Get a unique identifier for this thread. 
     */
    private static synchronized int next(){
        next ++;
        return next;
    }

    public ThreadStreamReader(InputStream is) {
        this(is, true); //default is synchronize.
    }
    
    public ThreadStreamReader(InputStream is, boolean synchronize) {
        this.setName("ThreadStreamReader: "+next());
        this.setDaemon(true);
        contents = new StringBuilder();
        this.is = is;
        this.synchronize = synchronize;
    }

    @Override
	public void run() {
        try {
            InputStreamReader in = new InputStreamReader(is);
            int c;
            if(synchronize){
                while ((c = in.read()) != -1) {
                    synchronized(lock){
                        contents.append((char) c);
                    }
                }
            }else{
                while ((c = in.read()) != -1) {
                    contents.append((char) c);
                }
            }
        } catch (Exception e) {
            //that's ok
        }
    }

    public String getContents() {
        synchronized(lock){
            return contents.toString();
        }
    }
}
