package tegdev.optotypes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DiagnosticActivity extends AppCompatActivity {

    ImageView imagePhoto;

    TextView textDate;
    TextView textPatient;
    TextView textYears;
    TextView textSex;
    TextView textReazon;
    TextView textAVRE;
    TextView textAVLE;
    TextView textAVRP;
    TextView textAVLP;
    TextView textCenter;
    TextView textSustain;
    TextView textMaintain;

    Button comeBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

    }
}
