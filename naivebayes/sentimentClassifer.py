import csv
import math

reader = csv.reader(open('neg.csv', 'rb'))
negwords = dict(x for x in reader)
reader = csv.reader(open('pos.csv', 'rb'))
poswords = dict(x for x in reader)


def pTermNeg(term): # the probability that term occurs, given that the review is Negative:
	term = term.lower()
	if term in negwords:
		termfrequency = float(negwords[term]) + 1
	else: 
		termfrequency = 1
	return termfrequency / float(negwords["totalamountofwords"])

def pTermPos(term): # the probability that term occurs, given that the review is Positive:
	term = term.lower()
	if term in poswords:
		termfrequency = float(poswords[term]) + 1
	else: 
		termfrequency = 1
	return (1 + termfrequency) / float(poswords["totalamountofwords"])

def pReviewNeg(review):
	reviewWords = review.split()
	words = set(reviewWords)
	p = 0
	for word in words:
		if pTermNeg(word) != 0:
			p += math.log(pTermNeg(word))
	return p

def pReviewPos(review):
	reviewWords = review.split()
	words = set(reviewWords)
	p = 0
	for word in words:
		if pTermPos(word) != 0:
			p += math.log(pTermPos(word))
	return p

def classify(review):
	if pReviewNeg(review) > pReviewPos(review):
		return "negative"
	else:
		return "positive"

correct = 0
for i in range(500):
	filepath = "../MovieSearchEngine/sentimentTrainingData/neg/neg (" + str(i+500) + ").txt"
	file = open(filepath, 'r')
	contents = file.read()
	if classify(contents) == "negative":
		correct += 1

poscounter = 0
for i in range(500):
	filepath = "../MovieSearchEngine/sentimentTrainingData/pos/pos (" + str(i+500) + ").txt"
	file = open(filepath, 'r')
	contents = file.read()
	if classify(contents) == "positive":
		correct += 1

print float(correct)/float(1000)
