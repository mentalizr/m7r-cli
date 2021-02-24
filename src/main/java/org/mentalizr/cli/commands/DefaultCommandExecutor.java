package org.mentalizr.cli.commands;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.util.Version;

import static org.mentalizr.cli.M7rCli.ID_HELP;
import static org.mentalizr.cli.M7rCli.ID_VERSION;

public class DefaultCommandExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) {

        OptionParserResult optionParserResultGlobal = cliCall.getOptionParserResultGlobal();
        if (optionParserResultGlobal.hasOption(ID_VERSION)) {
            Version version = Version.getInstance(M7rCli.class);
            System.out.println();
            System.out.println("-----------------------------------------------");
            System.out.println("m7r-cli " + version.getVersion());
            System.out.println("-----------------------------------------------");
            System.out.println();
            System.out.println("Build:         " + version.getBuild());

        } else if (optionParserResultGlobal.hasOption(ID_HELP)) {
            System.out.println("help: not implemented yet!");
        } else {
            System.out.println("mentalizr command line interface. Call 'm7r help' for more information.");
        }

    }
}
