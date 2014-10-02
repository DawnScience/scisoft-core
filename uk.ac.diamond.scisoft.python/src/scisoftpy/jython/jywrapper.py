###
# Copyright 2011 Diamond Light Source Ltd.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
###

import uk.ac.diamond.scisoft.analysis.rpc.flattening.TypedNone as _typednone

typednone=_typednone

import uk.ac.diamond.scisoft.analysis.rpc.flattening.DatasetDescriptor as _datasetdescriptor
datasetdescriptor=_datasetdescriptor

# In java a wrapper isn't necessary, simply use a byte[]
# Set to None so that if someone tries to instantiate it give an error
binarywrapper=None
