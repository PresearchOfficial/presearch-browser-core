/**
 * Copyright (c) 2021 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.chromium.chrome.browser.firstrun;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.BraveRewardsHelper;
import org.chromium.chrome.browser.app.BraveActivity;
import org.chromium.chrome.browser.customtabs.CustomTabActivity;
import org.chromium.chrome.browser.night_mode.GlobalNightModeStateProviderHolder;
import org.chromium.chrome.browser.onboarding.OnboardingPrefManager;
import org.chromium.chrome.browser.preferences.BravePrefServiceBridge;
import org.chromium.chrome.browser.preferences.ChromePreferenceKeys;
import org.chromium.chrome.browser.preferences.SharedPreferencesManager;
import org.chromium.chrome.browser.util.PackageUtils;

import java.lang.Math;

public class P3aOnboardingActivity extends FirstRunActivityBase {
    // mInitializeViewsDone and mInvokePostWorkAtInitializeViews are accessed
    // from the same thread, so no need to use extra locks
    private boolean mInitializeViewsDone;
    private boolean mInvokePostWorkAtInitializeViews;
    private boolean mIsP3aEnabled;
    private FirstRunFlowSequencer mFirstRunFlowSequencer;
    private Button mBtnContinue;

    private void initializeViews() {
        assert !mInitializeViewsDone;
        setContentView(R.layout.activity_p3a_onboarding);

        boolean isFirstInstall = PackageUtils.isFirstInstall(this);

        TextView p3aOnboardingTitle = findViewById(R.id.p3a_onboarding_title);
        p3aOnboardingTitle.setText(getResources().getString(R.string.p3a_onboarding_title_text_1));
        mIsP3aEnabled = true;
        ImageView p3aOnboardingImg = findViewById(R.id.p3a_onboarding_img);
        p3aOnboardingImg.setImageResource(R.drawable.ic_presearch_logo_borderless);
        mBtnContinue = findViewById(R.id.btn_continue);
        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnboardingPrefManager.getInstance().setP3aOnboardingShown(true);
                accept();
            }
        });

        mInitializeViewsDone = true;
        if (mInvokePostWorkAtInitializeViews) {
            finishNativeInitializationPostWork();
        }
    }

    private void finishNativeInitializationPostWork() {
        assert mInitializeViewsDone;

        try {
            mIsP3aEnabled = BravePrefServiceBridge.getInstance().getP3AEnabled();
        } catch (Exception e) {
            Log.e("P3aOnboarding", e.getMessage());
        }
        mBtnContinue.setEnabled(true);
    }

    @Override
    public void finishNativeInitialization() {
        ThreadUtils.assertOnUiThread();
        super.finishNativeInitialization();

        if (mInitializeViewsDone) {
            finishNativeInitializationPostWork();
        } else {
            mInvokePostWorkAtInitializeViews = true;
        }
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void triggerLayoutInflation() {
        mFirstRunFlowSequencer = new FirstRunFlowSequencer(this) {
            @Override
            public void onFlowIsKnown(Bundle freProperties) {
                initializeViews();
            }
        };
        mFirstRunFlowSequencer.start();
        onInitialLayoutInflationComplete();
    }

    public void completeOnboardingExperience() {
        FirstRunStatus.setFirstRunFlowComplete(true);
        exitOnboarding();
    }

    private void exitOnboarding() {
        finish();
        sendFirstRunCompletePendingIntent();
    }

    private void accept() {
        // Do not use existing function because it contains consent to Google crash report upload
        SharedPreferencesManager.getInstance().writeBoolean(
                ChromePreferenceKeys.FIRST_RUN_CACHED_TOS_ACCEPTED, true);
        FirstRunUtils.setEulaAccepted();
        completeOnboardingExperience();
    }
}
