# Engineer Fleet Service
<img src="https://github.com/simple-icons/simple-icons/blob/develop/icons/java.svg" width="30" height="30">   Springboot micro service ready to run. 

# Story [![](https://img.shields.io/badge/apache-springboot-brightgreen.svg)]() [![](https://img.shields.io/badge/language-java-yellow.svg)]() [![](https://img.shields.io/badge/IT-spock-green.svg)]()
> Challenge!

You are given a[​]int​ scooters, which has as many elements as there are districts in Stockholm that Company operates in.
Foreach i​, ​scooters[i]​ is the number of scooters in that district (0-based index).
During a work day, scooters are maintained (batteries changed, cleaned, checked for damages) by the Fleet Manager (FM) 
and possibly other Fleet Engineers (FEs). Each FE, as well as the FM, can only maintain scooters in one district. 
Additionally, there is a limit on how many scooters a single FE may supervise: the FM is able to maintain up to C scooters, 
and an FE is able to maintain up to P scooters. Each scooter has to be maintained by some FE or the FM.

## Business Logic
Find the minimum number of FEs which are required to help the FM so that every scooter in each district of Stockholm is maintained. 
Note that you may choose which district the FM should go to. 

### Input/Output
As input, you are given the []int scooters, int C and int P.
The result should be i​ nt​ - the minimum number of FEs which are required to help the FM

### Constraints
[]scooters will contain between 1 and 100 elements. 
Each element in scooters will be between 0 and 1000. C will be between 1 and 999.
P will be between 1 and 1000.

## How to run

```
./gradlew clean build

./gradlew bootRun
```

## How to consume
|         Operation                      |           Example       |
|----------------------------------------|-------------------------|
| Find the optimized number of engineers | POST   /fleet/engineers |


+ Request (application/json)

            {
                "scooters": [15, 10],
                "C": 12,
                "P": 5
            }

+ Response 200 (application/json)

    + Attributes

        + fleet_engineers - Number of needed fleet engineers.

    + Body

            {
                "fleet_engineers": 3
            }

+ Response 400 (application/json)

            {
                "error": {
                    "path": "/fleet/engineers",
                    "code": "400",
                    "status": "BAD REQUEST",
                    "message": ""
                }
            }
