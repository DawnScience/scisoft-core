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

import unittest
import sys, os
import scisoftpy as dnp
import xmlrpclib
import math
import uuid

class Test(unittest.TestCase):

    def _assertFlattenedEquals(self, expected, actual):
        if expected is None:
            self.assertEquals(None, actual)
        elif isinstance(expected, (list, tuple)):
            self.assertEquals(len(expected), len(actual))
            for exp, act in zip(expected, actual):
                self._assertFlattenedEquals(exp, act)
        elif isinstance(expected, dict):
            self.assertEquals(len(expected), len(actual))
            for k, v in expected.iteritems():
                self.assertTrue(k in actual)
                self._assertFlattenedEquals(v, actual[k])
        elif isinstance(expected, float) and dnp.isnan(expected):
            self.assertTrue(dnp.isnan(actual))
        elif isinstance(expected, Exception):
            self.assertTrue(isinstance(actual, Exception))
            # We are a little lenient on Exception comparisons because
            # Exception helpers are not a perfect flatten/unflatten
            # 1) we add the original type of the Exception to the beginning
            #     of the Exception string
            # 2) we add the stack trace to the end of the exception
            actualstr = str(actual)
            i = actualstr.find("\n\n")
            if i >= 0:
                actualstr = actualstr[:i]
            expstr = str(expected)
            i = expstr.find("\n\n")
            if i >= 0:
                expstr = expstr[:i]
            if expstr != actualstr and "Exception: " + expstr != actualstr:
                # on mismatch, display error on original string
                self.assertEquals(str(expected), str(actual))
        elif isinstance(expected, (dnp.ndarray)):
            self.assertTrue(dnp.equaldataset(expected, actual))
        else:
            self.assertEquals(expected, actual)
    
    def _checkFlattenedState(self, flat):
        if isinstance(flat, (str, int, float, bool, xmlrpclib.Binary)):
            return
        if isinstance(flat, (list, tuple)):
            for elem in flat:
                self._checkFlattenedState(elem)
            return
        if isinstance(flat, dict):
            for k, v in flat.iteritems():
                self.assertTrue(isinstance(k, str))
                self._checkFlattenedState(v)
            return
        
        self.fail("Unexpected object type: " + str(type(flat)));
    
    
    def _flattenAndCheck(self, toFlatten):
        flat = dnp.flatten.flatten(toFlatten);
        self._checkFlattenedState(flat);
        return flat;
    
    
     
    def _flattenAndUnflatten(self, inObj, expectedObj=None, expectedType=None):
        if expectedObj == None:
            expectedObj = inObj;
        if expectedType == None and expectedObj != None:
            expectedType = type(expectedObj)
            
            
        self.assertTrue(dnp.flatten.canflatten(inObj))
        flat = self._flattenAndCheck(inObj)
        self.assertTrue(dnp.flatten.canunflatten(flat))
        out = dnp.flatten.unflatten(flat)
        
        self._assertFlattenedEquals(expectedObj, out);
        
        if expectedObj is not None and expectedType == type(expectedObj):
            self.assertEquals(expectedType, type(out))
        elif expectedType is not None:
            self.assertTrue(isinstance(out, expectedType))

        # finally, take a unflattened item and make sure it has made something
        # that is fully flattenable still
        self.assertTrue(dnp.flatten.canflatten(out));
        self._assertFlattenedEquals(out, dnp.flatten.unflatten(self._flattenAndCheck(out)));

        return out;
   
    def _createNumpyArray(self):
        a = dnp.arange(100)
        a.shape = (10,10)
        return a

    def _createAxisMapBean(self):
        b = dnp.plot.axismapbean(dnp.plot.axismapbean.FULL)
        b.axisID = ["an id", "another id"]
        return b
    
    def _createDataSetWithAxisInformation(self):
        ds = dnp.plot.datasetwithaxisinformation()
        ds.data = self._createNumpyArray()
        ds.axisMap = self._createAxisMapBean()
        return ds

    def _createROIBase(self):
        base = dnp.roi._roibase()
        base.spt = [1.0, 2.7]
        base.plot = True
        return base

    def _createPointROI(self):
        point = dnp.roi.point()
        point.spt = [1.0, 2.7]
        point.plot = True
        return point

    def _createRectangularROI(self):
        rect = dnp.roi.rectangle()
        rect.spt = [1.0, 2.7]
        rect.plot = True
        rect.len = [8.2, 16.3]
        rect.ang = 0.55
        return rect

    def _createSectorROI(self):
        sect = dnp.roi.sector()
        sect.spt = [1.0, 2.7]
        sect.plot = True
        sect.ang = [0.0, 1.0]
        sect.rad = [15.0, 7.3]
        sect.clippingCompensation = True
        sect.symmetry = dnp.roi.sect.INVERT
        sect.combineSymmetry = False
        sect.averageArea = True
        return sect
    
    def _createLinearROI(self):
        line = dnp.roi.line()
        line.spt = [5.0, 3.2]
        line.plot = False
        line.len = 7.2
        line.ang = 0.2
        line.crossHair = True
        return line
        
    def _createCircularROI(self):
        circle = dnp.roi.circle()
        circle.spt = [1.0, 2.7]
        circle.plot = True
        circle.rad = 15.0
        return circle

    def _createEllipticalROI(self):
        ellipse = dnp.roi.ellipse()
        ellipse.spt = [1.0, 2.7]
        ellipse.plot = True
        ellipse.saxis = [15.0, 24]
        ellipse.ang = 0.4
        return ellipse
    
    def testInteger(self): 
        self._flattenAndUnflatten(18)
        self._flattenAndUnflatten(-7)
        self._flattenAndUnflatten(0)
        self._flattenAndUnflatten(sys.maxint)
        self._flattenAndUnflatten(-sys.maxint - 1)
    
    def testBoolean(self): 
        self._flattenAndUnflatten(True)
        self._flattenAndUnflatten(False)
    
    def testString(self): 
        self._flattenAndUnflatten("")
        self._flattenAndUnflatten("bananas")
        self._flattenAndUnflatten("\nhello\tgoodbye")
    
    def testDouble(self): 
        self._flattenAndUnflatten(0)
        self._flattenAndUnflatten(math.pi)
        self._flattenAndUnflatten(float('NaN'))
        self._flattenAndUnflatten(float('Inf'))
        self._flattenAndUnflatten(dnp.floatmax)
        
    def testBinary(self):
        self._flattenAndUnflatten(dnp.rpc.binarywrapper("\0\1\2\3"))
        self._flattenAndUnflatten(dnp.rpc.binarywrapper(' ' * 0))
        self._flattenAndUnflatten(dnp.rpc.binarywrapper(' ' * 1000))
    
    def testDict(self):
        self._flattenAndUnflatten({'moo': 'cow', 'quack': 'duck', 'pi': math.pi, 'One': 1.0}) 
        self._flattenAndUnflatten({'r1': self._createRectangularROI(), 's1': self._createSectorROI()})
        self._flattenAndUnflatten({1: 'one', 2.0: 'two point zero'})

    def testListAndTuple(self):
        '''Lists and tuples are always unflattened as lists''' 
        t = (1, 2, 15)
        self._flattenAndUnflatten(list(t))
        self._flattenAndUnflatten(t, list(t))
        
        l2d = [list(t), list(t)]
        t2d = (t, t)
        self._flattenAndUnflatten(l2d)
        self._flattenAndUnflatten(t2d, l2d)

    def testNone(self):
        self._flattenAndUnflatten(None)
        
        # test None in other data structures
        self._flattenAndUnflatten({None: 'none'})
        self._flattenAndUnflatten({'none': None})
        self._flattenAndUnflatten({None: None})
        self._flattenAndUnflatten([None])
        self._flattenAndUnflatten((None,), [None])

    def testTypedNone(self):
        '''TODO'''
        pass

    
    def testGuiBean(self):
        bean = dnp.plot.bean()
        bean[dnp.plot.parameters.title] = "My amazing plot"
        bean[dnp.plot.parameters.plotid] = uuid.UUID("93dfd804-85ba-4074-afce-d621f7f2aac6")
        bean[dnp.plot.parameters.roi] = self._createRectangularROI()
        bean[dnp.plot.parameters.fileselect] = ["File1.plot", "File2.plot"]
        bean[dnp.plot.parameters.imagegridsize] = [12, 14]
        bean[dnp.plot.parameters.plotmode] = dnp.plot.plotmode.scatter2d
        
        self._flattenAndUnflatten(bean);

    def testGuiBean_PLOTIDAsString(self):
        expected = dnp.plot.bean()
        expected[dnp.plot.parameters.plotid] = uuid.UUID("93dfd804-85ba-4074-afce-d621f7f2aac6")
        
        input = dnp.plot.bean()
        input[dnp.plot.parameters.plotid] = "93dfd804-85ba-4074-afce-d621f7f2aac6"

        self._flattenAndUnflatten(input, expected);

    def testGuiBean_PLOTMODEAsString(self):
        expected = dnp.plot.bean()
        expected[dnp.plot.parameters.plotmode] = dnp.plot.plotmode.multi2d
        
        input = dnp.plot.bean()
        input[dnp.plot.parameters.plotmode] = str(dnp.plot.plotmode.multi2d)

        self._flattenAndUnflatten(input, expected);

    def testGuiBean_keysAsString(self):
        expected = dnp.plot.bean()
        expected[dnp.plot.parameters.plotmode] = dnp.plot.plotmode.multi2d
        expected[dnp.plot.parameters.title] = "My amazing plot"
        
        input = dnp.plot.bean()
        input[str(dnp.plot.parameters.plotmode)] = dnp.plot.plotmode.multi2d
        input["Title"] = "My amazing plot"

        self._flattenAndUnflatten(input, expected);
        
    def testGuiBeanReallyGaveMeAGuiBean(self):
        # Make sure that we really get a guibean out, not just some dict that looks similar to a GuiBean
        self.assertTrue(isinstance(dnp.flatten.unflatten(self._flattenAndCheck(dnp.plot.bean())),dnp.plot.bean))
        
    def testDataBean(self):
        bean = dnp.plot.databean(data=[self._createAxisMapBean(), self._createAxisMapBean()], 
                              axisData = {"data1" : self._createNumpyArray(), "data2" : self._createNumpyArray()})
        self._flattenAndUnflatten(bean);
        
    def testNumpyArray(self):
        self._flattenAndUnflatten(self._createNumpyArray())

    def testAbstractDatasetDescriptor(self):
        array = self._createNumpyArray()
        from tempfile import mkstemp
        from numpy import save as npsave
        (osfd, filename) = mkstemp(suffix='.npy', prefix='scisofttmp-')
        os.close(osfd)
        try:
            npsave(filename, array)
        except:
            # If we failed to write the file, remove it
            # mkstemp returned a new file that did not exist, so 
            # we can't be removing someone else's file
            os.remove(filename)
            raise
        add = dnp.rpc.abstractdatasetdescriptor(filename=filename, deleteAfterLoad=False)
        self._flattenAndUnflatten(add, array, dnp.ndarray)
        self.assertTrue(os.path.exists(filename))
        os.remove(filename)
        

    def testROIBase(self):
        self._flattenAndUnflatten(self._createROIBase())
        roibase = dnp.roi._roibase()
        roibase.spt = None
        self._flattenAndUnflatten(roibase)
        
    def testPointROI(self):
        self._flattenAndUnflatten(self._createPointROI())
        
    def testLinearROI(self):
        self._flattenAndUnflatten(self._createLinearROI())
        
    def testRectangularROI(self):
        self._flattenAndUnflatten(self._createRectangularROI())

    def testSectorROI(self):
        self._flattenAndUnflatten(self._createSectorROI())

    def testCircularROI(self):
        self._flattenAndUnflatten(self._createCircularROI())

    def testEllipticalrROI(self):
        self._flattenAndUnflatten(self._createEllipticalROI())

    def testPointROIList(self):
        pointList = dnp.roi.point_list()
        pointList.append(self._createPointROI())
        pointList.append(self._createPointROI())
        self._flattenAndUnflatten(pointList)

    def testLinearROIList(self):
        lineList = dnp.roi.line_list()
        lineList.append(self._createLinearROI())
        lineList.append(self._createLinearROI())
        self._flattenAndUnflatten(lineList)
        
    def testRectangularROIList(self):
        rectList = dnp.roi.rectangle_list()
        rectList.append(self._createRectangularROI())
        rectList.append(self._createRectangularROI())
        self._flattenAndUnflatten(rectList)

    def testSectorROIList(self):
        sectList = dnp.roi.sector_list()
        sectList.append(self._createSectorROI())
        sectList.append(self._createSectorROI())
        self._flattenAndUnflatten(sectList)
        
    def testCircularROIList(self):
        circleList = dnp.roi.circle_list()
        circleList.append(self._createCircularROI())
        circleList.append(self._createCircularROI())
        self._flattenAndUnflatten(circleList)

    def testEllipticalROIList(self):
        ellipseList = dnp.roi.ellipse_list()
        ellipseList.append(self._createEllipticalROI())
        ellipseList.append(self._createEllipticalROI())
        self._flattenAndUnflatten(ellipseList)

    def testDataSetWithAxisInformation(self):
        self._flattenAndUnflatten(self._createDataSetWithAxisInformation())
        
    def testAxisMapBean(self):
        self._flattenAndUnflatten(self._createAxisMapBean())
        
    def testException(self):
        self._flattenAndUnflatten(Exception("Exceptional things happened"), expectedObj=Exception("Exception: Exceptional things happened"))
        
    def testExceptionOtherType(self):
        # Exceptions are always unflattened as Exception type, original type information is lost
        valError = ValueError("Value Error Happened")
        self._flattenAndUnflatten(valError, expectedObj=Exception("ValueError: Value Error Happened"), expectedType=Exception)
        
    def testExceptionWithTB(self):
        def function_for_tb():
            raise Exception("raised exception")
        try:
            function_for_tb()
        except Exception, e:
            self._flattenAndUnflatten(e, expectedObj=Exception("Exception: raised exception"))
    def testGuiParameters(self):
        # test one explicitly
        self._flattenAndUnflatten(dnp.plot.parameters.plotmode)
        # test all of them
        for p in dnp.plot.parameters._params:
            self._flattenAndUnflatten(p)

    def testGuiPlotMode(self):
        # test one explicitly
        self._flattenAndUnflatten(dnp.plot.plotmode.empty)
        # test all of them
        for p in dnp.plot.plotmode._modes:
            self._flattenAndUnflatten(p)

    def testUUID(self):
        self._flattenAndUnflatten(uuid.UUID("93dfd804-85ba-4074-afce-d621f7f2aac6"));
        self._flattenAndUnflatten(uuid.UUID("dd09fd5c-bb75-4c8b-854b-7f3bb2c9c399"));
        self._flattenAndUnflatten(uuid.UUID("00000000-0000-0000-0000-000000000000"));
        self._flattenAndUnflatten(uuid.UUID("ffffffff-ffff-ffff-ffff-ffffffffffff"));
        self._flattenAndUnflatten(uuid.UUID("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"));
        for i in range(100):
            # There is a reproducibility danger in using random UUIDs in this
            # test in that if one fails to match it cannot simply be re-run to
            # reproduce. The failing UUID should be extracted from the error message
            # stack trace and added to the list above of explicit values.
            # If it fails, the message will look something like:
            # AssertionError: UUID('93dfd804-85ba-4074-afce-d621f7f2aac6') != UUID('5928f264-e431-432e-ab68-873a0a031ff2')
            self._flattenAndUnflatten(uuid.uuid4())
    
    def testUnicode(self):
        self._flattenAndUnflatten(unicode("test"), "test", str)
    
    def testTypedNone(self):
        self._flattenAndUnflatten(dnp.rpc.typednone("java.lang.Double"))
        self._flattenAndUnflatten(dnp.rpc.typednone("uk.ac.diamond.scisoft.analysis.roi.RectangularROIList"))
    
    def testCheckFlattenableFalse(self):
        self.assertFalse(dnp.flatten.canflatten(object()))
        
    def testCheckUnFlattenableFalse(self):
        self.assertFalse(dnp.flatten.canunflatten(object()))
        
    def testFlattenableUnsupported(self):
        self.assertRaises(TypeError, dnp.flatten.flatten, object())
        
    def testUnFlattenableUnsupported(self):
        self.assertRaises(TypeError, dnp.flatten.unflatten, object())
        
        
if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
