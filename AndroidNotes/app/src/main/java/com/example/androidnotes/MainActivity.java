package com.example.androidnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private final ArrayList<Notes> notesList = new ArrayList<>();  // Notes will be stored here
    private RecyclerView recyclerView; // Layout's recyclerview
    private MyAdapter myAdapter; // Data to recyclerview adapter
    private static final int save_Counter = 1, edit_counter = 2;
    private ActivityResultLauncher<Intent> activitySaveResult, activityEditResult;

    public final static String TITLE="title";
    public final static String CONTENT="content";

    public final static String DATE="date";
    public final static String NOTEDATA="noteData";
    public final static String POSITION="position";

    private static final String TAG = "MainActivity";
    public static String empty_string="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notes_main);
        myAdapter = new MyAdapter(notesList, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "onCreate: "+ notesList);
        activitySaveResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == AddOrEditActivity.RESULT_OK){
                            doSaveEdit(save_Counter,result.getData());
                        }
                    }
                }
        );

        activityEditResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == AddOrEditActivity.RESULT_OK){
                            doSaveEdit(edit_counter,result.getData());
                        }
                    }
                }
        );

        loadFile();
        updateNotesCount();

    }

    public void updateNotesCount()
    {
        if (notesList.size() != 0)
            setTitle(getString(R.string.app_name)+ " (" + notesList.size() + ")");
        else
            setTitle(getString(R.string.app_name));
    }

    @Override
    public void onClick(View v) {
        Notes notes = notesList.get(recyclerView.getChildLayoutPosition(v));
        Intent data = new Intent(this, AddOrEditActivity.class);
        data.putExtra(NOTEDATA, notes);
        data.putExtra(POSITION, recyclerView.getChildLayoutPosition(v));
        activityEditResult.launch(data);
    }

    // From OnLongClickListener
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        final int pos = recyclerView.getChildLayoutPosition(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //builder.setIcon(R.drawable.icon1);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                notesList.remove(pos);
                myAdapter.notifyItemRemoved(pos);
                //myAdapter.notifyDataSetChanged();
                updateNotesCount();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setMessage("Delete Note '"+notesList.get(pos).getTitle()+"'?");
        //builder.setTitle("Yes/No Dialog");

        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    private void loadFile() {

        Log.d(TAG, "loadFile: Loading JSON File into App");
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            JsonReader reader = new JsonReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            reader.beginArray();
            while (reader.hasNext())
            {
                Notes n = new Notes();
                reader.beginObject();
                while (reader.hasNext())
                {
                    String name = reader.nextName();
                    switch (name)
                    {
                        case TITLE:
                            n.setTitle(reader.nextString());
                            break;
                        case CONTENT:
                            n.setContent(reader.nextString());
                            break;
                        case DATE:
                            n.setDate(reader.nextString());
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
                Log.d(TAG, "loadFile: File gets Load Load in Array" + n);
                notesList.add(n);
            }
            reader.endArray();
        }
        catch (FileNotFoundException e) {
            Toast.makeText(this, "No JSON file found", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.infomenu)
        {
            Intent intent = new Intent(this, AboutDemoActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId()==R.id.addnotesmenu) {
            Intent intent2 = new Intent(this, AddOrEditActivity.class);
            activitySaveResult.launch(intent2);
            return true;
        }
        else {
            Toast.makeText(this, "Invalid menu selection", Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    protected void doSaveEdit(int requestCode, @Nullable Intent data){


            if(requestCode==save_Counter)
            {
                Notes note = new Notes();
                assert data != null;
                note.setTitle(data.getStringExtra(TITLE));
                note.setContent(data.getStringExtra(CONTENT));
                note.setDate(data.getStringExtra(DATE));
                Log.d(TAG, "requestCode==save_Counter"+note);
                notesList.add(0, note);
                myAdapter.notifyDataSetChanged();
            }
            else if(requestCode==edit_counter)
            {
                Notes notes = new Notes();
                assert data != null;
                notes.setTitle(data.getStringExtra(TITLE));
                notes.setContent(data.getStringExtra(CONTENT));
                notes.setDate(data.getStringExtra(DATE));
                notesList.remove(data.getIntExtra(POSITION, -1));
                notesList.add(0, notes);
                myAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this, "Invalid menu selection", Toast.LENGTH_SHORT).show();
            }

    }


    @Override
    protected void onResume()
    {
        updateNotesCount();
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        saveNotes();

    }

    private void saveNotes() {

        Log.d(TAG, "Saving JSON File");
        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
            writer.setIndent("  ");
            writer.beginArray();
            for (Notes n : notesList)
            {
                writer.beginObject();
                writer.name(TITLE).value(n.getTitle());
                Log.d(TAG, "saveNotes: "+n.getContent()+ n.getTitle());
                writer.name(CONTENT).value(n.getContent());
                writer.name(DATE).value(n.getDate());
                writer.endObject();
            }
            writer.endArray();
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Closing app!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}