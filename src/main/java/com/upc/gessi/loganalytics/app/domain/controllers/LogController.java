package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.*;
import com.upc.gessi.loganalytics.app.client.APIClient;
import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

@Controller
public class LogController {

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @Autowired
    private SessionController sessionController;

    @Autowired
    private InternalMetricController internalMetricController;

    @Autowired
    private MetricController metricController;
    @Autowired
    private FactorController factorController;
    @Autowired
    private IndicatorController indicatorController;

    @Autowired
    private LogRepository logRepository;

    public List<String> getOriginalLogs(MultipartFile file) {
        List<String> list = new ArrayList<>();
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            list = Arrays.asList(content.split("\\r?\\n"));
        } catch (IOException e) {
            logger.error("Error processing the file");
        }
        return list;
    }

    public List<Log> parseLogs(List<String> originalLogs) {
        List<Log> list = new ArrayList<>();
        for (String originalLog : originalLogs) {
            if (originalLog.contains("logout") || originalLog.contains("No user is logged in yet")) continue;
            String[] splitRequest = originalLog.split(", ");
            long epoch = getTimestamp(splitRequest[0]);

            if (originalLog.contains("enters app") || originalLog.contains("exits app") || originalLog.contains("'s session has timed out")) {
                String team = splitRequest[1].split(" ")[0];
                String[] splitTeam = splitRequest[1].split(" \\(");

                String session = splitTeam[1];
                String sessionId = session.replaceAll("\\)", "");
                String sessionString = " \\(" + sessionId + "\\)";
                String message = originalLog.replaceAll(sessionString, "");

                if (team.equals("admin") || team.equals("professor-pes") || team.equals("professor-asw")) continue;
                Session s;
                if (originalLog.contains("enters app")) s = sessionController.createSession(sessionId, epoch, team);
                else s = sessionController.updateSession(sessionId, epoch);
                if (s == null) logger.error("Tried to parse log of nonexistent team " + team);
                else {
                    Log newLog = new Log(epoch, team, message, s);
                    list.add(newLog);
                }
                continue;
            }

            if (!originalLog.contains(" GET ")) continue;
            String pageAndView = getPageAndView(splitRequest[1]);
            String page = getPage(pageAndView);
            internalMetricController.createPageMetric(page);
            HashMap<String,String> params = getParams(splitRequest);
            String team = getTeam(splitRequest[splitRequest.length - 1]);
            String sessionId = getSession(splitRequest[splitRequest.length - 1]);
            Session s = sessionController.getSessionToStoreLog(sessionId);
            if (s == null) {
                logger.error("Tried to parse log of nonexistent team " + team);
                continue;
            }
            String sessionString = " \\(" + sessionId + "\\)";
            String message = originalLog.replaceAll(sessionString, "");
            if (team.equals("admin") || team.equals("professor-pes") || team.equals("professor-asw")) continue;
            if (pageAndView.contains("Metrics") || pageAndView.contains("QualityFactors") || pageAndView.contains("StrategicIndicators") || pageAndView.contains("QualityModel")) {
                String viewFormat = getViewFormat(pageAndView);
                if (!pageAndView.contains("QualityModel")) {
                    boolean historic;
                    historic = !pageAndView.contains("Current");
                    List<String> ids = getIds(pageAndView, params, team);

                    if (pageAndView.contains("Metrics")) {
                        List<Metric> metricIds = new ArrayList<>();
                        boolean store = true;
                        for (String id : ids) {
                            Metric newMetric = new Metric(id);
                            if (!metricController.checkExistence(newMetric)) {
                                store = false;
                                logger.error("Tried to parse log with accesses to nonexistent metric " + id);
                            }
                            else metricIds.add(newMetric);
                        }
                        if (store) {
                            MetricAccess newLog = new MetricAccess(epoch, team, message, page, s, historic, viewFormat, metricIds);
                            internalMetricController.createMetricViewMetric(viewFormat);
                            list.add(newLog);
                        }
                    }
                    else if (pageAndView.contains("QualityFactors")) {
                        List<Factor> factorIds = new ArrayList<>();
                        boolean store = true;
                        for (String id : ids) {
                            Factor newFactor = new Factor(id);
                            if (!factorController.checkExistence(newFactor)) {
                                logger.error("Tried to parse log with accesses to nonexistent factor " + id);
                                store = false;
                            }
                            else factorIds.add(newFactor);
                        }
                        if (store) {
                            FactorAccess newLog = new FactorAccess(epoch, team, message, page, s, historic, viewFormat, factorIds);
                            internalMetricController.createFactorViewMetric(viewFormat);
                            list.add(newLog);
                        }
                    }
                    else {
                        List<Indicator> indicatorIds = new ArrayList<>();
                        boolean store = true;
                        for (String id : ids) {
                            Indicator newIndicator = new Indicator(id);
                            if (!indicatorController.checkExistence(newIndicator)) {
                                logger.error("Tried to parse log with accesses to nonexistent indicator " + id);
                                store = false;
                            }
                            else indicatorIds.add(newIndicator);
                        }
                        if (store) {
                            IndicatorAccess newLog = new IndicatorAccess(epoch, team, message, page, s, historic, viewFormat, indicatorIds);
                            internalMetricController.createIndicatorViewMetric(viewFormat);
                            list.add(newLog);
                        }
                    }
                }
                else {
                    QModelAccess newLog = new QModelAccess(epoch, team, message, page, s, viewFormat);
                    internalMetricController.createQModelViewMetric(viewFormat);
                    list.add(newLog);
                }
            }
            else {
                Log newLog = new Log(epoch, team, message, page, s);
                list.add(newLog);
            }
        }
        return list;
    }

    public List<Log> getAllByPageAndTeam(String page, String team) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<Log> logIterable = logRepository.findByPageAndTeamAndTimeBetween(page, team, timestampYesterday, timestampToday);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        return logList;
    }

    private List<Integer> getQualityFactorsFromSI(String SIName, String team) {
        List<Integer> qualityFactorsIds = new ArrayList<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/strategicIndicators";
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("prj", team);
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response != null && response.body() != null) {
                String json = response.body().string();
                JsonArray jsonIndicators = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonIndicators.size(); ++i) {
                    JsonObject item = jsonIndicators.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    if (externalId.equals(SIName)) {
                        JsonArray qFactors = item.get("qualityFactors").getAsJsonArray();
                        for (int j = 0; j < qFactors.size(); ++j)
                            qualityFactorsIds.add(qFactors.get(j).getAsInt());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard server");
        }
        return qualityFactorsIds;
    }

    private List<String> getQFExternalId(List<Integer> qualityFactorsIds) {
        List<String> ids = new ArrayList<>();
        try {
            for (Integer qualityFactorsId : qualityFactorsIds) {
                APIClient apiClient = new APIClient();
                String url = "http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors";
                HashMap<String, String> queryParams = new HashMap<>();
                HashSet<String> pathSegments = new HashSet<>();
                pathSegments.add(qualityFactorsId.toString());
                Response response = apiClient.get(url, queryParams, pathSegments);
                if (response != null && response.body() != null) {
                    String json = response.body().string();
                    JsonObject jsonFactor = JsonParser.parseString(json).getAsJsonObject();
                    String externalId = jsonFactor.get("externalId").getAsString();
                    ids.add(externalId);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard server");
        }
        return ids;
    }

    private List<Integer> getMetricsFromQF(String QFName, String team) {
        List<Integer> metricsIds = new ArrayList<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors";
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("prj", team);
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response != null && response.body() != null) {
                String json = response.body().string();
                JsonArray jsonFactors = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonFactors.size(); ++i) {
                    JsonObject item = jsonFactors.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    if (externalId.equals(QFName)) {
                        JsonArray metrics = item.get("metrics").getAsJsonArray();
                        for (int j = 0; j < metrics.size(); ++j) {
                            metricsIds.add(metrics.get(j).getAsInt());
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard server");
        }
        return metricsIds;
    }

    private List<String> getMetricExternalId(List<Integer> metricsIds, String team) {
        List<String> ids = new ArrayList<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/metrics";
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("prj", team);
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response != null && response.body() != null) {
                String json = response.body().string();
                JsonArray jsonMetrics = JsonParser.parseString(json).getAsJsonArray();
                for (Integer metricId : metricsIds) {
                    for (int i = 0; i < jsonMetrics.size(); ++i) {
                        JsonObject item = jsonMetrics.get(i).getAsJsonObject();
                        int Id = item.get("id").getAsInt();
                        if (Id == metricId) {
                            String externalId = item.get("externalId").getAsString();
                            //externalId = metricController.removeUsername(item, externalId);
                            ids.add(externalId);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard server");
        }
        return ids;
    }

    private List<String> getAllIds(String page, String team) {
        List<String> ids = new ArrayList<>();
        APIClient apiClient = new APIClient();
        String url = null;
        if (page.contains("StrategicIndicators"))
            url = "http://gessi-dashboard.essi.upc.edu:8888/api/strategicIndicators";
        else if (page.contains("QualityFactors"))
            url = "http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors";
        else if (page.contains("Metrics"))
            url = "http://gessi-dashboard.essi.upc.edu:8888/api/metrics";
        if (url != null) {
            HashMap<String,String> queryParams = new HashMap<>();
            queryParams.put("prj", team);
            HashSet<String> pathSegments = new HashSet<>();
            try {
                Response response = apiClient.get(url, queryParams, pathSegments);
                if (response != null && response.body() != null) {
                    String json = response.body().string();
                    JsonArray jsonElems = JsonParser.parseString(json).getAsJsonArray();
                    for (int i = 0; i < jsonElems.size(); ++i) {
                        JsonObject item = jsonElems.get(i).getAsJsonObject();
                        String externalId = item.get("externalId").getAsString();
                        //externalId = metricController.removeUsername(item, externalId);
                        ids.add(externalId);
                    }
                }
            } catch (IOException e) {
                logger.error("Error in the Learning Dashboard server");
            }
        }
        return ids;
    }

    private List<String> getIds(String page, HashMap<String,String> params, String team) {
        List<String> ids = new ArrayList<>();
        if (!params.isEmpty()) {
            if (page.contains("Detailed")) {
                if (params.containsKey("id")) {
                    ids.add(params.get("id"));
                }
            }
            else {
                if (page.contains("QualityFactors")) {
                    if (params.containsKey("id")) {
                        String SIName = params.get("id");
                        List<Integer> qualityFactorsIds = getQualityFactorsFromSI(SIName, team);
                        if (!qualityFactorsIds.isEmpty())
                            ids = getQFExternalId(qualityFactorsIds);
                    }
                }
                else if (page.contains("Metrics")) {
                    if (params.containsKey("id")) {
                        String QFName = params.get("id");
                        List<Integer> metricsIds = getMetricsFromQF(QFName, team);
                        if (!metricsIds.isEmpty())
                            ids = getMetricExternalId(metricsIds, team);
                    }
                }
            }
        }
        else ids = getAllIds(page, team);
        return ids;
    }

    private String getViewFormat(String page) {
        String viewFormat;
        if (page.contains("Table")) viewFormat = "Table";
        else if (page.contains("Graph")) viewFormat = "Graph";
        else if (page.contains("Sunburst")) viewFormat = "Sunburst";
        else {
            if (page.contains("Gauge")) viewFormat = "ChartGauge";
            else if (page.contains("Slider")) viewFormat = "ChartSlider";
            else if (page.contains("Radar")) viewFormat = "ChartRadar";
            else if (page.contains("Stacked")) viewFormat = "ChartStacked";
            else if (page.contains("Polar")) viewFormat = "ChartPolar";
            else viewFormat = "Chart";
        }
        return viewFormat;
    }

    private String[] getKeyValue(String splitParams) {
        String [] keyValue = splitParams.split(":");
        String key = keyValue[0];
        String value = keyValue[1].replace("[", "");
        value = value.replace("]", "");
        return new String[]{key, value};
    }

    private HashMap<String,String> getParams(String[] splitRequest) {
        HashMap<String,String> params = new HashMap<>();
        boolean start = false;
        boolean notFinished = true;
        for (int i = 0; i < splitRequest.length && notFinished; ++i) {
            if (splitRequest[i].contains("parameters={") && splitRequest[i].contains("}")) {
                if (!splitRequest[i].contains("parameters={}")) {
                    String [] splitParams = splitRequest[i].split("[{}]");
                    String [] keyValue = getKeyValue(splitParams[1]);
                    params.put(keyValue[0], keyValue[1]);
                }
                notFinished = false;
            }
            else if (splitRequest[i].contains("parameters={")) {
                start = true;
                String [] splitParams = splitRequest[i].split("[{]");
                String [] keyValue = getKeyValue(splitParams[1]);
                params.put(keyValue[0], keyValue[1]);
            }
            else if (start) {
                if (splitRequest[i].contains("}")) {
                    notFinished = false;
                    start = false;
                    splitRequest[i] = splitRequest[i].replace("}", "");
                }
                String [] keyValue = getKeyValue(splitRequest[i]);
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }

    private long getTimestamp(String StringTimestamp) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(StringTimestamp);
            return date.getTime();
        } catch (ParseException e) {
            logger.error("Error parsing the date");
        }
        return 0;
    }

    private String getPageAndView(String StringPage) {
        String[] splitPage = StringPage.split("\"");
        return splitPage[1];
    }

    private String getPage(String pageAndView) {
        int slash = StringUtils.countOccurrencesOf(pageAndView, "/");
        if (slash == 2) {
            String[] split = pageAndView.split("/");
            return split[1];
        }
        return pageAndView.replaceAll("/", "");
    }

    private String getTeam(String StringTeam) {
        int startIndex = "Action performed by ".length();
        String team = StringTeam.split(" \\(")[0];
        return team.substring(startIndex);
    }

    private String getSession(String StringTeam) {
        String[] splitTeam = StringTeam.split(" \\(");
        if (splitTeam.length > 1) {
            String session = StringTeam.split(" \\(")[1];
            return session.replaceAll("\\)", "");
        }
        return null;
    }

    public List<Log> getAll(Integer page, Integer size, String dateBefore, String dateAfter, String team, String subject, String keyword) {
        if (page == null) {
            if (dateBefore != null) {
                if (dateAfter != null) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        formatter.setLenient(false);
                        Date dBefore = formatter.parse(dateBefore);
                        Date dAfter = formatter.parse(dateAfter);
                        long epochBefore = dBefore.getTime();
                        long epochAfter = dAfter.getTime() + 86399999;
                        if (epochBefore > epochAfter)
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "dateBefore is not previous to dateAfter");
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, team, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndTeamOrderByTimeDesc(epochBefore, epochAfter, team);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, subject, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndSubjectOrderByTimeDesc(epochBefore, epochAfter, subject);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, keyword);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenOrderByTimeDesc(epochBefore, epochAfter);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
                else {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        formatter.setLenient(false);
                        Date dBefore = formatter.parse(dateBefore);
                        long epoch = dBefore.getTime();
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(epoch, team);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(epoch, subject);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(epoch);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
            }
            else if (dateAfter != null) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    formatter.setLenient(false);
                    Date dAfter = formatter.parse(dateAfter);
                    long epoch = dAfter.getTime() + 86399999;

                    if (team != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamOrderByTimeDesc(epoch, team);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (subject != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectOrderByTimeDesc(epoch, subject);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (keyword != null) {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualOrderByTimeDesc(epoch);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                }
            }
            else if (team != null) {
                Iterable<Log> logIterable;
                List<Log> logList = new ArrayList<>();
                if (keyword != null) {
                    logIterable = logRepository.findByTeamAndMessageContainingOrderByTimeDesc(team, keyword);
                }
                else {
                    logIterable = logRepository.findByTeamOrderByTimeDesc(team);
                }
                logIterable.forEach(logList::add);
                return logList;
            }
            else if (subject != null) {
                Iterable<Log> logIterable;
                List<Log> logList = new ArrayList<>();
                if (keyword != null) {
                    logIterable = logRepository.findBySubjectAndMessageContainingOrderByTimeDesc(subject, keyword);
                }
                else  {
                    logIterable = logRepository.findBySubjectOrderByTimeDesc(subject);
                }
                logIterable.forEach(logList::add);
                return logList;
            }
            else if (keyword != null) {
                Iterable<Log> logIterable = logRepository.findByMessageContainingOrderByTimeDesc(keyword);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
            else {
                Iterable<Log> logIterable = logRepository.findAllByOrderByTimeDesc();
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
        }
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
            if (dateBefore != null) {
                if (dateAfter != null) {
                    try {
                        Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                        Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                        long epochBefore = dBefore.getTime();
                        long epochAfter = dAfter.getTime() + 86399999;
                        if (epochBefore > epochAfter)
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "dateBefore is not previous to dateAfter");
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, team, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndTeamOrderByTimeDesc(epochBefore, epochAfter, team, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, subject, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndSubjectOrderByTimeDesc(epochBefore, epochAfter, subject, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, keyword, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenOrderByTimeDesc(epochBefore, epochAfter, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
                else {
                    try {
                        Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                        long epoch = dBefore.getTime();
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(epoch, team, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(epoch, subject, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(epoch, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
            }
            else if (dateAfter != null) {
                try {
                    Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                    long epoch = dAfter.getTime();

                    if (team != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword, pageable);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamOrderByTimeDesc(epoch, team, pageable);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (subject != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword, pageable);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectOrderByTimeDesc(epoch, subject, pageable);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (keyword != null) {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword, pageable);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualOrderByTimeDesc(epoch, pageable);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                }
            }
            else if (team != null) {
                if (keyword != null) {
                    Iterable<Log> logIterable = logRepository.findByTeamAndMessageContainingOrderByTimeDesc(team, keyword, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else {
                    Iterable<Log> logIterable = logRepository.findByTeamOrderByTimeDesc(team, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
            }
            else if (subject != null) {
                if (keyword != null) {
                    Iterable<Log> logIterable = logRepository.findBySubjectAndMessageContainingOrderByTimeDesc(subject, keyword, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else  {
                    Iterable<Log> logIterable = logRepository.findBySubjectOrderByTimeDesc(subject, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
            }
            else if (keyword != null) {
                Iterable<Log> logIterable = logRepository.findByMessageContainingOrderByTimeDesc(keyword, pageable);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
            else {
                Iterable<Log> logIterable = logRepository.findAllByOrderByTimeDesc(pageable);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
        }
    }

    public void storeLogs(List<Log> parsedLogs) {
        if (!parsedLogs.isEmpty()) {
            Log logFile = parsedLogs.get(0);
            Log logDB = logRepository.findFirstByOrderByTimeDesc();
            if (logDB != null) {
                if (logFile.getTime() > logDB.getTime())
                    logRepository.saveAll(parsedLogs);
            }
            else logRepository.saveAll(parsedLogs);
        }
    }
}
