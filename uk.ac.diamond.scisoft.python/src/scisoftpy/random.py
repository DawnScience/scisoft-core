
import os
if os.name == 'java':
    from jython.jyrandom import * #@UnusedWildImport
else:
    from numpy.random import *
