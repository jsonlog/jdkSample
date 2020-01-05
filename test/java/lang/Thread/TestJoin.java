import org.testng.annotations.Test;

public class TestJoin {
    public class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread start ...");
            for (int i = 0; i < 1000000; i++) {

            }
            System.out.println("MyThread end ...");
        }
    }

    @Test
    public void testJoin() throws InterruptedException {
        Thread thread = new MyThread();
        thread.start();
        thread.join();
        System.out.println("testJoin end ...");
    }
}
