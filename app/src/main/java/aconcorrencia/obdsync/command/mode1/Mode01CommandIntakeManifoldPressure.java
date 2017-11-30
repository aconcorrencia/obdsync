package aconcorrencia.obdsync.command.mode1;

import aconcorrencia.obdsync.command.mode1.Mode01Command;

import java.util.ArrayList;

/**
 * Created by Sillas on 10/12/2016.
 */

public class Mode01CommandIntakeManifoldPressure extends Mode01Command<Float>{

    public Mode01CommandIntakeManifoldPressure() {
        super("0B");
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }

    @Override
    protected Float getData(String data, ArrayList<Integer> bytesResult){
        return bytesResult.get(2) + 0f;
    }
}