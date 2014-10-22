
import sys

try:
    from uk.ac.diamond.scisoft.analysis.plotclient.dataset import DatasetMailman as dsm
    manager = dsm.getRemoteManager()
except:
    ## This code has special handling because the RCP classes may not be available 
    print >> sys.stderr, "Could not import Dataset Manager"
    _manager = None
    
from jycore import _wrapin

@_wrapin
def send(*arg, **kwarg):
    _manager.send(*arg)


@_wrapin
def get(*arg, **kwarg):
    return _manager.get(*arg)