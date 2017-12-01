package aconcorrencia.obdsync.command.mode1;


import java.util.ArrayList;

public class Mode01CommandAbsoluteLoad extends Mode01Command<Float>{

    public Mode01CommandAbsoluteLoad(){
        super("43");
    }

    @Override
    protected Float getDataResponse(ArrayList<Integer> bytesResult){
        return (bytesResult.get(2) * 100.0f) / 255.0f;
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }
}