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
    def __init__(self, name, lower=None, upper=None):
        self.name = name
        self.lower = lower
        self.upper = upper

class SRSLoader(PythonLoader):
    '''
    Loads an SRS dat file and returns a dataholder object
    '''
    def load(self):
        '''
        Returns a DataHolder object
        '''

        f = open(self.name)
    
        try:
            while True:
                l = f.readline()
                if not l:
                    raise io_exception, "End of file reached unexpectedly"
                ls = l.lstrip()
                if ls.startswith("&SRS"):
                    break
            else:
                raise io_exception, "Not an SRS file"
    
            srstext = []
            while True:
                l = f.readline()
                if not l:
                    raise io_exception, "End of file reached unexpectedly"
                ls = l.strip()
                if ls:
                    if ls.startswith("&END"):
                        break
                    if self.load_metadata:
                        srstext.append(l)
            else:
                raise io_exception, "No end tag found"
    
            l = f.readline()
            if not l:
                raise io_exception, "End of file reached unexpectedly"
            colstext = l.strip()
            datatext = []
            while True:
                l = f.readline()
                if l:
                    datatext.append(l)
                else:
                    break
    
            data = self._parse_data(colstext, datatext)
            metadata = self._parse_head(srstext)
    
            return DataHolder(data, metadata)

        finally:
            f.close()

    @staticmethod
    def _parse_head(text):
        '''
        Scan header lines and extract key/value pairs. A pair contains an equals sign
        '''
        srsmeta = []
        othermeta = []
        other = False
        mdtext = "MetaDataAtStart"
        for l in text:
            ls = l.strip()
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
            ss = SRSLoader._parse_srsline(m)
            for s in ss:
                meta.append(s)
        for m in othermeta:
            s = m.split('=',1)
            if len(s) == 1:
                raise io_exception, "Metadata did not contain equal sign: " + m
            meta.append((s[0], SRSLoader._parse_value(s[1])))
        return meta

    @staticmethod
    def _parse_srsline(line):
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

        return meta

    @staticmethod
    def _parse_data(cols, text):
        '''
        Convert to all data to dictionary. Keys are column headers and values are
        1D NumPy arrays 
        '''
        cols = cols.split('\t')
        lc = len(cols)
        data = [[] for dummy in cols]
        for t in text:
            r = t.split('\t')
            for i,v in enumerate(r):
                data[i].append(SRSLoader._parse_value(v))
            lr = len(r)
            if lr < lc:
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

import pycore as _core

import sys

try:
    import Image as _im #@UnresolvedImport
except:
    print >> sys.stderr, "Could not import python image library"

class ImageLoader(PythonLoader):
    def load(self):
        if _im is None:
            raise NotImplementedError
        im = _im.open(self.name)
        if im.mode == 'RGB':
            if self.ascolour:
                # convert to an rgb dataset
                from pycore import ndarrayRGB
                d = _core.asarray(im, dtype=_core.int16).view(ndarrayRGB)
            else:
                im = im.convert('L')
                d = _core.asarray(im)
        else:
            d = _core.asarray(im)

        from os import path as _path
        n = self.name
        if _path.exists(n):
            n = _path.basename(n)

        return DataHolder([(n, d)])

class ImageSaver(PythonSaver):
    def save(self, data):
        if _im is None:
            raise NotImplementedError

        im = _im.fromarray(data[0])
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
    def load(self):
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
                from pycore import ndarrayRGB
                d = _core.asarray(d, dtype=_core.int16).view(ndarrayRGB)

                if not self.ascolour:
                    d = d.get_grey()

            data.append(("image%d" % i, d))
            if self.load_metadata:
                for k,v in p.tags.items():
                    metadata[k] = v.value

        if len(data) < 1:
            pass
        return DataHolder(data, metadata)

if _tf is None:
    LibTIFFLoader = ImageLoader
else:
    LibTIFFLoader = TIFFfileLoader

PNGLoader = ImageLoader
JPEGLoader = ImageLoader
TIFFLoader = LibTIFFLoader

ADSCLoader = None
CBFLoader = None
CrysLoader = None
MARLoader = LibTIFFLoader
PilLoader = LibTIFFLoader
RawLoader = None
XMapLoader  = None

_pngsave = ImageSaver
_jpegsave = ImageSaver
_tiffsave = ImageSaver
_imgsave = None
_rawtextsave = None
_rawbinsave = None
_pngscaledsave = None
_jpegscaledsave = None

#from pycore import asDatasetDict, asDatasetList, toList

from pynxio import NXLoader
from pyhdf5io import HDF5Loader

input_formats = { "png": PNGLoader, "gif": ImageLoader,
               "jpeg": JPEGLoader,
               "tiff": TIFFLoader,
               "adsc": ADSCLoader, "img": ADSCLoader,
               "cbf": CBFLoader, "crys": CrysLoader,
               "mar": MARLoader, "mccd": MARLoader,
               "pil": PilLoader,
               "srs": SRSLoader,
               "binary": RawLoader, "xmap": XMapLoader,
               "nx": NXLoader, "hdf5": HDF5Loader
               }
colour_loaders  = [ PNGLoader, ImageLoader, JPEGLoader, TIFFLoader ]
loaders = [ PNGLoader, ADSCLoader, CrysLoader, MARLoader, CBFLoader, XMapLoader, RawLoader, SRSLoader ]

output_formats = { "png": _pngsave, "gif": _imgsave, "jpeg": _jpegsave, "tiff": _tiffsave, "text": _rawtextsave,
              "binary": _rawbinsave }

scaled_output_formats = { "png": _pngscaledsave, "jpeg": _jpegscaledsave }

