package com.yxd.xiaomi2meidi.tracker.login;

import com.yxd.xiaomi2meidi.tracker.LogTracker;
import com.yxd.xiaomi2meidi.tracker.TrackManagerBuilder;
import com.yxd.xiaomi2meidi.tracker.service.RequestInfo;

public final class LoginLogTrackHelper {
    public static final LoginLogTrackHelper INSTANCE = new LoginLogTrackHelper();
    private static String currentTrace;

    private LoginLogTrackHelper() {
    }

    public final void newTransaction(  String traceName) {
        currentTrace = traceName;
        LogTracker logTracker = LogTracker.getInstance();
        String str = currentTrace;
        logTracker.addTrackManager(str, new TrackManagerBuilder( str).create());
    }

    public final void addTag(String key, String str) {
        if (currentTrace == null) {
            return;
        }
        LogTracker.getInstance().addTag(currentTrace, key, str);
    }

    public final void addTrackerMethod(String method) {
        if (currentTrace == null) {
            return;
        }
        LogTracker.getInstance().addTrackerMethod(currentTrace, method);
    }

    public final void addTrackerEvent(RequestInfo info, boolean z) {
        if (currentTrace == null) {
            return;
        }
        LogTracker.getInstance().addTrackerEvent(currentTrace, info, z);
    }

    public final void trackerEnd() {
        if (currentTrace == null) {
            return;
        }
        LogTracker.getInstance().trackerEnd(currentTrace);
    }

    public final void trackerCancel() {
        if (currentTrace == null) {
            return;
        }
        LogTracker.getInstance().trackerCancel(currentTrace);
    }

    public final String getTrackerId() {
        String trackerId;
        return (currentTrace == null || (trackerId = LogTracker.getInstance().getTrackerId(currentTrace)) == null) ? "" : trackerId;
    }

    public final String getTrackerPath() {
        String trackerPath;
        return (currentTrace == null || (trackerPath = LogTracker.getInstance().getTrackerPath(currentTrace)) == null) ? "" : trackerPath;
    }

    public final void cleanUp() {
        if (currentTrace == null) {
            return;
        }
        LogTracker.getInstance().deleteTrackManager(currentTrace);
    }
}