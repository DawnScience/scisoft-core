
import unittest
import scisoftpy as dnp

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

    def testInput(self):
        list_input = [1, 2, 3]
        tuple_input = (1, 2, 3)
        arr_input = dnp.array([1, 2, 3])

        zeros_output = dnp.array([0, 0, 0])

        zeros_like_list = dnp.zeros_like(list_input)
        zeros_like_tuple = dnp.zeros_like(tuple_input)
        zeros_like_arr = dnp.zeros_like(arr_input)

        self.assertEquals(zeros_like_list.shape, (3,))
        self.checkitems(zeros_like_list, zeros_output)

        self.assertEquals(zeros_like_tuple.shape, (3,))
        self.checkitems(zeros_like_tuple, zeros_output)

        self.assertEquals(zeros_like_arr.shape, (3,))
        self.checkitems(zeros_like_arr, zeros_output)

    def test2dInput(self):
        arr_input = dnp.array([[1, 2], [3, 4], [5, 6]])
        list_input = [[1, 2], [3, 4], [5, 6]]

        zeros_output = dnp.array([[0, 0], [0, 0], [0, 0]])

        zeros_like_arr = dnp.zeros_like(arr_input)
        zeros_like_list = dnp.zeros_like(list_input)

        self.assertEquals(zeros_like_arr.shape, (3,2))
        self.checkitems(zeros_like_arr, zeros_output)

        self.assertEquals(zeros_like_list.shape, (3,2))
        self.checkitems(zeros_like_list, zeros_output)

    def testEmptyListInput(self):
        array_input = dnp.array([])
        list_input = []

        zeros_output = dnp.array([])

        zeros_like_array = dnp.zeros_like(list_input)
        zeros_like_list = dnp.zeros_like(list_input)

        self.assertEquals(zeros_like_array.shape, (0,))
        self.checkitems(zeros_like_array, zeros_output)

        self.assertEquals(zeros_like_list.shape, (0,))
        self.checkitems(zeros_like_list, zeros_output)


def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
