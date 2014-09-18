/*
 * Copyright 2012 Diamond Light Source Ltd.
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

import gda.data.nexus.tree.NexusTreeProvider;

import java.io.BufferedWriter;
import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;

/**
 * This saver adds Nexus metadata to files
 */
public class SRSNexusLoader extends SRSLoader {
	
	public SRSNexusLoader() {
	}
	
	/**
	 * @param FileName
	 */
	public SRSNexusLoader(String FileName) {
		fileName = FileName;
	}

	@Override
	protected void writeMetadata(BufferedWriter out, IDataHolder holder) throws IOException {
		if (holder instanceof NexusTreeProvider) {
			NexusTreeProvider ndh = (NexusTreeProvider) holder;
			if (ndh.getNexusTree() != null) {
				out.write(ndh.getNexusTree().toXML(true, true));
				out.write(ndh.getNexusTree().toText("", ":", "/", "|"));
			}
		}
		super.writeMetadata(out, holder);
	}
}
