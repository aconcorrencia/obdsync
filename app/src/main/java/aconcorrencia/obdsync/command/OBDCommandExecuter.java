package aconcorrencia.obdsync.command;

import aconcorrencia.obdsync.command.OBDCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Yuri on 29/11/2017.
 */

public class OBDCommandExecuter{
    private InputStream inputStream;
    private OutputStream outputStream;

    public OBDCommandExecuter(InputStream inputStream, OutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /**
     *
     * @param obdCommand qualquer instancia de OBDCommand
     * @param <commandType>
     * @param <returnedCommandType>
     * @return
     */
    public <commandType extends OBDCommand<returnedCommandType>,returnedCommandType> returnedCommandType execute(commandType obdCommand){
        try{
            return obdCommand.execute(outputStream,inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return obdCommand.getDefaultData();
    }

    public <commandTypeClass extends Class<? extends OBDCommand<returnedCommandType>>,returnedCommandType> returnedCommandType execute(commandTypeClass obdCommandClass){
        try{
            return execute(obdCommandClass.newInstance());
        }
        catch(InstantiationException e){
            e.printStackTrace();
        }
        catch(IllegalAccessException e){
            e.printStackTrace();
        }

        return null;
    }
}
