package aconcorrencia.obdsync;

import aconcorrencia.obdsync.command.OBDCommandExecuter;
import aconcorrencia.obdsync.command.mode1.Mode01CommandRPM;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Yuri on 01/12/2017.
 */

public class Example{

    public void x() throws IOException{
        InputStream inputStream = new InputStream(){
            @Override
            public int read() throws IOException{
                return 0;
            }
        };
        OutputStream outputStream = new OutputStream(){
            @Override
            public void write(int i) throws IOException{

            }
        };

        OBDCommandExecuter exec = new OBDCommandExecuter(outputStream, inputStream);
        Float rpm = exec.execute(new Mode01CommandRPM());

        // ...same as...

        exec.execute(Mode01CommandRPM.class);

        // ...same as...

        Mode01CommandRPM command = new Mode01CommandRPM();
        command.execute(outputStream, inputStream);
    }
}
