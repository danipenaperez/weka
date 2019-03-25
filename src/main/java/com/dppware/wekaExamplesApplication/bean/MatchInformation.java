package com.dppware.wekaExamplesApplication.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchInformation {

    private Boolean ishomeMatch;
    private Boolean isWontLastMatch;
    private String result;

    public Instance asInstance(Instances dataSet) {
        Instance ins = new DenseInstance(2);
        ins.setDataset(dataSet);
        ins.setValue(0, this.ishomeMatch.toString());
        ins.setValue(1, this.isWontLastMatch.toString());
        return ins;
    }

    public MatchInformation(boolean ishomeMatch, boolean isWontLastMatch) {
        this.ishomeMatch = ishomeMatch;
        this.isWontLastMatch = isWontLastMatch;
    }

}
