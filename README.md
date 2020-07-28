# Algorithms, Part I 
#### by Princeton University

## Week 1 - Union Find
### Score - 97% 

#### Objective: 
This assignment was based on percolation. The idea was to see if the system percolates, meaning there is a path of open sites from the top of the system to the bottom of the system. 

IMAGE OF PERCOALTION 

After this was complete, the challenge was to figure out the percentage of sites that had to be openned in order for the system to percolate. By running the simulation many times, the probability converged to a number (59.3%)

IMAGE OF PERCOLATION PROBABILITY


#### What I learned: 
- Implementing a Union Find algorithm to find if a specific site was connected to another (QuickFindUF)
- Using a virtual top and bottom to to more efficiently determine if there was a path from the top to the bottom 
- Using standard statistic libraries to calculate mean, standard deviation, confidence intervals


#### Challenges faced: 
- Dealing with backwash. Only the open site that were in the path should be highlighted. Using the first image on the left as an example, not highlighting the the open sites on the bottom of the left of the board is difficult because it is connected to the virtual bottom. To remedy this, I had to make two Union Find instances with one of them not including the virtual bottom. 


#### Missed points on: 
- Too many calls to statistic methods with large data sets 





