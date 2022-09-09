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
Test rudimentary aspects of scisoft package

import unittest
unittest.TestProgram(argv=["scisoft_test"])
'''
from __future__ import print_function
import unittest
import scisoftpy as np

class Test(unittest.TestCase):

    def setUp(self):
        pass
#        import pydevd
#        pydevd.settrace(stdoutToServer=True, stderrToServer=True)

    def checkitems(self, la, ds):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertAlmostEqual(la[i], ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertAlmostEqual(la[i][j], ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertAlmostEqual(la[i][j][k], ds[i, j, k])

    def testStrAndRepr(self):
        print('String and repr testing')
        a = np.arange(6, dtype=np.int32)
        print(str(a), repr(a))
        a = np.arange(6, dtype=np.float)
        print(str(a), repr(a))
        a = np.array([4,3.])
        print(str(a), repr(a))

    def testMethods(self):
        print('Methods testing')
        print(np.arange(6, dtype=np.int32))
        print(np.arange(6, dtype=np.float))
        a = np.array([4,3.])
        print(type(a))
        print(np.sort(a, None))
        self.checkitems([3.,4], np.sort(a, None))
        print(a.sort())
        self.checkitems([3.,4], a)
        a = np.array([4,3.])
        self.checkitems([1,0], a.argsort())
        self.checkitems([1,0], np.argsort(a, None))
        a = np.arange(6, dtype=np.float)
        print(a.take([0, 2, 4]))
        print(a.take([0, 2, 4], 0))
        d = np.take(a, [0, 2, 4], 0)
        print(type(d), d)
        d = np.diag([0, 2, 3])
        print(type(d), d)
        a.shape = 2,3
        self.checkitems([1,2], a.take([1,2]))
        self.checkitems([[1,2], [4,5]], a.take([1,2],1))
        self.checkitems([0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5], a.repeat(2))
        self.checkitems([[0, 1, 2], [0, 1, 2], [3, 4, 5], [3, 4, 5]], a.repeat(2, axis=0))
        self.checkitems([[0, 0, 1, 1, 2, 2], [3, 3, 4, 4, 5, 5]], a.repeat(2, axis=1))
#        print a.sort()

    def testRepeat(self):
        self.checkitems([3,3,3,3], np.repeat(3, 4))
        self.checkitems([1,1,1,2,2,2,3,3,3], np.repeat([1,2,3], 3))
        self.checkitems([1,1,2,2,3,3,4,4,5,5,6,6], np.repeat([[1,2,3],[4,5,6]], 2))
        self.checkitems([[1,2,3],[1,2,3],[4,5,6],[4,5,6]], np.repeat([[1,2,3],[4,5,6]], 2, 0))
        self.checkitems([[1,1,2,2,3,3],[4,4,5,5,6,6]], np.repeat([[1,2,3],[4,5,6]], 2, 1))

    def testTile(self):
        self.checkitems([3,3,3,3], np.tile(3, 4))
        self.checkitems([1,2,3,1,2,3,1,2,3], np.tile([1,2,3], 3))
        self.checkitems([[1,2,3],[1,2,3],[1,2,3]], np.tile([1,2,3], [3,1]))
        self.checkitems([[1,2,1,2],[1,2,1,2]], np.tile([1,2], [2,2]))
        self.checkitems([[[1,2,1,2]],[[1,2,1,2]]], np.tile([1,2], [2,1,2]))
        self.checkitems([[1,2,1,2],[3,4,3,4]], np.tile([[1,2],[3,4]], 2))
        self.checkitems([[1,2],[3,4],[1,2],[3,4]], np.tile([[1,2],[3,4]], [2,1]))

    def testScisoft(self):
        a = np.ones([3,4])
        print(a.shape)
        a.shape = [2,6]
        print(a.shape)
        a.shape = 12
        print(a.shape)
        a.shape = (2,6)
        print(a.shape)
        print(a)
        print(a*2)
        b = np.arange(12)
        print(b)
        print(b[0])
        b[2] = 2.3
        print(b[1:8:3])
        b[6:2:-1] = -2.1
        b.shape = [2,6]
        print(b + 2)
        print(2 + b)
        b += 2
        print(b[1,3])
        b[0,5] = -2.3
        print(b[0,2:5])
        b[:,1] = -7.1
        print(b)
        try:
            c = np.add(a, b)
            print(c)
        except:
            print("Failed with an IAE as expected")

    def testReshape(self):
        print('Reshape testing')
        a = np.arange(10.)
        self.assertEqual((2,5), a.reshape(2,5).shape)
        self.assertEqual((2,5), a.reshape((2,5)).shape)
        self.assertEqual((5,2), a.reshape(5,-1).shape)

    def testSqueeze(self):
        print('Squeeze testing')
        a = np.arange(10.).reshape(2,5)
        self.assertTrue(a is a.squeeze())
        a.shape = 2,1,5
        self.assertFalse(a is a.squeeze())
        self.assertEqual((2,5), a.squeeze().shape)

    def testResize(self):
        print('Resize testing')
        a = np.arange(10.)
        a.resize(12, refcheck=False)
        self.checkitems([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0], a)
        print(a)
        a = np.arange(10.)
        a.resize((2,6), refcheck=False)
        self.checkitems([[0, 1, 2, 3, 4, 5], [6, 7, 8, 9, 0, 0]], a)
        print(a)
        a = np.arange(10.)
        a.resize(2,6, refcheck=False)
        self.checkitems([[0, 1, 2, 3, 4, 5], [6, 7, 8, 9, 0, 0]], a)
        print(a)
        a = np.arange(6.)
        self.checkitems([0, 1, 2, 3, 4], np.resize(a, 5))
        self.checkitems([0, 1, 2, 3, 4, 5, 0, 1, 2], np.resize(a, 9))
        self.checkitems([[0, 1, 2], [3, 4, 5]], np.resize(a, (2,3)))
        self.checkitems([[0, 1, 2], [3, 4, 5], [0, 1, 2]], np.resize(a, (3,3)))

    def testCompounds(self):
        a = np.arange(24).reshape(3,4,2)
        oa = np.compoundarray(a)
        ca = oa.copy()
        self.assertEqual(ca.shape[0], 3)
        self.assertEqual(ca.shape[1], 4)
        la = ca[1,2]
        print(la)
        self.assertEqual(la[0], 12)
        self.assertEqual(la[1], 13)
        ca[2,2] = (0,0)
        self.assertEqual(ca[2,2][0], 0)
        self.assertEqual(ca[2,2][1], 0)
        ca[2,2] = (-2, 1)
        self.assertEqual(ca[2,2][0], -2)
        self.assertEqual(ca[2,2][1], 1)
        ca[1:,3:] = (-1,-1)
        self.assertEqual(ca[2,3][0], -1)
        self.assertEqual(ca[2,3][1], -1)
        ca[1:,3:] = (3,-4)
        self.assertEqual(ca[2,3][0], 3)
        self.assertEqual(ca[2,3][1], -4)

        ia = np.array([2, -3])
        print('Integer index testing')
        ca = oa.copy()
        print(ca)
        cb = ca[ia]
        print(cb)
        self.assertEqual(cb.shape[0], 2)
        self.assertEqual(cb[0,0][0], 16)
        self.assertEqual(cb[0,0][1], 17)
        self.assertEqual(cb[1,0][0], 0)
        self.assertEqual(cb[1,0][1], 1)
        print(np.compoundarray(np.array([1,2])))
        ca[ia] = np.compoundarray(np.array([1,2]))
        self.assertEqual(ca[0,2][0], 1)
        self.assertEqual(ca[0,2][1], 2)
        self.assertEqual(ca[1,1][0], 10)
        self.assertEqual(ca[1,1][1], 11)

        ca[ia] = (1,2) # this works too

        print('Boolean index testing')
        ba = np.array([[0, 0, 1, 0], [1, 0, 0, 0], [0, 1, 0, 1]], dtype=np.bool)
        ca = oa.copy()
        cc = ca[ba]
        # index dataset does not work
        # test boolean too
        print(cc)
        self.assertEqual(cc.shape[0], 4)
        self.assertEqual(cc[0][0], 4)
        self.assertEqual(cc[0][1], 5)
        self.assertEqual(cc[1][0], 8)
        self.assertEqual(cc[1][1], 9)
        self.assertEqual(cc[2][0], 18)
        self.assertEqual(cc[2][1], 19)
        self.assertEqual(cc[3][0], 22)
        self.assertEqual(cc[3][1], 23)
        ca[ba] = (1,2)
        self.assertEqual(ca[0,2][0], 1)
        self.assertEqual(ca[0,2][1], 2)
        self.assertEqual(ca[1,0][0], 1)
        self.assertEqual(ca[1,0][1], 2)
        self.assertEqual(ca[2,1][0], 1)
        self.assertEqual(ca[2,1][1], 2)
        self.assertEqual(ca[2,3][0], 1)
        self.assertEqual(ca[2,3][1], 2)

    def testBools(self):
        b = np.array([False, True], dtype=np.bool)
        self.assertEqual(b[0], False)
        self.assertEqual(b[1], True)

    def testHelp(self):
        import sys
        if sys.executable is None:
            if len(sys.path) > 0:
                sys.executable = sys.path[0]
            else:
                sys.executable = sys.__file__ #@UndefinedVariable

        # silence output
        # help(np)
        self.assertTrue(len(np.__doc__) > 500)

    def testCentroid(self):
        print('Centroid testing')
        a = np.arange(12.)
        x = np.arange(12.) + 2
        ca = np.centroid(a)
        self.assertEqual(ca[0], 539./66)
        ca = np.centroid(a, x)
        self.assertEqual(ca[0], 539./66 + 1.5)
        a.shape = (3,4)
        ca = np.centroid(a)
        self.assertEqual(ca[0], 131./66)
        self.assertEqual(ca[1], 147./66)
        x = np.arange(3.) + 2
        y = np.arange(4.) + 3
        ca = np.centroid(a, [x,y])
        self.assertEqual(ca[0], 131./66 + 1.5)
        self.assertEqual(ca[1], 312./66) #147./66 + 2.5)

    def testQuantile(self):
        print('Quantile testing')
        a = np.array([6., 47., 49., 15., 42., 41., 7., 39., 43., 40., 36., 21.])
        ans = [19.5, 39.5, 42.25]
        self.assertEqual(np.median(a), ans[1])
        iqr = np.iqr(a)
        self.assertEqual(iqr, ans[2] - ans[0])
        qs = np.quantile(a, [0.25, 0.5, 0.75])
        self.checkitems(ans, np.array(qs))
        a.shape = (3,4)
        qs = np.quantile(a, [0.25, 0.5, 0.75], axis=1)
        self.checkitems([12.75, 31., 32.25], qs[0])
        self.checkitems([31., 40., 38.], qs[1])
        self.checkitems([47.5, 41.25, 40.75], qs[2])
        iqr = np.iqr(a, axis=1)
        print(type(iqr))
        self.assertEqual(-12.75 + 47.5, iqr[0])
        self.assertEqual(-31. + 41.25, iqr[1])
        self.assertEqual(-32.25 + 40.75, iqr[2])


    def testGradient(self):
        print('Gradient testing')
        z = np.arange(200.)
        dz = np.gradient(z)
        self.assertEqual(1, len(dz.shape))
        self.assertEqual(200, dz.size)
        self.assertEqual(1, dz[0])

        x = np.array([1, 2, 4, 7, 11, 16], dtype=np.float)
        g = np.gradient(x)
        self.checkitems([1., 1.5, 2.5, 3.5, 4.5, 5.], g)
        g = np.gradient(x, 2)
        self.checkitems([0.5, 0.75, 1.25, 1.75, 2.25, 2.5], g)
        a = np.arange(6, dtype=np.float)*2
        g = np.gradient(x, a)
        self.checkitems([0.5, 0.75, 1.25, 1.75, 2.25, 2.5], g)

        g = np.gradient(np.array([[1, 2, 6], [3, 4, 5]], dtype=np.float))
        self.checkitems([[2., 2., -1.], [2., 2., -1.]], g[0])
        self.checkitems([[1., 2.5, 4.], [1., 1., 1.]], g[1])

        g = np.gradient(np.array([[1, 2, 6], [3, 4, 5]], dtype=np.float), 2)
        self.checkitems([[1., 1., -0.5], [1., 1., -0.5]], g[0])
        self.checkitems([[0.5, 1.25, 2.], [0.5, 0.5, 0.5]], g[1])

        g = np.gradient(np.array([[1, 2, 6], [3, 4, 5]], dtype=np.float), 2, 1)
        self.checkitems([[1., 1., -0.5], [1., 1., -0.5]], g[0])
        self.checkitems([[1., 2.5, 4.], [1., 1., 1.]], g[1])

        g = np.gradient(np.array([[1, 2, 6], [3, 4, 5]], dtype=np.float), 1, np.array([1.,2.,5.]))
        self.checkitems([[2., 2., -1.], [2., 2., -1.]], g[0])
        self.checkitems([[1., 1.25, 4./3], [1., 0.5, 1./3]], g[1])

        # test slice views
        x = np.array([1, 2, 4, 7, 11, 16], dtype=np.float)
        g = np.gradient(x[2:])
        self.checkitems([3, 3.5, 4.5, 5.], g)

    def testAsfarray(self):
        print('Float array testing')
        self.assertEqual(np.float64, np.asfarray([1.,]).dtype, "")
        self.assertEqual(np.float64, np.asfarray([1.,], dtype=np.int).dtype, "")
        self.assertEqual(np.float64, np.asfarray([1,]).dtype, "")
        self.failUnlessRaises(TypeError, np.asfarray, [1+12j,])

    def testRoll(self):
        print('Roll testing')
        x = np.arange(10)
        r = np.roll(x, 2)
        self.checkitems([8, 9, 0, 1, 2, 3, 4, 5, 6, 7], r)
        x.shape = (2,5)
        r = np.roll(x, 1)
        self.checkitems([[9, 0, 1, 2, 3], [4, 5, 6, 7, 8]], r)
        r = np.roll(x, 1, 0)
        self.checkitems([[5, 6, 7, 8, 9], [0, 1, 2, 3, 4]], r)
        r = np.roll(x, 1, 1)
        self.checkitems([[4, 0, 1, 2, 3], [9, 5, 6, 7, 8]], r)

    def testItem(self):
        print('Item testing')
        a = np.array(10)
        self.assertEqual(10, a.item())
        self.assertEqual(10, a.item(0))
        self.assertRaises(IndexError, a.item, 1)
        self.assertRaises(ValueError, a.item, 1, 1)
        a = np.array([10.])
        self.assertEqual(10, a.item())
        self.assertEqual(10, a.item(0))
        self.assertRaises(IndexError, a.item, 1)
        self.assertRaises(ValueError, a.item, 1, 1)

        a = np.arange(10.)
        self.assertEqual(4, a.item(4))
        self.assertRaises(IndexError, a.item, 11)
        self.assertRaises(ValueError, a.item, 1, 1)
        a.shape = (2,5)
        self.assertEqual(4, a.item(4))
        self.assertEqual(4, a.item(0,4))
        self.assertRaises(IndexError, a.item, 11)
        self.assertRaises(IndexError, a.item, 2, 1)

    def testZeroRank(self):
        print('Zero rank arrays testing')
        zi = np.array(1)
        print(zi)
        self.assertEqual(0, len(zi.shape))
        self.assertEqual(1, zi[()])
        self.assertEqual(np.array(1), zi[...])
        zi[()] = -3
        self.assertEqual(-3, zi[()])
        zf = np.array(1.)
        self.assertEqual(0, len(zf.shape))
        self.assertEqual(1., zf[()])
        self.assertEqual(np.array(1.), zf[...])
        zf[()] = -3
        self.assertEqual(-3, zf[()])

    def testUnpack(self):
        print('Unpacking testing')
        print(tuple(np.arange(6)))
        print(tuple(np.arange(6).reshape(2,3)))
        print(tuple(np.arange(6).reshape(3,2)))

    def testIndices(self):
        print('Indices testing')
        x, y = np.indices((516, 516))

    def testSlicing(self):
        print('Slicing testing')
        a = np.arange(60).reshape(2, 5, 3, 2)
        self.assertEqual((5, 3, 2), a[-1].shape)
        self.assertEqual((5, 3, 2), a[-1, :, :].shape)
        self.assertEqual((5, 3, 2), a[-1, :, :, :].shape)
        self.assertEqual((5, 2, 2), a[-1, :, 1:, :].shape)
        self.assertEqual((5, 3, 2), a[-1, ...].shape)
        self.assertEqual((2, 5, 3), a[..., -1].shape)
        self.assertEqual((5, 3),    a[1, ..., -1].shape)
        self.assertEqual((1, 5, 3, 2), a[-1, np.newaxis].shape)
        self.assertEqual((2, 1, 5, 3, 2), a[:, np.newaxis].shape)
        self.assertEqual((2, 1, 3, 2), a[:, np.newaxis, -1].shape)
        self.assertEqual((2, 5, 3, 1), a[..., -1, np.newaxis].shape)
        self.assertEqual((2, 1, 5, 3), a[:, np.newaxis, ..., -1].shape)
        self.assertEqual((2, 1, 5, 3, 1), a[:, np.newaxis, ..., np.newaxis, -1].shape)
        self.assertEqual((2, 1, 5, 3, 1), a[:, np.newaxis, ..., -1, np.newaxis].shape)

    def testSlicedViews(self):
        print('Sliced view testing')
        a = np.arange(9).reshape(3,3)
        a[1][1] = -3
        self.assertEqual(-3, a[1, 1])
        self.assertEqual(-3, a[1][1])
        b = a[::2, 1:]
        b[...] = 0
        self.assertEqual(0, a[0, 1])

        a = np.arange(28).reshape(4,7)[1:4,::2]
        try:
            a.shape = 12
            self.fail("This should fail")
        except:
            pass
        a.shape = 3,2,2

        # broadcasted set slice
        a[...] = np.array([-1, -2])
        self.checkitems([[-1, -2], [-1, -2]], a[1])

    def testAppend(self):
        print('Append testing')
        a = np.array([])
        x = 1
        print(np.append(a, x))

    def testTranspose(self):
        print('Transpose testing')
        a = np.arange(20).reshape(4,5)
        print(a.T)

    def testEquals(self):
        print('Equality testing')
        self.checkitems([False, True], np.array([2., 3]) == 3)
        self.checkitems([True], np.array([2.]) == 2)
        self.checkitems([False], np.array([3.]) == 2)
        self.assertTrue(np.array(-2.) == -2)
        self.assertFalse(np.array(-2.) == 2)
        self.checkitems([False, True], np.array([2.-3.5j, 3]) == 3)
        self.assertTrue(np.array(-2.+3.5j) == -2+3.5j)
        self.assertFalse(np.array(-2.) == -2+3.5j)

    def testIndexesAndPositions(self):
        print('Indexes testing')
        self.assertTrue(np.unravel_index(1621, (6,7,8,9)) == (3, 1, 4, 1))
        l = np.unravel_index([22, 41, 37], (7,6))
        self.checkitems([3, 6, 6], l[0])
        self.checkitems([4, 5, 1], l[1])

        print('Positions testing')
        self.assertTrue(np.ravel_multi_index((3,1,4,1), (6,7,8,9)) == 1621)
        arr = np.array([[3,6,6],[4,5,1]])
        self.checkitems([22, 41, 37], np.ravel_multi_index(arr, (7,6)))
        self.checkitems([22, 23, 19], np.ravel_multi_index(arr, (4,6), mode='clip'))
        self.checkitems([12, 13, 13], np.ravel_multi_index(arr, (4,4), mode=('clip', 'wrap')))

    def testRoots(self):
        print('Roots testing')
        rts = np.roots([1, 0, -1])
        rts.real.sort()
        self.checkitems([-1, 1], rts.real)
        self.checkitems([0, 0], rts.imag)
        rts = np.roots([1, 2, 1])
        self.checkitems([-1, -1], rts.real)
        self.checkitems([0, 0], rts.imag)
        rts = np.roots([1, 0, 1])
        rts.real.sort()
        self.checkitems([0, 0], rts.real)
        self.checkitems([1, -1], rts.imag)
        rts = np.roots([3.2, 2, 1])
        self.checkitems([-0.3125, -0.3125], rts.real)
        self.checkitems([0.46351240544347894, -0.46351240544347894], rts.imag)

    def testBitwise(self):
        print('Bitwise testing')
        a = np.arange(-4,4, dtype=np.int8)
        b = np.arange(8, dtype=np.int8)
        self.checkitems([0, 1, 2, 3, 0, 1, 2, 3], np.bitwise_and(a, b))
        self.checkitems([-4, -3, -2, -1, 4, 5, 6, 7], np.bitwise_or(a, b))
        self.checkitems([-4, -4, -4, -4, 4, 4, 4, 4], np.bitwise_xor(a, b))
        self.checkitems([3, 2, 1, 0, -1, -2, -3, -4], np.invert(a))
        self.checkitems([-1, -2, -3, -4, -5, -6, -7, -8], np.invert(b))
        self.checkitems([-4, -6, -8, -8, 0, 32, -128, -128], np.left_shift(a, b))
        self.checkitems([0, 0, 0, 0, 4, 10, 24, 56], np.left_shift(b, a))
        self.checkitems([0, 0, 0, 0, 0, 2, 8, 24], np.left_shift(a, a))
        self.checkitems([-4, -2, -1, -1, 0, 0, 0, 0], np.right_shift(a, b))
        self.checkitems([0, 0, 0, 0, 4, 2, 1, 0], np.right_shift(b, a))
        self.checkitems([-1, -1, -1, -1, 0, 0, 0, 0], np.right_shift(a, a))

    def testDivmod(self):
        print('Divmod testing')
        a = np.arange(-4,4, dtype=np.int8)
        c = divmod(a, 2)
        self.checkitems([-2, -2, -1, -1,  0,  0,  1,  1], c[0])
        self.checkitems([0, 1, 0, 1, 0, 1, 0, 1], c[1])
        c = divmod(a, 2.5)
        self.checkitems([-2., -2., -1., -1.,  0.,  0.,  0.,  1.], c[0])
        self.checkitems([1.,  2.,  0.5,  1.5,  0.,  1.,  2.,  0.5], c[1])

    def testModf(self):
        print('Modf testing')
        a = np.modf(2.5)
        self.assertAlmostEqual(0.5, a[0], 5)
        self.assertAlmostEqual(2.0, a[1], 5)
        a = np.modf(-.4)
        self.assertAlmostEqual(-0.4, a[0], 5)
        self.assertAlmostEqual(0.0, a[1], 5)

    def testRemainder(self):
        print('Remainder testing')
        a = np.remainder([4,7], [2,3])
        self.checkitems([0, 1], a)
        self.checkitems([0, -2,  5, -1], np.remainder([4, 7, -3, -7], [2, -3,  8, -3]))
        self.checkitems([0, 1,  -3, -1], np.fmod([4, 7, -3, -7], [2, -3,  8, -3]))
        self.checkitems([-1,  0, -1,  1,  0,  1], np.fmod([-3, -2, -1, 1, 2, 3], 2))
        self.checkitems([1, 0, 1, 1, 0, 1], np.mod([-3, -2, -1, 1, 2, 3], 2))

    def testInterpolate(self):
        print('Interpolate testing')
        xp = [1, 2, 3]
        fp = [3, 2, 0]
        self.assertAlmostEqual(1.0, np.interp(2.5, xp, fp), 5)
        self.checkitems([ 3. ,  3. ,  2.5 ,  0.56,  0. ], np.interp([0, 1, 1.5, 2.72, 3.14], xp, fp))
        UNDEF = -99.0
        self.assertAlmostEqual(UNDEF, np.interp(3.14, xp, fp, right=UNDEF), 5)

    def testAtleast(self):
        print('Atleast testing')
        self.checkitems([1.], np.atleast_1d(1.))
        self.checkitems([[1.]], np.atleast_2d(1.))
        self.checkitems([[[1.]]], np.atleast_3d(1.))

        a = np.atleast_1d(1., [2, 3])
        self.checkitems([1.], a[0])
        self.checkitems([2,3], a[1])
        a = np.atleast_2d(np.arange(2))
        self.checkitems([[0, 1]], a)
        a = np.atleast_3d(np.arange(2))
        self.checkitems([[[0], [1]]], a)
        a = np.atleast_3d(np.arange(6).reshape(3,2))
        self.checkitems([[[0], [1]], [[2], [3]], [[4], [5]]], a)

    def testStack(self):
        print('Stack testing')
        self.checkitems([1,1,1], np.hstack(np.ones(3)))
        self.checkitems([[1],[1],[1]], np.vstack(np.ones(3)))
        self.checkitems([[[1,1,1]]], np.dstack(np.ones(3)))

        a = np.array([1,2,3])
        b = np.array([2,3,4])
        self.checkitems([1,2,3,2,3,4], np.hstack((a, b)))
        self.checkitems([[1,2], [2,3], [3,4]], np.hstack((a.reshape(3,1), b.reshape(3,1))))

        self.checkitems([[1,2,3],[2,3,4]], np.vstack((a, b)))
        self.checkitems([[1], [2], [3], [2], [3], [4]], np.vstack((a.reshape(3,1), b.reshape(3,1))))

        self.checkitems([[[1,2], [2,3], [3,4]]], np.dstack((a, b)))
        self.checkitems([[[1,2]], [[2,3]], [[3,4]]], np.dstack((a.reshape(3,1), b.reshape(3,1))))
        self.checkitems([[1,2], [2,3], [3,4]], np.column_stack((a, b)))
        self.checkitems([[1,2, 0, 1, 2], [2,3, 3, 4, 5], [3,4, 6, 7, 8]], np.column_stack((a, b, np.arange(9).reshape(3,3))))

        a = np.array([[]]*6).T
        b = np.array([1,2,3,4,5,6])
        s = np.vstack((a,b))
        self.checkitems([[1,2,3,4,5,6]], s)
        self.assertEqual(a.dtype, np.float64)
        self.assertEqual(s.dtype, np.float64)
        s = np.vstack((a,a))
        self.assertEqual(s.shape, (0,6))
        self.assertEqual(s.dtype, np.float64)
        s = np.vstack((a,a.astype(int)))
        self.assertEqual(s.dtype, np.float64)
        s = np.vstack((a.astype(int), a))
        self.assertEqual(s.dtype, np.float64)

    def testMeshGrid(self):
        print('Meshgrid testing')
        x = np.arange(0, 6, 1)
        y = np.arange(0, 4, 1)
        xy = np.meshgrid(x,y)
        self.checkitems([[0,1,2,3,4,5],[0,1,2,3,4,5],[0,1,2,3,4,5],[0,1,2,3,4,5]], xy[0])
        self.checkitems([[0,0,0,0,0,0], [1,1,1,1,1,1], [2,2,2,2,2,2], [3,3,3,3,3,3]], xy[1])

        xy = np.meshgrid(x,y, indexing='ij')
        self.checkitems([[0,1,2,3],[0,1,2,3],[0,1,2,3],[0,1,2,3],[0,1,2,3],[0,1,2,3]], xy[1])
        self.checkitems([[0,0,0,0], [1,1,1,1], [2,2,2,2], [3,3,3,3], [4,4,4,4], [5,5,5,5]], xy[0])

    def testHistogram(self):
        print('Histogram testing')
        h, v = np.histogram([1, 2, 1], bins=[0, 1, 2, 3])
        self.checkitems([0, 2, 1], h)
        self.checkitems([0, 1, 2, 3], v)
        h, v = np.histogram([0, 1, 2, 1], bins=2)
        self.checkitems([1, 3], h)
        self.checkitems([0, 1, 2], v)
        h, v = np.histogram(np.arange(4), bins=4, range=[0,4])
        self.checkitems([1, 1, 1, 1], h)
        self.checkitems([0, 1, 2, 3, 4], v)

        h, v = np.histogram(np.arange(4), bins=4, range=[0,4], weights=np.full(4, 0.25))
        self.checkitems([0.25, 0.25, 0.25, 0.25], h)
        self.checkitems([0, 1, 2, 3, 4], v)

    def testHistogram2d(self):
        print('Histogram2d testing')
        h, v, w = np.histogram2d([1, 2, 1, 0], [2, 3, 1, 3], bins=[[0, 1, 2], [2, 3, 4]])
        self.checkitems([[0, 1], [1, 1]], h)
        self.checkitems([0, 1, 2], v)
        self.checkitems([2, 3, 4], w)
        h, v, w = np.histogram2d([0, 1, 2, 1], [2, 3, 1, 3], bins=2)
        self.checkitems([[0, 1], [1, 2]], h)
        self.checkitems([0, 1, 2], v)
        self.checkitems([1, 2, 3], w)
        h, v, w = np.histogram2d([0, 1, 2, 1], [2, 3, 1, 3], bins=2, weights=np.full(4, 0.25))
        self.checkitems([[0, 0.25], [0.25, 0.5]], h)
        self.checkitems([0, 1, 2], v)
        self.checkitems([1, 2, 3], w)
        h, v, w = np.histogram2d(np.arange(4), np.arange(1,5), bins=4, range=[[0,4], [1,5]])
        self.checkitems([1, 1, 1, 1], np.array([h[i,i] for i in range(len(h))]))
        self.checkitems([0, 1, 2, 3, 4], v)
        self.checkitems([1, 2, 3, 4, 5], w)

    def testHistogramdd(self):
        print('Histogramdd testing')
        h, (v, w) = np.histogramdd(np.column_stack((np.array([1, 2, 1, 0]), np.array([2, 3, 1, 3]))), bins=[[0, 1, 2], [2, 3, 4]])
        self.checkitems([[0, 1], [1, 1]], h)
        self.checkitems([0, 1, 2], v)
        self.checkitems([2, 3, 4], w)
        h, (v, w) = np.histogramdd(np.array([[0, 1, 2, 1], [2, 3, 1, 3]]).T, bins=2)
        self.checkitems([[0, 1], [1, 2]], h)
        self.checkitems([0, 1, 2], v)
        self.checkitems([1, 2, 3], w)
        h, (v, w) = np.histogramdd(np.array([[0, 1, 2, 1], [2, 3, 1, 3]]).T, bins=2, weights=np.full(4, 0.25))
        self.checkitems([[0, 0.25], [0.25, 0.5]], h)
        self.checkitems([0, 1, 2], v)
        self.checkitems([1, 2, 3], w)
        h, (v, w) = np.histogramdd(np.column_stack((np.arange(4), np.arange(1,5))), bins=4, range=[[0,4], [1,5]])
        self.checkitems([1, 1, 1, 1], np.array([h[i,i] for i in range(len(h))]))
        self.checkitems([0, 1, 2, 3, 4], v)
        self.checkitems([1, 2, 3, 4, 5], w)

    def testBincount(self):
        print('Bincount testing')
        a = np.array([2, 1, 2, 4, 6, 0])
        c = np.bincount(a)
        self.checkitems([1, 1, 2, 0, 1, 0, 1], c)
        c = np.bincount(a, None, 10)
        self.checkitems([1, 1, 2, 0, 1, 0, 1, 0, 0, 0], c)
        w = np.array([1, 1, 2, 4, 1, 0])
        c = np.bincount(a, w)
        self.checkitems([0, 1, 3, 0, 4, 0, 1], c)
        c = np.bincount(a, w * 0.5)
        self.checkitems([0, 1, 3, 0, 4, 0, 1], 2*c)

if __name__ == '__main__':
    unittest.main(verbosity=2)
