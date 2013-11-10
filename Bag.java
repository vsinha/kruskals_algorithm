import java.util.Iterator;

/**
 * User: Viraj Sinha (vsinha)
 * Date: 11/4/13
 */

public class Bag<Item> implements Iterable<Item> {
    private Node first;

    private class Node {
        Item item;
        Node next;
    }

    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public void delete(Item i) { //go through the linked list, delete the node specified
        Node sentinel = first;
        if (first.item == i) { //check the first item first
            //System.out.println("deleted first item");
            first = first.next;
        }
        while (sentinel.next != null) { //while there are more nodes
            //System.out.println("examining edge: " + sentinel.next.item + " to compare to " + i);
            if (sentinel.next.item == i) { //if the next node is the one we want
                sentinel.next = sentinel.next.next; //dereference the next node and complete the LL around it
                return; // we've done what we need to, so gtfo
            }
            sentinel = sentinel.next; //go to the next node
        }

        //return silently if we failed to delete any node, this is not a problem
        //System.out.println("Bag failed to delete anything");
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
