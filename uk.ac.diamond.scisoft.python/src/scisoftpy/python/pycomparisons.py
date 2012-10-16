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

'''
Comparisons package
'''

import numpy as _np #@UnresolvedImport

all = _np.all #@ReservedAssignment

any = _np.any #@ReservedAssignment

greater = _np.greater

greater_equal = _np.greater_equal

less = _np.less

less_equal = _np.less_equal

equal = _np.equal

not_equal = _np.not_equal

logical_not = _np.logical_not

logical_and = _np.logical_and

logical_or = _np.logical_or

logical_xor = _np.logical_xor

allclose = _np.allclose

nonzero = _np.nonzero

select = _np.select

where = _np.where

