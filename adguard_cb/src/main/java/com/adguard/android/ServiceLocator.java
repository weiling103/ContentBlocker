/**
 This file is part of Adguard Content Blocker (https://github.com/AdguardTeam/ContentBlocker).
 Copyright © 2016 Performix LLC. All rights reserved.

 Adguard Content Blocker is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by the
 Free Software Foundation, either version 3 of the License, or (at your option)
 any later version.

 Adguard Content Blocker is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 Adguard Content Blocker.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.adguard.android;

import android.content.Context;
import com.adguard.android.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

/**
 * Service locator class.
 */
public class ServiceLocator {
    private final Context context;
    private static final Logger LOG = LoggerFactory.getLogger(ServiceLocator.class);
    private static WeakHashMap<Context, ServiceLocator> locators = new WeakHashMap<>();
    private FilterService filterService;
    private PreferencesService preferencesService;
    private JobService jobService;

    /**
     * Creates an instance of the ServiceLocator
     *
     * @param context Context
     */
    private ServiceLocator(Context context) {
        LOG.info("Initializing ServiceLocator for {}", context);
        this.context = context;
    }

    /**
     * Gets service locator instance
     *
     * @param context Context
     * @return ServiceLocator instance
     */
    public synchronized static ServiceLocator getInstance(Context context) {
        ServiceLocator instance = locators.get(context.getApplicationContext());

        if (instance == null) {
            instance = new ServiceLocator(context);
            locators.put(context, instance);
        }

        return instance;
    }

    /**
     * @return Filter service singleton
     */
    public FilterService getFilterService() {
        if (filterService == null) {
            filterService = new FilterServiceImpl(context);
        }

        return filterService;
    }

    /**
     * @return Preferences service singleton
     */
    public PreferencesService getPreferencesService() {
        if (preferencesService == null) {
            preferencesService = new PreferencesServiceImpl(context);
        }

        return preferencesService;
    }

    /**
     * @return Job service singleton
     */
    public JobService getJobService() {
        if (jobService == null) {
            jobService = new JobServiceImpl();
        }

        return jobService;
    }
}
