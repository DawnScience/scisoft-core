
import os
if os.name == 'java':
    from jython.jymaths import * #@UnusedWildImport
else:
    from python.pymaths import * #@UnusedWildImport
