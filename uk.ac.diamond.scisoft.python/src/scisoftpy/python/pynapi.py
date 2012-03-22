#!/usr/bin/env python
# This program is public domain

"""
Tree view for NeXus files.

The `nexus.tree` routines provide a natural interface to NeXus datasets.
Entries in a group are referenced much like fields in a class are
referenced in python.  Rather than following the directory model of
the `nexus.napi` interface, users are free to reference separate fields
in the dataset at the same time.  Large datasets are not read until 
they are needed, and may be read or written one slab at a time.

There are a number of functions which operate on files::

    import nexus
    nxfile = nexus.load('file.nxs') # loads a structure from a file
    nexus.save('copy.nxs', tree)    # saves a structure to a file
    nexus.tree('copy.nxs')          # display the contents of a file

The tree returned from load() has an entry for each group, field and
attribute.  You can traverse the hierarchy using the names of the
groups.  For example, tree.entry.instrument.detector.distance is an
example of a field containing the distance to each pixel in the 
detector. Entries can also be referenced by NXclass name, such as 
tree.NXentry[0].instrument. Since there may be multiple entries of the 
same NXclass, the NXclass attribute returns a possibly empty list.

Properties of the entry in the tree are referenced by nx attributes.
Depending on the node type, different nx attributes may be available.

Nodes (class NXnode) have attributes shared by both groups and fields::
    * nxname     node name
    * nxclass    node class for groups, 'SDS' for fields
    * nxgroup    group containing the entry, or None for the root
    * nxattrs    dictionary of NeXus attributes for the node

Groups (class NXgroup) have attributes for accessing children::
    * nxentries  dictionary of entries within the group
    * nxcomponent('nxclass')  return group entries of a particular class
    * nxdir()    print the list of entries in the group
    * nxtree()   print the list of entries and subentries in the group
    * nxplot()   plot signal and axes for the group, if available

Fields (class SDS) have attributes for accessing data:
    * nxdims     dimensions of data in the field
    * nxtype     data type
    * nxdata     data in the field
    * nxdata_as  data returned in particular units
    * nxslab     slab context for the field

Linked fields (class NXlink) have attributes for accessing the link::
    * nxlink     reference to the linked field

Unknown fields (class Unknown) are groups with a name that doesn't
start with 'NX'.  These groups are not loaded or saved.

NeXus attributes (class NXattr) have a type and a value only::
    * nxtype     attribute type
    * nxdata     attribute data

Data can be stored in the NeXus file in a variety of units, depending
on which facility is storing the file.  This makes life difficult
for reduction and analysis programs which must know the units they
are working with.  Our solution to this problem is to allow the reader
to retrieve data from the file in particular units.  For example, if
detector distance is stored in the file using millimeters you can 
retrieve them in meters using::
    entry.instrument.detector.distance.nxdata_as('m')
See `nexus.unit` for more details on the unit formats supported.

The slab interface to field data works by opening the file handle
and keeping it open as long as the slab interface is needed.  This
is done in python 2.5 using the with statement.  Once the context
is entered, nxget() and nxput() methods on the node allow you to read
and write data a slab at a time.  For example::

    # Read a Ni x Nj x Nk array one vector at a time
    with root.NXentry[0].data.data as slab:
        Ni,Nj,Nk = slab.nxdims
        size = [1,1,Nk]
        for i in range(Ni):
            for j in range(Nj):
                value = slab.nxget([i,j,0],size)

The equivalent can be done in Python 2.4 and lower using the context
functions __enter__ and __exit__::

    slab = data.nxslab.__enter__()
    ... do the slab functions ...
    data.nxslab.__exit__()

You can traverse the tree by component class instead of component name.  
Since there may be multiple components of the same class in one group
you will need to specify which one to use.  For example, 
tree.NXentry[0].NXinstrument[0].NXdetector[0].distance references the 
first detector of the first instrument of the first entry.  Unfortunately, 
there is no guarantee regarding the order of the entries, and it may vary 
from call to call, so the above is of limited utility.

The load() and save() functions are implemented using the class
`nexus.tree.NeXusTree`, a subclass of `nexus.napi.NeXus` which allows 
all the usual API functions.  You can subclass NeXusTree with your
own version that defines, e.g., a NXmonitor() method to return an
NXmonitor object when an NXmonitor class is read.  Your NXmonitor
class should probably be a subclass of NXgroup.
"""
__all__ = ['SDS', 'NXgroup', 'NXattr', 
           'NXlink', 'NXlinkdata', 'NXlinkgroup',
           'load', 'save', 'tree', 'HDF5Loader', 'NXLoader' ]

#import numpy as np #@UnresolvedImport
from nxs import napi #@UnresolvedImport

from ..nexus.hdf5 import HDF5tree as _tree
from ..nexus.hdf5 import HDF5group as _group
from ..nexus.hdf5 import HDF5dataset as _dataset
from ..nexus import nxclasses as _nx

from pycore import ndarray, ndgeneric, asarray, array, zeros, ones
import numpy as _np #@UnresolvedImport


io_exception = IOError
class _lazydataset(object):
    def __init__(self, dataset):
        self.maxshape = dataset.maxshape
        self.shape = dataset.shape
        self.dtype = dataset.dtype
        self.file = dataset.file.filename
        self.name = dataset.name

    def __getitem__(self, key):
        try:
            nh = napi.open(self.file, 'r')
        finally:
            pass

        if nh is None:
            raise io_exception, "No tree found"

        v = nh[self.name][key]

        nh.close()

        return v
    
class SDS(_dataset):
    def __init__(self, dataset, attrs={}, parent=None):
        '''Make a SDS
        
        dataset can be a ndarray or HDF5Dataset when created from a file
        '''
        if not isinstance(dataset, (_lazydataset, ndarray)): #, h5py.Dataset
            dataset = asarray(dataset)
        _dataset.__init__(self, dataset, attrs=attrs, parent=parent)
#        self.__data = dataset
#        if not isinstance(dataset, ndarray):
#            self.__shape = tuple(dataset.shape)
#            self.__maxshape = tuple(dataset.maxshape)
#        else:
#            self.__shape = self.__maxshape = tuple(dataset.shape)

class NXLoader(object):
    def __init__(self, name):
        self.name = name

    def load(self):
        # capture all error messages
        try:
            nh = napi.open(self.name, 'r')
        finally:
            pass

        if nh is None:
            raise io_exception, "No tree found"

        # convert tree to own tree
        pool = dict()
        t = self._copynode(pool, nh)
        nh.close()
        return t

    def _mkgroup(self, link, attrs, parent):
        if parent is None:
            return _tree(link.filename, attrs)
        return _group(attrs, parent)

    def _mkdataset(self, dataset, attrs, parent):
        d = _lazydataset(dataset)
        return _dataset(d, attrs=attrs, parent=parent)

    def _copynode(self, pool, link, parent=None):
        if link.id in pool:
            return pool[link.id]

        attrs = []
        for n,v in link.attrs():
#            if isinstance(v, ndgeneric):
#                if v.dtype.kind in 'SUa':
#                    v = str(v)
#                else:
#                    print v.shape,
#                    v = v.item()
#                    print dir(v)
#                    pass
#            else:
#                if len(v) == 1:
#                    v = v[0]
            attrs.append((n, v))

        if isinstance(link, h5py.Group):
            g = self._mkgroup(link, attrs, parent)
            pool[link.id] = g
            nodes = []
            for n,l in link.items():
                try:
                    nodes.append((n, self._copynode(pool, l, g)))
                except Exception, e:
                    print n, l, e
            g.init_group(nodes)
            return g
        elif isinstance(link, h5py.Dataset):
            d = self._mkdataset(link, attrs, parent)
            pool[link.id] = d
            return d
        elif isinstance(link, h5py.SoftLink):
            pass
        elif isinstance(link, h5py.ExternalLink):
            pass
        else:
            pass

    def setloadmetadata(self, load_metadata):
        pass

class HDF5Loader(NXLoader):
    pass

from copy import copy



NeXusError = napi.NeXusError

_np.set_printoptions(threshold=5)

class NeXusTree(napi.NeXus):
    """
    Structure-based interface to the NeXus file API.

    Usage::

      file = NeXusTree(filename, ['r','rw','w'])
        - open the NeXus file
      root = file.readfile()
        - read the structure of the NeXus file.  This returns a NeXus tree.
      file.writefile(root)
        - write a NeXus tree to the file.
      data = file.readpath(path)
        - read data from a particular path

    Example::

      nx = NeXusTree('REF_L_1346.nxs','r')
      tree = nx.readfile()
      for entry in tree.NXentry:
          process(entry)
      copy = NeXusTree('modified.nxs','w')
      copy.writefile(tree)

    Note that the large datasets are not loaded immediately.  Instead, the
    when the data set is requested, the file is reopened, the data read, and
    the file closed again.  open/close are available for when we want to
    read/write slabs without the overhead of moving the file cursor each time.
    The NXdata nodes in the returned tree hold the node values.

    Subclasses can provide methods for individual NeXus classes such
    as NXbeam or NXdata.  Brave users can also specialize NXgroup, 
    NXattr, SDS and NXlink methods.
    """
    def readfile(self):
        """
        Read the nexus file structure from the file.  Large datasets
        are not read until they are needed.  

        Returns a tree of NXgroup, SDS and NXlink nodes.
        """
        self.open()
        self.openpath("/")
        root = self._readgroup()
        self.close()
        root.nxgroup = None        
        # Resolve links (not necessary now that nxlink is set as a property)
        #self._readlinks(root, root)
        return root

    def writefile(self, tree):
        """
        Write the nexus file structure to the file.  The file is assumed to
        start empty.

        Updating individual nodes can be done using the napi interface, with
        nx.handle as the nexus file handle.
        """
        self.open()
        links = []
        # Root node is special --- only write its children.
        # TODO: maybe want to write root node attributes?
        for entry in tree.nxentries.values():
            links += self._writegroup(entry, path="")
        self._writelinks(links)
        self.close()

    def readpath(self, path):
        """
        Read the data on a particular file path.

        Returns a numpy array containing the data, a python scalar, or a
        string depending on the shape and storage class.
        """
        self.open()
        self.openpath(path)
        try:
            return self.getdata()
        except ValueError:
            return None

    def _readdata(self, name):
        """
        Read a data node, returning SDS or NXlink depending on the
        nature of the node.
        """
        # Finally some data, but don't read it if it is big
        # Instead record the location, type and size
        self.opendata(name)
        attrs = self.getattrs()
        if 'target' in attrs and attrs['target'].nxdata != self.path:
            # This is a linked dataset; don't try to load it.
            #print "read link %s->%s"%(path,attrs['target'].nxdata)
            data = NXlinkdata(target=attrs['target'].nxdata, name=name)
        else:
            dims,type = self.getinfo()
            #Read in the data if it's not too large
            if _np.prod(dims) < 1000:# i.e., less than 1k dims
                try:
                    value = self.getdata()
                except ValueError:
                    value = None
            else:
                value = None
            data = self.SDS(value=value,name=name,dtype=type,shape=dims,
                            attrs=attrs,file=self,path=self.path)
        self.closedata()
        return data

    # These are groups that HDFView explicitly skips
    _skipgroups = ['CDF0.0','_HDF_CHK_TBL_','Attr0.0','RIG0.0','RI0.0',
                   'RIATTR0.0N','RIATTR0.0C']

    def _readchildren(self,n):
        children = {}
        for dummy in range(n):
            name,nxclass = self.getnextentry()
            #print "name,class,path",name,nxclass,self.path
            if nxclass in self._skipgroups:
                pass # Skip known bogus classes
            elif nxclass == 'SDS':
                children[name] = self._readdata(name)
            elif nxclass.startswith('NX'):
                self.opengroup(name,nxclass)
                children[name] = self._readgroup()
                self.closegroup()
            else: # Bad entry; flag it but don't do anything
#                children[name] = self.Unknown(nxname=name,nxclass=nxclass)
                self.opengroup(name,nxclass)
                children[name] = self._readgroup()
                self.closegroup()
        return children

    def _readgroup(self):
        """
        Read the currently open group and all subgroups.
        """
        # TODO: does it make sense to read without recursing?
        # TODO: can we specify which NXclasses we are interested
        # in and skip those of different classes?
        n,name,nxclass = self.getgroupinfo()
        attrs = self.getattrs()
        if 'target' in attrs and attrs['target'].nxdata != self.path:
            # This is a linked group; don't try to load it.
            #print "read group link %s->%s"%(attrs['target'].nxdata,self.path)
            group = self.NXlinkgroup(target=attrs['target'].nxdata, name=name)
        else:
            #print "read group",nxclass,"from",self.path
            children = self._readchildren(n)
            # If we are subclassed with a handler for the particular
            # NXentry class name use that constructor for the group
            # rather than the generic NXgroup class.
            if hasattr(self,nxclass):
                factory = getattr(self,nxclass)
                #print 'factory', type(self), type(nxclass), type(factory)
            else:
                factory = self.NXgroup
            group = factory(nxclass=nxclass,nxname=name,attrs=attrs,entries=children)
            # Build chain back structure
            for node in children.values():
                node.nxgroup = group
        return group
    
    def _readlinks(self, root, group):
        """
        Convert linked nodes into direct references.
        """
        for entry in group.nxentries.values():
            if isinstance(entry, NXlinkdata) or isinstance(entry, NXlinkgroup):
                link = root
                try:
                    for level in entry._target[1:].split('/'):
                        link = getattr(link,level)
                    entry.nxlink = link
                except AttributeError:
                    pass
            elif isinstance(entry, NXgroup):
                self._readlinks(root, entry)
                
    def _writeattrs(self, attrs):
        """
        Return the attributes for the currently open group/data or for
        the file if no group or data object is open.
        """
        for name,pair in attrs.iteritems():
            #print "write attrs",name,pair.nxtype,pair.nxdata
            self.putattr(name,pair.nxdata,pair.nxtype)

    def _writedata(self, data, path):
        """
        Write the given data node.

        NXlinks cannot be written until the linked group is created, so
        this routine returns the set of links that need to be written.
        Call writelinks on the list.
        """

        path = path + "/" + data.nxname
        #print 'write data',path

        # If the data is linked then
        if hasattr(data,'_target'):
            return [(path, data._target)]

        # Finally some data, but don't read it if it is big
        # Instead record the location, type and size
        #print "creating data",child.nxname,child.nxdims,child.nxtype
        #If the array size is too large, their product needs a long integer
        if _np.prod(data.nxdims) > 10000 or _np.prod(data.nxdims) < 0:
            # Compress the fastest moving dimension of large datasets
            slab_dims = ones(len(data.nxdims),'i')
            slab_dims[-1] = data.nxdims[-1]
            self.compmakedata(data.nxname, data.nxtype, data.nxdims,
                              'lzw', slab_dims)
        else:
            # Don't use compression for small datasets
            self.makedata(data.nxname, data.nxtype, data.nxdims)
        self.opendata(data.nxname)
        self._writeattrs(data.nxattrs)
        value = data.nxdata
        if value is not None:
            self.putdata(data.nxdata)                
        self.closedata()
        return []

    def _writegroup(self, group, path):
        """
        Write the given group structure, including the data.

        NXlinks cannot be written until the linked group is created, so
        this routine returns the set of links that need to be written.
        Call writelinks on the list.
        """
        path = path + "/" + group.nxname
        #print 'write group',path

        links = []
        self.makegroup(group.nxname, group.nxclass)
        self.opengroup(group.nxname, group.nxclass)
        self._writeattrs(group.nxattrs)
        if hasattr(group, '_target'):
            links += [(path, group._target)]
        for child in group.nxentries.values():
            if child.nxclass == 'SDS':
                links += self._writedata(child,path)
            elif hasattr(child,'_target'):
                links += [(path+"/"+child.nxname,child._target)]
            else:
                links += self._writegroup(child,path)
        self.closegroup()
        return links

    def _writelinks(self, links):
        """
        Create links within the NeXus file as indicated by the set of pairs
        returned by writegroup.
        """
        gid = {}

        # identify targets
        for path,target in links:
            gid[target] = None

        # find gids for targets
        for target in gid.iterkeys():
            #sprint "target",target
            self.openpath(target)
            # Can't tell from the name if we are linking to a group or
            # to a dataset, so cheat and rely on getdataID to signal
            # an error if we are not within a group.
            try:
                gid[target] = self.getdataID()
            except NeXusError:
                gid[target] = self.getgroupID()

        # link sources to targets
        for path,target in links:
            if path != target:
                # ignore self-links
                parent = "/".join(path.split("/")[:-1])
                #print "link %s -> %s"%(parent,target)
                self.openpath(parent)
                self.makelink(gid[target])

_pythontype={type(int()):'int32', type(long()):'int64', 
             type(float()):'float32', type(str()):'char'}

def _gettype(value):
    if isinstance(value, ndgeneric) or isinstance(value, ndarray):
        nxtype = value.dtype.name
        nxdims = max(list(value.shape),[1])
    else:
        try:      
            nxtype = _pythontype[type(value)]
            #print "In api.tree._gettype(value), _pythontype[type(value)]: " + nxtype
            if nxtype == 'char':
                nxdims = [len(value)]
            else:
                nxdims = [1]
        except KeyError:
            nxtype = ''
            nxdims = [1]
    return nxtype, nxdims

def _settype(value, nxtype):
    if nxtype == _gettype(value)[0]:
        return value
    elif nxtype == 'char':
        return str(value)
    else:
        return value.astype(nxtype)
    

def getClass(classname, modulename="tree"):
    return getattr(__import__(modulename, globals(), locals(), [classname]), classname)

def getAxes(axes):
    """
    Parse the 'axes' attribute for the axis names.
    
    The delimiter separating each axis can be white space, a comma, or a colon.
    """
    import re
    sep=re.compile('[\[]*(\s*,*:*)+[\]]*')
    return filter(lambda x: len(x)>0, sep.split(axes.nxdata))

def getRoot(node):
    if node.nxgroup is None:
        return None
    elif node.nxgroup.nxclass == "NXroot":
        return node.nxgroup
    else:
        return getRoot(node.nxgroup)


class NXattr(object):
    """
    Attributes need to keep track of nxtype as well as attribute value.
    """
    def __init__(self,value=None,dtype=''):
        if isinstance(value, NXattr):
            self.nxdata,self.nxtype = value.nxdata,value.nxtype
        elif dtype:
            self.nxdata,self.nxtype = value,dtype
        else:
            self.nxdata,self.nxtype = value,'char'
            if value:
                if isinstance(value, NXnode): 
                    raise NeXusError, "A data attribute cannot be an SDS or NeXus group"
                if isinstance(value, ndgeneric) or isinstance(value, ndarray):
                    self.nxtype = value.dtype.name
                else:
                    try:      
                        self.nxtype = _pythontype[type(value)]
                    except KeyError:
                        pass

    def __str__(self):
        return str(self.nxdata)

    def __repr__(self):
        if self.nxtype == 'char':
            return "NXattr('%s','%s')"%(self.nxdata,self.nxtype)
        else:
            return "NXattr(%s,'%s')"%(self.nxdata,self.nxtype)

    def __eq__(self, other):
        """
        Return true if the value of the attribute is the same as the other.
        """
        if isinstance(other, NXattr):
            return self.nxdata == other.nxdata
        else:
            return self.nxdata == other


class NXnode(object):
    """
    Abstract base class for elements in NeXus files.
    
    The node has a subclass of SDS (Scientific Data Set), NXgroup, or one 
    of the NXgroup subclasses. Child nodes should be accessible directly as 
    object attributes. Constructors for NXnode objects are defined by either 
    the SDS or NXgroup classes.
    
    Attributes
    ----------
    nxclass : string
        The class of the NXnode. NXnodes can have class SDS, NXgroup, 
        or one of the NXgroup subclasses. 
    nxname : string
        The name of the NXnode. 
    nxgroup : NXnode
        The parent NXgroup, if the node is defined as the attribute of parent
        node.
    nxentries : list
        A list of all the NeXus objects contained within an NXgroup. This list
        excludes all node attributes whose names begin with 'nx' or '_'.
    nxattrs : list
        A list of all the NeXus attributes, i.e., attribute with class NXattr.
    nxpath : string
        The path to this node with respect to the root of the NeXus tree. For
        NeXus data read from a file, this will be a group of class NXroot, but
        if the NeXus tree was defined interactively, it can be any valid 
        NXgroup.
    """
    nxclass = "unknown"
    nxname = "unknown"
    nxgroup = None

    def __str__(self):
        return "%s:%s"%(self.nxclass,self.nxname)

    def __repr__(self):
        return "NXnode('%s','%s')"%(self.nxclass,self.nxname)
        
    def __setattr__(self, name, value):
        if not name.startswith('nx') and not name.startswith('_'):
            if not isinstance(value, NXnode):
                value = SDS(value)
            value.nxname=name
            value.nxgroup=self.nxname
        object.__setattr__(self, name, value)

    def _setattrs(self, attrs):
        for k,v in attrs.items():          
            setattr(self, k, v)

    def _attrs(self):
        return dict([(k,v) 
                     for k,v in self.__dict__.items()
                     if isinstance(v,NXattr)])

    def _entries(self):
        return dict([(k,v) 
                     for k,v in self.__dict__.items()
                     if isinstance(v,NXnode) and not k.startswith('nx')
                        and not k.startswith('_')])

    nxattrs = property(_attrs,doc="NeXus attributes for node")
    nxentries = property(_entries,doc="NeXus nodes within group")

    def _str_name(self,indent=0):
        if self.nxclass == 'SDS':
            return " "*indent+self.nxname
        else:
            return " "*indent+self.nxname+':'+self.nxclass

    def _str_value(self,indent=0):
        return ""

    def _str_attrs(self,indent=0):
        attrs = self.nxattrs
        names = attrs.keys()
        names.sort()
        result = []
        for k in names:
            result.append(" "*indent+"@%s = %s"%(k,attrs[k].nxdata))
        return "\n".join(result)

    def _str_tree(self,indent=0,attrs=False,recursive=False):
        """
        Print current node and possibly subnodes.
        """
        # Print node
        result = [self._str_name(indent=indent)]
        if attrs and self.nxattrs: 
            result.append(self._str_attrs(indent=indent+2))
        # Print children
        entries = self.nxentries
        if entries:
            names = entries.keys()
            names.sort()
            if recursive:
                for k in names:
                    result.append(entries[k]._str_tree(indent=indent+2,
                                                       attrs=attrs, recursive=True))
            else:
                for k in names:
                    result.append(entries[k]._str_name(indent=indent+2))
        result
        return "\n".join(result)
    
    def _getpath(self):
        if self.nxgroup is None:
            return ""
        else:
            return self.nxgroup._getpath()+"/"+self.nxname
    
    def _getheadnode(self):
        if self.nxgroup is None:
            return self
        else:
            return self.nxgroup._getheadnode()
    
    nxpath = property(_getpath, "Path to NeXus node")
    nxhead = property(_getheadnode, "Head node of this object's tree")

    def nxdir(self,attrs=False,recursive=False):
        """
        Print the node directory.
        
        The directory is a list of NeXus objects within this node, either 
        NeXus groups or SDS data. If 'attrs' is True, SDS attributes are 
        displayed. If 'recursive' is True, the contents of child groups are 
        also displayed.
        """
        print self._str_tree(attrs=attrs,recursive=recursive)

    def nxtree(self,attrs=True):
        """
        Print the directory tree.
        
        The tree contains all child objects of this node and their children. 
        It invokes the 'nxdir' method with both 'attrs' and 'recursive' set 
        to True.
        """
        print self._str_tree(attrs=attrs,recursive=True)
        
    def nxtreestr(self, attrs=True):
        """Return directory tree string"""
        return self._str_tree(attrs=attrs,recursive=True)
    
#    def nxsave(self, filename, format='w5'):
#        """
#        Save the NeXus node to a data file.
#        
#        The object is wrapped in an NXroot group (with name 'root') and an 
#        NXentry group (with name 'entry'), if necessary, in order to produce 
#        a valid NeXus file.
#        """
#        #print "nxsave() call in tree.py NXnode class"
#        if self.nxclass == "NXroot":
#            tree = self
#        elif self.nxclass == "NXentry":
#            tree = NXroot(self)
#        else:
#            tree = NXroot(NXentry(self))
#        file = NeXusTree(filename, format)
#        file.writefile(tree)
#        file.close()


class SDS(NXnode):
    """
    A NeXus data node (Scientific Data Set).
    
    This is a subclass of NXnode to contain scalar, array, or string data
    and associated NeXus attributes. 
    
    SDS(value=None, name='unknown', dtype='', shape=[], attrs={}, file=None, 
        path=None, group=None, **attr)

    Parameters
    ----------
    value : scalar value, Numpy array, or string
        The numerical or string value of the SDS, which is directly accessible 
        as the SDS attribute 'nxdata'. 
    name : string
        The name of the SDS, which is directly accessible as the SDS 
        attribute 'nxname'. If the SDS is initialized as the attribute of 
        a parent node, the name is automatically set to the name of this 
        attribute.
    dtype : string
        The data type of the SDS value, which is directly accessible as the 
        SDS attribute 'nxtype'. Valid types correspond to standard Numpy 
        data types, using names defined by the NeXus API,
        i.e., 'float32' 'float64'
              'int8' 'int16' 'int32' 'int64'
              'uint8' 'uint16' 'uint32' 'uint64'
              'char'
        If the data type is not specified, then it is determined automatically
        by the data type of the 'value' parameter.
    shape : list of ints
        The dimensions of the SDS data, which is accessible as the SDS
        attribute 'nxdims'. This corresponds to the shape of a Numpy array, 
        or the length of a string. The shape is [1] if the value is a scalar.
    attrs : dict
        A dictionary containing SDS attributes. The dictionary values should
        all have class NXattr.
    file : filename
        The file from which the SDS has been read.
    path : string
        The path to this node with respect to the root of the NeXus tree, 
        using the convention for unix file paths.
    group : NXnode (SDS, NXgroup, or subclass of NXgroup)
        The parent NeXus node, which is accessible as the SDS attribute 
        'nxgroup'. If the SDS is initialized as the attribute of 
        a parent node, the group is set to the parent node.
    
    SDS attributes can also be set directly by keyword arguments, which are
    converted to objects of class NXattr, e.g.,
    
        temperature = SDS(40.0, units='K')

    Attributes
    ----------
    nxclass : 'SDS'
        The class of the NXnode.
    nxname : string
        The name of the SDS. 
    nxtype : string
        The data type of the SDS value. Valid values are given above.
    nxdims : list of ints
        The dimensions of the SDS data. This is equivalent to the shape of a 
        Numpy array. 
    nxattrs : dict
        A dictionary of the SDS attributes, i.e., those with class NXattr
    nxdata : scalar, Numpy array or string
        The data value of the SDS. This is normally initialized using the 
        'value' parameter (see above). If the NeXus data is contained 
        in a file and the size of the SDS array is too large to be stored 
        in memory, the value is not read in until this attribute is directly
        accessed. Even then, if there is insufficient memory, a value of None
        will be returned. In this case, the SDS array should be read as a 
        series of smaller slabs using 'nxget'. 
    nxdata_as('units') : scalar value or Numpy array
        If the SDS 'units' attribute has been set, the data values, stored
        in 'nxdata', are returned after conversion to the specified units.
    nxpath : string
        The path to this node with respect to the root of the NeXus tree. For
        NeXus data read from a file, this will be a group of class NXroot, but
        if the NeXus tree was defined interactively, it can be any valid 
        NXgroup. This is determined by recursively accessing the 'nxgroup'
        attributes of the parent nodes.

    Methods
    -------
    nxdir(self, attrs=False):
        Print the SDS specification.
        
        This outputs the name, dimensions and data type of the SDS. 
        If 'attrs' is True, SDS attributes are displayed.

    nxtree(self, attrs=True):
        Print the SDS and its attributes.
        
        It invokes the 'nxdir' method with 'attrs' set to True.
        
    nxsave(filename, format='w5')
        Save the SDS into a file wrapped in a NXroot group and NXentry group
        with default names. This is equivalent to 
        
        >>> NXroot(NXentry(SDS(...))).nxsave(filename)

    Examples
    --------
    >>> x = SDS(np.linspace(0,2*np.pi,101), units='degree')
    >>> phi = x.nxdata_as(units='radian')
    >>> y = SDS(np.sin(phi))
    
    # Read a Ni x Nj x Nk array one vector at a time
    >>> with root.NXentry[0].data.data as slab:
            Ni,Nj,Nk = slab.nxdims
            size = [1,1,Nk]
            for i in range(Ni):
                for j in range(Nj):
                    value = slab.nxget([i,j,0],size)

    """
    def __init__(self, value=None, name='unknown', dtype='', shape=(), attrs={},
                 file=None, path=None, group=None, **attr):
        if isinstance(value, list) or isinstance(value, tuple):
            value = array(value)
        self._value = value
        self.nxclass = 'SDS' # Scientific Data Set
        self.nxname = name
        self.nxtype = ''
        self.nxdims = list(shape)
        #print "self.nxtype: " + str(self.nxtype)
        if dtype:
            self.nxtype = dtype
            #print "self.nxtype: " + str(self.nxtype)
        elif value is not None:
            #print "Type declared in SDS.__init__(): " + str(_gettype(value))
            self.nxtype, self.nxdims = _gettype(value)
        else:
            self.nxtype = ''
        self.nxgroup = group
        # Append extra keywords to the attribute list
        for key in attr.keys():
            attrs[key] = NXattr(attr[key])
        # Convert NeXus attributes to python attributes
        self._setattrs(attrs)
#        if 'units' in attrs:
#            units = attrs['units'].nxdata
#        else:
#            units = None
#        self._converter = unit.Converter(units)
        self._incontext = False
        self._file = file
        self._path = path
        for key in attrs.keys():
            del attrs[key]

    def __repr__(self):
        if self._value is not None:
            return "SDS(name=%s,value=%s)" % (self.nxname, self._str_value())
        else:
            return "SDS(name='%s')" % self.nxname
        
    def __setattr__(self, name, value):
        if name.startswith('nx') or name.startswith('_') or isinstance(value, NXattr):
            object.__setattr__(self, name, value)
        else:
            object.__setattr__(self, name, NXattr(value))

    def __len__(self):
        """
        Return the length of the SDS data.
        """
        return _np.prod(self.nxdims)
        
    def index(self, value, max=False):
        """
        Return the index of the SDS nxdata array that is greater than or equal to the value.
        
        If max, then return the index that is less than or equal to the value.
        This should only be used on one-dimensional monotonically increasing arrays.
        """
        if max:
            return len(self.nxdata)-len(self.nxdata[self.nxdata>=value])
        else:
            return len(self.nxdata[self.nxdata<value])

    def __getitem__(self, index):
        """
        Returns a slice from the SDS.
        
        In most cases, the slice values are applied to the SDS nxdata array
        and returned within an SDS object with the same metadata. However,
        if the array is one-dimensional and the index start and stop values 
        are real, the nxdata array is returned with values between those limits.
        This is to allow axis arrays to be limited by their actual value. This
        real-space slicing should only be used on monotonically increasing (or
        decreasing) one-dimensional arrays.
        """
        if isinstance(index, slice) and \
           (isinstance(index.start, float) or isinstance(index.stop, float)):
            index = slice(self.index(index.start), self.index(index.stop,max=True)+1)
        if self._value is not None:
            result = self.nxdata.__getitem__(index)
        else:
            offset = zeros(len(self.nxdims),dtype=int)
            size = array(self.nxdims)
            if isinstance(index, int):
                offset[0] = index
                size[0] = 1
            else:
                if isinstance(index, slice): index = [index]
                i = 0
                for ind in index:
                    if isinstance(ind, int):
                        offset[i] = ind
                        size[i] = 1
                    else:
                        if ind.start: offset[i] = ind.start
                        if ind.stop: size[i] = ind.stop - offset[i]
                    i = i + 1
            try:
                result = self.nxget(offset, size)
            except ValueError:
                result = self.nxdata.__getitem__(index)
        return SDS(result, name=self.nxname, attrs=self.nxattrs)
                
    def __setitem__(self, index, value):
        """
        Assign a slice to the SDS.
        """
        if self._value is not None:
            self.nxdata[index] = value
        else:
            raise NeXusError, "SDS dataspace not yet allocated"

    def nxreshape(self, shape):
        """
        Returns the SDS with the defined shape
        """
        self.nxdims = list(shape)
        if isinstance(self.nxdata, ndarray): self.nxdata.shape = shape
        return self

    def __enter__(self):
        """
        Open the datapath for reading slab by slab.

        Note: the results are undefined if you try accessing
        more than one slab at a time.  Don't nest your 
        "with data" statements!
        """
        # TODO: provide a file lock to prevent movement of the
        # file cursor when in the slab context.
        # TODO: if HDF allows multiple cursors, extend napi to support them
        self._close_on_exit = not self._file.isopen
        self._file.open() # Force file open even if closed
        self._file.openpath(self._path)
        self._incontext = True
        return self

    def __exit__(self, *args):
        """
        Close the file associated with the data after reading.
        """
        self._incontext = False
        if self._close_on_exit:
            self._file.close()

    def nxget(self, offset, size, units=""):
        """
        Return a slab from the data array.

        Offsets are 0-origin. Shape can be inferred from the data.
        Offset and shape must each have one entry per dimension.
        
        If units are specified, convert the values to the given units
        before returning them.

        This operation should be performed in a "with group.data"
        context.

        Raises ValueError cannot convert units.

        Corresponds to NXgetslab(handle,data,offset,shape)
        """
        self.__enter__()
        value = self._file.getslab(offset,size)
        self.__exit__()
        return self._converter(value,units)

    def nxput(self, data, offset):
        """
        Put a slab into the data array.

        Offsets are 0-origin.  Shape can be inferred from the data.
        Offset and shape must each have one entry per dimension.

        This operation should be performed in a "with group.data"
        context.

        Raises ValueError if this fails.  No error is raised when
        writing to a file which is open read-only.

        Corresponds to NXputslab(handle,data,offset,shape)
        """
        self.__enter__()
        self._file.putslab(data, offset, data.shape)
        self.__exit__()
        
    def __str__(self):
        """
        If value is loaded, return the value as a string.  If value is
        not loaded, return the empty string.  Only the first view values
        for large arrays will be printed.
        """
        if self._value is not None:
            return str(self._value)
        return ""

    def _str_value(self,indent=0):
        v = str(self)
        if '\n' in v:
            v = '\n'.join([(" "*indent)+s for s in v.split('\n')])
        return v

    def _str_tree(self,indent=0,attrs=False,recursive=False):
        dims = 'x'.join([str(n) for n in self.nxdims])
        #return "%s(%s)"%(self.nxtype, dims)
        s = str(self)
        if '\n' in s or s == "":
            s = "%s(%s)"%(self.nxtype, dims)
        v=[" "*indent + "%s = %s"%(self.nxname, s)]
        if attrs and self.nxattrs: v.append(self._str_attrs(indent=indent+2))
        return "\n".join(v)

    def nxaxes(self):
        """
        Return a list of SDSs containing axes.
        
        Only works if the SDS has the 'axes' attribute
        """
        try:
            return [getattr(self.nxgroup,name) for name in getAxes(self.axes)]
        except KeyError:
            return None

    def nxdata_as(self, units=""):
        """
        Return the data in particular units.
        """
        if self._value is None:
            if self._file:
                self._value = self._file.readpath(self._path)
            else:
                return None
        if isinstance(self._value, _np.ndarray):
            self._value = self._value.reshape(self.nxdims)
        return self._converter(self._value,units)
    
    def nxdata_set(self, value):
        dummy_nxtype, nxdims = _gettype(value)
        if nxdims == self.nxdims:
            self._value = _settype(value, self.nxtype)
        else:
            raise NeXusError, "Dimensions do not match SDS"

    nxdata = property(nxdata_as,nxdata_set,doc="The data values in default units")


class NXgroup(NXnode):
    """
    A NeXus group node.
    
    This is a subclass of NXnode and is the base class for the specific
    NeXus group classes, e.g., NXentry, NXsample, NXdata. 
    
    NXgroup(*items, **opts)

    Parameters
    ----------
    The NXgroup parameters consist of a list of positional and/or keyword 
    arguments.
    
    Positional Arguments : These must be valid NeXus nodes, either an SDS 
    or a NeXus group. These are added without modification as children of this 
    group. 
    
    Keyword Arguments : Apart from a list of special keywords shown below, 
    keyword arguments are used to add children to the group using the keywords
    as attribute names. The values can either be valid SDS data or NXgroups, 
    in which case the 'nxname' attribute is changed to the keyword, or they 
    can be numerical or string data, which are converted to SDS objects.
    
    Special Keyword Arguments:

    name : string
        The name of the NXgroup, which is directly accessible as the NXgroup 
        attribute 'nxname'. If the NXgroup is initialized as the attribute of 
        a parent node, the name is automatically set to the name of this 
        attribute. If 'nxclass' is specified and has the usual prefix 'NX',
        the default name is the class name without this prefix.
    nxclass : string
        The class of the NXgroup. 
    entries : dict
        A dictionary containing a list of group entries. This is an 
        alternative way of adding group entries to the use of keyword 
        arguments.
    file : filename
        The file from which the SDS has been read.
    path : string
        The path to this node with respect to the root of the NeXus tree, 
        using the convention for unix file paths.
    group : NXnode (NXgroup or subclass of NXgroup)
        The parent NeXus node, which is accessible as the group attribute 
        'nxgroup'. If the group is initialized as the attribute of 
        a parent node, this is set to the parent node.

    Attributes
    ----------
    nxclass : string
        The class of the NXnode.
    nxname : string
        The name of the SDS. 
    nxentries : dict
        A dictionary of all the group entries.
    nxattrs : dict
        A dictionary of the group attributes. 
    nxpath : string
        The path to this node with respect to the root of the NeXus tree. For
        NeXus data read from a file, this will be a group of class NXroot, but
        if the NeXus tree was defined interactively, it can be any valid 
        NXgroup. This is determined by recursively accessing the 'nxgroup'
        attributes of the parent nodes.

    Methods
    -------
    nxdir(self, attrs=False):
        Print the group directory.
        
        The directory is a list of NeXus objects within this group, either 
        NeXus groups or SDS data. If 'attrs' is True, SDS attributes are 
        displayed. If 'recursive' is True, the contents of child groups are 
        also displayed.

    nxtree(self, attrs=True):
        Print the SDS and its attributes.
        
        It invokes the 'nxdir' method with both 'attrs' and 'recursive' 
        set to True.
        
    nxsave(filename, format='w5')
        Save the NeXus group into a file

        The object is wrapped in an NXroot group (with name 'root') and an 
        NXentry group (with name 'entry'), if necessary, in order to produce 
        a valid NeXus file.

    nxsave(filename, format='w5')
        Save the NeXus group into a file

        The object is wrapped in an NXroot group (with name 'root') and an 
        NXentry group (with name 'entry'), if necessary, in order to produce 
        a valid NeXus file.

    Examples
    --------
    >>> x = SDS(np.linspace(0,2*np.pi,101), units='degree')
    >>> entry = NXgroup(x, name='entry', nxclass='NXentry')
    >>> entry.sample = NXgroup(temperature=SDS(40.0,units='K'),
                               nxclass='NXsample')
    >>> entry.sample.nxtree()
    sample:NXsample
      temperature = 40.0
        @units = K

    Note: All the currently defined NeXus classes are defined as subclasses
          of the NXgroup class. It is recommended that these are used 
          directly, so that the above examples become:

    >>> entry = NXentry(x)
    >>> entry.sample = NXsample(temperature=SDS(40.0,units='K'))
     
    """

    def __init__(self, *items, **opts):
        if "name" in opts.keys():
            self.nxname = opts["name"]
            del opts["name"]
        if "entries" in opts.keys():
            for k,v in opts["entries"].items():
                setattr(self, k, v)
            del opts["entries"]
        if "attrs" in opts.keys():
            self._setattrs(opts["attrs"])
            del opts["attrs"]
        for k,v in opts.items():
            setattr(self, k, v)
        if self.nxname == "unknown" and self.nxclass.startswith("NX"):
            self.nxname = self.nxclass[2:]
        for item in items:
            try:
                setattr(self, item.nxname, item)
            except AttributeError:
                raise NeXusError, "Non-keyword arguments must be valid NXnodes"

#    def __cmp__(self, other):
#        """Sort groups by their distances or names."""
#        try:
#            return cmp(self.distance, other.distance)
#        except KeyError:
#            return cmp(self.nxname, other.nxname)

    def _str_value(self,indent=0):
        return ""

    def __getattr__(self, key):
        """
        Provide direct access to nodes via nxclass name.
        """
        if key.startswith('NX'):
            return self.nxcomponent(key)
        raise KeyError(key+" not in "+self.nxclass+":"+self.nxname)

    def __setattr__(self, name, value):
        """
        Set a node attribute as a node or regular Python attribute.
        
        It is assumed that attributes starting with 'nx' or '_' are regular 
        Python attributes. All other attributes are converted to valid NXnodes,
        with class SDS, NXgroup, or a sub-class of NXgroup, depending on the
        assigned value.
        
        The internal value of the attribute name, i.e., 'nxname', is set to the 
        attribute name used in the assignment.  The parent group of the 
        attribute, i.e., 'nxgroup', is set to the parent node of the attribute.
        
        If the assigned value is a numerical (scalar or array) or string object,
        it is converted to an object of class SDS, whose attribute, 'nxdata', 
        is set to the assigned value.
        """
        if isinstance(value, NXattr) or name.startswith('nx') or name.startswith('_'):
            object.__setattr__(self, name, value)
        elif isinstance(value, NXnode):
            value.nxgroup = self
            value.nxname = name
            object.__setattr__(self, name, value)
        else:
            object.__setattr__(self, name, SDS(value=value, name=name, group=self))

    def __getitem__(self, index):
        """
        Returns a slice from the NXgroup nxsignal attribute (if it exists) as
        a new NXdata group.
        
        In most cases, the slice values are applied to the SDS nxdata array
        and returned within an SDS object with the same metadata. However,
        if the array is one-dimensional and the index start and stop values 
        are real, the nxdata array is returned with values between the limits
        set by those axis values.
        This is to allow axis arrays to be limited by their actual value. This
        real-space slicing should only be used on monotonically increasing (or
        decreasing) one-dimensional arrays.
        """
        if not self.nxsignal: 
            raise NeXusError, "No plottable signal"
        if not hasattr(self,"nxclass"):
            raise NeXusError, "Indexing not allowed for groups of unknown class"
        if isinstance(index, int):
            axes = self.nxaxes
            axes[0] = axes[0][index]
#            result = NXdata(self.nxsignal[index], axes)
#            if self.nxerrors: result.errors = self.errors[index]
        elif isinstance(index, slice):
            axes = self.nxaxes
            axes[0] = axes[0][index]
            if isinstance(index.start, float) or isinstance(index.stop, float):
                index = slice(self.nxaxes[0].index(index.start), 
                              self.nxaxes[0].index(index.stop,max=True)+1)
#                result = NXdata(self.nxsignal[index], axes)
#                if self.nxerrors: result.errors = self.errors[index]
#            else:
#                result = NXdata(self.nxsignal[index], axes)
#                if self.nxerrors: result.errors = self.errors[index]
        else:
            i = 0
            slices = []
            axes = self.nxaxes
            for ind in index:
                axes[i] = axes[i][ind]
                if isinstance(ind, slice) and \
                   (isinstance(ind.start, float) or isinstance(ind.stop, float)):
                    slices.append(slice(self.nxaxes[i].index(ind.start),
                                        self.nxaxes[i].index(ind.stop)))
                else:
                    slices.append(ind)
                i = i + 1
#            result = NXdata(self.nxsignal.__getitem__(tuple(slices)), axes)
#            if self.nxerrors: result.errors = self.errors.__getitem__(tuple(slices))
#        axes = []
#        for axis in result.nxaxes:
#            if len(axis) > 1: axes.append(axis)
#        result._setaxes(axes)
#        return result
        return None

    def _setSDS(self, value, name):
        if isinstance(value, list) or isinstance(value, tuple):
            setattr(self, name, SDS(value=array(value), name=name))
            return name
        elif isinstance(value, ndarray):
            setattr(self, name, SDS(value=value, name=name))
            return name
        elif isinstance(value, SDS):
            if value.nxname == 'unknown': value.nxname = name
            setattr(self, value.nxname, value)
            return value.nxname
        else:
            raise NeXusError, "%s: '%s' should be a Numpy array or an SDS" % (type(value), name)

    def _setaxes(self, axes):
        #print "_setaxes", signal.nxname, id(signal)
        self.nxsignal.axes = ":".join([axis.nxname for axis in axes])
        
    def _fixaxes(self, signal):
        """
        Remove length-one dimensions from plottable data
        """
        if isinstance(signal, NXlinkdata): signal = signal.nxlink
        while 1 in signal.nxdims: signal.nxdims.remove(1)
        axes = []
        for axisname in getAxes(signal.axes):
            axis = getattr(self,axisname)
            if len(axis) > 1: axes.append(axis)
        signal.axes = ":".join([axis.nxname for axis in axes])
        
    def nxinsert(self, node):
        """
        Add a valid NeXus node (SDS or NXgroup) to the group
        """
        if isinstance(node, NXnode):
            node.nxgroup = self
            object.__setattr__(self, node.nxname, node)

    def nxcomponent(self, nxclass):
        """
        Find all child nodes that have a particular class.
        """
        return [E for dummy_name,E in self.nxentries.items() if E.nxclass==nxclass]

    def nxsignals(self):
        """
        Return a dictionary of SDS's containing signal data.
        
        The key is the value of the signal attribute.
        """
        signals = {}
        for node in self.nxentries.values():
            if 'signal' in node.nxattrs:
                signals[node.signal.nxdata] = node
        return signals                

    def _signal(self):
        """
        Return the SDS containing the signal data.
        """
        for node in self.nxentries.values():
            if 'signal' in node.nxattrs and str(node.signal.nxdata) == '1':
                if 1 in node.nxdims: self._fixaxes(node)
                return self.__dict__[node.nxname]
        return None

    def _axes(self):
        """
        Return a list of SDSs containing the axes.
        """
        try:
            return [getattr(self,name) for name in getAxes(self.nxsignal.axes)]
        except KeyError:
            axes = {}
            for node in self.nxentries:
                if 'axis' in getattr(self,node).nxattrs:
                    axes[getattr(self,node).axis.nxdata] = getattr(self,node)
            return [axes[key] for key in sorted(axes.keys())]

    def _errors(self):
        """
        Return the SDS containing the signal errors.
        """
        try:
            return self.nxentries['errors']
        except KeyError:
            return None

    nxsignal = property(_signal, "Signal SDS within group")
    nxaxes = property(_axes, "List of axes within group")
    nxerrors = property(_errors, "Errors SDS within group")


class NXlink(NXnode):
    """
    NeXus linked node.
    
    The real node will be accessible by following the nxlink attribute.
    """
    nxclass = "NXlink"

    def __init__(self, target=None, name="link"):
        self.nxname = name
        self.nxgroup = None
        if isinstance(target, NXnode):
            self._target = target.nxpath
            if target.nxclass == "NXlink":
                raise NeXusError, "Cannot link to another NXlink object"
            elif target.nxclass == "SDS":
                self.__class__ = NXlinkdata                
            else:
                self.__class__ = NXlinkgroup
        else:
            self._target = target

    def __getattr__(self, key):
        try:
            if key == "nxdata": return self.nxlink.nxdata
            return self.nxlink.__dict__[key]
        except KeyError:
            raise KeyError, (key+" not in %s" % self._target)

    def __repr__(self):
        return "NXlink('%s')"%(self._target)

    def __str__(self):
        return "NXlink('%s')"%(self._target)

    def _str_tree(self, indent=0, attrs=False, recursive=False):
        if self.nxlink:
            return self.nxlink._str_tree(indent, attrs, recursive)
        else:
            return " "*indent+self.nxname+' -> '+self._target

    def _link(self):
        link = getRoot(self)
        if link:
            try:
                for level in self._target[1:].split('/'):
                    link = getattr(link, level)
                return link
            except AttributeError:
                return None
        else:
            return None

    def _attrs(self):
        return dict([(k,v) 
                     for k,v in self.nxlink.__dict__.items()
                     if isinstance(v,NXattr)])

    def _entries(self):
        return dict([(k,v) 
                     for k,v in self.nxlink.__dict__.items()
                     if isinstance(v,NXnode) and not k.startswith('nx')
                        and not k.startswith('_')])

    nxlink = property(_link, "Linked object")
    nxattrs = property(_attrs,doc="NeXus attributes for node")
    nxentries = property(_entries,doc="NeXus nodes within group")    


class NXlinkdata(NXlink, SDS):
    """
    NeXus linked SDS.
    
    The real node will be accessible by following the nxlink attribute.
    """
    def __setattr__(self, name, value):
        if name.startswith('_'):
            object.__setattr__(self, name, value)
        elif name == "nxname" or name == "nxclass" or name == "nxgroup":
            SDS.__setattr__(self, name, value)
        elif self.nxlink:
            self.nxlink.__setattr__(name, value)

    def nxget(self, offset, size, units=""):
        """
        Get a slab from the data array.

        Offsets are 0-origin.  Shape can be inferred from the data.
        Offset and shape must each have one entry per dimension.
        
        If units are specified, convert the values to the given units
        before returning them.

        This operation should be performed in a "with group.data"
        conext.

        Raises ValueError cannot convert units.

        Corresponds to NXgetslab(handle,data,offset,shape)
        """
        self.nxlink.__enter__()
        value = self.nxlink._file.getslab(offset,size)
        return self.nxlink._converter(value,units)
        self.nxlink.__exit__()
    
class NXlinkgroup(NXlink, NXgroup):
    """
    NeXus linked group.
    
    The real node will be accessible by following the nxlink attribute.
    """
    def __setattr__(self, name, value):
        if name.startswith('_'):
            object.__setattr__(self, name, value)
        elif name == "nxname" or name == "nxclass" or name == "nxgroup":
            NXgroup.__setattr__(self, name, value)
        elif self.nxlink:
            if not isinstance(value, NXnode): value = SDS(value=value, name=name)
            value.nxgroup = self
            value.nxname = name
            object.__setattr__(self, name, value)
            setattr(self.nxlink, name, value)


# File level operations
def load(filename, mode='r'):
    """
    Read a NeXus file, returning a tree of nodes
    """
    file = NeXusTree(filename,mode)
    tree = file.readfile()
    file.close()
    return tree

def save(filename, node, format='w5'):
    """
    Write a NeXus file from a tree of nodes
    """
    if node.nxclass == "NXroot":
        tree = node
#    elif node.nxclass == "NXentry":
#        tree = NXroot(node)
#    else:
#        tree = NXroot(NXentry(node))
    file = NeXusTree(filename, format)
    file.writefile(tree)
    file.close()

def tree(file):
    """
    Read and summarize the named nexus file.
    """
    nxfile = load(file)
    nxfile.nxtree()

def demo(argv):
    """
    Process command line commands in argv.  argv should contain
    program name, command, arguments, where command is one of
    the following:
        copy fromfile.nxs tofile.nxs
        ls f1.nxs f2.nxs ...
    """
    if len(argv) > 1:
        op = argv[1]
    else:
        op = 'help'
    if op == 'ls':
        for f in argv[2:]: dir(f)
    elif op == 'copy' and len(argv)==4:
        tree = load(argv[2])
        save(argv[3], tree)
    elif op == 'plot' and len(argv)==4:
        tree = load(argv[2])
        for entry in argv[3].split('.'):
            tree = getattr(tree,entry)
        tree.nxplot()
        tree._plotter.show()
            
    else:
        usage = """
usage: %s cmd [args]
    copy fromfile.nxs tofile.nxs
    ls *.nxs
    plot file.nxs entry.data
        """%(argv[0],)
        print usage

if __name__ == "__main__":
    import sys
    demo(sys.argv)

