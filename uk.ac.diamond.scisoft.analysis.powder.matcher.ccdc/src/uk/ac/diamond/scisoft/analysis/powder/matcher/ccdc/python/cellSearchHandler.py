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
    import cellSearcher as cellLib
except ImportError:
    #This has some import problems...
    logger.error("Could not create generic cellSearcher setup...")


"""
TMP CURRENT CELL SEAARCHER STATE

Once module configured with handler correctly

"""
import sys
import os
import logging


import pprint as pp #Optional too?


from PIL import Image #Optional library for saving out images

#CCDC imports
from ccdc import io, search, molecule
from ccdc import crystal
from ccdc.io import MoleculeReader
from ccdc.io import EntryReader
from ccdc._lib import ChemistryLib
from ccdc.search import ReducedCellSearch
from ccdc.search import SimilaritySearch
from ccdc.search import TextNumericSearch
from ccdc.diagram import DiagramGenerator

fileName = os.path.basename(sys.argv[0])
fileName = fileName[:-3]

#Configuration CSD
entryReader = io.EntryReader('CSD')

#--    LOGGER CONFIGURATION     --#
logger = logging.getLogger(fileName)
logger.setLevel(logging.DEBUG)
# create console handler with a higher log level
ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)
# create file handler which logs even debug messages
fh = logging.FileHandler('/tmp/spamcellsearcher.log')
fh.setLevel(logging.DEBUG)
# create formatter and add it to the handlers
formatter = logging.Formatter('%(name)s - %(levelname)s - %(message)s')
fh.setFormatter(formatter)
ch.setFormatter(formatter)
# add the handlers to the logger
logger.addHandler(fh)
logger.addHandler(ch)



#--    HELPER FUNCTIONS    --#

def generateDiagram(refcode, filepath):
    """
    Generates a diagram on refcode.
    Creates img for crystal, molecule + entry

    :param refcode: refcode a unique identifier for //TODO: name of that please?
    :param filepath:
    :return: fulpath namee of generated *.png diagram
    """
    mol = entryReader.molecule(refcode)
    crystal = entryReader.crystal(refcode)

    generator = DiagramGenerator()
    # img = generator.image(crystal)
    # logStatus("Generated crystal diagram. ")
    # img.save(filepath + refcode + "crystal", "PNG")

    #BUG: seg fault if try and do both of these at once
    img = generator.image(mol)
    logger.info("Generated mole diagram")
    img.save(filepath + refcode, "PNG")
    return filepath + refcode + ".png"

def retrieveHits(searcher):
    """

    :param searcher: expects searcher Search type
    :return: ccdc.search.SearchHit
    """
    hits =  searcher.search()
    logger.info("Retrieve hits: " + str(len(hits)))
    return hits

def displayHits(hits):
    """
    Configure format to display all the information inside a relating to the crystal components.

    :param hits: ccdc.search.SearchHit
    """
    logger.info("Cell Matches: ")
    for i in hits:
        logger.info("Hit Entry Identifier: " + str(i.identifier))
        logger.info("CCDC Number : " + str(i.entry.ccdc_number))
        logger.info("Similarity Values")
        displayCrystal(i.crystal)

def displayCrystal(crystal):
    """
    Formats pretty particular values from a CCDC.crystal

    :param crystal: ccdc.crystal.Crystal
    """
    logger.info("Crystal Lengths:" + str(crystal.reduced_cell.cell_lengths))
    logger.info("Crystal Angles: " + str(crystal.reduced_cell.cell_angles))
    logger.info("Lattice Centering: " + str(crystal.lattice_centring))
    logger.info("Molecule Formula " + str(crystal.molecule.formula))
    logger.info("Spacegroup " + str(crystal.crystal_system))


def query(refcode):
    """
    Performs crystal search query on refcode
    :param refcode: refcode format of  six letters + digits  ... n
    :return: ccdc.entry.Entry
    """
    logger.info("Reading in refcode entry: "+ str(refcode))
    return entryReader.crystal(refcode)




#--    SEARCH CONFIGURATION    --#

# TODO: assumes a value is left there...

def setCrystalLength(inCrystal, nA=None, nB=None, nC=None):
    """
    @deprecated: 
    :param inCrystal: ccdc.crystal.Crystal
    :param nA: float
    :param nB: float
    :param nC: float
    :return: ccdc.crystal.Crystal
    """
    if (nA == None):
        cellLens = inCrystal.reduced_cell.cell_lengths
        nA = cellLens.a
    if (nB == None):
        cellLens = inCrystal.reduced_cell.cell_lengths
        nB = cellLens.b
    if (nC == None):
        cellLens = inCrystal.reduced_cell.cell_lengths
        nC = cellLens.c
    inCrystal = inCrystal.reduced_cell.cell_lengths._replace(a=nA, b=nB, c=nC)
    return inCrystal



def setCrystalAngles(inCrystal, alp, bet, gam):
    """
    @deprecated: 
    :param inCrystal: ccdc.crystal.Crystal
    :param alp: float
    :param bet: float
    :param gam: float
    :return: ccdc.crystal.Crystal
    """
    if (alp == None):
        cellAngs = inCrystal.reduced_cell.cell_angles
        alp = cellAngs.alpha
    if (bet == None):
        cellAngs = inCrystal.reduced_cell.cell_angles
        bet = cellAngs.beta
    if (gam == None):
        cellAngs = inCrystal.reduced_cell.cell_angles
        gam = cellAngs.gamma
    inCrystal = inCrystal.reduced_cell.cell_angles._replace(alpha=alp, beta=bet, gamma=gam)
    return inCrystal


def setSpacegroup(inCyrstal, spaceGroup):
    """
    @deprecated: 
    TODO: validation
    :param inCyrstal: ccdc.crystal.Crystal
    :param spaceGroup:  
    :return: ccdc.crystal.Crystal
    """
    outCrystal = inCyrstal.spacegroup_symbol._replace(spaceGroup)
    return outCrystal


def setFormula(inCrystal, formula):
    """
    @deprecated: 
    TODO: validation
    :param inCrystal: ccdc.crystal.Crystal
    :param formula: string
    :return: ccdc.crystal.Crystal
    """
    return inCrystal


#--    SEARCHING QUERYS  --#

def searchRefcode(refCode):
    """
    TODO: validation refcode
    :param refCode: refcode format of  six letters + digits  ... n
    :return: ccdc.search.SearchHit
    """
    query = TextNumericSearch()
    query.add_all_identifiers(refCode)

    hits = query.search()
    return hits

def searchCrystal(inCrystal):
    """
    Search on a configured crystal. Potentially configured by crystal configuration
    functions or a based on a entry of CCDC,

    :param inCrystal: ccdc.crystal.Crystal
    :return: ccdc.search.SearchHit
    """
    query = search.ReducedCellSearch.CrystalQuery(inCrystal)
    searcher = search.ReducedCellSearch(query)
    return retrieveHits(searcher)

def searchCrystalTols(inCrystal, absAngTol,percentLenTol):
    """

    :param inCrystal: ccdc.crystal.Crystal
    :param absAngTol: float 
    :param percentLenTol: float 
    :return: ccdc.search.SearchHit
    """
    query = search.ReducedCellSearch.CrystalQuery(inCrystal)
    searcher = search.ReducedCellSearch(query)

    if(percentLenTol != None):
        searcher.settings.percent_length_tolerance = percentLenTol
        logger.info(searcher.settings.percent_length_tolerance)
    if (absAngTol != None):
        searcher.settings.absolute_angle_tolerance = absAngTol
        logger.info(searcher.settings.absolute_angle_tolerance)
    return retrieveHits(searcher)


def searchCellVals(a,b,c):
    """
    The generic lattice centering does not work...
    @deprecated: need to set crystal system
    :param a: float
    :param b: float
    :param c: float 
    :return: ccdc.search.SearchHit
    """
    cellLen = crystal.CellLengths(a,b,c)
    query = ReducedCellSearch.Query(lengths=cellLen)
    specifics = query._get_query()
    searcher= ReducedCellSearch(query)
    return retrieveHits(searcher)

def searchCellAngles(alpha,beta,gamma):
    """
    @deprecated:
    :param alpha: float
    :param beta: float
    :param gamma: float
    :return: ccdc.search.SearchHit
    """
    cellAngles = crystal.CellAngles(alpha,beta,gamma)
    query = ReducedCellSearch.Query(angles=cellAngles)
    searcher = ReducedCellSearch(query)
    return retrieveHits(searcher)


def unitCellQuery(cellLengths, cellAngles, centring):
    """
    :param cellLengths: float
    :param cellAngles: float
    :param centring: ChemLin.Spacegroupcentering TODO: validation?
    :return: ccdc.search.SearchHit
    """
    logger.info("Created cell angles"+ str(cellAngles))
    logger.info("Created cell lengths" + str(cellLengths))
    logger.info("Set to centering" + str(centring))

    query = ReducedCellSearch.Query(angles=cellAngles, lengths=cellLengths, lattice_centring=centring)
    searcher = ReducedCellSearch(query)

    return retrieveHits(searcher)


def unitCellQuerySettings(cellLengths, cellAngles, centring, absAngTol,percentLenTol):
    """

    :param cellLengths: float
    :param cellAngles: float
    :param centring: float
    :param absAngTol: float
    :param percentLenTol: float
    :return: ccdc.search.SearchHit
    """
    logger.info("Created cell angles"+ str(cellAngles))
    logger.info("Created cell lengths" + str(cellLengths))
    logger.info("Set to centering" + str(centring))

    query = ReducedCellSearch.Query(angles=cellAngles, lengths=cellLengths, lattice_centring=centring)
    searcher = ReducedCellSearch(query)

    if(percentLenTol != None):
        searcher.settings.percent_length_tolerance = percentLenTol
    if (absAngTol != None):
        searcher.settings.absolute_angle_tolerance = absAngTol

    return retrieveHits(searcher)

def searchCentering(center):
    """
    TODO: how generate safely
    :param center:
    """
    pass

def searchSpaceGroup(spacegroup):
    """
    TODO: validate spacegroup... isValid believe does not work how would like
    :param spacegroup:s
    """
    pass

def searchCCDCNumber(num):
    """
    TODO: look at email reply got
    :param num:
    :return: ccdc.search.SearchHit
    """
    searcher = TextNumericSearch()
    searcher.add_ccdc_number(num)
    return retrieveHits(searcher)

def searchChemicalFormula(formula):
    """
    :param formula:
    """
    pass

def searchName(name):
    """
    :param name:
    :return: ccdc.search.SearchHit
    """
    searcher = TextNumericSearch()
    searcher.add_synonym(name)
    return retrieveHits(searcher)

def filterHitsHasAtleastElements(hits, elements):
    """

    :param hits: ccdc.search.SearchHit
    :param elements: list of elements to filter on capitalised
    :return: ccdc.search.SearchHit
    """
    filterHits = []
    for hit in hits:
        #TODO: might not have a molecule. or hit.molecule
        atoms = hit.molecule.atoms

        for atom in atoms:
            if (atom.atomic_symbol in elements):
                filterHits.append(hit)
                break

    return filterHits

def filterHitsMustHaveElements(hits, elements):
    """
    :param hits: ccdc.search.SearchHit
    :param elements: list of elements to filter on capitalised
    :return: ccdc.search.SearchHit
    """
    filterHits = []
    for hit in hits:
        #TODO: might not have a molecule.
        atoms = hit.molecule.atoms
        atomSymbols = []
        for atom in atoms:
            atomSymbols.append(atom.atomic_symbol)
        if(set(elements) < set(atomSymbols)):
            filterHits.append(hit)

    return filterHits


def filterHitsFormula(hits, formula):
    """

    :param hits:
    :param formula:
    :return: ccdc.search.SearchHit
    """
    filterHits = []
    for hit in hits:
        #TODO: might not have a molecule.
        if(hit.molecule.formula == formula):
            filterHits.append(hit)
    return filterHits





#--     INPUT AND OUTPUT CRYSTALS       --//



def extractCrystalCif(filepath):
    """
    TODO: Can i store molecule information inside and that'll be in crystal?
    :param filepath:
    :return:
    """
    reader = io.EntryReader(filepath + ".cif")
    entry_from_cif = reader[0]
    logger.info(entry_from_cif.crystal)
    displayCrystal(entry_from_cif.crystal)
    return entry_from_cif.crystal;


def saveCrystalCif(filepath, filename, crystal):
    """

    :param filepath:
    :param filename:
    :param crystal:
    """
    logger.info("Saving: " + filepath + filename)
    with io.CrystalWriter(os.path.join(filepath, filename + '.cif')) as crystal_writer:
        crystal_writer.write(crystal)

def saveRefcodeCif(filepath, refcode):
    """

    :param filepath:
    :param refcode:
    """
    saveCrystalCif(filepath, refcode, query(refcode))
    logger.info("Generated cif for " + refcode)







"""
EXPERMINENTAL METHODs.

Potential dynamic load of setting.

To just query the Python for a structure and then load that subsequent structure on the java side.
Why fill out all the specific settings when they need to eventually be passed.
Can flag up the type on a return

TODO: how load the setup on the java sides..

"""
# def gatherSettings():
#     pass
#     #Can dnp.flatten the settings
#  
#  
# """
# Gath
# """   
# def gatherCrystalSettings(crystal):
#     crystal
#     pass
#     #Can dnp.flatten the settings
#     
     

def gatherSpaceGroups():
    """
    The order of these responds to the order desired.
    Someone like a enum when pass in the optitions.

    This could for example be dynamically loaded into a combo box and selected from that.

    :return: ordered enum of spaceGroups with corresponding text
    """
    spaceGroupsOps = ChemistryLib.Spacegroup_centring_text()
    #Split of text
    return spaceGroupsOps


def reportGenerator(filepath,refcode):
    """
    :param filepath:
    :param refcode:
    :return:path to where generated html report can be found
    """
    entry = EntryReader('csd').entry(refcode)
    mol = entry.molecule
    atoms = mol.atoms
    bonds = mol.bonds
    img = DiagramGenerator().image(mol)
    doi = entry.publication.doi
    if doi is None:
        doi = '&nbsp;'
    else:
        doi = '<a href="http://dx.doi.org/%s">%s</a>' % (doi, doi)

    template_file_name = os.path.join(
        os.path.dirname(__file__), 'simple_report_template.html'
    )

    template = unicode(open(template_file_name).read())

    fileGenPath = os.path.join(filepath + refcode+ '.html')
    with open(fileGenPath, 'w') as html:
        s = template.format(
            entry=entry,
            molecule=mol,
            image=img,
            doi=doi,
            synonyms='; '.join(s for s in entry.synonyms),
            counts=dict(
                natoms=len(atoms),
                ndonors=len([a for a in atoms if a.is_donor]),
                nacceptors=len([a for a in atoms if a.is_acceptor]),
                nrot_bonds=len([b for b in bonds if b.is_rotatable]),
            ),
        )
        html.write(s.encode('utf8'))

    return fileGenPath

























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
        self._unitCellQuery = unitCellQuery

        self._searchCellVals = searchCellVals

        #self.cifQueryPath = cellLib.extractCrystalCif

        self._strDetails = query

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

        self.searchHits = self._unitCellQuery(cellAngles=cellAngles, cellLengths=cellLengths, centring=centring)
        logger.info("Success in running request for find cell matches")
        displayHits(self.searchHits)

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
        #cellLib.displayHits(self.searchHits)

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
            self.searchHits = searchCrystal(self.searchCrystal)
            logger.info("Success in running cell matches with crystal")
        else:
            logger.info("Running tolerance search")
            self.searchHits = searchCrystalTols(self.searchCrystal,self.angTol,self.lengthTol)
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
        self.searchHits = filterHitsHasAtleastElements(self.searchHits,elements)
        return True

    def saveCifAllHits(self, filepath):
        """
        TODO: how make the save file unique for each search? need to just call the refocde cif
        :param filepath:
        """
        for hit in self.searchHits:
            saveCrystalCif(filepath, hit.identifier,hit)


    def saveRefcodeCif(self,filepath, refcode):
        """

        :param filepath:
        :param refcode:
        :return:
        """
        saveCrystalCif(filepath, refcode, self.entryReader.crystal(refcode))
        logger.info("Generated cif for " + refcode)
        return True


    def generateReportRefcode(self,filepath,refcode):
        """

        :param self:
        :param filepath:
        :param refcode:
        :return:
        """
        reportGenerator(filepath,refcode)
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


