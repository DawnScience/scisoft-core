/*-
 * Copyright 2023 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import java.util.Arrays;

import hdf.hdf5lib.structs.H5O_token_t;

public class HDF5Token {
	private byte[] data;
	private int hash;

	public HDF5Token(H5O_token_t token) {
		this(token.data);
	}

	public HDF5Token(byte[] data) {
		this.data = data;
		this.hash = calculateHash();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HDF5Token)) {
			return false;
		}
		HDF5Token other = (HDF5Token) obj;
		return Arrays.equals(data, other.data);
	}

	public byte[] getData() {
		return data;
	}

	private static final int HASH_MULTIPLIER = 149;

	/**
	 * Calculate hash but omit leading or trailing zeros
	 * @return hash
	 */
	private int calculateHash() {
		int result = 1;
		int length = data.length;
		if (length > 0 && data[length - 1] == 0) {
			int i = length - 2;
			for (; i >= 0; i--) {
				if (data[i] != 0) {
					break;
				}
			}
			for (; i >= 0; i--) {
				result = result * HASH_MULTIPLIER + (0xff & data[i]);
			}
		} else {
			int i = 0;
			for (; i < length; i++) {
				if (data[i] != 0) {
					break;
				}
			}
			for (; i < length; i++) {
				result = result * HASH_MULTIPLIER + (0xff & data[i]);
			}
		}

		return result;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}
