package org.mentalizr.cli.commands.sessionManagement;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.utils.io.textfile.TextFile;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.LoginService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mentalizr.cli.M7rCli.*;

public class LoginCommandExecutor implements CommandExecutor {

    private String user;
    private String password;

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);

        CommandExecutorHelper.checkedInit(cliContext);

        if (optionParserResultSpecific.hasOption(OPTION__CREDENTIAL_FILE)) {
            obtainCredentialsFromFile();
        } else {
            obtainCredentialsFromCommandLine(optionParserResultSpecific);
        }

        String body = callLoginService(cliContext);

        System.out.println("[OK] Successfully logged in to " + cliContext.getCliConfiguration().getServer());

        debugOut(body, cliContext);

    }

    private void obtainCredentialsFromFile() {
        File credentialFile = new File(CliConfigurationFiles.getConfigDirCLI(), "credentials.txt");
        if (!credentialFile.exists())
            throw new CliException("Credential file '" + credentialFile.getAbsolutePath() + "' not found.");
        try {
            List<String> credentialFileStrings = TextFile.getLinesAsStrings(credentialFile);
            if (credentialFileStrings.size() < 2)
                throw new CliException("Could not read credential file. Content is malformed.");
            this.user = credentialFileStrings.get(0);
            this.password = credentialFileStrings.get(1);

        } catch (IOException e) {
            throw new CliException("Could not read credential file '" + credentialFile + "'. " + e.getMessage(), e);
        }
    }

    private void obtainCredentialsFromCommandLine(OptionParserResult optionParserResultSpecific) {
        if (optionParserResultSpecific.hasOption(ID_USER)) {
            user = optionParserResultSpecific.getValue(ID_USER);
        } else {
            System.out.print("user: ");
            user = System.console().readLine();
        }

        if (optionParserResultSpecific.hasOption(ID_PASSWORD)) {
            password = optionParserResultSpecific.getValue(ID_PASSWORD);
        } else {
            System.out.print("password: ");
            password = new String(System.console().readPassword());
        }
    }

    private String callLoginService(CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new LoginService(user, password, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

    private void debugOut(String body, CliContext cliContext) {
        if (cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }
    }

}
