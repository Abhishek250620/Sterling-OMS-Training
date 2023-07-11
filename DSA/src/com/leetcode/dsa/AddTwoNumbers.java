package com.leetcode.dsa;


 class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode l3 = dummy;
         int carry =0;
         while(l1!=null || l2!=null){
            int sum =0;
            if(l1!=null){
                sum+=l1.val;
                l1=l1.next;
            }

            if(l2!=null){
                sum+=l2.val;
                l2=l2.next;
            }

            sum+=carry;
            carry=sum/10;
            ListNode node = new ListNode(sum%10);
            l3.next=node;
            l3=l3.next;
         }
         if(carry>0){
             ListNode node=new ListNode(carry);
             l3.next=node;
         }
        return dummy.next;
    }
}
