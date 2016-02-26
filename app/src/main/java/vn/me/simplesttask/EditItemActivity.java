package vn.me.simplesttask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String content = getIntent().getStringExtra(AppConst.ITEM);
        etContent = (EditText) findViewById(R.id.etContent);
        etContent.setText(content);
    }

    public void onSaveEdit(View view) {
        String newContent = etContent.getText().toString();
        Intent data = new Intent();
        data.putExtra(AppConst.ITEM, newContent);
        setResult(RESULT_OK, data);
        finish();
    }
}
