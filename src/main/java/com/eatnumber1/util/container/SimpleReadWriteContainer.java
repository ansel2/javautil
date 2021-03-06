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

package com.eatnumber1.util.container;

import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Jul 13, 2007
 */
public class SimpleReadWriteContainer<V> extends SimpleContainer<V> implements ReadWriteContainer<V> {
    public SimpleReadWriteContainer( V delegate ) {
        super(delegate);
    }

    public SimpleReadWriteContainer() {
    }

    @Override
    public <T> T doReadAction( @NotNull ContainerAction<V, T> action ) throws ContainerException {
        return action.doAction(getDelegate());
    }

    @Override
    public <T> T doWriteAction( @NotNull ContainerAction<V, T> action ) throws ContainerException {
        return action.doAction(getDelegate());
    }
}
