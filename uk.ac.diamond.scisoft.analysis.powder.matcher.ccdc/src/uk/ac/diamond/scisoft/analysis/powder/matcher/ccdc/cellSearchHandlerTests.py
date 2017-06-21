"""
The wrapper interface that runs as a server for xmlrpc java calls.


Uses XMLRPC framework to package the calls from remotes. For example java.

All activity is then log and current state is found in logStatus() 

Every function handler returns either a success or object.

TODO: maybe can gather the results elsewhere and just have the calls passable. Well can store the hits inside the searcher...
TODO: restraint addition on searcher    searcher.add_distance_constraint('HA', 0, 1, 1, 0, (0, 4.0), 'Inter')

#TODO: which
is lock safe and can be queried whilst function is handled. The
length of function run is unfortunately undeterminate. Depends on the search
procedure.

TODO: have the identifying keys for handlers in specific file
"""

import sys
import os
import logging
import cellSearcher
import types
scisoftpath = '/scratch/DAWN_git/scisoft-core.git/uk.ac.diamond.scisoft.python/src' #TODO: no absolute path
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport

from ccdc import io, search, molecule
from ccdc import crystal
from ccdc._lib import ChemistryLib
from ccdc import crystal
from ccdc.search import Search
from ccdc.search import ReducedCellSearch
from ccdc.search import SimilaritySearch
from ccdc.search import TextNumericSearch
from ccdc.diagram import DiagramGenerator

import cellSearchHandler as handler


def createSearcher():
    handler.CellSearcher
    assert True


def findCell():
    

def findElement():
    
def findC    
