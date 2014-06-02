/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.crystallography;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;


public class VersionUtils {

	public static boolean isOldVersion(String versionCompare, String versionWith) {
		Version compare = new Version(versionCompare);
		Version with    = new Version(versionWith);
		int c    =  compare.compareTo(with);
		return c > 0;
	}
	
	private static class Version implements Comparable<Version>{
		
		private final int			major;
		private final int			minor;
		private final int			micro;
		private final String		qualifier;
		private static final String	SEPARATOR		= ".";

		public Version(String version)  {

			int maj = 0;
			int min = 0;
			int mic = 0;
			String qual = "";

			try {
				StringTokenizer st = new StringTokenizer(version, SEPARATOR, true);
				maj = parseInt(st.nextToken(), version);

				if (st.hasMoreTokens()) { // minor
					st.nextToken(); // consume delimiter
					min = parseInt(st.nextToken(), version);

					if (st.hasMoreTokens()) { // micro
						st.nextToken(); // consume delimiter
						mic = parseInt(st.nextToken(), version);

						if (st.hasMoreTokens()) { // qualifier separator
							st.nextToken(); // consume delimiter
							qual = st.nextToken(""); // remaining string

							if (st.hasMoreTokens()) { // fail safe
								throw new IllegalArgumentException("invalid version \"" + version + "\": invalid format");
							}
						}
					}
				}
			} catch (NoSuchElementException e) {
				IllegalArgumentException iae = new IllegalArgumentException("invalid version \"" + version + "\": invalid format");
				iae.initCause(e);
				throw iae;
			}

			major = maj;
			minor = min;
			micro = mic;
			qualifier = qual;
			validate();
		}
		
		private void validate() {
			if (major < 0) {
				throw new IllegalArgumentException("invalid version: negative number \"" + major + "\"");
			}
			if (minor < 0) {
				throw new IllegalArgumentException("invalid version: negative number \"" + minor + "\"");
			}
			if (micro < 0) {
				throw new IllegalArgumentException("invalid version: negative number \"" + micro + "\"");
			}
			for (char ch : qualifier.toCharArray()) {
				if (('A' <= ch) && (ch <= 'Z')) {
					continue;
				}
				if (('a' <= ch) && (ch <= 'z')) {
					continue;
				}
				if (('0' <= ch) && (ch <= '9')) {
					continue;
				}
				if ((ch == '_') || (ch == '-')) {
					continue;
				}
				throw new IllegalArgumentException("invalid version: invalid qualifier \"" + qualifier + "\"");
			}
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + major;
			result = prime * result + micro;
			result = prime * result + minor;
			result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Version other = (Version) obj;
			if (major != other.major)
				return false;
			if (micro != other.micro)
				return false;
			if (minor != other.minor)
				return false;
			if (qualifier == null) {
				if (other.qualifier != null)
					return false;
			} else if (!qualifier.equals(other.qualifier))
				return false;
			return true;
		}
		
		public int compareTo(Version other) {
			if (other == this) { // quicktest
				return 0;
			}

			int result = major - other.major;
			if (result != 0) {
				return result;
			}

			result = minor - other.minor;
			if (result != 0) {
				return result;
			}

			result = micro - other.micro;
			if (result != 0) {
				return result;
			}

			return qualifier.compareTo(other.qualifier);
		}
		
	}
	
	private static int parseInt(String value, String version) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			IllegalArgumentException iae = new IllegalArgumentException("invalid version \"" + version + "\": non-numeric \"" + value + "\"");
			iae.initCause(e);
			throw iae;
		}
	}
	
	

}
