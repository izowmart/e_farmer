package com.example.e_farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.models.User;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MyFarmTasks extends AppCompatActivity {

    private static final String TAG = "MyFarmTasks";
    private Toolbar toolbar;
    private EditText taskName, taskAssignee, taskSupervisor, taskStart, taskDue, taskDescription;
    private Button saveTask, savingBtn;
    private ProgressBar progressBar;
    private String name, assignee, supervisor, start, due, description;

    private FarmTask farmTask;
    private Box<FarmTask> farmTaskBox;
    private BoxStore farmerApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_farm_tasks);

        //        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        farmTaskBox = farmerApp.boxFor(FarmTask.class);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_farm_tasks);
        toolbar.setTitle("Add Farm Task");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        taskName = findViewById(R.id.task_name);
        taskAssignee = findViewById(R.id.task_assignee);
        taskSupervisor = findViewById(R.id.task_supervisor);
        taskStart = findViewById(R.id.task_start);
        taskDue = findViewById(R.id.task_due);
        taskDescription = findViewById(R.id.task_description);

        savingBtn = findViewById(R.id.saving_tasks_btn);
        progressBar = findViewById(R.id.tasks_progress_bar);
        saveTask = findViewById(R.id.save_task_btn);

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFarmTask();
            }
        });

    }

    private void addFarmTask() {
        name = taskName.getText().toString().trim();
        assignee = taskAssignee.getText().toString().trim();
        supervisor = taskSupervisor.getText().toString().trim();
        start = taskStart.getText().toString().trim();
        due = taskDue.getText().toString().trim();
        description = taskDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            taskName.setError("Field Required!");
        } else if (TextUtils.isEmpty(assignee)) {
            taskAssignee.setError("Field Required!");
        } else if (TextUtils.isEmpty(supervisor)) {
            taskSupervisor.setError("Field Required!");
        } else if (TextUtils.isEmpty(start)) {
            taskStart.setError("Field Required!");
        } else if (TextUtils.isEmpty(due)) {
            taskDue.setError("Field Required!");
        } else if (TextUtils.isEmpty(description)) {
            taskDescription.setError("Field Required!");
        } else {

            AsyncTask<Void, Void, Void> tasks = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    saveTask.setVisibility(View.GONE);
                    savingBtn.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Thread.sleep(1500);

                        // This where everything is collected and stored in our local database
                        User user = new User();

                        farmTask = new FarmTask();

                        farmTask.setName(name);
                        farmTask.setAssignee(assignee);
                        farmTask.setSupervisor(supervisor);
                        farmTask.setStart(start);
                        farmTask.setDue(due);
                        farmTask.setDescription(description);

                        farmTask.user.setTarget(user);
                        farmTaskBox.put(farmTask);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }


                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);
                    saveTask.setVisibility(View.VISIBLE);
                    savingBtn.setVisibility(View.GONE);

                    Intent toDashboardActivity = new Intent(MyFarmTasks.this, MainActivity.class);
                    startActivity(toDashboardActivity);
                    finish();
                    Toast.makeText(MyFarmTasks.this, "Task added successfully", Toast.LENGTH_SHORT).show();

                }
            };

            tasks.execute();



        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
