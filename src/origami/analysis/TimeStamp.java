package origami.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp implements Cloneable{

		private Date date;
		private long standarizedTime;
		private String action;
		private int IDAction;
		private String block;
		private int numberBlock;
		private int IDBlock;
		private String inner;
		
		public TimeStamp(){
			date = null;
			action = "";
			IDAction = 0;
			block = "";
			numberBlock = 0;
			IDBlock =0;
			inner = "";
		}
				
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		
		public String getBlock() {
			return block;
		}
		public void setBlock(String block) {
			this.block = block;
		}
		public int getNumberBlock() {
			return numberBlock;
		}
		public void setNumberBlock(int numberBlock) {
			this.numberBlock = numberBlock;
		}
		public int getIDAction() {
			return IDAction;
		}
		
		public static  int getIDAction(String action) {
			int idAction = -1;
			if (action.compareTo("NEW") == 0)
				idAction = 1;
			else if(action.compareTo("OPEN") == 0)
				idAction = 1;
			else if(action.compareTo("ADD") == 0)
				idAction = 2;
			else if(action.compareTo("EDIT") == 0)
				idAction = 3;
			else if(action.compareTo("DELETE") == 0)
				idAction = 4;
			else if(action.compareTo("SAVE") == 0)
				idAction = 5;
			else if(action.compareTo("COMPIL") == 0)
				idAction = 5;
			else if(action.compareTo("TEST") == 0)
				idAction = 6;
			else if(action.compareTo("FULL") == 0)
				idAction = 6;
			else if(action.compareTo("SBS") == 0)
				idAction = 7;
			else if(action.compareTo("STOP") == 0)
				idAction = 8;
			else if(action.compareTo("SECOND") == 0)
				idAction = 9;
			return idAction;
		}
		
		public void setIDAction(int iDAction) {
			IDAction = iDAction;
		}
		public int getIDBlock() {
			return IDBlock;
		}
		
		public static  int getIDBlock(String action) {
			int idBlock = -1;
			if (action.compareTo("INPUT") == 0)
				idBlock = 1;
			else if(action.compareTo("OUTPUT") == 0)
				idBlock = 2;
			else if(action.compareTo("EXPRESSION") == 0)
				idBlock = 3;
			else if(action.compareTo("DECISION") == 0)
				idBlock = 4;
			else if(action.compareTo("WHILE") == 0)
				idBlock = 5;
			else if(action.compareTo("FOR") == 0)
				idBlock = 6;
		
			return idBlock;
		}
		public void setIDBlock(int iDBlock) {
			IDBlock = iDBlock;
		}
		public String getInner() {
			return inner;
		}
		public void setInner(String inner) {
			this.inner = inner;
		}
		
		public String toString(){
			
		return formatingDate(date) + "\t" +
			   formatingTime(date) + "\t" +
			   standarizedTime + "\t" +
			   action + "\t" +
			   IDAction + "\t" +
			   block + "\t" +
			   numberBlock + "\t" +
			   IDBlock + "\t" +
			   inner;
			
		}
		
		public String formatingDate(Date date){
			String pattern = "dd-MM-yyyy";
	        SimpleDateFormat sdf = new SimpleDateFormat();
	        // apply the pattern to the SimpleDateFormat class
	        sdf.applyPattern(pattern);
			return sdf.format(date);
		}
		
		public String formatingTime(Date date){
			String pattern = "HH:mm:ss";
	        SimpleDateFormat sdf = new SimpleDateFormat();
	        // apply the pattern to the SimpleDateFormat class
	        sdf.applyPattern(pattern);
			return sdf.format(date);
		}

		public long getStandarizedTime() {
			return standarizedTime;
		}

		public void setStandarizedTime(long standarizedTime) {
			this.standarizedTime = standarizedTime;
		}
		
		public void setStandarizedTime() {
			this.standarizedTime = this.date.getTime();
		}

		public Object clone() throws CloneNotSupportedException {

		    TimeStamp clone=(TimeStamp)super.clone();

		    // make the shallow copy of the object of type Department
		    	clone.date=(Date)date.clone();
		    return clone;

		  }
		
}
