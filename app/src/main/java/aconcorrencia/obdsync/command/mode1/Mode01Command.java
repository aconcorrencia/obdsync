package aconcorrencia.obdsync.command.mode1;

import aconcorrencia.obdsync.command.OBDCommand;

/**
 * Created by Yuri on 29/11/2017.
 */

abstract class Mode01Command<DataType> extends OBDCommand<DataType>{
    Mode01Command(String pid){
        super("01" + " " + pid);
    }
}
