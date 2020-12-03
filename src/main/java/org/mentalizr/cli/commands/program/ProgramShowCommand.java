package org.mentalizr.cli.commands.program;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientGetAllService;
import org.mentalizr.client.restService.userAdmin.ProgramGetAllService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.util.List;

public class ProgramShowCommand extends CommandExecutor {

    public ProgramShowCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        List<ProgramSO> collection = ProgramGetAllService.call(this.cliContext).getCollection();

        if (collection.size() == 0) {
            System.out.println("No programs found.");
        } else {
            System.out.println(collection.size() + " program" + (collection.size() > 1 ? "s" : "") + " found:");
            System.out.println("program id                           |");
            System.out.println("-------------------------------------+");
            for (ProgramSO programSO : collection) {
                System.out.println(programSO.getProgramId());
            }
        }

    }

}


