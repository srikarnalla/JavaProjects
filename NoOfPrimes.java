package threads;

public class Primes extends Thread{
    private int start, end;
    private SharedData sdRef;

    public Primes(int start,int end,SharedData sd){
        this.start = start;
        this.end = end;
        this.sdRef=sd;
    }

    public void display() {
        sdRef.display();
    }

    public void run(){

        for (int i=start; i<=end; i++){
            int counter = 0;
            for (int j=1;j<=i;j++){
                if (i%j == 0 ){
                    counter++;
                }
            }
            if (counter == 2){
                sdRef.increment();
            }
        }
    }

    public static void main(String[] args){
        SharedData sd = new SharedData(0);
        Primes p1 = new Primes(1,25000,sd);
        Primes p2 = new Primes(25001,50000,sd);
        Primes p3 = new Primes(50001,75000,sd);
        Primes p4 = new Primes(75001,100000,sd);
        p1.start();
        p2.start();
        p3.start();
        p4.start();

        try {
            p1.join();
            p2.join();
            p3.join();
            p4.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sd.display();
    }
}

package threads;

public class SharedData {
    int counter;
    public SharedData(int counter){
        this.counter = counter;
    }
    public synchronized void display(){
        System.out.println("No of Primes are "+counter);
    }
    public synchronized void increment(){
        counter++;
    }
}
