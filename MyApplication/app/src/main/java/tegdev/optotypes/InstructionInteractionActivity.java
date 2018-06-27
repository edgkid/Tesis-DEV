package tegdev.optotypes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructionInteractionActivity extends AppCompatActivity {

    TextView textMessageInstruction;
    ImageView imageInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_interaction);

        textMessageInstruction = (TextView) findViewById(R.id.idTextInstruction);
        imageInstruction = (ImageView) findViewById(R.id.idImageInstruction);

    }
}
