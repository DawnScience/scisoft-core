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

#@PydevCodeAnalysisIgnore
'''
Test dataset methods

import unittest
unittest.TestProgram(argv=["dataset_test"])
'''
from __future__ import print_function
import unittest

import scisoftpy as np

import os
isjava = os.name == 'java'

def toInt(o):
    return int(o)

def toAny(o):
    return o

class Test(unittest.TestCase):
    def setUp(self):
        self.a = [ [[0, 1], [2, 3]], [[4, 5], [6, 7]] ]
        self.mm = [ [[0., 2.], [6., 12.]], [[20., 30.], [42., 56.]] ]
        self.q = [ [[0., 0.5], [0.6666666666666666, 0.75]], [[0.8, 0.8333333333333334], [0.8571428571428571, 0.875]] ]
        self.r = [ [[1., 3.], [5., 7.]], [[9., 11.], [13., 15.]] ]
        self.s = [ 0, 1, 2, 3, 4, 5, 6, 7 ]
        self.t = [ [[5.]] ]
        self.u = [ [[0., 1.], [7., 3.]], [[4., 5.], [7., 7.]] ]
        self.dda = np.array(self.a, np.float)

        self.dz = 1.
        self.da = [0.1, 1.5]
        self.db = [[0, 1.], [2.5, 3]]
        self.db2 = [[0, 2.], [4., 6]]
        self.dc = [[[0, 1.], [2.5, 3]], [[4.2, 5.1], [6.6, 7.9]]]
        if isjava:
            import Jama.Matrix as mat
            self.m = mat(self.db)
        else:
            self.m = self.db
        self.zz = 1.+0.5j
        self.za = [0.1, 1.5]
        self.zb = [[0, 1.], [2.5, 3]]
        self.zc = [[[0, 1.], [2.5, 3]], [[4.2, 5.1], [6.6, 7.9]]]
        

    def checkitems(self, la, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(convert(la[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(convert(la[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(convert(la[i][j][k]), ds[i, j, k])

    def testZeros(self): # make new datasets with zeros
        print("test zeros")
        dds = np.zeros(3, dtype=np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(1, dds.ndim)
        self.assertEqual(3, dds.shape[0])
        self.assertEqual(0, dds[0])
        dds = np.zeros((2,3), dtype=np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(0, dds[0,0])
        dds = np.zeros_like(dds)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(0, dds[0,0])
        dds = np.zeros(np.array([1,2]), dtype=np.float)
        self.assertEqual((1,2), dds.shape)
        self.assertEqual(0, dds[0,0])
        dds = np.zeros_like(np.array([1,2]), dtype=np.float)
        self.assertEqual((2,), dds.shape)
        self.assertEqual(0, dds[0])

    def testOnes(self): # make new datasets with ones
        print("test ones")
        dds = np.ones(3, dtype=np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(1, dds.ndim)
        self.assertEqual(3, dds.shape[0])
        self.assertEqual(1, dds[0])
        dds = np.ones((2,3), np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(1, dds[0,0])
        dds = np.ones_like(dds)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(1, dds[0,0])
        dds = np.ones(np.array([1,2]), dtype=np.float)
        self.assertEqual((1,2), dds.shape)
        self.assertEqual(1, dds[0,0])
        dds = np.ones_like(np.array([1,2]), dtype=np.float)
        self.assertEqual((2,), dds.shape)
        self.assertEqual(1, dds[0])

    def testTwos(self): # make new datasets with twos
        print("test twos")
        dds = np.full(3, 2.)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(np.float64, dds.dtype)
        self.assertEqual(1, dds.ndim)
        self.assertEqual(3, dds.shape[0])
        self.assertEqual(2., dds[0])
        dds = np.full((2,3), 2.)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(np.float64, dds.dtype)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(2., dds[0,0])
        dds = np.full_like(dds, 2.)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(np.float64, dds.dtype)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(2., dds[0,0])

        dds = np.full(3, 2, dtype=np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(np.float64, dds.dtype)
        self.assertEqual(1, dds.ndim)
        self.assertEqual(3, dds.shape[0])
        self.assertEqual(2., dds[0])
        dds = np.full((2,3), 2, dtype=np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(np.float64, dds.dtype)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(2., dds[0,0])
        dds = np.full_like(dds, 2, dtype=np.float)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(np.float64, dds.dtype)
        self.assertEqual(2, dds.ndim)
        self.assertEqual(2, dds.shape[0])
        self.assertEqual(3, dds.shape[1])
        self.assertEqual(2., dds[0,0])
        dds = np.full(np.array([1,2]), 2, dtype=np.float)
        self.assertEqual((1,2), dds.shape)
        self.assertEqual(2, dds[0,0])
        dds = np.full_like(np.array([1,2]), 2, dtype=np.float)
        self.assertEqual((2,), dds.shape)
        self.assertEqual(2, dds[0])

    def testCompound(self): # make new datasets with ones
        print("test compound")
#        dds = np.ndarrayCB(3, (3,4))
#        self.assertEqual(3, dds.itemsize, msg="itemsize incorrect")
        dds = np.zeros((3,5), np.rgb)
        print(type(dds), dds.dtype, dds.shape, dds.itemsize)
        self.assertEqual(6, dds.itemsize, msg="itemsize incorrect")
        dds = dds.red
        print(type(dds), dds.dtype, dds.shape)
        self.assertEqual(2, dds.itemsize, msg="itemsize incorrect")
        dds = np.zeros((3,4), np.cint32, elements=2)
        print(type(dds), dds.dtype, dds.shape)
        self.assertEqual(8, dds.itemsize, msg="itemsize incorrect")
        dds = np.zeros((3,4), np.cint32(2))
        print(type(dds), dds.dtype, dds.shape)
        self.assertEqual(8, dds.itemsize, msg="itemsize incorrect")

    def testArray(self): # make new datasets
        print("test array")
        dds = np.array(self.dz, np.float)
        self.assertEqual(self.dz, dds)
        self.assertEqual(dds, self.dz)
        self.assertEqual(dds.item(), self.dz)
        if isjava:
            self.assertEqual(1, dds.dtype.elements)
        self.assertEqual(8, dds.itemsize)
        dds = np.array(self.da, np.float)
        self.checkitems(self.da, dds)
        dds = np.array(self.db, np.float)
        self.checkitems(self.db, dds)
        dds = np.array(self.dc, np.float)
        self.checkitems(self.dc, dds)
        mds = np.array(self.m)
        self.checkitems(self.db, mds)
        zds = np.array(self.zz, np.complex)
        self.assertEqual(self.zz, zds)
        self.assertEqual(zds, self.zz)
        self.assertEqual(zds.item(), self.zz)
        self.assertEqual(zds.real.item(), 1.)
        self.assertEqual(zds.imag.item(), .5)
        self.assertEqual(np.real(zds), 1.)
        self.assertEqual(np.imag(zds), .5)

        zds = np.array([self.zz, self.zz], np.complex)
        self.assertEqual(self.zz, zds[0])
        self.assertRaises(ValueError, zds.item)
        if isjava:
            self.assertEqual(1, zds.dtype.elements)
        self.assertEqual(16, zds.itemsize)
        self.checkitems([1., 1.], zds.real)
        self.checkitems([.5, .5], zds.imag)
        self.checkitems([1., 1.], np.real(zds))
        self.checkitems([.5, .5], np.imag(zds))
        a = np.array([1+2j, 3+4j, 5+6j])
        a.real = np.array([2, 4, 6])
        self.checkitems([2, 4, 6], np.real(a))
        a.imag = np.array([8, 10, 12])
        self.checkitems([8, 10, 12], np.imag(a))
        zds = np.array(self.za, np.complex)
        self.checkitems(self.za, zds)
        zds = np.array(self.zb, np.complex)
        self.checkitems(self.zb, zds)
        zds = np.array(self.zc, np.complex)
        self.checkitems(self.zc, zds)
        ids = np.array(self.s, np.int)
        self.checkitems(self.s, ids)
        ids = np.array(self.a, np.int)
        self.checkitems(self.a, ids)
        ids = np.array(self.s, np.int8)
        self.checkitems(self.s, ids)
        ids = np.array(self.a, np.int8)
        self.checkitems(self.a, ids)
        ids = np.array(self.s, np.int16)
        self.checkitems(self.s, ids)
        ids = np.array(self.a, np.int16)
        self.checkitems(self.a, ids)
        ids = np.array(self.s, np.int64)
        self.checkitems(self.s, ids)
        ids = np.array(self.a, np.int64)
        self.checkitems(self.a, ids)

        tds = np.array(self.a)
        self.assertEqual(tds.dtype, np.int_)
        tds = np.array(self.m)
        self.assertEqual(tds.dtype, np.float64)
        tds = np.array(self.q)
        self.assertEqual(tds.dtype, np.float64)
        tds = np.array(self.r)
        self.assertEqual(tds.dtype, np.float64)
        tds = np.array(self.s)
        self.assertEqual(tds.dtype, np.int_)
        tds = np.array(self.t)
        self.assertEqual(tds.dtype, np.float64)
        tds = np.array(self.u)
        self.assertEqual(tds.dtype, np.float64)

        tds = np.array([np.arange(5), np.arange(5)+5])
        self.checkitems(np.arange(10).reshape(2,5), tds)

    def testLongArray(self):
        print("test long array")
        if isjava:
            global long # don't know why this is necessary!
        else:
            from past.builtins import long # requires future package
        l = long(12)
        dds = np.array([l])
        self.assertEqual(dds.dtype, np.int64)
        self.checkitems([12], dds)
        self.checkitems([l], dds)

    def testAsArray(self):
        ta = np.array([1, 2])
        ata = np.asarray(ta)
        self.assertEqual(ata.dtype, np.int_)
        self.checkitems([1, 2], ata)
        ata = np.asarray(ta, np.float)
        self.assertEqual(ata.dtype, np.float_)
        self.checkitems([1, 2], ata)

    def testSlicing(self):
        a = np.array([], dtype=np.float_)
        self.assertEqual(len(a), 0)
        a = np.zeros((2,))
        self.assertEqual(len(a), 2)
        self.checkitems([0,0], a[:])
        self.assertEqual(a[1], 0)
        a[:] = 0.5
        self.checkitems([0.5,0.5], a[:])

        a = np.zeros((2,3))
        self.assertEqual(len(a), 2)
        self.checkitems([0,0,0], a[0])
        a[1] = 0.2
        self.checkitems([0.2,0.2,0.2], a[1])
        a = np.zeros((2,3,4))
        self.assertEqual(len(a), 2)
        a = np.arange(10).reshape((5,2))
        a[3,:] = np.array([0,0])
        self.checkitems([0,0], a[3])
        a[3,:] = np.array([1,1]).reshape(1,2)
        self.checkitems([1,1], a[3])
        a[:2,1] = np.array([2,2])
        self.checkitems([2,2], a[:2,1])

        a = np.arange(7)
        c = list(range(7))
        self.checkitems(c[::2], a[::2])
        self.checkitems(c[0::2], a[0::2])
        self.checkitems(c[3::2], a[3::2])
        self.checkitems(c[6::2], a[6::2])
        self.checkitems(c[6:6:2], a[6:6:2])
        self.checkitems(c[7::2], a[7::2])
        self.checkitems(c[-1::2], a[-1::2])
        self.checkitems(c[-2::2], a[-2::2])
        self.checkitems(c[::-2], a[::-2])
        self.checkitems(c[0::-2], a[0::-2])
        self.checkitems(c[3::-2], a[3::-2])
        self.checkitems(c[6::-2], a[6::-2])
        self.checkitems(c[6:6:-2], a[6:6:-2])
        self.checkitems(c[7::-2], a[7::-2])
        self.checkitems(c[-1::-2], a[-1::-2])
        self.checkitems(c[-2::-2], a[-2::-2])
        self.checkitems(a[::-2], a[:-8:-2])

        a = np.arange(24).reshape(6,4)
        self.assertEqual((6,4), a[:].shape)
        self.assertEqual((6,4), a[:,].shape)
        self.assertEqual((6,3), a[:,:3].shape)
        self.assertEqual((0,3), a[4:2,:3].shape)
        self.assertEqual((6,), a[:,3].shape)
        self.assertEqual((0,), a[4:2,3].shape)

    def testArange(self):
        print("test arange")
        dds = np.arange(12, dtype=np.float)
        self.checkitems(list(range(12)), dds)
        dds = np.arange(2,12, dtype=np.int)
        self.checkitems(list(range(2,12)), dds)
        dds = np.arange(2,12,3)
        self.checkitems(list(range(2,12,3)), dds)
        dds = np.arange(12,2,-3)
        self.checkitems(list(range(12,2,-3)), dds)

    def testSpace(self):
        print("test space")
        dds = np.linspace(0, 11, 12)
        self.checkitems(list(range(12)), dds)
        dds = np.linspace(0, 11, 12, dtype=np.int32)
        self.checkitems(list(range(12)), dds, toInt)
        dds = np.linspace(0, 5, 11)
        self.checkitems([0.5*i for i in range(11)], dds)
        dds = np.linspace(0, 5, 11, dtype=np.int32)
        self.checkitems([i/2 for i in range(11)], dds, toInt)

        import math
        dds = np.logspace(0,2,5)
        self.checkitems([math.pow(10.,0.5*i) for i in range(5)], dds)
        dds = np.logspace(0,2,5, dtype=np.int32)
        self.checkitems([1, 3, 10, 31, 100], dds, toInt)

    def checkitemsadd(self, la, lb, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(convert(la[i])+convert(lb[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(convert(la[i][j])+convert(lb[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(convert(la[i][j][k])+convert(lb[i][j][k]), ds[i, j, k])

    def checkitemsaddconst(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(convert(la[i])+c, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(convert(la[i][j])+c, ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(convert(la[i][j][k])+c, ds[i, j, k])

    def testAdd(self):
        print("test add")
        da = np.array(self.db, np.int)
        dr = da + da
        self.checkitemsadd(self.db, self.db, dr, convert=toInt)
        dr = da.copy()
        dr += da
        self.checkitemsadd(self.db, self.db, dr, convert=toInt)
        dr = da + 1.2
        self.checkitemsaddconst(self.db, 1.2, dr, convert=toInt)
        dr = da.copy()
        dr += 1
        self.checkitemsaddconst(self.db, toInt(1.2), dr, convert=toInt)
        dr = da + da + 1.2
        self.checkitemsaddconst(self.db2, 1.2, dr, convert=toInt)

        da = np.array(self.db, np.float)
        dr = da + da
        self.checkitemsadd(self.db, self.db, dr)
        dr = da.copy()
        dr += da
        self.checkitemsadd(self.db, self.db, dr)
        dr = da + 1.2
        self.checkitemsaddconst(self.db, 1.2, dr)
        dr = da.copy()
        dr += 1.2
        self.checkitemsaddconst(self.db, 1.2, dr)
        
        za = np.array(self.zb, np.complex)
        zr = za + za
        self.checkitemsadd(self.zb, self.zb, zr)
        zr = za.copy()
        zr += za
        self.checkitemsadd(self.zb, self.zb, zr)
        zr = za + (1.3 + 0.2j)
        self.checkitemsaddconst(self.zb, (1.3 + 0.2j), zr)
        zr = za.copy()
        zr += (1.3 + 0.2j)
        self.checkitemsaddconst(self.zb, (1.3 + 0.2j), zr)

    def checkitemssub(self, la, lb, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(convert(la[i])-convert(lb[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(convert(la[i][j])-convert(lb[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(convert(la[i][j][k])-convert(lb[i][j][k]), ds[i, j, k])

    def checkitemssubconst(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(convert(la[i])-c, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(convert(la[i][j])-c, ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(convert(la[i][j][k])-c, ds[i, j, k])

    def checkitemssubconst2(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(convert(la[i]-c), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(convert(convert(la[i][j])-c), ds[i, j],
                                      msg="%d, %d : %r %r" % (i,j, convert(convert(la[i][j])-c), ds[i, j]))
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(convert(la[i][j][k]-c), ds[i, j, k])

    def testSub(self):
        print("test sub")
        da = np.array(self.db, np.int)
        dr = da - da
        self.checkitemssub(self.db, self.db, dr, convert=toInt)
        dr = da.copy()
        dr -= da
        self.checkitemssub(self.db, self.db, dr, convert=toInt)
        dr = da - 1.2
        self.checkitemssubconst(self.db, 1.2, dr, convert=toInt)
#         dr = da.copy()
#         print 'Before', dr[1,0]
#         dr -= 1.2
#         print 'After', dr[1,0]
#         self.checkitemssubconst2(self.db, 1.2, dr, convert=toInt)

        da = np.array(self.db, np.float)
        dr = da - da
        self.checkitemssub(self.db, self.db, dr)
        dr = da.copy()
        dr -= da
        self.checkitemssub(self.db, self.db, dr)
        dr = da - 1.2
        self.checkitemssubconst(self.db, 1.2, dr)
        dr = 1.2 - da
        self.checkitemssubconst(self.db, 1.2, 0 - dr)
        dr = da.copy()
        dr -= 1.2
        self.checkitemssubconst2(self.db, 1.2, dr)
        
        za = np.array(self.zb, np.complex)
        zr = za - za
        self.checkitemssub(self.zb, self.zb, zr)
        zr = za.copy()
        zr -= za
        self.checkitemssub(self.zb, self.zb, zr)
        zr = za - (1.3 + 0.2j)
        self.checkitemssubconst(self.zb, (1.3 + 0.2j), zr)
        zr = za.copy()
        zr -= (1.3 + 0.2j)
        self.checkitemssubconst2(self.zb, (1.3 + 0.2j), zr)

    def checkitemsdiv(self, la, lb, ds, convert=toAny, iscomplex=False):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                x = convert(la[i])/convert(lb[i]+1.)
                if iscomplex:
                    x = complex(x)
                self.assertAlmostEqual(x, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    x = convert(convert(la[i][j])/convert(lb[i][j]+1.))
                    z = ds[i, j]
                    if iscomplex:
                        x = complex(x)
                        self.assertAlmostEqual(x.real, z.real)
                        self.assertAlmostEqual(x.imag, z.imag)
                    else:
                        self.assertAlmostEqual(x, z)

        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertAlmostEqual(convert(la[i][j][k])/convert(lb[i][j][k]+1.), ds[i, j, k])

    def checkitemsdivconst(self, la, c, ds, convert=toAny, iscomplex=False):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                try:
                    iter(la)
                    d = convert(la[i])
                except:
                    d = la
                try:
                    iter(c)
                    n = convert(c[i]+1.)
                except:
                    n = c
                self.assertAlmostEqual(d/n, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    try:
                        iter(la)
                        d = convert(la[i][j])
                    except:
                        d = la
                    try:
                        iter(c)
                        n = convert(c[i][j]+1.)
                    except:
                        n = c
                    x = d/n
                    z = ds[i, j]
                    if iscomplex:
                        x = complex(x)
                        self.assertAlmostEqual(x.real, z.real)
                        self.assertAlmostEqual(x.imag, z.imag)
                    else:
                        self.assertAlmostEqual(x, z,
                                      msg="%d, %d : %r %r" % (i,j, x, z))
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        try:
                            iter(la)
                            d = convert(la[i][j][k])
                        except:
                            d = la
                        try:
                            iter(c)
                            n = convert(c[i][j][k]+1.)
                        except:
                            n = c
                        self.assertAlmostEqual(d/n, ds[i, j, k])

    def checkitemsdivconst2(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                try:
                    iter(la)
                    d = convert(la[i])
                except:
                    d = la
                try:
                    iter(c)
                    n = convert(c[i]+1.)
                except:
                    n = c
                self.assertEqual(d/n, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    try:
                        iter(la)
                        d = convert(la[i][j])
                    except:
                        d = la
                    try:
                        iter(c)
                        n = c[i][j]+1.
                    except:
                        n = c
                    self.assertEqual(convert(d/n), ds[i, j],
                                      msg="%d, %d : %r %r" % (i,j, convert(d/n), ds[i, j]))
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        try:
                            iter(la)
                            d = convert(la[i][j][k])
                        except:
                            d = la
                        try:
                            iter(c)
                            n = convert(c[i][j][k]+1.)
                        except:
                            n = c
                        self.assertEqual(d/n, ds[i, j, k])

    def testDiv(self):
        print("test div")
        da = np.array(self.db, np.int)
        dr = da // (da+1)
        self.checkitemsdiv(self.db, self.db, dr, convert=toInt)
        dr = da.copy()
        dr //= da + 1
        self.checkitemsdiv(self.db, self.db, dr, convert=toInt)
        dr = da / 1.2
        self.checkitemsdivconst(self.db, 1.2, dr, convert=toInt)
#         dr = da.copy()
#         dr /= 1.2
#         self.checkitemsdivconst2(self.db, 1.2, dr, convert=toInt)

        da = np.array(self.db, np.float)
        dr = da / (da+1)
        self.checkitemsdiv(self.db, self.db, dr)
        dr = da.copy()
        dr /= da + 1
        self.checkitemsdiv(self.db, self.db, dr)
        dr = da / 1.2
        self.checkitemsdivconst(self.db, 1.2, dr)
        dr = 1.2 / (da+1)
        self.checkitemsdivconst(1.2, self.db, dr)
        dr = da.copy()
        dr /= 1.2
        self.checkitemsdivconst(self.db, 1.2, dr)
        
        za = np.array(self.zb, np.complex)
        zr = za / (za + 1)
        self.checkitemsdiv(self.zb, self.zb, zr, iscomplex=True)
        zr = za.copy()
        zr /= za + 1
        self.checkitemsdiv(self.zb, self.zb, zr, iscomplex=True)
        zr = za / (1.3 + 0.2j)
        self.checkitemsdivconst(self.zb, (1.3 + 0.2j), zr, iscomplex=True)
        zr = za.copy()
        zr /= (1.3 + 0.2j)
        self.checkitemsdivconst(self.zb, (1.3 + 0.2j), zr, iscomplex=True)

        a = np.arange(-4, 4, dtype=np.int8)
        import sys
        py3 = sys.hexversion >= 0x03000000
        if py3:
            self.checkitems([-2, -1.5, -1, -0.5,  0,  0.5,  1,  1.5], a / 2)
            self.checkitems([2,  1.5,  1,  0.5,  0, -0.5, -1, -1.5], a / -2)
        else:
            self.checkitems([-2, -2, -1, -1,  0,  0,  1,  1], a / 2)
            self.checkitems([2,  1,  1,  0,  0, -1, -1, -2], a / -2)
        self.checkitems([-2, -2, -1, -1,  0,  0,  1,  1], a // 2)
        self.checkitems([0, 1, 0, 1, 0, 1, 0, 1], a % 2)
        self.checkitems([2,  1,  1,  0,  0, -1, -1, -2], a // -2)
        self.checkitems([0, -1,  0, -1,  0, -1,  0, -1], a % -2)
        self.checkitems([-1.6, -1.2, -0.8, -0.4,  0. ,  0.4,  0.8,  1.2], a / 2.5)
        self.checkitems([-2., -2., -1., -1.,  0.,  0.,  0.,  1.], a // 2.5)
        self.checkitems([1., 2., 0.5, 1.5, 0., 1., 2., 0.5], a % 2.5)
        self.checkitems([1.6, 1.2, 0.8, 0.4, -0., -0.4, -0.8, -1.2], a / -2.5)
        self.checkitems([1., 1., 0., 0., -0., -1., -1., -2.], a // -2.5)
        self.checkitems([-1.5, -0.5, -2., -1., 0., -1.5, -0.5, -2.], a % -2.5)

    def testCopyItems(self):
        if isjava:
            import jarray
            print("test copy items")
            da = np.array(self.db, np.float)
            jda = da._jdataset()
            ta = np.zeros(da.shape)
            jda.copyItemsFromAxes(None, None, ta._jdataset())
            print(ta.data)
            ta = np.array(self.m)
            jda.setItemsOnAxes(None, None, ta.data)
            print(da.data)
    
            print('2')
            da = np.array(self.db, np.float)
            jda = da._jdataset()
            ta = np.zeros((2,))
            axes = [ True, False ]
            jda.copyItemsFromAxes(None, axes, ta._jdataset())
            print(ta.data)
            ta = jarray.array([ -2, -3.4 ], 'd')
            jda.setItemsOnAxes(None, axes, ta)
            print(da.data)
    
            print('3')
            da = np.array(self.db, np.float)
            jda = da._jdataset()
            ta = np.zeros((2,))
            axes = [ False, True ]
            jda.copyItemsFromAxes(None, axes, ta._jdataset())
            print(ta.data)
            ta = jarray.array([ -2.5, -3.4 ], 'd')
            jda.setItemsOnAxes(None, axes, ta)
            print(da.data)

    def testDatasetSlice(self):
        print('test slicing with start and stop')
        ds = np.arange(16)
        dr = ds[2:10]
        self.checkitems(list(range(2,10)), dr)

        print('test slicing with steps')
        dr = ds[::2]
        self.checkitems(list(range(0,16,2)), dr)

        print('test slicing 3D')
        dr = self.dda[1:,::2,1::2]
        self.checkitems(self.t, dr)

        print('test putting in a 3D slice')
        dr = self.dda.copy()
        dr[:,1::2,::2] = 7.
        self.checkitems(self.u, dr)
 
        print('test putting a list in a 3D slice')
        dr = self.dda.copy()
        print(dr[:,1::2,::2].shape)
        dr[:,1::2,::2] = np.array([7., 7]).reshape(2,1,1)
        self.checkitems(self.u, dr)

        print('test slicing with ellipse')
        dr = ds[...]
        self.checkitems(list(range(16)), dr)

        print('test slicing 3D with ellipse')
        dr = self.dda[...,1::2]
        self.checkitems(self.t, dr[1:, ::2])

        print('test putting in a 3D slice with ellipsis')
        dr = self.dda.copy()
        dr[...,1::2,::2] = 7.
        self.checkitems(self.u, dr)
 
        print('test putting a list in a 3D slice with ellipsis')
        dr = self.dda.copy()
        print(dr[...,1::2,::2].shape)
        dr[...,1::2,::2] = np.array([7., 7]).reshape(2,1,1)
        self.checkitems(self.u, dr)

        print('test slice shape reduction')
        dr = np.arange(120).reshape(4,3,5,2)
        s = dr[:2,...,1:].shape
        print(s)
        self.assertEqual(s, (2,3,5,1))
        s = dr[:2,...,1].shape
        print(s)
        self.assertEqual(s, (2,3,5))
        try:
            s = dr[:2,...,...,1].shape
            raise AssertionError('Should have raised an error')
        except:
            pass

        print('test newaxis')
        dr = np.arange(15).reshape(3,5)
        s = dr[np.newaxis,:,1:].shape
        print(s)
        self.assertEqual(s, (1,3,4))
        s = dr[np.newaxis,...].shape
        print(s)
        self.assertEqual(s, (1,3,5))
        s = dr[np.newaxis,...,np.newaxis].shape
        print(s)
        self.assertEqual(s, (1,3,5,1))
        s = dr[...,np.newaxis].shape
        print(s)
        self.assertEqual(s, (3,5,1))
        s = dr[:,np.newaxis,...,np.newaxis].shape
        print(s)
        self.assertEqual(s, (3,1,5,1))
        a = np.zeros((5,4))
        b = np.arange(5.)
        a[:,2] = b
        self.checkitems(b, a[:,2])
        a.fill(0)
        try:
            b.shape = (5,1)
            a[:,2] = b
            raise AssertionError('Should have raised an error')
        except:
            pass
        try:
            b.shape = (1,5,1)
            a[:,2] = b
            raise AssertionError('Should have raised an error')
        except:
            pass

    def testTake(self):
        print('test take')
        ds = np.arange(16)
        self.checkitems([2, 5], ds.take([2, 5]))
        self.checkitems([2, 5], ds.take(np.array([2, 5])))
        ds = np.arange(16).reshape(4,4)
        self.checkitems([2, 5], ds.take([2, 5]))
        self.checkitems([[4, 5, 6, 7], [12, 13, 14, 15]], ds.take([1, 3], 0))
        self.checkitems([[1, 3], [5, 7], [9, 11], [13, 15]], ds.take([1, 3], 1))

    def testPut(self):
        print('test put')
        ds = np.arange(6.)
        ds.put([2, 5], [-2, -5.5])
        self.checkitems([0, 1, -2, 3, 4, -5.5], ds)
        ds.put(np.array([0, 4]), [-2, -5.5])
        self.checkitems([-2, 1, -2, 3, -5.5, -5.5], ds)
        ds = np.arange(6.).reshape(2,3)
        ds.put([2, 5], [-2, -5.5])
        self.checkitems([[0, 1, -2], [3, 4, -5.5]], ds)
        ds.put(np.array([0, 4]), [-2, -5.5])
        self.checkitems([[-2, 1, -2], [3, -5.5, -5.5]], ds)

    def testArgs(self):
        print('test arg maxs')
        ds = np.array([[[1., 0., 3.], [.5, 2.5, 2.]], [[0., 3., 1.], [1.5, 2.5, 2.]]])
        self.assertEqual(ds.argmax(), 2)
        self.checkitems([[0, 1, 0], [1, 0, 0]], ds.argmax(0))
        self.checkitems([[0, 1, 0], [1, 0, 1]], ds.argmax(1))
        self.checkitems([[0, 1, 0], [1, 0, 1]], ds.argmax(-2))
        self.checkitems([[2, 1], [1, 1]], ds.argmax(2))

        print('test arg mins')
        self.assertEqual(ds.argmin(), 1)
        self.checkitems([[1, 0, 1], [0, 0, 0]], ds.argmin(0))
        self.checkitems([[1, 0, 1], [0, 1, 0]], ds.argmin(1))
        self.checkitems([[1, 0, 1], [0, 1, 0]], ds.argmin(-2))
        self.checkitems([[1, 0], [0, 0]], ds.argmin(2))

    def testProds(self):
        print('test prod')
        ds = np.arange(12).reshape(3,4)
        self.assertEqual(np.prod(ds), 0)
        self.checkitems([ 0, 45, 120, 231], np.prod(ds, 0))
        self.checkitems([ 0, 45, 120, 231], np.prod(ds, -2))
        self.checkitems([ 0, 840, 7920], np.prod(ds, 1))
        self.checkitems([ 0, 840, 7920], np.prod(ds, -1))
        
        print('test cumprod')
        self.checkitems([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], np.cumprod(ds))
        self.checkitems([[0, 1, 2, 3], [0, 5, 12, 21], [0, 45, 120, 231]], np.cumprod(ds, 0))
        self.checkitems([[0, 1, 2, 3], [0, 5, 12, 21], [0, 45, 120, 231]], np.cumprod(ds, -2))
        self.checkitems([[0, 0, 0, 0], [4, 20, 120, 840], [8, 72, 720, 7920]], np.cumprod(ds, 1))
        self.checkitems([[0, 0, 0, 0], [4, 20, 120, 840], [8, 72, 720, 7920]], np.cumprod(ds, -1))

    def testSums(self):
        print('test sum')
        ds = np.arange(12).reshape((3,4))
        self.assertEqual(ds.sum(), 66)
        self.assertEqual(np.sum(ds), 66)
        self.checkitems([12, 15, 18, 21], ds.sum(0))
        self.checkitems([12, 15, 18, 21], ds.sum(-2))
        self.checkitems([12, 15, 18, 21], np.sum(ds, 0))
        self.checkitems([12, 15, 18, 21], np.sum(ds, -2))
        self.checkitems([ 6, 22, 38], ds.sum(1))
        self.checkitems([ 6, 22, 38], ds.sum(-1))
        self.checkitems([ 6, 22, 38], np.sum(ds, 1))
        self.checkitems([ 6, 22, 38], np.sum(ds, -1))
        lds = np.arange(1024*1024, dtype=np.int32)
        self.assertEqual(np.sum(lds, dtype=np.int32), -524288)
        self.assertEqual(np.sum(lds, dtype=np.int64), 549755289600)

        print('test cumsum')
        self.checkitems([0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66], np.cumsum(ds))
        self.checkitems([[0, 1, 2, 3], [4, 6, 8, 10], [12, 15, 18, 21]], np.cumsum(ds, 0))
        self.checkitems([[0, 1, 3, 6], [4, 9, 15, 22], [8, 17, 27, 38]], np.cumsum(ds, 1))
        self.checkitems([[0, 1, 3, 6], [4, 9, 15, 22], [8, 17, 27, 38]], np.cumsum(ds, -1))

    def testStats(self):
        print('test stats')
        a = np.arange(12.).reshape(4,3)
        self.assertEqual(11, a.argmax())
        self.assertEqual(0, a.argmin())
        self.assertEqual(11., a.max())
        self.assertEqual(0., a.min())
        self.assertEqual(5.5, a.mean())
        self.assertAlmostEqual(11.9166666667, a.var())
        self.assertAlmostEqual(3.45205252953, a.std())
        self.assertAlmostEqual(66., a.sum())
        self.assertAlmostEqual(0., a.prod())

        self.checkitems([2, 2, 2, 2], a.argmax(1))
        self.checkitems([0, 0, 0, 0], a.argmin(1))
        self.checkitems([2.,5.,8.,11.], a.max(1))
        self.checkitems([2.,5.,8.,11.], a.max(-1))
        self.checkitems([0., 3., 6., 9.], a.min(1))
        self.checkitems([0., 3., 6., 9.], a.min(-1))
        self.checkitems([1., 4., 7., 10.], a.mean(1))
        self.checkitems([1., 4., 7., 10.], a.mean(-1))
        b = 2./3
        self.checkitems([b, b, b, b], a.var(1))
        self.checkitems([b, b, b, b], a.var(-1))
        self.checkitems([1., 1., 1., 1.], a.var(1, ddof=1))
        self.checkitems([1., 1., 1., 1.], a.var(-1, ddof=1))
        b = np.sqrt(b)
        self.checkitems([b, b, b, b], a.std(1))
        self.checkitems([b, b, b, b], a.std(-1))
        self.checkitems([1., 1., 1., 1.], a.std(1, ddof=1))
        self.checkitems([1., 1., 1., 1.], a.std(-1, ddof=1))
        self.checkitems([3., 12., 21., 30.], a.sum(1))
        self.checkitems([3., 12., 21., 30.], a.sum(-1))
        self.checkitems([0., 60., 336., 990.], a.prod(1))
        self.checkitems([0., 60., 336., 990.], a.prod(-1))
        # a.rms()  
        # a.rms(1) 

    def testGDA2270(self):
        print('test negative indices')
        ds = np.arange(10.)
        self.assertEqual(8, ds[-2])
        ds[-2] = 0
        self.assertEqual(0, ds[8])
        ds = np.arange(10.).reshape(2,5)
        du = np.arange(5.,10)
        dt = ds[-1]
        for i in range(du.shape[0]):
            self.assertEqual(dt[i], du[i])
        ds[-1] = -2
        for i in range(ds.shape[1]):
            self.assertEqual(ds[-1,i], -2)

    def testGDA2271(self):
        print('test out of bound indices')
        ds = np.arange(10.)
        def getf(ind):
            return ds[ind]
        def setf(ind, value):
            ds[ind] = value

        self.assertRaises(IndexError, getf, 10)
        self.assertRaises(IndexError, getf, -11)

        if isjava:
#            ds.extendible = False
            self.assertRaises(IndexError, setf, 11, 0.)
            self.assertRaises(IndexError, setf, -11, 0.)
#            ds.extendible = True
#            ds[10] = 0.

    def testBooleanMethods(self):
        print('test boolean methods')
        a = np.array([True, False])

        self.checkitems([False, False], a & False)
        b = a.copy()
        b &= False
        self.checkitems([False, False], b)
        self.checkitems(a, a & True)
        b = a.copy()
        b &= True
        self.checkitems(a, b)

        self.checkitems([True, False], a | False)
        b = a.copy()
        b |= False
        self.checkitems([True, False], b)
        self.checkitems([True, True], a | True)
        b = a.copy()
        b |= True
        self.checkitems([True, True], b)

        self.checkitems([True, False], a ^ False)
        b = a.copy()
        b ^= False
        self.checkitems([True, False], b)
        self.checkitems([False, True], a ^ True)
        b = a.copy()
        b ^= True
        self.checkitems([False, True], b)

    def testBooleanAccessors(self):
        print('test boolean get and set')
        tm = np.array(self.mm)
        c = tm > 11.6
        lc = [ [[False, False], [ False, True]], [[True, True], [True, True]]]
        self.checkitems(lc, c)

        d = tm[c]
        self.checkitems([ 12., 20., 30., 42., 56. ], d)

        d = tm[tm > 11.6]
        self.checkitems([ 12., 20., 30., 42., 56. ], d)

        d = tm[np.logical_not(c)]
        self.checkitems([ 0, 2., 6 ], d)

        # all false (should be zero shape)
        d = tm[tm > tm.max()]
        print('all false', d)
        self.checkitems([], d)

        ta = tm.copy()
        ta[c] = -2.3
        self.checkitems([ [[0., 2.], [6., -2.3]], [[-2.3, -2.3], [-2.3, -2.3]] ], ta)

        tm[tm > 11.6] = -2.3
        self.checkitems([ [[0., 2.], [6., -2.3]], [[-2.3, -2.3], [-2.3, -2.3]] ], tm)

        tm[np.logical_not(c)] = -3.3
        self.checkitems([ [[-3.3, -3.3], [-3.3, -2.3]], [[-2.3, -2.3], [-2.3, -2.3]] ], tm)

    def testInteger(self):
        print('test integer get and set')
        tm = np.array(self.mm).flatten()

        d = tm[np.array([2, 4, 7])]
        self.checkitems([ 6., 20., 56. ], d)

        tm[np.array([3, 4, 5, 6, 7])] = -2.3
        self.checkitems([ 0., 2., 6., -2.3, -2.3, -2.3, -2.3, -2.3 ], tm)

    def testIntegers(self):
        print('test integers get and set')
        a = np.array([8,9,10,11,12,13])
        d = a[np.where(a > 10)]
        self.checkitems([11, 12, 13], d)

        a.shape = 3,2
        d = a[np.where(a > 10)]
        self.checkitems([11, 12, 13], d)

        a[np.where(a > 10)] = -1
        self.checkitems([[8,9],[10,-1], [-1, -1]], a)

        # fancy indexing
        a = np.array([8,9,10,11,12,13])
        a.shape = 3,2
        self.assertEqual(13, a[(2,1)])
        d = a[(2,1),]
        self.checkitems([[12, 13], [10, 11]], d)

        tm = np.array(self.mm)
        d = tm[[1]]
        self.checkitems([[[20., 30.], [42., 56.]]], d)
 
        d = tm[np.array([0, 1]), np.array([1, 1])]
        self.checkitems([[6.,  12.], [42.,  56.]], d)
 
        d = tm[np.array([0, 1]), np.array([1, 1]), :1]
        self.checkitems([[6.], [42.]], d)
 
        d = tm[np.array([[0, 1], [1, 1]]), np.array([[1, 1], [1, 0]]), :1]
        self.checkitems([[[6.], [42.]], [[42.], [20.]]], d)
 
        d = tm[[[0, 1], [1, 1]], [[1, 1], [1, 0]], :1]
        self.checkitems([[[6.], [42.]], [[42.], [20.]]], d)
 
        tm[np.array([[0, 1], [1, 1]]), np.array([[1, 1], [1, 0]]), :1] = -2.3
        self.checkitems([[[0., 2.], [-2.3, 12.]], [[-2.3, 30.], [-2.3, 56.]]], tm)

        # fancy with booleans too
        tm = np.array(self.mm)
        d = tm[np.array([0, 1]), np.array([False, True]), :1]
        self.checkitems([[6.], [42.]], d)

        # TODO broadcasting with lower ranked booleam
        d = np.arange(8).reshape(4,2)
        m = np.array([False, True, True, False])
        print('broadcast', d[m])
        self.checkitems([[2, 3], [4, 5]], d[m])

#         m = np.array([False, False, False, False])
#         f = d[m]
        # should be (0,2)
#         print 'broadcast', f, f.shape
        
    def testSelect(self):
        print('test select')
        tm = np.select([np.array([[[False, True], [True, False]], [[True, True], [False, False]]])], [np.array(self.mm)], -2.3)
        self.checkitems([ [[-2.3, 2.], [6., -2.3]], [[20., 30.], [-2.3, -2.3]] ], tm)

    def testWhere(self):
        print('test where')
        t = np.array([[[-0.5, 0.1], [2, 0]], [[1.3, 1.4], [-10, -20]]])
        tm = np.where(t > 0, np.array(self.mm), -2.3)
        self.checkitems([ [[-2.3, 2.], [6., -2.3]], [[20., 30.], [-2.3, -2.3]] ], tm)

    def testPrint(self):
        print('test print')
        a = np.arange(10)
        print(type(a))
        print(a)

    def testMean(self):
        print('test mean')
        a = np.arange(10).reshape(2,5)
        self.assertEqual(4.5, a.mean())
        self.checkitems([2.5, 3.5, 4.5, 5.5, 6.5], a.mean(0))
        self.checkitems([2., 7.], a.mean(1))
        self.checkitems([2., 7.], a.mean(-1))

    def testMaxMin(self):
        print('test max/min')
        a = np.arange(10, dtype=np.float).reshape(2,5)
        self.assertEqual(0., a.min())
        self.checkitems([0., 1., 2., 3., 4.], a.min(0))
        self.checkitems([0., 5.], a.min(1))
        self.checkitems([0., 5.], a.min(-1))
        self.assertEqual(0, a.argmin())
        self.checkitems([0, 0, 0, 0, 0], a.argmin(0))
        self.checkitems([0, 0], a.argmin(1))
        self.checkitems([0, 0], a.argmin(-1))
        if isjava:
            self.checkitems([0., 1., 2., 3., 4.], a.min(0, True))
            self.checkitems([0., 5.], a.min(1, True))
            self.checkitems([0., 5.], a.min(-1, True))
            self.checkitems([0, 0, 0, 0, 0], a.argmin(0, True))
            self.checkitems([0, 0], a.argmin(1, True))
            self.checkitems([0, 0], a.argmin(-1, True))

        self.assertEqual(9., a.max())
        self.checkitems([5., 6., 7., 8., 9.], a.max(0))
        self.checkitems([4., 9.], a.max(1))
        self.checkitems([4., 9.], a.max(-1))
        self.assertEqual(9, a.argmax())
        self.checkitems([1, 1, 1, 1, 1], a.argmax(0))
        self.checkitems([4, 4], a.argmax(1))
        self.checkitems([4, 4], a.argmax(-1))
        if isjava:
            self.checkitems([5., 6., 7., 8., 9.], a.max(0, True))
            self.checkitems([4., 9.], a.max(1, True))
            self.checkitems([4., 9.], a.max(-1, True))
            self.checkitems([1, 1, 1, 1, 1], a.argmax(0, True))
            self.checkitems([4, 4], a.argmax(1, True))
            self.checkitems([4, 4], a.argmax(-1, True))

    def testIterate(self):
        print('test iterate')
        l = [v for v in np.arange(3)]
        self.assertEqual([0, 1, 2], l)

    def testCopy(self):
        print('test copy')
        a = np.array(self.q)
        self.checkitems(self.q, np.copy(a))

    def testClip(self):
        a = np.arange(8, dtype=np.float)
        answer = [2., 2., 2., 3., 4., 5., 5.5, 5.5, ]
        self.checkitems(answer, np.clip(a, 2., 5.5))
        r = np.zeros((8,), dtype=a.dtype)
        np.clip(a, 2., 5.5, r)
        self.checkitems(answer, r)

    def testMaxMinSelect(self):
        a = np.array([2., 2., 2., 3., 4., 5., 5.5, 5.5])
        b = np.array([2.5, 1., 2., 3.5, -4., 65., 5.5, 15.5])
        abmin = [2.,1.,2., 3.,-4.,5.,5.5,5.5]
        self.checkitems(abmin, np.minimum(a, b))
        r = np.zeros((8,), dtype=a.dtype)
        np.minimum(a, b, r)
        self.checkitems(abmin, r)

        abmax = [2.5,2.,2., 3.5,4.,65.,5.5,15.5]
        self.checkitems(abmax, np.maximum(a, b))
        np.maximum(a, b, r)
        self.checkitems(abmax, r)

if __name__ == '__main__':
    unittest.main(verbosity=2)
