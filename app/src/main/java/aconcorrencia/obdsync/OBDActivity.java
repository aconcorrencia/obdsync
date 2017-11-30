package aconcorrencia.obdsync;

import aconcorrencia.obdsync.command.OBDCommand;
import aconcorrencia.obdsync.command.mode1.commands.*;
import aconcorrencia.obdsync.connection.OBDSync;
import aconcorrencia.obdsync.connection.bluetooth.IBluetoothConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import aconcorrencia.obdsync.Adapter.ListViewAdapter;
import aconcorrencia.obdsync.Adapter.RecyclerViewOnClickListenerHack;

import java.util.ArrayList;

public class OBDActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack,IBluetoothConnection {

    protected RecyclerView mRecyclerView;
    private boolean animation = true;
    private ImageView imgEngine;
    private OBDSync sync;
    private String mac;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obd);

        imgEngine = (ImageView) findViewById(R.id.img_engine);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        //textView = (TextView) findViewById(R.id.textView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        String mac= sharedPreferences.getString("addressDevice", "");

        showListView();
    }


    @Override
    protected void onStart() {
        super.onStart();

        //...

//        sync.initialize();

        //...
    }

    /***
     * Preenche a ListView
     */
    private void showListView() {

        ArrayList<ListViewObj> intens = new ArrayList<>();
        intens.add(new ListViewObj("Air Intake Temperature", new AirIntakeTemperatureCommand().getCommand()));
        intens.add(new ListViewObj("Intake Manifold Pressure", new IntakeManifoldPressureCommand().getCommand()));
        intens.add(new ListViewObj("Mass Air flow", new MassAirFlowCommand().getCommand()));
        intens.add(new ListViewObj("Absolute Load", new AbsoluteLoadCommand().getCommand()));
        intens.add(new ListViewObj("RPM", new AbsoluteLoadCommand().getCommand()));
        intens.add(new ListViewObj("Speed", new SpeedCommand().getCommand()));

        ListViewAdapter adapter = new ListViewAdapter(OBDActivity.this, intens);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setClickable(false);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setVisibility(RecyclerView.VISIBLE);
    }



    @Override
    public void onClickListener(View view, int position) {
        switch (position) {
            case 0:
                Float result = sync.getExecuter().execute(RPMCommand.class);
                Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show();
                break;
            case 1:

                /*try {
                    AvailablePidsCommand_01_20 rpmCommand = new AvailablePidsCommand_01_20();
                    rpmCommand.run(this.mmInStream, this.mmOutStream);
                    //rpmCommand.getPercentage();
                    Intent it = new Intent(OBDActivity.this, PIDSActivity.class);
                    it.putExtra("PIDS", rpmCommand.getResult());
                    Log.w("PI", "" + rpmCommand.getResult());
                    startActivity(it);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                // bluetoothConnection.sendPID(ObdCommands.INTAKE_MANIFOLD_ABS_PRESSURE);
                break;
            case 2:

                // bluetoothConnection.sendPID(ObdCommands.INTAKE_AIR_TEMPERATURE);
                break;
            case 3:


                break;

            // bluetoothConnection.sendPID(ObdCommands.CALCULATE_ENGINE_LOAD);
            case 4:


                break;
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    public void notSupportedBluetooth() {
        Toast.makeText(this, "Bluetooth não é suportado", Toast.LENGTH_LONG).show();
        this.finish();

    }

    @Override
    public void onBluetoothConnectionError() {
        animation = false;
        imgEngine.setImageResource(R.drawable.img_engine_red);
        Toast.makeText(OBDActivity.this, "Erro ao conectar!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void beforeBluetoothConnection() {
        Thread timer = new Thread() {
            public void run() {
                while (animation) {
                    try {
                        if (animation) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    imgEngine.setImageResource(R.drawable.img_engine_green);
                                }
                            });
                        }
                        sleep(500);
                        if (animation) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    imgEngine.setImageResource(R.drawable.img_engine);
                                }
                            });
                        }
                        sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        timer.start();
    }

    @Override
    public void afterBluetoothConnection() {
        Toast.makeText(OBDActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
        animation = false;
        imgEngine.setImageResource(R.drawable.img_engine_green);
        showListView();
    }

    @Override
    public void afterBluetoothConnectionClose() {

    }

    @Override
    public void onBluetoothNotSuported() {
        Toast.makeText(this, "Bluetooth não é suportado", Toast.LENGTH_LONG).show();
        this.finish();
    }
}
