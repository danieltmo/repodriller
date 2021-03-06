/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.util.ArrayList;
import java.util.List;
import org.repodriller.RepositoryMining;
import org.repodriller.filter.range.Commits;
import repository.AnalysisRepository;
import tool.PersistenceTool;

/**
 *
 * @author Daniel
 */
public class GitCollector {

    private AnalysisRepository repository;
    private RepositoryMining mining;
    private ArrayList<PersistenceTool> tools = new ArrayList<>();

    public GitCollector(AnalysisRepository repository) {
        this.repository = repository;
        mining = new RepositoryMining().in(repository);
    }

    public ArrayList<PersistenceTool> getTools() {
        return tools;
    }

    public void addTools(List<PersistenceTool> tools) {
        this.tools.addAll(tools);
    }

    public void addTool(PersistenceTool tool) {
        this.tools.add(tool);
    }

    public void collect() {

        for (PersistenceTool t : tools) {
            mining.process(t.getTool(), t.getPersistenceMechanism());
        }

        mining.mine();
    }

    public GitCollector all() {
        mining.through(Commits.all());
        return this;
    }

    public void range(String initialCommit, String finalCommit) {
        mining.through(Commits.range(initialCommit, finalCommit));
    }

    public void collect(String commit) {
        mining.through(Commits.single(commit));
    }

    public void collect(List<String> commitList) {
        mining.through(Commits.list(commitList));
    }

}
