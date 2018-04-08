package com.gmail.zendarva.capabilities;

import com.gmail.zendarva.domain.Research;

import java.util.List;

/**
 * Created by James on 3/31/2018.
 */
public interface ILearnedRecipes {

    List<String> learnedResearches();
    void learnResearch(Research research);
    boolean knowsResearch(Research research);

    void set(ILearnedRecipes cap);
    void clear();
}
