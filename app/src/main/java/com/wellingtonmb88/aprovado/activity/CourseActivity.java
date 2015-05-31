package com.wellingtonmb88.aprovado.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wellingtonmb88.aprovado.R;
import com.wellingtonmb88.aprovado.async.SQliteAsyncTask;
import com.wellingtonmb88.aprovado.entity.Course;
import com.wellingtonmb88.aprovado.utils.CommonUtils;
import com.wellingtonmb88.aprovado.utils.Constants;

import java.text.ParseException;

/**
 * Created by Wellington on 26/05/2015.
 */
public class CourseActivity extends AppCompatActivity {

    private EditText mDisciplina;
    private EditText mProfessor;
    private EditText mEditTextM1;
    private EditText mEditTextM2;
    private EditText mEditTextB1;
    private EditText mEditTextB2;
    private EditText mEditTextMB1;
    private EditText mEditTextMB2;
    private EditText mEditTextMF;
    
    private Spinner mSpinnerSemestre;
    private Toolbar mToolbarLayout;
    private FloatingActionButton mSaveFAB;

    private Course mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        loadUI();
        loadDataUI();
        setListener();
    }

    private void loadUI(){

        FrameLayout toolbar = (FrameLayout) findViewById(R.id.toolbar);
        mToolbarLayout = (Toolbar) toolbar.findViewById(R.id.toolbar_layout);

        mSaveFAB = (FloatingActionButton) findViewById(R.id.floatingActionButton_save);
        mDisciplina = (EditText) findViewById(R.id.editText_disciplina);
        mProfessor = (EditText) findViewById(R.id.editText_professor);
        mEditTextM1 = (EditText) findViewById(R.id.editText_m1);
        mEditTextM2 = (EditText) findViewById(R.id.editText_m2);
        mEditTextB1 = (EditText) findViewById(R.id.editText_b1);
        mEditTextB2 = (EditText) findViewById(R.id.editText_b2);
        mEditTextMB1 = (EditText) findViewById(R.id.editText_mb1);
        mEditTextMB2 = (EditText) findViewById(R.id.editText_mb2);
        mEditTextMF = (EditText) findViewById(R.id.editText_mf);

        mSpinnerSemestre = (Spinner) findViewById(R.id.spinner);
    }

    private void loadDataUI(){

        mCourse = new Course();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CourseActivity.this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSemestre.setAdapter(adapter);

        mToolbarLayout.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbarLayout);

        if( getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent != null && intent.hasExtra(Constants.CourseExtra.INTENT_EXTRA)){
            Bundle bundle = intent.getBundleExtra(Constants.CourseExtra.INTENT_EXTRA);
            if(bundle != null){
                mCourse = (Course)bundle.getParcelable(Constants.CourseExtra.BUNDLE_EXTRA);
                if(mCourse != null){

                    if(mCourse.name != null){
                        mDisciplina.setText(mCourse.name);
                        mProfessor.setText(mCourse.professor);
                        mSpinnerSemestre.setSelection(mCourse.semester);
                        validateFields();
                    }
                }
            }
        }

    }

    private void setListener(){
        mSaveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String action = Constants.CourseDatabaseAction.INSERT_COURSE;
                    if(mCourse != null && mCourse.name != null && mCourse.name.length() > 0 ){
                        action = Constants.CourseDatabaseAction.UPDATE_COURSE;
                    }
                    getCourse();
                    if(mDisciplina.getText().length() > 0){
                        SQliteAsyncTask task = new SQliteAsyncTask(getApplicationContext(), null, mCourse);
                        task.execute(action);
                        finish();
                    }else{
                        mDisciplina.setError(getString(R.string.calculator_edittext_error_message));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCourse() throws ParseException {

        mCourse.name = mDisciplina.getText().toString();
        mCourse.professor = mProfessor.getText().toString();
        mCourse.semester = mSpinnerSemestre.getSelectedItemPosition();
        mCourse.m1 = CommonUtils.parseFloatLocaleSensitive(mEditTextM1.getText().toString());
        mCourse.b1 = CommonUtils.parseFloatLocaleSensitive(mEditTextB1.getText().toString());
        mCourse.mediaB1 = CommonUtils.parseFloatLocaleSensitive(mEditTextMB1.getText().toString());
        mCourse.m2 = CommonUtils.parseFloatLocaleSensitive(mEditTextM2.getText().toString());
        mCourse.b2 = CommonUtils.parseFloatLocaleSensitive(mEditTextB2.getText().toString());
        mCourse.mediaB2 = CommonUtils.parseFloatLocaleSensitive(mEditTextMB2.getText().toString());
        mCourse.mediaFinal = CommonUtils.parseFloatLocaleSensitive(mEditTextMF.getText().toString());
    }

    private void validateFields(){
        String m1 = String.valueOf(mCourse.m1);
        String m2 = String.valueOf(mCourse.m2);
        String b1 = String.valueOf(mCourse.b1);
        String b2 = String.valueOf(mCourse.b2);
        String mb1 = String.valueOf(mCourse.mediaB1);
        String mb2 = String.valueOf(mCourse.mediaB2);
        String mf = String.valueOf(mCourse.mediaFinal);

        String ZERO = "0.0";

        if(!ZERO.equals(m1)){
            mEditTextM1.setText(m1);
        }if(!ZERO.equals(m2)){
            mEditTextM2.setText(m2);
        }if(!ZERO.equals(b1)){
            mEditTextB1.setText(b1);
        }if(!ZERO.equals(b2)){
            mEditTextB2.setText(b2);
        }if(!ZERO.equals(mb1)){
            mEditTextMB1.setText(mb1);
        }if(!ZERO.equals(mb2)){
            mEditTextMB2.setText(mb2);
        }if(!ZERO.equals(mf)){
            mEditTextMF.setText(mf);
        }
    }

}
