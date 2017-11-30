
package aconcorrencia.obdsync.command.mode1;

import aconcorrencia.obdsync.command.mode1.Mode01Command;

import java.util.ArrayList;

public class Mode01CommandRPM extends Mode01Command<Float>{
    public Mode01CommandRPM() {
        super("0C");
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }

    @Override
    protected Float getData(String data, ArrayList<Integer> bytesResult){
        return (bytesResult.get(2) * 256 + bytesResult.get(3)) / 4f;
    }
}