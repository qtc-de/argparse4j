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
package eu.tneitzel.argparse4j.impl.action;

import java.util.Map;

import eu.tneitzel.argparse4j.inf.Argument;
import eu.tneitzel.argparse4j.inf.ArgumentAction;
import eu.tneitzel.argparse4j.inf.ArgumentParser;

/**
 * <p>
 * Argument action to print version message and exit program.
 * </p>
 * <p>
 * Please note that this
 * {@link #run(ArgumentParser, Argument, Map, String, Object)} terminates
 * program after printing the version message. {@link #consumeArgument()} always
 * returns {@code false}.
 * </p>
 */
public class VersionArgumentAction implements ArgumentAction {

    @Override
    public void run(ArgumentParser parser, Argument arg,
            Map<String, Object> attrs, String flag, Object value) {
        parser.printVersion();
        System.exit(0);
    }

    @Override
    public boolean consumeArgument() {
        return false;
    }

    @Override
    public void onAttach(Argument arg) {
    }

}
