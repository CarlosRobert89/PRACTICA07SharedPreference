package carlos.robert.a07sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnGuardar;
    private Button btnBorrarDatos;
    private ImageButton btnBorrarNombre;
    private ImageButton btnBorrarEdad;
    private Button btnJson;

    //sólo permite guardar textos, boolean e int (ya que es un fichero xml)
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVista();
        //generar el fichero xml en el que voy a escribir (nombre fichero, modo)
        sp = getSharedPreferences(Constantes.PERSONA, MODE_PRIVATE);

        rellenarDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNombre.getText().toString().isEmpty()
                        || txtEdad.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Faltan DATOS", Toast.LENGTH_SHORT).show();
                } else {
                    String nombre = txtNombre.getText().toString();
                    int edad = Integer.parseInt(txtEdad.getText().toString());
                    //escribir en él editando el fichero
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(Constantes.NOMBRE, nombre);
                    editor.putInt(Constantes.EDAD, edad);
                    //aplicar cambios
                    editor.apply();
                    //vaciar
                    txtNombre.setText("");
                    txtEdad.setText("");
                }
            }
        });

        btnBorrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                txtNombre.setText("");
                txtEdad.setText("");
            }
        });

        btnBorrarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.NOMBRE);
                editor.apply();
                txtNombre.setText("");
            }
        });

        btnBorrarEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.EDAD);
                editor.apply();
                txtEdad.setText("");
            }
        });

        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JsonActivity.class);
                startActivity(intent); //abrir siguiente actividad
            }
        });
    }

    //al iniciar aparecen los datos guardados en el xml
    private void rellenarDatos() {
        String nombre = sp.getString(Constantes.NOMBRE, "");
        int edad = sp.getInt(Constantes.EDAD, -1);

        if (!nombre.isEmpty()) {
            txtNombre.setText(nombre);
        }
        if (edad != -1) {
            txtEdad.setText(String.valueOf(edad));
        }
    }

    private void inicializarVista() {
        txtNombre = findViewById(R.id.txtNombreMain);
        txtEdad = findViewById(R.id.txtEdadMAin);
        btnGuardar = findViewById(R.id.btnGuardarMain);
        btnBorrarDatos = findViewById(R.id.btnBorrarDatosMain);
        btnBorrarNombre = findViewById(R.id.btnBorrarNombreMain);
        btnBorrarEdad = findViewById(R.id.btnBorrarEdadMain);
        btnJson = findViewById(R.id.btnJSON);
    }
}