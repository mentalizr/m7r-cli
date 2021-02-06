package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.cli.fileSystem.TherapistAddSOFS;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.TherapistAddService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSOX;

import java.nio.file.Paths;
import java.util.List;

public class TherapistAddCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        TherapistAddSO therapistAddSO;

        if (optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            String fileName = optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE);
            therapistAddSO = TherapistAddSOFS.fromFile(Paths.get(fileName));
        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_SHOW_TEMPLATE)) {
            System.out.println(getTemplate());
            return;
        } else {
            therapistAddSO = fromPrompt();
        }

        TherapistAddSO therapistAddSOBack = callTherapistAdd(therapistAddSO, cliContext);

        System.out.println("[OK] Therapist [" + therapistAddSOBack.getUsername() + "] added with UUID: [" + therapistAddSOBack.getUuid() + "]");
    }

    private TherapistAddSO fromPrompt() throws CommandExecutorException {

        TherapistAddSO therapistAddSO = new TherapistAddSO();

        boolean active = ConsoleReader.promptForYesOrNo("active? (y/n): ");
        therapistAddSO.setActive(active);

        String username = ConsoleReader.promptForMandatoryString("Username: ");
        therapistAddSO.setUsername(username);

        String title = ConsoleReader.promptForOptionalString("Title (optional): ");
        therapistAddSO.setTitle(title);

        String firstname = ConsoleReader.promptForMandatoryString("Firstname: ");
        therapistAddSO.setFirstname(firstname);

        String lastname = ConsoleReader.promptForMandatoryString("Lastname: ");
        therapistAddSO.setLastname(lastname);

        String email = ConsoleReader.promptForMandatoryString("Email: ");
        therapistAddSO.setEmail(email);

        String genderString = ConsoleReader.promptForOptionString("Gender (m,f,x): ", "m", "f", "x");
        int gender = 0;
        switch (genderString) {
            case "f":
                gender=0;
                break;
            case "m":
                gender=1;
                break;
            case "x":
                gender=2;
                break;
        }
        therapistAddSO.setGender(gender);

        String password = ConsoleReader.promptForMandatoryString("Password: ");
        therapistAddSO.setPassword(password);

        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new CommandExecutorException(new UserAbortedException());

        return therapistAddSO;
    }

    private String getTemplate() {

        TherapistAddSO therapistAddSO = new TherapistAddSO();
        therapistAddSO.setActive(true);
        therapistAddSO.setTitle("M.Sc.");
        therapistAddSO.setUsername("jdummy");
        therapistAddSO.setFirstname("Joe");
        therapistAddSO.setLastname("Dummy");
        therapistAddSO.setEmail("joe.dummy@example.org");
        therapistAddSO.setGender(1);
        therapistAddSO.setPassword("topsecret");

        return TherapistAddSOX.toJsonWithFormatting(therapistAddSO);
    }

    private TherapistAddSO callTherapistAdd(TherapistAddSO therapistAddSO, CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new TherapistAddService(therapistAddSO, restCallContext).call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }

    }

}
