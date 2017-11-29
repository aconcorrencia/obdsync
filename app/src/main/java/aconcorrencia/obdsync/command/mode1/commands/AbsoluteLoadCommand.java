
package aconcorrencia.obdsync.command.mode1.commands;


import aconcorrencia.obdsync.command.mode1.Mode01Command;

import java.util.ArrayList;

public class AbsoluteLoadCommand extends Mode01Command<Float>{

    public AbsoluteLoadCommand() {
        super("43");
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }

    @Override
    protected Float getData(String data, ArrayList<Integer> bytesResult){
        return (bytesResult.get(2) * 100.0f) / 255.0f;
    }
}