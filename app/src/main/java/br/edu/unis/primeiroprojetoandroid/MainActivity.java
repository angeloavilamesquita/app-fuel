package br.edu.unis.primeiroprojetoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtGasolinePrice;
    private EditText edtAlcoholPrice;
    private Button btnChooseFuel;
    private TextView txtFuelResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp1 = this.getSharedPreferences("last_query", MODE_PRIVATE);
        sp1.edit().clear().apply();

        startWidgets();
        // verificar se tem sharedPreference já salvo
        SharedPreferences sp = this.getSharedPreferences("last_query", MODE_PRIVATE);

            this.edtAlcoholPrice.setText("" + sp.getFloat("lastAlcoholPrice", 0F));
            this.edtGasolinePrice.setText("" + sp.getFloat("lastGasolinePrice", 0F));
            this.txtFuelResult.setText("" + sp.getString("lastPriceResult", ""));


        chooseFuelAction();
    }

    private void startWidgets() {
        this.edtGasolinePrice = findViewById(R.id.edit_text_gasoline_price);
        this.edtAlcoholPrice = findViewById(R.id.edit_text_alcohol_price);
        this.btnChooseFuel = findViewById(R.id.btn_choose_fuel);
        this.txtFuelResult = findViewById(R.id.txt_fuel_result);
    }

    private void chooseFuelAction() {
        this.btnChooseFuel.setOnClickListener(view -> {
            if (isEmptyFields()) return;

            float alcoholPrice = Float.parseFloat(edtAlcoholPrice.getText().toString());
            float gasolinePrice = Float.parseFloat(edtGasolinePrice.getText().toString());
            float priceResult = (alcoholPrice * 100) / gasolinePrice;

            String result = String.valueOf(R.string.label_gasoline);
            if (priceResult < 70) result = String.valueOf(R.string.label_alcohol);
            txtFuelResult.setText(result);

            // salvo em shared preference para a próxima consulta
            SharedPreferences sp = this.getSharedPreferences("last_query", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("lastAlcoholPrice", alcoholPrice);
            editor.putFloat("lastGasolinePrice", gasolinePrice);
            editor.putString("lastPriceResult", result);
            editor.apply();


       });
    }

    private boolean isEmptyFields() {
        if (edtGasolinePrice.length() == 0) {
            showToast(this);
            edtGasolinePrice.requestFocus();
            return true;
        }
        if (edtAlcoholPrice.length() == 0) {
            showToast(this);
            edtAlcoholPrice.requestFocus();
            return true;
        }
        return false;
    }

    private static void showToast(Context context) {
        Toast.makeText(context, R.string.toast_empty_fields, Toast.LENGTH_SHORT).show();
    }

    /* You have put the attribute android:onClick na interface (xml)
    public void chooseFuelAction(View view) {
        float alcoholPrice = Float.parseFloat(edtAlcoholPrice.getText().toString());
        float gasolinePrice = Float.parseFloat(edtGasolinePrice.getText().toString());
        float priceResult = (alcoholPrice * 100) / gasolinePrice;

        if (priceResult >= 70) {
            txtFuelResult.setText(R.string.label_gasoline);
        } else {
            txtFuelResult.setText(R.string.label_alcohol);
        }
    }*/
}