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
    
or:

    $ m7r login -u <user>
    
or:

    $ m7r login
    
#### Logout

Performs logout operation.

    $ m7r logout
    
#### Status

Shows session status. Refreshes session. Shows user role if logged in.

    $ m7r status
    
#### Noop

Executes a no operation command. Refreshes session.

    $ m7r noop