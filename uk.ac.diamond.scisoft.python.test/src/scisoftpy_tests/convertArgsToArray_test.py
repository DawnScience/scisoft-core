
import unittest
import scisoftpy as dnp
import inspect
from scisoftpy.jython.jycore import _argsToArrayType

class Test(unittest.TestCase):

    def toAny(o):
        return o

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

    def testSelectedArgsToCheck(self):

        @_argsToArrayType('first_list', 'first_tuple', 'first_array')
        def check3Args(first_list, second_list, first_tuple, first_integer, first_array):
            return (first_list, second_list, first_tuple, first_integer, first_array)

        list1 = [1, 2, 3]
        list2 = [4, 5]
        tuple1 = (6, 7, 8)
        integer1 = 9
        array1 = dnp.array([[10, 11, 12], [13, 14, 15]])
        returned_args = check3Args(list1, list2, tuple1, integer1, array1)
        self.assertTrue(isinstance(returned_args[0], dnp.ndarray))
        self.checkitems(list1, returned_args[0])
        self.assertTrue(isinstance(returned_args[1], list))
        self.assertEqual(returned_args[1], list2)
        self.assertTrue(isinstance(returned_args[2], dnp.ndarray))
        self.checkitems(tuple1, returned_args[2])
        self.assertTrue(isinstance(returned_args[3], int))
        self.assertTrue(isinstance(returned_args[4], dnp.ndarray))
        self.checkitems(array1, returned_args[4])

    def testWrongArgName(self):

        with self.assertRaises(ValueError):
            @_argsToArrayType('first_list', 'wrongName')
            def checkWrongArgName(first_list, second_list):
                return (first_list, second_list)

    def testNoArgs(self):

        @_argsToArrayType
        def checkArgs(first_list, first_array):
            return (first_list, first_array)

        list1 = [1, 2, 3]
        array1 = dnp.array([4, 5, 6])
        returned_args = checkArgs(list1, array1)
        self.assertTrue(isinstance(returned_args[0], list))
        self.assertEqual(returned_args[0], list1)
        self.assertTrue(isinstance(returned_args[1], dnp.ndarray))
        self.checkitems(array1, returned_args[1])

    def testDocString(self):

        @_argsToArrayType
        def checkArgs(*args):
            '''Return arguments unmodified'''
            return (args)

        self.assertEqual(inspect.getdoc(checkArgs), '''Return arguments unmodified''')

    def testKeywordArgName(self):

        @_argsToArrayType('second_array', 'third_array')
        def checkKeywordArgName(first_array, second_array=dnp.array([8, 9]), third_array=dnp.array([10, 11]), fourth_array=dnp.array([12, 13])):
            return (first_array, second_array, third_array, fourth_array)

        array1 = dnp.array([1, 2, 3])
        list1 = [4, 5]
        list2 = [6, 7]
        returned_args = checkKeywordArgName(array1, second_array=list1, third_array=list2)
        self.assertTrue(isinstance(returned_args[1], dnp.ndarray))
        self.checkitems(list1, returned_args[1])
        self.assertTrue(isinstance(returned_args[2], dnp.ndarray))
        self.checkitems(list2, returned_args[2])

        returned_keyword_args = checkKeywordArgName(array1, third_array=list2)
        self.assertTrue(isinstance(returned_keyword_args[1], dnp.ndarray))
        self.checkitems([8, 9], returned_keyword_args[1])
        self.assertTrue(isinstance(returned_keyword_args[2], dnp.ndarray))
        self.checkitems([6, 7], returned_keyword_args[2])

    def testConcatenateSingleList(self):

        b = dnp.concatenate([[1,2],[2,3],[3,4]])
        self.assertEqual(b.shape, (6,))
        self.checkitems([1, 2, 2, 3, 3, 4], b)

        c = dnp.concatenate([[[1, 2], [3, 4], [5, 6]]])
        self.assertEqual(c.shape, (3, 2))
        self.checkitems([[1, 2], [3, 4], [5, 6]], c)

    def testConcatenateLists(self):

        list1 = [[1, 2, 3], [4, 5, 6]]
        list2 = [[13, 14, 15], [16, 17, 18]]     

        a = dnp.concatenate((list1, list2))
        self.assertEqual(a.shape, (4, 3))

    def testConcatenateSingleArray(self):

        b = dnp.concatenate(dnp.array([[1,2],[2,3],[3,4]]))
        self.assertEqual(b.shape, (6,))
        self.checkitems([1, 2, 2, 3, 3, 4], b)

        c = (dnp.arange(120)).reshape(2, 3, 4, 5)
        d = dnp.concatenate(c)
        self.assertEqual(d.shape, (6, 4, 5))

        e = dnp.concatenate(c, axis=1)
        self.assertEqual(e.shape, (3, 8, 5))

        f = dnp.concatenate(c, axis=2)
        self.assertEqual(f.shape, (3, 4, 10))

    def testConcatenateArrays(self):

        arr1 = dnp.array([[0, 1, 2], [3, 4, 5]])
        arr2 = dnp.array([[6, 7, 8], [9, 10, 11]])
        arr3 = dnp.array([1, 2, 3])
        arr4 = dnp.array([13, 14, 15])

        a = dnp.concatenate((arr1, arr2))
        self.assertEqual(a.shape, (4, 3))
        self.checkitems([[ 0,  1,  2], [ 3,  4,  5], [ 6,  7,  8], [ 9, 10, 11]], a)

        b = dnp.concatenate((arr1, arr2), axis=1)
        self.assertEqual(b.shape, (2, 6))
        self.checkitems([[ 0,  1,  2,  6,  7,  8], [ 3,  4,  5,  9, 10, 11]], b)

        c = dnp.concatenate((arr3, arr4))
        self.assertEqual(c.shape, (6, ))


def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
