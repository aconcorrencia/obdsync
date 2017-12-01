package aconcorrencia.obdsync.command.mode1;

import java.util.ArrayList;

/**
 * Created by Sillas on 10/12/2016.
 */

public class Mode01CommandIntakeManifoldPressure extends Mode01Command<Float>{

    public Mode01CommandIntakeManifoldPressure(){
        super("0B");
    }

    @Override
    protected Float getDataResponse(ArrayList<Integer> bytesResult){
        return bytesResult.get(2) + 0f;
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }
}