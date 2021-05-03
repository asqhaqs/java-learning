package mockito;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2021-02-13
 */
@RunWith(MockitoJUnitRunner.class)d
public class MockListTest {
    @Test
    public void simpleTest() {
        //创建mock对象，参数可以是类，也可以是接口
        List<String> list = mock(List.class);
        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");
        String result = list.get(0);
        //验证方法调用(是否调用了get(0))
        verify(list).get(0);
        //junit测试
        Assert.assertEquals("helloworld", result);


        // 你可以mock具体的类型,不仅只是接口
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        // 测试桩
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        // 输出“first”
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        // 抛出异常
        // System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        // 因为get(999) 没有打桩，因此输出null
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
        //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
        // 验证get(0)被调用的次数
        verify(mockedList).get(0);
    }
}
