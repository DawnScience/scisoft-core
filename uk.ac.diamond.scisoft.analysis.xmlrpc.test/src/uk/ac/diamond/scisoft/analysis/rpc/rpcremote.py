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

# Set up environment
import os, sys
scisoftpath = os.getcwd() + '/../uk.ac.diamond.scisoft.python/src'
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport

# define fancy function which is easier to write in Python
def python_cos(ds):
    '''Performs a cos() on ds input'''
    return dnp.cos(ds)


# Make the fancy function available
rpcserver = dnp.rpc.rpcserver(0)
rpcserver.add_handler("cos", python_cos)

# returns port in stdout
print('server_port:{}'.format(rpcserver.port))
sys.stdout.flush()

rpcserver.serve_forever()
