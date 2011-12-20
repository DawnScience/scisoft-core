###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

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

