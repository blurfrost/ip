# Performative User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

Deadlines consist of a task description and a due date and time.



### deadline [description] /by [date YYYY-MM-DD] [time HHMM] ###

This command adds a deadline with a description and a due date and time.
The due date can be in the formats 'YYYY-MM-DD HHMM'

Example: `deadline submit report /by 2025-12-25 1900`

```
[D][] submit report (by: 25 Dec 2025 1900)
```

### deadline [description] /by [day of the week] [optional: time HHMM] ###
This command adds a deadline with a description and a due day, set to any day of the week
(e.g. Mon, Monday are both acceptable). The time is set to 2359 by default, but is set to the specified time 
if included in the command.

Example: `deadline prepare for presentation /by Mon`

(if today is 19 Sep 2025, a Friday, the 'Mon' will be set as the next Monday, 22 Sep 2025)

```
[D][] prepare for presentation (by: 22 Sep 2025 2359)
```

Example: `deadline wash dishes /by Tue 1900`

(likewise, if today is 19 Sep 2025, a Friday, the 'Tue' will be set as the next Tuesday, 23 Sep 2025)

```
[D][] wash dishes (by: 23 Sep 2025 1900)
```





## Feature ABC

// Feature details


## Feature XYZ

// Feature details