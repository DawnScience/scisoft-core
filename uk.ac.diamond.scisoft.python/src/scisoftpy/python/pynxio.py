
try:
    from .pyh5py import NXLoader, SDS #@UnusedImport
except Exception, e:
    import sys
    print >> sys.stderr, "h5py could not be imported", e
    print >> sys.stderr, "NAPI not supported yet"
#    from .pynapi import NXLoader, SDS #@Reimport @UnusedImport

