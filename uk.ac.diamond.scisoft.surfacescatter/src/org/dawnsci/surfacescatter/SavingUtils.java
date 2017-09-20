package org.dawnsci.surfacescatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.dawnsci.surfacescatter.MethodSettingEnum.MethodSetting;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class SavingUtils {


	private PrintWriter writer;

	public void genXSave(boolean writeOnlyGood,
			String title,
			CurveStitchDataPackage csdp,
			DirectoryModel drm,
			ArrayList<FrameModel> fms,
			GeometricParametersModel gm){

		try {
			File file = new File(title);
			file.createNewFile();
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		writer.println("#Output file created: " + strDate);

		if (drm.getCorrectionSelection() == MethodSetting.SXRD){		    

			for(int gh = 0 ; gh<fms.size(); gh++){
				FrameModel f = fms.get(gh);
				if((writeOnlyGood == true && f.isGoodPoint()) ||
						!writeOnlyGood){
					writer.println(f.getH() +"	"+ f.getK() +"	"+f.getL() + 
							"	"+ csdp.getSplicedCurveYFhkl().getDouble(gh)+ "	"+ csdp.getSplicedCurveY().getError(gh));

				}
			}
		}

		else{
			writer.println("#"+gm.getxName()+"	I	Ie");

			for(int gh = 0 ; gh<fms.size(); gh++){
				FrameModel f = fms.get(gh);
				if((writeOnlyGood == true && f.isGoodPoint()) ||
						!writeOnlyGood){

					writer.println(drm.getxList().get(gh) +"	"+ 
							csdp.getSplicedCurveY().getDouble(gh)+ "	"+ 
							csdp.getSplicedCurveY().getError(gh));

				}
			}
		}	
		writer.close();
	}	



	public void anarodSave(boolean writeOnlyGood,
			String title,
			CurveStitchDataPackage csdp,
			DirectoryModel drm,
			ArrayList<FrameModel> fms,
			GeometricParametersModel gm){

		try {
			File file = new File(title);
			file.createNewFile();
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);

		if (drm.getCorrectionSelection() == MethodSetting.SXRD){

			writer.println("# Test file created: " + strDate);
			writer.println("# Headers: ");
			writer.println("#h	k	l	F	Fe");

			for(int gh = 0 ; gh<fms.size(); gh++){

				FrameModel f = fms.get(gh);

				if((writeOnlyGood == true && f.isGoodPoint()) ||
						!writeOnlyGood){

					writer.println(f.getH() +"	"+ f.getK() +"	"+f.getL() + 
							"	"+ csdp.getSplicedCurveYFhkl().getDouble(gh)+ "	"+ csdp.getSplicedCurveYFhkl().getError(gh));

				}
			}
		}

		else{


			if (Double.isFinite(fms.get(0).getQ())){

				writer.println("# Test file created: " + strDate);
				writer.println("# Headers: ");
				writer.println("#qdcd	I	Ie");

				for(int gh = 0 ; gh<fms.size(); gh++){
					FrameModel f = fms.get(gh);
					if((writeOnlyGood == true && f.isGoodPoint()) ||
							!writeOnlyGood){


						writer.println(fms.get(gh).getQ() + "	"+ 
								csdp.getSplicedCurveY().getDouble(gh)+ "	"+ 
								csdp.getSplicedCurveY().getError(gh));
					}
				}
			}
			//		    
			else{
				writer.println("#"+gm.getxName()+"	I	Ie");

				for(int gh = 0 ; gh<fms.size(); gh++){
					FrameModel f = fms.get(gh);
					if((writeOnlyGood == true && f.isGoodPoint()) ||
							!writeOnlyGood){

						writer.println(drm.getxList().get(gh) +"	"+ 
								csdp.getSplicedCurveY().getDouble(gh)+ "	"+ 
								csdp.getSplicedCurveY().getError(gh));
					}
				}
			}
			//	    	
		}

		writer.close();
	}	

	public void intSave(boolean writeOnlyGood,
			String title,
			CurveStitchDataPackage csdp,
			DirectoryModel drm,
			ArrayList<FrameModel> fms,
			GeometricParametersModel gm){

		File file =null;

		try {
			file = new File(title);
			file.createNewFile();
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int index  = title.lastIndexOf(".");

		if(index != -1){
			String ext = title.substring(0,index);
			file.renameTo(new File(ext + ".int"));
		}
		else{
			file.renameTo(new File(title + ".int"));
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);

		if(drm.getCorrectionSelection() == MethodSetting.SXRD){

			writer.println("# Test file created: " + strDate);
			writer.println("# Headers: ");
			writer.println("#h	k	l	F	Fe	lorentz	correction 	polarisation correction		area correction");

			for(int gh = 0 ; gh<fms.size(); gh++){
				FrameModel f = fms.get(gh);
				if((writeOnlyGood == true && f.isGoodPoint()) ||
						!writeOnlyGood){

					writer.println(f.getH() +"	"+ f.getK() +"	"+f.getL() + 
							"	"+ csdp.getSplicedCurveYFhkl().getDouble(gh)+ "	"+ csdp.getSplicedCurveYFhkl().getError(gh) +"	"
							+ f.getLorentzianCorrection()+"	" + f.getPolarisationCorrection() +"	" + f.getAreaCorrection());
				}
			}
		}
		else{

			writer.println("# Test file created: " + strDate);
			writer.println("# Headers: ");

			if (Double.isFinite(fms.get(0).getQdcd())){			

				writer.println("#qdcd	I	Ie	Area Correction	Flux Correction");

				if(drm.getCorrectionSelection() == MethodSetting.Reflectivity_with_Flux_Correction_Gaussian_Profile ||
						drm.getCorrectionSelection() == MethodSetting.Reflectivity_with_Flux_Correction_Simple_Scaling){
					//
					for(int gh = 0 ; gh<fms.size(); gh++){

						FrameModel fm = fms.get(gh);
						if((writeOnlyGood == true && fm.isGoodPoint()) ||
								!writeOnlyGood){

							writer.println(fm.getQdcd() +"	"+ 
									csdp.getSplicedCurveY().getDouble(gh)+ "	"+ 
									csdp.getSplicedCurveY().getError(gh)+ "	"+ 
									fm.getReflectivityAreaCorrection()+ "	"+
									fm.getReflectivityFluxCorrection());
						}
					}
				}

				if(drm.getCorrectionSelection() == MethodSetting.Reflectivity_without_Flux_Correction_Gaussian_Profile ||
						drm.getCorrectionSelection() == MethodSetting.Reflectivity_without_Flux_Correction_Simple_Scaling){

					for(int gh = 0 ; gh<fms.size(); gh++){

						FrameModel fm = fms.get(gh);
						if((writeOnlyGood == true && fm.isGoodPoint()) ||
								!writeOnlyGood){

							writer.println(fm.getQdcd() +"	"+ 
									csdp.getSplicedCurveY().getDouble(gh)+ "	"+ 
									csdp.getSplicedCurveY().getError(gh)+ "	"+ 
									fm.getReflectivityAreaCorrection());
						}
					}
				}

				if(drm.getCorrectionSelection() == MethodSetting.Reflectivity_NO_Correction){

					for(int gh = 0 ; gh<fms.size(); gh++){

						FrameModel fm = fms.get(gh);
						if((writeOnlyGood == true && fm.isGoodPoint()) ||
								!writeOnlyGood){

							writer.println(fm.getQdcd() +"	"+ 
									csdp.getSplicedCurveY().getDouble(gh)+ "	"+ 
									csdp.getSplicedCurveY().getError(gh));
						}
					}
				}
			}
		}

		writer.close();
	}


	public void simpleXYYeSave(boolean useQ,
			boolean writeOnlyGood, 
			String title, 
			int state,
			CurveStitchDataPackage csdp,
			ArrayList<FrameModel> fms){

		File file =null;

		try {
			file = new File(title);
			file.createNewFile();
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int index  = title.lastIndexOf(".");
		if(index != -1){
			String ext = title.substring(0,index);
			file.renameTo(new File(ext + ".int"));
		}
		else{
			file.renameTo(new File(title + ".int"));
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);

		writer.println("# Test file created: " + strDate);
		writer.println("# Headers: ");


		IDataset y = DatasetFactory.createFromObject(0);
		IDataset ye = DatasetFactory.createFromObject(0);
		IDataset x = csdp.getSplicedCurveX();

		switch(state){
		case 0:
			y = csdp.getSplicedCurveY();
			ye = csdp.getSplicedCurveY().getErrors();
			break;
		case 1:
			y = csdp.getSplicedCurveYFhkl();
			ye = y = csdp.getSplicedCurveYFhkl().getErrors();
			break;
		case 2:
			y = csdp.getSplicedCurveYRaw();
			ye = csdp.getSplicedCurveYRaw().getErrors();
			break;
		default:
			//
			break;
		}

		if(useQ){
			x = csdp.getSplicedCurveQ();
			writer.println("#q	Y	Ye");
		}
		else{
			writer.println("#X	Y	Ye");
		}

		for(int gh = 0 ; gh<fms.size(); gh++){
			FrameModel fm = fms.get(gh);

			if((writeOnlyGood == true && fm.isGoodPoint()) ||
					!writeOnlyGood){

				writer.println(x.getDouble(gh) + 
						"	"+ y.getDouble(gh)+ 
						"	"+ ye.getDouble(gh));
			}
		}


		writer.close();
	}	


}
