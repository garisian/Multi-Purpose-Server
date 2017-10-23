# Multi-Purpose-Server
This is used as the backend server for both BookMeNow Chrome extension and Locate Andoid App. BookMeNow backend has been implemented but Locate is still a work-in-progress

## Usage
Currently the server is run via localhost. Simply modifying the host address from localhost will allow it to work for anyone. Only request with proper headers and tags specified in the code will be executed. There is a document called "tags.txt" with all the accepted post requests (TODO)

## BookMeNow
In order to allow the app to send emails, you must do the following steps:

Allow less secure app: ON in https://myaccount.google.com/lesssecureapps as the server needs a gmail id and password to send from.

Enable POP Download via the following instructions
First, set up POP in Gmail
On your computer, open Gmail.
In the top right, click Settings Settings.
Click Settings.
Click the Forwarding and POP/IMAP tab.
In the "POP Download" section, select Enable POP for all mail or Enable POP for mail that arrives from now on.
At the bottom of the page, click Save Changes.


## Locate
Currently Under Development
