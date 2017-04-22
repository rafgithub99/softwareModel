package dressingRoomTest;

//Customer with a run method that inserts customers into dressingRooms
import java.util.Random;

public class Customer implements Runnable {
	
	private final static Random generator = new Random();
	private final DressingRooms dressingRoom;
	
	int numberOfitems = generator.nextInt(5) +1; //random No of items, 6 MAX
	int timePerItem = generator.nextInt(30000) +1;//random time, 3min MAX
	int totalTime = numberOfitems * timePerItem;//total time spent in room
	
	//constructor
	public Customer (DressingRooms room){
		dressingRoom = room;
	}//end constructor
	
	//request a room in DressingRooms class
	public void run(){
				
		try {
			dressingRoom.requestRoom(numberOfitems);//room request
			Thread.sleep(totalTime);//time between in and out of room
			dressingRoom.vacateRoom();//leave dressing room
			
		} catch(InterruptedException ex){
			ex.printStackTrace();
		}//end try/catch
		
	}//end run
	
	public int NumberOfitems(){
		int items = this.numberOfitems;
		return items;
	}//end NumberOfitems
	
	public int TimePerItem(){
		int itemTime = this.timePerItem;
		return itemTime;
	}//end NumberOfitems
	
	public int TotalTime(){
		int time = this.totalTime;
		return time;
	}//end NumberOfitems

}//end class Customer
