package org.mentalizr.cli.commands.user.patient;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.cli.fileSystem.PatientAddSOFS;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientAddService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientAddSO;
import org.mentalizr.serviceObjects.userManagement.PatientAddSOX;

import java.nio.file.Paths;
import java.util.List;

public class PatientAddCommand implements CommandExecutor {

    @Override
    public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(optionParserResultGlobal, commandList, optionParserResultSpecific);
        CommandExecutorHelper.checkedInit(cliContext);

        PatientAddSO patientAddSO;

        if (optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            String fileName = optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE);
            patientAddSO = PatientAddSOFS.fromFile(Paths.get(fileName));
        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_SHOW_TEMPLATE)) {
            System.out.println(getTemplate());
            return;
        } else {
            patientAddSO = fromPrompt();
        }

        PatientAddSO patientAddSOBack = callService(patientAddSO, cliContext);

        System.out.println("[OK] Patient [" + patientAddSOBack.getUsername() + "] added with UUID: [" + patientAddSOBack.getUuid() + "]");
    }

    private PatientAddSO fromPrompt() throws CommandExecutorException {

        PatientAddSO patientAddSO = new PatientAddSO();

        boolean active = ConsoleReader.promptForYesOrNo("active? (y/n): ");
        patientAddSO.setActive(active);

        String username = ConsoleReader.promptForMandatoryString("Username: ");
        patientAddSO.setUsername(username);

        String firstname = ConsoleReader.promptForMandatoryString("Firstname: ");
        patientAddSO.setFirstname(firstname);

        String lastname = ConsoleReader.promptForMandatoryString("Lastname: ");
        patientAddSO.setLastname(lastname);

        String email = ConsoleReader.promptForMandatoryString("Email: ");
        patientAddSO.setEmail(email);

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
        patientAddSO.setGender(gender);

        String password = ConsoleReader.promptForMandatoryString("Password: ");
        patientAddSO.setPassword(password);

        String programId = ConsoleReader.promptForMandatoryString("Program: ");
        patientAddSO.setProgramId(programId);

        String therapistId = ConsoleReader.promptForMandatoryString("Therapist ID: ");
        patientAddSO.setTherapistId(therapistId);

        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new CommandExecutorException(new UserAbortedException());

        return patientAddSO;
    }

    private String getTemplate() {

        PatientAddSO patientAddSO = new PatientAddSO();
        patientAddSO.setActive(true);
        patientAddSO.setUsername("");
        patientAddSO.setFirstname("");
        patientAddSO.setLastname("");
        patientAddSO.setEmail("");
        patientAddSO.setGender(1);
        patientAddSO.setPassword("");
        patientAddSO.setProgramId("");
        patientAddSO.setTherapistId("");

        return PatientAddSOX.toJsonWithFormatting(patientAddSO);
    }

    public PatientAddSO callService(PatientAddSO patientAddSO, CliContext cliContext) throws CommandExecutorException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
        try {
            return new PatientAddService(patientAddSO, restCallContext)
                    .call();
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
