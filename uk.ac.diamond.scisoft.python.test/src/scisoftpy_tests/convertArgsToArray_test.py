
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
        self.checkitems(returned_args[0], dnp.array(list1))
        self.assertTrue(isinstance(returned_args[1], list))
        self.assertEquals(returned_args[1], list2)
        self.assertTrue(isinstance(returned_args[2], dnp.ndarray))
        self.checkitems(returned_args[2], dnp.array(tuple1))
        self.assertTrue(isinstance(returned_args[3], int))
        self.assertTrue(isinstance(returned_args[4], dnp.ndarray))
        self.checkitems(returned_args[4], array1)

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
        self.assertEquals(returned_args[0], list1)
        self.assertTrue(isinstance(returned_args[1], dnp.ndarray))
        self.checkitems(returned_args[1], array1)

    def testDocString(self):

        @_argsToArrayType
        def checkArgs(*args):
            '''Return arguments unmodified'''
            return (args)

        self.assertEquals(inspect.getdoc(checkArgs), '''Return arguments unmodified''')
        
    def testKeywordArgName(self):

        @_argsToArrayType('second_array', 'third_array')
        def checkKeywordArgName(first_array, second_array=dnp.array([8, 9]), third_array=dnp.array([10, 11]), fourth_array=dnp.array([12, 13])):
            return (first_array, second_array, third_array, fourth_array)

        array1 = dnp.array([1, 2, 3])
        list1 = [4, 5]
        list2 = [6, 7]
        returned_args = checkKeywordArgName(array1, second_array=list1, third_array=list2)
        self.assertTrue(isinstance(returned_args[1], dnp.ndarray))
        self.checkitems(returned_args[1], dnp.array(list1))
        self.assertTrue(isinstance(returned_args[2], dnp.ndarray))
        self.checkitems(returned_args[2], dnp.array(list2))
        
        returned_keyword_args = checkKeywordArgName(array1, third_array=list2)
        self.assertTrue(isinstance(returned_keyword_args[1], dnp.ndarray))
        self.checkitems(returned_keyword_args[1], dnp.array([8, 9]))
        self.assertTrue(isinstance(returned_keyword_args[2], dnp.ndarray))
        self.checkitems(returned_keyword_args[2], dnp.array([6, 7]))

    def testConcatenate(self):
        
        list1 = [[1, 2, 3], [4, 5, 6]]
        list2 = [[13, 14, 15], [16, 17, 18]]        
        arr1 = dnp.array([[0, 1, 2], [3, 4, 5]])
        arr2 = dnp.array([[6, 7, 8], [9, 10, 11]])
        arr3 = dnp.array([1, 2, 3])
        arr4 = dnp.array([13, 14, 15])

        a = dnp.concatenate(list1)
        self.assertEquals(a.shape, (6,))

        b = dnp.concatenate(arr1)
        self.assertEquals(b.shape, (2, 3))

        c = dnp.concatenate((arr1, arr2))
        self.assertEquals(c.shape, (4, 3))

        d = dnp.concatenate((arr3, arr4))
        self.assertEquals(d.shape, (6, ))

        e = dnp.concatenate((list1, list2))
        self.assertEquals(e.shape, (4, 3))

        f = dnp.concatenate([list1, list2])
        self.assertEquals(f.shape, (4, 3))


def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
