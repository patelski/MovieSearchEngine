import csv

# dictionaries containing the number of occurrences per word:
negwords = {} 
poswords = {}

# total word count in each class:
negcount = 0
poscount = 0

for i in range(500):
	filepath = "../MovieSearchEngine/sentimentTrainingData/neg/neg (" + str(i+1) + ").txt"
	file = open(filepath, 'r')
	contents = file.read().split()
	for j in range(len(contents)):
		negcount += 1
		word = contents[j].lower()
		if word in negwords:
			negwords[word] += 1
		else:
			negwords[word] = 1
negwords["totalamountofwords"] = negcount

writer = csv.writer(open('neg.csv', 'wb'))
for key, value in negwords.items():
	writer.writerow([key, value])

for i in range(500):
	filepath = "../MovieSearchEngine/sentimentTrainingData/pos/pos (" + str(i+1) + ").txt"
	file = open(filepath, 'r')
	contents = file.read().split()
	for j in range(len(contents)):
		poscount += 1
		word = contents[j].lower()
		if word in poswords:
			poswords[word] += 1
		else:
			poswords[word] = 1
poswords["totalamountofwords"] = poscount

writer = csv.writer(open('pos.csv', 'wb'))
for key, value in poswords.items():
	writer.writerow([key, value])