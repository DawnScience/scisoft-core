/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class for Ring region of interest
 */
public class RingROI extends SectorROI implements Serializable {

	/**
	 * 
	 */
	public RingROI() {
		super(30., 120., Math.PI*0.25, Math.PI*2./3.);
	}

	/**
	 * Create an annulus
	 * @param sr 
	 * @param er
	 */
	public RingROI(double sr, double er) {
		super(sr, er);
	}

	/**
	 * @param sr 
	 * @param er
	 * @param sp
	 * @param ep
	 */
	public RingROI(double sr, double er, double sp, double ep) {
		super(sr, er, sp, ep);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param sp
	 * @param ep
	 */
	public RingROI(double ptx, double pty, double sr, double er, double sp, double ep) {
		super(ptx, pty, sr, er, sp, ep);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param sp
	 * @param ep
	 * @param clip 
	 */
	public RingROI(double ptx, double pty, double sr, double er, double sp, double ep, double dpp, boolean clip) {
		super(ptx, pty, sr, er, sp, ep, dpp, clip);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param sp
	 * @param ep
	 * @param clip 
	 * @param sym 
	 */
	public RingROI(double ptx, double pty, double sr, double er, double sp, double ep, double dpp, boolean clip, int sym) {
		super(ptx, pty, sr, er, sp, ep, dpp, clip, sym);
	}

	/**
	 * @return a copy
	 */
	@Override
	public RingROI copy() {
		RingROI c = new RingROI(spt[0], spt[1], rad[0], rad[1], ang[0], ang[1], dpp, clippingCompensation, symmetry);
		c.setCombineSymmetry(combineSymmetry);
		c.name = name;
		c.plot = plot;
		return c;
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Centre %s Radii %s Angles (%g, %g)", Arrays.toString(spt), Arrays.toString(rad), getAngleDegrees(0), getAngleDegrees(1));
	}
}
