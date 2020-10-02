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

try: # attempt to prevent OpenMPI initialization issues
    import mpi4py
    mpi4py.rc(initialize=False)
except:
    pass

import os, sys
scisoftpath = os.path.abspath(os.path.join('..', 'uk.ac.diamond.scisoft.python', 'src'))
sys.path.append(scisoftpath)

import scisoftpy as dnp #@UnresolvedImport
import threading

# This is the server that is going to loopback locally to python
rpcserver1 = dnp.rpc.rpcserver(0)
rpcserver1.add_handler("loopback", lambda arg: arg)

internal_rpcclient = dnp.rpc.rpcclient(rpcserver1.port)
rpcserver2 = dnp.rpc.rpcserver(0)
rpcserver2.add_handler("loopback", lambda arg: arg)
rpcserver2.add_handler("loopback_after_local", lambda arg: internal_rpcclient.loopback(arg))

# returns port in stdout
print('server_port:{}'.format(rpcserver2.port))
sys.stdout.flush()

t2 = threading.Thread(target=rpcserver2.serve_forever)
t2.start()

rpcserver1.serve_forever()


