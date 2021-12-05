Feature: Verify GSC plugin error scenarios

  @GCS
  Scenario Outline:Verify GCS Source properties validation errors for mandatory fields
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    Then Open GCS Properties
    Then Enter the GCS Properties with blank property "<property>"
    Then Validate mandatory property error for "<property>"
    Examples:
      | property        |
      | referenceName   |
      | path            |
      | format          |

  @TC-Wrong_OutputFieldpath_test @GCS
  Scenario: To verify Error message for incorrect output path field value
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsOutputFieldTestBucket" and format "gcsCSVFileFormat" with Path Field "gcsInvalidPathField"
    Then Verify Output Path field Error Message

  @TC-Wrong-Regex-Path @GCS
  Scenario: To verify Pipeline preview gets failed for incorrect Regex-Path
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    When Target is BigQuery
    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "gcsCsvBucket" and format "gcsCSVFileFormat" with regex path filter "gcsIncorrectRegexPath"
    Then Capture and validate output schema
    Then Validate GCS properties
    Then Close the GCS Properties
    Then Open BigQuery Properties
    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
    Then Validate BigQuery properties
    Then Close the BigQuery Properties
    Then Save the pipeline
    Then Preview and run the pipeline
    Then Verify the preview of pipeline is "failed"

#  @TC-plugin-857 @GCS @Bug
#  Scenario: Verify automatic datatype detection of Timestamp in GCS
#    Given Open Datafusion Project to configure pipeline
#    When Source is GCS bucket
#    Then Open GCS Properties
#    Then Enter the GCS Properties with GCS bucket "gcsTimestampBucket" and format "gcsCSVFileFormat"
#    Then Capture and validate output schema
#    Then verify the datatype
#
#  @TC-plugin-855 @GCS @Bug
#  Scenario: Verify Pipeline preview completes successfully for UTF-32File encoding
#    Given Open Datafusion Project to configure pipeline
#    When Source is GCS bucket
#    When Target is BigQuery
#    Then Connect Source as "GCS" and sink as "BigQuery" to establish connection
#    Then Open GCS Properties
#    Then Enter the GCS Properties with GCS bucket "gcsCsvBucket" and format "gcsCSVFileFormat" with file encoding "gcsFileEncoding32"
#    Then Validate GCS properties
#    Then Close the GCS Properties
#    Then Open BigQuery Properties
#    Then Enter the BigQuery Sink properties for table "gcsBqTableName"
#    Then Validate BigQuery properties
#    Then Close the BigQuery Properties
#    Then Save the pipeline
#    Then Preview and run the pipeline
#    Then Verify the preview of pipeline is "success"
