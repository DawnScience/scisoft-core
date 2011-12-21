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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

@SuppressWarnings("unused")
public interface InterfaceAndImpl {
	public String interfacecall(int a);
	public String implcalloverloaded(int a);
	
	public class Impl implements InterfaceAndImpl {

		@Override
		public String interfacecall(int a) {
			return "interfacecall";
		}
		
		public String implcall(int a) {
			return "implcall";
		}	
		
		@Override
		public String implcalloverloaded(int a) {
			return "implcalloverloaded - interface";
		}	
		
		public String implcalloverloaded(String a) {
			return "implcalloverloaded - impl";
		}	
	}
}
