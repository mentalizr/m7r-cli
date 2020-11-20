# m7r-cli
mentalizr command line interface

## Build

Required: gradle 5.6

    gradle fatJar
     
## Usage     
     
### Session management

#### Login

Performs login operation. User and password are prompted for input if not given as 
options.

    $ m7r login -u <user> -p <password>
   
As an additional option, credentials can be read from file `~/.m7r/cli/credentials.txt` 
which contains the user name as a first line, and the password as a second line.
However, be aware of potential security risks, when using this option.
    
    $ m7r login -c 
    
##### Specific options

    -u --user                    user name
    -p --password                password
    -c --credential-file         cread credentials from file
    
#### Logout

Performs logout operation.

    $ m7r logout
    
#### Status

Shows session status. Refreshes session. Shows user role if logged in.

    $ m7r status
    
#### Noop

Executes a no operation command. Refreshes session.

    $ m7r noop
    
### User management

## Show all users

Show all users in role patient, therapist or administrator:

    $ m7r user admin|patient|therapist show
    
## Add user

Add user with role patient, therapist or administrator. Input is prompted on command line:

    $ m7r user admin|patient|therapist add
    
Example:

    $ m7r user therapist add
    
 Add user with a specific role from json file, typed as *AddServiceObject*:
 
     $ m7r user admin|patient|therapist add --from-file myFile.json
     
 Show json template for adding user in a specific role:
 
     $ m7r user admin|patient|therapist add --show-template
     
 ## Get user
 
 Get user by name as json, typed as *ResultServiceObject* 
 
     $ m7r user admin|patient|therapist get --user <username>
     
     
     