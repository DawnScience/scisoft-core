Image Plotting Tools
====================

The image plotting tools appear automatically when an image (two-dimensional data array) is plotted. 
You can choose an image tool by going to the tool action menu: 

.. image:: images/plot-tool-2d.png

and choosing the kind of tool you would like. The follow section details with what each of the tools do.

Color Mapping
-------------
.. image:: images/brightness_contrast.gif 

The colour mapping tool is used to adjust how the values of intensity are coloured. The colours are assigned
within a maximum and minimum value for intensity. There are also minimum and maximum cut values, outside these
ranges and a value is considered bad and has a special colour. For instance dead pixels blue and zingers red.

Imagining the plotting the intensities with a normal distribution the colour range of the palette and cut offs
is given by:

                ++----------------------**---------------
                |                      *  *              
                ++                    *    *             
                |                     *    *             
                ++                    *     *            
                |                    *       *            
                +*                   *       *            
                |*                  *        *            
                +*                  *        *           
                |                  *          *         
                ++                 *          *          
                |                  *           *        
                ++                 *           *        
                |                 *            *        
                ++                *            *       
                |                *              *      
        Min Cut           Min    *              *      Max                     Max cut
 Blue <- |   (min colour)  |    (color range, palette)  |      (max color)      | -> Red
                |               *                 *  
                |              *        +         *  
----------------++------------**---------+----------**----+---------------**+---------------++

Pressing the 'h' key with the plot selected will result in the current max and min for the colour range
being reset.