/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.contacts.preference;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

import com.android.contacts.R;
import com.android.contacts.preference.ContactsPreferences;
import android.view.View;
import android.widget.ImageView;

/**
 * Custom preference: view mode (normal or small view contacts).
 */
public final class ViewModePreference extends ListPreference {

    private ContactsPreferences mPreferences;
    private Context mContext;

    public ViewModePreference(Context context) {
        super(context);
        prepare();
    }

    public ViewModePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepare();
    }

    private void prepare() {
        mContext = getContext();
        mPreferences = new ContactsPreferences(mContext);
        setEntries(new String[]{
                mContext.getString(R.string.display_options_view_mode_standard),
                mContext.getString(R.string.display_options_view_mode_compact),
        });
        setEntryValues(new String[]{
                String.valueOf(ContactsPreferences.VIEW_MODE_STANDARD),
                String.valueOf(ContactsPreferences.VIEW_MODE_COMPACT),
        });
        setValue(String.valueOf(mPreferences.getViewMode()));
    }

    @Override
    protected boolean shouldPersist() {
        return false; // This preference takes care of its own storage
    }

    @Override
    public CharSequence getSummary() {
        switch (mPreferences.getViewMode()) {
            case ContactsPreferences.VIEW_MODE_STANDARD:
                return mContext.getString(R.string.display_options_view_mode_standard);
            case ContactsPreferences.VIEW_MODE_COMPACT:
                return mContext.getString(R.string.display_options_view_mode_compact);
        }
        return null;
    }

    @Override
    protected boolean persistString(String value) {
        int newValue = Integer.parseInt(value);
        if (newValue != mPreferences.getViewMode()) {
            mPreferences.setViewMode(newValue);
            notifyChanged();
        }
        return true;
    }

    @Override
    // UX recommendation is not to show cancel button on such lists.
    protected void onPrepareDialogBuilder(Builder builder) {
        super.onPrepareDialogBuilder(builder);
        builder.setNegativeButton(null, null);
    }
}
