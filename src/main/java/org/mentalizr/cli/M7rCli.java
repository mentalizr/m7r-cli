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
import org.mentalizr.cli.commands.sessionManagement.LoginCommand;
import org.mentalizr.cli.commands.sessionManagement.LogoutCommand;
import org.mentalizr.cli.commands.sessionManagement.NoopCommand;
import org.mentalizr.cli.commands.sessionManagement.StatusCommand;
import org.mentalizr.cli.commands.user.therapist.TherapistAddCommand;
import org.mentalizr.cli.commands.user.therapist.TherapistGetCommand;
import org.mentalizr.cli.commands.user.therapist.TherapistRestoreCommand;
import org.mentalizr.cli.commands.user.therapist.TherapistShowCommand;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.util.List;

public class M7rCli {

    public static final String ID_DEBUG = "debug";
    public static final String ID_STACKTRACE = "stacktrace";
    public static final String ID_SILENT = "silent";

    public static final String LOGIN = "login";
    public static final String ID_USER = "login";
    public static final String ID_PASSWORD = "password";
    public static final String LOGOUT = "logout";
    public static final String NOOP = "noop";
    public static final String INIT = "init";
    public static final String CONFIG = "config";
    public static final String EDIT = "edit";
    public static final String SHOW = "show";
    public static final String VERSION = "version";
    public static final String HELP = "help";
    public static final String STATUS = "status";
    public static final String USER = "user";
    public static final String THERAPIST = "therapist";
    public static final String PATIENT = "patient";
    public static final String ADMIN = "admin";
    public static final String ADD = "add";
    public static final String RESTORE = "restore";
    public static final String GET = "get";
    public static final String DELETE = "delete";
    public static final String DEACTIVATE = "deactivate";
    public static final String ACTIVATE = "activate";

    public static final String ID_ACTIVE = "active";

    public static final String ID_EMAIL = "email";
    public static final String ID_TITLE = "title";
    public static final String ID_FIRSTNAME = "firstname";
    public static final String ID_LASTNAME = "lastname";
    public static final String ID_GENDER = "gender";
    public static final String ID_FROM_FILE = "fromFile";
    public static final String ID_SHOW_TEMPLATE = "showTemplate";
    public static final String ID_UUID = "uuid";
    public static final String OPTION__CREDENTIAL_FILE = "credentialFile";


    private static CliCallGlobalConfiguration processParserResultGlobalOptions(OptionParserResult optionParserResult) {
        CliCallGlobalConfiguration cliCallGlobalConfiguration = new CliCallGlobalConfiguration();
        if (optionParserResult.hasOption(ID_DEBUG)) cliCallGlobalConfiguration.setDebug(true);
        if (optionParserResult.hasOption(ID_SILENT)) cliCallGlobalConfiguration.setSilent(true);
        if (optionParserResult.hasOption(ID_STACKTRACE)) cliCallGlobalConfiguration.setStacktrace(true);
        return cliCallGlobalConfiguration;
    }

    private static CommandLineInterface prepareCLI() {

        Commands commands = new Commands().add(LOGIN).withSpecificOptions(
                new Options()
                .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user").build(ID_USER))
                .add(new OptionBuilder().withLongName("password").withShortName('p').hasArgument().withDescription("password").build(ID_PASSWORD))
                .add(new OptionBuilder().withLongName("credential-file").withShortName('c').withDescription("use credential file").build(OPTION__CREDENTIAL_FILE))
        )
                .root().add(LOGOUT)
                .root().add(INIT)
                .root().add(CONFIG).addOneOf(SHOW, EDIT)
                .root().add(HELP)
                .root().add(VERSION)
                .root().add(NOOP)
                .root().add(STATUS);
        Commands userCommands = commands.root().add(USER);
        userCommands.add(ADD).addOneOf(PATIENT, THERAPIST, ADMIN).withSpecificOptions(
                new Options()
                        .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
                        .add(new OptionBuilder().withLongName("show-template").withDescription("show json template").build(ID_SHOW_TEMPLATE))
        );
        userCommands.add(RESTORE).addOneOf(PATIENT, THERAPIST, ADMIN).withSpecificOptions(
                new Options()
                    .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
        );
        userCommands.addOneOf(GET, DELETE, ACTIVATE, DEACTIVATE).addOneOf(PATIENT, THERAPIST, ADMIN).withSpecificOptions(
                new Options()
                        .add(new OptionBuilder().withLongName("uuid").withShortName('i').hasArgument().withDescription("uuid").build(ID_UUID))
                        .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user name").build(ID_USER))
        );
        userCommands.add(SHOW).addOneOf(PATIENT, THERAPIST, ADMIN);

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build(ID_DEBUG))
                        .add(new OptionBuilder().withLongName("silent").withDescription("silent").build(ID_SILENT))
                        .add(new OptionBuilder().withLongName("stacktrace").build(ID_STACKTRACE))
                )
                .withCommands(commands)
                .build();

    }

    public static void main(String[] args) {

        CommandLineInterface cli = prepareCLI();
        CliContext cliContext = null;
        try {
            ParserResult parserResult = cli.parse(args);

            CliCallGlobalConfiguration cliCallGlobalConfiguration = processParserResultGlobalOptions(parserResult.getOptionParserResultGlobal());
            List<String> commandList = parserResult.getCommandList();
            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();

            cliContext = new CliContext(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);

            if (parserResult.getCommandList().get(0).equals(VERSION)) {
                VersionCommand versionCommand = new VersionCommand(cliContext);
                versionCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(HELP)) {
                HelpCommand helpCommand = new HelpCommand(cliContext);
                helpCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(LOGIN)) {
                LoginCommand loginCommand = new LoginCommand(cliContext);
                loginCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(LOGOUT)) {
                LogoutCommand logoutCommand = new LogoutCommand(cliContext);
                logoutCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(NOOP)) {
                NoopCommand noopCommand = new NoopCommand(cliContext);
                noopCommand.execute();
            }

            if (parserResult.getCommandList().get(0).equals(STATUS)) {
                StatusCommand statusCommand = new StatusCommand(cliContext);
                statusCommand.execute();
            }

            if (commandList.get(0).equals(INIT)) {
                InitCommand initCommand = new InitCommand(cliContext);
                initCommand.execute();
            }

            if (commandList.get(0).equals(CONFIG)) {
                if (commandList.get(1).equals(SHOW)) {
                    ShowConfigCommand showConfigCommand = new ShowConfigCommand(cliContext);
                    showConfigCommand.execute();
                } else {
                    EditConfigCommand editConfigCommand = new EditConfigCommand(cliContext);
                    editConfigCommand.execute();
                }
            }

            if (commandList.get(0).equals(USER) && commandList.get(1).equals(ADD) && commandList.get(2).equals(THERAPIST)) {
                    TherapistAddCommand therapistAddCommand = new TherapistAddCommand(cliContext);
                    therapistAddCommand.execute();;
            }

            if (commandList.get(0).equals(USER) && commandList.get(1).equals(RESTORE) && commandList.get(2).equals(THERAPIST)) {
                TherapistRestoreCommand therapistRestoreCommand = new TherapistRestoreCommand(cliContext);
                therapistRestoreCommand.execute();
            }

            if (commandList.get(0).equals(USER) && commandList.get(1).equals(GET) && commandList.get(2).equals(THERAPIST)) {
                TherapistGetCommand therapistGetCommand = new TherapistGetCommand(cliContext);
                therapistGetCommand.execute();
            }

            if (commandList.get(0).equals(USER) && commandList.get(1).equals(SHOW) && commandList.get(2).equals(THERAPIST)) {
                TherapistShowCommand therapistShowCommand = new TherapistShowCommand(cliContext);
                therapistShowCommand.execute();
            }

        } catch (UnrecognizedArgumentException e) {
            System.out.println("[Error] m7r syntax error. " + e.getMessage());
            System.out.println("m7r " + ArgsHelper.getArgsString(args));
            System.out.println("    " + e.getArgumentPointerString());
            System.exit(ExitStatus.M7R_SYNTAX_ERROR);

        } catch (RestServiceHttpException e) {
            switch (e.getStatusCode()) {
                case 401:
                    System.out.println("[ERROR] Authentication failed.");
                    System.exit(ExitStatus.HTTP_AUTHENTICATION_ERROR);
                default:
                    String message = e.getMessage() != null ? e.getMessage() : "";
                    System.out.println("[ERROR] HttpError " + e.getStatusCode() + " " + message);
                    System.exit(ExitStatus.HTTP_OTHER_ERROR);
            }

        } catch (RestServiceConnectionException e) {
            System.out.println("[ERROR] " + e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
            if (cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            }
            System.exit(ExitStatus.CONNECTION_ERROR);

        } catch (CliException e) {
            System.out.println("[ERROR] " + e.getMessage());
            if (cliContext != null && cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            }
            System.exit(ExitStatus.INTERNAL_ERROR);

        } catch (RuntimeException e) {
            System.out.println("[INTERNAL ERROR] " + e.getMessage());
            if (cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            } else {
                System.out.println("Consider calling command with --stacktrace global option.");
            }

        } catch (UserAbortedException e) {
            System.out.println("[Abort] Aborted by user.");
        }

    }

}
