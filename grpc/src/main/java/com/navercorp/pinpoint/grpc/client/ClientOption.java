/*
 * Copyright 2019 NAVER Corp.
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

package com.navercorp.pinpoint.grpc.client;

import com.navercorp.pinpoint.common.util.Assert;
import com.navercorp.pinpoint.grpc.ChannelTypeEnum;

import java.util.concurrent.TimeUnit;

/**
 * @author jaehong.kim
 */
public class ClientOption {
    public static final long DEFAULT_KEEPALIVE_TIME = TimeUnit.SECONDS.toMillis(30); // 30 seconds
    public static final long DEFAULT_KEEPALIVE_TIMEOUT = TimeUnit.SECONDS.toMillis(60); // 60 seconds
    public static final long IDLE_TIMEOUT_MILLIS_DISABLE = TimeUnit.DAYS.toMillis(30); // Disable
    public static final boolean KEEPALIVE_WITHOUT_CALLS_DISABLE = Boolean.FALSE;
    // <a href="https://tools.ietf.org/html/rfc7540#section-6.5.2">
    public static final int DEFAULT_MAX_HEADER_LIST_SIZE = 8 * 1024;
    public static final int DEFAULT_MAX_MESSAGE_SIZE = 4 * 1024 * 1024;
    // <a href="https://tools.ietf.org/html/rfc7540#section-6.9.2">initial connection flow-control window size</a>
    public static final int DEFAULT_FLOW_CONTROL_WINDOW = 1 * 1024 * 1024; // 1MiB
    public static final int INITIAL_FLOW_CONTROL_WINDOW = 65535;
    public static final int DEFAULT_CONNECT_TIMEOUT = 3000;
    public static final int DEFAULT_WRITE_BUFFER_HIGH_WATER_MARK = 32 * 1024 * 1024;
    public static final int DEFAULT_WRITE_BUFFER_LOW_WATER_MARK = 16 * 1024 * 1024;
    public static final String DEFAULT_CHANNEL_TYPE = ChannelTypeEnum.AUTO.name();

    public static final int DEFAULT_MAX_TRACE_EVENT = 0;
    public static final int DEFAULT_LIMIT_COUNT = 100;
    public static final int DEFAULT_LIMIT_TIME = 60 * 1000;

    private final long keepAliveTime;
    private final long keepAliveTimeout;
    // KeepAliveManager.keepAliveDuringTransportIdle
    private final boolean keepAliveWithoutCalls = KEEPALIVE_WITHOUT_CALLS_DISABLE;
    private final long idleTimeoutMillis = IDLE_TIMEOUT_MILLIS_DISABLE;
    private final int maxHeaderListSize;
    private final int maxInboundMessageSize;
    private final int flowControlWindow;

    // ChannelOption
    private final int connectTimeout;
    private final int writeBufferHighWaterMark;
    private final int writeBufferLowWaterMark;
    private final ChannelTypeEnum channelTypeEnum;
    private final int maxTraceEvent;
    private final int limitCount;
    private final long limitTime;

    private ClientOption(long keepAliveTime, long keepAliveTimeout, int maxHeaderListSize, int maxInboundMessageSize,
                         int flowControlWindow, int connectTimeout, int writeBufferHighWaterMark, int writeBufferLowWaterMark,
                         ChannelTypeEnum channelTypeEnum, int maxTraceEvent, int limitCount, long limitTime) {
        this.keepAliveTime = keepAliveTime;
        this.keepAliveTimeout = keepAliveTimeout;
        this.flowControlWindow = flowControlWindow;
        this.maxHeaderListSize = maxHeaderListSize;
        this.maxInboundMessageSize = maxInboundMessageSize;
        this.connectTimeout = connectTimeout;
        this.writeBufferHighWaterMark = writeBufferHighWaterMark;
        this.writeBufferLowWaterMark = writeBufferLowWaterMark;

        this.channelTypeEnum = Assert.requireNonNull(channelTypeEnum, "channelTypeEnum");
        this.maxTraceEvent = maxTraceEvent;

        this.limitCount = limitCount;
        this.limitTime = limitTime;
    }

    public int getFlowControlWindow() {
        return flowControlWindow;
    }

    public int getMaxHeaderListSize() {
        return maxHeaderListSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public long getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public boolean isKeepAliveWithoutCalls() {
        return keepAliveWithoutCalls;
    }

    public long getIdleTimeoutMillis() {
        return idleTimeoutMillis;
    }

    public int getMaxInboundMessageSize() {
        return maxInboundMessageSize;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getWriteBufferHighWaterMark() {
        return writeBufferHighWaterMark;
    }

    public int getWriteBufferLowWaterMark() {
        return writeBufferLowWaterMark;
    }

    public ChannelTypeEnum getChannelTypeEnum() {
        return channelTypeEnum;
    }
    public int getMaxTraceEvent() {
        return maxTraceEvent;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public long getLimitTime() {
        return limitTime;
    }

    @Override
    public String toString() {
        return "ClientOption{" +
                "keepAliveTime=" + keepAliveTime +
                ", keepAliveTimeout=" + keepAliveTimeout +
                ", keepAliveWithoutCalls=" + keepAliveWithoutCalls +
                ", idleTimeoutMillis=" + idleTimeoutMillis +
                ", maxHeaderListSize=" + maxHeaderListSize +
                ", maxInboundMessageSize=" + maxInboundMessageSize +
                ", flowControlWindow=" + flowControlWindow +
                ", connectTimeout=" + connectTimeout +
                ", writeBufferHighWaterMark=" + writeBufferHighWaterMark +
                ", writeBufferLowWaterMark=" + writeBufferLowWaterMark +
                ", channelTypeEnum=" + channelTypeEnum +
                ", maxTraceEvent=" + maxTraceEvent +
                ", limitCount=" + limitCount +
                ", limitTime=" + limitTime +
                '}';
    }

    public static class Builder {
        private int flowControlWindow = DEFAULT_FLOW_CONTROL_WINDOW;
        private int maxHeaderListSize = DEFAULT_MAX_HEADER_LIST_SIZE;
        private long keepAliveTime = DEFAULT_KEEPALIVE_TIME;
        private long keepAliveTimeout = DEFAULT_KEEPALIVE_TIMEOUT;

        private int maxInboundMessageSize = DEFAULT_MAX_MESSAGE_SIZE;

        private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        private int writeBufferHighWaterMark = DEFAULT_WRITE_BUFFER_HIGH_WATER_MARK;
        private int writeBufferLowWaterMark = DEFAULT_WRITE_BUFFER_LOW_WATER_MARK;
        private ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.valueOf(DEFAULT_CHANNEL_TYPE);
        private int maxTraceEvent;

        private int limitCount;
        private long limitTime;

        public ClientOption build() {
            final ClientOption clientOption = new ClientOption(keepAliveTime, keepAliveTimeout, maxHeaderListSize, maxInboundMessageSize,
                    flowControlWindow, connectTimeout,
                    writeBufferHighWaterMark, writeBufferLowWaterMark, channelTypeEnum,
                    maxTraceEvent, limitCount, limitTime);
            return clientOption;
        }

        public void setFlowControlWindow(int flowControlWindow) {
            if (!(flowControlWindow >= INITIAL_FLOW_CONTROL_WINDOW)) {
                throw new IllegalArgumentException("flowControlWindow expected >= " + INITIAL_FLOW_CONTROL_WINDOW);
            }
            this.flowControlWindow = flowControlWindow;
        }

        public void setMaxHeaderListSize(int maxHeaderListSize) {
            Assert.isTrue(maxHeaderListSize > 0, "maxHeaderListSize must be positive");
            this.maxHeaderListSize = maxHeaderListSize;
        }

        public void setKeepAliveTime(long keepAliveTime) {
            Assert.isTrue(keepAliveTime > 0, "keepAliveTime must be positive");
            this.keepAliveTime = keepAliveTime;
        }

        public void setKeepAliveTimeout(long keepAliveTimeout) {
            Assert.isTrue(keepAliveTimeout > 0, "keepAliveTimeout must be positive");
            this.keepAliveTimeout = keepAliveTimeout;
        }

        public void setMaxInboundMessageSize(int maxInboundMessageSize) {
            Assert.isTrue(maxInboundMessageSize > 0, "maxInboundMessageSize must be positive");
            this.maxInboundMessageSize = maxInboundMessageSize;
        }

        public void setConnectTimeout(int connectTimeout) {
            Assert.isTrue(connectTimeout > 0, "connectTimeout must be positive");
            this.connectTimeout = connectTimeout;
        }

        public void setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
            Assert.isTrue(writeBufferHighWaterMark > 0, "writeBufferHighWaterMark must be positive");
            this.writeBufferHighWaterMark = writeBufferHighWaterMark;
        }

        public void setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
            Assert.isTrue(writeBufferLowWaterMark > 0, "writeBufferLowWaterMark must be positive");
            this.writeBufferLowWaterMark = writeBufferLowWaterMark;
        }

        public void setChannelTypeEnum(String channelTypeEnum) {
            Assert.requireNonNull(channelTypeEnum, "channelTypeEnum");
            this.channelTypeEnum = ChannelTypeEnum.valueOf(channelTypeEnum);
        }

        public void setMaxTraceEvent(int maxTraceEvent) {
            Assert.isTrue(maxTraceEvent >= 0, "maxTraceEvent must be positive");
            this.maxTraceEvent = maxTraceEvent;
        }

        public void setLimitCount(int limitCount) {
            Assert.isTrue(limitCount >= 0, "limitCount must be positive");
            this.limitCount = limitCount;
        }

        public void setLimitTime(long limitTime) {
            Assert.isTrue(limitTime >= 0, "limitTime must be positive");
            this.limitTime = limitTime;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "flowControlWindow=" + flowControlWindow +
                    ", maxHeaderListSize=" + maxHeaderListSize +
                    ", keepAliveTime=" + keepAliveTime +
                    ", keepAliveTimeout=" + keepAliveTimeout +
                    ", maxInboundMessageSize=" + maxInboundMessageSize +
                    ", connectTimeout=" + connectTimeout +
                    ", writeBufferHighWaterMark=" + writeBufferHighWaterMark +
                    ", writeBufferLowWaterMark=" + writeBufferLowWaterMark +
                    ", channelTypeEnum=" + channelTypeEnum +
                    ", maxTraceEvent=" + maxTraceEvent +
                    ", limitCount=" + limitCount +
                    ", limitTime=" + limitTime +
                    '}';
        }
    }
}