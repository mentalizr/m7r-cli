package org.mentalizr.cli;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfiguration;

import java.util.List;

import static org.mentalizr.cli.M7rCli.*;

public class CliContext {

    private final CliCallGlobalConfiguration cliCallGlobalConfiguration;
    private final List<String> commandList;
    private final OptionParserResult optionParserResultSpecific;
    private CliConfiguration cliConfiguration;

    public CliContext(
            CliCallGlobalConfiguration cliCallGlobalConfiguration,
            List<String> commandList,
            OptionParserResult optionParserResultSpecific
    ) {
        this.cliCallGlobalConfiguration = cliCallGlobalConfiguration;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
        this.cliConfiguration = null;
    }

//    public static CliContext getInstance(ParserResult parserResult) {
//
//        CliCallGlobalConfiguration cliCallGlobalConfiguration = new CliCallGlobalConfiguration();
//        OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();
//        if (optionParserResultGlobal.hasOption(ID_DEBUG)) cliCallGlobalConfiguration.setDebug(true);
//        if (optionParserResultGlobal.hasOption(ID_SILENT)) cliCallGlobalConfiguration.setSilent(true);
//        if (optionParserResultGlobal.hasOption(ID_STACKTRACE)) cliCallGlobalConfiguration.setStacktrace(true);
//
//        return new CliContext(cliCallGlobalConfiguration, parserResult.getCommandList(), parserResult.getOptionParserResultSpecific());
//    }

    public static CliContext getInstance(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        CliCallGlobalConfiguration cliCallGlobalConfiguration = new CliCallGlobalConfiguration();
        if (optionParserResultGlobal.hasOption(ID_DEBUG)) cliCallGlobalConfiguration.setDebug(true);
        if (optionParserResultGlobal.hasOption(ID_SILENT)) cliCallGlobalConfiguration.setSilent(true);
        if (optionParserResultGlobal.hasOption(ID_STACKTRACE)) cliCallGlobalConfiguration.setStacktrace(true);

        return new CliContext(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }

    public static CliContext getInstance(CliCall cliCall) {
        CliCallGlobalConfiguration cliCallGlobalConfiguration = new CliCallGlobalConfiguration();
        OptionParserResult optionParserResultGlobal = cliCall.getOptionParserResultGlobal();
        if (optionParserResultGlobal.hasOption(ID_DEBUG)) cliCallGlobalConfiguration.setDebug(true);
        if (optionParserResultGlobal.hasOption(ID_SILENT)) cliCallGlobalConfiguration.setSilent(true);
        if (optionParserResultGlobal.hasOption(ID_STACKTRACE)) cliCallGlobalConfiguration.setStacktrace(true);

        List<String> commandList = cliCall.getCommandList();
        OptionParserResult optionParserResultSpecific = cliCall.getOptionParserResultSpecific();

        return new CliContext(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }


    public CliCallGlobalConfiguration getCliCallGlobalConfiguration() {
        return cliCallGlobalConfiguration;
    }

    public boolean isDebug() {
        if (this.cliCallGlobalConfiguration == null) return false;
        return this.cliCallGlobalConfiguration.isDebug();
    }

    public boolean showStacktrace() {
        if (this.cliCallGlobalConfiguration == null) return false;
        return this.cliCallGlobalConfiguration.isStacktrace();
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return optionParserResultSpecific;
    }

    public void assertCliConfiguration() {
        if (this.cliConfiguration == null) {
            throw new IllegalStateException("CliConfiguration expected.");
        }
    }

    public CliConfiguration getCliConfiguration() {
        return cliConfiguration;
    }

    public void setCliConfiguration(CliConfiguration cliConfiguration) {
        this.cliConfiguration = cliConfiguration;
    }

}
