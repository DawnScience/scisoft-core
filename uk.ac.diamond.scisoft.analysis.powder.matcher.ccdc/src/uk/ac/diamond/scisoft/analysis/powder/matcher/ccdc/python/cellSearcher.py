"""
A generic searching setup.

Unit cell configuration information is asked for and the searched based on this.

Every function handler returns either a success or object. 


TODO's
TODO: test framework
#TODO: Greylog??
#TODO: make note to search on spacegroups setting can be set with characaters P,I,F,A,B,C,R
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




def playgroundQuery():

    query('ABEBUF')

    #Just wanted a diagram genereted
    #generateDiagram("ARAVIZ", "/tmp/")


    pathSaved = reportGenerator("/tmp/","ARAVIZ")
    print("Generated diagram for ARAVIZ :"+ pathSaved)
    
    
    saveRefcodeCif("/tmp/","ARAVIZ")
    
    cry = extractCrystalCif("/scratch/CCDC_playground/CCDCPlay/referenceCode/testsCCDC/testdata/CACDOA01");

    isActive = True

    #XXX: cant create own crystal so will just be settign the first entry...
    firstEntry = entryReader[0]
    meCrystal = firstEntry.crystal
    #searchCrystal(meCrystal)
    logger.info(meCrystal.formula)

    #TMP JUST NEED SAMPLE DATA
    a = 5.4311946
    b = 5.4311946
    c = 5.4311946
    alp = 90
    beta = 90
    gamma = 90
#     11.1991
#     11.8541
#     14.0985
#     93.606
#     113.098
#     103.500
    
    
    logger.info("Seraching Unit Cell")
    logger.info("Lenths: " + str(a) + "," + str(b) + ","  + str(c))
    logger.info("Angles: " + str(alp) + ","  +str(beta) + ","  + str(gamma))
    #logger.info("Lattice Centering: " + ChemistryLib.Spacegroup_centring_text().text(1))

    #Example search
    centring = ChemistryLib.Spacegroup_centring_text().text(1) #Primitive
    cellAngles = crystal.CellAngles(alp,beta,gamma)
    cellLengths = crystal.CellLengths(a,b,c)


    displayHits(searchCellVals(a,b,c))



    attemptCry = crystal.Crystal
    attemptCry.lattice_centring =  ChemistryLib.Spacegroup_centring_text().text(1)
    attemptCry.cell_angles = cellAngles
    attemptCry.cell_lengths = cellLengths
    logger.info(attemptCry.cell_angles)
    hits = searchCrystal(attemptCry)

    displayHits(hits)

    logger.info("\n\nRestraining crystal on formula");
    logger.info(attemptCry.molecule)
    #
    mol = molecule.Molecule
    mol.formula = "C1 H6 Cl1 N3 O4"
    attemptCry.molecule = mol

    hits = searchCrystal(attemptCry)
    displayHits(hits)
    #
    # logger.info("\n\nRestraining crystal on spacegroup");
    # attemptCry.crystal_system = "cubic"


    hits = searchCrystal(attemptCry)
    displayHits(hits)

    logger.info("\n\nTurning tolerance off hack");

    absAngleTol = 180
    perLenTol = 100

    hits = searchCrystalTols(attemptCry,absAngleTol,perLenTol)
    displayCrystal(hits[-1].crystal)
    #displayHits(hits)

    #
    #
    #
    #
    # logger.info("\n\nSearching Hits:")
    # hits = unitCellQuery(cellLengths, cellAngles, centring)
    #
    # displayHits(hits)
    #
    #
    # formula = "C1 H6 Cl1 N3 O4"
    # logger.info("\n\nFiltered Formula:" + str(formula))
    # filteredHits = filterHitsFormula(hits,formula)
    # displayHits(filteredHits)

    # elements = ['Cl']
    # logger.info("\n\nElement restrictions:" + str(elements))
    #
    # logger.info("\n\nFiltered Hits Contains:")
    # filteredHits = filterHitsHasAtleastElements(hits, elements)
    # displayHits(filteredHits)
    #
    # logger.info("\n\nFiltered Hits Must Contain:")
    # filteredHits = filterHitsMustHaveElements(hits, elements)
    # displayHits(filteredHits)
    #


#logger.info("Running settings search")
#    hits = unitCellQuerySettings(cellLengths,cellAngles,centring,absAngTol=50,percentLenTol=20)

#   displayHits(hits)

    #pass id = name
    #emptyCrystal = crystal.Crystal(ChemistryLib.ConcreteCrystalStructure())
    #
    # emptyCrystal = crystal.Crystal(ChemistryLib.ConcreteCrystalStructure(), "unknown") #This is broken however as has no values set inside it
    # #emptyCentre = emptyCrystal.spacegroup().centring()
    # emptyCrystal.ReducedCell(ChemistryLib.Spacegroup_centring_text().text(0))
    #
    # centring = ChemistryLib.Spacegroup_centring_text().text(1) #Primitive
    # cellAngles = crystal.CellAngles(80,70,70)
    # cellLengths = crystal.CellLengths(8,10,10)
    #
    # emptyCrystal.ReducedCell(meCrystal._crystal.cell())
    #
    #
    # setCrystalLength(emptyCrystal, cellLengths.a,cellLengths.b,cellLengths.c)
    # setCrystalAngles(emptyCrystal,cellAngles.alpha,cellAngles.beta,cellAngles.gamma)
    # emptyCrystal.cell_lengths = cellLengths
    #
    # cell   =  ChemistryLib.Cell()
    # cell.set_spacegroup(centring)
    # ChemistryLib.ReducedCell(cell)
    # emptyCrystal= emptyCrystal.reduced_cell._replace(meCrystal.reduced_cell)
    #
    # searchCrystal(emptyCrystal)


    #TODO: to really get at everything meCrystal._crystal.cell()
    #TODO: could a *.cell().is_valid() check the changes...
    #TODO: tp validate

    # while(isActive):
    #     meCrystal = inLengths(meCrystal) #I know were using a ref and not copying I just dont like imaginary changes :/



if __name__ == '__main__':
    playgroundQuery()

