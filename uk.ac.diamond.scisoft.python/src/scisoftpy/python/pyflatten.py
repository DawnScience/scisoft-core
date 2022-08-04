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
Python version of flattening. See Java version for documentation on flattening.
This module is generally for internal use by AnalysisRPC and is only made
public for the purpose of testing, see pyflatten_test.py
'''


import scisoftpy.python.pycore as _core
import scisoftpy.python.pyroi as _roi
import scisoftpy.python.pybeans as _beans
import scisoftpy.python.pywrapper as _wrapper
import numpy as _np #@UnresolvedImport
from tempfile import mkstemp
import os
import sys
import copy
import uuid
import traceback
import six
from six.moves import map
from six.moves import zip

TYPE = "__type__"
CONTENT = "content"

_TEMP_LOCATION_SET = False
_TEMP_LOCATION = None

def settemplocation(loc=None):
    '''
     Set a custom temporary file location. This is used by some flatteners to store large data sets which are faster
     to write to disk than send over XML-RPC.
     
     There is no requirement for the unflattener at the other end to have the same temp location as a full path to the
     temp file should be stored in the flattened form.
     
     loc new temp file location to use, or None to use default of system temp
    '''
    global _TEMP_LOCATION, _TEMP_LOCATION_SET
    _TEMP_LOCATION = loc
    _TEMP_LOCATION_SET = True

class flatteningHelper(object):
    def __init__(self, typeObj, typeName):
        self.typeObj = typeObj
        self.typeName = typeName

    def canunflatten(self, obj):
        return isinstance(obj, dict) and obj.get(TYPE) == self.typeName
    
    def canflatten(self, obj):
        return isinstance(obj, self.typeObj)

class roiHelper(flatteningHelper):

    def __init__(self, typeObj, typeName):
        super(roiHelper, self).__init__(typeObj, typeName)
        
    def flatten(self, thisRoiBase):
        d = copy.deepcopy(thisRoiBase.__dict__)
        rval = dict()
        for k, v in six.iteritems(d):
            rval[k] = flatten(v)
        rval[TYPE] = self.typeName
        return rval
    
    def unflatten(self, obj):
        unflattenedObj = dict()
        for k, v in six.iteritems(obj):
            if k == TYPE:
                continue
            v = unflatten(v)
            unflattenedObj[k] = v
        return self.typeObj(**unflattenedObj)

    @staticmethod
    def getROIBaseHelper():
        return roiHelper(_roi._iroi, "org.eclipse.dawnsci.analysis.dataset.roi.ROIBase")
        
    @staticmethod
    def getPointHelper():
        return roiHelper(_roi.point, "org.eclipse.dawnsci.analysis.dataset.roi.PointROI")
        
    @staticmethod
    def getRectangleHelper():
        return roiHelper(_roi.rectangle, "org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI")
        
    @staticmethod
    def getSectorHelper():
        return roiHelper(_roi.sector, "org.eclipse.dawnsci.analysis.dataset.roi.SectorROI")

    @staticmethod
    def getLineHelper():
        return roiHelper(_roi.line, "org.eclipse.dawnsci.analysis.dataset.roi.LinearROI")

    @staticmethod
    def getCircleHelper():
        return roiHelper(_roi.circle, "org.eclipse.dawnsci.analysis.dataset.roi.CircularROI")

    @staticmethod
    def getEllipseHelper():
        return roiHelper(_roi.ellipse, "org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROI")


class axisMapBeanHelper(flatteningHelper):

    TYPE_NAME = "uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean"
    
    def __init__(self):
        super(axisMapBeanHelper, self).__init__(_beans.axismapbean, self.TYPE_NAME)
    
    def flatten(self, thisAxisMapBean):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[_beans.axismapbean._AXIS_ID] = flatten(thisAxisMapBean.axisID)
        rval[_beans.axismapbean._AXIS_NAMES] = flatten(thisAxisMapBean.axisNames)
        return rval
    
    def unflatten(self, obj):
        unflattenedMap = dict()
        unflattenedMap[_beans.axismapbean._AXIS_ID] = unflatten(obj[_beans.axismapbean._AXIS_ID])
        unflattenedMap[_beans.axismapbean._AXIS_NAMES] = unflatten(obj[_beans.axismapbean._AXIS_NAMES])
        return _beans.axismapbean(**unflattenedMap)
        
class datasetWithAxisInformationHelper(flatteningHelper):

    TYPE_NAME = "uk.ac.diamond.scisoft.analysis.plotserver.DatasetWithAxisInformation"
    
    def __init__(self):
        super(datasetWithAxisInformationHelper, self).__init__(_beans.datasetwithaxisinformation, self.TYPE_NAME)
        
    def flatten(self, thisDatasetWithAxisInformation):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[_beans.datasetwithaxisinformation._DATA] = flatten(thisDatasetWithAxisInformation.data)
        rval[_beans.datasetwithaxisinformation._AXIS_MAP] = flatten(thisDatasetWithAxisInformation.axisMap)
        return rval
    
    def unflatten(self, obj):
        unflattenedMap = dict()
        unflattenedMap[_beans.datasetwithaxisinformation._DATA] = unflatten(obj[_beans.datasetwithaxisinformation._DATA])
        unflattenedMap[_beans.datasetwithaxisinformation._AXIS_MAP] = unflatten(obj[_beans.datasetwithaxisinformation._AXIS_MAP])        
        return _beans.datasetwithaxisinformation(**unflattenedMap)
            
class dictHelper(flatteningHelper):
    TYPE_NAME = "java.util.Map"
    ORDERED = "ordered"
    KEYS = "keys"
    VALUES = "values"
    
    def __init__(self):
        super(dictHelper, self).__init__(dict, self.TYPE_NAME)
        
    def flatten(self, thisDict):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[self.KEYS] = []
        rval[self.VALUES] = []
        from collections import OrderedDict
        rval[self.ORDERED] = isinstance(thisDict, OrderedDict)
        for k, v in six.iteritems(thisDict):
            rval[self.KEYS].append(flatten(k))
            rval[self.VALUES].append(flatten(v))
        return rval

    def unflatten(self, thisDict):
        from collections import OrderedDict
        rval = OrderedDict() if thisDict[self.ORDERED] else dict() 
        for k, v in zip(thisDict[self.KEYS], thisDict[self.VALUES]):
            rval[unflatten(k)] = unflatten(v)
        return rval

class passThroughHelper(object):
    def flatten(self, obj):
        return obj
    
    def unflatten(self, obj):
        return obj

    def canflatten(self, obj):
        return isinstance(obj, (six.text_type, str, int, int, float, _wrapper.binarywrapper))

    def canunflatten(self, obj):
        return self.canflatten(obj)

class ndArrayHelper(flatteningHelper):
    TYPE_NAME = "org.eclipse.january.dataset.Dataset"
    FILENAME = "filename"
    DELETEFILEAFTERLOAD = "deletefile"
    INDEX = "index"
    NAME = "name"
    
    def __init__(self):
        super(ndArrayHelper, self).__init__(_np.ndarray, self.TYPE_NAME)
    
    def flatten(self, obj):
        rval = dict()
        if isinstance(obj, _core.ndarrayRGB):
            obj = obj.view(_np.ndarray)
        if isinstance(obj, _np.ndarray):
            global _TEMP_LOCATION, _TEMP_LOCATION_SET
            if not _TEMP_LOCATION_SET:
                _TEMP_LOCATION = os.getenv('SCISOFT_RPC_TEMP')
                _TEMP_LOCATION_SET = True
            if _TEMP_LOCATION == "":
                _TEMP_LOCATION = None
                 
            (osfd, filename) = mkstemp(suffix='.npy', prefix='scisofttmp-', dir=_TEMP_LOCATION)
            os.close(osfd)
            try:
                _np.save(filename, _np.asarray(obj, order='C')) # convert to C order as Java loader cannot cope otherwise
            except:
                # If we failed to write the file, remove it
                # mkstemp returned a new file that did not exist, so 
                # we can't be removing someone else's file
                os.remove(filename)
                raise
            rval[self.FILENAME] = filename
            rval[self.DELETEFILEAFTERLOAD] = True
        elif isinstance(obj, _wrapper.datasetdescriptor):
            rval[self.FILENAME] = obj.filename
            if obj.deleteAfterLoad:
                rval[self.DELETEFILEAFTERLOAD] = True
            if obj.index is not None:
                rval[self.INDEX] = obj.index
            if obj.name is not None:
                rval[self.NAME] = obj.name

        rval[TYPE] = self.TYPE_NAME
        return rval

    def unflatten(self, obj):
        filename = obj[self.FILENAME]
        deletefile = False
        if self.DELETEFILEAFTERLOAD in obj:
            deletefile = obj[self.DELETEFILEAFTERLOAD]
        try:
            return _np.load(filename)
        finally:
            if deletefile:
                os.remove(filename)
                
    def canflatten(self, obj):
        return isinstance(obj, (_np.ndarray, _wrapper.datasetdescriptor))

class guiBeanHelper(flatteningHelper):
    TYPE_NAME = "uk.ac.diamond.scisoft.analysis.plotserver.GuiBean"
    
    def __init__(self):
        super(guiBeanHelper, self).__init__(_beans.guibean, self.TYPE_NAME)
    
    def flatten(self, obj):
        rval = dict()
        for k, v in six.iteritems(obj):
            rval[str(k)] = flatten(v)
            
        rval[TYPE] = self.TYPE_NAME
        return rval

    def unflatten(self, thisDict):
        rval = _beans.guibean()
        for k, v in six.iteritems(thisDict):
            if k == TYPE:
                continue
            guiparam = _beans.parameters.get(k)
            val = unflatten(v)
            if guiparam == _beans.parameters.plotid and not isinstance(val, uuid.UUID):
                val = uuid.UUID(val)
            elif guiparam == _beans.parameters.plotmode and not isinstance(val, _beans.plotmode._plotmodehelper):
                val = _beans.plotmode.get(val)
            rval[guiparam] = val
        return rval

class guiParametersHelper(flatteningHelper):
    TYPE_NAME = "uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters"

    def __init__(self):
        super(guiParametersHelper, self).__init__(_beans._parameters._parametershelper, self.TYPE_NAME)
    
    def flatten(self, obj):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[CONTENT] = str(obj)
        return rval

    def unflatten(self, thisDict):
        return _beans.parameters.get(thisDict[CONTENT])

class plotModeHelper(flatteningHelper):
    TYPE_NAME = "uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode"

    def __init__(self):
        super(plotModeHelper, self).__init__(_beans._plotmode._plotmodehelper, self.TYPE_NAME)
    
    def flatten(self, obj):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[CONTENT] = str(obj)
        return rval

    def unflatten(self, thisDict):
        return _beans.plotmode.get(thisDict[CONTENT])

class roiListHelper(flatteningHelper):
    
    def __init__(self, typeObj, typeName):
        super(roiListHelper, self).__init__(typeObj, typeName)
        
    def flatten(self, thisList):
        flatList = []
        for thisRoi in thisList:
            flatList.append(flatten(thisRoi))
        rval = dict()
        rval[TYPE] = self.typeName
        rval[CONTENT] = flatList
        return rval
    
    def unflatten(self, obj):
        rval = self.typeObj()
        for thisRoi in obj[CONTENT]:
            rval.append(unflatten(thisRoi))
        return rval

    @staticmethod
    def getPointListHelper():
        return roiListHelper(_roi.point_list, "org.eclipse.dawnsci.analysis.dataset.roi.PointROIList")

    @staticmethod
    def getLineListHelper():
        return roiListHelper(_roi.line_list, "org.eclipse.dawnsci.analysis.dataset.roi.LinearROIList")

    @staticmethod
    def getRectangleListHelper():
        return roiListHelper(_roi.rectangle_list, "org.eclipse.dawnsci.analysis.dataset.roi.RectangularROIList")

    @staticmethod
    def getSectorListHelper():
        return roiListHelper(_roi.sector_list, "org.eclipse.dawnsci.analysis.dataset.roi.SectorROIList")

    @staticmethod
    def getCircleListHelper():
        return roiListHelper(_roi.circle_list, "org.eclipse.dawnsci.analysis.dataset.roi.CircularROIList")

    @staticmethod
    def getEllipseListHelper():
        return roiListHelper(_roi.ellipse_list, "org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROIList")

class listAndTupleHelper(object):
    def flatten(self, obj):
        outList = []
        for thisItem in obj:
            outList.append(flatten(thisItem))
        return outList
    
    def unflatten(self, obj):
        outList = []
        for thisItem in obj:
            outList.append(unflatten(thisItem))
        return outList
        
    
    def canflatten(self, obj):
        return isinstance(obj, (list, tuple))

    def canunflatten(self, obj):
        return isinstance(obj, (list, tuple))

class dataBeanHelper(flatteningHelper):
    TYPE_NAME = "uk.ac.diamond.scisoft.analysis.plotserver.DataBean"
    
    def __init__(self):
        super(dataBeanHelper, self).__init__(_beans.databean, self.TYPE_NAME)

    def flatten(self, thisDataBean):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[_beans.databean._AXIS_DATA] = flatten(thisDataBean.axisData)
        rval[_beans.databean._DATA] = flatten(thisDataBean.data)
        return rval
    
    def unflatten(self, obj):
        unflattenedMap = dict()
        unflattenedMap[_beans.databean._AXIS_DATA] = unflatten(obj[_beans.databean._AXIS_DATA])
        unflattenedMap[_beans.databean._DATA] = unflatten(obj[_beans.databean._DATA])
        return _beans.databean(**unflattenedMap)

class noneHelper(flatteningHelper):
    TYPE_NAME = "__None__"
    TYPED_NONE_TYPE = "typedNoneType"
    NULL = "null"
    
    def __init__(self):
        super(noneHelper, self).__init__(None, self.TYPE_NAME)

    def flatten(self, thisNone):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        if not (thisNone is None):
            rval[self.TYPED_NONE_TYPE] = thisNone.typedNoneType
        else:
            rval[self.TYPED_NONE_TYPE] = self.NULL
        return rval
    
    def unflatten(self, obj):
        if obj is None or obj[self.TYPED_NONE_TYPE] == self.NULL:
            return None
        
        rval = _wrapper.typednone()
        rval.typedNoneType = obj[self.TYPED_NONE_TYPE]
        return rval
    
    def canflatten(self, obj):
        return obj is None or isinstance(obj, _wrapper.typednone)
    
    def canunflatten(self, obj):
        return obj is None or super(noneHelper, self).canunflatten(obj)
        
class uuidHelper(flatteningHelper):
    TYPE_NAME = "java.util.UUID"
    
    def __init__(self):
        super(uuidHelper, self).__init__(uuid.UUID, self.TYPE_NAME)

    def flatten(self, thisUUID):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        rval[CONTENT] = str(thisUUID)
        return rval
    
    def unflatten(self, obj):
        return uuid.UUID(obj[CONTENT])

class stackTraceElementHelper(object):
    TYPE_NAME = "java.lang.StackTraceElement"
    DECLARINGCLASS = "declaringClass"
    METHODNAME = "methodName"
    FILENAME = "fileName"
    LINENUMBER = "lineNumber"
    CLASSLOADERNAME = "classLoaderName"
    MODULENAME = "moduleName"
    MODULEVERSION = "moduleVersion"

    def canunflatten(self, obj):
        return isinstance(obj, dict) and obj.get(TYPE) == self.TYPE_NAME
    
    def canflatten(self, obj):
        # There is no actual type in Python that is a StackTraceElement
        # so we can't flatten it. We flatten it as a side effect of
        # creating a flat exception (see exceptionHelper)
        return False

    def flatten(self, obj):
        # This method can take a tuple as returned by extract_tb and
        # convert it to a flattened form. Note extra item was inserted for traceback formatter
        rval = dict()
        rval[TYPE] = self.TYPE_NAME

        length = len(obj)
        if length == 5 or length == 8:
            filename, line_number, function_name, unused_text, clazz = obj[:5]
            rval[self.DECLARINGCLASS] = flatten(clazz)
            rval[self.METHODNAME] = flatten(function_name)
            rval[self.FILENAME] = flatten(filename)
            rval[self.LINENUMBER] = flatten(line_number)

            if length == 8:
                classloader_name, module_name, module_version = obj[5:]
                rval[self.CLASSLOADERNAME] = flatten(classloader_name)
                rval[self.MODULENAME] = flatten(module_name)
                rval[self.MODULEVERSION] = flatten(module_version)

            return rval

        raise ValueError("Number of items to flatten incorrect: {}".format(length))
    
    def unflatten(self, obj):
        clazz = unflatten(obj[self.DECLARINGCLASS])
        function_name = unflatten(obj[self.METHODNAME])
        filename = unflatten(obj[self.FILENAME])
        line_number = unflatten(obj[self.LINENUMBER])

        if self.CLASSLOADERNAME in obj:
            classloader_name = unflatten(obj[self.CLASSLOADERNAME])
            module_name = unflatten(obj[self.MODULENAME])
            module_version= unflatten(obj[self.MODULEVERSION])
            return filename, line_number, function_name, clazz, classloader_name, module_name, module_version

        return filename, line_number, function_name, clazz


class exceptionHelper(flatteningHelper):
    TYPE_NAME = "java.lang.Exception"
    EXECTYPESTR = "exctypestr"
    EXECVALUESTR = "excvaluestr"
    TRACEBACK = "traceback"
    PYTHONTEXTS = "pythontexts"

    def __init__(self):
        super(exceptionHelper, self).__init__(Exception, self.TYPE_NAME)

    def flatten(self, thisException):
        rval = dict()
        rval[TYPE] = self.TYPE_NAME
        (_etype, value, tb) = sys.exc_info()
        try:
            rval[self.EXECTYPESTR] = flatten(str(thisException.__class__.__name__))
            traceback.format_exception_only(thisException.__class__, thisException)
            try:
                rval[self.EXECVALUESTR] = flatten(str(thisException))
            except:
                rval[self.EXECVALUESTR] = flatten(None)

            # We only add a traceback if one is available and the exception
            # being flattened is the exception
            def flatten_tb(tb):
                # Set the stack order to newest on top
                # (Java and Python have opposite order for storing 
                # stack traces, this normalizes them) 
                tb.reverse()
                
                # Ideally we would do "rval[self.TRACEBACK] = flatten(tb)" but
                # there is no type info to do that, so instead we have to 
                # explicitly flatten
                steHelper = stackTraceElementHelper()
                stes = [steHelper.flatten(f) for f in tb]
                
                texts = flatten([f[3] for f in tb])
                 
                return stes, texts

            if tb is not None and id(value) == id(thisException):
                extract_tb = traceback.extract_tb(tb)
                extract_tb = list(zip(extract_tb, ("",) * len(extract_tb)))
                extract_tb = [tuple(s[0]) + (s[1],) for s in extract_tb]
                rval[self.TRACEBACK], rval[self.PYTHONTEXTS] = flatten_tb(extract_tb)
            elif hasattr(thisException, "flatten_traceback"):
                rval[self.TRACEBACK], rval[self.PYTHONTEXTS] = flatten_tb(thisException.flatten_traceback)
        finally:
            _etype = value = tb = None
        return rval
    
    def unflatten(self, obj):
        # For Python we squish the entire traceback into the message
        # because we can't create a "fake" traceback like we do with
        # Java using Exception.setStackTrace()
        # But we also store the raw stack trace in flatten_traceback,
        # however, this is not really API and just enables us to
        # preserve the flattened form (i.e. flatten -> unflatten -> flatten ->
        # unflatten -> etc) does not lose information. In practice this
        # continued transforms is only done on tests and the actual message
        # of the exception is not well preserved  
        exctype = unflatten(obj[self.EXECTYPESTR])
        excstr = unflatten(obj[self.EXECVALUESTR])
        if excstr is None:
            excstr = ""
        else:
            excstr = ": " + excstr
        excheader = exctype + excstr
        if self.TRACEBACK in obj:
            stackTrace = unflatten(obj[self.TRACEBACK])
            texts = None
            if self.PYTHONTEXTS in obj:
                texts = unflatten(obj[self.PYTHONTEXTS])
            if texts is None or len(texts) != len(stackTrace):
                # texts are mismatched, discard them
                texts = [""] * len(stackTrace)

            # insert Python trace messages for formatter
            stackTrace = [s[:3] + (t,) + s[3:] for s,t in zip(stackTrace, texts)]

            # Set the stack order to oldest on top
            # (Java and Python have opposite order for storing 
            # stack traces, this returns to Python order) 
            stackTrace.reverse()
            
            excmsg = excheader
            analheader = "\n\nTraceback (from AnalysisRPC Remote Side, most recent call last):\n"
            if analheader not in excmsg:
                excmsg += analheader
                out = traceback.format_list([x[:4] for x in stackTrace]) # clip for formatter
                excmsg += "".join(out)
            e = Exception(excmsg)
            e.flatten_traceback = stackTrace
            return e
        else:
            return Exception(excheader)

helpers = [noneHelper(), roiListHelper.getLineListHelper(), roiListHelper.getPointListHelper(),
           roiListHelper.getSectorListHelper(), roiListHelper.getRectangleListHelper(),
           roiListHelper.getCircleListHelper(), roiListHelper.getEllipseListHelper(),
           roiHelper.getRectangleHelper(), roiHelper.getSectorHelper(),
           roiHelper.getCircleHelper(), roiHelper.getEllipseHelper(),
           roiHelper.getLineHelper(), roiHelper.getPointHelper(), roiHelper.getROIBaseHelper(), 
           ndArrayHelper(), guiBeanHelper(),
           guiParametersHelper(), plotModeHelper(), axisMapBeanHelper(),
           datasetWithAxisInformationHelper(), dataBeanHelper(),
           dictHelper(), passThroughHelper(), listAndTupleHelper(),
           uuidHelper(), exceptionHelper(), stackTraceElementHelper()]

def addhelper(helper):
    helpers.insert(0, helper)

def flatten(obj):
    for thisHelper in helpers:
        if thisHelper.canflatten(obj):
            return thisHelper.flatten(obj)
    raise TypeError("Object " + repr(obj) + " cannot be flattened")
        
def unflatten(obj):
    for thisHelper in helpers:
        if thisHelper.canunflatten(obj):
            return thisHelper.unflatten(obj)
    raise TypeError("Object " + repr(obj) + " cannot be unflattened")

def canflatten(obj):
    for thisHelper in helpers:
        if thisHelper.canflatten(obj):
            return True
    return False
        
def canunflatten(obj):
    for thisHelper in helpers:
        if thisHelper.canunflatten(obj):
            return True
    return False


