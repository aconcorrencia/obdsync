package aconcorrencia.obdsync.command.mode1.mode01;

import aconcorrencia.obdsync.command.OBDCommand;
import aconcorrencia.obdsync.command.mode1.Mode01Command;

/**
 * Created by Yuri on 29/11/2017.
 */

public class AvailablePidsCommand extends Mode01Command<String>{
    public AvailablePidsCommand(){
        super("00");
    }

    @Override
    public String getDefaultData(){
        return null;
    }

    @Override
    protected String getData(String data){
        return null;
    }
}
