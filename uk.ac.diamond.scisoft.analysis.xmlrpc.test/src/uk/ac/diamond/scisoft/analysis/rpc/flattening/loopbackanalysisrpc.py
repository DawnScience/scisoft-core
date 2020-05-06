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

import os, sys
scisoftpath = os.path.abspath(os.path.join('..', 'uk.ac.diamond.scisoft.python', 'src'))
sys.path.append(scisoftpath)

import scisoftpy as dnp #@UnresolvedImport
import threading

internal_rpcclient = dnp.rpc.rpcclient(8715)
rpcserver = dnp.rpc.rpcserver(8714)
rpcserver.add_handler("loopback", lambda arg: arg)
rpcserver.add_handler("loopback_after_local", lambda arg: internal_rpcclient.loopback(arg))
t = threading.Thread(target=rpcserver.serve_forever)
t.start()

# This is the server that is going to loopback locally to python
rpcserver = dnp.rpc.rpcserver(8715)
rpcserver.add_handler("loopback", lambda arg: arg)
rpcserver.serve_forever()
