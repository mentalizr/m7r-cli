package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class TherapistRestoreCommand extends CommandExecutor {

    public TherapistRestoreCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        TherapistSO therapistSO;

        System.out.println("Execute TherapistRestoreCommand");

        if (optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            // TODO
            System.out.println("Restore Therapist from file: " + optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE));
        }
//        else if (optionParserResultSpecific.hasOption(M7rCli.ID_SHOW_TEMPLATE)) {
//            System.out.println(getTemplate());
//            return;
//        } else {
//            therapistSO = fromPrompt();
//        }

//        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
//        RestService restService = new AddTherapistService(therapistSO);
//        String body = RestServiceCaller.call(restCallContext, restService);
//
//        Jsonb jsonb = JsonbBuilder.create();
//        TherapistSO therapistSOBack = jsonb.fromJson(body, TherapistSO.class);
//
//        System.out.println("[OK] User + '" + therapistSOBack.getUsername() + "' added as therapist with UUID: " + therapistSOBack.getUuid());

    }


    private TherapistSO fromPrompt() throws UserAbortedException {

        TherapistSO therapistSO = new TherapistSO();

        boolean active = ConsoleReader.promptForYesOrNo("active? (y/n): ");
        therapistSO.setActive(active);

        String username = ConsoleReader.promptForMandatoryString("Username: ");
        therapistSO.setUsername(username);

        String title = ConsoleReader.promptForOptionalString("Title (optional): ");
        therapistSO.setTitle(title);

        String firstname = ConsoleReader.promptForMandatoryString("Firstname: ");
        therapistSO.setFirstname(firstname);

        String lastname = ConsoleReader.promptForMandatoryString("Lastname: ");
        therapistSO.setLastname(lastname);

        String email = ConsoleReader.promptForMandatoryString("Email: ");
        therapistSO.setEmail(email);

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
        therapistSO.setGender(gender);

        String password = ConsoleReader.promptForMandatoryString("Password: ");
        therapistSO.setPassword(password);

        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new UserAbortedException();

        return therapistSO;
    }

    private String getTemplate() {

        TherapistSO therapistSO = new TherapistSO();
        therapistSO.setActive(true);
        therapistSO.setTitle("M.Sc.");
        therapistSO.setUsername("jdummy");
        therapistSO.setFirstname("Joe");
        therapistSO.setLastname("Dummy");
        therapistSO.setEmail("joe.dummy@example.org");
        therapistSO.setGender(1);
        therapistSO.setPassword("topsecret");

        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

        return jsonb.toJson(therapistSO);
    }
}
