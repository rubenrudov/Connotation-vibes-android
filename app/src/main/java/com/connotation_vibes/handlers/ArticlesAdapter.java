package com.connotation_vibes.handlers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.connotation_vibes.R;
import com.connotation_vibes.models.Article;

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Article> articles;

    public ArticlesAdapter(Context context, ArrayList<Article> countries) {
        this.context = context;
        this.articles = countries;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, source, author;
        LinearLayout layout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView.findViewById(R.id.layout);
            this.title = itemView.findViewById(R.id.articleTitle);
            this.source = itemView.findViewById(R.id.articleSource);
            this.author = itemView.findViewById(R.id.articleAuthor);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new ArticlesAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Article article = this.articles.get(position);
        viewHolder.title.setText("Title: " + article.getTitle());
        viewHolder.source.setText("Source: " + article.getSource());
        viewHolder.author.setText("Author: " + article.getAuthor());
        // Short and long click listeners
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(context, Article.class);
                intent.putExtra("title", articles.get(position).getTitle());
                intent.putExtra("source", articles.get(position).getSource());
                intent.putExtra("author", articles.get(position).getAuthor());
                intent.putExtra("content", articles.get(position).getContent());
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                final ReadMoreDialog dialog = new ReadMoreDialog(ArticlesAdapter.this.context, intent);
                int DeviceTotalWidth = metrics.widthPixels;
                int DeviceTotalHeight = metrics.heightPixels;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.article_content_dialog);
                dialog.setCancelable(true);
                Objects.requireNonNull(dialog.getWindow()).setLayout(DeviceTotalWidth ,DeviceTotalHeight);
                dialog.show();
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Intent intent = new Intent(context, Article.class);
                intent.putExtra("polarity", String.valueOf(articles.get(position).getPolarity()));
                intent.putExtra("subjectivity", String.valueOf(articles.get(position).getSubjectivity()));
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                final BarChartDialog dialog = new BarChartDialog(ArticlesAdapter.this.context, intent);
                int DeviceTotalWidth = metrics.widthPixels;
                int DeviceTotalHeight = metrics.heightPixels;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bar_chart_dialog);
                dialog.setCancelable(true);
                Objects.requireNonNull(dialog.getWindow()).setLayout(DeviceTotalWidth, DeviceTotalHeight);
                dialog.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public void updateData(ArrayList<Article> newArticles) {
        articles.clear();
        articles.addAll(newArticles);
        notifyDataSetChanged();
    }

    static class ReadMoreDialog extends Dialog {
        Context context;
        Intent intent;
        TextView dialogTitle, dialogSource, dialogAuthor, dialogContent;
        ReadMoreDialog(@NonNull Context context, Intent intent) {
            super(context);
            this.context = context;
            this.intent = intent;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.dialogTitle = findViewById(R.id.dialogTitle);
            this.dialogSource = findViewById(R.id.dialogSource);
            this.dialogAuthor = findViewById(R.id.dialogAuthor);
            this.dialogContent = findViewById(R.id.dialogContent);
            this.dialogContent.setText(Objects.requireNonNull(this.intent.getExtras()).getString("source"));
            this.dialogAuthor.setText(Objects.requireNonNull(this.intent.getExtras()).getString("author"));
            this.dialogContent.setText(Objects.requireNonNull(this.intent.getExtras()).getString("content"));
        }
    }

    static class BarChartDialog extends Dialog {
        Context context;
        Intent intent;
        TextView polarity, subjectivity;

        public BarChartDialog(@NonNull Context context, Intent intent) {
            super(context);
            this.context = context;
            this.intent = intent;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            this.polarity = findViewById(R.id.polarity);
            this.subjectivity = findViewById(R.id.subjectivity);
            this.polarity.setText(this.intent.getExtras().getString("polarity"));
            this.subjectivity.setText(this.intent.getExtras().getString("subjectivity"));
        }
    }
}
