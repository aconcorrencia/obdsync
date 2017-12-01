package aconcorrencia.obdsync.command.mode1;

import java.util.ArrayList;

/**
 * Created by Sillas on 11/12/2016.
 */

public class Mode01CommandMassAirFlow extends Mode01Command<Float>{
    /**
     * Default ctor.
     */
    public Mode01CommandMassAirFlow(){
        super("10");
    }

    @Override
    protected Float getDataResponse(ArrayList<Integer> bytesResult){
        return (bytesResult.get(2) * 256 + bytesResult.get(3)) / 100.0f;
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }
}
