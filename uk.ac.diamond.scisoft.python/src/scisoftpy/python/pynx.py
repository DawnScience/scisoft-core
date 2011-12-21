###
# Copyright 2011 Diamond Light Source Ltd.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
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

