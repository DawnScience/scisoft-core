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


from uk.ac.diamond.scisoft.analysis.io import PNGLoader as _pngload
from uk.ac.diamond.scisoft.analysis.io import PNGScaledSaver as _pngscaledsave
from uk.ac.diamond.scisoft.analysis.io import JPEGLoader as _jpegload
from uk.ac.diamond.scisoft.analysis.io import JPEGSaver as _jpegsave
from uk.ac.diamond.scisoft.analysis.io import JPEGScaledSaver as _jpegscaledsave

from java.lang import System as _system #@UnresolvedImport
from java.io import PrintStream as _pstream #@UnresolvedImport
from java.io import OutputStream as _ostream #@UnresolvedImport
class _NoOutputStream(_ostream):
    def write(self, b, off, length): pass

try:
    from uk.ac.diamond.scisoft.analysis.io import TIFFImageLoader as _tiffload
except:
    import sys
    print >> sys.stderr, "Could not import TIFF loader"
    _tiffload = None

from uk.ac.diamond.scisoft.analysis.io import TIFFImageSaver as _tiffsave
from uk.ac.diamond.scisoft.analysis.io import JavaImageLoader as _imgload
from uk.ac.diamond.scisoft.analysis.io import JavaImageSaver as _imgsave
from uk.ac.diamond.scisoft.analysis.io import ADSCImageLoader as _adscload

try:
    from uk.ac.diamond.scisoft.analysis.io import CBFLoader as _cbfload
except:
    import sys #@Reimport
    print >> sys.stderr, "Could not import CBF loader"
    print >> sys.stderr, "Problem with path for dynamic/shared library or product bundling"
    _cbfload = None

from uk.ac.diamond.scisoft.analysis.io import CrysalisLoader as _crysload
from uk.ac.diamond.scisoft.analysis.io import MARLoader as _marload
from uk.ac.diamond.scisoft.analysis.io import MAR345Loader as _mar345load
from uk.ac.diamond.scisoft.analysis.io import PilatusTiffLoader as _ptiffload
from uk.ac.diamond.scisoft.analysis.io import ExtendedSRSLoader as _srsload
from uk.ac.diamond.scisoft.analysis.io import PilatusEdfLoader as _pilatusEdfLoader
from uk.ac.diamond.scisoft.analysis.io import RawBinarySaver as _rawbinsave
from uk.ac.diamond.scisoft.analysis.io import RawBinaryLoader as _rawbinload
from uk.ac.diamond.scisoft.analysis.io import RawTextSaver as _rawtxtsave
from uk.ac.diamond.scisoft.analysis.io import RawTextLoader as _rawtxtload

from uk.ac.diamond.scisoft.analysis.io import XMapLoader as _xmapload
from uk.ac.diamond.scisoft.analysis.io import DatLoader as _dlsdatload
from uk.ac.diamond.scisoft.analysis.io import NumPyFileLoader as _numpyload
from uk.ac.diamond.scisoft.analysis.io import NumPyFileSaver as _numpysave
from uk.ac.diamond.scisoft.analysis.io import RAxisImageLoader as _raxisload
from uk.ac.diamond.scisoft.analysis.io import PgmLoader as _pgmload

from uk.ac.diamond.scisoft.analysis.io import LoaderFactory as _loader_factory

from org.eclipse.dawnsci.analysis.api.io import ScanFileHolderException as io_exception

from uk.ac.diamond.scisoft.analysis.io import DataHolder as _jdataholder
from uk.ac.diamond.scisoft.analysis.io import MetaDataAdapter as _jmetadata

from jycore import asDatasetList, _jinput#, asDatasetDict, toList

from scisoftpy.dictutils import DataHolder

from uk.ac.diamond.scisoft.analysis.io import HDF5Loader as _hdf5loader
from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5File as _hdf5file
from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5Group as _hdf5group

class h5manager(object):
    '''This holds a HDF5 tree and manages access to it. This provides
    dictionary-like access to lists of datasets
    '''
    def __init__(self, tree):
        '''Arguments:
        tree -- HDF tree
        '''
        if isinstance(tree, _hdf5file):
            self.file = tree
            self.grp = self.file.getGroup()
        elif isinstance(tree, _hdf5group):
            self.file = None
            self.grp = tree
        else:
            raise ValueError, "Tree not a hdf5 file or hdf5 group"

    def gettree(self):
        if self.file is None:
            t = _hdf5file(-1, "FakePlasticTree.h5")
            t.setGroup(self.grp)
            return t
        return self.file

    def __getitem__(self, key):
        '''Return a list of datasets in tree whose names match given key'''
        return asDatasetList(self.grp.getDatasets(key))

def loadnexus(name):
    '''Load a HDF5 file and return a HDF5 tree manager'''
    import warnings
    warnings.warn("This will be deprecated in the next version", PendingDeprecationWarning)
    h5loader = _hdf5loader(name)
    return h5manager(h5loader.loadTree())


from jyhdf5io import HDF5Loader
from jynxio import NXLoader

class BareJavaLoader(object):
    def load(self, warn=True):
        # capture all error messages
        oldErr = _system.err
        _system.setErr(_pstream(_NoOutputStream()))
        try:
            jdh = self._loadFile()
        finally:
            _system.setErr(oldErr)

        data = asDatasetList(jdh.getList())
        names = jdh.getNames()
        basenames = []
        from os import path as _path
        for n in names: # remove bits of path so sanitising works
            if _path.exists(n):
                basenames.append(_path.basename(n))
            else:
                basenames.append(n)

        if len(data) != len(basenames):
            raise io_exception, "Number of names does not match number of datasets"

        metadata = None
        if self.load_metadata:
            meta = jdh.getMetadata()
            if meta:
                mnames = meta.metaNames
                if mnames:
                    metadata = [ (k, meta.getMetaValue(k)) for k in mnames ]

        return DataHolder(zip(basenames, data), metadata, warn)

    def setloadmetadata(self, load_metadata):
        self.load_metadata = load_metadata
        self._setLoadMetadata(load_metadata)

class JavaLoader(BareJavaLoader):
    def _loadFile(self):
        return self.loadFile()

    def _setLoadMetadata(self, load_metadata):
        self.setLoadMetadata(load_metadata)

class SRSLoader(JavaLoader, _srsload):
    def __init__(self, *arg):
        _srsload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class PilatusEdfLoader(JavaLoader, _pilatusEdfLoader):
    def __init__(self, *arg):
        _pilatusEdfLoader.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class PNGLoader(JavaLoader, _pngload):
    def __init__(self, *arg):
        _pngload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class JPEGLoader(JavaLoader, _jpegload):
    def __init__(self, *arg):
        _jpegload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class ImageLoader(JavaLoader, _imgload):
    def __init__(self, *arg):
        _imgload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

if _tiffload is None:
    TIFFLoader = None
else:
    class TIFFLoader(JavaLoader, _tiffload):
        def __init__(self, *arg):
            _tiffload.__init__(self, *arg) #@UndefinedVariable
            self.load_metadata = True

class ADSCLoader(JavaLoader, _adscload):
    def __init__(self, *arg):
        _adscload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

if _cbfload is None:
    CBFLoader = None
else:
    try:
        _c = _cbfload()
    except:
        CBFLoader = None
        import sys #@Reimport
        print >> sys.stderr, "Problem creating CBF loader so no CBF support"
    else:
        del _c
        class CBFLoader(JavaLoader, _cbfload):
            def __init__(self, *arg):
                _cbfload.__init__(self, *arg) #@UndefinedVariable
                self.load_metadata = True

class CrysLoader(JavaLoader, _crysload):
    def __init__(self, *arg):
        _crysload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class MARLoader(JavaLoader, _marload):
    def __init__(self, *arg):
        _marload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class MAR345Loader(JavaLoader, _mar345load):
    def __init__(self, *arg):
        _mar345load.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class PilLoader(JavaLoader, _ptiffload):
    def __init__(self, *arg):
        _ptiffload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class BinaryLoader(JavaLoader, _rawbinload):
    def __init__(self, *arg):
        _rawbinload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class TextLoader(JavaLoader, _rawtxtload):
    def __init__(self, *arg):
        _rawtxtload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class XMapLoader(JavaLoader, _xmapload):
    def __init__(self, *arg):
        _xmapload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class DLSLoader(JavaLoader, _dlsdatload):
    def __init__(self, *arg):
        _dlsdatload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class NumPyLoader(JavaLoader, _numpyload):
    def __init__(self, *arg):
        _numpyload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class RAxisLoader(JavaLoader, _raxisload):
    def __init__(self, *arg):
        _raxisload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class PGMLoader(JavaLoader, _pgmload):
    def __init__(self, *arg):
        _pgmload.__init__(self, *arg) #@UndefinedVariable
        self.load_metadata = True

class LoaderFactoryDelegate(BareJavaLoader):
    def __init__(self, *arg):
        self.name = arg[0]
        self.load_metadata = True

    def _loadFile(self):
        return _loader_factory.getData(self.name, self.load_metadata, None)

    def _setLoadMetadata(self, load_metadata):
        pass

# register extra loaders as workaround for Jython not being OSGI
import os as _os
_lfe = _os.environ.get("LOADER_FACTORY_EXTENSIONS")
if _lfe:
    _extensions = _lfe.split("|")
    import java.lang.Class as _Class
    for e in _extensions:
        if e:
            bits = e.split(":")
            if bits[2] == "0":
                _loader_factory.registerLoader(bits[0], _Class.forName(bits[1]), 0)
            else:
                _loader_factory.registerLoader(bits[0], _Class.forName(bits[1]))

input_formats = { "png": PNGLoader, "gif": ImageLoader,
               "jpeg": JPEGLoader,
               "tiff": TIFFLoader,
               "adsc": ADSCLoader, "img": ADSCLoader,
               "cbf": CBFLoader, "crys": CrysLoader,
               "mar": MARLoader, "mccd": MARLoader,
               "mar3450": MAR345Loader, "pck3450": MAR345Loader,
               "pil": PilLoader,
               "srs": SRSLoader,
               "binary": BinaryLoader, "xmap": XMapLoader,
               "npy": NumPyLoader,
               "dls": DLSLoader,
               "osc": RAxisLoader,
               "pgm": PGMLoader,
               "nx": NXLoader,
               "hdf5": HDF5Loader,
               "edf": PilatusEdfLoader,
               "text": TextLoader
               }
fallback_loader = LoaderFactoryDelegate
colour_loaders  = [ PNGLoader, ImageLoader, JPEGLoader, TIFFLoader ]
loaders = [ fallback_loader, ImageLoader, ADSCLoader, CrysLoader, MARLoader, CBFLoader, XMapLoader, BinaryLoader, SRSLoader, PilatusEdfLoader, PGMLoader, HDF5Loader ]

class _Metadata(_jmetadata):
    def __init__(self, metadata):
        self.mdata = metadata

    def getMetaNames(self):
        return self.mdata.keys()

    def getMetaValue(self, key):
        return self.mdata.get(key)

class JavaSaver(object):
    @classmethod
    def tojava(cls, dataholder):
        '''
        Make a java data holder 
        '''
        jdh = _jdataholder()
        for k,v in dataholder.items():
            if k != "metadata":
                v = _jinput(v)
                jdh.addDataset(k, v)

        if "metadata" in dataholder:
            md = dict()
            for k, v in dataholder.metadata.items():
                v = _jinput(v)
                md[k] = v
            jdh.setMetadata(_Metadata(md))
        return jdh

    def save(self, dataholder):
        jdh = JavaSaver.tojava(dataholder)
        self.saveFile(jdh)

class PNGSaver(JavaSaver, _imgsave):
    def __init__(self, name, signed, bits):
        if bits is None:
            bits = 16
        _imgsave.__init__(self, name, "png", bits, True) #@UndefinedVariable

class ImageSaver(JavaSaver, _imgsave):
    def __init__(self, name, signed, bits):
        _imgsave.__init__(self, name, "gif", 8, True) #@UndefinedVariable

class JPEGSaver(JavaSaver, _jpegsave):
    def __init__(self, name, signed, bits):
        _jpegsave.__init__(self, name) #@UndefinedVariable

class TIFFSaver(JavaSaver, _tiffsave):
    def __init__(self, name, signed, bits):
        if bits is None:
            bits = 16
        _tiffsave.__init__(self, name, bits, not signed) #@UndefinedVariable

class TextSaver(JavaSaver, _rawtxtsave):
    def __init__(self, name, signed, bits):
        _rawtxtsave.__init__(self, name) #@UndefinedVariable

class BinarySaver(JavaSaver, _rawbinsave):
    def __init__(self, name, signed, bits):
        _rawbinsave.__init__(self, name) #@UndefinedVariable

class NumPySaver(JavaSaver, _numpysave):
    def __init__(self, name, signed, bits):
        _numpysave.__init__(self, name) #@UndefinedVariable

output_formats = { "png": PNGSaver, "gif": ImageSaver, "jpeg": JPEGSaver, "tiff": TIFFSaver, "text": TextSaver,
              "binary": BinarySaver, "npy": NumPySaver }

class ScaledPNGSaver(JavaSaver, _pngscaledsave):
    def __init__(self, *arg):
        _pngscaledsave.__init__(self, *arg) #@UndefinedVariable

class ScaledJPEGSaver(JavaSaver, _jpegscaledsave):
    def __init__(self, *arg):
        _jpegscaledsave.__init__(self, *arg) #@UndefinedVariable

scaled_output_formats = { "png": ScaledPNGSaver, "jpeg": ScaledJPEGSaver }

