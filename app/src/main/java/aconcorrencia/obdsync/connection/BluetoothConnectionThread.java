package aconcorrencia.obdsync.connection;

import aconcorrencia.obdsync.command.OBDCommandExecuter;
import aconcorrencia.obdsync.command.at.ATCommandAutoProtocol;
import aconcorrencia.obdsync.command.at.ATCommandReset;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Yuri on 29/11/2017.
 */

public abstract class BluetoothConnectionThread extends Thread{
    private final BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket = null;

    protected BluetoothConnectionThread(final BluetoothDevice bluetoothDevice){
        this.bluetoothDevice = bluetoothDevice;
    }

    /**
     * Executada quando há um erro na abertura de conexão bluetooth
     */
    protected abstract void onError();

    /**
     * Executada quando a conexão bluetooth foi estabelicida com sucesso
     *
     * @param obdCommandExecuter referente ao InputStream e OutputStram do dispositivo bluetooth
     */
    protected abstract void onSuccess(OBDCommandExecuter obdCommandExecuter);

    /**
     * Exudada quando a thread é cancelada
     */
    protected abstract void onCancel();

    /**
     * Executada quando a thread inicia
     */
    protected abstract void onStart();

    /**
     * Executada quando thread termina
     */
    protected abstract void onEnd();

    /**
     * Tenta atribuir um bluetooth socket, a partir do dispositivo bluetooth, priorizando a tentativa de conexão
     * bluetooth com segurança, caso não seja possivel, tenta a conexão insegura
     * Tambem segue o ciclo de vida definido nessa classe
     *
     * @see #onError()
     * @see #onStart()
     * @see #onSuccess(OBDCommandExecuter)
     * @see #onEnd()
     */
    public final void run(){
        InputStream inputStream;
        OutputStream outputStream;
        OBDCommandExecuter executer;
        final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        onStart();
        try{
            try{
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            }
            catch(Exception e1){
                bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            }
            bluetoothSocket.connect();
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();

            executer = new OBDCommandExecuter(outputStream,inputStream);

            executer.execute(ATCommandReset.class);
            executer.execute(ATCommandAutoProtocol.class);
//            executer.execute(Mode01CommandRPM.class);

            onSuccess(executer);
        }
        catch(IOException e){
            e.printStackTrace();
            onError();
        }
        onEnd();
    }

    /**
     * Fecha o socket bluetoth e então chama {@link #onCancel()}
     */
    public void cancel(){
        if(bluetoothSocket != null){
            try{
                bluetoothSocket.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            onCancel();
        }
    }
}
