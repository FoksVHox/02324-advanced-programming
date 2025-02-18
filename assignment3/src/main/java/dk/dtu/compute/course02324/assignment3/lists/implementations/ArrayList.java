package dk.dtu.compute.course02324.assignment3.lists.implementations;



import dk.dtu.compute.course02324.assignment3.lists.types.List;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * An implementation of the interface {@link List} based on basic Java
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class ArrayList<E> implements List<E> {

    /**
     * Constant defining the default size of the array when the
     * list is created. The value can be any (strictly) positive
     * number. Here, we have chosen <code>10</code>, which is also
     * Java's default for some array-based collection implementations.
     */
    final private int DEFAULT_SIZE = 10;

    /**
     * Current size of the list.
     */
    private int size = 0;

    /**
     *  The array for storing the elements of the
     */
    private E[] list = createEmptyArray(DEFAULT_SIZE);

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull E get(int pos) throws IndexOutOfBoundsException {
        isOutOfBounds(pos);
        return list[pos];
    }

    @Override
    public E set(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        isOutOfBounds(pos);

        E oldValue = list[pos];
        list[pos] = e;
        return oldValue;
    }

    @Override
    public boolean add(@NotNull E e) {
        if(e == null) throw new IllegalArgumentException();

        if (size == list.length) {
            list = Arrays.copyOf(list, size * 2 + 1); // Resize if full
        }

        for (int i = size; i > 0; i--) {
            list[i] = list[i - 1];
        }

        list[0] = e;
        size++;

        return true;
    }

    @Override
    public boolean add(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        isOutOfBounds(pos);

        if (size == list.length) {
            list = Arrays.copyOf(list, size * 2 + 1); // Resize if full
        }

        for (int i = size; i > pos; i--) {
            list[i] = list[i - 1];
        }

        list[pos] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int pos)  {
        isOutOfBounds(pos);

        E removedElement = list[pos];

        // Shift elements left
        for (int i = pos; i < size - 1; i++) {
            list[i] = list[i + 1];
        }

        list[size - 1] = null; // Prevent memory leak
        size--;

        return removedElement;
    }

    @Override
    public boolean remove(E e) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(list[i], e)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(list[i], e)) {
                return i;
            }
        }
        return -1; // Not found
    }

    @Override
    public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {
        if(c == null) throw new IllegalArgumentException();
        Arrays.sort((E[]) list, 0, size, c);
    }

    /**
     * Creates a new array of type <code>E</code> with the given size.
     *
     * @param length the size of the array
     * @return a new array of type <code>E</code> and the given length
     */
    private E[] createEmptyArray(int length) {
        // there is unfortunately no really easy and elegant way to initialize
        // an array with a type coming in as a generic type parameter, but
        // the following is simple enough. And it is OK, since the array
        // is never passed out of this class.
        return (E[]) new Object[length];
    }

    private void isOutOfBounds(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds: " + index);
    }
}
