"""
The wrapper interface that runs as a server for xmlrpc java calls.


Uses XMLRPC framework to package the calls from remote. For example java.

All activity is then log and current state is found in logStatus() 

Every function handler returns either a success or object.

TODO: maybe can gather the results elsewhere and just have the calls passable. Well can store the hits inside the searcher...
TODO: restraint addition on searcher    searcher.add_distance_constraint('HA', 0, 1, 1, 0, (0, 4.0), 'Inter')

#TODO: which
is lock safe and can be queried whilst function is handled. The
length of function run is unfortunately indeterminate. Depends on the search
procedure.

TODO: have the identifying keys for handlers in specific file

TODO: a query system to expose the handler functions and feed them back. Rather than just hoping the java keys are correct.
"""

#TODO: check dependencies seperate

import sys
import os
import logging
import types

#LOGGER CONFIGURATION
fileName = os.path.basename(sys.argv[0])
logger = logging.getLogger(fileName)
logger.setLevel(logging.DEBUG)
# create console handler with a higher log level
ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)
# create file handler which logs even debug messages
fh = logging.FileHandler('/tmp/interfaceCellSearcher.log')
fh.setLevel(logging.DEBUG)
# create formatter and add it to the handlers
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
fh.setFormatter(formatter)
ch.setFormatter(formatter)
# add the handlers to the logger
logger.addHandler(fh)
logger.addHandler(ch)

import os 
dir_path = os.path.dirname(os.path.realpath(__file__))
print(dir_path)

import os
cwd = os.getcwd()
print(cwd)


#Adding additional module directoyr 
sys.path.insert(0,'/some/directory') 

#TODO: is essential to have scisoftpy? probably not create own python class for handling xmlrpc components
# scisoftpath = '/scratch/DAWN_git/scisoft-core.git/uk.ac.diamond.scisoft.python/src' #TODO: no absolute path
# sys.path.append(scisoftpath)
#TODO: there are lots of configuration within the pyrpc. can not really remove such a nice configuration for running the server.
#The better plan is to make sure the scisoft dnp is ready to go

try:
    import scisoftpy as dnp #@UnresolvedImport
except ImportError:
    raise ImportError('Scisoftpy is not avaliable')
    #scisoftpy is not avaliable... should handle this appropriotely 
    #Can I grab a error from the process spawning this? even then in xmlrpc do I get errors easyily?
    #logger.error("Scisoft Py was not configured")
    

#Attempt try at import first

#Check python path setup for CSDS

try:
    user_paths = os.environ['PYTHONPATH'].split(os.pathsep)
    #print(user_paths)
except KeyError:
    user_paths = []


#print(os.environ['LD_LIBRARY_PATH'])

#Check licensing 
#CCDC_CSD_LICENCE_FILE=
try:
    os.environ['CCDC_CSD_LICENCE_FILE']
except TypeError:
    raise "CCDC_CSD_LICENCE_FILE license path is not configured. Attempt setting environment towards license location"


#CSDHOME - might not matter if on PYTHON PATH
try:
    os.environ['CSDHOME']
except TypeError:
    raise "CSDHOME path is not configured. Attempt installing CCDC or setting environment towards CSD installation"


try:
    from ccdc import io, search, molecule
    from ccdc import crystal
    from ccdc._lib import ChemistryLib
    from ccdc import crystal
    from ccdc.search import Search
    from ccdc.search import ReducedCellSearch
    from ccdc.search import SimilaritySearch
    from ccdc.search import TextNumericSearch
    from ccdc.diagram import DiagramGenerator
    from ccdc.io import EntryReader
except ImportError: 
    raise ImportError('Part of the CCDC is not avaliable')





try: 
    import cellSearcher.py
except ImportError:
    #This has some import problems...
    logger.error("Could not create generic cellSearcher setup...")




global currentStatus

"""
TODO:
"""
def getStatus():
    currentStatus

serverAvaliable = False

"""
Call to check if server is running
"""
def isCallAvaliable():
    return serverAvaliable

class CellSearcher:
    """
    Initialise searcher to querying the CCDC.
    Configurable and re runnable.
    """
    def __init__(self):
        #Hit results from search
        self.searchHits = []

        #Config for search
        self.overallQuery = None
        self.searchCrystal = crystal.Crystal

        self.entryReader = io.EntryReader('CSD')
                    #TODO: tmp just set default centring
        self.searchCrystal.lattice_centring =  ChemistryLib.Spacegroup_centring_text().text(1)


        #Lattice Params
        self.cellAngles = None #crystal.CellAngles()
        self.cellLengths = None #     crystal.CellLengths()
        self.angTol = None
        self.lengthTol = None

        #Filtering After Search
        self.refcode = None
        self.ccdcNumber = None
        self.atomicElements = []

        """
        Functional Assignment
        """
        self.unitCellQuery = cellSearcher.unitCellQuery

        self._searchCellVals = cellSearcher.searchCellVals

        #self.cifQueryPath = cellSearcher.extractCrystalCif

        self.strDetails = cellSearcher.query

        """
        Filtering that can be performed on the hits
        """
        self.filterOnSpacegroup = None

    def findCellMatches(self,a,b,c,alpha,beta,gamma):
        """
        TODO: docs


        :param a:
        :param b:
        :param c:
        :param alpha:
        :param beta:
        :param gamma:
        :return:
        """
        #Validate input
        arguments = locals()
        logger.info('The following arguments were passed: '+ ', '.join(k for k, v in arguments.items() if v is not None))

        cellAngles = crystal.CellAngles(alpha, beta, gamma)
        logger.info("Created cell angles"+ str(cellAngles))

        cellLengths = crystal.CellLengths(a, b, c)
        logger.info("Created cell lengths" + str(cellLengths))

        #Tmp kept as Primitive
        centring = ChemistryLib.Spacegroup_centring_text().text(1)
        logger.info("Set to centering" + str(centring))

        self.searchHits = self.unitCellQuery(cellAngles=cellAngles, cellLengths=cellLengths, centring=centring)
        logger.info("Success in running request for find cell matches")
        cellSearcher.displayHits(self.searchHits)

        return True

    def gatherMatches(self):
        """
        Set up as a raw string gather matches for now
        TODO: create crystal flattener

        :return:
        """
        #Refine the hit list here to match group.
        #TMP: right now im just going to return a list of identifiers
        logger.info("Sending request for hits results\n\n")
        #cellSearcher.displayHits(self.searchHits)

        allHits = []
        #TODO: cast to typem
        ## TMP str return
        #line = ""
        #for hit in self.searchHits:
        #      line += hit.crystal.to_string(format='sdf') +"\n"

        #Quick + dirty format to just display on other side
        for hit in self.searchHits:
            #logger.info("Casting one hit " + hit.crystal.to_string(format='sdf'))
            detailsImp = []
            logger.info(hit.molecule.identifier)
            logger.info("CCDC number: " + str(hit.entry.ccdc_number))
            
            details = EntryReader('CSD').entry(hit.identifier)
            detailsImp.append(hit.identifier) # refcode
            logger.info(str(details.crystal.cell_lengths[0]))
            
            detailsImp.append(str(details.crystal.cell_lengths[0]))
            
            detailsImp.append(str(details.crystal.cell_lengths[1]))
            detailsImp.append(str(details.crystal.cell_lengths[2]))

            detailsImp.append(str(details.crystal.cell_angles[0]))
            detailsImp.append(str(details.crystal.cell_angles[1]))
            detailsImp.append(str(details.crystal.cell_angles[2]))

            detailsImp.append(hit.crystal.formula)

            #detailsImp.append(hit.crystal.lattice_centring)
            allHits.append(detailsImp)

        return allHits


    def searchConfiguredCrystal(self):
        """
        Search on preonfigured self.searchCrystal

        :return:
        """
        if(self.searchCrystal.cell_angles == None):
              logger.debug("Attempting search on null cell angles")
              return False
        if(self.searchCrystal.cell_lengths == None):
              logger.debug("Attempting search on null cell legnths")
              return False
        if(self.searchCrystal.lattice_centring == None):
              logger.debug("Attempting search on null cell centering")
              return False
        
        logger.info("Searching on crystal: ")
        logger.info(self.searchCrystal)
        logger.info(self.searchCrystal.cell_angles)
        logger.info(self.searchCrystal.cell_lengths)


        if(self.angTol == None and self.lengthTol == None):
            self.searchHits = cellSearcher.searchCrystal(self.searchCrystal)
            logger.info("Success in running cell matches with crystal")
        else:
            logger.info("Running tolerance search")
            self.searchHits = cellSearcher.searchCrystalTols(self.searchCrystal,self.angTol,self.lengthTol)
            logger.info("Success in running cell matches  with crystal under configured tolerance")

        return True

    ##Search Configuration Mechanisms

    def setLatticeParameters(self,a,b,c,alpha,beta,gamma):
        """

        :param a:
        :param b:
        :param c:
        :param alpha:
        :param beta:
        :param gamma:
        :return:
        """
        self.cellAngles = crystal.CellAngles(alpha, beta, gamma)
        self.cellLengths = crystal.CellLengths(a, b, c)

        #Crystal set
        self.searchCrystal.cell_angles = self.cellAngles
        self.searchCrystal.cell_lengths = self.cellLengths

        logger.info("Set crystal: ")
        logger.info(self.searchCrystal.cell_angles)
        logger.info(self.searchCrystal.cell_lengths)

        return True

    def setLatticeTolerance(self, absoluteAngleTol,percentLengthTol):
        """

        :param absoluteAngleTol:
        :param percentLengthTol:
        :return:
        """
        self.angTol = absoluteAngleTol
        self.lengthTol = percentLengthTol
        logger.info("Success in setting tolerance")
        #TODO: set crystals
        return True

    def setRefCode(self, refcode):
        """

        :param refcode:
        :return:
        """
        self.refcode = refcode
        return True

    def setCCDCNum(self, ccdcNum):
        """

        :param ccdcNum:
        :return:
        """
        self.ccdcNumber = ccdcNum
        return True

    ##Filtered Search hits

    def filterOnFormula(self, formula):
        """
        TODO: check if correct format

        :param formula: string of formula in standard -> "C1 H6 Cl1 N3 O4"
        :return:
        """
        self.searchHits = cellSearcher.filterHitsFormula(self.searchHits,formula)
        return True

    def filterHitsOnElements(self, elements):
        """

        :param elements: strings of every atomic symbols to filter on
        :return:
        """
        elements = [element.capitalize() for element in elements]
        self.searchHits = cellSearcher.filterHitsHasAtleastElements(self.searchHits,elements)
        return True

    def saveCifAllHits(self, filepath):
        """
        TODO: how make the save file unique for each search? need to just call the refocde cif
        :param filepath:
        """
        for hit in self.searchHits:
            cellSearcher.saveCrystalCif(filepath, hit.identifier,hit)


    def saveRefcodeCif(self,filepath, refcode):
        """

        :param filepath:
        :param refcode:
        :return:
        """
        cellSearcher.saveCrystalCif(filepath, refcode, self.entryReader.crystal(refcode))
        logger.info("Generated cif for " + refcode)
        return True


    def generateReportRefcode(self,filepath,refcode):
        """

        :param self:
        :param filepath:
        :param refcode:
        :return:
        """
        cellSearcher.reportGenerator(filepath,refcode)
        logger.info("Generated report at " + filepath + " with refcode " + refcode )
        return True

    #TODO: Weight up flatterners


    def crystalFlattener(self, obj):
        """
        The generic flattening procedure. Return object is dependent on the function called.
        :param self:
        :param obj:
        :return:
        """
        return dnp.flatten.flatten(obj)

    def crystalUnflatten(self,obj):
        """
        The generic unflattening procedure. Return object is dependent on the function called.
        :param self:
        :param obj:
        :return:
        """
        return dnp.flatten.unflatten(obj)



#CELL SEARCHER INSTANCE CONFIGURATION
searcher = CellSearcher()

# searcher.findCellMatches(5.4767,5.4767,5.4767,90,90,90)
# searcher.gatherMatches()

# Make the fancy functions available
logger.info("Rpc server call setup")
rpcserver = dnp.rpc.rpcserver(8700)

rpcserver.add_handler("AVALIABLE", isCallAvaliable)

#SEARCHER HANDLERS
rpcserver.add_handler("FINDCELLMATCHES", searcher.findCellMatches)
rpcserver.add_handler("GATHERMATCHES", searcher.gatherMatches)
rpcserver.add_handler("SEARCHCRYSTAL", searcher.searchConfiguredCrystal)

rpcserver.add_handler("SETREFCODE", searcher.setRefCode)
rpcserver.add_handler("SETCDCNUM", searcher.setCCDCNum)
rpcserver.add_handler("SETLATTICE", searcher.setLatticeParameters)
rpcserver.add_handler("SETTOLLATTICE", searcher.setLatticeTolerance)

#TODO:If I am filtering here I wonder why? just filter on java. Is the cost of sending more outweighing to the cost of the python filter 
rpcserver.add_handler("FILTERELEMENTS", searcher.filterHitsOnElements)

#Output generators
rpcserver.add_handler("SAVECIFREFCODE", searcher.saveRefcodeCif)
rpcserver.add_handler("GENERATEREPORTREFCODE",searcher.generateReportRefcode)

logger.info("Waiting call indefinitely...")
serverAvaliable = True
rpcserver.serve_forever()
logger.debug("Server Closed")
rpcserver.close()
rpcserver.shutdown()


