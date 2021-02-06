package org.mentalizr.cli;

import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.mentalizr.cli.commands.*;
import org.mentalizr.cli.commands.backup.BackupCommand;
import org.mentalizr.cli.commands.backup.RecoverCommand;
import org.mentalizr.cli.commands.program.ProgramAddCommand;
import org.mentalizr.cli.commands.program.ProgramDeleteCommand;
import org.mentalizr.cli.commands.program.ProgramShowCommand;
import org.mentalizr.cli.commands.sessionManagement.*;
import org.mentalizr.cli.commands.user.accessKey.AccessKeyCreateCommand;
import org.mentalizr.cli.commands.user.accessKey.AccessKeyDeleteCommand;
import org.mentalizr.cli.commands.user.accessKey.AccessKeyShowCommand;
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
    public static final String ID_VERSION = "version";
    public static final String ID_HELP = "help";
    public static final String ID_USER = "login";
    public static final String ID_PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String NOOP = "noop";
    public static final String INIT = "init";
    public static final String CONFIG = "config";
    public static final String EDIT = "edit";
    public static final String SHOW = "show";
    public static final String HELP = "help";
    public static final String STATUS = "status";
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
    public static final String OPTION__ACCESS_KEY = "accesskey";
    public static final String WIPE = "wipe";
    public static final String ACCESS_KEY = "accesskey";
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

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build(ID_DEBUG))
                .add(new OptionBuilder().withLongName("silent").withDescription("silent").build(ID_SILENT))
                .add(new OptionBuilder().withLongName("stacktrace").build(ID_STACKTRACE))
                .add(new OptionBuilder().withShortName('v').withLongName("version").withDescription("show version").build(ID_VERSION))
                .add(new OptionBuilder().withShortName('h').withLongName("help").withDescription("show help").build(ID_HELP));

        Commands commands = new Commands();

        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withCommandExecutor(new DefaultCommandExecutor())
                .build();
        commands.setDefaultCommand(defaultCommand);

        commands.add(new CommandSequenceBuilder()
                .addCommands(LOGIN)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user").build(ID_USER))
                        .add(new OptionBuilder().withLongName("password").withShortName('p').hasArgument().withDescription("password").build(ID_PASSWORD))
                        .add(new OptionBuilder().withLongName("credential-file").withShortName('c').withDescription("use credential file").build(OPTION__CREDENTIAL_FILE)))
                .withCommandExecutor(new LoginCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(LOGOUT)
                .withCommandExecutor(new LogoutCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(INIT)
                .withCommandExecutor(new InitCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(CONFIG, SHOW)
                .withCommandExecutor(new ShowConfigCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(CONFIG, EDIT)
                .withCommandExecutor(new EditConfigCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(HELP)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(NOOP)
                .withCommandExecutor(new NoopCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(STATUS)
                .withCommandExecutor(new StatusCommandExecutor())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(BACKUP)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(RECOVER)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("directory").withShortName('d').hasArgument().withDescription("directory").build(OPTION__DIRECTORY)))
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(WIPE)
                .build());

//        Commands commands = new Commands().add(LOGIN).withSpecificOptions(
//                new Options()
//                        .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user").build(ID_USER))
//                        .add(new OptionBuilder().withLongName("password").withShortName('p').hasArgument().withDescription("password").build(ID_PASSWORD))
//                        .add(new OptionBuilder().withLongName("credential-file").withShortName('c').withDescription("use credential file").build(OPTION__CREDENTIAL_FILE))
//        )
//                .root().add(LOGOUT)
//                .root().add(INIT)
//                .root().add(CONFIG).addOneOf(SHOW, EDIT)
//                .root().add(HELP)
//                .root().add(VERSION)
//                .root().add(NOOP)
//                .root().add(STATUS)
//                .root().add(BACKUP)
//                .root().add(RECOVER).withSpecificOptions(
//                        new Options()
//                                .add(new OptionBuilder().withLongName("directory").withShortName('d').hasArgument().withDescription("directory").build(OPTION__DIRECTORY))
//                )
//                .root().add(WIPE);

        Options specificOptionsUserAdd = new Options()
                .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
                .add(new OptionBuilder().withLongName("show-template").withDescription("show json template").build(ID_SHOW_TEMPLATE));

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, ADD)
                .withSpecificOptions(specificOptionsUserAdd)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, ADD)
                .withSpecificOptions(specificOptionsUserAdd)
                .withCommandExecutor(new TherapistAddCommand())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, ADD)
                .withSpecificOptions(specificOptionsUserAdd)
                .build());

        Options specificOptionsUserRestore = new Options()
                .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE));

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, RESTORE)
                .withSpecificOptions(specificOptionsUserRestore)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, RESTORE)
                .withSpecificOptions(specificOptionsUserRestore)
                .withCommandExecutor(new TherapistRestoreCommand())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, RESTORE)
                .withSpecificOptions(specificOptionsUserRestore)
                .build());

        Options specificOptionsUser = new Options()
                .add(new OptionBuilder().withLongName("uuid").withShortName('i').hasArgument().withDescription("uuid").build(ID_UUID))
                .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user name").build(ID_USER));

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, GET)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, DELETE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, ACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, DEACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, GET)
                .withSpecificOptions(specificOptionsUser)
                .withCommandExecutor(new TherapistGetCommand())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, DELETE)
                .withSpecificOptions(specificOptionsUser)
                .withCommandExecutor(new TherapistDeleteCommand())
                .build());

        // TODO CommandExecutor
        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, ACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        // TODO CommandExecutor
        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, DEACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, GET)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, DELETE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, ACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, DEACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, SHOW)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, SHOW)
                .withCommandExecutor(new TherapistShowCommand())
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, SHOW)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PROGRAM, ADD)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PROGRAM, DELETE)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("program").withShortName('p').hasArgument().withDescription("program").build(OPTION__PROGRAM)))
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PROGRAM, SHOW)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ACCESS_KEY, CREATE)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ACCESS_KEY, SHOW)
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ACCESS_KEY, DELETE)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("accessKey").withShortName('a').hasArgument().withDescription("access key").build(OPTION__ACCESS_KEY)))
                .build());


//        Commands userCommands = commands.root().addOneOf(PATIENT, THERAPIST, ADMIN);
//        userCommands.add(ADD).withSpecificOptions(
//                new Options()
//                        .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
//                        .add(new OptionBuilder().withLongName("show-template").withDescription("show json template").build(ID_SHOW_TEMPLATE))
//        );
//        userCommands.add(RESTORE).withSpecificOptions(
//                new Options()
//                    .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
//        );
//        userCommands.addOneOf(GET, DELETE, ACTIVATE, DEACTIVATE).withSpecificOptions(
//                new Options()
//                        .add(new OptionBuilder().withLongName("uuid").withShortName('i').hasArgument().withDescription("uuid").build(ID_UUID))
//                        .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user name").build(ID_USER))
//        );
//        userCommands.add(SHOW);

//        Commands programCommands = commands.root().add(PROGRAM);
//        programCommands.add(ADD);
//        programCommands.add(DELETE).withSpecificOptions(
//                new Options()
//                        .add(new OptionBuilder().withLongName("program").withShortName('p').hasArgument().withDescription("program").build(OPTION__PROGRAM))
//        );
//        programCommands.add(SHOW);

//        Commands accessKeyCommands = commands.root().add(ACCESS_KEY);
//        accessKeyCommands.add(CREATE);
//        accessKeyCommands.add(SHOW);
//        accessKeyCommands.add(DELETE).withSpecificOptions(
//                new Options()
//                        .add(new OptionBuilder().withLongName("accessKey").withShortName('a').hasArgument().withDescription("access key").build(OPTION__ACCESS_KEY))
//        );
//
//        return new CommandLineInterfaceBuilder()
//                .withGlobalOptions(new Options()
//                        .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build(ID_DEBUG))
//                        .add(new OptionBuilder().withLongName("silent").withDescription("silent").build(ID_SILENT))
//                        .add(new OptionBuilder().withLongName("stacktrace").build(ID_STACKTRACE))
//                )
//                .withCommands(commands)
//                .build();

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build();
    }

    public static void main(String[] args) {

        CommandLineInterface cli = prepareCLI();
        CliContext cliContext = null;
        try {
            ParserResult parserResult = cli.execute(args);

            // TODO
            System.exit(0);

            cliContext = CliContext.getInstance(parserResult);

//            CliCallGlobalConfiguration cliCallGlobalConfiguration = processParserResultGlobalOptions(parserResult.getOptionParserResultGlobal());
            List<String> commandList = parserResult.getCommandList();
//            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();
//
//            cliContext = new CliContext(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);

//            if (commandList.get(0).equals(INIT)) {
//                InitCommandExecutor initCommand = new InitCommandExecutor(cliContext);
//                initCommand.execute();
//            }

//            if (commandList.get(0).equals(CONFIG)) {
//                if (commandList.get(1).equals(SHOW)) {
//                    ShowConfigCommandExecutor showConfigCommand = new ShowConfigCommandExecutor(cliContext);
//                    showConfigCommand.execute();
//                } else {
//                    EditConfigCommandExecutor editConfigCommand = new EditConfigCommandExecutor(cliContext);
//                    editConfigCommand.execute();
//                }
//            }

//            if (commandList.get(0).equals(THERAPIST)) {
//                String subCommand = commandList.get(1);
//                switch (subCommand) {
//                    case ADD:
//                        new TherapistAddCommand(cliContext).execute();
//                        break;
//                    case RESTORE:
//                        new TherapistRestoreCommand(cliContext).execute();
//                        break;
//                    case GET:
//                        new TherapistGetCommand(cliContext).execute();
//                        break;
//                    case SHOW:
//                        new TherapistShowCommand(cliContext).execute();
//                        break;
//                    case DELETE:
//                        new TherapistDeleteCommand(cliContext).execute();
//                        break;
//                }
//            }

            if (commandList.get(0).equals(PATIENT)) {
                String subCommand = commandList.get(1);
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
                    case DELETE:
                        new AccessKeyDeleteCommand(cliContext).execute();
                        break;
                }
            }

            if (commandList.get(0).equals(BACKUP)) {
                AbstractCommandExecutor commandExecutor = new BackupCommand(cliContext);
                commandExecutor.execute();
            }

            if (commandList.get(0).equals(RECOVER)) {
                AbstractCommandExecutor commandExecutor = new RecoverCommand(cliContext);
                commandExecutor.execute();
            }

            if (commandList.get(0).equals(WIPE)) {
                AbstractCommandExecutor commandExecutor = new WipeCommand(cliContext);
                commandExecutor.execute();
            }

        } catch (UnrecognizedArgumentException e) {
            System.out.println("[Error] m7r syntax error. " + e.getMessage());
            System.out.println("m7r " + e.getArgsAsString());
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
        } catch (CommandExecutorException e) {
            if (e.getCause() instanceof RestServiceHttpException) {
                RestServiceHttpException restServiceHttpException = (RestServiceHttpException) e.getCause();
                System.out.println("[ERROR] Http-Error " + restServiceHttpException.getStatusCode() + ": " + e.getMessage());
                if (cliContext.showStacktrace()) {
                    e.printStackTrace();
                }
                System.exit(ExitStatus.HTTP_OTHER_ERROR);
            } else if (e.getCause() instanceof RestServiceConnectionException) {
                System.out.println("[ERROR] " + e.getMessage());
                System.out.println("Cause: " + e.getCause().getMessage());
                if (cliContext.showStacktrace()) {
                    e.printStackTrace();
                }
                System.exit(ExitStatus.CONNECTION_ERROR);
            } else if (e.getCause() instanceof UserAbortedException) {
                System.out.println("[Abort] Aborted by user.");
            } else {
                Throwable cause = e.getCause();
                if (cause != null) {
                    System.out.println("[ERROR] " + cause.getMessage());
                } else {
                    System.out.println("[ERROR] " + e.getMessage());
                }
                if (cliContext.showStacktrace()) {
                    e.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

}
