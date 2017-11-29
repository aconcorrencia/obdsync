package aconcorrencia.obdsync.connection;

import aconcorrencia.obdsync.command.OBDCommandExecuter;
import aconcorrencia.obdsync.connection.bluetooth.BluetoothConnectionHandleable;
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
    private BluetoothConnectionHandleable handleable;
    private String bluetoothMACAddress;

    private BluetoothConnectionThread bluetoothConnectionThread = null;
    private OBDCommandExecuter obdCommandExecuter = null;

    public OBDSync(Activity activity, BluetoothConnectionHandleable handleable, String bluetoothMACAddress){
        this.activity = activity;
        this.handleable = handleable;
        this.bluetoothMACAddress = bluetoothMACAddress;
        initialize();
    }

    public <T extends Activity & BluetoothConnectionHandleable> OBDSync(T activityBluetoothHandleable, String bluetoothMACAddress){
        this(activityBluetoothHandleable, activityBluetoothHandleable, bluetoothMACAddress);
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

    public OBDCommandExecuter getExecuter(){
        if(!isBluetoothConnectionSuccessful()){
            throw new IllegalStateException("Executor não foi iniciado");
        }
        return obdCommandExecuter;
    }

    public void destroy(){
        if(bluetoothConnectionThread != null){
            bluetoothConnectionThread.cancel();
            bluetoothConnectionThread = null;
        }
    }

    public void initialize(){
        BluetoothDevice bluetoothDevice;

        if(hasBluetoothSupport()){
            handleable.onBluetoothNotSuported();
        }
        if(isBluetoothEnable()){
            if(!isBluetoothConnectionInitialized()){
                handleable.beforeBluetoothConnection();
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
                        handleable.onBluetoothConnectionError();
                    }

                    @Override
                    protected void onSuccess(OBDCommandExecuter obdCommandExecuter1){
                        handleable.afterBluetoothConnection();
                        obdCommandExecuter = obdCommandExecuter1;
                    }

                    @Override
                    protected void onCancel(){
                        handleable.afterBluetoothConnectionClose();
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
//            handleable.onBluetoothNotSuported();
//        }
    }
}
