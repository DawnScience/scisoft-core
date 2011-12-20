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

'''
Comparisons package
'''

import numpy as _np #@UnresolvedImport

all = _np.all

any = _np.any

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

