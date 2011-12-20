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
