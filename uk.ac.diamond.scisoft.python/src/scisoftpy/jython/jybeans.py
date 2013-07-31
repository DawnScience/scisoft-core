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

from uk.ac.diamond.scisoft.analysis.plotserver import GuiParameters as _guiparam

class _parameters:
    plotmode = _guiparam.PLOTMODE
    title = _guiparam.TITLE
    roi = _guiparam.ROIDATA
    roilist = _guiparam.ROIDATALIST
    roiclearall = _guiparam.ROICLEARALL
    plotid = _guiparam.PLOTID
    plotop = _guiparam.PLOTOPERATION
    fileop = _guiparam.FILEOPERATION
    filename = _guiparam.FILENAME
    fileselect = _guiparam.FILESELECTEDLIST
    dispview = _guiparam.DISPLAYFILEONVIEW
    axisop = _guiparam.AXIS_OPERATION
parameters = _parameters()

from uk.ac.diamond.scisoft.analysis.plotserver import GuiPlotMode as _guiplotmode

class _plotmode:
    oned = _guiplotmode.ONED
    oned_threed = _guiplotmode.ONED_THREED
    twod = _guiplotmode.TWOD
    surf2d = _guiplotmode.SURF2D
    scatter2d = _guiplotmode.SCATTER2D
    scatter3d = _guiplotmode.SCATTER3D
    multi2d = _guiplotmode.MULTI2D
    imgexpl = _guiplotmode.IMGEXPL
    volume = _guiplotmode.VOLUME
    empty = _guiplotmode.EMPTY

plotmode = _plotmode()

from uk.ac.diamond.scisoft.analysis.plotserver import GuiBean as _guibean

guibean = _guibean

import java.lang.Exception as _exception

exception = _exception

from uk.ac.diamond.scisoft.analysis.plotserver import AxisMapBean as _axismapbean

axismapbean = _axismapbean

from uk.ac.diamond.scisoft.analysis.plotserver import DataSetWithAxisInformation as _datasetwithaxisinformation

datasetwithaxisinformation = _datasetwithaxisinformation

from uk.ac.diamond.scisoft.analysis.plotserver import DataBean as _databean

databean = _databean

