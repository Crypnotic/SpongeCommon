/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.inventory.query.type;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.KeyValueMatcher;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.common.inventory.lens.Lens;
import org.spongepowered.common.inventory.query.SpongeDepthQuery;

@SuppressWarnings("unchecked")
public final class KeyValueMatcherQuery<T> extends SpongeDepthQuery {

    private final KeyValueMatcher<T> matcher;

    public KeyValueMatcherQuery(KeyValueMatcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Lens lens, Lens parent, Inventory inventory) {
        if (parent == null) {
            return false;
        }

        final Key<? extends Value<T>> key = this.matcher.getKey();
        if (this.matcher.matches(inventory.get(key).orElse(null))) {
            return true;
        }

        // Check for lens properties
        final Object value = parent.getDataFor(lens).get(key);
        return this.matcher.matches((T) value);
    }

}