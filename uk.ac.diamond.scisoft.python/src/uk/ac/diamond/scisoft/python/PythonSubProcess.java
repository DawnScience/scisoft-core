/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This creates and manages input and output to a Python subprocess
 */
public class PythonSubProcess {
	// arguments to create a python server which reads a line from standard input and exec() that line
	private static final String[] args = {
		"-c",
		"import sys\n" +
		"while True:\n" +
		"  print 'READY'\n" +
		"  sys.stdout.flush()\n" +
		"  l = sys.stdin.readline()\n" +
		"  if not l:\n" +
		"    break\n" +
		"  exec l\n",
	};

	private Process p;
	private StreamHandler obr;
	private StreamHandler ebr;
	private OutputStreamWriter stdin;

	private static final String READY = "READY";
	private static final int TIMEOUT = 5;

	/**
	 */
	public PythonSubProcess() {
		this(null, null);
	}

	/**
	 * @param exec path to Python executable
	 * @param env
	 */
	public PythonSubProcess(String exec, Map<String, String> env) {
		if (exec == null) {
			exec = "python";
		}
		List<String> cmds = new ArrayList<String>();
		cmds.add(exec);
		for (String a : args) {
			cmds.add(a);
		}
		ProcessBuilder pb = new ProcessBuilder(cmds);
		if (env != null && env.size() > 0) {
			pb.environment().putAll(env);
		}
		try {
			p = pb.start();
			stdin = new OutputStreamWriter(p.getOutputStream());
			// wrap output streams to ensure process does not deadlock
			obr = new StreamHandler(p.getInputStream());
			ebr = new StreamHandler(p.getErrorStream());

			obr.start();
			ebr.start();
			// make sure it's ready
			String l = obr.readLine();
			if (!READY.equals(l)) {
				throw new IllegalStateException("Problem with python subprocess not being ready: " + l + "; " + ebr.readLine(TIMEOUT));
			}
		} catch (Throwable t) {
			throw new IllegalStateException(t);
		}
	}

	/**
	 * Wrapper thread to asynchronously get lines from given stream
	 */
	class StreamHandler extends Thread {
		private BufferedReader stream;
		private boolean alive;
		BlockingQueue<String> out;

		public StreamHandler(InputStream in) {
			stream = new BufferedReader(new InputStreamReader(in));
			alive = true;
			out = new LinkedBlockingQueue<String>();
		}

		@Override
		public void run() {
			while (alive) {
				try {
					String l = stream.readLine();
					if (l == null) { // die if stream has ended
						alive = false;
					} else {
						out.add(l);
					}
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void interrupt() {
			alive = false;
			super.interrupt();
		}

		/**
		 * Clear internally stored content
		 */
		public void clear() {
			out.clear();
		}

		/**
		 * Read line
		 * @param milliSeconds
		 * @return line
		 */
		public String readLine(int milliSeconds)  {
			if (alive) {
				try {
					return out.poll(milliSeconds, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
				}
			}
			return null;
		}

		/**
		 * Blocking read line
		 * @return line
		 */
		public String readLine()  {
			if (alive) {
				try {
					return out.take();
				} catch (InterruptedException e) {
				}
			}
			return null;
		}
	}

	/**
	 * Send command and retrieve results
	 * @param text
	 * @return output lines and error - each string is empty or concatenated lines or null when there is no error
	 */
	public String[] communicate(String text) {
		send(text);
		List<String> results = new ArrayList<String>();

		String l;
		while (true) {
			l = obr.readLine();
			if (READY.equals(l)) {
				break;
			}
			results.add(l);
		}
		StringBuilder out = new StringBuilder();
		for (String r : results) {
			out.append(r);
			out.append('\n');
		}
		String[] lines = new String[] {out.toString(), null};

		results.clear();
		while (true) {
			l = ebr.readLine(TIMEOUT);
			if (l == null)
				break;
			results.add(l);
		}
		if (results.size() > 0) {
			out.setLength(0);
			for (String r : results) {
				out.append(r);
				out.append('\n');
			}
			lines[1] = out.toString();
		}
		return lines;
	}

	private void send(String text) {
		try {
			obr.clear();
			ebr.clear();
			stdin.write(text);
			stdin.flush();
		} catch (IOException e) {
		}
	}

	/**
	 * Stop subprocess
	 */
	public void stop() {
		try {
			stdin.close();
		} catch (IOException e) {
		}
	}
}
