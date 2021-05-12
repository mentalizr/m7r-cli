package org.mentalizr.cli;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.InfoDefaultCommand;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.ManOption;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.option.VersionOption;
import org.mentalizr.cli.commands.EditConfigCommandExecutor;
import org.mentalizr.cli.commands.InitCommandExecutor;
import org.mentalizr.cli.commands.ShowConfigCommandExecutor;
import org.mentalizr.cli.commands.WipeCommand;
import org.mentalizr.cli.commands.backup.BackupCommand;
import org.mentalizr.cli.commands.backup.RecoverCommand;
import org.mentalizr.cli.commands.program.ProgramAddCommand;
import org.mentalizr.cli.commands.program.ProgramDeleteCommand;
import org.mentalizr.cli.commands.program.ProgramShowCommand;
import org.mentalizr.cli.commands.sessionManagement.LoginCommandExecutor;
import org.mentalizr.cli.commands.sessionManagement.LogoutCommandExecutor;
import org.mentalizr.cli.commands.sessionManagement.NoopCommandExecutor;
import org.mentalizr.cli.commands.sessionManagement.StatusCommandExecutor;
import org.mentalizr.cli.commands.user.accessKey.AccessKeyCreateCommand;
import org.mentalizr.cli.commands.user.accessKey.AccessKeyDeleteCommand;
import org.mentalizr.cli.commands.user.accessKey.AccessKeyShowCommand;
import org.mentalizr.cli.commands.user.patient.*;
import org.mentalizr.cli.commands.user.therapist.*;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceBusinessException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceServerException;
import org.mentalizr.util.Version;

import java.util.Objects;

public class M7rCli {

    public static final String ID_DEBUG = "debug";
    public static final String ID_STACKTRACE = "stacktrace";
    public static final String ID_SILENT = "silent";
    public static final String ID_USER = "login";
    public static final String ID_PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String NOOP = "noop";
    public static final String INIT = "init";
    public static final String CONFIG = "config";
    public static final String EDIT = "edit";
    public static final String SHOW = "show";
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

    private static Cli prepareCLI() {

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new ManOption())
                .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build(ID_DEBUG))
                .add(new OptionBuilder().withLongName("silent").withDescription("silent").build(ID_SILENT))
                .add(new OptionBuilder().withLongName("stacktrace").build(ID_STACKTRACE));

        Commands commands = new Commands();

        commands.setDefaultCommand(new InfoDefaultCommand());

        commands.add(new CommandSequenceBuilder()
                .addCommands(LOGIN)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user").build(ID_USER))
                        .add(new OptionBuilder().withLongName("password").withShortName('p').hasArgument().withDescription("password").build(ID_PASSWORD))
                        .add(new OptionBuilder().withLongName("credential-file").withShortName('c').withDescription("use credential file").build(OPTION__CREDENTIAL_FILE)))
                .withCommandExecutor(new LoginCommandExecutor())
                .withDescription("Login to mentalizr server.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(LOGOUT)
                .withCommandExecutor(new LogoutCommandExecutor())
                .withDescription("Logout from mentalizr server.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(INIT)
                .withCommandExecutor(new InitCommandExecutor())
                .withDescription("Create local configuration files and directories.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(CONFIG, SHOW)
                .withCommandExecutor(new ShowConfigCommandExecutor())
                .withDescription("Print configuration.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(CONFIG, EDIT)
                .withCommandExecutor(new EditConfigCommandExecutor())
                .withDescription("Edit configuration.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(NOOP)
                .withCommandExecutor(new NoopCommandExecutor())
                .withDescription("No operation except refreshing server session.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(STATUS)
                .withCommandExecutor(new StatusCommandExecutor())
                .withDescription("Show status of connection to server.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(BACKUP)
                .withCommandExecutor(new BackupCommand())
                .withDescription("Create backup of user database to local json files.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(RECOVER)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("directory").withShortName('d').hasArgument().withDescription("directory").build(OPTION__DIRECTORY)))
                .withCommandExecutor(new RecoverCommand())
                .withDescription("Recover user database from local json files.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(WIPE)
                .withCommandExecutor(new WipeCommand())
                .withDescription("Delete all data in user database.")
                .build());

        Options specificOptionsUserAdd = new Options()
                .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE))
                .add(new OptionBuilder().withLongName("show-template").withDescription("show json template").build(ID_SHOW_TEMPLATE));

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, ADD)
                .withSpecificOptions(specificOptionsUserAdd)
                .withCommandExecutor(new PatientAddCommand())
                .withDescription("Add user in role patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, ADD)
                .withSpecificOptions(specificOptionsUserAdd)
                .withCommandExecutor(new TherapistAddCommand())
                .withDescription("Add user in role therapist.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, ADD)
                .withSpecificOptions(specificOptionsUserAdd)
                .withDescription("Add user in role admin.")
                .build());

        Options specificOptionsUserRestore = new Options()
                .add(new OptionBuilder().withLongName("from-file").withShortName('f').hasArgument().withDescription("from file (json)").build(ID_FROM_FILE));

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, RESTORE)
                .withSpecificOptions(specificOptionsUserRestore)
                .withCommandExecutor(new PatientRestoreCommand())
                .withDescription("Restore all users in role patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, RESTORE)
                .withSpecificOptions(specificOptionsUserRestore)
                .withCommandExecutor(new TherapistRestoreCommand())
                .withDescription("Restore all users in role therapist.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, RESTORE)
                .withSpecificOptions(specificOptionsUserRestore)
                .withDescription("Restore all users in role admin")
                .build());

        Options specificOptionsUser = new Options()
                .add(new OptionBuilder().withLongName("uuid").withShortName('i').hasArgument().withDescription("uuid").build(ID_UUID))
                .add(new OptionBuilder().withLongName("user").withShortName('u').hasArgument().withDescription("user name").build(ID_USER));

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, GET)
                .withCommandExecutor(new PatientGetCommand())
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Show patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, DELETE)
                .withSpecificOptions(specificOptionsUser)
                .withCommandExecutor(new PatientDeleteCommand())
                .withDescription("Delete patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, ACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Activate patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, DEACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Deactivate patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, GET)
                .withSpecificOptions(specificOptionsUser)
                .withCommandExecutor(new TherapistGetCommand())
                .withDescription("Show therapist.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, DELETE)
                .withSpecificOptions(specificOptionsUser)
                .withCommandExecutor(new TherapistDeleteCommand())
                .withDescription("Delete therapist.")
                .build());

        // TODO CommandExecutor
        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, ACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Delete therapist.")
                .build());

        // TODO CommandExecutor
        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, DEACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Deactivate therapist.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, GET)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Show admin.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, DELETE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Delete admin.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, ACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Activate admin.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, DEACTIVATE)
                .withSpecificOptions(specificOptionsUser)
                .withDescription("Deactivate admin.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PATIENT, SHOW)
                .withCommandExecutor(new PatientShowCommand())
                .withDescription("Show all patient.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(THERAPIST, SHOW)
                .withCommandExecutor(new TherapistShowCommand())
                .withDescription("Show all therapists.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ADMIN, SHOW)
                .withDescription("Show all admins.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PROGRAM, ADD)
                .withCommandExecutor(new ProgramAddCommand())
                .withDescription("Add program.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PROGRAM, DELETE)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("program").withShortName('p').hasArgument().withDescription("program").build(OPTION__PROGRAM)))
                .withCommandExecutor(new ProgramDeleteCommand())
                .withDescription("Delete program.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(PROGRAM, SHOW)
                .withCommandExecutor(new ProgramShowCommand())
                .withDescription("Show all programs.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ACCESS_KEY, CREATE)
                .withCommandExecutor(new AccessKeyCreateCommand())
                .withDescription("Create access keys.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ACCESS_KEY, SHOW)
                .withCommandExecutor(new AccessKeyShowCommand())
                .withDescription("Show all access keys.")
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands(ACCESS_KEY, DELETE)
                .withSpecificOptions(new Options()
                        .add(new OptionBuilder().withLongName("accessKey").withShortName('a').hasArgument().withDescription("access key").build(OPTION__ACCESS_KEY)))
                .withCommandExecutor(new AccessKeyDeleteCommand())
                .withDescription("Delete access key.")
                .build());

        Version version = Version.getInstance(M7rCli.class);
        CliDescription cliDescription = new CliDescriptionBuilder()
                .withDescription("The mentalizr command line interface.\nhttps://github.com/mentalizr/m7r-cli")
                .withVersionByTag(version.getVersion(), version.getBuild())
                .build("m7r");

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withAutoHelp()
                .build(cliDescription);
    }

    public static void main(String[] args) {

        Cli cli = prepareCLI();

        CliCall cliCall = null;

        try {
            cliCall = cli.parse(args);
        } catch (UnrecognizedArgumentException e) {
            System.out.println("[Error] m7r syntax error. " + e.getMessage());
            System.out.println(e.getCallString());
            System.out.println(e.getCallPointerString());
            System.exit(ExitStatus.M7R_SYNTAX_ERROR);
        }

        boolean showStacktrace = cliCall.getOptionParserResultGlobal().hasOption(ID_STACKTRACE);
        boolean isDebug = cliCall.getOptionParserResultGlobal().hasOption(ID_DEBUG);

        try {

            cli.execute(cliCall);

        } catch (CliException e) {

            System.out.println("[ERROR] " + e.getMessage());
            if (showStacktrace) e.printStackTrace();
            System.exit(ExitStatus.INTERNAL_ERROR);

        } catch (RuntimeException e) {

            System.out.println("[INTERNAL ERROR] " + e.getMessage());
            if (showStacktrace) {
                e.printStackTrace();
            } else {
                System.out.println("Consider calling command with --stacktrace global option.");
            }

        } catch (CommandExecutorException e) {

            if (e.getCause() instanceof RestServiceBusinessException) {

                RestServiceBusinessException restServiceBusinessException = (RestServiceBusinessException) e.getCause();
                System.out.println("[ERROR] " + restServiceBusinessException.getMessage());
                if (showStacktrace) e.printStackTrace();
                System.exit(ExitStatus.HTTP_OTHER_ERROR);

            } else if (e.getCause() instanceof RestServiceServerException) {

                if (isDebug) {
                    RestServiceServerException restServiceServerException = (RestServiceServerException) e.getCause();
                    System.out.println("[ERROR] Server error.");
                    System.out.println("Cause: " + restServiceServerException.getMessage());
                } else {
                    System.out.println("[ERROR] Server error. Call with --debug option for more info.");
                }
                if (showStacktrace) e.printStackTrace();
                System.exit(ExitStatus.HTTP_OTHER_ERROR);

            } else if (e.getCause() instanceof RestServiceHttpException) {

                RestServiceHttpException restServiceHttpException = (RestServiceHttpException) e.getCause();
                System.out.println("[ERROR] Http-Error " + restServiceHttpException.getStatusCode() + ": " + e.getMessage());
                if (showStacktrace) e.printStackTrace();
                System.exit(ExitStatus.HTTP_OTHER_ERROR);

            } else if (e.getCause() instanceof RestServiceConnectionException) {

                System.out.println("[ERROR] " + e.getCause().getMessage());
                if (showStacktrace) e.printStackTrace();
                System.exit(ExitStatus.CONNECTION_ERROR);

            } else if (e.getCause() instanceof UserAbortedException) {

                System.out.println("[Abort] Aborted by user.");
                System.exit(ExitStatus.USER_ABORT);

            } else {
                System.out.println("Unknown error type.");
                Throwable cause = e.getCause();
                System.out.println("[ERROR] " + Objects.requireNonNullElse(cause, e).getMessage());
                if (showStacktrace) {
                    e.printStackTrace();
                }
            }
        }
    }

}
