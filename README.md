# m7r-cli
mentalizr command line interface

## Build

Required: gradle 5.6

    gradle fatJar
     
### Session management

#### Login

Performs login operation. User and password are prompted for input if not given as 
options.

    $ m7r login -u <user> -p <password>
   
As an additional option, credentials can be read from a file `~/.m7r/cli/credentials.txt` 
which contains the user name as a first line, and the password as a second line.
However, be aware of potential security risks, when using this option.
    
    $ m7r login -c 
    
##### Specific options

    -u --user                user name
    -p --password            password
    -c --credential-file     cread credentials from file
    
#### Logout

Performs logout operation.

    $ m7r logout
    
#### Status

Shows session status. Refreshes session. Shows user role if logged in.

    $ m7r status
    
#### Noop

Executes a no operation command. Refreshes session.

    $ m7r noop