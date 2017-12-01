package aconcorrencia.obdsync.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Yuri on 29/11/2017.
 */

public class OBDCommandExecuter{
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * Construtor
     *
     * @param outputStream OutputStram onde sera feito o envio
     * @param inputStream  InputStream onde sera feita a leitura
     */
    public OBDCommandExecuter(OutputStream outputStream, InputStream inputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /**
     * Executor de {@link OBDCommand} via instancia
     *
     * @param obdCommand          Alguma instancia de <obdCommandSubType>
     * @param <dataType>          Tipo de retorno definido em {@link OBDCommand}
     * @param <obdCommandSubType> Tipo ao qual extende {@link OBDCommand}
     *
     * @return Será o mesmo tipo retornado por {@link OBDCommand#execute(OutputStream, InputStream)}
     *
     * @see OBDCommand
     * @see OBDCommand#getDefaultData()
     * @see OBDCommand#getDataResponse(ArrayList) ()
     * @see OBDCommand#execute(OutputStream, InputStream)
     */
    public <dataType, obdCommandSubType extends OBDCommand<dataType>> dataType execute(obdCommandSubType obdCommand){
        try{
            return obdCommand.execute(outputStream, inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return obdCommand.getDefaultData();
    }

    /**
     * Executor de {@link OBDCommand} via classe
     *
     * @param obdCommandClass     Alguma subclasse de <obdCommandSubType>
     * @param <dataType>          Tipo retorno definido em {@link OBDCommand}
     * @param <obdCommandSubType> Tipo ao qual extende {@link OBDCommand}
     *
     * @return Será o mesmo tipo retornado por {@link OBDCommand#execute(OutputStream, InputStream)}
     *
     * @see OBDCommand
     * @see OBDCommand#getDefaultData()
     * @see OBDCommand#getDataResponse(ArrayList) ()
     * @see OBDCommand#execute(OutputStream, InputStream)
     */
    public <dataType, obdCommandSubType extends OBDCommand<dataType>> dataType execute(Class<obdCommandSubType> obdCommandClass){
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
