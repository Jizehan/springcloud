import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

/**
 * 输入一个链表，反转链表后，输出新链表的表头。
 * 输入：{1,2,3}
 * 输出：{3,2,1}
 */ class Solution {
    /**
     * 这个方法比较简洁，(是下面第二种方法的简单表达，其实一样)
     * @param head
     * @return
     */
    /*public static ListNode ReverseList(ListNode head){
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }*/

    /**
     * 此方案最是清晰明了！
     * @param head
     * @return
     */
    public static ListNode ReverseList(ListNode head) {
        ListNode newHead = null;
        ListNode currentHead = head;
        if(head == null || head.next == null){
            return head;
        }
        ListNode next = null;
        while(currentHead != null){
            next = currentHead.next;
            currentHead.next = newHead;
            newHead = currentHead;
            currentHead = next;
        }

        return newHead;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(12);

        ListNode listNode1 = ReverseList(listNode);
        System.out.println("!!!!!");
    }


    /**此方法存在问题，不可采取，投机取巧非正道，而且输入的数字大于一位数时结果不对！！！！垃圾！！！
     *
     * @param args
     * @throws IOException
     */
    /*public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (true){
            input = bufferedReader.readLine();
            if (null == input || input.length() == 0) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            System.out.println(sb.append("}").append(input.substring(1, input.length() - 1)).append("{").reverse());
        }
    }*/


}

