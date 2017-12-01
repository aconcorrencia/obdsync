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
     * @param obdCommand          Alguma instancia de <OBDCommandSubType>
     * @param <DataType>          Tipo de retorno definido em {@link OBDCommand}
     * @param <OBDCommandSubType> Tipo ao qual extende {@link OBDCommand}
     *
     * @return Será o mesmo tipo retornado por {@link OBDCommand#execute(OutputStream, InputStream)}
     *
     * @see OBDCommand
     * @see OBDCommand#getDefaultData()
     * @see OBDCommand#getDataResponse(ArrayList) ()
     * @see OBDCommand#execute(OutputStream, InputStream)
     */
    public <DataType, OBDCommandSubType extends OBDCommand<DataType>> DataType execute(OBDCommandSubType obdCommand){
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
     * @param obdCommandClass     Alguma subclasse de <OBDCommandSubType>
     * @param <DataType>          Tipo retorno definido em {@link OBDCommand}
     * @param <OBDCommandSubType> Tipo ao qual extende {@link OBDCommand}
     *
     * @return Será o mesmo tipo retornado por {@link OBDCommand#execute(OutputStream, InputStream)}
     *
     * @see OBDCommand
     * @see OBDCommand#getDefaultData()
     * @see OBDCommand#getDataResponse(ArrayList) ()
     * @see OBDCommand#execute(OutputStream, InputStream)
     */
    public <DataType, OBDCommandSubType extends OBDCommand<DataType>> DataType execute(Class<OBDCommandSubType> obdCommandClass){
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
