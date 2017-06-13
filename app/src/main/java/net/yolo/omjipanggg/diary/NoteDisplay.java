package net.yolo.omjipanggg.diary;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.yolo.omjipanggg.diary.TaskContract;
import net.yolo.omjipanggg.diary.TaskDBHelper;

public class NoteDisplay extends AppCompatActivity {

    private ImageButton fab;
    private ListView listView;
    private ListAdapter listAdapter;
    private TaskDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);
        listView = (ListView) findViewById(R.id.listDiary);
        fab = (ImageButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    private void addTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Leave a note!");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task = inputField.getText().toString();

                helper = new TaskDBHelper(NoteDisplay.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.Columns.DIARY, task);

                db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                Log.d("NoteDisplay", inputField.getText().toString());
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


    public void onDeleteButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.list);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE, TaskContract.Columns.DIARY, task);


        helper = new TaskDBHelper(NoteDisplay.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

    private void updateUI() {
        helper = new TaskDBHelper(NoteDisplay.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.DIARY},
                null, null, null, null, null);

        ListAdapter listAdapter = new SimpleCursorAdapter(
                NoteDisplay.this,
                R.layout.list_view,
                cursor,
                new String[]{TaskContract.Columns.DIARY},
                new int[]{R.id.list},
                0
        );
        listView.setAdapter(listAdapter);
    }
}
