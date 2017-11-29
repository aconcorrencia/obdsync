package aconcorrencia.obdsync.command;

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

    public <T extends OBDCommand<RC>,RC> RC execute(T obdCommand){
        try{
            return obdCommand.execute(outputStream,inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return obdCommand.getDefaultData();
    }

    public <T extends Class<? extends OBDCommand<RC>>,RC> RC execute(T obdCommandClass){
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
