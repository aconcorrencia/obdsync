package aconcorrencia.obdsync.connection.bluetooth;

/**
 * Created by Yuri on 29/11/2017.
 */

public interface IBluetoothConnection {
    /**
     * Quando há erro na conexão
     */
    void onBluetoothConnectionError();

    /**
     * Antes de conectar(ou tentar)
     */
    void beforeBluetoothConnection();

    /**
     * Depois de conectar
     */
    void afterBluetoothConnection();

    /**
     * Depode fe char a conexão
     */
    void afterBluetoothConnectionClose();

    /**
     * Quando bluetooth não é suportado
     */
    void onBluetoothNotSuported();
}