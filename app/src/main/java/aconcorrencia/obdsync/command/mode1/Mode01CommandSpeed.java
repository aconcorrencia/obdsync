package aconcorrencia.obdsync.command.mode1;


import aconcorrencia.obdsync.command.mode1.Mode01Command;

import java.util.ArrayList;

/**
 * Created by Sillas on 11/12/2016.
 */

public class Mode01CommandSpeed extends Mode01Command<Integer>{
    /**
     * Default ctor.
     */
    public Mode01CommandSpeed() {
        super("0D");
    }

    @Override
    public Integer getDefaultData(){
        return null;
    }

    @Override
    protected Integer getData(String data, ArrayList<Integer> bytesResult){
        return bytesResult.get(2);
    }
}