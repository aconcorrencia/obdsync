package aconcorrencia.obdsync.command.mode1.commands;

import aconcorrencia.obdsync.command.mode1.Mode01Command;
import aconcorrencia.obdsync.connection.OBDSync;

import java.util.ArrayList;

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
    protected String getData(String data, ArrayList<Integer> bytesResult){
        return null;
    }
}
