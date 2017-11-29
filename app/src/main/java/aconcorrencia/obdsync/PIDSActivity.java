package aconcorrencia.obdsync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import aconcorrencia.obdsync.Adapter.ListViewAdapterPids;

import java.util.ArrayList;

public class PIDSActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pids);


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        String pids;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Log.w("3","3");
                pids= null;
            } else {
                Log.w("2","2");
                pids= extras.getString("PIDS");
            }
        } else {
            Log.w("1","1");
            pids= (String) savedInstanceState.getSerializable("PIDS");
        }
        try{
            String lista;
            lista=pids.substring(0,4);
            pids=pids.replace(lista,"");
            pids=Util.hexToBin(pids);
            String[] ary = pids.split("");
            Log.w("ARRAY","Ag: "+pids);
            ArrayList<ListViewObjPids> mlistViewObjs = new ArrayList<>();

            if(lista.equals("4100")) {
                mlistViewObjs.add(new ListViewObjPids("Monitor Status Since DTCs Cleared", "Inclui o status da  lâmpada indicadora de mau funcionamento (MIL) e número de DTCs.", ary[1]));
                mlistViewObjs.add(new ListViewObjPids("Freeze DTC", "Congela DTC(Diagnostic trouble codes)", ary[2]));
                mlistViewObjs.add(new ListViewObjPids("Fuel System Status", "Status do Sistema de Combustível", ary[3]));
                mlistViewObjs.add(new ListViewObjPids("Calculated Engine Load", "Mede a quantidade de ar (e combustível) que o automóvel injeta para dentro do motor e, em seguida, compara esse valor para o máximo teórico.", ary[4]));
                mlistViewObjs.add(new ListViewObjPids("Engine Coolant Temperature", "Determina a temperatura do fluido de arrefecimento do motor.", ary[5]));
                mlistViewObjs.add(new ListViewObjPids("Short Term Fuel Trim—Bank 1", "", ary[6]));
                mlistViewObjs.add(new ListViewObjPids("Long Term Fuel Trim—Bank 1", "", ary[7]));
                mlistViewObjs.add(new ListViewObjPids("Short Term Fuel Trim—Bank 2", "", ary[8]));
                mlistViewObjs.add(new ListViewObjPids("Long Term Fuel Trim—Bank 2", "", ary[9]));
                mlistViewObjs.add(new ListViewObjPids("Fuel Pressure", "", ary[10]));
                mlistViewObjs.add(new ListViewObjPids("MAP - Intake Manifold Absolute Pressure", "010B- É um sensor que gera um sinal que é proporcional à quantidade de vácuo no colector de admissão. O computador do motor, em seguida, usa essas informações para ajustar o ponto de ignição e de enriquecimento de combustível.", ary[11]));
                mlistViewObjs.add(new ListViewObjPids("Engine RPM", "Informa as rotações por minuto do veículo.", ary[12]));
                mlistViewObjs.add(new ListViewObjPids("Vehicle Speed", "Velocidade do Veículo", ary[13]));
                mlistViewObjs.add(new ListViewObjPids("Timing Advance", "", ary[14]));
                mlistViewObjs.add(new ListViewObjPids("IAT - Intake Air Temperature", "010F - Usado para consumo.", ary[15]));
                mlistViewObjs.add(new ListViewObjPids("MAF Air Flow Rate", "", ary[16]));
                mlistViewObjs.add(new ListViewObjPids("Throttle Position", "", ary[17]));
                mlistViewObjs.add(new ListViewObjPids("Commanded Secondary Air Status", "", ary[18]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensors Present (in 2 banks)", "", ary[19]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 1", "", ary[20]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 2", "", ary[21]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 3", "", ary[22]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 4", "", ary[23]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 5", "", ary[24]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 6", "", ary[25]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 7", "", ary[26]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 8", "", ary[27]));
                mlistViewObjs.add(new ListViewObjPids("OBD Standards This Vehicle", "Descreve quais as normas OBD essa unidade foi concebida para cumprir.", ary[28]));
                mlistViewObjs.add(new ListViewObjPids("Oxygen Sensors Present (in 4 banks)", "", ary[29]));
                mlistViewObjs.add(new ListViewObjPids("Auxiliary Input Status", "", ary[30]));
                mlistViewObjs.add(new ListViewObjPids("Run Time Since Engine Start", "", ary[31]));
            }else{
                if(lista.equals("4120")) {
                    mlistViewObjs.add(new ListViewObjPids("Distance traveled with malfunction indicator lamp (MIL) on", "", ary[1]));
                    mlistViewObjs.add(new ListViewObjPids("Fuel Rail Pressure (relative to manifold vacuum)", "", ary[2]));
                    mlistViewObjs.add(new ListViewObjPids("Fuel Rail Gauge Pressure (diesel, or gasoline direct injection)", "", ary[3]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 1", "", ary[4]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 2", "", ary[5]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 3", "", ary[6]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 4", "", ary[7]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 5", "", ary[8]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 6", "", ary[9]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 7", "", ary[10]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 8", "", ary[11]));
                    mlistViewObjs.add(new ListViewObjPids("Commanded EGR", "", ary[12]));
                    mlistViewObjs.add(new ListViewObjPids("EGR Error", "", ary[13]));
                    mlistViewObjs.add(new ListViewObjPids("Commanded evaporative purge", "", ary[14]));
                    mlistViewObjs.add(new ListViewObjPids("Fuel Tank Level Input", "", ary[15]));
                    mlistViewObjs.add(new ListViewObjPids("Warm-ups since codes cleared", "", ary[16]));
                    mlistViewObjs.add(new ListViewObjPids("Distance traveled since codes cleared", "", ary[17]));
                    mlistViewObjs.add(new ListViewObjPids("Evap. System Vapor Pressure", "", ary[18]));
                    mlistViewObjs.add(new ListViewObjPids("Absolute Barometric Pressure", "", ary[19]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 1", "", ary[20]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 2", "", ary[21]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 3", "", ary[22]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 4", "", ary[23]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 5", "", ary[24]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 6", "", ary[25]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 7", "", ary[26]));
                    mlistViewObjs.add(new ListViewObjPids("Oxygen Sensor 8", "", ary[27]));
                    mlistViewObjs.add(new ListViewObjPids("Catalyst Temperature: Bank 1, Sensor 1", "", ary[28]));
                    mlistViewObjs.add(new ListViewObjPids("Catalyst Temperature: Bank 2, Sensor 1", "", ary[29]));
                    mlistViewObjs.add(new ListViewObjPids("Catalyst Temperature: Bank 1, Sensor 2", "", ary[30]));
                    mlistViewObjs.add(new ListViewObjPids("Catalyst Temperature: Bank 2, Sensor 2", "", ary[31]));
                }else{
                    if(lista.equals("4140")) {
                        mlistViewObjs.add(new ListViewObjPids("Monitor status this drive cycle", "", ary[1]));
                        mlistViewObjs.add(new ListViewObjPids("Control module voltage", "", ary[2]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute load value", "0143 - Usado para consumo", ary[3]));
                        mlistViewObjs.add(new ListViewObjPids("Fuel–Air commanded equivalence ratio", "", ary[4]));
                        mlistViewObjs.add(new ListViewObjPids("Relative throttle position", "", ary[5]));
                        mlistViewObjs.add(new ListViewObjPids("Ambient air temperature", "", ary[6]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute throttle position B", "", ary[7]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute throttle position C", "", ary[8]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute throttle position D", "", ary[9]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute throttle position E", "", ary[10]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute throttle position F", "", ary[11]));
                        mlistViewObjs.add(new ListViewObjPids("Commanded throttle actuator", "", ary[12]));
                        mlistViewObjs.add(new ListViewObjPids("Time run with MIL on", "", ary[13]));
                        mlistViewObjs.add(new ListViewObjPids("Time since trouble codes cleared", "", ary[14]));
                        mlistViewObjs.add(new ListViewObjPids("Maximum value for Fuel–Air equivalence ratio, oxygen sensor voltage, oxygen sensor current, and intake manifold absolute pressure", "", ary[15]));
                        mlistViewObjs.add(new ListViewObjPids("Maximum value for air flow rate from mass air flow sensor", "", ary[16]));
                        mlistViewObjs.add(new ListViewObjPids("Fuel Type", "", ary[17]));
                        mlistViewObjs.add(new ListViewObjPids("Ethanol fuel %", "", ary[18]));
                        mlistViewObjs.add(new ListViewObjPids("Absolute Evap system Vapor Pressure", "", ary[19]));
                        mlistViewObjs.add(new ListViewObjPids("Evap system vapor pressure", "", ary[20]));
                        mlistViewObjs.add(new ListViewObjPids("Short term secondary oxygen sensor trim, A: bank 1, B: bank 3", "", ary[21]));
                        mlistViewObjs.add(new ListViewObjPids("Long term secondary oxygen sensor trim, A: bank 1, B: bank 3", "", ary[22]));
                        mlistViewObjs.add(new ListViewObjPids("Short term secondary oxygen sensor trim, A: bank 2, B: bank 4", "", ary[23]));
                        mlistViewObjs.add(new ListViewObjPids("Long term secondary oxygen sensor trim, A: bank 2, B: bank 4", "", ary[24]));
                        mlistViewObjs.add(new ListViewObjPids("Fuel rail absolute pressure", "", ary[25]));
                        mlistViewObjs.add(new ListViewObjPids("Relative accelerator pedal position", "", ary[26]));
                        mlistViewObjs.add(new ListViewObjPids("Hybrid battery pack remaining life", "", ary[27]));
                        mlistViewObjs.add(new ListViewObjPids("Engine oil temperature", "", ary[28]));
                        mlistViewObjs.add(new ListViewObjPids("Fuel injection timing", "", ary[29]));
                        mlistViewObjs.add(new ListViewObjPids("Engine fuel rate", "PID 015E - Usado para calculo do consumo", ary[30]));
                        mlistViewObjs.add(new ListViewObjPids("Emission requirements to which vehicle is designed", "", ary[31]));
                    }
                }
            }

            ListViewAdapterPids adapter = new ListViewAdapterPids(PIDSActivity.this, mlistViewObjs);

            mRecyclerView.setClickable(false);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(RecyclerView.VISIBLE);
        }catch (Exception e){
        Log.w("ERRO","E: "+e.toString());
        }


    }

}
