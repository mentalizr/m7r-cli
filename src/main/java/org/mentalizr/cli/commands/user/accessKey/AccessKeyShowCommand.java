package org.mentalizr.cli.commands.user.accessKey;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.commands.CommandExecutorHelper;
import org.mentalizr.client.restService.accessKey.AccessKeyGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCollectionSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCollectionSOX;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;

import java.util.List;

public class AccessKeyShowCommand implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliContext cliContext = CliContext.getInstance(cliCall);
        CommandExecutorHelper.checkedInit(cliContext);

        AccessKeyCollectionSO accessKeyCollectionSO =  callService(cliContext);

        if (cliContext.isDebug()) {
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

    private AccessKeyCollectionSO callService(CliContext cliContext) throws CommandExecutorException {

        try {
            return AccessKeyGetAllService.call(cliContext);
        } catch (RestServiceHttpException | RestServiceConnectionException e) {
            throw new CommandExecutorException(e);
        }
    }

}
