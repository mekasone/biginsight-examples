/**
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

import groovy.sql.Sql

env = System.getenv()

// truststore is created with the cluster's certificate by the build.gradle script
url = "jdbc:hive2://${env.hostname}:10000/default;ssl=true;sslTrustStore=./truststore.jks;trustStorePassword=mypassword;"

db = [ url:url, user:env.username, password:env.password, driver:'org.apache.hive.jdbc.HiveDriver']

sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

// test connectivity

tmpTableName = "${env.username}_temp_${new Date().getTime()}"

sql.execute """
     create table ${tmpTableName} ( id int, name string )
 """.toString()

sql.execute """
     drop table ${tmpTableName}
""".toString()

println "\n>> Connectivity test was successful."
