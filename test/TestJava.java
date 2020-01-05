import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

//import org.junit.Test;

/**
 * @Author jsonlog
 * @Date 2019-12-09
 */
public class TestJava {
    /*
    The test fixture is everything we need to have in place to exercise the SUT:
    @beforeSuite （测试套件执行前执行一次，可以包括N个Java包，N个java类）
    @beforeTest （介于测试套件suite和测试类之间的级别）
    @beforeClass （测试类中的所有方法执行之前执行一次）
    @beforeMethod （测试类中每个测试方法执行之前执行一次）
    @Test(groups ="fast",dependsOnGroups="pass") （测试类）
    @afterMethod（测试类中每个测试方法执行后执行一次）
    @afterClass （测试类中所有方法执行后执行一次）
    @afterTest （同beforTest）
    @afterSuite （测试套件执行后执行一次，同beforeSuite）
    @Listeners 定义一个测试类的监听器。
    @Parameters 介绍如何将参数传递给@Test方法。
    @BeforeGroups	在调用属于该组的所有测试方法之前运行。
    @AfterGroups	在调用属于该组的所有测试方法之后运行。
     */
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
        Class<?> clazz = Class.forName("User");//test.
        User user = (User) clazz.newInstance();
        System.out.println("user = " + user);
    }

    @Test
    public void testClazz() throws Exception {
        Class<?> clazz1 = Class.forName("User");
        Class clazz2 = User.class;
        Class clazz3 = new User().getClass();
        System.out.println("clazz1: " + clazz1);
        System.out.println("clazz2: " + clazz2);
        System.out.println("clazz3: " + clazz3);
    }

    @Test
    @SuppressWarnings("all")
    public void testConstructor() throws Exception {
        Class<?> clazz = Class.forName("User");
        Constructor<?> constructor = clazz.getDeclaredConstructor();//null
        Object object1 = constructor.newInstance();
        System.out.println(object1);

        Constructor<?> constructor2 = clazz.getDeclaredConstructor(new Class[] {int.class, String.class});
        Object object2 = constructor2.newInstance(1, "123456");
        System.out.println(object2);
    }

    @Test
    public void testMethod() throws Exception {
        Class<?> clazz = Class.forName("User");

        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method printInfo = clazz.getDeclaredMethod("printInfo", new Class[]{});
        for (Method method : declaredMethods) {
            System.out.println(method);
            if(printInfo.equals(method)){
//            if(method.getName().equals("printInfo")){
                User user = (User) clazz.newInstance();
                printInfo.invoke(user);//args:null
            }
        }
        System.out.println();
    }

    @Test
    public void testField() throws Exception {
        Class<?> clazz = Class.forName("User");

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
