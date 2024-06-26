/*
 * Copyright (C) 2011 Tatsuhiro Tsujikawa
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eu.tneitzel.argparse4j.ext.java7.mock;

import java.util.Collection;

import eu.tneitzel.argparse4j.inf.Argument;
import eu.tneitzel.argparse4j.inf.ArgumentAction;
import eu.tneitzel.argparse4j.inf.ArgumentChoice;
import eu.tneitzel.argparse4j.inf.ArgumentType;
import eu.tneitzel.argparse4j.inf.FeatureControl;

public class MockArgument implements Argument {

    @Override
    public Argument nargs(int n) {
        return null;
    }

    @Override
    public Argument nargs(String n) {
        return null;
    }

    @Override
    public Argument setConst(Object value) {
        return null;
    }

    @Override
    @SafeVarargs
    public final <E> Argument setConst(E... values) {
        return null;
    }

    @Override
    public Argument setDefault(Object value) {
        return null;
    }

    @Override
    @SafeVarargs
    public final <E> Argument setDefault(E... values) {
        return null;
    }

    @Override
    public Argument setDefault(FeatureControl ctrl) {
        return null;
    }

    @Override
    public <T> Argument type(Class<T> type) {
        return null;
    }

    @Override
    public <T> Argument type(ArgumentType<T> type) {
        return null;
    }

    @Override
    public Argument required(boolean required) {
        return null;
    }

    @Override
    public Argument action(ArgumentAction action) {
        return null;
    }

    @Override
    public Argument choices(ArgumentChoice choice) {
        return null;
    }

    @Override
    public <E> Argument choices(Collection<E> values) {
        return null;
    }

    @Override
    @SafeVarargs
    public final <E> Argument choices(E... values) {
        return null;
    }

    @Override
    public Argument dest(String dest) {
        return null;
    }

    @Override
    public Argument metavar(String... metavar) {
        return null;
    }

    @Override
    public Argument help(String help) {
        return null;
    }

    @Override
    public Argument help(FeatureControl help) {
        return null;
    }

    @Override
    public String textualName() {
        return null;
    }

    @Override
    public String getDest() {
        return null;
    }

    @Override
    public Object getConst() {
        return null;
    }

    @Override
    public Object getDefault() {
        return null;
    }

    @Override
    public FeatureControl getDefaultControl() {
        return null;
    }

    @Override
    public FeatureControl getHelpControl() {
        return null;
    }
}
