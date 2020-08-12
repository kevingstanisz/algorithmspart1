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
- Dealing with backwash. Only the open sites that were in the path should be highlighted. Using the first image on the left as an example, not highlighting the the open sites on the bottom of the left of the board is difficult because it is connected to the virtual bottom. To remedy this, I had to make two Union Find instances with one of them not including the virtual bottom. 


#### Missed points on: 
- Too many calls to statistic methods with large data sets 




## Week 2 - Deques and Randomized Queues 
### Score - 95% 

#### Objective: 
This assignment required me to create the functionalitt for a deque, which allows users to add and remove and item from the front or the back of the data structure. 


#### What I learned: 
- The importance of resizing arrays so that it does not take up too much memory when there is not a lot of data from deleting many entries - double the array when full and halve the array the array is a quarter full.
- This was the first time I created my own Iterator with hasNext() and next() operators.
- Several different sorting algorithms and understanding what the important features of a sorting algorithm are. It is important to be in-place and stable. In-place means there does not need to be any additional memory allocated to execute the algorithm. Stable means that keys with the same value remain in the same order as before the sort. The ideal sorting algorithm would be in-place, stable, and have a time complexity of N log N in the worst, average, and best case. Heap and mergesort can get close. 


#### Challenges faced: 
- The second part of this assignment was to randomly delete an item from a randomized queue. Randomly choosing a number from the amount of the elements in the queue and then setting that value to null seemed like the obvious route. However, this would create null values in the middle of the array. To solve this, I chose a random value, swapped the item in the random index I selected with the last item in the array and then set that item to null and decremented the size of queue. This kept the array in tact with no null values in the middle of the array. The other option was to use the StdRandom.shuffle method to shuffle the array and set the last item to null, but this took up too much time versus picking a random number and swapping two items. 


#### Missed points on: 
- Points off for too many typecast warnings. This was unclear on how to prevent this since in the lesson we were taught to welcome compile-time erros and avoid run-time erros. Since the genereic type name cannot be used when declaring arrays, casting must be done to make it work. 





