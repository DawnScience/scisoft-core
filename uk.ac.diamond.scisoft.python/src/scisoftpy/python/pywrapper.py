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

class typednone(object):
    '''
    A wrapper class for use when calling java overloaded methods and you
    need to distinguish which one of the methods to call when the argument
    is None. Has the effect of in Java source of (Type)null.
    '''
   
    def __init__(self, typedNoneType="java.lang.Object"):
        '''Create an instance of a typed none. The argument should be
        the name of a Java type suitable to pass to Class.forName()'''
        self.typedNoneType = typedNoneType
        
    __hash__ = None
    
    def __eq__(self, other):
        return (isinstance(other, self.__class__)
            and self.typedNoneType == other.typedNoneType)

    def __ne__(self, other):
        return not self.__eq__(other)

    def __repr__(self):
        return '%s(%s)' % (self.__class__.__name__, self.__dict__.__repr__())


class datasetdescriptor(object):
    '''
    Use this class to describe a Dataset or ndarray that already resides on disk and can be loaded 
    using the file loaders. This object is flattened to a flattened representation that is unflattened 
    by DatasetHelper.
    The unflattened form of this type is a Dataset
    '''
    def __init__(self, filename=None, deleteAfterLoad=False, index=None, name=None):
        '''
        Create a new descriptor
        Parameters:
          filename- the file to load
          deleteAfterLoad- true to remove the file once loaded
          index- Index of the data set to load if no name is specified
          name- Name of the data set to load
          
        If neither name or index is specified, load index 0
        '''
        self.filename = filename
        self.deleteAfterLoad = deleteAfterLoad
        self.index = index
        self.name = name
    
    
#Use this class to wrap a Binary object, typically a str of bytes
import xmlrpclib
binarywrapper = xmlrpclib.Binary
