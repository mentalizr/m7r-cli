package org.mentalizr.cli.commands.accessKey;

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
import org.mentalizr.client.restService.accessKey.AccessKeyGetAllService;
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

public class AccessKeyShowCommand extends CommandExecutor {

    public AccessKeyShowCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        CliCallGlobalConfiguration cliCallGlobalConfiguration = this.cliContext.getCliCallGlobalConfiguration();

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        AccessKeyCollectionSO accessKeyCollectionSO = new AccessKeyGetAllService(restCallContext)
                .call();

        if (cliCallGlobalConfiguration.isDebug()) {
            System.out.println("Response service object [AccessKeyCollection]:");
            System.out.println(AccessKeyCollectionSOX.toJsonWithFormatting(accessKeyCollectionSO));
        }

        List<AccessKeyRestoreSO> collection = accessKeyCollectionSO.getCollection();
        if (collection.size() == 0) {
            System.out.println("No access keys found.");
        } else {
            System.out.println(collection.size() + " access keys found:");
            System.out.println("ID                                   | access key   | program");
            System.out.println("-------------------------------------+--------------+-------------");
            for (AccessKeyRestoreSO accessKeyRestoreSO : collection) {
                System.out.println(accessKeyRestoreSO.getId() + " | " + accessKeyRestoreSO.getAccessKey() + " | " + accessKeyRestoreSO.getProgramId());
            }
        }
    }

}
