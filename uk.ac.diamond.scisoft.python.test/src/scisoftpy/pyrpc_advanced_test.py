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
import threading

PORT = 8715

class Test(unittest.TestCase):

    def testMultipleHandlers(self):
        rpcserver = rpc.rpcserver(PORT)
        rpcserver.add_handler("cat", lambda s1, s2: s1 + s2)
        rpcserver.add_handler("len", lambda s1, s2: len(s1 + s2))
        
        thread.start_new_thread(rpcserver.serve_forever, ())
        try:
            rpcclient = rpc.rpcclient(PORT)
            result = rpcclient.cat("Hello, ", "World!")
            self.assertEqual("Hello, World!", result)
            result = rpcclient.len("Hello, ", "World!")
            self.assertEqual(len("Hello, World!"), result)
        finally:
            rpcserver.shutdown()
            rpcserver.close()

    def testExceptionOnHandlerFlattening(self):
        rpcserver = rpc.rpcserver(PORT)
        rpcserver.add_handler("flaterror", lambda o: object())
        
        thread.start_new_thread(rpcserver.serve_forever, ())
        try:
            rpcclient = rpc.rpcclient(PORT)
            self.assertRaises(Exception, rpcclient.flaterror, ("Hello",))
        finally:
            rpcserver.shutdown()
            rpcserver.close()

    def testSingleIntegerArg(self):
        rpcserver = rpc.rpcserver(PORT)
        rpcserver.add_handler("echo", lambda o: o)
        
        thread.start_new_thread(rpcserver.serve_forever, ())
        try:
            rpcclient = rpc.rpcclient(PORT)
            self.assertRaises(Exception, rpcclient.flaterror, (18,))
        finally:
            rpcserver.shutdown()
            rpcserver.close()

    def testConnectionTimesOutQuicklyEnough(self):
        rpcclient = rpc.rpcclient(PORT)
        t = threading.Thread(target=self.assertRaises, args=(Exception, rpcclient.doesnotexist, ("Hello",)))
        t.start()
        t.join(2.0)
        self.assertFalse(t.isAlive())
        


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
