package org.mentalizr.cli.commands.program;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.ProgramDeleteService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class ProgramDeleteCommand extends AbstractCommandExecutor {

    public ProgramDeleteCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (!optionParserResultSpecific.hasOption(M7rCli.OPTION__PROGRAM))
            throw new CliException("Please specify --program option.");
        String programId = optionParserResultSpecific.getValue(M7rCli.OPTION__PROGRAM).trim();

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        new ProgramDeleteService(programId, restCallContext)
                .call();

        System.out.println("[OK] Program [" + programId + "] deleted.");
    }

}
