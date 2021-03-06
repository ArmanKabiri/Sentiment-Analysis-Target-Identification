# Sentiment-Analysis-Target-Identification
Improving sentiment analysis by detecting the target in the sentences

---
###### The model implemented in this project is published in IEEE Transactions on Computational Social Systems (Volume: 7, Issue: 1)
---

In a very brief story, in an opinionated long review, there may be several targets described by different potential terms. In this project, a new method is proposed to address this challenge. The implemented model in this project first runs sentence segmentation to decompose a long review into its constituent sentences, and then detects the main target of each sentence. The potential terms are then recognized using a POS tagger and are linked to their coresponding targets. Eventually, a lexicon-based approach is applied to calculate the final score for each review.

The model is implemented in Java 8 employing Weka, HAZM, FarsNet, Apache Common.

The members of the team working on this project are as follows:

Mohammad Ehsan Basiri; Moloud Abdar; Arman Kabiri; Shahla Nemati; Xujuan Zhou; Forough Allahbakhshi; Neil Y. Yen.
