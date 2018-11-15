/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A multi-range is specified by a string comprising comma-separated (or semicolon-separated)
 * sub-ranges. Each sub-range can be a single integer or two integers (start/end) separated by a
 * colon. Integers can be negative to imply a count from the end of the range. The end integer
 * must be greater than the start integer. The end integer of sub-ranges is inclusive. Also the
 * last sub-range can omit the end integer. Finally, each sub-range can be excluded with an
 * exclamation mark prefix; if any exclamation mark appears in a multi-range string then the
 * default is that the entire range is included and specified sub-ranges with "!" are excluded.
 * <p>
 * Some example sub-ranges:
 * <dl>
 *  <dt>"1"</dt><dd>second item (items are labelled from 0)</dd>
 *  <dt>"-1"</dt><dd>last item</dd>
 *  <dt>"-2"</dt><dd>second last item</dd>
 *  <dt>"2:5" or "2 : 5"</dt><dd>third to sixth inclusive</dd>
 *  <dt>"-3:-1" or "-3 : -1"</dt><dd>third last to last inclusive</dd>
 *  <dt>"!3"</dt><dd>all bar fourth item</dd>
 *  <dt>"!5:-2"</dt><dd>sixth to second last excluded</dd>
 * </dl>
 */
public class MultiRange {
	private static final String RANGE_SEPARATOR = "[;,]";
	private static final String EXCLAIM = "!";
	private static final String INTEGER_REGEXP = "-?\\d|[1-9]\\d*";
	private static final String INTEGER_GROUP = "(" + INTEGER_REGEXP + ")";
	private static final String NOTTABLE_INTEGER_GROUP = "(!?" + INTEGER_REGEXP + ")";
	static final Pattern SUBRANGE_PATTERN = Pattern.compile(NOTTABLE_INTEGER_GROUP + "\\s*(:\\s*)" + INTEGER_GROUP + "?");
	static final Pattern SINGLE_PATTERN = Pattern.compile(NOTTABLE_INTEGER_GROUP);
	private List<Long> limits = new ArrayList<>();
	private long min;
	private long max;
	private boolean defStatus = false; // default to not being in range

	private MultiRange() {
	}

	/**
	 * @param multiRangeSpec
	 * @return multi-range
	 */
	public static MultiRange createMultiRange(String multiRangeSpec) {
		MultiRange mr = new MultiRange();
		mr.parseMultiRange(multiRangeSpec);
		return mr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((limits == null) ? 0 : limits.hashCode());
		result = prime * result + (int) (max ^ (max >>> 32));
		result = prime * result + (int) (min ^ (min >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		MultiRange other = (MultiRange) obj;
		if (max != other.max) {
			return false;
		}
		if (min != other.min) {
			return false;
		}
		return limits.equals(other.limits);
	}

	/**
	 * @return minimum integer specified
	 */
	public long getMinimum() {
		return min;
	}

	/**
	 * @return maximum integer specified
	 */
	public long getMaximum() {
		return max;
	}

	/**
	 * @param size
	 * @param i
	 * @return true if given value is in multi-range
	 */
	public boolean contains(int size, int i) {
		long li = i;
		long lsize = size;
		if (min + lsize <= 0) {
			throw new IllegalArgumentException("Size must be greater than -minimum");
		}
		if (li < 0 || li >= lsize) {
			return false;
		}

		int jmax = limits.size();
		if (jmax == 0) {
			return li < lsize;
		}
		for (int j = 0; j < jmax; j += 3) {
			boolean n = limits.get(j) == 1;
			long l = limits.get(j + 1);
			if (l < 0) {
				l += lsize;
			}
			if (li < l) {
				return n;
			}
			long u = limits.get(j + 2);
			if (u < 0) {
				u += lsize;
			}
			if (li <= u) {
				return !n;
			}
		}

		return max == 0 ? defStatus : defStatus ^ (li < max);
	}

	/**
	 * Parse given specifier for a multi-range
	 * @param multiRangeSpec
	 */
	public void parseMultiRange(String multiRangeSpec) {
		defStatus = multiRangeSpec.contains(EXCLAIM); // a single "!" will switch default behaviour

		String[] subRanges = multiRangeSpec.split(RANGE_SEPARATOR);

		limits.clear();
		max = 0;
		min = 0;
		long last = Long.MIN_VALUE;
		for (int i =0, iend = subRanges.length - 1; i <= iend; i++) {
			String s = subRanges[i].trim();
			if (s.isEmpty()) {
				// do nothing
			} else {
				Matcher m = SUBRANGE_PATTERN.matcher(s);
				if (!m.matches()) {
					m = SINGLE_PATTERN.matcher(s);
					if (!m.matches()) {
						throw new IllegalArgumentException("Subrange \"" + s + "\" is incorrect");
					}
				} else {
					if (m.groupCount() != 3) {
					throw new IllegalArgumentException("Subrange \"" + s + "\" is incorrect");
					}
				}
				String l = m.group(1);
				long negate = l.startsWith(EXCLAIM) ? 1 : 0;
				if (negate == 1) {
					l = l.substring(1);
				}
				long ll;
				try {
					ll = Integer.parseInt(l);
				} catch (Exception e) {
					throw new IllegalArgumentException("Could not parse start of subrange \"" + s + "\"");
				}
				if (ll >= 0) {
					if (ll < last) {
						throw new IllegalArgumentException("Subrange \"" + s + "\" overlaps previous subrange");
					}
					last = ll;
				}
				if (ll < min) {
					min = ll;
				}
				if (ll > max) {
					max = ll;
				}
				limits.add(negate);
				limits.add(ll);
				if (m.groupCount() == 1) {
					limits.add(ll);
					continue;
				}
				String c = m.group(2);
				String u = m.group(3);
				if (c == null && u != null) {
					throw new IllegalArgumentException("Subrange \"" + s + "\" is incorrect");
				}
				long lu;
				try { // use start as end if not specified
					lu = u != null ? Integer.parseInt(u) : (c == null ? ll : Long.MAX_VALUE);
				} catch (Exception e) {
					throw new IllegalArgumentException("Could not parse end of subrange \"" + s + "\"");
				}
				if (u == null && c != null && i != iend) {
					throw new IllegalArgumentException("Subrange \"" + s + "\" is missing end integer");
				}
				if (lu >= 0) {
					if (lu < last) {
						throw new IllegalArgumentException("Subrange \"" + s + "\" overlaps previous subrange");
					}
					last = lu;
				}
				if (lu < min) {
					min = lu;
				}
				if (lu > max) {
					max = lu;
				}
				if ((ll >= 0) ^ (lu < 0)) {
					if (ll > lu) {
						throw new IllegalArgumentException("Start integer must be less than end integer");
					}
				} else {
					// cannot check other combinations of signs
				}
				limits.add(lu);
			}
		}

		checkOverlaps();
	}

	/**
	 * Checks for overlaps when size is large enough
	 */
	private void checkOverlaps() {
		long size = max;
		if (min < 0) { // ensure negative sub-range fit in size
			size += 1 - min;
		}

		long last = Long.MIN_VALUE;
		for (int j = 0, jmax = limits.size(); j < jmax; j += 3) {
			long l = limits.get(j + 1);
			if (l < 0) {
				l += size;
			}
			if (last >= l) {
				throw new IllegalArgumentException("Overlap not allowed");
			}
			last = l;
			long u = limits.get(j + 2);
			if (u < 0) {
				u += size;
			}
			if (last > u) {
				throw new IllegalArgumentException("Overlap not allowed");
			}
			last = u;
		}
	}
}
