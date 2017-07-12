package org.eclipse.che.examples;

public class LinkedListATN {

    /**
     * Sub Class used to create a node, every object of this considered as node in the linkedList.
     */
    class Node {
        int  val;
        Node next;

        public Node(int val) {
            this.val = val;
            this.next = null;
        }

        public int getLength() {
            if (this.next == null)
                return 0;
            return this.next.getLength() + 1;
        }

        public String toString() {
            return "Node[val=" + val + " next: " + next + "]";
        }
    }

    /**
     * Add same size linked lists
     */
    private Node addSameSizeLinkedLists(Node head1, Node head2) {
        if (head1 == null && head2 == null)
            return null;
        addSameSizeLinkedLists(head1.next, head2.next);
        int sum = head1.val + head2.val + carry;
        carry = sum / 10;
        sum = sum % 10;
        return push(sum);
    }

    private Node addCarryToTheDiffHead(Node head) {
        if (head == null)
            return null;
        addCarryToTheDiffHead(head.next);
        head.val = head.val + carry;
        carry = head.val / 10;
        head.val = head.val % 10;
        return head;
    }

    /**
     * Construct the linked list, this size of the linked list is equal to the difference in length 
     * of the two linked lists.
     * Example : L1 = 1->2->3->4
     *           L2 = 5->6
     * Then this method will give you the list Result list : 1->2 only.
     */
    private Node constructTheListFromDifferenceOfTheLists(Node head1, Node head2) {
        int diff = Math.abs(head1.getLength() - head2.getLength());
        Node diffHead = null;
        Node secondNode = null;
        if (head1.getLength() > head2.getLength()) {
            diffHead = secondNode = new Node(head1.val);
            head1 = head1.next;
            while (--diff > 0) {
                secondNode.next = new Node(head1.val);
                head1 = head1.next;
                secondNode = secondNode.next;
            }
        }
        if (head1.getLength() < head2.getLength()) {
            diffHead = secondNode = new Node(head2.val);
            head2 = head2.next;
            while (--diff > 0) {
                secondNode.next = new Node(head2.val);
                head2 = head2.next;
                secondNode = secondNode.next;
            }
        }
        return diffHead;
    }

    /**
     * Add linked lists which are different size
     */
    private Node addDifferentSizeLinkedLists(Node head1, Node head2) {
       
        Node diffHead = constructTheListFromDifferenceOfTheLists(head1, head2);
        if (head1.getLength() > head2.getLength()) {
            head1 = getMyHeadPointerFromCorrectLocation(head1, head1.getLength() - head2.getLength());
        } else if (head1.getLength() < head2.getLength()) {
            head2 = getMyHeadPointerFromCorrectLocation(head2, head2.getLength() - head1.getLength());
        }
        addSameSizeLinkedLists(head1, head2);
        if (carry > 0) {
            addCarryToTheDiffHead(diffHead);
        }
        diffHead.next = result;
        result = diffHead;
        return null;
    }

    /**
     * This method moves the head pointer from the list which is greater than other
     * and it moves no of steps which is equal to the difference in length of the two 
     * lists.
     * Example : L1: 1->2->3->4 , L2: 4->5 
     * Returns Head pointer , pointing to node 3 from the list cd L1.
     */
    private Node getMyHeadPointerFromCorrectLocation(Node head, int diff) {
        while (diff > 0) {
            head = head.next;
            diff--;
        }
        return head;
    }

    /**
     * A delegate method who delegated adding linked lists.
     */
    private Node addLists(Node head1, Node head2) {
        if (head1 == null && head2 == null)
            return null;
        if (head1 == null)
            return result = head2;
        if (head2 == null)
            return result = head1;
        if (head1.getLength() == head2.getLength()) {
            return addSameSizeLinkedLists(head1, head2);
        } else {
            return addDifferentSizeLinkedLists(head1, head2);
        }
    }

    /**
     *  Create a node with the value and return the node
     */

    private Node push(int val) {
        Node node = new Node(val);
        if (result == null) {
            result = node;
            return result;
        } else {
            node.next = result;
            result = node;
            return node;
        }

    }

    /**
     * Create a  Single Linked list from the input array.
     */
    private Node createListFromArray(int[] arr) {
        Node head = null;
        Node secondNode = null;
        int i = 0;
        if (arr != null && arr.length > 0) {
            if (i == 0) {
                head = secondNode = new Node(arr[i]);
            }
            while (++i < arr.length) {
                Node node = new Node(arr[i]);
                secondNode.next = node;
                secondNode = node;
            }
        }
        return head;
    }

    /**
     * Print the given list from Head to Tail using recursion
     */
    private void printLinkedList(Node head) {
        if (head == null)
            return;
        System.out.print(head.val);
        if (head.next != null)
            System.out.print("->");
        printLinkedList(head.next);
    }


    Node head1, head2, result;
    int  carry;

    // Driver program to test above functions
    public static void main(String args[]) {
        LinkedListATN list = new LinkedListATN();
        list.head1 = null;
        list.head2 = null;
        list.carry = 0;
        int arr2[] = {1, 0, 2, 4};
        int arr1[] = {1, 8, 9};
        list.head1 = list.createListFromArray(arr1);
        list.head2 = list.createListFromArray(arr2);
        list.addLists(list.head1, list.head2);
        if (list.carry > 0) {
            list.push(list.carry);
        }
        list.printLinkedList(list.result);
    }
}
