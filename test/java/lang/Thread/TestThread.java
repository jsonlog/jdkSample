import org.junit.Test;

public class TestThread {
    public static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running.");
        }
    }

    @Test
    public void testThread(String[] args) {
        Thread thread = new MyThread("myThread");

        System.out.println(Thread.currentThread().getName()+" call mythread.run()");
        thread.run();//不会启动新线程,可以重复调用

        System.out.println(Thread.currentThread().getName()+" call mythread.start()");
        thread.start();//启动一个新的线程，新线程调用 run 方法
    }
}
