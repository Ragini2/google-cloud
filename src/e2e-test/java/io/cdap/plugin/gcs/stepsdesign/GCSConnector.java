/*
 * Copyright © 2021 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.plugin.gcs.stepsdesign;

import io.cdap.e2e.pages.actions.CdfBigQueryPropertiesActions;
import io.cdap.e2e.pages.actions.CdfGcsActions;
import io.cdap.e2e.pages.actions.CdfLogActions;
import io.cdap.e2e.pages.actions.CdfPipelineRunAction;
import io.cdap.e2e.pages.actions.CdfStudioActions;
import io.cdap.e2e.pages.locators.CdfGCSLocators;
import io.cdap.e2e.pages.locators.CdfStudioLocators;
import io.cdap.e2e.utils.CdfHelper;
import io.cdap.e2e.utils.GcpClient;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.utils.CdapUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepsdesign.BeforeActions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.cdap.plugin.utils.GCConstants.ERROR_MSG_COLOR;
import static io.cdap.plugin.utils.GCConstants.ERROR_MSG_GCS_INVALID_PATH;
import static io.cdap.plugin.utils.GCConstants.ERROR_MSG_VALIDATION;

/**
 * GCS Plugin related step design.
 */
public class GCSConnector implements CdfHelper {
  List<String> propertiesOutputSchema = new ArrayList<String>();
  GcpClient gcpClient = new GcpClient();

  @Given("Open Datafusion Project to configure pipeline")
  public void openDatafusionProjectToConfigurePipeline() throws IOException, InterruptedException {
    openCdf();
  }

  @When("Source is GCS bucket")
  public void sourceIsGCSBucket() throws InterruptedException {
    CdfStudioActions.selectGCS();
  }

  @When("Target is BigQuery")
  public void targetIsBigQuery() {
    CdfStudioActions.sinkBigQuery();
  }

  @Then("Open GCS Properties")
  public void openGCSProperties() {
    CdfStudioActions.clickProperties("GCS");
  }

  @Then("Connect Source as {string} and sink as {string} to establish connection")
  public void connectSourceAndSinkToEstablishConnection(String source, String sink) throws InterruptedException {
    CdfStudioActions.connectSourceAndSink(source, sink);
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string}")
  public void enterTheGCSPropertiesWithGCSBucket(String bucket, String format) throws IOException,
    InterruptedException {
    CdfGcsActions.enterProjectId();
    CdfGcsActions.enterReferenceName();
    CdfGcsActions.getGcsBucket(CdapUtils.pluginProp(bucket));
    CdfGcsActions.selectFormat(CdapUtils.pluginProp(format));
    CdfGcsActions.enterSampleSize(CdapUtils.pluginProp("gcsSampleSize"));
    CdfGcsActions.skipHeader();
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string} with Path Field {string}")
  public void enterTheGCSPropertiesWithGCSBucketAndFormatWithPathField(String bucket, String format, String pathField)
    throws IOException, InterruptedException {
    enterTheGCSPropertiesWithGCSBucket(bucket, format);
    CdfGcsActions.enterPathField(CdapUtils.pluginProp(pathField));
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string} " +
    "with Override field {string} and data type {string}")
  public void enterTheGCSPropertiesWithGCSBucketAndFormatWithOverrideField
    (String bucket, String format, String overrideField, String dataType) throws IOException, InterruptedException {
    enterTheGCSPropertiesWithGCSBucket(bucket, format);
    CdfGcsActions.enterOverride(CdapUtils.pluginProp(overrideField));
    CdfGcsActions.clickOverrideDataType(CdapUtils.pluginProp(dataType));
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string} with delimiter field {string}")
  public void enterTheGCSPropertiesWithGCSBucketAndFormatWithDelimiterField(
    String bucket, String format, String delimiter) throws IOException, InterruptedException {
    enterTheGCSPropertiesWithGCSBucket(bucket, format);
    CdfGcsActions.enterDelimiterField(CdapUtils.pluginProp(delimiter));
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string} " +
    "with minSplitSize {string} and maxSplitSize {string}")
  public void enterTheGCSPropertiesWithGCSBucketAndFormatWithMinSplitSizeAndMaxSplitSize(
    String bucket, String format, String minSplitSize, String maxSplitSize) throws IOException, InterruptedException {
    enterTheGCSPropertiesWithGCSBucket(bucket, format);
    CdfGcsActions.enterMaxSplitSize(CdapUtils.pluginProp(minSplitSize));
    CdfGcsActions.enterMinSplitSize(CdapUtils.pluginProp(maxSplitSize));
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string} with regex path filter {string}")
  public void enterTheGCSPropertiesWithGCSBucketAndFormatWithRegexPathFilter(
    String bucket, String format, String regexPathFilter) throws IOException, InterruptedException {
    enterTheGCSPropertiesWithGCSBucket(bucket, format);
    CdfGcsActions.enterRegexPath(CdapUtils.pluginProp(regexPathFilter));
  }

  @Then("Enter the GCS Properties with GCS bucket {string} and format {string} with file encoding {string}")
  public void enterTheGCSPropertiesWithGCSBucketAndFormatWithFileEncoding(
    String bucket, String format, String fileEncoding) throws IOException, InterruptedException {
    enterTheGCSPropertiesWithGCSBucket(bucket, format);
    CdfGcsActions.selectFileEncoding(fileEncoding);
  }

  @Then("Capture and validate output schema")
  public void captureOutputSchema() {
    CdfBigQueryPropertiesActions.getSchema();
    SeleniumHelper.waitElementIsVisible(CdfGCSLocators.getSchemaLoadComplete, 10L);
    SeleniumHelper.waitElementIsVisible(SeleniumDriver.getDriver().findElement(
      By.xpath("//div[@data-cy='schema-fields-list']//*[@placeholder='Field name']")), 2L);
    List<WebElement> propertiesOutputSchemaElements = SeleniumDriver.getDriver().findElements(
      By.xpath("//div[@data-cy='schema-fields-list']//*[@placeholder='Field name']"));
    for (WebElement element : propertiesOutputSchemaElements) {
      propertiesOutputSchema.add(element.getAttribute("value"));
    }
    Assert.assertTrue(propertiesOutputSchema.size() >= 1);
  }

  @Then("Validate GCS properties")
  public void validateBigqueryProperties() {
    CdfGcsActions.clickValidateButton();
    SeleniumHelper.waitElementIsVisible(CdfStudioLocators.pluginValidationSuccessMsg, 10L);
    String expectedErrorMessage = CdapUtils.errorProp(ERROR_MSG_VALIDATION);
    String actualErrorMessage = CdfStudioLocators.pluginValidationSuccessMsg.getText();
    Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Then("Close the GCS Properties")
  public void closeTheGCSProperties() {
    CdfGcsActions.closeButton();
  }

  @Then("Open BigQuery Properties")
  public void openBigQueryProperties() {
    CdfStudioActions.clickProperties("BigQuery");
  }

  @Then("Enter the BigQuery Sink properties for table {string}")
  public void enterTheBigQueryPropertiesForTable(String tableName) throws InterruptedException, IOException {
    CdfBigQueryPropertiesActions.enterProjectId(CdapUtils.pluginProp("projectId"));
    CdfBigQueryPropertiesActions.enterDatasetProjectId(CdapUtils.pluginProp("projectId"));
    CdfBigQueryPropertiesActions.enterBigQueryReferenceName("BQ_Ref_" + UUID.randomUUID().toString());
    CdfBigQueryPropertiesActions.enterBigQueryDataset(CdapUtils.pluginProp("dataset"));
    CdfBigQueryPropertiesActions.enterBigQueryTable(CdapUtils.pluginProp(tableName));
    CdfBigQueryPropertiesActions.clickUpdateTable();
    CdfBigQueryPropertiesActions.clickTruncatableSwitch();
  }

  @Then("Validate BigQuery properties")
  public void validateBigQueryProperties() {
    CdfGcsActions.clickValidateButton();
    String expectedErrorMessage = CdapUtils.errorProp(ERROR_MSG_VALIDATION);
    String actualErrorMessage = CdfStudioLocators.pluginValidationSuccessMsg.getText();
    Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Then("Close the BigQuery Properties")
  public void closeTheBigQueryProperties() {
    CdfGcsActions.closeButton();
  }

  @Then("Save the pipeline")
  public void saveThePipeline() {
    CdfStudioActions.pipelineName();
    CdfStudioActions.pipelineNameIp("GCS_BQ_" + UUID.randomUUID().toString());
    CdfStudioActions.pipelineSave();
    SeleniumHelper.waitElementIsVisible(CdfStudioLocators.statusBanner, 5);
    WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 5);
    wait.until(ExpectedConditions.invisibilityOf(CdfStudioLocators.statusBanner));
  }

  @Then("Preview and run the pipeline")
  public void previewAndRunThePipeline() {
    SeleniumHelper.waitAndClick(CdfStudioLocators.preview, 5L);
    CdfStudioLocators.runButton.click();
  }

  @Then("Verify the preview of pipeline is {string}")
  public void verifyThePreviewOfPipelineIs(String previewStatus) {
    WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 180);
    wait.until(ExpectedConditions.visibilityOfElementLocated(
      By.xpath("//*[@data-cy='valium-banner-hydrator']//span[contains(text(),'" + previewStatus + "')]")));
    if (!previewStatus.equalsIgnoreCase("failed")) {
      wait.until(ExpectedConditions.invisibilityOf(CdfStudioLocators.statusBanner));
    }
  }

  @Then("Click on PreviewData for GCS")
  public void clickOnPreviewDataForGCS() {
    CdfGcsActions.clickPreviewData();
  }

  @Then("Verify Preview output schema matches the outputSchema captured in properties")
  public void verifyPreviewOutputSchemaMatchesTheOutputSchemaCapturedInProperties() {
    List<String> previewOutputSchema = new ArrayList<String>();
    List<WebElement> previewOutputSchemaElements = SeleniumDriver.getDriver().findElements(
      By.xpath("(//h2[text()='Output Records']/parent::div/div/div/div/div)[1]//div[text()!='']"));
    for (WebElement element : previewOutputSchemaElements) {
      previewOutputSchema.add(element.getAttribute("title"));
    }
    Assert.assertTrue(previewOutputSchema.equals(propertiesOutputSchema));
  }

  @Then("Close the Preview")
  public void closeThePreview() {
    CdfGCSLocators.closeButton.click();
    CdfStudioActions.previewSelect();
  }

  @Then("Deploy the pipeline")
  public void deployThePipeline() {
    SeleniumHelper.waitElementIsVisible(CdfStudioLocators.pipelineDeploy, 2);
    CdfStudioActions.pipelineDeploy();
  }

  @Then("Save and Deploy Pipeline")
  public void saveAndDeployPipeline() throws InterruptedException {
    CdfStudioActions.pipelineName();
    CdfStudioActions.pipelineNameIp("TestPipeline" + UUID.randomUUID().toString());
    CdfStudioActions.pipelineSave();
    SeleniumHelper.waitElementIsVisible(CdfStudioLocators.statusBanner);
    WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 5);
    wait.until(ExpectedConditions.invisibilityOf(CdfStudioLocators.statusBanner));
    CdfStudioActions.pipelineDeploy();
  }

  @Then("Run the Pipeline in Runtime")
  public void runThePipelineInRuntime() throws InterruptedException {
    CdfPipelineRunAction.runClick();
  }

  @Then("Wait till pipeline is in running state")
  public void waitTillPipelineIsInRunningState() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 300);
    wait.until(ExpectedConditions.or(
      ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-cy='Succeeded']")),
      ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-cy='Failed']"))));
  }

  @Then("Verify the pipeline status is {string}")
  public void verifyThePipelineStatusIs(String status) {
    boolean webelement = false;
    webelement = SeleniumHelper.verifyElementPresent("//*[@data-cy='" + status + "']");
    Assert.assertTrue(webelement);
  }

  @Then("Open Logs")
  public void openLogs() throws FileNotFoundException, InterruptedException {
    CdfPipelineRunAction.logsClick();
    BeforeActions.scenario.write(CdfPipelineRunAction.captureRawLogs());
    PrintWriter out = new PrintWriter(BeforeActions.myObj);
    out.println(CdfPipelineRunAction.captureRawLogs());
    out.close();
  }

  @Then("Validate successMessage is displayed")
  public void validateSuccessMessageIsDisplayed() {
    CdfLogActions.validateSucceeded();
  }

  @Then("Get Count of no of records transferred to BigQuery in {string}")
  public void getCountOfNoOfRecordsTransferredToBigQueryIn(String tableName) throws IOException, InterruptedException {
    int countRecords = gcpClient.countBqQuery(CdapUtils.pluginProp(tableName));
    BeforeActions.scenario.write("**********No of Records Transferred******************:" + countRecords);
    Assert.assertTrue(countRecords > 0);
  }

  @Then("Delete the table {string}")
  public void deleteTheTable(String tableName) throws IOException, InterruptedException {
    gcpClient.dropBqQuery(CdapUtils.pluginProp(tableName));
    BeforeActions.scenario.write("Table Deleted Successfully");
  }

  @Then("Enter the GCS Properties with blank property {string}")
  public void enterTheGCSPropertiesWithBlankProperty(String property) throws InterruptedException {
    if (property.equalsIgnoreCase("referenceName")) {
      CdfGcsActions.getGcsBucket(CdapUtils.pluginProp("gcsCsvBucket"));
      CdfGcsActions.selectFormat(CdapUtils.pluginProp("gcsCSVFileFormat"));
    } else if (property.equalsIgnoreCase("path")) {
      CdfGcsActions.enterReferenceName();
      CdfGcsActions.selectFormat(CdapUtils.pluginProp("gcsCSVFileFormat"));
    } else if (property.equalsIgnoreCase("format")) {
      CdfGcsActions.enterReferenceName();
      CdfGcsActions.getGcsBucket(CdapUtils.pluginProp("gcsCsvBucket"));
    }
  }

  @Then("Validate mandatory property error for {string}")
  public void validateMandatoryPropertyErrorFor(String property) {
    CdfStudioActions.clickValidateButton();
    SeleniumHelper.waitElementIsVisible(CdfStudioLocators.validateButton);
    CdapUtils.validateMandatoryPropertyError(property);
  }

  @Then("verify the datatype")
  public void verifyTheDatatype() {
    boolean flag = false;
    List<WebElement> elements = SeleniumDriver.getDriver().findElements(
      By.xpath("//*[@data-cy=\"select-undefined\"]/select"));
    for (WebElement datatype : elements) {
      String title = datatype.getAttribute("title");
      flag = title.equals("timestamp");
    }
    Assert.assertTrue(flag);
  }

  @Then("Verify Output Path field Error Message")
  public void verifyOutputPathFieldErrorMessage() {
    CdfBigQueryPropertiesActions.getSchema();
    SeleniumHelper.waitElementIsVisible(CdfGCSLocators.getSchemaLoadComplete, 10L);
    String expectedErrorMessage = CdapUtils.errorProp(ERROR_MSG_GCS_INVALID_PATH);
    String actualErrorMessage = CdapUtils.findPropertyErrorElement("pathField").getText();
    Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
    String actualColor = CdapUtils.getErrorColor(CdapUtils.findPropertyErrorElement("pathField"));
    String expectedColor = CdapUtils.errorProp(ERROR_MSG_COLOR);
    Assert.assertEquals(expectedColor, actualColor);
  }

  @Then("Verify output field {string} in target BigQuery table {string} contains path of the GcsBucket {string}")
  public void verifyOutputFieldInTargetBigQueryTableContainsPathOfTheGcsBucket(
    String field, String targetTable, String bucketPath) throws IOException, InterruptedException {
    String pathFromBQTable = GcpClient
      .executeSelectQuery("SELECT distinct " + CdapUtils.pluginProp(field) + " as bucket FROM `"
                            + (CdapUtils.pluginProp("projectId")) + "."
                            + (CdapUtils.pluginProp("dataset")) + "."
                            + CdapUtils.pluginProp(targetTable) + "` ");
    BeforeActions.scenario.write("GCC bucket path in BQ Table :" + pathFromBQTable);
    Assert.assertEquals(CdapUtils.pluginProp(bucketPath), pathFromBQTable);
  }

  @Then("Verify datatype of field {string} is overridden to data type {string} in target BigQuery table {string}")
  public void verifyDatatypeOfFieldIsOverriddenToDataTypeInTargetBigQueryTable(
    String field, String dataType, String targetTable) throws IOException, InterruptedException {
    String dataTypeInTargetTable = GcpClient
      .executeSelectQuery("SELECT data_type FROM `" + (CdapUtils.pluginProp("projectId")) + "."
                            + (CdapUtils.pluginProp("dataset")) + ".INFORMATION_SCHEMA.COLUMNS` " +
                            "WHERE table_name = '" + CdapUtils.pluginProp(targetTable)
                            + "' and column_name = '" + CdapUtils.pluginProp(field) + "' ");
    BeforeActions.scenario.write("Data type in target BQ Table :" + dataTypeInTargetTable);
    Assert.assertEquals(CdapUtils.pluginProp(dataType),
                        dataTypeInTargetTable.replace("64", StringUtils.EMPTY).toLowerCase());
  }
}
