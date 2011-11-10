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
pvoigt     -- pseudo-Voigt profile: [position, Gaussian FWHM, Lorentzian FWHM, area, mixing]
pearson7   -- Pearson VII profile: [position, FWHM, mixing, area]

'''

from gda.analysis.functions import Cubic as _cubic
from gda.analysis.functions import Gaussian as _gaussian
from gda.analysis.functions import Lorentzian as _lorentzian
from gda.analysis.functions import Offset as _offset
from gda.analysis.functions import PearsonVII as _pearsonvii
from gda.analysis.functions import PseudoVoigt as _pseudovoigt
from gda.analysis.functions import Quadratic as _quadratic
from gda.analysis.functions import Step as _step
from gda.analysis.functions import StraightLine as _straightline


cubic = _cubic
gaussian = _gaussian
lorentzian = _lorentzian
offset = _offset
constant = _offset
pearson7 = _pearsonvii
pvoigt = _pseudovoigt
quadratic = _quadratic
step = _step
linear = _straightline

_nparamsdict = { cubic:4, gaussian:3, lorentzian:3, offset:1, constant:1, pearson7:4, pvoigt:5, quadratic:3, step:7, linear:2}

def nparams(afunction):
    '''Get number of required parameters'''
    return _nparamsdict[afunction]

