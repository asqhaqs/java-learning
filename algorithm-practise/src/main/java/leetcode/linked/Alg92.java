package leetcode.linked;

public class Alg92 {

    // 思路：三指针，要区分从头开始翻转与从中间开始翻转的区别
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(n-m == 0){
            return head;
        }
        ListNode pre_head = new ListNode(1);
        pre_head.next = head;

        for(int i=1; i<m; i++){
            pre_head = pre_head.next;
        }

        ListNode pre = pre_head.next;
        ListNode cur = pre.next;
        ListNode third = cur.next;

        for(int i=0; i<n-m; i++){
            cur.next = pre;
            pre = cur;
            cur = third;
            if(third != null){
                third = third.next;
            }

        }
        pre_head.next.next = cur;
        pre_head.next = pre;

        if(m>1){   // 区别 直接从head开始翻转与从head之后的节点开始翻转
            return head;
        }else{
            return pre_head.next;
        }

    }


    public static void main(String[] args) {
        ListNode head = new ListNode(11);
        head.next  = new ListNode(22);
        Alg92 test = new Alg92();
        ListNode transforHead = test.reverseBetween(head, 1, 2);
        do{
            System.out.println(transforHead.val);
            transforHead = transforHead.next;
        }while (transforHead!= null);
    }

}
