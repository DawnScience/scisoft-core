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

#!/usr/bin/env python
'''
Takes a functions definition file and the shell of a Java class,
generates the methods and prints to standard output the completed class

Runs as follows
$ python generatefunctions.py functions.txt ../Maths.java > Maths.java

The format is

func: [number of parameters]
  foo - javadoc for foo
integer:
  ox = ix + 1
real:
  ox = ix + 1.5
complex:
  ox = ix + 1.2*iy
  oy = 2.3*iy - 0.5*ix

Any parameters are assumed to be numbers (double or Complex) and can be
referred to using pax, pbx, etc for real parts and pay, pby, etc for
imaginary parts. The order of the function specification should be set
as shown in the example. If integer code is not specified, then a case
is automatically generated with a promoted dataset type: int 8 & 16 to
float32 and int 32 & 64 to float64.
'''

#
# State machine code from David Mertz
# http://www.ibm.com/developerworks/library/l-python-state.html
#

class InitializationError(Exception): pass

class StateMachine:
    def __init__(self):
        self.handlers = []
        self.startState = None
        self.endStates = []

    def add_state(self, handler, end_state=0):
        self.handlers.append(handler)
        if end_state:
            self.endStates.append(handler)

    def set_start(self, handler):
        self.startState = handler

    def run(self, cargo=None):
        if not self.startState:
            raise InitializationError, "must call .set_start() before .run()"
        if not self.endStates:
            raise InitializationError, "at least one state must be an end_state"
        handler = self.startState
        while 1:
            (newState, cargo) = handler(cargo)
            if newState in self.endStates:
                newState(cargo)
                break
            elif newState not in self.handlers:
                raise RuntimeError, "Invalid target %s" % newState
            else:
                handler = newState


def beginmethod(name, jdoc=None, params=0):
    print("\t/**\n\t * %s - %s" %  (name, jdoc))
    print("\t * @param a\n\t * @return dataset\n\t */")
    print("\t@SuppressWarnings(\"cast\")")
    plist = []
    if params > 0:
        plist = ["pa"]
        psig = "final Object " + plist[0]
        for p in range(1,params):
            plist.append("p"+chr(ord('a')+p))
            psig += ", final Object " + plist[p]
        print("\tpublic static AbstractDataset %s(final Dataset a, %s) {" % (name, psig))
    else:
        print("\tpublic static AbstractDataset %s(final Dataset a) {" % name)
    print("\t\tfinal int isize;")
    print("\t\tfinal IndexIterator it = a.getIterator();")
    print("\t\tAbstractDataset ds;")
    print("\t\tfinal int dt = a.getDtype();")
    for p in plist:
        print("\t\tfinal double %s = AbstractDataset.toReal(%s);" % (p+"x", p))
#        print("\t\tfinal double %s = AbstractDataset.toImag(%s);" % (p+"y", p))

    print("")
    print("\t\tswitch(dt) {")

def endmethod(name, types):
    print("\t\tdefault:")
    dtypes = types[0]
    for t in types[1:]:
        dtypes += ", %s" % t
    print("\t\t\tthrow new IllegalArgumentException(\"%s supports %s datasets only\");" % (name, dtypes))
    print("\t\t}\n")
    print("\t\tds.setName(a.getName());")
    print("\t\taddFunctionName(ds, \"%s\");" % name)
    print("\t\treturn ds;")
    print("\t}\n")

def sameloop(codedict, cprefix, vletter, text, oclass=None, otype=None, odtype=None):
    for w in codedict.keys():
        dtype = "%s%d" % (cprefix, w)
        ivar = "%s%ddata" % (vletter,w)
        itype, iclass = codedict[w]
        ovar = "o%s%ddata" % (vletter,w)
        preloop(dtype, itype, iclass, ivar, oclass, ovar, otype, odtype)
        loop(text, itype, ivar, ovar, otype)
        postloop()

def complexloop(codedict, cprefix, vletter, text, real):
    for w in codedict.keys():
        dtype = "%s%d" % (cprefix, w)
        ivar = "%s%ddata" % (vletter,w)
        ovar = "o%s%ddata" % (vletter,w)
        itype, iclass, iwide = codedict[w]
        odtype = None
        if real:
            if iclass.find("Complex") >= 0:
                dummy, oclass = iclass.split("Complex",1)
                odtype = "FLOAT%s" % iwide
            else:
                raise ValueError, "Cannot find complex class"
        else:
            oclass = iclass
        preloop(dtype, itype, iclass, ivar, oclass, ovar, odtype=odtype)
        loopcomplex(text, itype, ivar, ovar, real)
        postloop()

def compoundloop(codedict, cprefix, vletter, text, oclass=None, otype=None, odtype=None):
    for w in codedict.keys():
        dtype = "%s%d" % (cprefix, w)
        ivar = "%s%ddata" % (vletter,w)
        ovar = "o%s%ddata" % (vletter,w)
        itype, iclass = codedict[w]
        preloopcompound(dtype, itype, iclass, ivar, oclass, ovar, otype, odtype)
        loopcompound(text, itype, ivar, ovar, otype)
        postloop()

def deftemps(text, jtype, lprefix):
#    vars = { 'ox':jtype, 'oy':jtype }
    vars = {}
    for t in text:
        # need to build up list of temporaries
        if t.find(" = ") >= 0:
            lhs,rhs = t.split(" = ",1)
            lhs = lhs.strip()
            if rhs != None and lhs not in vars:
                # check for object instantiation then declare new variable
                if t.find(" new") >= 0:
                    dummy, rest = t.split(" new ", 1)
                    lclass,dummy = rest.split("(",1)
                    if lclass != "":
                        vars[lhs] = lclass
                        print "%s%s %s;" % (lprefix, lclass, lhs)
                    else:
                        raise ValueError, "Cannot find class of new variable in line: %s" % t
                else:
                    vars[lhs] = jtype
                    print "%s%s %s;" % (lprefix, jtype, lhs)
    return vars

def transtext(text, jtype, otype=None, lprefix="\t\t\t\t"):
    if otype == None:
        otype = jtype
    vars = deftemps(text, otype, lprefix)
    for t in text:
        # need to build up list of temporaries
        if t.find(" = ") >= 0:
            lhs, rhs = t.split(" = ",1)
            slhs = lhs.strip()
            if slhs not in vars:
                raise ValueError, "Cannot find class of new variable in line: %s" % t
            if t.find(" new") < 0:
                print "%s%s = (%s) (%s);" % (lprefix, lhs, vars[slhs], rhs[:-2])
            else:
                print "%s%s" % (lprefix, t),
        else:
            print "%s%s" % (lprefix, t),

#    print("vars used", vars)

def loop(text, jtype, ivar, ovar, otype):
    print("\t\t\tfor (int i = 0; it.hasNext();) {")
    print("\t\t\t\tfinal %s %s = %s[it.index];" % (jtype, "ix", ivar))
    transtext(text, jtype, otype)
    print("\t\t\t\t%s[i++] = %s;" % (ovar, "ox"))
    print("\t\t\t}")

def loopcomplex(text, jtype, ivar, ovar, real):
    print("\t\t\tfor (int i = 0; it.hasNext();) {")
    print("\t\t\t\tfinal %s %s = %s[it.index];" % (jtype, "ix", ivar))
    print("\t\t\t\tfinal %s %s = %s[it.index+1];" % (jtype, "iy", ivar))
    transtext(text, jtype)
    print("\t\t\t\t%s[i++] = %s;" % (ovar, "ox"))
    if not real:
        print("\t\t\t\t%s[i++] = %s;" % (ovar, "oy"))
    print("\t\t\t}")

def loopcompound(text, jtype, ivar, ovar, otype):
    print("\t\t\tfor (int i = 0; it.hasNext();) {")
    print("\t\t\t\tfor (int j = 0; j < isize; j++) {")
    print("\t\t\t\t\tfinal %s %s = %s[it.index+j];" % (jtype, "ix", ivar))
    transtext(text, jtype, otype, lprefix="\t\t\t\t\t")
    print("\t\t\t\t\t%s[i++] = %s;" % (ovar, "ox"))
    print("\t\t\t\t}")
    print("\t\t\t}")


def preloop(dtype, itype, iclass, ivar, oclass=None, ovar=None, otype=None, odtype=None):
    if oclass == None:
        oclass = iclass
    if otype == None:
        otype = itype
    if odtype == None:
        odtype = dtype
    print("\t\tcase Dataset.%s:" % dtype)
    print("\t\t\tds = (AbstractDataset) DatasetFactory.zeros(a, Dataset.%s);" % odtype)
    print("\t\t\tfinal %s[] %s = ((%s) a).data;" % (itype, ivar, iclass))
    print("\t\t\tfinal %s[] %s = ((%s) ds).getData();" % (otype, ovar, oclass))

def preloopcompound(dtype, itype, iclass, ivar, oclass=None, ovar=None, otype=None, odtype=None):
    if oclass == None:
        oclass = iclass
    if otype == None:
        otype = itype
    if odtype == None:
        odtype = dtype
    print("\t\tcase Dataset.%s:" % dtype)
    print("\t\t\tds = (AbstractDataset) DatasetFactory.zeros(a, Dataset.%s);" % odtype)
    print("\t\t\tisize = a.getElementsPerItem();")
    print("\t\t\tfinal %s[] %s = ((%s) a).data;" % (itype, ivar, iclass))
    print("\t\t\tfinal %s[] %s = ((%s) ds).getData();" % (otype, ovar, oclass))

def postloop():
    print("\t\t\tbreak;")


def func(cargo):
    f, last = cargo
    dummy, params = last.split("func:", 1)
    params = params.strip()
    if len(params) > 0:
        nparams = int(params)
        if nparams > 26:
            raise ValueError, "Number of parameters is greater than the supported 26!"
    else:
        nparams = 0

    while True:
        l = f.readline()
        if l == None:
            return eof, (f, l)
        l = l.strip(' ')
        name, jdoc = l.split(" - ", 1)
        beginmethod(name, jdoc.strip(), nparams)
#        if len(plist) > 0: print "Parameters", plist
        return cases, (f, '', name, [])

def cases(cargo):
    f, last, name, types = cargo
#    print "cases: |%s|, |%s|" % (name, last)
    while True:
        if last == None or len(last) > 0:
            l = last
            last = None
        else:
            l = f.readline()
        if l == None:
            endmethod(name, types)
            return eof
        if len(l) > 0:
            code, out = whichcode(l)
            if code == None:
                if out == None:
                    endmethod(name, types)
                    return func, (f, l)
                return parsefile, (f, l)
            return code, (f, out, name, types)
        else:
            endmethod(name, types)
            return eof, None


def whichcode(line):
    typespec, out = line.split(":", 1)
    if typespec == "integer":
        return icode, out
    elif typespec == "real":
        return rcode, out
    elif typespec == "complex":
        return ccode, out
    elif typespec == "func":
        return None, None
    else:
        return None, out

def getcode(f):
    text = []
    while True:
        l = f.readline()
        if l == None:
            break
        l = l.strip(' ')
        if len(l) == 0:
            break
        if l[0] == '\n':
            continue
        if l.find(":") >= 0:
            code, line = whichcode(l)
            if code != None:
                break
            if line == None:
                break
        text.append(l)

    return text, l

def icode(cargo):
    f, all, name, types = cargo
    text, last = getcode(f)
#    print "int case:", name
#    print text
    sameloop({ 8 : ("byte", "ByteDataset"), 16 : ("short", "ShortDataset"),
                32 : ("int", "IntegerDataset"), 64 : ("long", "LongDataset") },
                "INT", "i", text)
    types.append("integer")
    compoundloop({ 8 : ("byte", "CompoundByteDataset"), 16 : ("short", "CompoundShortDataset"),
                32 : ("int", "CompoundIntegerDataset"), 64 : ("long", "CompoundLongDataset") },
                "ARRAYINT", "ai", text)
    types.append("compound integer")
    return cases, (f, last, name, types)

def rcode(cargo):
    f, all, name, types = cargo
    text, last = getcode(f)
#    print "real case:", name
#    print text
# TODO check for integer type and promote if not found
    if "integer" not in types:
        sameloop({ 8 : ("byte", "ByteDataset"), 16 : ("short", "ShortDataset") },
                "INT", "i", text, oclass="FloatDataset", otype="float", odtype="FLOAT32")
        sameloop({ 32 : ("int", "IntegerDataset"), 64 : ("long", "LongDataset") },
                "INT", "i", text, oclass="DoubleDataset", otype="double", odtype="FLOAT64")
        types.append("integer")
        compoundloop({ 8 : ("byte", "CompoundByteDataset"), 16 : ("short", "CompoundShortDataset") },
                "ARRAYINT", "ai", text, oclass="CompoundFloatDataset", otype="float", odtype="ARRAYFLOAT32")
        compoundloop({ 32 : ("int", "CompoundIntegerDataset"), 64 : ("long", "CompoundLongDataset") },
                "ARRAYINT", "ai", text, oclass="CompoundDoubleDataset", otype="double", odtype="ARRAYFLOAT64")
        types.append("compound integer")

    sameloop({ 32: ("float", "FloatDataset"), 64 : ("double", "DoubleDataset") },
                "FLOAT", "f", text)
    types.append("real")

    compoundloop({ 32: ("float", "CompoundFloatDataset"), 64 : ("double", "CompoundDoubleDataset") },
                "ARRAYFLOAT", "af", text)
    types.append("compound real")
    return cases, (f, last, name, types)

def ccode(cargo):
    f, all, name, types = cargo
    text, last = getcode(f)
#    print "comp case:", name
#    print text
    if all.find("real") >= 0:
        real = True
    else:
        real = False
    complexloop({ 64: ("float", "ComplexFloatDataset", "32"), 128 : ("double", "ComplexDoubleDataset", "64") },
                "COMPLEX", "c", text, real)
    types.append("complex")
    return cases, (f, last, name, types)

def parsefile(cargo):
    f,last = cargo
    while True:
        if last == None or len(last) > 0:
            l = last
            last = None
        else:
            l = f.readline()
        if l == None:
            return eof, (f, l)
        l = l.strip(' ')
        if len(l) > 0:
            if l.find("func:") < 0:
                raise ValueError, "Line is not a function definition: %s" % l
            return func, (f, l)

def eof(cargo):
    pass

def generatefunctions(funcs_file):
    m = StateMachine()
    m.add_state(parsefile)
    m.add_state(func)
    m.add_state(cases)
    m.add_state(icode)
    m.add_state(rcode)
    m.add_state(ccode)
    m.add_state(eof, end_state=1)
    m.set_start(parsefile)
    m.run((funcs_file, ''))

def generateclass(funcs, shell):
    while True:
        l = shell.readline()
        if l.startswith("// Start of generated code"):
            print l,
            break
        print l,

    generatefunctions(funcs)

    while True:
        l = shell.readline()
        if l.startswith("// End of generated code"):
            print l,
            break

    while True:
        l = shell.readline()
        if not l:
            break
        print l,

if __name__ == '__main__':
    import sys
    if len(sys.argv) > 1:
        fname = sys.argv[1]
        funcs_file = open(fname, 'r')
    else:
        funcs_file = sys.stdin

    if len(sys.argv) > 2:
        fname = sys.argv[2]
    else:
        fname = "Maths.txt"
    shell_file = open(fname, 'r')

    generateclass(funcs_file, shell_file)
