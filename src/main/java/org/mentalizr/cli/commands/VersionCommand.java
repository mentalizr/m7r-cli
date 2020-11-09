package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.util.Version;

import java.util.List;

public class VersionCommand extends CommandExecutor {

    public VersionCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }

    @Override
    public void execute() {
        Version version = Version.getInstance(M7rCli.class);
        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println("m7r-cli " + version.getVersion());
        System.out.println("-----------------------------------------------");
        System.out.println();
        System.out.println("Build:         " + version.getBuild());
    }

}
