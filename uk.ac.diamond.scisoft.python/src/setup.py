from setuptools import setup, find_packages

setup(
    name="scisoftpy",
    version="2.29.0",
    description="DAWN Python Extensions",
    author="Peter Chang",
    author_email="dataanalysis@diamond.ac.uk",
    classifiers=[
        "Development Status :: 5 - Production/Stable",
        "Intended Audience :: Developers",
        "Intended Audience :: Science/Research",
        "License :: OSI Approved :: Apache Software License",
        "Natural Language :: English",
        "Programming Language :: Python :: 2.7",
        "Programming Language :: Python :: 3",
        "Topic :: Scientific/Engineering",
        "Topic :: Software Development :: Libraries :: Python Modules",
    ],
    packages=find_packages(exclude=["uk*"]),
    install_requires=[
        "h5py", # depends on numpy
        "pillow",
        "six",
        "subprocess32; python_version < '3' and os_name == 'posix'"
    ],
    extra_requires={
        "all": [ "tifffile", "pycbf; python_version >= '3'" ],
    },
    zip_safe=True,
    url="https://gerrit.diamond.ac.uk/plugins/gitiles/scisoft/scisoft-core",
)
