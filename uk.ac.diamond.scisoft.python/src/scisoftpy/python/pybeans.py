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

import numpy #@UnresolvedImport @UnusedImport
import pyscisoft

exception = Exception

class guibean(dict):
    def __init__(self):
        pass

class _parameters(object):
    _params = []
    _str_to_params = dict()
    def _register(self, param):
        self._params.append(param)
        self._str_to_params[str(param)] = param
        
    class _parametershelper(object):
        def __init__(self, outer, rep, name):
            self._rep = rep
            self._name = name
            outer._register(self)
    
        def __str__(self):
            return self._name
        
        def __repr__(self):
            return self._rep
        
    def __init__(self):
        self.plotmode = self._parametershelper(self, "plotmode", "PlotMode")
        self.title = self._parametershelper(self, "title", "Title")
        self.roi = self._parametershelper(self, "roi", "ROI")
        self.roilist = self._parametershelper(self, "roilist", "ROIList")
        self.plotid = self._parametershelper(self, "plotid", "PlotID")
        self.plotop = self._parametershelper(self, "plotop", "PlotOp")
        self.fileop = self._parametershelper(self, "fileop", "FileOp")
        self.filename = self._parametershelper(self, "filename", "Filename")
        self.fileselect = self._parametershelper(self, "fileselect", "FileList")
        self.dispview = self._parametershelper(self, "dispview", "DisplayOnView")
        self.imagegridxpos = self._parametershelper(self, "imagegridxpos", "IGridX")
        self.imagegridypos = self._parametershelper(self, "imagegridypos", "IGridY")
        self.imagegridsize = self._parametershelper(self, "imagegridsize", "IGridSize")
        self.metadatanodepath = self._parametershelper(self, "metadatanodepath", "NodePath")
        self.treenodepath = self._parametershelper(self, "treenodepath", "TreeNodePath")
        self.gridpreferences = self._parametershelper(self, "GRIDPREFERENCES", "GridPrefs")
        self.imagegridstore = self._parametershelper(self, "imagegridstore", "ImageGridStore")
        self.volumeheadersize = self._parametershelper(self, "volumeheadersize", "RawVolumeHeaderSize")
        self.volumevoxeltype = self._parametershelper(self, "volumevoxeltype", "RawVolumeVoxelType")
        self.volumexdim = self._parametershelper(self, "volumexdim", "RawVolumeVoxelXDim")
        self.volumeydim = self._parametershelper(self, "volumeydim", "RawVolumeVoxelYDim")
        self.volumezdim = self._parametershelper(self, "volumezdim", "RawVolumeVoxelZDim")
        self.imagegridliveview = self._parametershelper(self, "imagegridliveview", "ImageGridLiveView")
        self.fittedpeaks = self._parametershelper(self, "fittedpeaks", "FittedPeaks")
        self.masking = self._parametershelper(self, "masking", "Masking")
        self.calibrationpeaks = self._parametershelper(self, "calibrationpeaks", "CalibrationPeaks")
        self.calibrationfunctionncd = self._parametershelper(self, "calibrationfunctionncd", "CalibrationFunction")
        self.onedfile = self._parametershelper(self, "onedfile", "OneDFile")
        
    def get(self, parametername):
        '''Return the GUIParameter with the given name, or return None for no matching'''
        return self._str_to_params.get(parametername)
    
parameters = _parameters()


class _plotmode(object):
    _modes = []
    _str_to_modes = dict()    
    def _register(self, mode):
        self._modes.append(mode)
        self._str_to_modes[str(mode)] = mode
        
    class _plotmodehelper(object):
        def __init__(self, outer, rep, name):
            self._rep = rep
            self._name = name
            outer._register(self)
    
        def __str__(self):
            return self._name
        
        def __repr__(self):
            return self._rep
        
        
    def __init__(self):
        self.oned = self._plotmodehelper(self, "oned", "ONED")
        self.oned_threed = self._plotmodehelper(self, "oned_threed", "ONED_THREED")
        self.twod = self._plotmodehelper(self, "twod", "TWOD")
        self.surf2d = self._plotmodehelper(self, "surf2d", "SURF2D")
        self.scatter2d = self._plotmodehelper(self, "scatter2d", "SCATTER2D")
        self.scatter3d = self._plotmodehelper(self, "scatter3d", "SCATTER3D")
        self.multi2d = self._plotmodehelper(self, "multi2d", "MULTI2D")
        self.imgexpl = self._plotmodehelper(self, "imgexpl", "IMGEXPL")
        self.volume = self._plotmodehelper(self, "volume", "VOLUME")
        self.empty = self._plotmodehelper(self, "empty", "EMPTY")
        
    def get(self, modename):
        '''Return the GuiPlotMode with the given name, or return None for no matching'''
        return self._str_to_modes.get(modename)

plotmode = _plotmode()

class axismapbean(object):   
    _MAP_MODE = 'mapMode'
    _AXIS_ID = 'axisID'
    
    DIRECT = 0
    FULL = 1

    XAXIS = "x-axis"
    YAXIS = "y-axis"
    ZAXIS = "z-axis"
    XAXIS2 = "2nd x-axis"

    def __init__(self, mapMode=DIRECT, axisID=[]):
        self.axisID = axisID
        self.mapMode = mapMode

    def __eq__(self, other):
        return (isinstance(other, axismapbean)
            and self.axisID == other.axisID and self.mapMode == other.mapMode)
        
    def __ne__(self, other):
        return not self.__eq__(other)
    
    # mutable, not hashable
    __hash__ = None

    def __repr__(self):
        return 'axismapbean(%s)' % self.__dict__.__repr__()

class datasetwithaxisinformation(object):
    _DATA = "data"
    _AXIS_MAP = "axisMap"
    
    def __init__(self, data=None, axisMap=None):
        '''
        data should be an numpy.ndarray
        axisMap should be an axismapbean
        '''
        self.data = data
        self.axisMap = axisMap
        
    def __eq__(self, other):
        return (isinstance(other, datasetwithaxisinformation) 
                and self.axisMap == other.axisMap and pyscisoft.equaldataset(self.data, other.data))

    def __ne__(self, other):
        return not self.__eq__(other)
    
    # mutable, not hashable
    __hash__ = None

    def __repr__(self):
        return 'datasetWithAxisInformation(%s)' % self.__dict__.__repr__()
        
class databean(object):
    _DATA = "data"
    _AXIS_DATA = "axisData"
    
    def __init__(self, data=None, axisData=None):
        self.data = data or []
        self.axisData = axisData or dict()
 
    def __eq__(self, other):
        if not isinstance(other, databean) or self.data != other.data:
            return False
        for k, v in self.axisData.iteritems():
            if not pyscisoft.equaldataset(v, other.axisData.get(k)):
                return False
        return True

    def __ne__(self, other):
        return not self.__eq__(other)

    def __repr__(self):
        return self.__dict__.__repr__()
    
