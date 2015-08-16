# conference-scheduler
Code for the problem specified in problem-statement.txt

## Steps to run code:
1. Clone the project.

2. Execute `mvn clean install`

3. Go to newly created `target` folder.

4. Execute `java -cp "*" foo.bar.conference.main.ConferenceSchedulerMain ../talks/talks.txt`

Folder `talks` contains different schedules which can be passed as input to program.

## Assumptions:

1. The input file passed contains valid list of talks

2. If conference has multiple tracks, each track will have their own networking event.
