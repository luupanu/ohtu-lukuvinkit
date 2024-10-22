# Reading tips

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/47e9c44c64954e628fa54e620912e9eb)](https://www.codacy.com/app/Koppari/ohtu-lukuvinkit?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=luupanu/ohtu-lukuvinkit&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/luupanu/ohtu-lukuvinkit.svg?branch=master)](https://travis-ci.org/luupanu/ohtu-lukuvinkit)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/47e9c44c64954e628fa54e620912e9eb)](https://www.codacy.com/app/Koppari/ohtu-lukuvinkit?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=luupanu/ohtu-lukuvinkit&amp;utm_campaign=Badge_Grade)

**Reading tips is a web app that at its core lets the user save down reading tips of different types. Other features include tagging, filtering and prioritizing tips, adding comments and keeping track of what you've already read.**

## Installation

A (modernish) browser running JavaScript is required for this app to work properly.

To run the app locally:

1. Clone this repository
2. Install [gradle](https://gradle.org/)
3. Run `gradle run` in the project folder. A local server will be created to `localhost:8080`

## Usage instructions
The app should be quite intuitive to use. At the bottom of the page you will find a form to create a new reading tip. Currently you can choose from three types of reading tips. You can filter the reading tips from the top right hand of the screen. Click a comment section to browse and possibly leave a comment on a reading tip. You can reorder reading tips by dragging and dropping them around, however already read reading tips will always be found at the bottom of the page. It is only possible to reorder tips that belong to the same category (either read, or unread).

## Links

* [Heroku](https://lukuvinkit.herokuapp.com/)
* [Test coverage report](https://app.codacy.com/app/Koppari/ohtu-lukuvinkit/files)
* [Architecture](https://github.com/luupanu/ohtu-lukuvinkit/blob/master/docs/arkkitehtuuri.pdf)
* [Product Backlog](https://docs.google.com/spreadsheets/d/10v1C_SqCL5R2vVQS019tSk6TDwTYgx2USbZ7cdNQoRU)
* [Sprint 1 Backlog](https://docs.google.com/spreadsheets/d/10v1C_SqCL5R2vVQS019tSk6TDwTYgx2USbZ7cdNQoRU/edit#gid=0)
* [Sprint 2 Backlog](https://docs.google.com/spreadsheets/d/10v1C_SqCL5R2vVQS019tSk6TDwTYgx2USbZ7cdNQoRU/edit#gid=1294251749)
* [Sprint 3 Backlog](https://docs.google.com/spreadsheets/d/10v1C_SqCL5R2vVQS019tSk6TDwTYgx2USbZ7cdNQoRU/edit#gid=774886918)
* [Sprint 4 Backlog](https://docs.google.com/spreadsheets/d/10v1C_SqCL5R2vVQS019tSk6TDwTYgx2USbZ7cdNQoRU/edit#gid=893567725)
* [Project report](https://docs.google.com/document/d/1R37DjycL4cZtxiy4vopNPnmZpccZQRmjbc3LnRv8R70/)
* [License](https://github.com/luupanu/ohtu-lukuvinkit/blob/master/LICENSE)

## Definition of Done

A user story can be seen as done when all of the following requirements are fulfilled:

* The acceptance criteria of the user story are fulfilled
* All tests pass successfully
* The line coverage for all tests is at least 80%
* The code is documented at a general level (The usage cases for classes)
* Travis CI build is successful
* Heroku build is successful and runs
