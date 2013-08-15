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

from dictutils import DataHolder, ListDict

_extra_suffices = { 'jpg' : ['jpeg'], 'tif' : ['tiff'], 'dat' : ['srs', 'dls'], 'h5' : ['hdf5'], 'nxs' : ['nx'] }


def _findsuffix(name, formats):
    bits = name.split('.')
    if len(bits) > 1:
        suffix = bits[-1].lower()
        if suffix in formats:
            return [suffix]
        if suffix in _extra_suffices:
            return _extra_suffices[suffix]
    return None

def load(name, format=None, formats=None, withmetadata=True, ascolour=False, warn=True, **kwarg):
    '''Load a file and return a list of datasets (or a dictionary of datasets) and
    optionally a dictionary of metadata items

    format (or formats) -- list of formats to try. Supported input formats:
        png, gif, jpeg, tiff
        adsc, crysalis, mar, pilatus, ocs -> image detectors
        cbf
        xmap
        srs
        binary -> raw
        text -> raw
        dls (DLS dat format, not SRS - more like gnuplot)
        edf
        npy (NumPy)
        pgm
        hdf5 -> returns a HDF5 tree
        nx   -> returns a NeXus tree
    withmetadata -- if True, load metadata as well
    ascolour -- if True, attempt load as colour dataset
    warn -- if True, suppress warning
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
        lformats = _findsuffix(name, _iformats)

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
                lfh = ldr.load(warn=warn)
                break
            except _ioexception, e:
                if warn:
                    print 'Warning: ', e
            except:
                if warn:
                    import sys
                    print >> sys.stderr, 'Unexpected exception raised: ', sys.exc_info()

    if lfh is None:
        raise IOError, 'Cannot load file'

    return lfh

def save(name, data, format=None, range=(), autoscale=False, signed=True, bits=None): #@ReservedAssignment
    '''Save a (list of) datasets with optional scaling range

    Supported output formats:
        png, gif, jpeg, tiff
        binary -> raw
        text -> raw
        npy

    signed -- save as signed numbers if true
    bits   -- save with number of bits
    '''
    saver = None
    try:
        if len(range) > 0 or autoscale:
            if not autoscale:
                if len(range) != 2:
                    raise ValueError, 'Range has to be a pair of limits (lower, upper)'
                if range[0] >= range[1]:
                    raise ValueError, 'Given minimum must be less than maximum'
            if format is None:
                format = _findsuffix(name, _soformats)[0]
#            print "scaled save format", format
            sclass = _soformats[format]
            if autoscale:
                saver = sclass(name)
            else:
                saver = sclass(name, range[0], range[1])
        else:
            if format is None:
                format = _findsuffix(name, _oformats)[0]
#            print "save format", format, "as", name
            sclass = _oformats[format]
            if sclass is None:
                raise ValueError, 'Format not supported'
            saver = sclass(name, signed, bits)
    except KeyError:
        raise ValueError, 'Format not supported'



    if isinstance(data, ListDict):
        dh = data
    else:
        dl = []
        if format == 'binary':
            for d in _asIterable(data):
                n = getattr(d, 'name', 'data')
                dl.append((n, d))
        else:
            for d in _toList(data):
                n = getattr(d, 'name', 'data')
                dl.append((n, d))
        dh = ListDict(dl)

    saver.save(dh)


import os as _os
_path = _os.path
_join = _path.join

def find_run_file(run, data_dir, visit=None, year=None, ending=".dat"):
    '''Find run file in given data directory

    Looks for file in data_dir/year/visit/
    Arguments:
    run      - run number
    data_dir - beamline data directory, such as '/dls/i01/data'
    visit    - visit-ID, such as cm1234-1 (defaults to data_dir and its sub-directories)
    year     - calendar year (defaults to visit directory and any year in range 2000-99)
    ending   - suffix or list of suffices (defaults to '.dat')

    Returns file with shortest name (if there is a tie, then the one matching
     the first ending is chosen)
    '''
    from glob import glob, iglob

    run = str(run)
    if data_dir is None:
        raise ValueError, 'Beamline data directory must be defined'

    if type(ending) is str:
        ending = (ending,)
    es = [ '*' + e for e in ending ]


    if year is None:
        years = (None, '20[0-9][0-9]')
    else:
        years = (str(year),)

    if visit is None:
        visits = (None, '*')
    else:
        visits = (visit,)

    files = []

    for y in years:
        if y is None:
            ds = (data_dir,)
        else:
            ds = glob(_join(data_dir, y))
        for d in ds:
            for v in visits:
                if v is None:
                    vs = (d,)
                else:
                    vs = glob(_join(d, v))
                for lv in vs:
                    for e in es:
                        files.extend(iglob(_join(lv, run + e)))
                    if len(files) > 0:
                        break
                if len(files) > 0:
                    break

    if len(files) == 0:
        raise IOError, 'Run files not found'
    if len(files) == 1:
        return files[0]

    ls = [len(f) for f in files]
    lc = min(ls)
    if ls.count(lc) > 1: # if there is more than one of same length
        for l, f in zip(ls, files):
            if l == lc:
                if f.endswith(ending[0]): # prefer first ending
                    return f
    return files[ls.index(lc)]

#from scisoftpy import ndarraywrapped as _npwrapped

class Run(DataHolder):
    '''Represent a run from an SRS file'''
    def __init__(self, run, data_dir, visit=None, year=None, ending=".dat"):
        '''Specify a run number (or file name) and data directory

        Looks for file in data_dir/year/visit-*/
        Arguments:
        run - run number
        data_dir - beamline data directory, such as '/dls/i01/data'
        visit - visit ID, such as cm1234 (default to '*')
        year - calendar year (default to any year in range 2000-99)
        ending - suffix or list of suffices (default to '.dat')
        '''
        run = int(run)
        srsfile = find_run_file(run, data_dir, visit, year, ending)

        dh = load(srsfile, format='srs')
        itms = []
        mds = []
        for i in dh.items():
            if i[0] == 'metadata':
                mds = i[1].items()
            else:
                itms.append(i)
        DataHolder.__init__(self, itms, mds)
        self.__run = run
        self.__file = srsfile

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
