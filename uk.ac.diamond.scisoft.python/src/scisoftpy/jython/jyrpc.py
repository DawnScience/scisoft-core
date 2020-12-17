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
from __future__ import print_function

import uk.ac.diamond.scisoft.analysis.rpc as _rpc
from org.eclipse.dawnsci.analysis.api.rpc import IAnalysisRpcHandler as _IAnalysisRpcHandler, AnalysisRpcException as _AnalysisRpcException

from java.lang import UnsupportedOperationException as _USOExc

import threading

from .jyflatten import flatten as _flat, unflatten as _unflat

class _wrap(_IAnalysisRpcHandler):
    def __init__(self, function):
        self._func = function

    def run(self, args): # Java side passes an array of Objects
        return self._func(*args) # so unpack arguments

class rpcserver(object):
    '''
    An AnalysisRpc Server to serve up Python functions to other processes via RPC
    '''
    def __init__(self, port=0):
        self._server = _rpc.AnalysisRpcServer(port)
        self._port = port
        self._condition = None

    @property
    def port(self):
        if not self._port:
            self._port = self._server.getPort()
        return self._port

    def add_handler(self, name, function):
        '''
        Register a new function with the Server. The function
        will be called when a request to the given name is made
        '''
        self._server.addHandler(name, _wrap(function))
        
    def serve_forever(self):
        '''
        Serve the RPC forever. The function does not return unless
        shutdown() is called from another thread. 
        '''
        self._server.start()
        self._condition = threading.Condition(threading.RLock())
        with self._condition:
            self._condition.wait()
        self._condition = None
        
    def shutdown(self):
        '''
        Shutdown the RPC Server. Must be called after serve_forever 
        or it will deadlock.
        Only available in Python >= 2.6
        '''
        self._server.shutdown()
        if self._condition:
            with self._condition:
                self._condition.notify_all()
        
    def close(self):
        '''
        Close the port related to the server
        '''
        self._server.close()

class _method:
    def __init__(self, send, destination):
        self._send = send
        self._destination = destination
    def __call__(self, *args):
        try:
            return self._send(self._destination, args)
        except _AnalysisRpcException as e: # wrap cause as general exception
            c = e.getCause()
            if isinstance(c, _USOExc):
                out = str(c.getCause())
                raise TypeError(out)
            bits = str(c).split('Exception: ')
            out = 'Exception: ' + bits[-1] # just pass last part of message
            raise Exception(out)

class rpcclient(object):
    '''
    An AnalysisRpc Client, used to connect to an AnalysisRpc server
    in another process.
    Calls to the server can be made either with the request method
    or as an attribute of the rpcclient instance.
    '''
    def __init__(self, port):
        ''' 
        Create a new AnalysisRpc Client which will connect on the specified port
        '''
        self._client = _rpc.AnalysisRpcClient(port)
        self._port = port
        
    def is_alive(self):
        return self._client.isAlive()

    def request(self, destination, params):
        '''
        Perform a request to the Server, directing it at the destination
        which was registered as the handler on the server, passing
        it the params specified.
        params must be a tuple or a list of the arguments
        '''
        return self._client.request(destination, params)

    def request_debug(self, destination, params, suspend):
        '''
        Perform a request to the Server starting debug if server supports
        it, directing it at the destination
        which was registered as the handler on the server, passing
        it the params specified.
        params must be a tuple or a list of the arguments
        suspend if true, suspends on entry
        '''
        return self._client.request_debug(destination, params, suspend)

    def __getattr__(self, destination):
        return _method(self.request, destination)
    