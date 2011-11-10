'''FFT package
'''

import os
if os.name == 'java':
    from jython.jyfft import * #@UnusedWildImport
else:
    from numpy.fft import *
