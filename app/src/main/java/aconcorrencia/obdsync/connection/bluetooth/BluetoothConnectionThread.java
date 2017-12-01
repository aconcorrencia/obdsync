package aconcorrencia.obdsync.connection.bluetooth;

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

    protected abstract void onError();

    protected abstract void onSuccess(OBDCommandExecuter obdCommandExecuter);

    protected abstract void onCancel();

    protected abstract void onStart();

    protected abstract void onEnd();

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

            executer = new OBDCommandExecuter(inputStream,outputStream);

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

    public void cancel() {
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
