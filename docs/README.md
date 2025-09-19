# Performative User Guide


![Product Screenshot](Ui.png)

## Introduction
Performative is a desktop app for managing tasks in different formats, such as to-dos, deadlines and events. 
It is optimized for use via a Command Line Interface (CLI), 
while having the benefits of a Graphical User Interface (GUI).

- [Quick Start](#quick-start)
- [Features](#features)
  - [Adding to-dos](#adding-to-dos-todo)
  - [Adding deadlines](#adding-deadlines-deadline)
  - [Adding events](#adding-events-event)
  - [Listing all tasks](#listing-all-tasks-list)


## Quick Start
1. Ensure you have Java `17` or above installed on your computer. 
Mac users: Ensure you have the precise JDK version prescribed here.
2. Download the latest `.jar` file from here.
3. Copy the file to the folder you want to use as the home folder for your Performative application.
4. Open a command terminal, `cd` into the folder you put the `.jar` file in, and run the command:
   `java -jar performative.jar`
A GUI similar to the above screenshot should appear in a few seconds. 

## Features

> [!NOTE]
> About date time formats:
> 
> [date] can be represented in the formats 'YYYY-MM-DD' or by the day of the week (e.g. Mon, Monday), in which the next 
> occurence of the specified day will be used.
> 
> [time] is in the 24-hour format 'HHMM'. If day of the week is used for [date], 
> it is optional to include the this field, in which a default timing (according to the feature) will be set.


### Adding to-dos: `todo`

To-dos consist of a task description.

Format: `todo [description]`

For example, `todo read book` has the task saved and displayed as:

```
[T][] read book
```

### Adding deadlines: `deadline`

Deadlines consist of a task description and a due date and time.

Format: `deadline [description] /by [date] [time]` 

This command adds a deadline with a description and a due date and time.

For example, `deadline submit report /by 2025-12-25 1900` has the task saved and displayed as:

```
[D][] submit report (by: 25 Dec 2025 1900)
```

If a day of week is set in the [date], the time is set to 2359 by default if not specified. 

For example, `deadline prepare for presentation /by Mon` has the task saved and displayed as:

```
[D][] prepare for presentation (by: 22 Sep 2025 2359)
```

For example: `deadline wash dishes /by Mon 1900` has the task saved and displayed as:

```
[D][] wash dishes (by: 22 Sep 2025 1900)
```

(in the above examples, if today is 19 Sep 2025, a Friday, the 'Mon' will be set as the next Monday, 22 Sep 2025)

### Adding events: `event`

Events consist of a task description, a start date and time, and an end date and time.

Format: `event [description] /at [start date] [start time] to [end date] [end time]`

This command adds an event with a description, a start date and time, and an end date and time.

For example, `event outing /from 2025-12-25 1200 /to 2025-12-26 1200` has the task saved and displayed as:

```
[E][] outing (from: 25 Dec 2025 1200, to: 26 Dec 2025 1200)
```

If a day of week is set in the [date], the time is set to 0900 by default if not specified.

For example, `event conference /from Mon 1200 /to Tue 2200` has the task saved and displayed as:

```
[E][] conference (from: 22 Sep 2025 1200, to: 23 Sep 2025 2200)
```

For example: `event short trip /from Mon /to Tue` has the task saved and displayed as:

```
[D][] short trip (from: 22 Sep 2025 0900, to: 23 Sep 2025 0900)
```

### Listing all tasks: `list`



### Finding tasks by substring: `find`


### Marking/unmarking a task as done/undone: `mark`/`unmark`


### Deleting a task: `delete`