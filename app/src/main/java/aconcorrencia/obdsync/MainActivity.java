package aconcorrencia.obdsync;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Button btnConectar;
    private String nameDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        nameDevice= sharedPreferences.getString("nameDevice", "");
        btnConectar= (Button)findViewById(R.id.btnConectar);
    }


    public void btnProcurar(View view){
        Intent it = new Intent(MainActivity.this,ProcurarActivity.class);
        startActivity(it);
    }

    public void btnConectar(View view){
        if(nameDevice.length()>0){
            Intent it = new Intent(MainActivity.this,OBDActivity.class);
            startActivity(it);
        }else{
            Toast.makeText(MainActivity.this, "Clique em Procurar e selecione um dispositivo!", Toast.LENGTH_LONG).show();
        }
    }

    public void btnSobre(View view){

    }

    @Override
    protected void onResume() {
        super.onResume();
        nameDevice= sharedPreferences.getString("nameDevice", "");
        if(nameDevice.length()>0){
            btnConectar.setText("CONECTAR <"+nameDevice+">");
        }
    }
}
