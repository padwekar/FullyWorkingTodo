package healthsignz.com.todalist;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecycerView ;
    EditText edittext_newItem ;
    ImageButton btn_add ;
    RecyclerView.LayoutManager layoutManager ;
    RecyclerViewAdapter mRecyclerViewAdapter ;
    DbHelper database;
    List<String> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= new DbHelper(MainActivity.this);
        itemList = new ArrayList<String>();
        try {
            itemList = database.getList();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        edittext_newItem = (EditText)findViewById(R.id.edittext_newElement);
        btn_add = (ImageButton)findViewById(R.id.button_img_add);
        //     btn_add.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(MainActivity.this);

        mRecycerView = (RecyclerView)findViewById(R.id.recyclerView);

        mRecycerView.setLayoutManager(layoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,itemList);
        mRecycerView.setAdapter(mRecyclerViewAdapter);
    }


    public void onClick(View v) {
        String item = edittext_newItem.getText().toString();
        if (item.isEmpty()){
            Toast.makeText(MainActivity.this,"Edit Text Empty",Toast.LENGTH_SHORT).show();
            return;
        }
            itemList.add(item);
     //       database.insertRecord(item);
            mRecyclerViewAdapter.notifyItemInserted(itemList.size() - 1);
            edittext_newItem.setText("");
            ActivityCompat.invalidateOptionsMenu(MainActivity.this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        TextView tv = new TextView(this);
        tv.setText("COUNT  :  " + RecyclerViewAdapter.items.size());
        tv.setTextSize(30);
        tv.setTextColor(Color.WHITE);
        tv.setPadding(0,5,40,5);
        // tv.setPaddingRelative(5,5,5,5);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(14);
        menu.add(0, 1, 1, "COUNT  :"+RecyclerViewAdapter.items.size()).setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        String names[]=RecyclerViewAdapter.items.toArray(new String[RecyclerViewAdapter.items.size()]);
        database.insertAllRecord(names);
        Toast.makeText(MainActivity.this,"INSERTED VALUES",Toast.LENGTH_SHORT).show();
    }
}
