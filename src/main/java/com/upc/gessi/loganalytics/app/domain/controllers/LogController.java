package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.*;
import com.upc.gessi.loganalytics.app.domain.models.*;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LogController {

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @Autowired
    private MetricController metricController;

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
            if (originalLog.contains("login")) continue;
            else if (originalLog.contains("logout")) continue;

            String[] splitRequest = originalLog.split(", ");
            long epoch = getTimestamp(splitRequest[0]);
            if (originalLog.contains("enters app") || originalLog.contains("exits app")
                    || originalLog.contains("'s session has timed out")) {
                String team = splitRequest[1].split(" ")[0];
                if (team.equals("admin") || team.equals("professor-pes")
                    || team.equals("professor-asw")) continue;
                Log newLog = new Log(epoch, team, originalLog);
                list.add(newLog);
                continue;
            }

            if (!originalLog.contains(" GET ")) continue;
            String page = getPage(splitRequest[1]);
            HashMap<String,String> params = getParams(splitRequest);
            String team = getTeam(splitRequest[splitRequest.length - 1]);
            if (team.equals("admin") || team.equals("professor-pes")
                || team.equals("professor-asw"))
                continue;

            if (!page.contains("Configuration") && !page.contains("Prediction")
                && !page.contains("Simulation")) {

                if (page.contains("Metrics") || page.contains("QualityFactors")
                    || page.contains("StrategicIndicators")
                    || page.contains("QualityModel")) {

                    String viewFormat = getViewFormat(page);
                    if (!page.contains("QualityModel")) {
                        boolean historic;
                        historic = !page.contains("Current");
                        List<String> ids = getIds(page, params, team);

                        if (page.contains("Metrics")) {
                            List<Metric> metricIds = new ArrayList<>();
                            for (String id : ids) {
                                Metric newMetric = new Metric(id);
                                metricIds.add(newMetric);
                            }
                            MetricAccess newLog = new MetricAccess(epoch, team, originalLog, page, historic, viewFormat, metricIds);
                            list.add(newLog);
                        } else if (page.contains("QualityFactors")) {
                            List<Factor> factorIds = new ArrayList<>();
                            for (String id : ids) {
                                Factor newFactor = new Factor(id);
                                factorIds.add(newFactor);
                            }
                            FactorAccess newLog = new FactorAccess(epoch, team, originalLog, page, historic, viewFormat, factorIds);
                            list.add(newLog);
                        } else {
                            List<Indicator> indicatorIds = new ArrayList<>();
                            for (String id : ids) {
                                Indicator newIndicator = new Indicator(id);
                                indicatorIds.add(newIndicator);
                            }
                            IndicatorAccess newLog = new IndicatorAccess(epoch, team, originalLog, page, historic, viewFormat, indicatorIds);
                            list.add(newLog);
                        }
                    } else {
                        QModelAccess newLog = new QModelAccess(epoch, team, originalLog, page, viewFormat);
                        list.add(newLog);
                    }
                }
            }
            else {
                Log newLog = new Log(epoch, team, originalLog, page);
                list.add(newLog);
            }
        }
        return list;
    }

    private List<Integer> getQualityFactorsFromSI(String SIName, String team) {
        List<Integer> qualityFactorsIds = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/strategicIndicators")).newBuilder();
            urlBuilder.addQueryParameter("prj", team);
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                String json = response.body().string();
                JsonArray jsonIndicators = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonIndicators.size(); ++i) {
                    JsonObject item = jsonIndicators.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    if (externalId.equals(SIName)) {
                        JsonArray qFactors = item.get("qualityFactors").getAsJsonArray();
                        for (int j = 0; j < qFactors.size(); ++j) {
                            qualityFactorsIds.add(qFactors.get(j).getAsInt());
                        }
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
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors")).newBuilder();
                urlBuilder.addPathSegments(qualityFactorsId.toString());
                Request request = new Request.Builder()
                        .url(urlBuilder.build().toString())
                        .method("GET", null)
                        .build();
                Response response = client.newCall(request).execute();
                if (response.body() != null) {
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
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors")).newBuilder();
            urlBuilder.addQueryParameter("prj", team);
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
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
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/metrics")).newBuilder();
            urlBuilder.addQueryParameter("prj", team);
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
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
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HttpUrl.Builder urlBuilder = null;
        if (page.contains("StrategicIndicators"))
            urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/strategicIndicators")).newBuilder();
        else if (page.contains("QualityFactors"))
            urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors")).newBuilder();
        else if (page.contains("Metrics"))
            urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/metrics")).newBuilder();

        if (urlBuilder != null) {
            urlBuilder.addQueryParameter("prj", team);
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.body() != null) {
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

    private String getPage(String StringPage) {
        String[] splitPage = StringPage.split("\"");
        return splitPage[1];
    }

    private String getTeam(String StringTeam) {
        int startIndex = "Action performed by ".length();
        return StringTeam.substring(startIndex);
    }
}
