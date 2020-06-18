
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

    def testAmin(self):
        a = dnp.array([[[1, 2, 3], [4, 5, 6]], [[7, 8, 9], [10, 11, 12]]])
        self.assertEqual(dnp.amin(a, axis=0).shape, (2,3))
        self.checkitems(dnp.amin(a, axis=0), dnp.array([[1, 2, 3], [4, 5, 6]]))
        self.assertEqual(dnp.amin(a, axis=2).shape, (2,2))
        self.assertEqual(dnp.amin(a, axis=(0, 1)).shape, (3,))
        self.checkitems(dnp.amin(a, axis=(0, 1)), dnp.array([1, 2, 3]))
        self.assertEqual(dnp.amin(a, axis=(0, 1, 2)), 1)
        self.assertEqual(dnp.amin(a, axis=0, keepdims=True).shape, (1, 2, 3))
        self.assertEqual(dnp.amin(a, axis=2, keepdims=True).shape, (2, 2, 1))
        self.assertEqual(dnp.amin(a, axis=(0, 1), keepdims=True).shape, (1, 1, 3))
        self.checkitems(dnp.amin(a, axis=(0, 1), keepdims=True), dnp.array([[[1, 2, 3]]]))
        self.assertEqual(dnp.amin(a, axis=(0, 2), keepdims=True).shape, (1, 2, 1))
        self.checkitems(dnp.amin(a, axis=(0, 2), keepdims=True), dnp.array([[[1], [4]]]))

if __name__ == '__main__':
    unittest.main(verbosity=2)
