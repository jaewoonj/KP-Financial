# CSV re-formatter
## How to Run this application
1. [Download IntelliJ](https://www.jetbrains.com/idea/download/#section=mac)
2. File -> New -> Project from Version Control -> Git
    - Enter this URL: https://github.com/jaewoonj/KP-Financial.git
3. Once the project is successfully loaded on IntelliJ, Run the application by clicking the Start button
    1. Before running check the following conditions
    2. Click and install all the dependencies, if intellij shows any install notification  
    3. Create "raw_data" under the project directory, if it doesn't exist
    4. Make sure to have all the input csv files under "raw_data"
    5. Create "output" under the project directory, if it doesn't exist

     
### Notes
* Under "raw_data" are the input csv files    
* Under "output" are the processed csv files

** the output files are sorted by the security name first **
** Sorting by dates and then by company can be easily achieved using Excel: Data -> Sort **  
  
![screenshot of excel](https://raw.githubusercontent.com/jaewoonj/KP-Financial/master/guide/img/excel-data-sort.png)
