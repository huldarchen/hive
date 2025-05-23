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

package org.apache.hadoop.hive.ql.ddl.table.column.drop;

import java.util.Map;

import org.apache.hadoop.hive.common.TableName;
import org.apache.hadoop.hive.ql.ddl.table.AbstractAlterTableDesc;
import org.apache.hadoop.hive.ql.ddl.table.AlterTableType;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.Explain;
import org.apache.hadoop.hive.ql.plan.Explain.Level;

/**
 * DDL task description for ALTER TABLE ... DROP COLUMN ... command.
 */
@Explain(displayName = "Drop Column", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
public class AlterTableDropColumnDesc extends AbstractAlterTableDesc {
  private static final long serialVersionUID = 1L;
  private final String colName;
  private final boolean ifExists;

  public AlterTableDropColumnDesc(TableName tableName, Map<String, String> partitionSpec, boolean isCascade,
      String colName, boolean ifExists) throws SemanticException {
    super(AlterTableType.DROP_COLUMN, tableName, partitionSpec, null, isCascade, false, null);
    this.colName = colName;
    this.ifExists = ifExists;
  }

  @Explain(displayName = "column name", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getColName() {
    return colName;
  }

  public boolean isIfExists() {
    return ifExists;
  }

  @Override
  public boolean mayNeedWriteId() {
    return true;
  }
}
