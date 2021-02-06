package com.connotation_vibes.models;

import androidx.recyclerview.widget.RecyclerView;

public class Article {
    private String title;
    private String source;
    private String author;
    private String content;
    private double subjectivity;
    private double polarity;

    public Article() {
        // Empty constructor
    }

    public Article(String title, String source, String author, String content, double subjectivity, double objectivity) {
        this.title = title;
        this.source = source;
        this.author = author;
        this.content = content;
        this.subjectivity = subjectivity;
        this.polarity = objectivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getSubjectivity() {
        return subjectivity;
    }

    public void setSubjectivity(float subjectivity) {
        this.subjectivity = subjectivity;
    }

    public double getPolarity() {
        return polarity;
    }

    public void setPolarity(float objectivity) {
        this.polarity = objectivity;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", subjectivity=" + subjectivity +
                ", polarity=" + polarity +
                '}';
    }
}
