/*
 * Copyright 2017 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.bootstrap.plugin.monitor;

import com.navercorp.pinpoint.common.trace.ServiceType;

import java.lang.ref.WeakReference;

/**
 * @author Taejin Koo
 */
public class DataSourceMonitorWrapper implements PluginMonitorWrapper, DataSourceMonitor {

    private final int id;
    private final WeakReference<DataSourceMonitor> monitorReference;

    private volatile ServiceType serviceType;
    private volatile String name;
    private volatile String url;

    public DataSourceMonitorWrapper(int id, DataSourceMonitor dataSourceMonitor) {
        if (dataSourceMonitor == null) {
            throw new NullPointerException("dataSourceMonitor may not be null");
        }

        this.id = id;
        this.monitorReference = new WeakReference<DataSourceMonitor>(dataSourceMonitor);
    }

    private DataSourceMonitor getInstance() {
        return monitorReference.get();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public ServiceType getServiceType() {
        if (this.serviceType != null) {
            return this.serviceType;
        }

        DataSourceMonitor dataSourceMonitor = getInstance();
        if (dataSourceMonitor != null) {
            ServiceType serviceType = dataSourceMonitor.getServiceType();
            if (serviceType != null) {
                this.serviceType = serviceType;
            }
            return serviceType;
        }
        return ServiceType.UNKNOWN;
    }

    @Override
    public String getName() {
        if (this.name != null) {
            return this.name;
        }

        DataSourceMonitor dataSourceMonitor = getInstance();
        if (dataSourceMonitor != null) {
            String name = dataSourceMonitor.getName();
            if (name != null) {
                this.name = name;
            }
            return name;
        }
        return null;
    }

    @Override
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }

        DataSourceMonitor dataSourceMonitor = getInstance();
        if (dataSourceMonitor != null) {
            String url = dataSourceMonitor.getUrl();
            if (url != null) {
                this.url = url;
            }
            return url;
        }
        return null;
    }

    @Override
    public int getActiveConnectionSize() {
        DataSourceMonitor dataSourceMonitor = getInstance();
        if (dataSourceMonitor != null) {
            return dataSourceMonitor.getActiveConnectionSize();
        }
        return -1;
    }

    @Override
    public int getMaxConnectionSize() {
        DataSourceMonitor dataSourceMonitor = getInstance();
        if (dataSourceMonitor != null) {
            return dataSourceMonitor.getMaxConnectionSize();
        }
        return -1;
    }

    @Override
    public boolean isDisabled() {
        DataSourceMonitor dataSourceMonitor = getInstance();
        if (dataSourceMonitor == null) {
            return true;
        }

        return dataSourceMonitor.isDisabled();
    }

    @Override
    public boolean equalsWithUnwrap(Object object) {
        if (object == null) {
            return false;
        }

        DataSourceMonitor instance = getInstance();
        if (instance == null) {
            return false;
        }

        return instance == object;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSourceMonitorWrapper{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }

}
