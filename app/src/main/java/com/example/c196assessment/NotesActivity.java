package com.example.c196assessment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.NoteDao;
import com.example.c196assessment.database.NoteEntity;
import com.example.c196assessment.ui.NotesAdapter;
import com.example.c196assessment.viewmodel.CourseViewModel;
import com.example.c196assessment.viewmodel.NoteViewModel;
import com.example.c196assessment.viewmodel.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.c196assessment.utilities.Constants.COURSE_ID_KEY;
import static com.example.c196assessment.utilities.Constants.NOTE_ID_KEY;
import static com.example.c196assessment.utilities.Constants.TERM_ID_KEY;

public class NotesActivity extends AppCompatActivity {

    ListView listView;
    AppDatabase mDb;

    /*protected void onResume() {
        super.onResume();
        updateList();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setTitle("Course Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.notesListView);
        mDb = AppDatabase.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);

        //List View

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
            int noteId;
            List<NoteEntity> notesList = mDb.noteDao().getCourseNotesList(courseId);
            noteId = notesList.get(position).getId();
            intent.putExtra(getString(R.string.NOTE_ID_KEY), noteId);
            startActivity(intent);
        });

        // End List View
        updateList(courseId);

        FloatingActionButton noteFab = findViewById(R.id.floatingActionButton);
        noteFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                intent.putExtra(COURSE_ID_KEY, courseId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);

        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, EditCourse.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList(int courseId) {
        List<NoteEntity> courseNotes = mDb.noteDao().getCourseNotesList(courseId);
        String[] notes = new String[courseNotes.size()];
        if(!courseNotes.isEmpty()) {
            for(int i = 0; i < courseNotes.size(); i++) {
                notes[i] = courseNotes.get(i).getNoteText();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    /*@BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<NoteEntity> noteData = new ArrayList<>();
    List<NoteEntity> courseNotes = new ArrayList<>();
    private NotesAdapter mAdapter;
    private NoteViewModel noteViewModel;
    NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.course_notes));

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);
        Log.println(Log.INFO, "TAG", "COURSE ID FROM NOTES :: " + courseId);
        NoteViewModel nvm = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), courseId)).get(NoteViewModel.class);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        //noteViewModel.loadData(courseId);
        //nvm.loadData(courseId);

        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                noteData.clear();
                noteData.addAll(noteEntities);

                if(mAdapter == null) {
                    mAdapter = new NotesAdapter(noteData, NotesActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        nvm.mCourseNotes.observe(this, notesObserver);
        //courseNotes = noteDao.getCourseNotesList(courseId); // add db.noteDao.....
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new NotesAdapter(noteData, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, EditCourse.class);
            // Add extras!!
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }*/
}