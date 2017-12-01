package aconcorrencia.obdsync.connection;

import aconcorrencia.obdsync.command.OBDCommand;
import aconcorrencia.obdsync.command.OBDCommandExecuter;
import aconcorrencia.obdsync.connection.bluetooth.BluetoothConnectionListener;
import aconcorrencia.obdsync.connection.bluetooth.BluetoothConnectionThread;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Created by Yuri on 29/11/2017.
 */

public class OBDSync{
    private static final int REQUEST_ENABLE_BT = 3;

    private Activity activity;
    private BluetoothConnectionListener listener;
    private String bluetoothMACAddress;

    private BluetoothConnectionThread bluetoothConnectionThread = null;
    private OBDCommandExecuter obdCommandExecuter = null;

    public OBDSync(Activity activity, BluetoothConnectionListener bluetoothConnectionListener, String bluetoothMACAddress){
        this.activity = activity;
        this.listener = bluetoothConnectionListener;
        this.bluetoothMACAddress = bluetoothMACAddress;
    }

    public <T extends Activity & BluetoothConnectionListener> OBDSync(T activityBluetoothConnectionListener, String bluetoothMACAddress){
        this(activityBluetoothConnectionListener, activityBluetoothConnectionListener, bluetoothMACAddress);
    }

    private boolean isBluetoothConnectionInitialized(){
        return bluetoothConnectionThread != null;
    }

    private boolean isBluetoothConnectionSuccessful(){
        return obdCommandExecuter != null;
    }

    private BluetoothAdapter getBluetoothAdapter(){
        return BluetoothAdapter.getDefaultAdapter();
    }

    private boolean hasBluetoothSupport(){
        return getBluetoothAdapter() != null;
    }

    private boolean isBluetoothEnable(){
        return getBluetoothAdapter().isEnabled();
    }

    private BluetoothDevice getBluetoothDevice(String mac){
        return getBluetoothAdapter().getRemoteDevice(mac);
    }

    private OBDCommandExecuter getExecuter(){
        if(!isBluetoothConnectionSuccessful()){
            throw new IllegalStateException("Executor não foi iniciado");
        }
        return obdCommandExecuter;
    }

    /**
     * Executa o comando e retorna o dado referente ao seu tipo de retorno
     *  ex: Mode01CommandRPM, tem o retorno do tipo Float, portanto seu retorno será Float,
     *      Mode01CommandSpeed, tem o retorno do tipo Integer, portanto seu retorno será Integer
     *
     * @param command
     * @param <returnedCommandType>
     * @return returnedCommandType
     */
    public <returnedCommandType> returnedCommandType executeCommand(OBDCommand<returnedCommandType> command){
        return getExecuter().execute(command);
    }

    /**
     *
     * @param obdCommandClass
     * @param <commandClass>
     * @param <returnedCommandType>
     * @return returnedCommandType
     */
    public <commandClass extends OBDCommand<returnedCommandType>,returnedCommandType> returnedCommandType executeCommand(Class<commandClass> obdCommandClass){
        return getExecuter().execute(obdCommandClass);
    }

    /**
     * Cancela a conexão e destroi a instancia de executor
     */
    public void destroy(){
        if(bluetoothConnectionThread != null){
            bluetoothConnectionThread.cancel();
            bluetoothConnectionThread = null;
            obdCommandExecuter = null;
        }
    }

    private void throwThread(final Runnable runnable){
        Thread timer = new Thread() {
            public void run() {
                activity.runOnUiThread(runnable);
            }
        };
        timer.start();
    }

    public void initialize(){
        BluetoothDevice bluetoothDevice;

        if(!hasBluetoothSupport()){
            listener.onBluetoothNotSuported();
        }
        if(isBluetoothEnable()){
            if(!isBluetoothConnectionInitialized()){
                listener.beforeBluetoothConnection();
                bluetoothDevice = getBluetoothDevice(bluetoothMACAddress);

                bluetoothConnectionThread = new BluetoothConnectionThread(bluetoothDevice){
                    @Override
                    protected void onStart(){

                    }

                    @Override
                    protected void onEnd(){

                    }

                    @Override
                    protected void onError(){
                        throwThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onBluetoothConnectionError();
                            }
                        });
                    }

                    @Override
                    protected void onSuccess(OBDCommandExecuter obdCommandExecuter1){
                        throwThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.afterBluetoothConnection();
                            }
                        });
                        obdCommandExecuter = obdCommandExecuter1;
                    }

                    @Override
                    protected void onCancel(){
                        throwThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.afterBluetoothConnectionClose();
                            }
                        });
                    }
                };
                bluetoothConnectionThread.start();
            }
            else if(isBluetoothConnectionInitialized()){
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
        }
//        else{
//            listener.onBluetoothNotSuported();
//        }
    }
}
