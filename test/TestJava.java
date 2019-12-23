import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import test.User;

/**
 * @Author jsonlog
 * @Date 2019-12-09
 */
public class TestJava {
    //javaFX
    @Test
    public void testThrowable() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
    @Test
    public void testFloat() {
        int i = Float.floatToIntBits(2.25f);
        System.out.println(Integer.toBinaryString(i));
        long d = Double.doubleToLongBits(2.25d);
        System.out.println(Long.toBinaryString(d));
        /*
        float：
        1bit（符号位） 8bits（指数位） 23bits（尾数位)
        double：
        1bit（符号位） 11bits（指数位） 52bits（尾数位）

        float的偏移量为2^8 - 1，double的偏移量为2^11 - 1；

        符号位为：0
        指数为1，用补码表示 0000 0001，转为移码就是1000 0001。
        尾数位为0010 0000 0000 0000 0000 000
         */
    }
    //JCF Java Collections Framework

    @Test
    public void testJCF(){
        //visit a list with iterator
        ArrayList<String> list = new ArrayList<String>();
        list.add(new String("Monday"));
        list.add(new String("Tuesday"));
        list.add(new String("Wensday"));
        Iterator<String> it = list.iterator();//得到迭代器
        while(it.hasNext()){
            String weekday = it.next();//访问元素
            System.out.println(weekday.toUpperCase());
        }

        //for
        for(String weekday : list){//enhanced for statement
            System.out.println(weekday.toUpperCase());
        }
    }

    @Test
    public void testReflection() throws Exception {
        Class<?> clazz = Class.forName("test.User");
        User user = (User) clazz.newInstance();
        System.out.println("user = " + user);
    }

    @Test
    public void testClazz() throws Exception {
        Class<?> clazz1 = Class.forName("test.User");
        Class clazz2 = User.class;
        Class clazz3 = new User().getClass();
        System.out.println("clazz1: " + clazz1);
        System.out.println("clazz2: " + clazz2);
        System.out.println("clazz3: " + clazz3);
    }

    @Test
    public void testConstructor() throws Exception {
        Class<?> clazz = Class.forName("test.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(null);
        Object object1 = constructor.newInstance();
        System.out.println(object1);

        Constructor<?> constructor2 = clazz.getDeclaredConstructor(new Class[] {int.class, String.class});
        Object object2 = constructor2.newInstance(1, "123456");
        System.out.println(object2);
    }

    @Test
    public void testMethod() throws Exception {
        Class<?> clazz = Class.forName("test.User");

        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method printInfo = clazz.getDeclaredMethod("printInfo", new Class[]{});
        for (Method method : declaredMethods) {
            System.out.println(method);
            if(printInfo.equals(method)){
//            if(method.getName().equals("printInfo")){
                User user = (User) clazz.newInstance();
                printInfo.invoke(user, null);
            }
        }
        System.out.println();
    }

    @Test
    public void testField() throws Exception {
        Class<?> clazz = Class.forName("test.User");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        // 创建并通过反射，修改一个 private 变量 id
        User user = (User) clazz.newInstance();
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(user, 123);
        user.printInfo();
    }


    class MyThreadPool extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running... ");
        }
    }

    @Test
    public void testThreadPoolExecutor() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(2);
            pool.execute(new MyThreadPool());
        }
        pool.shutdown();
    }

}
