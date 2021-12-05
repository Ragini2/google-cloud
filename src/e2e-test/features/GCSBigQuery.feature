Feature: Verification of GCS to BQ successful data transfer

  @GCS
  Scenario: To verify data is getting transferred from GCS to BigQuery with Mandatory fields
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsCsvBucket" and format "gcsCSVFileFormat"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save the pipeline
    Then Preview and run the pipeline
    Then Verify the preview of pipeline is "success"
    Then Click on PreviewData for GCS
    Then Verify Preview output schema matches the outputSchema captured in properties
    Then Close the Preview
    Then Deploy the pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Open Logs
    Then Validate successMessage is displayed
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Delete the table "gcsBqTableName"

  @GCS
  Scenario: To verify successful data transfer from GSC to BigQuery with outputField
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsOutputFieldTestBucket" and format "gcsCSVFileFormat" with Path Field "gcsPathField"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save and Deploy Pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Verify output field "gcsPathField" in target BigQuery table "gcsBqTableName" contains path of the GcsBucket "gcsOutputFieldTestBucket"
    Then Delete the table "gcsBqTableName"

  @GCS
  Scenario: To verify Successful GCS to BigQuery data transfer with Datatype override
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsOutputFieldTestBucket" and format "gcsCSVFileFormat" with Override field "gcsOverrideField" and data type "gcsOverrideDataType"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save and Deploy Pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Verify datatype of field "gcsOverrideField" is overridden to data type "gcsOverrideDataType" in target BigQuery table "gcsBqTableName"
    Then Delete the table "gcsBqTableName"

  @GCS
  Scenario: To verify Successful GCS to BigQuery data transfer with Delimiter
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsDelimitedBucket" and format "gcsTextFileFormat" with delimiter field "gcsDelimiter"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save and Deploy Pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Delete the table "gcsBqTableName"

  @GCS
  Scenario: To verify Successful GCS to BigQuery data transfer with blob file by entering MaxMinSplitSize
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsBlobBucket" and format "gcsBlobFileFormat" with minSplitSize "gcsMinSplitSize" and maxSplitSize "gcsMaxSplitSize"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save and Deploy Pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Delete the table "gcsBqTableName"

  @GCS
  Scenario: To verify Successful GCS to BigQuery data transfer using Regex path filter
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsCsvBucket" and format "gcsCSVFileFormat" with regex path filter "gcsRegexPathFilter"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save and Deploy Pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Delete the table "gcsBqTableName"

  @GCS
  Scenario Outline: line: To Verify data is getting transferred from GCS to BigQuery with different formats
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "<Bucket>" and format "<FileFormat>"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save and Deploy Pipeline
    Then Run the Pipeline in Runtime
    Then Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Then Open Logs
    Then Validate successMessage is displayed
    Then Get Count of no of records transferred to BigQuery in "gcsBqTableName"
    Then Delete the table "gcsBqTableName"
    Examples:
      | Bucket              | FileFormat            |
      | gcsCsvBucket        | gcsCSVFileFormat      |
      | gcsTsvBucket        | gcsTSVFileFormat      |
      | gcsTextBucket       | gcsTextFileFormat     |
      | gcsHeavyDataBucket  | gcsCSVFileFormat      |
