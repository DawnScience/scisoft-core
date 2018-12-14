
import unittest
import scisoftpy as dnp
import inspect
import java.lang.Integer as _jint
import java.lang.reflect.Array as _jarray

class Test(unittest.TestCase):

    def toAny(o):
        return o

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

    def testAmin(self):
        a = dnp.array([[[1, 2, 3], [4, 5, 6]], [[7, 8, 9], [10, 11, 12]]])
        self.assertEquals(dnp.amin(a, axis=0).shape, (2,3))
        self.checkitems(dnp.amin(a, axis=0), dnp.array([[1, 2, 3], [4, 5, 6]]))
        self.assertEquals(dnp.amin(a, axis=2).shape, (2,2))
        self.assertEquals(dnp.amin(a, axis=(0, 1)).shape, (3,))
        self.checkitems(dnp.amin(a, axis=(0, 1)), dnp.array([1, 2, 3]))
        self.assertEquals(dnp.amin(a, axis=(0, 1, 2)), 1)
        self.assertEquals(dnp.amin(a, axis=0, keepdims=True).shape, (1, 2, 3))
        self.assertEquals(dnp.amin(a, axis=2, keepdims=True).shape, (2, 2, 1))
        self.assertEquals(dnp.amin(a, axis=(0, 1), keepdims=True).shape, (1, 1, 3))
        self.checkitems(dnp.amin(a, axis=(0, 1), keepdims=True), dnp.array([[[1, 2, 3]]]))
        self.assertEquals(dnp.amin(a, axis=(0, 2), keepdims=True).shape, (1, 2, 1))
        self.checkitems(dnp.amin(a, axis=(0, 2), keepdims=True), dnp.array([[[1], [4]]]))

def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
