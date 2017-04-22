package dressingRoomTest;

//Creating a synchronized buffer using an ArrayBlockingQueue.
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Date;

public class DressingRooms {
	
	private final ArrayBlockingQueue<Integer> buffer; //shared buffer
	long start;//wait timer variables
	long stop;
	long wait; //individual customer wait time
	long totalWait;
	int count = 0; //how many times wait occurs
	long waitAvg; //average wait time
	
	public DressingRooms(int rooms){
		buffer = new ArrayBlockingQueue<Integer>(rooms);
	}//end constructor
	
	public void requestRoom (int items) throws InterruptedException{
		//start a timer if buffer is full (no dressing room avail)
		if (buffer.remainingCapacity() == 0 && start == 0){
			Date startTime = new Date();
			start = startTime.getTime();
			System.out.println("Person arrives, no dressing room available, starting timer");
			buffer.put(items); //still try to place a value in buffer so thread will go into queue
			System.out.printf("%s%2d\t %s%d\n", "Person enters with: ", items, 
					"Dressing rooms occupied: ", buffer.size());
		} else {
			buffer.put(items); //places a value in buffer
			System.out.printf("%s%2d\t %s%d\n", "Person enters with: ", items, 
					"Dressing rooms occupied: ", buffer.size());
		} //end if/else 
	}//end method set
	
	public void vacateRoom() throws InterruptedException {
		//if buffer was full stop timer and print wait time
		if (buffer.remainingCapacity() == 0 && start>0){
			Date stopTime = new Date();
			stop = stopTime.getTime();
			wait = stop - start;
			totalWait += wait;
			count ++;
			start = 0; //reset timer
			System.out.println("Dressing room now available, wait time was " +wait/1000+ " seconds");
			int readItems = buffer.take(); //then retrieve value from buffer
			System.out.printf("%s%2d\t %s%d\n", "Person leaves with: ", readItems, 
					"Dressing rooms occupied: ", buffer.size());
		} else {
			int readItems = buffer.take(); //retrieve value from buffer
			System.out.printf("%s%2d\t %s%d\n", "Person leaves with: ", readItems, 
					"Dressing rooms occupied: ", buffer.size());
		}//end if/else
	}//end method get
	
	public long averageWait(){
		waitAvg = totalWait/count;
		return waitAvg;
	}//end averageWait

}//end class DressingRooms
