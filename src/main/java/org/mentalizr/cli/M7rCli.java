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
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.httpClient.HeaderHelper;
import org.mentalizr.cli.httpClient.HttpClientCreator;
import org.mentalizr.cli.httpClient.HttpRequestCreator;
import org.mentalizr.cli.restService.Login;
import org.mentalizr.cli.restService.Noop;
import org.mentalizr.cli.restService.RestService;
import org.mentalizr.cli.util.Version;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class M7rCli {

    private static final String ID_DEBUG = "debug";
    private static final String ID_STACKTRACE = "stacktrace";
    private static final String ID_SILENT = "silent";

    private static final String COMMAND_VERSION = "version";
    private static final String COMMAND_HELP = "help";

    private static final String COMMAND_LOGIN = "login";
    private static final String ID_USER = "login";
    private static final String ID_PASSWORD = "password";

    private static final String COMMAND_NOOP = "noop";

    private static final String COMMAND_CONFIG = "config";
    private static final String COMMAND_CONFIG_EDIT = "edit";
    private static final String COMMAND_CONFIG_SHOW = "show";


    private static CliCallGlobalConfiguration cliCallGlobalConfiguration = new CliCallGlobalConfiguration();

    private static void processParserResultGlobalOptions(OptionParserResult optionParserResult) {
        if (optionParserResult.hasOption(ID_DEBUG)) cliCallGlobalConfiguration.setDebug(true);
        if (optionParserResult.hasOption(ID_SILENT)) cliCallGlobalConfiguration.setSilent(true);
        if (optionParserResult.hasOption(ID_STACKTRACE)) cliCallGlobalConfiguration.setStacktrace(true);
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
                        .root().add("config").addOneOf("show", "edit")
                        .root().add("help")
                        .root().add("version")
                        .root().add("noop")
                )
                .build();
    }

    private static void showVersion() {
        System.out.println("m7r-cli");

        Version version = Version.getInstance(M7rCli.class);
        System.out.println("Version: " + version.getVersion());
        System.out.println("build known? " + version.isBuildKnown());
        System.out.println("Build: " + version.getBuild());
    }

    private static void showHelp() {
        // TODO
        System.out.println("help: not implemented yet!");
    }

    private static void login(OptionParserResult optionParserResultSpecific) {

        String user = null;
        if (optionParserResultSpecific.hasOption(ID_USER)) {
            user = optionParserResultSpecific.getValue(ID_USER);
        } else {
            System.out.println("Error. No user specified.");
            System.exit(1);
        }

        String password = "";
        boolean hasPassword;
        if (optionParserResultSpecific.hasOption(ID_PASSWORD)) {
            password = optionParserResultSpecific.getValue(ID_PASSWORD);
        } else {
            hasPassword = false;
        }

        // TODO read password from console is not given as option
        RestService restService = new Login(user, password);
        execute(restService);

    }

    private static void noop() {
        RestService restService = new Noop();
        execute(restService);
    }

    private static void showConfig() {
        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        System.out.println(cliConfiguration.toString());
    }

    private static void editConfig() {
        // TODO
        System.out.println("Edit config. Not implemented yet.");
    }


    public static void main(String[] args) {

        CommandLineInterface cli = prepareCLI();
        try {
            ParserResult parserResult = cli.parse(args);
            processParserResultGlobalOptions(parserResult.getOptionParserResultGlobal());
            List<String> commandList = parserResult.getCommandList();

            if (parserResult.getCommandList().get(0).equals(COMMAND_VERSION)) {
                showVersion();
                System.exit(0);
            }

            if (parserResult.getCommandList().get(0).equals(COMMAND_HELP)) {
                showHelp();
                System.exit(0);
            }

            if (parserResult.getCommandList().get(0).equals(COMMAND_LOGIN)) {
                login(parserResult.getOptionParserResultSpecific());
            }

            if (parserResult.getCommandList().get(0).equals(COMMAND_NOOP)) {
                noop();
            }

            if (commandList.get(0).equals(COMMAND_CONFIG)) {
                if (commandList.get(1).equals(COMMAND_CONFIG_SHOW)) {
                    showConfig();
                } else {
                    editConfig();
                }
            }

        } catch (UnrecognizedArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("m7r " + ArgsHelper.getArgsString(args));
            System.out.println("    " + e.getArgumentPointerString());

//            System.out.println(e.getArgumentIndex());

        }




//        try {
//            Init.init();
//        } catch (CliException e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);
//
//
//        try {
//            execute(cliConfiguration);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }



    private static void execute(RestService restService) {

        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        System.out.println(cliConfiguration.toString());

//        RestService restService = new Noop();
//        RestService restService = new Login("dummy", "secret");

        HttpClient client = HttpClientCreator.create(cliConfiguration);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, cliConfiguration);

        HttpHeaders httpHeadersRequest = httpRequest.headers();
        System.out.println("Request-Headers:");
        HeaderHelper.showHeaders(httpHeadersRequest);

        HttpResponse<String> response = null;
        // TODO ErrorHandling optimieren
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());

        }

        HttpHeaders httpHeaderResponse = response.headers();
        System.out.println("Response-Headers:");
        HeaderHelper.showHeaders(httpHeaderResponse);

        System.out.println(response.statusCode());
        System.out.println(response.body());

    }

}
