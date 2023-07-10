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
	
	public void display() {
		if(head==null) {
			System.out.println("List is empty!!!!");
		}else {
			Node temp = head;
			while(temp!=null) {
				System.out.print(temp.value+" ");
				temp=temp.next;
			}
		}
	}
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.insert(12);
		list.insert(23);
		list.insert(13);
		list.insert(14);
		list.display();
	}
}
