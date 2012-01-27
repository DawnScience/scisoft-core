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

TestFolder = "../../../uk.ac.diamond.scisoft.analysis/testfiles/images/"
IOTestFolder = TestFolder + "../gda/analysis/io/"
OutTestFolder = TestFolder + "../../test-scratch/"

import os
isjava = os.name == 'java'

class Test(unittest.TestCase):
    def load(self, name, testfolder=TestFolder):
        path = testfolder + name
        im = dnp.io.load(path)
        print type(im[0]), im[0].shape
        return im

    def colourload(self, name, testfolder=TestFolder):
        path = testfolder + name
        im = dnp.io.load(path, ascolour=True)
        print type(im[0]), im[0].shape
        return im[0]

    def testLoading(self):
        import os
        print os.getcwd()
        self.load("test.png")
        self.load("testrgb.png")

        self.colourload("test.png")
        im = self.colourload("testrgb.png")
        print 'slicing RGB: ', type(im[:5,2])

        self.load("test.jpg")
        self.load("testlossy85.jpg")
        self.load("testrgb.jpg")
        self.load("testrgblossy85.jpg")

        self.colourload("test.jpg")
        self.colourload("testrgb.jpg")
        self.colourload("testrgblossy85.jpg")

        self.load("test.tiff")
        self.load("test-df.tiff")
        self.load("test-pb.tiff")
        self.load("testrgb.tiff")
        self.load("testrgb-df.tiff")
        self.load("testrgb-lzw.tiff")
        self.load("testrgb-pb.tiff")
        try:
            self.load("test-trunc.tiff")
        except IOError, e:
            print 'Expected IO error caught:', e
        except:
            import sys
            print 'Unexpected exception caught', sys.exc_info()

        self.colourload("testrgb.tiff")
        self.colourload("testrgb-df.tiff")
        self.colourload("testrgb-lzw.tiff")
        self.colourload("testrgb-pb.tiff")
        return True

    def testLoadingSRS(self):
        dh = self.load("96356.dat", IOTestFolder+"SRSLoaderTest/")
        print dh.eta

    def testLoadingNXS(self):
        if isjava:
            f = IOTestFolder + "NexusLoaderTest/"
            nm = dnp.io.loadnexus(f + "FeKedge_1_15.nxs")
            print 'There are %d datasets called "Energy"' % len(nm['Energy'])

    def testLoadingHDF(self):
        f = IOTestFolder + "NexusLoaderTest/"
        t = dnp.io.load(f + "FeKedge_1_15.nxs", formats=['hdf5'])
        self.checkTree(t)
        t = dnp.io.load(f + "FeKedge_1_15.nxs")
        self.checkTree(t)

    def checkTree(self, t):
        print t
        g = t['entry1/instrument/FFI0']
        h = g['/entry1/instrument/FFI0']
        self.assertEquals(g, h, "relative and absolute do not match!")
        ga = g['..']
        assert ga is t['entry1/instrument'], "parent is wrong!"
        assert g['.'] is g, "self is wrong!"
        print t['entry1/instrument/FFI0/../../']
        print t['entry1/counterTimer01'].keys()
        l = t.getnodes("Energy")
        print 'List of energy datasets is:', len(l)
        assert len(l) is 1, "Number of energy datasets should be 1"
        d = l[0]
        print type(d)
        assert d.shape == (489,), "Wrong shape"
        dd = d[...]
        assert dd.shape == (489,), "Wrong shape"
        print type(d), type(dd)

    def save(self, name, data, testfolder=OutTestFolder):
        path = testfolder + name
        dnp.io.save(path, data)

    def testSaving(self):
        d = dnp.arange(100).reshape(10,10) % 3
        self.save("chequered.png", d)
        im = self.load("chequered.png", testfolder=OutTestFolder)
        im = self.load("test.png")
        self.save("grey.png", im)
        im2 = self.load("grey.png", testfolder=OutTestFolder)

    def testSavingOthers(self):
        im = self.colourload("testrgb.png")
        self.save("colour.png", im)
        im2 = self.colourload("colour.png", testfolder=OutTestFolder)

    def testSavingBits(self):
        d = dnp.arange(12*32).reshape((12,32))
        b = dnp.abs(dnp.array(d, dnp.int8))
        b[b < 0] = 0
        print b.min(), b.max()
        dnp.io.save(OutTestFolder+'uint.tiff', d, bits=32, signed=False)
        dnp.io.save(OutTestFolder+'ushort.tiff', d, bits=16, signed=False)
        dnp.io.save(OutTestFolder+'ubyte.tiff', b, bits=8, signed=False)
        dnp.io.save(OutTestFolder+'int.tiff', d, bits=32)
        dnp.io.save(OutTestFolder+'short.tiff', d, bits=16)
        dnp.io.save(OutTestFolder+'byte.tiff', dnp.array(d, dnp.int8), bits=8)
        dnp.io.save(OutTestFolder+'double.tiff', d, bits=33)
        dnp.io.save(OutTestFolder+'float.tiff', d, bits=33)
        dnp.io.save(OutTestFolder+'short.png', d, bits=16)
        dnp.io.save(OutTestFolder+'byte.png', b, bits=8)

    def testB16data(self):
        d = dnp.io.load(IOTestFolder + 'SRSLoaderTest/34146.dat', formats=['srs'])
        print d.keys()
        print d.metadata.keys()
        print d.metadata.values()

    def testNulldata(self):
        try:
            d = self.load("null.dat")
        except Exception, e:
            print "Expected exception caught:", e
        
if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
