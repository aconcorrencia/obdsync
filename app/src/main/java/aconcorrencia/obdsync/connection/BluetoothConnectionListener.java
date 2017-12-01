package aconcorrencia.obdsync.connection;

/**
 * Created by Yuri on 29/11/2017.
 */

public interface BluetoothConnectionListener{
    /**
     * Quando há erro na conexão
     */
    void onBluetoothConnectionError();

    /**
     * Obter o endereço de mac do bluetooth
     *
     * @return String refente ao MAC
     */
    String getBluetoothMACAddress();

    /**
     * Antes de conectar(ou tentar)
     */
    void beforeBluetoothConnection();

    /**
     * Depois de conectar
     */
    void afterBluetoothConnection();

    /**
     * Depode fechar a conexão
     */
    void afterBluetoothConnectionClose();

    /**
     * Quando bluetooth não é suportado
     */
    void onBluetoothNotSuported();
}
