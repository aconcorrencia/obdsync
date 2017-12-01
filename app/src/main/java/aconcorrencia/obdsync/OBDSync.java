package aconcorrencia.obdsync;

import aconcorrencia.obdsync.command.OBDCommand;
import aconcorrencia.obdsync.command.OBDCommandExecuter;
import aconcorrencia.obdsync.connection.BluetoothConnectionListener;
import aconcorrencia.obdsync.connection.BluetoothConnectionThread;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Created by Yuri on 29/11/2017.
 */

public class OBDSync{
    private Activity activity;
    private BluetoothConnectionListener listener;
    private String bluetoothMACAddress;

    private BluetoothConnectionThread bluetoothConnectionThread = null;
    private OBDCommandExecuter obdCommandExecuter = null;

    /**
     * @param activity                    Atividade em qual o contexto da chamda é feito
     * @param bluetoothConnectionListener Responsavel pela chamada de metodos ciclo de vida da conexão bluetooth {@link BluetoothConnectionListener}
     * @param bluetoothMACAddress         Endereço de MAC do bluetooth
     *
     * @see BluetoothConnectionListener
     */
    public OBDSync(Activity activity, BluetoothConnectionListener bluetoothConnectionListener, String bluetoothMACAddress){
        this.activity = activity;
        this.listener = bluetoothConnectionListener;
        this.bluetoothMACAddress = bluetoothMACAddress;
    }

    /**
     * @param activityBluetoothConnectionListener Atividade que tem a responsabilidade dos metodos do ciclo de vida da conexão bluetooth {@link BluetoothConnectionListener}
     * @param bluetoothMACAddress                 Endereço de MAC do bluetooth
     * @param <ActivityBluetoothListener>         Tipo em que é uma {@link Activity} e implementa {@link BluetoothConnectionListener}
     *
     * @see BluetoothConnectionListener
     */
    public <ActivityBluetoothListener extends Activity & BluetoothConnectionListener> OBDSync(ActivityBluetoothListener activityBluetoothConnectionListener, String bluetoothMACAddress){
        this(activityBluetoothConnectionListener, activityBluetoothConnectionListener, bluetoothMACAddress);
    }

    /**
     * Metodo auxiliar para verifica se a conexão foi inicializada
     *
     * @return Quando a {@link #bluetoothConnectionThread} já foi setada
     *
     * @see #initialize()
     */
    private boolean isBluetoothConnectionInitialized(){
        return bluetoothConnectionThread != null;
    }

    /**
     * Metodo auxiliar para veririficar se a conexão foi bem sucedida
     *
     * @return Quando o {@link #obdCommandExecuter} já foi setado
     *
     * @see #initialize()
     */
    private boolean isBluetoothConnectionSuccessful(){
        return obdCommandExecuter != null;
    }

    /**
     * Metodo auxiliar para obter o {@link BluetoothAdapter}
     *
     * @return resultado de {@link BluetoothAdapter#getDefaultAdapter()}
     *
     * @see #initialize()
     */
    private BluetoothAdapter getBluetoothAdapter(){
        return BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Metodo auxiliar para verificar suporte a bluetooth
     *
     * @return Quando existe {@link #getBluetoothAdapter()}
     *
     * @see #getBluetoothAdapter()
     * @see #initialize()
     */
    private boolean hasBluetoothSupport(){
        return getBluetoothAdapter() != null;
    }

    /**
     * Metodo auxiliar para verificar se bluetooth está ativo
     *
     * @return Quando tem suporte e está ativo
     *
     * @see #hasBluetoothSupport()
     * @see #getBluetoothAdapter()
     * @see #initialize()
     */
    private boolean isBluetoothEnable(){
        return hasBluetoothSupport() && getBluetoothAdapter().isEnabled();
    }

    /**
     * Metodo auxiliar para obter {@link BluetoothDevice}
     *
     * @param mac Endereço de mac do bluetooth
     *
     * @return Dispositivo bluetooth referente ao mac
     *
     * @see #initialize()
     */
    private BluetoothDevice getBluetoothDevice(String mac){
        return getBluetoothAdapter().getRemoteDevice(mac);
    }

    /**
     * Meotod auxiliar para obter o {@link #obdCommandExecuter}
     *
     * @return O {@link OBDCommandExecuter}
     *
     * @see OBDCommandExecuter
     * @see #executeCommand(OBDCommand)
     * @see #executeCommand(Class)
     */
    private OBDCommandExecuter getExecuter(){
        if(!isBluetoothConnectionSuccessful()){
            throw new IllegalStateException("Executor não foi iniciado");
        }
        return obdCommandExecuter;
    }

    /**
     * Metodo auxiliar para executor de {@link Runnable} na thread de UI da {@link #activity}
     *
     * @param runnable Runnable que será executado na thread
     *
     * @see #initialize()
     */
    private void throwThread(final Runnable runnable){
        new Thread(){
            public void run(){
                activity.runOnUiThread(runnable);
            }
        }.start();
    }

    /**
     * Executa comando via {@link #getExecuter()}
     * ex:
     * <p>Mode01CommandRPM extends Mode01Command<Float>, tem o retorno do tipo Float, portanto seu retorno será Float</p>
     * <p>Mode01CommandSpeed extends Mode01Command<Integer>, tem o retorno do tipo Integer, portanto seu retorno será Integer</p>
     *
     * @param command Instancia de {@link OBDCommand}
     * @param <dataType> Tipo de dado a ser retornado em sua execução
     *
     * @return Data do tipo descrito em #command
     *
     * @see OBDCommandExecuter#execute(OBDCommand)
     * @see OBDCommandExecuter#execute(Class)
     */
    public <dataType> dataType executeCommand(OBDCommand<dataType> command){
        return getExecuter().execute(command);
    }

    /**
     * Executa comando via {@link #getExecuter()}
     * ex:
     * <p>Mode01CommandRPM extends Mode01Command<Float>, tem o retorno do tipo Float, portanto seu retorno será Float</p>
     * <p>Mode01CommandSpeed extends Mode01Command<Integer>, tem o retorno do tipo Integer, portanto seu retorno será Integer</p>
     *
     * @param obdCommandClass {@link Class} de algum {@link OBDCommand}
     * @param <dataType> Tipo de dado a ser retornado em sua execução
     *
     * @return Data do tipo descrito em #command
     *
     * @see OBDCommandExecuter#execute(OBDCommand)
     * @see OBDCommandExecuter#execute(Class)
     */
    public <dataType, obdCommandSubType extends OBDCommand<dataType>> dataType executeCommand(Class<obdCommandSubType> obdCommandClass){
        return getExecuter().execute(obdCommandClass);
    }

    /**
     * Termina a thread de conexão bluetooth, caso ainda esteja em execução, e então limpa {@link #bluetoothConnectionThread} e {@link #obdCommandExecuter}
     *
     * @see BluetoothConnectionThread#cancel()
     */
    public void destroy(){
        if(bluetoothConnectionThread != null){
            bluetoothConnectionThread.cancel();
            bluetoothConnectionThread = null;
            obdCommandExecuter = null;
        }
    }

    /**
     * Inicializa o OBDSync, fazendo as tarefas de conexão bluetooth, seguindo o ciclo de vida do {@link #listener}
     *
     * @see BluetoothConnectionListener
     * @see BluetoothConnectionThread
     */
    public void initialize(){
        BluetoothDevice bluetoothDevice;
        final int REQUEST_ENABLE_BT = 3;

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
                        throwThread(new Runnable(){
                            @Override
                            public void run(){
                                listener.onBluetoothConnectionError();
                            }
                        });
                    }

                    @Override
                    protected void onSuccess(OBDCommandExecuter obdCommandExecuter1){
                        throwThread(new Runnable(){
                            @Override
                            public void run(){
                                listener.afterBluetoothConnection();
                            }
                        });
                        obdCommandExecuter = obdCommandExecuter1;
                    }

                    @Override
                    protected void onCancel(){
                        throwThread(new Runnable(){
                            @Override
                            public void run(){
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
    }
}
