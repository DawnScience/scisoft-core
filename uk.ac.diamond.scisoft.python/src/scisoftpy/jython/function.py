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
Fit functions
-------------

Available functions for fitting:

offset, constant -- constant offset, a: [a]
linear     -- linear function, a*x + b: [a,b]
quadratic  -- quadratic function, a*x^2 + b*x + c: [a,b,c]
cubic      -- cubic function, a*x^3 + b*x^2 + c*x + d: [a,b,c,d]
step       -- double step profile: [base, start, end, outer step, inner step, inner width fraction, inner offset fraction]  
gaussian   -- Gaussian profile (normal profile): [position, FWHM, area]
lorentzian -- Lorentzian profile (Cauchy or Breit-Wigner profile): [position, FWHM, area]
pvoigt     -- pseudo-Voigt profile: [position, Lorentzian FWHM, area, Gaussian FWHM, mixing (fraction of Lorentzian)]
pearson7   -- Pearson VII profile: [position, FWHM, area, power]

'''

from uk.ac.diamond.scisoft.analysis.fitting.functions import Cubic as _cubic
from uk.ac.diamond.scisoft.analysis.fitting.functions import Gaussian as _gaussian
from uk.ac.diamond.scisoft.analysis.fitting.functions import Lorentzian as _lorentzian
from uk.ac.diamond.scisoft.analysis.fitting.functions import Offset as _offset
from uk.ac.diamond.scisoft.analysis.fitting.functions import PearsonVII as _pearsonvii
from uk.ac.diamond.scisoft.analysis.fitting.functions import PseudoVoigt as _pseudovoigt
from uk.ac.diamond.scisoft.analysis.fitting.functions import Quadratic as _quadratic
from uk.ac.diamond.scisoft.analysis.fitting.functions import Step as _step
from uk.ac.diamond.scisoft.analysis.fitting.functions import StraightLine as _straightline


from scisoftpy.jython.jycore import _wrap, toList
import java.lang.Class as _jclass #@UnresolvedImport


def _jfnclass(jclass):
    class _JavaFunction():
        _jclass = True
        def __init__(self, *args):
            self._jfn = jclass(*args)
            self._jclass = False

        def _jfunc(self): # unwrap
            return self._jfn

        def val(self, *coords):
            return self._jfn.val(*coords)

        @_wrap
        def makeDataset(self, *coords):
            return self._jfn.makeDataset(toList(*coords))
        @_wrap
        def residual(self, allvalues, data, *coords):
            return self._jfn.residual(allvalues, data, *coords)

    return _JavaFunction

cubic = _jfnclass(_cubic)
gaussian = _jfnclass(_gaussian)
lorentzian = _jfnclass(_lorentzian)
offset = _jfnclass(_offset)
constant = _jfnclass(_offset)
pearson7 = _jfnclass(_pearsonvii)
pvoigt = _jfnclass(_pseudovoigt)
quadratic = _jfnclass(_quadratic)
step = _jfnclass(_step)
linear = _jfnclass(_straightline)

def isjclass(cls):
    jattr = getattr(cls, '_jclass', False)
    return jattr or isinstance(cls, _jclass)

def isjmethod(fn):
    return hasattr(fn, '_jfn')

_nparamsdict = { cubic:4, gaussian:3, lorentzian:3, offset:1, constant:1, pearson7:4, pvoigt:5, quadratic:3, step:7, linear:2}

def nparams(afunction):
    '''Get number of required parameters'''
    return _nparamsdict[afunction]

