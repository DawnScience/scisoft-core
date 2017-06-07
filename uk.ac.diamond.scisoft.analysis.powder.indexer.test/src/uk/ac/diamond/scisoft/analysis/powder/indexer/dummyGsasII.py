# Set up environment
import os, sys, logging

scisoftpath = '/scratch/DAWN_git/scisoft-core.git/uk.ac.diamond.scisoft.python/src' #TODO: no absolute path
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport

#GSASII dependencies
gsasIIPath = '/scratch/software/Indexers/GSASII_Indexing/gsas2' #TODO: lin kto environment varaible of gsasII instead
sys.path.append(gsasIIPath)
import GSASIIindex 

#Configure logger
logger = logging.getLogger('runGSASII')
hdlr = logging.FileHandler('/tmp/dummygsasII.log')
formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
hdlr.setFormatter(formatter)
logger.addHandler(hdlr) 
logger.setLevel(logging.DEBUG)

#A check to call if the file is avaliable
def isCallAvaliable():
    return callable

def getIndexing(peakData, controls, bravais):
    plausibleCells = "1,2,3,4,5,6,7,1,2,3,4,5,6,7"
    return plausibleCells    

# Identical functiosn to runGSASII available
logger.info("Rpc server call setup")
rpcserver = dnp.rpc.rpcserver(8715)
rpcserver.add_handler("AVAILABLE", isCallAvaliable)
rpcserver.add_handler("INDEXING", getIndexing)

logger.info("Waiting call indefinately...")
callable = True
rpcserver.serve_forever()
logger.debug("Server Closed")   
rpcserver.close()
rpcserver.shutdown()

