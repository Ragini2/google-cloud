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
package io.cdap.plugin.gcp.stepsdesign;

import io.cdap.e2e.pages.actions.BigQueryActions;
import io.cdap.e2e.pages.actions.CdfBigQueryPropertiesActions;
import io.cdap.e2e.pages.actions.CdfGcsActions;
import io.cdap.e2e.pages.actions.CdfLogActions;
import io.cdap.e2e.pages.actions.CdfPipelineRunAction;
import io.cdap.e2e.pages.actions.CdfStudioActions;
import io.cdap.e2e.pages.locators.CdfBigQueryPropertiesLocators;
import io.cdap.e2e.pages.locators.CdfStudioLocators;
import io.cdap.e2e.utils.CdfHelper;
import io.cdap.e2e.utils.GcpClient;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepsdesign.BeforeActions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import static io.cdap.e2e.utils.RemoteClass.createDriverFromSession;

/**
 * DemoTestCase GCP.
 */
public class GCSBasicDemo implements CdfHelper {
    static int i = 0;
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

    @Then("Link Source and Sink to establish connection")
    public void linkSourceAndSinkToEstablishConnection() throws InterruptedException {
        Thread.sleep(2000);
        SeleniumHelper.dragAndDrop(CdfStudioLocators.fromGCS, CdfStudioLocators.toBigQiery);
    }

    @Then("Enter the GCS Properties with {string} GCS bucket")
    public void enterTheGCSPropertiesWithGCSBucket(String bucket) throws InterruptedException, IOException {

        CdfGcsActions.gcsProperties();
        CdfGcsActions.enterReferenceName();
        CdfGcsActions.enterProjectId();
        CdfGcsActions.enterGcsBucket(bucket);
        CdfGcsActions.enterFormat();
        CdfGcsActions.skipHeader();
        CdfGcsActions.getSchema();
        Thread.sleep(10000);

    }


    @Then("Run and Preview")
    public void runAndPreview() throws InterruptedException {

        CdfStudioActions.runAndPreviewData();
    }

    @Then("Overrride the BigQuery Properties")
    public void overrrideTheBigQueryProperties() throws InterruptedException {
        CdfStudioLocators.bigQueryProperties.click();
        SeleniumDriver.getDriver().findElement(By.xpath("(//*[@title=\"int\"])[2]")).click();
        Thread.sleep(5000);
        Select drp = new Select(SeleniumDriver.getDriver().findElement(By.xpath("(//*[@title=\"int\"])[2]")));
        drp.selectByValue("string");
        CdfBigQueryPropertiesLocators.validateBttn.click();
        Thread.sleep(6000);
        CdfGcsActions.closeButton();
    }

    @Then("Open {string} link to login")
    public void openLinkToLogin(String arg0) throws IOException, InterruptedException {
        RemoteWebDriver driver = createDriverFromSession(SeleniumDriver.session(), SeleniumDriver.url);
        SeleniumDriver.openPage(SeleniumHelper.readParameters(arg0));
        Thread.sleep(3000);
    }


    @Then("enter the Query to check the count of table created {string}")
    public void enterTheQueryToCheckTheCountOfTableCreated(String table) throws InterruptedException {
        BigQueryActions.writeNewQuery("SELECT COUNT(*) FROM cdf-athena.test_automation." + table);
    }

    @Then("capture the count")
    public void captureTheCount() throws InterruptedException {
        BigQueryActions.countTable();
    }

    @Then("Save and Deploy Pipeline")
    public void saveAndDeployPipeline() throws InterruptedException {
        CdfStudioActions.pipelineName();
        CdfStudioActions.pipelineNameIp("TestPipeline" + UUID.randomUUID().toString());
        CdfStudioActions.pipelineSave();
        Thread.sleep(3000);
        CdfStudioActions.pipelineDeploy();
    }

    @Then("Run the Pipeline in Runtime")
    public void runThePipelineInRuntime() throws InterruptedException {
        CdfPipelineRunAction.runClick();
    }

    @Then("Wait till pipeline is in running state")
    public void waitTillPipelineIsInRunningState() throws InterruptedException {
        Boolean bool = true;
        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 1000000);
        wait.until(ExpectedConditions.or(ExpectedConditions.
                                           visibilityOfElementLocated(By.xpath("//*[@data-cy='Succeeded']")),
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

    @Then("validate successMessage is displayed")
    public void validateSuccessMessageIsDisplayed() {
        CdfLogActions.validateSucceeded();
    }

    @Then("Click on Advance logs and validate the success message")
    public void clickOnAdvanceLogsAndValidateTheSuccessMessage() {
        CdfLogActions.goToAdvanceLogs();
        CdfLogActions.validateSucceeded();
    }


    @Then("Enter the BigQuery Properties for table {string}")
    public void enterTheBigQueryPropertiesForTable(String arg0) throws InterruptedException, IOException {
        CdfBigQueryPropertiesActions.enterBigQueryProperties(SeleniumHelper.readParameters(arg0));
    }


    @Then("Close the GCS Properties")
    public void closeTheGCSProperties() {
        CdfGcsActions.closeButton();
    }

    @Then("Close the BigQuery Properties")
    public void closeTheBigQueryProperties() {
        CdfGcsActions.closeButton();
    }

    @Then("Get Count of no of records transferred to BigQuery in {string}")
    public void getCountOfNoOfRecordsTransferredToBigQueryIn(String arg0) throws IOException, InterruptedException {
        int countRecords;
        countRecords = gcpClient.countBqQuery(SeleniumHelper.readParameters(arg0));
        BeforeActions.scenario.write("**********No of Records Transferred******************:" + countRecords);
        Assert.assertTrue(countRecords > 0);
    }

    @Then("Delete the table {string}")
    public void deleteTheTable(String arg0) throws IOException, InterruptedException {
        gcpClient.dropBqQuery(SeleniumHelper.readParameters(arg0));
        BeforeActions.scenario.write("Table Deleted Successfully");
    }

}
