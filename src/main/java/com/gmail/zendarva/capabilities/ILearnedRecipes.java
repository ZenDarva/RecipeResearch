package com.gmail.zendarva.capabilities;

import com.gmail.zendarva.domain.Research;

import java.util.List;

/**
 * Created by James on 3/31/2018.
 */
public interface ILearnedRecipes {

    public List<String> learnedResearches();
    public void learnResearch(Research research);
    public boolean knowsResearch(Research research);

    public void set(ILearnedRecipes cap);

    public void clear();
}
