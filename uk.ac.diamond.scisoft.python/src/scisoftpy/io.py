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

'''
I/O  package
'''

import os
if os.name == 'java':
    import jython.jycore as _core #@UnusedImport
    import jython.jyio as _io #@UnusedImport
    loadnexus = _io.loadnexus
else:
    import python.pycore as _core #@Reimport
    import python.pyio as _io #@Reimport

_asIterable = _core.asIterable
_toList = _core.toList
#_asDDict = _core.asDatasetDict
#_asDList = _core.asDatasetList
_iformats = _io.input_formats 
_colourclasses = _io.colour_loaders
_loaders = _io.loaders
_oformats = _io.output_formats
_soformats = _io.scaled_output_formats
_ioexception = _io.io_exception
_srsload = _io.SRSLoader

from dictutils import DataHolder

_extra_suffices = { "jpg" : "jpeg", "tif" : "tiff", "dat" : "srs", "h5" : "hdf5", "nxs" : "nx" }


def _findsuffix(name, formats):
    bits = name.split('.')
    if len(bits) > 1:
        suffix = bits[-1].lower()
        if suffix in formats:
            return suffix
        if suffix in _extra_suffices:
            return _extra_suffices[suffix]
    return None

def load(name, format=None, formats=None, withmetadata=True, ascolour=False, **kwarg):
    '''Load a file and return a list of datasets (or a dictionary of datasets) and
    optionally a dictionary of metadata items

    format (or formats) -- list of formats to try. Supported input formats:
        png, gif, jpeg, tiff
        adsc, crysalis, mar, pilatus, ocs -> image detectors
        cbf
        xmap
        srs
        binary -> raw
        dls (DLS dat format, not SRS - more like gnuplot)
        edf
        npy (NumPy)
        hdf5 -> returns a HDF5 tree
        nx   -> returns a NeXus tree
    '''
    try:
        f = open(name)
        f.close()
    except:
        raise ValueError, 'File %s does not exist' % name

    lformats = None
    if formats is not None:
        lformats = _toList(formats)
    if lformats is None:
        if format is not None:
            lformats = _toList(format)
    else:
        if format is not None:
            lformats.extend(_toList(format))
        
    if lformats is None:
        # parse name to find extension and match with loader
        suf = _findsuffix(name, _iformats)
        if suf:
            lformats = [suf]

    if lformats: # remove unsupported
        for f in lformats:
            if f not in _iformats:
                lformats.remove(f)

    lfh = None
    loaders = None

    if not lformats:
        loaders = _loaders
    else:
        loaders = [ _iformats[f] for f in lformats ]

    if loaders:
        for l in loaders:
            if not l:
                continue

            try:
                if l in _colourclasses:
                    ldr = l(name, not ascolour)
                else:
                    ldr = l(name)
                ldr.setloadmetadata(withmetadata)
                lfh = ldr.load()
                break
            except _ioexception, e:
                print e
            except:
                import sys
                print >> sys.stderr, 'Unexpected exception raised: ', sys.exc_info()

    if lfh is None:
        raise IOError, "Cannot load file"

    return lfh

def save(name, data, format=None, range=(), autoscale=False): #@ReservedAssignment
    '''Save a (list of) datasets with optional scaling range

    Supported output formats:
        png, gif, jpeg, tiff
        binary -> raw
        text -> raw
        npy
    '''
    saver = None
    try:
        if len(range) > 0 or autoscale:
            if not autoscale:
                if len(range) != 2:
                    raise ValueError, "Range has to be a pair of limits (lower, upper)"
                if range[0] >= range[1]:
                    raise ValueError, "Given minimum must be less than maximum"
            if format is None:
                format = _findsuffix(name, _soformats)
            print "scaled save format", format
            sclass = _soformats[format]
            if autoscale:
                saver = sclass(name)
            else:
                saver = sclass(name, range[0], range[1])
        else:
            if format is None:
                format = _findsuffix(name, _oformats)
            print "save format", format, "as", name
            sclass = _oformats[format]
            if sclass is None:
                raise ValueError, "Format not supported"
            saver = sclass(name)
    except KeyError:
        raise ValueError, "Format not supported"

    if isinstance(data, DataHolder):
        dh = data
    else:
        dl = []
        if format == "binary":
            for d in _asIterable(data):
                n = getattr(d, 'name', 'data')
                dl.append((n, d))
        else:
            for d in _toList(data):
                n = getattr(d, 'name', 'data')
                dl.append((n, d))
        dh = DataHolder(dl)

    saver.save(dh)


import os as _os
_path = _os.path

#from scisoftpy import ndarraywrapped as _npwrapped

class srsrun(DataHolder):
    '''Represent a run from an SRS file'''
    def __init__(self, run, datadir=None, ending=".dat"):
        '''Specify a run number (or file name) and data directory

        Arguments:
        run -- integer (negative values mean relative to last run number) or filename
        datadir -- data directory
        ending -- file name ending, defaults to ".dat"
        '''
#        try:
#            self.run = int(run)
#            if run <= 0:
#                try:
#                    import gda.data.NumTracker as NumTracker #@UnresolvedImport
#                    self.run += NumTracker().getCurrentFileNumber()
#                except ImportError:
#                    print "No gda configuration access so cannot support negative numbers"
#                    raise
#
#            if datadir is None:
#                datadir = self._getgdadir()
#            self.srsfile = _path.join(datadir, self.run + ending)
#            if not _os.access(self.srsfile, _os.R_OK):
#                self.srsfile = self._findsrs(datadir, ending)
#        except ValueError:
#            if isinstance(run, str):
#                if run.startswith('/'):
#                    datadir = ""
#                else:
#                    if datadir is None:
#                        datadir = self._getgdadir()
#                self.srsfile = _path.join(datadir, run)
#            else:
#                print "run must be a number or a file path"
        self.run = int(run)
        self.srsfile = self._findsrs(datadir, ending)

        print 'file is', self.srsfile
        self.basedir = _path.dirname(self.srsfile)
        dh = load(self.srsfile, formats=['srs'])
        DataHolder.__init__(self, dh.items(), dh.metadata.items())

    def _getgdadir(self):
        try:
            import gda.data.PathConstructor as PathConstructor
            datadir = PathConstructor.createFromDefaultProperty()
        except ImportError:
            print "No gda configuration access so please specify data directory"
            raise
        return datadir

    def _findsrs(self, datadir, ending=".dat"):
        '''Find an SRS file by looking down two layers from given data directory'''
        fname = "%d%s" % (self.run, ending)
        print 'looking for', fname
        dirs = _os.listdir(datadir)
        dirs = [ _path.join(datadir, d) for d in dirs if _path.isdir(_path.join(datadir, d)) ]
        dirs.sort()
        print dirs
        for d in dirs:
            ldirs = _os.listdir(d)
            ldirs = [ l for l in ldirs if _path.isdir(_path.join(d, l)) ]
            ldirs.sort(reverse=True)
            print ' ', d, ldirs
            for l in ldirs:
                f = _path.join(d, l, fname)
                if _os.access(f, _os.R_OK):
                    return f
        return None

    def __str__(self):
        return "\t".join(self.keys())


if __name__ == '__main__':
    from dictutils import sanitise_name
    insane = ['hello', '1hello', ' hello !!$#' ]
    sane = ['hello', '_1hello', '_hello_____' ]
    for i,s in enumerate(insane):
        ss = sanitise_name(s)
        print "|%s| => |%s| cf |%s|, (%d)" % (s, ss, sane[i], ss == sane[i])

    import sys
    if len(sys.argv) > 1:
        name = sys.argv[1]
    else:
        name = '/scratch/workspace/images/i16pilatus.dat'
    dh = load(name, withmetadata=False)
    from pprint import pprint
    pprint(dh.metadata); print
    pprint(dh); print
