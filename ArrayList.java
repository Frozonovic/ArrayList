/**
 * A custom version ArrayList class modeled after default ArrayList class
 *
 * @param <E>
 */
public class ArrayList<E>
{
    // Constants
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_RETURN = -1;
    private static final int LOW_BOUND = 0;
    private static final int ITERATION = 1;
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
        // Quick Note: Primitive ints can't be initialized as null
        if (initialCapacity < LOW_BOUND) {
            throw new IllegalArgumentException("Error: Negative capacity is not allowed");
        } else if (initialCapacity == LOW_BOUND) {
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
     * @param index Index to insert element at
     * @param element Element to insert into backing array
     */
    public void add(int index, E element) {
        // Our ArrayList accepts nulls, thus given element can be null
        if (index < LOW_BOUND || index > _size) {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        } else if (index == _size) {
            add(element);
        } else {
            grow();

            for (int i = _size; i > index; i--) {
                E elementToAdd = get(i - ITERATION);
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
        // Our ArrayList accepts nulls, thus given element can be null
        grow();

        _backingArray[_size] = element;
        _size++;

        return true;
    }


    /**
     * Clears the backing array by nulling out each value and resets size to 0
     */
    public void clear() {
        for (int i = LOW_BOUND; i < _size; i++) {
            set(i, null);
        }

        _size = LOW_BOUND;
    }


    /**
     * Fetches the element at a given index
     *
     * @param index Index of element to fetch
     * @return The element at the given index
     */
    public E get(int index) {
        if (index < LOW_BOUND || index >= _size) {
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
        int returnValue = DEFAULT_RETURN;

        for (int i = LOW_BOUND; i < _size && returnValue == -1; i++) {
            if (element == _backingArray[i]) {
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
        return _size == LOW_BOUND;
    }


    /**
     * Removes the element at the given index
     *
     * @param index Index of element to remove
     * @return The value of the removed element
     */
    public E remove(int index) {
        if (index < LOW_BOUND || index >= _size) {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        } else {
            E returnIndex = _backingArray[index];
            _size--;

            for (int i = LOW_BOUND; i < _size; i++) {
                set(i, _backingArray[i + ITERATION]);
            }

            //
            _backingArray[_size] = null;
            return returnIndex;
        }
    }


    /**
     * Updates an element in the ArrayList with a new element
     *
     * @param index Index of element to replace
     * @param element New value to update the current element with
     * @return The value of the previous element occupying that index
     */
    public E set(int index, E element) {
        if (index < LOW_BOUND || index >= _size) {
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


    // Private Methods
    /**
     * Doubles the capacity of the backing array if size reaches capacity
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        if (_size == _capacity && _size < Integer.MAX_VALUE / 2) {
            _capacity *= DOUBLE;
            E[] _tempArray = (E[]) new Object[_capacity];

            for (int i = LOW_BOUND; i < _size; i++) {
                _tempArray[i] = _backingArray[i];
            }

            _backingArray = _tempArray;
        }
    }
}
