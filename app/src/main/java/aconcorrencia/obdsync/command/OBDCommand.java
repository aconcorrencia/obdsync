package aconcorrencia.obdsync.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Yuri on 29/11/2017.
 */

public abstract class OBDCommand<T>{
    private static final String COMMAND_BREAKER = "\r";
    private static final char READ_END = '>';
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");
    private static final Pattern BUSINIT_PATTERN = Pattern.compile("(BUS INIT)|(BUSINIT)|(\\.)");
    private static final Pattern SEARCHING_PATTERN = Pattern.compile("SEARCHING");
    private static final Pattern DIGITS_LETTERS_PATTERN = Pattern.compile("([0-9A-F])+");

    private ArrayList<Integer> bytesResult = new ArrayList<>();
    private String command;

    public OBDCommand(String command){
        this.command = command;
    }

    public abstract T getDefaultData();
    protected abstract T getData(String data,ArrayList<Integer> bytesResult);

    private static void sendCommand(String command, OutputStream outputStream) throws IOException{
        byte[] commandBytes;

        commandBytes = (command + COMMAND_BREAKER).getBytes();

        outputStream.write(commandBytes);
        outputStream.flush();
    }

    private String read(InputStream inputStream) throws IOException{
        byte b;
        StringBuilder stringBuilder;
        String data;

        stringBuilder = new StringBuilder();

        // read until '>' arrives OR end of stream reached
        char c;
        // -1 if the end of the stream is reached
        while((b = (byte) inputStream.read()) > -1){
            c = (char) b;
            if(c == READ_END){
                break;
            }
            stringBuilder.append(c);
        }

        data = stringBuilder.toString();

        data = SEARCHING_PATTERN.matcher(data).replaceAll("");
        data = WHITESPACE_PATTERN.matcher(data).replaceAll("");
        data = BUSINIT_PATTERN.matcher(data).replaceAll("");

        if(!DIGITS_LETTERS_PATTERN.matcher(data).matches()){
            //  throw new NonNumericResponseException(rawData);
        }

        // read string each two chars
        bytesResult.clear();
        int begin = 0;
        int end = 2;
        while(end <= data.length()){
            bytesResult.add(Integer.decode("0x" + data.substring(begin, end)));
            begin = end;
            end += 2;
        }

        return data;
    }

    private void send(OutputStream outputStream) throws IOException{
        sendCommand(command, outputStream);
    }

    public T execute(OutputStream outputStream, InputStream inputStream) throws IOException{
        String data;

        // Somente uma instancia por vez
        synchronized(OBDCommand.class){
            send(outputStream);
            data = read(inputStream);
        }

        return getData(data,bytesResult);
    }
}
