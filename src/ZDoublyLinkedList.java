import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Data Structure: Doubly Linked List
 * File: ZSinglyLinkedList.java
 * Author: Zack Sai
 *
 * This file demonstrates the Singly Linked List data structure
 * Best Case Efficiency:
 *      get: O(1) – get at front
 *      set: O(1) – set at front
 *      remove: O(1) – remove at front
 *
 * Worst Case Efficiency:
 *      get: O(n) – retrieves at position passed (n)
 *      set: O(n) – stores at position passed (n)
 *      remove: O(n) – removes from position passed (n)
 *
 * Reallocate() method is used to expand the array when it is full and an
 * element must be added
 *
 * @param <E> generic data type of the list created
 */
public class ZDoublyLinkedList<E> {

    // Properties: a doubly-linked list class has a head node (head), tail node (tail), a size (currentSize)
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;


    /**
     * ZListIterator class implements the methods of the list through the use of a ListIterator
     */
    private class ZListIterator implements ListIterator<E> {

        // Properties: next item in list (nextItem), most recently accessed (lastItemReturned), position (index)
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index = 0;

        /**
         * constructor for iterator initializes an iterator at a given position in a list
         * First ensures valid position, then
         * @param i
         */
        public ZListIterator(int i){

            // Handle case: bad position
            if (i < 0 || i > size){
                throw new IndexOutOfBoundsException("Invalid index of " + i);
            }

            // Handle case: last item
            if (i==size){
                index = size;
                nextItem = null;
            } else {
                // Default: construct list from the beginning
                nextItem = head;
                for (index = 0; index < i; index++){
                    nextItem = nextItem.nextNode;
                }
            }

            // new object so no item has been returned yet
            lastItemReturned = null;

        }

        /**
         * Check if end of list has been reached
         * @return true if next item in list isn't null
         */
        @Override
        public boolean hasNext() {
            return nextItem!= null;
        }

        /**
         * return the next item and increment the iterator
         * @return the next item in the list
         */
        @Override
        public E next() {
            // First check if list has next item
            if (!hasNext()) throw new NoSuchElementException();

            // Update lastItemReturned to the current node, increment the node and the position, and return the entry
            lastItemReturned = nextItem;
            nextItem = nextItem.nextNode;
            index++;
            return lastItemReturned.entry;
        }

        /**
         * Check if beginning of list has been reached
         * Determine if the current node has a previous, but first check if end of list has been reached
         * @return true if current node has a previous nonnull node
         */
        @Override
        public boolean hasPrevious() {

            // Check if current node is at the end of a non-empty list or if it has a previous node
            return (nextItem == null && size != 0) || nextItem.prevNode != null;
        }

        /**
         * return the previous item and decrement the iterator
         * @return the previous item in the list
         */
        @Override
        public E previous() {
            // First check if list has previous item
            if (!hasPrevious()) throw new NoSuchElementException();

            // Handle case: end of list has been reached (go back one position to the tail)
            if (nextItem == null) nextItem = tail;

            // Otherwise, decrement position by setting current item to the one before it
            else nextItem = nextItem.prevNode;

            // Update lastItemReturned to the current node, decrement the position, and return the entry
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.entry;
        }


        // Required methods for implementation of ListIterator
        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        /**
         * add method adds a new node before the current node and links the surrounding nodes appropriately
         * Handle cases: adding at end, beginning, or middle of the list
         * @param entryValue is the value of the new node to be added
         */
        @Override
        public void add(E entryValue) {

            if (head == null){  // Handle case: empty list

                // Initialize head and tail node to the value passed
                head = new Node<>(entryValue);
                tail = head;
            } else if (nextItem == null){   // Handle case: end of list

                // Make a new node
                Node<E> newNode = new Node<>(entryValue);

                // Link the new node to the tail, THEN update tail node to the new node
                tail.nextNode = newNode;
                newNode.prevNode = tail;
                tail = newNode;
            } else { // Default case: insertion anywhere else

                // Make a new node
                Node<E> newNode = new Node<>(entryValue);

                // Link the new node's prev node to the current node's prev node
                newNode.prevNode = nextItem.prevNode;

                // Link the current node's prev node to the new node
                nextItem.prevNode.nextNode = newNode;

                // Link the new node's next item to the current node
                newNode.nextNode = nextItem;

                // Link the current node's previous node to the new node
                nextItem.prevNode = newNode;
            }

            // Finally, increment size and position, and reset last item returned
            size++;
            index++;
            lastItemReturned = null;

        }
    }   // End inner class ZListIterator


    /**
     * Inner class node is the backbone of the Doubly Linked List data structure
     * A node contains a piece of data (Entry) and a link to the next node (nextNode) and previous node (prevNode)
     * A node must contain an entry and can be initialized with the constructors below
     *
     * @param <E> generic data type the node will contain
     */
    private static class Node<E> {

        // Properties: a node contains an entry and a link to the next entry
        private E entry;
        private Node<E> nextNode;
        private Node<E> prevNode;

        // Behaviors:
        /**
         * 1-param constructor initializes a node to a passed entry value
         * Links this entry to a null next entry
         *
         * @param entryValue
         */
        private Node(E entryValue) {
            entry = entryValue;
            nextNode = null;
            prevNode = null;
        }

        /**
         * 2-param constructor initializes a node with an entry value
         * Links this entry to the given linkedNode
         *
         * @param entryValue
         */
        private Node(E entryValue, Node<E> newNextNode) {
            entry = entryValue;
            nextNode = newNextNode;
        }

    }   // End inner class Node, Resume ZSingleLinkedList class:
}
