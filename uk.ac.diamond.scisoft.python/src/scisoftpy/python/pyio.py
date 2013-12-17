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
###h this software. If not, see <http://www.gnu.org/licenses/>.
###

'''
'''

from scisoftpy.dictutils import DataHolder

io_exception = IOError

class PythonLoader(object):
    def __init__(self, name, togrey=False):
        self.ascolour = not togrey
        self.name = name
        self.load_metadata = True

    def setloadmetadata(self, load_metadata):
        self.load_metadata = load_metadata

class PythonSaver(object):
    def __init__(self, name, lower=None, upper=None, **argv):
        self.name = name
        self.lower = lower
        self.upper = upper

class NumPyLoader(PythonLoader):
    def load(self, warn=True):
        import numpy as np #@UnresolvedImport
        d = np.load(self.name)
        import os.path as _path
        n = self.name
        if _path.exists(n):
            n = _path.basename(n)
        return DataHolder([(n, d)], warn=warn)

class NumPySaver(PythonSaver):
    def save(self, data):
        import numpy as np #@UnresolvedImport
        np.save(self.name, data)

from re import compile as _compile
_begin_number = _compile(r'^[-+]?[\d]?\.?\d')

class SRSLoader(PythonLoader):
    '''
    Loads an SRS dat file and returns a dataholder object
    '''
    def load(self, warn=True):
        '''
        warn        -- if True (default), print warnings about key names

        Returns a DataHolder object
        '''

        f = open(self.name)
    
        try:
            header = False
            while True:
                l = f.readline()
                if not l:
                    raise io_exception, "End of file reached unexpectedly"
                ls = l.lstrip()
                if ls.startswith("&SRS") or ls.startswith("&DLS"):
                    header = True
                    break
                if _begin_number.match(ls):
                    break
            else:
                raise io_exception, "Not an SRS file"
    
            srstext = []
            if header:
                while True:
                    l = f.readline()
                    if not l:
                        raise io_exception, "End of file reached unexpectedly"
                    ls = l.strip()
                    if ls:
                        if ls.startswith("&END"):
                            break
                        if self.load_metadata:
                            srstext.append(ls)
                else:
                    raise io_exception, "No end tag found"
    
                l = f.readline()
                if not l:
                    raise io_exception, "End of file reached unexpectedly"
                colstext = l.strip()
                datatext = []
            else:
                colstext = None
                datatext = [ls]

            while True:
                l = f.readline()
                if not l:
                    break
                ls = l.lstrip()
                if _begin_number.match(ls):
                    datatext.append(ls)
                else:
                    break
    
            data = SRSLoader._parse_data(colstext, datatext, warn)
            metadata = SRSLoader._parse_head(srstext, warn)
    
            return DataHolder(data, metadata, warn)

        finally:
            f.close()

    @staticmethod
    def _parse_head(text, warn):
        '''
        Scan header lines and extract key/value pairs. A pair contains an equals sign
        '''
        srsmeta = []
        othermeta = []
        other = False
        mdtext = "MetaDataAtStart"
        for ls in text:
            i = ls.find(mdtext)
            if i >= 0:
                other = not other
                if other:
                    i += len(mdtext) + 1 # after right angle bracket
                    r = ls[i:].strip()
                    if len(r) > 0:
                        othermeta.append(r)
                else:
                    r = ls[:i-2].strip()
                    if len(r) > 0:
                        othermeta.append(r)
                    i += len(mdtext) + 1 # after right angle bracket
                    r = ls[i:].strip()
                    if len(r) > 0:
                        srsmeta.append(r)
                continue
            if other:
                othermeta.append(ls)
            else:
                srsmeta.append(ls)
        meta = []
        for m in srsmeta:
            ss = SRSLoader._parse_srsline(m, warn)
            for s in ss:
                meta.append(s)
        for m in othermeta:
            s = m.split('=',1)
            if len(s) == 1:
                raise io_exception, "Metadata did not contain equal sign: " + m
            meta.append((s[0], SRSLoader._parse_value(s[1])))
        return meta

    @staticmethod
    def _parse_srsline(line, warn):
        '''
        Scan a SRS header line of comma-separated key/value pairs
        '''
        meta = []
        if line.startswith("SRS"):
            ki = 0
            l = len(line)
            while ki < l:
                kf = line.find('=', ki)
                if kf > 0:
                    k = line[ki:kf]
                    vi = kf+1
                    nc = line.find(',', vi)
                    if nc < vi:
                        raise io_exception, "No comma found: " + line[vi:]
                    qi = line.find('\'', vi)
                    if qi > 0 and qi < nc:
                        if qi != vi:
                            raise io_exception, "Quoted string does not follow equal sign: " + line[qi:]
                        # inside quoted string
                        qf = line.find('\'', qi+1)
                        if qf > 0 and qf != (nc-1):
                            raise io_exception, "A comma does not follow a quoted string: " + line[qi:]

                    vf = nc
                    v = line[vi:vf]
                    meta.append((k, SRSLoader._parse_value(v)))
                    ki = nc+1
                else:
                    raise io_exception, "No equal sign on line: " + line[ki:]
        else:
            s = line.split('=',1)
            if len(s) == 1:
                raise io_exception, "Metadata did not contain equal sign: " + line
            meta.append((s[0], SRSLoader._parse_value(s[1])))
        return meta

    @staticmethod
    def _parse_data(cols, text, warn):
        '''
        Convert to all data to dictionary. Keys are column headers and values are
        1D NumPy arrays 
        '''
        import re
        cs_regex = re.compile('\s+')

        if cols is None:
            # use first line
            r = cs_regex.split(text[0].strip())
            lc = len(r)
            from math import log10, ceil
            p = ceil(log10(lc))
            fmt = 'Column_%%0%dd' % p
            cols = [ fmt % (i+1,) for i in range(lc) ]
        else:
            if cols.count('\t') > 0: # columns separated by tabs
                cols = cols.split('\t')
            else:
                cols = cs_regex.split(cols)
            lc = len(cols)
        data = [[] for dummy in cols]
        for t in text:
            r = cs_regex.split(t.strip())
            lr = len(r)
            if lr > lc:
                if warn:
                    print 'Long row!'
                lr = lc
            for i in range(lr):
                data[i].append(SRSLoader._parse_value(r[i]))

            if lr < lc:
                if warn:
                    print 'Short row!'
                for i in range(lr, lc):
                    data[i].append(0)

        from pycore import array as parray
        return [(c, parray(d)) for c, d in zip(cols, data)]

    @staticmethod
    def _parse_value(text):
        '''
        Convert to integer, float or string (strips outer quotes)
        '''
        try:
            v = int(text)
        except ValueError:
            try:
                v = long(text)
            except ValueError:
                try:
                    v = float(text)
                except ValueError:
                    if text.startswith('\''):
                        if text.endswith('\''):
                            v = text[1:-1]
                        else:
                            raise io_exception, "No matching single quotes in string: " + text
                    elif text.startswith('"'):
                        if text.endswith('"'):
                            v = text[1:-1]
                        else:
                            raise io_exception, "No matching double quotes in string: " + text
                    else:
                        v = text
        return v

class DLSLoader(SRSLoader):
    '''
    Loads a DLS dat file and returns a dataholder object
    '''
    def load(self, warn=True):
        '''
        warn        -- if True (default), print warnings about key names

        Returns a DataHolder object
        '''

        f = open(self.name)
    
        try:
            hdrtext = []
            while True:
                l = f.readline()
                if not l:
                    raise io_exception, "End of file reached unexpectedly"
                ls = l.strip()
                if ls:
                    if _begin_number.match(ls):
                        break
                    if ls.startswith("#"):
                        if self.load_metadata:
                            ls = ls[1:].strip()
                            if ls:
                                hdrtext.append(ls)
            else:
                raise io_exception, "No end tag found"

            colstext = hdrtext.pop()[1:].strip() if hdrtext else None
            datatext = [ls]
            while True:
                l = f.readline()
                if l:
                    ls = l.strip()
                    if ls.startswith("#"):
                        if self.load_metadata:
                            ls = ls[1:].strip()
                            if ls:
                                hdrtext.append(ls)
                    elif _begin_number.match(ls):
                        datatext.append(ls)
                else:
                    break
    
            data = self._parse_data(colstext, datatext, warn)
            metadata = self._parse_head(hdrtext, warn)
    
            return DataHolder(data, metadata, warn)

        finally:
            f.close()

    @staticmethod
    def _parse_head(text, warn):
        '''
        Scan header lines and extract key/value pairs. A pair contains a colon
        but value can be more key/value pairs containing an equals sign, separated by semi-colons
        '''
        dlsmeta = []
        othermeta = []
        for l in text:
            r = l.split(':')
            if len(r) == 1:
                othermeta.append(r)
            else:
                if len(r) > 2 and warn:
                    print 'Line has more than one colon:', l
                dlsmeta.append((r[0], l[len(r[0])+1:].strip()))
        return dlsmeta

import pycore as _core

import sys

try:
    import Image as _im #@UnresolvedImport @UnusedImport
except:
    try: # for the Pillow fork
        from PIL import Image as _im  # @UnresolvedImport @Reimport
    except:
        print >> sys.stderr, "Could not import python image library"

from pycore import ndarrayRGB as _RGB

class ImageLoader(PythonLoader):
    def load(self, warn=True):
        if _im is None:
            raise NotImplementedError
        im = _im.open(self.name)
        if im.mode == 'RGB':
            if self.ascolour:
                # convert to an rgb dataset
                d = _core.asarray(im, dtype=_core.int16).view(_RGB)
            else:
                im = im.convert('L')
                d = _core.asarray(im)
        else:
            d = _core.asarray(im)

        import os.path as _path
        n = self.name
        if _path.exists(n):
            n = _path.basename(n)

        return DataHolder([(n, d)], warn=warn)

class ImageSaver(PythonSaver):
    def save(self, data):
        if _im is None:
            raise NotImplementedError

        d = data[0]
        if isinstance(d, _RGB):
            s = list(d.shape)
            s.append(3)
#            c = _core.ndarray(s, buffer=d.data, dtype=_core._uint8)
            c = _core.zeros(s, dtype=_core._uint8)
#            print d.red
            c[...,0] = d.get_red(dtype=_core._uint8)
            c[...,1] = d.get_green(dtype=_core._uint8)
            c[...,2] = d.get_blue(dtype=_core._uint8)
            d = c
        im = _im.fromarray(d)
        im.save(self.name)


class TIFFSaver(PythonSaver):
    def save(self, data):
        if _im is None:
            raise NotImplementedError

        d = data[0]
        if isinstance(d, _RGB):
            s = list(d.shape)
            s.append(3)
#            c = _core.ndarray(s, buffer=d.data, dtype=_core._uint8)
            c = _core.zeros(s, dtype=_core._uint8)
#            print d.red
            c[...,0] = d.get_red(dtype=_core._uint8)
            c[...,1] = d.get_green(dtype=_core._uint8)
            c[...,2] = d.get_blue(dtype=_core._uint8)
            d = c
        try:
            im = _im.fromarray(d)
        except:
            if d.dtype == _core._uint16: # trap a known PIL 1.1.7 TIFF bug
                im = _im.frombuffer("I;16", tuple(reversed(d.shape)), d.data, 'raw', "I;16", 0, 1)
            else:
                raise
        im.save(self.name)

# capture all error messages
import os
orig_fd = sys.__stderr__.fileno()
saved = os.dup(orig_fd) #@UndefinedVariable
sys.__stderr__.flush()
fd = os.open(os.devnull, os.O_RDWR)
os.dup2(fd, orig_fd) #@UndefinedVariable

try:
    import scisoftpy._external.tifffile as _tf
except:
    print >> sys.stderr, "Could not import tiff file package"
finally:
    os.dup2(saved, orig_fd)#@UndefinedVariable
    os.close(fd)

class TIFFfileLoader(PythonLoader):
    def load(self, warn=True):
        if _tf is None:
            raise NotImplementedError

        data = []
        if self.load_metadata:
            metadata = dict()
        else:
            metadata = None

        t = _tf.TIFFfile(self.name)
        for i, p in enumerate(t.pages):
            d = p.asarray()
            if p.is_rgb:
                # convert to an rgb dataset
                d = _core.asarray(d, dtype=_core.int16).view(_RGB)

                if not self.ascolour:
                    d = d.get_grey()

            data.append(("image%d" % i, d))
            if self.load_metadata:
                for k,v in p.tags.items():
                    metadata[k] = v.value

        if len(data) < 1:
            pass
        return DataHolder(data, metadata, warn)

if _tf is None:
    TIFFLoader = ImageLoader
else:
    TIFFLoader = TIFFfileLoader

try:
    import pycbf as _cbf  # @UnresolvedImport
    import numpy  # @UnresolvedImport
    class CBFLoader(PythonLoader):
        def getvalue(self, h):
            vtype = h.get_typeofvalue()
            if "bnry" not in vtype:
                return h.get_value()
            try:
                (_compression, _binaryid, _elsize, _elsigned, \
                    _elunsigned, _elements, _minelement, _maxelement, \
                    _byteorder, dimfast, dimmid, dimslow, _padding) = \
                    h.get_integerarrayparameters_wdims()
                isreal = False
            except:
                try:
                    (_compression, _binaryid, _elsize, _elements, \
                        _byteorder, dimfast, dimmid, dimslow, _padding) = \
                        h.get_doublearrayparameters_wdims()
                    isreal = True
                except:
                    return h.get_value()
                    
            if dimfast == 0:
                dimfast = 1
            if dimmid == 0:
                dimmid = 1
            if dimslow == 0:
                dimslow = 1
#             print "compression: ", _compression
#             print "binaryid", _binaryid
#             print "elsize", _elsize
#             if not isreal:
#                 print "elsigned", _elsigned
#                 print "elunsigned",_elunsigned
#                 print "minelement", _minelement
#                 print "maxelement", _maxelement
#             print "elements", _elements
#             print "byteorder", _byteorder
#             print "dimfast", dimfast
#             print "dimmid", dimmid
#             print "dimslow",dimslow
#             print "padding", _padding
            if isreal:
                s = h.get_doublearray_as_string()
                print type(s)
                d = numpy.frombuffer(s, numpy.float64)
            else:
                s = h.get_integerarray_as_string()
                print type(s)
                d = numpy.frombuffer(s, numpy.uint32)
#             print d.shape
#             print d[0:10], d[d.shape[0]/2], d[-1]
            d.shape = (dimfast, dimmid)
            return d

        def load(self, warn=True):
            data = []
            metadata = []
            try:
                h = _cbf.cbf_handle_struct()
                h.read_widefile(self.name, _cbf.MSG_DIGEST)
                h.rewind_datablock()
                for nd in range(h.count_datablocks()):
                    h.select_datablock(nd)
#                     db_name = h.datablock_name()
#                     print "DBl: %d, %s" % (nd, db_name)
                    h.rewind_category()
                    for nc in range(h.count_categories()):
                        h.select_category(nc)
#                         ct_name = h.category_name()
#                         print "  Cat: %d, %s" % (nc, ct_name)
                        h.rewind_column()
                        colnames = []
                        for nv in range(h.count_columns()):
                            h.select_column(nv)
                            cl_name = h.column_name()
                            colnames.append(cl_name)
#                             print "    Col: %d, %s" % (nv, cl_name)
                        h.rewind_row()
                        for nh in range(h.count_rows()):
                            h.select_row(nh)
                            h.rewind_column()
                            for nv in range(h.count_columns()):
                                h.select_column(nv)
                                v = self.getvalue(h)
#                                 print "    %d %d: " % (nh, nv), v
                                item = "%s-%d" % (colnames[nv], nh), v
                                if isinstance(v, numpy.ndarray):
                                    data.append(item)
                                else:
                                    metadata.append(item)
            finally:
                del(h)
            return DataHolder(data, metadata, warn)
except:
    CBFLoader = None

PNGLoader = ImageLoader
JPEGLoader = ImageLoader
TIFFLoader = TIFFLoader

ADSCLoader = None
CrysLoader = None
MARLoader = TIFFLoader
PilLoader = TIFFLoader
BinaryLoader = None
TextLoader = None
XMapLoader  = None
PGMLoader = ImageLoader

_pngsave = ImageSaver
_jpegsave = ImageSaver
_tiffsave = TIFFSaver
_imgsave = None
_rawtxtsave = None
_rawbinsave = None
_pngscaledsave = None
_jpegscaledsave = None

#from pycore import asDatasetDict, asDatasetList, toList

try:
    from pynxio import NXLoader
    from pyhdf5io import HDF5Loader
except:
    NXLoader = None
    HDF5Loader = None

input_formats = { "png": PNGLoader, "gif": ImageLoader,
               "jpeg": JPEGLoader,
               "tiff": TIFFLoader,
               "adsc": ADSCLoader, "img": ADSCLoader,
               "cbf": CBFLoader, "crys": CrysLoader,
               "mar": MARLoader, "mccd": MARLoader,
               "pil": PilLoader,
               "pgm": PGMLoader,
               "srs": SRSLoader,
               "dls": DLSLoader,
               "binary": BinaryLoader, "xmap": XMapLoader,
               "nx": NXLoader, "hdf5": HDF5Loader,
               "text": TextLoader,
               "npy": NumPyLoader
               }
colour_loaders  = [ PNGLoader, ImageLoader, JPEGLoader, TIFFLoader ]
loaders = [ PNGLoader, ADSCLoader, CrysLoader, MARLoader, CBFLoader, XMapLoader, BinaryLoader, SRSLoader, PGMLoader, TextLoader ]

output_formats = { "png": _pngsave, "gif": _imgsave, "jpeg": _jpegsave, "tiff": _tiffsave, "text": _rawtxtsave,
              "binary": _rawbinsave, "npy": NumPySaver }

scaled_output_formats = { "png": _pngscaledsave, "jpeg": _jpegscaledsave }

