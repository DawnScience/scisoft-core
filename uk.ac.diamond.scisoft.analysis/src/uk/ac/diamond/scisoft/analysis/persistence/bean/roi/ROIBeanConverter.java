/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.diamond.scisoft.analysis.persistence.bean.roi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.roi.CircularFitROI;
import uk.ac.diamond.scisoft.analysis.roi.CircularROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalFitROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.roi.FreeDrawROI;
import uk.ac.diamond.scisoft.analysis.roi.GridROI;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.LinearROI;
import uk.ac.diamond.scisoft.analysis.roi.PerimeterBoxROI;
import uk.ac.diamond.scisoft.analysis.roi.PointROI;
import uk.ac.diamond.scisoft.analysis.roi.PolygonalROI;
import uk.ac.diamond.scisoft.analysis.roi.PolylineROI;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;
import uk.ac.diamond.scisoft.analysis.roi.RingROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;
import uk.ac.diamond.scisoft.analysis.roi.XAxisBoxROI;
import uk.ac.diamond.scisoft.analysis.roi.YAxisBoxROI;

/**
 * Class used to convert from an IROI to a ROIBean and vice-versa
 * @author wqk87977
 *
 */
public class ROIBeanConverter {

	static private Logger logger = LoggerFactory.getLogger(ROIBeanConverter.class);

	/**
	 * Method that converts a IROI to a ROIBean
	 * @param name
	 * @param roi
	 * @return ROIBean
	 */
	public static ROIBean getROIBean(String name, IROI roi){
		Class<? extends IROI> roiClass = roi.getClass();
		if(roiClass == PointROI.class){
			PointROI proi = (PointROI) roi;
			PointROIBean proibean = new PointROIBean();
			proibean.setName(name);
			proibean.setStartPoint(proi.getPoint());
			return proibean;

		} else if(roiClass == PerimeterBoxROI.class){
			PerimeterBoxROI proi = (PerimeterBoxROI) roi;
			PerimeterBoxROIBean proibean = new PerimeterBoxROIBean();
			proibean.setName(name);
			proibean.setStartPoint(proi.getPoint());
			proibean.setEndPoint(proi.getEndPoint());
			proibean.setAngle(proi.getAngle());
			proibean.setLengths(proi.getLengths());
			return proibean;

		} else if(roiClass == RectangularROI.class){
			RectangularROI rroi = (RectangularROI) roi;
			RectangularROIBean rroibean = new RectangularROIBean();
			rroibean.setName(name);
			rroibean.setStartPoint(rroi.getPoint());
			rroibean.setEndPoint(rroi.getEndPoint());
			rroibean.setAngle(rroi.getAngle());
			rroibean.setLengths(rroi.getLengths());
			return rroibean;

		} else if(roiClass == GridROI.class){
			GridROI groi = (GridROI) roi;
			GridROIBean groibean = new GridROIBean();
			groibean.setName(name);
			groibean.setStartPoint(groi.getPoint());
			groibean.setEndPoint(groi.getEndPoint());
			groibean.setAngle(groi.getAngle());
			groibean.setLengths(groi.getLengths());
			groibean.setxSpacing(groi.getxSpacing());
			groibean.setySpacing(groi.getySpacing());
			groibean.setGridLinesOn(groi.isGridLineOn());
			groibean.setMidPointOn(groi.isMidPointOn());
			return groibean;

		} else if(roiClass == LinearROI.class){
			LinearROI lroi = (LinearROI) roi;
			LinearROIBean lroibean = new LinearROIBean();
			lroibean.setName(name);
			lroibean.setStartPoint(lroi.getPoint());
			lroibean.setEndPoint(lroi.getEndPoint());
			return lroibean;

		} else if(roiClass == PolylineROI.class){
			PolylineROI plroi = (PolylineROI) roi;
			PolylineROIBean plroibean = new PolylineROIBean();
			plroibean.setName(name);
			plroibean.setStartPoint(plroi.getPoint());
			List<double[]> points = new ArrayList<double[]>();
			for (int i = 0; i < plroi.getNumberOfPoints(); i++) {
				points.add(plroi.getPoint(i).getPoint());
			}
			plroibean.setPoints(points);
			return plroibean;

		} else if(roiClass == PolygonalROI.class){
			PolygonalROI pgroi = (PolygonalROI) roi;
			PolygonalROIBean pgroibean = new PolygonalROIBean();
			pgroibean.setName(name);
			pgroibean.setStartPoint(pgroi.getPoint());
			List<double[]> points = new ArrayList<double[]>();
			for (int i = 0; i < pgroi.getNumberOfPoints(); i++) {
				points.add(pgroi.getPoint(i).getPoint());
			}
			pgroibean.setPoints(points);
			return pgroibean;

		} else if(roiClass == FreeDrawROI.class){
			FreeDrawROI fdroi = (FreeDrawROI) roi;
			FreedrawROIBean fdroibean = new FreedrawROIBean();
			fdroibean.setName(name);
			fdroibean.setStartPoint(fdroi.getPoint());
			List<double[]> points = new ArrayList<double[]>();
			for(int i = 0; i<fdroi.getNumberOfPoints(); i++){
				points.add(fdroi.getPoint(i).getPoint());
			}
			fdroibean.setPoints(points);
			return fdroibean;

		} else if(roiClass == RingROI.class){
			RingROI rroi = (RingROI) roi;
			RingROIBean rroibean = new RingROIBean();
			rroibean.setName(name);
			rroibean.setStartPoint(rroi.getPoint());
			rroibean.setAngles(rroi.getAngles());
			rroibean.setRadii(rroi.getRadii());
			rroibean.setSymmetry(rroi.getSymmetry());
			rroibean.setDpp(rroi.getDpp());
			return rroibean;

		} else if(roiClass == SectorROI.class){
			SectorROI sroi = (SectorROI)roi;
			SectorROIBean sroibean = new SectorROIBean();
			sroibean.setName(name);
			sroibean.setStartPoint(sroi.getPoint());
			sroibean.setAngles(sroi.getAngles());
			sroibean.setRadii(sroi.getRadii());
			sroibean.setSymmetry(sroi.getSymmetry());
			sroibean.setDpp(sroi.getDpp());
			sroibean.setClippingCompensation(sroi.isClippingCompensation());
			sroibean.setCombineSymmetry(sroi.isCombineSymmetry());
			sroibean.setAverageArea(sroi.isAverageArea());
			return sroibean;

		} else if(roiClass == CircularROI.class){
			CircularROI croi = (CircularROI) roi;
			CircularROIBean croibean = new CircularROIBean();
			croibean.setName(name);
			croibean.setStartPoint(croi.getCentre());
			croibean.setRadius(croi.getRadius());
			return croibean;

		} else if(roiClass == EllipticalROI.class){
			EllipticalROI eroi = (EllipticalROI) roi;
			EllipticalROIBean eroibean = new EllipticalROIBean();
			eroibean.setName(name);
			eroibean.setStartPoint(eroi.getPoint());
			eroibean.setSemiAxes(eroi.getSemiAxes());
			eroibean.setAngle(eroi.getAngle());
			return eroibean;

		} else if(roiClass == CircularFitROI.class){
			CircularFitROI croi = (CircularFitROI) roi;
			CircularFitROIBean croibean = new CircularFitROIBean();
			croibean.setName(name);
			croibean.setRadius(croi.getRadius());
			croibean.setStartPoint(croi.getPoint());
			List<double[]> points = new ArrayList<double[]>();
			PolylineROI poly = croi.getPoints();
			for (int i = 0; i < poly.getNumberOfPoints(); i++) {
				points.add(poly.getPoint(i).getPoint());
			}
			croibean.setPoints(points);
			return croibean;

		} else if(roiClass == EllipticalFitROI.class){
			EllipticalFitROI eroi = (EllipticalFitROI) roi;
			EllipticalFitROIBean eroibean = new EllipticalFitROIBean();
			eroibean.setName(name);
			eroibean.setStartPoint(eroi.getPoint());
			eroibean.setSemiAxes(eroi.getSemiAxes());
			eroibean.setAngle(eroi.getAngle());
			List<double[]> points = new ArrayList<double[]>();
			PolylineROI poly = eroi.getPoints();
			for (int i = 0; i < poly.getNumberOfPoints(); i++) {
				points.add(poly.getPoint(i).getPoint());
			}
			eroibean.setPoints(points);
			return eroibean;

		} else {
			logger.debug("This type is not supported");
		}
		return null;
	}

	/**
	 * Method that converts a roi bean to a ROiBase
	 * @param rbean
	 * @return ROIBase
	 */
	public static IROI getROI(ROIBean rbean){
		if(rbean instanceof PointROIBean){
			PointROIBean proibean = (PointROIBean) rbean;
			PointROI proi = new PointROI();
			proi.setName(proibean.getName());
			proi.setPoint(proibean.getStartPoint());
			return proi;
		} else if(rbean instanceof PerimeterBoxROIBean){
			PerimeterBoxROIBean proibean = (PerimeterBoxROIBean) rbean;
			PerimeterBoxROI proi = new PerimeterBoxROI();
			proi.setName(proibean.getName());
			proi.setPoint(proibean.getStartPoint());
			proi.setLengths(proibean.getLengths());
			proi.setAngle(proibean.getAngle());
			return proi;
		} else if(rbean instanceof RectangularROIBean){
			RectangularROIBean rroibean = (RectangularROIBean) rbean;
			RectangularROI rroi = new RectangularROI(rroibean.getStartPoint()[0], 
					rroibean.getStartPoint()[1], rroibean.getLengths()[0], 
					rroibean.getLengths()[1], rroibean.getAngle());
			rroi.setName(rroibean.getName());
			return rroi;
		} else if(rbean instanceof GridROIBean){
			GridROIBean groibean = (GridROIBean) rbean;
			GridROI groi = new GridROI(groibean.getStartPoint()[0], 
					groibean.getStartPoint()[1], groibean.getLengths()[0], 
					groibean.getLengths()[1], groibean.getAngle(),
					groibean.getxSpacing(), groibean.getySpacing(),
					groibean.isGridLinesOn(), groibean.isMidPointOn());
			groi.setName(groibean.getName());
			return groi;
		} else if(rbean instanceof LinearROIBean){
			LinearROIBean lroibean = (LinearROIBean) rbean;
			LinearROI lroi = new LinearROI();
			lroi.setPoint(lroibean.getStartPoint());
			lroi.setEndPoint(lroibean.getEndPoint());
			lroi.setName(lroibean.getName());
			return lroi;
		} else if(rbean instanceof PolylineROIBean){
			PolylineROIBean plroibean = (PolylineROIBean) rbean;
			PolylineROI plroi = new PolylineROI(plroibean.getStartPoint());
			Iterator<double[]> it = plroibean.getPoints().iterator();
			while (it.hasNext()) {
				double[] point = it.next();
				plroi.insertPoint(point);
			}
			return plroi;
		} else if(rbean instanceof PolygonalROIBean){
			PolygonalROIBean pgroibean = (PolygonalROIBean) rbean;
			PolygonalROI pgroi = new PolygonalROI(pgroibean.getStartPoint());
			Iterator<double[]> it = pgroibean.getPoints().iterator();
			while (it.hasNext()) {
				double[] point = it.next();
				pgroi.insertPoint(point);
			}
			pgroi.setName(pgroibean.getName());
			return pgroi;
		} else if(rbean instanceof FreedrawROIBean){
			FreedrawROIBean fdroibean = (FreedrawROIBean) rbean;
			FreeDrawROI fdroi = new FreeDrawROI(fdroibean.getStartPoint());
			Iterator<double[]> it = fdroibean.getPoints().iterator();
			while (it.hasNext()){
				double[] point = it.next();
				fdroi.insertPoint(point);
			}
			fdroi.setName(fdroibean.getName());
			return fdroi;
		} else if(rbean instanceof RingROIBean){
			RingROIBean sroibean = (RingROIBean) rbean;
			RingROI sroi = new RingROI();
			sroi.setName(sroibean.getName());
			sroi.setPoint(sroibean.getStartPoint());
			sroi.setRadii(sroibean.getRadii());
			sroi.setAngles(sroibean.getAngles());
			sroi.setDpp(sroibean.getDpp());
			sroi.setSymmetry(sroibean.getSymmetry());
			return sroi;
		} else if (rbean instanceof SectorROIBean){
			SectorROIBean sroibean = (SectorROIBean) rbean;
			SectorROI sroi = new SectorROI();
			sroi.setName(sroibean.getName());
			sroi.setPoint(sroibean.getStartPoint());
			sroi.setRadii(sroibean.getRadii());
			sroi.setAngles(sroibean.getAngles());
			sroi.setDpp(sroibean.getDpp());
			sroi.setSymmetry(sroibean.getSymmetry());
			sroi.setClippingCompensation(sroibean.isClippingCompensation());
			sroi.setCombineSymmetry(sroibean.isCombineSymmetry());
			sroi.setAverageArea(sroibean.isAverageArea());
			return sroi;
		} else if(rbean instanceof CircularROIBean){
			CircularROIBean croibean = (CircularROIBean) rbean;
			CircularROI croi = new CircularROI(croibean.getRadius(), 
					croibean.getStartPoint()[0], croibean.getStartPoint()[1]);
			croi.setName(croibean.getName());
			return croi;
		} else if(rbean instanceof CircularFitROIBean){
			CircularFitROIBean croibean = (CircularFitROIBean) rbean;
			Iterator<double[]> it = croibean.getPoints().iterator();
			PolylineROI poly = new PolylineROI();
			while (it.hasNext()) {
				double[] point = it.next();
				poly.insertPoint(point);
			}
			CircularFitROI croi = new CircularFitROI(poly);
			croi.setName(croibean.getName());
			return croi;
		} else if(rbean instanceof EllipticalROIBean){
			EllipticalROIBean eroibean = (EllipticalROIBean) rbean;
			EllipticalROI croi = new EllipticalROI();
			croi.setName(eroibean.getName());
			croi.setPoint(eroibean.getStartPoint());
			croi.setAngle(eroibean.getAngle());
			croi.setSemiaxes(eroibean.getSemiAxes());
			return croi;
		} else if(rbean instanceof EllipticalFitROIBean){
			EllipticalFitROIBean eroibean = (EllipticalFitROIBean) rbean;
			Iterator<double[]> it = eroibean.getPoints().iterator();
			PolylineROI poly = new PolylineROI();
			while (it.hasNext()) {
				double[] point = it.next();
				poly.insertPoint(point);
			}
			EllipticalFitROI eroi = new EllipticalFitROI(poly);
			eroi.setName(eroibean.getName());
			eroi.setSemiaxes(eroibean.getSemiAxes());
			eroi.setAngle(eroibean.getAngle());
			return eroi;
		} else {
			logger.debug("This type is not supported");
		}
		return null;
	}

	/**
	 * Method that returns true if the type of ROI is supported by the ROIBeanConverter
	 * @return boolean
	 */
	public static boolean isROISupported(IROI roi){
		if(roi instanceof PointROI
				|| roi instanceof LinearROI
				|| roi instanceof CircularROI
				|| roi instanceof GridROI
				|| roi instanceof PerimeterBoxROI
				|| roi instanceof RectangularROI
				|| roi instanceof RingROI
				|| roi instanceof SectorROI
				|| roi instanceof FreeDrawROI
				|| roi instanceof PolylineROI
				|| roi instanceof PolygonalROI
				|| roi instanceof XAxisBoxROI
				|| roi instanceof YAxisBoxROI
				|| roi instanceof EllipticalROI
				|| roi instanceof CircularFitROI
				|| roi instanceof EllipticalFitROI)
			return true;
		return false;
	}
}
