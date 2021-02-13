package Utils;

import enums.TestType;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2020-11-26
 */
public class EnumsTest {
    public static void main(String[] args) {
        String name = TestType.TEST_TYPE2.name();
        System.out.println(name);
        System.out.println(System.currentTimeMillis());

        for (TestType t: TestType.values()) {
            System.out.println(t.name());
        }
    }
}
