# build maven package (part of full build lifecycle:
#   validate - validate the project is correct and all necessary information is available
#   compile - compile the source code of the project
#   test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
#   package - take the compiled code and package it in its distributable format, such as a JAR.
#   integration-test - process and deploy the package if necessary into an environment where integration tests can be run
#   verify - run any checks to verify the package is valid and meets quality criteria
#   install - install the package into the local repository, for use as a dependency in other projects locally
#   deploy - done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects)
mvn deploy

# build package without running tests
mvn package -DskipTests
mvn package -Dmaven.test.skip=true 

#So if you are testing one module than you are safe using -fae. 
# Otherwise, if you have multiple modules, and if you want all 
#of them tested (even the ones that depend on the failing tests 
# module), you should run
mvn clean install -fn.
#-fae will continue with the module that has a failing test (will run all other tests), but all modules that depend on it will be skipped.

# clean the project (clean lifecycle)
mvn clean

# build project site (site lifecycle)
mvn site

# build and install test jar
mvn jar:test-jar
mvn install:install-file -DgroupId=groupId -DartifactId=nfvo-core-lib -Dversion=1.0-SNAPSHOT -Dclassifier=tests -Dpackaging=test-jar -Dfile=nfvo-core-lib-1.0-SNAPSHOT-tests.jar
