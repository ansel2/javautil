package com.eatnumber1.util.collections.concurrent;

import com.eatnumber1.util.collections.concurrent.iterators.SynchronizedListIterator;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 */
public class SynchronizedListFacade<T, D extends List<T>> extends SynchronizedCollectionFacade<T, D> implements SynchronizedList<T, D> {
    public SynchronizedListFacade( @NotNull D delegate, @NotNull Lock lock ) {
        super(delegate, lock);
    }

    public SynchronizedListFacade( @NotNull D delegate, @NotNull ReadWriteLock lock ) {
        super(delegate, lock);
    }

    public SynchronizedListFacade( @NotNull D delegate ) {
        super(delegate);
    }

    @Override
    public boolean addAll( int index, Collection<? extends T> c ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().addAll(index, c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public T get( int index ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().get(index);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public T set( int index, T element ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().set(index, element);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void add( int index, T element ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            getDelegate().add(index, element);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public T remove( int index ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().remove(index);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int indexOf( Object o ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().indexOf(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int lastIndexOf( Object o ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().lastIndexOf(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return new SynchronizedListIterator<T, ListIterator<T>>(getDelegate().listIterator(), getReadWriteLock());
    }

    @Override
    public ListIterator<T> listIterator( int index ) {
        return new SynchronizedListIterator<T, ListIterator<T>>(getDelegate().listIterator(index), getReadWriteLock());
    }

    @Override
    public List<T> subList( int fromIndex, int toIndex ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return new SynchronizedListFacade<T, List<T>>(getDelegate().subList(fromIndex, toIndex), getReadWriteLock());
        } finally {
            readLock.unlock();
        }
    }
}
