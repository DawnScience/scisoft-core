'''
Created on 1 May 2011

@author: Jonah
'''
import unittest
import scisoftpy.python.pyrpc as rpc
import thread

PORT = 8713

def catTwoStrings(string1, string2):
    return string1 + string2

class Test(unittest.TestCase):

    def testBasic(self):
        
        rpcserver = rpc.rpcserver(PORT)
        rpcserver.add_handler("cat", catTwoStrings)
        
        thread.start_new_thread(rpcserver.serve_forever, ())
        try:
            rpcclient = rpc.rpcclient(PORT)
            result = rpcclient.cat("Hello, ", "World!")
            
            self.assertEqual("Hello, World!", result)
        finally:
            rpcserver.shutdown()
            rpcserver.close()


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
