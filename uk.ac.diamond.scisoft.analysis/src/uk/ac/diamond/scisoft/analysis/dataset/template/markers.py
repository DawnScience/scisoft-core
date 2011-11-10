#!/usr/bin/env python
'''
transmutation class for markers

It performs line-by-line substitutions based on markers embedded in comments.

Mark up source class with following comment markers:
// DATA_TYPE      - abstract dataset constant
// CLASS_TYPE     - boxed primitive class
// PRIM_TYPE      - java primitive type
// PRIM_TYPE_LONG - java primitive type (cast to long first if integer)
// GET_ELEMENT    - use get element method
// FROM_OBJECT    - use convert from object method
// REAL_ONLY      - keep line when a real dataset
// OBJECT_UNEQUAL - use object inequality
// OBJECT_USE     - use commented out code
// BOOLEAN_OMIT   - omit line when boolean dataset
// BOOLEAN_USE    - use commented out code
// BOOLEAN_FALSE  - return false when boolean dataset
// BOOLEAN_ZERO   - return zero when boolean dataset
// FORMAT_STRING  - format string for getString method
// DEFAULT_VAL    - default value for expanded dataset
// INT_EXCEPTION  - surround with try/catch for integer arithmetic exception
// INT_ZEROTEST   - use commented out code for testing for integer zero
// ADD_CAST       - add a cast to primitive type
// OMIT_SAME_CAST - omit a cast to same type 
// OMIT_REAL_CAST - omit a cast to real type 
// OMIT_CAST_INT  - omit a cast for int type 
// OMIT_UPCAST    - omit a cast to same type 
// IGNORE_CLASS   - ignored dataset class used in line
// GEN_COMMENT    - replace this with a message about generated class
@SuppressWarnings("cast")
'''

class transmutate(object):
    def __init__(self, scriptfile, srcclass, source, dstclass, destination, disreal=True,
    disbool=False, disobj=False):
        '''
        scriptfile
        srcclass
        source
        dstclass
        destination
        disreal indicates whether destination is a real dataset
        disbool indicates whether destination is a boolean dataset
        disobj indicates whether destination is an object type-dataset

        source and destination are lists of strings which describe dtype,
        Java boxed primitive class, Java primitive type, getElement abstract method,
        Object converter toReal, string format, default expansion value
        (from class constant)
        '''
        self.sdsclass = srcclass
        self.ddsclass = dstclass
        self.commentline = "// Produced from %s.java by %s" % (srcclass, scriptfile)

        if len(source) != len(destination):
            raise ValueError, "length of lists should be the same"

        (self.sdtype, self.spclass, self.sprim, self.sgetel,
        self.sconv, self.sform, self.sdef) = source
        (self.ddtype, self.dpclass, self.dprim, self.dgetel,
        self.dconv, self.dform, self.ddef) = destination

        self.dcast = "(" + self.dprim + ") "

        if (self.ddtype.startswith("INT") or self.ddtype.startswith("ARRAYINT")) and self.dprim is not "long":
            self.dprimlong = self.dprim + ") (long"
        else:
            self.dprimlong = self.dprim

        self.isreal = disreal
        self.isbool = disbool
        self.isobj = disobj
        if self.isbool:
            self.isreal = False

        from ordereddict import OrderedDict #@UnresolvedImport
        self.processors = OrderedDict([ ("// DATA_TYPE", self.data),
            ("// CLASS_TYPE", self.jpclass),
            ("// PRIM_TYPE", self.primitive),
            ("// ADD_CAST", self.addcast),
            ("// PRIM_TYPE_LONG", self.primitivelong),
            ("// GET_ELEMENT", self.getelement),
            ("// GET_ELEMENT_WITH_CAST", self.getelementcast),
            ("// FROM_OBJECT", self.fromobj),
            ("// REAL_ONLY", self.unrealomit),
            ("// OBJECT_UNEQUAL", self.unequal),
            ("// OBJECT_USE", self.objuse),
            ("// BOOLEAN_OMIT", self.boolomit),
            ("// BOOLEAN_USE", self.booluse),
            ("// BOOLEAN_FALSE", self.boolfalse),
            ("// BOOLEAN_ZERO", self.boolzero),
            ("// FORMAT_STRING", self.string),
            ("// INT_EXCEPTION", self.intexception),
            ("// INT_ZEROTEST", self.intzerotest),
            ("// OMIT_SAME_CAST", self.omitcast),
            ("// OMIT_REAL_CAST", self.omitrealcast),
            ("// OMIT_CAST_INT", self.omitcastint),
            ("// OMIT_UPCAST", self.omitupcast),
            ("// DEFAULT_VAL", self.defval),
            ("@SuppressWarnings(\"cast\")", self.omit) ])

        self.icasts = [ "(byte) ", "(short) ", "(int) ", "(long) "]
        self.rcasts = [ "(float) ", "(double) "]

        # also // IGNORE_CLASS
#        print "prim |", self.dprim, "| conv |", self.dconv, "| cast |", self.dcast
#        if self.dprim in self.dconv:
#            print 'found primitive matches cast'

        self.processors[srcclass] = self.jclass

    def data(self, line):
        '''
        dataset type
        '''
        return line.replace(self.sdtype, self.ddtype)

    def jclass(self, line):
        '''
        dataset name is also used as Java class name
        '''
        return line.replace(self.sdsclass, self.ddsclass)

    def jpclass(self, line):
        '''
        Java class name for boxed primitive
        '''
        if self.isobj:
            l = line.replace(self.spclass, '')
            l = l.replace('new ', '')
            return l
        return line.replace(self.spclass, self.dpclass)

    def primitive(self, line):
        '''
        java primitive type is an element type
        '''
        return line.replace(self.sprim, self.dprim)

    def primitivelong(self, line):
        return line.replace(self.dprim, self.dprimlong)

    def getelement(self, line):
        return line.replace(self.sgetel, self.dgetel)

    def getelementcast(self, line):
        l = line.replace(self.sgetel, self.dgetel)
        if not self.isobj and self.dprim in self.dconv:
            l = self.addcast(l)
        return l

    def addcast(self, line):
        return line.replace(' = ', ' = ' + self.dcast)

    def fromobj(self, line):
#        if self.isobj or self.dconv is None:
#            l = line.replace(self.sconv, '')
#            return l
        return line.replace(self.sconv, self.dconv)

    def omitcast(self, line):
        return line.replace(self.dcast, "")

    def omitcastint(self, line):
        if self.isreal or self.isbool:
            return line
        for c in self.icasts:
            if c in line:
                return line.replace(c, "")
        return line

    def omitrealcast(self, line):
#        if self.isreal:
#            return line
        for c in self.rcasts:
            if c in line:
                return line.replace(c, "")
        return line

    def omitupcast(self, line):
        if self.isreal or self.isbool:
            return line
        f = False
        for c in self.icasts:
#            print line, c, f
            if c == self.dcast:
                f = True
            if f and c in line:
                return line.replace(c, "")
        return line

    def omit(self, line):
        return None

    def unrealomit(self, line):
        if not self.isreal:
            return None
        return line

    def unequal(self, line):
        if not self.isobj:
            return line
        l = line.replace(' != ', '.equals(')
        l = l.replace('if (', 'if (!')
        return l.replace(') ', ')) ')

    def objuse(self, line):
        if self.isobj: # uncomment line
            s = line.find("// ")
            return line[:s] + line[s+3:]
        return line

    def boolomit(self, line):
        if self.isbool or self.isobj:
            return None
        return line.replace(" // BOOLEAN_OMIT", "")

    def booluse(self, line):
        if self.isbool: # uncomment line
            s = line.find("// ")
            return line[:s] + line[s+3:]
        return line

    def boolfalse(self, line):
        if self.isbool or self.isobj:
            return "\t\treturn false;"
        return line

    def boolzero(self, line):
        if self.isbool or self.isobj:
            return "\t\treturn 0;"
        return line

    def string(self, line):
        if self.isobj and 'String' == self.dpclass:
            s = line.find(self.sform)
            b = line.find("String")
            e = line.find(';', s)
            return line[:b] + line[s+len(self.sform) + 2:(e-1)] + line[e:]
        return line.replace(self.sform, self.dform)

    def intexception(self, line):
        if self.ddtype.startswith("INT"):
            nformat = "\t\t\t\ttry {\n\t%s\n\t\t\t\t} catch (ArithmeticException e) {\n\t\t\t\t\t%s] = 0;\n\t\t\t\t}"
            lhs = line.split('] ')[0].lstrip()
            return nformat % (line, lhs)
        elif self.ddtype.startswith("ARRAYINT"):
            nformat = "\t\t\t\t\t\ttry {\n\t%s\n\t\t\t\t\t\t} catch (ArithmeticException e) {\n\t\t\t\t\t\t\t%s] = 0;\n\t\t\t\t\t\t}"
            lhs = line.split('] ')[0].lstrip()
            return nformat % (line, lhs)
        else:
            return line

    def intzerotest(self, line):
        if self.ddtype.startswith("INT") or self.ddtype.startswith("ARRAYINT"):
            s = line.find("// ")
            return line[:s] + line[s+3:]
        else:
            return line

    def defval(self, line):
        if self.isobj:
            return None
        return line.replace(self.sdef, self.ddef)

    def processline(self, line):
        '''
        return processed line
        '''
        l = line.rstrip()
        if l.find("// IGNORE_CLASS") >= 0:
            return l
        for m in self.processors:
            if l == None or len(l) == 0:
                break
            if l.find(m) >= 0:
                p = self.processors[m]
                l = p(l)
        if l != None:
            if l.find("// GEN_COMMENT") >= 0:
                return self.commentline
        return l
