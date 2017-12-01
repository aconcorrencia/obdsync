package aconcorrencia.obdsync.command.at;

import aconcorrencia.obdsync.command.OBDCommand;

import java.util.ArrayList;

/**
 * Created by Yuri on 29/11/2017.
 */

abstract class ATCommand extends OBDCommand<String>{
    ATCommand(String command){
        super("AT" + " " + command);
    }

    @Override
    protected String getDefaultData(){
        return null;
    }

    @Override
    protected boolean notConvertData(){
        return true;
    }

    @Override
    protected String getDataResponse(ArrayList<Integer> bytesResult){
        return null;
    }
}
