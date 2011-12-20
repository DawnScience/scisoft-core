###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

import uk.ac.diamond.scisoft.analysis.rpc.flattening.TypedNone as _typednone

typednone=_typednone

import uk.ac.diamond.scisoft.analysis.rpc.flattening.AbstractDatasetDescriptor as _abstractdatasetdescriptor
abstractdatasetdescriptor=_abstractdatasetdescriptor

# In java a wrapper isn't necessary, simply use a byte[]
# Set to None so that if someone tries to instatiate it give an error
binaryWrapper=None
