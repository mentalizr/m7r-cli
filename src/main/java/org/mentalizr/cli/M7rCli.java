package org.mentalizr.cli;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.ArgsHelper;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.mentalizr.cli.commands.*;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;

import java.util.List;

public class M7rCli {

    public static final String ID_DEBUG = "debug";
    public static final String ID_STACKTRACE = "stacktrace";
    public static final String ID_SILENT = "silent";

    public static final String LOGIN = "login";
    public static final String ID_USER = "login";
    public static final String ID_PASSWORD = "password";

    public static final String NOOP = "noop";
    public static final String INIT = "init";
    public static final String CONFIG = "config";
    public static final String EDIT = "edit";
    public static final String SHOW = "show";
    public static final String VERSION = "version";
    public static final String HELP = "help";

    private static CliCallGlobalConfiguration processParserResultGlobalOptions(OptionParserResult optionParserResult) {
        CliCallGlobalConfiguration cliCallGlobalConfiguration = new CliCallGlobalConfiguration();
        if (optionParserResult.hasOption(ID_DEBUG)) cliCallGlobalConfiguration.setDebug(true);
        if (optionParserResult.hasOption(ID_SILENT)) cliCallGlobalConfiguration.setSilent(true);
        if (optionParserResult.hasOption(ID_STACKTRACE)) cliCallGlobalConfiguration.setStacktrace(true);
        return cliCallGlobalConfiguration;
    }

    private static CommandLineInterface prepareCLI() {
        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build(ID_DEBUG))
                        .add(new OptionBuilder().withLongName("silent").withDescription("silent").build(ID_SILENT))
                        .add(new OptionBuilder().withLongName("stacktrace").build(ID_STACKTRACE))
                )
                .withCommands(new Commands()
                        .add("login").withSpecificOptions(new Options()
                                .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user").build(ID_USER))
                                .add(new OptionBuilder().withLongName("password").withShortName('p').hasArgument().withDescription("password").build(ID_PASSWORD))
                        )
                        .root().add(INIT)
                        .root().add(CONFIG).addOneOf(SHOW, EDIT)
                        .root().add(HELP)
                        .root().add(VERSION)
                        .root().add(NOOP)
                )
                .build();
    }

    public static void main(String[] args) {

        CommandLineInterface cli = prepareCLI();
        try {
            ParserResult parserResult = cli.parse(args);
            CliCallGlobalConfiguration cliCallGlobalConfiguration = processParserResultGlobalOptions(parserResult.getOptionParserResultGlobal());
            List<String> commandList = parserResult.getCommandList();
            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();

            if (parserResult.getCommandList().get(0).equals(VERSION)) {
                VersionCommand versionCommand = new VersionCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                versionCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(HELP)) {
                HelpCommand helpCommand = new HelpCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                helpCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(LOGIN)) {
                LoginCommand loginCommand = new LoginCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                loginCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(NOOP)) {
                NoopCommand noopCommand = new NoopCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                noopCommand.execute();
            }

            if (commandList.get(0).equals(INIT)) {
                InitCommand initCommand = new InitCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                initCommand.execute();
            }

            if (commandList.get(0).equals(CONFIG)) {
                if (commandList.get(1).equals(SHOW)) {
                    ShowConfigCommand showConfigCommand = new ShowConfigCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                    showConfigCommand.execute();
                } else {
                    EditConfigCommand editConfigCommand = new EditConfigCommand(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
                    editConfigCommand.execute();
                }
            }

        } catch (UnrecognizedArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("m7r " + ArgsHelper.getArgsString(args));
            System.out.println("    " + e.getArgumentPointerString());

//            System.out.println(e.getArgumentIndex());

        }

    }

}
