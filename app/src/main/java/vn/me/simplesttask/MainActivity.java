package vn.me.simplesttask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private int editingPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        lvItems.requestFocus();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        editingPos = position;
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        i.putExtra(AppConst.ITEM, items.get(position));
                        startActivityForResult(i, AppConst.REQUEST_CODE);
                    }
                }
        );
    }

    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();
        if (!itemText.isEmpty()) {
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeItems();
            hideSoftKeyboard(etNewItem);
        } else {
            Toast.makeText(this, "New item is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File taskFile = new File(filesDir, AppConst.TASK_FILE);
        try {
            items = new ArrayList<>(FileUtils.readLines(taskFile));
        } catch (IOException e) {
            items = new ArrayList<>();
            e.printStackTrace();
        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File taskFile = new File(fileDir, AppConst.TASK_FILE);
        try {
            FileUtils.writeLines(taskFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConst.REQUEST_CODE && resultCode == RESULT_OK) {
            if (editingPos != -1) {
                String newContent = data.getStringExtra(AppConst.ITEM);
                items.set(editingPos, newContent);
                itemsAdapter.notifyDataSetChanged();
                editingPos = -1;
                writeItems();
                Toast.makeText(this, "Edit successful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
