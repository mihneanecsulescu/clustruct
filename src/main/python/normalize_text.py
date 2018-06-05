from nltk.stem.porter import PorterStemmer
import re
import file_utils
from constants.stop_words import *
from constants.constants import PREPROCESSED_FILES_DIRECTORY_PATH

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

    file_utils.print_to_file(PREPROCESSED_FILES_DIRECTORY_PATH, (str(class_label) + file_name).replace('java', 'txt'), text)

