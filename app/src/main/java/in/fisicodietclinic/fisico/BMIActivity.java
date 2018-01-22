package in.fisicodietclinic.fisico;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;


public class BMIActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    ProgressDialog dialog;
    String response_string;
    Button calculate;
    EditText edit_weight,edit_height;
    String weight,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

            calculate = (Button) findViewById(R.id.id_calculate);
        edit_height = (EditText) findViewById(R.id.input_edit_height);
        edit_weight = (EditText) findViewById(R.id.input_edit_weight);


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weight = edit_weight.getText().toString();
                height = edit_height.getText().toString();
                Intent intent = new Intent(BMIActivity.this, BMIDetails.class);
                intent.putExtra("weight",weight);
                intent.putExtra("height",height);
                startActivity(intent);

            }
        });
    }
}
