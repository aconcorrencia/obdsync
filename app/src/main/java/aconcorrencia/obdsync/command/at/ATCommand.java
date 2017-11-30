package aconcorrencia.obdsync.command.at;

import aconcorrencia.obdsync.command.OBDCommand;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Yuri on 29/11/2017.
 */

abstract class ATCommand extends OBDCommand<String>{
    ATCommand(String command){
        super("AT" + " " + command);
    }

    @Override
    public String getDefaultData(){
        return null;
    }

    @Override
    protected boolean noData() {
        return true;
    }

    @Override
    protected String getData(String data, ArrayList<Integer> bytesResult){
        return null;
    }
}
