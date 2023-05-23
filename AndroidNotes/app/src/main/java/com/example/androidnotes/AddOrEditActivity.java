package com.example.androidnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOrEditActivity extends AppCompatActivity {
    EditText heading, content;
    private static final String TAG = "AddOrEditActivity";
    private String oldHeading = "", oldContent = "", newHeading ="", newContent ="";
    private int position;
    private Notes notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatenotes);

        content = findViewById(R.id.notesBottom1);
        heading = findViewById(R.id.notesTitle1);

        heading.setTextIsSelectable(true);
        content.setTextIsSelectable(true);
        content.setMovementMethod(new ScrollingMovementMethod());

        Intent data = getIntent();
        if (data.hasExtra(MainActivity.POSITION))
        {
            position = data.getIntExtra(MainActivity.POSITION,-1);
        }

        if (data.hasExtra(MainActivity.NOTEDATA))
        {
            notes = (Notes) data.getSerializableExtra(MainActivity.NOTEDATA);
            if (notes != null)
            {
                heading.setText(notes.getTitle());
                content.setText(notes.getContent());
                oldHeading = notes.getTitle();
                oldContent = notes.getContent();
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId()==R.id.savenote)
        {
                if (heading.getText().toString().matches("") && content.getText().toString().matches(""))
                {
                    Toast.makeText(this,getString(R.string.noNotes),Toast.LENGTH_LONG).show();

                    finish();

                }
                else if (heading.getText().toString().equals(""))
                {
                    //Toast.makeText(this,getString(R.string.noTitle),Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }

                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(AddOrEditActivity.this,"Enter title to save",Toast.LENGTH_LONG).show();
                        }
                    });

                    builder.setMessage("Title is empty cannot save");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                else
                {
                    if(detectChange())
                        saveDate();
                    else
                        finish();
                }
        }
        else
        {
            Toast.makeText(this,"Invalid Option",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean detectChange()
    {
        newHeading = heading.getText().toString();
        newContent = content.getText().toString();

        if(oldHeading.matches("") && oldContent.matches("") && newHeading.matches("") && newContent.matches(""))
            return false;

        else if(!oldHeading.matches(newHeading) || !oldContent.matches(newContent))
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed() {

        if (!heading.getText().toString().matches("") && detectChange()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(!detectChange()){
                        finish();

                    }
                    else{
                        saveDate();
                    }
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });

            builder.setMessage("Your note is not saved! Save note '"+ heading.getText().toString()+"'?");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if(detectChange() && heading.getText().toString().matches("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }

            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(AddOrEditActivity.this, "Enter a title to save.", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setMessage("Note with empty title cannot be saved. ");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            super.onBackPressed();
        }
    }

    public void saveDate()
    {
        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("E MMM dd',' hh:mm a ");

        Intent data = new Intent();
        data.putExtra(MainActivity.TITLE, heading.getText().toString());
        data.putExtra(MainActivity.CONTENT, content.getText().toString());
        data.putExtra(MainActivity.DATE,ft.format(d));
        if(position !=-1)
            data.putExtra(MainActivity.POSITION, position);
        setResult(RESULT_OK,data);
        Log.d(TAG, "saveDate: "+ data);
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        finish();
    }
}