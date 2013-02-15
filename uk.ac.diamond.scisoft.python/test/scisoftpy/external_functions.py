
import scisoftpy as dnp

def fun():
    print 'called fun()'
    return dnp.pi

def funadd(a, b):
    return a+b

def fundec(a, b=1.5):
    return a-b

def funexception():
    raise ValueError, 'Help!!'

def funscipy():
    import scipy
    return scipy.version.version

def funarrayscalar():
    import numpy
    return numpy.complex64(2+3j), numpy.float32(1.), numpy.int8(123), numpy.bool8(True)
