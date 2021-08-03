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
from __future__ import print_function
import unittest

import scisoftpy as dnp

import os
from os import path
TestFolder = path.dirname(__file__) + "/../../../uk.ac.diamond.scisoft.analysis.test/testfiles/images/"

IOTestFolder = TestFolder + "../gda/analysis/io/"
OutTestFolder = TestFolder + "../../test-scratch/"
if not path.exists(OutTestFolder):
    os.mkdir(OutTestFolder)

TestShape = (512, 256)

class Test(unittest.TestCase):
    def load(self, name, testfolder=TestFolder, shape=None):
        path = testfolder + name
        im = dnp.io.load(path)
        print(type(im[0]), im[0].shape)
        if shape is not None:
            self.assertSequenceEqual(im[0].shape, shape)
        return im

    def colourload(self, name, testfolder=TestFolder, shape=None):
        path = testfolder + name
        im = dnp.io.load(path, ascolour=True)
        print(type(im[0]), im[0].shape)
        if shape is not None:
            self.assertSequenceEqual(im[0].shape, shape)
        return im[0]

    def testLoading(self):
        print(os.getcwd())
        self.load("test.png", shape=TestShape)
        self.load("testrgb.png", shape=TestShape)

        self.colourload("test.png", shape=TestShape)
        im = self.colourload("testrgb.png", shape=TestShape)
        print('slicing RGB: ', type(im[:5,2]))
        self.assertTrue(isinstance(im, dnp.ndarrayRGB))

        self.load("test.jpg", shape=TestShape)
        self.load("testlossy85.jpg", shape=TestShape)
        self.load("testrgb.jpg", shape=TestShape)
        self.load("testrgblossy85.jpg", shape=TestShape)

        self.colourload("test.jpg", shape=TestShape)
        self.colourload("testrgb.jpg", shape=TestShape)
        self.colourload("testrgblossy85.jpg", shape=TestShape)

        self.load("test.tiff", shape=TestShape)
        self.load("test-df.tiff", shape=TestShape)
        self.load("test-pb.tiff", shape=TestShape)
        self.load("testrgb.tiff", shape=TestShape)
        self.load("testrgb-df.tiff", shape=TestShape)
        self.load("testrgb-lzw.tiff", shape=TestShape)
        self.load("testrgb-pb.tiff", shape=TestShape)
        try:
            self.load("test-trunc.tiff")
            self.fail('Should have failed loading truncated file')
        except IOError as e:
            print('Expected IO error caught:', e)
        except:
            import sys
            print('Unexpected exception caught', sys.exc_info())

        self.colourload("testrgb.tiff", shape=TestShape)
        self.colourload("testrgb-df.tiff", shape=TestShape)
        self.colourload("testrgb-lzw.tiff", shape=TestShape)
        self.colourload("testrgb-pb.tiff", shape=TestShape)

    def testLoadingSRS(self):
        dh = self.load("96356.dat", IOTestFolder+"SRSLoaderTest/")
        print(dh.eta)
        self.assertEquals(dh.eta.size, 41)
        self.assertAlmostEquals(dh.eta[3], 32.7779, 4)

    def testLoadingNXS(self):
        f = IOTestFolder + "NexusLoaderTest/"
        nm = dnp.io.load(f + "FeKedge_1_15.nxs")
        print('There are %d datasets called "Energy"' % len(nm.getnodes('Energy')))

    def testLoadingHDF(self):
        f = IOTestFolder + "NexusLoaderTest/"
        t = dnp.io.load(f + "FeKedge_1_15.nxs", format=['hdf5'])
        self.checkTree(t)
        t = dnp.io.load(f + "FeKedge_1_15.nxs")
        self.checkTree(t)
        t = dnp.io.load(f + "h5py_complex.h5")
        cs = t.complex_example
        cx = cs[1:10:2, 3:10:3]
        self.assertAlmostEqual(cx[0,0].real, 0.22884877031898887, places=17)
        self.assertAlmostEqual(cx[0,0].imag, 0.19673784135439948, places=17)
        self.assertAlmostEqual(cx[4,2].real, 0.6922704317579508, places=17)
        self.assertAlmostEqual(cx[4,2].imag, -1.8087566023531674, places=17)

    def testLoadingHDFStrings(self):
        f = IOTestFolder + "NexusLoaderTest/"
        t = dnp.io.load(f + 'unicode.h5')
        # check can use string attributes as keys
        from scisoftpy.nexus.utils import _text_type
        for k in t.attrs:
            if k.startswith('scalar'):
                self.assertTrue(isinstance(t.attrs[k], _text_type))

    def testLoadingHDF5Scalars(self):
        f = IOTestFolder + "NexusLoaderTest/"
        nm = dnp.io.load(f + "scalar.h5", format=['hdf5'])
        d = nm['scalar'][...]
        self.assertAlmostEqual(d[()], 1.0, places=17)
        d = nm['s_f_string'][...]
        self.assertEqual(d, b'hello!')
        d = nm['s_a_string'][...]
        self.assertEqual(d, b'hello!')
        d = nm['s_u_string'][...]
        
        hello_text = u'hello!'
        if os.name != 'java':
            import h5py
            if h5py.version.version_tuple[0] > 2:
                hello_text = b'hello!' # h5py >= 3 will return bytes
        self.assertEqual(d, hello_text) # h5py >= 3 will return bytes

    def checkTree(self, t):
        print(t)
        g = t['entry1/instrument/FFI0']
        h = g['/entry1/instrument/FFI0']
        self.assertEqual(g, h, "relative and absolute do not match!")
        ga = g['..']
        assert ga is t['entry1/instrument'], "parent is wrong!"
        assert g['.'] is g, "self is wrong!"
        print(t['entry1/instrument/FFI0/../../'])
        print(list(t['entry1/counterTimer01'].keys()))
        l = t.getnodes("Energy")
        print('List of energy datasets is:', len(l))
        assert len(l) is 1, "Number of energy datasets should be 1"
        d = l[0]
        print(type(d))
        assert d.shape == (489,), "Wrong shape"
        dd = d[...]
        assert dd.shape == (489,), "Wrong shape"
        print(type(d), type(dd))

    def save(self, name, data, testfolder=OutTestFolder, signed=True, bits=None):
        path = testfolder + name
        dnp.io.save(path, data, signed=signed, bits=bits)

    def testSaving(self):
        d = dnp.arange(100).reshape(10,10) % 3
        self.save("chequered.png", d)
        im = self.load("chequered.png", testfolder=OutTestFolder, shape=(10,10))
        im = self.load("test.png", shape=TestShape)
        self.save("grey.png", im)
        im2 = self.load("grey.png", testfolder=OutTestFolder, shape=TestShape)
        self.save("chequered.npy", d)
        im3 = self.load("chequered.npy", testfolder=OutTestFolder, shape=(10,10))

    def testSavingOthers(self):
        im = self.colourload("testrgb.png", shape=TestShape)
        self.save("colour.png", im)
        im2 = self.colourload("colour.png", testfolder=OutTestFolder, shape=TestShape)

    def testSavingBits(self):
        d = dnp.arange(12*32).reshape((12,32))
        b = dnp.abs(dnp.array(d, dnp.int8))
        b[b < 0] = 0
        print(b.min(), b.max())
        self.save('uint.tiff', d, bits=32, signed=False)
        self.save('ushort.tiff', d, bits=16, signed=False)
        self.save('ubyte.tiff', b, bits=8, signed=False)
        self.save('int.tiff', d, bits=32)
        self.save('short.tiff', d, bits=16)
        self.save('byte.tiff', dnp.array(d, dnp.int8), bits=8)
        self.save('double.tiff', d, bits=33)
        self.save('float.tiff', d, bits=33)
        self.save('short.png', d, bits=16)
        self.save('byte.png', b, bits=8)

    def testB16data(self):
        d = dnp.io.load(IOTestFolder + 'SRSLoaderTest/34146.dat', format=['srs'])
        print(list(d.keys()))
        print(list(d.metadata.keys()))
        print(list(d.metadata.values()))
        self.assertIn('x', d.keys())
        self.assertIn('SRSDAT', d.metadata.keys())
        self.assertEqual(int(d.metadata['SRSDAT']), 2011513)

    def testNulldata(self):
        try:
            d = self.load("null.dat")
        except Exception as e:
            print("Expected exception caught:", e)

if __name__ == '__main__':
    unittest.main(verbosity=2)
