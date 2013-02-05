
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
