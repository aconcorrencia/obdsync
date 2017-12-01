package aconcorrencia.obdsync.command;

/**
 * Created by Yuri on 01/12/2017.
 */

public class UnexpectedValueException extends Exception{
    public UnexpectedValueException(String message){
        super(message);
    }

    public UnexpectedValueException(String message, Throwable cause){
        super(message, cause);
    }

    public UnexpectedValueException(Throwable cause){
        super(cause);
    }
}
