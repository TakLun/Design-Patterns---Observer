package Subject;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import util.Debug;
import Observer.Observer;

public class SystemData implements Subject{

	public static int total_cache;
	public static int total_memory;

	private List<Observer> observers;
	
	private BufferedReader br;
	
	//Data
	private String process;
	private String user;
	private int cpu;
	private int memory;
	private String descr;
	
	StringBuffer systemRecord;
	
	/**
	* Constructor for SystemData Class
	* Initializes all data including total cache, total RAM, process name,
	* user name, cpu, current RAM, process description,
	* and the data structure to store the system data
	*/
	public SystemData(){
		observers = new ArrayList<Observer>();
		br = null;
		
		total_cache = 80000;
		total_memory = 13107200;
		
		process = "";
		user = "";
		cpu = -1;
		memory = -1;
		descr = "";
		
		systemRecord = new StringBuffer();
	}
	
	/**
	* Method starts the dashboard display. 
	* Reads the data from the data file and parses the line into the appropriate data
	* Each line is stored in the system records
	*
	* @param data
	*/
	public void start(String data){
		try{
			String line;
			
			br = new BufferedReader(new FileReader(data));
		
			while( (line = br.readLine()) != null){
				systemRecord.append(line + "\n");
				parseData(line);
			}
			
			if(Debug.getDebugValue() == 2){
				Debug.dump(2, "System Records");
				
				System.out.println(systemRecord);
			}
		
		}catch(IOException e){
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
		
		}finally{
		
			try{
				if(br != null)
					br.close();
				
			}catch(IOException e){
				Debug.dump(0, e.getMessage());
			
				System.exit(0);
			}			
		}
	
	}
	
	/**
	* Method that parses a line from the data file
	* The line is parsed into the process name, user name, 
	* cpu, RAM, and process description. 
	* Data is then set and the changes are noted by the observers
	*
	* @param line
	*/
	private void parseData(String line){
		try{
		
			String process = "";
			String user = "";
			int cpu = -1;
			int memory = -1;
			String descr = "";
			
			String sysData[] = line.split("\\t|\\t");
		
			if(sysData.length == 9){
				for(int i=0;i<sysData.length;i=i+2){
					String element[] = sysData[i].split(": ");
				
					if(i==0 && element.length > 1){
						process = element[1];
					
					}else if(i==2 && element.length > 1){
						user = element[1];
					
					}else if(i==4 && element.length > 1){
						cpu = Integer.parseInt(element[1]);
					
					}else if(i==6 && element.length > 1){
						memory = Integer.parseInt(element[1]);
					
					}else if(i==8 && element.length > 1){
						descr = element[1];
					
					}			
				}
				
			}else if(sysData.length == 3){
				
				for(int i=0;i<sysData.length;i=i+2){
					String element[] = sysData[i].split(": ");
				
					if(i==0 && element.length > 1){
						total_memory = Integer.parseInt(element[1]);
					
					}else if(i==2 && element.length > 1){
						total_cache = Integer.parseInt(element[1]);
					
					}			
				}
				
			}
			
			setData(process, user, cpu, memory, descr);
			
			Thread.sleep(100);
			
		}catch(InterruptedException e){
			
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
			
		}catch(NumberFormatException e){
		
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
		}catch(ArrayIndexOutOfBoundsException e){
		
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
		}
	}
	
	/**
	* Sets data that was parsed into the appropriate variables
	*
	* @param process user cpu memory descr
	*/
	private void setData(String process, String user, int cpu, int memory, String descr){
	
		this.process = process;
		this.user = user;
		this.cpu = cpu;
		this.memory = memory;
		this.descr = descr;
	
		dataChanged();	
	}
	
	/**
	* All data changed is then notifieed by the observers
	*
	*/
	private void dataChanged(){
		System.out.print("\033[2J");
		notifyObservers();	
	}
	
	/**
	* Add an Observer to the Subject
	*
	* @param o
	*/
	@Override
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	/**
	* Removes an Observer from the Subject
	*
	* @param o
	*/
	@Override
	public void removeObserer(Observer o){
		int i = observers.indexOf(o);
		if(i>0)
			observers.remove(i);
			
			
		Debug.dump(4, "Observer Removed");
	}
	
	/**
	* Notifies all the observers to the subject of the 
	* changes that were made to the data.
	*
	*/
	@Override
	public void notifyObservers(){
		try{
			for(int i=0;i<observers.size();i++){
				Observer observer = (Observer)observers.get(i);
				observer.update(process, user, cpu, memory, descr);
			}	
			
		}catch(ArrayIndexOutOfBoundsException e){
		
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
			
		}catch(NullPointerException e){
		
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
		}
	}
	
	/**
	* Returns the system record stored in the record data structure
	*
	* @return String
	*
	*/
	public String toString(){
		return systemRecord.toString();	
	}

}
