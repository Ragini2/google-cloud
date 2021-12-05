Feature: Validate GCS plugin output schema for different formats
  @GCS
  Scenario Outline:Verify GCS Source properties validation errors for mandatory fields
    Given Open Datafusion Project to configure pipeline
    When Source is GCS bucket
    Then Open GCS Properties
    Then Enter the GCS Properties with GCS bucket "<Bucket>" and format "<FileFormat>"
    Then Capture and validate output schema
    Examples:
      | Bucket              | FileFormat            |
      | gcsCsvBucket        | gcsCSVFileFormat      |
      | gcsTsvBucket        | gcsTSVFileFormat      |
      | gcsAvroBucket       | gcsAvroFileFormat     |
      | gcsBlobBucket       | gcsBlobFileFormat     |
      | gcsParquetBucket    | gcsParquetFileFormat  |
      | gcsDelimitedBucket  | gcsDelimitedFileFormat|
      | gcsTextBucket       | gcsTextFileFormat     |
