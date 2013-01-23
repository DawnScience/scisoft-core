Fitting 1D Sideplot
===================

Overview
--------
This side plot allows peaks to be fitted to a 1-dimensional dataset. There are
2 basic modes of operation:

 - `Auto Fitting`_.
 - `Click and drag fitting`_.

The fitting algorithm works in the following manner. Initially the first
derivative of the one dimensional dataset is taken. A smoothing function is
applied during this process. The default value for the smoothing value is 1% of
the data length or this value can be set manually using the Fitting1D
`preferences page`_. Using the first derivative, the regions thought to contain
peaks are found.  These peak containing regions are identified by a region of
positive gradient followed by a region of negative gradient. Using this
information an optimisation routine is applied to the data in the region where
the peak was found. The selected peaks is then fitted to the data. Currently,
the types of peaks fitted can be chosen in the side plot or in the
`preferences page`_.

.. figure:: images/FittingViewAuto.png

The results from the peak fitting are then shown in the table and their
positions are indicated in the plot. An individual peak can be selected from
the table and the region where the peak came from is highlighted in the main
plot view, the data and the fitted peak are displayed in the plot at the
bottom of the sideplot.

.. _`Auto Fitting`:

Auto Fitting
------------
This mode of operation is designed to make an attempt at fitting peaks to the
input data automatically but still allowing the user to manipulate the peaks
found after this operation. There are two ways to do this: The first method is
to specify the number of peaks that the fitting routing should fit. This will
attempt to fit the *n* peaks with the largest area and then stop. If there are
less than *n* peaks in the data then the routine will fit the maximum number of
peaks that the peak finding routine has found.

The second method of automatic fitting is to continue to fit peaks to the data
until a threshold is met. There are two different measures which are available
through the preference page: **area** and **height**. Using this method will
continue to fit peaks to the data until the peak being fitted is less than the
threshold selected in the preference page. For example, if area is selected as
the measure and the  threshold is set 0.05 the peak fitting routine will
continue until the next peak being fitted is less than the 5% of the area of
the largest peak. The automatically found peaks are coloured blue in the table.

.. _`Click and drag fitting`:

Click and Drag Fitting
----------------------
This allows regions to be selected from the plot and peaks to fitted within
this region. To do this right click and drag across the region containing the
peak. When the button is released then the a menu allowing the user to choose
the number of peaks that are to be fitted to the region selected. Using the
advanced settings, the type of peak, fitting method (including accuracy) and
the smoothing can be specified. If a peak is found then the table is updated
and the manually found peaks are coloured green

.. figure:: images/Fitting1DDragging.png

Peak Manipulation 
-----------------       
Once peaks have been found, the location of these peaks can be shown on the
main plot by selecting 'show all peaks'. This will highlight the region of data
where a peaks has been found in the main plotter. The box is defined as the
full width at half maximum of the fitted peak. This allows the performance of
the peak fitting to be assessed by examining the plot after the routing is
finished. By right clicking on a row in the table the data and the fitted peak
is shown in the lower plot. Overlaid onto this sub plot is the full width at
half maximum and the mean of the fitted peak.

If the peak is found to be incorrectly fitted then the peak can be deleted from
the list. This is particularly useful if the automatic peak fitting has found
an erroneous peak. This is achieved by left clicking on the row in the table
and selecting delete. A peak can also be edited using the edit option. Using
this a different probability density function can be fitted to the region where
the existing peak was found, for example. If no peak is found the table remains
unchanged.

.. _`preferences page`:

Preferences
-----------
The preferences interface is used to control various features in the fitting
routine and interface. The preferences can be found in Windows > Preferences.

.. figure:: images/Fitting1DPreferences.png 

The 'Peak control' section allows which type of peak is fitted to the data in
the plot. This will then be the type of peak fitted for auto fitting and is
preserved between sessions. The number of peaks is the maximum number of peaks
the routine will fit if automatic stopping is not selected. If automatic
stopping is selected then this will be disabled.

Below this is the algorithm controls. This allows the the type of fitting
algorithm employed and the accuracy of that algorithm to be specified. The
lower the number the more accurate, but more expensive and time consuming, the
fitting will be. The smoothing that is applied to calculate the differential of
the data being fitted can also be specified. The units associated with this
smoothing are the number of data points in the data. When auto smoothing is
selected the smoothing is set to 1% of then data length.

The stopping criteria can also be customised. When selected the routine will
continue until the specified threshold is met. This threshold is the proportion
of the largest peak. The measure can be either the height of the largest peak
or the area of the largest peak. 

For example, if the threshold is set to 0.10 and the largest peak is has an
area of 100 then the routing will continue to run until the next peak being
fitted as an area of less that 10. At this point the routine will stop and the
results from the peaks found will be displayed. 

