package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.AddTherapistService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TherapistAddCommand extends CommandExecutor {

    public TherapistAddCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        TherapistAddSO therapistAddSO;

        if (optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            String fileName = optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE);
            therapistAddSO = fromFile(fileName);
        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_SHOW_TEMPLATE)) {
            System.out.println(getTemplate());
            return;
        } else {
            therapistAddSO = fromPrompt();
        }

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        RestService restService = new AddTherapistService(therapistAddSO);
        String body = RestServiceCaller.call(restCallContext, restService);

        Jsonb jsonb = JsonbBuilder.create();
        TherapistAddSO therapistAddSOBack = jsonb.fromJson(body, TherapistAddSO.class);

        System.out.println("[OK] Therapist '" + therapistAddSOBack.getUsername() + "' added with UUID: " + therapistAddSOBack.getUuid());

    }

    private TherapistAddSO fromFile(String fileName) {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new CliException("Specified file '" + fileName + "' not found.");
        }

        String therapistAddSO;
        try {
            therapistAddSO = Files.readString(path);
        } catch (IOException e) {
            throw new CliException("Could not read file '" + fileName + "': " + e.getMessage(), e);
        }

        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(therapistAddSO, TherapistAddSO.class);
    }

    private TherapistAddSO fromPrompt() throws UserAbortedException {

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
        if (!confirm) throw new UserAbortedException();

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

        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

        return jsonb.toJson(therapistAddSO);
    }
}
