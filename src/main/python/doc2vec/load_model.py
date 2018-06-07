from gensim.models import Doc2Vec
from os import listdir
import csv
from constants.paths import *
from train import train


def load_model():
    doc_labels = [f for f in listdir(PREPROCESSED_FILES_DIRECTORY_PATH)]
    train(doc_labels)
    loaded_model = Doc2Vec.load(MODEL_DIRECTORY_PATH)

    f = open(VECTOR_REPRESENTATION_FILE_PATH, 'wb')
    for doc_label in doc_labels:
        wr = csv.writer(f, quoting=csv.QUOTE_NONE)
        wr.writerow([doc_label] + loaded_model.docvecs[doc_label].tolist())
        # print '*'
        # print fel
        # print loaded_model.docvecs[fel]
        # print '\n'
