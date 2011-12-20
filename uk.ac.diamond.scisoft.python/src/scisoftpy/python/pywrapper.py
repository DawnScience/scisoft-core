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


class abstractdatasetdescriptor(object):
    '''
    Use this class to describe an AbstractDataset or ndarray that already resides on disk and can be loaded 
    using the file loaders. This object is flattened to a flattened representation that is unflattened 
    by AbstractDatasetHelper.
    The unflattened form of this type is an AbstractDataset
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
binaryWrapper = xmlrpclib.Binary
