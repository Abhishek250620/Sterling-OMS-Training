package com.linear.dsa;

class Node{
	int value;
	Node next;

	public Node(int value) {
		this.value=value;
		this.next=null;
	}
}

public class LinkedList {
	Node head;

	public LinkedList() {
		this.head=null;
	}

	public void insert(int value) {
		Node newNode = new Node(value);

		if(head == null) {
			head = newNode;
		}
		else {
			Node temp = head;

			while(temp.next!=null) {
				temp=temp.next;
			}

			temp.next = newNode;
		}
	}
	
	public void insertFirst(int value) {
		Node newNode = new Node(value);
		
		newNode.next=head;
		head=newNode;
	}
	
	public void display() {
		if(head==null) {
			System.out.println("List is empty!!!!");
		}else {
			Node temp = head;
			while(temp!=null) {
				System.out.print(temp.value+" ");
				temp=temp.next;
			}
			System.out.println();
		}
	}
	
	public int size() {
		int count =0;
		if(head == null) {
			return count;
		}else {
			Node temp = head;
			while(temp!=null) {
				temp=temp.next;
				count++;
			}
			return count;
		}
	}
	
	public void insertMiddle(int index,int value) {
		if(index>size() || index<0) {
			System.out.println("Invalid Index....");
		}
		else if(index==0) {
			insertFirst(value);
		}
		else if(index==size()) {
			insert(value);
		}
		else {
			int count=0;
			Node temp = head;
			Node newNode = new Node(value);
			while(count<index-1) {
				count++;
				temp=temp.next;
			}
			newNode.next=temp.next;
			temp.next=newNode;
		}
	}
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.insert(12);
		list.insert(23);
		list.insert(13);
		list.insert(14);
		list.insertFirst(10);
		list.insertMiddle(4, 244);
		list.display();
		System.out.println("Size of list: "+list.size());
	}
}
