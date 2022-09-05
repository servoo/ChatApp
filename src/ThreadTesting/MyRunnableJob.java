package ThreadTesting;

public class MyRunnableJob implements Runnable {


    @Override
    public void run() {
        go();
    }

    private void go() {
        try {
            Thread.sleep(5000);
        } catch (Exception exception) {
            System.out.println(exception);
        }

        doMore();
    }

    private void doMore() {
        System.out.println("TOP OF THE STACK BRO");
    }
}

class ThreadWorkerMan {
    public static void main(String[] args) {
        Runnable runnable = new MyRunnableJob();
        Thread t = new Thread(runnable);
        t.start();

        System.out.println("I'm running in MAIN papa!");
    }
}
