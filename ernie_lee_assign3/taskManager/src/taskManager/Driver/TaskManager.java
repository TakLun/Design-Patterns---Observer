package Driver;

import util.Debug;

import Subject.SystemData;

import Observer.Processes;
import Observer.Performance;
import Observer.Users;

public class TaskManager{

	/**
	* Main driver class for the program
	*
	* @param args
	*/
	public static void main(String []args){
	
		try{
	
			String textFile = args[0];
			int debug = Integer.parseInt(args[1]);
	
			Debug.setDebugValue(debug);
	
			SystemData systemData = new SystemData();
		
			Processes processes = new Processes(systemData);
			Performance performance = new Performance(systemData);
			Users users = new Users(systemData);
		
			systemData.start(textFile);
			
		}catch(NumberFormatException e){
			Debug.dump(0, e.getMessage());
			
			System.exit(0);
		}
	}
}
