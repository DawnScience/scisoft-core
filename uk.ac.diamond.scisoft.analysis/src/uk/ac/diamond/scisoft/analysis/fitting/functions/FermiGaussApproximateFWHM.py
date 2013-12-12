from uk.ac.diamond.scisoft.analysis.fitting.functions import FermiGauss
import scisoftpy as dnp

fg = FermiGauss()
x = dnp.arange(0,0.2,0.001)

def scan_temps(start,stop,step,fwhm):
	fg.setParameterValues(0.1,start,0,1,0,fwhm)
	test = fg.calculateValues([x])
	temps = dnp.arange(start,stop,step)
	result = dnp.zeros(temps.shape)
	count = 0
	for temp in temps:
		fg.setParameterValues(0.1,temp,0,1,0,0.0)
		comp = fg.calculateValues([x])
		result[count] = dnp.sum(dnp.square(dnp.array(test)-dnp.array(comp)))
		count+=1
	dnp.plot.line(temps,result)
	return temps[result.minPos().tolist()]

def scan_fwhm(start,stop,step,t_start, t_stop=500, t_step=1):
	fwhms = dnp.arange(start,stop,step)
	result = dnp.zeros(fwhms.shape)
	count = 0
	for fwhm in fwhms:
		result[count] = scan_temps(t_start, t_stop, t_step, fwhm)
		count+=1
	dnp.plot.line(result,fwhms,name="Plot 2")
	return (fwhms,result)

def scan_start_temp():
	temps = dnp.arange(10,200,10)
	p0 = dnp.zeros(temps.shape)
	p1 = dnp.zeros(temps.shape)
	p2 = dnp.zeros(temps.shape)
	count = 0
	for temp in temps:
		(fwhm,values) = scan_fwhm(0,0.2,0.01,temp)
		res = dnp.fit.polyfit(values,fwhm,2)
		p0[count] = res[0]
		p1[count] = res[1]
		p2[count] = res[2]
		count+=1
	dnp.plot.line(temps,[p0,p1,p2],name="Plot 3")
	return(dnp.fit.polyfit(temps,p0,2),
		dnp.fit.polyfit(temps,p1,2),
		dnp.fit.polyfit(temps,p2,2))
	
scan_start_temp()