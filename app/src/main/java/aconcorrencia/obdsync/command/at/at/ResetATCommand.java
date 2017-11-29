package aconcorrencia.obdsync.command.at.at;

import aconcorrencia.obdsync.command.OBDCommand;
import aconcorrencia.obdsync.command.at.ATCommand;

/**
 * Created by Yuri on 29/11/2017.
 */

public class ResetATCommand extends ATCommand{
    public ResetATCommand(){
        super("AT Z");
    }
}
