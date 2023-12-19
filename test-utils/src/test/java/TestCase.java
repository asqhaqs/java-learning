import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-11-20
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCase {
    @Test
    public void simpleTest() {
        LinkedList<String> list = mock(LinkedList.class);
        when(list.get(0)).thenReturn("hello world");
        String result = list.get(0);
        verify(list).get(0);
        Assert.assertEquals("hello world", result);
        // 测试桩
        when(list.get(0)).thenReturn("first");
        when(list.get(1)).thenThrow(new RuntimeException());

        System.out.println(list.get(0));
//        System.out.println(list.get(1));
        System.out.println(list.get(999));
        verify(list, times(2)).get(0);
        verify(list, atLeast(2)).get(0);
        verify(list, atMost(2)).get(0);
        verify(list, never()).get(3);

        // 为返回值为void的函数通过Stub抛出异常
        doThrow(new RuntimeException()).when(list).clear();
//        list.clear();

        // A. 验证mock一个对象的函数执行顺序
        List singleMock = mock(List.class);
        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");
        // 为该mock对象创建一个inOrder对象
        InOrder inOrder = Mockito.inOrder(singleMock);
        // 确保add函数首先执行的是add("was added first"),然后才是add("was added second")
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

    }
}
