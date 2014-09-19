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
Crystallography package
'''

import uk.ac.diamond.scisoft.analysis.crystallography as _xstal
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToMillerSpace as _maptomspace #@UnresolvedImport

class unitcell(_xstal.UnitCell):
    '''Unit cell'''
    def __init__(self, parameters, angles=None):
        '''Create a unit cell

        Two ways to specify a unit cell. Either as a combined list of lengths
        and angles or as two separate lists
        Arguments:
        parameters -- list of lengths (in Angstroms) and angles (in degrees)
        angles -- if None, default to 90 degrees
        '''
        n = len(parameters)
        if n >= 6:
            lengths = parameters[:3]
            angles = parameters[3:6]
        elif n >= 3:
            lengths = parameters[:3]
            if not angles or len(angles) != 3:
                angles = [90.] * 3
        else:
            raise ValueError, "Not enough parameters"

        super(_xstal.UnitCell, self).__init__(lengths, angles)

class reciprocalcell(_xstal.ReciprocalCell):
    '''Reciprocal lattice cell'''
    pass

class millerspace(_xstal.MillerSpace):
    '''Miller space'''
    pass

class millermapping(_maptomspace):
    '''Mapping to Miller space'''
    pass
