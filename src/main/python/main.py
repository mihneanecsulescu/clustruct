#!/usr/bin/python

import sys
import getopt
from utils.preprocessor import preprocess
from doc2vec.load_model import load_model
import argparse


def main(argv):
    _, arguments = getopt.getopt(argv, "")
    # arguments = argv.split("'")[1::2]
    new_file_path = arguments.pop(0)
    packages_path_with_class_label = arguments
    preprocess(new_file_path, packages_path_with_class_label)
    load_model()


if __name__ == "__main__":
    # parser = argparse.ArgumentParser()
    # parser.add_argument('new_file_path',
    #                     type=str,
    #                     help='path of the file which will be moved')
    # parser.add_argument('packages_path_with_class_label',
    #                     type=str,
    #                     nargs='+',
    #                     help='path to the other packages')
    # args = vars(parser.parse_args())
    # print args


    main(sys.argv[1:])
    # main("'E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/FeedbackEntity.java' '1|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign' '2|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/constants' '3|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/domain' '4|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/dto' '5|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/exception' '6|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/repository' '7|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/rest' '8|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/service/assignment' '9|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/service/assignment/mapper' '10|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/service/feedback' '11|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/service/feedback/mapper' '12|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/service/submittedassignment' '13|E:/Facultate/Thesis/assignmentdesign/src/main/java/assignmentdesign/service/submittedassignment/mapper'")
    # main("E:/Facultate/III/Design Patterns/iStudent/assignmentdesign/src/main/java/assignmentdesign/FeedbackEntity.java 1E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign 2E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\constants 3E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\domain 4E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\dto 5E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\exception 6E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\repository 7E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\rest 8E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\service\assignment 9E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\service\assignment\mapper 10E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\service\feedback 11E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\service\feedback\mapper 12E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\service\submittedassignment 13E:\Facultate\III\Design Patterns\iStudent\assignmentdesign\src\main\java\assignmentdesign\service\submittedassignment\mapper")
    # preprocess(
    #     'C:/Users/Mihnea/Dropbox/Licenta/dbutils/1.3/AbstractKeyedHandler.java',
    #     ['1C:/Users/Mihnea/Dropbox/Licenta/dbutils/1.3']
    # )
    # file_utils.create_directory(PREPROCESSED_FILES_DIRECTORY_PATH)
    # normalize_text('C:\\Users\\Mihnea\\Dropbox\\Licenta\\dbutils\\1.3', 'ArrayHandler.java', 1)
    # main(sys.argv[1])
