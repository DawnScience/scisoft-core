from setuptools import setup, find_packages
import sys
import os


py2 = sys.hexversion < 0x03000000

INSTALL_REQUIRES = ['h5py < 3', 'pillow', 'numpy', 'six']

if py2 and os.name == 'posix':
    INSTALL_REQUIRES.append('subprocess32')

setup(
    name="scisoftpy",
    version="2.23.0",
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
    packages=find_packages(exclude=['uk*']),
    install_requires=INSTALL_REQUIRES,
    zip_safe=True,
    url="https://gerrit.diamond.ac.uk/plugins/gitiles/scisoft/scisoft-core",
)

try:
    import tifffile
except ImportError:
    print("Could not import tifffile package. The io subpackage will use Pillow to load TIFFs unless tifffile is installed.")
