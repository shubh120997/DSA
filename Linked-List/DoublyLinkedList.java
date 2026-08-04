class Node {
    int data;
    Node next;
    Node prev;

    Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    Node(int data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public static Node createDLL(int[] arr) {
        if (arr.length == 0)
            return null;

        Node head = new Node(arr[0]);
        Node current = head;

        for (int i = 1; i < arr.length; i++) {
            Node temp = new Node(arr[i], null, current);

            current.next = temp;
            current = temp;
        }

        return head;
    }

    public static void printDLL(Node head) {
        Node temp = head;

        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public static Node deleteHead(Node head) {
        if (head == null || head.next == null)
            return null;

        Node newHead = head.next;

        newHead.prev = null;

        head.next = null;

        return newHead;
    }

    public static Node deleteTail(Node head) {
        if (head == null || head.next == null)
            return null;

        // Iterate to last node
        Node currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }

        // Second Last Node
        Node secondLastNode = currNode.prev;

        // Remove previous node address from current node
        currNode.prev = null;
        // Remove next node address from the second last node
        secondLastNode.next = null;

        return head;
    }

    public static Node deleteKthNode(Node head, int k) {
        if (head == null || head.next == null)
            return null;

        Node currNode = head;
        if (k == 1) {
            head = currNode.next;
            head.prev = null;

            currNode.next = null;
        } else {
            int nodeCount = 1;

            while (nodeCount != k) {
                nodeCount++;
                currNode = currNode.next;
            }

            Node prevNode = currNode.prev;
            Node nextNode = currNode.next;

            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            currNode.prev = null;
            currNode.next = null;
        }

        return head;
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

public class DoublyLinkedList {
    public static void main(String[] args) {
        int[] arr = {
                1,
                2,
                4,
                5,
                6,
                7
        };

        Node head = Node.createDLL(arr);

        // head = Node.deleteHead(head);
        // System.out.print("After deleting head: ");

        // head = Node.deleteTail(head);
        // System.out.print("After deleting tail: ");

        head = Node.deleteKthNode(head, 4);
        System.out.print("After deleting Kth Node: ");
        Node.printDLL(head);
    }
}
