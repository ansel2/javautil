/*
 * Copyright 2007 Russell Harmon
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.eatnumber1.util.collections.concurrent;

import com.eatnumber1.util.collections.concurrent.iterators.SynchronizedIterator;
import com.eatnumber1.util.concurrent.facade.SynchronizedReadWriteFacade;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Jul 13, 2007
 */
@ThreadSafe
public class SynchronizedCollectionFacade<T, D extends Collection<T>> extends SynchronizedReadWriteFacade<D> implements SynchronizedCollection<T, D> {
    public SynchronizedCollectionFacade( D delegate, @NotNull Lock lock ) {
        super(delegate, lock);
    }

    public SynchronizedCollectionFacade( @NotNull D delegate, @NotNull ReadWriteLock lock ) {
        super(delegate, lock);
    }

    public SynchronizedCollectionFacade( @NotNull D delegate ) {
        super(delegate);
    }

    @Override
    public int size() {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains( Object o ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().contains(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return synchronizedIterator();
    }

    @Override
    public Object[] toArray() {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().toArray();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T> T[] toArray( T[] a ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            //noinspection SuspiciousToArrayCall
            return getDelegate().toArray(a);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean add( T t ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().add(t);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean remove( Object o ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().remove(o);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean containsAll( Collection<?> c ) {
        Lock readLock = readLock();
        readLock.lock();
        try {
            return getDelegate().containsAll(c);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean addAll( Collection<? extends T> c ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().addAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeAll( Collection<?> c ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().removeAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean retainAll( Collection<?> c ) {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            return getDelegate().retainAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        Lock writeLock = writeLock();
        writeLock.lock();
        try {
            getDelegate().clear();
        } finally {
            writeLock.unlock();
        }
    }

    @NotNull
    @Override
    public SynchronizedIterator<T, Iterator<T>> synchronizedIterator() {
        return new SynchronizedIterator<T, Iterator<T>>(getDelegate().iterator(), getReadWriteLock());
    }
}
