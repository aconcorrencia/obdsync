package aconcorrencia.obdsync.command.mode1.commands;

import aconcorrencia.obdsync.command.OBDCommand;

import java.util.ArrayList;

/**
 * Created by Sillas on 11/12/2016.
 */

public class MassAirFlowCommand extends OBDCommand<Float>{
    /**
     * Default ctor.
     */
    public MassAirFlowCommand() {
        super("10");
    }

    @Override
    public Float getDefaultData(){
        return -1f;
    }

    @Override
    protected Float getData(String data, ArrayList<Integer> bytesResult){
        return (bytesResult.get(2) * 256 + bytesResult.get(3)) / 100.0f;
    }
}
