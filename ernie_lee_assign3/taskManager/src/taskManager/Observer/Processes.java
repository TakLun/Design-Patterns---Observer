package Observer;

import java.util.List;
import java.util.ArrayList;

import util.Debug;
import util.Display;

import Subject.Subject;

public class Processes implements Observer, Display{

	private List<String> process;
	private List<String> user;
	private List<Integer> cpu;
	private List<Integer> memory;
	private List<String> descr;
	
	private int size;

	private Subject systemData;
	
	/**
	* Constructor of the Process class
	* Initializes all data structures that stores the process name data,
	* user name data, cpu data, memory data, and the process description data.
	*
	* The Process class is then added to the Observers to the Subject
	*
	* @param systemData
	*
	*/
	public Processes(Subject systemData){
	
		process = new ArrayList<String>();
		user = new ArrayList<String>();
		cpu = new ArrayList<Integer>();
		memory = new ArrayList<Integer>();
		descr = new ArrayList<String>();
		
		size = 0;
	
		this.systemData = systemData;
		systemData.registerObserver(this);
		
		Debug.dump(3, "Process Display");
	
	}

	/**
	* Updates the process name, user name, cpu, memory, and process description.
	* And processes and users not added to the data structure is added
	* Any data that already exists is updated.
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
			
							this.process.set(i, process);
							this.user.set(i, user);
							this.cpu.set(i, cpu);
							this.memory.set(i, memory);
							this.descr.set(i, descr);
					
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
					this.cpu.add(cpu);
					this.memory.add(memory);
					this.descr.add(descr);
			
					process_exists = true;
			
					size++;
				}
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
	* Displays Process information
	* Displays process name, cpu, memory, user name, and process description
	* on to standard out
	*
	*/
	@Override
	public void display(){

		try{
		
			if(Debug.getDebugValue() == 1){
		
				System.out.println("\nProcesses");
				for(int i=0;i<process.size();i++){
					System.out.println("Process Name: " + process.get(i) + "\t\tCPU: " + cpu.get(i) + "\t\tMemory: " +  memory.get(i) + "\t\tUser: " + user.get(i) + "\t\tDescription: " + descr.get(i));
				}
			
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
	* Returns the process record stored in the record data structure
	*
	* @return String
	*
	*/
	public String toString(){
		String processRecord = "";
		
		for(int i=0;i<process.size();i++){
			processRecord = processRecord + process.get(i) + " " + cpu.get(i) +  " " + memory.get(i) + " " + user.get(i) + " " + descr.get(i) + "\n";
		}
		
		return processRecord;
	
	}
}
