package com.jizehan.algorithms;

import java.util.*;

/**
 * 描述
 * 设计LRU缓存结构，该结构在构造时确定大小，假设大小为K，并有如下两个功能
 * set(key, value)：将记录(key, value)插入该结构
 * get(key)：返回key对应的value值
 * [要求]
 * set和get方法的时间复杂度为O(1)
 * 某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的。
 * 当缓存的大小超过K时，移除最不经常使用的记录，即set或get最久远的。
 * 若opt=1，接下来两个整数x, y，表示set(x, y)
 * 若opt=2，接下来一个整数x，表示get(x)，若x未出现过或已被移除，则返回-1
 * 对于每个操作2，输出一个答案
 *
 *
 * 方法：HashMap+双向链表
 * 解题思路：先通过遍历operators数组，将set和get操作区分开。利用HashMap存储双向链表中节点的key和节点的映射<key,Node>，其中Node为双向链表的节点<key,value>
 * get操作：先判断map中是否存在key，如果map不存在key 则返回-1；如果map存在key，先将该key的结点删除，然后再将此节点插入到链表的表头。
 * set操作：先判断map中是否存在key， 如果已经存在key，将val更新，并删除这个节点，再将node插入到表头，如果不存在key，先判断是否超出空间，如果超出先在链表和map删除最后一个节点，
 * 再将节点插入到表头，并将对应的映射添加到map中。
 */

public class Solution1 {
    public static void main(String[] args) {

    }

    public class Node{
        private int val;
        private int key;
        private Node pre =null;
        private Node next =null;
        private Node(int key,int val){
            this.val=val;
            this.key=key;
        }
    }

    private HashMap<Integer,Node> map = new HashMap();
    private Node head = new Node(-1,-1);  //头节点
    private Node tail = new Node(-1,-1);  //尾节点
    private int k=0;

    /**
     * lru design
     * @param operators int整型二维数组 the ops
     * @param k int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU (int[][] operators, int k) {
        // write code here
        this.k = k;
        head.next = tail;
        tail.pre = head;

        int len = (int)Arrays.stream(operators).filter(x->x[0]==2).count();  //获取数组中开头为2（get操作）的元素个数
        int res[] = new int[len];

        for(int i=0,j=0;i<operators.length;i++){
            if(operators[i][0] == 1){   //获取数组中开头为1（set操作）的元素个数
                set(operators[i][1],operators[i][2]);  //set(key,val)
            }else{   //获取数组中开头为2（get操作）的元素个数
                res[j++] = get(operators[i][1]);  //get(key)
            }
        }
        return res;
    }

    public void set(int key,int val){
        //判断是否存在key
        Node node = null;
        if(map.containsKey(key)){   //如果已经存在key，将val更新，并删除这个节点，再将node插入到表头
            node = map.get(key);
            node.val = val;
            //删除该结点
            node.next.pre = node.pre;
            node.pre.next = node.next;
            //将node节点提到第一个
            moveToFirst(node);
        }else{  //如果不存在key，先判断是否超出空间，如果超出先在链表和map删除最后一个节点，再将节点插入到表头，并将对应的映射添加到map中
            if(map.size()==k){
                //在map中删除映射到最后一个节点的key
                int keyremove =tail.pre.key;
                map.remove(keyremove);
                //在链表中删除最后一个节点
                tail.pre.pre.next = tail;
                tail.pre = tail.pre.pre;
            }
            node = new Node(key,val);
            //在map中添加对新节点的映射
            map.put(key,node);
            //将节点插入到表头
            moveToFirst(node);

        }
    }

    public int get(int key){
        // 如果不存在key 则返回-1
        // 如果存在key，先将该key的结点删除，然后再将此节点插入到链表的表头
        if(!map.containsKey(key)){
            return -1;
        }else{
            Node node = map.get(key);
            //删除该结点
            node.next.pre = node.pre;
            node.pre.next = node.next;
            //插入到表头
            moveToFirst(node);
            return node.val;
        }
    }
    //将节点插入到表头
    public void moveToFirst(Node node){
        head.next.pre =node;
        node.next =head.next;
        node.pre =head;
        head.next=node;
    }
}
