package current;

import java.util.Random;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * 简单实现单例
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2022-09-29
 */
public class SingletonTest {

    private static final Supplier<SingletonTest> heavySupplier =
            Suppliers.memoize(SingletonTest::new);

    private SingletonTest() {
        System.out.println("being created" + new Random().nextInt(9));
    }

    public static SingletonTest getSingleton() {
        return heavySupplier.get();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            SingletonTest heavyObject = SingletonTest.getSingleton();
            System.out.println(heavyObject);
        }
    }
}
