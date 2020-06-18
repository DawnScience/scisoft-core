
import unittest
import scisoftpy as dnp

class Test(unittest.TestCase):

    def checkitems(self, la, ds):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEqual(la[i], ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEqual(la[i][j], ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEqual(la[i][j][k], ds[i, j, k])

    def testMean(self):
        a = dnp.array([[[1, 2, 3], [4, 5, 6]], [[3, 4, 5], [6, 7, 8]]])

        self.assertEqual(dnp.mean(a), 4.5)
        self.checkitems(dnp.mean(a, axis=0), dnp.array([[2, 3, 4], [5, 6, 7]]))
        self.assertEqual(dnp.mean(a, axis=0).shape, (2, 3))
        self.checkitems(dnp.mean(a, axis=2), dnp.array([[2, 5], [4, 7]]))
        self.assertEqual(dnp.mean(a, axis=2).shape, (2, 2))
        self.checkitems(dnp.mean(a, axis=2), dnp.array([[2, 5], [4, 7]]))
        self.assertEqual(dnp.mean(a, axis=(0, 2)).shape, (2,))
        self.checkitems(dnp.mean(a, axis=(0, 2)), dnp.array([3, 6]))

        self.assertEqual(dnp.mean(a, keepdims=True).shape, (1, 1, 1))
        self.checkitems(dnp.mean(a, axis=0, keepdims=True), dnp.array([[[2, 3, 4], [5, 6, 7]]]))
        self.assertEqual(dnp.mean(a, axis=0, keepdims=True).shape, (1, 2, 3))
        self.assertEqual(dnp.mean(a, axis=1, keepdims=True).shape, (2, 1, 3))
        self.assertEqual(dnp.mean(a, axis=2, keepdims=True).shape, (2, 2, 1))
        self.assertEqual(dnp.mean(a, axis=(0,2), keepdims=True).shape, (1, 2, 1))
        self.checkitems(dnp.mean(a, axis=(0,2), keepdims=True), dnp.array([[[3], [6]]]))

    def testStd(self):
        a = dnp.array([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])
        self.assertEqual(dnp.std(a, keepdims=True).shape, (1, 1, 1))
        self.checkitems(dnp.std(a, keepdims=True), dnp.array([[[2.29128784747792]]]))

    def testSum(self):
        a = dnp.array([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])
        self.assertEqual(dnp.sum(a, keepdims=True).shape, (1, 1, 1))
        self.checkitems(dnp.sum(a, keepdims=True), dnp.array([[[36]]]))

    def testAmin(self):
        a = dnp.array([[[1, 2], [3, 4]], [[5, 6], [7, 8]]])
        self.assertEqual(dnp.amin(a, axis=(0, 1)).shape, (2,))
        self.assertEqual(dnp.amin(a, axis=(0, 1), keepdims=True).shape, (1, 1, 2))
        self.checkitems(dnp.amin(a, axis = (0, 1), keepdims=True), dnp.array([[[1, 2]]]))

if __name__ == '__main__':
    unittest.main(verbosity=2)
