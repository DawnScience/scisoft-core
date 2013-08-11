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
AnalysisRpc implementation in Python
'''
from SocketServer import ThreadingMixIn
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler, SimpleXMLRPCServer
from xmlrpclib import ServerProxy
import scisoftpy.python.pyflatten as _flatten

class _method:
    def __init__(self, send, destination):
        self._send = send
        self._destination = destination
    def __call__(self, *args):
        return self._send(self._destination, args)

class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/RPC2',)
    
class ThreadedSimpleXMLRPCServer(ThreadingMixIn, SimpleXMLRPCServer):
    pass

class rpcserver(object):
    '''
    An AnalysisRpc Server to serve up Python functions to other processes via RPC
    '''
    def __init__(self, port):
        '''
        Create a new AnalysisRpc Server listening on the specified port
        '''
        self._server = ThreadedSimpleXMLRPCServer(("127.0.0.1", port), requestHandler=RequestHandler, logRequests=False)
        self._server.register_introspection_functions()
        
        self._server.register_function(self._xmlrpchandler, 'Analysis.handler');
        self._server.register_function(self._xmlrpc_is_alive, 'Analysis.is_alive');
        self._handlers = dict()
   
    def _xmlrpchandler(self, destination, args):
        try:
            handler = self._handlers.get(destination)
            if handler is None:
                ret = Exception("No handler registered for " + destination)
            else:
                unflattened = map(_flatten.unflatten, args)
                ret = handler(*unflattened)
            flatret = _flatten.flatten(ret)
        except Exception, e:
            flatret = _flatten.flatten(e)
        return flatret
    
    def _xmlrpc_is_alive(self):
        return True

    def add_handler(self, name, function):
        '''
        Register a new function with the Server. The function
        will be called when a request to the given name is made
        '''
        self._handlers[name] = function
        
    def serve_forever(self):
        '''
        Serve the RPC forever. The function does not return unless
        shutdown() is called from another thread. 
        '''
        self._server.serve_forever()
        
    def shutdown(self):
        '''
        Shutdown the RPC Server. Must be called after serve_forever 
        or it will deadlock.
        Only available in Python >= 2.6
        '''
        self._server.shutdown()
        
    def close(self):
        '''
        Close the port related to the server
        '''
        self._server.server_close()
        

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
        self._serverProxy = ServerProxy("http://127.0.0.1:%d" % port)
        self._port = port
        
    def request(self, destination, params):
        '''
        Perform a request to the Server, directing it at the destination
        which was registered as the handler on the server, passing
        it the params specified.
        params must be a tuple or a list of the arguments
        '''
        flatargs = _flatten.flatten(params)
        flatret = self._serverProxy.Analysis.handler(destination, flatargs)
        unflatret = _flatten.unflatten(flatret)
        if (isinstance(unflatret, Exception)):
            raise unflatret
        return unflatret
    def is_alive(self):
        try:
            self._serverProxy.Analysis.is_alive()
            return True
        except:
            return False
    
    def __getattr__(self, destination):
        return _method(self.request, destination)
    
    
