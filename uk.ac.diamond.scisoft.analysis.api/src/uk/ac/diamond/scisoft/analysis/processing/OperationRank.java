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

package uk.ac.diamond.scisoft.analysis.processing;

public enum OperationRank {

	ZERO(0),
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	ANY(-1),
	NONE(-2), 
	SAME(-3); // Denotes that this is the same as the input
	
	private final int rank;
	
	OperationRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}
	
	public static OperationRank get(int rank) {
		for (OperationRank or : values()) {
			if (or.rank == rank) return or;
		}
		return null;
	}
	
	public OperationRank oneLess() {
		return get(rank-1);
	}

	public boolean isDiscrete() {
		return this!=ANY && this!=NONE;
	}

	public boolean isCompatibleWith(OperationRank with) {
		
		if (this==NONE || with==NONE) return false;
		if (this.isDiscrete() && with.isDiscrete()) return this.rank==with.rank;
		if (ANY==with || this==ANY) return true;
		
		return false;
	}
}
