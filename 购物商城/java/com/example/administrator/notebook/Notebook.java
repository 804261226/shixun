package com.example.administrator.notebook;

public class Notebook {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String text;
    String time;
    public Notebook(){

    }
    public Notebook(String text, String time) {
        this.text = text;
        this.time = time;
    }

    public Notebook(int id, String text, String time) {
        this.id = id;
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
