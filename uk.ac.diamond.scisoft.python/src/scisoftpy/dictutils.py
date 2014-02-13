# -*- coding: utf-8 -*-
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

def _maketranslator():
    '''
    Make a translator to sanitise names for python variable by replacing characters that are not
    ASCII letters, digits or '_' (underscore) with an underscore
    '''
    from string import ascii_letters, digits, printable, maketrans
    safechars = '_' + ascii_letters + digits
    trans = list(printable)
    for i,t in enumerate(trans):
        if not t in safechars:
            trans[i] = '_'
    tostrs = ''.join(trans)
    return maketrans(printable, tostrs)

_translator = _maketranslator()

_method_names = ['metadata', 'clear', 'popitem', 'has_key', 'keys', 'fromkeys', 'get', 'copy', 'setdefault', 'update', 'pop',
                 'values', 'items', 'iterkeys', 'itervalues', 'iteritems', 'append', 'extend', 'index', 'remove']

def sanitise_name(text, warn=True):
    '''
    Sanitise name:
        if text is metadata then prepend with an underscore
        translate dodgy characters to underscores
        if text starts with a digit then prepend with an underscore
        if text is a reserved method name then prepend with an underscore
        if text starts with double underscores then raise error
    '''
    from string import digits
    if text is None:
        if warn:
            print "Warning in sanitising dict keys: text is None so replacing with 'none'"
        text = 'none'

    if not isinstance(text, str):
        text = str(text)

    sane = text.translate(_translator)
    if not sane:
        return sane

    if sane[0] in digits:
        sane = '_' + sane
        if warn:
            print "Warning in sanitising dict keys: First character of '%s' is a digit so prepending an underscore" % sane[1:]
    if sane in _method_names:
        sane = '_' + sane
        if warn:
            print "Warning in sanitising dict keys: '%s' is a reserved method name so prepending an underscore" % sane
    if sane.startswith('__'):
        raise ValueError, "Cannot use a name that starts with double underscores: %s" % sane

    return sane

def make_safe(items, warn=True):
    '''
    Make a list of key/value tuples safe by sanitising keys
    and make them unique
    '''
    if items is None or len(items) == 0:
        return []

    items = zip(*items) # transform to two lists
    keys = [sanitise_name(k, warn) for k in items[0]]
    vals = items[1]

    lk = len(keys)
    sk = set(keys)
    ls = len(sk)
    if ls < lk:
        # some headers are not unique so append a number
        uk = dict(zip(sk, [0]*ls))
        for i,k in enumerate(keys):
            n = uk[k]
            if n > 0:
                newkey = keys[i] + str(n)
                while newkey in uk:
                    n += 1
                    newkey = keys[i] + str(n)
                if warn:
                    print "Replacing duplicate key '%s' with '%s'" % (k, newkey)
                keys[i] = newkey
            uk[k] = n+1
    
    return zip(keys, vals)

from _external.ordereddict import OrderedDict as _odict

class ListDict(object):
    '''
    Combined list/ordered dictionary class. Keys to the dictionary can be exposed as attributes.
    This supports all dictionary methods, pop, append, extend, index, remove and del
    '''
    def __init__(self, data=None, warn=True, lock=False, interactive=True):
        '''
        A dictionary or list of tuples of key/value pairs. If lock=True,
        keys cannot be reassigned without first deleting the item. If interactive=True,
        then expose keys as attributes.
        '''
        super(ListDict, self).__setattr__('__lock__', lock)
        super(ListDict, self).__setattr__('__warn__', warn)
        super(ListDict, self).__setattr__('__inter__', interactive)
        if isinstance(data, dict):
            data = [ i for i in data.items() ]
        if interactive:
            data = make_safe(data, warn)
        super(ListDict, self).__setattr__('__odict__', _odict(data))
        if interactive and data:
            self.__dict__.update(data)

    def _replacedata(self, data):
        self.__odict__.clear()
        if isinstance(data, dict):
            data = [ i for i in data.items() ]
        if self.__inter__:
            data = make_safe(data, self.__warn__)
        self.__odict__.update(data)
        if self.__inter__ and data:
            self.__dict__.update(data)

    def __getitem__(self, key):
        '''
        Key can be a number (integer) in which case return value at index of key list
        '''
        from types import StringType, IntType, UnicodeType
        if type(key) is IntType:
            if key > self.__odict__.__len__():
                raise IndexError, 'Key was too large'
            key = self.__odict__.keys()[key]

        if type(key) is StringType or type(key) is UnicodeType:
            return self.__odict__.__getitem__(key)
        else:
            raise KeyError, "Key was not a string or integer"

    def __setitem__(self, key, value):
        '''
        Key can be a number (integer) in which case set value at index of key list
        '''
        from types import StringType, UnicodeType, IntType
        kt = type(key)
        if kt is IntType:
            if key > len(self.__odict__):
                raise IndexError, "Key was too large"
            key = self.__odict__.keys()[key]
            kt = type(key)

        if kt is StringType or kt is UnicodeType:
            if self.__inter__:
                key = sanitise_name(key, self.__warn__)
            if self.__lock__ and key in self.__odict__:
                raise KeyError, "Dictionary is locked, delete item to reassign to key"
            self.__odict__.__setitem__(key, value)
            if self.__inter__:
                self.__setattr__(key, value)
        else:
            raise KeyError, "Key was not a string or integer"

    def __delitem__(self, key):
        '''
        Key can be a number (integer) in which case set value at index of key list
        '''
        from types import StringType, IntType
        if type(key) is IntType:
            if key > len(self):
                raise IndexError, "Key was too large"
            key = self.__odict__.keys()[key]

        if type(key) is StringType:
            self.__odict__.__delitem__(key)
            if self.__inter__:
                self.__dict__.__delitem__(key)
        else:
            raise KeyError, "Key was not a string or integer"

    def __setattr__(self, key, value):
        from types import StringType
        if type(key) is not StringType:
            raise KeyError, "Key was not a string or integer"

        if self.__lock__ and key in self.__odict__:
            raise KeyError, "Dictionary is locked, delete item to reassign to key"

        if self.__inter__:
            self.__dict__[key] = value
            if key in ['_OrderedDict__end', '_OrderedDict__map'] or key.startswith('__'):
                return # ignore internal attributes used by ordereddict implementation

            self.__odict__.__setitem__(key, value)

    def __delattr__(self, key):
        self.__delitem__(key)

    def __str__(self):
        s = "{"
        for k in self.__odict__: #_odict.__iter__(self):
            s += k + ": " + str(self[k]) + ", "
        if len(s) > 0:
            s = s[:-2]
        return s + '}'

    def __repr__(self):
        s = ""
        for k in self.__odict__: #_odict.__iter__(self):
            s += "('" + k + "', " + str(self[k]) + "), "
        if len(s) > 0:
            s = s[:-2]
        return _odict.__class__(_odict(self)).__name__ + "([" + s + "])"

    def __len__(self):
        return self.__odict__.__len__()

    def __iter__(self):
        return self.__odict__.iterkeys()

    def keys(self):
        return self.__odict__.keys()

    def clear(self):
        self.__odict__.clear()

    def update(self, other, **kwargs):
        self.__odict__.update(other, kwargs)

    def setdefault(self, key, default=None):
        self.__odict__.setdefault(key, default)

    def values(self):
        return self.__odict__.values()

    def items(self):
        return self.__odict__.items()

    def iterkeys(self):
        return self.__odict__.iterkeys()

    def itervalues(self):
        return self.__odict__.itervalues()

    def iteritems(self):
        return self.__odict__.iteritems()

    def pop(self, i=-1):
        '''
        Remove item at i-th index and return it
        '''
        from types import IntType
        if type(i) is not IntType:
            raise IndexError, "Index must be an integer"
        r = self.__getitem__(i)
        self.__delitem__(i)
        return r

    def append(self, item):
        '''
        Add item on to end of list. Item must be one of following:
            * a dictionary (only first key/value pair used)
            * a list or tuple with more than one item (otherwise single item is used as an object for next criterion)
            * an object with a 'name' attribute
        '''
        from types import TupleType, ListType, DictType
        if type(item) is DictType:
            if len(item) > 1:
                print "Only adding first item in dictionary"
            if len(item) > 0:
                item = item.popitem() # fall through with key/value pair
        if type(item) is TupleType or type(item) is ListType:
            if len(item) > 1:
                k = item[0]
                v = item[1:]
                if len(v) == 1:
                    v = v[0]
                self[k] = v
                return
            else:
                item = item[0]
        if hasattr(item, 'name'):
            if item.name is None:
                raise KeyError, "Item's 'name' attribute is None"
            self[item.name] = item
            return
        raise ValueError, "Item is not a sequence or has no 'name' attribute"

    def extend(self, items):
        '''
        Add items in iterable to end of list
        '''
        from types import DictType
        if type(items) is DictType:
            for i in items.items():
                self.append(i)
        else:
            for i in items:
                self.append(i)

    def index(self, value):
        '''
        Return index of first occurrence of value  
        '''
        for k,v in self.items():
            if v == value:
                return self.keys().index(k)
        raise ValueError, "Value not found"

    def remove(self, value):
        '''
        Remove first occurrence of value
        '''
        for k,v in self.items():
            if v == value:
                del self[k]
                return
        raise ValueError, "Value not found"

class DataHolder(ListDict):
    '''
    This class holds data and metadata from a single data file
    
    Arguments:
    data        -- a list of tuples where each tuple is a string, NumPy array pair
    metadata    -- a list of tuples where each tuple is a string, object pair
    warn        -- if True (default), print warnings about key names

    Data can be accessed in three ways:
        1. as an attribute
        2. as a dictionary value
        3. as a list item
    Metadata can be accessed in a similar manner though from an attribute called metadata
    '''
    def __init__(self, data=None, metadata=None, warn=True):
        ListDict.__init__(self, data, warn)
        self.metadata = ListDict(metadata, warn)

def _test_make_safe():
    # test make_safe
    data = [ ('a', 0), ('a', 1), ('a.b', -2), ('a$asd923', -4), (u'a£b£.123', -7) ]
    safe_data = make_safe(data)
    expected = [ ('a', 0), ('a1', 1), ('a_b', -2), ('a_asd923', -4), ('a_b_.123', -7) ]
    for a, b in zip(safe_data, expected):
        if a[0] != b[0] and a[1] != b[1]:
            print("Actual %s, %d does not match expected %s, %d" % (a, b))

def _test_setting_listdict(inter=True):
#    d = ListDict({'a': 1, 'c':-2})
    d = ListDict([('a', 1), ('c',-2)], interactive=inter)
    if inter:
        print d.a
    else:
        print d['a']
    print len(d)
    d['d'] = 0.7
    if inter:
        d.b = 0.5
        print d.b
    else:
        d['b'] = 0.5
        print d['b']
    print len(d)
    print d.keys()
    print d
    print d.items()
    d[1] = 2.2
    print d
    d[1] = 2.5
#    print d[2]
    del d['d']
    print d.pop()
    print d
    if inter:
        del d.c
    else:
        del d['c']
    print d
    print d.keys()
    d.append(('d', -2.3))
    d.append({'e': 2.3})
    d.append(['f', 2.3])
    d.append(['g', 1])
    print d
    print d.index(1)
    d.remove(1)
    class testObj():
        def __str__(self):
            return '"test obj"'

    try:
        g = testObj()
        d.append(g)
    except Exception, e:
        print 'Exception raised successfully ' + str(e)

    try:
        g.name = None
        d.append(g)
    except Exception, e:
        print 'Exception raised successfully ' + str(e)

    g.name = 'blah'
    d.append(g)
    print d

    try:
        d.extend(2.3)
    except Exception, e:
        print 'Exception raised successfully ' + str(e)

    d.extend({'h': -2})
    d.extend([('i',-2.5)])
    print d
    d['d e'] = 7.5
    print d
    if inter:
        print d.d_e
    else:
        print d['d e']
    print d[6]

    try:
        d.append((None, -20))
    except Exception, e:
        print "Exception raised successfully " + str(e)

    print sanitise_name(None)
    print sanitise_name(1)

    ld = ListDict([('a', 1), ('c',-2)], lock=True, interactive=inter)
    try:
        ld['c'] = 3
    except Exception, e:
        print "Exception raised successfully " + str(e)

    if inter:
        try:
            ld.c = 3
        except Exception, e:
            print "Exception raised successfully " + str(e)

    try:
        ld[0] = 3
    except Exception, e:
        print "Exception raised successfully " + str(e)

    if inter:
        del ld.c
        ld.c = 3
    else:
        del ld['c']
        ld['c'] = 3
    print ld
    ld.__c = 3
    print ld

#    from pprint import pprint
#    pprint(dir(ld)); print
    ld._replacedata([('a', -1), ('c',-2.5)])

def _test_subclass():
    class new_list(ListDict):
        def __init__(self, data=None, warn=True, lock=False):
            super(new_list, self).__init__(data=data, warn=warn, lock=lock, interactive=True)

    rl = new_list()
    rl['a'] = 1.23
    print rl

if __name__ == "__main__":
    _test_setting_listdict()
#    _test_setting_listdict(inter=False)
#    _test_subclass()
