import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author Joshua Reno
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial length of {@code INITIAL_CAPACITY} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Cannot add null data to MaxHeap");
        } else {
            if (size + 1 == backingArray.length) {
                regrow();
            }
            // remember index 0 is empty
            if (size == 0) {
                backingArray[1] = item;
                size++;
            } else {
                backingArray[size + 1] = item;
                heapifyUp(size + 1);
                size++;
            }
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        } else if (size == 1) {
            T data = backingArray[1];
            size--;
            backingArray[1] = null;
            return data;
        } else {
            T data = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            heapifyDown(1);
            size--;
            return data;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }


    /**
     * Regrows the backingArray to size 2n+1 where n is the size of the
     * current backingArray
     * Runs in O(n)
     */
    private void regrow() {
        T[] tempArray = (T[]) new Comparable[2 * backingArray.length + 1];
        for (int i = 0; i < backingArray.length; i++) {
            tempArray[i] = backingArray[i];
        }
        backingArray = tempArray;
    }

    /**
     * eapify's the backingArray and gives the Heap the
     * appropriate structure
     *
     * @param index the index of the added element
     */
    private void heapifyUp(int index) {
        if (index != 1
                && backingArray[index].compareTo(
                backingArray[parent(index)]) > 0) {
            T indexValue = backingArray[index];
            T parentValue = backingArray[parent(index)];
            backingArray[index] = parentValue;
            backingArray[parent(index)] = indexValue;
            heapifyUp(parent(index));
        }
    }

    /**
     * Heapify's the backingArray and gives the Heap the
     * appropriate structure
     *
     * @param index the index of the first element (1)
     */
    private void heapifyDown(int index) {
        int num;
        int left = leftChild(index);
        int right = rightChild(index);
        if (left < backingArray.length
                && index < backingArray.length
                && backingArray[index] != null
                && backingArray[left] != null
                && backingArray[right] != null
                && (backingArray[index].compareTo(backingArray[left]) < 0)
                && backingArray[left].compareTo(backingArray[right]) > 0) {
            num = left;
            T numData = backingArray[num];
            T indexData = backingArray[index];
            backingArray[num] = indexData;
            backingArray[index] = numData;
            heapifyDown(num);
        } else if (right < backingArray.length
                && index < backingArray.length
                && backingArray[index] != null
                && backingArray[left] != null
                && backingArray[right] != null
                && (backingArray[index].compareTo(backingArray[right]) < 0)
                && backingArray[left].compareTo(backingArray[right]) < 0) {
            num = right;
            T numData = backingArray[num];
            T indexData = backingArray[index];
            backingArray[num] = indexData;
            backingArray[index] = numData;
            heapifyDown(num);
        } else if (left < backingArray.length
                && backingArray[index] != null
                && backingArray[left] != null
                && backingArray[index].compareTo(backingArray[left]) < 0) {
            num = left;
            T numData = backingArray[num];
            T indexData = backingArray[index];
            backingArray[num] = indexData;
            backingArray[index] = numData;
            heapifyDown(num);
        }
    }

    /**
     *
     * @param index any index
     * @return the parent (node) index of the parameter index
     */
    private int parent(int index) {
        return (index / 2);
    }

    /**
     *
     * @param index a parent (node) index in the backingArray
     * @return the index of the left child (node)
     */
    private int leftChild(int index) {
        return 2 * index;
    }

    /**
     *
     * @param index a parent (node) index in the backingArray
     * @return the index of the right child (node)
     */
    private int rightChild(int index) {
        return 2 * index + 1;
    }

}
