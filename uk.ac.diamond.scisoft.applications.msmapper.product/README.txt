To use in a shared location, some OSGi Java properties should be set in configuration/config.ini:

osgi.configuration.area.readOnly=true # should be unnecessary to write configuration metadata for a headless application
osgi.sharedConfiguration.area.readOnly=true # ensure users with install privileges do not alter it too

Therefore, on installing the product, run
$ msmapper -initialize -vmargs -Dosgi.configuration.area.readOnly=false

See
 https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fmisc%2Fruntime-options.html
and 
 https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fmisc%2Fmulti_user_installs.html
