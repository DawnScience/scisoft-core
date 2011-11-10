Poll View and MX Live Perspective
=================================

Introduction
------------
The Poll view and associated items have been created to allow the user to
monitor files which are changing and to display the contents.

Concept
-------
Each Polling Job is described by a simple text file which can be opened using
the SDA.  These files are persistent and will be available every time the SDA
starts.

Available Polling Jobs
----------------------
To see the available polling jobs, in the Poll View, use the drop down menu and
select Add New Job.  this should provide you with the following items:

 - SRSScatterPlotJob
 
  + This job loads in a file and then tries to plot the data as a scatter plot.
  + **Class** The type of the job, generally don't change this
  + **PollTime** The frequency with which the plot is updated
  + **FileName** The filename of a file which will contain the filename of the actual file to load.
  + **PlotViewName** The name of the Plot into which you want the result to be shown
  + **XAxis** The name of the X Axis which is to be used in the plot
  + **YAxis** The name of the Y Axis which is to be used in the plot

 - SRSLinePlotJob
 
  + This job loads in a file and then tries to plot some lines of data
  + **Class** The type of the job, generally don't change this
  + **PollTime** The frequency with which the plot is updated
  + **FileName** The filename of a file which will contain the filename of the actual file to load.
  + **PlotViewName** The name of the Plot into which you want the result to be shown
  + **XAxis** The name of the X Axis which is to be used in the plot
  + **YAxis** The name of the Y Axis (comma separated with no spaces) which is to be plotted

 - Image Update Job
 
  + This job loads in a file(only when the name of the file changes) and then tries to plot the image which is returned
  + **Class** The type of the job, generally don't change this
  + **PollTime** The frequency with which the plot is updated
  + **FileName** The filename of a file which will contain the filename of the actual file to load.
  + **PlotViewName** The name of the Plot into which you want the result to be shown

 - File Print Job
 
  + This job simply prints a filename, its a test job and only included for debugging purposes
  + **Class** The type of the job, generally don't change this
  + **PollTime** The frequency with which the plot is updated
  + **FileName** The filename of a file which will contain the filename of the actual file to load.

 - Browser Update
 
  + This job takes a URL and displayed it in a special URL viewing window
  + **Class** The type of the job, generally don't change this
  + **PollTime** The frequency with which the plot is updated
  + **FileName** The filename of a file which will contain the URL which is to be displayed.
  + **URLViewName** The name of the URL viewing window into which you want the result to be shown


Interaction
-----------
All the interactions with the polling should be done through the poll view.  All of the functions available 
are replicated as easy to use buttons on the top of the view, or with more information buttons in the drop
down menu.  These functions are:

 - **Pause All Jobs** Pauses all the jobs so they are no longer updated
 - **Restart All Jobs** restarts all the jobs so they will poll again
 - **Delete Job** Deletes the selected job from the list, and removes the associated file.
 - **Delete All Jobs** Clears out all the jobs in the list.
 - **Refresh All Jobs** Reparses all the job information, this can be useful when changing the polling time to force an update

The final thing which is useful to know is where the polling job files are sent, and this is specified in the preferences.
In the top menu click Window->Preferences and then select Polling Preferences.  In this you can see which directory you wish to use
and how to change them.