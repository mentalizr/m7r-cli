package org.mentalizr.cli.backup;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyRestoreService;
import org.mentalizr.client.restService.userAdmin.PatientRestoreService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSOX;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSOX;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RecoverAccessKeys {

    public static void exec(RecoverFileLocation recoverFileLocation, CliContext cliContext) throws RestServiceHttpException, RestServiceConnectionException {

        List<Path> files = recoverFileLocation.getAllAccessKeyFiles();

        for (Path file : files) {

            String json = getJson(file);
            AccessKeyRestoreSO accessKeyRestoreSO = AccessKeyRestoreSOX.fromJson(json);

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(cliContext);
            new AccessKeyRestoreService(accessKeyRestoreSO, restCallContext).call();

            System.out.println("AccessKey [" + accessKeyRestoreSO.getId() + "] restored.");
        }

        System.out.println("[OK] " + files.size() + " access keys restored.");
    }

    private static String getJson(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CliException("Could not read access key file to recover from [" + file.toAbsolutePath() + "]. " + e.getMessage(), e);
        }
    }

}
