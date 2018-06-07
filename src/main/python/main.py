#!/usr/bin/python

import sys
import getopt
from utils.preprocessor import preprocess
from doc2vec.load_model import load_model


def main(argv):
    _, arguments = getopt.getopt(argv, "")
    new_file_path = arguments.pop(0)
    packages_path_with_class_label = arguments
    preprocess(new_file_path, packages_path_with_class_label)
    load_model()


if __name__ == "__main__":
    main(sys.argv[1:])
    # preprocess(
    #     'C:/Users/Mihnea/Dropbox/Licenta/dbutils/1.3/AbstractKeyedHandler.java',
    #     ['1C:/Users/Mihnea/Dropbox/Licenta/dbutils/1.3']
    # )
    # file_utils.create_directory(PREPROCESSED_FILES_DIRECTORY_PATH)
    # normalize_text('C:\\Users\\Mihnea\\Dropbox\\Licenta\\dbutils\\1.3', 'ArrayHandler.java', 1)
    # main(sys.argv[1])
