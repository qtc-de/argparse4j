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
package eu.tneitzel.argparse4j.internal;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.tneitzel.argparse4j.helper.MessageLocalization;
import eu.tneitzel.argparse4j.helper.TextHelper;
import eu.tneitzel.argparse4j.inf.ArgumentParserException;
import eu.tneitzel.argparse4j.inf.FeatureControl;
import eu.tneitzel.argparse4j.inf.SubparserGroup;
import eu.tneitzel.argparse4j.inf.Subparsers;

/**
 * <strong>The application code must not use this class directly.</strong>
 */
public final class SubparsersImpl extends SubparserGroupImpl implements Subparsers {

    private final Set<SubparserGroupImpl> subparsers_ = new LinkedHashSet<SubparserGroupImpl>();
    private String help_ = "";
    private String description_ = "";
    private String dest_ = "";
    private String metavar_ = "";

    SubparsersImpl(ArgumentParserImpl mainParser) {
        super(mainParser);
    }

    public SubparserGroup addSubparserGroup() {
        SubparserGroupImpl subparsersGroup = new SubparserGroupImpl(mainParser_);
        subparsers_.add(subparsersGroup);
        return subparsersGroup;
    }

    @Override
    public SubparsersImpl dest(String dest) {
        dest_ = TextHelper.nonNull(dest);
        return this;
    }

    @Override
    public SubparsersImpl help(String help) {
        help_ = TextHelper.nonNull(help);
        return this;
    }

    @Override
    public SubparsersImpl title(String title) {
        title_ = TextHelper.nonNull(title);
        return this;
    }

    @Override
    public SubparsersImpl description(String description) {
        description_ = TextHelper.nonNull(description);
        return this;
    }

    public String getDescription() {
        return description_;
    }

    @Override
    public SubparsersImpl metavar(String metavar) {
        metavar_ = TextHelper.nonNull(metavar);
        return this;
    }

    public Map<String, SubparserImpl> getParsers() {
        Map<String, SubparserImpl> parsers = new LinkedHashMap<String, SubparserImpl>();
        parsers.putAll(parsers_);

        for (SubparserGroupImpl subparsersGroup : subparsers_)
            parsers.putAll(subparsersGroup.getParsers());

        return parsers;
    }

    /**
     * Get next SubparserImpl from given command and state. This function
     * resolves abbreviated command input as well. If the given command is
     * ambiguous, {@link ArgumentParserException} will be thrown. If no matching
     * SubparserImpl is found, this function returns null.
     *
     * @return next SubparserImpl or null
     */
    private SubparserImpl resolveNextSubparser(String command)
            throws ArgumentParserException {
        if (command.isEmpty()) {
            return null;
        }
        Map<String, SubparserImpl> parsers = getParsers();
        SubparserImpl ap = parsers.get(command);
        if (ap == null) {
            List<String> cand = TextHelper.findPrefix(parsers.keySet(),
                    command);
            int size = cand.size();
            if (size == 1) {
                ap = parsers.get(cand.get(0));
            } else if (size > 1) {
                // Sort it to make unit test easier
                Collections.sort(cand);
                throw new ArgumentParserException(String.format(
                        TextHelper.LOCALE_ROOT,
                        localize("ambiguousCommandError"), command,
                        TextHelper.concat(cand, 0, ", ")), mainParser_);
            }
        }
        return ap;
    }

    void parseArg(ParseState state, Map<String, Object> opts)
            throws ArgumentParserException {
        Map<String, SubparserImpl> parsers = getParsers();
        if (parsers.isEmpty()) {
            throw new IllegalArgumentException("too many arguments");
        }
        SubparserImpl ap = resolveNextSubparser(state.getArg());
        if (ap == null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, SubparserImpl> entry : parsers.entrySet()) {
                sb.append("'").append(entry.getKey()).append("', ");
            }
            sb.delete(sb.length() - 2, sb.length());
            throw new UnrecognizedCommandException(String.format(
                    TextHelper.LOCALE_ROOT,
                    localize("invalidChoiceError"), state.getArg(),
                    sb.toString()), mainParser_, state.getArg());
        } else {
            ++state.index;
            ap.parseArgs(state, opts);
            // Call after parseArgs to overwrite dest_ attribute set by
            // sub-parsers.
            if (!dest_.isEmpty()) {
                opts.put(dest_, ap.getCommand());
            }
        }
    }

    String formatShortSyntax() {
        if (metavar_.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (Map.Entry<String, SubparserImpl> entry : getParsers().entrySet()) {
                if (entry.getValue().getHelpControl() != FeatureControl.SUPPRESS) {
                    sb.append(entry.getKey()).append(",");
                }
            }
            if (sb.length() > 1) {
                sb.delete(sb.length() - 1, sb.length());
            }
            sb.append("}");
            return sb.toString();
        } else {
            return metavar_;
        }
    }

    /**
     * Writes the help message for this and descendants.
     *
     * @param writer
     *            The writer to output
     * @param format_width
     *            column width
     */
    void printSubparserHelp(PrintWriter writer, int format_width)
    {
        boolean printed = false;
        TextHelper.printHelp(writer, formatShortSyntax(), help_, mainParser_.getTextWidthCounter(), format_width);

        for (Map.Entry<String, SubparserImpl> entry : parsers_.entrySet())
        {
            // Don't generate help for aliases.
            if (entry.getValue().getHelpControl() != FeatureControl.SUPPRESS &&
                    entry.getKey().equals(entry.getValue().getCommand()))
            {
                entry.getValue().printSubparserHelp(writer, format_width);
                printed = true;
            }
        }

        Iterator<SubparserGroupImpl> iterator = subparsers_.iterator();

        if (printed && iterator.hasNext())
        {
            writer.println();
        }

        while (iterator.hasNext())
        {
            iterator.next().printSubparserHelp(writer, format_width);

            if (iterator.hasNext())
            {
                writer.println();
            }
        }
    }

    private String localize(String messageKey) {
        return MessageLocalization.localize(
                mainParser_.getConfig().getResourceBundle(), messageKey);
    }

    @Override
    public SubparserGroup getSubparserGroup(String title)
    {
        for (SubparserGroupImpl group : subparsers_)
        {
            if (group.getTitle().equals(title))
            {
                return group;
            }
        }

        return null;
    }

    @Override
    public SubparserGroup getOrCreateSubparserGroup(String title)
    {
        for (SubparserGroupImpl group : subparsers_)
        {
            if (group.getTitle().equals(title))
            {
                return group;
            }
        }

        return this.addSubparserGroup().title(title);
    }
}
