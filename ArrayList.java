// Imports
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A custom version ArrayList class modeled after default ArrayList class
 *
 * @author blee20@georgefox.edu
 * @param <E> The type of elements in this list
 */
public class ArrayList<E> implements Iterable<E>
{
    // Constants
    private static final int DEFAULT_CAPACITY = 10;
    private static final int NOT_FOUND = -1;
    private static final int DOUBLE = 2;


    // Instance Variables
    private int _size = 0;
    private E[] _backingArray;


    // Constructors
    /**
     * Creates an instance of class ArrayList object with capacity of 10
     */
    public ArrayList()
    {
        this(DEFAULT_CAPACITY);
    }


    /**
     * Creates an instance of class ArrayList object
     *
     * @param initialCapacity Starting capacity for new ArrayList instance
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity)
    {
        if (initialCapacity < 0)
        {
            throw new IllegalArgumentException("Error: Initial capacity must be greater than or" +
                    "equal to 0");
        }
        else if (initialCapacity == 0)
        {
            _backingArray = (E[]) new Object[DEFAULT_CAPACITY];
        }
        else
        {
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
    public void add(int index, E element)
    {
        if (index < 0 || index > _size)
        {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        }
        else if (index == _size)
        {
            grow();

            add(element);
        }
        else
        {
            grow();

            for (int i = _size; i > index; i--)
            {
                E elementToAdd = get(i - 1);
                _backingArray[i] = elementToAdd;
            }

            _backingArray[index] = element;
            _size += 1;
        }
    }


    /**
     * Appends an element to the backing array
     *
     * @param element Element to append to array
     * @return Boolean value based on success of method
     */
    public boolean add(E element)
    {
        grow();

        _backingArray[_size] = element;
        _size += 1;

        return true;
    }


    /**
     * Clears the backing array by nulling out each value and resets size to 0
     */
    public void clear()
    {
        for (int i = 0; i < _size; i++)
        {
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
    public E get(int index)
    {
        if (index < 0 || index >= _size)
        {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        }
        else
        {
            return _backingArray[index];
        }
    }


    /**
     * Fetches the index of a given element if it exists
     *
     * @param element Element to fetch index for
     * @return The index value if element exists or -1 if element does not exist
     */
    public int indexOf(E element)
    {
        int returnValue = NOT_FOUND;

        for (int i = 0; i < _size && returnValue == NOT_FOUND; i++)
        {
            if (element == _backingArray[i])
            {
                returnValue = i;
            }
        }

        return returnValue;
    }


    /**
     * Determines if the backing array is empty or not
     *
     * @return True if the collection is empty, else false
     */
    public boolean isEmpty()
    {
        return _size == 0;
    }


    /**
     * Removes an element at a given index and shuffles elements accordingly
     *
     * @param index Index of element to remove
     * @return The value of that index
     */
    public E remove(int index)
    {
        if (index < 0 || index >= _size)
        {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        }
        else
        {
            E returnElement = _backingArray[index];

            for (int i = index; i < _size - 1; i++)
            {
                set(i, _backingArray[i + 1]);
            }

            set(_size - 1, null);

            _size--;

            return returnElement;
        }
    }


    /**
     * Replaces the element at the specified index with the given element
     *
     * @param index Index of element to replace
     * @param element New element to replace current element with
     * @return Previous element at specified index
     */
    public E set(int index, E element)
    {
        if (index < 0 || index >= _size)
        {
            throw new IndexOutOfBoundsException("Error: Must provide a valid index");
        }
        else
        {
            E returnElement = _backingArray[index];

            _backingArray[index] = element;

            return returnElement;
        }
    }


    /**
     * Fetches the current size of the ArrayList object
     *
     * @return The backing array's current size (_size)
     */
    public int size()
    {
        return _size;
    }


    /**
     * Creates a new iterator
     *
     * @return An iterator of type E
     */
    public Iterator<E> iterator()
    {
        return new ArrayListIterator();
    }


    /**
     * Private inner-class that adds support for enhanced for-loop notation
     */
    private class ArrayListIterator implements Iterator<E>
    {
        // Instance Variables
        private int _index;


        // Constructor
        /**
         * Creates an instance of private inner-class ArrayListIterator object
         */
        public ArrayListIterator()
        {
            _index = 0;
        }


        // Instance Methods
        /**
         * Determines if there are more elements
         *
         * @return True if there is another element, else false
         */
        public boolean hasNext()
        {
            return _index < (_size);
        }


        /**
         * Fetches the next element if it exists
         *
         * @return Next element if hasNext returns true, else throws exception
         */
        public E next()
        {
            if (hasNext())
            {
                return get(_index++);
            }

            else
            {
                throw new NoSuchElementException("Error: Element not found");
            }
        }
    }


    // Private Methods
    /**
     * Doubles the capacity of the backing array if size reaches capacity
     */
    @SuppressWarnings("unchecked")
    private void grow()
    {
        if (_size == _backingArray.length && _size <= (Integer.MAX_VALUE - 1) / 2)
        {
            E[] tempArray = (E[]) new Object[_backingArray.length * DOUBLE];

            for (int i = 0; i < _size; i++)
            {
                tempArray[i] = _backingArray[i];
            }

            _backingArray = tempArray;
        }
    }
}
