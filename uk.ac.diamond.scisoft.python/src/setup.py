from setuptools import setup, find_packages
import sys
import os


py2 = sys.hexversion < 0x03000000

INSTALL_REQUIRES = ['h5py', 'pillow', 'numpy', 'six', 'py4j']

if py2 and os.name == 'posix':
    INSTALL_REQUIRES.append('subprocess32')

setup(
    name="scisoftpy",
    version="2.16.0",
    description="DAWN Python Extensions",
    author="Peter Chang",
    author_email="scientificsoftware@diamond.ac.uk",
    classifiers=[
        "Development Status :: 5 - Production/Stable",
        "Intended Audience :: Developers",
        "License :: OSI Approved :: Apache Software License",
        "Natural Language :: English",
        "Programming Language :: Python :: 2.7",
        "Programming Language :: Python :: 3.6",
        "Programming Language :: Python :: 3.7",
        "Programming Language :: Python :: 3.8",
    ],
    packages=find_packages(exclude=['py4j*', 'uk*']),
    install_requires=INSTALL_REQUIRES,
    zip_safe=True,
    url="https://gerrit.diamond.ac.uk/admin/repos/scisoft/scisoft-core",
)
