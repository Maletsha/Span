set CLASSPATH=%CLASSPATH%;lib\junit-4.13.1.jar;lib\hamcrest-core-1.3.jar;classes\League.jar;test
javac test\com\company\Span\LeagueTests.java

java org.junit.runner.JUnitCore com.company.Span.LeagueTests