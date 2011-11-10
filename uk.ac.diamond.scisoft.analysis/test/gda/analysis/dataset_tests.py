#@PydevCodeAnalysisIgnore
import unittest

from gda.analysis import DataSet
from Jama import Matrix as Matrix

a = [ [[0., 1.], [2., 3.]], [[4., 5.], [6., 7.]] ]
m = [ [[0., 2.], [6., 12.]], [[20., 30.], [42., 56.]] ]
q = [ [[0., 0.5], [0.6666666666666666, 0.75]], [[0.8, 0.8333333333333334], [0.8571428571428571, 0.875]] ]
r = [ [[1., 3.], [5., 7.]], [[9., 11.], [13., 15.]] ]
s = [ 0., 1., 2., 3., 4., 5., 6., 7. ]
t = [ [[5.]] ]
u = [ [[0., 1.], [7., 3.]], [[4., 5.], [7., 7.]] ]
v = [ [[0., 1.], [2., 3.]], [[1., 2.], [3., 4.]] ]

class DataSetTest(unittest.TestCase):
    def setUp(self):
        self.da = DataSet.array(a)
        self.db = DataSet.arange(1.,9.)
        self.db.resize((2,2,2))
        self.dr = DataSet.array(r)
        pass

    def testDataSet1D(self):
        d = DataSet.array(1)
        la = [0, 1, 2, 3, 4, 5]
        d = DataSet.array(la)
        len = d.getDimensions()
        for i in range(len[0]):
            self.assertEquals(la[i], d[i])

    def testDataSet1DMixed(self):
        la = [0, 1.1, 2, 3.7, 4, 5]
        d = DataSet.array(la)
        len = d.getDimensions()
        for i in range(len[0]):
            self.assertEquals(la[i], d[i])

    def testDataSet2D(self):
        la = [[0, 1, 2], [3, 4, 5]]
        d = DataSet.array(la)
        len = d.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                self.assertEquals(la[i][j], d[i,j])

    def testDataSet3D(self):
        la = [ [[0, 1], [2, 3]], [[4, 5], [6, 7]] ]
        d = DataSet.array(la)
        len = d.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(la[i][j][k], d[i,j,k])

    def testDataSet3DFloats(self):
        la = [ [[0., 1.], [2., 3.]], [[4., 5.], [6., 7.]] ]
        d = DataSet.array(la)
        len = d.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(la[i][j][k], d[i,j,k])

    def testDataSetAdd(self):
        print 'testing adding two datasets'
        dr = self.da + self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(r[i][j][k], dr[i,j,k])
        print 'testing adding a dataset with a constant'
        dr = self.da + 1.
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k] + 1, dr[i,j,k])
        print 'testing adding a constant with a dataset'
        dr = 1. + self.da
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k] + 1, dr[i,j,k])
        print 'testing adding in-place a dataset to a dataset'
        dr = DataSet(self.da)
        dr += self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(r[i][j][k], dr[i,j,k])
        print 'testing adding in-place a constant to a dataset'
        dr = DataSet(self.da)
        dr += 1.0
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k] + 1, dr[i,j,k])

    def testDataSetSub(self):
        print 'testing subtracting two datasets'
        dr = self.dr - self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k], dr[i,j,k])
        print 'testing subtracting a constant from a dataset'
        dr = self.db - 1.
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k], dr[i,j,k])
        print 'testing subtracting a dataset from a constant'
        dr = 1. - self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(-a[i][j][k], dr[i,j,k])
        print 'testing subtracting in-place a dataset from a dataset'
        dr = DataSet(self.dr)
        dr -= self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k], dr[i,j,k])
        print 'testing subtracting in-place a constant from a dataset'
        dr = DataSet(self.db)
        dr -= 1.0
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k], dr[i,j,k])

    def testDataSetMul(self):
        print 'testing multiplying two datasets'
        dr = self.da * self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(m[i][j][k], dr[i,j,k])
        print 'testing multiplying a dataset by a constant'
        dr = self.da * 2.3
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]*2.3, dr[i,j,k])
        print 'testing multiplying a constant by a dataset'
        dr = 2.1 * self.da
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]*2.1, dr[i,j,k])
        print 'testing multiplying in-place a dataset by a dataset'
        dr = DataSet(self.da)
        dr *= self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(m[i][j][k], dr[i,j,k])
        print 'testing multiplying in-place a constant by a dataset'
        dr = DataSet(self.da)
        dr *= 2.7
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]*2.7, dr[i,j,k])

    def testDataSetDiv(self):
        print 'testing dividing two datasets'
        dr = self.da / self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(q[i][j][k], dr[i,j,k])
        print 'testing dividing a dataset by a constant'
        dr = self.da / 2.3
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]/2.3, dr[i,j,k])
        print 'testing dividing a constant by a dataset'
        dr = 2.1 / self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(2.1/(1.+a[i][j][k]), dr[i,j,k])
        print 'testing dividing in-place a dataset by a dataset'
        dr = self.da.copy()
        dr /= self.db
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(q[i][j][k], dr[i,j,k])
        print 'testing dividing in-place a constant by a dataset'
        dr = self.da.copy()
        dr /= 2.7
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]/2.7, dr[i,j,k])

    def testDataSetNeg(self):
        print 'testing negating a dataset'
        dr = -self.da
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(-a[i][j][k], dr[i,j,k])

    def testDataSetPow(self):
        print 'testing raising a dataset to a power'
        dr = self.da**1.5
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]**1.5, dr[i,j,k])
        print 'testing raising a dataset to a power in-place'
        dr = DataSet(self.da)
        dr **= 1.5
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(a[i][j][k]**1.5, dr[i,j,k])

    def testDataSetSlice(self):
        print 'testing slicing with start and stop'
        ds = DataSet.arange(16)
        dr = ds[2:10]
        len = dr.getDimensions()
        for i in range(len[0]):
            self.assertEquals(s[i] + 2, dr[i])
        print 'testing slicing with steps'
        dr = ds[::2]
        len = dr.getDimensions()
        for i in range(len[0]):
            self.assertEquals(2*s[i], dr[i])
        print 'testing slicing 3D'
        dr = self.da[1:,::2,1::2]
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(t[i][j][k], dr[i,j,k])
        print 'testing putting in a 3D slice'
        dr = self.da.copy()
        dr[0:,1::2,::2] = 7.
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(u[i][j][k], dr[i,j,k])

        dr = self.da.copy()
        dt = DataSet.arange(4)
        dt += 1
        dt.shape = [2,2]
        dr[1:,:,:] = dt
        len = dr.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                for k in range(len[2]):
                    self.assertEquals(v[i][j][k], dr[i,j,k])
 
    def testJama(self):
        print 'testing a Jama Matrix'
        m = Matrix([[0,1,1.5],[2,3,3.5]])
        dma = DataSet.array(m)
        dmb = DataSet.array(m.getArray())
        len = dmb.getDimensions()
        for i in range(len[0]):
            for j in range(len[1]):
                self.assertEquals(m.get(i,j), dma[i,j])
                self.assertEquals(m.get(i,j), dmb[i,j])

        dm2 = DataSet.array([m.getArray(), m.getArray()])
        len = dm2.getDimensions()
        for i in range(len[1]):
            for j in range(len[2]):
                self.assertEquals(m.get(i,j), dm2[0,i,j])
                self.assertEquals(m.get(i,j), dm2[1,i,j])


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(DataSetTest)
    unittest.TextTestRunner(verbosity=2).run(suite)
