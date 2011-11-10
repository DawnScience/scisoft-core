'''
Flatten Package, normally for internal use by RPC only.
For documentation, refer to Java IFlattener and IRootFlattener
'''

import os
if os.name == 'java':
    import jython.jyflatten as _flatten #@UnusedImport
else:
    import python.pyflatten as _flatten #@Reimport

flatten=_flatten.flatten
unflatten=_flatten.unflatten
canflatten=_flatten.canflatten
canunflatten=_flatten.canunflatten
settemplocation=_flatten.settemplocation
addhelper=_flatten.addhelper