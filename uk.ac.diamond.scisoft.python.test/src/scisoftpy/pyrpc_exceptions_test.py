###
# Copyright 2011 Diamond Light Source Ltd.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
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
