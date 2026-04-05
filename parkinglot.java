package threads;
import java.util.concurrent.locks.ReentrantLock;


package threads;

public class parking extends Thread{
    private int id;
    private ParkingLot pt;

    public parking(int id,ParkingLot pt){
        this.id=id;
        this.pt=pt;
    }

    public void run(){
        boolean parked = pt.enter(id);
        if (parked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pt.leave(id);
        }
    }

    public static void main(String[] args){
        ParkingLot pt = new ParkingLot(3);
        parking p1 = new parking(1,pt);
        parking p2 = new parking(2,pt);
        parking p3 = new parking(3,pt);
        parking p4 = new parking(4,pt);

        p1.start();
        p2.start();
        p3.start();
        p4.start();

        try{
            p1.join();
            p2.join();
            p3.join();
            p4.join();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}


public class ParkingLot {
    private int spots;
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingLot(int spots){
        this.spots = spots;
    }

    public boolean enter(int id){
        lock.lock();
        try{
            if (spots>0){
                spots--;
                System.out.println("Car " + id + " parked. Spots left: " + spots);
                return true;
            } else {
                System.out.println("Car " + id + " waiting...");
                return false;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void leave(int id) {
        lock.lock();
        try {
            spots++;
            System.out.println("Car " + id + " left. Spots left: " + spots);
        } finally {
            lock.unlock();
        }
    }

    public void display(){
        System.out.println(spots);
    }
}
