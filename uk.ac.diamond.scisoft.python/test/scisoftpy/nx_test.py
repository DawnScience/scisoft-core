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
Test random class
import unittest
unittest.TestProgram(argv=["io_test"])
'''
import unittest

import scisoftpy as dnp

TestFolder = "../../../uk.ac.diamond.scisoft.analysis/testfiles/"
IOTestFolder = TestFolder + "gda/analysis/io/NexusLoaderTest/"
OutTestFolder = TestFolder + "../test-scratch/"

class Test(unittest.TestCase):
    def testLoadingNX(self):
        t = dnp.io.load(IOTestFolder + "FeKedge_1_15.nxs")
        print t
        g = t['entry1/instrument/FFI0']
        h = g['/entry1/instrument/FFI0']
        self.assertEquals(g, h, "relative and absolute do not match!")
        ga = g['..']
        assert ga is t['entry1/instrument'], "parent is wrong!"
        assert g['.'] is g, "self is wrong!"
        print t['entry1/instrument/FFI0/../../']
        d = t['/entry1/FFI0/Energy']
        self.assertEquals(d.shape, (489,), "shapes do not match")
        self.assertEquals(d[2], 6922, "value does not match")
        self.assertEquals(d[479], 7944.5, "value does not match")
        print d.dtype, d[3:6:2]

    def testLoadingHDF(self):
        t = dnp.io.load(IOTestFolder + "testlinks.h5")
        print t
        g = t['/down/to/this/level']
        h = t['/g_hl']
        self.assertEquals(g, h, "relative and absolute do not match!")
        d = g['d1']
        self.assertEquals(d.shape, (25, 3), "shapes do not match")
        self.assertEquals(d[0,1], 1, "value does not match")
        print d.dtype, d[3:6:2]
#        ga = g['..']
#        assert ga is t['entry1/instrument'], "parent is wrong!"
#        assert g['.'] is g, "self is wrong!"
#        print t['entry1/instrument/FFI0/../../']

    def save(self, name, data, testfolder=TestFolder):
        path = testfolder + name
        dnp.io.save(path, data)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
