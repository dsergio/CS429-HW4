
# David Sergio
# CSCD 429 HW 4 Extra Credit

library(ggplot2)

# to clean data, I performed a Replace All regex [ ]+ with " "

# replace with full path
hw4data <- read.csv("\\synthetic_control_data_cleaned.txt", header = FALSE, sep = " ")
#hw4data <- read.csv("\\class_example.txt", header = FALSE, sep = " ")

str(hw4data)

d <- dist(hw4data)

print(d)

#clusters1 <- hclust(d, method = "single")
#plot(clusters1)

clusters2 <- kmeans(d, centers = 6)

#print(clusters2)
str(clusters2)
#print(clusters2$cluster)

#plot(hw4data, col = clusters2$cluster)

data_with_clusters <- cbind(hw4data, clusterNumber = clusters2$cluster)
head(data_with_clusters)

data_split = split(data_with_clusters, f = data_with_clusters$clusterNumber)
cluster1 <- subset(data_split$`1`, select = -clusterNumber)
cluster2 <- subset(data_split$`2`, select = -clusterNumber)
cluster3 <- subset(data_split$`3`, select = -clusterNumber)
cluster4 <- subset(data_split$`4`, select = -clusterNumber)
cluster5 <- subset(data_split$`5`, select = -clusterNumber)
cluster6 <- subset(data_split$`6`, select = -clusterNumber)

#t_cluster1 <- t(cluster1)
#print(t_cluster1)

#print(cluster1)

#s_cluster1 <- stack(cluster1)
#print(s_cluster1)
#plot(s_cluster1, x = s_cluster1$ind)
#barplot(as.matrix(cluster1))

ggplot(stack(cluster1), aes(x = ind, y = values)) + geom_point()
ggplot(stack(cluster2), aes(x = ind, y = values)) + geom_point()
ggplot(stack(cluster3), aes(x = ind, y = values)) + geom_point()
ggplot(stack(cluster4), aes(x = ind, y = values)) + geom_point()
ggplot(stack(cluster5), aes(x = ind, y = values)) + geom_point()
ggplot(stack(cluster6), aes(x = ind, y = values)) + geom_point()

# replace with full path
#write.csv(data_split$`1`, "\\class_example_cluster1.txt")







