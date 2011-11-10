#@PydevCodeAnalysisIgnore
'''
Test dataset methods

import unittest
unittest.TestProgram(argv=["dataset_test"])
'''
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
                self.assertEquals(convert(la[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k]), ds[i, j, k])

    def testOnes(self): # make new datasets with ones
        print "test ones"
        dds = np.ones(3, np.float)
        if isjava:
            self.assertEquals(1, dds.elementsPerItem)
        self.assertEquals(1, dds.ndim)
        self.assertEquals(3, dds.shape[0])
        self.assertEquals(1, dds[0])
        dds = np.ones((2,3), np.float)
        if isjava:
            self.assertEquals(1, dds.elementsPerItem)
        self.assertEquals(2, dds.ndim)
        self.assertEquals(2, dds.shape[0])
        self.assertEquals(3, dds.shape[1])
        self.assertEquals(1, dds[0,0])

    def testCompound(self): # make new datasets with ones
        print "test compound"
        dds = np.ndarrayCB(3, (3,4))
        self.assertEquals(3, dds.itemsize, msg="itemsize incorrect")
        dds = np.ndarrayRGB((3,5))
        print type(dds), dds.dtype, dds.shape
        self.assertEquals(6, dds.itemsize, msg="itemsize incorrect")
        dds = dds.get_red()
        print type(dds), dds.dtype, dds.shape
        self.assertEquals(2, dds.itemsize, msg="itemsize incorrect")
        dds = np.zeros((3,4), np.cint32, 2)
        print type(dds), dds.dtype, dds.shape
        self.assertEquals(8, dds.itemsize, msg="itemsize incorrect")
        dds = np.zeros((3,4), np.cint32(2))
        print type(dds), dds.dtype, dds.shape
        self.assertEquals(8, dds.itemsize, msg="itemsize incorrect")

    def testArray(self): # make new datasets
        print "test array"
        dds = np.array(self.dz, np.float)
        self.assertEquals(self.dz, dds)
        self.assertEquals(dds, self.dz)
        self.assertEquals(dds.item(), self.dz)
        if isjava:
            self.assertEquals(1, dds.elementsPerItem)
        self.assertEquals(8, dds.itemsize)
        dds = np.array(self.da, np.float)
        self.checkitems(self.da, dds)
        dds = np.array(self.db, np.float)
        self.checkitems(self.db, dds)
        dds = np.array(self.dc, np.float)
        self.checkitems(self.dc, dds)
        mds = np.array(self.m)
        self.checkitems(self.db, mds)
        zds = np.array(self.zz, np.complex)
        self.assertEquals(self.zz, zds)
        self.assertEquals(zds, self.zz)
        self.assertEquals(zds.item(), self.zz)

        zds = np.array([self.zz, self.zz], np.complex)
        self.assertEquals(self.zz, zds[0])
        self.assertRaises(ValueError, zds.item)

        if isjava:
            self.assertEquals(2, zds.elementsPerItem)
        self.assertEquals(16, zds.itemsize)
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
        self.assertEquals(tds.dtype, np.int32)
        tds = np.array(self.m)
        self.assertEquals(tds.dtype, np.float64)
        tds = np.array(self.q)
        self.assertEquals(tds.dtype, np.float64)
        tds = np.array(self.r)
        self.assertEquals(tds.dtype, np.float64)
        tds = np.array(self.s)
        self.assertEquals(tds.dtype, np.int32)
        tds = np.array(self.t)
        self.assertEquals(tds.dtype, np.float64)
        tds = np.array(self.u)
        self.assertEquals(tds.dtype, np.float64)

    def testSlicing(self):
        a = np.array([], dtype=np.float_)
        self.assertEquals(len(a), 0)
        a = np.zeros((2,))
        self.assertEquals(len(a), 2)
        self.checkitems([0,0], a[:])
        self.assertEquals(a[1], 0)
        a[:] = 0.5
        self.checkitems([0.5,0.5], a[:])

        a = np.zeros((2,3))
        self.assertEquals(len(a), 2)
        self.checkitems([0,0,0], a[0])
        a[1] = 0.2
        self.checkitems([0.2,0.2,0.2], a[1])
        a = np.zeros((2,3,4))
        self.assertEquals(len(a), 2)
        a = np.arange(10).reshape((5,2))
        a[3,:] = np.array([0,0])
        self.checkitems([0,0], a[3])
        a[3,:] = np.array([1,1]).reshape(1,2)
        self.checkitems([1,1], a[3])
        a[:2,1] = np.array([2,2])
        self.checkitems([2,2], a[:2,1])


    def testArange(self):
        print "test arange"
        dds = np.arange(12, dtype=np.float)
        self.checkitems(range(12), dds)
        dds = np.arange(2,12, dtype=np.int)
        self.checkitems(range(2,12), dds)
        dds = np.arange(2,12,3)
        self.checkitems(range(2,12,3), dds)
        dds = np.arange(12,2,-3)
        self.checkitems(range(12,2,-3), dds)

    def checkitemsadd(self, la, lb, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEquals(convert(la[i])+convert(lb[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j])+convert(lb[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k])+convert(lb[i][j][k]), ds[i, j, k])

    def checkitemsaddconst(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEquals(convert(la[i])+c, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j])+c, ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k])+c, ds[i, j, k])

    def testAdd(self):
        print "test add"
        da = np.array(self.db, np.int)
        dr = da + da
        self.checkitemsadd(self.db, self.db, dr, convert=toInt)
        dr = da.copy()
        dr += da
        self.checkitemsadd(self.db, self.db, dr, convert=toInt)
        dr = da + 1.2
        self.checkitemsaddconst(self.db, 1.2, dr, convert=toInt)
        dr = da.copy()
        dr += 1.2
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
                self.assertEquals(convert(la[i])-convert(lb[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j])-convert(lb[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k])-convert(lb[i][j][k]), ds[i, j, k])

    def checkitemssubconst(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEquals(convert(la[i])-c, ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j])-c, ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k])-c, ds[i, j, k])

    def checkitemssubconst2(self, la, c, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEquals(convert(la[i]-c), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(convert(la[i][j])-c), ds[i, j],
                                      msg="%d, %d : %r %r" % (i,j, convert(convert(la[i][j])-c), ds[i, j]))
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k]-c), ds[i, j, k])

    def testSub(self):
        print "test sub"
        da = np.array(self.db, np.int)
        dr = da - da
        self.checkitemssub(self.db, self.db, dr, convert=toInt)
        dr = da.copy()
        dr -= da
        self.checkitemssub(self.db, self.db, dr, convert=toInt)
        dr = da - 1.2
        self.checkitemssubconst(self.db, 1.2, dr, convert=toInt)
        dr = da.copy()
        print 'Before', dr[1,0]
        dr -= 1.2
        print 'After', dr[1,0]
        self.checkitemssubconst2(self.db, 1.2, dr, convert=toInt)

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

    def checkitemsdiv(self, la, lb, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEquals(convert(la[i])/convert(lb[i]+1.), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j])/convert(lb[i][j]+1.), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k])/convert(lb[i][j][k]+1.), ds[i, j, k])

    def checkitemsdivconst(self, la, c, ds, convert=toAny):
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
                self.assertEquals(d/n, ds[i])
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
                    self.assertEquals(d/n, ds[i, j],
                                      msg="%d, %d : %r %r" % (i,j, d/n, ds[i, j]))
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
                        self.assertEquals(d/n, ds[i, j, k])

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
                self.assertEquals(d/n, ds[i])
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
                    self.assertEquals(convert(d/n), ds[i, j],
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
                        self.assertEquals(d/n, ds[i, j, k])

    def testDiv(self):
        print "test div"
        da = np.array(self.db, np.int)
        dr = da / (da+1)
        self.checkitemsdiv(self.db, self.db, dr, convert=toInt)
        dr = da.copy()
        dr /= da + 1
        self.checkitemsdiv(self.db, self.db, dr, convert=toInt)
        dr = da / 1.2
        self.checkitemsdivconst(self.db, 1.2, dr, convert=toInt)
        dr = da.copy()
        dr /= 1.2
        self.checkitemsdivconst2(self.db, 1.2, dr, convert=toInt)

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
        self.checkitemsdiv(self.zb, self.zb, zr)
        zr = za.copy()
        zr /= za + 1
        self.checkitemsdiv(self.zb, self.zb, zr)
        zr = za / (1.3 + 0.2j)
        self.checkitemsdivconst(self.zb, (1.3 + 0.2j), zr)
        zr = za.copy()
        zr /= (1.3 + 0.2j)
        self.checkitemsdivconst(self.zb, (1.3 + 0.2j), zr)

    def testCopyItems(self):
        if isjava:
            import jarray
            print "test copy items"
            da = np.array(self.db, np.float)
            ta = np.zeros(da.getShape())
            da.copyItemsFromAxes(None, None, ta)
            print ta.getBuffer()
            ta = np.array(self.m)
            da.setItemsOnAxes(None, None, ta.getBuffer())
            print da.getBuffer()
    
            print '2'
            da = np.array(self.db, np.float)
            ta = np.zeros((2,))
            axes = [ True, False ]
            da.copyItemsFromAxes(None, axes, ta)
            print ta.getBuffer()
            ta = jarray.array([ -2, -3.4 ], 'd')
            da.setItemsOnAxes(None, axes, ta)
            print da.getBuffer()
    
            print '3'
            da = np.array(self.db, np.float)
            ta = np.zeros((2,))
            axes = [ False, True ]
            da.copyItemsFromAxes(None, axes, ta)
            print ta.getBuffer()
            ta = jarray.array([ -2.5, -3.4 ], 'd')
            da.setItemsOnAxes(None, axes, ta)
            print da.getBuffer()

    def testDatasetSlice(self):
        print 'test slicing with start and stop'
        ds = np.arange(16)
        dr = ds[2:10]
        self.checkitems(range(2,10), dr)

        print 'test slicing with steps'
        dr = ds[::2]
        self.checkitems(range(0,16,2), dr)

        print 'test slicing 3D'
        dr = self.dda[1:,::2,1::2]
        self.checkitems(self.t, dr)

        print 'test putting in a 3D slice'
        dr = self.dda.copy()
        dr[:,1::2,::2] = 7.
        self.checkitems(self.u, dr)
 
        print 'test putting a list in a 3D slice'
        dr = self.dda.copy()
        print dr[:,1::2,::2].shape
        dr[:,1::2,::2] = np.array([7., 7]).reshape(2,1,1)
        self.checkitems(self.u, dr)

        print 'test slicing with ellipse'
        dr = ds[...]
        self.checkitems(range(16), dr)

        print 'test slicing 3D with ellipse'
        dr = self.dda[...,1::2]
        self.checkitems(self.t, dr[1:, ::2])

        print 'test putting in a 3D slice with ellipsis'
        dr = self.dda.copy()
        dr[...,1::2,::2] = 7.
        self.checkitems(self.u, dr)
 
        print 'test putting a list in a 3D slice with ellipsis'
        dr = self.dda.copy()
        print dr[...,1::2,::2].shape
        dr[...,1::2,::2] = np.array([7., 7]).reshape(2,1,1)
        self.checkitems(self.u, dr)

        print 'test slice shape reduction'
        dr = np.arange(120).reshape(4,3,5,2)
        s = dr[:2,...,1:].shape
        print s
        self.assertEquals(s, (2,3,5,1))
        s = dr[:2,...,1].shape
        print s
        self.assertEquals(s, (2,3,5))
        s = dr[:2,...,...,1].shape
        print s
        self.assertEquals(s, (2,3,5))

    def testArgs(self):
        print 'test arg maxs'
        ds = np.array([[[1., 0., 3.], [.5, 2.5, 2.]], [[0., 3., 1.], [1.5, 2.5, 2.]]])
        self.assertEquals(ds.argmax(), 2)
        self.checkitems([[0, 1, 0], [1, 0, 0]], ds.argmax(0))
        self.checkitems([[0, 1, 0], [1, 0, 1]], ds.argmax(1))
        self.checkitems([[2, 1], [1, 1]], ds.argmax(2))

        print 'test arg mins'
        self.assertEquals(ds.argmin(), 1)
        self.checkitems([[1, 0, 1], [0, 0, 0]], ds.argmin(0))
        self.checkitems([[1, 0, 1], [0, 1, 0]], ds.argmin(1))
        self.checkitems([[1, 0], [0, 0]], ds.argmin(2))

    def testProds(self):
        print 'test prod'
        ds = np.arange(12).reshape(3,4)
        self.assertEquals(np.prod(ds), 0)
        self.checkitems([ 0, 45, 120, 231], np.prod(ds, 0))
        self.checkitems([ 0, 840, 7920], np.prod(ds, 1))
        
        print 'test cumprod'
        self.checkitems([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], np.cumprod(ds))
        self.checkitems([[0, 1, 2, 3], [0, 5, 12, 21], [0, 45, 120, 231]], np.cumprod(ds, 0))
        self.checkitems([[0, 0, 0, 0], [4, 20, 120, 840], [8, 72, 720, 7920]], np.cumprod(ds, 1))

    def testSums(self):
        print 'test sum'
        ds = np.arange(12).reshape((3,4))
        self.assertEquals(np.sum(ds), 66)
        self.checkitems([12, 15, 18, 21], np.sum(ds, 0))
        self.checkitems([ 6, 22, 38], np.sum(ds, 1))
        
        print 'test cumsum'
        self.checkitems([0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66], np.cumsum(ds))
        self.checkitems([[0, 1, 2, 3], [4, 6, 8, 10], [12, 15, 18, 21]], np.cumsum(ds, 0))
        self.checkitems([[0, 1, 3, 6], [4, 9, 15, 22], [8, 17, 27, 38]], np.cumsum(ds, 1))

    def testGDA2270(self):
        print 'test negative indices'
        ds = np.arange(10.)
        self.assertEquals(8, ds[-2])
        ds[-2] = 0
        self.assertEquals(0, ds[8])
        ds = np.arange(10.).reshape(2,5)
        du = np.arange(5.,10)
        dt = ds[-1]
        for i in range(du.shape[0]):
            self.assertEquals(dt[i], du[i])
        ds[-1] = -2
        for i in range(ds.shape[1]):
            self.assertEquals(ds[-1,i], -2)

    def testGDA2271(self):
        print 'test out of bound indices'
        ds = np.arange(10.)
        def getf(ind):
            return ds[ind]
        def setf(ind, value):
            ds[ind] = value

        self.assertRaises(IndexError, getf, 10)
        self.assertRaises(IndexError, getf, -11)

        if isjava:
            ds.extendible = False
            self.assertRaises(IndexError, setf, 11, 0.)
            self.assertRaises(IndexError, setf, -11, 0.)
            ds.extendible = True
            ds[10] = 0.

    def testBoolean(self):
        print 'test boolean get and set'
        tm = np.array(self.mm)
        c = tm > 11.6
        lc = [ [[False, False], [ False, True]], [[True, True], [True, True]]]
        self.checkitems(lc, c)

        d = tm[c]
        self.checkitems([ 12., 20., 30., 42., 56. ], d)

        d = tm[np.logical_not(c)]
        self.checkitems([ 0, 2., 6 ], d)

        tm[c] = -2.3
        self.checkitems([ [[0., 2.], [6., -2.3]], [[-2.3, -2.3], [-2.3, -2.3]] ], tm)

        tm[np.logical_not(c)] = -3.3
        self.checkitems([ [[-3.3, -3.3], [-3.3, -2.3]], [[-2.3, -2.3], [-2.3, -2.3]] ], tm)

    def testInteger(self):
        print 'test integer get and set'
        tm = np.array(self.mm)

        d = tm[np.array([2, 4, 7])]
        self.checkitems([ 6., 20., 56. ], d)

        tm[np.array([3, 4, 5, 6, 7])] = -2.3
        self.checkitems([ [[0., 2.], [6., -2.3]], [[-2.3, -2.3], [-2.3, -2.3]] ], tm)

    def testSelect(self):
        print 'test select'
        tm = np.select([np.array([[[False, True], [True, False]], [[True, True], [False, False]]])], [np.array(self.mm)], -2.3)

        self.checkitems([ [[-2.3, 2.], [6., -2.3]], [[20., 30.], [-2.3, -2.3]] ], tm)


if __name__ == "__main__":
    #import sys
    #sys.argv = ['', 'Test.testDataset']
    unittest.main()
