# RandomGenerator
Project to create useful random generators.

Currently Available:
 - ZoneDateTime
 - InetAddress
 - Dictionary
 
List of first names used in tests are from: https://github.com/dominictarr/random-name

An Object Generator uses those generators to try an feel objects with random values.

Main class uses ApacheLogLineGenerator to output to a file random Apache log lines.

#TODO
 - Add Usage explanation of generators
 - Configure main class to take in parameters such as file name, if generation should be live or not, number of lines in a non live generate file


I suggest using ELK to analyze generated log files.

ELK configuration for beginner dashboard can be found at : https://github.com/elastic/examples/tree/master/ElasticStack_apache