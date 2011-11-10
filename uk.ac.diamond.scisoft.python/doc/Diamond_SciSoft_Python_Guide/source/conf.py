# -*- coding: utf-8 -*-

# get standard configurations settings
import os.path
conf_common_path = os.path.abspath('../../../../../builders.dawn/documentation/source/conf_common.py')
if not os.path.isfile(conf_common_path):
    raise Exception, 'File %s not found' % (conf_common_path,)
execfile(conf_common_path)

# General information about the project.
project = u'Diamond SciSoft Python Guide'
copyright = copyright_scisoft

# The short X.Y version.
version = '1.0'
# The full version, including alpha/beta/rc tags.
release = '1.0'
# The version number to append to the basename of PDF files (the part before the .pdf suffix) (an additional option for GDA and related projects)
version_for_filenames = '1.0'

# Theme options are theme-specific and customize the look and feel of a theme
# further.  For a list of options available for each theme, see the
# documentation.
html_theme_options = html_theme_options_scisoft

# The name of an image file (relative to this directory) to place at the top
# of the sidebar.
html_logo = html_logo_scisoft

# Grouping the document tree into LaTeX files. List of tuples
# (source start file, target name, title, author, documentclass [howto/manual]).
latex_documents = [
  ('contents', 'Diamond_SciSoft_Python_Guide.tex', u'Diamond SciSoft Python Guide',
   _author_scisoft, 'manual'),
]

# The name of an image file (relative to this directory) to place at the top of
# the title page.
latex_logo = latex_logo_scisoft

