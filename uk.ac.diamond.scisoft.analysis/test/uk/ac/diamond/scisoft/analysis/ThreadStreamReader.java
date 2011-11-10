/**
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license.txt included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
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