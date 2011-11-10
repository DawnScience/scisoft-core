
try:
    from .pyh5py import HDF5Loader #@UnusedImport
except Exception, e:
    import sys
    print >> sys.stderr, "h5py could not be imported", e
    print >> sys.stderr, "NAPI not supported yet"
#    from .pynapi import HDF5Loader #@Reimport @UnusedImport
