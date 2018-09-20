package com.stool.gram.pojo;

public class GramResult {
    private Float score;

    private String label;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }



    @Override
    public String toString() {
        return "GramResult{" +
                "score=" + score +
                ", label=" + label +
                '}';
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
