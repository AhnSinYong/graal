/*
 * Copyright (c) 2012, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.graal.pointsto.infrastructure;

import jdk.vm.ci.meta.JavaType;
import jdk.vm.ci.meta.ResolvedJavaType;
import jdk.vm.ci.meta.Signature;

public class WrappedSignature implements Signature {

    private final Universe universe;
    private final Signature wrapped;
    private final ResolvedJavaType defaultAccessingClass;

    public WrappedSignature(Universe universe, Signature wrapped, WrappedJavaType defaultAccessingClass) {
        this.universe = universe;
        this.wrapped = wrapped;
        this.defaultAccessingClass = defaultAccessingClass.getWrapped();
    }

    @Override
    public int getParameterCount(boolean receiver) {
        return wrapped.getParameterCount(receiver);
    }

    @Override
    public JavaType getParameterType(int index, ResolvedJavaType accessingClass) {
        return universe.lookup(wrapped.getParameterType(index, defaultAccessingClass).resolve(defaultAccessingClass));
    }

    @Override
    public JavaType getReturnType(ResolvedJavaType accessingClass) {
        return universe.lookup(wrapped.getReturnType(defaultAccessingClass).resolve(defaultAccessingClass));
    }
}
