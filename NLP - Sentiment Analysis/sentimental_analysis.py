# Import necessary libraries
import spacy
from textblob import TextBlob
import pandas as pd
import os

# Setting filepath to directory of script
here = os.path.dirname(os.path.abspath(__file__))
reviews_file = os.path.join(here, "amazon_product_reviews.csv")

# Load data and spacy language model
reviews_df = pd.read_csv(reviews_file)
nlp = spacy.load("en_core_web_md")

# Preprocessing of Data
clean_data = reviews_df.dropna(subset = ["reviews.text"])
reviews_data = clean_data["reviews.text"].drop_duplicates()

# Functions needed for processing
def sentiment(review):
    """
    Takes a product review as input and predicts its sentiment
    """
    doc = nlp(review)
    clean_doc = [token.text for token in doc if not token.is_stop]
    doc = " ".join(clean_doc)
    
    return TextBlob(doc).sentiment.polarity

def is_similar(review1, review2, threshold = 0.7):
    """
    Checks if two reviews are similar up to a given threshold (0.7 by default up to 1.0)
    """
    return nlp(review1).similarity(nlp(review2)) >= threshold

# Categorization of reviews based on sentiment:
# # Positive, Negative or Neutral

positive = []
neutral = []
negative = []

for review in reviews_data:
    sentiment = TextBlob(review).sentiment.polarity

    if sentiment > 0.0:
        positive.append([sentiment, review])
    elif sentiment < 0.0:
        negative.append([sentiment, review])
    else:
        neutral.append([sentiment, review])

print(f"Number of positive reviews: {len(positive)}"+
      f"\nNumber of negative reviews: {len(negative)}"+
      f"\nNumber of neutral reviews: {len(neutral)}")

# Samples of each list
positive_samples = [2684,3833,294,392,3433]
negative_samples = [193,80,191,79,95]
neutral_samples = [73,54,5,201,218]

def print_samples(samples, review_list, type):
    """
    Takes a list of reviews and sample numbers and prints the sentiment and review for each sample
    """
    print(f"\n{type} samples:")
    for sample in samples:
        print(f">>> {review_list[sample][0]:.2f}: {review_list[sample][1]}")

#print(f"\nPositive samples:")
print_samples(positive_samples, positive, "Positive")

#print(f"\nNegative samples:")
print_samples(negative_samples, negative, "Negative")

#print(f"\nNeutral samples:")
print_samples(neutral_samples, neutral, "Neutral")


# Similarity Analysis
def cross_similarity(review, review_list, accuracy = 0.7):
    """
    Returns the number of reviews in a list that are similar to desired review to an indicated level of accuracy
    """
    return len([review_ for review_ in review_list if is_similar(review, review_[1], accuracy) and review != review_[1]])

chosen_review = neutral[54][1]

num_neutral = cross_similarity(chosen_review, neutral, 0.86)
num_negative = cross_similarity(chosen_review, negative, 0.86)
num_positive = cross_similarity(chosen_review, positive, 0.86)

print(
    f"""
    If we consider the review:
        '{chosen_review}'
    We can see that the review appears to be positive though it is classified as neutral.
    If we cross-check its similarity against the all the other reviews with a 86% accuracy threshold:
        {num_neutral} neutral reviews are similar,
        {num_negative} negative reviews are similar and
        {num_positive} positive reviews are simlar.
""")