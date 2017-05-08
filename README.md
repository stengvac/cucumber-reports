#Cucumber reports
Project Cucumber reports is reporting tool for tests written in Cucumber framework see https://cucumber.io/.
Reports are presented via web application.

#Main idea
The main idea of this project is to store results of executed Cucumber tests to DB. 
Results are send after each tested feature to REST API, where are results grouped 
and persisted to [MongoDB](https://www.mongodb.com/).
Results are grouped by two attributes. First attribute is called in terminology of this project
job name and second one sequential number. As reader can guess reporting tool is focused on
gathering results from Continuous Integration and Continuous Delivery systems for example
[Jenkins](https://jenkins.io/). So tests are grouped per each job execution. Later on detailed
mainly technical reports can be accessed via web application.
 
Statistics, trends and graphs are planned, but for now those sections are under construction.

## Prerequisites
* JDK 8
* MongoDB 3.2+
* Application server supporting JRE 8

##Usage
Download sources from [repo](https://github.com/stengvac/cucumber-reports.git). 
and run compile via maven as:
```
mvn clean install
```
from directories rest-api and view copy *.war files and deploy them on application server. 
Project allow to configure connection to DB via property file. Default values are:
+ spring.data.mongodb.host=localhost
+ spring.data.mongodb.port=27017
+ spring.data.mongodb.user=null
+ spring.data.mongodb.password=null
+ spring.data.mongodb.database=cucumber_reports

After both WAR files are deployed lets start with data collecting.
First of switch annotations used to run Cucumber tests via jUnit runner from
```
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
```

to
Note: right now UnoCucumberRunner is not part of maven repositories so it is 
required to compile project and use local nexus for now.
```
import cz.airbank.cucumber.reports.collector.Cucumber;

import org.junit.runner.RunWith;

@RunWith(UnoCucumberRunner.class)
```

This runner extends Cucumber runner and extract more information from 
tested feature files. Basically runner only add (if conditions are met) another plugin
which collect data from Cucumber formatting/reporting API and later on send them on server via REST in JSON format.
Other plugins executed via runner work without change so it is possible to use multiple reporting tools simultaneously.

UnoCucumberRunner can run feature files locally without sending data to server (so it is basically Cucumber runner).
Modes are switched by environment variables:
 
* cucumber.job.name - job name used to group results by. This value should be immutable in long term so it is possible to create some 
statistics over time. System of course support multiple job names. 
* cucumber.job.sequential.number - The sequential number of job execution. System expects incrementing number series. 
* cucumber.rest.api.url - URL of REST API for data collecting. After each tested feature data are send to this URL.

For example:
Lets have cucumber.job.name=testJob and cucumber.job.sequential.number=5 all incoming tested features with those values 
are grouped together. Results with cucumber.job.name=anotherJob and cucumber.job.sequential.number=6 are also grouped together.
System now register two job names.
