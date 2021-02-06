package org.mentalizr.cli.commands.user.patient;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.cli.fileSystem.PatientAddSOFS;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientAddService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientAddSO;
import org.mentalizr.serviceObjects.userManagement.PatientAddSOX;

import java.nio.file.Paths;

public class PatientAddCommand extends AbstractCommandExecutor {

    public PatientAddCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

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

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        PatientAddSO patientAddSOBack = new PatientAddService(patientAddSO, restCallContext)
                .call();

        System.out.println("[OK] Patient [" + patientAddSOBack.getUsername() + "] added with UUID: [" + patientAddSOBack.getUuid() + "]");
    }

    private PatientAddSO fromPrompt() throws UserAbortedException {

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
        if (!confirm) throw new UserAbortedException();

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

}
