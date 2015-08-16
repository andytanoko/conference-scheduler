# conference-scheduler
Code for the problem specified in problem-statement.txt

## Steps to run code:
1. Clone the project using `git clone https://github.com/sk4x0r/conference-scheduler.git`
2. Go to home folder of project.
3. Execute `mvn clean install`
4. Go to newly created `target` folder.
5. Execute `java -cp "*" foo.bar.conference.main.ConferenceSchedulerMain ../talks/talks.txt`

Folder `talks` contains different schedules which can be passed as input to program.

## Assumptions:
1. The input file passed contains valid list of talks
2. If conference has multiple tracks, each track will have their own networking event.

## References:
1. Similar problem - https://github.com/evagabriela/ConferenceManagerApp
2. Schedule used in one of the input files - http://www.aieaworld.org/2015-conference-schedule
