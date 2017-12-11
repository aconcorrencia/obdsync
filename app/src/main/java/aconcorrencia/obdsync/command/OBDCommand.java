package aconcorrencia.obdsync.command;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Yuri on 29/11/2017.
 */

public abstract class OBDCommand<DataType>{
    private static final String COMMAND_BREAKER = "\r";
    private static final char READ_END = '>';
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");
    private static final Pattern BUSINIT_PATTERN = Pattern.compile("(BUS INIT)|(BUSINIT)|(\\.)");
    private static final Pattern SEARCHING_PATTERN = Pattern.compile("SEARCHING");
    private static final Pattern DIGITS_LETTERS_PATTERN = Pattern.compile("([0-9A-F])+");

    private String command;
    private String lastData;

    public String getLastData(){
        return lastData;
    }

    protected OBDCommand(String command){
        this.command = command;
    }

    /**
     * Envia um comando para o Outpustram do socket
     *
     * @param command      Comando a ser enviado
     * @param outputStream Outpustream do socket no qual o comando sera enviado
     *
     * @throws IOException Lançado pelos metodos {@link OutputStream#write(int)} e {@link OutputStream#flush()}
     */
    private static void sendCommand(String command, OutputStream outputStream) throws IOException{
        byte[] commandBytes;

        commandBytes = (command + COMMAND_BREAKER).getBytes();

        outputStream.write(commandBytes);
        outputStream.flush();
    }

    /**
     * Data padrão caso não seja possivel enviar o comando
     *
     * @return data padrão do tipo definido em DataType
     *
     * @see #execute(OutputStream, InputStream)
     */
    protected abstract DataType getDefaultData();

    /**
     * Obter data que sera retornada como resposta apos a execução do comando
     *
     * @param bytesValue ArrayList contendo os bytes em Integer
     *
     * @return data usada na resposta de {@link #execute(OutputStream, InputStream)}
     *
     * @see #getBytesValue()
     * @see #execute(OutputStream, InputStream)
     * @see OBDCommandExecuter
     * @see OBDCommandExecuter#execute(Class)
     * @see OBDCommandExecuter#execute(OBDCommand)
     */
    protected abstract DataType getDataResponse(ArrayList<Integer> bytesValue);

    /**
     * Obtem o Arraylist referente a {@link #lastData} caso {@link #notConvertData()} retorne false
     *
     * @return Lista de bytes, cada item é um byte com seu valor em Integer
     */
    private ArrayList<Integer> getBytesValue(){
        ArrayList<Integer> bytes;

        bytes = new ArrayList<>();

        if(notConvertData()){
            return null;
        }
        bytes.clear();
        int begin = 0;
        int end = 2;
        while(end <= lastData.length()){
            String byteString = lastData.substring(begin, end);
            bytes.add(Integer.decode("0x" + byteString));
            begin = end;
            end += 2;
        }

        return bytes;
    }

    /**
     * Faz a leitura do que há disponivel no InputStream do socket, ate alcançar o fim ou no marcador {@link #READ_END}
     * A leitura é feita, sendo removido os padrões {@link #SEARCHING_PATTERN}, {@link #WHITESPACE_PATTERN} e {@link #BUSINIT_PATTERN}
     *
     * @param inputStream InputStream do socket no qual sera feito a leitura da resposta
     *
     * @throws IOException Lançada por {@link InputStream#read()}
     */
    private void read(InputStream inputStream) throws IOException{
        byte b;
        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder();

        // -1 if the end of the stream is reached
        while((b = (byte) inputStream.read()) > -1){
            char c = (char) b;
            if(c == READ_END){
                break;
            }
            stringBuilder.append(c);
        }

        lastData = stringBuilder.toString();

        lastData = SEARCHING_PATTERN.matcher(lastData).replaceAll("");
        lastData = WHITESPACE_PATTERN.matcher(lastData).replaceAll("");
        lastData = BUSINIT_PATTERN.matcher(lastData).replaceAll("");

//        if(!DIGITS_LETTERS_PATTERN.matcher(lastData).matches()){
//            throw new NumberFormatException(lastData);
//        }
    }

    /**
     * Envia o comando no OutputStream do socket
     *
     * @param outputStream OutputStram no qual o comando sera enviado
     *
     * @throws IOException Lançado por {@link #sendCommand(String, OutputStream)}
     * @see #sendCommand(String, OutputStream)
     */
    private void send(OutputStream outputStream) throws IOException{
        sendCommand(command, outputStream);
    }

    /**
     * Se é necessario fazer fazer a conversão de {@link #lastData} em {@link #getBytesValue()}
     * Somente deve ser sobrescrita se o comando não precisa, ou não deve fazer a conversão
     *
     * @return se sera feito a conversão
     */
    protected boolean notConvertData(){
        return false;
    }

    /**
     * Obter comando
     *
     * @return obtem o comando
     */
    public String getCommand(){
        return command;
    }

    /**
     * Envia o comando para o OutputStream e faz a respectiva leitura do InputStream
     *
     * @param outputStream OutputStream do socket onde sera enviado o comando
     * @param inputStream  InputStram do socket onde sera retornada a resposta
     *
     * @return data do tipo definido no DataType
     *
     * @throws IOException Lancado por {@link #send(OutputStream)} e {@link #read(InputStream)}
     * @see OBDCommandExecuter#execute(OBDCommand)
     * @see OBDCommandExecuter#execute(Class)
     */
    public DataType execute(OutputStream outputStream, InputStream inputStream) throws IOException{
        // Somente uma instancia por vez
        synchronized(OBDCommand.class){
            send(outputStream);
            read(inputStream);

            return getDataResponse(getBytesValue());
        }
    }
}
