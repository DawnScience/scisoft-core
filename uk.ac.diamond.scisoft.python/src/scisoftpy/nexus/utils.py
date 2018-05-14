###
# Copyright 2018 Diamond Light Source Ltd.
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

'''
Collection of utility functions to work with NeXus trees
'''

from .nxclasses import NXobject as _nxobject, NXroot as _nxroot, NXentry as _nxentry, NX_CLASSES as _nxclasses

def _visitall(tree, visitor):
    for n,e in tree.items():
        if isinstance(e, _nxobject):
            _visitall(e, visitor)
        visitor(n,e)

import sys
_text_type = str if sys.hexversion >= 0x03000000 else basestring

def find_class(nx_tree, nx_class):
    '''
    Find all nodes of a given NXclass
    :param nx_tree: tree
    :param nx_class: NeXus class or list of classes

    :return: list of name-group pairs
    '''
    tt = type(nx_tree)
    if not issubclass(tt, _nxobject) and not issubclass(tt, _nxroot):
        raise ValueError("Tree must be an NeXus object")
    if not isinstance(nx_class, list):
        nx_class = [nx_class]
    for i,n in enumerate(nx_class):
        if isinstance(n, _text_type) and n in _nxclasses:
            n = _nxclasses[n]
            nx_class[i] = n
        if not issubclass(n, _nxobject):
            raise ValueError("Given class must be a subclass of NXobject")
    
    hits = []
    def visitor(name, obj):
        if type(obj) in nx_class:
            hits.append((name, obj))
        elif hasattr(obj, "attrs") and "NX_class" in obj.attrs:
            nxc = obj.attrs["NX_class"]
            if not isinstance(nxc, _text_type):
                nxc = nxc.item()
            if nxc in nx_class:
                hits.append((name, obj))

    _visitall(nx_tree, visitor)
    
    return hits
