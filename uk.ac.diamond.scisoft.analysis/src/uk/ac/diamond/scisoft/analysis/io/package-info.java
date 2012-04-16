package uk.ac.diamond.scisoft.analysis.io;

/**
 * 
 * 
 * 
LoaderFactory loads data into Dawn uses SoftReferences to the data. 
This makes the system speed up considerably. 
This is because multiple calls to read data and meta are data are made, particularly if you are using meta data viewers. 
That is because some file formats have loaders which load the entire data to get meta data (e.g. mccd). 
As well many different parts can ask the LoaderFactory for the same data repeatedly.

While you get a speed up of the system there are two issues with this approach:
1.	The system is more likely to use its full memory allocation and sooner.
2.	The memory profile of the application hides real memory leaks.

One can control the memory of file loading as follows:
1.	Set ‘uk.ac.diamond.scisoft.analysis.io.nocaching’ to make everything uncached.
2.	Set ‘uk.ac.diamond.scisoft.analysis.io.weakcaching’ to use weak references.
3.	Default behaviour is to use SoftReferences.

SoftReferences are better in this context as they mean that if you just looked at a directory, the last data in the folder is still in memory (as much as will fit in the xmx setting) and the images open considerably faster. 
The garbage collector does remove these references in due course and sometimes you have to wait while the system runs the garbage collector (around 2-5s for 1Gb cached on an i7 machine). 

**/
