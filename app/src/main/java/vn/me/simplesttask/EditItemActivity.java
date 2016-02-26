package vn.me.simplesttask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        etContent.setSelection(content.length());

        etContent.requestFocus();
    }

    public void onSaveEdit(View view) {
        String newContent = etContent.getText().toString();
        hideSoftKeyboard(etContent);
        Intent data = new Intent();
        data.putExtra(AppConst.ITEM, newContent);
        setResult(RESULT_OK, data);
        finish();
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
