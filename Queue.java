import java.util.Iterator;

/**
 * User: Viraj Sinha (vsinha)
 * 11/7/13
 */
public class Queue<Item extends Weighted> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;
    private double totalWeight;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;

        totalWeight += item.weight();
    }

    public void delete(Item i) { //go through the linked list, delete the node specified
        Node sentinel = first;
        while (sentinel.next != null) { //while there are more nodes
            if (sentinel.next.item == i) { //if the next node is the one we want
                sentinel.next = sentinel.next.next; //dereference the next node and complete the LL around it
                return;
            }
            sentinel = sentinel.next; //go to the next node
        }
    }

    public double getWeight() {
        return totalWeight;
    }

    public Item getRelativeToTop(int num) { //return the first item in the queue
        Node current = first;
        for (int i = 0; i < num; i++) {
            if (current.next == null) { // if we can't go any further, just return what we have
                return current.item;    // this is better than returning null
            }
            current = current.next;
        }

        //System.out.println("returning " + num + " from the top: " + current.item);
        return current.item;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;

        if (isEmpty()) {
            last = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            dequeue();
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
