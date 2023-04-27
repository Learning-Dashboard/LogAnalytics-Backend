package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upc.gessi.loganalytics.app.client.APIClient;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamPrimaryKey;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamRepository;
import jakarta.annotation.PostConstruct;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    SubjectController subjectController;

    @Value("${semester.name}")
    private String semester;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @PostConstruct
    public void storeAllTeams() {
        HashSet<Team> teamSet = getCurrentLDTeams();
        for (Team t : teamSet) {
            TeamPrimaryKey teamPrimaryKey = new TeamPrimaryKey(t.getId(), t.getSemester());
            Optional<Team> teamOptional = teamRepository.findById(teamPrimaryKey);
            if (teamOptional.isEmpty()) teamRepository.save(t);
        }
    }

    private HashSet<Team> getCurrentLDTeams() {
        HashSet<String> subjectSet = new HashSet<>();
        HashMap<Integer, String> teamIdsMap = new HashMap<>();
        HashSet<Team> teamSet = new HashSet<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/iterations";
            HashMap<String, String> queryParams = new HashMap<>();
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response != null && response.body() != null) {
                String json = response.body().string();
                JsonArray jsonIterations = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonIterations.size(); ++i) {
                    JsonObject item = jsonIterations.get(i).getAsJsonObject();
                    String subjectName = item.get("label").getAsString();
                    subjectSet.add(subjectName);
                    JsonArray jsonProjectIds = item.get("project_ids").getAsJsonArray();
                    for (int j = 0; j < jsonProjectIds.size(); ++j) {
                        teamIdsMap.put(jsonProjectIds.get(j).getAsInt(), subjectName);
                    }
                }
                subjectController.storeSubjects(subjectSet);
                apiClient = new APIClient();
                url = "http://gessi-dashboard.essi.upc.edu:8888/api/projects";
                response = apiClient.get(url, queryParams, pathSegments);
                if (response != null && response.body() != null) {
                    json = response.body().string();
                    JsonArray jsonProjects = JsonParser.parseString(json).getAsJsonArray();
                    for (int i = 0; i < jsonProjects.size(); ++i) {
                        JsonObject item = jsonProjects.get(i).getAsJsonObject();
                        Integer id = item.get("id").getAsInt();
                        if (teamIdsMap.containsKey(id)) {
                            String name = item.get("externalId").getAsString();
                            if (teamIdsMap.get(id) != null) {
                                Subject s = new Subject(teamIdsMap.get(id));
                                teamSet.add(new Team(name, semester, s));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return teamSet;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Team getTeam(String id, String semester) {
        TeamPrimaryKey pk = new TeamPrimaryKey(id, semester);
        Optional<Team> team = teamRepository.findById(pk);
        return team.orElse(null);
    }

    public List<Team> getStoredTeams() {
        Iterable<Team> teamIterable = teamRepository.findAll();
        List<Team> teamList = new ArrayList<>();
        teamIterable.forEach(teamList::add);
        return teamList;
    }
}
