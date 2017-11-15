package org.dawnsci.surfacescatter;

import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;

public class ModifiedAgnosticTrackerWithFrames1 extends AbstractTracker {

	public ModifiedAgnosticTrackerWithFrames1(DirectoryModel drm, int trackingMarker, int k, double[] seedLocation,
			int selection) {

		super.init(drm, trackingMarker, k, seedLocation, selection);

		if (super.getFrame().getRoiLocation() == null) {
			runTrack(trackingMarker);
		}

	}

	public ModifiedAgnosticTrackerWithFrames1(DirectoryModel drm, int trackingMarker, int k, int selection) {

		super.init(drm, trackingMarker, k, selection);

		if (super.getFrame().getRoiLocation() == null) {
			runTrack(trackingMarker);
		}

	}

	private void runTrack(int trackingMarker) {

		if (super.getFrame().getTrackingMethodology() != TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION
				&& super.getFrame().getTrackingMethodology() != TrackingMethodology.TrackerType1.USE_SET_POSITIONS) {

			switch (trackingMarker) {

			case 0:
				initialiseRestart();
				break;

			case 1:
				initialiseRestart();
				break;

			case 2:
				restartInOppositeDirection();
				break;
			default:
				// blank, no trackingMarkers >3 should enter this class
			}

		}

		else if (super.getFrame().getTrackingMethodology() == TrackingMethodology.TrackerType1.SPLINE_INTERPOLATION) {
			/////// start the interpolation tracker

			interpolationRoutine();

		}
		
		else if (super.getFrame().getTrackingMethodology() == TrackerType1.USE_SET_POSITIONS) {
			
			int[][] lenpt = LocationLenPtConverterUtils.locationToLenPtConverter(super.getDrm().getSetPositions()[selection]);
			super.getDrm().setInitialLenPt(lenpt);
			
		}

		if (super.isTheTrackingMarkerNot3()) {
			setPostTrackParameters();

		}

	}
}
