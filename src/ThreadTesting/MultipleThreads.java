package ThreadTesting;

public class MultipleThreads implements Runnable{

    public static void main (String [] args) {
        MultipleThreads runnable = new MultipleThreads();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.setName("T1");
        t2.setName("T2");

        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName() + " is running.");
        }
    }
}
