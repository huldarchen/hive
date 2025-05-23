/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.common;

import org.apache.hadoop.hive.common.io.DiskRangeList;
import org.apache.hive.common.util.SuppressFBWarnings;


/**
 * Disk range information class containing disk ranges and total length.
 */
public class DiskRangeInfo {

  private DiskRangeList head, tail = null;
  private long totalLength;

  public DiskRangeInfo(int indexBaseOffset) {
    // Some data is missing from the stream for PPD uncompressed read (because index offset is
    // relative to the entire stream and we only read part of stream if RGs are filtered; unlike
    // with compressed data where PPD only filters CBs, so we always get full CB, and index offset
    // is relative to CB). To take care of the case when UncompressedStream goes seeking around by
    // its incorrect (relative to partial stream) index offset, we will increase the length by our
    // offset-relative-to-the-stream, and also account for it in buffers (see createDiskRangeInfo).
    // So, index offset now works; as long as noone seeks into this data before the RG (why would
    // they), everything works. This is hacky... Stream shouldn't depend on having all the data.
    this.totalLength = indexBaseOffset;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "intended")
  public void addDiskRange(DiskRangeList diskRange) {
    if (tail == null) {
      head = tail = diskRange;
    } else {
      tail = tail.insertAfter(diskRange);
    }
    totalLength += diskRange.getLength();
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "intended")
  public DiskRangeList getDiskRanges() {
    return head;
  }

  public long getTotalLength() {
    return totalLength;
  }
}

