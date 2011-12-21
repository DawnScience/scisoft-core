# -*- coding: utf-8 -*-

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

# get standard configurations settings
import os
if not 'BUILDERS_DOCUMENTATION' in os.environ:
    raise Exception, 'Environment variable BUILDERS_DOCUMENTATION must be set'
if not os.path.isabs(os.environ['BUILDERS_DOCUMENTATION']):
    raise Exception, 'Environment variable BUILDERS_DOCUMENTATION must be an absolute file path, but is "%s"' % (os.environ['BUILDERS_DOCUMENTATION'],) 
conf_common_path = os.path.join(os.environ['BUILDERS_DOCUMENTATION'], 'source', 'conf_common.py')
if not os.path.isfile(conf_common_path):
    raise Exception, 'File %s not found' % (conf_common_path,)
execfile(conf_common_path)

# General information about the project.
project = u'Diamond SciSoft Analysis User Guide'
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
  ('contents', 'Diamond_SciSoft_Analysis_User_Guide.tex', u'Diamond SciSoft Analysis User Guide',
   _author_scisoft, 'manual'),
]

# The name of an image file (relative to this directory) to place at the top of
# the title page.
latex_logo = latex_logo_scisoft

