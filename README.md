# betalog_converter
Create BetaLog.csv file from BetaLog.txt file

Each record in the BetaLog.txt file may have one or more lines. The first number in the first 
line of a record is the time (seconds) for that record. Variables in a record appear as:

varName = value

where value is a floating point number.

The output file, BetaLog.csv, will have a comma-separated header consisting of the word "time", 
and all of the variable names. Each subsequent line in BetaLog.csv will be a comma-separated 
list of floating point numbers, including the time and the value of each variable.
