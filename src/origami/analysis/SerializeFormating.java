package origami.analysis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class SerializeFormating {
	Vector<String> info;
	
	public SerializeFormating(Vector<String> info){
		this.info = info;
	}
	
	@SuppressWarnings("deprecation")
	public TimeStampVector formingInfoVector(){
		TimeStampVector infovector = new TimeStampVector();
		for(int index = 0; index<info.size(); index++){
			String stringInfo =info.get(index);
			//if the line is a timestamp
			try{
				if(isStringInfoTimeStampValid(stringInfo)){
					
					infovector.setTimeStamp(stringToTimeStamp(stringInfo));
			    	
				}
					//if the line is a inner content
				else{
					int poslastElement =infovector.size() - 1;
					TimeStamp  timestamp = infovector.getTimeStamp(poslastElement);
					//stringInfo = stringInfo.replaceAll("\n", " ") + "\n";
					timestamp.setInner(timestamp.getInner() + stringInfo);
					infovector.replace(poslastElement, timestamp);
					}
		    	}
			catch(Exception e){
				System.out.println( e.getClass() + "MISSING INFO");
				}//catch
		}// end for
		return infovector;
	}
		
	@SuppressWarnings({ "deprecation" })
	public TimeStamp stringToTimeStamp(String infostring){
		String action ="";
		String numberblock = "";
		TimeStamp  timestamp = new TimeStamp();
		String replacement = infostring;
    	//setting timestamp
		String datetime = replacement.substring(0, 19);
		timestamp.setDate(getDateFormat(datetime));
		timestamp.setStandarizedTime();
    	//setting action
		boolean sbs = replacement.charAt(19) == ';';
		replacement = replacement.substring(20, replacement.length());
    	// in case of no action but SBS
		if(sbs){
			action ="SBS";
			timestamp.setAction(action);
			timestamp.setIDAction(TimeStamp.getIDAction(action));
			}
		// has an action 
		else
			{
			int posNextBlank = replacement.indexOf(" ");
	
			//in case of NEW, SAVE, OPEN no actions, neither blocks and numberblocks...
	    	if (posNextBlank == -1 ){
				action = replacement.substring(0, replacement.length());
				timestamp.setAction(action);
				timestamp.setIDAction(TimeStamp.getIDAction(action));
				replacement = "";
				}
	    	//in case of ADD, EDIT, DELETE, COMPIL, T, FULL or SAVE with innit
	    	else{
		    	action = replacement.substring(0, posNextBlank);
		    	
		    	//action is T or Te
		    	if (action.startsWith("T") || action.compareTo("") == 0)
					action = "TEST";
		    	
		    	timestamp.setAction(action);
				timestamp.setIDAction(TimeStamp.getIDAction(action));
				replacement = replacement.substring(posNextBlank + 1, replacement.length());
				//in case of actions with blocks
				if(action.compareTo("ADD")== 0 || action.compareTo("EDIT")== 0 || action.compareTo("DELETE")== 0){
					//setting block
					posNextBlank = replacement.indexOf(" ");
					String block = (replacement.substring(0, posNextBlank)).toUpperCase();
					timestamp.setBlock(block);
					//setting IDBlock
					timestamp.setIDBlock(TimeStamp.getIDBlock(block));
					//setting number block
					posNextBlank = replacement.indexOf(" ");
					replacement = replacement.substring(posNextBlank + 1, replacement.length());
					posNextBlank = replacement.indexOf(" ");
					//in case after number block is the last character in replacement and  inner will be ""
					if(posNextBlank == -1){
						numberblock = replacement;
						replacement = "";
						}
					else{
						numberblock = (replacement.substring(0,posNextBlank));
						replacement = replacement.substring(posNextBlank + 1, replacement.length());
					}
					timestamp.setNumberBlock(Integer.parseInt(numberblock));
					
					}
    		}//else ADD, EDIT, DELETE
			
	    }//else SBS	
		timestamp.setInner(cleanReplacement(replacement));
		//System.out.println(timestamp);
		return timestamp;
	}
	
	private String cleanReplacement(String repl){
		repl = repl.replaceAll("\n", " ");
		repl = repl.replaceAll("\r", " ");
		
		return repl;
		
	}
	public Date getDateFormat(String datetime) {
			DateFormat sourceFormat =null;
			Date date = null;
			try{
				if (datetime.matches("\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}")){
					sourceFormat =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					date = sourceFormat.parse(datetime);
				}
				
			}
			catch(ParseException pe){
				System.out.println(pe);
				return new Date();
			}
			return date;
	}
	
	public Vector<String> formatingPlainInfo(){
		for(int index=0; index<info.size(); index++){
	    	String replacement=info.elementAt(index);
	    	if((replacement.substring(0, 10)).matches("\\d{2}/\\d{2}/\\d{4}")){
	    		replacement = replacement.replaceFirst("\\s", ",");
	    		replacement = replacement.replaceFirst("\\s", ",");
	    		replacement = replacement.replaceFirst("\\s", ",");
	    		replacement = replacement.replaceFirst("\\s", ",");
	    		replacement = replacement.replaceFirst("\\s", ",");
	    		replacement = replacement.replaceAll(",", "\t");
	    		info.set(index, replacement);
	    		}
			}
		return info;		
	}
	
	public boolean isStringInfoTimeStampValid(String stringInfo){
		
		String timestamp = stringInfo.substring(0,10);
		return timestamp.matches("\\d{2}/\\d{2}/\\d{4}");
	}

}
