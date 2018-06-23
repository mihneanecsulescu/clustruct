from nltk.stem.porter import PorterStemmer
import re
from utils import file_utils
from constants.stop_words import *
from constants.paths import PREPROCESSED_FILES_DIRECTORY_PATH
from os import listdir

non_alpha_numeric_re = re.compile(r'[^a-zA-Z]')
extra_blanks_re = re.compile(r'\s+')


def normalize_text(directory_path, file_name, class_label):
    raw_text = open(directory_path + '/' + file_name).read()
    clean_text = non_alpha_numeric_re.sub(' ', raw_text)
    clean_text = extra_blanks_re.sub(' ', clean_text)
    clean_text = clean_text.lower().split(' ')
    clean_text = [w for w in clean_text if not w in JAVA_STOP_WORDS]
    clean_text = [w for w in clean_text if not w in ENGLISH_STOP_WORDS]

    words = clean_text

    porter = PorterStemmer()
    stemmed = [porter.stem(word) for word in words]
    text = ' '.join(stemmed[1:-1])

    if class_label != '':
        file_utils.print_to_file(PREPROCESSED_FILES_DIRECTORY_PATH, (class_label + '-' + file_name).replace('java', 'txt'), text)
    else:
        file_utils.print_to_file(PREPROCESSED_FILES_DIRECTORY_PATH, file_name.replace('java', 'txt'), text)


def preprocess(new_file_path, packages_path_with_class_label):
    file_utils.create_directory(PREPROCESSED_FILES_DIRECTORY_PATH)
    new_file_containing_directory, new_file_name = new_file_path.rsplit('/', 1)
    normalize_text(new_file_containing_directory, new_file_name, '')

    for package_path_with_class_label in packages_path_with_class_label:
        # class_label, package_path = package_path_with_class_label[0], package_path_with_class_label[1:]
        class_label, package_path = package_path_with_class_label.split('|', 1)
        for file_name in listdir(package_path):
            if file_name.endswith('.java') and file_name != new_file_name:
                normalize_text(package_path, file_name, class_label)
