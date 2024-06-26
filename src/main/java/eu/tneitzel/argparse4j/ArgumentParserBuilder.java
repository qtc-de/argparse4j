package eu.tneitzel.argparse4j;

import static eu.tneitzel.argparse4j.ArgumentParsers.DEFAULT_FORMAT_WIDTH;
import static eu.tneitzel.argparse4j.ArgumentParsers.DEFAULT_PREFIX_CHARS;
import static eu.tneitzel.argparse4j.ArgumentParsers.cjkWidthLangs_;

import java.util.Locale;

import eu.tneitzel.argparse4j.helper.ASCIITextWidthCounter;
import eu.tneitzel.argparse4j.helper.CJKTextWidthCounter;
import eu.tneitzel.argparse4j.helper.TextWidthCounter;
import eu.tneitzel.argparse4j.inf.Argument;
import eu.tneitzel.argparse4j.inf.ArgumentParser;
import eu.tneitzel.argparse4j.internal.ArgumentParserConfigurationImpl;
import eu.tneitzel.argparse4j.internal.ArgumentParserImpl;
import eu.tneitzel.argparse4j.internal.TerminalWidth;

/**
 * ArgumentParserBuilder is a class to build new ArgumentParser with a given
 * custom configuration.
 *
 * @since 0.8.0
 */
public class ArgumentParserBuilder {
    private final String prog_;
    private boolean addHelp_ = true;
    private String prefixChars_ = DEFAULT_PREFIX_CHARS;
    private String fromFilePrefix_ = null;
    private Locale locale_ = Locale.getDefault();
    private boolean cjkWidthHack_ = true;
    private int defaultFormatWidth_ = DEFAULT_FORMAT_WIDTH;
    private boolean terminalWidthDetection_ = true;
    private boolean singleMetavar_ = false;
    private boolean noDestConversionForPositionalArgs_ = false;
    private boolean includeArgumentNamesAsKeysInResult_ = false;
    boolean mustHelpTextIncludeMutualExclusivity_ = false;

    ArgumentParserBuilder(String prog, DefaultSettings defaultSettings) {
        prog_ = prog;
        defaultSettings.apply(this);
    }

    /**
     * <p>
     * If true, {@code -h/--help} are available. If false, they are
     * not. Default value: {@code true}.
     * </p>
     *
     * @param flag
     *         {@code true} or {@code false}
     * @return This builder.
     */
    public ArgumentParserBuilder addHelp(boolean flag) {
        addHelp_ = flag;
        return this;
    }

    /**
     * <p>
     * The set of characters that prefix named arguments. Default value:
     * {@code "-"}.
     * </p>
     *
     * @param prefixChars
     *         The named argument prefixes.
     * @return This builder.
     */
    public ArgumentParserBuilder prefixChars(String prefixChars) {
        prefixChars_ = prefixChars;
        return this;
    }

    /**
     * <p>
     * The set of characters that prefix file path from which
     * additional arguments should be read. Specify {@code null} to
     * disable reading arguments from file. Default value: {@code null}.
     * </p>
     *
     * @param fromFilePrefix
     *         The from file path prefixes.
     * @return This builder.
     */
    public ArgumentParserBuilder fromFilePrefix(String fromFilePrefix) {
        fromFilePrefix_ = fromFilePrefix;
        return this;
    }

    /**
     * <p> The locale to use for messages. Default value: {@code
     * Locale.getDefault()}. </p>
     *
     * @param locale
     *         The locale for messages.
     * @return This builder.
     */
    public ArgumentParserBuilder locale(Locale locale) {
        locale_ = locale;
        return this;
    }

    /**
     * <p>
     * Set {@code true} to enable CJK width hack.
     * </p>
     *
     * <p>
     * The CJK width hack is treat Unicode characters having East Asian Width
     * property Wide/Full/Ambiguous to have twice a width of ascii characters
     * when formatting help message if locale is "ja", "zh" or "ko". This
     * feature is enabled by default.
     * </p>
     *
     * @param flag
     *         {@code true} or {@code false}
     * @return This builder.
     */
    public ArgumentParserBuilder cjkWidthHack(boolean flag) {
        cjkWidthHack_ = flag;
        return this;
    }

    /**
     * <p>
     * Set the default format width. This width is used when terminal width
     * detection is disabled or when terminal width detection cannot detect the
     * actual number of columns.  Default value:
     * {@link ArgumentParsers#DEFAULT_FORMAT_WIDTH}.
     * </p>
     *
     * @param defaultFormatWidth
     *         The default format width.
     * @return This builder.
     */
    public ArgumentParserBuilder defaultFormatWidth(int defaultFormatWidth) {
        defaultFormatWidth_ = defaultFormatWidth;
        return this;
    }

    /**
     * <p> Set {@code true} to enable terminal width detection. </p>
     *
     * <p> If this feature is enabled, argparse4j will automatically detect the
     * terminal width and use it to format help messages. This  feature is
     * enabled by default. </p>
     *
     * @param flag
     *         {@code true} or {@code false}
     * @return This builder.
     */
    public ArgumentParserBuilder terminalWidthDetection(boolean flag) {
        terminalWidthDetection_ = flag;
        return this;
    }

    /**
     * <p>
     * If singleMetavar is {@code true}, a metavar string in help message is
     * only shown after the last flag instead of each flag.
     * </p>
     * <p>
     * By default and {@code false} is given to this method, a metavar is shown
     * after each flag:
     * </p>
     *
     * <pre>
     * -f FOO, --foo FOO
     * </pre>
     * <p>
     * If {@code true} is given to this method, a metavar string is shown only
     * once:
     * </p>
     *
     * <pre>
     * -f, --foo FOO
     * </pre>
     *
     * @param flag
     *         Switch to display a metavar only after the last flag.
     * @return This builder.
     */
    public ArgumentParserBuilder singleMetavar(boolean flag) {
        singleMetavar_ = flag;
        return this;
    }

    /**
     * <p>
     * Do not perform any conversion to produce "dest" value (See
     * {@link Argument#getDest()}) from positional argument name.
     * </p>
     *
     * <p>
     * Prior 0.5.0, no conversion is made to produce "dest" value from
     * positional argument name. Since 0.5.0, "dest" value is generated by
     * replacing "-" with "_" in positional argument name. This is the same
     * conversion rule for named arguments.
     * </p>
     *
     * <p>
     * By default, this is set to {@code false} (which means, conversion will be
     * done). Application is advised to update its implementation to cope with
     * this change. But if it is not feasible, call this method with
     * {@code true} to turn off the conversion and retain the same behaviour
     * with pre-0.5.0 version.
     * </p>
     *
     * @param flag
     *         Switch not to perform conversion to produce dest for positional
     *         arguments. If {@code true} is given, no conversion is made.
     * @return This builder.
     */
    public ArgumentParserBuilder noDestConversionForPositionalArgs(boolean flag) {
        noDestConversionForPositionalArgs_ = flag;
        return this;
    }

    /**
     * <p>
     * Include the name (see below) of the argument as a key in the parse
     * result in addition to the key in <code>dest</code> of the argument.
     * </p>
     *
     * <p>
     * The argument name is determined as follows:
     * </p>
     *
     * <ul>
     *     <li>Positional arguments: the name of the argument</li>
     *     <li>Named arguments: the first long flag, or if no long flag is
     *     present, the first flag, without the prefix</li>
     * </ul>
     *
     * @param flag
     *         If {@code true} is given, the argument name (as defined above)
     *         will also be in included as a key in the result. Otherwise only
     *         <code>dest</code> of the argument will be a key in the result.
     * @return This builder.
     */
    public ArgumentParserBuilder includeArgumentNamesAsKeysInResult(boolean flag) {
        includeArgumentNamesAsKeysInResult_ = flag;
        return this;
    }

    /**
     * <p>
     * Add a text to the help of mutually-exclusive groups explaining that at
     * most 1 arguments of the group may be given.
     * </p>
     *
     * @param flag
     *         If {@code true} is given, the help text of mutually-exclusive
     *         groups will be extended to indicate at most 1 argument of the
     *         group can be given. Otherwise the mutual exclusivity of a group
     *         is only visible in the usage.
     * @return This builder.
     */
    public ArgumentParserBuilder mustHelpTextIncludeMutualExclusivity(boolean flag) {
        mustHelpTextIncludeMutualExclusivity_ = flag;
        return this;
    }

    /**
     * Build the argument parser.
     *
     * @return newly created argument parser.
     */
    public ArgumentParser build() {
        return new ArgumentParserImpl(config());
    }

    private ArgumentParserConfigurationImpl config() {
        return new ArgumentParserConfigurationImpl(prog_, addHelp_, prefixChars_,
                fromFilePrefix_, locale_, createTextWidthCounter(),
                getFormatWidth(), singleMetavar_,
                noDestConversionForPositionalArgs_,
                includeArgumentNamesAsKeysInResult_,
                mustHelpTextIncludeMutualExclusivity_);
    }

    private TextWidthCounter createTextWidthCounter() {
        return cjkWidthHack_ && cjkWidthLangs_.contains(locale_.getLanguage()) ? new CJKTextWidthCounter() : new ASCIITextWidthCounter();
    }

    private int getFormatWidth() {
        if (terminalWidthDetection_) {
            int w = new TerminalWidth().getTerminalWidth() - 5;
            return w <= 0 ? defaultFormatWidth_ : w;
        } else {
            return defaultFormatWidth_;
        }
    }

}
