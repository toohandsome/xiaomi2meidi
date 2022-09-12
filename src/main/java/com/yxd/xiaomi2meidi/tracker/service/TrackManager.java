package com.yxd.xiaomi2meidi.tracker.service;

import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes11.dex */
public class TrackManager {
    private TrackFormat mTrackFormat;
    private List<String> trackLogs = new ArrayList();
    private List<String> tags = new ArrayList();

    public TrackManager(TrackFormat trackFormat) {
        this.mTrackFormat = trackFormat;
    }

    public void addTrackerMethod(String str) {
        this.mTrackFormat.addToTrancePath(str);
    }

    public void addTrackerEvent(RequestInfo requestInfo, boolean z) {
        this.trackLogs.add(this.mTrackFormat.makeTrackLog(requestInfo, buildTags(), z));
        this.tags.clear();
    }

    public void trackerEnd() {
        if (!this.trackLogs.isEmpty()) {
            //TODO
            System.err.println(" 有人调用这里 !!!!");
            //MopCountly.sharedInstance().traceAction("TOPIC_LOG_TRACE", buildTraceData(this.trackLogs));
        }
    }

    public void trackerCancel() {
        this.trackLogs.clear();
        this.tags.clear();
    }

    public void addTags(String str) {
        this.tags.add(str);
    }

    public TrackFormat getTrackFormat() {
        return this.mTrackFormat;
    }

    private String buildTags() {
        String str = "";
        for (String str2 : this.tags) {
            str = StringUtils.isEmpty(str) ? str + str2 : str + "$" + str2;
        }
        return str;
    }

    private String buildTraceData(List<String> list) {
        TrackerBean trackerBean = new TrackerBean();
        trackerBean.trace_logs = list;
        Gson gson = new Gson();
        return gson.toJson(trackerBean);
    }
}