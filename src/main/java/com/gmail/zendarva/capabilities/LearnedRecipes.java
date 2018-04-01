package com.gmail.zendarva.capabilities;

import com.gmail.zendarva.domain.Research;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 4/1/2018.
 */
public class LearnedRecipes implements ILearnedRecipes {

    List<String> researches = new LinkedList<>();

    @Override
    public List<String> learnedResearches() {
        return Collections.unmodifiableList(researches);
    }

    @Override
    public void learnResearch(Research research) {
        researches.add(research.itemToScan.toLowerCase());
    }

    public boolean knowsResearch(Research research){
        return researches.stream().anyMatch(f->f.toLowerCase().equals(research.itemToScan.toLowerCase()));
    }

    @Override
    public void set(ILearnedRecipes cap) {
        this.researches=cap.learnedResearches();
    }
}
