
from ..nexus.hdf5 import HDF5dataset as _dataset

from .pycore import ndarray, asarray

class SDS(_dataset):
    def __init__(self, dataset, attrs={}, parent=None):
        '''Make a SDS
        
        dataset can be a ndarray or HDF5Dataset when created from a file
        '''
        _dataset.__init__(self, attrs, parent)
#        if not isinstance(dataset, ndarray):
#            dataset = asarray(dataset)
#        self.__data = dataset
#        if not isinstance(dataset, ndarray):
#            if dataset.isString() or not dataset.isSupported():
#                self.__shape = self.__maxshape = None
#            else:
#                self.__shape = tuple(dataset.getDataset().getShape())
#                self.__maxshape = tuple(dataset.getMaxShape())
#        else:
#            self.__shape = self.__maxshape = tuple.getShape()

