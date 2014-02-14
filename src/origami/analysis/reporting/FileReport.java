package origami.analysis.reporting;

import java.text.SimpleDateFormat;
import java.util.Date;

import origami.analysis.TimeStamp;
import origami.analysis.TimeStampVector;

public class FileReport {
	long interrumptionTime;
	int interrumptions;
	
	public FileReport(){
	}
	
	public TimeStampVector reportWithoutFullSave(TimeStampVector tsvOriginal){
		TimeStampVector report = new TimeStampVector();
		try{
		for(int index =0; index < tsvOriginal.size(); index++){
			TimeStamp  timestamp = tsvOriginal.getTimeStamp(index);
			if(!(timestamp.getAction().compareTo("FULL") == 0 || timestamp.getAction().compareTo("SAVE") == 0)){
				TimeStamp timestampcopy = (TimeStamp)timestamp.clone();
				report.setTimeStamp(timestampcopy);
				}
			}
		}
		catch(CloneNotSupportedException e){
			System.err.println(e);
		}
		
		return report;
	}
	
	
	
	public double getSolvingTime(TimeStampVector tsv){
	 long timeElapsed = 0;
	 long open = tsv.getTimeStamp(0).getStandarizedTime();
	 for(int index = 0; index < tsv.size(); index++){
		 if(tsv.getTimeStamp(index).getAction().compareTo("OPEN") == 0){
			 //System.out.println("***" + timestamp);
			 long close = tsv.getTimeStamp(index -1).getStandarizedTime();
			 timeElapsed = timeElapsed + (close - open);
			 open = tsv.getTimeStamp(index).getStandarizedTime();
			 //System.out.println("close: " + close + " open: " + open+ "te: " + timeElapsed);
		 	}
	 	}
	 	timeElapsed = timeElapsed + tsv.getTimeStamp(tsv.size() - 1).getStandarizedTime() - open;
	 	return timeElapsed/60.00;
	}
	
	public double getSolvingTimeWithoutInterruptions(TimeStampVector tsv){
		 long timeElapsed = 0;
		 interrumptionTime = 0;
		 long open = tsv.getTimeStamp(0).getStandarizedTime();
		 for(int index = 0; index < tsv.size(); index++){
			 if(tsv.getTimeStamp(index).getAction().compareTo("OPEN") == 0){
				 //System.out.println("***" + timestamp);
				 long close = tsv.getTimeStamp(index -1).getStandarizedTime();
				 timeElapsed = timeElapsed + (close - open);
				 open = tsv.getTimeStamp(index).getStandarizedTime();
				 //System.out.println("close: " + close + " open: " + open+ "te: " + timeElapsed);
			 	}
		 	}
		  	timeElapsed = timeElapsed + tsv.getTimeStamp(tsv.size() - 1).getStandarizedTime() - open;
		 	
		  	//identifying interruptions and discounting from ElapsedTime
		  	for(int index = 1; index < tsv.size(); index++){
		  	  long previous = tsv.getTimeStamp(index-1).getStandarizedTime();
		  	  long next = tsv.getTimeStamp(index).getStandarizedTime();
		  	  long step = next - previous;
		  	    if (step >= 600 && tsv.getTimeStamp(index).getAction().compareTo("OPEN") != 0){
		  		timeElapsed = timeElapsed -step;
		  		interrumptionTime+=step;
		  		interrumptions++;
		  		System.out.println("Interruption: " + step/60.00);
		  	    }
		  	}
			//System.out.println("Interruption Time: " + interrumptionTime/60.00);
		  	return timeElapsed/60.00;
		}
	
	@SuppressWarnings("deprecation")
	public int getDaysWorked(TimeStampVector tsv){
		int daysWorked = 1;
		for(int index = 0; index < tsv.size(); index++){
			 if(tsv.getTimeStamp(index).getAction().compareTo("OPEN") == 0){
				 if(tsv.getTimeStamp(index - 1).getDate().getDay() < tsv.getTimeStamp(index).getDate().getDay()  ){
					 daysWorked++;
				 } 
			 	}
			 }
		return daysWorked;
	}
	
	public int getPeriodsWorked(TimeStampVector tsv){
		int periodsWorked = 1;
		for(int index = 0; index < tsv.size(); index++){
			 if(tsv.getTimeStamp(index).getAction().compareTo("OPEN") == 0){
				 periodsWorked++;
			 	}
			 }
		return periodsWorked;
	}
	
	public int getSecondRep(TimeStampVector tsv){
	    int secondRep = 0;
		for(int index = 0; index < tsv.size(); index++){
			 if(tsv.getTimeStamp(index).getAction().compareTo("SECOND") == 0){
				 secondRep++;
			 	}
			 }
		return secondRep;
	    
	}
	
	public double getInterrumptionTime(){
	    return interrumptionTime/60.00;
	}
	
	public int getInterrumptions(){
	    return interrumptions;
	}
	
	public String getActivityStats(TimeStampVector tsv){
	    int constructingActivity = 0;
	    int testingActivity = 0;
	    
	    for(int index = 0; index < tsv.size(); index++){
		 if(tsv.getTimeStamp(index).getAction().compareTo("ADD") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("DELETE") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("EDIT") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("NEW") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("OPEN") == 0) 
			 {
			 	constructingActivity++;
		 	}
		 else if (tsv.getTimeStamp(index).getAction().compareTo("COMPIL") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("TEST") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("STOP") == 0){
		     		testingActivity++;
		 	}
		 }
	    return "Constructing Activity: " + constructingActivity +
		    		" Testing Activity: " + testingActivity;
	}
	
	public String getActivityStatsByType(TimeStampVector tsv){
	    int newOpenActivity = 0;
	    int addActivity = 0;
	    int deleteActivity = 0;
	    int editActivity = 0;
	    int testingActivity = 0;
	    
	    for(int index = 0; index < tsv.size(); index++){
		 if(tsv.getTimeStamp(index).getAction().compareTo("ADD") == 0){
		     addActivity++;
		 }
		 else if(tsv.getTimeStamp(index).getAction().compareTo("DELETE") == 0){
		     deleteActivity++;
		 }
		 else if ( tsv.getTimeStamp(index).getAction().compareTo("EDIT") == 0){
		     editActivity++;
		 }
		 else if(tsv.getTimeStamp(index).getAction().compareTo("NEW") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("OPEN") == 0){
		     newOpenActivity++;
		 } 	
		 else if (tsv.getTimeStamp(index).getAction().compareTo("COMPIL") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("TEST") == 0 ||
			 tsv.getTimeStamp(index).getAction().compareTo("STOP") == 0){
		     		testingActivity++;
		 	}
		 }
	    return "NewOpen\tAdd\tUpdate\tDelete\tTesting\n" +
		 	newOpenActivity +"\t" + addActivity +"\t" + editActivity +"\t" + deleteActivity +"\t" + testingActivity; 
	}
	
	@SuppressWarnings("deprecation")
	public String reportExcel(TimeStampVector tsv){
		String string = "";
		int blocks[] = new int[6];
		int periodsWorked = 1;
		int daysWorked= 1;
		long timeElapsed = 0;
		int firstCompiling = 0;
		boolean firstCompilingFlag = true;
		int firstCompilingSuccessful = 0;
		boolean firstCompilingSuccessfulFlag = true;
		int numberTest = 0;
		int numberError = 0;
		int inactivity = 0;
		double firstActivity = 0;
		boolean firstActivityFlag = true;
		int secondRep = 0;
		
		//Getting Starting date and time
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd-MM-yyyy");
		String startingDate = sdf.format(tsv.getTimeStamp(0).getDate());
		sdf.applyPattern("HH:mm:ss");
		String startingTime = sdf.format(tsv.getTimeStamp(0).getDate());
		
		long open = tsv.getTimeStamp(0).getStandarizedTime();
		 for(int index = 0; index < tsv.size(); index++){
			 TimeStamp timestamp = tsv.getTimeStamp(index);
			 String action = timestamp.getAction();
			 
			 if(firstActivityFlag && action.compareTo("ADD") == 0 ){
				 firstActivity =timestamp.getStandarizedTime();
				 firstActivityFlag = false;
			 }
			 //number blocks
			 if(action.compareTo("ADD") == 0 || action.compareTo("DELETE") == 0){
				 int blockcount = 1;
				 if(action.compareTo("DELETE") == 0) blockcount=-1;
				 //identifying block
				 int blockType = timestamp.getIDBlock();
				 switch(blockType){
					 case 1:blocks[0]+=blockcount;
					 break;
					 case 2:blocks[1]+=blockcount;
					 break;
					 case 3:blocks[2]+=blockcount;
					 break;
					 case 4:blocks[3]+=blockcount;
					 break;
					 case 5:blocks[4]+=blockcount;
					 break;
					 default:blocks[5]+=blockcount;
					 break;
				 }
			 }
			 //to check Time, Days and Periods 
			 else if (action.compareTo("OPEN") == 0){
				 periodsWorked++;
				 long close = tsv.getTimeStamp(index -1).getStandarizedTime();
				 timeElapsed = timeElapsed + (close - open);
				 open = timestamp.getStandarizedTime();
				 inactivity = inactivity + (int)(open - close);
				 
				 if(tsv.getTimeStamp(index - 1).getDate().getDay() < timestamp.getDate().getDay() ){
					 daysWorked++;
				 } 
			 }
			 //check for compiling
			 else if(action.compareTo("COMPIL") == 0){
				 if(firstCompilingFlag){
					 firstCompiling = (int)timestamp.getStandarizedTime() - inactivity;
					 firstCompilingFlag = false; 
				 }
				 if (firstCompilingSuccessfulFlag && timestamp.getInner().indexOf("successful") != -1 ){
					 firstCompilingSuccessful = (int)timestamp.getStandarizedTime() - inactivity;
					 firstCompilingSuccessfulFlag = false;
					 
				 }
				 if (timestamp.getInner().indexOf("ERROR") != -1){
					 numberError++;					 
				 }
				 
			 }// action == COMPIL
			 else if (action.compareTo("TEST") == 0){
				 numberTest++;
			 }
		 	}//for
		 	timeElapsed = timeElapsed + tsv.getTimeStamp(tsv.size() - 1).getStandarizedTime() - open;
		 	
    //forming string report
		 	int numberBlocks = 0;
		 	String blockCount = "";
		 	for(int pos: blocks){
		 		numberBlocks+=pos;
		 		blockCount+= pos + "\t";
		 	}
		 	
		 	string = startingDate + "\t" +
		 			 startingTime + "\t" +
		 			 timeElapsed/60.00 + "\t" +
		 			 firstActivity/60.00 + "\t" +
		 			 daysWorked + "\t" +
		 			 periodsWorked + "\t" +
		 			 firstCompiling/60.00 + "\t" +
		 			 firstCompilingSuccessful/60.00 + "\t" +
		 			 numberTest + "\t" +
		 			 numberError + "\t" +
		 			 numberBlocks + "\t" +
		 			 blockCount;
		 			 
		 	return string;	
	}
	
	
	public String getCompileInfo(TimeStampVector tsv){
		String string="";
		for(int index =0; index < tsv.size(); index++){
			TimeStamp  timestamp = tsv.getTimeStamp(index);
			String inner = timestamp.getInner();
			if(timestamp.getAction().compareTo("COMPIL") == 0){
				string = string + timestamp.getStandarizedTime()/60.00 + " ";
				if(inner.indexOf("successful") != -1){
					string = string + "\tSucessful\n";
				}
				else if(inner.indexOf("Error") != -1){
					string = string + "\tError\n";
				}
				else{
					string = string + "\tNO RECONOCIDO\n";
				}
			}// if
		}
		return string;
	}
	
	/*public String getFirstCompileTimeSucessful(TimeStampVector tsv){
		String string="";
		for(int index =0; index < tsv.size(); index++){
			TimeStamp  timestamp = tsv.getTimeStamp(index);
			String action =timestamp.getAction();
			String innner = timestamp.getInner();
			if(compareTo("COMPIL") == 0 and inner.match()){
				string = timestamp.getStandarizedTime() + " " + timestamp.getInner();
				index = tsv.size();
			}// if
		}
		return string;
	}*/
}
