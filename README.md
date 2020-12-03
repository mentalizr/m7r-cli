# m7r-cli

mentalizr command line interface

Allows for administering programs, patients, therapists and access keys as well as
performing backups and recoveries.

## Build

Required: gradle 5.6

    gradle fatJar

## Install

Put *bin* directory on PATH.

## Usage     
     
### Show version

    $ m7r version     
     
### Initialize

Initialize `m7r`, create configuration directory under *~/.m7r* if not preexisting:

    $ m7r init
    
### Configuration
    
Show m7r configuration as specified in *~/.m7r/cli/cli.config*:

    $ m7r config show
    
Open m7r configuration file *~/.m7r/cli/cli.config* with default editor:

    $ m7r config edit
     
### Session management

#### Login

Perform login operation. User and password are optional and will be prompted for input if not specified.

    $ m7r login -u <user> -p <password>
   
Read credentials from file `~/.m7r/cli/credentials.txt` 
which must contain the user name as a first line, and the password as a second line:
    
    $ m7r login -c 
    
#### Logout

Perform logout operation:

    $ m7r logout
    
#### Status

Show session status:

    $ m7r status
    
Session will be refreshed, user role will be shown if logged in.
    
#### Noop

Execute a no operation command and refresh session:

    $ m7r noop
    
### User management of patients

#### Show all patients

Show all users in role patient, therapist or administrator:

    $ m7r patient show
    
#### Add patient

Add patient, input will be prompted on command line:

    $ m7r patient add
    
Add patient by json file of type *AddPatient*:
 
     $ m7r patient add --from-file <jsonFile>
     
Show json template for adding patient:
 
     $ m7r patient add --show-template
     
 #### Get patient
 
 Get patient by user name as json of type *PatientRestore* 
 
     $ m7r patient --user <username>
     
#### Restore patient

Restore patient from json of type *PatientRestore*:

    $ m7r patient restore --from-file <jsonFile>
    
#### Delete patient

Delete patient by user name:

    $ m7r patient delete --user <username>
     
### User management of therapists

As an analogy to patients:

    $ m7r therapist add
    $ m7r therapist add --from-file <jsonFile>
    $ m7r therapist add --show-template
    $ m7r therapist restore --from-file <jsonFile> 
    $ m7r therapist delete --user <username>

### Access key managment

#### Create access keys

Create a specified number of access keys. All related information will
be prompted for input.

    $ m7r accessKey create
    
Show all access keys:

    $ m7r accessKey show
    
Delete access key:

    $ m7r accessKey delete --accessKey <acces-key>

### Program management

#### Add program

Add a program, input will be prompted:

    $ m7r program add
    
#### Show all programs

    $ m7r program show
    
#### Delete program

Delete a program by program id. 
All referencing patients must be deleted first.
 
    $ m7r program delete --program <program_id>
    
### Backup

Crate a backup of all user entities in a subdirectory of 
*~/.m7r/backup* which will be named after current timestamp.

    $ m7r backup

### Recover

Recover from a specified backup directory. 
No entities must be preexisting.

    $ m7r recover --directory <backup_directory>
    
### Wipe

Dangerous! Delete all entities except administrator accounts.
Security question needs to be confirmed.

    $ m7r wipe
    