package origami.analysis;


import java.util.Vector;


public class TimeStampVector implements Cloneable {

	Vector<TimeStamp> info;
	
	public TimeStampVector(){
		this.info = new Vector<TimeStamp>();
	}
	
	public TimeStampVector(Vector<TimeStamp> info){
		this.info = info;
	}
	
	public TimeStamp getTimeStamp(int index) {
		return info.get(index);
	}
	public void setTimeStamp(TimeStamp timestamp) {
		info.add(timestamp);
	}
	public String toString(){
		String string = "";
		for(int index =0; index < info.size(); index++){
			string = string + info.get(index).toString() + "\n";
		}
		return string;
	}

	public void replace(int index, TimeStamp element){
		info.set(index, element);
	}
	
	public int size(){
		return info.size();
		}
		
	public void standarizedTimeStampVector (){
		long firstStep = this.getTimeStamp(0).getStandarizedTime();
		for(int index = 0; index < this.size(); index++){
			TimeStamp  timestamp = this.getTimeStamp(index);
			long time = timestamp.getStandarizedTime();
			timestamp.setStandarizedTime((time- firstStep)/1000);
		}	
	}
	
	public void remove(int index){
		this.remove(index);
		
	}
	
	public Object clone() throws CloneNotSupportedException {

	    
		TimeStampVector clone=(TimeStampVector)super.clone();
	    return clone;

	  }
}


