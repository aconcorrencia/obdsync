package aconcorrencia.obdsync.command.at;

import aconcorrencia.obdsync.command.OBDCommand;

import java.util.ArrayList;

/**
 * Created by Yuri on 29/11/2017.
 */

public abstract class ATCommand extends OBDCommand<String>{
    public ATCommand(String command){
        super("AT" + " " + command);
    }

    @Override
    public String getDefaultData(){
        return null;
    }

    @Override
    protected String getData(String data, ArrayList<Integer> bytesResult){
        return null;
    }
}
