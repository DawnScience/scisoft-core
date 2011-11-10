#!/usr/bin/env python
'''
From compound double dataset generate other classes

$ python fromcpddouble.py ../CompoundDoubleDataset.java

'''

from markers import transmutate #@UnresolvedImport

# default dataset definition
defds = { "CompoundDoubleDataset":["ARRAYFLOAT64", "Double", "double", "getElementDoubleAbs", "toReal(obj)", "%.8g",
"NaN"] }

defkey = defds.keys()[0]

# all other dataset definitions
fds = { "CompoundFloatDataset":["ARRAYFLOAT32", "Float", "float", "getElementDoubleAbs", "(float) toReal(obj)", "%.8g",
"NaN"] }

allds = { 
"CompoundIntegerDataset":["ARRAYINT32", "Integer", "int", "getElementLongAbs", "(int) toLong(obj)", "%d",
"MIN_VALUE"],
"CompoundLongDataset":["ARRAYINT64", "Long", "long", "getElementLongAbs", "toLong(obj)", "%d",
"MIN_VALUE"],
"CompoundShortDataset":["ARRAYINT16", "Short", "short", "getElementLongAbs", "(short) toLong(obj)", "%d",
"MIN_VALUE"],
"CompoundByteDataset":["ARRAYINT8", "Byte", "byte", "getElementLongAbs", "(byte) toLong(obj)", "%d",
"MIN_VALUE"]
 }

def generateclass(dclass):
    handlers  = [ transmutate(__file__, defkey, defds[defkey], d, fds[d], True) for d in fds ]
    handlers += [ transmutate(__file__, defkey, defds[defkey], d, allds[d], False) for d in allds ]
    files  = [ open(d + ".java", "w") for d in fds ]
    files += [ open(d + ".java", "w") for d in allds ]
    ncls = len(files)

    while True:
        l = dclass.readline()
        if not l:
            break
        for n in range(ncls):
            nl = handlers[n].processline(l)
            if nl != None:
                print >> files[n], nl

if __name__ == '__main__':
    import sys
    if len(sys.argv) > 1:
        fname = sys.argv[1]
    else:
        fname = "CompoundDoubleDataset.java"

    dclass_file = open(fname, 'r')

    generateclass(dclass_file)
