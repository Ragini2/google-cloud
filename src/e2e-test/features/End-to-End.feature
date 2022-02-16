@postgreSQL_Source
Feature: End to End data transfer from PSQL source to different Sink

   @postgreSQL
    Scenario:Verify all the records transfer from PSQL to Bigquery supporting different data types
    Given Open DataFusion Project to configure pipeline
    When Source is PostgreSQL
    When Sink is BigQuery
    Then Open PostgreSQL Properties
    Then Enter PostgreSQL property reference name
    Then Select Driver name from dropdown
    Then Enter PostgreSQL property host name "psqlHost"
    Then Enter PostgreSQL property user name
    Then Enter PostgreSQL property password
    Then Enter PostgreSQL property database "pSQLDbName"
    Then Enter PostgreSQL property importQuery "pSQLDBImportQueryForAll"
    Then Validate output schema with expectedSchema "pSQLSourceSchema"
    Then Validate PostgreSQL properties
    Then Close the PostgreSQL properties
    Then Open BigQuery Target Properties
    Then Enter the BigQuery Target Properties for table "psqlBigQuery"
    Then Validate Bigquery properties
    Then Close the BigQuery properties
    Then Connect Source as "PostgreSQL" and sink as "BigQuery" to establish connection
    Then Add pipeline name
    Then Preview and run the pipeline
    Then Verify the preview of pipeline is "success"
    Then Click on PreviewData for BigQuery
    Then Verify Preview output schema matches the outputSchema captured in properties
    Then Close the Preview and deploy the pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Open the Logs and capture raw logs
    Then Get Count of no of records transferred to BigQuery in "psqlBigQuery"
    Then Validate records out from cloudSQLPostgreSQL is equal to records transferred in BigQuery "psqlBigQuery" output records

 @postgreSQL
 Scenario Outline: Verify records get transferred on combining different tables using joins
  Given Open DataFusion Project to configure pipeline
  When Source is PostgreSQL
  When Sink is BigQuery
  Then Open PostgreSQL Properties
  Then Enter PostgreSQL property reference name
  Then Select Driver name from dropdown
  Then Enter PostgreSQL property host name "psqlHost"
  Then Enter PostgreSQL property user name
  Then Enter PostgreSQL property password
  Then Enter PostgreSQL property database "pSQLDbName"
  Then Enter PostgreSQL property importQuery using different joins "<psqlDbImportQueryJoins>"
  Then Validate output schema with expectedSchema "pSQLSourceSchema"
  Then Capture output schema
  Then Validate PostgreSQL properties
  Then Close the PostgreSQL properties
  Then Open BigQuery Target Properties
  Then Enter the BigQuery Target Properties for table "psqlBigQuery"
  Then Validate Bigquery properties
  Then Close the BigQuery properties
  Then Connect Source as "PostgreSQL" and sink as "BigQuery" to establish connection
  Then Add pipeline name
  Then Preview and run the pipeline
  Then Verify the preview of pipeline is "success"
  Then Click on PreviewData for BigQuery
  Then Verify Preview output schema matches the outputSchema captured in properties
  Then Close the Preview and deploy the pipeline
  Then Run the Pipeline in Runtime
  Then Wait till pipeline is in running state
  Then Verify the pipeline status is "Succeeded"
  Then Open the Logs and capture raw logs
  Then Get Count of no of records transferred to BigQuery in "psqlBigQuery"
  Then Validate records out from cloudSQLPostgreSQL is equal to records transferred in BigQuery "psqlBigQuery" output records
  Examples:
   |  psqlDbImportQueryJoins     |
   |  psqlDbImportQueryInnerJoin |
   |  psqlDbImportQueryLeftJoin  |
   |  psqlDbImportQueryRightJoin |
   |  psqlDbImportQueryOuterJoin |

 @postgreSQL
 Scenario:Verify only distinct records are transferred
  Given Open DataFusion Project to configure pipeline
  When Source is PostgreSQL
  When Sink is BigQuery
  Then Open PostgreSQL Properties
  Then Enter the cloudSQLPostgreSQL properties for database "cloudPSQLDbName" using query "cloudPSQLDBImportQueryDistinct" for distinct values "cloudPSQLSplitColumnDistinctValue"
  Then Capture output schema
  Then Validate PostgreSQL properties
  Then Close the PostgreSQL properties
  Then Open BigQuery Target Properties
  Then Enter the BigQuery Target Properties for table "cloudPsqlBigQuery"
  Then Validate Bigquery properties
  Then Close the BigQuery properties
  Then Connect Source as "CloudSQL-PostgreSQL" and sink as "BigQuery" to establish connection
  Then Add pipeline name
  Then Preview and run the pipeline
  Then Verify the preview of pipeline is "success"
  Then Click on PreviewData for BigQuery
  Then Verify Preview output schema matches the outputSchema captured in properties
  Then Close the Preview and deploy the pipeline
  Then Run the Pipeline in Runtime
  Then Wait till pipeline is in running state
  Then Verify the pipeline status is "Succeeded"
  Then Open the Logs and capture raw logs
  Then Get Count of no of records transferred to BigQuery in "cloudPsqlBigQuery"
  Then Validate records out from cloudSQLPostgreSQL is equal to records transferred in BigQuery "cloudPsqlBigQuery" output records

 @postgreSQL
 Scenario:Verify records with minimum values are transferred from cloudPSQL to BigQuery
  Given Open DataFusion Project to configure pipeline
  When Source is PostgreSQL
  When Sink is BigQuery
  Then Open PostgreSQL Properties
  Then Enter the cloudSQLPostgreSQL properties for database "cloudPSQLDbName" using query "cloudPSQLDBImportQueryForMin" for min values "cloudPSQLSplitColumnMinValue"
  Then Capture output schema
  Then Validate PostgreSQL properties
  Then Close the PostgreSQL properties
  Then Open BigQuery Target Properties
  Then Enter the BigQuery Target Properties for table "cloudPsqlBigQuery"
  Then Validate Bigquery properties
  Then Close the BigQuery properties
  Then Connect Source as "CloudSQL-PostgreSQL" and sink as "BigQuery" to establish connection
  Then Add pipeline name
  Then Preview and run the pipeline
  Then Verify the preview of pipeline is "success"
  Then Click on PreviewData for BigQuery
  Then Verify Preview output schema matches the outputSchema captured in properties
  Then Close the Preview and deploy the pipeline
  Then Run the Pipeline in Runtime
  Then Wait till pipeline is in running state
  Then Verify the pipeline status is "Succeeded"
  Then Open the Logs and capture raw logs
  Then Get Count of no of records transferred to BigQuery in "cloudPsqlBigQuery"
  Then Validate records out from cloudSQLPostgreSQL is equal to records transferred in BigQuery "cloudPsqlBigQuery" output records


 @postgreSQL
 Scenario Outline: Verify all the records transfer from cloudSQLPostgreSQL to Bigquery for different where clause
  Given Open DataFusion Project to configure pipeline
  When Source is PostgreSQL
  When Sink is BigQuery
  Then Open PostgreSQL Properties
  Then Enter the cloudSQLPostgreSQL properties for database "cloudPSQLDbName" using query "<cloudPostgresSQLDatabaseImportQuery>" for max and min "<cloudPostgresSQLSplitColumnValues>"
  Then Capture output schema
  Then Validate PostgreSQL properties
  Then Close the cloudSQLPostgreSQL properties
  Then Open BigQuery Target Properties
  Then Enter the BigQuery Target Properties for table "cloudPsqlBigQuery"
  Then Validate Bigquery properties
  Then Close the BigQuery properties
  Then Connect Source as "CloudSQL-PostgreSQL" and sink as "BigQuery" to establish connection
  Then Add pipeline name
  Then Preview and run the pipeline
  Then Verify the preview of pipeline is "success"
  Then Click on PreviewData for BigQuery
  Then Verify Preview output schema matches the outputSchema captured in properties
  Then Close the Preview and deploy the pipeline
  Then Run the Pipeline in Runtime
  Then Wait till pipeline is in running state
  Then Verify the pipeline status is "Succeeded"
  Then Open the Logs and capture raw logs
  Then Get Count of no of records transferred to BigQuery in "cloudPsqlBigQuery"
  Then Validate records out from cloudSQLPostgreSQL is equal to records transferred in BigQuery "cloudPsqlBigQuery" output records
  Examples:
   |  cloudPostgresSQLDatabaseImportQuery       |  cloudPostgresSQLSplitColumnValues         |
   |  cloudPSQLDBImportQueryForBetween          |   cloudPSQLSplitColumnBetweenValue         |
   |  cloudPSQLDBImportQueryForIn               |   cloudPSQLSplitColumnInValue              |
   |  cloudPSQLDBImportQueryNotIn               |   cloudPSQLSplitColumnNotInValue           |
   |  cloudPSQLDBImportQueryOrderBy             |   cloudPSQLSplitColumnOrderByValue         |
