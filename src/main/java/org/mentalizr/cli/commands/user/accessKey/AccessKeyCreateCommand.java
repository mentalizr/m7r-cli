package org.mentalizr.cli.commands.user.accessKey;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyCreateService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AccessKeyCreateCommand extends CommandExecutor {

    public AccessKeyCreateCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        CliCallGlobalConfiguration cliCallGlobalConfiguration = this.cliContext.getCliCallGlobalConfiguration();

        AccessKeyCreateSO accessKeyCreateSO = fromPrompt();

        if (cliCallGlobalConfiguration.isDebug()) {
            System.out.println("Request service object [AccessKeyCreate]:");
            System.out.println(AccessKeyCreateSOX.toJsonWithFormatting(accessKeyCreateSO));
        }

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        AccessKeyCollectionSO accessKeyCollectionSO = new AccessKeyCreateService(accessKeyCreateSO, restCallContext)
                .call();

        if (cliCallGlobalConfiguration.isDebug()) {
            System.out.println("Response service object [AccessKeyCollection]:");
            System.out.println(AccessKeyCollectionSOX.toJsonWithFormatting(accessKeyCollectionSO));
        }

        System.out.println("[OK] " + accessKeyCreateSO.getNrOfKeys() + " access keys created:");

        List<AccessKeyRestoreSO> collection = accessKeyCollectionSO.getCollection();
        for (AccessKeyRestoreSO accessKeyRestoreSO : collection) {
            System.out.println(accessKeyRestoreSO.getAccessKey());
        }

        writeToFile(accessKeyCollectionSO);
    }

    private AccessKeyCreateSO fromPrompt() throws UserAbortedException {

        AccessKeyCreateSO accessKeyCreateSO = new AccessKeyCreateSO();

        int nrOfKeys = ConsoleReader.promptForMandatoryInt("number of keys: ");
        accessKeyCreateSO.setNrOfKeys(nrOfKeys);

        String startWith = ConsoleReader.promptForOptionalString("Begin letters of code (optional): ").toUpperCase();
        accessKeyCreateSO.setStartWith(startWith);

        boolean active = ConsoleReader.promptForYesOrNo("active? (y/n): ");
        accessKeyCreateSO.setActive(active);

        String programId = ConsoleReader.promptForMandatoryString("Program: ");
        accessKeyCreateSO.setProgramId(programId);

        String therapistId = ConsoleReader.promptForMandatoryString("Therapist ID: ");
        accessKeyCreateSO.setTherapistId(therapistId);

        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new UserAbortedException();

        return accessKeyCreateSO;
    }

    private void writeToFile(AccessKeyCollectionSO accessKeyCollectionSO) {

        List<AccessKeyRestoreSO> collection = accessKeyCollectionSO.getCollection();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = timestamp + "_userAccessKeys.txt";
        File configDir = CliConfigurationFiles.getCreateDir();
        try {
            Files.createDirectories(configDir.toPath());
        } catch (IOException e) {
            throw new CliException("Could not crate directory [" + configDir.getAbsolutePath() + "].");
        }
        File file = new File(configDir, fileName);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            for (AccessKeyRestoreSO accessKeyRestoreSO : collection) {
                printWriter.println(accessKeyRestoreSO.getAccessKey());
            }
        } catch (IOException e) {
            throw new CliException("Could not write to file [" + file.getAbsolutePath() + "].");
        }

        System.out.println("Generated user access keys written to [" + file.getAbsolutePath() + "].");
    }

}
