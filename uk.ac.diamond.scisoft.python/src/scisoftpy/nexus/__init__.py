
import os
if os.name == 'java':
    # jython 2.5.1 import bug (fixed in 2.5.2)
    from scisoftpy.jython.jyhdf5io import SDS
else:
    from ..python.pynxio import SDS #@Reimport

from nxclasses import *
