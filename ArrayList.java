
// Imports
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom version ArrayList class modeled after default ArrayList class
 *
 * @author Frozonovic
 */
public class ArrayList<E> implements Iterable<E> {
    // Constants
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DOUBLE = 2;

    // Internal State
    private int _size = 0;
    private static int _capacity;
    private E[] _backingArray;

    // Constructors
    /**
     * Creates an instance of class ArrayList object with capacity of 10
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an instance of class ArrayList object
     *
     * @param initialCapacity Starting capacity for new ArrayList instance
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Error: Negative capacity is not allowed");
        } else if (initialCapacity == 0) {
            _capacity = DEFAULT_CAPACITY;
            _backingArray = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            _capacity = initialCapacity;
            _backingArray = (E[]) new Object[initialCapacity];
        }
    }

    // Instance Methods
    /**
     * Inserts an element at a designated index and shifts anything accordingly
     *
     * @param index   Index to insert element at
     * @param element Element to insert into backing array
     */
    public void add(int index, E element) {
        if (index < 0 || index > _size) {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        } else if (index == _size) {
            add(element);
        } else {
            grow();

            for (int i = _size; i > index; i--) {
                E elementToAdd = get(i - 1);
                _backingArray[i] = elementToAdd;
            }

            _backingArray[index] = element;
            _size++;
        }
    }

    /**
     * Appends an element to the backing array
     *
     * @param element Element to append to array
     * @return Boolean value based on success of method
     */
    public boolean add(E element) {
        grow();

        _backingArray[_size] = element;
        _size++;

        return true;
    }

    /**
     * Clears the backing array by nulling out each value and resets size to 0
     */
    public void clear() {
        for (int i = 0; i < _size; i++) {
            set(i, null);
        }

        _size = 0;
    }

    /**
     * Fetches the element at a given index
     *
     * @param index Index of element to fetch
     * @return The element at the given index
     */
    public E get(int index) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        } else {
            return _backingArray[index];
        }
    }

    /**
     * Fetches the index of the given element
     *
     * @param element Element to fetch index for
     * @return The index that the element first occurs at, else a negative integer
     */
    public int indexOf(E element) {
        int returnValue = -1;

        for (int i = 0; i < _size && returnValue == -1; i++) {
            if (element != null && element.equals(_backingArray[i])) {
                returnValue = i;
            } else if (element == _backingArray[i]) {
                returnValue = i;
            }
        }

        return returnValue;
    }

    /**
     * Determines if there is anything stored in the ArrayList
     *
     * @return True if anything exists in the ArrayList, else false
     */
    public boolean isEmpty() {
        return _size == 0;
    }

    /**
     * Removes the element at the given index
     *
     * @param index Index of element to remove
     * @return The value of the removed element
     */
    public E remove(int index) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        } else {
            E returnIndex = _backingArray[index];
            _size--;

            for (int i = 0; i < _size; i++) {
                set(i, _backingArray[i + 1]);
            }

            _backingArray[_size] = null;
            return returnIndex;
        }
    }

    /**
     * Updates an element in the ArrayList with a new element
     *
     * @param index   Index of element to replace
     * @param element New value to update the current element with
     * @return The value of the previous element occupying that index
     */
    public E set(int index, E element) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        } else {
            E returnElement = _backingArray[index];
            _backingArray[index] = element;

            return returnElement;
        }
    }

    /**
     * Fetches the current size of the ArrayList
     *
     * @return How many elements are in the current ArrayList
     */
    public int size() {
        return _size;
    }

    /**
     * Creates a new iterator
     *
     * @return An iterator of type E
     */
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Private inner-class that adds support for enhanced for-loop notation
     */
    private class ArrayListIterator implements Iterator<E> {
        // Instance Variables
        private int _index;

        // Constructor
        /**
         * Creates an instance of private inner-class ArrayListIterator object
         */
        public ArrayListIterator() {
            _index = 0;
        }

        // Instance Methods
        /**
         * Determines if there are more elements
         *
         * @return True if there is another element, else false
         */
        public boolean hasNext() {
            return _index < _size;
        }

        /**
         * Fetches the next element if it exists
         *
         * @return Next element if hasNext returns true, else throws exception
         */
        public E next() {
            if (hasNext()) {
                return get(_index++);
            } else {
                throw new NoSuchElementException("Error: Element not found");
            }
        }
    }

    // Private Methods
    /**
     * If size reaches ArrayList object capacity, double the capacity
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        if (_size == _capacity) {
            if (!(_size < Integer.MAX_VALUE / 2)) {
                throw new OutOfMemoryError("Error: Integer limit reached");
            }

            _capacity *= DOUBLE;
            E[] tempArray = (E[]) new Object[_capacity];

            for (int i = 0; i < _size; i++) {
                tempArray[i] = _backingArray[i];
            }

            _backingArray = tempArray;
        }
    }
}
