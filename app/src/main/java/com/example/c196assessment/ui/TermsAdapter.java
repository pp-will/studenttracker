package com.example.c196assessment.ui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196assessment.R;
import com.example.c196assessment.database.DateConverter;
import com.example.c196assessment.database.TermEntity;
import com.example.c196assessment.utilities.DateUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    private final List<TermEntity> mTerms;
    private final Context mContext;

    public TermsAdapter(List<TermEntity> mTerms, Context mContext) {
        this.mTerms = mTerms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TermEntity term = mTerms.get(position);
        holder.termTitle.setText(term.getTermTitle());
        DateUtils dateUtils = new DateUtils();
        String startDate = dateUtils.formattedDate(term.getStartDate());
        String endDate = dateUtils.formattedDate(term.getEndDate());
        holder.termDates.setText(startDate + " - " + endDate);

        holder.mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO INent switch to course view
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.term_title)
        TextView termTitle;
        @BindView(R.id.term_dates)
        TextView termDates;
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View termView) {
            super(termView);
            ButterKnife.bind(this, termView);
        }
    }
}
