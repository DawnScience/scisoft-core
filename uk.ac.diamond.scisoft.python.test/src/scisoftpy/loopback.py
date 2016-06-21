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
Provide a loop back mechanism that allows Java to call into this script and have it call SDAPlotter right back
'''
# Note: manual add of path is needed when this is run as an automated test by:
# AllPyPlotMethodsPluginTest.java
# AllPyPlotMethodsTest.java
import os, sys
sys.path.append(sys.argv[1])
import scisoftpy as dnp #@UnresolvedImport
import thread

server = dnp.rpc.rpcserver(8912)
server.add_handler("addpoints",    lambda *args: dnp.plot.addpoints(*args))
server.add_handler("addline",      lambda *args: dnp.plot.addline(*args))
server.add_handler("getbean",      lambda *args: dnp.plot.getbean(*args))
server.add_handler("getdatabean",  lambda *args: dnp.plot.getdatabean(*args))
server.add_handler("getfiles",     lambda *args: dnp.plot.getfiles(*args))
server.add_handler("getguinames",  lambda *args: dnp.plot.getguinames(*args))
server.add_handler("image",        lambda *args: dnp.plot.image(*args))
server.add_handler("images",       lambda *args: dnp.plot.images(*args))
server.add_handler("line",         lambda *args: dnp.plot.line(*args))
server.add_handler("points",       lambda *args: dnp.plot.points(*args))
server.add_handler("scanforimages",lambda *args: dnp.plot.scanforimages(*args))
server.add_handler("setbean",      lambda *args: dnp.plot.setbean(*args))
server.add_handler("setdatabean",  lambda *args: dnp.plot.setdatabean(*args))
server.add_handler("setroi",       lambda *args: dnp.plot.setroi(*args))
server.add_handler("setrois",      lambda *args: dnp.plot.setrois(*args))
server.add_handler("stack",        lambda *args: dnp.plot.stack(*args))
server.add_handler("surface",      lambda *args: dnp.plot.surface(*args))
server.add_handler("updateline",   lambda *args: dnp.plot.updateline(*args))
server.add_handler("updateplot",   lambda *args: dnp.plot.updateplot(*args))
server.add_handler("updatestack",  lambda *args: dnp.plot.updatestack(*args))
server.add_handler("viewnexus",    lambda *args: dnp.plot.viewnexus(*args))
server.add_handler("volume",       lambda *args: dnp.plot.volume(*args))

## Not part of SDAPlotter, used to control dnp in other ways
server.add_handler("setremoteport_rpc", lambda port: dnp.plot.setremoteport(rpcport=port))

server.serve_forever()
