
package aconcorrencia.obdsync.command.mode1.commands;

import aconcorrencia.obdsync.command.mode1.Mode01Command;

import java.util.ArrayList;

public class RPMCommand extends Mode01Command<Float>{
    public RPMCommand() {
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