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
Run this test manually to sanity check plotting in a running SDA when this 
script is launched from the host Eclipse Dev.
If launched as jython, will use RMI to access plotserver
If launched as python, will use AnalysisRpc to access plotserver, via SDAPlotter
''' 

# Note: manual add of path is needed when this is run as an automated test by:
# AutomatedManualPlottingPluginTest.java
import os, sys
scisoftpath = os.getcwd() + '/../uk.ac.diamond.scisoft.python/src'
sys.path.append(scisoftpath)
import scisoftpy as dnp

dnp.rpc.settemplocation('d:/temp/bug')

a = dnp.arange(100)
a.shape = (10,10)
if os.name == "java":
    plotName = "Plot 1 DNP Java"
else:
    plotName = "Plot 1 DNP Python"
dnp.plot.image(a, name=plotName)

