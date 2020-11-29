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
import org.mentalizr.cli.commands.accessKey.AccessKeyCreateCommand;
import org.mentalizr.cli.commands.accessKey.AccessKeyShowCommand;
import org.mentalizr.cli.commands.backup.BackupCommand;
import org.mentalizr.cli.commands.backup.RecoverCommand;
import org.mentalizr.cli.commands.program.ProgramAddCommand;
import org.mentalizr.cli.commands.program.ProgramDeleteCommand;
import org.mentalizr.cli.commands.program.ProgramShowCommand;
import org.mentalizr.cli.commands.sessionManagement.LoginCommand;
import org.mentalizr.cli.commands.sessionManagement.LogoutCommand;
import org.mentalizr.cli.commands.sessionManagement.NoopCommand;
import org.mentalizr.cli.commands.sessionManagement.StatusCommand;
import org.mentalizr.cli.commands.user.patient.*;
import org.mentalizr.cli.commands.user.therapist.*;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceBusinessException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceServerException;

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
    public static final String BACKUP = "backup";
    public static final String RECOVER = "recover";
    public static final String PROGRAM = "program";
    public static final String ID_FROM_FILE = "fromFile";
    public static final String ID_SHOW_TEMPLATE = "showTemplate";
    public static final String ID_UUID = "uuid";
    public static final String OPTION__CREDENTIAL_FILE = "credentialFile";
    public static final String OPTION__DIRECTORY = "directory" ;
    public static final String OPTION__PROGRAM = "program";
    public static final String WIPE = "wipe";
    public static final String ACCESS_KEY = "accessKey";
    public static final String CREATE = "create";
    public static final String OPTION__TO_FILE = "file";


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
                .root().add(STATUS)
                .root().add(BACKUP)
                .root().add(RECOVER).withSpecificOptions(
                        new Options()
                                .add(new OptionBuilder().withLongName("directory").withShortName('d').hasArgument().withDescription("directory").build(OPTION__DIRECTORY))
                )
                .root().add(WIPE)
                .root().add(ACCESS_KEY).addOneOf(CREATE, SHOW);

        Commands userCommands = commands.root().add(USER).addOneOf(PATIENT, THERAPIST, ADMIN);
        userCommands.add(ADD).withSpecificOptions(
                new Options()
                        .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
                        .add(new OptionBuilder().withLongName("show-template").withDescription("show json template").build(ID_SHOW_TEMPLATE))
        );
        userCommands.add(RESTORE).withSpecificOptions(
                new Options()
                    .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
        );
        userCommands.addOneOf(GET, DELETE, ACTIVATE, DEACTIVATE).withSpecificOptions(
                new Options()
                        .add(new OptionBuilder().withLongName("uuid").withShortName('i').hasArgument().withDescription("uuid").build(ID_UUID))
                        .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user name").build(ID_USER))
        );
        userCommands.add(SHOW);

        Commands programCommands = commands.root().add(PROGRAM);
        programCommands.add(ADD);
        programCommands.add(DELETE).withSpecificOptions(
                new Options()
                .add(new OptionBuilder().withLongName("program").withShortName('p').hasArgument().withDescription("program").build(OPTION__PROGRAM))
        );
        programCommands.add(SHOW);

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

            if (commandList.get(0).equals(USER) && commandList.get(1).equals(THERAPIST)) {
                String subCommand = commandList.get(2);
                switch (subCommand) {
                    case ADD:
                        new TherapistAddCommand(cliContext).execute();
                        break;
                    case RESTORE:
                        new TherapistRestoreCommand(cliContext).execute();
                        break;
                    case GET:
                        new TherapistGetCommand(cliContext).execute();
                        break;
                    case SHOW:
                        new TherapistShowCommand(cliContext).execute();
                        break;
                    case DELETE:
                        new TherapistDeleteCommand(cliContext).execute();
                        break;
                }
            }

            if (commandList.get(0).equals(USER) && commandList.get(1).equals(PATIENT)) {
                String subCommand = commandList.get(2);
                switch (subCommand) {
                    case ADD:
                        new PatientAddCommand(cliContext).execute();
                        break;
                    case RESTORE:
                        new PatientRestoreCommand(cliContext).execute();
                        break;
                    case GET:
                        new PatientGetCommand(cliContext).execute();
                        break;
                    case SHOW:
                        new PatientShowCommand(cliContext).execute();
                        break;
                    case DELETE:
                        new PatientDeleteCommand(cliContext).execute();
                        break;
                }
            }

            if (commandList.get(0).equals(PROGRAM)) {
                String subCommand = commandList.get(1);
                switch (subCommand) {
                    case ADD:
                        new ProgramAddCommand(cliContext).execute();
                        break;
                    case DELETE:
                        new ProgramDeleteCommand(cliContext).execute();
                        break;
                    case SHOW:
                        new ProgramShowCommand(cliContext).execute();
                        break;
                }
            }

            if (commandList.get(0).equals(ACCESS_KEY)) {
                String subCommand = commandList.get(1);
                switch (subCommand) {
                    case CREATE:
                        new AccessKeyCreateCommand(cliContext).execute();
                        break;
                    case SHOW:
                        new AccessKeyShowCommand(cliContext).execute();
                        break;
                }
            }

            if (commandList.get(0).equals(BACKUP)) {
                CommandExecutor commandExecutor = new BackupCommand(cliContext);
                commandExecutor.execute();
            }

            if (commandList.get(0).equals(RECOVER)) {
                CommandExecutor commandExecutor = new RecoverCommand(cliContext);
                commandExecutor.execute();
            }

            if (commandList.get(0).equals(WIPE)) {
                CommandExecutor commandExecutor = new WipeCommand(cliContext);
                commandExecutor.execute();
            }

        } catch (UnrecognizedArgumentException e) {
            System.out.println("[Error] m7r syntax error. " + e.getMessage());
            System.out.println("m7r " + ArgsHelper.getArgsString(args));
            System.out.println("    " + e.getArgumentPointerString());
            System.exit(ExitStatus.M7R_SYNTAX_ERROR);

        } catch (RestServiceBusinessException e) {
            System.out.println("[ERROR] " + e.getMessage());
            if (cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            }
            System.exit(ExitStatus.HTTP_OTHER_ERROR);

        } catch (RestServiceServerException e) {
            if (!cliContext.getCliCallGlobalConfiguration().isDebug()) {
                System.out.println("[ERROR] Server error. Call with --debug option for more info.");
            } else {
                System.out.println("[ERROR] Server error.");
                System.out.println("Cause of server Error: " + e.getMessage());
            }
            if (cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            }
            System.exit(ExitStatus.HTTP_OTHER_ERROR);


        } catch (RestServiceHttpException e) {
            System.out.println("[ERROR] Http-Error " + e.getStatusCode() + ": " + e.getMessage());
            if (cliContext.getCliCallGlobalConfiguration().isStacktrace()) {
                e.printStackTrace();
            }
            System.exit(ExitStatus.HTTP_OTHER_ERROR);

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
