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
This assignment required me to create the functionality for a deque, which allows users to add and remove and item from the front or the back of the data structure. 


#### What I learned: 
- The importance of resizing arrays so that it does not take up too much memory when there is not a lot of data from deleting many entries - double the array when full and halve the array the array is a quarter full.
- This was the first time I created my own Iterator with hasNext() and next() operators.
- The basic sorting algorithms. These are not necessarily the most efficient ones (selection, insertion, shell, quick) but they gave me a good idea of the different approaches that can be taken when sorting. It also cleared up how to calculate time complexity in the worst, average, and best case.  


#### Challenges faced: 
- The second part of this assignment was to randomly delete an item from a randomized queue. Randomly choosing a number from the amount of the elements in the queue and then setting that value to null seemed like the obvious route. However, this would create null values in the middle of the array. To solve this, I chose a random value, swapped the item in the random index I selected with the last item in the array and then set that item to null and decremented the size of queue. This kept the array in tact with no null values in the middle of the array. The other option was to use the StdRandom.shuffle method to shuffle the array and set the last item to null, but this took up too much time versus picking a random number and swapping two items. 


#### Missed points on: 
- Points off for too many typecast warnings. This was unclear on how to prevent this since in the lesson we were taught to welcome compile-time erros and avoid run-time erros. Since the genereic type name cannot be used when declaring arrays, casting must be done to make it work. 




## Week 3 - Collinear Points 
### Score - 85% 

#### Objective: 
The goal of this assigment was to find the largest line segment that connected 4 or more points in a plane from (0, 0) to (1, 1). There were two ways of doing this, the Brute force way and the Faster, sorting-based solution. The Brute force way was to look at every combination of 4 points and compare the slopes of one of the points to the other three. If this was the same, then the points were all in a line. The faster appraoch required calucalating the slope of all points to one origin. After sorting these slopes, if there were 3 with the same slope, then that meant they were in a line. Repeat for all of the points and all of the lines would be detemrined. 


#### What I learned: 
- Why heap and mergesort are two of the better sorting algorithms. It is important to be in-place and stable. In-place means there does not need to be any additional memory allocated to execute the algorithm. Stable means that keys with the same value remain in the same order as before the sort. The ideal sorting algorithm would be in-place, stable, and have a time complexity of N log N in the worst, average, and best case. Heap and mergesort can get close. 
- It is necessary to understand the thoery behind the API before implementing it. It took me a while to understand the faster, sorting-based solution, and I tried to implement it while I was learning about it and that caused me to take a much longer time to complete the assignment. 
- The important of immutability. In Java, primitive data types are immutable, which means that the values stored inside them cannot be changed once set. However, for non-primitive types, deep copies need to be made to ensure another method cannot change its value. 


#### Challenges faced: 
- This was the by far the most diffiucult assignment of the six. The main challenge was not duplicating line segments. Say they were 5 or 6 points in a line, then there would be more than one solution to having 4 in a row. Since only the maximum line was to be recorded, it was important to make sure the outermost points were recorded before adding it as a line. This meant using the compareTo() function, which determined which points were the farthest to the top right, with the right being prioritized. This ensured the farthest points in the line segment were known an no middle points would be added. 



#### Missed points on: 
- Timing - the faster, sorting based solution still could have been faster. Too many comparisons were being made and could still have been optimized. Also, everything passed into the API had to be immutable.  



## Week 4 - 8 Puzzle  
### Score - 100% 

#### Objective: 
This was a very practical assignement. The 8 puzzle can be played on a 3x3 grid with 8 sliding tiles. The goal of the game is to rearrange the tiles by sliding tiles into the empty slot so the tiles end up in row-major order. 


#### What I learned: 
- Priority queues and why they are so important. A priority queue is a queue where each element has a value or priority to it. The two functions that we were concerned with in this lesson were inserting an item and returning the maximum value. An array can insert an item in O(1) and return the maximum in O(n). An ordered array can insert an iten in O(1) and return the largest item in O(n). The binary heap implementation resulted in O(log(n)) for both inserting and returning. A binary heap is a binary tree that is a complete tree. 
- The lesson was also about elementary symbol tables and how to implement adding and retrieving pairs or keys and values. It was clear binary search trees performed much better than binary searches and sequential searches. 
- Utilizing the A* search alogorithm and MinPQ data type to solve this problem. By calculating the Hamming and Manhattan distances, this allowed for each search node to have a priority. The algorithm says to insert a node onto the queue, delete the search node with the minimum prioirity, and queue all neighboring search nodes onto the queue. This is repeated until the goal board has been achieved.


#### Challenges faced: 
- This assignment was not terribly difficult. One challange was understanding why using the twin will result in one board being solvable and one board being unsolvable. Another was not looking for the goal board when enqueuing the board but rather when the search node was being dequeued. 


#### Missed points on: 
- Nothing!




