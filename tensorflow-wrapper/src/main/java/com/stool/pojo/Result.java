package com.stool.pojo;

public class Result {
    private String label;
    private Float score;

    public Result() {
    }

    public Result(String label, Float score) {
        this.label = label;
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "label='" + label + '\'' +
                ", score=" + score +
                '}';
    }
}
