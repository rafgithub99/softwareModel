package dressingRoomTest;

//customers as threads manipulating the dressingRooms class
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Scanner;

public class Scenario {
	
	private final static Random generator = new Random();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//create new thread pool
		ExecutorService application = Executors.newCachedThreadPool();
		
		//create new scanner
		Scanner sc = new Scanner(System.in);
		System.out.println("Dressing Rooms Scenario by Rafael v1.0");
		System.out.println("-----------------------------------------");
		System.out.println("Enter the number of dressing rooms to test: ");
		int numberOfRooms = sc.nextInt();
		System.out.println("Enter the number of customers to test: ");
		int numberOfCustomers = sc.nextInt();
		long startTime = System.currentTimeMillis();//start scenario time
		System.out.println();
		System.out.println("Customer Actions");
		System.out.println("-----------------------------------------");
		sc.close();
		
		//create dressingRooms and customers
		DressingRooms dressingRoom = new DressingRooms(numberOfRooms);
		int items = 0;
		int time = 0;
		int totalCustomers = numberOfCustomers;
		while (numberOfCustomers > 0){//loop thru the number of customers
			try{
				Customer customer = new Customer(dressingRoom);
				application.execute(customer); //customer instance created
				items += customer.NumberOfitems();//add items count
				time += customer.TotalTime();//add time in room count
				
				//random time between customer arrivals
				Thread.sleep(generator.nextInt(30000));
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}//end try/catch
			
			numberOfCustomers--;//decrement customer count
		}//end while loop
		
		application.shutdown();
		try{
			application.awaitTermination(5, TimeUnit.MINUTES);//wait till threads finish or timeout
			int avgItems = items/totalCustomers;
			double avgTime = time/totalCustomers;
			long avgWait = dressingRoom.averageWait();
			System.out.println("-----------------------------------------");
			System.out.println("Total number of customers: " +totalCustomers);
			System.out.println("Average number of items per customer: " +avgItems);
			System.out.println("Average dressing room time per customer: "+avgTime/1000+" seconds.");	
			System.out.println("The average wait time per customer was: "+avgWait/1000+" seconds.");
		} catch (InterruptedException ex){
			ex.printStackTrace();
		}//end try/catch
		
		long endTime = System.currentTimeMillis();//end scenario time
		System.out.println("The whole scenario took "+((endTime - startTime)/1000) + " seconds");
		
	}//end main method
}//end class Scenario
