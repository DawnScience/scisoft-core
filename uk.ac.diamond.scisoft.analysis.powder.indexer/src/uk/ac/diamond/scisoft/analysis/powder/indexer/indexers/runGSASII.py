import os, sys, logging
scisoftpath = '/scratch/DAWN_git/scisoft-core.git/uk.ac.diamond.scisoft.python/src' #TODO: no absolute path
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport

#GSASII dependencies
gsasIIPath = '/scratch/software/Indexers/GSASII_Indexing/gsas2' #TODO: link to environment varaible of gsasII instead
sys.path.append(gsasIIPath)
import GSASIIindex 

#Configure logger
logger = logging.getLogger('runGSASII')
hdlr = logging.FileHandler('/tmp/gsasII.log')
formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
hdlr.setFormatter(formatter)
logger.addHandler(hdlr) 
logger.setLevel(logging.DEBUG)

#TODO: REMOVE LATER POINT ONCE DEPENDENCY ON WX IN GSASII GONE
#TODO: JSON string return objects - go that weby route - that casting time though
import wx #TODO: does this requrie a load of ana??

def fake():
    return 0,0,0,0

class faker(wx.ProgressDialog):
    
    def __init__(self, title, message, maximum, style):
        print("intialised faker")
    def Update(self, *args, **kwargs):
        #(continue, skip)
        return (True, False)
    def Pulse(self, *args, **kwaradgs):
        return (True, False)
    def GetSize(self):
        return (0,0)
    def SetPosition(self,arg):
        """do nuthin"""
    def Destroy(self):
        """Guess ignore special clean up"""
    
wx.ClientDisplayRect = fake

wx.ProgressDialog = faker

#Approx Cell Unit After: 530.39,0,Pm3m,5.8789,5.8789,5.8789,90.00,90.00,90.00,203.178
#Cube-P system to speed up search
# testPeaks = [ 10.7569, 5.69600, 8.05950, 9.87500, 11.40750, 12.75950, 13.98300, 16.15950, 17.14650,
#                 18.08200, 18.97250, 19.81635, 20.64200, 21.43100, 22.93000, 23.64550, 24.34150, 25.01900, 25.68050,
#                 26.32550, 26.95700 ]


callable = False

#A check to call if the file is avaliable
def isCallAvaliable():
    return callable

#call it more a wrapper to just initialised a wx.App()
#side note in the GSASII implementation there might be no difference between a peak and peaks ...
def getIndexing(peakData, controls, bravais):
    '''
    Peform indexing call to gsasII and return a list of plasible cells.
    
    :param peakData -> list[peak[x,y],...,*]
    :param controls -> [UNKNOWN_UNUSED,zero=0,ncno = 4 ,volume=25,]
    :param bravais  -> true/false grid of - ['Cubic-F','Cubic-I','Cubic-P','Trigonal-R','Trigonal/Hexagonal-P',
    'Tetragonal-I','Tetragonal-P','Orthorhombic-F','Orthorhombic-I','Orthorhombic-C',
    'Orthorhombic-P','Monoclinic-C','Monoclinic-P','Triclinic']
    
    return plausibleCells -> [[M20,a,b,c,alp,bet,gam],...,*] + what bravais found on? urghh not gettable...will need to change crystal system ...
    '''
    
    logger.info("Gathering Indexing")
    logger.info("Check types setup")
    logger.info("Raw Peak Data:")
    rawPeaks = [str(i) for i in peakData]
    logger.info(rawPeaks)
    
    controlsLog = [str(i)  for i in controls ]
    logger.info("Controls Given: ")
    logger.info(controlsLog)
    
    logger.info("Bravais Search: ")
    bravaisLog = [str(i) for i in bravais ]
    logger.info(bravaisLog)
    
    app = wx.App() #Creating fake UI app
    
    #peaks = [] #peak ->[position,intensity,use,indexed,h,k,l,d-obs,d-calc]  
    
    #Might not be able to do this way as the method might depend on the x values... TODO: more research to see if 
    #gsasIIindex.py calls any adjustments based on second point

    #Confgiure peaks for how GSASII runs it     
    #TMP fake x values - THE RUN DOESNT DEPEND ON ACCURATE X VALUES
    peaks= []
    for peakIdx in range(0,len(peakData)):
        peakConfigure = [float(peakIdx),peakData[peakIdx],True,False,0,0,0,1.0,0.0] #the x position might be just there to pass around ... that what it seemed in the algo...i hope i hope 
        peaks.append(peakConfigure)
    
    logger.info("Configured Indexer Peaks: ")
    for peak in peaks:
        peakLog = [str(type(i)) for i in peak]
        logger.info(peakLog)
    
    logger.info("Calling upong gsasII index of peaks")   

    success,dmin,cells = GSASIIindex.DoIndexPeaks(peaks, controls, bravais)
    
    logger.info("Indexing of peaks gsasII call complete")
    logger.info("Number of cells found: " + str(len(cells)))
    
    #Cell Contents:
    #M20,X20,ibrav,a,b,c,alp,bet,gam,V,False,False0
    #Merit,MeritUp,bravaisOn,a,b,c,alp,bet,gam,Volume,
    
    #The ibrav index corresponds to one of the below crystal system cell searches
    bravaisNames = ['Cubic-F','Cubic-I','Cubic-P','Trigonal-R','Trigonal/Hexagonal-P',
    'Tetragonal-I','Tetragonal-P','Orthorhombic-F','Orthorhombic-I','Orthorhombic-C',
    'Orthorhombic-P','Monoclinic-C','Monoclinic-P','Triclinic']
    
    plausibleCells = []
    
    if success:
        logger.info("Plausible cells produced: M20,a,b,c,alp,bet,gam")
        for i in cells:
            #print "%10.3f %10.5S %10.5f %10.5f %10.3f %10.3f %10.3f" % (i[0],bravaisNames[i[2]],i[3],i[4],i[5],i[6],i[7],i[8]) #(M20,a,b,c,alp,bet,gam)
            releventCell = [i[0],i[3],i[4],i[5],i[6],i[7],i[8]]   
            logger.info(releventCell)
            plausibleCells.append(releventCell)
    else:
        ogger.debug("Failure in indexing call, invalid search paramters or no plausible matches")
    
    cast = dnp.array(plausibleCells)
    logger.info("Attempting to return plausible cells")
    
    flattenCells = [val for sublist in plausibleCells for val in sublist]

    #stringigy list
    strCells = ','.join(map(str, flattenCells)) 
    
    
#     strList = []
#     for cell in plausibleCells:
#         strCell = [str(i) for i in cell]
#         strList.append(strCell)
    
    logger.info("Create string of data")
    logger.info(strCells)
    
    #just return a string array
    return strCells
    
    
#Test mode components
def getTestSampleData():
    plausibleCells = "1,2,3,4,5,6,7,1,2,3,4,5,6,7"
    return plausibleCells

#rpcserver.add _handler("TESTDATA", getTestSampleData)

# Make the fancy functions available
logger.info("Rpc server call setup")
rpcserver = dnp.rpc.rpcserver(8715)
rpcserver.add_handler("AVAILABLE", isCallAvaliable)
rpcserver.add_handler("INDEXING", getIndexing)

#Tests handlers
rpcserver.add_handler("TESTDATA", getTestSampleData)

logger.info("Waiting call indefinately...")
callable = True
rpcserver.serve_forever()
logger.debug("Server Closed")   
rpcserver.close()
rpcserver.shutdown()

