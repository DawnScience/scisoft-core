
import unittest
import scisoftpy as dnp
import inspect


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

    def testMean(self):
        a = dnp.array([[[1, 2, 3], [4, 5, 6]], [[3, 4, 5], [6, 7, 8]]])

        self.assertEquals(dnp.mean(a), 4.5)
        self.checkitems(dnp.mean(a, axis=0), dnp.array([[2, 3, 4], [5, 6, 7]]))
        self.assertEqual(dnp.mean(a, axis=0).shape, (2, 3))
        self.checkitems(dnp.mean(a, axis=2), dnp.array([[2, 5], [4, 7]]))
        self.assertEqual(dnp.mean(a, axis=2).shape, (2, 2))

        self.assertEqual(dnp.mean(a, keepdims=True).shape, (1, 1, 1))
        self.checkitems(dnp.mean(a, axis=0, keepdims=True), dnp.array([[[2, 3, 4], [5, 6, 7]]]))
        self.assertEqual(dnp.mean(a, axis=0, keepdims=True).shape, (1, 2, 3))
        self.assertEqual(dnp.mean(a, axis=1, keepdims=True).shape, (2, 1, 3))
        self.assertEqual(dnp.mean(a, axis=2, keepdims=True).shape, (2, 2, 1))

    def testStd(self):
        a = dnp.array([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])
        self.assertEquals(dnp.std(a, keepdims=True).shape, (1, 1, 1))
        self.checkitems(dnp.std(a, keepdims=True), dnp.array([[[2.29128784747792]]]))

    def testSum(self):
        a = dnp.array([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])
        self.assertEquals(dnp.sum(a, keepdims=True).shape, (1, 1, 1))
        self.checkitems(dnp.sum(a, keepdims=True), dnp.array([[[36]]]))


def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
