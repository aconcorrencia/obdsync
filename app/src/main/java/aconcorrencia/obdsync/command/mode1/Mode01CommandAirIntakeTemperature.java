package aconcorrencia.obdsync.command.mode1;


import java.util.ArrayList;

/**
 * Created by Sillas on 10/12/2016.
 */

public class Mode01CommandAirIntakeTemperature extends Mode01Command<Float>{

    public Mode01CommandAirIntakeTemperature(){
        super("0F");
    }

    @Override
    protected Float getDataResponse(ArrayList<Integer> bytesResult){
        return bytesResult.get(2) - 40f;
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }
}