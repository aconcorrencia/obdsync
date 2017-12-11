package aconcorrencia.obdsync.command.mode1;

import java.util.ArrayList;

public class Mode01CommandRPM extends Mode01Command<Float>{
    public Mode01CommandRPM(){
        super("0C");
    }

    @Override
    protected Float getDataResponse(ArrayList<Integer> bytesResult){
        return (float)(bytesResult.get(2) * 256 + bytesResult.get(3)) / 4;
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }
}