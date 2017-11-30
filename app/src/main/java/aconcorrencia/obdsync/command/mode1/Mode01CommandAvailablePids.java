package aconcorrencia.obdsync.command.mode1;

import aconcorrencia.obdsync.command.mode1.Mode01Command;
import aconcorrencia.obdsync.connection.OBDSync;

import java.util.ArrayList;

/**
 * Created by Yuri on 29/11/2017.
 */

public class Mode01CommandAvailablePids extends Mode01Command<String>{
    public Mode01CommandAvailablePids(){
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
