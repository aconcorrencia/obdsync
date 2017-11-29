package aconcorrencia.obdsync.connection.bluetooth;

import aconcorrencia.obdsync.command.OBDCommandExecuter;
import aconcorrencia.obdsync.command.at.commands.AutoProtocolATCommand;
import aconcorrencia.obdsync.command.at.commands.ResetATCommand;
import aconcorrencia.obdsync.command.mode1.commands.AvailablePidsCommand;
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

    public BluetoothConnectionThread(final BluetoothDevice bluetoothDevice){
        this.bluetoothDevice = bluetoothDevice;
    }

    protected abstract void onError();

    protected abstract void onSuccess();

    protected abstract void onCancel();

    protected abstract void onStart();

    public final void run(){
        InputStream inputStream;
        OutputStream outputStream;
        OBDCommandExecuter executer;

        onStart();
        try{
            try{
                bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(UUID.randomUUID());
            }
            catch(RuntimeException e1){
                try{
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.randomUUID());
                }
                catch(RuntimeException e){
                    onError();
                    return;
                }
            }
            bluetoothSocket.connect();
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();

            executer = new OBDCommandExecuter(inputStream,outputStream);

            executer.execute(ResetATCommand.class);
            executer.execute(AutoProtocolATCommand.class);
            executer.execute(AvailablePidsCommand.class);

            onSuccess();
        }
        catch(IOException e){
            onError();
        }
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
