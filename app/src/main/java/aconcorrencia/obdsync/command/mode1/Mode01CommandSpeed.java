package aconcorrencia.obdsync.command.mode1;


import java.util.ArrayList;

/**
 * Created by Sillas on 11/12/2016.
 */

public class Mode01CommandSpeed extends Mode01Command<Integer>{
    public Mode01CommandSpeed(){
        super("0D");
    }

    @Override
    protected Integer getDataResponse(ArrayList<Integer> bytesResult){
        return bytesResult.get(2);
    }

    @Override
    public Integer getDefaultData(){
        return null;
    }
}