/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.notifications.retention;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import androidx.core.app.NotificationCompat;

import org.chromium.base.ContextUtils;
import org.chromium.base.IntentUtils;
import org.chromium.base.Log;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.brave_stats.BraveStatsUtil;
import org.chromium.chrome.browser.local_database.DatabaseHelper;
import org.chromium.chrome.browser.notifications.BraveOnboardingNotification;
import org.chromium.chrome.browser.notifications.channels.BraveChannelDefinitions;
import org.chromium.chrome.browser.ntp.BraveNewTabPageLayout;
import org.chromium.chrome.browser.onboarding.OnboardingPrefManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RetentionNotificationUtil {
    public static String NOTIFICATION_TYPE = "notification_type";
    private static final String BRAVE_BROWSER = "Presearch Browser";

    public static final String HOUR_3 = "hour_3";
    public static final String HOUR_24 = "hour_24";
    public static final String DAY_6 = "day_6";
    public static final String EVERY_SUNDAY = "every_sunday";
    public static final String DAY_10 = "day_10";
    public static final String DAY_30 = "day_30";
    public static final String DAY_35 = "day_35";
    public static final String BRAVE_STATS_ADS_TRACKERS = "brave_stats_ads_trackers";
    public static final String BRAVE_STATS_DATA = "brave_stats_data";
    public static final String BRAVE_STATS_TIME = "brave_stats_time";
    public static final String DEFAULT_BROWSER_1 = "default_browser_1";
    public static final String DEFAULT_BROWSER_2 = "default_browser_2";
    public static final String DEFAULT_BROWSER_3 = "default_browser_3";

    public static final String DORMANT_USERS_DAY_14 = "dormant_users_days_14";
    public static final String DORMANT_USERS_DAY_25 = "dormant_users_days_25";
    public static final String DORMANT_USERS_DAY_40 = "dormant_users_days_40";

    private static Map<String, RetentionNotification> mNotificationMap = new HashMap<String, RetentionNotification>() {
        {
            put(DEFAULT_BROWSER_1,
                    new RetentionNotification(17, 3 * 24 * 60,
                            BraveChannelDefinitions.ChannelId.BRAVE_BROWSER, BRAVE_BROWSER));
            put(DEFAULT_BROWSER_2, new RetentionNotification(18, 6 * 24 * 60, BraveChannelDefinitions.ChannelId.BRAVE_BROWSER, BRAVE_BROWSER));
            put(DEFAULT_BROWSER_3,
                    new RetentionNotification(19, 30 * 24 * 60,
                            BraveChannelDefinitions.ChannelId.BRAVE_BROWSER, BRAVE_BROWSER));
        }
    };

    public static RetentionNotification getNotificationObject(String notificationType) {
        return mNotificationMap.get(notificationType);
    }

    public static Notification getNotification(
            Context context, String notificationType, String notificationText) {
        RetentionNotification retentionNotification = getNotificationObject(notificationType);
        Log.e("NTP", notificationText);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, retentionNotification.getChannelId());
        builder.setContentTitle(retentionNotification.getNotificationTitle());
        builder.setContentText(notificationText);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText));
        builder.setSmallIcon(R.drawable.ic_chrome);
        builder.setAutoCancel(true);

        Intent launchIntent =
                context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        launchIntent.putExtra(NOTIFICATION_TYPE, notificationType);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                retentionNotification.getNotificationId(), launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        | IntentUtils.getPendingIntentMutabilityFlag(true));

        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }

    public static String getNotificationText(Context context, String notificationType) {
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getInstance();
        switch (notificationType) {
        case DEFAULT_BROWSER_1:
        case DEFAULT_BROWSER_2:
        case DEFAULT_BROWSER_3:
            return context.getResources().getString(R.string.set_brave_as_your);
        }
        return "";
    }

    private static PendingIntent getRetentionNotificationActionIntent(Context context, String notificationType) {
        Intent intent = new Intent(context, RetentionNotificationPublisher.class);
        intent.setAction(RetentionNotificationPublisher.RETENTION_NOTIFICATION_ACTION);
        intent.putExtra(NOTIFICATION_TYPE, notificationType);
        return PendingIntent.getBroadcast(context,
                getNotificationObject(notificationType).getNotificationId(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        | IntentUtils.getPendingIntentMutabilityFlag(true));
    }

    public static void scheduleNotification(Context context, String notificationType) {
        RetentionNotification retentionNotification = getNotificationObject(notificationType);
        Intent notificationIntent = new Intent(context, RetentionNotificationPublisher.class);
        notificationIntent.putExtra(NOTIFICATION_TYPE, notificationType);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, retentionNotification.getNotificationId(),
                        notificationIntent, 0 | IntentUtils.getPendingIntentMutabilityFlag(true));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, retentionNotification.getNotificationTime());

        Date date = calendar.getTime();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
    }

    public static void scheduleNotificationWithTime(
            Context context, String notificationType, long timeInMilliseconds) {
        RetentionNotification retentionNotification = getNotificationObject(notificationType);
        Intent notificationIntent = new Intent(context, RetentionNotificationPublisher.class);
        notificationIntent.putExtra(NOTIFICATION_TYPE, notificationType);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, retentionNotification.getNotificationId(),
                        notificationIntent, 0 | IntentUtils.getPendingIntentMutabilityFlag(true));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
    }

    public static void scheduleNotificationForEverySunday(Context context, String notificationType) {
        RetentionNotification retentionNotification = getNotificationObject(notificationType);
        Calendar currentDate = Calendar.getInstance();
        while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            currentDate.add(Calendar.DATE, 1);
        }
        currentDate.set(Calendar.HOUR_OF_DAY, 9);
        currentDate.set(Calendar.MINUTE, 45);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        Intent notificationIntent = new Intent(context, RetentionNotificationPublisher.class);
        notificationIntent.putExtra(NOTIFICATION_TYPE, notificationType);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, retentionNotification.getNotificationId(),
                        notificationIntent, 0 | IntentUtils.getPendingIntentMutabilityFlag(true));
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert am != null;
        am.setRepeating(AlarmManager.RTC_WAKEUP, currentDate.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }

    public static void scheduleDormantUsersNotifications(Context context) {
    }
}
