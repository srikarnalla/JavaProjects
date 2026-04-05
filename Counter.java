package threads;

public class Money extends Thread{
    private int id;
    private BankAccount balnc;

    public Money(int id, BankAccount b){
        this.id=id;
        this.balnc=b;
    }

    public void display(){
        balnc.display();
    }

    public void run(){
        for (int i =0; i<1000; i++){
            if (id==1){
                balnc.withdraw();
            }
            else{
                balnc.deposit();
            }
        }
    }

    public static void main(String[] args){
        BankAccount b = new BankAccount(0);
        Money t1 = new Money(1,b);
        Money t2 = new Money(2,b);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        b.display();
    }
}

import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int balance=0;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(int balance){
        this.balance=balance;
    }

    public void deposit() {
        lock.lock();
        try {
            balance++;
        } finally {
            lock.unlock();
        }

    }

    public void withdraw() {
        lock.lock();
        try {
            balance--;
        } finally {
            lock.unlock();
        }
    }

    public void display() {
        System.out.println("Balance = " + balance);
    }


}
