package Observer;

import java.util.List;
import java.util.ArrayList;

import util.Debug;
import util.Display;

import Subject.Subject;
import Subject.SystemData;

public class Performance implements Observer, Display{

	private List<String> process;
	private List<String> user;

	private int current_cpu;
	private int current_memory;
	private int size;
	
	private List<Integer> process_cpu;
	private List<Integer> process_memory;
	
	private Subject systemData;
	
	
	/**
	* Constructor of the Performance class
	* Initializes all data structures that stores the cueent cpu usage data, and
	* current memory usage data.
	*
	* All data structures needed to differentiate processes are also initialized
	*
	* The Performance class is then added to the Observers to the Subject
	*
	* @param systemData
	*
	*/
	public Performance(Subject systemData){

		current_cpu = 0;
		current_memory = 0;
		size = 0;
		
		process_cpu = new ArrayList<Integer>();
		process_memory = new ArrayList<Integer>();
		
		process = new ArrayList<String>();
		user = new ArrayList<String>();

		this.systemData = systemData;
		systemData.registerObserver(this);	
		
		Debug.dump(3, "Performance Display");
	}

	/**
	* Updates the current cpu usage, and the current memory usage.
	* All changes to the cpu usage and memory usage to any of the processes are noted 
	* and the current cpu usage and current memory usage are updated
	*
	* @param process user cpu memory descr
	*
	*/
	@Override
	public void update(String process, String user, int cpu, int memory, String descr){

		try{
			if(cpu != -1 && memory != -1){
	
				boolean process_exists = false;
	
				String user_status[] = user.split("-");
				user = user_status[0];
	
				if(this.process.contains(process) && this.user.contains(user)){
			
					for(int i=0;i<size;i++){
						if(this.process.get(i).equals(process) && this.user.get(i).equals(user)){
			
							current_cpu = current_cpu - process_cpu.get(i);
							current_memory = current_memory - process_memory.get(i);
			
							process_cpu.set(i, cpu);
							process_memory.set(i, memory);
					
							process_exists = true;
					
							break;
						}
					}
			
				}else{
					process_exists = false;
		
				}
		
		
				if(!process_exists){
					this.process.add(process);
					this.user.add(user);
					process_cpu.add(cpu);
					process_memory.add(memory);
			
					process_exists = true;
			
					size++;
				}
		
				current_cpu = current_cpu + cpu;
				current_memory = current_memory + memory;
			}
		
			display();
			
		}catch(NullPointerException e){

			Debug.dump(0, e.getMessage());
			
			System.exit(0);
		
		}catch(ArrayIndexOutOfBoundsException e){
		
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
		}
	}

	/**
	* Displays Performance information
	* Displays current cpu usage, current memory usage,
	* total cache, and total memory to standard out
	*
	*/
	@Override
	public void display(){
	
		try{
		
			if(Debug.getDebugValue() == 1){
				System.out.println("\nMemory");
				System.out.println("Current CPU: " + current_cpu + "\t\tCurrent Memory: " + ((double)current_memory/SystemData.total_memory)*100  + "%\t\tTotal Memory: " +  SystemData.total_memory + "\t\tTotal Cache: " + SystemData.total_cache);
			
			}
			
		}catch(NullPointerException e){
			Debug.dump(0, e.getMessage());
			
			System.exit(0);
		
		}catch(ArrayIndexOutOfBoundsException e){
		
			Debug.dump(0, e.getMessage());
		
			System.exit(0);
		}
	}
	
	/**
	* Returns the performance record stored in the record data structure
	*
	* @return String
	*
	*/
	public String toString(){
	
		String performanceRecord = "";
		
		for(int i=0;i<process.size();i++){
			performanceRecord = performanceRecord + current_cpu + " " + current_memory +  " " + SystemData.total_memory + " " + SystemData.total_cache + "\n";
		}
		
		return performanceRecord;
	
	}
}
