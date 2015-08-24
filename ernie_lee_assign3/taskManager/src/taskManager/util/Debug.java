package util;

import java.util.logging.Logger;

public abstract class Debug extends Logger{
	
	private static int DEBUG_VALUE;
	
	/**
	 * debug constructor
	 */
	protected Debug(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
	/**
	 * Sets debug value of the Logger
	 * 
	 * @param val
	 */
	public static void setDebugValue(int val){
		DEBUG_VALUE = val;
	}
	
	/**
	 * Gets the debug value set by the main class
	 * 
	 * @return int DEBUG_VALUE
	 */
	public static int getDebugValue(){
		return DEBUG_VALUE;		
	}
	
	/**
	 * Prints out the message specified by the debug value
	 *  
	 * DEBUG_VALUE=4 [Print to stdout, using the Logger, everytime an Observer is removed]
	 * DEBUG_VALUE=3 [Print to stdout, using the Logger, everytime an Observer is added]
	 * DEBUG_VALUE=2 [Print to stdout, using the Logger, to display all system updates]
	 * DEBUG_VALUE=1 [Print to stdout, using the Logger, to display the dashboard]
	 * DEBUG_VALUE=0 [No output should be printed. Only error messages should be printed] 
	 * 
	 * @param value
	 * @param message
	 */
	public static void dump(int value, String message){
		
		if(value == DEBUG_VALUE){
			
			System.out.print("Debug Level " + DEBUG_VALUE + " " + message + ": ");
			
			if(DEBUG_VALUE == 4){	
				
				System.out.println("Removed Observer");
				
			}else if(DEBUG_VALUE == 3){
				
				System.out.println("Registered Observer");
				
			}else if(DEBUG_VALUE == 2){
				
				System.out.println("All Updates Displayed");
				
			}else if(DEBUG_VALUE == 1){
				
				//Do Nothing
				//Print out dashboard to output
				
			}else if(DEBUG_VALUE == 0){
			
				System.out.println("Error Message");
			
			}
		}
	}
	
	/**
	 * returns the debug value of the debug logger
	 * 
	 * @return String
	 */
	public String toString(){
		
		return String.valueOf(DEBUG_VALUE);
	}
}
