from os import listdir, path
from constants.paths import *
from gensim.models import Doc2Vec
from LabeledLineSentence import LabeledLineSentence


def train(doc_labels):
    # doc_labels = [f for f in listdir(PREPROCESSED_FILES_DIRECTORY_PATH)]

    data = []
    for doc in doc_labels:
        data.append(open(path.join(PREPROCESSED_FILES_DIRECTORY_PATH, doc), 'r').read())

    it = LabeledLineSentence(data, doc_labels)

    model = Doc2Vec(dm=1, size=300, window=12, min_count=1, workers=11, alpha=0.025,
                    min_alpha=0.025)

    model.build_vocab(it)
    for epoch in range(10):
        model.train(it)
        model.alpha -= 0.002  # decrease the learning rate
        model.min_alpha = model.alpha  # fix the learning rate, no decay
        model.train(it)

    model.save(MODEL_DIRECTORY_PATH)
