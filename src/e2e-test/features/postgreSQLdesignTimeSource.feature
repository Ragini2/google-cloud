Feature: postgreSQL Source Design Time scenarios

  @postgreSQL
  Scenario:Verify no error is reflected when all the field are passed in the source postgreSQL
    Given Open DataFusion Project to configure pipeline
    When Source is PostgreSQL
    Then Open PostgreSQL Properties
    Then Enter the PostgreSQL properties for database "pSQLDbName" using query "pSQLDBImportQueryForAll"
#    Then Capture output schema
#    Then Validate PostgreSQL properties

  @postgreSQL
  Scenario Outline:Verify PostgreSQL Source properties errors for mandatory fields
    Given Open DataFusion Project to configure pipeline
    When Source is PostgreSQL
    Then Open PostgreSQL Properties
    Then Enter the PostgreSQL Source Properties with blank property "<property>"
    Then Validate mandatory property error for "<property>"
    Examples:
      | property         |
      | referenceName    |
      | database         |
      | Host             |
      | importQuery      |
      | jdbcPluginName   |
      | Port             |
      | Username         |
      | Password         |


  @postgreSQL
  Scenario Outline:Verify PostgreSQL Source properties errors for incorrect values
    Given Open DataFusion Project to configure pipeline
    When Source is PostgreSQL
    Then Open PostgreSQL Properties
    Then Enter the PostgreSQL Source Properties with incorrect values in property fields "<property>"
    Then Validate error message for incorrect values in property fields"<property>"
    Examples:
    | property            |
    | Host                |
    | Port                |
    | Database            |
    | Import Query        |
    | Bounding Query      |
    | Split-By Field Name |






