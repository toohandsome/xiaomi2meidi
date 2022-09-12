package com.yxd.xiaomi2meidi.tracker;

import com.yxd.xiaomi2meidi.tracker.service.RequestInfo;
import com.yxd.xiaomi2meidi.tracker.service.TrackManager;

import java.util.HashMap;

/* loaded from: classes11.dex */
public class LogTracker {
    private static LogTracker instance = new LogTracker();
    private HashMap<String, TrackManager> trackMap = new HashMap<>();

    private LogTracker() {
    }

    public static LogTracker getInstance() {
        return instance;
    }

    private TrackManager getTrackManager(String str) {
        return this.trackMap.get(str);
    }

    private TrackManager checkTrackManager(String str) {
        TrackManager trackManager = getTrackManager(str);
        if (trackManager == null) {
            TrackManager create = new TrackManagerBuilder( str).create();
            addTrackManager(str, create);
            return create;
        }
        return trackManager;
    }

    public void addTrackManager(String str, TrackManager trackManager) {
        deleteTrackManager(str);
        this.trackMap.put(str, trackManager);
    }

    public TrackManager deleteTrackManager(String str) {
        return this.trackMap.remove(str);
    }

    public String getTrackerPath(String str) {
        return checkTrackManager(str).getTrackFormat().getTracePath();
    }

    public String getTrackerId(String str) {
        TrackManager checkTrackManager = checkTrackManager(str);
        String transId = checkTrackManager.getTrackFormat().getTransId();
        String makeTranceId = checkTrackManager.getTrackFormat().makeTranceId();
        return transId + "$" + makeTranceId;
    }

    public boolean addTrackerMethod(String str, String str2) {
        checkTrackManager(str).addTrackerMethod(str2);
        return true;
    }

    public boolean addTag(String str, String str2, String str3) {
        TrackManager checkTrackManager = checkTrackManager(str);
        checkTrackManager.addTags(str2 + ":"+ str3);
        return true;
    }

    public boolean addTrackerEvent(String str, RequestInfo requestInfo, boolean z) {
        checkTrackManager(str).addTrackerEvent(requestInfo, z);
        return true;
    }

    public boolean trackerEnd(String str) {
        TrackManager deleteTrackManager = deleteTrackManager(str);
        if (deleteTrackManager == null) {
            return false;
        }
        deleteTrackManager.trackerEnd();
        return true;
    }

    public boolean trackerCancel(String str) {
        TrackManager trackManager = getTrackManager(str);
        if (trackManager != null) {
            trackManager.trackerCancel();
            return true;
        }
        return false;
    }
}