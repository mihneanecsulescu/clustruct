#!/usr/bin/python

from constants.constants import PREPROCESSED_FILES_DIRECTORY_PATH
import file_utils
from normalize_text import normalize_text

def main(argv):
    print 'aaa'


if __name__ == "__main__":
    file_utils.create_directory(PREPROCESSED_FILES_DIRECTORY_PATH)
    normalize_text('C:\\Users\\Mihnea\\Dropbox\\Licenta\\dbutils\\1.3', 'ArrayHandler.java', 1)
    # main(sys.argv[1])
