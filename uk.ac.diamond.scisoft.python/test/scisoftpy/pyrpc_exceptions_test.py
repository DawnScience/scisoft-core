###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

'''
Created on 1 May 2011

@author: Jonah
'''
import unittest
import scisoftpy.python.pyrpc as rpc
import thread

PORT = 8714

class Test(unittest.TestCase):


    def setUp(self):
        self.rpcserver = rpc.rpcserver(PORT)
        self.rpcserver.add_handler("cat", lambda s1, s2: s1 + s2)
        thread.start_new_thread(self.rpcserver.serve_forever, ())
        
        self.rpcclient = rpc.rpcclient(PORT)

    def tearDown(self):
        self.rpcserver.shutdown()
        self.rpcserver.close()


    def testBasicOperation(self):
        result = self.rpcclient.cat("Hello, ", "World!")   
        self.assertEqual("Hello, World!", result)
        
    def testHandlerExceptionOperation(self):
        # Test a missing argument
        self.assertRaises(Exception, self.rpcclient.cat, ("Hello",))

    def testInternalExceptionOperation(self):
        # force a flattening error on call side
        self.assertRaises(TypeError, self.rpcclient.cat, (object(),))
        
    def testNoMatchingHandlerException(self):
        # force a not found on the call side error
        self.assertRaises(Exception, self.rpcclient.cat_invalid, ("Hello",))
        try:
            self.rpcclient.cat_invalid("Hello")
        except Exception, e:
            self.assertEquals("No handler registered for cat_invalid", e.args[0])   
            
        

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
