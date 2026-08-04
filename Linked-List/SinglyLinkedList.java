class Node {
    int data;
    Node next;

    Node(int d1) {
        this.data = d1;
        this.next = null;
    }

    public static Node createLL(int[] arr) {
        if (arr.length == 0)
            return null;

        Node head = new Node(arr[0]);
        Node current = head;

        for (int i = 1; i < arr.length; i++) {
            Node temp = new Node(arr[i]);
            current.next = temp;
            current = temp;
        }

        return head;
    }

    public static void printLL(Node head) {
        Node temp = head;

        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
        System.out.println();
    }

    public static Node insertAtKthPosition(Node head, int ele, int k) {
        Node newNode = new Node(ele);

        if (head == null)
            return newNode;

        if (k == 1) {
            newNode.next = head;
            return newNode;
        }

        Node temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            if (count == k - 1) {
                newNode.next = temp.next;
                temp.next = newNode;
            }
            temp = temp.next;
        }
        return head;
    }
}

public class SinglyLinkedList {
    public static void main(String args[]) {
        int[] arr = { 1, 2, 4, 5, 6, 7 };

        Node head = Node.createLL(arr);

        int ele = 19;
        int k = 1;

        head = Node.insertAtKthPosition(head, ele, k);

        Node.printLL(head);

    }
}
