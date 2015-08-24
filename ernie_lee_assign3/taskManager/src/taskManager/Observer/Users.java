package Observer;

import java.util.List;
import java.util.ArrayList;

import util.Debug;
import util.Display;

import Subject.Subject;

public class Users implements Observer, Display{

	private List<String> user;
	private List<String> status;
	
	private Subject systemData;
	
	/**
	* Constructor of the Users class
	* Initializes all data structures that stores the user name data,
	* and status data.
	*
	* The Users class is then added to the Observers to the Subject
	*
	* @param systemData
	*
	*/
	public Users(Subject systemData){
	
		user = new ArrayList<String>();
		status = new ArrayList<String>();
		
		this.systemData = systemData;
		systemData.registerObserver(this);
		
		Debug.dump(3, "Users Display");
	
	}
	
	/**
	* Updates the user name, and the user status.
	* And users not added to the data structure is added.
	* Any users that already exists has its status updated.
	*
	* @param process user cpu memory descr
	*
	*/
	@Override
	public void update(String process, String user, int cpu, int memory, String descr){
	
		try{
			if(cpu != -1 && memory != -1){
	
				String user_status[] = user.split("-");
				user = user_status[0];
	
				if(this.user.contains(user_status[0])){
					int i = this.user.indexOf(user_status[0]);
			
					this.user.set(i, user_status[0]);
					this.status.set(i, user_status[1]);
			
				}else{
					if(user_status.length == 2){
						this.user.add(user_status[0]);
						this.status.add(user_status[1]);
					}
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
	* Displays Users information
	* Displays user name, and the user status
	* on to standard out
	*
	*/
	@Override
	public void display(){
	
		try{
		
			if(Debug.getDebugValue() == 1){
				System.out.println("\nUsers");
				for(int i=0;i<user.size();i++){
					System.out.println("User: " + user.get(i) + "\t\tStatus: " + status.get(i));
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
	* Returns the user record stored in the record data structure
	*
	* @return String
	*
	*/
	public String toString(){
	
		String userRecord = "";
	
		for(int i=0;i<user.size();i++){
					userRecord = userRecord + "User: " + user.get(i) + " Status: " + status.get(i) + "\n";
		}
		
		return userRecord;
	
	}

}
